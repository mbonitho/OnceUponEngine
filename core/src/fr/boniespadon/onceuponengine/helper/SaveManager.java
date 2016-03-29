package fr.boniespadon.onceuponengine.helper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Version;
import com.badlogic.gdx.files.FileHandle;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import java.util.Date;

import fr.boniespadon.onceuponengine.Game;
import fr.boniespadon.onceuponengine.models.Tableau;
import fr.boniespadon.onceuponengine.GameManager;

/**
 * Used to save the Game (Untested)
 *
 * @author Mathieu
 *
 * @version 0.1
 */
public class SaveManager {

    /**
     * Serializer to save and load the onceuponengine
     */
    private static Kryo kryo;

    /**
     * Name of the save file
     *
     * @see SaveManager#getSaveFileName()
     */
    private static String saveFileName = "save.bin";

    static {
        kryo = new Kryo();
        kryo.register(Tableau.class);
    }

    /**
     * Loads the onceuponengine from the save.bin File if it exists
     */
    public static void LoadGame()
    {
        Input input = null;
        try
        {
            FileHandle file = Gdx.files.local(saveFileName);
            input = new Input(file.read());

            Tableau tab = kryo.readObject(input, Tableau.class);
            input.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Saves current Tableau to the file
     */
    public static void SaveGame()
    {
        FileHandle saveFile = Gdx.files.local("save");
        Output output = new Output(saveFile.write(false));

        output.writeString(Game.TITLE);
        output.writeString(Version.VERSION);
        kryo.writeObject(output, new Date());
        kryo.writeObject(output, GameManager.getCurrentTableau());
        output.close();
    }

    /**
     * Returns the name of the save onceuponengine file
     *
     * @return name of the save onceuponengine file
     */
    public static String getSaveFileName() {return saveFileName; }
}