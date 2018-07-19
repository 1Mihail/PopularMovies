package mihailproductions.com.popularmovies;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import mihailproductions.com.popularmovies.Adapter.MovieListAdapter;
import mihailproductions.com.popularmovies.Client.ApiInterface;
import mihailproductions.com.popularmovies.Client.Client;
import mihailproductions.com.popularmovies.Model.LocalDB.MovieEntity;
import mihailproductions.com.popularmovies.Model.Movie;
import mihailproductions.com.popularmovies.Model.MovieResponse;
import mihailproductions.com.popularmovies.Model.ViewModel.FavoriteListViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.movie_list)
    RecyclerView mMovieListRV;
    private MovieListAdapter mAdapter;
    private ApiInterface mApi;
    private final String SORTING_ORDER = "sorting_order";
    private final String SCROLL_POSITION = "scroll_position";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mMovieListRV.setLayoutManager(new GridLayoutManager(this, 3));
        mApi = Client.getClient().create(ApiInterface.class);
        if(savedInstanceState==null){
            showMovies(mApi.getPopularMovies());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.corner_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        changeActionBarTitle(item.getItemId());
        switch (item.getItemId()) {
            case R.id.top_rated:
                showMovies(mApi.getTopRatedMovies());
                return true;
            case R.id.popular:
                showMovies(mApi.getPopularMovies());
                return true;
            case R.id.favorites:
                showFavorites();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void showFavorites(){
        FavoriteListViewModel viewModel = ViewModelProviders.of(this).get(FavoriteListViewModel.class);
        viewModel.getFavoriteList().observe(this, new Observer<List<MovieEntity>>() {
            @Override
            public void onChanged(@Nullable List<MovieEntity> moviesList) {
                ArrayList<Movie> newList = new ArrayList<>();
                for(MovieEntity movie : moviesList){
                    newList.add(new Movie(movie.getId(),movie.getTitle(),movie.getPosterPath()));
                }
                mAdapter = new MovieListAdapter(MainActivity.this, newList);
                mMovieListRV.setAdapter(mAdapter);
            }
        });
    }

    private void changeActionBarTitle(int option) {
        if (getSupportActionBar() != null) {
            switch (option) {
                case R.id.top_rated:
                    getSupportActionBar().setTitle(R.string.top_rated_movies);
                    break;
                case R.id.popular:
                    getSupportActionBar().setTitle(R.string.popular_movies);
                    break;
                case R.id.favorites:
                    getSupportActionBar().setTitle(R.string.favorites);
                    break;
            }
        }
    }

    private void showMovies(Call<MovieResponse> call) {
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                mAdapter = new MovieListAdapter(MainActivity.this, response.body().getResults());
                mMovieListRV.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        final String title = getSupportActionBar().getTitle().toString();
        if(title.equals(getResources().getString(R.string.top_rated_movies))){
            savedInstanceState.putInt(SORTING_ORDER, 0);
        }else if(getSupportActionBar().getTitle().equals(getResources().getString(R.string.popular_movies))){
            savedInstanceState.putInt(SORTING_ORDER, 1);
        }else if(getSupportActionBar().getTitle().equals(getResources().getString(R.string.favorites))){
            savedInstanceState.putInt(SORTING_ORDER, 2);
        }
        savedInstanceState.putIntArray(SCROLL_POSITION,
                new int[]{ mMovieListRV.getScrollX(), mMovieListRV.getScrollY()});
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {

        super.onRestoreInstanceState(savedInstanceState);
        int sortingOrder = savedInstanceState.getInt(SORTING_ORDER);

        switch (sortingOrder){
            case 0:
                getSupportActionBar().setTitle(R.string.top_rated_movies);
                showMovies(mApi.getTopRatedMovies());
                break;
            case 1:
                showMovies(mApi.getPopularMovies());
                getSupportActionBar().setTitle(R.string.popular_movies);
                break;
            case 2:
                showFavorites();
                getSupportActionBar().setTitle(R.string.favorites);
                break;
        }

        final int[] position = savedInstanceState.getIntArray(SCROLL_POSITION);
        if(position != null)
            mMovieListRV.post(new Runnable() {
                public void run() {
                    mMovieListRV.scrollTo(position[0], position[1]);
                }
            });
    }
}
