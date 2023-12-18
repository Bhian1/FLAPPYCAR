import javax.swing.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Game {

    private Shop shop;
    public static final int PIPE_DELAY = 100;

    private Boolean paused;

    private int pauseDelay;
    private int restartDelay;
    private int pipeDelay;

    private Bird bird;
    private ArrayList<Pipe> pipes;
    private Keyboard keyboard;

    public int score;
    public Boolean gameover;
    public Boolean started;

    private String equippedSpriteFileName;

    public Game() {
        keyboard = Keyboard.getInstance();
        restart();
        this.shop = null;
    }

    public void restart() {
        paused = false;
        started = false;
        gameover = false;

        score = 0;
        pauseDelay = 0;
        restartDelay = 0;
        pipeDelay = 0;

        bird = new Bird();


        if (equippedSpriteFileName != null) {
            bird.setSprite(equippedSpriteFileName);
        }

        pipes = new ArrayList<Pipe>();
    }

    public void update() {
        watchForStart();

        if (!started)
            return;

        watchForPause();
        watchForReset();

        if (paused)
            return;

        bird.update();

        if (gameover)
            return;

        movePipes();
        checkForCollisions();
    }

    public ArrayList<Render> getRenders() {
        ArrayList<Render> renders = new ArrayList<Render>();
        String backgroundImagePath;
        if (score >= 80) {
            backgroundImagePath = "lib/beach.png";
        } else if (score >= 50) {
            backgroundImagePath = "lib/desert2.png";
        } else if (score >= 30) {
            backgroundImagePath = "lib/jungle.jpg";
        } else if (score >= 10) {
            backgroundImagePath = "lib/nightCity.png";
        } else {
            backgroundImagePath = "lib/background.png";
        }
        renders.add(new Render(0, 0, backgroundImagePath));
        for (Pipe pipe : pipes)
            renders.add(pipe.getRender());
        renders.add(new Render(0, 0, "lib/foreground.png"));
        renders.add(bird.getRender());
        return renders;
    }

    private void watchForStart() {
        if (!started && keyboard.isDown(KeyEvent.VK_SPACE)) {
            started = true;
        }
    }

    private void watchForPause() {
        if (pauseDelay > 0)
            pauseDelay--;

        if (keyboard.isDown(KeyEvent.VK_P) && pauseDelay <= 0) {
            paused = !paused;
            pauseDelay = 10;
        }
    }

    private void watchForReset() {
        if (restartDelay > 0)
            restartDelay--;

        if (keyboard.isDown(KeyEvent.VK_R) && restartDelay <= 0) {
            restart();
            restartDelay = 10;
        }
    }

    private void movePipes() {
        pipeDelay--;

        if (pipeDelay < 0) {
            pipeDelay = PIPE_DELAY;
            Pipe northPipe = null;
            Pipe southPipe = null;

            for (Pipe pipe : pipes) {
                if (pipe.x - pipe.width < 0) {
                    if (northPipe == null) {
                        northPipe = pipe;
                    } else if (southPipe == null) {
                        southPipe = pipe;
                        break;
                    }
                }
            }

            if (northPipe == null) {
                Pipe pipe = new Pipe("north");
                pipes.add(pipe);
                northPipe = pipe;
            } else {
                northPipe.reset();
            }

            if (southPipe == null) {
                Pipe pipe = new Pipe("south");
                pipes.add(pipe);
                southPipe = pipe;
            } else {
                southPipe.reset();
            }

            northPipe.y = southPipe.y + southPipe.height + 175;
        }

        for (Pipe pipe : pipes) {
            pipe.update();
        }
    }

    private void checkForCollisions() {

        for (Pipe pipe : pipes) {
            if (pipe.collides(bird.x, bird.y, bird.width, bird.height)) {
                gameover = true;
                bird.dead = true;
            } else if (pipe.x == bird.x && pipe.orientation.equalsIgnoreCase("south")) {
                score++;
            }
        }
        
        if (bird.y + bird.height > App.HEIGHT - 80) {
            gameover = true;
            bird.y = App.HEIGHT - 80 - bird.height;
        }
    }

    public void setBirdSprite(String spriteFileName) {
        bird.setSprite(spriteFileName);
        equippedSpriteFileName = spriteFileName;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public String getEquippedSpriteFileName() {
        return equippedSpriteFileName;
    }
}