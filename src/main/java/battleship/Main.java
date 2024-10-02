package battleship;

import battleship.game.Game;
import battleship.game.State;
import battleship.players.AI;
import battleship.players.Person;
import battleship.players.Player;
import java.util.Map;
import java.util.UUID;

public class Main {

    public static void main(String[] args) {
        Player person = new Person();
        Player ai = new AI();

        Game game = new Game(
            Map.of(
                person.getId(), person.createField(),
                ai.getId(), ai.createField()
            )
        );
        Renderer renderer = new Renderer();

        UUID walkerID = person.getId();
        while (!game.isTheEnd()) {
            if (walkerID.equals(person.getId())) {
                printPersonMove(renderer, game, person, ai);
                State state = person.takeShot(game, ai.getId());
                walkerID = state == State.MISS ? ai.getId() : person.getId();
            } else {
                printAIMove(renderer, game, person, ai);
                State state = ai.takeShot(game, person.getId());
                walkerID = state == State.MISS ? person.getId() : ai.getId();
            }
        }
        printWinner(renderer, game, person, ai);
    }

    private static void printPersonMove(Renderer renderer, Game game, Player person, Player ai) {
        System.out.print(renderer.renderYourMove());
        System.out.print(renderer.renderGame(game, person.getId(), ai.getId()));
        System.out.print(renderer.renderWalkPlease());
    }

    private static void printAIMove(Renderer renderer, Game game, Player person, Player ai) {
        System.out.print(renderer.renderOpponentsMove());
        System.out.print(renderer.renderGame(game, person.getId(), ai.getId()));
        System.out.print(renderer.renderWaitPlease());
    }

    private static void printWinner(Renderer renderer, Game game, Player person, Player ai) {
        if (game.getWinner().equals(person.getId())) {
            System.out.print(renderer.renderYouWin());
        } else {
            System.out.print(renderer.renderYouLoss());
        }
        System.out.print(renderer.renderGame(game, person.getId(), ai.getId()));
    }
}
