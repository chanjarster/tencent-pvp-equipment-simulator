package me.chanjar.pvp.equipment.model;

import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.testng.Assert.assertEquals;

public class PurchasePlanTest {

  @Test
  public void testCalculateInsertResults() throws Exception {

    PurchasePlan sequence1 = new PurchasePlan(Arrays.asList("a1", "a2"));
    PurchasePlan sequence2 = new PurchasePlan(Arrays.asList("b1", "b2"));

    List<PurchasePlan> sequences = sequence1.combineUnique(sequence2, 3);

    assertEquals(sequences.size(), 6);
    Assertions.assertThat(sequences).contains(new PurchasePlan(Arrays.asList("b1", "b2", "a1", "a2")));
    Assertions.assertThat(sequences).contains(new PurchasePlan(Arrays.asList("b1", "a1", "b2", "a2")));
    Assertions.assertThat(sequences).contains(new PurchasePlan(Arrays.asList("b1", "a1", "a2", "b2")));
    Assertions.assertThat(sequences).contains(new PurchasePlan(Arrays.asList("a1", "b1", "b2", "a2")));
    Assertions.assertThat(sequences).contains(new PurchasePlan(Arrays.asList("a1", "b1", "a2", "b2")));
    Assertions.assertThat(sequences).contains(new PurchasePlan(Arrays.asList("a1", "a2", "b1", "b2")));

  }

  @Test
  public void testCalculateInsertResults2() throws Exception {

    PurchasePlan sequence1 = new PurchasePlan(Arrays.asList("a1", "a2"));
    PurchasePlan sequence2 = new PurchasePlan(Arrays.asList("a1", "a2"));

    List<PurchasePlan> sequences = sequence1.combineUnique(sequence2, 3);

    assertEquals(sequences.size(), 2);
    Assertions.assertThat(sequences).contains(new PurchasePlan(Arrays.asList("a1", "a2", "a1", "a2")));
    Assertions.assertThat(sequences).contains(new PurchasePlan(Arrays.asList("a1", "a1", "a2", "a2")));

  }

  @Test
  public void testCalculateInsertResults3() throws Exception {

    PurchasePlan sequence1 = new PurchasePlan(Arrays.asList("a1"));
    PurchasePlan sequence2 = new PurchasePlan(Arrays.asList("b1", "b2", "b3"));

    List<PurchasePlan> sequences = sequence1.combineUnique(sequence2, 2);

    assertEquals(sequences.size(), 4);
    Assertions.assertThat(sequences).contains(new PurchasePlan(Arrays.asList("a1", "b1", "b2", "b3")));
    Assertions.assertThat(sequences).contains(new PurchasePlan(Arrays.asList("b1", "a1", "b2", "b3")));
    Assertions.assertThat(sequences).contains(new PurchasePlan(Arrays.asList("b1", "b2", "a1", "b3")));
    Assertions.assertThat(sequences).contains(new PurchasePlan(Arrays.asList("b1", "b2", "b3", "a1")));

  }

}
