package battleship.generators;

import battleship.game.Cell;
import battleship.game.Coordinate;
import battleship.game.Field;
import battleship.game.Ship;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class AutomaticGenerator implements Generator {

    private final static Coordinate[] DIRECTIONS = {new Coordinate(-1, 0), new Coordinate(0, 1),
        new Coordinate(1, 0), new Coordinate(0, -1)};
    private final Random random;

    public AutomaticGenerator() {
        random = new Random();
    }

    @Override
    public Field generate() {
        List<Ship> ships = placeShips();
        return new Field(createCellsWithShips(ships), ships);
    }

    private Cell[][] createEmptyCells() {
        Cell[][] cells = new Cell[Field.SIZE][Field.SIZE];
        for (int i = 0; i < Field.SIZE; i++) {
            Arrays.fill(cells[i], Cell.EMPTY);
        }
        return cells;
    }

    private Cell[][] createCellsWithShips(List<Ship> ships) {
        Cell[][] cells = createEmptyCells();
        for (Ship ship : ships) {
            for (Coordinate coordinate : ship.coordinates()) {
                cells[coordinate.row()][coordinate.column()] = Cell.SHIP;
            }
        }
        return cells;
    }

    private List<Ship> placeShips() {
        List<Ship> ships = new ArrayList<>();
        Cell[][] cells = createEmptyCells();
        for (int count = 1; count < 5; count++) {
            int size = 5 - count;
            for (int i = 0; i < count; i++) {
                ships.add(placeShip(cells, size));
            }
        }
        return ships;
    }

    private Ship placeShip(Cell[][] cells, int size) {
        while (true) {
            Coordinate coordinate = findFreeCell(cells);
            Coordinate direction = findFreeDirection(cells, size, coordinate);
            if (!Objects.isNull(direction)) {
                Ship ship = createShip(coordinate, direction, size);
                markEnvironment(cells, ship);
                return ship;
            }
        }
    }

    private Coordinate findFreeCell(Cell[][] cells) {
        int row, column;
        do {
            row = random.nextInt(0, Field.SIZE);
            column = random.nextInt(0, Field.SIZE);
        } while (cells[row][column] != Cell.EMPTY);
        return new Coordinate(row, column);
    }

    private Coordinate findFreeDirection(Cell[][] cells, int size, Coordinate coordinate) {
        int index = random.nextInt(0, DIRECTIONS.length);
        for (int i = 0; i < DIRECTIONS.length; i++) {
            int temp = (index + i) % DIRECTIONS.length;
            if (checkDirection(cells, size, coordinate, DIRECTIONS[temp])) {
                return DIRECTIONS[temp];
            }
        }
        return null;
    }

    private boolean checkDirection(Cell[][] cells, int size, Coordinate coordinate, Coordinate direction) {
        for (int i = 0; i < size; i++) {
            int row = coordinate.row() + direction.row() * i;
            int column = coordinate.column() + direction.column() * i;
            int actualRow = Math.min(Math.max(0, row), Field.SIZE - 1);
            int actualColumn = Math.min(Math.max(0, column), Field.SIZE - 1);
            if (row != actualRow || column != actualColumn || cells[row][column] != Cell.EMPTY) {
                return false;
            }
        }
        return true;
    }

    private Ship createShip(Coordinate coordinate, Coordinate direction, int size) {
        List<Coordinate> coordinates = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            int row = coordinate.row() + direction.row() * i;
            int column = coordinate.column() + direction.column() * i;
            coordinates.add(new Coordinate(row, column));
        }
        return new Ship(size, coordinates);
    }

    private void markEnvironment(Cell[][] cells, Ship ship) {
        for (Coordinate coordinate : ship.coordinates()) {
            markNeighbors(cells, coordinate);
        }
    }

    private void markNeighbors(Cell[][] cells, Coordinate coordinate) {
        for (int i = Math.max(0, coordinate.row() - 1); i < Math.min(coordinate.row() + 2, Field.SIZE); i++) {
            for (int j = Math.max(0, coordinate.column() - 1); j < Math.min(coordinate.column() + 2, Field.SIZE); j++) {
                if (cells[i][j] == Cell.EMPTY) {
                    cells[i][j] = Cell.SHIP;
                }
            }
        }
    }
}
