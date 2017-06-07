package Interfaces;

import java.util.Date;

/**
 * Created by Adrian Ispas on 07.06.2017.
 * Interface which modeling the district object.
 */
public interface IPlace {

    // Set the name of place
    void setName(String name);

    // Set the city of place
    void setCity(ICity city);

    // Set the average price of place
    void setAvgPrice(double avgPrice);

    // Add a new activity in the place
    void addActivity(String activity);

    // Set start date
    void setStartDate(Date startDate);

    // Set end date
    void setEndDate(Date endDate);
}
