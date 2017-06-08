import Interfaces.ILocation;
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

    static IPlace findCheapest(Connection conn, String activity, Integer numberOfDays) throws SQLException, JSONException {
        String selectSQL = "SELECT DISTINCT * FROM place WHERE datediff(EndDate,BeginDate) >= ? ORDER BY AvgPrice ASC";

        PreparedStatement preparedStatement = conn.prepareStatement(selectSQL);
        preparedStatement.setInt(1, numberOfDays);
        ResultSet rs = preparedStatement.executeQuery();

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

    static ArrayList<ILocation> findTopN(Connection conn, String country, String district, String city, String beginDate, String endDate, Integer totalNumber) throws SQLException, JSONException {
        ArrayList<ILocation> locations = new ArrayList<>();

        if(country != null){
            Boolean completed = false;

            String selectSQL = "SELECT DISTINCT * FROM place p " +
                    "JOIN city ci ON (p.City = ci.Name)" +
                    "JOIN district d ON (ci.idDistrict = d.idDistrict)" +
                    "JOIN country ct ON (d.idCountry = ct.idCountry)" +
                    "WHERE BeginDate >= date(?)" +
                    "AND EndDate <= date(?) " +
                    "AND ct.Name = ? ORDER BY AvgPrice ASC";

            PreparedStatement preparedStatement = conn.prepareStatement(selectSQL);
            preparedStatement.setString(1, beginDate);
            preparedStatement.setString(2, endDate);
            preparedStatement.setString(3, country);
            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next() && !completed){
                String name = rs.getString("Name");
                String cityOfPlace = rs.getString("City");
                Double avgPrice = rs.getDouble("AvgPrice");
                String activities = rs.getString("Activities");
                Date beginDateOfPlace = rs.getDate("BeginDate");
                Date endDateOfPlace = rs.getDate("EndDate");

                JSONObject json = new JSONObject(activities);

                Iterator<String> keys = json.keys();
                ArrayList<String> actvs = new ArrayList<>();

                while(keys.hasNext()){
                    String key = keys.next();
                    String val = json.getString(key);
                    actvs.add(val);
                }

                if(totalNumber > 0){
                    locations.add(new Place(name, new City(cityOfPlace), avgPrice, actvs, beginDateOfPlace, endDateOfPlace));
                    totalNumber--;
                }
                else{
                    completed = true;
                }
            }
        }
        else if(district != null){
            Boolean completed = false;

            String selectSQL = "SELECT DISTINCT * FROM place p " +
                    "JOIN city ci ON (p.City = ci.Name)" +
                    "JOIN district d ON (ci.idDistrict = d.idDistrict)" +
                    "WHERE BeginDate >= date(?)" +
                    "AND EndDate <= date(?) " +
                    "AND d.Name = ? ORDER BY AvgPrice ASC";

            PreparedStatement preparedStatement = conn.prepareStatement(selectSQL);
            preparedStatement.setString(1, beginDate);
            preparedStatement.setString(2, endDate);
            preparedStatement.setString(3, district);
            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next() && !completed){
                String name = rs.getString("Name");
                String cityOfPlace = rs.getString("City");
                Double avgPrice = rs.getDouble("AvgPrice");
                String activities = rs.getString("Activities");
                Date beginDateOfPlace = rs.getDate("BeginDate");
                Date endDateOfPlace = rs.getDate("EndDate");

                JSONObject json = new JSONObject(activities);

                Iterator<String> keys = json.keys();
                ArrayList<String> actvs = new ArrayList<>();

                while(keys.hasNext()){
                    String key = keys.next();
                    String val = json.getString(key);
                    actvs.add(val);
                }

                if(totalNumber > 0){
                    locations.add(new Place(name, new City(cityOfPlace), avgPrice, actvs, beginDateOfPlace, endDateOfPlace));
                    totalNumber--;
                }
                else{
                    completed = true;
                }
            }
        }
        else if(city != null){
            Boolean completed = false;

            String selectSQL = "SELECT DISTINCT * FROM place p " +
                    "JOIN city ci ON (p.City = ci.Name)" +
                    "WHERE BeginDate >= date(?)" +
                    "AND EndDate <= date(?) " +
                    "AND ci.Name = ? ORDER BY AvgPrice ASC";

            PreparedStatement preparedStatement = conn.prepareStatement(selectSQL);
            preparedStatement.setString(1, beginDate);
            preparedStatement.setString(2, endDate);
            preparedStatement.setString(3, city);
            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next() && !completed){
                String name = rs.getString("Name");
                String cityOfPlace = rs.getString("City");
                Double avgPrice = rs.getDouble("AvgPrice");
                String activities = rs.getString("Activities");
                Date beginDateOfPlace = rs.getDate("BeginDate");
                Date endDateOfPlace = rs.getDate("EndDate");

                JSONObject json = new JSONObject(activities);

                Iterator<String> keys = json.keys();
                ArrayList<String> actvs = new ArrayList<>();

                while(keys.hasNext()){
                    String key = keys.next();
                    String val = json.getString(key);
                    actvs.add(val);
                }

                if(totalNumber > 0){
                    locations.add(new Place(name, new City(cityOfPlace), avgPrice, actvs, beginDateOfPlace, endDateOfPlace));
                    totalNumber--;
                }
                else{
                    completed = true;
                }
            }
        }

        return locations;
    }
}
