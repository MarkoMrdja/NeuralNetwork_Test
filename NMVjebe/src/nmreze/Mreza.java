package nmreze;

public class Mreza {
	private Sloj[] mreza;
	
	/***
	 * Konstruktor klase mreza
	 * @param brUlaza - broj fizickih ulaza
	 * @param config - niz integera koji predstavlja konfiguraciju mreze
	 * @param fje - niz funkcija aktivacija
	 */
	public Mreza(int brUlaza, int[] config, String[] fje) {
		mreza = new Sloj[config.length];
		
		// postoji razlika izmedu ulaznog i ostalih slojeva
		// broj ulaza su fizicki ulazi
		mreza[0] = new Sloj(config[0], brUlaza, fje[0]);
		
		// svi ostali slojevi imaju broj Ulaza jednak broju neurona prethodnog sloja
		for(int i = 1; i < mreza.length; i++) {
			mreza[i] = new Sloj(config[i], config[i-1], fje[i]);
		}
	}
	
	public double[] getY(double[] x) {
		double[] y = mreza[0].getY(x);
		
		for(int i = 1; i < mreza.length; i++) {
			y = mreza[i].getY(y);
		}
		
		return y;
	}
	
	public void reset() {
		for(int i = 0; i < mreza.length; i++) {
			mreza[i].reset();
		}
	}
	
	public void print2console() {
		System.out.println();
		System.out.println("===========================================================");
		for(int i = 0; i < mreza.length; i++) {
			System.out.println("     >>>   Sloj[" + (i+1) + "]  <<<<");
			mreza[i].print2console();
		}
		System.out.println("===========================================================");
		System.out.println();
	}
	
	public Sloj getSloj(int i) {
		return mreza[i];
	}

	public int getBrojSlojeva() {
		return mreza.length;
	}
}
