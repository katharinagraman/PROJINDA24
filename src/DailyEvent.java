import java.awt.*;
import java.time.LocalTime;

public class DailyEvent {
    /**
     * Class keeping track of daily events
     * Each event has an identifier important for other feautures
     */

    private String type;
    private String titleOfEvent, description;
    private LocalTime startTime, endTime;
    private int month, day, year;

    private boolean isPainted = false;


    /**
     * User will input a title of their event
     * They will pick a date
     * They have to pick a start and end time
     * @param titleOfEvent
     * @param startTime
     * @param endTime
     */
    public DailyEvent(String titleOfEvent, String id, LocalTime startTime, LocalTime endTime, String description) {
        this.titleOfEvent = titleOfEvent;
        this.type = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.description = description;


    }

    public String getTitle(){
        return titleOfEvent;
    }

    public String getType(){
        return type;
    }

    /**
     * Returns the name the event user has chosen
     * @return titleOfEvent
     */

    public LocalTime getStartTime(){
        return startTime;
    }

    public LocalTime getEndTime(){
        return endTime;
    }

    public String getDescription(){
        return description;
    }
    public Color getColourOfEvent(){
        return switch (this.type) {
            case "task" -> Color.BLUE;
            case "event" -> Color.RED;
            case "chore" -> Color.GREEN;
            default -> Color.BLACK;
        };

    }

    public void setPaintedTrue(){
        isPainted = true;
    }

    public void setDescription(String s){
        description = s;
    }

    public boolean isPainted(){
        return isPainted;
    }


    @Override
    public String toString(){
        return titleOfEvent + " " + startTime+ " - "+ endTime;

    }
}
