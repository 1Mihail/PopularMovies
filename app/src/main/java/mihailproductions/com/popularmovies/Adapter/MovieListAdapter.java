package mihailproductions.com.popularmovies.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import mihailproductions.com.popularmovies.R;

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.ViewHolder> {
    private String[] mMovies = new String[0];
    private LayoutInflater mInflater;

    public MovieListAdapter(Context context, String[] data) {
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
        Picasso.with(holder.moviePoster.getContext())
                .load(mMovies[position])
                .placeholder(android.R.drawable.ic_menu_gallery)
                .error(android.R.drawable.ic_delete)
                .into(holder.moviePoster);
    }

    @Override
    public int getItemCount() {
        return mMovies.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.movie_list_poster) ImageView moviePoster;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            onItemClick(view, getAdapterPosition());
        }
    }

    private void onItemClick(View view, int position) {
        Log.i("TAG", "You clicked " + mMovies[position] + ", which is at cell position " + position);
    }
}