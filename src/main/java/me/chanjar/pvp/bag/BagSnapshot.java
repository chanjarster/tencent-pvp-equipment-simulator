package me.chanjar.pvp.bag;

import com.fasterxml.jackson.annotation.JsonIgnore;
import me.chanjar.pvp.bag.Bag;
import me.chanjar.pvp.bag.BagAddResult;
import me.chanjar.pvp.equipment.model.Attribute;
import me.chanjar.pvp.equipment.model.Equipment;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * 背包快照
 */
public class BagSnapshot {

  private final List<Equipment> equipmentList;

  private final Attribute enhancement;

  private final int spentCoins;

  private final BagAddResult bagAddResult;

  public BagSnapshot(Bag bag, BagAddResult bagAddResult) {
    this.equipmentList = new ArrayList<>(bag.getCurrentEquipmentList());
    this.enhancement = bag.getEnhancement().clone();
    this.spentCoins = bag.getSpentCoins();
    this.bagAddResult = bagAddResult;
  }

  @JsonIgnore
  public List<Equipment> getEquipmentList() {
    return equipmentList;
  }

  public List<String> getEquipmentIds() {
    return equipmentList.stream().map(e -> e.getId()).collect(toList());
  }

  public Attribute getEnhancement() {
    return enhancement;
  }

  public int getSpentCoins() {
    return spentCoins;
  }

  public BagAddResult getBagAddResult() {
    return bagAddResult;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
        .append("equipmentList", equipmentList)
        .append("enhancement", enhancement)
        .append("spentCoins", spentCoins)
        .append("bagAddResult", bagAddResult)
        .toString();
  }
}
