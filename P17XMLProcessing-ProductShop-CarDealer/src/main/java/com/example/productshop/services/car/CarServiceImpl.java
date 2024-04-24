package com.example.productshop.services.car;

import com.example.productshop.entities.car.*;
import com.example.productshop.entities.product.Product;
import com.example.productshop.entities.product.ProductsInRangeContainerDTO;
import com.example.productshop.entities.product.ProductsInRangeExportDTO;
import com.example.productshop.repositories.CarRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.productshop.enums.OutXmlPaths.*;

@Service
public class CarServiceImpl implements CarService{
    private final CarRepository carRepository;
    private final Gson gson;

    private final ModelMapper modelMapper;

    public CarServiceImpl(CarRepository carRepository) {
        this.carRepository = carRepository;
        this.modelMapper = new ModelMapper();
        this.gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
    }

    @Override
    public void exportToyotaCars() throws JAXBException, IOException {
        List<Car> cars = this.carRepository.findAllByOrderByModelAscTravelledDistanceDesc();

        List<CarExportDTO> carExportDTOS = cars.stream()
                .map(car -> modelMapper.map(car,CarExportDTO.class))
                .collect(Collectors.toList());
        CarExportContainerDTO carContainerDTO = new CarExportContainerDTO();
        carContainerDTO.setCars(carExportDTOS);

        JAXBContext context = JAXBContext.newInstance(CarExportContainerDTO.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,true);
        try(FileWriter writer = new FileWriter(TOYOTA_CARS)){
            marshaller.marshal(carContainerDTO,writer);
        }

    }
    @Override
    public void exportAllCarsAndParts() throws JAXBException, IOException {

        List<Car> cars = this.carRepository.findAll();

        List<CarAndPartsExportDTO> carAndPartsDTOS = cars.stream()
                .map(car -> modelMapper.map(car, CarAndPartsExportDTO.class))
                .collect(Collectors.toList());

        CarAndPartsExportContainerDTO carContainerDTO = new CarAndPartsExportContainerDTO();
        carContainerDTO.setCars(carAndPartsDTOS);

        JAXBContext context = JAXBContext.newInstance(CarAndPartsExportContainerDTO.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,true);
        try(FileWriter writer = new FileWriter(CAR_AND_PARTS)){
            marshaller.marshal(carContainerDTO,writer);
        }

    }
}
