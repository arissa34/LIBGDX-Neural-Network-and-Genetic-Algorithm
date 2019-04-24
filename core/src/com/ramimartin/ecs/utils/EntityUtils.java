package com.ramimartin.ecs.utils;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.ramimartin.ecs.ECS;

public class EntityUtils {

    public static Entity createEntity(){
        Entity entity = new Entity();
        return entity;
    }

    public static Entity registerEntity(){
        Entity entity = new Entity();
        attachEntity(entity);
        return entity;
    }

    public static <T extends Component> Entity registerEntityAndAddComponent(T ...components){
        Entity entity = registerEntity();
        addComponents(entity, components);
        return entity;
    }

    public static <T extends Component> Entity createEntityAndAddComponent(T ...components){
        Entity entity = new Entity();
        addComponents(entity, components);
        return entity;
    }

    public static <T extends Component> T addComponent(Entity entity, T component){
        entity.add(component);
        return component;
    }

    public static <T extends Component> T[] addComponents(Entity entity, T ...components){
        for(Component component : components){
            entity.add(component);
        }
        return components;
    }

    public static Entity removeAllComponents(Entity entity){
        entity.removeAll();
        return entity;
    }

    public synchronized static Entity attachEntity(Entity entity){
        if(!ECS.get().getEngine().getEntities().contains(entity, true)){
            ECS.get().getEngine().addEntity(entity);
        }
        return entity;
    }

    public static Entity dettachEntity(Entity entity){
        if(ECS.get().getEngine().getEntities().contains(entity, true)) {
            ECS.get().getEngine().removeEntity(entity);
        }
        return entity;
    }
}
