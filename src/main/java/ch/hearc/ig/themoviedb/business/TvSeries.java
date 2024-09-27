package ch.hearc.ig.themoviedb.business;

public class TvSeries {
    private int id;
    private String original_name;
    private double rating;

    private String first_air_date;
    private String overview;

    private int number_of_episodes;
    private int number_of_seasons;

    // Getters and Setters
    public String getTitle() {
        return original_name;
    }

    public void setTitle(String title) {
        this.original_name = title;
    }

    public String getFirstAirDate() {
        return first_air_date;
    }

    public void setFirstAirDate(String firstAirDate) {
        this.first_air_date = firstAirDate;
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

    public int getNumberOfEpisodes() {
        return number_of_episodes;
    }

    public void setNumberOfEpisodes(int numberOfEpisodes) {
        this.number_of_episodes = numberOfEpisodes;
    }

    public int getNumberOfSeasons() {
        return number_of_seasons;
    }

    public void setNumberOfSeasons(int numberOfSeasons) {
        this.number_of_seasons = numberOfSeasons;
    }
}
