import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CalendarP {
    /**
     * Main programme where the Calendar Application is run
     */

    private ZonedDateTime today, chosenMonth;
    private Day dayFrame, todaysFrame;
    private JButton goLeftMonth, goRightMonth, hamburgerButton;

    /**
     * Map with days that contains events
     * Local Date is the identifier
     */

    private HashMap<LocalDate, Day> mapOfDaysWithTasks = new HashMap<>();
    private JLabel title = new JLabel("My Planner");

    private JLabel monthLB;

    private JFrame mainFrame;
    private JPanel calendarPanel, sideMenuPanel, changeButtonPanel;

    /**
     * Constructor, and layout of home page
     */
    public CalendarP() {
        today = ZonedDateTime.now();    // returns todays date and time
        chosenMonth = ZonedDateTime.now();  // keeps track of the given month

        mainFrame = new JFrame("Calendar"); // main window which exits on close
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setBackground(new Color(230, 230, 250));
        mainFrame.setSize(1920, 1080);


        //---- Hamburger menu -------------------------------------------///
        sideMenuPanel = new JPanel();   // Create sidemenu panel (initially hidden)
        sideMenuPanel.setBackground(Color.WHITE);
        sideMenuPanel.setPreferredSize(new Dimension(300, mainFrame.getHeight())); // Adjust width as needed
        sideMenuPanel.setVisible(false); // hides visibility


        // Create hamburger button and actionListener listening to clicking on it
        hamburgerButton = new JButton("☰");
        hamburgerButton.setFont(new Font("Georgia", Font.BOLD, 20));
        hamburgerButton.setPreferredSize(new Dimension(40, 40));
        hamburgerButton.addActionListener(e -> toggleSideMenu());

        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(new Color(230, 230, 250)); // Set background color of title panel
        titlePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Add padding to title panel

        // Add hamburger button to the title panel (align right)
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(hamburgerButton);
        buttonPanel.setBackground(new Color(230, 230, 250));
        titlePanel.add(buttonPanel, BorderLayout.EAST);

        // Add title label to the title panel
        title = new JLabel("My Planner");
        title.setFont(new Font("Georgia", Font.BOLD, 32));
        titlePanel.add(title, BorderLayout.WEST);

        // Add the title panel to the content pane at the NORTH position
        mainFrame.getContentPane().add(titlePanel, BorderLayout.NORTH);
        // Add side menu panel to the EAST position
        mainFrame.getContentPane().add(sideMenuPanel, BorderLayout.EAST);

        // --- hamburger end -----------------------------------------------------------------//

        // --- Calendar Panel ---//
        // Create the calendar panel

        calendarPanel = new JPanel(new GridLayout(0,7, 5, 10));   //asking for a grid layout with 7 columns where it fills with necessary rows
        calendarPanel.setSize(800,400);
        calendarPanel.setBackground(new Color(230, 230, 250));
        mainFrame.add(calendarPanel, BorderLayout.WEST);

        // Create buttons and add them to the button panel
        goLeftMonth = new JButton("<");
        goLeftMonth.addActionListener(e->oneMonthBackward());
        goLeftMonth.setSize(40,40);
        goRightMonth = new JButton(">");
        goRightMonth.addActionListener(e->oneMonthForward());
        goRightMonth.setSize(50,40);

        monthLB = new JLabel(chosenMonth.getMonth().toString());
        monthLB.setSize(300,40);
        monthLB.setFont(new Font("Georgia", Font.BOLD, 18));

        /**
         * Panel for changing buttons
         */
        changeButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        changeButtonPanel.setBackground(new Color(230, 230, 250));
        changeButtonPanel.setSize((goLeftMonth.getWidth() * 2) + monthLB.getWidth() + 20,45);
        changeButtonPanel.add(goLeftMonth, BorderLayout.WEST);
        changeButtonPanel.add(goRightMonth, BorderLayout.EAST);
        changeButtonPanel.add(monthLB, BorderLayout.CENTER);

        // Add button panel to the center of titlePanel
        titlePanel.add(changeButtonPanel, BorderLayout.CENTER);

        // method that displays the calendarPanel design

        displayCalendar(LocalDate.now()); //calculates layout of calendarPanel based on the first dat of the month

        mainFrame.setVisible(true);
    }

    /**
     * Displays calendar
     * @param date used to determine first day of month and then creating a calendar
     */
    private void displayCalendar(LocalDate date) {
        // Clear existing calendar panel
        calendarPanel.removeAll();

        // creates an object that is the first day of the same month as the date parameter
        LocalDate firstDayOfMonth = date.withDayOfMonth(1);

        // get value representing which day the first day of the month is
        int startDayOfWeek = firstDayOfMonth.getDayOfWeek().getValue();

        // Add empty labels for days before the first day of the month
        // so that column in the gridlayout is empty
        for (int i = 1; i < startDayOfWeek; i++) {
            calendarPanel.add(new JLabel(""));
        }

        // Add labels for each day of the month
        LocalDate currentDate = firstDayOfMonth;
        // while currentDate is still within the same date as the month of the parameter value
        while (currentDate.getMonthValue() == date.getMonthValue()) {
            JPanel dayPanel = createDayPanel(currentDate);
            dayPanel.setBackground(Color.WHITE);
            calendarPanel.add(dayPanel);
            currentDate = currentDate.plusDays(1);
        }

        // Repaint the calendar panel
        calendarPanel.revalidate();
        calendarPanel.repaint();
    }

    /**
     * Creates a day panel and assigns an action listener to it
     * @param date to determine what date
     * @return
     */
    private JPanel createDayPanel(LocalDate date) {
        JPanel dayPanel = new JPanel();
        dayPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK)); //border
        dayPanel.setPreferredSize(new Dimension(100, 100)); // size

        JLabel dateLabel = new JLabel(String.valueOf(date.getDayOfMonth()));
        dayPanel.add(dateLabel);

        // Actionlistener which opens a new window for each day, which creates a new Day object
        dayPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                // Open a new frame or perform an action when a day is clicked
                openDayFrame(date);
            }
        });

        return dayPanel;
    }

    /**
     * Method for actionListeners
     */
    private void oneMonthForward(){
        chosenMonth = chosenMonth.plusMonths(1);
        displayCalendar(chosenMonth.toLocalDate());
        changeButtonPanel.remove(monthLB);
        monthLB = new JLabel(chosenMonth.getMonth().toString());
        monthLB.setFont(new Font("Georgia", Font.BOLD, 18));
        monthLB.setSize(300,40);
        changeButtonPanel.setSize((goRightMonth.getWidth() *2) + monthLB.getWidth() + 20, 45);
        changeButtonPanel.add(monthLB, BorderLayout.CENTER);

        changeButtonPanel.repaint();
        changeButtonPanel.revalidate();

    }

    private void oneMonthBackward(){
        chosenMonth = chosenMonth.plusMonths(-1);
        displayCalendar(chosenMonth.toLocalDate());
        changeButtonPanel.remove(monthLB);
        monthLB = new JLabel(chosenMonth.getMonth().toString());
        monthLB.setFont(new Font("Georgia", Font.BOLD, 18));
        monthLB.setSize(300,40);
        changeButtonPanel.setSize((goRightMonth.getWidth() *2) + monthLB.getWidth() + 20, 45);
        changeButtonPanel.add(monthLB, BorderLayout.CENTER);

        changeButtonPanel.repaint();
        changeButtonPanel.revalidate();

    }


    /**
     * Opens a new frame which is a Day-class object
     * Object is stored in map in the container
     * @param date unique for each day
     */
    private void openDayFrame(LocalDate date) {
        if (!mapOfDaysWithTasks.containsKey(date)) {
            System.out.println("aloha");
            dayFrame = new Day(date);
            mapOfDaysWithTasks.put(date, dayFrame);
        }else{
            dayFrame = mapOfDaysWithTasks.get(date);
            System.out.println("dagen finns");

        }
        // Show or activate the dayFrame
        dayFrame.setVisible(true);
        dayFrame.toFront(); // Bring the frame to the front (in case it was minimized or behind other windows)

    }

    /**
     * Toggles side menu and prints today's tasks on it.
     */
    private void toggleSideMenu() {
        sideMenuPanel.removeAll();
        LocalDate now = LocalDate.now();
        sideMenuPanel.setVisible(!sideMenuPanel.isVisible());
        if(mapOfDaysWithTasks.containsKey(now)){
            todaysFrame = mapOfDaysWithTasks.get(today.toLocalDate());
            if(!todaysFrame.getDailyEvents().isEmpty()){
                sideMenuPanel.setLayout(new GridLayout(15,1,0,10));
                ArrayList<DailyEvent> dailyEvents = todaysFrame.getDailyEvents();

                for (int i = 0; i < dailyEvents.size(); i++) {
                    JPanel event = new JPanel();
                    event.setBackground(desaturate(dailyEvents.get(i).getColourOfEvent()));
                    event.setSize(sideMenuPanel.getWidth(),30);
                    JLabel name = new JLabel(dailyEvents.get(i).toString());
                    event.add(name);
                    sideMenuPanel.add(event);

                }

            }else{
                JLabel noTasksToday = new JLabel("No Tasks Today :)");
                sideMenuPanel.add(noTasksToday, BorderLayout.NORTH);
            }
        }else{
            JLabel noTasksToday = new JLabel("No Tasks Today :)");
            sideMenuPanel.add(noTasksToday, BorderLayout.NORTH);

        }
        sideMenuPanel.revalidate();
        sideMenuPanel.repaint();


        mainFrame.revalidate(); // Recalculate layout
        mainFrame.repaint();    // Redraw components
    }

    public Color desaturate(Color color){
        // Desaturate the color (reduce saturation)
        float[] hsbValues = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
        Color desaturatedColor = Color.getHSBColor(hsbValues[0], 0.3f, hsbValues[2]); // Adjust saturation (0.3f for less saturation)

        // Set the opacity (alpha) of the color (less opaque)
        Color lessOpaqueColor = new Color(desaturatedColor.getRed(), desaturatedColor.getGreen(), desaturatedColor.getBlue(), 150); // Alpha value (0-255)
        return lessOpaqueColor;

    }

    // Getters
    public JPanel getCalendarPanel() {
        return calendarPanel;
    }

    public HashMap<LocalDate, Day> getMapOfDaysWithTasks(){
        return mapOfDaysWithTasks;
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CalendarP(); // Create and display the calendar application
            }
        });
    }
}
