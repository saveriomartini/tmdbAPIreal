package ch.hearc.ig.themoviedb.persistence;

import ch.hearc.ig.themoviedb.business.Job;
import ch.hearc.ig.themoviedb.business.Movie;
import ch.hearc.ig.themoviedb.business.Person;

import java.util.HashMap;
import java.util.Map;

public class FakeDB {
    private static Map<Integer, Movie> MovieCollection;
    private static Map<Integer, Person> PersonCollection;
    private static Map<Integer, Job> JobCollection;

    public static void init() {
        MovieCollection = new HashMap<>();
        PersonCollection = new HashMap<>();
        JobCollection = new HashMap<>();
    }

    public static void addMovie(Movie movie) {
        MovieCollection.put(movie.getId(), movie);
    }

    public static void addPerson(Person person) {
        PersonCollection.put(person.getId(), person);
    }

//    public static void addJob(Job job) {
//        JobCollection.put(job.getId(), job);
    }





