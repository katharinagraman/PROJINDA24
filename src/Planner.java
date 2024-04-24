import javax.swing.*;


/**
 * This is the homepage. On the homepage you will see this month's calendar.
 * If you would like to switch month you would have to tap a button
 */
public class Planner{


    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        // ------- Creating homepage main frame ------------- //
        // comments are from jav tutorial at https://www.javatpoint.com/java-swing
        JFrame homeFrame =new JFrame("My Planner");//creating instance of JFrame
        homeFrame.setSize(1920,1080);//400 width and 500 height
        homeFrame.setLayout(null);//using no layout managers
        homeFrame.setVisible(true);//making the frame visible



        JButton b=new JButton("click");//creating instance of JButton
        b.setBounds(130,100,100, 40);//x axis, y axis, width, height
        homeFrame.add(b);//adding button in JFrame


    }
}


