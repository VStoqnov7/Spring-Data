package softuni.exam.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import softuni.exam.util.LocalDateAdapter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;


@Configuration
public class ApplicationBeanConfiguration {


    @Bean
    public Gson gson() {
        return new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();
    }

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

}
