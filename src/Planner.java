import javax.swing.*;
import java.awt.*;

public class Planner extends JFrame {
    private JPanel sideMenuPanel;
    private JButton hamburgerButton;

    private JLabel labelMonth, labelYear;
    private JLabel title = new JLabel("My Planner");

    public Planner() {
        setTitle("My Planner");
        setSize(1920,1080); // Set an initial size for demonstration
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set the background color of the Planner frame to lavender
        getContentPane().setBackground(new Color(230, 230, 250));

        // Create main content area
        JLabel contentLabel = new JLabel("Main Content Area", SwingConstants.CENTER);
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(contentLabel, BorderLayout.CENTER);

        // Create side menu panel (initially hidden)
        sideMenuPanel = new JPanel();
        sideMenuPanel.setBackground(Color.LIGHT_GRAY);
        sideMenuPanel.setPreferredSize(new Dimension(200, getHeight())); // Adjust width as needed
        sideMenuPanel.setVisible(false);

        // Create hamburger button
        hamburgerButton = new JButton("☰");
        hamburgerButton.setFont(new Font("Georgia", Font.BOLD, 20));
        hamburgerButton.setPreferredSize(new Dimension(40, 40));
        hamburgerButton.addActionListener(e -> toggleSideMenu());

        // Create title panel for layout
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(new Color(230, 230, 250)); // Set background color of title panel
        titlePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Add padding to title panel

        // Add title label to the title panel
        title.setFont(new Font("Georgia", Font.BOLD, 32));
        titlePanel.add(title, BorderLayout.WEST);

        // Add hamburger button to the title panel (align right)
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(hamburgerButton);
        buttonPanel.setBackground(new Color(230, 230, 250));
        titlePanel.add(buttonPanel, BorderLayout.EAST);

        // Add the title panel to the content pane at the NORTH position
        getContentPane().add(titlePanel, BorderLayout.NORTH);

        // Add side menu panel to the EAST position
        getContentPane().add(sideMenuPanel, BorderLayout.EAST);
    }

    private void toggleSideMenu() {
        sideMenuPanel.setVisible(!sideMenuPanel.isVisible());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Planner planner = new Planner();
            planner.setVisible(true);
        });
    }
}
