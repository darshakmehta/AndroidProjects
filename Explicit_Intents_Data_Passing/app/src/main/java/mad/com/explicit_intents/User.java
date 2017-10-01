package mad.com.explicit_intents;

import java.io.Serializable;

public class User implements Serializable { //To send object through intent we need to implement Serializabble
    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    String name;
    double age;

    public User(String name, double age) {
        super();
        this.name = name;
        this.age = age;
    }
}
