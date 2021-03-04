package ltcode.person;

import java.util.Arrays;
import java.util.Comparator;

public class Person implements Comparable<Person> {
    private String name;
    private int age;
    private Neighbour[] neighbours;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public void setNeighbours(Neighbour[] neighbours) {
        this.neighbours = neighbours;
    }

    public Neighbour[] getNeighbours() {
        return neighbours;
    }

    @Override
    public int compareTo(Person o) {
        return this.name.compareTo(o.name);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Person{");
        sb.append("name='").append(name).append('\'');
        sb.append(", age=").append(age);
        sb.append(", neighbours=").append(neighbours == null ? "null" : Arrays.asList(neighbours).toString());
        sb.append('}');
        return sb.toString();
    }

    public static Comparator<Person> comparatorByAge() {
        return (p1, p2) -> Integer.compare(p1.age, p2.age);
    }
}