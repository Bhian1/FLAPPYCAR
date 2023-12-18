import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class GamePanel extends JPanel implements Runnable {

    private Game game;

    public GamePanel(Game game) {
        this.game = game;
        setFocusable(true);
        requestFocusInWindow();
        addKeyListener(Keyboard.getInstance());
        new Thread(this).start();
    }

    public void update() {
        game.update();
        repaint();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2D = (Graphics2D) g;
        for (Render r : game.getRenders())
            if (r.transform != null)
                g2D.drawImage(r.image, r.transform, null);
            else
                g.drawImage(r.image, r.x, r.y, null);

        g2D.setColor(Color.BLACK);

        if (!game.started) {
            g2D.setFont(new Font("TimesRoman", Font.PLAIN, 20));
            g2D.drawString("Press SPACE to start", 150, 240);
        } else {
            g2D.setFont(new Font("TimesRoman", Font.PLAIN, 24));
            g2D.drawString(Integer.toString(game.score), 10, 440);
        }

        if (game.gameover) {
            g2D.setFont(new Font("TimesRoman", Font.PLAIN, 20));
            g2D.drawString("Press R to restart Score: " + game.score, 150, 240);
        }
    }

    public void run() {
        try {
            while (true) {
                update();
                Thread.sleep(18);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public void setFocusToGamePanel() {
        if (!this.hasFocus()) {
            this.requestFocusInWindow();
        }
    }
}