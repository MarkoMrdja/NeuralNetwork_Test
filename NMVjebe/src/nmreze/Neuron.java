package nmreze;

import java.util.Random;

public class Neuron {
	// atributi
	// bijas
	private double b;
	// tezine
	private double[] w;
	// fja
	private String fja;
	// pamtimo poslednju izracunati vrednost
	private double y;
	// delta neurona
	private double delta;
	// promena bijasa
	private double deltaB;
	// promene tezina
	private double[] deltaW;

	// konstruktor
	public Neuron(int brUlaza, String funkcijaAktivacije) {
		fja = funkcijaAktivacije;
		b = 0;
		w = new double[brUlaza];
		deltaB = 0;
		deltaW = new double[brUlaza];
	}

	// metode
	public double getY(double[] x) {
		double z = 0;

		for (int i = 0; i < x.length; i++) {
			z = z + w[i] * x[i];
		}
		z = z + b;

		switch (fja) {
			case "STEP":
				if (z >= 0)
					y = 1;
				else
					y = 0;
				break;
			case "LIN":
				y = z;
				break;
			case "SIG":
				y = 1 / (1 + Math.exp(-z));
				break;
			case "GAUS":
				y = Math.exp(Math.pow(z, 2.0));
				break;
		}
		return y;
	}

	public double getB() {
		return b;
	}
	
	public void setB(double newB) {
		deltaB = newB - b;
		b = newB;
	}

	public String getFja() {
		return fja;
	}

	public double getW(int i) {
		return w[i];
	}

	public void setW(int i, double value) {
		//System.out.println("w[" + i + "] = " + value);
		deltaW[i] = value - w[i];
		w[i] = value;
	}

	public void print2console() {
		System.out.println("- - - - - - - - - - - - - - - - - - - - - - - - ");
		for (int i = 0; i < w.length; i++) {
			System.out.println("w[" + (i + 1) + "] = " + w[i]);
		}
		System.out.println("b = " + b);
		System.out.println("Fja = " + fja);
		System.out.println("- - - - - - - - - - - - - - - - - - - - - - - - ");
	}

	public void reset(double min, double max) {
		Random rnd = new Random();
		// rnd.nextDouble(); vrednost izmedju 0 i 1
		// podesimo sve tezine i bijas na slucajne
		b = (max - min) * rnd.nextDouble() + min;
		for (int i = 0; i < w.length; i++) {
			w[i] = (max - min) * rnd.nextDouble() + min;
		}
	}

	public void reset() {
		reset(-1, 1);
	}

	public double getYprim() {
		double yPrim = 1;
		switch (fja) {
			case "STEP":
			case "LIN":
				yPrim = 1;
				break;
			case "SIG":
				yPrim = y * (1 - y);
				break;
			case "GAUS":
				yPrim = 2*y*Math.exp(Math.pow(y, 2.0));
				break;
		}
		return yPrim;
	}

	public int getBrojUlaza() {
		return w.length;
	}
	
	public double getLastY() {
		return y;
	}
	
	public double getDelta() {
		return delta;
	}
	
	public void setDelta(double value) {
		delta = value;
	}

	public double getDeltaB() {
		return deltaB;
	}

	public double getDeltaW(int t) {
		return deltaW[t];
	}

}
