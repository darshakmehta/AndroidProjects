package mad.com.hw06;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

public class Users implements Parcelable{

    private String first_Name;
    private String last_Name;
    private String username;
    private String password;
    private Bitmap image;

    public Users() {
    }

    public String getFirst_Name() {
        return first_Name;
    }

    public void setFirst_Name(String first_Name) {
        this.first_Name = first_Name;
    }

    public String getLast_Name() {
        return last_Name;
    }

    public void setLast_Name(String last_Name) {
        this.last_Name = last_Name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public static Creator<Users> getCREATOR() {
        return CREATOR;
    }

    @Override
    public String toString() {
        return "Users{" +
                "first_Name='" + first_Name + '\'' +
                ", last_Name='" + last_Name + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", image=" + image +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public Users(String firstName, String lastName, String username, String password, Bitmap image) {
        this.first_Name = firstName;
        this.last_Name = lastName;
        this.username = username;
        this.password = password;

        this.image = image;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(first_Name);
        parcel.writeString(last_Name);
        parcel.writeString(username);
        parcel.writeString(password);
        parcel.writeParcelable(image,PARCELABLE_WRITE_RETURN_VALUE);
    }

    public static final Parcelable.Creator<Users> CREATOR
            = new Parcelable.Creator<Users>() {
        public Users createFromParcel(Parcel in) {
            return new Users(in);
        }

        public Users[] newArray(int size) {
            return new Users[size];
        }
    };

    private Users(Parcel in) {
        this.first_Name = in.readString();
        this.last_Name = in.readString();
        this.username= in.readString();
        this.password = in.readString();
        this.image= in.readParcelable(Bitmap.class.getClassLoader());
    }
}

