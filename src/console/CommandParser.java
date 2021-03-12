package console;

import model.Direction;
import model.ElevatorSystem;

public class CommandParser {

    private final ElevatorSystem system;

    public CommandParser(ElevatorSystem system) {
        this.system = system;
    }

    public boolean executeCommandFromLine(String line) {
        String[] commandElements = line.split("\\s+");
        String commandName = commandElements[0];

        return switch (commandName) {
            case "pickup" -> pickupCommand(commandElements);
            case "update" -> updateCommand(commandElements);
            case "step" -> stepCommand(commandElements);
            case "status" -> statusCommand(commandElements);
            case "help" -> helpCommand(commandElements);
            case "exit" -> exitCommand(commandElements);
            default -> false;
        };
    }

    boolean pickupCommand(String[] commandElements) {
        if (commandElements.length != 3) {
            return false;
        }

        try {
            int floor = Integer.parseInt(commandElements[1]);
            String direction = commandElements[2];

            switch (direction) {
                case "up" -> system.pickup(floor, Direction.UP);
                case "down" -> system.pickup(floor, Direction.DOWN);
                default -> { return false; }
            }
        } catch (NumberFormatException e) {
            return false;
        }

        return true;
    }

    private boolean updateCommand(String[] commandElements) {
        if (commandElements.length != 3) {
            return false;
        }
        try {
            int elevatorId = Integer.parseInt(commandElements[1]);
            int destinationFloor = Integer.parseInt(commandElements[2]);

            system.update(elevatorId, destinationFloor);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    private boolean stepCommand(String[] commandElements) {
        if (commandElements.length != 1) {
            return false;
        }

        system.step();
        return true;
    }

    private boolean statusCommand(String[] commandElements) {
        if (commandElements.length != 1) {
            return false;
        }

        system.status();
        return true;
    }

    private boolean helpCommand(String[] commandElements) {
        if (commandElements.length != 1) {
            return false;
        }

        System.out.println("Help"); // TODO: Help message
        return true;
    }

    private boolean exitCommand(String[] commandElements) {
        if (commandElements.length != 1) {
            return false;
        }

        System.exit(0);
        return true;
    }

}
