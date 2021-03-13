package model;

import java.util.*;

public class Elevator {

    private final int id;
    private int currentFloor;
    private TreeSet<Integer> destinationFloors;
    private ElevatorState state;

    public Elevator(int id) {
        this.id = id;
        state = ElevatorState.WAITING;
        destinationFloors = new TreeSet<>();
    }

    public void setDestinationFloorAndActivate(int destinationFloor) {
        state = ElevatorState.ACTIVE;
        destinationFloors.add(destinationFloor);
    }

    public void performStep() {
        if (state == ElevatorState.WAITING || destinationFloors.isEmpty()) {
            return;
        }

        int destinationFloor = getDestinationFloor();

        if (currentFloor < destinationFloor) {
            currentFloor += 1;
        } else if (currentFloor > destinationFloor) {
            currentFloor -= 1;
        }

        if (currentFloor == destinationFloor){
            destinationFloors.remove(currentFloor);

            if (destinationFloors.isEmpty()) {
                state = ElevatorState.WAITING;
            }
        }
    }

    public void performStep(int steps) {
        for (int i = 0; i < steps; i++) {
            performStep();
        }
    }

    @Override
    public String toString() {
        return "Elevator{" +
                "id=" + id +
                ", currentFloor=" + currentFloor +
                ", destinationFloors=" + Arrays.toString(destinationFloors.toArray()) +
                ", state=" + state +
                '}';
    }

    public ElevatorState getState() {
        return state;
    }

    public void setState(ElevatorState state) {
        this.state = state;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public void setCurrentFloor(int currentFloor) {
        this.currentFloor = currentFloor;
    }

    public int getDestinationFloor() {
        return destinationFloors.first(); // may be null?
    }

}
