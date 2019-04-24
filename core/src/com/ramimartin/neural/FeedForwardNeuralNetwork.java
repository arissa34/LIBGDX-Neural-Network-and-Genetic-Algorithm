package com.ramimartin.neural;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;
import com.badlogic.gdx.utils.ObjectMap;

import java.util.Iterator;

public class FeedForwardNeuralNetwork {

    private float x, y;
    private float width, height;

    private ArrayMap<Integer, Neuron> listNeurons;
    private Array<Neuron> listInputNeurons;
    private ArrayMap<Integer, Array<Neuron>> listHiddenNeurons;
    private Array<Neuron> listOutputNeurons;
    public Array<Axon> listOutputAxon;
    private float xOfst = 50, yOfst = 40;

    public FeedForwardNeuralNetwork(float x, float y, int nbrInput, int nbrHiddenLayer, int nbrHidden, int nbrOutput) {
        this.x = x;
        this.y = y;

        int size = nbrInput + (nbrHiddenLayer * nbrHidden) + nbrOutput;

        listNeurons = new ArrayMap<Integer, Neuron>();
        listInputNeurons = new Array<Neuron>();
        listHiddenNeurons = new ArrayMap<Integer, Array<Neuron>>();
        listOutputNeurons = new Array<Neuron>();
        listOutputAxon = new Array<Axon>();

        for (int o = 0; o < nbrOutput; o++) {
            size--;
            Neuron n = new Neuron(xOfst + (xOfst * nbrHiddenLayer), (yOfst * o));
            listOutputNeurons.add(n);
            listNeurons.put(size, n);
            Axon axonOut = new Axon(n, null, 1);
            n.addConnectionOut(axonOut);
            listOutputAxon.add(axonOut);
        }

        for (int hl = nbrHiddenLayer - 1; hl >= 0; hl--) {
            Array<Neuron> hidden = new Array<Neuron>();
            for (int h = 0; h < nbrHidden; h++) {
                size--;
                float yOffset = -(yOfst / 2) * (nbrHidden - nbrOutput);
                Neuron n = new Neuron(xOfst + (xOfst * hl), yOffset + (yOfst * h));
                hidden.add(n);
                listNeurons.put(size, n);
                connect(n, hl == nbrHiddenLayer - 1 ? listOutputNeurons : listHiddenNeurons.get(hl + 1));//
            }
            listHiddenNeurons.put(hl, hidden);
        }

        for (int i = 0; i < nbrInput; i++) {
            size--;
            float yOffset = -(yOfst / 2) * (nbrInput - nbrOutput);
            Neuron n = new Neuron(0, yOffset + (yOfst * i));
            listInputNeurons.add(n);
            listNeurons.put(size, n);
            if(nbrHiddenLayer == 0){
                connect(n, listOutputNeurons);
            }else{
                connect(n, listHiddenNeurons.get(0));
            }
        }
    }

    public void connect(Neuron neuron, Array<Neuron> listToConnect) {
        if (listToConnect == null) return;
        for (int i = 0; i < listToConnect.size; i++) {
            Axon axon = new Axon(neuron, listToConnect.get(i));
            neuron.addConnectionOut(axon);
            listToConnect.get(i).addConnectionIn(axon);
        }
    }

    public void feedforward(float... inputs) {
        if (listInputNeurons.size != inputs.length) return;

       // for (int i = 0; i < inputs.length; i++) {
       //     Gdx.app.log("", "inputs " + i + " value : " + inputs[i]);
       // }

        int index = 0;
        for (int i = 0; i < listInputNeurons.size; i++, index++) {
            listInputNeurons.get(i).calculate(inputs[i]);
        }

        for (int i = index; i < listNeurons.size; i++) {
            listNeurons.get(i).calculate();
        }

       // for (int i = 0; i < listOutputAxon.size; i++) {
       //     Gdx.app.log("", "output " + i + " value : " + listOutputAxon.get(i).getValue());
       // }
       // Gdx.app.log("", "-------------------");
    }

    public void clearAxonValues(){
        Iterator<ObjectMap.Entry<Integer, Neuron>> iterator = listNeurons.iterator();
        while (iterator.hasNext()) {
            ObjectMap.Entry<Integer, Neuron> entry = iterator.next();
            for (int i = 0; i < entry.value.connectionsOut.size; i++) {
                entry.value.connectionsOut.get(i).setValue(0);
            }
        }
    }

    public void render(ShapeRenderer shapeRenderer) {
        for (int i = 0; i < listNeurons.size; i++) {
            listNeurons.get(i).render(x, y, shapeRenderer);
        }
    }

    public Array<Axon> exportAxons() {
        Array<Axon> output = new Array<Axon>();
        Iterator<ObjectMap.Entry<Integer, Neuron>> iterator = listNeurons.iterator();
        while (iterator.hasNext()) {
            ObjectMap.Entry<Integer, Neuron> entry = iterator.next();
            for (int i = 0; i < entry.value.connectionsOut.size; i++) {
                output.add(entry.value.connectionsOut.get(i));
            }
        }
        return output;
    }

    public void importAxons(Array<Axon> listAxons) {
        Array<Axon> curent = exportAxons();
        for(int i = 0; i < listAxons.size; i++){
            curent.get(i).setWeight(listAxons.get(i).getWeight());
        }
    }

    public void importAxons(double... listValues) {
        Array<Axon> curent = exportAxons();
        for(int i = 0; i < listValues.length; i++){
            curent.get(i).setWeight((float)listValues[i]);
        }
    }

    final static double EPSILON = 1e-12;
    public static float map(float valueCoord1,
                            float startCoord1, float endCoord1,
                            float startCoord2, float endCoord2) {

        if (Math.abs(endCoord1 - startCoord1) < EPSILON) {
            throw new ArithmeticException("/ 0");
        }

        float offset = startCoord2;
        float ratio = (endCoord2 - startCoord2) / (endCoord1 - startCoord1);
        return ratio * (valueCoord1 - startCoord1) + offset;
    }
}
