import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.*;

public class Day extends JFrame{

    //private Map<Date, DailyEvent> events = new HashMap();
    private HashMap<LocalDate,DailyEvent> tasksThisDay = new HashMap<LocalDate, DailyEvent>();


    private JFrame mainFrame;
    private JPanel calendarPanel;



    public Day(LocalDate date){
        mainFrame = new JFrame("Day: " + date);
        mainFrame.setSize(1920, 1080);

        JPanel dayPanel = new JPanel();
        JLabel dayLabel = new JLabel("Events for " + date);
        dayLabel.setFont(new Font("Georgia", Font.BOLD, 32));

        mainFrame.add(dayPanel);
        mainFrame.setVisible(true);

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
        /**
         * Här ska
         */


    }

    /**
     * Opens the taskframe where user can add tasks
     * This logic is handled in EventGUI
     * @param date
     */
    public void addEvent(LocalDate date){
        JPanel taskFrame = new EventGUI(date);
        taskThatDay = taskFrame.getTasksThatDay();

    }
    /**
     * Opens the taskframe where user can add tasks
     * This logic is handled in EventGUI
     * @param date
     */
    public void addEvent(LocalDate date){
        JPanel taskFrame = new EventGUI(date);
        tasksThisDay = taskFrame.addEvent();

    }

    /*
    Class that is the GUI of how each day looks, when opening a new day in Calendar P class we will
    create
    create Day() new Day("".....) so this is the GUI for tha
     */




}
