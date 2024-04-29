package softuni.exam.util;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class LocalTimeAdapterGson extends TypeAdapter<LocalTime> {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    @Override
    public void write(JsonWriter out, LocalTime value) throws IOException {
        if (value == null) {
            out.nullValue();
        } else {
            out.value(formatter.format(value));
        }
    }

    @Override
    public LocalTime read(JsonReader in) throws IOException {
        if (in.peek() == null) {
            return null;
        }
        return LocalTime.parse(in.nextString(), formatter);
    }
}

//    Gson gson = new GsonBuilder()
//            .registerTypeAdapter(LocalTime.class, new LocalTimeAdapter())
//            .create();
