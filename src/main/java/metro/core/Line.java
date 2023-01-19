package metro.core;

import java.util.ArrayList;
import java.util.List;

public class Line {
    private String numberLine;
    private String nameLine;
    private List<String> stations;
    public Line(String name, String number) {
        this.numberLine = number;
        this.nameLine = name;
        this.stations = new ArrayList<>();

    }
    public void addStation(String station) {

        stations.add(station);
    }

    public List<String> getStations() {
        return stations;
    }

    @Override
    public String toString() {
        return "Номер линии: " + getNumberLine() + " Линия: " + getNameLine() + " Станции: " + getStations();
    }
    public String getNumberLine() {
        return this.numberLine;
    }

    public String getNameLine() {
        return this.nameLine;
    }
}