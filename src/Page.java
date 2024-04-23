import java.time.LocalTime;
import java.util.*;

/**
 * Om göra mer modulär låt inherit denna där t.ex en dag har vissa fält medan planner har andra
 *
 */
public class Page {
    private Map<Task, LocalTime> tasks = new HashMap<>(); 
    // Map<Event, Date> events = new
    private String name;
    private Scanner scanner = new Scanner(System.in);

    /**
     * maps an
     */
    private Map<String, Page> exits = new HashMap<>();

    public Page(String name){
        this.name = name;
    }


    public void addExit(String name, Page nextPage){
        exits.put(name, nextPage);
    }



    private void addName(String name){
        this.name = name;

    }

    public String getName(){
        return this.name;
    }


    /**
     * Ha en textfil med alla pages men inte nu hahah
     * @param next is the next possible page to go to
     * @return
     */
    private Page goToPage(String nextPage){
    Page next = exits.get(nextPage);

    if (next == null) {
        System.out.println("You can't go that way!");
        printExits();
    }
    return next;

    }






    // Måste använda scanner typ som läser in vad användaren vill ha typ
    // Använd spelet som inspo typ CalendarState
    // Ska skapa en task
    private void makeTask(){
        System.out.println("Name");
        String name = scanner.nextLine();
        System.out.println("Description");
        String description = scanner.nextLine();
        Task newTask = new Task(name, description);
        System.out.println("What time would you like to complete the task? (HH:MM)");
        String time = scanner.nextLine();
         // se till följer formalia
        String[] timeA = new String[2];
        timeA = time.split(",");


        // Adds tasks with name description and date. date skulle kunna vara attribut i task ocskå
        tasks.put(newTask, LocalTime.of(Integer.parseInt(timeA[0]), Integer.parseInt(timeA[1])));

        }
    

        

        


        

    }

}
