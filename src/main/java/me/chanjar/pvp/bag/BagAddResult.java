package me.chanjar.pvp.bag;

/**
 * 背包添加装备的结果
 */
public enum BagAddResult {

  /**
   * 成功
   */
  SUCCESS,
  /**
   * 容量超出
   */
  CAPACITY_OVERFLOW,
  /**
   * 缺少依赖的装备
   */
  LACK_DEPEND_EQUIPMENT

}
