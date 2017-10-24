package mad.com.inclass07;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by darsh on 10/23/2017.
 */

public class AppDAO {
    private SQLiteDatabase db;

    public AppDAO(SQLiteDatabase db) {
        this.db = db;
    }

    public long save(App app) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(AppsTable.COLUMN_NAME, app.getName());
        contentValues.put(AppsTable.COLUMN_PRICE, app.getPrice());
        contentValues.put(AppsTable.COLUMN_LARGE_THUMB, app.getLarge_thumb_url());
        contentValues.put(AppsTable.COLUMN_SMALL_THUMB, app.getSmall_thumb_url());


        return db.insert(AppsTable.TABLE_NAME, null, contentValues);
    }

    public boolean update(App app) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(AppsTable.COLUMN_PRICE, app.getPrice());
        contentValues.put(AppsTable.COLUMN_LARGE_THUMB, app.getLarge_thumb_url());
        contentValues.put(AppsTable.COLUMN_SMALL_THUMB, app.getSmall_thumb_url());
        return db.update(AppsTable.TABLE_NAME, contentValues, AppsTable.COLUMN_NAME + "=?", new String[]{app.getName()}) > 0;
    }

    public boolean delete(App app) {
        return db.delete(AppsTable.TABLE_NAME, AppsTable.COLUMN_NAME + " =?", new String[]{app.getName()}) > 0;
    }

    public App get(String name) {
        App app = null;
        Cursor c = db.query(true, AppsTable.TABLE_NAME, new String[]{AppsTable.COLUMN_NAME, AppsTable.COLUMN_PRICE, AppsTable.COLUMN_LARGE_THUMB, AppsTable.COLUMN_SMALL_THUMB}, AppsTable.COLUMN_NAME + "=?", new String[]{name}, null, null, null, null, null);
        if (c != null && c.moveToFirst()) {
            app = buildNoteFromCursor(c);
            if (!c.isClosed()) {
                c.close();
            }
        }

        return app;
    }

    public List<App> getAll() {
        List apps = new ArrayList<App>();
        Cursor c = db.query(AppsTable.TABLE_NAME, new String[]{AppsTable.COLUMN_NAME, AppsTable.COLUMN_PRICE, AppsTable.COLUMN_LARGE_THUMB, AppsTable.COLUMN_SMALL_THUMB}, null, null, null, null, null);
        if (c != null && c.moveToFirst()) {
            do {
                App app = buildNoteFromCursor(c);
                if (app != null) {
                    apps.add(app);
                }
            } while (c.moveToNext());

            if (!c.isClosed()) {
                c.close();
            }
        }
        return apps;
    }

    private App buildNoteFromCursor(Cursor c) {
        App app = null;
        if (c != null) {
            app = new App();
            app.setName(c.getString(0));
            app.setPrice(c.getString(1));
            app.setLarge_thumb_url(c.getString(2));
            app.setSmall_thumb_url(c.getString(3));
        }
        return app;
    }
}
