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

    ZonedDateTime today, dateOfChosenMonth;


    private Day dayFrame, todaysFrame;
    JButton goLeftMonth, goRightMonth, hamburgerButton;

    JLabel month, year;
    /**
     * Map with days that contains events
     * Local Date is the identifier
     */
    HashMap<LocalDate, Day> mapOfDaysWithTasks = new HashMap<>();
    private JLabel title = new JLabel("My Planner");

    private JLabel monthLB;

    private JFrame mainFrame;
    private JPanel calendarPanel, sideMenuPanel;

    private HashMap<LocalDate,DailyEvent> taskThatDay = new HashMap<LocalDate, DailyEvent>();

    /**
     * eller så har jag en map och en getter i varje dag istället så tasks each day blir istället en
     * Map <Tid, Event>
     */
    private Map<Date, HashMap<Date,DailyEvent>> tasksEachDay = new HashMap<>();

    public CalendarP() {
        today = ZonedDateTime.now();
        dateOfChosenMonth = ZonedDateTime.now();


        mainFrame = new JFrame("Calendar");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(1920, 1080);






        //---- hamburger ---///

        // Create side menu panel (initially hidden)
        sideMenuPanel = new JPanel();
        sideMenuPanel.setBackground(Color.WHITE);
        sideMenuPanel.setPreferredSize(new Dimension(200, mainFrame.getHeight())); // Adjust width as needed
        sideMenuPanel.setVisible(false);


        // Create hamburger button
        hamburgerButton = new JButton("☰");
        hamburgerButton.setFont(new Font("Georgia", Font.BOLD, 20));
        hamburgerButton.setPreferredSize(new Dimension(40, 40));
        hamburgerButton.addActionListener(e -> toggleSideMenu());

        // Create title panel for layout här är nog den där fula ramen jag inte ens bad om högst upp
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(new Color(230, 230, 250)); // Set background color of title panel
        titlePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Add padding to title panel

        // Add title label to the title panel
        title = new JLabel("My Planner");
        title.setFont(new Font("Georgia", Font.BOLD, 32));
        titlePanel.add(title, BorderLayout.WEST);

        // Add hamburger button to the title panel (align right)
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(hamburgerButton);
        buttonPanel.setBackground(new Color(230, 230, 250));
        titlePanel.add(buttonPanel, BorderLayout.EAST);

        // Add the title panel to the content pane at the NORTH position
        mainFrame.getContentPane().add(titlePanel, BorderLayout.NORTH);

        // Add side menu panel to the EAST position
        mainFrame.getContentPane().add(sideMenuPanel, BorderLayout.EAST);



        // --- hamburger ---//

        // Create the calendar panel
        calendarPanel = new JPanel(new GridLayout(0,7 )); // Calendar grid (4 rows x 7 columns)
        calendarPanel.setSize(800,400);
        calendarPanel.setBackground(Color.GREEN);
        mainFrame.add(calendarPanel, BorderLayout.WEST);


        goLeftMonth = new JButton("<");
        goLeftMonth.setSize(40,40);

        goRightMonth = new JButton(">");
        goRightMonth.setSize(40,40);


        JPanel changeButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        changeButtonPanel.setSize(mainFrame.getWidth() - hamburgerButton.getWidth() - 10, 45);
        changeButtonPanel.add(goLeftMonth, BorderLayout.EAST);
        monthLB = new JLabel( dateOfChosenMonth.getMonth().toString());
        changeButtonPanel.add(monthLB, BorderLayout.CENTER);
        changeButtonPanel.add(goRightMonth, BorderLayout.WEST);
        changeButtonPanel.setBackground(Color.WHITE);


       titlePanel.add(buttonPanel, BorderLayout.CENTER);

        goLeftMonth.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                // Open a new frame or perform an action when a day is clicked
                oneMonthBackward();
            }
        });

        goRightMonth.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                // Open a new frame or perform an action when a day is clicked
                oneMonthForward();
            }
        });





        // Display the current month's calendar
        displayCalendar(LocalDate.now());
        // Add side menu panel to the EAST position

        mainFrame.setVisible(true);
    }


    private void makeCalendar(){
        today = ZonedDateTime.now();
        month = new JLabel(today.getMonth().toString());
        year = new JLabel(String.valueOf(today.getYear()));


        double spacingHorizontal = 1.5;
        double spacingVertical = 1.5;

    }


    // displays calendar
    private void displayCalendar(LocalDate date) {
        makeCalendar();
        // Clear existing calendar panel
        calendarPanel.removeAll();

        // Get the first day of the month
        LocalDate firstDayOfMonth = date.withDayOfMonth(1);

        // Determine the day of the week for the first day of the month (e.g., Monday = 1, Tuesday = 2, ..., Sunday = 7)
        int startDayOfWeek = firstDayOfMonth.getDayOfWeek().getValue();

        // Add empty labels for days before the first day of the month
        for (int i = 1; i < startDayOfWeek; i++) {
            calendarPanel.add(new JLabel(""));
        }

        // Add labels for each day of the month
        LocalDate currentDate = firstDayOfMonth;
        while (currentDate.getMonthValue() == date.getMonthValue()) {
            JPanel dayPanel = createDayPanel(currentDate);
            calendarPanel.add(dayPanel);
            currentDate = currentDate.plusDays(1);
        }

        // Repaint the calendar panel
        calendarPanel.revalidate();
        calendarPanel.repaint();
    }

    // Creates a day panel
    private JPanel createDayPanel(LocalDate date) {
        JPanel dayPanel = new JPanel();
        dayPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        dayPanel.setPreferredSize(new Dimension(100, 100));

        JLabel dateLabel = new JLabel(String.valueOf(date.getDayOfMonth()));
        dayPanel.add(dateLabel);

        // Add action listener to view details or navigate to this day
        dayPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                // Open a new frame or perform an action when a day is clicked
                openDayFrame(date);
            }
        });

        return dayPanel;
    }

    /**
     * Method for actionlistener
     */
    private void oneMonthForward(){
        dateOfChosenMonth = dateOfChosenMonth.plusMonths(1);
        displayCalendar(dateOfChosenMonth.toLocalDate());

    }

    private void oneMonthBackward(){
        dateOfChosenMonth =dateOfChosenMonth.plusMonths(-1);
        displayCalendar(dateOfChosenMonth.toLocalDate());
    }

    // method that actionlistener adapts, creates a new day fram

    private boolean isThereAFrame(LocalDate date){
        Day day = new Day(date);
        return mapOfDaysWithTasks.containsKey(day);

    }

    /**
     * Varje dag fram måste ha en vit panel där man kan skriva in grejer så i guess
     * en till action listener
     * Typ ett + är ju bra
     * @param date
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


    public JPanel getCalendarPanel() {
        return calendarPanel;
    }

    public HashMap<LocalDate, Day> getMapOfDaysWithTasks(){
        return mapOfDaysWithTasks;
    }


    private void toggleSideMenu() {
        sideMenuPanel.removeAll();
        LocalDate now = LocalDate.now();
        sideMenuPanel.setVisible(!sideMenuPanel.isVisible());
        System.out.println("NEJE");

        if(mapOfDaysWithTasks.containsKey(now)){
            todaysFrame = mapOfDaysWithTasks.get(today.toLocalDate());
            if(!todaysFrame.getTasksThisDay().isEmpty()){
                System.out.println("NOT EMPTY");
                HashMap<LocalTime, DailyEvent> mapforDay = todaysFrame.getTasksThisDay();
                String[] a = todaysFrame.arrayForHamburger();

                // Simulate adding components to sideMenuPanel
                GridBagConstraints gbc = new GridBagConstraints();
                gbc.anchor = GridBagConstraints.NORTH;
                gbc.insets = new Insets(5, 10, 5, 10);

                for (int i = 0; i < mapforDay.size(); i++) {
                    JLabel eventLabel = new JLabel(a[i]);
                    gbc.gridx = 0;
                    gbc.gridy = i;
                    sideMenuPanel.add(eventLabel, gbc);
                }

            }else{
                JLabel noTasksToday = new JLabel("No Tasks Today :)");
                sideMenuPanel.add(noTasksToday);
            }
        }else{
            JLabel noTasksToday = new JLabel("No Tasks Today :)");
            sideMenuPanel.add(noTasksToday);

        }
        sideMenuPanel.revalidate();
        sideMenuPanel.repaint();


        mainFrame.revalidate(); // Recalculate layout
        mainFrame.repaint();    // Redraw components
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
