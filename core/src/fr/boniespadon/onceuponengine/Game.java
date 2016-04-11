package fr.boniespadon.onceuponengine;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Game class
 *
 * @author Mathieu
 *
 * @version 0.1
 */
public class Game extends ApplicationAdapter {

    /**
     * Window width
     */
	public static final int WIDTH = 700;
    /**
     * Window height
     */
	public static final int HEIGHT = 700;

    /**
     * Width of background images (or width of the biggest image)
     */
	public static final int IMG_WIDTH = 2000;

    /**
     * Height of background images (or height of the biggest image)
     */
	public static final int IMG_HEIGHT = 2000;

    /**
     * Indicates if the onceuponengine is to be launched in fulscreen mode
     */
	public static final boolean FULLSCREEN = false;

    /**
     * The camera used to display the onceuponengine world in correct proportions
     */
	private static OrthographicCamera cam;

	/**
	 * The camera viewport, used to keep proportions when the window size changes
	 */
	private Viewport viewport;

    /**
     * The title of the onceuponengine window (for desktop)
     */
	public static final String TITLE = "Once Upon Engine Technical Demo";

    /**
     * The proportion by which the onceuponengine image widths must be modified to fit the camera
     */
    public static float RATIO_X;

    /**
     * The proportion by which the onceuponengine image heights must be modified to fit the camera
     */
    public static float RATIO_Y;

    /**
     * The batch in which everything will be drawn
     */
	private SpriteBatch batch;

    /**
     * Since the onceuponengine uses square backgrounds, the square size is equal to whichever
     * of the screen width or height is the lowest
     *
     * It is used to calculate the viewport dimensions of the camera
     */
    private static int squareSize;

	@Override
	public void create ()
	{
		batch = new SpriteBatch();

		squareSize = Math.min(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		RATIO_X = (float)WIDTH / IMG_WIDTH;
		RATIO_Y = (float)HEIGHT / IMG_HEIGHT;

		cam = new OrthographicCamera(squareSize, squareSize * (Gdx.graphics.getHeight() / Gdx.graphics.getWidth()));
		viewport = new FitViewport(WIDTH, HEIGHT, cam);

		cam.position.set(cam.viewportWidth / 2f, cam.viewportHeight / 2f, 0);
		cam.update();

		GameManager.setCurrentTableau("magiland_splash");
	}

	@Override
	public void render ()
	{
		// limit it with 1/60 sec
		float dt = Math.min(Gdx.graphics.getDeltaTime(), 1 / 60f);

		cam.update();
		batch.setProjectionMatrix(cam.combined);

		//fullscreen
		if (Gdx.input.isKeyPressed(Input.Keys.ALT_LEFT) && Gdx.input.isKeyPressed(Input.Keys.ENTER))
		{
			Graphics.DisplayMode currentMode = Gdx.graphics.getDisplayMode(); //getDesktopDisplayMode();

			if (Gdx.graphics.isFullscreen())
				Gdx.graphics.setWindowedMode(Game.WIDTH, Game.HEIGHT);
			else
				Gdx.graphics.setFullscreenMode(currentMode);

		}

		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		GameManager.update(dt);//Gdx.graphics.getDeltaTime());
		GameManager.render(batch);
	}

	@Override
	public void resize(int width, int height)
	{
		viewport.update(width, height);
		cam.update();
	}

	@Override
	public void dispose()
	{
		super.dispose();
	}

    /**
     * Returns mouse position in the onceuponengine world
     * @return Mouse position in the onceuponengine world
     */
	public static Vector2 getMousePosInGameWorld()
	{
		Vector3 pos = cam.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));

		return new Vector2(pos.x, pos.y);
	}
}