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

public class DeleteEventGUI extends JFrame{
    /**
     * This class handles user tasks
     */
    private EventListener day;
    private EventListenerDraw dailyCalendar;


    /**
     * sends copy of this map to day
     */
    private ArrayList<DailyEvent> eventsToday = new ArrayList<>();


    private JLabel title = new JLabel("Remove Events");
    private int removeIndex;

    private JPanel mainPanel;
    private ArrayList eventButtonList = new ArrayList<>();



    private JFrame taskFrame = new JFrame();

    //private EventListenerRemove day;

    public void addRemovableEvent(HashMap<LocalTime, DailyEvent> a){
        System.out.println("Not used");

    }
    /**
     * The constructor will take the day object that calls it
     *
     */
    public DeleteEventGUI(EventListener day, EventListenerDraw dailyCalendar) {
        this.day = day;
        this.dailyCalendar = dailyCalendar;
        taskFrame.setTitle("Delete events for ");
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
        buttonPanel.setBackground(new Color(230, 230, 250));
        titlePanel.add(buttonPanel, BorderLayout.EAST);

        // Add title panel to NORTH and button panel to EAST of taskFrame content pane
        taskFrame.getContentPane().add(titlePanel, BorderLayout.NORTH);

        // Set up mainPanel with GridBagLayout
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(this.eventsToday.size(),1,0,10));
        mainPanel.setVisible(true);
        mainPanel.setSize(taskFrame.getWidth(), taskFrame.getHeight() - titlePanel.getHeight());
        if(day.getDailyEvents()==null){
            System.out.println("null");
        }
        if (day.getDailyEvents()!=null){
           // this.eventsToday = day.getDailyEvents();
            for(DailyEvent event: day.getDailyEvents()){
                int i = 0;
                JButton removeEventButton = new JButton(event.toString());
                removeEventButton.addActionListener(e->removeEvent(removeEventButton));
                removeEventButton.setVisible(true);
                eventButtonList.add(i,removeEventButton);
                mainPanel.add(removeEventButton, BorderLayout.CENTER);
            }
        }



        // Add mainPanel to CENTER of taskFrame content pane
        taskFrame.getContentPane().add(mainPanel, BorderLayout.CENTER);

        // Make taskFrame visible
        taskFrame.setVisible(true);


    }

    public void updateDisplay() {
        mainPanel.removeAll();
        if (!day.getDailyEvents().isEmpty()) {
            sort(day.getDailyEvents()); // Sort events if needed
            mainPanel.setLayout(new GridLayout(eventsToday.size(), 1, 0, 10));

            for (DailyEvent event : day.getDailyEvents()) {
                JButton eventButton = new JButton(event.toString());
                eventButton.addActionListener(e -> removeEvent(eventButton));
                mainPanel.add(eventButton);
            }
        }


    }





    /**
     * This listens actionlistener to the add event butto
     * Only visible if three of the main fields are filled
     * Returns a map, m
     * Vill att den ska skicka till Day, kanske om
     * @return
     */
    public void removeEvent(JButton self){
        removeIndex = 0;
        // måste ta bort mapen nog och skapa en identifier som är

        for(int i = 0; i< day.getDailyEvents().size(); i++){
            if (self.getText().equals(day.getDailyEvents().get(i).toString())) {
                removeIndex = i;
                break;
            }
        }
        if(day.getDailyEvents().size() >1){
            day.removeEvent(removeIndex);
            eventButtonList.remove(removeIndex);
            //eventsToday.remove(removeIndex);
            mainPanel.remove(self);
        }else if (day.getDailyEvents().size() == 1){
            mainPanel.remove(self);
            eventButtonList.remove(0);
            day.removeEvent(0);
            //eventsToday.remove(0);
        }else{
            mainPanel.removeAll();
        }


        //-- remove index, update list in dailyCalendar, prompt the redraw--//

        //dailyCalendar.removeEvent(eventsToday);   //updates dailyCalendar's list
        updateDisplay();

        //mainPanel.remove(self);
        //updateDisplay();
    }


    public void setTasks(ArrayList<DailyEvent> a){
        eventsToday = a;
    }

    public void sort(ArrayList<DailyEvent> dailyEvents){
        int i, j;
        DailyEvent key;
        for (i = 1; i < dailyEvents.size(); i++) {
            key = dailyEvents.get(i);
            j = i - 1;

            // Move elements of arr[0..i-1],
            // that are greater than key,
            // to one position ahead of their
            // current position
            while (j >= 0 && dailyEvents.get(j).getStartTime().isAfter(key.getStartTime())) {
                dailyEvents.set(j + 1,dailyEvents.get(j));
                j = j - 1;
            }
            dailyEvents.set(j + 1, key);

        }
    }






}