
import java.time.Month;
import java.time.Year;
import java.util.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileReader;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Scanner;

/**
 * Today är hamburgarmenyn
 */
public class Planner {
    private LocalDate today;

    private Map<Year, Map<Month, Calendar>> bigCalendar;

    private Calendar calendar;

    private String name = "Planner";
    private static State state;

    private ArrayList<Page> pages = new ArrayList<>();

    private static Scanner scanner = new Scanner(System.in);

    /**
     * läser spelarens input och returnerar det som en sträng
     *
     * @return returnernar en sträng av läsarens input som går till parser
     */

    private static String getCommand() {
        // Read a line of user input from the terminal
        String command = scanner.nextLine();
        // Remove the prompt from the start of the line
        return command;
    }
    public void createPagesFromFile(String filename){
        try {
            BufferedReader file = new BufferedReader(new FileReader(filename));
            // Try to read the first line of the file
            String line = file.readLine();

            // Keep reading while there are lines left

            while (line != null) {

                /**
                 * Läser en rad och skapar en array där varje element är en bit i rummet
                 */
                String[] description = line.split(";");
                // om lika med Room såå skapa ny rum och lägg i listan
                if (description[0].equals("page")) {
                    String name = description[1];
                    Page newPage = new Page(name);

                    pages.add(newPage);
                    // getRooms().add(room);

                } else if (description[0].trim().equals("exit")) {

                    // namnet på rummet jag är i
                    String currentPage = description[1].trim();
                    String nextPage = description[2].trim();
                    Page currentP = findPageNyName(currentPage, pages);
                    Page nextP = findPageNyName(nextPage, pages);

                    if (currentP != null && nextP != null) {
                        currentP.addExit(nextPage, nextP);
                    }

                }
                line = file.readLine();

            }

            // Don't forget to close the file!
            file.close();
            Page start = pages.get(0);
            // starten är första rummet
            state = new State(start);

            // Set the starting room

        } catch (IOException e) {

            System.out.println("Something went wrong: " + e.getMessage());
            // Exit the program
            System.exit(1);
        }


    }

    private Page findPageNyName(String name, ArrayList<Page> pages) {
        for (Page page : pages) {
            if (name.equals(page.getName())) {
                return page;
            }
        }
        return null;
    }



    private String name(){
        return name;
    }



    public static void main(String[] args) {
        createPages();
        createCalendar();

        CommandParser parser = new CommandParser();

        System.out.print(calendar.print());

        while (true) {
            String command = getCommand();
            parser.parse(command, state);

        }
    }





}
