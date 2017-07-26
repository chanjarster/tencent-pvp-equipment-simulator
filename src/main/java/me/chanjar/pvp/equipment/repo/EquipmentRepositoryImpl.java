package me.chanjar.pvp.equipment.repo;

import me.chanjar.pvp.equipment.model.Equipment;
import me.chanjar.pvp.equipment.model.EquipmentModel;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.*;

import static java.util.stream.Collectors.toList;

@Component
public class EquipmentRepositoryImpl implements EquipmentRepository {

  private Map<String, Equipment> equipmentMap = new LinkedHashMap<>(100);

  @Override
  public Equipment newModel() {
    EquipmentModel model = new EquipmentModel();
    model.setEquipmentRepository(this);
    return model;
  }

  @Override
  public void registerEquipment(Equipment equipment) {

    if (StringUtils.isBlank(equipment.getId())) {
      throw new IllegalArgumentException("Equipment id is null");
    }

    ((EquipmentModel) equipment).setEquipmentRepository(this);
    equipmentMap.put(equipment.getId(), equipment);
  }

  @Override
  public void registerEquipments(Collection<Equipment> equipmentCollection) {
    equipmentCollection.stream().forEach(e -> registerEquipment(e));
  }

  @Override
  public Equipment getById(String id) {
    return equipmentMap.get(id);
  }

  @Override
  public List<Equipment> getByIds(Collection<String> ids) {
    if (CollectionUtils.isEmpty(ids)) {
      return Collections.emptyList();
    }
    return ids.stream().map(id -> getById(id)).collect(toList());
  }

  @Override
  public List<Equipment> getAll() {
    return new ArrayList<>(equipmentMap.values());
  }

}
