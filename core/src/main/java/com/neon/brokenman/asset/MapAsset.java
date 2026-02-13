package com.neon.brokenman.asset;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

public enum MapAsset implements Asset<TiledMap> {
    MAIN("mapBMW.tmx");

    private final AssetDescriptor<TiledMap> descriptor;

    MapAsset(String mapName) {
        //to load the map, we need to specify the path to the .tiled-project file, which contains the necessary information for loading the map correctly.
        TmxMapLoader.Parameters parameters = new TmxMapLoader.Parameters();
        parameters.projectFilePath = "maps/BMWmap.tiled-project";
        this.descriptor = new AssetDescriptor<>("maps/" + mapName, TiledMap.class, parameters);
    }

    @Override
    public AssetDescriptor getDescriptor() {
        return null;
    }
}
