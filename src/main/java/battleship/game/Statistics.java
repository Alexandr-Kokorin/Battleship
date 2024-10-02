package battleship.game;

public class Statistics {

    private int countQuadrupleShips;
    private int countTripleShips;
    private int countDoubleShips;
    private int countSingleShips;

    public Statistics() {
        countQuadrupleShips = 1;
        countTripleShips = 2;
        countDoubleShips = 3;
        countSingleShips = 4;
    }

    public void changeCountShips(int size) {
        switch (size) {
            case 1 -> countSingleShips--;
            case 2 -> countDoubleShips--;
            case 3 -> countTripleShips--;
            case 4 -> countQuadrupleShips--;
        }
    }

    public int getCountQuadrupleShips() {
        return countQuadrupleShips;
    }

    public int getCountTripleShips() {
        return countTripleShips;
    }

    public int getCountDoubleShips() {
        return countDoubleShips;
    }

    public int getCountSingleShips() {
        return countSingleShips;
    }
}
