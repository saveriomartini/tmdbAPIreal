package ch.hearc.ig.themoviedb.business;

public class Genre implements TmdbItem {
    private int id;
    private int id_tmdb;
    private String name;



    public Genre() {
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public int getId_tmdb() {
        return id_tmdb;
    }

    public void setId_tmdb(int id_tmdb) {
        this.id_tmdb = id_tmdb;
    }
}
