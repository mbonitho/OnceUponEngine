package fr.boniespadon.onceuponengine.models.events;

import com.badlogic.gdx.Gdx;

/**
 * Exits the onceuponengine
 *
 * @author Mathieu
 */
public class ExitEvent extends Event {

    /**
     *  Creates a new Exit Event
     *
     * @param id
     *        Id of the Event
     * @param additionalData
     *        No additional data is required for an Exit Event, so the parameter won't be used
     */
    public ExitEvent(String id, Object[] additionalData)
    {
        super(id, additionalData);
    }


    /**
     * Exits the onceuponengine
     *
     * @param deltaTime
     *        Game time between two frames
     */
    @Override
    public void DoStuff(float deltaTime)
    {
        System.out.println("Exitting...");
        Gdx.app.exit();
    }
}
