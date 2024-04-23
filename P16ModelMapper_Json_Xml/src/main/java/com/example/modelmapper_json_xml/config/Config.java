package com.example.modelmapper_json_xml.config;

import com.google.gson.*;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import java.util.Scanner;

@Configuration
public class Config {

    @Bean
    public ModelMapper createModelMapper() {
        ModelMapper modelMapper = new ModelMapper();

//        Converter<User,String> userToFullNameConverter = context -> context.getSource() == null ? null : context.getSource().getFirstName();
//        modelMapper.addConverter(userToFullNameConverter);



        return modelMapper;
    }
    @Bean /*("име на been ")*/
//  /*@Primary*/    за предимство на bean
    public Gson createGson() {
        return new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()

//                .registerTypeAdapter(LocalDateTime.class, new JsonSerializer<LocalDateTime>() {
//                    @Override
//                    public JsonElement serialize(LocalDateTime src, Type typeOfSrc, JsonSerializationContext context) {
//                        return new JsonPrimitive(src.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
//                    }
//                })
//                .registerTypeAdapter(LocalDateTime.class, new JsonDeserializer<LocalDateTime>() {
//                    @Override
//                    public LocalDateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
//                        return LocalDateTime.parse(json.getAsString(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
//                    }
//                })

//                .registerTypeAdapter(CustomerExportDTO.class, new JsonSerializer<CustomerExportDTO>() {
//                    @Override
//                    public JsonElement serialize(CustomerExportDTO src, Type typeOfSrc, JsonSerializationContext context) {
//                        JsonObject jsonObject = new JsonObject();
//                        jsonObject.addProperty("Id", src.getId());
//                        jsonObject.addProperty("Name", src.getName());
//                        jsonObject.addProperty("BirthDate", src.getBirthDate().toString());
//                        jsonObject.addProperty("IsYoungDriver", src.isYoungDriver());
//                        jsonObject.addProperty("IsYoungDriver", src.isYoungDriver());
//                        jsonObject.add("Sales", new JsonArray());          //  Empty array
//                        return jsonObject;
//                    }})

                .create();
    }

    @Bean
    public Scanner createScanner() {
        return new Scanner(System.in);
    }
}
