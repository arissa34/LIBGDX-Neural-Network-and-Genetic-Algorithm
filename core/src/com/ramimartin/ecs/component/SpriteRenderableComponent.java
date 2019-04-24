package com.ramimartin.ecs.component;

import com.badlogic.ashley.core.Component;
import com.ramimartin.ecs.component.listener.SpriteRenderListener;

public class SpriteRenderableComponent implements Component {

    public SpriteRenderListener spriteRenderListener;
    public SpriteRenderableComponent(SpriteRenderListener spriteRenderListener){
        this.spriteRenderListener = spriteRenderListener;
    }
}
