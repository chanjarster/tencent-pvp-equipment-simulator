package me.chanjar.pvp.solver;

import java.util.ArrayList;
import java.util.List;

/**
 * 出装方案-最终装备的出装顺序（不包含子装备）
 */
public class FinalEquipmentPlan {

  private List<String> equipmentIds = new ArrayList<>();

  public FinalEquipmentPlan(List<String> equipmentIds) {
    this.equipmentIds = equipmentIds;
  }

  /**
   * 最终装备的出装顺序
   *
   * @return
   */
  public List<String> getEquipmentIds() {
    return equipmentIds;
  }

}
