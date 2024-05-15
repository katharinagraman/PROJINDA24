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
     * Main programme where the Calendar Application is ran
     */


    private ZonedDateTime today, chosenMonth;

    private Day dayFrame, todaysFrame;
    private JButton goLeftMonth, goRightMonth, hamburgerButton;

    private JLabel month, year;
    /**
     * Map with days that contains events
     * Local Date is the identifier
     */
    private HashMap<LocalDate, Day> mapOfDaysWithTasks = new HashMap<>();
    private JLabel title = new JLabel("My Planner");

    private JLabel monthLB;

    private JFrame mainFrame;
    private JPanel calendarPanel, sideMenuPanel, changeButtonPanel;

    private HashMap<LocalDate,DailyEvent> taskThatDay = new HashMap<LocalDate, DailyEvent>();

    /**
     * eller så har jag en map och en getter i varje dag istället så tasks each day blir istället en
     * Map <Tid, Event>
     */
    private Map<Date, HashMap<Date,DailyEvent>> tasksEachDay = new HashMap<>();

    /**
     * Constructor consisting of the general design
     */
    public CalendarP() {
        today = ZonedDateTime.now();    // can return todays date or month or year
        chosenMonth = ZonedDateTime.now();  // keeps track of the given month


        mainFrame = new JFrame("Calendar"); // main window which exits on close
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(1920, 1080);


        //---- Hamburger menu ---///
        /**
         * The hamburger menu provides fast access to a students daily tasks
         */


        // Create side menu panel (initially hidden)
        sideMenuPanel = new JPanel();
        sideMenuPanel.setBackground(Color.WHITE);
        sideMenuPanel.setPreferredSize(new Dimension(300, mainFrame.getHeight())); // Adjust width as needed
        sideMenuPanel.setVisible(false); // hides visibility


        // Create hamburger button and actionlistener listening to clicking on it
        hamburgerButton = new JButton("☰");
        hamburgerButton.setFont(new Font("Georgia", Font.BOLD, 20));
        hamburgerButton.setPreferredSize(new Dimension(40, 40));
        hamburgerButton.addActionListener(e -> toggleSideMenu());


        // Create title panel for layout här är nog den där fula ramen jag inte ens bad om högst upp
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

        // --- hamburger end ---//

        // --- Calendar Panel ---//
        /**
         * For the calendar panel I asked ChatGPT for some aid
         * By using GridLayout I ask for 7 columns and then it can fill with necessary rows
         */
        // Create the calendar panel
        calendarPanel = new JPanel(new GridLayout(0,7 ));
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


        /**
         * Action Listeners which listens to mouse click
         */
        /*goLeftMonth.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                // Open a new frame or perform an action when a day is clicked
                oneMonthBackward();


            }
        });*/

        /*goRightMonth.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                // Open a new frame or perform an action when a day is clicked
                oneMonthForward();
            }
        });
*/

        // method that displays the calendarPanel design
        displayCalendar(LocalDate.now());


        mainFrame.setVisible(true);
    }

    /**
     * Work in progress supposed to be used once I figure how to make the calendar more neat
     */
    private void makeCalendar(){
        today = ZonedDateTime.now();
        month = new JLabel(today.getMonth().toString());
        year = new JLabel(String.valueOf(today.getYear()));


        double spacingHorizontal = 1.5;
        double spacingVertical = 1.5;

    }


    /**
     * This method takes the given date as a parameter
     * When I started I used LocalDate but now I am unsure which is the best suited
     * @param date
     */
    private void displayCalendar(LocalDate date) {
        //makeCalendar();
        // Clear existing calendar panel
        calendarPanel.removeAll();

        // creates an object that is the first day of the same month as the date parameter
        LocalDate firstDayOfMonth = date.withDayOfMonth(1);

        // Determine the day of the week for the first day of the month (e.g., Monday = 1, Tuesday = 2, ..., Sunday = 7)
        // returns the integer value of the first day of the mont so monday is 1 etc
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

    // Creates a day panel
    private JPanel createDayPanel(LocalDate date) {
        JPanel dayPanel = new JPanel();
        dayPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK)); //border
        dayPanel.setPreferredSize(new Dimension(100, 100)); // size

        //----test-----//


        Color eventColour = Color.BLUE;

        // Desaturate the color (reduce saturation)
        float[] hsbValues = Color.RGBtoHSB(eventColour.getRed(), eventColour.getGreen(), eventColour.getBlue(), null);
        Color desaturatedColor = Color.getHSBColor(hsbValues[0], 0.3f, hsbValues[2]); // Adjust saturation (0.3f for less saturation)

        // Set the opacity (alpha) of the color (less opaque)
        Color lessOpaqueColor = new Color(desaturatedColor.getRed(), desaturatedColor.getGreen(), desaturatedColor.getBlue(), 150); // Alpha value (0-255)
        dayPanel.setBackground(lessOpaqueColor);

        //----test----//
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
     * Method for actionlistener
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

    /**
     * Lägg till så att man ser året också!!!
     */
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
            dayFrame.setDate(date);
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

    private void printToSideMenu(HashMap<LocalTime, DailyEvent> events){
        todaysFrame = mapOfDaysWithTasks.get(today.toLocalDate());
        ArrayList<DailyEvent> dailyEvents = todaysFrame.getDailyEvents();
        String[] a = todaysFrame.arrayForHamburger();
        StringBuilder str = new StringBuilder();

        for (int i = 0; i <dailyEvents.size(); i++) {
            str.append(a[i]).append("\n");
        }
        sideMenuPanel.removeAll();
        JTextArea textAreaForSideMenu = new JTextArea(300, mainFrame.getHeight());
        textAreaForSideMenu.append(str.toString());
        sideMenuPanel.add(textAreaForSideMenu);
        sideMenuPanel.revalidate();
        sideMenuPanel.repaint();
        mainFrame.revalidate();
        mainFrame.repaint();

    }


    private void toggleSideMenu() {
        sideMenuPanel.removeAll();
        LocalDate now = LocalDate.now();
        sideMenuPanel.setVisible(!sideMenuPanel.isVisible());
        if(mapOfDaysWithTasks.containsKey(now)){
            todaysFrame = mapOfDaysWithTasks.get(today.toLocalDate());
            if(!todaysFrame.getDailyEvents().isEmpty()){
                ArrayList<DailyEvent> dailyEvents = todaysFrame.getDailyEvents();
                String[] a = todaysFrame.arrayForHamburger();

                // Simulate adding components to sideMenuPanel
                GridBagConstraints gbc = new GridBagConstraints();
                gbc.anchor = GridBagConstraints.NORTH;
                gbc.insets = new Insets(5, 10, 5, 10);

                for (int i = 0; i < dailyEvents.size(); i++) {
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
