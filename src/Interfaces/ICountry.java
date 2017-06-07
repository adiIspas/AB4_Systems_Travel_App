package Interfaces;

import java.util.ArrayList;

/**
 * Created by Adrian Ispas on 07.06.2017.
 * Interface which modeling the country object.
 */
public interface ICountry {

    // Set the name of country
    void setName(String name);

    // Get the name of country
    String getName();

    // Add a new district in country
    void addDistrict(IDistrict district);

    // Get list of districts from country
    ArrayList<IDistrict> getDistricts();
}
