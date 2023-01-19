package metro.core;

import java.util.Objects;

public class Station {

    private String nameStation;
    private String numberLine;
    private String depthStation;
    private String date;
    private boolean hasConnection;
    private String nameLine;


    public Station(String name, String line) {
        this.numberLine = line;
        this.nameStation = name;

    }

    public String getNameStation() {
        return this.nameStation;
    }
    public String getNumberLine() {
        return numberLine;
    }

    public String getNameLine() {
        return nameLine;
    }

    public void setNameLine(String nameLine) {
        this.nameLine = nameLine;
    }

    public String getDepthStation() {
        return depthStation;
    }

    public void setDepthStation(String depthStation) {
        this.depthStation = depthStation;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isHasConnection() {
        return hasConnection;
    }

    public void setHasConnection(boolean hasConnection) {
        this.hasConnection = hasConnection;
    }

    @Override
    public String toString() {
        return "Линия: " + getNumberLine() +  "  Линия: " +getNameLine() + "  Станции: " + getNameStation() + "  Дата открытия: "
                + getDate() + "  Глубина станции: " + getDepthStation() + "  Переход: " + isHasConnection();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Station station = (Station) o;
        return Objects.equals(nameStation, station.nameStation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nameStation);
    }
}