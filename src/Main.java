import Interfaces.IPlace;
import org.json.JSONException;

import java.sql.* ;
import java.text.ParseException;

/**
 * Created by Adrian Ispas on 07.06.2017.
 */
public class Main {
    public static void main(String[] args) throws SQLException, JSONException, ParseException {
        String file = "entities.txt";

        // Create the connection to database
        Connection conn = DBConnect.createConnection();

        // Save entries into database. Can run just first time or when we have new entries to add
        SaveData.saveIntoDB(conn,file);

        // Task 1)
        System.out.println("\nTaks 1");
        IPlace placeTask_1 = Tasks.findLocation(conn,"Cabana A");
        System.out.println(placeTask_1);

        // Task 2)

        // Task 3)
        System.out.println("\nTaks 3");
        IPlace placeTask_3 = Tasks.findCheapest(conn,"munte");
        System.out.println(placeTask_3);

        // Close the connection
        DBConnect.closeConnection();
    }
}
