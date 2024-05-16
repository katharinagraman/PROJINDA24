import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Day extends JFrame implements EventListener, EventListenerRemove {
    /**
     * Every day panel will have a Day Frame
     * This is also an object which stores tasks
     */
    private DayDesign dailyCalender = new DayDesign(this);
    private DeleteEventGUI deleteEventGUI = new DeleteEventGUI(this,dailyCalender);
    private ZonedDateTime today = ZonedDateTime.now();
    private int todayDate = ZonedDateTime.now().getDayOfMonth();
    private int month = ZonedDateTime.now().getMonthValue();

    private JPanel mainPanel, centerPanel, descriptionPanel;

    private ArrayList<DailyEvent> dailyEvents = new ArrayList<>(1);

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

    @Override
    public void removeEvent(int index){
        if(dailyEvents.isEmpty()){
            updateDisplay();
        }
        dailyEvents.remove(index);
        updateDisplay();
    }

    // Method to update the display with the tasks
    // behöver en metof som kan sortera så blir det lättare när jag printar
    private void updateDisplay() {
        // remvoes current
        centerPanel.removeAll();

        // dailyCalendar.setTasks(dailyEvents)
        JScrollPane scrollPane = new JScrollPane(dailyCalender);
        System.out.println("BREDD " + dailyCalender.getWidth());

        centerPanel.add(scrollPane, BorderLayout.WEST);

        // funkar med paneler kan flytta ritlogiken hit också men börja med osyblig panel

        drawEventsOnTimeTable(dailyEvents,dailyCalender);
        //JPanel event = new JPanel();
        //code
        //dailyCalender.add(event);

        //----



        // Repaint the panel to reflect the changes
        centerPanel.revalidate();
        centerPanel.repaint();
    }

    public void drawEventsOnTimeTable(ArrayList<DailyEvent> a, DayDesign dailyCalender){
        for(DailyEvent event: a){
            if(!event.isPainted()){
                event.setPaintedTrue();
                //LocalTime startOfDay = LocalTime.of(0,0); compareTo()
                // gör så att användaren kna flytta på en rektangel sen men bara tvärs över
                LocalTime startTime = event.getStartTime();
                LocalTime endTime = event.getEndTime();
                int y0Coordinate = startTime.getHour()  * dailyCalender.getIntervalHeight() * 2;
                int y1Coordinate = endTime.getHour()  * dailyCalender.getIntervalHeight() * 2;
                int heightOfTask;


                if((startTime.getMinute() % 30)!= 0){
                    y0Coordinate = dailyCalender.getStartCoordinate(startTime);
                }
                if((endTime.getMinute() % 30)!= 0){
                    y1Coordinate = dailyCalender.getEndCoordinate(endTime);
                }
                if (startTime.getMinute() == 30){
                    y0Coordinate = dailyCalender.getIntervalHeight() * ( 2 * startTime.getHour() + 1);
                }

                if (endTime.getMinute() == 30){
                    y1Coordinate = dailyCalender.getIntervalHeight() * (2 * endTime.getHour() + 1);
                }
                heightOfTask = y1Coordinate - y0Coordinate;

                // Set the opacity (alpha) of the color (less opaque)
                Color lessOpaqueColor = dailyCalender.desaturate(event.getColourOfEvent());
                //repaint(COLUMN_WIDTH,y0Coordinate,COLUMN_WIDTH,heightOfTask);

                JPanel block = new CalendarBlock();
                block.setBackground(lessOpaqueColor);
                block.setBounds(dailyCalender.getColumnWidth(), y0Coordinate,dailyCalender.getColumnWidth(),heightOfTask);
                block.addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseClicked(java.awt.event.MouseEvent evt) {
                        // Open a new frame or perform an action when a day is clicked
                        openDescription(event.getDescription());
                    }
                });
                JLabel titleOfBlock = new JLabel(event.toString());
                block.add(titleOfBlock);
                dailyCalender.add(block);


                // -----test ------ //
            }

        }

    }




    public void openDescription(String desc){
        JFrame descriptionFrame = new JFrame("Description");
        descriptionFrame.setSize(500,500);
        descriptionFrame.toFront();
        JTextArea descriptionArea = new JTextArea(desc);
        descriptionFrame.add(descriptionArea, BorderLayout.CENTER);
        descriptionFrame.setVisible(true);
        descriptionFrame.toFront();
        descriptionFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);


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
        addButton.addActionListener(e -> openCreateEventFrame());

        // Create panel for button and set its background color
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setSize(50,50);
        buttonPanel.setBackground(Color.LIGHT_GRAY);
        buttonPanel.add(addButton);

        // remove button
        JButton removeButton = new JButton("-");
        removeButton.setSize(50,50);
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







    }

    /**
     * Opens the Window which handles eventmaking
     */
    public void openCreateEventFrame(){
        JFrame eventFrame = new EventGUI(this, dailyCalender);
        //eventFrame.setAlwaysOnTop(true);


    }

    public void openRemoveFrame(Day day){
        DeleteEventGUI deleteFrame = new DeleteEventGUI(this, dailyCalender);
        deleteFrame.setTasks(dailyEvents);
        //deleteFrame.setAlwaysOnTop(true);
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


    public void sort(){
        int i, j;
        DailyEvent key;
        for (i = 1; i < dailyEvents.size(); i++) {
            key = dailyEvents.get(i);
            j = i - 1;

            // Move elements of arr[0..i-1],
            // that are greater than key,
            // to one position ahead of their
            // current position
            while (j >= 0 && dailyEvents.get(j).getStartTime().isAfter(key.getStartTime())) {
                dailyEvents.set(j + 1,dailyEvents.get(j));
                j = j - 1;
            }
            dailyEvents.set(j+1, key);
        }
    }

    public void setDate(LocalDate date){
        System.out.println("skapa variabler som kommer ihåg vilken dag och månad det är som strängar och objekt som attribut");
    }


    public void addPanels(){

    }


    public static void main(String[] args) {
        // Example usage: create a new Day frame
        LocalDate currentDate = LocalDate.now();
        SwingUtilities.invokeLater(() -> new Day(currentDate));
    }
}
