package mihailproductions.com.popularmovies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import mihailproductions.com.popularmovies.Client.ApiInterface;
import mihailproductions.com.popularmovies.Client.Client;
import mihailproductions.com.popularmovies.Model.Movie;
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
    private ApiInterface mApi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        ButterKnife.bind(this);
        int currentMovieId = getIntent().getIntExtra("movieid",0);
        mApi = Client.getClient().create(ApiInterface.class);
        showMovieDetails(mApi.getMovieDetails(currentMovieId));
    }

    private void showMovieDetails(Call<Movie> call){
        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                populateMovieActivity(response.body());
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                Toast.makeText(MovieActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    void populateMovieActivity(Movie movie){
        final String POSTER_BASE_URL="https://image.tmdb.org/t/p/w185//";
        Picasso.with(this)
                .load(POSTER_BASE_URL + movie.getPosterPath())
                .placeholder(android.R.drawable.ic_menu_gallery)
                .error(android.R.drawable.ic_delete)
                .into(mMoviePoster);
        mMovieTitle.setText(movie.getTitle());
        mMovieRating.setText(String.valueOf(movie.getVoteAverage()));
        mMovieRelease.setText(movie.getReleaseDate());
        mMovieOverview.setText(movie.getPlotSynopsis());
    }
}
