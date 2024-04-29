package softuni.exam.util;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class LocalTimeAdapterXml extends XmlAdapter<String, LocalTime> {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    @Override
    public LocalTime unmarshal(String v) {
        return LocalTime.parse(v, formatter);
    }

    @Override
    public String marshal(LocalTime v) {
        return v.format(formatter);
    }
}

//@XmlJavaTypeAdapter(LocalTimeAdapter.class)