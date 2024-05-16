import javax.swing.*;

public class CalendarBlock extends JPanel {
    boolean isPainted = false;

    public void setPainted(){
        isPainted = !isPainted;
    }

    public boolean isPainted(){
        return isPainted;
    }
}
