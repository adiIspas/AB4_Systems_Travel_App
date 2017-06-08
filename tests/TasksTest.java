import Interfaces.ILocation;
import Interfaces.IPlace;
import Internal.City;
import Internal.Place;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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

        Place myResult = new Place("Locatia A", new City("Slatina"), 500.00, activities, sqlBeginDate, sqlEndDate);

        // Delete content from DB
        deleteContentDB(conn);

        // Close the connection
        DBConnect.closeConnection();

        assertEquals(place, myResult);
    }

    @Test
    public void findCheapest() throws Exception {
        String file = "entities_for_tests.txt";

        // Create the connection to database
        Connection conn = DBConnect.createConnection();

        // Save entries into database. Can run just first time or when we have new entries to add
        SaveData.saveIntoDB(conn,file);

        IPlace cheapest = Tasks.findCheapest(conn,"act6", 10);

        ArrayList<String> activities = new ArrayList<>();
        activities.add("act4");
        activities.add("act5");
        activities.add("act6");

        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        Date parsed = format.parse("20170308");
        java.sql.Date sqlBeginDate = new java.sql.Date(parsed.getTime());

        format = new SimpleDateFormat("yyyyMMdd");
        parsed = format.parse("20170601");
        java.sql.Date sqlEndDate = new java.sql.Date(parsed.getTime());

        Place myResult = new Place("Locatia B", new City("Bucuresti"), 200.0, activities, sqlBeginDate, sqlEndDate);

        // Delete content from DB
        deleteContentDB(conn);

        // Close the connection
        DBConnect.closeConnection();

        assertEquals(cheapest, myResult);
    }

    @Test
    public void findTopN() throws Exception {
        String file = "entities_for_tests.txt";

        // Create the connection to database
        Connection conn = DBConnect.createConnection();

        // Save entries into database. Can run just first time or when we have new entries to add
        SaveData.saveIntoDB(conn,file);

        ArrayList<ILocation> locations = new ArrayList<>();
        ArrayList<String> activities = new ArrayList<>();
        activities.add("act4");
        activities.add("act7");
        activities.add("act8");

        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        Date parsed = format.parse("20170307");
        java.sql.Date sqlBeginDate1 = new java.sql.Date(parsed.getTime());

        format = new SimpleDateFormat("yyyyMMdd");
        parsed = format.parse("20170311");
        java.sql.Date sqlEndDate1 = new java.sql.Date(parsed.getTime());

        format = new SimpleDateFormat("yyyyMMdd");
        parsed = format.parse("20170308");
        java.sql.Date sqlBeginDate2 = new java.sql.Date(parsed.getTime());

        format = new SimpleDateFormat("yyyyMMdd");
        parsed = format.parse("20170315");
        java.sql.Date sqlEndDate2 = new java.sql.Date(parsed.getTime());

        format = new SimpleDateFormat("yyyyMMdd");
        parsed = format.parse("20170310");
        java.sql.Date sqlBeginDate3 = new java.sql.Date(parsed.getTime());

        format = new SimpleDateFormat("yyyyMMdd");
        parsed = format.parse("20170314");
        java.sql.Date sqlEndDate3 = new java.sql.Date(parsed.getTime());

        format = new SimpleDateFormat("yyyyMMdd");
        parsed = format.parse("20170309");
        java.sql.Date sqlBeginDate4 = new java.sql.Date(parsed.getTime());

        format = new SimpleDateFormat("yyyyMMdd");
        parsed = format.parse("20170312");
        java.sql.Date sqlEndDate4 = new java.sql.Date(parsed.getTime());

        format = new SimpleDateFormat("yyyyMMdd");
        parsed = format.parse("20170308");
        java.sql.Date sqlBeginDate5 = new java.sql.Date(parsed.getTime());

        format = new SimpleDateFormat("yyyyMMdd");
        parsed = format.parse("20170312");
        java.sql.Date sqlEndDate5 = new java.sql.Date(parsed.getTime());

        locations.add(new Place("Locatia L", new City("Pitesti"), 100.0, activities, sqlBeginDate1, sqlEndDate1));
        locations.add(new Place("Locatia B", new City("Craiova"), 120.0, activities, sqlBeginDate2, sqlEndDate2));
        locations.add(new Place("Locatia P", new City("Iasi"), 149.0, activities, sqlBeginDate3, sqlEndDate3));
        locations.add(new Place("Locatia Z", new City("Timisoara"), 150.0, activities, sqlBeginDate4, sqlEndDate4));
        locations.add(new Place("Locatia C", new City("Constanta"), 199.0, activities, sqlBeginDate5, sqlEndDate5));

        ArrayList<ILocation> myResults = Tasks.findTopN(conn,"Romania", null, null, "2017-03-07", "2017-03-15", 5);

        // Delete content from DB
        deleteContentDB(conn);

        // Close the connection
        DBConnect.closeConnection();

        int i = 0;
        for (ILocation myResult:myResults) {
            assertEquals(locations.get(i), myResult);
            i++;
        }
    }

    private static void deleteContentDB(Connection conn) throws SQLException {
        String deleteSQL = "DELETE FROM Place WHERE Name = ? AND City = ? LIMIT 1";
        PreparedStatement preparedStatement = conn.prepareStatement(deleteSQL);
        preparedStatement.setString(1, "Locatia L");
        preparedStatement.setString(2, "Pitesti");

        // Execute delete SQL statement
        preparedStatement.executeUpdate();

        deleteSQL = "DELETE FROM Place WHERE Name = ? AND City = ? LIMIT 1";
        preparedStatement = conn.prepareStatement(deleteSQL);
        preparedStatement.setString(1, "Locatia B");
        preparedStatement.setString(2, "Craiova");

        // Execute delete SQL statement
        preparedStatement.executeUpdate();

        deleteSQL = "DELETE FROM Place WHERE Name = ? AND City = ? LIMIT 1";
        preparedStatement = conn.prepareStatement(deleteSQL);
        preparedStatement.setString(1, "Locatia P");
        preparedStatement.setString(2, "Iasi");

        // Execute delete SQL statement
        preparedStatement.executeUpdate();

        deleteSQL = "DELETE FROM Place WHERE Name = ? AND City = ? LIMIT 1";
        preparedStatement = conn.prepareStatement(deleteSQL);
        preparedStatement.setString(1, "Locatia Z");
        preparedStatement.setString(2, "Timisioara");

        // Execute delete SQL statement
        preparedStatement.executeUpdate();

        deleteSQL = "DELETE FROM Place WHERE Name = ? AND City = ? LIMIT 1";
        preparedStatement = conn.prepareStatement(deleteSQL);
        preparedStatement.setString(1, "Locatia C");
        preparedStatement.setString(2, "Constanta");

        // Execute delete SQL statement
        preparedStatement.executeUpdate();

        deleteSQL = "DELETE FROM Place WHERE Name = ? AND City = ? LIMIT 1";
        preparedStatement = conn.prepareStatement(deleteSQL);
        preparedStatement.setString(1, "Locatia A");
        preparedStatement.setString(2, "Slatina");

        // Execute delete SQL statement
        preparedStatement.executeUpdate();

        deleteSQL = "DELETE FROM Place WHERE Name = ? AND City = ? LIMIT 1";
        preparedStatement = conn.prepareStatement(deleteSQL);
        preparedStatement.setString(1, "Locatia B");
        preparedStatement.setString(2, "Bucuresti");

        // Execute delete SQL statement
        preparedStatement.executeUpdate();

        deleteSQL = "DELETE FROM Place WHERE Name = ? AND City = ? LIMIT 1";
        preparedStatement = conn.prepareStatement(deleteSQL);
        preparedStatement.setString(1, "Locatia Z");
        preparedStatement.setString(2, "Timisoara");

        // Execute delete SQL statement
        preparedStatement.executeUpdate();
    }
}