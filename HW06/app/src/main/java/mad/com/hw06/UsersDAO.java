package mad.com.hw06;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by darsh on 11/2/2017.
 */

public class UsersDAO {
    private SQLiteDatabase db;

    public UsersDAO(SQLiteDatabase db) {
        this.db = db;
    }

    public long save(Users user) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(UsersTable.COLUMN_FIRST_NAME, user.getFirst_Name());
        contentValues.put(UsersTable.COLUMN_LAST_NAME, user.getLast_Name());
        contentValues.put(UsersTable.COLUMN_EMAIL_ID, user.getUsername());
        contentValues.put(UsersTable.COLUMN_PERSONAL_WEBSITE, user.getPassword());
        Bitmap bitmap = user.getImage();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] imageByte = stream.toByteArray();
        contentValues.put(UsersTable.COLUMN_IMAGE, imageByte);
        return db.insert(UsersTable.TABLE_NAME, null, contentValues);
    }

    public boolean update(Users user) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(UsersTable.COLUMN_FIRST_NAME, user.getFirst_Name());
        contentValues.put(UsersTable.COLUMN_LAST_NAME, user.getLast_Name());
        contentValues.put(UsersTable.COLUMN_EMAIL_ID, user.getUsername());
        contentValues.put(UsersTable.COLUMN_PERSONAL_WEBSITE, user.getUsername());
        Bitmap bitmap = user.getImage();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] imageByte = stream.toByteArray();
        contentValues.put(UsersTable.COLUMN_IMAGE, imageByte);
        return db.update(UsersTable.TABLE_NAME, contentValues, UsersTable.COLUMN_EMAIL_ID + "=?", new String[]{user.getUsername()}) > 0;
    }

    public boolean delete(Users user) {
        return db.delete(UsersTable.TABLE_NAME, UsersTable.COLUMN_EMAIL_ID + " =?", new String[]{user.getUsername()}) > 0;
    }

    public Users get(String username) {
        Users user = null;
        Cursor c = db.query(true, UsersTable.TABLE_NAME, new String[]{UsersTable.COLUMN_FIRST_NAME, UsersTable.COLUMN_LAST_NAME, UsersTable.COLUMN_EMAIL_ID, UsersTable.COLUMN_PERSONAL_WEBSITE, UsersTable.COLUMN_IMAGE}, UsersTable.COLUMN_EMAIL_ID + "=?", new String[]{username}, null, null, null, null, null);
        if (c != null && c.moveToFirst()) {
            user = buildUserFromCursor(c);
            if (!c.isClosed()) {
                c.close();
            }
        }
        return user;
    }

    public List<Users> getAll() {
        List users = new ArrayList<Users>();
            Cursor c = db.query(true, UsersTable.TABLE_NAME, new String[]{UsersTable.COLUMN_FIRST_NAME, UsersTable.COLUMN_LAST_NAME, UsersTable.COLUMN_EMAIL_ID, UsersTable.COLUMN_PERSONAL_WEBSITE, UsersTable.COLUMN_IMAGE}, null, null, null, null, null, null);
            if (c != null && c.moveToFirst()) {
            do {
                Users user = buildUserFromCursor(c);
                if (user != null) {
                    users.add(user);
                }
            } while (c.moveToNext());

            if (!c.isClosed()) {
                c.close();
            }
        }
        return users;
    }

    private Users buildUserFromCursor(Cursor c) {
        Users user = null;
        if (c != null) {
            user = new Users();
            user.setFirst_Name(c.getString(0));
            user.setLast_Name(c.getString(1));
            user.setUsername(c.getString(2));
            user.setPassword(c.getString(3));
            byte[] userImage = c.getBlob(4);
            Bitmap bitmap = BitmapFactory.decodeByteArray(userImage,0,userImage.length);
            user.setImage(bitmap);
        }
        return user;
    }
}
