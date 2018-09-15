package mad.com.inclass13test;

/**
 * Created by darsh on 12/4/2017.
 */

public class Location {
    String Lat, Lon;

    @Override
    public String toString() {
        return "Location{" +
                "Lat='" + Lat + '\'' +
                ", Lon='" + Lon + '\'' +
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
}
