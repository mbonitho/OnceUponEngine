package fr.boniespadon.onceuponengine;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;
import java.util.List;

import fr.boniespadon.onceuponengine.helper.XMLTableauxParser;
import fr.boniespadon.onceuponengine.models.Tableau;
import fr.boniespadon.onceuponengine.models.sprites.Sprite;

/**
 * Manages the game (update, render, play music...)
 *
 * @author Mathieu
 *
 * @version 0.1
 */
public class GameManager {

    /**
     * Background music currently playing
     *
     * @see GameManager#setBgm(Music)
     */
    private static Music bgm;

    /**
     * The Tableau currently displayed with its Sprites and Events
     *
     * @see Tableau
     */
    private static Tableau currentTableau;

    /**
     * List of Sprites that are to be added to the current Tableau
     *
     * @see GameManager#AddSpriteToCurrentTableau(Sprite)
     */
    private static List<Sprite> spritesToAdd = new ArrayList<Sprite>();

    /**
     * List of Sprites that are to be removed from the current Tableau
     *
     * @see GameManager#RemoveSpriteFromCurrentTableau(Sprite)
     */
    private static List<Sprite> spritesToRem = new ArrayList<Sprite>();

    /**
     * Method called at each frame : Updates every sprite on the screen and checks their events
     *
     * @param deltaTime
     *        Game time between two frames
     */
    public static void update(float deltaTime)
    {
        //Before looping over sprites, check if some sprites need to be added or removed
        for (Sprite s : spritesToAdd)
            currentTableau.getSprites().add(s);
        spritesToAdd.clear();

        for (Sprite s : spritesToRem)
            currentTableau.getSprites().remove(s);
        spritesToRem.clear();

        currentTableau.CheckEvents(deltaTime);

        for (Sprite s : currentTableau.getSprites())
            if (s != currentTableau.getHeldItem())
                s.update(deltaTime);
    }

    /**
     * Draws every Sprite form the current tableau
     *
     * The last sprite to be drawn is always the held item if the tableau is a onceuponengine Tableau
     *
     * @see Tableau#isGameTableau
     * @see Tableau#heldItem
     *
     * @param sb
     *          batch on which the Sprites will be drawn
     */
    public static void render(SpriteBatch sb) {
        sb.begin();

        for (Sprite s : currentTableau.getSprites())
        {
            //skip help item : it will be drawn above all others
            if (s == currentTableau.getHeldItem())
                continue;

            //skip inactive items
            if (!s.getActive())
                continue;

            if (s.getTexture() != null)
            {
                DrawSprite(sb, s);
            }
        }

        if (currentTableau.getIsGameTableau())
            drawHeldItemPlaceHolder(sb);

        if (currentTableau.getHeldItem() != null)
            DrawSprite(sb, currentTableau.getHeldItem());

        sb.end();
    }

    /**
     * Draws the heldItem placehoder sprite on the upper-right corner of the screen
     *
     * @param sb
     *        Batch on which the placeholder for heldItem will be drawn
     *
     * @see Tableau#heldItem
     */
    private static void drawHeldItemPlaceHolder(SpriteBatch sb)
    {
        Texture tex = new Texture("sprites/magiland/heldItem.png");

        float texWidth = tex.getWidth() * Game.RATIO_X;
        float texHeight = tex.getHeight() * Game.RATIO_Y;

        float posX = Game.WIDTH - texWidth - 30 * Game.RATIO_X;
        float posY = Game.HEIGHT - texHeight - 30 * Game.RATIO_X;

        sb.draw(tex, posX, posY, texWidth, texHeight);
    }

    /**
     * Draws a sprite on the batch
     *
     * @param sb
     *        Batch on which the placeholder for heldItem will be drawn
     * @param sprite
     *        Sprite to draw
     */
    private static void DrawSprite(SpriteBatch sb, Sprite sprite)
    {
        sb.setColor(1.0f, 1.0f, 1.0f, sprite.getOpacity());

        Rectangle bounds = new Rectangle(sprite.getPosition().x * Game.RATIO_X,
                sprite.getPosition().y * Game.RATIO_Y,
                sprite.getFrame().getRegionWidth() * Game.RATIO_X,
                sprite.getFrame().getRegionHeight() * Game.RATIO_Y);

        sprite.setBounds(bounds);

        TextureRegion tex = sprite.getTexture();
        if (sprite == currentTableau.getHeldItem())
            tex = sprite.getIconTexture();

        float spriteWidth = 0;
        float spriteHeight = 0;

        if (sprite != currentTableau.getHeldItem())
        {
            spriteWidth = sprite.getFrame().getRegionWidth() * Game.RATIO_X;
            spriteHeight = sprite.getFrame().getRegionHeight() * Game.RATIO_Y;
        }
        else
        {
            spriteWidth = sprite.getIconTexture().getRegionWidth() * Game.RATIO_X;
            spriteHeight = sprite.getIconTexture().getRegionHeight() * Game.RATIO_Y;
        }

        sb.draw(tex,
                sprite.getPosition().x * Game.RATIO_X,
                sprite.getPosition().y * Game.RATIO_Y,
                spriteWidth,
                spriteHeight);
    }

    /**
     * Adds a Sprite to the current Tableau so it will be drawn and have its Events checked
     * @param sprite
     *        Sprite to be added
     */
    public static void AddSpriteToCurrentTableau(Sprite sprite)
    {
        spritesToAdd.add(sprite);
    }

    /**
     * Removes a Sprite from the current Tableau
     *
     * @param sprite
     *        Sprite to be removed
     */
    public static void RemoveSpriteFromCurrentTableau(Sprite sprite)
    {
        //the sprite is only added to the collection if it exists
        if (currentTableau.getSprites().contains(sprite))
            spritesToRem.add(sprite);
    }

    /**
     * Returns the Tableau that is currently on screen
     * @return the Tableau that is currently on screen
     */
    public static Tableau getCurrentTableau()
    {
        return currentTableau;
    }

    /**
     * Changes the onceuponengine's current Tableau to the one whose name is specified.
     * Called when the onceuponengine is launched, and also every time a transition occurs
     *
     * @see fr.boniespadon.onceuponengine.models.events.TransitionEvent
     *
     * @param TabShortName
     *        Short name (with extension) of the tableau to transition to
     */
    public static void setCurrentTableau(String TabShortName)
    {
        currentTableau = XMLTableauxParser.ParseXMLTableau("tableaux/" + TabShortName);
    }

    /**
     * CHanges the games Background music (BGM). If another BGM is playing, it's stopped first.
     *
     * @param mus
     *        Music to play
     */
    public static void setBgm(Music mus)
    {
        if (bgm != null)
        {
            bgm.stop();
            bgm.dispose();
        }

        bgm = mus;
        bgm.play();
    }
}
