package mihailproductions.com.popularmovies.Client;

import mihailproductions.com.popularmovies.BuildConfig;
import mihailproductions.com.popularmovies.Model.Movie;
import mihailproductions.com.popularmovies.Model.MovieResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiInterface {

    String BASE_URL = "https://api.themoviedb.org/3/movie/";
    String POPULAR_REQUEST = "popular/";
    String TOP_RATED_REQUEST = "top_rated/";
    String API_KEY = "?api_key="+ BuildConfig.API_KEY;
    String ID_REQUEST = "{id}";
    String APPEND_VIDEOS_REVIEWS = "&append_to_response=videos,reviews";

    @GET(POPULAR_REQUEST + API_KEY)
    Call<MovieResponse> getPopularMovies();

    @GET(TOP_RATED_REQUEST + API_KEY)
    Call<MovieResponse> getTopRatedMovies();

    @GET(ID_REQUEST + API_KEY + APPEND_VIDEOS_REVIEWS)
    Call<Movie> getMovieDetails(@Path("id") int id);
}
