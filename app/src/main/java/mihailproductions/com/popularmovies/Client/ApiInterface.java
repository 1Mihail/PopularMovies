package mihailproductions.com.popularmovies.Client;

import mihailproductions.com.popularmovies.Model.MovieResponse;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {
    String BASE_URL = "https://api.themoviedb.org/3/movie/";
    String POPULAR_REQUEST = "popular";
    String TOP_RATED_REQUEST = "top_rated";
    String API_KEY = "/?api_key=###";

    @GET(POPULAR_REQUEST + API_KEY)
    Call<MovieResponse> getPopularMovies();


}
