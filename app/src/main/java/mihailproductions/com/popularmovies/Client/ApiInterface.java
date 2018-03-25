package mihailproductions.com.popularmovies.Client;

import mihailproductions.com.popularmovies.Model.Movie;
import mihailproductions.com.popularmovies.Model.MovieResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiInterface {
    String BASE_URL = "https://api.themoviedb.org/3/movie/";
    String POPULAR_REQUEST = "popular/";
    String TOP_RATED_REQUEST = "top_rated/";
    String API_KEY = "?api_key=###";
    String ID_REQUEST = "{id}";

    @GET(POPULAR_REQUEST + API_KEY)
    Call<MovieResponse> getPopularMovies();

    @GET(TOP_RATED_REQUEST + API_KEY)
    Call<MovieResponse> getTopRatedMovies();

    @GET(ID_REQUEST + API_KEY)
    Call<Movie> getMovieDetails(@Path("id") int id);
}
