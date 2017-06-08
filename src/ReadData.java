import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * Created by Adrian Ispas on 07.06.2017.
 * Read data from a specific file and put it in an array.
 */
public class ReadData {
    static ArrayList<String[]> readData(String file){
        ArrayList<String[]> lines = new ArrayList<>();

        // Read data from file.
        try {
            int countLines = 1;
            for (String line : Files.readAllLines(Paths.get("input/" + file))) {
                String[] currentLine = line.split(" _ ");

                // Check if the data contains all information
                if(currentLine.length < 8){
                    System.out.println("Linia " + countLines + " are inregistrari incomplete!");
                }
                else {
                    lines.add(currentLine);
                }
                countLines++;
            }
        } catch (IOException e) {
            System.out.println("Fisierul " + file + " nu a fost gasit.");
        }

        return lines;
    }
}
