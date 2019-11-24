package cz.stu.gfx;

import cz.stu.core.Updatable;

import java.awt.image.BufferedImage;

public class Animation implements Updatable {
    private int ticksPerImage = 1;
    private int timer;
    private int index;
    private BufferedImage[] frames;
    private boolean looped = false;

    public Animation(BufferedImage[] frames) {
        this.ticksPerImage = ticksPerImage;
        this.frames = frames;
        if(frames.length <= 0) {
            throw new IllegalArgumentException("Can't create animation without frames");
        }
    }

    @Override
    public void update() {
        timer++;
        if(timer > ticksPerImage) {
            timer = 0;
            index++;
            if(index == frames.length && looped) {
                index = 0;
            }
        }
    }

    public BufferedImage getCurrentFrame() {
        return frames[Math.min(index, frames.length - 1)];
    }

    public void reset() {
        timer = 0;
        index = 0;
    }

    public int getTicksPerImage() {
        return ticksPerImage;
    }

    public void setTicksPerImage(int ticksPerImage) {
        this.ticksPerImage = ticksPerImage;
    }

    public boolean isLooped() {
        return looped;
    }

    public void setLooped(boolean looped) {
        this.looped = looped;
    }

    public boolean isFinished() {
        return index == frames.length && !looped;
    }
}
