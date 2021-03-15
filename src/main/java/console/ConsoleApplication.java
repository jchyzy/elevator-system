package console;

import model.ElevatorSystem;
import org.thymeleaf.util.StringUtils;

import java.util.Scanner;

public class ConsoleApplication {

    private ElevatorSystem system;
    private CommandParser parser;

    public static void main(String[] args) {

        ConsoleApplication application = new ConsoleApplication();
        Scanner scanner = new Scanner(System.in);

        application.showInitMessage();
        application.askForConfigUntilCorrect(scanner);
        application.showHelpMessage();
        application.startApplicationLoop(scanner);
    }

    public void askForConfigUntilCorrect(Scanner scanner) {
        while (!askForConfig(scanner)) {
            System.out.println("Please enter correct configuration");
        }
    }

    public boolean askForConfig(Scanner scanner) {
        int elevatorsNumber;
        int floorsNumber;

        try {
            System.out.print("Number of elevators: ");
            String elevatorsNumberString = scanner.nextLine().trim();
            elevatorsNumber = Integer.parseInt(elevatorsNumberString);
            if (elevatorsNumber <= 0 || elevatorsNumber > ElevatorSystem.MAX_NUMBER_OF_ELEVATORS) {
                System.out.println("Wrong parameter.");
                return false;
            }

            System.out.print("Number of floors: ");
            String floorsNumberString = scanner.nextLine().trim();
            floorsNumber = Integer.parseInt(floorsNumberString);
            if (floorsNumber <= 0 || floorsNumber > ElevatorSystem.MAX_NUMBER_OF_FLOORS) {
                System.out.println("Wrong parameter.");
                return false;
            }
        } catch (NumberFormatException e) {
            System.out.println("Wrong format.");
            return false;
        }

        system = new ElevatorSystem(elevatorsNumber, floorsNumber);
        parser = new CommandParser(this, system);

        return true;
    }

    public void startApplicationLoop(Scanner scanner) {
        while (true) {
            String line = scanner.nextLine();

            if (line.equals("")) {
                continue;
            }
            boolean executionSucceeded = parser.executeCommandFromLine(line.trim());

            if (!executionSucceeded) {
                System.out.println("Wrong command or parameters. Type 'help' to see available commands.");
            }
        }
    }

    public void showInitMessage() {
        String initMessage = "Welcome to Elevator System! " +
                "Please enter desired configuration";

        System.out.println(initMessage);
    }

    public void showHelpMessage() {
        String helpMessage = StringUtils.repeat("-", 40) + "\n" +
                "ELEVATOR SYSTEM - available commands:\n" +
                "* pickup <floor> <direction>\n" +
                "* update <elevatorId> <destinationFloor>\n" +
                "* step [<stepsNumber>]\n" +
                "* status\n" +
                "* help\n" +
                "* exit\n" +
                StringUtils.repeat("-", 40);

        System.out.println(helpMessage);
    }
}
