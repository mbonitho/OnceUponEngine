package fr.boniespadon.onceuponengine.models.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * A graphic element to be displayed on a Tableau, which can be concerned by Events
 *
 * @see fr.boniespadon.onceuponengine.models.Tableau
 * @see fr.boniespadon.onceuponengine.models.events.Event
 *
 * @author Mathieu
 *
 * @version 0.1
 */
public class Sprite {

    /**
     * Name of the Sprite
     * Mainly used by Events to specify to which Sprite they apply
     *
     * @see Sprite#getName()
     */
    private String name;

    /**
     * Initial position (X, Y) of the Sprite. This is the position the Sprite was created at
     *
     * @see Sprite#getInitialPosition()
     */
    private Vector2 initialPosition;

    /**
     * Current position (X, Y) of the Sprite
     *
     * @see Sprite#getPosition()
     */
    private Vector2 position;

    /**
     * Represents the clickable zone of the Sprite
     *
     * @see Sprite#getBounds()
     */
    private Rectangle bounds;

    /**
     * Image drawn on the Tableau when the Sprite is not held
     *
     * @see Sprite#getTexture()
     */
    private Texture texture;

    /**
     * Image drawn on the Tableau when the Sprite is held
     *
     * @see Sprite#getIconTexture()
     */
    private Texture iconTexture;

    /**
     * Opacity of the Sprite between 0.0f and 1.0f
     *
     * @see Sprite#getOpacity()
     */
    private float opacity;

    /**
     * Determines if the Sprite is displayed and managed on the Tableau
     *
     * @see Sprite#getActive()
     */
    protected boolean active;

    /**
     * The number of frames in the animation if the Sprite is animated
     * To create a non animated Sprite, this should be set to 1
     *
     */
    private int stepCount;

    /**
     * The animation of this Sprite,
     * which decides which part of the Sprite is displayed at a given time.
     *
     * @see Animation
     */
    private Animation animation;

    /**
     * Creates a new Sprite
     *
     * @param name
     *        Name of the Sprite
     * @param x
     *        Initial horizontal position
     * @param y
     *        Initial vertical position
     * @param texName
     *        Image name
     * @param opacity
     *        Opacity of the Sprite
     * @param steps
     *        Number of steps of the animation (1 if the Sprite is not animated)
     */
    public Sprite(String name, float x, float y, String texName, float opacity, int steps)
    {
        this.name = name;
        this.position = new Vector2(x, y);
        this.initialPosition = new Vector2(x, y);

        if (texName != "")
        {
            this.texture = new Texture(texName);
            trySetIconTexture(texName);
        }

        this.opacity = opacity;
        this.active = true;

        try
        {
            bounds = new Rectangle(x, y, texture.getWidth(), texture.getHeight());
        }
        catch (NullPointerException exc)
        {
            //if there is no texture, bounds cover the entire screen
            bounds = new Rectangle(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        }

        stepCount = steps;

        if (this.getTexture() != null)
        {
            Texture txt = this.getTexture().getTexture();
            animation = new Animation(txt, steps, 0.2f); //todo animation speed
            bounds = new Rectangle(x, y, txt.getWidth() / steps, txt.getHeight());
        }
        else
            bounds = new Rectangle(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    /**
     * Updates the Sprites animation
     *
     * @see Animation
     *
     * @param deltaTime
     */
    public void update(float deltaTime)
    {
        if (animation != null)
            animation.update(deltaTime);
    }

    /**
     * Try attributing an icon texture to a sprite, if such texture exists
     *
     * @see Sprite#iconTexture
     *
     * @param texName
     */
    private void trySetIconTexture(String texName)
    {
        String texIconName = getTextureIconFileName(texName);
        try {
            this.iconTexture = new Texture(texIconName);
        }
        catch (Exception e)
        {
            System.out.println("Unable to find " + texIconName);
            this.iconTexture = null;
        }
    }

    /**
     * Returns the name of the icon file (eg: for "tree.png", it would be "tree-ico.png".
     * It may not correspond to an existing image
     * @param texName
     *        Name of the main texture of the Sprite
     * @return Name of the icon file (eg: for "tree.png", it would be "tree-ico.png".
     * It may not correspond to an existing image
     */
    private String getTextureIconFileName(String texName)
    {
        String name = texName.substring(0, texName.lastIndexOf('.'));
        String extension = texName.substring(texName.lastIndexOf('.'));
        return name + "-ico" + extension;
    }

    /**
     * Returns the portion of the Sprite texture currently displayed
     *
     * @see Animation
     *
     * @return portion of the Sprite texture currently displayed
     */
    public TextureRegion getFrame()
    {
        return animation.getFrame();
    }

    /**
     * Sets the Animation to be used for this Sprite
     *
     * @param anim
     *        Animation to be used for this Sprite
     */
    public void setAnimation(Animation anim)
    {
        this.animation = anim;
    }

    /**
     * Returns the current position of the Sprite
     * @return current position of the Sprite
     */
    public Vector2 getPosition() { return position; }

    /**
     * Returns the initial position of the Sprite
     * @return initial position of the Sprite
     */
    public Vector2 getInitialPosition() { return initialPosition; }

    /**
     * Sets the position of the Sprite
     *
     * @param x Horizontal position
     * @param y Vertical position
     */
    public void setPosition(float x, float y)
    {
        position.x = x;
        position.y = y;

        bounds.setX(x);
        bounds.setY(y);
    }

    /**
     * Returns the portion of the texture of the Sprite that is currently displayed
     *
     * @return portion of the texture of the Sprite that is currently displayed
     */
    public TextureRegion getTexture() {
        if (animation != null)
            return animation.getFrame();
        else
            if (texture != null)
                return new TextureRegion(texture);
            else
                return null;
    }

    /**
     * Returns the texture used when the sprite is held
     *
     * @return texture used when the sprite is held
     */
    public TextureRegion getIconTexture()
    {
        if (iconTexture != null)
            return new TextureRegion(iconTexture);
        else
            return getTexture();
    }

    /**
     * Returns the bounds of the Sprite
     * @return bounds of the Sprite
     */
    public Rectangle getBounds() { return bounds; }

    /**
     * Sets the bounds of the Sprite
     *
     * @param bounds
     */
    public void setBounds(Rectangle bounds) { this.bounds = bounds; }

    /**
     * Returns the name of the Sprite
     *
     * @return name of the Sprite
     */
    public String getName() { return name; }

    /**
     * Returns the opacity of the Sprite
     *
     * @return opacity of the Sprite
     */
    public float getOpacity() { return opacity; }

    /**
     * Sets the opacity of the Sprite
     *
     * @param opacity between 0.0f and 1.0f
     */
    public void setOpacity(float opacity) { this.opacity = opacity; }

    /**
     * Returns the active state of the sprite
     *
     * @return true if the Sprite is active, false otherwise
     */
    public boolean getActive() { return this.active; }

    /**
     * Activates or de-activates the Sprite
     *
     * @param active active state of the Sprite
     */
    public void setActive(boolean active ){ this.active = active; }

    /**
     * Cleanly disposes of the Sprite
     */
    public void dispose()
    {
        animation.dispose();
    }

    @Override
    public String toString()
    {
        String rsc = texture != null ? " tex existe " : " pas de tex ";

        return name + rsc;
    }
}
