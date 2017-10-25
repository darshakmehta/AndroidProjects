package mad.com.inclass07;

/**
 * Created by darsh on 10/23/2017.
 */

public class App {
    String small_thumb_url;
    String large_thumb_url;
    String name;
    String price;

    public String getSmall_thumb_url() {
        return small_thumb_url;
    }

    public void setSmall_thumb_url(String small_thumb_url) {
        this.small_thumb_url = small_thumb_url;
    }

    public String getLarge_thumb_url() {
        return large_thumb_url;
    }

    public void setLarge_thumb_url(String large_thumb_url) {
        this.large_thumb_url = large_thumb_url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "App{" +
                "small_thumb_url='" + small_thumb_url + '\'' +
                ", large_thumb_url='" + large_thumb_url + '\'' +
                ", name='" + name + '\'' +
                ", price='" + price + '\'' +
                '}';
    }
}
