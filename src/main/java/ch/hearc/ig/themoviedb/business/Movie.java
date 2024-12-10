package ch.hearc.ig.themoviedb.business;

import oracle.sql.DATE;

import java.io.Serializable;
import java.sql.Date;
import java.util.Set;

public class Movie implements TmdbItem, Serializable {
    private static final long serialVersionUID = 1L;
    private int ID;
    private int id_tmdb;
    private String original_title;
    private double rating;
    private Date releaseDate;
    private int runtime;
    private String tagline;
    private String overview;
    private String poster_path;

    private Language original_language;
    private Set<Genre> genres;


    // Constructors
    public Movie() {
    }

    public Movie(int ID, int id_tmdb, String original_title, double rating, Date releaseDate, int runtime, String tagline, String overview, String poster_path, Language original_language, Set<Genre> genres) {
        this.ID = ID;
        this.id_tmdb = id_tmdb;
        this.original_title = original_title;
        this.rating = rating;
        this.releaseDate = releaseDate;
        this.runtime = runtime;
        this.tagline = tagline;
        this.overview = overview;
        this.poster_path = null;
        this.original_language = null;
        this.genres = null;
    }


    // Getters and Setters
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getId_tmdb() {
        return id_tmdb;
    }

    public void setId_tmdb(int id_tmdb) {
        this.id_tmdb = id_tmdb;
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

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Set<Genre> getGenres() {
        return genres;
    }

    public void setGenres(Set<Genre> genres) {
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

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public Language getOriginal_language() {
        return original_language;
    }

    public void setOriginal_language(Language original_language) {
        this.original_language = original_language;
    }






    // toString
    @Override
public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\nMovie Details:");

        sb.append("\nID: ").append(this.id_tmdb);
        sb.append("\nTitle: ").append(this.original_title);
        sb.append("\nTagline: ").append(this.tagline);
        sb.append("\nRating: ").append(this.rating);
        sb.append("\nLanguage: ").append(this.original_language);
        sb.append("\nRelease Date: ").append(this.releaseDate);
        sb.append("\nGenres: ").append(this.genres);
        sb.append("\nRuntime: ").append(this.runtime).append("'");
        sb.append("\nOverview: ").append(this.overview);
        sb.append("\nPoster Path: ").append(this.poster_path);

        return sb.toString();
    }

    @Override
    public int getId() {
        return 0;
    }

    public String displayBasics() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.id_tmdb);
        sb.append(" - ").append(this.original_title);
        sb.append(" - ").append(this.releaseDate != null ? this.releaseDate.toLocalDate().getYear() : "N/A");

        return sb.toString();
    }
}
