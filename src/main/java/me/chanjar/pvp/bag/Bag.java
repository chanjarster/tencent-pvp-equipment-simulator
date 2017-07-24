package me.chanjar.pvp.bag;

import me.chanjar.pvp.equipment.model.Equipment;
import org.apache.commons.collections4.CollectionUtils;

import java.util.*;

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
   * 当前增强值
   */
  private final Enhancement currentEnhancement = new Enhancement();

  /**
   * 累计花费金币
   */
  private int spentCoins;

  /**
   * 默认背包容量5
   */
  public Bag() {
    this(5);
  }

  public Bag(int capacity) {
    this.capacity = capacity;
    currentEquipmentList = new ArrayList<>(capacity);
  }

  /**
   * 判断这样的出装顺序是否可行，其实就是模拟的把装备按照顺序都添加一遍
   *
   * @param equipmentList
   * @return
   */
  public boolean isFeasible(List<Equipment> equipmentList) {

    for (Equipment equipment : equipmentList) {
      BagAddResult result = add(equipment);
      if (!BagAddResult.SUCCESS.equals(result)) {
        return false;
      }
    }
    reset();
    return true;
  }

  /**
   * 模拟添加装备，获得每次添加装备后的背包快照
   *
   * @param equipmentList
   * @return 背包快照
   */
  public List<BagSnapshot> dryRun(List<Equipment> equipmentList) {

    List<BagSnapshot> results = new ArrayList<>();

    for (Equipment equipment : equipmentList) {
      BagAddResult addResult = add(equipment);
      if (!BagAddResult.SUCCESS.equals(addResult)) {
        results.add(new BagSnapshot(this, addResult));
        return results;
      }
      results.add(new BagSnapshot(this, addResult));
    }

    return results;
  }

  /**
   * 添加装备
   *
   * @param equipment
   * @return 如果容量满了，则不允许再放，返回false；如果容量没满，则添加成功，返回true
   */
  protected BagAddResult add(Equipment equipment) {

    if (CollectionUtils.isEmpty(equipment.getDependsOn())) {
      // 背包容量不足
      if (currentEquipmentList.size() >= capacity) {
        return BagAddResult.CAPACITY_OVERFLOW;
      }

      currentEquipmentList.add(equipment);
      currentEnhancement.enhanceBy(equipment);
      spentCoins = equipment.getPrice();
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
    sortIds.forEach(i -> currentEnhancement.remove(currentEquipmentList.remove(i.intValue())));

    // 添加装备
    currentEnhancement.enhanceBy(equipment);
    currentEquipmentList.add(equipment);
    spentCoins = equipment.getPrice();
    return BagAddResult.SUCCESS;
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
  public List<Equipment> getCurrentEquipmentList() {
    return currentEquipmentList;
  }

  /**
   * 获得当前增幅
   *
   * @return
   */
  public Enhancement getCurrentEnhancement() {
    return currentEnhancement;
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

  /**
   * 清空背包，恢复原始状态
   */
  public void reset() {
    currentEquipmentList.clear();
    currentEnhancement.reset();
    spentCoins = 0;
  }
}
