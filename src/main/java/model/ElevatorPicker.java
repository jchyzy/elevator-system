package model;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.function.Predicate;

public class ElevatorPicker {

    private List<Elevator> elevators;
    private int floorsNumber;

    public ElevatorPicker(List<Elevator> elevators, int floorsNumber) {
        this.elevators = elevators;
        this.floorsNumber = floorsNumber;
    }

    public Elevator chooseElevatorToPick(int floor, Direction direction) {
        return chooseElevatorFirstTry(floor)
                .or(() -> chooseElevatorSecondTry(floor, direction))
                .or(() -> chooseElevatorThirdTry(floor, direction))
                .orElseGet(this::getRandomElevator);
    }

    private Optional<Elevator> chooseElevatorFirstTry(int floor) {
        Predicate<Elevator> elevatorOnDesiredFloor = elevator -> (elevator.getCurrentFloor() == floor);
        Predicate<Elevator> elevatorMovingToDesiredFloor =
                elevator -> (elevator.getState() == ElevatorState.ACTIVE && elevator.getDestinationFloor() == floor);

        return elevators.stream()
                .filter(elevatorOnDesiredFloor.or(elevatorMovingToDesiredFloor))
                .findFirst();
    }

    private Optional<Elevator> chooseElevatorSecondTry(int floor, Direction direction) {
        if (direction == Direction.DOWN) {
            return elevators.stream()
                    .filter(elevator -> (elevator.getState() == ElevatorState.ACTIVE))
                    .filter(elevator -> (elevator.getCurrentFloor() > floor))
                    .filter(elevator -> (elevator.getDestinationFloor() <= floor))
                    .findFirst();
        } else {
            return elevators.stream()
                    .filter(elevator -> (elevator.getState() == ElevatorState.ACTIVE))
                    .filter(elevator -> (elevator.getCurrentFloor() < floor))
                    .filter(elevator -> (elevator.getDestinationFloor() >= floor))
                    .findFirst();
        }
    }

    private Optional<Elevator> chooseElevatorThirdTry(int floor, Direction direction) {
        return elevators.stream()
                .filter(elevator -> (elevator.getState() == ElevatorState.WAITING))
                .min(Comparator.comparingInt(elevator -> Math.abs(elevator.getCurrentFloor() - floor)));
    }

    private Elevator getRandomElevator() {
        Random random = new Random();
        int randomElevatorID = random.nextInt(floorsNumber + 1);
        return elevators.get(randomElevatorID);
    }
}
