package mihailproductions.com.popularmovies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import mihailproductions.com.popularmovies.Adapter.MovieListAdapter;
import mihailproductions.com.popularmovies.Client.ApiInterface;
import mihailproductions.com.popularmovies.Client.Client;
import mihailproductions.com.popularmovies.Model.MovieResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    MovieListAdapter adapter;
    @BindView(R.id.movie_list) RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        String[] data = {"https://image.tmdb.org/t/p/w185//nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg", "https://image.tmdb.org/t/p/w185//d3qcpfNwbAMCNqWDHzPQsUYiUgS.jpg", "https://image.tmdb.org/t/p/w185//4PiiNGXj1KENTmCBHeN6Mskj2Fq.jpg"};

        ApiInterface api = Client.getClient().create(ApiInterface.class);

        Call<MovieResponse> callPopularMovies = api.getPopularMovies();

        callPopularMovies.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                    Log.v("*************",response.body().getResults().size()+"");
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        adapter = new MovieListAdapter(this, data);
        recyclerView.setAdapter(adapter);
    }
}
