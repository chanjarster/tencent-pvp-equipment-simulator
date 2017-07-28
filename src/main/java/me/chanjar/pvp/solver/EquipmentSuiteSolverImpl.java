package me.chanjar.pvp.solver;

import me.chanjar.pvp.bag.Bag;
import me.chanjar.pvp.bag.BagSimulator;
import me.chanjar.pvp.equipment.model.Equipment;
import me.chanjar.pvp.equipment.model.PurchasePlan;
import me.chanjar.pvp.equipment.model.PurchasePlanPackage;
import me.chanjar.pvp.equipment.repo.EquipmentRepository;
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
  public List<FinalEquipmentPlan> calculateFeasibleFinalEquipmentPlans(int bagCapacity, List<String> finalEquipmentIds) {

    List<List<String>> finalEquipmentPermutationList = PermutationGenerator.permuteUnique(finalEquipmentIds);

    List<FinalEquipmentPlan> result = new ArrayList<>();

    for (List<String> ids : finalEquipmentPermutationList) {

      List<Equipment> equipments = equipmentRepository.getByIds(ids);

      if (!isRoughlyFeasible(bagCapacity, equipments)) {
        continue;
      }

      result.add(new FinalEquipmentPlan(ids));
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

  @Override
  public BigInteger calculatePurchasePlanAmount(FinalEquipmentPlan finalEquipmentPlan) {

    Equipment aggregator = equipmentRepository.newModel();
    aggregator.setId("_AGGREGATOR_");
    finalEquipmentPlan.getEquipmentIds().stream().forEach(id -> aggregator.getDependsOn().add(id));

    return aggregator.getPossibleSequenceAmount();
  }


  @Override
  public PurchasePlanPackage calculatePurchasePlanPackage(int bagCapacity, FinalEquipmentPlan finalEquipmentPlan,
      int maxResultAmount) {

    Equipment aggregator = equipmentRepository.newModel();
    aggregator.setId("_AGGREGATOR_");
    finalEquipmentPlan.getEquipmentIds().stream().forEach(id -> aggregator.getDependsOn().add(id));

    PurchasePlanPackage possiblePackage = aggregator.getPurchasePlanPackage(maxResultAmount);

    LOGGER.debug("Find {} possible ways", possiblePackage.getPurchasePlans().size());


    List<PurchasePlan> purchasePlans = new ArrayList<>();

    for (PurchasePlan possiblePurchasePlan : possiblePackage.getPurchasePlans()) {

      List<String> equipmentIds = possiblePurchasePlan.getEquipmentIds();
      // 移除掉最后一个"_AGGREGATOR_"
      equipmentIds.remove(equipmentIds.size() - 1);

      List<Equipment> solutionEquipments = equipmentRepository.getByIds(equipmentIds);

      Bag bag = new Bag(bagCapacity);
      if (!bagSimulator.isFeasible(bag, solutionEquipments)) {
        continue;
      }

      purchasePlans.add(possiblePurchasePlan);

    }

    return new PurchasePlanPackage(purchasePlans);

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
