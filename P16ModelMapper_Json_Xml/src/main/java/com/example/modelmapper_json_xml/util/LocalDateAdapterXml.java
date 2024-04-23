package com.example.modelmapper_json_xml.util;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateAdapterXml extends XmlAdapter<String, LocalDate> {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public LocalDate unmarshal(String v) {
        return LocalDate.parse(v, formatter);
    }

    @Override
    public String marshal(LocalDate v) {
        return v.format(formatter);
    }
}
//@XmlJavaTypeAdapter(LocalDateAdapter.class)
