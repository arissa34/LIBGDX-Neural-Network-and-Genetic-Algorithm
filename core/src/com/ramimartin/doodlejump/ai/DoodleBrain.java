package com.ramimartin.doodlejump.ai;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.ramimartin.doodlejump.level.LevelManager;
import com.ramimartin.doodlejump.model.DoodleModel;
import com.ramimartin.doodlejump.model.PlateformModel;
import com.ramimartin.doodlejump.physics.WorldPhysics;
import com.ramimartin.neural.FeedForwardNeuralNetwork;

public class DoodleBrain {

    private DoodleModel doodleModel;
    private FeedForwardNeuralNetwork feedForwardNeuralNetwork;
    private Array<DoodleSensor> sensors;

    private final int nbrInput = 2;
    private final int nbrLayers = 1;
    private final int nbrHidden = 1;
    private final int nbrOutput = 2;

    public DoodleBrain(DoodleModel doodleModel) {
        this.doodleModel = doodleModel;
        feedForwardNeuralNetwork = new FeedForwardNeuralNetwork(50, 700, nbrInput, nbrLayers, nbrHidden, nbrOutput);
        //addSensors();
    }

    private void addSensors() {
        float angle = 45 * MathUtils.degreesToRadians;
        sensors = new Array<DoodleSensor>();
        sensors.add(new DoodleSensor(0 * MathUtils.degreesToRadians, angle));
        sensors.add(new DoodleSensor(45 * MathUtils.degreesToRadians, angle));
        sensors.add(new DoodleSensor(90 * MathUtils.degreesToRadians, angle));
        sensors.add(new DoodleSensor(135 * MathUtils.degreesToRadians, angle));
        //sensors.add(new DoodleSensor(180 * MathUtils.degreesToRadians));
        //sensors.add(new DoodleSensor(225 * MathUtils.degreesToRadians));
        //sensors.add(new DoodleSensor(270 * MathUtils.degreesToRadians));
        //sensors.add(new DoodleSensor(325 * MathUtils.degreesToRadians));
    }

    public void update() {
       // for(int i = 0; i < sensors.size; i++){
       //     sensors.get(i).updatePos(doodleModel.getPosition());
       // }
    }

    private float maxvelocityY = 0;

    //public void think() {
    //    float s1 = sensors.get(0).hasContact() ? sensors.get(0).getDistanceContact() : 0;
    //    float s2 = sensors.get(1).hasContact() ? sensors.get(1).getDistanceContact() : 0;
    //    float s3 = sensors.get(2).hasContact() ? sensors.get(2).getDistanceContact() : 0;
    //    float s4 = sensors.get(3).hasContact() ? sensors.get(3).getDistanceContact() : 0;
    //    //float s5 = sensors.get(4).hasContact() ? sensors.get(4).getDistanceContact() : 0;
    //    //float s6 = sensors.get(5).hasContact() ? sensors.get(5).getDistanceContact() : 0;
    //    //float s7 = sensors.get(6).hasContact() ? sensors.get(6).getDistanceContact() : 0;
    //    //float s8 = sensors.get(7).hasContact() ? sensors.get(7).getDistanceContact() : 0;
//
    //    float dst = 0.4f * WorldPhysics.PTM;
//
    //    s1 *= WorldPhysics.PTM;
    //    s2 *= WorldPhysics.PTM;
    //    s3 *= WorldPhysics.PTM;
    //    s4 *= WorldPhysics.PTM;
    //    s1 = NeuralNetwork.map(s1, -dst, dst, -1f, 1f);
    //    s2 = NeuralNetwork.map(s2, -dst, dst, -1, 1);
    //    s3 = NeuralNetwork.map(s3, -dst, dst, -1, 1);
    //    s4 = NeuralNetwork.map(s4, -dst, dst, -1, 1);
//
    //    float velocityY = doodleModel.getBody().getLinearVelocity().y;
    //    velocityY = NeuralNetwork.map(velocityY, -4, 4, -1f, 1f);
//
    //    //float s9 = sensors.get(0).hasContactWithObstacle() ?  sensors.get(0).getDistanceContact() : 0;
    //    //float s10 = sensors.get(1).hasContactWithObstacle() ? sensors.get(1).getDistanceContact() : 0;
    //    //float s11 = sensors.get(2).hasContactWithObstacle() ? sensors.get(2).getDistanceContact() : 0;
    //    //float s12 = sensors.get(3).hasContactWithObstacle() ? sensors.get(3).getDistanceContact() : 0;
    //    //float s13 = sensors.get(4).hasContactWithObstacle() ? sensors.get(4).getDistanceContact() : 0;
    //    //float s14 = sensors.get(5).hasContactWithObstacle() ? sensors.get(5).getDistanceContact() : 0;
    //    //float s15 = sensors.get(6).hasContactWithObstacle() ? sensors.get(6).getDistanceContact() : 0;
    //    //float s16 = sensors.get(7).hasContactWithObstacle() ? sensors.get(7).getDistanceContact() : 0;
//
    //    neuralNetwork.feedforward(s1, s2, s3, s4/*, s5, s6, s7, s8, s9, s10, s11, s12, s13, s14, s15, s16, velocityY*/);
//
    //        if (neuralNetwork.listOutputAxon.get(0).getValue() > 0.5f) {
    //            doodleModel.goLeft();
    //        }
    //        if (neuralNetwork.listOutputAxon.get(1).getValue() > 0.5f) {
    //            doodleModel.goRight();
    //        }
    //}



