package drop7;

/**
 * @author keshav
 */

import java.lang.Math;

public class Permutation {
	// radix of the permutation
	// i.e. the set { (1,1), (1,2), (1,3), (2,1), (2,2), ...} has a radix of 3
	private int radix;
	
	// Length of each permutation
	// i.e. the set above has a length of 2
	private int length;
	
	/**
	 * 
	 * @param radix - radix of the permutation 
	 * @param length - length of the permutation
	 */
	public Permutation(int radix, int length) {
		this.radix = radix;
		this.length = length;
	}
	
	/**
	 * Generates an integer permutation corresponding to the num'th permutation in the total permutation set.
	 * @param num - permutation number
	 * @return int[] containing the values of the permutation
	 */
	public int[] get(double num) {
		int[] perm = new int[length];
		for (int i = 0 ; i < length ; i++) {
			perm[i] = 1 + (int)(num % ((int)(Math.pow(radix,length - i)))) / 
						 ((int)(Math.pow(radix, length - i - 1)));
		}
		return perm;
	}
	
	/**
	 * Returns the number of permutations.
	 * @return 
	 */
	public int length() {
		return (int) Math.pow(radix, length);
	}
}
