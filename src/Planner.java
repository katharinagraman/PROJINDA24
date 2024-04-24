import javax.swing.*;
import java.awt.*;


/**
 * Kanske att man ber om användarens namn osen står det xxx planner
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

        Color lavender = new Color(230, 230, 250);
        homeFrame.getContentPane().setBackground(lavender);

        //JButton b=new JButton("click");//creating instance of JButton
        //b.setBounds(130,100,100, 40);//x axis, y axis, width, height
        //homeFrame.add(b);//adding button in JFrame


        // ------ Labels and titles --------- //
        JLabel titleLabel = new JLabel("My Planner");
        titleLabel.setBounds(20,20, 300,300);
        titleLabel.setFont(new Font("Georgia", Font.PLAIN, 72)); // Set font to Georgia with size 16
        homeFrame.add(titleLabel);

    }
}


