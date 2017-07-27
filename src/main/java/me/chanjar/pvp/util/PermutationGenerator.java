package me.chanjar.pvp.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * http://www.programcreek.com/2013/02/leetcode-permutations-ii-java/
 */
public abstract class PermutationGenerator {

  private PermutationGenerator() {
    // static
  }

  public static <T> List<List<T>> permuteUnique(List<T> num) {
    List<List<T>> returnList = new ArrayList<>();
    returnList.add(new ArrayList<>());

    for (int i = 0; i < num.size(); i++) {
      Set<List<T>> currentSet = new HashSet<>();
      for (List<T> l : returnList) {
        for (int j = 0; j < l.size() + 1; j++) {
          l.add(j, num.get(i));
          List<T> T = new ArrayList<>(l);
          l.remove(j);
          currentSet.add(T);
        }
      }
      returnList = new ArrayList<>(currentSet);
    }

    return returnList;
  }

}
