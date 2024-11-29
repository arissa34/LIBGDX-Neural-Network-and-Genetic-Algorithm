package com.ramimartin.neural;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;
import com.badlogic.gdx.utils.ObjectMap;

import java.util.Iterator;

public class FeedForwardNeuralNetwork {

    private int nbrHidden;
    private ArrayMap<Integer, Neuron> listNeurons;
    private Array<Neuron> listInputNeurons;
    private ArrayMap<Integer, Array<Neuron>> listHiddenNeurons;
    private Array<Neuron> listOutputNeurons;
    public Array<Axon> listOutputAxon;
    private float xOfst = 50, yOfst = 40;

    public FeedForwardNeuralNetwork(int nbrInput, int nbrHiddenLayer, int nbrHidden, int nbrOutput) {

        this.nbrHidden = nbrHidden;
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
            if (nbrHiddenLayer == 0) {
                connect(n, listOutputNeurons);
            } else {
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

        int index = 0;
        for (int i = 0; i < listInputNeurons.size; i++, index++) {
            listInputNeurons.get(i).connectionsOut.get(0).setValue(inputs[i]);
        }
        for (int i = index; i < listNeurons.size; i++) {
            listNeurons.get(i).calculate();
        }
    }

    public void clearAxonValues() {
        Iterator<ObjectMap.Entry<Integer, Neuron>> iterator = listNeurons.iterator();
        while (iterator.hasNext()) {
            ObjectMap.Entry<Integer, Neuron> entry = iterator.next();
            for (int i = 0; i < entry.value.connectionsOut.size; i++) {
                entry.value.connectionsOut.get(i).setValue(0);
            }
        }
    }

    public void randomizeAxonValues() {
        Iterator<ObjectMap.Entry<Integer, Neuron>> iterator = listNeurons.iterator();
        while (iterator.hasNext()) {
            ObjectMap.Entry<Integer, Neuron> entry = iterator.next();
            for (int i = 0; i < entry.value.connectionsOut.size; i++) {
                entry.value.connectionsOut.get(i).setWeight(MathUtils.random(-1.0f, 1.0f));
            }
        }
    }

    public void render(ShapeRenderer shapeRenderer) {
        for (int i = 0; i < listNeurons.size; i++) {
            listNeurons.get(i).render(30, Gdx.graphics.getBackBufferHeight() - (nbrHidden * 25), shapeRenderer);
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
        for (int i = 0; i < listAxons.size; i++) {
            curent.get(i).setWeight(listAxons.get(i).getWeight());
        }
    }

    public void importAxons(double... listValues) {
        Array<Axon> curent = exportAxons();
        for (int i = 0; i < listValues.length; i++) {
            curent.get(i).setWeight((float) listValues[i]);
        }
    }

    final static double EPSILON = 1e-12;

    /**
     * Rééchelonne une valeur d'un intervalle d'origine à un autre intervalle cible tout en conservant sa proportion relative.
     *
     * @param valueCoord1 La valeur à rééchelonner dans l'intervalle d'origine.
     * @param startCoord1 La borne inférieure de l'intervalle d'origine.
     * @param endCoord1   La borne supérieure de l'intervalle d'origine.
     * @param startCoord2 La borne inférieure de l'intervalle cible.
     * @param endCoord2   La borne supérieure de l'intervalle cible.
     * @return La valeur rééchelonnée dans l'intervalle cible.
     */
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
