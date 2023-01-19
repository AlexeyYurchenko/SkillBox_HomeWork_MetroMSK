package metro;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

public class ParseJson {
    private Map<String, String> stationsDepth = new HashMap<>();

    public void createStationIndex(String path) {
        try {
            FileReader reader = new FileReader(path);
            JSONParser parser = new JSONParser();
            JSONArray jObj = (JSONArray) parser.parse(reader);
            jObj.forEach(stationObject -> {
                JSONObject stationJsonObject = (JSONObject) stationObject;
                stationsDepth.put((String) stationJsonObject.get("station_name"), ((String) stationJsonObject.get("depth")));
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public Map<String, String> getStationsDepth() {
        return stationsDepth;
    }
}