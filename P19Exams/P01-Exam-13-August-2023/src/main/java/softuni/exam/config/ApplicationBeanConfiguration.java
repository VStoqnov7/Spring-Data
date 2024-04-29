package softuni.exam.config;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import softuni.exam.util.MyValidator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Configuration
public class ApplicationBeanConfiguration {

    @Bean
    public ModelMapper modelMapper(){
        ModelMapper modelMapper = new ModelMapper();

//        LocalDate ->

//        modelMapper.addConverter(new Converter<String, LocalDate>() {
//            @Override
//            public LocalDate convert(MappingContext<String, LocalDate> mappingContext) {
//
//                LocalDate parse = LocalDate
//                        .parse(mappingContext.getSource(),
//                                DateTimeFormatter.ofPattern("yyyy-MM-dd"));
//                return parse;
//            }
//        });

//        LocalDateTime ->

//        modelMapper.addConverter(new Converter<String, LocalDateTime>() {
//            @Override
//            public LocalDateTime convert(MappingContext<String, LocalDateTime> mappingContext) {
//                LocalDateTime parse = LocalDateTime.parse(mappingContext.getSource(),
//                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
//                return parse;
//            }
//        });
//

//        LocalTime ->

//        modelMapper.addConverter(new Converter<String, LocalTime>() {
//            @Override
//            public LocalTime convert(MappingContext<String, LocalTime> mappingContext) {
//                LocalTime parse = LocalTime.parse(mappingContext.getSource(),
//                        DateTimeFormatter.ofPattern("HH:mm:ss"));
//                return parse;
//            }
//        });
        return modelMapper;
    }

    @Bean
    public MyValidator validator(){
        return new MyValidator();
    }
}
