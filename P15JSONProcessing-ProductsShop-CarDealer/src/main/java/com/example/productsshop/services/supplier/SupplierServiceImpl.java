package com.example.productsshop.services.supplier;

import com.example.productsshop.entities.supplier.Supplier;
import com.example.productsshop.entities.supplier.SupplierExportDTO;
import com.example.productsshop.repositories.SupplierRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.productsshop.enums.OutJsonPaths.LOCAL_SUPPLIERS;

@Service
public class SupplierServiceImpl implements SupplierService{

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
    public void exportSuppliers() {
        List<Supplier> suppliers = this.supplierRepository.findAll();

        List<SupplierExportDTO> suppliersNotImportParts = suppliers.stream()
                .filter(supplier -> !supplier.isImporter())
                .map(supplier -> {
                    SupplierExportDTO exportDTO = modelMapper.map(supplier, SupplierExportDTO.class);
                    exportDTO.setPartsCount(supplier.getParts().size());
                    return exportDTO;
                })
                .collect(Collectors.toList());

        try(FileWriter writer = new FileWriter(LOCAL_SUPPLIERS)) {
            gson.toJson(suppliersNotImportParts, writer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
