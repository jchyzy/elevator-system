package model;

import java.util.*;

public class Elevator {

    private final int id;
    private int currentFloor;
    private ElevatorState state;
    private LinkedHashSet<Integer> destinationsPickup;
    private LinkedHashSet<Integer> destinationsUpdate;

    public Elevator(int id) {
        this.id = id;
        state = ElevatorState.WAITING;
        destinationsPickup = new LinkedHashSet<>();
        destinationsUpdate = new LinkedHashSet<>();
    }

    public void setDestinationFloorAndActivatePickup(int destinationFloor) {
        state = ElevatorState.ACTIVE;
        destinationsPickup.add(destinationFloor);
    }

    public void setDestinationFloorAndActivateUpdate(int destinationFloor) {
        state = ElevatorState.ACTIVE;
        destinationsUpdate.add(destinationFloor);
    }

    public void performStep() {
        if (state == ElevatorState.WAITING || (destinationsUpdate.isEmpty() && destinationsPickup.isEmpty())) {
            return;
        }

        int destinationFloor = getDestinationFloor();

        if (currentFloor < destinationFloor) {
            currentFloor += 1;
        } else if (currentFloor > destinationFloor) {
            currentFloor -= 1;
        }

        if (currentFloor == destinationFloor) {
            destinationsUpdate.remove(currentFloor);
            destinationsPickup.remove(currentFloor);

            if (destinationsUpdate.isEmpty() && destinationsPickup.isEmpty()) {
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
        return "Elevator {" +
                "id=" + id +
                ", currentFloor=" + currentFloor +
                ", destinationFloors: update=" + Arrays.toString(destinationsUpdate.toArray()) + " pickup=" + Arrays.toString(destinationsPickup.toArray()) +
                ", state=" + state +
                " }";
    }

    public ElevatorState getState() {
        return state;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public int getDestinationFloor() {
        if (!destinationsUpdate.isEmpty()) {
            return destinationsUpdate.iterator().next();
        } else if (!destinationsPickup.isEmpty()) {
            return destinationsPickup.iterator().next();
        } else {
            return currentFloor;
        }
    }

    public int numberOfDestinationFloors() {
        return destinationsUpdate.size() + destinationsPickup.size();
    }

}
