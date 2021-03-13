package model;

import java.util.*;

public class ElevatorSystem {

    private final int elevatorsNumber;
    private final int floorsNumber; // from 0 to floorsNumber (inclusive)

    private List<Elevator> elevators;
    private final ElevatorPicker elevatorPicker;

    public ElevatorSystem(int elevatorsNumber, int floorsNumber) {
        this.elevatorsNumber = elevatorsNumber;
        this.floorsNumber = floorsNumber;
        createElevators();
        elevatorPicker = new ElevatorPicker(elevators, floorsNumber);
    }

    private void createElevators() {
        elevators = new ArrayList<>();

        for (int i = 0; i < elevatorsNumber; i++) {
            elevators.add(new Elevator(i));
        }
    }

    public void pickup(int floor, Direction direction) { // TODO: add direction usage
        if (floor >= 0 && floor <= floorsNumber) { // TODO: message if wrong parameters
            Elevator chosenElevator = elevatorPicker.chooseElevatorToPick(floor, direction);
            chosenElevator.setDestinationFloorAndActivate(floor);
        }
    }

    public void update(int elevatorId, int destinationFloor) { // current floor cannot be updated
        Elevator elevator = elevators.get(elevatorId);
        elevator.setDestinationFloorAndActivate(destinationFloor);
    }

    public void step() {
        elevators.forEach(Elevator::performStep);
    }

    public void step(int steps) {
        if (steps > 0) {
            elevators.forEach(elevator -> elevator.performStep(steps));
        }
    }

    public void status() {
        elevators.forEach(System.out::println);
    }

    public List<Elevator> getElevators() {
        return elevators;
    }

    public int getElevatorsNumber() {
        return elevatorsNumber;
    }

    public int getFloorsNumber() {
        return floorsNumber;
    }

}
