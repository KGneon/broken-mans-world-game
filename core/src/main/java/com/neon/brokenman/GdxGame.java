package com.neon.brokenman;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.profiling.GLProfiler;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.neon.brokenman.asset.AssetService;
import com.neon.brokenman.screen.GameScreen;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

import static com.neon.brokenman.config.VideoConstants.WORLD_HEIGHT;
import static com.neon.brokenman.config.VideoConstants.WORLD_WIDTH;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms.
 */
public class GdxGame extends Game {
    @Getter private Batch batch;
    @Getter private OrthographicCamera camera;
    @Getter private Viewport viewport;
    @Getter private AssetService assetService;
    private GLProfiler glProfiler;
    private FPSLogger fpsLogger;

    private final Map<Class<? extends Screen>, Screen> screenCashe = new HashMap<>();

    @Override
    public void create() {
        Gdx.app.setLogLevel(Application.LOG_DEBUG);

        this.batch = new SpriteBatch();
        this.camera = new OrthographicCamera();
        this.viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
        this.assetService = new AssetService(new InternalFileHandleResolver());
        this.glProfiler = new GLProfiler(Gdx.graphics);
        this.glProfiler.enable();
        this.fpsLogger = new FPSLogger();

        addScreen(new GameScreen(this));
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
    public void render() {
        glProfiler.reset();

        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        super.render();

        Gdx.graphics.setTitle("Broken Mans World - FPS: " + Gdx.graphics.getFramesPerSecond() + " - GL Calls: " + glProfiler.getDrawCalls());
        fpsLogger.log();
    }

    @Override
    public void dispose() {
        screenCashe.values().forEach(Screen::dispose);
        screenCashe.clear();

        this.batch.dispose();

        this.assetService.debugDiagnostics();
        this.assetService.dispose();
    }
}
