package com.ramimartin.neural;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;

public class Axon {

    private Neuron in;
    private Neuron out;
    private float weight;
    private float value;

    public Axon(Neuron in, Neuron out) {
       this(in, out, MathUtils.random(-1f, 1f));
    }

    public Axon(Neuron in, Neuron out, float weight) {
        this.in = in;
        this.out = out;
        this.weight = weight;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public void render(float x, float y, ShapeRenderer shapeRenderer){
        if(value >= 0){
            shapeRenderer.setColor(Color.GREEN);
        }else{
            shapeRenderer.setColor(Color.RED);
        }
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        if(out != null) {
            shapeRenderer.rectLine(x + in.getX(), y + in.getY(), x + out.getX(), y + out.getY(), 2);
        }else{
            shapeRenderer.rectLine(x + in.getX(), y + in.getY(), x + in.getX() + 50, y + in.getY(), 2);
        }
        shapeRenderer.end();
    }

}
