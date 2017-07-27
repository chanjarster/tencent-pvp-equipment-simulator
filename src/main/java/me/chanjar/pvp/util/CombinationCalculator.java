package me.chanjar.pvp.util;

import org.apache.commons.math3.util.CombinatoricsUtils;

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

}
