package fr.boniespadon.onceuponengine.models.events;

import com.badlogic.gdx.math.Vector2;

import java.util.Date;

import fr.boniespadon.onceuponengine.Game;
import fr.boniespadon.onceuponengine.models.sprites.Sprite;
import fr.boniespadon.onceuponengine.GameManager;

/**
 * Marks a Sprite as held and adds it in the inventory
 * If another Sprite was held, it goes back to its initial position
 *
 * @see fr.boniespadon.onceuponengine.models.Tableau#heldItem
 *
 * @author Mathieu
 */
public class ItemAddedEvent extends Event {

    /**
     * Name of the Sprite who will be added
     *
     * @see Sprite#name
     */
    private String itemAddedName;

    /**
     * Creates a new ItemAddedEvent
     *
     * @param id
     *        Id of the Event
     * @param additionalData
     *        The additional data must contain
     *              - the name of the Sprite that will be added
     */
    public ItemAddedEvent(String id, Object[] additionalData)
    {
        super(id, additionalData);

        //additionalData contains necessary item name
        itemAddedName = additionalData[0].toString();
    }

    /**
     * Marks the concerned Sprite as the current Tableau held Item
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

        if (new Date().getTime() - GameManager.getCurrentTableau().getHeldItemChangedTime().getTime() < 100)
            return;

        //Item-added events are always repeatable and won't be affected if repeatable is set to false
        //If it is a non-repeatable event and it has already run, exit method
        //if (!repeatable && hasRun)
        //    return;

        if (GameManager.getCurrentTableau().ContainsItem(itemAddedName))
        {
            Sprite itemToAdd = GameManager.getCurrentTableau().getSpriteFromName(itemAddedName);

            Sprite heldItem = GameManager.getCurrentTableau().getHeldItem();

            //moving the item to the top-right corner
            if (heldItem != itemToAdd)
            {
                Vector2 heldItemPos =
                        new Vector2(Game.IMG_WIDTH - itemToAdd.getIconTexture().getTexture().getWidth() - 20,
                        Game.IMG_HEIGHT - itemToAdd.getIconTexture().getTexture().getHeight() - 20);

                //drop current held item to its original pos (if necessary)
                if (heldItem != null)
                    heldItem.setPosition(heldItem.getInitialPosition().x, heldItem.getInitialPosition().y);

                //Moving held item to the top right corner
                itemToAdd.setPosition(heldItemPos.x, heldItemPos.y);

                GameManager.getCurrentTableau().setHeldItem(itemToAdd);
                System.out.println("Item added : " + itemAddedName);

                PlaySoundEffectEvent evt = new PlaySoundEffectEvent("-", new Object[]{ "global/sfx_wing.ogg", 0.5f });
                evt.DoStuff(deltaTime);
            }
        }

        hasRun = true;
    }
}
