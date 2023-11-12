package org.example;
/*
  @author   george
  @project   multi-treads23
  @class  MyUtils
  @version  1.0.0 
  @since 12.11.23 - 11.32
*/

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class MyUtils {
    public static void delay(int from, int to) {
        Random random = new Random();

        try {
            TimeUnit.SECONDS.sleep(random.nextInt(from, to));
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }
    }
    public static void delay(double interval) {
        try {
            TimeUnit.MILLISECONDS.sleep( (int) (interval * 1000));
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }
    }

}
