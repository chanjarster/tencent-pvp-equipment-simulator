package me.chanjar.pvp.rawdata;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import me.chanjar.pvp.equipment.model.Equipment;
import me.chanjar.pvp.equipment.repo.EquipmentRepositoryImpl;

import java.util.List;

/**
 * 用来生成装备JSON数据的帮助类
 */
public class JsonGenerator {

  public static void main(String[] args) throws JsonProcessingException {

    RawDataImporterImpl rawDataImporter = new RawDataImporterImpl();
    rawDataImporter.setEquipmentRepository(new EquipmentRepositoryImpl());
    List<Equipment> equipmentList = rawDataImporter
        .load(JsonGenerator.class.getResourceAsStream("/raw-data/equipment-list.xlsx"));

    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    String jsonString = objectMapper.writeValueAsString(equipmentList);
    System.out.println(jsonString);

  }
}
