package mad.com.hw06;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by darsh on 11/2/2017.
 */

public class TableOpenHelper extends SQLiteOpenHelper {

    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "users.db";

    public TableOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION );
    }

    public void onCreate(SQLiteDatabase db) {
        UsersTable.onCreate(db);
        CourseTable.onCreate(db);
        InstructorsTable.onCreate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        UsersTable.onUpgrade(db,oldVersion,newVersion);
        CourseTable.onUpgrade(db,oldVersion, newVersion);
        InstructorsTable.onUpgrade(db, oldVersion, newVersion);
    }
}
