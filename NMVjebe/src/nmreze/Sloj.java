package nmreze;

public class Sloj {
	// atributi
	private Neuron[] sloj;
	
	public Sloj(int brNeurona, int brUlaza, String fja) {
		sloj = new Neuron[brNeurona];
		//sloj[0] = NULL!!!!
		// NIGDE NISMO POZVALI KONSTRUKTOR NEURONA!!!
		for(int i = 0; i < sloj.length; i++) {
			sloj[i] = new Neuron(brUlaza, fja);
		}
	}
	
	public double[] getY(double[] x) {
		// IZLAZA IMAMO ONLIKO KOLIKO IMAMO NEURONA!!!
		double[] y = new double[sloj.length];
		for(int i = 0; i < sloj.length; i++) {
			y[i] = sloj[i].getY(x);
		}
		return y;
	}
	
	public Neuron getNeuron(int i) {
		return sloj[i];
	}
	
	public void print2console() {
		System.out.println("* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * ");
		for(int i = 0; i < sloj.length; i++) {
			System.out.println("Neuron " + (i+1));
			sloj[i].print2console();
		}
		System.out.println("* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * ");
	}
	
	public void reset() {
		for(int i = 0; i < sloj.length; i++) {
			sloj[i].reset();
		}
	}

	public int getBrojNeurona() {
		return sloj.length;
	}
}
