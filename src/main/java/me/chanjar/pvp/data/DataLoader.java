package me.chanjar.pvp.data;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.chanjar.pvp.equipment.model.Equipment;
import me.chanjar.pvp.equipment.model.EquipmentModel;
import me.chanjar.pvp.equipment.repo.EquipmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.SmartLifecycle;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.List;

@Component
public class DataLoader implements SmartLifecycle {

  private static final Logger LOGGER = LoggerFactory.getLogger(DataLoader.class);

  private volatile boolean running = false;

  private final Object monitor = new Object();

  private ObjectMapper objectMapper;

  private EquipmentRepository equipmentRepository;

  @Override
  public boolean isAutoStartup() {
    return true;
  }

  @Override
  public void stop(Runnable callback) {
    this.stop();
    callback.run();
  }

  @Override
  public void start() {
    synchronized (this.monitor) {
      if (!this.running) {
        doLoad();
      }
      this.running = true;
    }

  }

  private void doLoad() {

    try {
      LOGGER.info("Load equipment data from classpath:/data/equipment-list.json");
      InputStream jsonStream = getClass().getResourceAsStream("/data/equipment-list.json");
      List equipments = objectMapper.readValue(jsonStream, new TypeReference<List<EquipmentModel>>() {
      });
      equipmentRepository.registerBatch(equipments);
      LOGGER.info("{} equipments loaded", equipments.size());
    } catch (Exception e) {
      throw new RuntimeException("Data load failed", e);
    }

  }

  @Override
  public void stop() {
    this.running = false;
  }

  @Override
  public boolean isRunning() {
    return this.running;
  }

  @Autowired
  public void setObjectMapper(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  @Autowired
  public void setEquipmentRepository(EquipmentRepository equipmentRepository) {
    this.equipmentRepository = equipmentRepository;
  }

  @Override
  public int getPhase() {
    return 0;
  }

}
