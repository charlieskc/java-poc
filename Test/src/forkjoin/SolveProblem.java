package forkjoin;

import java.util.Date;
import java.util.concurrent.ForkJoinPool;

public class SolveProblem {



	  public static void main(String[] args) {
	    Problem test = new Problem();
	    // check the number of available processors
	    int nThreads = Runtime.getRuntime().availableProcessors();
	    System.out.println("availableProcessors: " + nThreads);
	    Solver mfj = new Solver(test.getList());
	    ForkJoinPool pool = new ForkJoinPool(nThreads);
	    Date t0 = new Date();
	    pool.invoke(mfj);
	 
	    long result = mfj.getResult();
	    Date t1 = new Date();
	    long timestamp = t1.getTime() - t0.getTime();
	    
	    System.out.println("Done. Result: " + result + " Time spent: " + timestamp);
	    long sum = 0;
	    // check if the result was ok
	    t0 = new Date();
	    for (int i = 0; i < test.getList().length; i++) {
	      sum += test.getList()[i];
	    }
	    t1 = new Date();
	    timestamp = t1.getTime() - t0.getTime();
	    System.out.println("Done. Result: " + sum + " Time spent: " + timestamp);
	  }
		


}
