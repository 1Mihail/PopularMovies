package mihailproductions.com.popularmovies.Model;

import com.google.gson.annotations.SerializedName;

public class Movie {
    private int id;
    private String title;
    @SerializedName("release_date")
    private String releaseDate;
    @SerializedName("poster_path")
    private String posterPath;
    @SerializedName("vote_average")
    private double voteAverage;
    @SerializedName("overview")
    private String plotSynopsis;
    @SerializedName("videos")
    private Videos videos;
    @SerializedName("reviews")
    private Reviews reviews;

    public Movie(String title, String releaseDate, String posterPath, double voteAverage, String plotSynopsis) {
        this.title = title;
        this.releaseDate = releaseDate;
        this.posterPath = posterPath;
        this.voteAverage = voteAverage;
        this.plotSynopsis = plotSynopsis;
    }

    public Movie(int id, String title, String posterPath) {
        this.id = id;
        this.title = title;
        this.posterPath = posterPath;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public String getPlotSynopsis() {
        return plotSynopsis;
    }

    public Videos getVideos() {
        return videos;
    }

    public Reviews getReviews() {
        return reviews;
    }
}
