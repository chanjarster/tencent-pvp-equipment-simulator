package me.chanjar.pvp.web;

import me.chanjar.pvp.equipment.model.Equipment;
import me.chanjar.pvp.equipment.repo.EquipmentRepository;
import me.chanjar.pvp.solver.EquipmentSuiteSolver;
import me.chanjar.pvp.util.EquipmentImporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigInteger;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping(path = "/equipment")
public class EquipmentRestController {

  private EquipmentRepository equipmentRepository;

  private EquipmentSuiteSolver equipmentSuiteSolver;

  private EquipmentImporter equipmentImporter;

  @RequestMapping(method = GET, path = "/all", produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
  public List<Equipment> getAll() {
    return equipmentRepository.getAll();
  }

  /**
   * 传入要获得的最终装备，获得可行的最终装备出装顺序
   *
   * @param equipmentIds
   * @param bagCapacity
   * @return
   */
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

  /**
   * 传入要获得的最终装备，大致计算所有装备购买顺序(含子装备)的数量
   *
   * @param equipmentIds
   * @return
   */
  @RequestMapping(method = GET, path = "/possible-sequence-amount")
  public String getPossibleSequenceAmount(@RequestParam("equipmentIds[]") List<String> equipmentIds) {

    List<Equipment> equipmentList = equipmentRepository.getByIds(equipmentIds);
    BigInteger result = equipmentSuiteSolver.getPossibleSequenceAmount(equipmentList);
    return NumberFormat.getIntegerInstance().format(result);

  }

  /**
   * 传入最终装备出装顺序，获得可行的含子装备的购买顺序
   *
   * @param equipmentIds    最终装备出装顺序
   * @param bagCapacity     背包数量
   * @param maxResultAmount 在最多多少个结果里，查找可行解
   * @return
   */
  @RequestMapping(method = GET, path = "/equipment-purchase-sequences", produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
  public Set<List<String>> getEquipmentPurchaseSequences(
      @RequestParam("equipmentIds[]") List<String> equipmentIds,
      @RequestParam("bagCapacity") int bagCapacity,
      @RequestParam("maxResultAmount") int maxResultAmount) {
    List<Equipment> equipmentList = equipmentRepository.getByIds(equipmentIds);

    List<List<Equipment>> equipmentPurchaseSequences =
        equipmentSuiteSolver.getEquipmentPurchaseSequences(bagCapacity, equipmentList, maxResultAmount);

    Set<List<String>> result = new LinkedHashSet<>(equipmentPurchaseSequences.size());

    for (List<Equipment> feasibleFinalEquipmentSequence : equipmentPurchaseSequences) {

      result.add(new ArrayList<>(
          feasibleFinalEquipmentSequence.stream().map(Equipment::getId).collect(toList())
      ));

    }

    return result;
  }

  /**
   * 上传装备数据excel文件
   *
   * @return
   */
  @RequestMapping(method = POST, path = "/upload-data")
  public String uploadData(MultipartFile file) throws IOException {
    equipmentRepository.deleteAll();
    equipmentImporter.load(file.getInputStream());
    return "success";
  }

  /**
   * 加载内置装备数据
   *
   * @return
   */
  @RequestMapping(method = POST, path = "/load-builtin-data")
  public String loadBuiltinData() {

    equipmentRepository.deleteAll();
    List<Equipment> equipmentList = equipmentImporter
        .load(getClass().getResourceAsStream("/equipment-list.xlsx"));
    equipmentList.forEach(e -> equipmentRepository.register(e));
    return "success";

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
  public void setEquipmentImporter(EquipmentImporter equipmentImporter) {
    this.equipmentImporter = equipmentImporter;
  }
}
