package me.chanjar.pvp.util;

import me.chanjar.pvp.equipment.model.Equipment;
import me.chanjar.pvp.equipment.repo.EquipmentRepositoryImpl;
import org.testng.annotations.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class EquipmentImporterImplTest {
  @Test
  public void testImportXlsx() throws Exception {

    EquipmentImporterImpl excelImporter = new EquipmentImporterImpl();
    excelImporter.setEquipmentRepository(new EquipmentRepositoryImpl());
    List<Equipment> equipmentList = excelImporter
        .load(EquipmentImporterImplTest.class.getResourceAsStream("/equipment-list.xlsx"));

    assertThat(equipmentList).hasSize(93);

  }

}
