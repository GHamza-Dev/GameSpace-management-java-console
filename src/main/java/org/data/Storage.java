package org.data;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.file.Paths;
public class Storage {
    public static void store(String pathname, Object objects){
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(Paths.get(pathname).toFile(), objects);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
