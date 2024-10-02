package battleship;

import battleship.game.Cell;
import battleship.game.Coordinate;
import battleship.game.Field;
import battleship.game.Game;
import java.util.Map;
import java.util.UUID;

public class Renderer {

    private final static String[] LETTERS = {"А", "Б", "В", "Г", "Д", "Е", "Ж", "З", "И", "К"};
    private final static Map<Cell, String>
        PLAYER_CELLS = Map.of(Cell.EMPTY, "_", Cell.SHIP, "#", Cell.HIT, "X", Cell.MISS, "*");
    private final static Map<Cell, String>
        OPPONENT_CELLS = Map.of(Cell.EMPTY, "_", Cell.SHIP, "_", Cell.HIT, "X", Cell.MISS, "*");

    public String renderGame(Game game, UUID player, UUID opponent) {
        Field playerField = game.getField(player);
        Field opponentField = game.getField(opponent);

        StringBuilder sb = new StringBuilder();
        sb.append("                0 1 2 3 4 5 6 7 8 9        0 1 2 3 4 5 6 7 8 9\n");
        for (int i = 0; i < Field.SIZE; i++) {
            sb.append(renderShipLeft(playerField, i));
            sb.append(LETTERS[i]).append(" ");
            sb.append(renderLineCells(PLAYER_CELLS, playerField, i));
            sb.append("       ");
            sb.append(renderLineCells(OPPONENT_CELLS, opponentField, i));
            sb.append(LETTERS[i]);
            sb.append(renderShipRight(opponentField, i));
            sb.append("\n");
        }
        return sb.toString();
    }

    private String renderLineCells(Map<Cell, String> cells, Field field, int row) {
        StringBuilder sb = new StringBuilder();
        for (int j = 0; j < Field.SIZE; j++) {
            sb.append(cells.get(field.getCell(new Coordinate(row, j)))).append(" ");
        }
        return sb.toString();
    }

    private String renderShipRight(Field field, int row) {
        return switch (row) {
            case 1 -> "     #### - " + field.getStatistics().getCountQuadrupleShips();
            case 3 -> "     ### - " + field.getStatistics().getCountTripleShips();
            case 5 -> "     ## - " + field.getStatistics().getCountDoubleShips();
            case 7 -> "     # - " + field.getStatistics().getCountSingleShips();
            default -> "";
        };
    }

    private String renderShipLeft(Field field, int row) {
        return switch (row) {
            case 1 -> " " + field.getStatistics().getCountQuadrupleShips() + " - ####     ";
            case 3 -> "  " + field.getStatistics().getCountTripleShips() + " - ###     ";
            case 5 -> "   " + field.getStatistics().getCountDoubleShips() + " - ##     ";
            case 7 -> "    " + field.getStatistics().getCountSingleShips() + " - #     ";
            default -> "              ";
        };
    }

    public String renderYourMove() {
        return "\n\n\n                                   Ваш ход!\n\n";
    }

    public String renderOpponentsMove() {
        return "\n\n\n                                Ход противника.\n\n";
    }

    public String renderWalkPlease() {
        return "\nВыберите клетку, по которой хотите нанести удар, и введите её в формате \"А2\":\n";
    }

    public String renderWaitPlease() {
        return "\nПожалуйста подождите, противник делает ход.\n";
    }

    public String renderYouWin() {
        return "\n\n\nПоздравляю! Вы победили!\n\n";
    }

    public String renderYouLoss() {
        return "\n\n\nСожалею. Вы проиграли.\n\n";
    }
}
