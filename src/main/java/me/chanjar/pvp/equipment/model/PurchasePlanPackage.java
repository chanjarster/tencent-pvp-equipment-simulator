package me.chanjar.pvp.equipment.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 购买方案包 - 包含多个购买方案
 */
public class PurchasePlanPackage {

  /**
   * 购买方案清单
   */
  private List<PurchasePlan> purchasePlans = new ArrayList<>(100);

  public PurchasePlanPackage(List<PurchasePlan> purchasePlans) {
    this.purchasePlans = purchasePlans;
  }

  public List<PurchasePlan> getPurchasePlans() {
    return purchasePlans;
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
   * @param maxResultAmount 最多返回的结果数量
   * @return 新的
   */
  public PurchasePlanPackage merge(PurchasePlanPackage another, int maxResultAmount) {

    List<PurchasePlan> subList1 = purchasePlans;
    List<PurchasePlan> subList2 = another.getPurchasePlans();

    List<PurchasePlan> result = new ArrayList<>(maxResultAmount);

    for (PurchasePlan sequence1 : subList1) {

      for (PurchasePlan sequence2 : subList2) {

        List<PurchasePlan> newSequences = sequence1.combineUnique(sequence2, maxResultAmount);

        for (PurchasePlan newSequence : newSequences) {

          if (result.size() >= maxResultAmount) {
            return new PurchasePlanPackage(result);
          }
          result.add(newSequence);

        }

      }

    }

    return new PurchasePlanPackage(result);

  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    PurchasePlanPackage that = (PurchasePlanPackage) o;

    return purchasePlans != null ? purchasePlans.equals(that.purchasePlans) : that.purchasePlans == null;
  }

  @Override
  public int hashCode() {
    return purchasePlans != null ? purchasePlans.hashCode() : 0;
  }

}
