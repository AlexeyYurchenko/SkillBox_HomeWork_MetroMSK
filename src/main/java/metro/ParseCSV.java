package metro;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParseCSV {
    private ArrayList<String> lines = new ArrayList<>();
    private Map<String,String> stationsDate = new HashMap<>();


    public List<String> parseCSV(String path){
        ArrayList<String> line;
        try {
            line = (ArrayList<String>) Files.readAllLines(Paths.get(path));
            lines.addAll(line);
        } catch (Exception ex) {
            ex.getMessage();
        }
        return lines;
    }
    public Map<String,String> splitCSV(){
        String firstLine = null;
        for(String line : lines) {
            if(firstLine == null) {
                firstLine = line;
                continue;
            }
            String[] tokens = line.split(",");
            stationsDate.put(tokens[0],tokens[1]);
        }
        return stationsDate;
    }

    public Map<String, String> getStationsDate() {
        return stationsDate;
    }
}