import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HamburgerMenu extends JFrame {

    private JPanel mainPanel;
    private JPanel sideMenuPanel;
    private JButton hamburgerButton;

    public HamburgerMenu() {
        setTitle("Hamburger Menu GUI");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create the main panel
        mainPanel = new JPanel(new BorderLayout());
        getContentPane().add(mainPanel);

        // Create the side menu panel
        sideMenuPanel = new JPanel();
        sideMenuPanel.setBackground(Color.LIGHT_GRAY);
        sideMenuPanel.setPreferredSize(new Dimension(200, getHeight()));
        mainPanel.add(sideMenuPanel, BorderLayout.EAST);
        sideMenuPanel.setVisible(false);

        // Create the hamburger button
        hamburgerButton = new JButton("☰");
        hamburgerButton.setFont(new Font("Arial", Font.BOLD, 20));
        hamburgerButton.setPreferredSize(new Dimension(40, 40)); // Square button
        hamburgerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toggleSideMenu();
            }
        });
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT)); // Align button to the right
        buttonPanel.add(hamburgerButton);
        mainPanel.add(buttonPanel, BorderLayout.NORTH);

        // Add some content to the main panel
        JLabel contentLabel = new JLabel("Main Content Area");
        contentLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(contentLabel, BorderLayout.CENTER);
    }

    private void toggleSideMenu() {
        if (sideMenuPanel.isVisible()) {
            sideMenuPanel.setVisible(false);
        } else {
            sideMenuPanel.setVisible(true);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                HamburgerMenu gui = new HamburgerMenu();
                gui.setVisible(true);
            }
        });
    }
}
