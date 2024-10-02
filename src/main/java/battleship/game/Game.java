package battleship.game;

import java.util.Map;
import java.util.UUID;

public class Game {

    private final Map<UUID, Field> fields;

    public Game(Map<UUID, Field> fields) {
        this.fields = fields;
    }

    public State takeShot(UUID id, Coordinate coordinate) {
        return fields.get(id).takeShot(coordinate);
    }

    public boolean isTheEnd() {
        boolean flag = false;
        for (Field field : fields.values()) {
            flag = flag || field.isLoss();
        }
        return flag;
    }

    public UUID getWinner() {
        for (UUID id : fields.keySet()) {
            if (!fields.get(id).isLoss()) {
                return id;
            }
        }
        return null;
    }

    public Field getField(UUID id) {
        return fields.get(id);
    }
}
