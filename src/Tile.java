/**
   	Namn: Patrik Olin
   	Datum 2017-10-15
  	Kurs: Java SE, Iftac
   	Laboration 3 
 */

public class Tile {

	private int value;

	/**
	 * Konstruktor som skapar en tile med ett värde.
	 * @param value	Värdet på brickan
	 */
	public Tile(int value) {
		this.value = value;
	}

	/**
	 * Metod som h�mtar en tiles värde.
	 * @return	Returnerar brickans value
	 */
	public int getValue() {
		return value;	
	}

	/**
	 * Metod som sätter en tiles värde mellan 0 och 15.
	 * @param value	Värdet på brickan
	 */
	public void setValue(int value) {
		if(value <= 0 || value >= 15) {
		this.value = value;
		}
	}
	
	/**
	 * Metod som kollar om en tile är tom (innehåller värdet 0).
	 * @return	true om brickans värde är 0.
	 */
	public boolean isEmpty() {
		if (value == 0)
			return true;
		else
			return false;
	}
	
	 public String toString () {
		 if (getValue() == 0) {
			 return " ";
		 }
		 return String.format("%02d", getValue());
	 }
	
}
