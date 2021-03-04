package ltcode;

import ltcode.person.Neighbour;
import ltcode.person.Person;
import ltcode.path.PathFinder;

import java.util.*;

/**
 * Graphic map: maps_img/example1_map.png
 */
public class Example1 {

    private Person michael;
    private Person darek;
    private Person sabrina;
    private Person antek;
    private Person eva;
    private Person zuzia;
    private Person david;
    private Person marco;
    private Person gargamel;
    private Person arnold;
    private List<Person> people;

    public void run() {
        this.createPeopleAndConnections();

        boolean doSearch = true;
        while (doSearch) {
            Person startPerson = getPerson("Start person:");
            Person targetPerson = getPerson("Target person:");
            System.out.println();

            //System.out.println("\nFINDING PATH with Queue\n");
            //PathFinder.findPath(startPerson, targetPerson);
            //System.out.println("\nFINDING PATH with PriorityQueue\n");
            PathFinder.findShortestPath(startPerson, targetPerson);
            System.out.println();

            System.out.println("Another search? (y/n)");
            String answer = this.userInput();
            if (!answer.equalsIgnoreCase("y") && !answer.equalsIgnoreCase("yes")) {
                doSearch = false;
            }
            System.out.println();
        }
    }

    private Person getPerson(String printText) {
        System.out.println(printText);
        Optional<Person> person = Optional.empty();
        while (person.isEmpty()) {
            String personName = this.userInput();
            person = people.stream()
                    .filter(p -> p.getName().equalsIgnoreCase(personName))
                    .findAny();
            if (person.isEmpty()) {
                System.out.println("Person with name: " + personName + " does not exist. Type correct name.");
            }
        }
        return person.get();
    }

    private String userInput() {
        System.out.print(">> ");
        return new java.util.Scanner(System.in).nextLine();
    }

    /**
     * Create all people on map and their neighbours
     */
    private void createPeopleAndConnections() {
        // Create people - age is in this example redundant
        michael = new Person("Michael", 77);
        darek = new Person("Darek", 17);
        sabrina = new Person("Sabring", 23);
        antek = new Person("Antek", 4);
        eva = new Person("Eva", 8);
        zuzia = new Person("Zuzia", 71);
        david = new Person("David", 25);
        marco = new Person("Marco", 33);
        arnold = new Person("Arnold", 45);
        gargamel = new Person("Gargamel", 500);

        people = new ArrayList<>() {
            {
                add(michael);
                add(darek);
                add(sabrina);
                add(antek);
                add(eva);
                add(zuzia);
                add(david);
                add(marco);
                add(arnold);
                add(gargamel);
            }
        };

        // Create neighbours
        michael.setNeighbours(new Neighbour[]{
                new Neighbour(darek, 7),
                new Neighbour(sabrina, 2)
        });
        darek.setNeighbours(new Neighbour[]{
                new Neighbour(michael, 7),
                new Neighbour(sabrina, 3),
                new Neighbour(antek, 8),
                new Neighbour(david, 5)
        });
        sabrina.setNeighbours(new Neighbour[]{
                new Neighbour(michael, 2),
                new Neighbour(darek, 3),
                new Neighbour(arnold, 20)
        });
        antek.setNeighbours(new Neighbour[]{
                new Neighbour(darek, 8),
                new Neighbour(eva, 1)
        });
        eva.setNeighbours(new Neighbour[]{
                new Neighbour(antek, 1),
                new Neighbour(marco, 3),
                new Neighbour(zuzia, 4)
        });
        zuzia.setNeighbours(new Neighbour[]{
                new Neighbour(eva, 4),
                new Neighbour(arnold, 5)
        });
        david.setNeighbours(new Neighbour[]{
                new Neighbour(darek, 5),
                new Neighbour(marco, 2)
        });
        marco.setNeighbours(new Neighbour[]{
                new Neighbour(david, 2),
                new Neighbour(eva, 3)
        });
        arnold.setNeighbours(new Neighbour[]{
                new Neighbour(sabrina, 20),
                new Neighbour(zuzia, 5)
        });
        gargamel.setNeighbours(new Neighbour[]{});
    }
}