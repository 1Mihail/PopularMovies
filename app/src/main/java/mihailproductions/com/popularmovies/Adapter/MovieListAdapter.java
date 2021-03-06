package mihailproductions.com.popularmovies.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import mihailproductions.com.popularmovies.MainActivity;
import mihailproductions.com.popularmovies.Model.Movie;
import mihailproductions.com.popularmovies.MovieActivity;
import mihailproductions.com.popularmovies.R;

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.ViewHolder> {
    private List<Movie> mMovies;
    private LayoutInflater mInflater;

    public MovieListAdapter(Context context, ArrayList<Movie> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mMovies = data;
    }

    @Override
    public MovieListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.movie_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieListAdapter.ViewHolder holder, int position) {
        final String POSTER_BASE_URL = "https://image.tmdb.org/t/p/w185//";
        Picasso.with(holder.moviePoster.getContext())
                .load(POSTER_BASE_URL + mMovies.get(position).getPosterPath())
                .placeholder(android.R.drawable.ic_menu_gallery)
                .error(android.R.drawable.ic_delete)
                .into(holder.moviePoster);
    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }

    private void onItemClick(View view, int position) {
        Intent i = new Intent(view.getContext(), MovieActivity.class);
        i.putExtra(MovieActivity.KEY_MOVIE_ID, mMovies.get(position).getId());
        view.getContext().startActivity(i);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.movie_list_poster)
        ImageView moviePoster;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onItemClick(view, getAdapterPosition());
        }
    }
}