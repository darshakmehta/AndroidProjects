package mad.com.hw06;
import android.os.Parcel;
import android.os.Parcelable;

public class Course implements Parcelable {

    private String courseTitle;
    private String instructorEmailId;
    private String username;
    private String scheduleDay;
    private String scheduleTimeHours;
    private String scheduleTimeMinutes;
    private String scheduleTimePeriod;
    private String creditHours;
    private String semester;

    public Course() {
    }

    public Course(String courseTitle, String instructorEmailId, String username, String scheduleDay, String scheduleTimeHours, String scheduleTimeMinutes, String scheduleTimePeriod, String creditHours, String semester) {
        this.courseTitle = courseTitle;
        this.instructorEmailId = instructorEmailId;
        this.username = username;
        this.scheduleDay = scheduleDay;
        this.scheduleTimeHours = scheduleTimeHours;
        this.scheduleTimeMinutes = scheduleTimeMinutes;
        this.scheduleTimePeriod = scheduleTimePeriod;
        this.creditHours = creditHours;
        this.semester = semester;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public String getInstructorEmailId() {
        return instructorEmailId;
    }

    public void setInstructorEmailId(String instructorEmailId) {
        this.instructorEmailId = instructorEmailId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getScheduleDay() {
        return scheduleDay;
    }

    public void setScheduleDay(String scheduleDay) {
        this.scheduleDay = scheduleDay;
    }

    public String getScheduleTimeHours() {
        return scheduleTimeHours;
    }

    public void setScheduleTimeHours(String scheduleTimeHours) {
        this.scheduleTimeHours = scheduleTimeHours;
    }

    public String getScheduleTimeMinutes() {
        return scheduleTimeMinutes;
    }

    public void setScheduleTimeMinutes(String scheduleTimeMinutes) {
        this.scheduleTimeMinutes = scheduleTimeMinutes;
    }

    public String getScheduleTimePeriod() {
        return scheduleTimePeriod;
    }

    public void setScheduleTimePeriod(String scheduleTimePeriod) {
        this.scheduleTimePeriod = scheduleTimePeriod;
    }

    public String getCreditHours() {
        return creditHours;
    }

    public void setCreditHours(String creditHours) {
        this.creditHours = creditHours;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(username);
        parcel.writeString(courseTitle);
        parcel.writeString(instructorEmailId);
        parcel.writeString(scheduleDay);
        parcel.writeString(scheduleTimeHours);
        parcel.writeString(scheduleTimeMinutes);
        parcel.writeString(scheduleTimePeriod);
        parcel.writeString(creditHours);
        parcel.writeString(semester);
    }

    @Override
    public String toString() {
        return "user{" +
                "username='" + username + '\'' +
                ", course title='" + courseTitle + '\'' +
                ", instructor emailid='" + instructorEmailId + '\'' +
                ", schedule day='" + scheduleDay + '\'' +
                ", schedule time hours='" + scheduleTimeHours + '\'' +
                ", schedule time minutes='" + scheduleTimeMinutes + '\'' +
                ", schedule time period='" + scheduleTimePeriod + '\'' +
                ", credit hours='" + creditHours + '\'' +
                ", semester='" + semester +
                '}';
    }

    public static final Parcelable.Creator<Course> CREATOR
            = new Parcelable.Creator<Course>() {
        public Course createFromParcel(Parcel in) {
            return new Course(in);
        }

        public Course[] newArray(int size) {
            return new Course[size];
        }
    };

    private Course(Parcel in) {
        this.username= in.readString();
        this.courseTitle = in.readString();
        this.instructorEmailId= in.readString();
        this.scheduleDay = in.readString();
        this.scheduleTimeHours = in.readString();
        this.scheduleTimeMinutes = in.readString();
        this.scheduleTimePeriod = in.readString();
        this.creditHours = in.readString();
        this.semester = in.readString();
    }
}
