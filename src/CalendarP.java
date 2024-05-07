import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CalendarP {

    ZonedDateTime today, dateOfChosenMonth;

    JButton goLeftMonth;
    JButton goRightMonth;

    JLabel month, year;

    HashMap<LocalDate, Day> mapOfDaysWithTasks = new HashMap<>();

    private JFrame mainFrame;
    private JPanel calendarPanel;

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
        mainFrame.setSize(1000, 600);

        goLeftMonth = new JButton("<");
        goLeftMonth.setSize(40,40);
        JPanel titlePanelMonth = new JPanel();
        titlePanelMonth.add(goLeftMonth, BorderLayout.WEST);

        goRightMonth = new JButton(">");
        goRightMonth.setSize(40,40);
        JPanel rightPanel = new JPanel();
        titlePanelMonth.add(goRightMonth, BorderLayout.EAST);

        JLabel monthL = new JLabel(dateOfChosenMonth.getMonth().toString());
        titlePanelMonth.add(monthL, BorderLayout.CENTER);

        mainFrame.add(titlePanelMonth, BorderLayout.NORTH);

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

        // Create the calendar panel
        calendarPanel = new JPanel(new GridLayout(0,7 )); // Calendar grid (4 rows x 7 columns)
        calendarPanel.setSize(800,600);
        mainFrame.add(calendarPanel);
        // Display the current month's calendar
        displayCalendar(LocalDate.now());

        mainFrame.add(calendarPanel);
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
        Day dayFrame = mapOfDaysWithTasks.get(date);

        if (dayFrame == null) {
            dayFrame = new Day(date);
            mapOfDaysWithTasks.put(date, dayFrame);
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


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CalendarP(); // Create and display the calendar application
            }
        });
    }
}
