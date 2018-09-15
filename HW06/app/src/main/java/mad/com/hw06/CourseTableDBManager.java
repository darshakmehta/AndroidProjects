package mad.com.hw06;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;


public class CourseTableDBManager {

    private Context mContext;
    private CourseTableOpenHelper courseTableOpenHelper;
    private SQLiteDatabase db;
    private CourseDAO courseDAO;

    public CourseTableDBManager(Context mContext){
        this.mContext = mContext;
        courseTableOpenHelper = new CourseTableOpenHelper(this.mContext);
        db = courseTableOpenHelper.getWritableDatabase();
        courseDAO = new CourseDAO(db);
    }

    public void close(){
        if(db != null){
            db.close();
        }
    }

    public CourseDAO getCourseDAO(){
        return this.courseDAO;
    }

    public long saveCourse(Course course){
        return this.courseDAO.save(course);
    }

    public List getAllCourses(String username){
        return this.courseDAO.getAll(username);
    }
}
