package softuni.exam.util;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeAdapterGson extends TypeAdapter<LocalDateTime> {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    @Override
    public void write(JsonWriter jsonWriter, LocalDateTime localDateTime) throws IOException {
        if (localDateTime == null){
            return;
        }
        jsonWriter.value(formatter.format(localDateTime));
    }

    @Override
    public LocalDateTime read(JsonReader jsonReader) throws IOException {
        if (jsonReader.peek() == null){
            return null;
        }
        return LocalDateTime.parse(jsonReader.nextString(),formatter);
    }
}


////    Gson gson = new GsonBuilder()
////            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())