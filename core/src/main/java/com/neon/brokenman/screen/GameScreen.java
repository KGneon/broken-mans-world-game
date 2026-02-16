package com.neon.brokenman.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.neon.brokenman.GdxGame;
import com.neon.brokenman.asset.AssetService;
import com.neon.brokenman.asset.MapAsset;

import static com.neon.brokenman.config.VideoConstants.UNIT_SCALE;

public class GameScreen extends ScreenAdapter {

    private final Batch batch;
    private final AssetService assetService;
    private final Viewport viewport;
    private final OrthographicCamera camera;
    private final OrthogonalTiledMapRenderer mapRenderer;

    public GameScreen(final GdxGame game) {
        this.assetService = game.getAssetService();
        this.viewport = game.getViewport();
        this.camera = game.getCamera();
        this.batch = game.getBatch();

        // Renderer powinien dostać mapę dopiero po załadowaniu w show().
        this.mapRenderer = new OrthogonalTiledMapRenderer(null, UNIT_SCALE, this.batch);
    }

    @Override
    public void show() {
        this.assetService.load(MapAsset.MAIN);
        this.mapRenderer.setMap(this.assetService.get(MapAsset.MAIN));

        camera.position.set(
                viewport.getWorldWidth() / 2f,
                viewport.getWorldHeight() / 2f,
                0
        );
        camera.update();

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);

        this.viewport.apply();
        this.batch.setColor(Color.WHITE);

        this.mapRenderer.setView(this.camera);
        this.mapRenderer.render();

        input();
        logic();
        draw();
    }

    private void input() {

    }

    private void logic() {

    }

    private void draw() {

    }

    @Override
    public void dispose() {
        this.mapRenderer.dispose();
    }
}
