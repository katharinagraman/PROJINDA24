import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CalendarP {

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
        mainFrame = new JFrame("Calendar");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(1000, 600);

        // Create the calendar panel
        calendarPanel = new JPanel(new GridLayout(4, 7)); // Calendar grid (4 rows x 7 columns)
        calendarPanel.setSize(800,600);
        // Display the current month's calendar
        displayCalendar(LocalDate.now());

        mainFrame.add(calendarPanel);
        mainFrame.setVisible(true);
    }


    // displays calendar
    private void displayCalendar(LocalDate date) {
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


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CalendarP(); // Create and display the calendar application
            }
        });
    }
}
