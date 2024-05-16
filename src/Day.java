import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Day extends JFrame implements EventListener {
    /**
     * Every day panel will have a Day Frame
     * This is also an object which stores daily events a person puts in
     */
    private DayDesign dailyCalender = new DayDesign(this);

    private JPanel mainPanel, centerPanel, simpleView;

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
        //deleteEventGUI.setTasks(dailyEvents);

        updateDisplay(); // Method to update the display with the new task
    }

    @Override
    public void removeEvent(int index){
        if(dailyEvents.isEmpty()){
            updateDisplay();
        }
        dailyEvents.remove(index);
        dailyCalender.remove(dailyCalender.getBlockOfEvents().get(index));  // remove panel from dailyCalendar
        dailyCalender.getBlockOfEvents().remove(index);
        updateDisplay();
    }


    /**
     * Constructor for design
     * @param date takes local date as a parameter, used to make it unique
     */
    public Day(LocalDate date) {
        super("Day: " + date);
        //deleteEventGUI.setVisible(false);

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
     * Updates the display
     */
    private void updateDisplay() {
        // removes current
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

    /**
     * Draws panels
     * @param a list of daily events
     * @param dailyCalender illustration
     */
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

                JPanel block = new JPanel();
                block.setBackground(lessOpaqueColor);
                block.setBounds(dailyCalender.getColumnWidth(), y0Coordinate,dailyCalender.getColumnWidth(),heightOfTask);
                block.addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseClicked(java.awt.event.MouseEvent evt) {
                        // Open a new frame or perform an action when a day is clicked
                        openDescription(event);
                    }
                });
                JLabel titleOfBlock = new JLabel(event.toString());
                block.add(titleOfBlock);
                dailyCalender.add(block);
                dailyCalender.getBlockOfEvents().add(block);


                // -----test ------ //
            }

        }

    }

    /**
     * Opens a frame displaying the description and allowing users to mainpulate it
     * @param event is a dailyEvent stored in day
     */
    public void openDescription(DailyEvent event){
        JFrame descriptionFrame = new JFrame("Description");
        descriptionFrame.setSize(500,500);
        descriptionFrame.toFront();
        JTextArea descriptionArea = new JTextArea(event.getDescription());
        descriptionFrame.add(descriptionArea, BorderLayout.CENTER);
        descriptionFrame.setVisible(true);
        descriptionFrame.toFront();
        descriptionFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Add window listener to save text when frame is closed
        descriptionFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // Get the text from JTextArea
                String updatedDescription = descriptionArea.getText();
                // Update the event's description with the edited text
                event.setDescription(updatedDescription);
                // Perform any other necessary actions here (e.g., save to database)
                // Print to console for demonstration
                System.out.println("Description updated: " + updatedDescription);
            }
        });


    }



    /**
     * Opens the Window which handles event-making
     */
    public void openCreateEventFrame(){
        JFrame eventFrame = new EventGUI(this);
        //eventFrame.setAlwaysOnTop(true);


    }

    /**
     * Opens frame that allows users to remove items.
     * @param day
     */
    public void openRemoveFrame(Day day){
        DeleteEventGUI deleteFrame = new DeleteEventGUI(this);
        deleteFrame.setTasks(dailyEvents);

    }

    public void addSimplifiedView(){
        simpleView.setVisible(!simpleView.isVisible());
        simpleView.removeAll();
        simpleView.setLayout(new GridLayout(dailyEvents.size(),0,0,10));
        sort(dailyEvents);
        for(DailyEvent event : dailyEvents){
            JPanel eventP = new JPanel();
            eventP.setBackground(dailyCalender.desaturate(event.getColourOfEvent()));
            JLabel title = new JLabel(event.toString());
            eventP.add(title);
            simpleView.add(eventP);
        }
        centerPanel.add(simpleView, BorderLayout.EAST);
    }

    public void sort(ArrayList<DailyEvent> a){
        int i, j;
        DailyEvent key;
        for (i = 1; i < a.size(); i++) {
            key = a.get(i);
            j = i - 1;

            // Move elements of arr[0..i-1],
            // that are greater than key,
            // to one position ahead of their
            // current position
            while (j >= 0 && a.get(j).getStartTime().isAfter(key.getStartTime())) {
                a.set(j + 1,a.get(j));
                j = j - 1;
            }
            a.set(j+1, key);
        }
    }


    /**
     * Returns an array with the daily events to home frame
     * @return
     */
    public String[] arrayForHamburger(){
        String[] a = new String[dailyEvents.size()];
        for(int i= 0; i<a.length;i++){
            a[i] = dailyEvents.get(i).toString();
        }
        return a;
    }

    // Getters

    /**
     * Returns daily events
     * @return dailyEvents
     */

    public ArrayList<DailyEvent> getDailyEvents(){
        return dailyEvents;
    }


    public static void main(String[] args) {
        // Example usage: create a new Day frame
        LocalDate currentDate = LocalDate.now();
        SwingUtilities.invokeLater(() -> new Day(currentDate));
    }
}
