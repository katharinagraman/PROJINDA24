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

public class DeleteEventGUI extends JFrame implements EventListenerRemove {
    /**
     * This class handles user tasks
     */
    private EventListener eventListener;
    private EventListenerDraw dailyCalendar;


    /**
     * sends copy of this map to day
     */
    private ArrayList<DailyEvent> eventsToday = new ArrayList<>();


    private JLabel title = new JLabel("Remove Events");

    private JPanel mainPanel;
    private ArrayList eventButtonList = new ArrayList<>();

    private JFrame taskFrame = new JFrame();

    public void addRemovableEvent(HashMap<LocalTime, DailyEvent> a){
        System.out.println("Not used");

    }
    /**
     * The constructor will take the day object that calls it
     *
     */
    public DeleteEventGUI(Day day) {

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
            this.eventsToday = day.getDailyEvents();
            for(DailyEvent event: eventsToday){
                JButton removeEventButton = new JButton(event.toString());
                removeEventButton.addActionListener(e->removeEvent(removeEventButton));
                removeEventButton.setVisible(true);
                mainPanel.add(removeEventButton, BorderLayout.CENTER);
            }
        }



        // Add mainPanel to CENTER of taskFrame content pane
        taskFrame.getContentPane().add(mainPanel, BorderLayout.CENTER);

        // Make taskFrame visible
        taskFrame.setVisible(true);


    }

    public void updateDisplay(){
        System.out.println("Hej");
        //mainPanel.removeAll();
        mainPanel.setLayout(new GridLayout(this.eventsToday.size(),0,0,10));
        if(!eventsToday.isEmpty()){
            for(DailyEvent event: eventsToday){
                JButton eventButton = new JButton(eventsToday.toString());
                eventButton.setSize(mainPanel.getWidth(), 20);
                eventButton.setVisible(true);
                eventButton.addActionListener(e -> removeEvent(eventButton));
            }

        }
        mainPanel.revalidate();
        mainPanel.repaint();
    }




    /**
     * This listens actionlistener to the add event butto
     * Only visible if three of the main fields are filled
     * Returns a map, m
     * Vill att den ska skicka till Day, kanske om
     * @return
     */
    public void removeEvent(JButton self){
        // måste ta bort mapen nog och skapa en identifier som är
        mainPanel.remove(self);

        updateDisplay();
    }


    public void setTasks(ArrayList<DailyEvent> a){
        eventsToday = a;

    }






}