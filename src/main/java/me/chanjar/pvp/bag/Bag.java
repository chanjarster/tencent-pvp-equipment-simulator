package me.chanjar.pvp.bag;

import com.fasterxml.jackson.annotation.JsonIgnore;
import me.chanjar.pvp.equipment.model.Attribute;
import me.chanjar.pvp.equipment.model.Equipment;
import org.apache.commons.collections4.CollectionUtils;

import java.util.*;

import static java.util.stream.Collectors.toList;

/**
 * 背包
 */
public class Bag {

  /**
   * 容量
   */
  private int capacity;

  /**
   * 当前装备列表
   */
  private final List<Equipment> currentEquipmentList;

  /**
   * 属性增强
   */
  private Attribute enhancement = new Attribute();

  /**
   * 累计花费金币
   */
  private int spentCoins;

  /**
   * 默认背包容量6
   */
  public Bag() {
    this(6);
  }

  public Bag(int capacity) {
    this.capacity = capacity;
    currentEquipmentList = new ArrayList<>(capacity);
  }

  /**
   * 添加装备，不管容量、是否缺少下级装备
   *
   * @param equipment
   */
  public void add(Equipment equipment) {

    enhanceBy(equipment.getAttribute());
    spentCoins += equipment.getPrice();
    currentEquipmentList.add(equipment);

  }

  /**
   * 买入装备
   *
   * @param equipment
   * @return 容量满了、缺少下级装备、成功
   */
  public BagAddResult buy(Equipment equipment) {

    if (CollectionUtils.isEmpty(equipment.getDependsOn())) {
      // 背包容量不足
      if (currentEquipmentList.size() >= capacity) {
        return BagAddResult.CAPACITY_OVERFLOW;
      }

      currentEquipmentList.add(equipment);
      enhanceBy(equipment.getAttribute());
      spentCoins += equipment.getPrice();
      return BagAddResult.SUCCESS;
    }

    // 合成装备
    List<String> dependsEquipmentIds = equipment.getDependsOn();
    Set<Integer> dependsEquipmentIndices = new HashSet<>(dependsEquipmentIds.size());

    for (int i = 0; i < dependsEquipmentIds.size(); i++) {
      String dependsEquipmentId = dependsEquipmentIds.get(i);
      int dependsEquipmentIndex = getEquipmentIndexOf(dependsEquipmentId, dependsEquipmentIndices);
      if (dependsEquipmentIndex == -1) {
        // 依赖的下级装备还没有
        return BagAddResult.LACK_DEPEND_EQUIPMENT;
      }
      dependsEquipmentIndices.add(dependsEquipmentIndex);
    }

    // 如果当前背包物品数量 - 要被消耗掉的物品数量 依然>=背包容量，那就不能添加这个物品
    if (currentEquipmentList.size() - dependsEquipmentIds.size() >= capacity) {
      return BagAddResult.CAPACITY_OVERFLOW;
    }

    // 消耗装备
    // 必须从最后往前删，如果这样0,1，把0删掉后，原来的1就会往前移，如果此时删掉1，实际上就是删掉原来的2
    ArrayList<Integer> sortIds = new ArrayList<>(dependsEquipmentIndices);
    Collections.sort(sortIds);
    Collections.reverse(sortIds);
    sortIds.forEach(i -> {
      Equipment remove = currentEquipmentList.remove(i.intValue());
      removeEnhance(remove.getAttribute());
    });

    // 添加装备
    enhanceBy(equipment.getAttribute());
    currentEquipmentList.add(equipment);
    spentCoins += equipment.getPrice();
    return BagAddResult.SUCCESS;
  }

  /**
   * 卖出装备
   *
   * @param equipmentId
   */
  public void sell(String equipmentId) {

    int equipmentIndexOf = getEquipmentIndexOf(equipmentId, Collections.emptySet());
    if (equipmentIndexOf == -1) {
      return;
    }

    Equipment equipment = currentEquipmentList.remove(equipmentIndexOf);
    spentCoins -= equipment.getSellPrice();
    removeEnhance(equipment.getAttribute());

  }

  /**
   * @param equipmentId
   * @param excludeIndices 排除掉的下标结果
   * @return
   */
  private int getEquipmentIndexOf(String equipmentId, Set<Integer> excludeIndices) {

    for (int i = 0; i < currentEquipmentList.size(); i++) {
      Equipment equipment = currentEquipmentList.get(i);
      if (equipment.getId().equals(equipmentId) && !excludeIndices.contains(Integer.valueOf(i))) {
        return i;
      }
    }
    return -1;

  }

  /**
   * 获得装备列表
   *
   * @return
   */
  @JsonIgnore
  public List<Equipment> getCurrentEquipmentList() {
    return currentEquipmentList;
  }

  public List<String> getCurrentEquipmentIds() {
    return currentEquipmentList.stream().map(e -> e.getId()).collect(toList());
  }

  /**
   * 被装备增强
   *
   * @param another
   */
  private void enhanceBy(Attribute another) {

    this.enhancement = enhancement.plus(another);

  }

  /**
   * 消耗装备，原先的增强要被抵消
   *
   * @param another
   */
  private void removeEnhance(Attribute another) {

    this.enhancement = enhancement.minus(another);

  }

  /**
   * 获得容量
   *
   * @return
   */
  public int getCapacity() {
    return capacity;
  }

  /**
   * 获得累计花费金币
   *
   * @return
   */
  public int getSpentCoins() {
    return spentCoins;
  }

  public Attribute getEnhancement() {
    return enhancement;
  }

  /**
   * 清空背包，恢复原始状态
   */
  public void reset() {
    currentEquipmentList.clear();
    enhancement = new Attribute();
    spentCoins = 0;
  }
}
