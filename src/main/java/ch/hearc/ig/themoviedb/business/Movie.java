package ch.hearc.ig.themoviedb.business;

import java.util.List;
import java.util.Set;

public class Movie {
    private int id;
    private String original_title;
    private double rating;

    private String releaseDate;
    private String overview;

    private int runtime;
    private Set<String> genres;

    // Getters and Setters
    public String getTitle() {
        return original_title;
    }

    public void setTitle(String title) {
        this.original_title = title;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public Set<String> getGenres() {
        return genres;
    }

    public void setGenres(Set<String> genres) {
        this.genres = genres;
    }

    // Optional: toString method for easy display
    @Override
    public String toString() {
        return "Movie{" +
                "title='" + original_title + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                ", overview='" + overview + '\'' +
                ", rating=" + rating +
                ", runtime=" + runtime +
                ", genres=" + genres +
                '}';
    }
}
