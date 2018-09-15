package mad.com.hw06;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

public class InstructorTableDBDataManager {

    private Context mContext;
    private InstructorTableOpenHelper instructorTableOpenHelper;
    private SQLiteDatabase db;
    private InstructorsDAO instructorDAO;

    public InstructorTableDBDataManager(Context mContext) {
        this.mContext = mContext;
        instructorTableOpenHelper = new InstructorTableOpenHelper(this.mContext);
        db = instructorTableOpenHelper.getWritableDatabase();
        instructorDAO = new InstructorsDAO(db);
    }

    public void close() {
        if (db != null) {
            db.close();
        }
    }

    public InstructorsDAO getInstructorDAO() {
        return this.instructorDAO;
    }

    public long saveInstructor(Instructor instructor) {
        return this.instructorDAO.save(instructor);
    }

    /*public Instructor getInstructor(String email_id) {
        return this.instructorDAO.get(email_id);
    }*/

    public List<Instructor> getAllInstructor(String username){
        return this.instructorDAO.getAll(username);
    }

}

