package mad.com.inclass13test;

import java.io.Serializable;

/**
 * Created by darsh on 12/4/2017.
 */

public class Trip implements Serializable {

    String name, cost;

    @Override
    public String toString() {
        return "Trip{" +
                "name='" + name + '\'' +
                ", cost='" + cost + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }
}
