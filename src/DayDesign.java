import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.lang.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DayDesign extends JComponent implements Scrollable {
    /**
     * Displays a grid and times
     * Task panels are drawn on top of it
     * Handles logic on where to put a panel
     */


    private static final LocalTime START_TIME = LocalTime.of(0, 0);
    private static final LocalTime END_TIME = LocalTime.of(23, 0);
    private static final int TIME_INTERVAL_MINUTES = 30;

    private static final int AMOUNT_COLUMNS = 3;
    private static final int COLUMN_WIDTH = 800 / 3;

    private int intervalHeight;
    ;

    private ArrayList<JPanel> blockOfEvents = new ArrayList<>();
    private ArrayList<DailyEvent> eventsToday = new ArrayList<>();

    private final int numIntervals = (int) (ChronoUnit.MINUTES.between(START_TIME, END_TIME) / TIME_INTERVAL_MINUTES) + 1;

    private static final int HOUR_HEIGHT = 100;


    /**
     * Repaints component whenever repaint() is called or object initialised
     * @param g the <code>Graphics</code> object to protect
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        drawGrid(g2);

    }

    /**
     * Constructor sets the size
     * @param day not used but good if adding a title
     */
    public DayDesign(Day day){
        setPreferredSize(new Dimension(800, 24 * HOUR_HEIGHT)); // sets size
    }


    /**
     * Draws a grid
     * @param g2 graphics2d object
     */
    public void drawGrid(Graphics2D g2) {
        intervalHeight = getHeight() / numIntervals;

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

    }

    /**
     * Desaturates color
     * @param color from event
     * @return desaturated color
     */
    public Color desaturate(Color color){
        // Desaturate the color (reduce saturation)
        float[] hsbValues = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
        Color desaturatedColor = Color.getHSBColor(hsbValues[0], 0.3f, hsbValues[2]); // Adjust saturation (0.3f for less saturation)

        // Set the opacity (alpha) of the color (less opaque)
        Color lessOpaqueColor = new Color(desaturatedColor.getRed(), desaturatedColor.getGreen(), desaturatedColor.getBlue(), 150); // Alpha value (0-255)
        return lessOpaqueColor;

    }

    /**
     * Calculates start coordinate for minutes that have a rest that is not 0 for
     * Mod 30
     * @param start
     * @return
     */
    public int getStartCoordinate(LocalTime start){
        if(start.getMinute() >0 && start.getMinute() < 30){
            int subHeight = (intervalHeight / 30) * (start.getMinute());
            return (start.getHour() * intervalHeight * 2) +  subHeight;

        }
        int subHeight = (intervalHeight / 30) * (start.getMinute() % 30);
        return (intervalHeight * ( 2 * start.getHour() + 1)) +  subHeight;
    }

    public int getEndCoordinate(LocalTime end){
        if(end.getMinute() >0 && end.getMinute() < 30){
            int subHeight = (intervalHeight / 30) * (end.getMinute());
            return (end.getHour() * intervalHeight * 2) +  subHeight;

        }
        int subHeight = (intervalHeight / 30) * (end.getMinute() % 30);
        return  (intervalHeight * ( 2 * end.getHour() + 1)) +  subHeight;
    }

    // Getters

    public int getColumnWidth(){
        return COLUMN_WIDTH;
    }

    public int getIntervalHeight(){
        return intervalHeight;
    }

    public ArrayList<JPanel> getBlockOfEvents(){
        return blockOfEvents;
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
