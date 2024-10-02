package battleship.players;

import battleship.game.Cell;
import battleship.game.Coordinate;
import battleship.game.Field;
import battleship.game.Game;
import battleship.game.State;
import battleship.generators.AutomaticGenerator;
import battleship.generators.Generator;
import java.util.Arrays;
import java.util.Random;
import java.util.UUID;

public class AI extends Player {

    private final Random random;
    private final Cell[][] cells;

    public AI() {
        random = new Random();
        cells = new Cell[Field.SIZE][Field.SIZE];
    }

    @Override
    public Field createField() {
        initializeCells(cells);
        Generator generator = new AutomaticGenerator();
        return generator.generate();
    }

    private void initializeCells(Cell[][] cells) {
        for (int i = 0; i < Field.SIZE; i++) {
            Arrays.fill(cells[i], Cell.EMPTY);
        }
    }

    @Override
    public State takeShot(Game game, UUID id) {
        pause();
        Coordinate coordinate = findFreeCell();
        cells[coordinate.row()][coordinate.column()] = Cell.MISS;
        return game.takeShot(id, coordinate);
    }

    private Coordinate findFreeCell() {
        int row, column;
        do {
            row = random.nextInt(0, Field.SIZE);
            column = random.nextInt(0, Field.SIZE);
        } while (cells[row][column] != Cell.EMPTY);
        return new Coordinate(row, column);
    }

    private void pause() {
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
