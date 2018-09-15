package mad.com.hw06;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

/**
 * Created by darsh on 11/2/2017.
 */

public class DatabaseManager {

    private Context mContext;
    private TableOpenHelper tableOpenHelper;
    private SQLiteDatabase db;
    private UsersDAO userDAO;
    private CourseDAO courseDAO;
    private InstructorsDAO instructorDAO;

    public DatabaseManager(Context mContext) {
        this.mContext = mContext;
        tableOpenHelper = new TableOpenHelper(this.mContext);
        db = tableOpenHelper.getWritableDatabase();
        userDAO = new UsersDAO(db);
        courseDAO = new CourseDAO(db);
        instructorDAO = new InstructorsDAO(db);
    }

    public void close() {
        if (db != null) {
            db.close();
        }
    }

    public UsersDAO getUsersDAO() {
        return this.userDAO;
    }

    public long saveUsers(Users user) {
        return this.userDAO.save(user);
    }

    public Users getUser(String name) {
        return this.userDAO.get(name);
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

    public boolean delete(Course course){
        return this.courseDAO.delete(course);
    }

    public boolean delete(Instructor instructor){
        return this.instructorDAO.delete(instructor);
    }

    public InstructorsDAO getInstructorDAO() {
        return this.instructorDAO;
    }

    public long saveInstructor(Instructor instructor) {
        return this.instructorDAO.save(instructor);
    }

    public List<Course> getCourseByInstructorByEmailId(String instructorEmailId){
        return this.courseDAO.getCourseByInstructorByEmailId(instructorEmailId);
    }

    public Course getCourse(String username, String courseTitle){
        return this.courseDAO.get(username, courseTitle);
    }

    public Instructor getInstructor(String username, String email_id) {
        return this.instructorDAO.get(username, email_id);
    }

    public List<Instructor> getAllInstructor(String username){
        return this.instructorDAO.getAll(username);
    }

    /*public boolean updateUsers(Users user){
        return this.userDAO.update(user);
    }

    public boolean deleteUsers(Users user){
        return this.userDAO.delete(user);
    }*/

    /*public List<Users> getAllUsers(){
        return this.userDAO.getAll();
    }*/

}
