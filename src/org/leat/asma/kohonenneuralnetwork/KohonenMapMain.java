package org.leat.asma.kohonenneuralnetwork;

import java.util.ArrayList;

import Utils.FileReading;
import kohonenMap.KohonenNetwork;
import kohonenMap.LearningFunction;

/**
 * 
 * @author Amidala
 *
 */

public class KohonenMapMain {

	public static void main(String[] args) {
		double maxWeight= 1000;
		ArrayList<double[]> inputVectors ;
		FileReading fRding = new FileReading();
		inputVectors = fRding.readFile("C:/DataSet/outfile.txt");
		int weightNumber = inputVectors.get(0).length;
		int neurNum = inputVectors.size();
		KohonenNetwork kn = new KohonenNetwork(weightNumber, maxWeight, neurNum);
		LearningFunction lf = new LearningFunction(kn, neurNum);
		lf.learn(inputVectors);

	}

}
