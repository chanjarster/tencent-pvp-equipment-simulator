package me.chanjar.pvp.util;

import org.apache.commons.math3.util.CombinatoricsUtils;

import java.util.Arrays;
import java.util.Iterator;

public abstract class CombinationCalculator {

  private CombinationCalculator() {
  }

  /**
   * C(n, r)
   *
   * @param n Number of sample points in set
   * @param r Number of sample points in each combination
   * @return
   */
  public static long C(int n, int r) {
    return CombinatoricsUtils.binomialCoefficient(n, r);
  }

  public static void main(String[] args) {
    Iterator<int[]> iterator = CombinatoricsUtils.combinationsIterator(4, 2);
    while (iterator.hasNext()) {
      int[] next = iterator.next();
      System.out.println(Arrays.toString(next));
    }
  }
}
