package me.chanjar.pvp.equipment.model;

import java.util.List;

/**
 * 装备
 */
public interface Equipment {

  /**
   * 装备ID
   */
  String getId();

  void setId(String id);

  /**
   * 类型
   *
   * @return
   */
  EquipmentType getType();

  void setType(EquipmentType type);

  /**
   * 价格
   */
  int getPrice();

  void setPrice(int price);

  /**
   * 依赖装备ID列表
   */
  List<String> getDependsOn();

  void setDependsOn(List<String> dependsOn);

  /**
   * 递归的获得依赖装备ID列表
   *
   * @return
   */
  List<String> getDependsOnRecursively();

  /**
   * 获得本装备购买顺序的排列<br>
   * 获得包括自己节点在内的，所有子节点的树遍历结果，遍历结果不是pre-order, in-order, post-order中的任何一种。<br>
   * 而是在保留先后顺序前提下的，所有可能结果，简单来说就是：子节点必须在父节点之前，但是兄弟子节点的先后顺序可以不一样。<br>
   *
   * @return
   */
  Permutation getPermutation();

}
