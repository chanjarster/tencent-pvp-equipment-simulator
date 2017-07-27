package me.chanjar.pvp.bag;

import me.chanjar.pvp.equipment.model.Equipment;

import java.util.List;

public interface BagSimulator {

  /**
   * 判断装备购买序列是否可行
   *
   * @param bag
   * @param equipmentList 装备购买序列，要注意，下级装备必须在上级装备之前
   * @return
   */
  boolean isFeasible(Bag bag, List<Equipment> equipmentList);

  /**
   * 根据出装顺序，获得每次购买之后的增幅即其他信息
   *
   * @param bag
   * @param equipmentList
   * @return
   */
  List<BagSnapshot> simulate(Bag bag, List<Equipment> equipmentList);

}
