package softuni.exam.util;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateAdapterGson extends TypeAdapter<LocalDate> {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @Override
    public void write(JsonWriter jsonWriter, LocalDate date) throws IOException {
        if (date == null){
            return;
        }
        jsonWriter.value(formatter.format(date));
    }

    @Override
    public LocalDate read(JsonReader jsonReader) throws IOException {
        if (jsonReader.peek() == null){
            return null;
        }
        return LocalDate.parse(jsonReader.nextString(),formatter);
    }
}
////    Gson gson = new GsonBuilder()
////            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())