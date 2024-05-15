import java.time.LocalTime;
import java.util.HashMap;

public interface EventListenerRemove {

    void addRemovableEvent(HashMap<LocalTime, DailyEvent> a);

}
