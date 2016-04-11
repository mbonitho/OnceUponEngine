package fr.boniespadon.onceuponengine.models.events;

import java.util.ArrayList;
import java.util.List;

import fr.boniespadon.onceuponengine.models.conditions.Condition;

/**
 * A onceuponengine Event. Represents something that is happening during the onceuponengine (ie a movement,
 * a reaction when something is clicked, a sound effect played...)
 */
public abstract class Event {

    /**
     * Types of triggers for events :
     *      - onClick : the event is triggered when the owner is clicked
     *      - auto : the event fires automatically
     *      - parallel : the event fires automatically and repeats itself indefinitely
     */
    public enum TRIGGERS{onClick, auto, parallel}

    /**
     * Id of the Event, used for Conditions, must be Unique
     *
     * @see Condition
     * @see Event#getId()
     */
    protected String id;

    /**
     * Name of the Sprite that needs to be clicked in the case of an onClick trigger
     * If the trigger of the Event is auto or parallel, the parameter is unused
     *
     * @see Event#getOwner()
     */
    protected String owner;

    /**
     * The Event parameters which vary according to the type of Event
     */
    protected Object[] additionalData;

    /**
     * Trigger of the Event
     *
     * @see Event.TRIGGERS
     */
    protected TRIGGERS trigger;

    /**
     * Conditions for the Event to Trigger
     */
    protected List<Condition> conditions;

    /**
     * Indicates if the Event has already run at least once
     *
     * @see Event#getHasRun()
     */
    protected boolean hasRun;

    /**
     * Indicates if the Event can be played more than once
     */
    protected boolean repeatable;

    /**
     * Creates a new Event with an Id and additional data
     *
     * @param id
     *        Id of the Event, must be Unique
     * @param addData
     *        Additional data, which vary according to the type of Event
     */
    public Event(String id, Object[] addData)
    {
        this.id = id;
        //By default, events are triggered on click
        trigger = TRIGGERS.onClick;
        repeatable = true;
        hasRun = false;
        additionalData = addData;

        conditions = new ArrayList<Condition>();
    }

    /**
     * Activates the effect of the Event (vary according to Event type)
     *
     * @param deltaTime
     *        Game time between 2 frames
     */
    public abstract void DoStuff(float deltaTime);

    /**
     * Returns the additional data of this Event
     * @return additional data of this Event
     */
    public Object[] getAdditionalData() { return additionalData; }

    /**
     * Sets the additional data of this Event
     * @param addData
     *        Additional data of this Event
     */
    public void setAdditionalData(Object[] addData) { additionalData = addData; }

    /**
     * Returns the Trigger of this Event
     *
     * @see Event.TRIGGERS
     *
     * @return the Trigger of this Event
     */
    public TRIGGERS getTrigger() { return trigger; }

    /**
     * Sets the Trigger of this Event
     *
     * @see Event.TRIGGERS
     *
     * @param t
     *        the Trigger of this Event
     */
    public void setTrigger(TRIGGERS t) { trigger = t; }

    /**
     * Returns true if this Event has run at least once, false otherwise
     *
     * @return true if this Event has run at least once, false otherwise
     */
    public boolean getHasRun() { return hasRun; }

    /**
     * Returns the name of the Sprite that needs to be clicked for this Event to run
     * This is only used when the trigger of this Event is set to onClick
     *
     * @see Event.TRIGGERS
     * @see fr.boniespadon.onceuponengine.models.sprites.Sprite
     *
     * @return Name of the Sprite that needs to be clicked for this Event to run
     */
    public String getOwner() { return this.owner;}

    /**
     * Sets the name of the Sprite that needs to be clicked for this Event to run
     * This is only used when the trigger of this Event is set to onClick
     *
     * @param ownerName
     *        Name of the owner of the Sprite
     */
    public void setOwner(String ownerName){ this.owner = ownerName; }

    /**
     * Sets the Conditions for the Sprite
     *
     * @see Condition
     *
     * @param cdts
     *        Conditions for the Sprite
     */
    public void setConditions(List<Condition> cdts){ this.conditions = cdts; }

    /**
     * Sets if this Event can be run more than once
     *
     * @param repeatable
     *        can this Event be run more than once
     */
    public void setRepeatable(boolean repeatable) { this.repeatable = repeatable; }

    /**
     * Returns this Event Id
     *
     * @return this Event Id
     */
    public String getId(){ return id; }

    /**
     * Checks every Conditions of this Event and return true if it is good to go
     *
     * @see Condition
     *
     * @return true if all Conditions are met, false otherwise
     */
    public boolean ConditionsMet()
    {
        for (Condition cdt : conditions)
            if (!cdt.ConditionMet())
                return false;
        return true;
    }

    @Override
    public String toString()
    {
        String addData = "";
        for (Object o : additionalData)
            addData += o.toString() + ";";

        return this.getClass().getName() + " - " + addData;
    }
}
