package model;

public class Elevator {

    private final int id;
    private int currentFloor;
    private int destinationFloor;
    private ElevatorState state;

    public Elevator(int id) {
        this.id = id;
        state = ElevatorState.WAITING;
    }

    public void setDestinationFloorAndActivate(int destinationFloor) {

        if (state != ElevatorState.ACTIVE) { // if ACTIVE do nothing
            state = ElevatorState.ACTIVE;
            this.destinationFloor = destinationFloor;
        }
    }

    public void performStep() {
        if (state == ElevatorState.ACTIVE) {
            if (currentFloor < destinationFloor) {
                currentFloor += 1;
            } else if (currentFloor > destinationFloor) {
                currentFloor -= 1;
            }

            if (currentFloor == destinationFloor){
                state = ElevatorState.WAITING;
            }
        }
    }

    @Override
    public String toString() {
        return "Elevator{" +
                "id=" + id +
                ", currentFloor=" + currentFloor +
                ", destinationFloor=" + destinationFloor +
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
        return destinationFloor;
    }

}
