package dice;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class Dice {

	/*
	 * For F(1)...F(6) the no. of ways is basically 2^(N-1)
	 * For N>6, it can be treat as F(N) = F(N-1) + F(N-2) + F(N-3) + F(N-4) + F(N-5) + F(N-6)
	 * e.g. F(7) basically a combination for F(6) + F(1) / F(5) + F(2) / F(4) + F(3)
	 */
	//The Map is to cache the F(n) result
	public static Map<BigInteger,BigInteger> m = new HashMap<BigInteger,BigInteger>();
	
	public static BigInteger getWays(int n){
		if(n<=6) return new BigDecimal(java.lang.Math.pow(2,n-1)).toBigInteger();
		//if the f(n) is already cached, return directly from map instead of calculating from scratch again
		if(m.containsKey(new BigInteger(String.valueOf(n)))) 
			return m.get(new BigInteger(String.valueOf(n)));
		else {
			//put the result to the cache if it is new
			m.put(new BigInteger(String.valueOf(n)), getWays(n-1).add(getWays(n-2)).add(getWays(n-3)).add(getWays(n-4)).add(getWays(n-5)).add(getWays(n-6)));
		}
		return getWays(n-1).add(getWays(n-2)).add(getWays(n-3)).add(getWays(n-4)).add(getWays(n-5)).add(getWays(n-6));
	}
	
	public static void main(String[] args) {
		int n=7250;
		System.out.println(Dice.getWays(n));
		
	}
}
