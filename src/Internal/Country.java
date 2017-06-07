package Internal;

import Interfaces.ICountry;
import Interfaces.IDistrict;

import java.util.ArrayList;

/**
 * Created by Adrian Ispas on 07.06.2017.
 */
public class Country implements ICountry {
    private String name;
    private ArrayList<IDistrict> districts;

    // Constructs of Country class
    public Country(){
        this.name = null;
        districts = new ArrayList<IDistrict>();
    }

    public Country(String name){
        this.name = name;
        districts = new ArrayList<IDistrict>();
    }

    public Country(String name, ArrayList<IDistrict> districts){
        this.name = name;
        this.districts = districts;
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
    public void addDistrict(IDistrict district) {
        this.districts.add(district);
    }

    @Override
    public ArrayList<IDistrict> getDistricts() {
        return this.districts;
    }
}
