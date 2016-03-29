package fr.boniespadon.onceuponengine.models.events;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

import fr.boniespadon.onceuponengine.GameManager;

/**
 * Plays background music
 *
 * @author Mathieu
 *
 * @version 0.1
 */
public class PlayBGMEvent extends Event {

    /**
     * The music to be played
     */
    private Music bgm;

    /**
     * Creates a new PlayBGMEvent
     *
     * @param id
     *        Id of the Event
     * @param additionalData
     *        The additional data must contain
     *              - the short name of the music file to be played
     */
    public PlayBGMEvent(String id, Object[] additionalData)
    {
        super(id, additionalData);

        String bgmName = additionalData[0].toString();
        String completeName = "bgm/" + bgmName;


        bgm = Gdx.audio.newMusic(Gdx.files.internal(completeName));
        bgm.setLooping(true);
        bgm.setVolume(0.2f);
    }

    /**
     * Plays a background music
     *
     * @param deltaTime
     *        Game time between two frames
     */
    @Override
    public void DoStuff(float deltaTime)
    {
        //It is a non-repeatable event so if it has already run, exit method
        if (hasRun)
            return;

        GameManager.setBgm(bgm);

        hasRun = true;
    }
}
