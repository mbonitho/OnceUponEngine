package fr.boniespadon.onceuponengine.models.events;

import fr.boniespadon.onceuponengine.models.sprites.Sprite;
import fr.boniespadon.onceuponengine.GameManager;

/**
 * Puts a Sprite offscreen and sets its activated state to false
 *
 * @author Mathieu
 *
 * @version 0.1
 */
public class SpriteDisappearsEvent extends Event {

    /**
     * Name of the Sprite that will be put offscreen
     *
     * @see Sprite#name
     */
    private String spriteName;

    /**
     * Sprite that will be put offscreen
     */
    private Sprite concernedSprite;

    /**
     * Creates a new SpriteDisappearsEvent
     *
     * @param id
     *        Id of the Event
     * @param additionalData
     *        The additional data must contain
     *              - the name of the Sprite whose texture will be changed
     *              - the new Texture File name
     *              - The number of animation
     */
    public SpriteDisappearsEvent(String id, Object[] additionalData)
    {
        super(id, additionalData);

        //additionalData contains name
        spriteName = additionalData[0].toString();
    }

    /**
     * Puts the Sprite offscreen and set its active state to false
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

        System.out.println(spriteName + " disappears");

        //retrieve Sprite,
        concernedSprite = GameManager.getCurrentTableau().getSpriteFromName(spriteName);

        if (concernedSprite == GameManager.getCurrentTableau().getHeldItem())
            GameManager.getCurrentTableau().setHeldItem(null);

        //teleports the Sprite offscreen
        concernedSprite.setPosition(10000f, 10000f);
        concernedSprite.setActive(false);

        hasRun = true;
    }
}
