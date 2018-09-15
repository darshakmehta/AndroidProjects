package mad.com.hw06;

        import android.graphics.Bitmap;
        import android.os.Parcel;
        import android.os.Parcelable;

public class Instructor implements Parcelable {

    private String username;
    private String instructorEmailId;
    private String instructorFirstName;
    private String instructorLastName;
    private String instructorWebsite;
    private Bitmap instructorImage;

    private boolean selected;

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public Instructor() {
    }

    public Instructor(String username, String instructorEmailId, String instructorFirstName, String instructorLastName, String instructorWebsite, Bitmap instructorImage) {
        this.username = username;
        this.instructorEmailId = instructorEmailId;
        this.instructorFirstName = instructorFirstName;
        this.instructorLastName = instructorLastName;
        this.instructorWebsite = instructorWebsite;
        this.instructorImage = instructorImage;
    }

    public Bitmap getInstructorImage() {
        return instructorImage;
    }

    public void setInstructorImage(Bitmap instructorImage) {
        this.instructorImage = instructorImage;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getInstructorEmailId() {
        return instructorEmailId;
    }

    public void setInstructorEmailId(String instructorEmailId) {
        this.instructorEmailId = instructorEmailId;
    }

    public String getInstructorFirstName() {
        return instructorFirstName;
    }

    public void setInstructorFirstName(String instructorFirstName) {
        this.instructorFirstName = instructorFirstName;
    }

    public String getInstructorLastName() {
        return instructorLastName;
    }

    public void setInstructorLastName(String instructorLastName) {
        this.instructorLastName = instructorLastName;
    }

    public String getInstructorWebsite() {
        return instructorWebsite;
    }

    public void setInstructorWebsite(String instructorWebsite) {
        this.instructorWebsite = instructorWebsite;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(username);
        parcel.writeString(instructorEmailId);
        parcel.writeString(instructorFirstName);
        parcel.writeString(instructorLastName);
        parcel.writeString(instructorWebsite);
        parcel.writeParcelable(instructorImage,PARCELABLE_WRITE_RETURN_VALUE);
    }

    @Override
    public String toString() {
        return "user{" +
                "username='" + username + '\'' +
                ", instructor emailid='" + instructorEmailId + '\'' +
                ", instructor first name='" + instructorFirstName + '\'' +
                ", instructor last name='" + instructorLastName + '\'' +
                ", instructor website='" + instructorWebsite +
                '}';
    }

    public static final Parcelable.Creator<Instructor> CREATOR
            = new Parcelable.Creator<Instructor>() {
        public Instructor createFromParcel(Parcel in) {
            return new Instructor(in);
        }

        public Instructor[] newArray(int size) {
            return new Instructor[size];
        }
    };

    private Instructor(Parcel in) {
        this.username= in.readString();
        this.instructorEmailId = in.readString();
        this.instructorFirstName= in.readString();
        this.instructorLastName = in.readString();
        this.instructorWebsite = in.readString();
        this.instructorImage = in.readParcelable(Bitmap.class.getClassLoader());
    }
}
