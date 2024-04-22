package com.example.productsshop.services.car;

import com.example.productsshop.entities.car.Car;
import com.example.productsshop.entities.car.CarAndPartsDTO;
import com.example.productsshop.entities.car.CarExportToyotaDTO;
import com.example.productsshop.entities.customer.CustomerExportDTO;
import com.example.productsshop.repositories.CarRepository;
import com.google.gson.*;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.productsshop.enums.OutJsonPaths.CAR_AND_PARTS;
import static com.example.productsshop.enums.OutJsonPaths.TOYOTA_CARS;

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
                .registerTypeAdapter(CarAndPartsDTO.class, new JsonSerializer<CarAndPartsDTO>() {
                    @Override
                    public JsonElement serialize(CarAndPartsDTO src, Type typeOfSrc, JsonSerializationContext context) {
                        JsonObject jsonObject = new JsonObject();
                        jsonObject.addProperty("Make", src.getMake());
                        jsonObject.addProperty("Model", src.getModel());
                        jsonObject.addProperty("TravelledDistance", src.getTravelledDistance());
                        // Мапиране на списъка с части (parts)
                        JsonArray partsArray = new JsonArray();
                        src.getParts().forEach(part -> {
                            JsonObject partObject = new JsonObject();
                            partObject.addProperty("Name", part.getName());
                            partObject.addProperty("Price", part.getPrice());
                            partsArray.add(partObject);
                        });
                        jsonObject.add("parts", partsArray);
                        return jsonObject;
                    }})
                .create();
    }



    @Override
    public void exportToyotaCars() {
        List<Car> toyotaCars = carRepository.findAllByMakeOrderByModelAscTravelledDistanceDesc("Toyota");

        List<CarExportToyotaDTO> exportDTOs = toyotaCars.stream()
                .map(customer -> modelMapper.map(customer,CarExportToyotaDTO.class))
                .collect(Collectors.toList());

        try (FileWriter writer = new FileWriter(TOYOTA_CARS)) {
            gson.toJson(exportDTOs, writer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void exportAllCarsAndParts() {
        List<Car> cars = this.carRepository.findAll();

        List<CarAndPartsDTO> carsAndParts = cars.stream()
                .map(car -> modelMapper.map(car, CarAndPartsDTO.class))
                .collect(Collectors.toList());

        try(FileWriter writer = new FileWriter(CAR_AND_PARTS)){
            gson.toJson(carsAndParts,writer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
