package fr.boniespadon.onceuponengine.models.events;

import com.badlogic.gdx.math.Vector2;

import fr.boniespadon.onceuponengine.models.sprites.Sprite;
import fr.boniespadon.onceuponengine.GameManager;

/**
 * "Teleports" a Sprite from its current coordinates to target coordinates
 *
 * The movement is not progressive, unlike MoveEvent
 *
 * @author Mathieu
 *
 * @version 0.1
 */
public class SetPositionEvent extends Event {

    /**
     * Name of the Sprite that will be teleported
     *
     * @see Sprite#position
     */
    private String spriteName;

    /**
     * New position of the Sprite
     */
    private Vector2 targetCoordinates;

    /**
     * Creates a new SetActiveEvent
     *
     * @param id
     *        Id of the Event
     * @param additionalData
     *        The additional data must contain
     *              - a float specifying target X position
     *              - a float specifying target Y position
     */
    public SetPositionEvent(String id, Object[] additionalData)
    {
        super(id, additionalData);

        //additionalData contains sprite name; target coordinates
        spriteName = additionalData[0].toString();
        float xTarget = Float.parseFloat(additionalData[1].toString());
        float yTarget = Float.parseFloat(additionalData[2].toString());

        targetCoordinates = new Vector2(xTarget, yTarget);
    }

    /**
     * "Teleports" the Sprite
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
        spriteToMove.setPosition(targetCoordinates.x, targetCoordinates.y);

        hasRun = true;
    }
}
