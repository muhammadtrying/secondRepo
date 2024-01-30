package uz.pdp.firstTaskThreads;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Main {
    static final int MILLION = 1_000_000;
    static final int NUMBER_OF_THREADS = 5;

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // calculating a sum of the number from one to one million with a basic method
        long startTime = System.currentTimeMillis();
        float sumWithSimpleMethod = 0;
        for (int i = 0; i < MILLION; i++) {
            sumWithSimpleMethod += i;
        }
        long endTime = System.currentTimeMillis();
        long firstTotalTime = endTime - startTime;
        // firstTotalTime is the time, the first method took to execute the task in

        ExecutorService executorService = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
        int numbersPerThread = MILLION / NUMBER_OF_THREADS;
        int startNumber = 1;

        List<Future<Long>> futures = new ArrayList<>();

        long startTime2 = System.currentTimeMillis();

        for (int i = 0; i < NUMBER_OF_THREADS; i++) {
            int endNumber = startNumber + numbersPerThread - 1;
            if ( i == NUMBER_OF_THREADS - 1 ) endNumber = MILLION;
            int finalEndNumber = endNumber;
            FutureTask<Long> future = new FutureTask<>(() -> {
                long sum = 0;
                for (int j = startNumber; j < finalEndNumber; j++) {
                    sum += j;
                }
                return sum;
            });
            future.run();
            futures.add(future);
        }
        long endTime2 = System.currentTimeMillis();
        long secondTotalTime = endTime2 - startTime2;
        float sumWithAdvancedMethod = 0;
        for (Future<Long> future : futures) {
            sumWithAdvancedMethod += future.get();
        }
        System.out.println("Sum with basic method" + sumWithSimpleMethod);
        System.out.println("Sum with advanced method" + sumWithAdvancedMethod);
        System.out.println("Time difference: " + (firstTotalTime - secondTotalTime));
    }
}
