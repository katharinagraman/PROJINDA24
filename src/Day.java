import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Day extends JFrame implements EventListener {
    /**
     * Every day panel will have a Day Frame
     * This is also an object which stores tasks
     */
    private DayDesign dailyCalender = new DayDesign(this);
    private DeleteEventGUI deleteEventGUI = new DeleteEventGUI(this);
    private ZonedDateTime today = ZonedDateTime.now();
    private int todayDate = ZonedDateTime.now().getDayOfMonth();
    private int month = ZonedDateTime.now().getMonthValue();

    private JPanel mainPanel, centerPanel;

    private ArrayList<DailyEvent> dailyEvents = new ArrayList<>();

    /**
     * This method is used from the interface in order to communicate with EventGUI window
     * @param title
     * @param type
     * @param startTime
     * @param endTime
     * @param description
     */
    @Override
    public void onEventAdded(String title, String type, LocalTime startTime, LocalTime endTime, String description) {
        // Create a new DailyEvent instance with the received data
        DailyEvent event = new DailyEvent(title, type, startTime, endTime, description);
        dailyEvents.add(event);
        deleteEventGUI.setTasks(dailyEvents);

        updateDisplay(); // Method to update the display with the new task
    }

    // Method to update the display with the tasks
    // behöver en metof som kan sortera så blir det lättare när jag printar
    private void updateDisplay() {
        // remvoes current
        centerPanel.removeAll();
        // DaydDesign newDaily = new DayDesing(this)
        // newDaily.setEvents = tasksThisDay.
        //JScrollPane scrollPane = new JScrollPane(dailyCalender);
        // centerPanel.add(scrollPane, BorderLayout.WEST);
        //
        JScrollPane scrollPane = new JScrollPane(dailyCalender);
        centerPanel.add(scrollPane, BorderLayout.WEST);

        // Repaint the panel to reflect the changes
        centerPanel.revalidate();
        centerPanel.repaint();
    }

    // constructor
    public Day(LocalDate date) {
        super("Day: " + date);
        deleteEventGUI.setVisible(false);

        // Create main panel with BorderLayout and set its background color to black
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.LIGHT_GRAY);

        // Create label for day events and set its font
        JLabel dayLabel = new JLabel("Events for " + date);
        dayLabel.setFont(new Font("Georgia", Font.BOLD, 32));

        // Create button for adding events
        JButton addButton = new JButton("+");
        addButton.setFont(new Font("Georgia", Font.BOLD, 20));

        // Create panel for button and set its background color
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setSize(50,50);
        buttonPanel.setBackground(Color.LIGHT_GRAY);
        buttonPanel.add(addButton);

        // remove button
        JButton removeButton = new JButton("-");
        removeButton.setFont(new Font("Georgia", Font.BOLD, 20));
        removeButton.addActionListener(e->openRemoveFrame(this));

        // Create panel for button and set its background color
        JPanel removeButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        removeButtonPanel.setSize(50,50);
        removeButtonPanel.setBackground(Color.LIGHT_GRAY);
        removeButtonPanel.add(removeButton);

        // Create a smaller panel for the content in the center
        centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(Color.WHITE); // Set background color for the center panel
        centerPanel.setPreferredSize(new Dimension(400, 300)); // Set preferred size for the center panel

        JScrollPane scrollPane = new JScrollPane(dailyCalender);
        centerPanel.add(scrollPane, BorderLayout.WEST);



        // Add components to main panel in appropriate positions
        mainPanel.add(dayLabel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.EAST);
        mainPanel.add(removeButtonPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // Set content pane of the frame to main panel
        getContentPane().add(mainPanel);

        // Set frame size and make it visible
        setSize(1920, 1080);  // Set a reasonable size for the frame
        setBackground(Color.LIGHT_GRAY);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);  // Set default close operation
        setVisible(true);  // Make the frame visible



        addButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                // Open a new frame or perform an action when a day is clicked
                openCreateEventFrame();
            }
        });



    }

    /**
     * Opens the Window which handles eventmaking
     */
    public void openCreateEventFrame(){
        JFrame eventFrame = new EventGUI(this, dailyCalender);


    }

    public void openRemoveFrame(Day day){
        DeleteEventGUI deleteFrame = new DeleteEventGUI(this);
        deleteFrame.setTasks(dailyEvents);


    }

    /**
     * Lägg till denna metod i delte gui oxlså
     */
    public void sort(){
        System.out.println("sorts arrayList after LocalTime");

    }






    public String[] arrayForHamburger(){
        String[] a = new String[dailyEvents.size()];
        for(int i= 0; i<a.length;i++){
            a[i] = dailyEvents.get(i).toString();
        }
        return a;
    }

    public ArrayList<DailyEvent> getDailyEvents(){
        return dailyEvents;
    }

    public JPanel getCenterPanel(){
        return  centerPanel;
    }



    public void setDate(LocalDate date){
        System.out.println("skapa variabler som kommer ihåg vilken dag och månad det är som strängar och objekt som attribut");
    }


    public static void main(String[] args) {
        // Example usage: create a new Day frame
        LocalDate currentDate = LocalDate.now();
        SwingUtilities.invokeLater(() -> new Day(currentDate));
    }
}
