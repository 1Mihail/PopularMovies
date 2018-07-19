package mihailproductions.com.popularmovies.Database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import mihailproductions.com.popularmovies.MainActivity;
import mihailproductions.com.popularmovies.Model.LocalDB.MovieEntity;
import mihailproductions.com.popularmovies.Repository.MovieDAO;

@Database(entities = {MovieEntity.class}, version = 1, exportSchema = false)
public abstract class MovieDB extends RoomDatabase {
    public abstract MovieDAO daoAccess();
    private static final Object LOCK = new Object();
    private static MovieDB sInstance;
    public static final String DATABASE_NAME = "movies_db";

    public static MovieDB getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        MovieDB.class, DATABASE_NAME)
                        .build();
            }
        }
        return sInstance;
    }
}