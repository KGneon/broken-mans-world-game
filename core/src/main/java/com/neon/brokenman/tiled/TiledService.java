package com.neon.brokenman.tiled;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.neon.brokenman.asset.AssetService;
import com.neon.brokenman.asset.MapAsset;

import java.util.function.Consumer;

import static com.neon.brokenman.config.TiledMapNamespace.TILED_MAP_OBJECTS;

public class TiledService {
    private final AssetService assetService;

    private TiledMap currentMap;

    //TODO observer pattern to check!!!
    private Consumer<TiledMap> mapChangeConsumer;
    private Consumer<TiledMapTileMapObject> loadObjectConsumer;

    public TiledService(AssetService assetService) {
        this.assetService = assetService;
        this.mapChangeConsumer = null;
        this.loadObjectConsumer = null;
        this.currentMap = null;
    }

    public TiledMap loadMap(MapAsset mapAsset) {
        TiledMap tiledMap = this.assetService.load(mapAsset);
        tiledMap.getProperties().put("mapAsset", mapAsset);
        return tiledMap;
    }

    public void setMap(TiledMap map) {
        if(this.currentMap != null) {
            this.assetService.unload(this.currentMap.getProperties().get("mapAsset", MapAsset.class));
        }

        this.currentMap = map;
        loadMapObjects(map);
        if(this.mapChangeConsumer != null) {
            this.mapChangeConsumer.accept(map);
        }
    }

    private void loadMapObjects(TiledMap tiledMap) {
        for(MapLayer layer : tiledMap.getLayers()) {
            if(TILED_MAP_OBJECTS.equals(layer.getName())) {
                loadObjectLayer(layer);
            }
        }
    }

    private void loadObjectLayer(MapLayer objectLayer) {
        if(loadObjectConsumer == null) {
            return;
        }
        for (MapObject mapObject : objectLayer.getObjects()) {
            if (mapObject instanceof TiledMapTileMapObject tileMapObject) {
                loadObjectConsumer.accept(tileMapObject);
            } else {
                throw new GdxRuntimeException("Unsupported map object type: " + mapObject.getClass().getSimpleName());
            }
        }
    }
}
