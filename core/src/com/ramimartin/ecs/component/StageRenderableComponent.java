package com.ramimartin.ecs.component;

import com.badlogic.ashley.core.Component;
import com.ramimartin.ecs.component.listener.StageRenderListener;

public class StageRenderableComponent implements Component {

    public StageRenderListener renderListener;
    public StageRenderableComponent(StageRenderListener renderListener){
        this.renderListener = renderListener;
    }
}
