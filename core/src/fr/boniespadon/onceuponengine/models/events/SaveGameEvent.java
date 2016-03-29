package fr.boniespadon.onceuponengine.models.events;

import fr.boniespadon.onceuponengine.helper.SaveManager;

/**
 * UNTESTED - Saves the onceuponengine in the save file
 *
 * @see SaveManager#saveFileName
 *
 * @author Mathieu
 *
 * @version 0.1
 */
public class SaveGameEvent extends Event {

    /**
     *Creates a new SaveGameEvent
     *
     * @param id
     *        Id of the Event
     * @param additionalData
     *        No additional data is required for an Save Game Event, so the parameter won't be used
     */
    public SaveGameEvent(String id, Object[] additionalData)
    {
        super(id, additionalData);
    }

    /**
     * Saves the onceuponengine in the save file
     *
     * @see SaveManager#saveFileName
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

        SaveManager.SaveGame();

        System.out.println(" Saving onceuponengine...");

        hasRun = true;
    }
}
