package me.chanjar.pvp.web;

import me.chanjar.pvp.equipment.model.Equipment;
import me.chanjar.pvp.equipment.repo.EquipmentRepository;
import me.chanjar.pvp.solver.EquipmentSuiteSolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping(path = "/equipment")
public class EquipmentRestController {

  private EquipmentRepository equipmentRepository;

  private EquipmentSuiteSolver equipmentSuiteSolver;

  @RequestMapping(method = GET, path = "/all", produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
  public List<Equipment> getAll() {
    return equipmentRepository.getAll();
  }

  @RequestMapping(method = GET, path = "/feasible-final-equipment-sequences", produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
  public Set<List<String>> getFeasibleFinalEquipmentSequences(
      @RequestParam("equipmentIds[]") List<String> equipmentIds,
      @RequestParam("bagCapacity") int bagCapacity) {
    List<Equipment> equipmentList = equipmentRepository.getByIds(equipmentIds);

    List<List<Equipment>> feasibleFinalEquipmentSequences =
        equipmentSuiteSolver.getFeasibleFinalEquipmentSequences(bagCapacity, equipmentList);

    Set<List<String>> result = new LinkedHashSet<>(feasibleFinalEquipmentSequences.size());

    for (List<Equipment> feasibleFinalEquipmentSequence : feasibleFinalEquipmentSequences) {

      result.add(new ArrayList<>(
          feasibleFinalEquipmentSequence.stream().map(Equipment::getId).collect(toList())
      ));

    }

    return result;
  }

  @RequestMapping(method = GET, path = "/equipment-purchase-sequences", produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
  public Set<List<String>> getEquipmentPurchaseSequences(
      @RequestParam("equipmentIds[]") List<String> equipmentIds,
      @RequestParam("bagCapacity") int bagCapacity,
      @RequestParam("maxPreInsertOffset") int maxPreInsertOffset) {
    List<Equipment> equipmentList = equipmentRepository.getByIds(equipmentIds);

    List<List<Equipment>> equipmentPurchaseSequences =
        equipmentSuiteSolver.getEquipmentPurchaseSequences(bagCapacity, equipmentList, maxPreInsertOffset);

    Set<List<String>> result = new LinkedHashSet<>(equipmentPurchaseSequences.size());

    for (List<Equipment> feasibleFinalEquipmentSequence : equipmentPurchaseSequences) {

      result.add(new ArrayList<>(
          feasibleFinalEquipmentSequence.stream().map(Equipment::getId).collect(toList())
      ));

    }

    return result;
  }

  @Autowired
  public void setEquipmentRepository(EquipmentRepository equipmentRepository) {
    this.equipmentRepository = equipmentRepository;
  }

  @Autowired
  public void setEquipmentSuiteSolver(EquipmentSuiteSolver equipmentSuiteSolver) {
    this.equipmentSuiteSolver = equipmentSuiteSolver;
  }
}
