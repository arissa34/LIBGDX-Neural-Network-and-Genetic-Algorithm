package com.ramimartin.doodlejump.ai;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.ramimartin.Config;
import com.ramimartin.doodlejump.level.LevelManager;
import com.ramimartin.doodlejump.model.DoodleModel;
import com.ramimartin.doodlejump.model.PlateformModel;
import com.ramimartin.doodlejump.physics.WorldPhysics;
import com.ramimartin.neural.FeedForwardNeuralNetwork;

public class DoodleBrain {

    private final DoodleModel doodleModel;
    private final FeedForwardNeuralNetwork feedForwardNeuralNetwork;

    public DoodleBrain(DoodleModel doodleModel) {
        this.doodleModel = doodleModel;
        feedForwardNeuralNetwork = new FeedForwardNeuralNetwork(Config.nbrInput, Config.nbrHiddenLayers, Config.nbrHidden, 2);
    }

    public void update() {
    }

    public void think() {
        if (doodleModel.isAlive()) {
            PlateformModel platformBelow = LevelManager.get().getPlatformBehind(doodleModel);
            PlateformModel platformAbove = LevelManager.get().getPlatformAbove(doodleModel);
            float widthVisionBelow = getWidthVisionBelow(platformBelow);
            float heightVisionBelow = getHeightVisionBelow(platformBelow);
            float widthVisionAbove = getWidthVisionAbove(platformAbove);
            float heightVisionAbove = getHeightVisionAbove(platformAbove);
            float velocityX = getVelocityX();
            float velocityY = getVelocityY();

            feedForwardNeuralNetwork.feedforward(widthVisionBelow, heightVisionBelow, widthVisionAbove, heightVisionAbove, velocityX, velocityY);

            if (feedForwardNeuralNetwork.listOutputAxon.get(0).getValue() > 0f) {
                doodleModel.goLeft(feedForwardNeuralNetwork.listOutputAxon.get(0).getValue());
            } else if (feedForwardNeuralNetwork.listOutputAxon.get(1).getValue() > 0f) {
                doodleModel.goRight(feedForwardNeuralNetwork.listOutputAxon.get(1).getValue());
            }
        } else {
            feedForwardNeuralNetwork.clearAxonValues();
        }
    }

    private float getWidthVisionBelow(PlateformModel platformBelow) {
        if (platformBelow == null) return 0;
        float widthVisionBelow = platformBelow.getX() - doodleModel.getX();
        widthVisionBelow *= WorldPhysics.PTM;
        widthVisionBelow = FeedForwardNeuralNetwork.map(widthVisionBelow, -200, 200, -1, 1);
        return widthVisionBelow;
    }

    private float getHeightVisionBelow(PlateformModel platformBelow) {
        if (platformBelow == null) return 0;
        float  heightVisionBelow = platformBelow.getY() - doodleModel.getY();
        heightVisionBelow *= WorldPhysics.PTM;
        heightVisionBelow = FeedForwardNeuralNetwork.map(heightVisionBelow, -200, 200, -1, 1);
        return heightVisionBelow;
    }

    private float getWidthVisionAbove(PlateformModel platformAbove) {
        if (platformAbove == null) return 0;
        float widthVisionAbove = platformAbove.getX() - doodleModel.getX();
        widthVisionAbove *= WorldPhysics.PTM;
        widthVisionAbove = FeedForwardNeuralNetwork.map(widthVisionAbove, -200, 200, -1, 1);
        return widthVisionAbove;
    }

    private float getHeightVisionAbove(PlateformModel platformAbove) {
        if (platformAbove == null) return 0;
        float heightVisionAbove = platformAbove.getY() - doodleModel.getY();
        heightVisionAbove *= WorldPhysics.PTM;
        heightVisionAbove = FeedForwardNeuralNetwork.map(heightVisionAbove, -200, 200, -1, 1);
        return heightVisionAbove;
    }

    private float getVelocityY() {
        float velocityY = doodleModel.getBody().getLinearVelocity().y;
        velocityY = FeedForwardNeuralNetwork.map(velocityY, -10, 10, -1f, 1f);
        return velocityY;
    }

    private float getVelocityX() {
        float velocityX = doodleModel.getBody().getLinearVelocity().x;
        velocityX = FeedForwardNeuralNetwork.map(velocityX, -10, 10, -1f, 1f);
        return velocityX;
    }

    public FeedForwardNeuralNetwork getFeedForwardNeuralNetwork() {
        return feedForwardNeuralNetwork;
    }

    public void renderNeuralNetwork(ShapeRenderer shapeRenderer) {
        feedForwardNeuralNetwork.render(shapeRenderer);
    }

    public void dispose(){
    }

}
