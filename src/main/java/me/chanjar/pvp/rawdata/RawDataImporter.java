package me.chanjar.pvp.rawdata;

import me.chanjar.pvp.equipment.model.Equipment;

import java.io.InputStream;
import java.util.List;

public interface RawDataImporter {

  /**
   * 从excel导入
   *
   * @param is
   * @return
   */
  List<Equipment> load(InputStream is);

}
