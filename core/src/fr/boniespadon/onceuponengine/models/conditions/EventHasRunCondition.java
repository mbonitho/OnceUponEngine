package fr.boniespadon.onceuponengine.models.conditions;

import fr.boniespadon.onceuponengine.models.events.Event;
import fr.boniespadon.onceuponengine.GameManager;

/**
 * A Condition which is met when a specific Event has run
 *
 * @see Event
 *
 * @author Mathieu
 *
 * @version 0.1
 */
public class EventHasRunCondition extends Condition {

    /**
     * The Id of the Event to check
     *
     * @see Event#id
     */
    private String eventId;

    /**
     * Creates a new EventHasRunCondition with an owner and additional data
     *
     * @param owner
     *        The Event to which this Condition applies
     * @param additionalData
     *        Additional parameter, here an Object[] containing only a String, the Id of the Event to check
     */
    public EventHasRunCondition(Event owner, Object[] additionalData) {
        super(owner, additionalData);

        //additionalData contains only the id of the Event to check
        eventId = additionalData[0].toString();
    }

    /**
     * Checks if this Condition is met
     *
     * @return true if the event contained in additional data has run, false otherwise
     */
    @Override
    public boolean ConditionMet() {

        Event evt = GameManager.getCurrentTableau().getEventFromId(eventId);

        if (evt != null)
            return evt.getHasRun();
        else
            return false;
    }
}
