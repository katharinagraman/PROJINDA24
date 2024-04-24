/**
 * Translates commands so user can navigate across application
 */

public class CommandParser {


    /**
     * Takes the state of the current planner and executes commands accordingly
     * @param command
     * @param state
     */
    public void parse(String command, State state){
        String[] splitCommand = command.trim().toLowerCase().split("\\s+");
        if (splitCommand.length < 1) {
            printInvalidMessage();
            return;
        }
        // Attempt to match the player input to a command and execute it
        switch (splitCommand[0]) {
            case "go" -> executeGoCommand(splitCommand, state);
            default -> printInvalidCommandMessage();
        }
    }

    /**
     * Users way of navigating on text based
     * @param splitCommand
     * @param state
     */
    private static void executeGoCommand(String[] splitCommand, State state) {

        if (splitCommand.length < 2) {
            printMissingModifierMessage("go");
        } else {

            Page current = state.getCurrentPage();
            Page nextPage = current.goToPage(splitCommand[1]);

            if(current)
        }
    }

    /**
     * Lacks correct modifier
     * @param command
     */
    private static void printMissingModifierMessage(String command) {
        System.out.println("I'm sorry, the \"" + command + "\" command requires one more command word. " +
                "Type \"help\" for information about commands.");
    }

    private static void printInvalidMessage() {
        System.out.println("I'm sorry, that's not a valid command. " +
                "Type \"help\" for information about commands.");
    }
}
