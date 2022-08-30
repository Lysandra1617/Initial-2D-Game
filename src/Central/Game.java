package Central;

public class Game implements Runnable {
    final Thread GAME_LOOP;

    Game() {
        GAME_LOOP = new Thread(this);
    }

    void init() {
        Main.WINDOW.init();
        Main.INTERFACE.init();
    }

    void start() {
        GAME_LOOP.start();
    }

    void draw() {
        Main.WINDOW.repaint();
    }

    void update() {
        Main.INTERFACE.update();
    }

    @Override
    public void run() {
        final double RUN_INTERVAL = 1000000000.0/60;
        double delta = 0;
        long currentTime = 0, priorTime = System.nanoTime();

        while (GAME_LOOP != null) {
            //Evaluating the passed time
            currentTime = System.nanoTime();
            delta += (currentTime - priorTime) / RUN_INTERVAL;
            priorTime = currentTime;

            //If enough time has passed
            if (delta >= 1) {
                //Update and then draw the game
                update();
                draw();
                delta--;
            }
        }
    }
}
