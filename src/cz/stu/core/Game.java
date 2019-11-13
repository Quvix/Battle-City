package cz.stu.core;

import cz.stu.input.KeyInput;
import cz.stu.state.GameStateManager;
import cz.stu.state.State;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class Game implements Runnable {
    private static final int UPDATES_PER_SECOND = 30;
    private static final double UPDATE_INTERVAL = 1_000. / UPDATES_PER_SECOND * 1_000_000;
    private static final int MAX_FRAMESKIP = 5;
    private static final String TITLE = "Tanky";
    public static final Dimension CANVAS_SIZE = new Dimension(1280, 720);
    private boolean running = false;
    private Thread thread;
    private Display display;
    private BufferStrategy bs;
    private GameStateManager gsm;

    public Game() {
        init();
        start();
    }

    private void init() {
        display = new Display(TITLE, CANVAS_SIZE);

        display.getCanvas().addKeyListener(new KeyInput());
        gsm = GameStateManager.getInstance();
        gsm.changeState(State.PLAY);
    }


    @Override
    public void run() {
        long timer = System.currentTimeMillis();;
        long nextUpdate = System.nanoTime();
        int frames = 0;
        int updates = 0;
        int skippedFrames;
        float interpolation;

        while (running) {
            skippedFrames = 0;
            while (System.nanoTime() > nextUpdate && skippedFrames < MAX_FRAMESKIP) {
                update();
                updates++;
                nextUpdate += UPDATE_INTERVAL;
                skippedFrames++;
                //System.out.println("UPDATED!");
            }
            interpolation = 1 - Math.min(1.0f, (float) ((nextUpdate - System.nanoTime()) / UPDATE_INTERVAL) );
            //System.out.println(interpolation);
            this.render(interpolation);
            frames++;

            // statistics every second
            if(System.currentTimeMillis() - timer > 1000){
                timer += 1000;
                System.out.println(String.format("UPS: %s, FPS: %s", updates, frames));
                frames = 0;
                updates = 0;
            }
        }
    }

    public synchronized void start(){
        if(running)
            return;
        running = true;
        thread = new Thread(this);
        thread.start();
    }

    public synchronized void stop(){
        if(!running)
            return;
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void render(float interpolation) {
        if(bs == null) {
            initBufferStrategy();
            return;
        }
        // Render single frame
        do {
            // The following loop ensures that the contents of the drawing buffer
            // are consistent in case the underlying surface was recreated
            do {
                // Get a new graphics context every time through the loop
                // to make sure the strategy is validated
                Graphics2D graphics = (Graphics2D) bs.getDrawGraphics();

                graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

                // Render to graphics
                // render black background to prevent black magic
                graphics.setColor(Color.BLACK);
                graphics.fillRect(0, 0, CANVAS_SIZE.width, CANVAS_SIZE.height);

                gsm.render(graphics, interpolation);

                // Dispose the graphics
                graphics.dispose();

                // Repeat the rendering if the drawing buffer contents
                // were restored
            } while (bs.contentsRestored());

            // Display the buffer
            bs.show();

            // Repeat the rendering if the drawing buffer was lost
        } while (bs.contentsLost());
    }

    private void update() {
        gsm.update();
    }

    // Initialize double buffering strategy
    private void initBufferStrategy() {
        display.getCanvas().createBufferStrategy(2);
        bs = display.getCanvas().getBufferStrategy();
    }
}
