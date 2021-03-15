package model;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class ElevatorPicker {

    private List<Elevator> elevators;

    public ElevatorPicker(List<Elevator> elevators) {
        this.elevators = elevators;
    }

    public Optional<Elevator> chooseElevatorToPick(int floor, Direction direction) {
        if (elevatorThatRequiresNoActionExist(floor)) {
            return Optional.empty();
        }
        return chooseElevatorPickingOnTheWay(floor, direction)
                .or(this::chooseElevatorLeastBusy);
    }

    private boolean elevatorThatRequiresNoActionExist(int floor) {
        Predicate<Elevator> elevatorOnDesiredFloor = elevator -> (elevator.getCurrentFloor() == floor);
        Predicate<Elevator> elevatorMovingToDesiredFloor =
                elevator -> (elevator.getState() == ElevatorState.ACTIVE && elevator.getDestinationFloor() == floor);

        return elevators.stream()
                .anyMatch(elevatorOnDesiredFloor.or(elevatorMovingToDesiredFloor));
    }

    private Optional<Elevator> chooseElevatorPickingOnTheWay(int floor, Direction direction) {
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

    private Optional<Elevator> chooseElevatorLeastBusy() {
        return elevators.stream()
                .min(Comparator.comparingInt(Elevator::numberOfDestinationFloors));
    }
}
