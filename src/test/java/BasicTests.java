import model.Direction;
import model.Elevator;
import model.ElevatorState;
import model.ElevatorSystem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class BasicTests {

    private final int elevatorsNumber = 5;
    private final int floorsNumber = 10;
    private final int[] floorsToTest = {1, 3, 5, 7, 10};
    private ElevatorSystem system;

    @BeforeEach
    private void initialize() {
        system = new ElevatorSystem(elevatorsNumber, floorsNumber);
    }

    @Test
    public void pickedElevatorWillEndOnDesiredFloorWithWaitingState() {
        Arrays.stream(floorsToTest)
                .forEach(this::pickedElevatorWillEndOnDesiredFloorWithWaitingState);
    }

    private void pickedElevatorWillEndOnDesiredFloorWithWaitingState(int floor) {
        system.pickup(floor, Direction.DOWN);
        system.step(elevatorsNumber);

        Optional<Elevator> elevatorsOnDesiredFloor = system.getElevators().stream()
                .filter(elevator -> elevator.getCurrentFloor() == floor)
                .findAny();

        assertTrue(elevatorsOnDesiredFloor.isPresent());
        assertSame(elevatorsOnDesiredFloor.get().getState(), ElevatorState.WAITING);
    }

    @Test
    public void updatedElevatorWillEndOnDesiredFloorWithWaitingState() {
        Arrays.stream(floorsToTest)
                .forEach(this::updatedElevatorWillEndOnDesiredFloorWithWaitingState);
    }

    private void updatedElevatorWillEndOnDesiredFloorWithWaitingState(int floor) {
        int id = 0;
        system.update(id, floor);
        system.step(floorsNumber);

        Elevator elevator = system.getElevators().get(id);

        assertEquals(floor, elevator.getCurrentFloor());
        assertSame(elevator.getState(), ElevatorState.WAITING);
    }
}
