package mad.com.hw05;

import java.io.Serializable;

public class Music implements Serializable {
    String name, artist, url, small_img_url, large_img_url, star_img;
    int flag;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSmall_img_url() {
        return small_img_url;
    }

    public void setSmall_img_url(String small_img_url) {
        this.small_img_url = small_img_url;
    }

    public String getLarge_img_url() {
        return large_img_url;
    }

    public void setLarge_img_url(String large_img_url) {
        this.large_img_url = large_img_url;
    }

    public String getStar_img() {
        return star_img;
    }

    public void setStar_img(String star_img) {
        this.star_img = star_img;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    @Override
    public String toString() {
        return "Music{" +
                "name='" + name + '\'' +
                ", artist='" + artist + '\'' +
                ", url='" + url + '\'' +
                ", small_img_url='" + small_img_url + '\'' +
                ", large_img_url='" + large_img_url + '\'' +
                ", star_img='" + star_img + '\'' +
                ", flag=" + flag +
                '}';
    }
}
