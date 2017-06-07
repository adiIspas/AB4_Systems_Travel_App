package Internal;

import Interfaces.ICity;
import Interfaces.IDistrict;

import java.util.ArrayList;

/**
 * Created by Adrian Ispas on 07.06.2017.
 */
public class District implements IDistrict {
    private String name;
    private ArrayList<ICity> cities;

    // Constructs of District class
    public District(){
        this.name = null;
        this.cities = new ArrayList<ICity>();
    }

    public District(String name){
        this.name = name;
        this.cities = new ArrayList<ICity>();
    }

    public District(String name, ArrayList<ICity> cities){
        this.name = name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void addCity(ICity city) {
        this.cities.add(city);
    }

    @Override
    public ArrayList<ICity> getCities() {
        return this.cities;
    }
}
