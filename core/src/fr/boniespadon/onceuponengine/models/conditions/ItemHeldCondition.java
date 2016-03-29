package fr.boniespadon.onceuponengine.models.conditions;

import fr.boniespadon.onceuponengine.models.Tableau;
import fr.boniespadon.onceuponengine.models.events.Event;
import fr.boniespadon.onceuponengine.models.sprites.Sprite;
import fr.boniespadon.onceuponengine.GameManager;

/**
 * A Condition which is met when a specific Sprite is held
 *
 * @see Tableau#getHeldItem()
 *
 * @author Mathieu
 *
 * @version 0.1
 */
public class ItemHeldCondition extends Condition {

    /**
     * The name of the Sprite which needs to be held
     *
     * @see Sprite#name
     */
    private String necessaryItemName;

    /**
     * Creates a new EventHasRunCondition with an owner and additional data
     *
     * @param owner
     *        The Event to which this Condition applies
     * @param additionalData
     *        Additional parameter, here an Object[] containing only a String, the name of the Sprite which needs to be held
     */
    public ItemHeldCondition(Event owner, Object[] additionalData) {
        super(owner, additionalData);

        //additionalData contains necessary item name
        necessaryItemName = additionalData[0].toString();
    }

    /**
     * Checks if this Condition is met
     *
     * @return true if the Sprite whose name is contained in additional data is held, false otherwise
     */
    @Override
    public boolean ConditionMet() {

        if (GameManager.getCurrentTableau().getHeldItem() == null)
            return false;

        //Check if held item is the one that is necessary
        Sprite heldItem = GameManager.getCurrentTableau().getHeldItem();
        String heldItemName = heldItem.getName();

        return necessaryItemName.equals(heldItemName);
    }
}
