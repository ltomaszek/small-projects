package ltcode.person;

public class Neighbour {
    private Person person;
    private int distance;

    public Neighbour(Person person, int distance) {
        this.person = person;
        this.distance = distance;
    }

    public Person getPerson() {
        return person;
    }

    public int getDistance() {
        return distance;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Neighbour{");
        sb.append("person=").append(person.getName());
        sb.append(", distance=").append(distance);
        sb.append('}');
        return sb.toString();
    }
}