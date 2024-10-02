package battleship.players;

import battleship.game.Coordinate;
import battleship.game.Field;
import battleship.game.Game;
import battleship.game.State;
import battleship.generators.AutomaticGenerator;
import battleship.generators.Generator;
import java.util.Scanner;
import java.util.UUID;

public class Person extends Player {

    private final Scanner scanner;

    public Person() {
        scanner = new Scanner(System.in);
    }

    @Override
    public Field createField() {
        Generator generator = new AutomaticGenerator();
        return generator.generate();
    }

    @Override
    public State takeShot(Game game, UUID id) {
        String input = scanner.nextLine();
        return game.takeShot(id, getCoordinate(input));
    }

    private Coordinate getCoordinate(String input) {
        char x = input.charAt(0);
        char y = input.charAt(1);
        int row = x <= 'К' ? x - 'А' : x - 'а';
        if (x == 'К' || x == 'к') {
            row--;
        }
        int column = y - '0';
        return new Coordinate(row, column);
    }
}
