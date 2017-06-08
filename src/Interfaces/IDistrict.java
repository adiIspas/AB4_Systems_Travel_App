package Interfaces;

import java.util.ArrayList;

/**
 * Created by Adrian Ispas on 07.06.2017.
 * Interface which modeling the district object.
 */
public interface IDistrict extends ILocation{

    // Set the name of district
    void setName(String name);

    // Get the name of district
    String getName();

    // Add a new city in district
    void addCity(ICity city);

    // Get list of cities from district
    ArrayList<ICity> getCities();
}
