package mad.com.sqlitenotes;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

public class DatabaseDataManager {
    private Context mContext;
    private DatabaseOpenHelper dbOpenHelper;
    private SQLiteDatabase db;
    private NoteDAO noteDAO;

    public DatabaseDataManager(Context mContext) {
        this.mContext = mContext;
        dbOpenHelper = new DatabaseOpenHelper(this.mContext);
        db = dbOpenHelper.getWritableDatabase();
        noteDAO = new NoteDAO(db);
    }

    public void close(){
        if(db != null){
            db.close();
        }
    }

    public NoteDAO getNoteDAO(){
        return this.noteDAO;
    }

    public long saveNOte(Note note){
        return this.noteDAO.save(note);
    }

    public boolean updateNote(Note note){
        return this.noteDAO.update(note);
    }

    public boolean deleteNote(Note note){
        return this.noteDAO.delete(note);
    }

    public Note get(long id){
        return this.noteDAO.get(id);
    }

    public List<Note> getAllNotes() {
        return this.noteDAO.getAllNotes();
    }
}
