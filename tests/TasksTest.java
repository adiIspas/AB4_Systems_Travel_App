import Interfaces.ILocation;
import Interfaces.IPlace;
import Internal.City;
import Internal.Place;
import org.junit.Test;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by Adrian Ispas on 08.06.2017.
 */
public class TasksTest {
    @Test
    public void findLocation() throws Exception {
        String file = "entities_for_tests.txt";

        // Create the connection to database
        Connection conn = DBConnect.createConnection();

        // Save entries into database. Can run just first time or when we have new entries to add
        SaveData.saveIntoDB(conn,file);

        IPlace place = Tasks.findLocation(conn,"Locatia A");
        ArrayList<String> activities = new ArrayList<>();
        activities.add("act1");
        activities.add("act2");
        activities.add("act3");

        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        Date parsed = format.parse("20170101");
        java.sql.Date sqlBeginDate = new java.sql.Date(parsed.getTime());

        format = new SimpleDateFormat("yyyyMMdd");
        parsed = format.parse("20170307");
        java.sql.Date sqlEndDate = new java.sql.Date(parsed.getTime());
        Place myLocation = new Place("Locatia A", new City("Slatina"), 100.00, activities, sqlBeginDate, sqlEndDate);

        // Close the connection
        DBConnect.closeConnection();

        assertEquals(myLocation,place);
    }

    @Test
    public void findCheapest() throws Exception {
        String file = "entities_for_tests.txt";

        // Create the connection to database
        Connection conn = DBConnect.createConnection();

        // Save entries into database. Can run just first time or when we have new entries to add
        SaveData.saveIntoDB(conn,file);

        ArrayList<ILocation> locations = Tasks.findTopN(conn,null, null, "Brasov", "2016-10-10", "2018-10-10", 5);
        for (ILocation location:locations) {
            System.out.println(location);
        }

        // Close the connection
        DBConnect.closeConnection();
    }

    @Test
    public void findTopN() throws Exception {
        String file = "entities_for_tests.txt";

        // Create the connection to database
        Connection conn = DBConnect.createConnection();

        // Save entries into database. Can run just first time or when we have new entries to add
        SaveData.saveIntoDB(conn,file);

        IPlace placeTask_3 = Tasks.findCheapest(conn,"ciclism", 10);
        System.out.println(placeTask_3);

        // Close the connection
        DBConnect.closeConnection();
    }
}