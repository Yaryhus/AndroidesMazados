package dadm.scaffold.space;

import dadm.scaffold.R;
import dadm.scaffold.engine.GameEngine;
import dadm.scaffold.engine.Item;
import dadm.scaffold.engine.Sprite;

public class LifeItem extends Item {


    public LifeItem(GameEngine gameEngine) {
        super(gameEngine, R.drawable.lifegen);

        typeS = "life";


        start(gameEngine);


    }


    @Override
    public void onCollision(GameEngine gameEngine, Sprite collider) {
        //Si colisiona con un jugador
        if (collider.typeS.equals("player")) {
            ((SpaceShipPlayer) collider).HP = 10;
            start(gameEngine);
        }
    }


}
