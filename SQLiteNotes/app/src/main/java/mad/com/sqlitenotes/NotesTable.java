package mad.com.sqlitenotes;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

public class NotesTable {
    static final String TABLENAME = "notes";
    static final String COLUMN_ID ="id";
    static final String COLUMN_SUBJECT ="subject";
    static final String COLUMN_TEXT ="text";

    static public void onCreate(SQLiteDatabase db){
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE "+ TABLENAME + " (");
        sb.append(COLUMN_ID + " integer primary key autoincrement, ");
        sb.append(COLUMN_SUBJECT + " text not null, ");
        sb.append(COLUMN_TEXT + " text not null);");
        try{
            db.execSQL(sb.toString());
        }catch (SQLiteException ex){
            ex.printStackTrace();
        }
    }

    static public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS "+ TABLENAME);
        NotesTable.onCreate(db);
    }
}
