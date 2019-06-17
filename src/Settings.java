import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import exceptions.ConfigFileNotFoundException;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class Settings {

    private static Settings instance;

    private Gson gson;
    private JsonObject settings;
    private String configPath;

    public static Settings getInstance(String configPath) throws ConfigFileNotFoundException {
        if(instance == null) {
            instance = new Settings(configPath);
            try {
                instance.loadConfig();
            } catch (ConfigFileNotFoundException e) {
                throw e;
            }
        }

        return instance;
    }

    private Settings(String configPath) {
        this.configPath = configPath;
        gson = new Gson();
    }

    public JsonObject getSettings() {
        return settings;
    }

    private void loadConfig() throws ConfigFileNotFoundException {
        try {
            JsonReader reader = new JsonReader(new FileReader("resources/" + configPath));
            settings = gson.fromJson(reader,JsonObject.class);
        } catch (FileNotFoundException e) {
            throw new ConfigFileNotFoundException("No se puede acceder al archivo de configuracion. Se carga configuracion por defecto.");
        }
    }
}
