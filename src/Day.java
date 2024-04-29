import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Day extends JFrame implements EventListener {
    private DataTemplate data;
    private JPanel mainPanel, centerPanel;

    private HashMap<LocalTime,DailyEvent> tasksThisDay = new HashMap<LocalTime, DailyEvent>();

    @Override
    public void onEventAdded(String title, String type, String startTime, String endTime) {
        // Create a new DailyEvent instance with the received data
        DailyEvent event = new DailyEvent(title, type, startTime, endTime);
        LocalTime time = LocalTime.now();
        tasksThisDay.put(time, event);
        updateDisplay(); // Method to update the display with the new task
    }

    // Method to update the display with the tasks
    private void updateDisplay() {
        centerPanel.removeAll();

        // Set layout manager to BoxLayout.Y_AXIS
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

        // Rebuild the display based on tasksThisDay
        for (Map.Entry<LocalTime, DailyEvent> entry : tasksThisDay.entrySet()) {
            LocalTime time = entry.getKey();
            DailyEvent event = entry.getValue();

            JLabel timeEventLabel = new JLabel(time.toString() +" "+ event.toString());

            // Add timeLabel and eventLabel to centerPanel
            centerPanel.add(timeEventLabel);

            // Add vertical spacing (adjust as needed)
            centerPanel.add(Box.createVerticalStrut(10));
        }

        // Repaint the panel to reflect the changes
        centerPanel.revalidate();
        centerPanel.repaint();
    }

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
        centerPanel = new JPanel();
        centerPanel.setBackground(Color.WHITE); // Set background color for the center panel
        centerPanel.setPreferredSize(new Dimension(400, 300)); // Set preferred size for the center panel

        //JTextArea eventArea = new JTextArea(400,300);
        //eventArea.setBackground(Color.WHITE);

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

        addButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                // Open a new frame or perform an action when a day is clicked
                openCreateEventFrame(date, tasksThisDay);
            }
        });
    }

    public void openCreateEventFrame(LocalDate date, HashMap<LocalTime, DailyEvent> eventsThisDay){
        JFrame eventFrame = new EventGUI(this);


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

    public HashMap<LocalTime, DailyEvent> getTasksThisDay(){
        return tasksThisDay;
    }

    public static void main(String[] args) {
        // Example usage: create a new Day frame
        LocalDate currentDate = LocalDate.now();
        SwingUtilities.invokeLater(() -> new Day(currentDate));
    }
}