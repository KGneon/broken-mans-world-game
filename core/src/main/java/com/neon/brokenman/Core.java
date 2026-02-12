package com.neon.brokenman;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.HashMap;
import java.util.Map;

import static com.neon.brokenman.config.VideoConstants.WORLD_HEIGHT;
import static com.neon.brokenman.config.VideoConstants.WORLD_WIDTH;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms.
 */
public class Core extends Game {

    private Batch batch;
    private OrthographicCamera camera;
    private Viewport viewport;
    private final Map<Class<? extends Screen>, Screen> screenCashe = new HashMap<>();

    @Override
    public void create() {
        this.batch = new SpriteBatch();
        this.camera = new OrthographicCamera();
        this.viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);

        addScreen(new GameScreen());
        setScreen(GameScreen.class);

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        super.resize(width, height);
    }

    public void addScreen(Screen screen) {
        screenCashe.put(screen.getClass(), screen);
    }

    public void setScreen(Class<? extends Screen> screenClass) {
        Screen screen = screenCashe.get(screenClass);
        if (screen == null) {
            throw new GdxRuntimeException("No screen with class " + screenClass + " found in cache.");
        }
        setScreen(screen);
    }

    @Override
    public void dispose() {
        screenCashe.values().forEach(Screen::dispose);
        screenCashe.clear();

        this.batch.dispose();
    }
}
