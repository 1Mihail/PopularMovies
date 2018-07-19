package mihailproductions.com.popularmovies.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import mihailproductions.com.popularmovies.Model.Result_;
import mihailproductions.com.popularmovies.R;

public class ReviewListAdapter extends RecyclerView.Adapter<ReviewListAdapter.ViewHolder> {
    private List<Result_> mReviews;
    private LayoutInflater mInflater;

    public ReviewListAdapter(Context context, List<Result_> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mReviews = data;
    }

    @Override
    public ReviewListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.review_list_item, parent, false);
        return new ReviewListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReviewListAdapter.ViewHolder holder, int position) {
        holder.review_author.setText(mReviews.get(position).getAuthor());
        holder.review_content.setText(mReviews.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        return mReviews.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.review_author)
        TextView review_author;

        @BindView(R.id.review_content)
        TextView review_content;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }
}