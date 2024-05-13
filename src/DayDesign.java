import javax.swing.*;
import java.awt.*;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class DayDesign extends JComponent implements Scrollable {

    private static final LocalTime START_TIME = LocalTime.of(0, 0);
    private static final LocalTime END_TIME = LocalTime.of(23, 0);
    private static final int TIME_INTERVAL_MINUTES = 30;

    private static final int HOUR_HEIGHT = 100;

    public DayDesign(){
        setPreferredSize(new Dimension(1920,1000)); // sets size
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        int numIntervals = (int) (ChronoUnit.MINUTES.between(START_TIME, END_TIME) / TIME_INTERVAL_MINUTES);

        int lineHeight = getHeight() / numIntervals;

        for (int i = 0; i <= numIntervals; i++) {
            int y = i * lineHeight;
            LocalTime currentTime = START_TIME.plusMinutes(i * TIME_INTERVAL_MINUTES);

            // Draw time label
            g2.drawString(currentTime.toString(), 10, y + 15);

            // Draw horizontal grid line
            g2.drawLine(0, y, getWidth(), y);
        }

        // Draw vertical grid lines for hours
        int numHours = (int) ChronoUnit.HOURS.between(START_TIME, END_TIME);
        int columnWidth = getWidth() / numHours;

        for (int i = 0; i <= numHours; i++) {
            int x = i * columnWidth;
            g2.drawLine(x, 0, x, getHeight());
        }

        // Example: Drawing events (you can replace this with actual event rendering logic)
        g2.setColor(Color.BLUE);
        g2.fillRect(50, lineHeight * 2, columnWidth, lineHeight * 3); // Example event block
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(600, 400); // Set preferred size of the calendar panel
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
            JFrame frame = new JFrame("Day Calendar");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().add(new DayDesign());
            frame.pack();
            frame.setVisible(true);
        });
    }
}
