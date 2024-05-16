import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.LocalTime;
import java.util.*;
import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EventGUI extends JFrame {
    /**
     * This class handles user tasks
     */
    private EventListener eventListener;
    private EventListenerDraw dailyCalendar;

    private EventListenerRemove removeList;
    private LocalDate date;

    /**
     * sends copy of this map to day
     */
    private ArrayList<DailyEvent> dailyEventsToday = new ArrayList<>();
    private HashMap<LocalTime, DailyEvent> timeOfEvent;
    private JLabel title = new JLabel("Add Event");
    private JLabel nameOfEvent, type, start,end;
    private JComboBox<String> typeComboBox;
    private JTextArea description;
    private JButton addEventButton = new JButton("Add Event");

    private String[] timesHH = new String[34];
    private String[] timesMM = new String[60];

    private JTextField titleOfEvent, typeOfEvent, start1,start2, end1, end2; // typeOfevent ska va en såndär meny man markerar

    private JTextField[] arrayOfNeccTextF = new JTextField[4];
    private JFrame taskFrame = new JFrame();


    /**
     * The constructor will take the day object that calls it
     * @param day Day object that calls it
     */
    public EventGUI(EventListener day, EventListenerDraw dailyCalendar) {


        this.arrayOfNeccTextF[0] = start1;
        this.arrayOfNeccTextF[1] = start2;
        this.arrayOfNeccTextF[2] = end1;
        this.arrayOfNeccTextF[3] = end2;

        /**
         * Filling the arrays with the times that a user may input
         */
        for(int i = 0; i<24; i++){
            this.timesHH[i] = String.valueOf(i);
        }
        String[] zeroInts = new String[10];
        for(int i = 0; i<1; i++){
            for(int j = 0; j<10; j++){
                zeroInts[j] = i +""+ j;

            }
        }
        for(int i=24; i<timesHH.length; i++){
            for(int j = 0; i<zeroInts.length; j++){
                timesHH[i] = zeroInts[j];
                i++;

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
        this.dailyCalendar = dailyCalendar;

        // Initialise Labels
        nameOfEvent = new JLabel("Event Name:");
        type = new JLabel("Event Type:");
        start = new JLabel("Start Time:");
        end = new JLabel("End Time:");

        // Set up task handling Window
        taskFrame.setTitle("Events for ");
        taskFrame.setSize(500, 500);
        taskFrame.setBackground(Color.WHITE);
        taskFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Set up title panel
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(new Color(230, 230, 250));
        title.setFont(new Font("Georgia", Font.BOLD, 32));
        titlePanel.add(title, BorderLayout.WEST);

        // Set up button panel used for placing the button at desired postion
        // I  find it easier
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(addEventButton);
        buttonPanel.setBackground(new Color(230, 230, 250));
        titlePanel.add(buttonPanel, BorderLayout.EAST);

        // Add title panel to NORTH and button panel to EAST of taskFrame content pane
        taskFrame.getContentPane().add(titlePanel, BorderLayout.NORTH);

        // Set up mainPanel with GridBagLayout
        // AI aided
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
        String[] eventTypeOptions = {"Task", "Event", "Chore", "School"};
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
         * Kanske buggen kan lösas om jag använder
         * addActionListener(e -> toggleSideMenu());
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
                    // logic for non numbers
                    if(legalInputHourTimes(start1.getText()) && legalInputHourTimes(end1.getText()) && legalInputMinuteTimes(start2.getText()) && legalInputMinuteTimes(end2.getText())){

                        LocalTime start = getTime(getTimeString(start1,start2));
                        LocalTime end = getTime(getTimeString(end1,end2));

                        if (startTimeIsNotSoonerThanEnd(start, end)){
                            addEvent(day);

                        }else{
                            JOptionPane.showMessageDialog(null, "Events cannot start sooner than they end", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                        if(start1.getText().length() > 2 || start2.getText().length() > 2 || end1.getText().length() > 2 || end2.getText().length() > 2){
                            JOptionPane.showMessageDialog(null, "To many characters in the time field", "Error", JOptionPane.ERROR_MESSAGE);
                        }

                    }else{
                        JOptionPane.showMessageDialog(null, "Time is wrongly formatted", "Error", JOptionPane.ERROR_MESSAGE);
                    }
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
        String type = typeComboBox.getItemAt(typeComboBox.getSelectedIndex()).toLowerCase();
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

        dailyEventsToday.add(newEvent);
        // puts the tasks in the map created in Day
        day.onEventAdded(title, type, start,end, descriptionE);
        // lägga in ävem här för daily design?
        //dailyCalendar.setEventsToday();
        //dailyCalendar.drawEvent(dailyEventsToday);
        //removeList.addRemovableEvent(tasksThisDay);
        dispose();


    }


    public boolean startTimeIsNotSoonerThanEnd(LocalTime start, LocalTime end){
        if(start.isBefore(end)){
            return true;
        }
        return false;

    }
    /**
     * Gör så att den översätter strängar till LocalTime ist
     */
    public String getTimeString(JTextField a, JTextField b){
        if(legalInputHourTimes(a.getText()) && legalInputMinuteTimes(b.getText())){
            if(a.getText().length() == 1){
                System.out.println("yes sir");
                return "0"+a.getText() +":"+ b.getText();
            }

            return a.getText() +":"+b.getText();
        }

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
        //mst finnas ett bättre sätt
        for(String x : timesHH){
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




}