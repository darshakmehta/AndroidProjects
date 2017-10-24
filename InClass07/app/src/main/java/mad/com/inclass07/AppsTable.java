package mad.com.inclass07;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by darsh on 10/23/2017.
 */

public class AppsTable {

    public static final String TABLE_NAME = "apps";
    /*public static final String COLUMN_ID = "_id";*/
    public static final String COLUMN_PRICE = "price";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_SMALL_THUMB = "small_thumb";
    public static final String COLUMN_LARGE_THUMB = "large_thumb";

    static public void onCreate(SQLiteDatabase db){
        StringBuilder createQuery = new StringBuilder();
        createQuery.append("CREATE TABLE "+ TABLE_NAME +" (");
        createQuery.append(COLUMN_NAME+ " text primary key, ");
        createQuery.append(COLUMN_PRICE+ " text not null, ");
        createQuery.append(COLUMN_LARGE_THUMB+ " text not null, ");
        createQuery.append(COLUMN_SMALL_THUMB+" text not null );");
        try{
            db.execSQL(createQuery.toString());
            Log.d("TABLE", "Created Table");
        }catch(SQLException sqle){
            Log.d("TABLE", "Exception");
            sqle.printStackTrace();
        }

    }

    static public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        AppsTable.onCreate(db);
    }

}

