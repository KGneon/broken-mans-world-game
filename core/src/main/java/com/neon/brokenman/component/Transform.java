package com.neon.brokenman.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.gdx.math.Vector2;
import lombok.Getter;

public class Transform implements Component, Comparable<Transform> {
    public static final ComponentMapper<Transform> MAPPER = ComponentMapper.getFor(Transform.class);

    @Getter private final Vector2 position;
    @Getter private final int z;
    @Getter private final Vector2 size;
    @Getter private final Vector2 scaling;
    @Getter private float rotationDeg;

    public Transform(
            Vector2 position,
            int z,
            Vector2 size,
            Vector2 scaling,
            float rotationDeg
            ) {
        this.position = position;
        this.z = z;
        this.size = size;
        this.scaling = scaling;
        this.rotationDeg = rotationDeg;
    }
    @Override
    public int compareTo(final Transform other) {;
        if(this.z != other.z){
            return Float.compare(this.z, other.z);
        }
        if(this.position.y != other.position.y){
            return Float.compare(this.position.y, other.position.y);
        }
        return Float.compare(this.position.x, other.position.x);
    }
}
