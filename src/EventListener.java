import java.time.LocalTime;

public interface EventListener {
    void onEventAdded(String title, String type, LocalTime startTime, LocalTime endTime, String description);
}
