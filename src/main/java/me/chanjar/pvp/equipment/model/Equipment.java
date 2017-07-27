package me.chanjar.pvp.equipment.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.math.BigInteger;
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
   * 获得如果要购买此装备的装备数量变化清单（递归地包含依赖装备），比如：
   * <pre>
   *    a
   *   / \
   *  b   c
   * </pre>
   * 那么其结果就是[+1, +1, -1]，因为买b +1、买C +1、买a -2+1=-1（合成掉b,c）
   *
   * @return
   */
  @JsonIgnore
  int[] getOccupancyDeltaList();

  /**
   * 递归的获得依赖装备ID列表
   *
   * @return
   */
  @JsonIgnore
  List<String> getDependsOnRecursively();

  /**
   * 递归地获得依赖装备的数量
   *
   * @return
   */
  @JsonIgnore
  int getDependsOnAmountRecursively();

  /**
   * 大致计算所有装备购买顺序(含子装备)的数量（递归计算的）。比如：
   * <pre>
   *    a
   *   / \
   *  b   c
   * </pre>
   * 它的装备购买顺序有两种：[b, c, a], [c, b, a]，所以返回2。<br>
   * 计算结果不考虑重复情况，比如a依赖两个b，那么返回的结果依然是2<br>
   * <br>
   * 算法说明见源代码：doc/出装顺序数计算说明.png
   *
   * @return
   */
  @JsonIgnore
  BigInteger getPossibleSequenceAmount();

  /**
   * 获得所有本装备的购买顺序<br>
   * 获得包括自己节点在内的，所有子节点的树遍历结果，遍历结果是对pre-order的再编排。<br>
   * 简单来说就是：子节点必须在父节点之前，但是兄弟子节点的先后顺序可以不一样。<br>
   *
   * @param maxResultAmount 最多返回的结果数量
   * @return
   * @see me.chanjar.pvp.util.ListInsertUtils#combineUnique(List, List, int)
   */
  @JsonIgnore
  Permutation calculatePermutation(int maxResultAmount);

  Attribute getAttribute();

  void setAttribute(Attribute attribute);
}
