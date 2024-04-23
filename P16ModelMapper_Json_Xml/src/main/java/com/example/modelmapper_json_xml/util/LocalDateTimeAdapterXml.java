package com.example.modelmapper_json_xml.util;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeAdapterXml extends XmlAdapter<String, LocalDateTime> {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    @Override
    public LocalDateTime unmarshal(String v) {
        return LocalDateTime.parse(v, formatter);
    }

    @Override
    public String marshal(LocalDateTime v) {
        return v.format(formatter);
    }
}


//@XmlJavaTypeAdapter(LocalDateTimeAdapter.class)