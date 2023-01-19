package metro;

import metro.core.Line;
import metro.core.Station;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class StationIndex {
    Station station;
    ParserJsoup parserJsoup;
    ParseCSV parseCSV;
    ParseJson parseJson;
    private List<Line> lines;
    private List<Station> stations;

    public StationIndex(ParserJsoup parserJsoup, ParseCSV parseCSV, ParseJson parseJson) {
        this.parseJson = parseJson;
        this.parseCSV = parseCSV;
        parseCSV.splitCSV();
        this.parserJsoup = parserJsoup;
        addStations();
        addLines();
        addDate();
        addDepth();
        addConnection();
        addNameLineInStation();
        addStationInLine();
    }

    public List addLines() {
        Elements elements = parserJsoup.getElementsOfLines();
        lines = new LinkedList<>();
        elements.forEach((line) -> lines.add(new Line(line.ownText(), line.attr("data-line"))));
        return lines;
    }

    public List addStations() {
        Elements elements = parserJsoup.getElementsOfStations();
        String REGEX_FOR_STATION = "\\d\\d?.";
        stations = new LinkedList<>();
        for (Element element : elements) {
            String[] arrayStation = element.text().replaceAll(REGEX_FOR_STATION, "").split("\\s\\s");
            for (int i = 0; i < arrayStation.length; i++) {
                station = new Station(arrayStation[i].trim(), element.attr("data-line"));
                stations.add(station);
            }
        }
        return stations;
    }

    public void addConnection() {
        String REGEX_FOR_STATION = "\\d\\d?.";
        Elements elements = parserJsoup.getElementsOfConnection();
        for (Element element : elements) {
            Elements connectionsList = element.select("p:has(span[title])");
            for (Element connectionElement : connectionsList) {
                String[] connectionStation = connectionElement.text().replaceAll(REGEX_FOR_STATION, "").split("\\s\\s");
                for (String s : connectionStation) {
                    String connection = s.trim();
                    for (Station station1 : stations) {
                        if (station1.getNameStation().contains(connection)) {
                            station1.setHasConnection(true);
                        }
                    }
                }
            }

        }
    }

    public void addNameLineInStation() {
        for (Line line1 : lines) {
            for (Station station1 : stations) {
                if (line1.getNumberLine().equals(station1.getNumberLine())) {
                    station1.setNameLine(line1.getNameLine());
                }
            }
        }
    }

    public void addDate() {
        for (Map.Entry<String, String> entry : parseCSV.getStationsDate().entrySet()) {
            String nameCSV = entry.getKey();
            String dateCSV = entry.getValue();
            for (Station station1 : stations) {
                if (station1.getNameStation().equals(nameCSV)) {
                    station1.setDate(dateCSV);
                }
            }
        }
    }

    public void addDepth() {
        for (Map.Entry<String, String> entry : parseJson.getStationsDepth().entrySet()) {
            String nameJson = entry.getKey();
            String depthJson = entry.getValue();
            for (Station station1 : stations) {
                if (station1.getNameStation().equals(nameJson)) {
                    station1.setDepthStation(depthJson);
                }
            }
        }
    }
    public void addStationInLine(){
        for (Line line1 : lines) {
            for (Station station1 : stations) {
                if (line1.getNumberLine().equals(station1.getNumberLine())) {
                    line1.addStation(station1.getNameStation());
                }
            }
        }
    }

    public void printParseCSV() {
        for (Map.Entry entry : parseCSV.getStationsDate().entrySet()) {
            System.out.println("Название станции: " + entry.getKey() + " Дата открытия: " + entry.getValue());
        }
    }

    public void printParseJson() {
        for (Map.Entry entry : parseJson.getStationsDepth().entrySet()) {
            System.out.println("Название станции: " + entry.getKey() + " Глубина станции: " + entry.getValue());
        }

    }

    public List<Line> getLines() {
        return lines;
    }

    public List<Station> getStations() {
        return stations;
    }
}