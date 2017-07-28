package me.chanjar.pvp.solver;

import me.chanjar.pvp.equipment.model.Equipment;
import me.chanjar.pvp.equipment.repo.EquipmentRepository;
import me.chanjar.pvp.equipment.repo.EquipmentRepositoryImpl;
import me.chanjar.pvp.bag.BagSimulatorImpl;
import me.chanjar.pvp.rawdata.RawDataImporter;
import me.chanjar.pvp.rawdata.RawDataImporterImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest(classes = {
    RawDataImporterImpl.class,
    BagSimulatorImpl.class,
    EquipmentRepositoryImpl.class,
    EquipmentSuiteSolverImpl.class })
public class EquipmentSuiteSolverImplTest extends AbstractTestNGSpringContextTests {

  @Autowired
  private RawDataImporter equipmentImporter;

  @Autowired
  private EquipmentRepository equipmentRepository;

  @Autowired
  private EquipmentSuiteSolver equipmentSuiteSolver;

  @BeforeMethod()
  public void initialize() {

    List<Equipment> equipmentList = equipmentImporter
        .load(getClass().getResourceAsStream("/equipment-list.xlsx"));
    equipmentRepository.registerBatch(equipmentList);

  }

  @Test
  public void testCalculateFeasibleFinalEquipmentSequences() throws Exception {

    List<Equipment> finalEquipments = new ArrayList<>();
    finalEquipments.add(equipmentRepository.getById("暗影战斧"));
    finalEquipments.add(equipmentRepository.getById("影忍之足"));
    finalEquipments.add(equipmentRepository.getById("反伤刺甲"));
    finalEquipments.add(equipmentRepository.getById("冰痕之握"));
    finalEquipments.add(equipmentRepository.getById("贤者的庇护"));
    finalEquipments.add(equipmentRepository.getById("不祥征兆"));

    Equipment aggregator = equipmentRepository.newModel();
    aggregator.setId("_AGGREGATOR_");
    for (Equipment finalEquipment : finalEquipments) {
      aggregator.getDependsOn().add(finalEquipment.getId());
    }
    System.out.println(aggregator.getPossibleSequenceAmount());

    final int batCapacity = 6;
    final int maxResultAmount = 2;

//    List<List<Equipment>> lists = equipmentSuiteSolver.calculateFeasibleFinalEquipmentPlans(batCapacity, null);
//    for (List<Equipment> list : lists) {
//      System.out.println(Arrays.toString(list.stream().map(Equipment::getId).toArray()));
      //
      //      List<List<Equipment>> equipmentPurchaseSequences =
      //          equipmentSuiteSolver.getEquipmentPurchaseSequences(batCapacity, list, maxResultAmount);
      //      for (List<Equipment> equipmentPurchaseSequence : equipmentPurchaseSequences) {
      //        System.out.println(Arrays.toString(equipmentPurchaseSequence.stream().map(Equipment::getId).toArray()));
      //      }
//    }

  }

}
