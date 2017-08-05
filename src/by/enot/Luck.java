package by.enot;

import java.util.Random;

public class Luck {
	
	public static boolean isLucky(int chance) {
		Random rand = new Random();
		int currentNum = rand.nextInt(100);
		if (currentNum < chance) return true;
		else return false;
	}

}
