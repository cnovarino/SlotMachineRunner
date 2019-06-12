import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import exceptions.ConfigFileNotFoundException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Settings {

    private static Settings instance;

    private Gson gson;
    private JsonObject settings;
    private String config_path;

    public static Settings getInstance(String config_path) throws ConfigFileNotFoundException {
        if(instance == null) {
            instance = new Settings(config_path);
            try {
                instance.loadConfig();
            } catch (ConfigFileNotFoundException e) {
                throw e;
            }
        }

        return instance;
    }

    private Settings(String config_path) {
        this.config_path = config_path;
        gson = new Gson();
    }

    public JsonObject getSettings() {
        return settings;
    }

    private void loadConfig() throws ConfigFileNotFoundException {
        try {
            JsonReader reader = new JsonReader(new FileReader("resources/" + config_path));
            settings = gson.fromJson(reader,JsonObject.class);
        } catch (FileNotFoundException e) {
            throw new ConfigFileNotFoundException("No se puede acceder al archivo de configuracion. Se carga configuracion por defecto.");
        }
    }
}
