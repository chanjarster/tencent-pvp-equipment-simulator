package me.chanjar.pvp.equipment.repo;

import me.chanjar.pvp.equipment.model.Equipment;

import java.util.Collection;
import java.util.List;

/**
 * 装备仓库
 */
public interface EquipmentRepository {

  /**
   * 新建Model
   *
   * @return
   */
  Equipment newModel();

  /**
   * 注册装备
   *
   * @param equipment
   */
  void registerEquipment(Equipment equipment);

  /**
   * 根据ID获得装备
   *
   * @param id
   * @return
   */
  Equipment getById(String id);

  /**
   * 根据ID列表获得装备
   *
   * @param ids
   * @return
   */
  List<Equipment> getByIds(Collection<String> ids);

}
