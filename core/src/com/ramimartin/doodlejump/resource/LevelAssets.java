package com.ramimartin.doodlejump.resource;

import com.badlogic.gdx.graphics.Texture;

public class LevelAssets extends AbsAssets {


    private static final String pathBaseTexture = "data/level/texture";

    public static final String ldg_bckg =                  pathBaseTexture + "/loading/ldg_bckg.jpg";

    public static final String ground =                    pathBaseTexture + "/Ground.png";
    public static final String layer_1 =                   pathBaseTexture + "/Layer1.png";
    public static final String layer_2 =                   pathBaseTexture + "/Layer2.png";
    public static final String layer_3 =                   pathBaseTexture + "/Layer3.png";
    public static final String platform =                   pathBaseTexture + "/platform/Platformer5.png";
    public static final String obstacle =                       pathBaseTexture + "/platform/obstacle.png";

    public static final String character_01_die =          pathBaseTexture + "/characters/01/Die.png";
    public static final String character_01_idle =         pathBaseTexture + "/characters/01/Idle.png";
    public static final String character_01_jump =         pathBaseTexture + "/characters/01/Jump.png";

    public static final String character_02_die =          pathBaseTexture + "/characters/02/Die.png";
    public static final String character_02_idle =         pathBaseTexture + "/characters/02/Idle.png";
    public static final String character_02_jump =         pathBaseTexture + "/characters/02/Jump.png";

    public static final String character_03_die =          pathBaseTexture + "/characters/03/Die.png";
    public static final String character_03_idle =         pathBaseTexture + "/characters/03/Idle.png";
    public static final String character_03_jump =         pathBaseTexture + "/characters/03/Jump.png";

    public static final String character_04_die =          pathBaseTexture + "/characters/04/Die.png";
    public static final String character_04_idle =         pathBaseTexture + "/characters/04/Idle.png";
    public static final String character_04_jump =         pathBaseTexture + "/characters/04/Jump.png";

    public static final String character_05_die =          pathBaseTexture + "/characters/05/Die.png";
    public static final String character_05_idle =         pathBaseTexture + "/characters/05/Idle.png";
    public static final String character_05_jump =         pathBaseTexture + "/characters/05/Jump.png";

    public static final String character_06_die =          pathBaseTexture + "/characters/06/Die.png";
    public static final String character_06_idle =         pathBaseTexture + "/characters/06/Idle.png";
    public static final String character_06_jump =         pathBaseTexture + "/characters/06/Jump.png";

    public static final String character_07_die =          pathBaseTexture + "/characters/07/Die.png";
    public static final String character_07_idle =         pathBaseTexture + "/characters/07/Idle.png";
    public static final String character_07_jump =         pathBaseTexture + "/characters/07/Jump.png";

    public static final String character_08_die =          pathBaseTexture + "/characters/08/Die.png";
    public static final String character_08_idle =         pathBaseTexture + "/characters/08/Idle.png";
    public static final String character_08_jump =         pathBaseTexture + "/characters/08/Jump.png";

    public static final String character_09_die =          pathBaseTexture + "/characters/09/Die.png";
    public static final String character_09_idle =         pathBaseTexture + "/characters/09/Idle.png";
    public static final String character_09_jump =         pathBaseTexture + "/characters/09/Jump.png";



    public static void loadAll(){
        if(!isLoaded(ground))   load(ground, Texture.class);
        if(!isLoaded(layer_1))  load(layer_1, Texture.class);
        if(!isLoaded(layer_2))  load(layer_2, Texture.class);
        if(!isLoaded(layer_3))  load(layer_3, Texture.class);
        if(!isLoaded(platform)) load(platform, Texture.class);
        if(!isLoaded(obstacle))     load(obstacle, Texture.class);

        if(!isLoaded(character_01_die))  load(character_01_die, Texture.class);
        if(!isLoaded(character_01_idle)) load(character_01_idle, Texture.class);
        if(!isLoaded(character_01_jump)) load(character_01_jump, Texture.class);

        if(!isLoaded(character_02_die))  load(character_02_die, Texture.class);
        if(!isLoaded(character_02_idle)) load(character_02_idle, Texture.class);
        if(!isLoaded(character_02_jump)) load(character_02_jump, Texture.class);

        if(!isLoaded(character_03_die))  load(character_03_die, Texture.class);
        if(!isLoaded(character_03_idle)) load(character_03_idle, Texture.class);
        if(!isLoaded(character_03_jump)) load(character_03_jump, Texture.class);

        if(!isLoaded(character_04_die))  load(character_04_die, Texture.class);
        if(!isLoaded(character_04_idle)) load(character_04_idle, Texture.class);
        if(!isLoaded(character_04_jump)) load(character_04_jump, Texture.class);

        if(!isLoaded(character_05_die))  load(character_05_die, Texture.class);
        if(!isLoaded(character_05_idle)) load(character_05_idle, Texture.class);
        if(!isLoaded(character_05_jump)) load(character_05_jump, Texture.class);

        if(!isLoaded(character_06_die))  load(character_06_die, Texture.class);
        if(!isLoaded(character_06_idle)) load(character_06_idle, Texture.class);
        if(!isLoaded(character_06_jump)) load(character_06_jump, Texture.class);

        if(!isLoaded(character_07_die))  load(character_07_die, Texture.class);
        if(!isLoaded(character_07_idle)) load(character_07_idle, Texture.class);
        if(!isLoaded(character_07_jump)) load(character_07_jump, Texture.class);

        if(!isLoaded(character_08_die))  load(character_08_die, Texture.class);
        if(!isLoaded(character_08_idle)) load(character_08_idle, Texture.class);
        if(!isLoaded(character_08_jump)) load(character_08_jump, Texture.class);

        if(!isLoaded(character_09_die))  load(character_09_die, Texture.class);
        if(!isLoaded(character_09_idle)) load(character_09_idle, Texture.class);
        if(!isLoaded(character_09_jump)) load(character_09_jump, Texture.class);
    }

}
