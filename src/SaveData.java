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

        for (String[] currentLine : list) {
            String name = currentLine[0];
            String city = currentLine[1];
            Double avgPrice = Double.parseDouble(currentLine[2]);
            String[] activities = currentLine[3].split(",");
            String startDate = currentLine[4];
            String endDate = currentLine[5];

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
            }
        }
    }
}
