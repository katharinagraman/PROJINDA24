import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;

public interface EventListenerRemove {

    void removeEvent(int index);

    ArrayList<DailyEvent> getDailyEvents();
}
