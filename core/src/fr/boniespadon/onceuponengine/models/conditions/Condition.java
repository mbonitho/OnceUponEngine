package fr.boniespadon.onceuponengine.models.conditions;

import fr.boniespadon.onceuponengine.models.events.Event;

/**
 * A condition for an Event to be launched
 * This is the parent, non-specialized class
 * A Condition belongs to an Event
 *
 * @see Event
 *
 * @author Mathieu
 *
 * @version 0.1
 */
public abstract class Condition {

    /**
     * The Event to which this Condition applies
     *
     * @see Condition#getOwner()
     */
    protected Event owner;

    /**
     * The Condition parameters which vary according to the type of Condition
     */
    protected Object[] additionalData;

    /**
     * Creates a new Condition with an owner and additional data
     *
     * @param owner
     *        The Event to which this Condition applies
     * @param additionalData
     *        Additional parameters, which vary according to the type of Condition
     */
    public Condition(Event owner, Object[] additionalData)
    {
        this.owner = owner;
        this.additionalData = additionalData;
    }

    /**
     * Indicates if this Condition is met
     *
     * @return true if this Condition is met (so the Event can run), false otherwise
     */
    public abstract boolean ConditionMet();

    /**
     * Returns this Condition's owner
     * @return this Condition's owner
     */
    public Event getOwner()
    {
        return owner;
    }

    /**
     * Returns this Condition's additional data
     *
     * @return this Condition's additional data
     */
    public Object[] getAdditionalData()
    {
        return additionalData;
    }

    /**
     * Sets this event additional data
     *
     * @param addData
     *        Additional data (parameters) to use
     */
    public void setAdditionalData(Object[] addData) { additionalData = addData; }

    @Override
    public String toString()
    {
        String addData = "";
        for (Object o : additionalData)
            addData += o.toString() + ";";

        return this.getClass().getName() + " - " + addData;
    }
}
