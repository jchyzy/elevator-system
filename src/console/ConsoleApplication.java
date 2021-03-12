package console;

import model.ElevatorSystem;

import java.util.Scanner;

public class ConsoleApplication {

    private ElevatorSystem system;
    private CommandParser parser;

    private static final int MAX_NUMBER_OF_FLOORS = 100;

    public static void main(String[] args) {

        ConsoleApplication application = new ConsoleApplication();

        Scanner scanner = new Scanner(System.in);
        while (!application.askForConfig(scanner)) {
            System.out.println("Please enter correct configuration");
        }

        // TODO: Add help message
        application.startApplicationLoop(scanner);
    }

    public boolean askForConfig(Scanner scanner) {
        int elevatorsNumber;
        int floorsNumber;

        try {
            System.out.print("Number of elevators: ");
            String elevatorsNumberString = scanner.nextLine();
            elevatorsNumber = Integer.parseInt(elevatorsNumberString);
            if (elevatorsNumber <= 0 || elevatorsNumber > 16) {
                System.out.println("Wrong parameter.");
                return false;
            }

            System.out.print("Number of floors: ");
            String floorsNumberString = scanner.nextLine();

            floorsNumber = Integer.parseInt(floorsNumberString);
            if (floorsNumber <= 0 || floorsNumber > MAX_NUMBER_OF_FLOORS) {
                System.out.println("Wrong parameter.");
                return false;
            }
        } catch (NumberFormatException e) {
            System.out.println("Wrong format.");
            return false;
        }

        system = new ElevatorSystem(elevatorsNumber, floorsNumber);
        parser = new CommandParser(system);

        return true;
    }

    public void startApplicationLoop(Scanner scanner) {
        while (true) {
            String line = scanner.nextLine();

            if (line.equals("")) {
                continue;
            }
            boolean executionSucceed = parser.executeCommandFromLine(line);

            if (!executionSucceed) {
                System.out.println("Cannot parse command. Type 'help' to see available commands.");
            }
        }
    }
}
