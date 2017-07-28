package me.chanjar.pvp.solver;

import me.chanjar.pvp.equipment.model.PurchasePlanPackage;

import java.math.BigInteger;
import java.util.List;

/**
 * 出装方案可行解求解器
 */
public interface EquipmentSuiteSolver {

  /**
   * 计算可行的出装方案
   *
   * @param bagCapacity       背包容量
   * @param finalEquipmentIds 想要计算的最终装备ID，顺序无所谓，因为本方法内部会做permutation，并检验每个结果的可行性
   * @return
   */
  List<FinalEquipmentPlan> calculateFeasibleFinalEquipmentPlans(int bagCapacity, List<String> finalEquipmentIds);

  /**
   * 传入出装方案，大致计算可能的购买方案的数量
   *
   * @param finalEquipmentPlan 出装方案
   * @return
   */
  BigInteger calculatePurchasePlanAmount(FinalEquipmentPlan finalEquipmentPlan);

  /**
   * 传入出装方案，获得可行的购买方案包（含子装备）
   *
   * @param bagCapacity        背包容量，目前版本游戏里是6
   * @param finalEquipmentPlan 出装方案
   * @param maxResultAmount    在最多多少个结果里搜索可行解
   * @return
   */
  PurchasePlanPackage calculatePurchasePlanPackage(int bagCapacity, FinalEquipmentPlan finalEquipmentPlan,
      int maxResultAmount);
}
