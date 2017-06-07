import Interfaces.IPlace;
import Internal.City;
import Internal.Place;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Adrian Ispas on 07.06.2017.
 */
public class Tasks {
    static IPlace findLocation(Connection conn, String locationName) throws SQLException, JSONException {
        String selectSQL = "SELECT * FROM place WHERE Name = ?";

        PreparedStatement preparedStatement = conn.prepareStatement(selectSQL);
        preparedStatement.setString(1, locationName);
        ResultSet rs = preparedStatement.executeQuery();

        while(rs.next()){
            String city = rs.getString("City");
            Double avgPrice = rs.getDouble("AvgPrice");
            String activities = rs.getString("Activities");
            Date beginDate = rs.getDate("BeginDate");
            Date endDate = rs.getDate("EndDate");

            JSONObject json = new JSONObject(activities);

            Iterator<String> keys = json.keys();
            ArrayList<String> actvs = new ArrayList<>();
            while(keys.hasNext()) {
                String key = keys.next();
                String val = json.getString(key);
                actvs.add(val);
            }

            return new Place(locationName, new City(city), avgPrice, actvs, beginDate, endDate);
        }

        return null;
    }

    static IPlace findCheapest(Connection conn, String activity) throws SQLException, JSONException {
        String selectSQL = "SELECT * FROM place ORDER BY AvgPrice ASC";

        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(selectSQL);

        while(rs.next()){
            String name = rs.getString("Name");
            String city = rs.getString("City");
            Double avgPrice = rs.getDouble("AvgPrice");
            String activities = rs.getString("Activities");
            Date beginDate = rs.getDate("BeginDate");
            Date endDate = rs.getDate("EndDate");

            JSONObject json = new JSONObject(activities);

            Iterator<String> keys = json.keys();
            ArrayList<String> actvs = new ArrayList<>();

            while(keys.hasNext()){
                String key = keys.next();
                String val = json.getString(key);
                actvs.add(val);
            }

            if(actvs.contains(activity)) {
                return new Place(name, new City(city), avgPrice, actvs, beginDate, endDate);
            }
        }

        return null;
    }
}
