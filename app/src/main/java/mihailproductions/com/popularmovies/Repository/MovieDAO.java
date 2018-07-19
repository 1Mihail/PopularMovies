package mihailproductions.com.popularmovies.Repository;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import mihailproductions.com.popularmovies.Model.LocalDB.MovieEntity;
@Dao
public interface MovieDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertOnlySingleMovie (MovieEntity movie);
    @Query("SELECT * FROM MovieEntity WHERE id = :id")
    LiveData<MovieEntity> fetchMovieByIdLive(int id);
    @Query("SELECT * FROM MovieEntity WHERE id = :id")
    MovieEntity fetchMovieById (int id);
    @Query ("SELECT * FROM MovieEntity")
    LiveData<List<MovieEntity>> fetchFavorites ();
    @Delete
    void deleteMovie (MovieEntity Movie);
}
