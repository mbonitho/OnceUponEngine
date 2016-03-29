package fr.boniespadon.onceuponengine.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import fr.boniespadon.onceuponengine.Game;

/**
 * Entry point for desktop mode
 *
 * Defines onceuponengine configuration and launches the onceuponengine
 *
 * @author Mathieu
 *
 * @version 0.1
 */
public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		Game g = new Game();
		config.title = Game.TITLE;
		config.height = Game.HEIGHT;
		config.width = Game.WIDTH;
		config.fullscreen = Game.FULLSCREEN;
		config.resizable = false;
		config.addIcon("sprites/icon.png", Files.FileType.Internal);



		new LwjglApplication(g, config);
	}
}
