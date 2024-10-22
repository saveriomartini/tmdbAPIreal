package ch.hearc.ig.themoviedb.business;

public class Credit {
    private int id;
    private Person person;
    private Department department;

    private Job job;

    private String character;

    public Credit() {
    }

    public Credit(int id, Person person, Department department, Job job, String character) {
        this.id = id;
        this.person = person;
        this.department = department;
        this.job = job;
        this.character = character;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public Person getPerson() {
        return person;
    }
    public void setPerson(Person person) {
        this.person = person;
    }
    public Department getDepartment() {
        return department;
    }
    public void setDepartment(Department department) {
        this.department = department;
    }
    public Job getJob() {
        return job;
    }
    public void setJob(Job job) {
        this.job = job;
    }
    public String getCharacter() {
        return character;
    }
    public void setCharacter(String character) {
        this.character = character;
    }
    @Override
    public String toString() {
        return "Credit{" +
                "id=" + id +
                ", person=" + person +
                ", department=" + department +
                ", job=" + job +
                ", character='" + character + '\'' +
                '}';
    }
}
