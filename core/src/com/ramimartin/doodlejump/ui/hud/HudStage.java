package com.ramimartin.doodlejump.ui.hud;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.ramimartin.doodlejump.ga.DoodlePopulation;
import com.ramimartin.ecs.component.StageRenderableComponent;
import com.ramimartin.ecs.component.UpdatableComponent;
import com.ramimartin.ecs.component.ZComponent;
import com.ramimartin.ecs.component.listener.StageRenderListener;
import com.ramimartin.ecs.component.listener.UpdatableListener;
import com.ramimartin.ecs.utils.EntityUtils;

public class HudStage extends Stage implements StageRenderListener, UpdatableListener {

    private DoodlePopulation population;

    private Entity entity;
    private Label fpsLabel;
    private Label generationLabel;
    private Label bestFitnessLabel;
    private Label populationLabel;
    private Label.LabelStyle labelStyle;
    private Table table;

    public HudStage(DoodlePopulation population) {

        this.population = population;

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("data/font/TitanOne-Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.borderWidth = 1;
        parameter.shadowOffsetX = 3;
        parameter.shadowOffsetY = 3;
        parameter.shadowColor = new Color(0, 0.5f, 0, 0.75f);
        BitmapFont titanFont = generator.generateFont(parameter);

        labelStyle = new Label.LabelStyle(titanFont, Color.WHITE);

        table = new Table();
        table.setFillParent(true);
        table.top();

        fpsLabel = new Label("", labelStyle);
        generationLabel = new Label("Generation : ", labelStyle);
        populationLabel = new Label("Population : ", labelStyle);
        bestFitnessLabel = new Label("Best Fitness : ", labelStyle);

        fpsLabel.setPosition(10, 20);

        addActor(fpsLabel);
        table.add(generationLabel).pad(5).expandX().right().row();
        table.add(populationLabel).pad(5).expandX().right().row();
        table.add(bestFitnessLabel).pad(5).expandX().right().row();
        addActor(table);

        entity = EntityUtils.registerEntityAndAddComponent(
                new StageRenderableComponent(this),
                new ZComponent(0),
                new UpdatableComponent(this)
        );
    }

    private void updatePosulationLabel(){
        if(population == null) return;
        generationLabel.setText("Generation : "+population.generation);
        populationLabel.setText("Population : "+population.populationAlive()+"/"+population.populationSize);
        bestFitnessLabel.setText("Best Fitness : "+Math.round(population.bestFitnessEver/10));
    }

    @Override
    public void update(float delta) {
        updatePosulationLabel();
        int fps = Gdx.graphics.getFramesPerSecond();
        if (fps > 30) {
            fpsLabel.setColor(Color.GREEN);
        } else {
            fpsLabel.setColor(Color.RED);
        }
        fpsLabel.setText("FPS : " + fps);
    }

    @Override
    public void draw(float delta) {
        act();
        draw();
    }
}
