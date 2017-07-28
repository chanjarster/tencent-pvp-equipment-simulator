package me.chanjar.pvp.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.chanjar.pvp.equipment.model.Equipment;

import java.util.List;

public class EquipmentJsonWriter {

  private ObjectMapper objectMapper;

  public void writeJson(List<Equipment> equipmentList) throws JsonProcessingException {
    objectMapper.writeValueAsString(equipmentList);
  }

}
