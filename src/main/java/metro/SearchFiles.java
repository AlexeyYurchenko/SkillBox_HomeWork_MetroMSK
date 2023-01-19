package metro;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SearchFiles {
    public static List<String> findFiles(Path path, String[] fileExtensions) {
        List<String> result = new ArrayList<>();
        if (!Files.isDirectory(path)) {
            System.out.println("Путь должен быть каталогом!");
        } else {
            Stream<Path> walk;
            try {
                walk = Files.walk(path);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            result = walk
                    .filter(p -> !Files.isDirectory(p))
                    .map(p -> p.toString().toLowerCase())
                    .filter(f -> Arrays.stream(fileExtensions).anyMatch(f::endsWith))
                    .collect(Collectors.toList());
        }
        return result;
    }
}