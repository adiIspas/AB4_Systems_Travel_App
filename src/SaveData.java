import org.json.JSONException;
import org.json.JSONObject;

import java.sql.*;
import java.text.ParseException;
import java.util.ArrayList;

/**
 * Created by Adrian Ispas on 07.06.2017.
 * Save date into a specific database.
 */
public class SaveData {
     static void saveIntoDB(Connection conn, String file) throws SQLException, JSONException, ParseException {

        ArrayList<String[]> list = ReadData.readData(file);
        boolean haveCountry = false;
        boolean haveDistrict = false;
        boolean haveCity = false;

        for (String[] currentLine : list) {
            String country = currentLine[0];
            String district = currentLine[1];
            String name = currentLine[2];
            String city = currentLine[3];
            Double avgPrice = Double.parseDouble(currentLine[4]);
            String[] activities = currentLine[5].split(",");
            String startDate = currentLine[6];
            String endDate = currentLine[7];

            JSONObject activitiesJson = new JSONObject();
            for (int i = 0; i < activities.length; i++) {
                activitiesJson.put(""+i,activities[i]);
            }

            String selectSQL = "SELECT Name FROM place WHERE Name = ? AND City = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(selectSQL);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, city);
            ResultSet rs = preparedStatement.executeQuery();

            // The place exist, so we update the to actual place information.
            if(rs.next()){
                String updateTableSQL = "UPDATE place SET AvgPrice = ?, Activities = ?, BeginDate = ?, EndDate = ? WHERE Name = ? AND City = ?";
                preparedStatement = conn.prepareStatement(updateTableSQL);
                preparedStatement.setDouble(1, avgPrice);
                preparedStatement.setString(2, activitiesJson.toString());
                preparedStatement.setString(3, startDate);
                preparedStatement.setString(4, endDate);
                preparedStatement.setString(5, name);
                preparedStatement.setString(6, city);

                // Execute update SQL statement
                preparedStatement.executeUpdate();
            }
            // The place doesn't exist, so we insert the place.
            else{
                // Insert place
                String insertSQL = "INSERT INTO Place"
                        + "(Name, City, AvgPrice, Activities, BeginDate, EndDate) VALUES"
                        + "(?,?,?,?,date(?),date(?))";
                preparedStatement = conn.prepareStatement(insertSQL);
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, city);
                preparedStatement.setDouble(3, avgPrice);
                preparedStatement.setString(4, activitiesJson.toString());
                preparedStatement.setString(5, startDate);
                preparedStatement.setString(6, endDate);

                // Execute insert SQL statement
                preparedStatement.executeUpdate();

                // Search country
                selectSQL = "SELECT idCountry FROM Country WHERE Name = ?";
                preparedStatement = conn.prepareStatement(selectSQL);
                preparedStatement.setString(1, country);
                rs = preparedStatement.executeQuery();

                // We don't have this country
                if(!rs.next()){
                    // Insert country
                    insertSQL = "INSERT INTO Country"
                            + "(Name) VALUES"
                            + "(?)";
                    preparedStatement = conn.prepareStatement(insertSQL);
                    preparedStatement.setString(1, country);

                    // Execute insert SQL statement
                    preparedStatement.executeUpdate();
                }

                // Search district
                selectSQL = "SELECT idDistrict FROM District WHERE Name = ?";
                preparedStatement = conn.prepareStatement(selectSQL);
                preparedStatement.setString(1, district);
                rs = preparedStatement.executeQuery();

                // We don't have this district
                if(!rs.next()) {
                    // Insert district
                    insertSQL = "INSERT INTO District"
                            + "(idCountry, Name) VALUES"
                            + "((SELECT idCountry FROM Country WHERE Name = ? ), ?)";
                    preparedStatement = conn.prepareStatement(insertSQL);
                    preparedStatement.setString(1, country);
                    preparedStatement.setString(2, district);

                    // Execute insert SQL statement
                    preparedStatement.executeUpdate();
                }

                // Search city
                selectSQL = "SELECT idCity FROM City WHERE Name = ?";
                preparedStatement = conn.prepareStatement(selectSQL);
                preparedStatement.setString(1, city);
                rs = preparedStatement.executeQuery();

                // We don't have this city
                if(!rs.next()) {
                    // Insert city
                    insertSQL = "INSERT INTO City"
                            + "(idDistrict, Name) VALUES"
                            + "((SELECT idDistrict FROM District WHERE Name = ? ), ?)";
                    preparedStatement = conn.prepareStatement(insertSQL);
                    preparedStatement.setString(1, district);
                    preparedStatement.setString(2, city);

                    // Execute insert SQL statement
                    preparedStatement.executeUpdate();
                }
            }
        }
    }
}
