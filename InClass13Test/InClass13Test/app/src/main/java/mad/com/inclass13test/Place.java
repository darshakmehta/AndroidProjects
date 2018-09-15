package mad.com.inclass13test;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by darsh on 12/4/2017.
 */

public class Place implements Serializable{

    String Cost, Duration,Place, Lat, Lon;
    ArrayList<Location> Location;

    @Override
    public String toString() {
        return "Place{" +
                "Cost='" + Cost + '\'' +
                ", Duration='" + Duration + '\'' +
                ", Place='" + Place + '\'' +
                ", Lat='" + Lat + '\'' +
                ", Lon='" + Lon + '\'' +
                ", Location=" + Location +
                '}';
    }

    public String getLat() {
        return Lat;
    }

    public void setLat(String lat) {
        Lat = lat;
    }

    public String getLon() {
        return Lon;
    }

    public void setLon(String lon) {
        Lon = lon;
    }

    public String getCost() {
        return Cost;
    }

    public void setCost(String cost) {
        Cost = cost;
    }

    public String getDuration() {
        return Duration;
    }

    public void setDuration(String duration) {
        Duration = duration;
    }

    public String getPlace() {
        return Place;
    }

    public void setPlace(String place) {
        Place = place;
    }

    public ArrayList<mad.com.inclass13test.Location> getLocation() {
        return Location;
    }

    public void setLocation(ArrayList<mad.com.inclass13test.Location> location) {
        Location = location;
    }
}
