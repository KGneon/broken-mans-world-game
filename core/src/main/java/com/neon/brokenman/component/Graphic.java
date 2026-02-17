package com.neon.brokenman.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import lombok.Getter;
import lombok.Setter;

public class Graphic implements Component {
    public static final ComponentMapper<Graphic> MAPPER = ComponentMapper.getFor(Graphic.class);

    @Getter @Setter private TextureRegion region;
    @Getter private final Color color;

    public Graphic(final Color color, final TextureRegion region) {
        this.color = color;
        this.region = region;
    }


}
