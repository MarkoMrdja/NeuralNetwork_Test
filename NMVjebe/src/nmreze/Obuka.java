package nmreze;

public class Obuka {
	// atributi
	private double ni; // brzina obucavanja
	private double maxErr; // dozvoljena greska
	private int maxIt; // maksimalan broj iteracija
	
	// konstruktor
	/***
	 * 
	 * @param ni
	 * @param maxErr
	 * @param maxIt
	 */
	public Obuka(double ni, double maxErr, int maxIt) {
		this.ni = ni;
		this.maxErr = maxErr;
		this.maxIt = maxIt;
	}
	
	// obuka jednog neurona
	public boolean obuci(Neuron n, double[][] ulaz, double[] izlaz) {
		int it = 0;
		double err = 0;
		
		n.reset();
		
		do {
			it++;
			err = 0;
			for(int i = 0; i < ulaz.length; i++) {
				double[] x = ulaz[i];
				double yZel = izlaz[i];
				
				// 1. racunamo izlaz iz neurona
				double yIzr = n.getY(x);
				
				// 2. racunamo gresku
				double tErr = yZel - yIzr;
				err += (tErr * tErr)/2;
				double delta = tErr * n.getYprim();
				
				// 3. podesavamo tezine
				for(int t = 0; t < n.getBrojUlaza(); t++) {
					double newW = n.getW(t) + ni * delta * x[t];
					n.setW(t, newW);
				}
				double newB = n.getB() + ni * delta;
				n.setB(newB);
			}
			
			System.out.println(it + ". err = " + err);
		}while(err > maxErr && it < maxIt);
		
		return err <= maxErr;
	}

	public boolean obuci(Mreza mreza, double[][] x, double[][] y, double beta) {
		double sumErr = 0;
		int it = 0;
		
		mreza.reset();
		
		do {
			sumErr = 0;
			for(int i = 0; i < x.length; i++) {
				mreza.getY(x[i]);
				
				for(int s = mreza.getBrojSlojeva() - 1; s >=0; s--) {
					Sloj sloj = mreza.getSloj(s);
					
					if(s == mreza.getBrojSlojeva() - 1) {
						for(int n = 0; n < sloj.getBrojNeurona(); n++) {
							Neuron neuron = sloj.getNeuron(n);

							double yZeljeno = y[i][n];
							double yIzracunato = neuron.getLastY();
							double err = yZeljeno - yIzracunato;

							double delta = err * neuron.getYprim();
							
							neuron.setDelta(delta);
							
							sumErr += err*err/2;
						}
					}else {
						for(int n = 0; n < sloj.getBrojNeurona(); n++) {
							Neuron neuron = sloj.getNeuron(n);
							
							double delta = 0;
							Sloj slojIspred = mreza.getSloj(s + 1);
							for(int t = 0; t < slojIspred.getBrojNeurona(); t++) {
								Neuron neuronIspred = slojIspred.getNeuron(t);
								
								delta += neuronIspred.getDelta() * neuronIspred.getW(n);
							}
							delta = delta*neuron.getYprim();
							
							neuron.setDelta(delta);
						}
					}
				}
				for(int s = 0; s < mreza.getBrojSlojeva(); s++) {
					Sloj sloj = mreza.getSloj(s);
					
					if(s == 0) {
						for(int n = 0; n < sloj.getBrojNeurona(); n++) {
							Neuron neuron = sloj.getNeuron(n);
							
							for(int t = 0; t < neuron.getBrojUlaza(); t++) {
								double staroW = neuron.getW(t);
								double novoW = staroW + ni*neuron.getDelta()*x[i][t] + beta*neuron.getDeltaW(t);
								neuron.setW(t, novoW);
							}
							double stariB = neuron.getB();
							double noviB = stariB + ni*neuron.getDelta() + beta*neuron.getDeltaB();
							neuron.setB(noviB);
						}
					}else {
						for(int n = 0; n < sloj.getBrojNeurona(); n++) {
							Neuron neuron = sloj.getNeuron(n);
							
							Sloj slojIza = mreza.getSloj(s - 1);
							
							for(int t = 0; t < neuron.getBrojUlaza(); t++) {
								double staroW = neuron.getW(t);
								double novoW = staroW + ni*neuron.getDelta()* slojIza.getNeuron(t).getLastY() + beta*neuron.getDeltaW(t); // izlaz iz odgovarajuceg neurona ispred
								neuron.setW(t, novoW);
							}
							double stariB = neuron.getB();
							double noviB = stariB + ni*neuron.getDelta() + beta*neuron.getDeltaB();
							neuron.setB(noviB);
						}
					}	
				}
			}
			it++;
			System.out.println(it + ". sumErr = " + sumErr);
		}while(sumErr > maxErr && it < maxIt);
		return sumErr <= maxErr;
	}
	
	public boolean obuci(Mreza mreza, double[][] x, double[][] y) {
		return obuci(mreza, x, y,0);
	}
	
	public double getTacnost(Mreza mreza, double[][] testX, double[][] testY, double odstupanje) {
		int pogodak = 0;
		int slucajeva = 0;
		for(int i = 0; i < testX.length; i++) {
			double[] yIz = mreza.getY(testX[i]);
			for(int j = 0; j < yIz.length; j++) {
				slucajeva++;
				if(yIz[j] <= (testY[i][j]+odstupanje) && yIz[j] >= (testY[i][j]-odstupanje)) {
					pogodak++;
				}
			}
		}
		double tacnost = (double)pogodak*100/slucajeva ;
		
		return tacnost;
	}
	
	public double getTacnost(Mreza mreza, double[][] testX, double[][] testY) {
		return getTacnost(mreza, testX, testY, 0);
	}
}
