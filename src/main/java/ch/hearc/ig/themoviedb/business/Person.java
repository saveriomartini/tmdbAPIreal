package ch.hearc.ig.themoviedb.business;

public class Person {

    private int id;
    private String name;
    private int id_themoviedb;

    public Person() {
    }

    public Person(int id, String name, int id_themoviedb) {
        this.id = id;
        this.name = name;
        this.id_themoviedb = id_themoviedb;
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

    public int getId_themoviedb() {
        return id_themoviedb;
    }

    public void setId_themoviedb(int id_themoviedb) {
        this.id_themoviedb = id_themoviedb;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", id_themoviedb=" + id_themoviedb +
                '}';
    }
}
