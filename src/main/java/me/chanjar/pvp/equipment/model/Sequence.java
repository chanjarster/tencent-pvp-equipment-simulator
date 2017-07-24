package me.chanjar.pvp.equipment.model;

import me.chanjar.pvp.util.ListInsertUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toSet;

/**
 * 装备购买顺序
 */
public class Sequence {

  private List<String> equipmentIds = new ArrayList<>();

  public Sequence(List<String> equipmentIds) {
    this.equipmentIds = equipmentIds;
  }

  public List<String> getEquipmentIds() {
    return equipmentIds;
  }

  /**
   * @param another
   * @return
   * @see ListInsertUtils#mergeInsert(List, List, int)
   */
  public List<Sequence> mergeInsert(Sequence another) {

    List<List<String>> lists = ListInsertUtils.mergeInsert(equipmentIds, another.getEquipmentIds(), -1);

    return new ArrayList<>(lists.stream().map(list -> new Sequence(list)).collect(toSet()));
  }

  /**
   * 追加结果
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

    Sequence sequence = (Sequence) o;

    return equipmentIds != null ? equipmentIds.equals(sequence.equipmentIds) : sequence.equipmentIds == null;
  }

  @Override
  public int hashCode() {
    return equipmentIds != null ? equipmentIds.hashCode() : 0;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("equipmentIds", equipmentIds)
        .toString();
  }

}
