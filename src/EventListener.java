import java.time.LocalTime;
import java.util.ArrayList;

public interface EventListener {
    /**
     * This Interface was created in order to support adding an event
     * However I believe that it isn't necessary now
     * I asked AI and it recommended this
     * @param title
     * @param type
     * @param startTime
     * @param endTime
     * @param description
     */
    void onEventAdded(String title, String type, LocalTime startTime, LocalTime endTime, String description);

    ArrayList<DailyEvent> getDailyEvents();

    void removeEvent(int removeIndex);
}
