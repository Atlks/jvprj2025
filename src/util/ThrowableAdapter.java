package util;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.Arrays;

public class ThrowableAdapter extends TypeAdapter<Throwable> {
    @Override
    public void write(JsonWriter out, Throwable value) throws IOException {
        out.beginObject();
        out.name("type").value(value.getClass().getName());
        out.name("message").value(value.getMessage());
        out.name("stackTrace").value(Arrays.toString(value.getStackTrace()));
      //  if(value instanceof )
        out.endObject();
    }

    @Override
    public Throwable read(JsonReader in) throws IOException {
        // 反序列化通常不需要
        return null;
    }
}
