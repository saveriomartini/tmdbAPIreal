package ch.hearc.ig.themoviedb.business;

import java.util.Set;

public class Movie {
    private int id;
    private String original_title;
    private double rating;
    private String releaseDate;
    private Set<String> genres;
    private int runtime;
    private String tagline;
    private String overview;
    private Set<Person> cast;


    // Constructors
    public Movie() {
    }

    public Movie(int id, String original_title, double rating, String releaseDate, int runtime, String tagline, String overview) {
        this.id = id;
        this.original_title = original_title;
        this.rating = rating;
        this.releaseDate = releaseDate;
        this.runtime = runtime;
        this.tagline = tagline;
        this.overview = overview;
    }


    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Set<String> getGenres() {
        return genres;
    }

    public void setGenres(Set<String> genres) {
        this.genres = genres;
    }

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public Set<Person> getCast() {
        return cast;
    }

    public void setCast(Set<Person> cast) {
        this.cast = cast;
    }

    // toString
    @Override
public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\nMovie Details:");

        sb.append("\nID: ").append(this.id);
        sb.append("\nOriginal Title: ").append(this.original_title);
        sb.append("\nRating: ").append(this.rating);
        sb.append("\nRelease Date: ").append(this.releaseDate);
        sb.append("\nGenres: ").append(this.genres);
        sb.append("\nRuntime: ").append(this.runtime).append("'");
        sb.append("\nTagline: ").append(this.tagline);
        sb.append("\nOverview: ").append(this.overview);
        sb.append("\nCast: ").append(this.cast);

        sb.append("\nClass: ").append(this.getClass());

        return sb.toString();
    }


}
