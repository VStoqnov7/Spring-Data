package exam.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import exam.util.LocalDateAdapterGson;
import exam.util.MyValidator;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

@Configuration
public class ApplicationBeanConfiguration {

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

    @Bean
    public MyValidator validator(){
        return new MyValidator();
    }

    @Bean
    public Gson gson(){
        return new GsonBuilder()
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapterGson())
            .create();
    }
}
