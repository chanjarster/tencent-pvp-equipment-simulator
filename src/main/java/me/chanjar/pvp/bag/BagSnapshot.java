package me.chanjar.pvp.bag;

import me.chanjar.pvp.equipment.model.Equipment;

import java.util.ArrayList;
import java.util.List;

/**
 * 背包快照
 */
public class BagSnapshot {

  private final List<Equipment> equipmentList;

  private final Enhancement enhancement;

  private final int spentCoins;

  private final BagAddResult bagAddResult;

  public BagSnapshot(Bag bag, BagAddResult bagAddResult) {
    this.equipmentList = new ArrayList<>(bag.getCurrentEquipmentList());
    this.enhancement = bag.getCurrentEnhancement().clone();
    this.spentCoins = bag.getSpentCoins();
    this.bagAddResult = bagAddResult;
  }

  public List<Equipment> getEquipmentList() {
    return equipmentList;
  }

  public Enhancement getEnhancement() {
    return enhancement;
  }

  public int getSpentCoins() {
    return spentCoins;
  }

  public BagAddResult getBagAddResult() {
    return bagAddResult;
  }
}
