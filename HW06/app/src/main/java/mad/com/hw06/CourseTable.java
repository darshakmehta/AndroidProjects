package mad.com.hw06;


import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class CourseTable {

    public static final String TABLE_NAME = "courses";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_COURSE_TITLE = "coursetitle";
    public static final String COLUMN_INSTRUCTOR_EMAILID = "instructoremailid";
    public static final String COLUMN_SCHEDULE_DAY = "scheduleday";
    public static final String COLUMN_SCHEDULE_HOURS = "schedulehours";
    public static final String COLUMN_SCHEDULE_MINUTES = "scheduleminutes";
    public static final String COLUMN_SCHEDULE_PERIOD = "scheduleperiod";
    public static final String COLUMN_CREDIT_HOURS = "credithours";
    public static final String COLUMN_SEMESTER = "semester";

    static public void onCreate(SQLiteDatabase db){
        StringBuilder createQuery = new StringBuilder();
        createQuery.append("CREATE TABLE "+ TABLE_NAME +" (");
        createQuery.append(COLUMN_USERNAME+ " text not null, ");
        createQuery.append(COLUMN_COURSE_TITLE+ " text not null, ");
        createQuery.append(COLUMN_INSTRUCTOR_EMAILID+ " text not null, ");
        createQuery.append(COLUMN_SCHEDULE_DAY+" text not null, ");
        createQuery.append(COLUMN_SCHEDULE_HOURS+" text not null, ");
        createQuery.append(COLUMN_SCHEDULE_MINUTES+" text not null, ");
        createQuery.append(COLUMN_SCHEDULE_PERIOD+" text not null, ");
        createQuery.append(COLUMN_CREDIT_HOURS+" text not null, ");
        createQuery.append(COLUMN_SEMESTER+" text not null);");
        try{
            db.execSQL(createQuery.toString());
        }catch(SQLException sqle){
            sqle.printStackTrace();
        }

    }

    static public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        UsersTable.onCreate(db);
    }
}
