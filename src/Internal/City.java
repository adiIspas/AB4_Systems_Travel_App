package Internal;

import Interfaces.ICity;

/**
 * Created by Adrian Ispas on 07.06.2017.
 */
public class City implements ICity {
    private String name;

    // Constructs of City class
    public City(){
        this.name = null;
    }

    public City(String name){
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
    public String toString() {
        return "City{" +
                "name='" + name + '\'' +
                '}';
    }
}
