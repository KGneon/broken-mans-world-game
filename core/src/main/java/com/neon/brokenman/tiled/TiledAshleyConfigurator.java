package com.neon.brokenman.tiled;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import com.badlogic.gdx.math.Vector2;
import com.neon.brokenman.GdxGame;
import com.neon.brokenman.asset.AssetService;
import com.neon.brokenman.component.Graphic;
import com.neon.brokenman.component.Transform;

import static com.neon.brokenman.config.VideoConstants.UNIT_SCALE;

public class TiledAshleyConfigurator {

    private final Engine engine;
    private final AssetService assetService;

    public TiledAshleyConfigurator(Engine engine, AssetService assetService) {
        this.engine = engine;
        this.assetService = assetService;
    }


    public void onLoadObject(TiledMapTileMapObject tileMapObject) {
        Entity entity = this.engine.createEntity();
        TiledMapTile tile = tileMapObject.getTile();
        TextureRegion textureRegion = getTextureRegion(tile);
        int z = tile.getProperties().get("z", Integer.class);

        entity.add(new Graphic(Color.WHITE.cpy(), textureRegion));
        addEntityTransform(
                tileMapObject.getX(), tileMapObject.getY(), z,
                textureRegion.getRegionWidth(), textureRegion.getRegionHeight(),
                tileMapObject.getScaleX(), tileMapObject.getScaleY(),
                entity
        );

        this.engine.addEntity(entity);
    }

    private void addEntityTransform(
            float x, float y, int z,
            float width, float height,
            float scaleX, float scaleY, Entity entity
    ) {
        Vector2 position = new Vector2(x, y);
        Vector2 size = new Vector2(width, height);
        Vector2 scaling = new Vector2(scaleX, scaleY);

        position.scl(UNIT_SCALE);
        size.scl(UNIT_SCALE);

        entity.add(new Transform(position, z, size, scaling, 0f));
    }

    private TextureRegion getTextureRegion(TiledMapTile tile) {
        return tile.getTextureRegion();
    }
}
