package Internal;

import Interfaces.ICity;
import Interfaces.IPlace;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Adrian Ispas on 07.06.2017.
 */
public class Place implements IPlace {
    private String name;
    private ICity city;
    private Double avgPrice;
    private ArrayList<String> activities;
    private Date startDate;
    private Date endDate;

    // Constructs of Place class
    public Place(){
        this.name = null;
        this.city = null;
        this.avgPrice = null;
        this.activities = new ArrayList<String>();
        this.startDate = null;
        this.endDate = null;
    }

    public Place(String name, ICity city, Double avgPrice, ArrayList<String> activities, Date startDate, Date endDate){
        this.name = name;
        this.city = city;
        this.avgPrice = avgPrice;
        this.activities = activities;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setCity(ICity city) {
        this.city = city;
    }

    @Override
    public void setAvgPrice(double avgPrice) {
        this.avgPrice = avgPrice;
    }

    @Override
    public void addActivity(String activity) {
        this.activities.add(activity);
    }

    @Override
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Override
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "Place{" +
                "name='" + name + '\'' +
                ", city=" + city +
                ", avgPrice=" + avgPrice +
                ", activities=" + activities +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Place)) return false;

        Place place = (Place) o;

        if (!name.equals(place.name)) return false;
        if (!city.equals(place.city)) return false;
        if (!avgPrice.equals(place.avgPrice)) return false;
        for(int i = 0; i < activities.size(); i++){
            if(!activities.get(i).equals(place.activities.get(i)))
                return false;
        }
        if (!startDate.equals(place.startDate)) return false;
        return endDate.equals(place.endDate);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + city.hashCode();
        result = 31 * result + avgPrice.hashCode();
        result = 31 * result + activities.hashCode();
        result = 31 * result + startDate.hashCode();
        result = 31 * result + endDate.hashCode();
        return result;
    }
}
