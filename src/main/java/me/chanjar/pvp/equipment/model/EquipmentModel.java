package me.chanjar.pvp.equipment.model;

import me.chanjar.pvp.equipment.repo.EquipmentRepository;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EquipmentModel implements Equipment {

  private String id;

  private EquipmentType type;

  private int price;

  private List<String> dependsOn = new ArrayList<>(3);

  private transient EquipmentRepository equipmentRepository;

  @Override
  public EquipmentType getType() {
    return type;
  }

  @Override
  public void setType(EquipmentType type) {
    this.type = type;
  }

  @Override
  public String getId() {
    return id;
  }

  @Override
  public void setId(String id) {
    this.id = id;
  }

  @Override
  public int getPrice() {
    return price;
  }

  @Override
  public void setPrice(int price) {
    this.price = price;
  }

  @Override
  public List<String> getDependsOn() {
    return dependsOn;
  }

  @Override
  public void setDependsOn(List<String> dependsOn) {
    this.dependsOn = dependsOn;
  }

  @Override
  public List<String> getDependsOnRecursively() {

    List<String> result = new ArrayList<>();
    for (String id : dependsOn) {
      result.addAll(equipmentRepository.getById(id).getDependsOnRecursively());
      result.add(id);
    }
    return result;
  }

  @Override
  public Permutation getPermutation() {

    if (CollectionUtils.isEmpty(dependsOn)) {

      return new Permutation(
          Collections.singletonList(
              new Sequence(Collections.singletonList(id))
          )
      );
    }

    List<Permutation> subPermutations = new ArrayList<>();
    for (String id : dependsOn) {
      subPermutations.add(equipmentRepository.getById(id).getPermutation());
    }

    Permutation result = subPermutations.get(0);

    for (int j = 1; j < subPermutations.size(); j++) {
      Permutation sub2 = subPermutations.get(j);
      result = result.merge(sub2);

    }

    result.getSequenceList().stream().forEach(s -> s.append(this.id));
    return result;

  }

  public void setEquipmentRepository(EquipmentRepository equipmentRepository) {
    this.equipmentRepository = equipmentRepository;
  }

}
