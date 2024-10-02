package battleship.game;

import java.util.List;

public record Ship(
    int size,
    List<Coordinate> coordinates
) { }
