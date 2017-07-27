package me.chanjar.pvp.util;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.math3.util.CombinatoricsUtils;

import java.util.*;

/**
 * 两个list插入的工具
 */
public class ListInsertUtils {
  /**
   * 在保证原先list内部先后顺序的情况下，把一个list插入到另一个list，获得所有结果。举例说明：<br>
   * S1: [a1, a2]<br>
   * S2: [b1, b2]<br>
   * <br>
   * 把S2插入到S1，结果是：<br>
   * <br>
   * [a1, a2, b1, b2]<br>
   * [a1, b1, a2, b2]<br>
   * [b1, a1, a2, b2]<br>
   * [a1, b1, b2, a2]<br>
   * [b1, a1, b2, a2]<br>
   * [b1, b2, a1, a2]<br>
   * <br>
   * 可以看到，[a1, a2]始终保持前后顺序，只不过其前、后、中间插入了[b1, b2]，而[b1, b2]也始终保持前后顺序<br>
   * <br>
   * 其实这是一个组合问题：<br>
   * S1: [a1, a2] 可以看作 2 red balls<br>
   * S2: [b1, b2] 可以看走 2 black balls<br>
   * 现在要把red ball和black ball进行排列，比如 rbrb<br>
   * 所以问题简化为，你要在4个slot里，选择2个放red balls，其余的放black balls，那么结果就是C(4,2)=6，也就是说有6种排法<br>
   * 在定好red/black ball的位置后，把[a1, a2]按照顺序填到red ball的位置，[b1, b2]按照顺序填到black ball的位置<br>
   * 所以：数量=C(m + n, n)，m为S1数量，n为S2数量<br>
   * <br>
   * 也可以换一种理解方式：如何在 2 red balls 里插入 2 black balls。<br>
   * 2 red balls 的插入点有 2+1=3 个：_ r _ r _<br>
   * 所以参数里的 maxResultAmount 就是用来控制能够使用几个插入点的，如果是2，那么就只能使用最后两个插入点： r _ r _ <br>
   * 参考文档：https://math.stackexchange.com/questions/15884/permutation-with-duplicates<br>
   *
   * @param redBalls        例子中的红球
   * @param blackBalls      例子中的黑球
   * @param maxResultAmount 最多返回结果数，避免执行时间过长
   * @return
   */
  public static List<List<String>> combineUnique(List<String> redBalls, List<String> blackBalls,
      final int maxResultAmount) {
    int n = redBalls.size() + blackBalls.size();
    if (n == 0) {
      return Collections.emptyList();
    }

    int k = redBalls.size();
    if (k == 0) {
      return Collections.singletonList(new ArrayList<>(blackBalls));
    }

    LinkedHashSet<List<String>> result = new LinkedHashSet<>(maxResultAmount / 2);

    Iterator<int[]> iterator = CombinatoricsUtils.combinationsIterator(n, k);
    while (iterator.hasNext()) {

      if (result.size() >= maxResultAmount) {
        break;
      }
      int[] next = iterator.next();
      // 获得的是槽的位置

      Iterator<String> redBallIterator = redBalls.iterator();
      String[] strings = new String[n];
      for (int redBallPos : next) {
        strings[redBallPos] = redBallIterator.next();
      }
      Iterator<String> blackBallIterator = blackBalls.iterator();
      for (int i = 0; i < strings.length; i++) {
        if (strings[i] != null) {
          continue;
        }
        strings[i] = blackBallIterator.next();
      }

      result.add(Arrays.asList(strings));
    }

    return new ArrayList<>(result);

  }


  /**
   * {@link #combineUnique(List, List, int)}的另一种实现，递归方式完成的，没有使用第三方工具
   * @return
   */
  private static List<List<String>> combinationUnique2(List<String> base, List<String> another,
      final int reservePosition, final int maxResultAmount) {

    if (CollectionUtils.isEmpty(another)) {
      List<List<String>> res = new ArrayList<>(1);
      res.add(new ArrayList<>(base));
      return res;
    }

    int[] insertPositions = getInsertPositions(base.size(), reservePosition, maxResultAmount);

    List<String> newAnother = new ArrayList<>(another);
    String anotherFirst = newAnother.remove(0);

    Set<List<String>> res = new LinkedHashSet<>();

    for (int insertPosition : insertPositions) {

      List<String> newBase = new ArrayList<>(base);
      newBase.add(insertPosition, anotherFirst);

      res.addAll(combinationUnique2(newBase, newAnother, insertPosition, maxResultAmount));

    }

    return new ArrayList<>(res);
  }

  /**
   * 获得插入点（数组下标）。比如有一个数组长度为3，保留插入点为-1（即没有保留），那么返回的结果是：[3, 2, 1, 0]。<br>
   * 如果保留插入点为0，那么返回的结果是：[3, 2, 1]<br>
   * 之所以返回的结果颠倒是为了先从后面开始试
   *
   * @param listLength      数组or列表长度
   * @param reservePosition 保留的插入点（数组下标），结果不能插在这个点，和这个点之前
   * @return 能够插入的点
   */
  private static int[] getInsertPositions(int listLength, int reservePosition, int maxCount) {

    int[] positions = new int[Math.min(listLength - reservePosition, maxCount)];

    int i = 0;
    for (int pos = listLength; pos > reservePosition && i < positions.length; pos--) {
      positions[i++] = pos;
    }

    return positions;

  }

}
