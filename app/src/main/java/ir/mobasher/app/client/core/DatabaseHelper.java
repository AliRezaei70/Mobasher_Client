package ir.mobasher.app.client.core;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicInteger;

import ir.mobasher.app.client.model.users.User;


/**
 * DatabaseHelper helper class used to manage the creation and upgrading of your
 * database. This class also usually provides the DAOs used by the other
 * classes.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

// Local
//    @SuppressLint("SdCardPath")
//    private static final String DATABASE_NAME = Environment
//            .getExternalStorageDirectory().getAbsolutePath() + "/Android/data/com.example.salmansaleem.assignment/mehndi.db";

    //
    // private static final String DATABASE_NAME = AppController.getInstance().getFilesDir() + "/mehndi.db";
    private static final String DATABASE_NAME = "MobasherLawyerApp.db";

    private static final int DATABASE_VERSION = 1;


   // private Dao<RecordModel, Integer> studentRecordModelIntegerDao = null;

    private Dao<User, Integer> usersDao = null;


    private static final AtomicInteger usageCounter = new AtomicInteger(0);

    private static DatabaseHelper database;

    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }



    public static synchronized DatabaseHelper getInstance(Context context) {
        if (database == null) {

            database = new DatabaseHelper(context);
        }
        usageCounter.incrementAndGet();
        return database;
    }

    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        try {
            Log.i(DatabaseHelper.class.getName(), "onCreate");

            TableUtils.createTable(connectionSource, User.class);

        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Can't create database", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            Log.i(DatabaseHelper.class.getName(), "onUpgrade");


        } catch (Exception e) {
            Log.e(DatabaseHelper.class.getName(), "Can't drop databases", e);
            throw new RuntimeException(e);
        }
    }

    public Dao<User, Integer> getUsersDao() throws SQLException {
        if (usersDao == null) {
            usersDao = getDao(User.class);
        }
        return usersDao;
    }




    @Override
    public void close() {
        if (usageCounter.decrementAndGet() == 0) {
            super.close();
            usersDao = null;

            database = null;
        }
    }


}

