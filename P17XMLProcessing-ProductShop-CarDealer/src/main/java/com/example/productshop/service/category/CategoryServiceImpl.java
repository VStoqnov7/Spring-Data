package com.example.productshop.services.category;

import com.example.productshop.entities.category.Category;
import com.example.productshop.entities.category.CategoryByProductCountContainerDTO;
import com.example.productshop.entities.category.CategoryByProductCountDTO;
import com.example.productshop.repositories.CategoryRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static com.example.productshop.enums.OutXmlPaths.CATEGORIES_BY_PRODUCTS_COUNT;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    private final Gson gson;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        this.modelMapper = new ModelMapper();
    }

    @Override
    public void categoriesByProductsCount() throws JAXBException, IOException {
        List<CategoryByProductCountDTO> categories = this.categoryRepository.findAllCategoriesByProductsCount();

        CategoryByProductCountContainerDTO containerDTO = new CategoryByProductCountContainerDTO();
        containerDTO.setCategories(categories);

        JAXBContext context = JAXBContext.newInstance(CategoryByProductCountContainerDTO.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        try (FileWriter writer = new FileWriter(CATEGORIES_BY_PRODUCTS_COUNT)) {
            marshaller.marshal(containerDTO, writer);
        }


    }
}
