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

    /**
     * Returns the name the event user has chosen
     * @return titleOfEvent
     */
    private String getTitleOfEvent(){
        return titleOfEvent;

    }




    @Override
    public String toString(){
        return titleOfEvent;

    }
}
