package model;

import java.util.*;

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

    private Elevator chooseElevatorToPick(int floor, Direction direction) {

        Optional<Elevator> firstTry = elevators.stream()
                .filter(elevator -> (elevator.getCurrentFloor() == floor))
                .findFirst();

        if (firstTry.isPresent()) {
            return firstTry.get();
        }

        Optional<Elevator> secondTry;
        if (direction == Direction.DOWN) {
            secondTry = elevators.stream()
                    .filter(elevator -> (elevator.getState() == ElevatorState.ACTIVE))
                    .filter(elevator -> (elevator.getCurrentFloor() > floor))
                    .filter(elevator -> (elevator.getDestinationFloor() <= floor))
                    .findFirst();
        }

        else {
            secondTry = elevators.stream()
                    .filter(elevator -> (elevator.getState() == ElevatorState.ACTIVE))
                    .filter(elevator -> (elevator.getCurrentFloor() < floor))
                    .filter(elevator -> (elevator.getDestinationFloor() >= floor))
                    .findFirst();
        }

        if (secondTry.isPresent()) {
            return secondTry.get();
        }

        Optional<Elevator> thirdTry = elevators.stream()
                .filter(elevator -> (elevator.getState() == ElevatorState.WAITING))
                .min(Comparator.comparingInt(elevator -> Math.abs(elevator.getCurrentFloor() - floor)));

        if (thirdTry.isPresent()) {
            return thirdTry.get();
        }

        Random random = new Random();
        int randomElevatorID = random.nextInt(floorsNumber + 1);
        return elevators.get(randomElevatorID);
    }


    public void pickup(int floor, Direction direction) { // TODO: add direction usage
        if (floor >= 0 && floor <= floorsNumber) { // TODO: message if wrong parameters
            Elevator chosenElevator = chooseElevatorToPick(floor, direction);
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

    public void status() {
        elevators.forEach(System.out::println);
    }
}
