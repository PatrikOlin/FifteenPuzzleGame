import java.util.ArrayList;
import java.util.List;

/**
   	Namn: Patrik Olin
   	Datum 2017-10-15
  	Kurs: Java SE, Iftac
   	Laboration 3 
 */

public class Board {


	private int size = 4;  // Detta ligger med f�r att i framtiden erbjuda spelaren olika br�dstorlekar, t.ex 3x3.
	private List<Tile> board = new ArrayList<Tile>();
	private List<Tile> solvedBoard = new ArrayList<Tile>();
	private int moveCount = 0;
	private int numOfShuffleMoves;

	public enum Direction {
		UP, LEFT, DOWN, RIGHT;
	}

	/**
	 * Konstruktor som skapar och fyller en arraylist, board, med 16 Tiles med värde 0-15 med nollan sist.
	 * Skapar ocks� en till likadan arraylist, solvedBoard som board sedan jämförs med för att se om spelet är löst eller ej.
	 */
	public Board() {
		int tileValue = 1;
		for (int i = 0; i < size*size; i++) {
			if (i < 15) {
				Tile tile = new Tile(tileValue);
				board.add(tile);
				solvedBoard.add(tile);
				tileValue++;
			} else {
				Tile tile = new Tile(0);
				board.add(tile);
				solvedBoard.add(tile);
			}
		}
	}
	
