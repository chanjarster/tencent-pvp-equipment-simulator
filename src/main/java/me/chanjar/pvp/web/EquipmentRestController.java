package me.chanjar.pvp.web;

import me.chanjar.pvp.bag.Bag;
import me.chanjar.pvp.bag.BagAddResult;
import me.chanjar.pvp.bag.BagSimulator;
import me.chanjar.pvp.bag.BagSnapshot;
import me.chanjar.pvp.equipment.model.Equipment;
import me.chanjar.pvp.equipment.model.PurchasePlanPackage;
import me.chanjar.pvp.equipment.repo.EquipmentRepository;
import me.chanjar.pvp.rawdata.RawDataImporter;
import me.chanjar.pvp.solver.EquipmentSuiteSolver;
import me.chanjar.pvp.solver.FinalEquipmentPlan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;
import java.text.NumberFormat;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping(path = "/equipment")
public class EquipmentRestController {

  private EquipmentRepository equipmentRepository;

  private EquipmentSuiteSolver equipmentSuiteSolver;

  private RawDataImporter equipmentImporter;

  private BagSimulator bagSimulator;

  /**
   * 获得所有装备
   *
   * @return
   */
  @RequestMapping(method = GET, path = "/all", produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
  public List<Equipment> getAll() {
    return equipmentRepository.getAll();
  }

  /**
   * @see EquipmentSuiteSolver#calculateFeasibleFinalEquipmentPlans(int, List)
   */
  @RequestMapping(method = GET, path = "/feasible-final-equipment-plans", produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
  public List<FinalEquipmentPlan> calculateFeasibleFinalEquipmentPlans(
      @RequestParam("equipmentIds[]") List<String> equipmentIds,
      @RequestParam("bagCapacity") int bagCapacity) {

    return equipmentSuiteSolver.calculateFeasibleFinalEquipmentPlans(bagCapacity, equipmentIds);

  }

  /**
   * @see EquipmentSuiteSolver#calculatePurchasePlanAmount(FinalEquipmentPlan)
   */
  @RequestMapping(method = GET, path = "/purchase-plan-amount")
  public String calculatePurchasePlanAmount(@RequestParam("equipmentIds[]") List<String> equipmentIds) {

    BigInteger result = equipmentSuiteSolver.calculatePurchasePlanAmount(new FinalEquipmentPlan(equipmentIds));
    return NumberFormat.getIntegerInstance().format(result);

  }

  /**
   * @see EquipmentSuiteSolver#calculatePurchasePlanPackage(int, FinalEquipmentPlan, int)
   */
  @RequestMapping(method = GET, path = "/purchase-plan-package", produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
  public PurchasePlanPackage calculatePurchasePlanPackage(
      @RequestParam("equipmentIds[]") List<String> equipmentIds,
      @RequestParam("bagCapacity") int bagCapacity,
      @RequestParam("maxResultAmount") int maxResultAmount) {

    PurchasePlanPackage purchasePlanPackage = equipmentSuiteSolver
        .calculatePurchasePlanPackage(bagCapacity, new FinalEquipmentPlan(equipmentIds), maxResultAmount);

    return purchasePlanPackage;
  }

  /**
   * 传入最终装备列表，获得最终效果
   *
   * @param equipmentIds
   * @return
   */
  @RequestMapping(method = GET, path = "/final-effect")
  public BagSnapshot getFinalEffect(@RequestParam("equipmentIds[]") List<String> equipmentIds) {

    Bag bag = new Bag(equipmentIds.size());
    List<Equipment> equipmentList = equipmentRepository.getByIds(equipmentIds);
    equipmentList.stream().forEach(e -> bag.add(e));

    return new BagSnapshot(bag, BagAddResult.SUCCESS);

  }

  /**
   * @see BagSimulator#simulate(Bag, List)
   */
  @RequestMapping(method = GET, path = "/progress-effects")
  public List<BagSnapshot> getProgressEffect(
      @RequestParam("bagCapacity") int bagCapacity,
      @RequestParam("equipmentIds[]") List<String> equipmentIds) {

    List<Equipment> equipmentList = equipmentRepository.getByIds(equipmentIds);
    return bagSimulator.simulate(new Bag(bagCapacity), equipmentList);
  }

  @Autowired
  public void setEquipmentRepository(EquipmentRepository equipmentRepository) {
    this.equipmentRepository = equipmentRepository;
  }

  @Autowired
  public void setEquipmentSuiteSolver(EquipmentSuiteSolver equipmentSuiteSolver) {
    this.equipmentSuiteSolver = equipmentSuiteSolver;
  }

  @Autowired
  public void setEquipmentImporter(RawDataImporter equipmentImporter) {
    this.equipmentImporter = equipmentImporter;
  }

  @Autowired
  public void setBagSimulator(BagSimulator bagSimulator) {
    this.bagSimulator = bagSimulator;
  }
}
