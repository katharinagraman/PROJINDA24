import javax.swing.*;
import java.awt.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.LocalTime;
import java.util.*;
import java.time.LocalDate;

public class EventGUI extends JPanel {
    private LocalDate date;

    /**
     * sends copy of this map to day
     */
    private HashMap<LocalTime,DailyEvent> tasksThisDay = new HashMap<LocalTime, DailyEvent>();
    private HashMap<LocalTime, DailyEvent> timeOfEvent;
    private JLabel title = new JLabel("Add Event");
    private JLabel nameOfEvent, type, start,end;
    private JButton addEventButton = new JButton("Add Event");

    private LocalTime userInputedTime;
    private DailyEvent userInputedEvent;

    private JPanel titleOfEventPanel, typeOfEventPanel, startAndEndPanel, descriptionPanel;
    private JTextField titleOfEvent, typeOfEvent, startAndEnd; // typeOfevent ska va en såndär meny man markerar
    //default ska vara en task
    private JTextField[] arrayOfNeccText = new JTextField[3];
    private JFrame taskFrame = new JFrame();
    /**
     * This GUi is similar to apples kalender och det jag gör är egentligen
     * en panel där man lägger in tasks
     * Den kommer att skicka data eller om jag använder getTask()
     *
     */
    public EventGUI(LocalDate date){
        this.date = date;
        JLabel taskFrameLabel = new JLabel("Events for " + date);
        taskFrame.setSize(1000,1000);
        taskFrame.setBackground(Color.WHITE);
        taskFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        /**
         * action listener on button add!!! Men bara synlig om man fyllt vissa fält
         */

        addEventButton.setFont(new Font("Georgia", Font.BOLD, 20));
        addEventButton.setPreferredSize(new Dimension(40, 40));
        addEventButton.setVisible(false);
        if(necessaryFieldsFilled()){

        addEventButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClickedToAddEvent(java.awt.event.MouseEvent evt) {
                // Open a new frame or perform an action when a day is clicked
                addEvent(tasksThisDay, date);
            }
        });
        }

        //_---------------
        // Create title panel for layout här är nog den där fula ramen jag inte ens bad om högst upp
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(new Color(230, 230, 250)); // Set background color of title panel
        titlePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Add padding to title panel

        // Add title label to the title panel
        title.setFont(new Font("Georgia", Font.BOLD, 32));
        titlePanel.add(title, BorderLayout.WEST);

        // Add hamburger button to the title panel (align right)
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(addEventButton);
        buttonPanel.setBackground(new Color(230, 230, 250));
        titlePanel.add(buttonPanel, BorderLayout.EAST);

        // Add the title panel to the content pane at the NORTH position
        taskFrame.getContentPane().add(titlePanel, BorderLayout.NORTH);

        // Add side menu panel to the EAST position
        taskFrame.getContentPane().add(titlePanel, BorderLayout.EAST);



        //----- textfields to receive user input
        titleOfEvent = new JTextField(100);
        typeOfEvent = new JTextField(100);
        startAndEnd = new JTextField(100);

        titleOfEventPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        typeOfEventPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        startAndEndPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 10, 5, 10); // Padding

        mainPanel.add(nameOfEvent, gbc);
        gbc.gridx = 1;
        mainPanel.add(titleOfEvent, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(type, gbc);
        gbc.gridx = 1;
        mainPanel.add(typeOfEvent, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        mainPanel.add(start, gbc);
        gbc.gridx = 1;
        mainPanel.add(end, gbc);

    }

    public void addEvent(HashMap<LocalTime, DailyEvent> a ,LocalDate date){
        String titleE = titleOfEvent.getText();
        String type = typeOfEvent.getText();
        String start = startAndEnd.getText();

        // Define a DateTimeFormatter for parsing time in "HH:mm" format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        // Parse the time string into a LocalTime object
        LocalTime inputTime = LocalTime.parse(start, formatter);

        DailyEvent event = new DailyEvent(titleE, type,start,start);
        tasksThisDay.put(inputTime,event);
        /**
         * will be the given days on field
         */
        a.put(inputTime, event);
    }

    /**
     * adds all necessary data which is later sent back to the dayFrame in the calendar
     * @param a
     */
    public void addEventData(Map<LocalDate, HashMap<LocalTime, DailyEvent>> a){
        timeOfEvent.put(userInputedTime, userInputedEvent);
        a.put(date, timeOfEvent);

    }

    public boolean necessaryFieldsFilled(){
        arrayOfNeccText[0] = titleOfEvent;
        arrayOfNeccText[1] = typeOfEvent;
        arrayOfNeccText[2] = startAndEnd;
        for (int i = 0; i<arrayOfNeccText.length; i++){
            String inputText = arrayOfNeccText[i].getText();
            if (inputText.isEmpty()){
                return false;

            }
        }
        addEventButton.setVisible(true);
        return true;


    }

    /**
     * getTask()
     * returns task type, task name, startime of task, end time of task, and description
     */
}
