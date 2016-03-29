package fr.boniespadon.onceuponengine.models.events;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

/**
 * Plays a sound effect
 *
 * @author Mathieu
 *
 * @version 0.1
 */
public class PlaySoundEffectEvent extends Event {

    /**
     * The sound effect to be played
     */
    private Sound sound;

    /**
     * The frequence at which the sound effect will be played (influences the pitch)
     */
    private float frequence;

    /**
     * Creates a new PlaySoundEffectEvent
     *
     * @param id
     *        Id of the Event
     * @param additionalData
     *        The additional data must contain
     *              - the short name of the sound effect file to be played
     *              - The frequence at which the sound effect will be played
     */
    public PlaySoundEffectEvent(String id, Object[] additionalData)
    {
        super(id, additionalData);

        String effectName = additionalData[0].toString();

        frequence = Float.parseFloat(additionalData[1].toString());

        String completeName = "sound/" + effectName;

        sound = Gdx.audio.newSound(Gdx.files.internal(completeName));
    }

    /**
     * Plays a sound effect
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

        sound.play(frequence);

        //todo dispose sound

        hasRun = true;
    }
}
