package program;

import nmreze.Mreza;
import nmreze.Obuka;

public class vjeba {

	public static void main(String[] args) {
		
		// <=30 - 0 , 31..40 - 0.5, >40 - 1
		// 
		double[][] x = {
				{1, 0.5, 0, 0},
				{1, 0, 1, 0},
				{1, 0, 1, 1},
				{0.5, 0, 1, 1},
				{0, 0, 1, 1},
				{0, 0, 1, 0},
				{1, 0.5, 1, 0},
				{0, 0.5, 1, 1},
				{0.5, 0.5, 0, 1},
				{0.5, 1, 1, 0},
				{1, 0.5, 0, 1}
		};
		
		double[][] y = {
				{1},
				{1},
				{0},
				{1},
				{0},
				{1},
				{1},
				{1},
				{1},
				{1},
				{0}
		};
		
		double[][] testX = {
				{0, 1, 0, 0},
				{0, 1, 0, 1},
				{0.5, 1, 0, 0}
		};
		
		double[][] testY = {
				{0},
				{0},
				{1}
		};
		
		int[] config = {2,1};
		String[] fje = {"GAUS", "STEP"};
		int brUlaza = 4;
		
		Mreza m = new Mreza(brUlaza, config, fje);
		
		double ni = 0.1;
		double maxErr = 0;
		int maxIt = 5000;
		
		Obuka o = new Obuka(ni, maxErr, maxIt);
		
		if(o.obuci(m, x, y)) {
			double tacnost = o.getTacnost(m, testX, testY);
			System.out.println("Mreza je obucena sa tacnoscu od: " + tacnost);
			m.print2console();
			
		}
		

	}

}
