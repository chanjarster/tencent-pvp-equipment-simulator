package me.chanjar.pvp.solver;

import me.chanjar.pvp.bag.Bag;
import me.chanjar.pvp.equipment.model.Equipment;
import me.chanjar.pvp.equipment.model.Permutation;
import me.chanjar.pvp.equipment.model.Sequence;
import me.chanjar.pvp.equipment.repo.EquipmentRepository;
import me.chanjar.pvp.simulator.BagSimulator;
import me.chanjar.pvp.util.PermutationGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Component
public class EquipmentSuiteSolverImpl implements EquipmentSuiteSolver {

  private static final Logger LOGGER = LoggerFactory.getLogger(EquipmentSuiteSolverImpl.class);

  private EquipmentRepository equipmentRepository;

  private BagSimulator bagSimulator;

  @Override
  public BigInteger getPossibleSequenceAmount(List<Equipment> finalEquipmentList) {

    Equipment aggregator = equipmentRepository.newModel();
    aggregator.setId("_AGGREGATOR_");
    for (Equipment finalEquipment : finalEquipmentList) {
      aggregator.getDependsOn().add(finalEquipment.getId());
    }

    return aggregator.getPossibleSequenceAmount();
  }


  @Override
  public List<List<Equipment>> getFeasibleFinalEquipmentSequences(int bagCapacity, List<Equipment> finalEquipmentList) {

    // 获得装备列表的排列
    List<List<Equipment>> finalEquipmentPermutationList = PermutationGenerator.permuteUnique(finalEquipmentList);

    List<List<Equipment>> result = new ArrayList<>();

    for (List<Equipment> equipmentList : finalEquipmentPermutationList) {

      if (!isRoughlyFeasible(bagCapacity, equipmentList)) {
        continue;
      }

      result.add(equipmentList);
    }

    return result;

  }

  @Override
  public List<List<Equipment>> getEquipmentPurchaseSequences(int bagCapacity, List<Equipment> finalEquipmentList,
      int maxInsertPosAmount) {

    Equipment aggregator = equipmentRepository.newModel();
    aggregator.setId("_AGGREGATOR_");
    for (Equipment finalEquipment : finalEquipmentList) {
      aggregator.getDependsOn().add(finalEquipment.getId());
    }

    Permutation solutionPermutation = aggregator.calculatePermutation(maxInsertPosAmount);

    LOGGER.debug("Find {} possible ways", solutionPermutation.getSequenceList().size());

    List<List<Equipment>> result = new ArrayList<>();

    for (Sequence solutionSequence : solutionPermutation.getSequenceList()) {

      List<String> equipmentIds = solutionSequence.getEquipmentIds();
      // 移除掉最后一个"_AGGREGATOR_"
      equipmentIds.remove(equipmentIds.size() - 1);

      List<Equipment> solutionEquipmentList = equipmentRepository.getByIds(equipmentIds);

      Bag bag = new Bag(bagCapacity);
      if (!bagSimulator.isFeasible(bag, solutionEquipmentList)) {
        continue;
      }

      result.add(solutionEquipmentList);

    }

    return result;

  }


  /**
   * 粗略看一下其是否有可行解，通过判断根据这个顺序购买装备的话，所需要的背包空槽是否会超出背包容量
   *
   * @param finalEquipmentList
   * @return
   */
  private boolean isRoughlyFeasible(int bagCapacity, List<Equipment> finalEquipmentList) {

    int sum = 0;
    for (Equipment equipment : finalEquipmentList) {
      int[] occupancyDeltaList = equipment.getOccupancyDeltaList();
      for (int delta : occupancyDeltaList) {
        sum += delta;
        if (sum > bagCapacity) {
          return false;
        }
      }
    }
    return true;

  }

  @Autowired
  public void setEquipmentRepository(EquipmentRepository equipmentRepository) {
    this.equipmentRepository = equipmentRepository;
  }

  @Autowired
  public void setBagSimulator(BagSimulator bagSimulator) {
    this.bagSimulator = bagSimulator;
  }
}
