import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Shop extends JFrame {

    private Game game;
    private GamePanel gamePanel;

    public void setGamePanelReference(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    private void setFocusToGamePanel() {
        if (gamePanel != null) {
            gamePanel.requestFocusInWindow();
        }
    }

    public Shop(Game game) {
        this.game = game;

        setLayout(new GridLayout(2, 2));

        // Add four purchase boxes
        for (int i = 0; i < 4; i++) {
            add(createPurchaseBox(i + 1));
        }

        setTitle("Shop");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(false);

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                if (gamePanel != null) {
                    gamePanel.requestFocusInWindow();
                }
                dispose();
            }
        });
    }

    private JPanel createPurchaseBox(int boxNumber) {
        JPanel purchaseBox = new JPanel();
        purchaseBox.setLayout(new BorderLayout());

        String spriteFileName = getSpriteFileName(boxNumber);
        ImageIcon spriteIcon = new ImageIcon("lib/" + spriteFileName);
        JLabel spriteLabel = new JLabel(spriteIcon);
        spriteLabel.setHorizontalAlignment(JLabel.CENTER);
        purchaseBox.add(spriteLabel, BorderLayout.CENTER);

        JButton equippedButton = new JButton("Equip");
        equippedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean changed = changeBirdSprite(spriteFileName);
                if (changed) {
                    JOptionPane.showMessageDialog(null, "Item in Box " + boxNumber + " equipped! Bird sprite changed.");
                } else {
                    JOptionPane.showMessageDialog(null, "Item in Box " + boxNumber + " equipped! Bird sprite remains the same.");
                }
            }
        });
        purchaseBox.add(equippedButton, BorderLayout.SOUTH);

        return purchaseBox;
    }

    private String getSpriteFileName(int boxNumber) {
        switch (boxNumber) {
            //Shop car sprites display
            case 1:
                return "car.png";
            case 2:
                return "car2.png";
            case 3:
                return "car3.png";
            case 4:
                return "car4.png";
            default:
                return "";
        }
    }

    private boolean changeBirdSprite(String newSpriteFileName) {
        if (game != null) {
            game.setBirdSprite(newSpriteFileName);
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Game game = new Game();
            Shop shop = new Shop(game);

            GamePanel gamePanel = new GamePanel(game);
            gamePanel.setGame(game);
            shop.setGamePanelReference(gamePanel);

            game.setShop(shop);

            shop.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            shop.setVisible(true);
        });
    }

}