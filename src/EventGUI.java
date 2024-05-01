import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.LocalTime;
import java.util.*;
import java.time.LocalDate;

public class EventGUI extends JFrame {
    private EventListener eventListener;
    private LocalDate date;

    /**
     * sends copy of this map to day
     */
    private HashMap<LocalTime,DailyEvent> tasksThisDay = new HashMap<LocalTime, DailyEvent>();
    private HashMap<LocalTime, DailyEvent> timeOfEvent;
    private JLabel title = new JLabel("Add Event");
    private JLabel nameOfEvent, type, start,end;
    private JComboBox<String> typeComboBox;
    private JTextArea description;
    private JButton addEventButton = new JButton("Add Event");

    private LocalTime userInputedTime;
    private DailyEvent userInputedEvent;

    private String[] timesHH = new String[34];
    private String[] timesMM = new String[60];


    private JPanel titleOfEventPanel, typeOfEventPanel, startAndEndPanel, descriptionPanel;
    private JTextField titleOfEvent, typeOfEvent, start1,start2, end1, end2; // typeOfevent ska va en såndär meny man markerar
    //default ska vara en task
    private JTextField[] arrayOfNeccText = new JTextField[3];
    private JFrame taskFrame = new JFrame();
    /**
     * This GUi is similar to apples kalender och det jag gör är egentligen
     * en panel där man lägger in tasks
     * Den kommer att skicka data eller om jag använder getTask()
     *
     */


