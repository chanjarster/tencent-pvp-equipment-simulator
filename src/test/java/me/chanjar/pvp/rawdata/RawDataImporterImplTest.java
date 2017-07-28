package me.chanjar.pvp.rawdata;

import me.chanjar.pvp.equipment.model.Equipment;
import me.chanjar.pvp.equipment.model.EquipmentSkill;
import me.chanjar.pvp.equipment.model.EquipmentType;
import me.chanjar.pvp.equipment.repo.EquipmentRepositoryImpl;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.testng.Assert.assertEquals;

public class RawDataImporterImplTest {

  @Test
  public void testLoad() throws Exception {

    RawDataImporterImpl excelImporter = new RawDataImporterImpl();
    excelImporter.setEquipmentRepository(new EquipmentRepositoryImpl());
    List<Equipment> equipmentList = excelImporter
        .load(RawDataImporterImplTest.class.getResourceAsStream("/me/chanjar/pvp/rawdata/raw-data-test.xlsx"));

    assertThat(equipmentList).hasSize(1);

    Equipment equipment = equipmentList.get(0);
    assertEquals(equipment.getId(), "破甲弓");
    assertEquals(equipment.getType(), EquipmentType.ATTACK);
    assertEquals(equipment.getSellPrice(), 1260);
    assertEquals(equipment.getPrice(), 2100);
    assertEquals(equipment.getAttribute().getAttack(), 1);
    assertEquals(equipment.getAttribute().getDefense(), 2);
    assertEquals(equipment.getAttribute().getHpRecoverPer5Secs(), 3);
    assertEquals(equipment.getAttribute().getManaAttack(), 4);
    assertEquals(equipment.getAttribute().getManaDefense(), 5);
    assertEquals(equipment.getAttribute().getMpRecoverPer5Secs(), 6);
    assertEquals(equipment.getAttribute().getMaxMp(), 7);
    assertEquals(equipment.getAttribute().getMaxHp(), 8);
    assertEquals(equipment.getAttribute().getSpeedPct(), 9);
    assertEquals(equipment.getAttribute().getCdCutdownPct(), 10);
    assertEquals(equipment.getAttribute().getAttackSpeedPct(), 11);
    assertEquals(equipment.getAttribute().getBustProbabilityPct(), 12);
    assertEquals(equipment.getAttribute().getAttackSuckBloodPct(), 13);
    assertEquals(equipment.getDependsOn(), Arrays.asList("陨星", "铁剑"));
    assertEquals(equipment.getAttribute().getPassiveSkills(), Arrays.asList(new EquipmentSkill("45%物理护甲穿透", "", true)));
    assertThat(equipment.getAttribute().getInitiativeSkills()).hasSize(0);

  }

}
