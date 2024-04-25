package com.example.productshop.services.supplier;


import com.example.productshop.entities.car.CarExportContainerDTO;
import com.example.productshop.entities.car.CarExportDTO;
import com.example.productshop.entities.supplier.Supplier;
import com.example.productshop.entities.supplier.SupplierExportContainerDTO;
import com.example.productshop.entities.supplier.SupplierExportDTO;
import com.example.productshop.repositories.SupplierRepository;
import com.example.productshop.services.supplier.SupplierService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.productshop.enums.OutXmlPaths.LOCAL_SUPPLIERS;
import static com.example.productshop.enums.OutXmlPaths.TOYOTA_CARS;

@Service
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository;
    private final Gson gson;

    private final ModelMapper modelMapper;


    public SupplierServiceImpl(SupplierRepository supplierRepository, Gson gson) {
        this.supplierRepository = supplierRepository;
        this.modelMapper = new ModelMapper();
        this.gson = new GsonBuilder()
            .setPrettyPrinting()
            .create();
    }

    @Override
    public void exportSuppliers() throws JAXBException, IOException {
        List<Supplier> suppliers = this.supplierRepository.findAll()
                .stream()
                .filter(supplier -> !supplier.isImporter())
                .collect(Collectors.toList());

        List<SupplierExportDTO> supplierDTOS = suppliers.stream()
                .map(supplier -> {
                    SupplierExportDTO exportDTO = modelMapper.map(supplier, SupplierExportDTO.class);
                    exportDTO.setPartsCount(supplier.getParts().size());
                    return exportDTO;
                })
                .collect(Collectors.toList());

        SupplierExportContainerDTO supplierContainerDTO = new SupplierExportContainerDTO();
        supplierContainerDTO.setSuppliers(supplierDTOS);

        JAXBContext context = JAXBContext.newInstance(SupplierExportContainerDTO.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,true);
        try(FileWriter writer = new FileWriter(LOCAL_SUPPLIERS)){
            marshaller.marshal(supplierContainerDTO,writer);
        }

    }
}
