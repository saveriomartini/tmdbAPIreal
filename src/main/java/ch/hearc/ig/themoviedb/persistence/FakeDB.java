package ch.hearc.ig.themoviedb.persistence;

import ch.hearc.ig.themoviedb.business.Job;
import ch.hearc.ig.themoviedb.business.Movie;
import ch.hearc.ig.themoviedb.business.Person;

import java.util.HashMap;
import java.util.Map;

public class FakeDB {
    // Inizializzazione immediata delle collezioni
    private static Map<Integer, Movie> MovieCollection = new HashMap<>();
    private static Map<Integer, Person> PersonCollection = new HashMap<>();
    private static Map<Integer, Job> JobCollection = new HashMap<>();

    // Metodo per aggiungere un film
    public static void addMovie(Movie movie) {
        MovieCollection.put(movie.getId(), movie);
    }

    // Metodo per aggiungere una persona
    public static void addPerson(Person person) {
        PersonCollection.put(person.getId(), person);
    }

    // Metodo per aggiungere un lavoro (scommentato)
    public static void addJob(Job job) {
        JobCollection.put(job.getId(), job);
    }

    // Altri metodi per ottenere dati dalla collezione (opzionali)
    public static Map<Integer, Movie> getAllMovies() {
        return MovieCollection;
    }

    public static Map<Integer, Person> getAllPersons() {
        return PersonCollection;
    }

    public static Map<Integer, Job> getAllJobs() {
        return JobCollection;
    }

    @Override
    public String toString() {
        return "FakeDB{" +
                "MovieCollection=" + MovieCollection +
                ", PersonCollection=" + PersonCollection +
                ", JobCollection=" + JobCollection +
                '}';
    }
}
