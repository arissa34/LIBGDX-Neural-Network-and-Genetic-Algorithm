package com.ramimartin.ecs.component;

import com.badlogic.ashley.core.Component;
import com.ramimartin.ecs.component.listener.ShapeRenderListener;

public class ShapeRenderableComponent implements Component {

    public ShapeRenderListener shapeRenderListener;
    public ShapeRenderableComponent(ShapeRenderListener shapeRenderListener){
        this.shapeRenderListener = shapeRenderListener;
    }
}
