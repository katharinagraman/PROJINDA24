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

public class DayDesign extends JComponent implements Scrollable, EventListenerDraw {
    /**
     * Shows the given timetable and comminucates with the Day Apllication by taking it as a parameter
     * so gör en repaint funktion
     */


    private static final LocalTime START_TIME = LocalTime.of(0, 0);
    private static final LocalTime END_TIME = LocalTime.of(23, 0);
    private static final int TIME_INTERVAL_MINUTES = 30;
    //private Graphics2D g2;

    private static final int INTERVAL_HEIGHT = 60;
    private static final int AMOUNT_COLUMNS = 3;
    private static final int COLUMN_WIDTH = 800 / 3;

    private int intervalHeight;
    // använd mapen kanske
    private Map<DailyEvent, Rectangle> dailyEventAndBlockOnCalendar = new HashMap<>();

    private ArrayList<Rectangle2D> blockOfEvents = new ArrayList<>();
    private ArrayList<DailyEvent> eventsToday = new ArrayList<>();

    private final int numIntervals = (int) (ChronoUnit.MINUTES.between(START_TIME, END_TIME) / TIME_INTERVAL_MINUTES) + 1;

    private static final int HOUR_HEIGHT = 100;

    public void drawEvent(ArrayList<DailyEvent> a){
        if (this.eventsToday.isEmpty()){
            this.eventsToday = a;
        }else {
            this.eventsToday.addAll(a);

        }
        repaint(); // repaint trigger paintComnponent
    }

    public void removeEvent(ArrayList<DailyEvent> a){
        this.eventsToday = a;
        //repaint();
    }

    // painCOmoonent triggers drawGrid
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        drawGrid(g2);

    }




    public DayDesign(Day day){
        setPreferredSize(new Dimension(800, 24 * HOUR_HEIGHT)); // sets size
    }

    public void setEventsToday(ArrayList<DailyEvent> a){
        this.eventsToday = a;
    }




    // drawgrid draws grid and rectangles
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

        if(eventsToday != null){
            drawEventsOnTimeTable(eventsToday, g2);
        }

        // Example: Drawing events (you can replace this with actual event rendering logic)
        //g2.setColor(Color.BLUE);
        //g2.fillRect(COLUMN_WIDTH, intervalHeight * 2, COLUMN_WIDTH, intervalHeight* 3); // Example event block
        //g2.setColor(Color.GREEN);
        //g2.fillRect(COLUMN_WIDTH, intervalHeight *14, COLUMN_WIDTH, intervalHeight*(16-14));


    }

    // triggered by drawEventsOnTimeTable
    public void drawEventsOnTimeTable(ArrayList<DailyEvent> a, Graphics2D g2){
        for(DailyEvent event: a){
            //LocalTime startOfDay = LocalTime.of(0,0); compareTo()
            // gör så att användaren kna flytta på en rektangel sen men bara tvärs över
            LocalTime startTime = event.getStartTime();
            LocalTime endTime = event.getEndTime();
            int y0Coordinate = startTime.getHour()  * intervalHeight * 2;
            int y1Coordinate = endTime.getHour()  * intervalHeight * 2;
            int heightOfTask;


            if((startTime.getMinute() % 30)!= 0){
                y0Coordinate = getStartCoordinate(startTime);
            }
            if((endTime.getMinute() % 30)!= 0){
                y1Coordinate = getEndCoordinate(endTime);
            }
            if (startTime.getMinute() == 30){
                y0Coordinate = intervalHeight * ( 2 * startTime.getHour() + 1);
            }

            if (endTime.getMinute() == 30){
                y1Coordinate = intervalHeight * (2 * endTime.getHour() + 1);
            }
            heightOfTask = y1Coordinate - y0Coordinate;


            Color eventColour = event.getColourOfEvent();

            // Desaturate the color (reduce saturation)
            float[] hsbValues = Color.RGBtoHSB(eventColour.getRed(), eventColour.getGreen(), eventColour.getBlue(), null);
            Color desaturatedColor = Color.getHSBColor(hsbValues[0], 0.3f, hsbValues[2]); // Adjust saturation (0.3f for less saturation)

            // Set the opacity (alpha) of the color (less opaque)
            Color lessOpaqueColor = new Color(desaturatedColor.getRed(), desaturatedColor.getGreen(), desaturatedColor.getBlue(), 150); // Alpha value (0-255)

            //repaint(COLUMN_WIDTH,y0Coordinate,COLUMN_WIDTH,heightOfTask);
            g2.setColor(lessOpaqueColor);
            g2.fillRect(COLUMN_WIDTH, y0Coordinate, COLUMN_WIDTH, heightOfTask);
            g2.setColor(Color.BLACK);
            g2.drawString(event.toString(), COLUMN_WIDTH + 10, y0Coordinate + 20);
            g2.setColor(event.getColourOfEvent()); // Set border color
            g2.drawRect(COLUMN_WIDTH, y0Coordinate, COLUMN_WIDTH, heightOfTask); // Draw border

            // -----test ------- //
            JPanel block = new JPanel();
            JLabel lb = new JLabel("WASSUP");
            block.setBounds(COLUMN_WIDTH,y0Coordinate + 50,COLUMN_WIDTH,heightOfTask);
            block.setBackground(lessOpaqueColor);
            block.add(lb);




            // -----test ------ //
        }

    }

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
