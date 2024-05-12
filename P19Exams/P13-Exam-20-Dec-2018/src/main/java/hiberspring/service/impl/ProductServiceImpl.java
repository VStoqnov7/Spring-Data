package hiberspring.service.impl;

import hiberspring.domain.dtos.ProductImportContainerDTO;
import hiberspring.domain.entities.Branch;
import hiberspring.domain.entities.Product;
import hiberspring.repository.BranchRepository;
import hiberspring.repository.ProductRepository;
import hiberspring.service.ProductService;
import hiberspring.util.FileUtil;
import hiberspring.util.ValidationUtil;
import hiberspring.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

import static hiberspring.common.Constants.*;

@Service
public class ProductServiceImpl implements ProductService {
    private final String PRODUCT_PATH = PATH_TO_FILES + "products.xml";
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final XmlParser xmlParser;
    private final ProductRepository productRepository;
    private final BranchRepository branchRepository;
    private final FileUtil fileUtil;

    public ProductServiceImpl(ModelMapper modelMapper, ValidationUtil validationUtil, XmlParser xmlParser, ProductRepository productRepository, BranchRepository branchRepository, FileUtil fileUtil) {
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.xmlParser = xmlParser;
        this.productRepository = productRepository;
        this.branchRepository = branchRepository;
        this.fileUtil = fileUtil;
    }

    @Override
    public Boolean productsAreImported() {
        return this.productRepository.count() > 0;
    }

    @Override
    public String readProductsXmlFile() throws IOException {
        return fileUtil.readFile(PRODUCT_PATH);
    }

    @Override
    public String importProducts() throws JAXBException, IOException {
        StringBuilder sb = new StringBuilder();

        ProductImportContainerDTO productDTO = xmlParser.parseXml(ProductImportContainerDTO.class, PRODUCT_PATH);

        productDTO.getProducts()
                .forEach(dto-> {
                    boolean isValid = validationUtil.isValid(dto);
                    Optional<Branch> branchByName = this.branchRepository.findByName(dto.getBranch());
                    if (isValid && branchByName.isPresent()){
                        Product product = modelMapper.map(dto,Product.class);
                        product.setBranch(branchByName.get());
                        this.productRepository.save(product);
                        sb.append(String.format(SUCCESSFUL_IMPORT_MESSAGE,"Product",dto.getName())).append(System.lineSeparator());
                    }else {
                        sb.append(INCORRECT_DATA_MESSAGE).append(System.lineSeparator());
                    }
                });


        return sb.toString().trim();
    }
}
