package me.chanjar.pvp.util;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;

import java.util.ArrayList;
import java.util.LinkedHashSet;
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
   * [a1, b1, a2, b2]<br>
   * [b1, a1, a2, b2]<br>
   * [a1, b1, b2, a2]<br>
   * [b1, a1, b2, a2]<br>
   * [b1, b2, a1, a2]<br>
   * <br>
   * 可以看到，[a1, a2]始终保持前后顺序，只不过其前、后、中间插入了[b1, b2]，而[b1, b2]也始终保持前后顺序<br>
   * <br>
   * 可以把这个问题看作是一个组合问题，下面说明：<br>
   * S1: [a1, a2] 可以看作 2 red balls<br>
   * S2: [b1, b2] 可以看走 2 black balls<br>
   * 现在要把red ball和black ball放在一行里，它们之间不必连续，比如 rbrb<br>
   * 所以问题简化为，你要在4个slot里，选择2个作为red，那么结果就是C(4,2)=6，也就是说有6种排法<br>
   * 在定好red ball的位置后，剩余的就是black ball，然后在把[a1, a2]按照顺序填到red ball的位置，[b1, b2]按照顺序填到black ball的位置<br>
   * 所以：数量=C(m + n, n)，m为S1数量，n为S2数量<br>
   * <br>
   * 也可以换一种理解方式：如何在 2 red balls 里插入 2 black balls。<br>
   * 2 red balls 的插入点有 2+1=3 个：_ r _ r _<br>
   * 所以参数里的 maxPreInsertOffset 就是用来控制能够使用哪些插入点的，如果是2，那么就只能使用最后两个插入点： r _ r _ <br>
   * 参考文档：https://math.stackexchange.com/questions/15884/permutation-with-duplicates<br>
   *
   *
   * @param base               被插入的list
   * @param another            插入list
   * @param reservePosition    保留插入点（数组下标），another的元素不能插入在保留插入点，也不能插入在保留插入点之前
   * @param maxPreInsertOffset another序列最多往前插几个偏移量
   * @return
   */
  public static List<List<String>> mergeInsert(List<String> base, List<String> another,
      final int reservePosition, final int maxPreInsertOffset) {

    if (CollectionUtils.isEmpty(another)) {
      List<List<String>> res = new ArrayList<>(1);
      res.add(new ArrayList<>(base));
      return res;
    }

    int[] insertPositions = getInsertPositions(base.size(), reservePosition, maxPreInsertOffset);

    List<String> newAnother = new ArrayList<>(another);
    String anotherFirst = newAnother.remove(0);

    Set<List<String>> res = new LinkedHashSet<>();

    for (int insertPosition : insertPositions) {

      List<String> newBase = new ArrayList<>(base);
      newBase.add(insertPosition, anotherFirst);

      res.addAll(mergeInsert(newBase, newAnother, insertPosition, maxPreInsertOffset));

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
