package mad.com.dynamic_layout;

import java.util.ArrayList;

/**
 * Created by darsh on 9/24/2017.
 */

public class createUsers {
    public static ArrayList generate(){
        ArrayList<User> list = new ArrayList<User>();
        User user1 = new User("Rich","Lambert",35);
        User user2 = new User("Wei","Wang",25);
        User user3 = new User("Amit","Roy",26);
        list.add(user1);
        list.add(user2);
        list.add(user3);
        return list;
    }
}
