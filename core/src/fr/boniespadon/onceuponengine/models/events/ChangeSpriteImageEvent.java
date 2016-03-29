package fr.boniespadon.onceuponengine.models.events;

import fr.boniespadon.onceuponengine.models.sprites.Sprite;
import fr.boniespadon.onceuponengine.models.sprites.Animation;
import fr.boniespadon.onceuponengine.GameManager;

/**
 * Changes the Texture of an Sprite. The new Texture can possess a
 * different number of animation steps than the previous one
 *
 * @see Sprite
 * @see Animation
 *
 * @author Mathieu
 */
public class ChangeSpriteImageEvent extends Event {

    /**
     * Name of the Sprite whose Texture will be changed
     *
     * @see Sprite#name
     */
    private String spriteName;

    /**
     * Sprite whose Texture will be changed
     *
     * @see Sprite#texture
     */
    private Sprite concernedSprite;

    /**
     * Short file name of the new Texture
     *
     * @see Sprite#texture
     */
    private String newTextureFileName;

    /**
     * Number of steps of the new Sprite Texture
     *
     * @see Animation#frameCount
     */
    private int newTextureSteps;

    /**
     * Creates a new ChangeSpriteImageEvent
     *
     * @param id
     *        Id of the Event
     * @param additionalData
     *        The additional data must contain
     *              - the name of the Sprite whose texture will be changed
     *              - the new Texture File name
     *              - The number of animation
     */
    public ChangeSpriteImageEvent(String id, Object[] additionalData)
    {
        super(id, additionalData);

        //additionalData contains sprite name and new texture fileName
        spriteName = additionalData[0].toString();
        newTextureFileName = "sprites/" + additionalData[1].toString();
        newTextureSteps = Integer.parseInt(additionalData[2].toString());

    }

    /**
     * Changes the Sprite image
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

        //retrieve Sprite, make it invisible and remove all of its events
        concernedSprite = GameManager.getCurrentTableau().getSpriteFromName(spriteName);

        System.out.println("Changing sprite image of "
                + concernedSprite.getName() + " to " + newTextureFileName);

        //if sprite to change is held item, drop that item
        if (concernedSprite == GameManager.getCurrentTableau().getHeldItem())
            GameManager.getCurrentTableau().setHeldItem(null);

        GameManager.RemoveSpriteFromCurrentTableau(concernedSprite);
        Sprite newSprite = new Sprite(concernedSprite.getName(),
                concernedSprite.getPosition().x,
                concernedSprite.getPosition().y,
                newTextureFileName,
                concernedSprite.getOpacity(), newTextureSteps);

        newSprite.setAnimation(new Animation(newSprite.getTexture().getTexture(), newTextureSteps, 0.5f));

        GameManager.AddSpriteToCurrentTableau(newSprite);

        concernedSprite.setActive(false);
        concernedSprite.setPosition(-1000, -1000);

        hasRun = true;
    }
}
