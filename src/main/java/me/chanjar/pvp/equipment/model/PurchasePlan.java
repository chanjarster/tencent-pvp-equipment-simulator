package me.chanjar.pvp.equipment.model;

import me.chanjar.pvp.util.ListInsertUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * 购买方案 - 所有含子装备+最终装备的购买顺序
 */
public class PurchasePlan {

  private List<String> equipmentIds = new ArrayList<>(40);

  public PurchasePlan(List<String> equipmentIds) {
    this.equipmentIds = equipmentIds;
  }

  /**
   * 装备购买顺序
   *
   * @return
   */
  public List<String> getEquipmentIds() {
    return equipmentIds;
  }

  /**
   * @param another
   * @param maxResultAmount
   * @return
   * @see ListInsertUtils#combineUnique(List, List, int)
   */
  public List<PurchasePlan> combineUnique(PurchasePlan another, int maxResultAmount) {

    List<List<String>> lists = ListInsertUtils.combineUnique(equipmentIds, another.getEquipmentIds(), maxResultAmount);

    return lists.stream().map(list -> new PurchasePlan(list)).collect(toList());
  }

  /**
   * 添加装备ID
   *
   * @param id
   */
  public void append(String id) {

    List<String> strings = new ArrayList<>(equipmentIds);
    strings.add(id);
    this.equipmentIds = strings;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    PurchasePlan sequence = (PurchasePlan) o;

    return equipmentIds != null ? equipmentIds.equals(sequence.equipmentIds) : sequence.equipmentIds == null;
  }

  @Override
  public int hashCode() {
    return equipmentIds != null ? equipmentIds.hashCode() : 0;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
        .append("equipmentIds", equipmentIds)
        .toString();
  }

}
