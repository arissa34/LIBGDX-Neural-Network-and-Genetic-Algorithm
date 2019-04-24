package com.ramimartin.ecs.component;

import com.badlogic.ashley.core.Component;

/**
 * Created by Rami on 22/10/2017.
 */

public class ZComponent implements Component{
    public int zIndex = 0;

    public ZComponent(int zIndex) {
        this.zIndex = zIndex;
    }

    public ZComponent() {
    }


}
