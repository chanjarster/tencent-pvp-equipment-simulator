package me.chanjar.pvp.simulator;

import me.chanjar.pvp.bag.Bag;
import me.chanjar.pvp.bag.BagAddResult;
import me.chanjar.pvp.equipment.model.Equipment;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BagSimulatorImpl implements BagSimulator {

  @Override
  public boolean isFeasible(Bag bag, List<Equipment> equipmentList) {

    bag.reset();
    for (Equipment equipment : equipmentList) {
      BagAddResult result = bag.buy(equipment);
      if (!BagAddResult.SUCCESS.equals(result)) {
        return false;
      }
    }
    return true;

  }

  @Override
  public List<BagSnapshot> simulate(Bag bag, List<Equipment> equipmentList) {

    bag.reset();
    List<BagSnapshot> results = new ArrayList<>();

    for (Equipment equipment : equipmentList) {
      BagAddResult addResult = bag.buy(equipment);
      if (!BagAddResult.SUCCESS.equals(addResult)) {
        results.add(new BagSnapshot(bag, addResult));
        return results;
      }
      results.add(new BagSnapshot(bag, addResult));
    }

    return results;

  }

}
