package dadm.scaffold.space;

import dadm.scaffold.R;
import dadm.scaffold.engine.GameEngine;
import dadm.scaffold.engine.Item;
import dadm.scaffold.engine.Sprite;

public class TimeItem extends Item {

    public TimeItem(GameEngine gameEngine){
        super(gameEngine, R.drawable.time);

        typeS = "time";

        start(gameEngine);
    }

    @Override
    public void onCollision(GameEngine gameEngine, Sprite collider) {
        //Si colisiona con un jugador
        if(collider.typeS.equals("player")) {
            ((SpaceShipPlayer) collider).reduceTime();
            start(gameEngine);
        }
    }
}
