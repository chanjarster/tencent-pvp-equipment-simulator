package me.chanjar.pvp.solver;

import me.chanjar.pvp.equipment.model.Equipment;

import java.math.BigInteger;
import java.util.List;

/**
 * 出装方案可行解求解器
 */
public interface EquipmentSuiteSolver {

  /**
   * 传入要获得的最终装备，大致计算所有装备购买顺序(含子装备)的数量
   *
   * @param finalEquipmentList
   * @return
   */
  BigInteger getPossibleSequenceAmount(List<Equipment> finalEquipmentList);

  /**
   * 计算可行的最终装备出装顺序
   *
   * @param bagCapacity
   * @param finalEquipmentList
   * @return
   */
  List<List<Equipment>> getFeasibleFinalEquipmentSequences(int bagCapacity, List<Equipment> finalEquipmentList);

  /**
   * 根据最终出装顺序，获得可行的装备购买顺序(含子装备)
   *
   * @param bagCapacity        背包容量，目前版本游戏里是6
   * @param finalEquipmentList 最终出装结果
   * @param maxInsertPosAmount 最多尝试几个插入点
   */
  List<List<Equipment>> getEquipmentPurchaseSequences(int bagCapacity, List<Equipment> finalEquipmentList,
      int maxInsertPosAmount);

}
