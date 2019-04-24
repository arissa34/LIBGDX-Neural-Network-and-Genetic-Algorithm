package com.ramimartin.ga.population;

import com.badlogic.ashley.core.Component;

public abstract class PopulationComponent implements Component {

    public Population population;

    public PopulationComponent(Population population) {
        this.population = population;
    }
}
