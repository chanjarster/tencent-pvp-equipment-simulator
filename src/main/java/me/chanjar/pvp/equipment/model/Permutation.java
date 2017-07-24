package me.chanjar.pvp.equipment.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 装备购买顺序的排列
 */
public class Permutation {

  /**
   * 装备购买顺序
   */
  private List<Sequence> sequenceList = new ArrayList<>();

  public Permutation(List<Sequence> sequenceList) {
    this.sequenceList = sequenceList;
  }

  public List<Sequence> getSequenceList() {
    return sequenceList;
  }

  /**
   * 两个排列的序列清单笛卡尔积，合并，产生新的结果。举例说明：<br>
   * <br>
   * P1的序列清单：[A1, A2]<br>
   * P2的序列清单：[B1]<br>
   * <br>
   * A1的内部是：[a1, a2]<br>
   * B1的内部是：[b1, b2]<br>
   * <br>
   * 笛卡尔积之后：[A1,B1]，[A2,B2]<br>
   * <br>
   * 拿第一组结果[A1,B1]，做插入工作。把B1插入到A1里面，保留本身内部顺序，那么结果可以有：<br>
   * <br>
   * [a1, a2, b1, b2]<br>
   * [a1, b1, b2, a2]<br>
   * [a1, b1, a2, b2]<br>
   * [b1, b2, a1, a2]<br>
   * [b1, a1, b2, a2]<br>
   * [b1, a1, a2, b2]<br>
   * <br>
   * 看到没有，[a1, a2]始终保持前后顺序，只不过其前、后、中间插入了[b1, b2]，而[b1, b2]也始终保持前后顺序<br>
   *
   * @param another
   * @return 新的
   */
  public Permutation merge(Permutation another) {

    List<Sequence> result = new ArrayList<>();

    for (Sequence sequence1 : sequenceList) {

      for (Sequence sequence2 : another.getSequenceList()) {

        List<Sequence> newSequences = sequence1.mergeInsert(sequence2);
        result.addAll(newSequences);

      }

    }

    return new Permutation(result);

  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Permutation that = (Permutation) o;

    return sequenceList != null ? sequenceList.equals(that.sequenceList) : that.sequenceList == null;
  }

  @Override
  public int hashCode() {
    return sequenceList != null ? sequenceList.hashCode() : 0;
  }

}
