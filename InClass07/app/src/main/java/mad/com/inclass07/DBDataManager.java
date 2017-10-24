package mad.com.inclass07;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

/**
 * Created by darsh on 10/23/2017.
 */

public class DBDataManager {

    private Context mContext;
    private DatabaseOpenHelper databaseOpenHelper;
    private SQLiteDatabase db;
    private AppDAO appDAO;

    public DBDataManager(Context mContext){
        this.mContext = mContext;
        databaseOpenHelper = new DatabaseOpenHelper(this.mContext);
        db = databaseOpenHelper.getWritableDatabase();
        appDAO = new AppDAO(db);
    }

    public void close(){
        if(db != null){
            db.close();
        }
    }

    public AppDAO getAppDAO(){
        return this.appDAO;
    }

    public long saveApp(App app){
        return this.appDAO.save(app);
    }

    public boolean updateApp(App app){
        return this.appDAO.update(app);
    }

    public boolean deleteApp(App app){
        return this.appDAO.delete(app);
    }

    public App getApp(String name){
        return this.appDAO.get(name);
    }

    public List<App> getAllApps(){
        return this.appDAO.getAll();
    }
}