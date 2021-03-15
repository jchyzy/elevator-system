package model;

import java.util.*;

public class ElevatorSystem {

    private final int elevatorsNumber;
    private final int floorsNumber; // from 0 to floorsNumber (inclusive)

    private List<Elevator> elevators;
    private final ElevatorPicker elevatorPicker;

    public static final int MAX_NUMBER_OF_FLOORS = 100;
    public static final int MAX_NUMBER_OF_ELEVATORS = 16;


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

    public boolean pickup(int floor, Direction direction) {
        if (floor >= 0 && floor <= floorsNumber) {
            if ((floor == 0 && direction == Direction.DOWN) || (floor == floorsNumber && direction == Direction.UP)) {
                return false;
            }

            Optional<Elevator> chosenElevator = elevatorPicker.chooseElevatorToPick(floor, direction);
            chosenElevator.ifPresent(elevator -> elevator.setDestinationFloorAndActivatePickup(floor));
            return true;
        }
        return false;
    }

    public boolean update(int elevatorId, int destinationFloor) { // current floor cannot be updated
        if (elevators.contains(elevatorId) && destinationFloor >= 0 && destinationFloor <= floorsNumber) {
            Elevator elevator = elevators.get(elevatorId);
            elevator.setDestinationFloorAndActivateUpdate(destinationFloor);
            return true;
        }
        return false;
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
