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
    private DayDesign dailyCalender = new DayDesign();
    private ZonedDateTime today = ZonedDateTime.now();
    private int todayDate = ZonedDateTime.now().getDayOfMonth();
    private int month = ZonedDateTime.now().getMonthValue();
    private DataTemplate data;
    private JPanel mainPanel, centerPanel;

    private HashMap<LocalTime,DailyEvent> tasksThisDay = new HashMap<>();

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
        LocalTime time = startTime;
        tasksThisDay.put(time, event);
        updateDisplay(); // Method to update the display with the new task
    }

    // Method to update the display with the tasks
    // behöver en metof som kan sortera så blir det lättare när jag printar
    private void updateDisplay() {
        // remvoes current
        centerPanel.removeAll();
        //sort(tasksThisDay);

        // Set layout manager to BoxLayout.Y_AXIS
        centerPanel.setLayout(new BorderLayout());
        dailyCalender.drawEventsOnTimeTable(tasksThisDay, dailyCalender.getGraphics());
        centerPanel.add(dailyCalender, BorderLayout.WEST);

        // Repaint the panel to reflect the changes
        centerPanel.revalidate();
        centerPanel.repaint();
    }

    // constructor
    public Day(LocalDate date) {
        super("Day: " + date);

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

        // Create a smaller panel for the content in the center
        centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(Color.WHITE); // Set background color for the center panel
        centerPanel.setPreferredSize(new Dimension(400, 300)); // Set preferred size for the center panel

        JScrollPane scrollPane = new JScrollPane(dailyCalender);
        centerPanel.add(scrollPane, BorderLayout.WEST);


        // Add components to main panel in appropriate positions
        mainPanel.add(dayLabel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.EAST);
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // Set content pane of the frame to main panel
        getContentPane().add(mainPanel);

        // Set frame size and make it visible
        setSize(1920, 1080);  // Set a reasonable size for the frame
        setBackground(Color.LIGHT_GRAY);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);  // Set default close operation
        setVisible(true);  // Make the frame visible



//        //-------- Panels according to hour ------ //
//        JPanel[] hourPanels = new JPanel[24];
//        for(int i = 0; i<24; i++){
//            if(i < 10){
//                JLabel hourLabel = new JLabel("0"+ i);
//            }
//            JLabel hourLabel = new JLabel(String.valueOf(i));
//            JPanel hourPanel = new JPanel();
//            hourPanel.add(hourLabel);
//            hourPanel.setSize(480,480);
//            hourPanels[i] = hourPanel;
//            centerPanel.add(hourPanel);
//            centerPanel.add(Box.createVerticalStrut(10));
//
//        }
//
// --------- Panels according to minute -------------//
//        JPanel[] minutesOfHours= new JPanel[12];
//        for(int i = 0; i<12; i++){
//            JPanel hourPanel = new JPanel();
//            hourPanel.setSize(480,480);
//            hourPanels[i] = hourPanel;
//        }
        // ---- scroll pane ---//

        addButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                // Open a new frame or perform an action when a day is clicked
                openCreateEventFrame(date, tasksThisDay);
            }
        });





    }

    /**
     * Opens the Window which handles eventmaking
     * @param date
     * @param eventsThisDay
     */
    public void openCreateEventFrame(LocalDate date, HashMap<LocalTime, DailyEvent> eventsThisDay){
        JFrame eventFrame = new EventGUI(this);


    }

    public void sort(){
        HashMap<LocalTime, DailyEvent> sortedMap = new HashMap<>();
        Set<LocalTime> keys = tasksThisDay.keySet();
        LocalTime[] times = new LocalTime[tasksThisDay.size()];
        for(int i = 0; i<tasksThisDay.size(); i++){
            for (LocalTime key : tasksThisDay.keySet()) {
                times[i] = key;
                i++;
            }
        }

        int n = times.length;
        for (int i = 1; i < n; ++i) {
            LocalTime key = times[i];
            int j = i - 1;

            /* Move elements of arr[0..i-1], that are
               greater than key, to one position ahead
               of their current position */
            while (j >= 0 && (times[j].compareTo(key) > 0)) {
                times[j + 1] = times[j];
                j = j - 1;
            }
            times[j + 1] = key;
        }
        for(int i = 0; i<tasksThisDay.size();i++){
            sortedMap.put(times[i], tasksThisDay.get(times[i]));
            System.out.println(sortedMap.entrySet());
        }
        tasksThisDay = sortedMap;
    }

    public void createSortedEventBackground(){
        JPanel mainDesignPanel = new JPanel();
        data = new DataTemplate(tasksThisDay);
        data.sort();
    }

    public void createDesignForDefaultBackground(){
        JPanel mainDesignPanel = new JPanel();

    }

    /**
     * Får typ skapa fält som representerar en tid
     * @param a
     */
    public void addDailyEvent(HashMap<LocalTime, DailyEvent> a){
        GridBagConstraints gbc = new GridBagConstraints();
        // egentligen kan ju bara säga att time y = timmen, så tex 13:00 motsvarar att timey = 13:15!!
        int timex = 0;
        int timey = 0;
        int eventx = 1;
        int eventy = 0;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH.mm");
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets(5, 10, 5, 10);

        for (LocalTime key : a.keySet()) {
            gbc.gridx = timex;
            gbc.gridy = timey;
            JLabel timeLabel = new JLabel(key.format(formatter));

            mainPanel.add(timeLabel, gbc);
            gbc.gridx = eventx;
            JLabel event = new JLabel(a.get(key).toString(), 20);
            mainPanel.add(event, gbc);
            timey++;
            gbc.gridx = 0;
            gbc.gridy = timey;

        }

    }

    public String[] arrayForHamburger(){
        String[] a = new String[tasksThisDay.size()];
        for(int i= 0; i<a.length;i++){
            for (LocalTime key : tasksThisDay.keySet()) {
                a[i] = key+ " " + tasksThisDay.get(key).toString();
                i++;
            }

        }

        return a;
    }

    public HashMap<LocalTime, DailyEvent> getTasksThisDay(){
        return tasksThisDay;
    }

    public JPanel getCenterPanel(){
        return  centerPanel;
    }


    public String getDate(){
        return "";

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
