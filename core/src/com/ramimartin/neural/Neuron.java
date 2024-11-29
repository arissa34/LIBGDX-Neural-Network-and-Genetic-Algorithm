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

    public void calculate(){
        float sum = 0;
        for (int i = 0; i < connectionsIn.size; i++) {
            float value = connectionsIn.get(i).getValue();
            float weight = connectionsIn.get(i).getWeight();
            sum += value * weight;
        }
        sum += 1; // BIAS

        float valueOutput = tanh(sum);
        for (int i = 0; i < connectionsOut.size; i++) {
            connectionsOut.get(i).setValue(valueOutput);
        }
    }

    public float tanh(double x){
        return (float) (Math.tanh(x));
    }

    public float sigmoid(double x){
        return (float) (1 / (1 + Math.exp(x)));
    }

    public float relu(double x) {
        return (float) Math.max(0, x);
    }

    public float leakyRelu(double x) {
        return (float) (x > 0 ? x : 0.01 * x);
    }

    public float[] softmax(float[] inputs) {
        float sum = 0;
        float[] outputs = new float[inputs.length];
        for (float input : inputs) {
            sum += Math.exp(input);
        }
        for (int i = 0; i < inputs.length; i++) {
            outputs[i] = (float) (Math.exp(inputs[i]) / sum);
        }
        return outputs;
    }

    public float elu(double x, double alpha) {
        return (float) (x > 0 ? x : alpha * (Math.exp(x) - 1));
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
