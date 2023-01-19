package metro;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.FileReader;

public class ParserJsoup {
    private String parseHtml;
    private String path;
    private Elements elementsOfLines;
    private Elements elementsOfStations;
    private Elements elementsOfConnection;


    public ParserJsoup(String path) {

        this.path = path;
        parseHtml();
        parseLines();
        parseStations();
        parseConnection();
    }

    public String parseHtml() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                parseHtml += line + "\r\n";
            }
        } catch (Exception e) {
            e.getMessage();
        }
        return parseHtml;
    }

    public Elements parseLines() {
        try {
            Document doc = Jsoup.parse(parseHtml);
            elementsOfLines = doc.getElementsByAttributeValueStarting("class",
                    "js-metro-line t-metrostation-list-header t-icon-metroln ln");
        } catch (Exception ex) {
            ex.getMessage();
        }
        return elementsOfLines;
    }

    public Elements parseStations() {
        try {
            Document doc = Jsoup.parse(parseHtml);
            elementsOfStations = doc.getElementsByClass("js-metro-stations");
        } catch (Exception ex) {
            ex.getMessage();
        }
        return elementsOfStations;
    }

    public Elements parseConnection() {
        try {
            Document doc = Jsoup.parse(parseHtml);
            elementsOfConnection = doc.getElementsByClass("js-metro-stations t-metrostation-list-table");
        } catch (Exception ex) {
            ex.getMessage();
        }
        return elementsOfConnection;
    }

    public Elements getElementsOfLines() {
        return elementsOfLines;
    }

    public Elements getElementsOfStations() {
        return elementsOfStations;
    }

    public Elements getElementsOfConnection() {
        return elementsOfConnection;
    }
}