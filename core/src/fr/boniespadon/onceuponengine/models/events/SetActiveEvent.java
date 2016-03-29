package fr.boniespadon.onceuponengine.models.events;

import fr.boniespadon.onceuponengine.models.sprites.Sprite;
import fr.boniespadon.onceuponengine.GameManager;

/**
 * Activates or de-activates a Sprite.
 * It determines if a Sprite will be displayed
 *
 * @author Mathieu
 *
 * @version 0.1
 */
public class SetActiveEvent extends Event {

    /**
     * Name of the Sprite that will be activated or de-activated
     *
     * @see Sprite#name
     */
    private String spriteName;

    /**
     * Sprite that will be activated or de-activated
     */
    private Sprite concernedSprite;

    /**
     * Determines if the Sprite will be activated or de-activated
     */
    private boolean active;

    /**
     * Creates a new SetActiveEvent
     *
     * @param id
     *        Id of the Event
     * @param additionalData
     *        The additional data must contain
     *              - true if the Sprite is to be activated, false otherwise
     */
    public SetActiveEvent(String id, Object[] additionalData)
    {
        super(id, additionalData);

        //additionalData contains name
        spriteName = additionalData[0].toString();
        active = Boolean.parseBoolean(additionalData[1].toString());
    }

    /**
     * Activates or de-activates the Sprite
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

        //retrieve Sprite,
        concernedSprite = GameManager.getCurrentTableau().getSpriteFromName(spriteName);

        if (concernedSprite == GameManager.getCurrentTableau().getHeldItem())
            GameManager.getCurrentTableau().setHeldItem(null);

        concernedSprite.setActive(active);

        hasRun = true;
    }
}
