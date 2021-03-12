package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ElevatorSystem {

    private final int elevatorsNumber;
    private final int floorsNumber; // from 0 to floorsNumber (inclusive)

    private List<Elevator> elevators;

    public ElevatorSystem(int elevatorsNumber, int floorsNumber) {
        this.elevatorsNumber = elevatorsNumber;
        this.floorsNumber = floorsNumber;
        createElevators();
    }

    private void createElevators() {
        elevators = new ArrayList<>();

        for (int i = 0; i < elevatorsNumber; i++) {
            elevators.add(new Elevator(i));
        }
    }

    private Optional<Elevator> chooseElevatorToPick() { // TODO: improve method
        for (Elevator elevator: elevators) {
            if (elevator.getState() == ElevatorState.WAITING) {
                return Optional.of(elevator);
            }
        }
        return Optional.empty();
    }


    public void pickup(int floor, Direction direction) { // TODO: add direction usage
        if (floor >= 0 && floor <= floorsNumber) { // TODO: message if wrong parameters
            Optional<Elevator> availableElevator = chooseElevatorToPick();
            availableElevator.ifPresent(elevator -> elevator.setDestinationFloorAndActivate(floor));
        }
    }

    public void update(int elevatorId, int destinationFloor) { // current floor cannot be updated
        Elevator elevator = elevators.get(elevatorId);
        elevator.setDestinationFloorAndActivate(destinationFloor);
    }

    public void step() {
        elevators.forEach(Elevator::performStep);
    }

    public void status() {
        elevators.forEach(System.out::println);
    }
}
