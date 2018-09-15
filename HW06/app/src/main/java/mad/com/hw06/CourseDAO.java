package mad.com.hw06;



import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;


public class CourseDAO {

    private SQLiteDatabase db;

    public CourseDAO(SQLiteDatabase db) {
        this.db = db;
    }

    public long save(Course course) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CourseTable.COLUMN_USERNAME, course.getUsername());
        contentValues.put(CourseTable.COLUMN_COURSE_TITLE, course.getCourseTitle());
        contentValues.put(CourseTable.COLUMN_INSTRUCTOR_EMAILID, course.getInstructorEmailId());
        contentValues.put(CourseTable.COLUMN_SCHEDULE_DAY, course.getScheduleDay());
        contentValues.put(CourseTable.COLUMN_SCHEDULE_HOURS, course.getScheduleTimeHours());
        contentValues.put(CourseTable.COLUMN_SCHEDULE_MINUTES, course.getScheduleTimeMinutes());
        contentValues.put(CourseTable.COLUMN_SCHEDULE_PERIOD, course.getScheduleTimePeriod());
        contentValues.put(CourseTable.COLUMN_CREDIT_HOURS, course.getCreditHours());
        contentValues.put(CourseTable.COLUMN_SEMESTER, course.getSemester());
        return db.insert(CourseTable.TABLE_NAME, null, contentValues);
    }


    public boolean delete(Course course) {
        return db.delete(CourseTable.TABLE_NAME, CourseTable.COLUMN_USERNAME + " =? and "+ CourseTable.COLUMN_COURSE_TITLE + " =?", new String[]{course.getUsername(), course.getCourseTitle()}) > 0;
    }

    public List<Course> getAll(String username) {
        List courses = new ArrayList<Course>();
        Cursor c = db.query(CourseTable.TABLE_NAME, new String[]{CourseTable.COLUMN_USERNAME, CourseTable.COLUMN_COURSE_TITLE, CourseTable.COLUMN_INSTRUCTOR_EMAILID, CourseTable.COLUMN_SCHEDULE_DAY, CourseTable.COLUMN_SCHEDULE_HOURS, CourseTable.COLUMN_SCHEDULE_MINUTES, CourseTable.COLUMN_SCHEDULE_PERIOD, CourseTable.COLUMN_CREDIT_HOURS, CourseTable.COLUMN_SEMESTER}, CourseTable.COLUMN_USERNAME + "=?", new String[]{username}, null, null, null, null);
        if (c != null && c.moveToFirst()) {
            do {
                Course course = buildCourseFromCursor(c);
                if (course != null) {
                    courses.add(course);
                }
            } while (c.moveToNext());
            if (!c.isClosed()) {
                c.close();
            }
        }
        return courses;
    }

    public List<Course> getCourseByInstructorByEmailId(String instructorEmailId) {
        List courses = new ArrayList<Course>();
        Cursor c = db.query(CourseTable.TABLE_NAME, new String[]{CourseTable.COLUMN_USERNAME, CourseTable.COLUMN_COURSE_TITLE, CourseTable.COLUMN_INSTRUCTOR_EMAILID, CourseTable.COLUMN_SCHEDULE_DAY, CourseTable.COLUMN_SCHEDULE_HOURS, CourseTable.COLUMN_SCHEDULE_MINUTES, CourseTable.COLUMN_SCHEDULE_PERIOD, CourseTable.COLUMN_CREDIT_HOURS, CourseTable.COLUMN_SEMESTER}, CourseTable.COLUMN_INSTRUCTOR_EMAILID + "=?", new String[]{instructorEmailId}, null, null, null, null);
        if (c != null && c.moveToFirst()) {
            do {
                Course course = buildCourseFromCursor(c);
                if (course != null) {
                    courses.add(course);
                }
            } while (c.moveToNext());
            if (!c.isClosed()) {
                c.close();
            }
        }
        return courses;
    }

    public Course get(String username, String courseTitle) {
        Course course = null;
        Cursor c = db.query(CourseTable.TABLE_NAME, new String[]{CourseTable.COLUMN_USERNAME, CourseTable.COLUMN_COURSE_TITLE, CourseTable.COLUMN_INSTRUCTOR_EMAILID, CourseTable.COLUMN_SCHEDULE_DAY, CourseTable.COLUMN_SCHEDULE_HOURS, CourseTable.COLUMN_SCHEDULE_MINUTES, CourseTable.COLUMN_SCHEDULE_PERIOD, CourseTable.COLUMN_CREDIT_HOURS, CourseTable.COLUMN_SEMESTER}, CourseTable.COLUMN_USERNAME + "=? and "+CourseTable.COLUMN_COURSE_TITLE + " =?", new String[]{username, courseTitle}, null, null, null, null);
        if (c != null && c.moveToFirst()) {
            course = buildCourseFromCursor(c);
            if (!c.isClosed()) {
                c.close();
            }
        }
        return course;
    }

    private Course buildCourseFromCursor(Cursor c) {
        Course course = null;
        if (c != null) {
            course = new Course();
            course.setUsername(c.getString(0));
            course.setCourseTitle(c.getString(1));
            course.setInstructorEmailId(c.getString(2));
            course.setScheduleDay(c.getString(3));
            course.setScheduleTimeHours(c.getString(4));
            course.setScheduleTimeMinutes(c.getString(5));
            course.setScheduleTimePeriod(c.getString(6));
            course.setCreditHours(c.getString(7));
            course.setSemester(c.getString(8));
        }
        return course;
    }
}
