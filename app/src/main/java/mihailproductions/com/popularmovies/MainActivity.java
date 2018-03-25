package mihailproductions.com.popularmovies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import mihailproductions.com.popularmovies.Adapter.MovieListAdapter;

public class MainActivity extends AppCompatActivity {
    MovieListAdapter adapter;
    @BindView(R.id.movie_list) RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        String[] data = {"https://image.tmdb.org/t/p/w185//nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg", "https://image.tmdb.org/t/p/w185//d3qcpfNwbAMCNqWDHzPQsUYiUgS.jpg", "https://image.tmdb.org/t/p/w185//4PiiNGXj1KENTmCBHeN6Mskj2Fq.jpg"};

        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        adapter = new MovieListAdapter(this, data);
        recyclerView.setAdapter(adapter);
    }
}
