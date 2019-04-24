package com.ramimartin.ecs.component;

import com.badlogic.ashley.core.Component;
import com.ramimartin.ecs.component.listener.UpdatableListener;

public class UpdatableComponent implements Component {

    public UpdatableListener listener;
    public UpdatableComponent(UpdatableListener listener){
        this.listener = listener;
    }
}
