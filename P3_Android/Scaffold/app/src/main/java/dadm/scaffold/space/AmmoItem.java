package dadm.scaffold.space;

import dadm.scaffold.R;
import dadm.scaffold.engine.GameEngine;
import dadm.scaffold.engine.Item;
import dadm.scaffold.engine.Sprite;

public class AmmoItem extends Item {

    public AmmoItem(GameEngine gameEngine){
        super(gameEngine, R.drawable.mothership_blue);

        typeS = "life";

        start(gameEngine);
    }


    @Override
    public void onCollision(GameEngine gameEngine, Sprite collider) {
        //Si colisiona con un jugador
        if(collider.typeS.equals("player")) {
            ((SpaceShipPlayer) collider).numberOfBombs = 4;
            start(gameEngine);
        }
    }



}
