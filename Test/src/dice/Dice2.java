package dice;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public class Dice2 {
	/* This inner class is an implementation of AtomicBigInteger to deal with large value number and concurrency, since java only provide AtomicInteger but no AtomicBigInteger
	 * The addAndGet is based on the java implementation for AtomicInteger, using infinite loop to check the atomacy and only return if there is no other thread is updating the same AtomicBigInteger 
	 */
	/**
	 * @author Charlie
	 *
	 */
	public final class AtomicBigInteger {

		private final AtomicReference<BigInteger> valueHolder = new AtomicReference<>();

		public String toString(){
			return valueHolder.get().toString();
		}

		public AtomicBigInteger(BigInteger bigInteger) {
			valueHolder.set(bigInteger);
		}

		public BigInteger addAndGet(BigInteger i) {
			for (;;) {
				BigInteger current = valueHolder.get();
				BigInteger next = current.add(i);
				if (valueHolder.compareAndSet(current, next)) {
					return next;
				}
			}
		}
	}

	//Cache the result of factorial - with used permutation calculation
	private static Map<BigInteger, BigInteger> factorialMap = new HashMap<BigInteger, BigInteger>();

	//Calculate factorial of n, i.e. return n!
	public static BigInteger getFactorial(BigInteger n){
		BigInteger inc = BigInteger.ONE;
		BigInteger fact = BigInteger.ONE;

		if(factorialMap.containsKey(n)){
			return factorialMap.get(n);
		}
		else{
			for(int i=0; i<n.intValue(); i++){
				fact = fact.multiply(inc);
				inc = inc.add(BigInteger.ONE);
			}
			factorialMap.put(n, fact);
		}
		return fact;
	}

	public static void main(String[] args) {
		/* The rolling dice puzzle can be treated as 1a + 2b + 3c + 4d + 5e + 6f = N, where a,b,e,d,e,f is the occurrence of the rolling 1,2,...,6 respectively
		* Using a brute force approach to solve all combinations of a,b,c,d,e that matches sum N
		* From most outer to most inner loop represents the face 1 to 6 of the dice, 	
		* In the most inner loop representing face 6 of the dice, and since the max occurrences for showing all 6 should be N/6, the loop ends at ceiling(N/6), and so on for 5/4/3/2
		* Breaks loop if the sum is already > N
		*  */
		
		AtomicBigInteger count = new Dice2().new AtomicBigInteger(BigInteger.ZERO);
		AtomicBigInteger countIgnoreOrder = new Dice2().new AtomicBigInteger(BigInteger.ZERO);

		long start = System.currentTimeMillis();
		//ExcutorService for multi-threading programming
		ExecutorService exec = Executors.newWorkStealingPool();
		int N=50;
		for(int a=0; a<=N; a++){
			//start a new thread to calculate the occurrences, starting from rolling No "1" to N-th "1", i.e. 0 to 610's 1
			exec.execute(new Runnable() {
				int a;
				@Override
				public void run() {
					for(int b=0; b<=Math.ceil(N/2); b++){
						if (a+2*b > N) break; //breaks to improve performance when the result is already > N
						for(int c=0;c<=Math.ceil(N/3);c++){
							if (a+2*b+3*c > N) break;
							for(int d=0;d<=Math.ceil(N/4);d++){
								if (a+2*b+3*c+4*d > N) break;
								for(int e=0;e<=Math.ceil(N/5);e++){
									if (a+2*b+3*c+4*d+5*e > N) break;
									for(int f=0;f<=Math.ceil(N/6);f++){										
										if (a+2*b+3*c+4*d+5*e+6*f == N){
											//If ordering does not matter, simply count the occurrences
											countIgnoreOrder.addAndGet(BigInteger.ONE);
											//System.out.println(a + "," + b + "," + c + "," + d + "," + e + "," + f);
										
											/* If ordering does matter, need to take care of permutation
											* Consider when N = 19, one of the result can be Three 1's, Two 2's, Four 3's would be like 3,2,4,0,0,0 (i.e. 3*1 + 2*2 + 4*3 = 19)
											* It can be like 11122333, 11133322, 12233311, etc.
											* For Permutation with repeated item, the formula would be N!/(no of occurrence of 1)! *(no of occurrence of 2)! *...* (no of occurrence of 6)!
											* i.e. N!/a!b!c!d!e!f!, e.g. for the above it would be  (3+2+4)!/3!2!3! 
											*/
											int sum = a+b+c+d+e+f;
											BigInteger x= Dice2.getFactorial(new BigInteger(String.valueOf(sum)));
											BigInteger y= (Dice2.getFactorial(new BigInteger(String.valueOf(a))).multiply(
													Dice2.getFactorial(new BigInteger(String.valueOf(b)))).multiply(
															Dice2.getFactorial(new BigInteger(String.valueOf(c)))).multiply(
																	Dice2.getFactorial(new BigInteger(String.valueOf(d))).multiply(
																			Dice2.getFactorial(new BigInteger(String.valueOf(e))).multiply(
																					Dice2.getFactorial(new BigInteger(String.valueOf(f)))))));
											count.addAndGet(x.divide(y));
										}
										if (a+2*b+3*c+4*d+5*e+6*f > N) break;
									}
								}
							}
						}
					}
				}
				public Runnable init(int a) {
					this.a=a;
					return(this);
				}
			}.init(a));
		}
		exec.shutdown();

		try {
			//just in case it takes 1 day
			exec.awaitTermination(1, TimeUnit.DAYS);

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		long timespent = (System.currentTimeMillis() - start)/1000;

		System.out.println("count with ordering = \n" + count.toString() + "\ncount without ordering = \n" + countIgnoreOrder.toString() + "\nTime Spent = " + String.valueOf(timespent) + "s");

	}
}
