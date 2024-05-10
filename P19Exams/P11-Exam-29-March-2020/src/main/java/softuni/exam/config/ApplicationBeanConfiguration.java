package softuni.exam.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import softuni.exam.util.LocalDateAdapterGson;
import softuni.exam.util.LocalDateTimeAdapterGson;
import softuni.exam.util.ValidationUtilImpl;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Configuration
public class ApplicationBeanConfiguration {

    @Bean
    public Gson gson() {
        return new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapterGson())
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapterGson())
                .create();
    }

    @Bean
    public ValidationUtilImpl validationUtil() {
        return new ValidationUtilImpl();
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

}
