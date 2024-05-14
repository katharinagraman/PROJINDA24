import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

public class DayDesign extends JComponent implements Scrollable, EventListenerDraw {
    /**
     * Shows the given timetable and comminucates with the Day Apllication by taking it as a parameter
     * so gör en repaint funktion
     */


    private static final LocalTime START_TIME = LocalTime.of(0, 0);
    private static final LocalTime END_TIME = LocalTime.of(23, 0);
    private static final int TIME_INTERVAL_MINUTES = 30;
    private Graphics2D g2;

    private static final int INTERVAL_HEIGHT = 60;
    private static final int AMOUNT_COLUMNS = 3;
    private static final int COLUMN_WIDTH = 800 / 3;;

    private HashMap<LocalTime, DailyEvent> eventsToday;

    private final int numIntervals = (int) (ChronoUnit.MINUTES.between(START_TIME, END_TIME) / TIME_INTERVAL_MINUTES) + 1;

    private static final int HOUR_HEIGHT = 100;

    public void drawEvent(HashMap<LocalTime, DailyEvent> a, Graphics2D g2){
        drawEventsOnTimeTable(a,g2);

    }
    public DayDesign(Day day){
        setPreferredSize(new Dimension(800, 24 * HOUR_HEIGHT)); // sets size
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g2 = (Graphics2D) g;
        drawGrid(g2);
    }

    public Graphics2D getGraphics(){
        return g2;
    }

    private void drawGrid(Graphics2D g2) {
        int intervalHeight = getHeight() / numIntervals;

        for (int i = 0; i < numIntervals; i++) {
            int y = i * intervalHeight;
            LocalTime currentTime = START_TIME.plusMinutes(i * TIME_INTERVAL_MINUTES);

            // Draw time label
            g2.drawString(currentTime.toString(), 10, y + 15);

            // Draw horizontal grid line
            g2.drawLine(0, y, getWidth(), y);
        }

        // Draw vertical grid lines for columns
        for (int i = 0; i <= AMOUNT_COLUMNS; i++) {
            int x = i * COLUMN_WIDTH;
            g2.drawLine(x, 0, x, getHeight());
        }

        // Example: Drawing events (you can replace this with actual event rendering logic)
        g2.setColor(Color.BLUE);
        g2.fillRect(COLUMN_WIDTH, HOUR_HEIGHT * 2, COLUMN_WIDTH, HOUR_HEIGHT * 3); // Example event block
        g2.setColor(Color.GREEN);
        g2.fillRect(COLUMN_WIDTH, HOUR_HEIGHT *14, COLUMN_WIDTH, HOUR_HEIGHT*(16-14));
        if(eventsToday != null){
            System.out.println("Not null");
            drawEventsOnTimeTable(eventsToday, g2);
        }

    }

    public void drawEventsOnTimeTable(HashMap<LocalTime, DailyEvent> a, Graphics2D g2){
        for(LocalTime key : a.keySet()){
            //LocalTime startOfDay = LocalTime.of(0,0); compareTo()
            LocalTime startTime = a.get(key).getStartTime();
            LocalTime endTime = a.get(key).getEndTime();

            int y0Coordinate = startTime.getHour() + startTime.getMinute() * HOUR_HEIGHT;
            int y1Coordinate = endTime.getHour()  + endTime.getMinute() * HOUR_HEIGHT;
            int heightOfTask = y1Coordinate - y0Coordinate;

            g2.setColor(a.get(key).getColourOfEvent());
            g2.fillRect(COLUMN_WIDTH, y0Coordinate, COLUMN_WIDTH, heightOfTask);
        }

    }


    // ---- override for scrollable ------ //

    @Override
    public Dimension getPreferredScrollableViewportSize() {
        return getPreferredSize(); // Use the component's preferred size as viewport size
    }

    @Override
    public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) {
        return HOUR_HEIGHT; // Scroll one hour at a time
    }

    @Override
    public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) {
        return HOUR_HEIGHT * 5; // Scroll five hours at a time
    }

    @Override
    public boolean getScrollableTracksViewportWidth() {
        return true; // Always match the width of the viewport
    }

    @Override
    public boolean getScrollableTracksViewportHeight() {
        return false; // Let the viewport determine the height
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Day day = new Day(LocalDate.now());
            JFrame frame = new JFrame("Events for");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            DayDesign timetable = new DayDesign(day);
            JScrollPane scrollPane = new JScrollPane(timetable);
            scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

            frame.add(scrollPane, BorderLayout.CENTER);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
