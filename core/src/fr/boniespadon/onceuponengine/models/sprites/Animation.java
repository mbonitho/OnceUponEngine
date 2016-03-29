package fr.boniespadon.onceuponengine.models.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

/**
 * Animation of a Sprite,
 * which decides which part of the Sprite is displayed at a given time.
 *
 * @see Sprite
 *
 * @author Mathieu
 *
 * @version 0.1
 */
public class Animation
{
    /**
     * Portions of the Sprite's texture
     */
    private Array<TextureRegion> frames;

    /**
     * Animation speed
     */
    private float maxFrameTime;

    /**
     * The duration that a frame has been on the screen befor switching to the next
     */
    private float currentFrameTime;

    /**
     * The number of frames in the Animation
     */
    private int frameCount;

    /**
     * The number of the frame that is currently displayed
     */
    private int frame;

    /**
     * Creates an Animation with the specified Texture, frame count and animation speed
     *
     * @param txt
     *        Texture of the animation
     * @param frameCount
     *        Number of frames in the animation
     * @param cycleTime
     *        Animation speed
     */
    public Animation(Texture txt, int frameCount, float cycleTime)
    {
        TextureRegion region = new TextureRegion(txt);

        frames = new Array<TextureRegion>();

        int frameWidth = region.getRegionWidth() / frameCount;

        for (int i = 0; i < frameCount; i++)
            frames.add(new TextureRegion(region, i * frameWidth, 0, frameWidth, region.getRegionHeight()));

        this.frameCount = frameCount;
        maxFrameTime = cycleTime;
        frame = 0;
    }

    /**
     * Updates the animation : displays the correct frame at a given moment,
     * wait the right amount of time, and then displays the next frame
     *
     * @param dt
     *        Game DeltaTime
     */
    public void update(float dt)
    {
        currentFrameTime += dt;

        if (currentFrameTime > maxFrameTime) {
            frame++;
            currentFrameTime = 0;
        }

        if (frame >= frameCount)
            frame = 0;
    }

    /**
     * Returns the currently displayed portion of the Sprite's texture, aka the frame
     * @return
     */
    public TextureRegion getFrame() {
        return frames.get(frame);
    }

    /**
     * Cleanly disposes of the animation
     */
    public void dispose()
    {
        for (TextureRegion tr : frames)
            tr.getTexture().dispose();

    }
}