    private float widthVisionBelow, heightVisionBelow;
    private float widthVisionAbove, heightVisionAbove;
    public void think() {
        PlateformModel platformBelow = LevelManager.get().getPlatformBehind(doodleModel);
        if (platformBelow != null && doodleModel.isAlive()) {
//
            widthVisionBelow = platformBelow.getX() - doodleModel.getX();
            heightVisionBelow = platformBelow.getY() - doodleModel.getY();
            widthVisionBelow *= WorldPhysics.PTM;
            heightVisionBelow *= WorldPhysics.PTM;
//
            heightVisionBelow = FeedForwardNeuralNetwork.map(heightVisionBelow, -100, 100, -1, 1);
            widthVisionBelow = FeedForwardNeuralNetwork.map(widthVisionBelow, -100, 100, -1, 1);
//
            float hasObstacleBelow = platformBelow.hasObstacle() ? 1 : -1;
//
            float hasObstacleAbove = 0;
            PlateformModel platformAbove = LevelManager.get().getPlatformAbove(doodleModel);
            if (platformAbove != null) {
                widthVisionAbove = platformAbove.getX() - doodleModel.getX();
                heightVisionAbove = platformAbove.getY() - doodleModel.getY();
                widthVisionAbove *= WorldPhysics.PTM;
                heightVisionAbove *= WorldPhysics.PTM;
                hasObstacleAbove = platformAbove.hasObstacle() ? 1 : -1;
            }
            float velocityY = doodleModel.getBody().getLinearVelocity().y;
            velocityY = FeedForwardNeuralNetwork.map(velocityY, -4, 4, -1f, 1f);
            //float forceLenght = NeuralNetwork.map(body.getLinearVelocity().len(), -100, 100, -1, 1);
            feedForwardNeuralNetwork.feedforward(widthVisionBelow, velocityY/*, hasObstacleBelow, widthVisionAbove, heightVisionAbove, hasObstacleAbove*/);
//
            if (feedForwardNeuralNetwork.listOutputAxon.get(0).getValue() > 0.5f) {
                doodleModel.goLeft();
            } else if (feedForwardNeuralNetwork.listOutputAxon.get(1).getValue() > 0.5f) {
                doodleModel.goRight();
            }
//
        } else {
            widthVisionBelow = 0;
            heightVisionBelow = 0;
            feedForwardNeuralNetwork.clearAxonValues();
        }
    }

    public FeedForwardNeuralNetwork getFeedForwardNeuralNetwork() {
        return feedForwardNeuralNetwork;
    }

    public void renderNeuralNetwork(ShapeRenderer shapeRenderer) {
        feedForwardNeuralNetwork.render(shapeRenderer);
    }

    public void dispose(){
        //for(int i = 0; i < sensors.size; i++){
        //    sensors.get(i).destroyBody();
        //}
    }

}
