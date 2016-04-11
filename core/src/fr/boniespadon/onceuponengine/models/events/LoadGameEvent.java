package fr.boniespadon.onceuponengine.models.events;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.io.File;

import fr.boniespadon.onceuponengine.helper.SaveManager;

/**
 * UNTESTED - Loads the game from the save file
 *
 * @see SaveManager#saveFileName
 *
 * @author Mathieu
 *
 * @version 0.1
 */
public class LoadGameEvent extends Event {

    /**
     *Creates a new LoadGameEvent
     *
     * @param id
     *        Id of the Event
     * @param additionalData
     *        No additional data is required for an Load Game Event, so the parameter won't be used
     */
    public LoadGameEvent(String id, Object[] additionalData)
    {
        super(id, additionalData);
    }

    /**
     * Loads the game from the save file
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

        FileHandle fh = Gdx.files.internal(SaveManager.getSaveFileName());
        File saveFile = fh.file();

        if (saveFile.exists()) //if debug
        {

        }
        else
        { //if prod

        }

        SaveManager.LoadGame();

        System.out.println(" Saving onceuponengine...");

        hasRun = true;
    }
}
