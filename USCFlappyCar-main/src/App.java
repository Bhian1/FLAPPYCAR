import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class App {

    public static int WIDTH = 500;
    public static int HEIGHT = 520;

    public static void main(String[] args) {
        Game game = new Game();
        GamePanel panel = new GamePanel(game);

        JFrame frame = new JFrame();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        Keyboard keyboard = Keyboard.getInstance();
        frame.addKeyListener(keyboard);
        frame.add(panel);

        // Add a shop button
        JButton shopButton = new JButton("Shop");
        shopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // To open shop
                Shop shop = new Shop(game);
                shop.setGamePanelReference(panel); // Pass the reference to GamePanel
                shop.setVisible(true);

                shop.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                        panel.setFocusable(true);
                        panel.requestFocusInWindow();
                    }
                });
            }
        });


        JPanel buttonPanel = new JPanel();
        buttonPanel.add(shopButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.setResizable(false);
        frame.setSize(WIDTH, HEIGHT);
    }
}