package mihailproductions.com.popularmovies.Model.LocalDB;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class MovieEntity {
    @NonNull
    @PrimaryKey
    private int id;
    private String title;
    private String posterPath;

    public MovieEntity(@NonNull int id, String title, String posterPath) {
        this.id = id;
        this.title = title;
        this.posterPath = posterPath;
    }

    @NonNull
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    @Override
    public String toString() {
        return "MovieEntity{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", posterPath='" + posterPath + '\'' +
                '}';
    }
}
