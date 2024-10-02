package battleship.game;

import java.util.List;

public class Field {

    public final static int SIZE = 10;
    private final Cell[][] cells;
    private final List<Ship> ships;
    private final Statistics statistics;

    public Field(Cell[][] cells, List<Ship> ships) {
        this.cells = cells;
        this.ships = ships;
        statistics = new Statistics();
    }

    public State takeShot(Coordinate coordinate) {
        if (getCell(coordinate) != Cell.SHIP) {
            cells[coordinate.row()][coordinate.column()] = Cell.MISS;
            return State.MISS;
        }
        cells[coordinate.row()][coordinate.column()] = Cell.HIT;
        Ship ship = findShip(coordinate);
        if (isDestroy(ship)) {
            markEnvironment(ship);
            statistics.changeCountShips(ship.size());
            ships.remove(ship);
            return State.DESTROY;
        }
        return State.HIT;
    }

    private Ship findShip(Coordinate coordinate) {
        for (Ship ship : ships) {
            for (Coordinate temp : ship.coordinates()) {
                if (temp.equals(coordinate)) {
                    return ship;
                }
            }
        }
        return new Ship(0, List.of());
    }

    private boolean isDestroy(Ship ship) {
        for (Coordinate coordinate : ship.coordinates()) {
            if (getCell(coordinate) != Cell.HIT) {
                return false;
            }
        }
        return true;
    }

    private void markEnvironment(Ship ship) {
        for (Coordinate coordinate : ship.coordinates()) {
            markNeighbors(coordinate);
        }
    }

    private void markNeighbors(Coordinate coordinate) {
        for (int i = Math.max(0, coordinate.row() - 1); i < Math.min(coordinate.row() + 2, SIZE); i++) {
            for (int j = Math.max(0, coordinate.column() - 1); j < Math.min(coordinate.column() + 2, SIZE); j++) {
                if (cells[i][j] == Cell.EMPTY) {
                    cells[i][j] = Cell.MISS;
                }
            }
        }
    }

    public boolean isLoss() {
        return ships.isEmpty();
    }

    public Cell getCell(Coordinate coordinate) {
        return cells[coordinate.row()][coordinate.column()];
    }

    public Statistics getStatistics() {
        return statistics;
    }
}
