import java.util.Scanner;

/**
	Namn: Patrik Olin
	Datum 2017-10-15
	Kurs: Java SE, Iftac
	Laboration 3 
*/

public class Game {

	private static Board.Direction dir;

	/**
	 * Tar emot spelarens input (WASD) och översätter detta till Direction enum.
	 * @param playerChoice	Spelarens val av WASD via vår scanner keyboardInput.
	 * @return	dir, en enum av Direction i Board.
	 */
	public static Board.Direction playerInput(String playerChoice) {
		if (playerChoice.contains("w")) {
			dir = Board.Direction.UP;
			return dir;
		} else if (playerChoice.contains("a")) {
			dir = Board.Direction.LEFT;
			return dir;
		} else if (playerChoice.contains("s")) {
			dir = Board.Direction.DOWN;
			return dir;
		} else if (playerChoice.contains("d")) {
			dir = Board.Direction.RIGHT;
			return dir;
		} else {
			System.out.println("Felaktig input, använd WASD-tangenterna!");
		}
		return dir;
	}

	/**
	 * Printar ut brädet i rätt format.
	 * @param board	Brädet som ska printas ut.
	 */
	public static void printBoard(Board board) {
		for (int i = 0; i < board.getSize()*4; i++) {
			if (i % 4 == 0 && i > 0) {
				System.out.println("");					
			}
			System.out.print(String.format("%4s", board.get(i)));
		}
		System.out.println("\n");
	}

	public static void main(String[] args) {

		Scanner keyboardInput = new Scanner(System.in);
		boolean gameLoop = true;
		Board board = new Board();
		while (gameLoop) {
			System.out.println("Välkommen till 15-spelet.\n");
			board.shuffleBoard(1);
			printBoard(board);
			do {
				System.out.println("Ditt drag [W/A/S/D]:");
				String playerChoice = keyboardInput.next().toLowerCase();
				playerInput(playerChoice);
				board.moveEmptyTile(dir);
				printBoard(board);

			} while (!board.isSolved());

			System.out.println("Grattis, du löste det! Det tog dig " + board.getMoveCount() + " drag!");
			System.out.println("Vill du spela igen? [Ja/Nej]");
			String playAgain = keyboardInput.next().toLowerCase();
			if (playAgain.contains("n")) {
				gameLoop = false;
			}
		}
		keyboardInput.close();
	}

}