	/**
	 * Slumpar brädet genom att utföra ett antal slumpade förflyttningar. Detta säkerställer att brädet alltid går att lösa,
	 * eftersom reglerna för moveEmptyTile måste följas. Hur många gånger man vill slumpa väljer man genom att ändra numOfShuffleMoves.
	 * @param numOfShuffleMoves	Antalet slumpade förflyttningar som ska utföras
	 */
	public void shuffleBoard(int numOfShuffleMoves) {
		boolean success = true;
		while (numOfShuffleMoves > 0) {
			switch ((int)Math.floor(Math.random()*4)) {
			case 0: 
				success = moveEmptyTile(Direction.UP);
				break;
			case 1:
				success = moveEmptyTile(Direction.LEFT);
				break;
			case 2: 
				success = moveEmptyTile(Direction.DOWN);
				break;
			case 3: 
				success = moveEmptyTile(Direction.RIGHT);
				break;
			}
			if(success) numOfShuffleMoves--;
		}
		moveCount = 0; // Sätter moveCount till noll så att moveCount i slutet stämmer med spelarens förflyttningar.
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < board.size(); i++) {
			sb.append(String.format("%4s", board.get(i)));
		}
		return sb.toString();
	}
	

	/** 
	 * Flyttar den tomma brickan baserat på enum Direction. Letar först upp den tomma brickans position med hjälp av isEmpty-funktionen i Tile
	 * och sparar denna i tmp. Använder sedan setTile och getTile för att byta plats p� närliggande tile och stoppa in den tomma brickan i tmp igen.
	 * @param dir	Enum Direction.
	 * @return		Returnerar true om flytten gick att utföra.
	 */
	public boolean moveEmptyTile(Direction dir) {

		int row = 0;
		int col = 0;
		Tile tmp;
		outer: for(row = 0; row < size; row++) {
			for(col = 0; col < size; col++) {
				if (getTile(row, col).isEmpty()) {
					break outer;
				}
			}
		}
		try {
			switch(dir) {
			case UP:
				tmp = getTile(row, col);
				setTile(row, col, getTile(row - 1, col));
				setTile(row - 1, col, tmp);
				moveCount++;
				return true;
			case LEFT:
				tmp = getTile(row, col);
				if(col - 1 == -1) return false;
				setTile(row, col, getTile(row, col - 1));
				setTile(row, col - 1, tmp);
				moveCount++;
				return true;
			case DOWN:
				tmp = getTile(row, col);
				setTile(row, col, getTile(row + 1, col));
				setTile(row + 1, col, tmp);
				moveCount++;
				return true;
			case RIGHT:
				tmp = getTile(row, col);
				if(col + 1 == 4) return false;
				setTile(row, col, getTile(row, col + 1));
				setTile(row, col + 1, tmp);
				moveCount++;
				return true;
			default:
				return false;
			}
		} catch (Exception IndexOutOfBoundsException) {
			return false;
		}
	}

	/**
	 * En funktion som flyttar en bricka vars row och col matas in. Räknar om row och col till index i arraylist med row * 4 + col och använder sedan index
	 * och funktionen moveEmptyTile för att utföra flyttet, förutsatt att den angivna tilen har en tom tile bredvid sig. Kanske inte den mest eleganta lösningen.
	 * @param row	Raden man vill flytta till.
	 * @param col	Kolumnen man vill flytta till.
	 * @return		Returnerar true om flytten gick att utföra.
	 */
	public boolean moveTile(int row, int col) {
		int tmpIndex = row * 4 + col;
		try {
			if (tmpIndex == 0) {
				if (board.get(1).isEmpty()) {
					moveEmptyTile(Direction.LEFT);
					return true;
				} else if (board.get(4).isEmpty()) {
					moveEmptyTile(Direction.UP);
					return true;
				}
			}
		 if (tmpIndex == 15) {
				if (board.get(11).isEmpty()) {
						moveEmptyTile(Direction.DOWN);
						return true;
					}
				}
			 if (board.get(tmpIndex-1).isEmpty()) {
				moveEmptyTile(Direction.RIGHT);
				return true;
			}  if (board.get(tmpIndex+1).isEmpty()) {
				moveEmptyTile(Direction.LEFT);
				return true;
			}  if (tmpIndex >= 12) {
				if(board.get(8).isEmpty() || board.get(9).isEmpty() || board.get(10).isEmpty() || board.get(11).isEmpty()) {
					moveEmptyTile(Direction.DOWN);
					return true; 
					}
			}  if (board.get(tmpIndex+4).isEmpty()) {
				moveEmptyTile(Direction.UP);
				return true;
			}  if (board.get(tmpIndex-4).isEmpty()) {
				moveEmptyTile(Direction.DOWN);
				return true;
			}
		} catch (Exception IndexOutOfBoundsException) {
			return false;
		}
		return false;
	}

	/**
	 * Hämtar värdet på angiven position.
	 * @param row	Brickans rad.
	 * @param col	Brickans kolumn.
	 * @return
	 */
	public Tile getTile(int row, int col) {
		Tile tempTile = board.get(row * 4 + col);
		return tempTile;
	}

	/**
	 * Stoppar in en tile på brädet på angiven position.
	 * @param row	
	 * @param col
	 * @param tile
	 */
	public void setTile(int row, int col, Tile tile) {
		board.set(row * 4 + col, tile);
	}

	/**
	 * Kollar om brädet är löst genom att jämföra det med solvedBoard som vi vet är löst.
	 * @return
	 */
	public boolean isSolved() {
		if(board.equals(solvedBoard)) {
			return true;
		}
		return false;
	}

	/**
	 * Hämtar moveCount.
	 * @return
	 */
	public int getMoveCount() {
		return moveCount;
	}

	/**
	 * Används av printBoard-funktionen i Game för att använda arraylists get.
	 * @param i
	 * @return
	 */
	public Tile get(int i) {
		return board.get(i);
	}

	/**
	 * Hämtar brädets storlek (som för närvarande alltid är 4).
	 * @return
	 */
	public int getSize() {
		return size;
	}

	/**
	 * Sätter brädets storlek. Används för närvarande inte.
	 * @param size
	 */
	public void setSize(int size) {
		this.size = size;
	}

	/**
	 * Hämtar antal gånger brädet ska utföra slumpmässiga förflyttningar för att slumpa fram ett nytt bräde.
	 * @return
	 */
	public int getNumOfShuffleMoves() {
		return numOfShuffleMoves;
	}

	/**
	 * Sätter antal gånger brädet ska utföra slumpmässiga förflyttningar för att slumpa fram ett nytt bräde.
	 * @param numOfShuffleMoves
	 */
	public void setNumOfShuffleMoves(int numOfShuffleMoves) {
		this.numOfShuffleMoves = numOfShuffleMoves;
	}

}
