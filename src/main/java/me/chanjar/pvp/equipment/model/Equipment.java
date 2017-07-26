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
   * 获得如果要购买此装备的装备数量变化清单（递归的包含依赖装备），比如：
   * <pre>
   *    a
   *   / \
   *  b   c
   * </pre>
   * 那么其结果就是[+1, +1, -1]，因为买b +1、买C +1、买a -2+1=-1（合成掉b,c）
   *
   * @return
   */
  int[] getOccupancyDeltaList();

  /**
   * 类型
   *
   * @return
   */
  EquipmentType getType();

  void setType(EquipmentType type);

  /**
   * 买入价格
   */
  int getPrice();

  void setPrice(int price);

  /**
   * 依赖装备ID列表
   *
   * @return
   */
  List<String> getDependsOn();

  void setDependsOn(List<String> dependsOn);

  /**
   * 卖出价格
   *
   * @return
   */
  int getSellPrice();

  void setSellPrice(int sellPrice);

  /**
   * 备注
   *
   * @return
   */
  String getRemark();

  void setRemark(String remark);

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
   * @param maxPreInsertOffset another序列最多往前插几个偏移量
   * @return
   */
  Permutation calculatePermutation(int maxPreInsertOffset);

  Attribute getAttribute();

  void setAttribute(Attribute attribute);
}
