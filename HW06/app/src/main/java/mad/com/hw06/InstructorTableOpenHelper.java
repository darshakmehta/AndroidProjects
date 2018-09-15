package mad.com.hw06;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by darsh on 11/3/2017.
 */

public class InstructorTableOpenHelper extends SQLiteOpenHelper {

    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "users.db";

    public InstructorTableOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION );
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        InstructorsTable.onCreate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        InstructorsTable.onUpgrade(db,oldVersion,newVersion)  ;
    }
}
