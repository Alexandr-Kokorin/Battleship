package battleship.players;

import battleship.game.Field;
import battleship.game.Game;
import battleship.game.State;
import java.util.UUID;

public abstract class Player {

    private final UUID id;

    public Player() {
        id = UUID.randomUUID();
    }

    public abstract Field createField();

    public abstract State takeShot(Game game, UUID id);

    public UUID getId() {
        return id;
    }
}
