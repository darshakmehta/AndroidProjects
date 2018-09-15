package mad.com.hw06;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class InstructorsDAO {
    private SQLiteDatabase db;

    public InstructorsDAO(SQLiteDatabase db) {
        this.db = db;
    }

    public long save(Instructor instructors) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(InstructorsTable.COLUMN_USERNAME, instructors.getUsername());
        contentValues.put(InstructorsTable.COLUMN_FIRST_NAME, instructors.getInstructorFirstName());
        contentValues.put(InstructorsTable.COLUMN_LAST_NAME, instructors.getInstructorLastName());
        contentValues.put(InstructorsTable.COLUMN_EMAIL_ID, instructors.getInstructorEmailId());
        contentValues.put(InstructorsTable.COLUMN_PERSONAL_WEBSITE, instructors.getInstructorWebsite());
        Bitmap bitmap = instructors.getInstructorImage();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] imageByte = stream.toByteArray();
        contentValues.put(InstructorsTable.COLUMN_IMAGE, imageByte);
        return db.insert(InstructorsTable.TABLE_NAME, null, contentValues);
    }

    /*public boolean update(Instructor instructors) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(InstructorsTable.COLUMN_USERNAME, instructors.getUsername());
        contentValues.put(InstructorsTable.COLUMN_FIRST_NAME, instructors.getInstructorFirstName());
        contentValues.put(InstructorsTable.COLUMN_LAST_NAME, instructors.getInstructorLastName());
        contentValues.put(InstructorsTable.COLUMN_EMAIL_ID, instructors.getInstructorEmailId());
        contentValues.put(InstructorsTable.COLUMN_PERSONAL_WEBSITE, instructors.getInstructorWebsite());
        Bitmap bitmap = instructors.getInstructorImage();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] imageByte = stream.toByteArray();
        contentValues.put(InstructorsTable.COLUMN_IMAGE, imageByte);
        return db.update(InstructorsTable.TABLE_NAME, contentValues, InstructorsTable.COLUMN_EMAIL_ID + "=?", new String[]{instructors.getUsername()}) > 0;
    }*/

    public boolean delete(Instructor instructor) {
        return db.delete(InstructorsTable.TABLE_NAME, InstructorsTable.COLUMN_USERNAME + " =? and "+ InstructorsTable.COLUMN_EMAIL_ID+ " =?", new String[]{instructor.getUsername(), instructor.getInstructorEmailId()}) > 0;
    }

    public Instructor get(String username, String email_id) {
        Instructor instructor = null;
        Cursor c = db.query(true, InstructorsTable.TABLE_NAME, new String[]{InstructorsTable.COLUMN_USERNAME, InstructorsTable.COLUMN_FIRST_NAME, InstructorsTable.COLUMN_LAST_NAME, InstructorsTable.COLUMN_EMAIL_ID, InstructorsTable.COLUMN_PERSONAL_WEBSITE, InstructorsTable.COLUMN_IMAGE}, InstructorsTable.COLUMN_USERNAME + " =?" + " AND " + InstructorsTable.COLUMN_EMAIL_ID + " =?", new String[]{username, email_id}, null, null, null, null, null);
        if (c != null && c.moveToFirst()) {
            instructor = buildInstructorFromCursor(c);
            if (!c.isClosed()) {
                c.close();
            }
        }
        return instructor;
    }

    public List<Instructor> getAll(String username) {
        List instructors = new ArrayList<Instructor>();
        Cursor c = db.query(true, InstructorsTable.TABLE_NAME, new String[]{InstructorsTable.COLUMN_USERNAME, InstructorsTable.COLUMN_FIRST_NAME, InstructorsTable.COLUMN_LAST_NAME, InstructorsTable.COLUMN_EMAIL_ID, InstructorsTable.COLUMN_PERSONAL_WEBSITE, InstructorsTable.COLUMN_IMAGE}, InstructorsTable.COLUMN_USERNAME + " =?", new String[]{username}, null, null, null, null, null);
        if (c != null && c.moveToFirst()) {
            do {
                Instructor instructor = buildInstructorFromCursor(c);
                if (instructor != null) {
                    instructors.add(instructor);
                }
            } while (c.moveToNext());

            if (!c.isClosed()) {
                c.close();
            }
        }
        return instructors;
    }

    private Instructor buildInstructorFromCursor(Cursor c) {
        Instructor instructor = null;
        if (c != null) {
            instructor = new Instructor();
            instructor.setUsername(c.getString(0));
            instructor.setInstructorFirstName(c.getString(1));
            instructor.setInstructorLastName(c.getString(2));
            instructor.setInstructorEmailId(c.getString(3));
            instructor.setInstructorWebsite(c.getString(4));
            byte[] instructorImage = c.getBlob(5);
            Bitmap bitmap = BitmapFactory.decodeByteArray(instructorImage,0,instructorImage.length);
            instructor.setInstructorImage(bitmap);
        }
        return instructor;
    }
}
