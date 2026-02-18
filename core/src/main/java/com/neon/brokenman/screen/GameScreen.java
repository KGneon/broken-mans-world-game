package com.neon.brokenman.screen;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.neon.brokenman.GdxGame;
import com.neon.brokenman.asset.AssetService;
import com.neon.brokenman.asset.MapAsset;
import com.neon.brokenman.system.RenderSystem;

import static com.neon.brokenman.config.VideoConstants.UNIT_SCALE;

public class GameScreen extends ScreenAdapter {

    private final Batch batch;
    private final AssetService assetService;
    private final Viewport viewport;
    private final OrthographicCamera camera;
    private final OrthogonalTiledMapRenderer mapRenderer;
    private final Engine engine;

    public GameScreen(final GdxGame game) {
        this.assetService = game.getAssetService();
        this.viewport = game.getViewport();
        this.camera = game.getCamera();
        this.batch = game.getBatch();

        // Renderer powinien dostać mapę dopiero po załadowaniu w show().
        this.mapRenderer = new OrthogonalTiledMapRenderer(null, UNIT_SCALE, this.batch);
        this.engine = new Engine();

        this.engine.addSystem(new RenderSystem(this.batch, this.viewport));
//        this.engine.addSystem(new HealSystem(this.batch, this.viewport, this.assetService));
//        this.engine.addSystem(new DamageSystem(this.batch, this.viewport, this.assetService));
//        this.engine.addSystem(new MoveSystem(this.batch, this.viewport, this.assetService));
    }

    @Override
    public void show() {
        this.engine.getSystem(RenderSystem.class).setMap(this.assetService.get(MapAsset.MAIN));
    }

    @Override
    public void hide() {
        this.engine.removeAllEntities();
    }

    @Override
    public void render(float delta) {
        delta = Math.min(delta, 1/ 30f);
        this.engine.update(delta);
    }

    @Override
    public void dispose() {
        for(EntitySystem system : this.engine.getSystems()) {
            if( system instanceof Disposable disposableSystem) {
                disposableSystem.dispose();
            }
        }

    }
}
