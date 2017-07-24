package me.chanjar.pvp.util;

import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
   * [a1, b1, b2, a2]<br>
   * [a1, b1, a2, b2]<br>
   * [b1, b2, a1, a2]<br>
   * [b1, a1, b2, a2]<br>
   * [b1, a1, a2, b2]<br>
   * <br>
   * 可以看到，[a1, a2]始终保持前后顺序，只不过其前、后、中间插入了[b1, b2]，而[b1, b2]也始终保持前后顺序<br>
   *
   * @param base            被插入的list
   * @param another         插入list
   * @param reservePosition 保留插入点（数组下标），another的元素不能插入在保留插入点，也不能插入在保留插入点之前
   * @return
   */
  public static List<List<String>> mergeInsert(List<String> base, List<String> another,
      int reservePosition) {

    if (CollectionUtils.isEmpty(another)) {
      List<List<String>> res = new ArrayList<>();
      res.add(new ArrayList<>(base));
      return res;
    }

    int[] insertPositions = getInsertPositions(base.size(), reservePosition);

    ArrayList<String> newAnother = new ArrayList<>(another);
    String anotherFirst = newAnother.remove(0);

    Set<List<String>> res = new HashSet<>();

    for (int insertPosition : insertPositions) {

      List<String> newBase = new ArrayList<>(base);
      newBase.add(insertPosition, anotherFirst);

      res.addAll(mergeInsert(newBase, newAnother, insertPosition));

    }

    return new ArrayList<>(res);
  }

  /**
   * 获得插入点（数组下标）。比如有一个数组长度为3，保留插入点为-1（即没有保留），那么返回的结果是：[0, 1, 2, 3]。<br>
   * 如果保留插入点为0，那么返回的结果是：[1, 2, 3]
   *
   * @param listLength      数组or列表长度
   * @param reservePosition 保留的插入点（数组下标），结果不能插在这个点，和这个点之前
   * @return 能够插入的点
   */
  private static int[] getInsertPositions(int listLength, int reservePosition) {

    int[] positions = new int[listLength - reservePosition];

    int i = 0;
    for (int pos = reservePosition + 1; pos <= listLength; pos++) {
      positions[i++] = pos;
    }

    return positions;
  }

}
