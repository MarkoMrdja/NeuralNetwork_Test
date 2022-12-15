package nmreze;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class ReadFile {
	List<double[]> dataSet;
	List<double[]> testSet;
	
	// konstruktor
	/**
	 * 
	 * @param fileName ime fajla
	 * @param brojUzorakaZaTest procenat obucavajuceg skupa koji ce se koristiti za test 1..99%
	 */
	public ReadFile(String fileName, int procenatUzorakaZaTest) {
		dataSet = new ArrayList<double[]>();
		testSet = new ArrayList<double[]>();
		
		// Procitaj ceo fajl i stavi u dataSet
		readAll(fileName);
		
		generisiTestListu(procenatUzorakaZaTest);
		
		System.out.println();
	}

	private void generisiTestListu(int brojUzorakaZaTest) {
		int brojTestPodataka = brojUzorakaZaTest*dataSet.size()/100;
		
		for(int i = 0; i < brojTestPodataka; i++) {
			int broj = dajRnd(0, dataSet.size());
			testSet.add(dataSet.get(broj));
			dataSet.remove(broj);
		}
	}
	
	private int dajRnd(double min, double max) {
		Random r = new Random();
		return  (int)(min + r.nextDouble()*(max - min)); 
	}

	private void readAll(String fileName) {
		File file = new File(fileName);
		try (BufferedReader reader = new BufferedReader(new FileReader(file))){
			String line = "";
			while((line = reader.readLine()) != null) {
				// reader.readLine vraca String -> "3.6216,8.6661,-2.8073,-0.44699,0"
				double[] temp = getDoubleNiz(line);
				dataSet.add(temp);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	// 1.3183,1.9017,-3.3111,0.065071,1
	private double[] getDoubleNiz(String line) {
		String[] podaci = line.split(",");
		double[] temp = new double[podaci.length];
		for(int i = 0; i < podaci.length; i++) {
			temp[i] = Double.parseDouble(podaci[i]);
		}
		return temp;
	}

	/***
	 * 
	 * @param brojIzlaza - broj fizickih izlaza
	 * @return
	 */
	public double[][] getIzlazi(int brojIzlaza) {
		// imamo dataSet, on se sastoji od double[]
		double[][] skup = new double[dataSet.size()][brojIzlaza];
		int odaklePocinjuIzlazi = dataSet.get(0).length - brojIzlaza;
		for(int i = 0; i < skup.length; i++) {
			for(int j = 0; j < skup[i].length; j++) {
				skup[i][j] = dataSet.get(i)[odaklePocinjuIzlazi + j];
			}
		}
		return skup;
	}

	/***
	 * 
	 * @param brojUlaza - broj fizickih ulaza
	 * @return
	 */
	public double[][] getUlazi(int brojUlaza) {
		int brIzlaza = dataSet.get(0).length -  brojUlaza;
		
		// imamo dataSet, on se sastoji od double[]
		//System.out.println("Dimenzije ulaza su " + dataSet.size() + " - " + (dataSet.get(0).length - brIzlaza));
		double[][] skup = new double[dataSet.size()][dataSet.get(0).length - brIzlaza];
		for(int i = 0; i < skup.length; i++) {
			for(int j = 0; j < skup[i].length; j++) {
				skup[i][j] = dataSet.get(i)[j];
			}
		}
		return skup;
	}

	// u nasem primeru poslednji element je izlaz
	// 0.11592,3.2219,-3.4302,-2.8457,1
	// ovo je ulaz 0.11592,3.2219,-3.4302,-2.8457
	// a 1 je izlaz iz mreze
	public double[][] getTestUlazi(int br) {
		// imamo testSet, on se sastoji od double[]
		int brIzlaza = dataSet.get(0).length -  br;
		
		// imamo dataSet, on se sastoji od double[]
		//System.out.println("Dimenzije ulaza su " + testSet.size() + " - " + (testSet.get(0).length - brIzlaza));
		
		double[][] skup = new double[testSet.size()][testSet.get(0).length - brIzlaza];
		for(int i = 0; i < skup.length; i++) {
			for(int j = 0; j < skup[i].length; j++) {
				skup[i][j] = testSet.get(i)[j];
			}
		}
		return skup;
	}

	public double[][] getTestIzlazi(int br) {
		// imamo testSet, on se sastoji od double[]
		double[][] skup = new double[testSet.size()][br];
		int odaklePocinjuIzlazi = testSet.get(0).length - br;
		for(int i = 0; i < skup.length; i++) {
			for(int j = 0; j < skup[i].length; j++) {
				skup[i][j] = testSet.get(i)[odaklePocinjuIzlazi + j];
			}
		}
		return skup;
	}
	
	public int ukupanBrojPodataka() {
		return dataSet.size() + testSet.size();
	}
}
