package com.ramimartin.neural;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;

public class Neuron {

    private float x, y;
    public Array<Axon> connectionsIn;
    public Array<Axon> connectionsOut;
    private float radiusCircle = 14;

    public Neuron(float x, float y){
        this.x = x;
        this.y = y;
        connectionsOut = new Array<Axon>();
        connectionsIn = new Array<Axon>();
    }

    public void addConnectionIn(Axon axon){
        connectionsIn.add(axon);
    }

    public void addConnectionOut(Axon axon){
        connectionsOut.add(axon);
    }

    private float[] inputs;
    public void calculate(){
        if(inputs == null) {
            inputs = new float[connectionsIn.size];
        }

        for (int i = 0; i < connectionsIn.size; i++) {
            inputs[i] = connectionsIn.get(i).getValue();
        }

        calculate(inputs);
    }

    public float calculate(float... inputs){
        float sum = 0;
        for (int i = 0; i < inputs.length; i++) {
            float weight = 0;
            if(connectionsIn.size == 0){
                //Gdx.app.log("", "is Input Neuron");
                weight = 1;
            }else {
                weight = connectionsIn.get(i).getWeight();
            }
            sum += inputs[i] * weight;
        }
        if(connectionsIn.size > 0) {
            sum += 1; // BIAS
        }
        float valueOutput = tanh(sum);
        for (int i = 0; i < connectionsOut.size; i++) {
            connectionsOut.get(i).setValue(valueOutput);
        }
        //Gdx.app.log("", "valueOutput : "+valueOutput);
        return valueOutput;
    }

    public float tanh(double x){
        return (float) (Math.tanh(x));
    }

    public float sigmoid(double x){
        return (float) (1 / (1 + Math.exp(x)));
    }

    public float getX(){
        return x;
    }

    public float getY(){
        return y;
    }

    public void render(float x, float y, ShapeRenderer shapeRenderer){
        for (int i = 0; i < connectionsOut.size; i++) {
            connectionsOut.get(i).render(x, y, shapeRenderer);
        }
        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.circle(x + getX(),y + getY(), radiusCircle);
        shapeRenderer.end();
    }
}
