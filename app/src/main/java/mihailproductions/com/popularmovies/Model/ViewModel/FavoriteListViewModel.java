package mihailproductions.com.popularmovies.Model.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.support.annotation.NonNull;

import java.util.List;

import mihailproductions.com.popularmovies.Database.MovieDB;
import mihailproductions.com.popularmovies.MainActivity;
import mihailproductions.com.popularmovies.Model.LocalDB.MovieEntity;

public class FavoriteListViewModel extends AndroidViewModel {

    private LiveData<List<MovieEntity>> mFavoriteList;

    public FavoriteListViewModel(@NonNull Application application) {
        super(application);

        MovieDB database = MovieDB.getInstance(getApplication().getApplicationContext());

        mFavoriteList = database.daoAccess().fetchFavorites();
    }

    public LiveData<List<MovieEntity>> getFavoriteList() {
        return mFavoriteList;
    }
}
