package ltcode.path;

import ltcode.person.Neighbour;
import ltcode.person.Person;

import java.util.*;

public class PathFinder {

    /**
     * Method use BFS, distance is not taking into account
     * @param startPerson
     * @param targetPerson
     */
    public static void findPath(Person startPerson, Person targetPerson) {
        System.out.printf("*** Start person: %s | target person: %s\n", startPerson.getName(), targetPerson.getName());
        if (startPerson == null || targetPerson == null)
            throw new IllegalArgumentException("Person can not be null!");
        if (startPerson == targetPerson) {
            System.out.println("Start and target persons are the same!");
            return;
        }
        System.out.println("Following steps are checked:");

        Set<Person> addedPeople = new HashSet<>();  // added people to queue
        addedPeople.add(startPerson);
        ArrayDeque<DisNeighbour> deque = new ArrayDeque<>();
        deque.offer(new DisNeighbour(startPerson, null, 0));
        DisNeighbour currNeighbour = null;
        boolean foundPath = false;

        while (!deque.isEmpty()) {
            currNeighbour = deque.poll();
            System.out.println("Checking person: " + currNeighbour.person.getName() + " | total distance: "
                    + currNeighbour.totalDistance);
            if (currNeighbour.person == targetPerson) {
                foundPath = true;
                break;
            }
            // add neighbours to queue
            for (Neighbour neighbour : currNeighbour.person.getNeighbours()) {
                if (addedPeople.contains(neighbour.getPerson()) == false) {
                    deque.offer(new DisNeighbour(neighbour.getPerson(), currNeighbour.fromPerson,
                            currNeighbour.totalDistance + neighbour.getDistance()));
                    addedPeople.add(neighbour.getPerson());
                }
            }
        }

        if (foundPath) {
            System.out.println("Found path!!! Total distance: " + currNeighbour.totalDistance);
        } else {
            System.out.println("Path not found");
        }
    }

    public static void findShortestPath(Person startPerson, Person targetPerson) {
        System.out.printf("*** Start person: %s | target person: %s\n", startPerson.getName(), targetPerson.getName());
        if (startPerson == null || targetPerson == null)
            throw new IllegalArgumentException("Person can not be null!");
        if (startPerson == targetPerson) {
            System.out.println("Start and target persons are the same!");
            return;
        }
        System.out.println("Following steps are checked:");

        Set<Person> visited = new HashSet<>();  // added people to queue
        PriorityQueue<DisNeighbour> queue = new PriorityQueue<>(
                (n1, n2) -> Integer.compare(n1.totalDistance, n2.totalDistance));
        queue.offer(new DisNeighbour(startPerson, null, 0));
        DisNeighbour currNeighbour = null;
        boolean foundPath = false;

        while (!queue.isEmpty()) {
            currNeighbour = queue.poll();
            if (visited.contains(currNeighbour.person)) {
                continue;
            }
            if (currNeighbour.fromPerson != null) {
                System.out.println(currNeighbour.fromPerson.getName() + " -> " + currNeighbour.person.getName()
                        + " | total distance: " + currNeighbour.totalDistance);
            }
            if (currNeighbour.person == targetPerson) {
                foundPath = true;
                break;
            }
            visited.add(currNeighbour.person);

            // add neighbours to queue
            for (Neighbour neighbour : currNeighbour.person.getNeighbours()) {
                if (visited.contains(neighbour.getPerson()) == false) {
                    queue.offer(new DisNeighbour(neighbour.getPerson(), currNeighbour.person,
                            currNeighbour.totalDistance + neighbour.getDistance()));
                }
            }
        }

        if (foundPath) {
            System.out.println("Found path!!! Total distance: " + currNeighbour.totalDistance);
        } else {
            System.out.println("Path not found");
        }

    }

    /**
     * Helper static class that keeps track of the total distance from start person to current neighbour
     * Useful for finding the shortest path with PriorityQueue
     */
    private static class DisNeighbour {
        private Person person;
        private Person fromPerson;
        private int totalDistance;

        public DisNeighbour(Person person, Person fromPerson, int totalDistance) {
            this.person = person;
            this.fromPerson = fromPerson;
            this.totalDistance = totalDistance;
        }
    }
}
