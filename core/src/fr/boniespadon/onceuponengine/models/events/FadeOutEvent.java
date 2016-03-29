package fr.boniespadon.onceuponengine.models.events;

import java.util.ArrayList;
import java.util.List;

import fr.boniespadon.onceuponengine.models.sprites.Sprite;
import fr.boniespadon.onceuponengine.GameManager;

/**
 * Progressively changes the opacity of the Sprite from 1 to 0
 *
 * @author Mathieu
 */
public class FadeOutEvent extends Event{

    /**
     * Name of the Sprite to be faded in. if "*" is provided, all Sprites will be faded out
     *
     * @see Sprite#name
     */
    private String spriteName;

    /**
     * Opacity of the concerned Sprite at a given time
     */
    private float currentSpriteOpacity;

    /**
     * Fade out speed
     */
    private float speed;

    /**
     * Creates a new FadeInEvent
     *
     * @param id
     *        Id of the Event
     * @param additionalData
     *        The additional data must contain
     *              - the name of the Sprite whose opacity will be changed
     *              - the speed of the Fade effect
     */
    public FadeOutEvent(String id, Object[] additionalData)
    {
        super(id, additionalData);
        currentSpriteOpacity = 1.0f;

        //additionalData contains name of sprite to fade; fade in speed
        spriteName = additionalData[0].toString();
        speed = Float.parseFloat(additionalData[1].toString());
    }

    /**
     * Changes the Sprite opacity from 1 to 0
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

        //System.out.println("Fade in... (speed " + speed + ", opacity :" + owner.getOpacity() + ")");
        List<Sprite> spritesToFade = new ArrayList<Sprite>();

        if (spriteName.equals("*"))
            spritesToFade = GameManager.getCurrentTableau().getSprites();
        else
            spritesToFade.add(GameManager.getCurrentTableau().getSpriteFromName(spriteName));

        if (currentSpriteOpacity > 0f)
        {
            currentSpriteOpacity -= speed;
        }
        else
        {
            currentSpriteOpacity = 0f;
            hasRun = true;
        }

        for (Sprite spriteToFade : spritesToFade)
            spriteToFade.setOpacity(currentSpriteOpacity);
    }
}
