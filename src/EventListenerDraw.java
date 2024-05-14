import java.awt.*;
import java.time.LocalTime;
import java.util.HashMap;

public interface EventListenerDraw {

    void drawEvent(HashMap<LocalTime, DailyEvent> a);


}
