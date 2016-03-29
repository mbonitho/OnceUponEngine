package fr.boniespadon.onceuponengine.models.events;

import com.badlogic.gdx.math.Vector2;

import java.util.Date;

import fr.boniespadon.onceuponengine.models.sprites.Sprite;
import fr.boniespadon.onceuponengine.GameManager;

/**
 * Moves a Sprites from its coordinates to target coordinates, at a certain speed
 *
 * @see Sprite
 *
 * @author Mathieu
 *
 * @version 0.1
 */
public class MoveEvent extends Event {

    /**
     * Name of the Sprite that will move
     *
     * @see Sprite#name
     */
    private String spriteName;

    /**
     * Target position of the Sprite
     */
    private Vector2 targetCoordinates;

    /**
     * Movement speed
     */
    private float speed;

    /**
     * Unused yet - Repeat movement indefinitely or not
     */
    private boolean loops;

    /**
     * Creates a new MoveEvent
     *
     * @param id
     *        Id of the Event
     * @param additionalData
     *        The additional data must contain
     *              - the name of the Sprite that will move
     *              - horizontal target position
     *              - vertical target position
     *              - movement speed
     */
    public MoveEvent(String id, Object[] additionalData)
    {
        super(id, additionalData);

        //additionalData contains sprite name; target coordinates; speed; loop_Y/N
        spriteName = additionalData[0].toString();
        float xTarget = Float.parseFloat(additionalData[1].toString());
        float yTarget = Float.parseFloat(additionalData[2].toString());

        speed = Float.parseFloat(additionalData[3].toString());
        loops = false; //Boolean.parseBoolean(additionalData[4].toString());
        targetCoordinates = new Vector2(xTarget, yTarget);
    }

    /**
     * Move concerned Sprite
     *
     * @param deltaTime
     *        Game time between two frames
     */
    @Override
    public void DoStuff(float deltaTime)
    {
        //If it is an auto event and it has already run, exit method
        if (trigger == TRIGGERS.auto && hasRun)
            return;

        //If it is a non-repeatable event and it has already run, exit method
        if (!repeatable && hasRun)
            return;

        Sprite spriteToMove = GameManager.getCurrentTableau().getSpriteFromName(spriteName);

        float currentX = spriteToMove.getPosition().x;
        float currentY = spriteToMove.getPosition().y;

        float moveX = currentX - targetCoordinates.x;
        float moveY = currentY - targetCoordinates.y;

        //todo classe pour les logs
        System.out.println((new Date()).toString() + "- Moving " + spriteToMove.getName()
                + " from (" + currentX + ";" + currentY
                + ") to (" + targetCoordinates.x + ";" + targetCoordinates.y + ")");

        if (moveX > 0)
        {
            currentX -= speed * deltaTime;
            if (currentX < targetCoordinates.x)
                currentX = targetCoordinates.x;
        }
        else if (moveX < 0)
        {
            currentX += speed * deltaTime;
            if (currentX > targetCoordinates.x)
                currentX = targetCoordinates.x;
        }

        if (moveY > 0)
        {
            currentY -= speed * deltaTime;
            if (currentY < targetCoordinates.y)
                currentY = targetCoordinates.y;
        }
        else if (moveY < 0)
        {
            currentY += speed * deltaTime;
            if (currentY > targetCoordinates.y)
                currentY = targetCoordinates.y;
        }

        spriteToMove.setPosition(currentX, currentY);

        if (spriteToMove.getPosition().x == targetCoordinates.x
                && spriteToMove.getPosition().y == targetCoordinates.y)
            hasRun = true;
    }
}
