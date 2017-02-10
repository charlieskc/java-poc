package fibonacci;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class FibSimple {
	
	private static Map<Integer, BigInteger> fibMap = new HashMap<>();

	public static BigInteger f(int i) {
	    if (i == 0) 
	        return BigInteger.ZERO;
	    else if (i == 1)
	    	return BigInteger.ONE;
	    
	    //cache the calculated f(i) result to the fibMap to avoid re-calculation starting from scratch during the recursion
	    if (fibMap.containsKey(i)) {
	        return fibMap.get(i);
	    }
	    BigInteger tmp = f(i - 2).add(f(i - 1));
	    fibMap.put(i, tmp);
	    return tmp;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(FibSimple.f(8181).toString());
	}

}
