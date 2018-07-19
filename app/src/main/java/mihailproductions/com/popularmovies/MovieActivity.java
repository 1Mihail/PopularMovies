package mihailproductions.com.popularmovies;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.persistence.room.Room;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import mihailproductions.com.popularmovies.Adapter.ReviewListAdapter;
import mihailproductions.com.popularmovies.Client.ApiInterface;
import mihailproductions.com.popularmovies.Client.Client;
import mihailproductions.com.popularmovies.Database.MovieDB;
import mihailproductions.com.popularmovies.Model.LocalDB.MovieEntity;
import mihailproductions.com.popularmovies.Model.Movie;
import mihailproductions.com.popularmovies.Model.Result;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieActivity extends AppCompatActivity {
    @BindView(R.id.movie_poster)
    ImageView mMoviePoster;
    @BindView(R.id.movie_title)
    TextView mMovieTitle;
    @BindView(R.id.movie_rating)
    TextView mMovieRating;
    @BindView(R.id.movie_release)
    TextView mMovieRelease;
    @BindView(R.id.movie_overview)
    TextView mMovieOverview;
    @BindView(R.id.favorites)
    ImageView mFavorites;
    @BindView(R.id.reviews_rv)
    RecyclerView mReviewsRV;
    @BindView(R.id.no_reviews)
    TextView noReviews;
    private ReviewListAdapter mAdapter;
    private ApiInterface mApi;
    private Movie movie;
    private int mCurrentMovieId;

    private static final String DATABASE_NAME = "movies_db";
    private MovieDB movieDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        ButterKnife.bind(this);
        movieDatabase = Room.databaseBuilder(getApplicationContext(),
                MovieDB.class, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build();
        mCurrentMovieId = getIntent().getIntExtra("movieid", 0);
        mApi = Client.getClient().create(ApiInterface.class);
        showMovieDetails(mApi.getMovieDetails(mCurrentMovieId));

        adjustFavoriteButtonColor();

        mFavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        MovieEntity favoriteMovie = movieDatabase.daoAccess().fetchMovieById(mCurrentMovieId);
                        if(movie!=null && favoriteMovie==null){
                            movieDatabase.daoAccess().insertOnlySingleMovie(new MovieEntity(movie.getId() ,movie.getTitle(),movie.getPosterPath()));
                        }else if(favoriteMovie != null){
                                movieDatabase.daoAccess().deleteMovie(favoriteMovie);
                            }
                    }
                }) .start();
            }
        });
    }

    void adjustFavoriteButtonColor(){
        final LiveData<MovieEntity> favoriteMovie = movieDatabase.daoAccess().fetchMovieByIdLive(mCurrentMovieId);
        favoriteMovie.observe(this, new Observer<MovieEntity>() {
            @Override
            public void onChanged(@Nullable MovieEntity favoriteMovie) {
                if(favoriteMovie!=null){
                    mFavorites.setColorFilter(getResources().getColor(R.color.colorFavorite));
                }else{
                    mFavorites.setColorFilter(getResources().getColor(R.color.colorPrimaryDark));
                }
            }
        });

    }

    void initiateTrailers(){
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.ll_container);
        final String youtube = "YouTube";
        final float scale = getResources().getDisplayMetrics().density/2;
        int marginDpAsPixels = (int) (getResources().getDimension(R.dimen.margin)*scale + 0.5f);
        int margin2DpAsPixels = (int) (getResources().getDimension(R.dimen.margin2)*scale + 0.5f);
        for(final Result result : movie.getVideos().getResults()){
            if(result.getSite().equals(youtube))
            {
                TextView trailerTV = new TextView(this);
                trailerTV.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
                trailerTV.setText(result.getName());
                trailerTV.setPadding(marginDpAsPixels, margin2DpAsPixels, marginDpAsPixels, margin2DpAsPixels);
                trailerTV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        watchYoutubeVideo(MovieActivity.this,result.getKey());
                    }
                });
                trailerTV.setCompoundDrawablesWithIntrinsicBounds( R.drawable.ic_play_circle, 0, 0, 0);
                linearLayout.addView(trailerTV,2);
            }
        }
    }

    public static void watchYoutubeVideo(Context context, String id){
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id));
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://www.youtube.com/watch?v=" + id));
        try {
            context.startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            context.startActivity(webIntent);
        }
    }

    private void showMovieDetails(Call<Movie> call) {
        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                movie = response.body();
                populateMovieActivity();
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                Toast.makeText(MovieActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initiateReviews(){
        mReviewsRV.setLayoutManager(new LinearLayoutManager(this));
        if(movie.getReviews().getResults().size()>0){
            mAdapter = new ReviewListAdapter(this, movie.getReviews().getResults());
            mReviewsRV.setAdapter(mAdapter);
        }else{
            mReviewsRV.setVisibility(View.GONE);
            noReviews.setVisibility(View.VISIBLE);
        }

    }

    void populateMovieActivity() {
        final String POSTER_BASE_URL = "https://image.tmdb.org/t/p/w185//";
        Picasso.with(this)
                .load(POSTER_BASE_URL + movie.getPosterPath())
                .placeholder(android.R.drawable.ic_menu_gallery)
                .error(android.R.drawable.ic_delete)
                .into(mMoviePoster);
        mMovieTitle.setText(movie.getTitle());
        mMovieRating.setText(String.valueOf(movie.getVoteAverage()));
        mMovieRelease.setText(movie.getReleaseDate());
        mMovieOverview.setText(movie.getPlotSynopsis());
        initiateTrailers();
        initiateReviews();
    }
}
