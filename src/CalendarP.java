import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CalendarP {

    private JFrame mainFrame;
    private JPanel calendarPanel;


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
        calendarPanel = new JPanel(new GridLayout(6, 7)); // Calendar grid (6 rows x 7 columns)
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

    /**
     * Varje dag fram måste ha en vit panel där man kan skriva in grejer så i guess
     * en till action listener
     * Typ ett + är ju bra
     * @param date
     */
    private void openDayFrame(LocalDate date) {
        JFrame dayFrame = new JFrame("Day: " + date);
        dayFrame.setSize(1920, 1080);

        JPanel dayPanel = new Day();
        JLabel dayLabel = new JLabel("Events for " + date);
        dayLabel.setFont(new Font("Georgia", Font.BOLD, 32));

        dayFrame.add(dayPanel);
        dayFrame.setVisible(true);

        // add button
        JButton addButton = new JButton("+");
        addButton.setFont(new Font("Georgia", Font.BOLD, 20));
        addButton.setPreferredSize(new Dimension(40, 40));
        addButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClickedToAddEvent(java.awt.event.MouseEvent evt) {
                // Open a new frame or perform an action when a day is clicked
                addEvent(date);
            }
        });
    }

    /**
     * Opens the taskframe where user can add tasks
     * This logic is handled in EventGUI
     * @param date
     */
    public void addEvent(LocalDate date){
        JPanel taskFrame = new EventGUI(date);
        taskFrame.add(tasksEachDay);

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
