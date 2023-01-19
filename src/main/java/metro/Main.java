package metro;

import metro.core.Line;
import metro.core.Station;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.nio.file.Paths;
import java.util.List;

public class Main {
    private static StationIndex stationIndex;
    private static final String FILE_STATIONS = "src/main/resources/fileStations.json";
    private static final String FILE_METRO = "src/main/resources/MetroStations.json";
    private final static String PATH = "data/metro.html";
    private final static String PATH_STATION_DATA = "data/stations-data";
    private final static String PATH_DATES_1 = "data/stations-data/data/4/6/dates-1.csv";
    private final static String PATH_DATES_2 = "data/stations-data/data/0/5/dates-2.csv";
    private final static String PATH_DATES_3 = "data/stations-data/data/9/6/dates-3.csv";
    private final static String PATH_DEPTHS_1 = "data/stations-data/data/2/4/depths-1.json";
    private final static String PATH_DEPTHS_2 = "data/stations-data/data/7/1/depths-2.json";
    private final static String PATH_DEPTHS_3 = "data/stations-data/data/4/6/depths-3.json";

    public static void main(String[] args) {
        start();
        createJsonMetro();
        createJsonStations();
        getSearchFiles();
    }

    private static void start() {

        ParseCSV parseCSV = new ParseCSV(); // TODO Класс парсинга файлов формата CSV.
        parseCSV.parseCSV(PATH_DATES_1);
        parseCSV.parseCSV(PATH_DATES_2);
        parseCSV.parseCSV(PATH_DATES_3);

        ParseJson parseJson = new ParseJson(); // TODO Класс парсинга файлов формата JSON.
        parseJson.createStationIndex(PATH_DEPTHS_1);
        parseJson.createStationIndex(PATH_DEPTHS_2);
        parseJson.createStationIndex(PATH_DEPTHS_3);

        ParserJsoup parserJsoup = new ParserJsoup(PATH); // TODO Класс парсинга веб-страницы.
        stationIndex = new StationIndex(parserJsoup, parseCSV, parseJson); // TODO Класс, в который можно добавлять данные, полученные на предыдущих шагах.
//        stationIndex.printParseJson();
//        stationIndex.printParseCSV();
//        stationIndex.getStations().forEach(System.out::println);
//        stationIndex.getLines().forEach(System.out :: println);
    }

    private static void createJsonStations() { // TODO файл stations.json со свойствами станций.
        JSONArray jsonArray = new JSONArray();
        for (Station st : stationIndex.getStations()) {
            JSONObject jsonObject = new JSONObject();
            if (st.getNameStation() != null) {
                jsonObject.put("name", st.getNameStation());
            }
            if (st.getNameLine() != null) {
                jsonObject.put("line", st.getNameLine());
            }
            if (st.getDate() != null) {
                jsonObject.put("date", st.getDate());
            }
            if (st.getDepthStation() != null) {
                jsonObject.put("depth", st.getDepthStation());
            }
            jsonObject.put("hasConnection", st.isHasConnection());
            jsonArray.add(jsonObject);
        }
        JSONObject jsonStation = new JSONObject();
        jsonStation.put("stations", jsonArray);
//        System.out.println(jsonStation);
        try {
            FileWriter writer = new FileWriter(FILE_STATIONS);
            writer.write(jsonStation.toJSONString());
            writer.flush();
            writer.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void createJsonMetro() { // TODO со списком станций по линиям и списком линий по формату JSON-файла из проекта SPBMetro.
        JSONObject stationObject2 = new JSONObject();
        JSONArray lineArray = new JSONArray();
        for (Line ln : stationIndex.getLines()) {
            stationObject2.put(ln.getNumberLine(), ln.getStations());
        }
        for (Line ln2 : stationIndex.getLines()) {
            JSONObject lineObject2 = new JSONObject();
            lineObject2.put("number", ln2.getNumberLine());
            lineObject2.put("name", ln2.getNameLine());
            lineArray.add(lineObject2);
        }
        JSONObject jsonStation2 = new JSONObject();
        jsonStation2.put("stations", stationObject2);
        jsonStation2.put("lines", lineArray);
//        System.out.println(jsonStation2);
        try {
            FileWriter writer2 = new FileWriter(FILE_METRO);
            writer2.write(jsonStation2.toJSONString());
            writer2.flush();
            writer2.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void getSearchFiles() { // TODO поиск файлов в папках. В методах этого класса необходимо реализовать обход папок, лежащих в архиве.
        SearchFiles searchFiles = new SearchFiles();
        String[] extensions = {"json", "csv"};
        List<String> files = searchFiles.findFiles(Paths.get(PATH_STATION_DATA), extensions);
        files.forEach(System.out::println);
    }
}