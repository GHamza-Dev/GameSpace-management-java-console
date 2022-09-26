package org.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.gamespace.Reservation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
