package me.chanjar.pvp.util;

import me.chanjar.pvp.equipment.model.Equipment;

import java.io.InputStream;
import java.util.List;

public interface EquipmentImporter {

  /**
   * 从excel导入
   *
   * @param is
   * @return
   */
  List<Equipment> importXlsx(InputStream is);

}
