import java.awt.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;

public interface EventListenerDraw {

    void drawEvent(ArrayList<DailyEvent> a);

    void removeEvent(ArrayList<DailyEvent> a);


}