    public EventGUI(EventListener day) {

        for(int i = 0; i<24; i++){
            this.timesHH[i] = String.valueOf(i);
        }
        for(int i = 0; i<10; i++){
            for(int j = 0; i<1; i++){
                for(int k = 0; j<10; j++){
                    this.timesHH[i] = ""+j+k;
                    i++;

                }

            }
        }


        for(int i = 0; i<60; i++){
            for(int j = 0; j<6; j++){
                for(int k = 0; k<10;k++ ){
                    this.timesMM[i] = ""+j+k;
                    i++;
                }

            }
        }
        this.eventListener = day;


        // Initialize components
        nameOfEvent = new JLabel("Event Name:");
        type = new JLabel("Event Type:");
        start = new JLabel("Start Time:");
        end = new JLabel("End Time:");

        // Set up taskFrame
        taskFrame.setTitle("Events for " + date);
        taskFrame.setSize(500, 500);
        taskFrame.setBackground(Color.WHITE);
        taskFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Set up title panel
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(new Color(230, 230, 250));
        title.setFont(new Font("Georgia", Font.BOLD, 32));
        titlePanel.add(title, BorderLayout.WEST);

        // Set up button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(addEventButton);
        buttonPanel.setBackground(new Color(230, 230, 250));
        titlePanel.add(buttonPanel, BorderLayout.EAST);

        // Add title panel to NORTH and button panel to EAST of taskFrame content pane
        taskFrame.getContentPane().add(titlePanel, BorderLayout.NORTH);

        // Set up mainPanel with GridBagLayout
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets(5, 10, 5, 10);

        // Add components to mainPanel using GridBagConstraints
        mainPanel.add(nameOfEvent, gbc);
        gbc.gridx = 1;
        titleOfEvent = new JTextField(20); // Initialize text field
        mainPanel.add(titleOfEvent, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(type, gbc);
        gbc.gridx = 1;
        String[] eventTypeOptions = {"Task", "Event", "Chore"};
        typeComboBox = new JComboBox<>(eventTypeOptions);
        mainPanel.add(typeComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        mainPanel.add(start, gbc);
        gbc.gridx = 1;
        start1 = new JTextField(5); // Initialize text field
        mainPanel.add(start1, gbc);
        gbc.gridx = 2;
        start2 = new JTextField(5);
        mainPanel.add(start2, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        mainPanel.add(end, gbc);
        gbc.gridx = 1;
        end1 = new JTextField(5);
        mainPanel.add(end1,gbc);
        gbc.gridx = 2;
        end2 = new JTextField(5);
        mainPanel.add(end2, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        JLabel descriptionOfEvent = new JLabel("Description: ");
        mainPanel.add(descriptionOfEvent, gbc);
        gbc.gridx = 1;
        description = new JTextArea(10,20);
        mainPanel.add(description, gbc);


        // Add mainPanel to CENTER of taskFrame content pane
        taskFrame.getContentPane().add(mainPanel, BorderLayout.CENTER);

        // Make taskFrame visible
        taskFrame.setVisible(true);


        /**
         * addbutton logic
         * använd stream kanske?
         */
        addEventButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                DailyEvent newEVent;
                /**
                 * if some fields are filled user can add else it will send a JDialog saying all fields are not filled
                 * Jag vill egentligen att allt ska nollstälals så man kan fortsätta till man är nöjd. Och när den stängs så stängs inte allt
                 * Bara det fönstret där man lägger till
                 */
                if (!titleOfEvent.getText().isEmpty() && !start1.getText().isEmpty() && !start2.getText().isEmpty() && !end1.getText().isEmpty() && !end2.getText().isEmpty()) {
                    System.out.println("hej"); //printar så denna funkar men den printar alltid när jah trycker tyvärr
                    addEvent(day);
                    dispose();
                }else {
                    // Display error message if required fields are empty
                    JOptionPane.showMessageDialog(null, "Please fill in all required fields.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }

        });
    }


    /**
     * This listens actionlistener to the add event butto
     * Only visible if three of the main fields are filled
     * Returns a map, m
     * Vill att den ska skicka till Day, kanske om
     * @return
     */
    public void addEvent(EventListener day){
        String title = titleOfEvent.getText();
        String type = typeComboBox.getItemAt(typeComboBox.getSelectedIndex());
        LocalTime start = getTime(getTimeString(start1,start2));
        LocalTime end = getTime(getTimeString(end1,end2));
        String descriptionE = description.getText();
        DailyEvent newEvent = new DailyEvent(title, type, start, end, descriptionE);
        // Parse the inputed start and end time
        // add to map
        //LocalTime time = LocalTime.now();
        /**
         * Use a stream that sends to the Dage and updates
         * Or send the DayData as a parameter and then call it DayData.update()
         * Tänk typ att Day skickar sin parameter typ EVents som är en map. Om jag sen kallar på typ Day.sortData() så
         * Kan prova stram annts
         */

        tasksThisDay.put(start, newEvent);
        day.onEventAdded(title, type, start,end, descriptionE);

    }
    /**
     * Gör så att den översätter strängar till LocalTime ist
     */
    public String getTimeString(JTextField a, JTextField b){
        if(legalInputHourTimes(a.getText()) && legalInputMinuteTimes(b.getText())){
            if(a.getText().length() == 1){
                return "0"+a.getText() +":"+ b.getText();
            }
            System.out.println("yes sir");
            return a.getText() +":"+b.getText();
        }
        // JOptionPane.showMessageDialog(null, "Please fill in all required fields.", "Error", JOptionPane.ERROR_MESSAGE);
        /**
         * sends an error duvet typ error fönstret
         */
        return null;

    }

    public LocalTime getTime(String timeString){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return LocalTime.parse(timeString, formatter);
    }


    public boolean legalInputHourTimes(String a){
        for(String x : timesHH){
            //if(a.charAt(0) == 0){
            //}
            if(x.equals(a)){
                return true;
            }
        }
        return false;
    }

    public boolean legalInputMinuteTimes(String a){
        for(String x : timesMM){

            if(x.equals(a)){
                return true;
            }
        }
        return false;
    }

    public HashMap<LocalTime, DailyEvent> getTasksThisDay(){
        return tasksThisDay;
    }

    public static void main(String[] args) {
        // Example usage: create a new EventGUI frame
        LocalDate currentDate = LocalDate.now();
        Day day = new Day(currentDate);
        SwingUtilities.invokeLater(() -> new EventGUI(day));
    }
}