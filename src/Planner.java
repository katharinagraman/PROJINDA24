import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class Planner extends JFrame {

    private Map<Date, ArrayList<DailyEvent>> EventsByDate = new HashMap<>();

    /**
     * homePage where student starts
     * also displays a calendar
     */
    private JPanel sideMenuPanel;
    private JButton hamburgerButton;

    private int chosenYear, chosenMonth, chosenDay, currentYear, currentMonth, currentDay;

    private JButton calendarDates;

    private JLabel labelMonth, labelYear;
    private JLabel title = new JLabel("My Planner");

    private GridLayout calendarLayout = new GridLayout(6, 7);

    public Planner() {
        setTitle("My Planner");
        setSize(1920,1080); // Set an initial size for demonstration
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set the background color of the Planner frame to lavender
        getContentPane().setBackground(new Color(230, 230, 250));



        // ----------
        // Create side menu panel (initially hidden)
        sideMenuPanel = new JPanel();
        sideMenuPanel.setBackground(Color.WHITE);
        sideMenuPanel.setPreferredSize(new Dimension(200, getHeight())); // Adjust width as needed
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
        title.setFont(new Font("Georgia", Font.BOLD, 32));
        titlePanel.add(title, BorderLayout.WEST);

        // Add hamburger button to the title panel (align right)
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(hamburgerButton);
        buttonPanel.setBackground(new Color(230, 230, 250));
        titlePanel.add(buttonPanel, BorderLayout.EAST);

        // Add the title panel to the content pane at the NORTH position
        getContentPane().add(titlePanel, BorderLayout.NORTH);

        // Add side menu panel to the EAST position
        getContentPane().add(sideMenuPanel, BorderLayout.EAST);

        // ------- Calendar -------- //

        JPanel calendarPanel = new JPanel(new BorderLayout());
        CalendarP calendar = new CalendarP();
        calendarPanel.add(calendar.getCalendarPanel(), BorderLayout.CENTER);

        getContentPane().add(calendarPanel, BorderLayout.WEST);


        // ----Hamburger -------//
        LocalDate today = LocalDate.now();
        HashMap<LocalDate,Day> mapOfDayFrames = calendar.getMapOfDaysWithTasks();
        Day todaysFrame = mapOfDayFrames.get(today);


        if(todaysFrame!= null){
            HashMap<LocalTime, DailyEvent> mapforDay = todaysFrame.getTasksThisDay();
            String[] a = todaysFrame.arrayForHamburger();

            // Simulate adding components to sideMenuPanel
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.anchor = GridBagConstraints.NORTH;
            gbc.insets = new Insets(5, 10, 5, 10);

            for (int i = 0; i < 10; i++) {
                JLabel eventLabel = new JLabel(a[i]);
                gbc.gridx = 0;
                gbc.gridy = i;
                sideMenuPanel.add(eventLabel, gbc);
            }

        }else{
            JLabel noTasksToday = new JLabel("No Tasks Today :)");
            sideMenuPanel.add(noTasksToday);
        }

    }



    private void toggleSideMenu() {
        sideMenuPanel.setVisible(!sideMenuPanel.isVisible());
        revalidate(); // Recalculate layout
        repaint();    // Redraw components
    }





    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Planner planner = new Planner();
            planner.setVisible(true);
        });
    }
}
