package mihailproductions.com.popularmovies.Database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import mihailproductions.com.popularmovies.Model.LocalDB.MovieEntity;
import mihailproductions.com.popularmovies.Repository.MovieDAO;

@Database(entities = {MovieEntity.class}, version = 1, exportSchema = false)
public abstract class MovieDB extends RoomDatabase {
    public abstract MovieDAO daoAccess() ;
}