package mad.com.inclass07;

import java.util.Comparator;

/**
 * Created by darsh on 10/23/2017.
 */

public class PriceComparatorAsc implements Comparator<App> {

    @Override
    public int compare(App app1, App app2) {
        double rating1 = Double.parseDouble(app1.getPrice());
        double rating2 = Double.parseDouble(app2.getPrice());
        if(rating1 > rating2){
            return 1;
        } else {
            return -1;
        }
    }

}