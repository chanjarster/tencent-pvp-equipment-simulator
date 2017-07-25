package me.chanjar.pvp.bag;

import me.chanjar.pvp.equipment.model.Equipment;

/**
 * TODO Enhancement应该可以做加法、减法，同时被Bag、Equipment使用
 */
public class Enhancement implements Cloneable {



  /**
   * 被装备增强
   *
   * @param equipment
   */
  public void enhanceBy(Equipment equipment) {

    // TODO
  }

  /**
   * 消耗装备，原先的增强要被抵消
   *
   * @param equipment
   */
  public void remove(Equipment equipment) {
    // TODO
  }

  /**
   * clone
   *
   * @return
   */
  public Enhancement clone() {
    // TODO
    Enhancement enhancement = new Enhancement();
    return enhancement;
  }

  /**
   * 恢复原始状态
   */
  public void reset() {

  }
}
