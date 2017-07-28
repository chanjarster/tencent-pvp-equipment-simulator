package me.chanjar.pvp.equipment.model;

import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

public class PurchasePlanPackageTest {

  @Test
  public void testMerge() throws Exception {

    PurchasePlanPackage p1 = new PurchasePlanPackage(Collections.singletonList(new PurchasePlan(Arrays.asList("a1"))));
    PurchasePlanPackage p2 = new PurchasePlanPackage(Collections.singletonList(new PurchasePlan(Arrays.asList("b1"))));

    PurchasePlanPackage p3 = p1.merge(p2, 2);
    assertThat(p3.getPurchasePlans()).hasSize(2);
    assertThat(p3.getPurchasePlans()).contains(new PurchasePlan(Arrays.asList("a1", "b1")));
    assertThat(p3.getPurchasePlans()).contains(new PurchasePlan(Arrays.asList("b1", "a1")));

  }

  @Test
  public void testMerge2() throws Exception {

    PurchasePlanPackage p1 = new PurchasePlanPackage(Collections.singletonList(new PurchasePlan(Arrays.asList("a1", "a2"))));
    PurchasePlanPackage p2 = new PurchasePlanPackage(Collections.singletonList(new PurchasePlan(Arrays.asList("b1", "b2"))));

    PurchasePlanPackage p3 = p1.merge(p2, 3);
    assertThat(p3.getPurchasePlans()).hasSize(6);
    assertThat(p3.getPurchasePlans()).contains(new PurchasePlan(Arrays.asList("b1", "b2", "a1", "a2")));
    assertThat(p3.getPurchasePlans()).contains(new PurchasePlan(Arrays.asList("b1", "a1", "b2", "a2")));
    assertThat(p3.getPurchasePlans()).contains(new PurchasePlan(Arrays.asList("b1", "a1", "a2", "b2")));
    assertThat(p3.getPurchasePlans()).contains(new PurchasePlan(Arrays.asList("a1", "b1", "b2", "a2")));
    assertThat(p3.getPurchasePlans()).contains(new PurchasePlan(Arrays.asList("a1", "b1", "a2", "b2")));
    assertThat(p3.getPurchasePlans()).contains(new PurchasePlan(Arrays.asList("a1", "a2", "b1", "b2")));
  }

}
