package me.chanjar.pvp.equipment.model;

import me.chanjar.pvp.equipment.repo.EquipmentRepository;
import me.chanjar.pvp.equipment.repo.EquipmentRepositoryImpl;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.testng.Assert.assertEquals;

public class EquipmentModelTest {

  private EquipmentRepository equipmentRepository = new EquipmentRepositoryImpl();

  @AfterMethod(alwaysRun = true)
  public void clearEquipmentRepository() {
    equipmentRepository.deleteAll();
  }

  /**
   * <pre>
   * a
   * </pre>
   *
   * @return
   */
  @DataProvider
  public Object[][] tree1() {

    Equipment a = createEquipment("a", null);

    return new Object[][] {
        new Object[] { a }
    };
  }

  @Test(dataProvider = "tree1")
  public void testGetPermutation1(Equipment a) throws Exception {

    PurchasePlanPackage permutation = a.getPurchasePlanPackage(1000);

    assertThat(permutation.getPurchasePlans()).hasSize(1);
    assertThat(permutation.getPurchasePlans()).contains(new PurchasePlan(Arrays.asList("a")));

  }

  @Test(dataProvider = "tree1")
  public void testGetOccupancyDeltaList1(Equipment a) throws Exception {

    int[] occupancyDeltaList = a.getOccupancyDeltaList();

    assertThat(occupancyDeltaList).hasSize(1);
    assertThat(occupancyDeltaList).containsSequence(1);

  }

  @Test(dataProvider = "tree1")
  public void testGetDependsOnAmountRecursively1(Equipment a) throws Exception {

    assertEquals(a.getDependsOnAmountRecursively(), 0);
  }

  @Test(dataProvider = "tree1")
  public void testGetPossibleSequenceAmount1(Equipment a) throws Exception {

    assertEquals(a.getPossibleSequenceAmount().intValue(), 1);
  }


  /**
   * <pre>
   *      a
   *     / \
   *    b   c
   * </pre>
   *
   * @return
   */
  @DataProvider
  public Object[][] tree2() {

    Equipment a = createEquipment("a", new String[] { "b", "c" });
    createEquipment("b", null);
    createEquipment("c", null);

    return new Object[][] {
        new Object[] { a }
    };
  }

  @Test(dataProvider = "tree2")
  public void testGetPermutation2(Equipment a) throws Exception {

    PurchasePlanPackage permutation = a.getPurchasePlanPackage(1000);

    assertThat(permutation.getPurchasePlans()).hasSize(2);
    assertThat(permutation.getPurchasePlans()).contains(new PurchasePlan(Arrays.asList("b", "c", "a")));
    assertThat(permutation.getPurchasePlans()).contains(new PurchasePlan(Arrays.asList("c", "b", "a")));
  }

  @Test(dataProvider = "tree2")
  public void testGetOccupancyDeltaList2(Equipment a) throws Exception {

    int[] occupancyDeltaList = a.getOccupancyDeltaList();

    assertThat(occupancyDeltaList).hasSize(3);
    assertThat(occupancyDeltaList).containsSequence(1, 1, -1);

  }

  @Test(dataProvider = "tree2")
  public void testGetDependsOnAmountRecursively2(Equipment a) throws Exception {

    assertEquals(a.getDependsOnAmountRecursively(), 2);
  }

  @Test(dataProvider = "tree2")
  public void testGetPossibleSequenceAmount2(Equipment a) throws Exception {

    assertEquals(a.getPossibleSequenceAmount().intValue(), 2);
  }

  /**
   * <pre>
   *      x
   *     / \
   *    a2  b2
   *   /     \
   *  a1      b1
   * </pre>
   *
   * @return
   */
  @DataProvider
  public Object[][] tree3() {

    Equipment x = createEquipment("x", new String[] { "a2", "b2" });
    createEquipment("a2", new String[] { "a1" });
    createEquipment("a1", null);
    createEquipment("b2", new String[] { "b1" });
    createEquipment("b1", null);

    return new Object[][] {
        new Object[] { x }
    };
  }

  @Test(dataProvider = "tree3")
  public void testGetPermutation3(Equipment x) throws Exception {

    PurchasePlanPackage permutation = x.getPurchasePlanPackage(1000);

    assertThat(permutation.getPurchasePlans()).hasSize(6);
    assertThat(permutation.getPurchasePlans()).contains(new PurchasePlan(Arrays.asList("b1", "b2", "a1", "a2", "x")));
    assertThat(permutation.getPurchasePlans()).contains(new PurchasePlan(Arrays.asList("b1", "a1", "b2", "a2", "x")));
    assertThat(permutation.getPurchasePlans()).contains(new PurchasePlan(Arrays.asList("b1", "a1", "a2", "b2", "x")));
    assertThat(permutation.getPurchasePlans()).contains(new PurchasePlan(Arrays.asList("a1", "b1", "b2", "a2", "x")));
    assertThat(permutation.getPurchasePlans()).contains(new PurchasePlan(Arrays.asList("a1", "b1", "a2", "b2", "x")));
    assertThat(permutation.getPurchasePlans()).contains(new PurchasePlan(Arrays.asList("a1", "a2", "b1", "b2", "x")));

  }

  @Test(dataProvider = "tree3")
  public void testGetOccupancyDeltaList3(Equipment x) throws Exception {

    int[] occupancyDeltaList = x.getOccupancyDeltaList();

    assertThat(occupancyDeltaList).hasSize(5);
    assertThat(occupancyDeltaList).containsSequence(1, 0, 1, 0, -1);

  }

  @Test(dataProvider = "tree3")
  public void testGetDependsOnAmountRecursively3(Equipment x) throws Exception {

    assertEquals(x.getDependsOnAmountRecursively(), 4);
  }

  @Test(dataProvider = "tree3")
  public void testGetPossibleSequenceAmount3(Equipment a) throws Exception {

    assertEquals(a.getPossibleSequenceAmount().intValue(), 6);
  }

  /**
   * <pre>
   *      a
   *     / \
   *    b   c
   *   / \   \
   *  d   e   f
   * </pre>
   *
   * @return
   */
  @DataProvider
  public Object[][] tree4() {

    Equipment a = createEquipment("a", new String[] { "b", "c" });
    createEquipment("b", new String[] { "d", "e" });
    createEquipment("d", null);
    createEquipment("e", null);
    createEquipment("c", new String[] { "f" });
    createEquipment("f", null);

    return new Object[][] {
        new Object[] { a }
    };
  }

  @Test(dataProvider = "tree4")
  public void testGetPermutation4(Equipment a) throws Exception {

    PurchasePlanPackage permutation = a.getPurchasePlanPackage(1000);

    assertThat(permutation.getPurchasePlans()).hasSize(20);

    assertThat(permutation.getPurchasePlans())
        .contains(new PurchasePlan(Arrays.asList(StringUtils.split("f,c,d,e,b,a", ','))));
    assertThat(permutation.getPurchasePlans())
        .contains(new PurchasePlan(Arrays.asList(StringUtils.split("f,d,c,e,b,a", ','))));
    assertThat(permutation.getPurchasePlans())
        .contains(new PurchasePlan(Arrays.asList(StringUtils.split("f,d,e,c,b,a", ','))));
    assertThat(permutation.getPurchasePlans())
        .contains(new PurchasePlan(Arrays.asList(StringUtils.split("f,d,e,b,c,a", ','))));
    assertThat(permutation.getPurchasePlans())
        .contains(new PurchasePlan(Arrays.asList(StringUtils.split("d,f,c,e,b,a", ','))));
    assertThat(permutation.getPurchasePlans())
        .contains(new PurchasePlan(Arrays.asList(StringUtils.split("d,f,e,c,b,a", ','))));
    assertThat(permutation.getPurchasePlans())
        .contains(new PurchasePlan(Arrays.asList(StringUtils.split("d,f,e,b,c,a", ','))));
    assertThat(permutation.getPurchasePlans())
        .contains(new PurchasePlan(Arrays.asList(StringUtils.split("d,e,f,c,b,a", ','))));
    assertThat(permutation.getPurchasePlans())
        .contains(new PurchasePlan(Arrays.asList(StringUtils.split("d,e,f,b,c,a", ','))));
    assertThat(permutation.getPurchasePlans())
        .contains(new PurchasePlan(Arrays.asList(StringUtils.split("d,e,b,f,c,a", ','))));
    assertThat(permutation.getPurchasePlans())
        .contains(new PurchasePlan(Arrays.asList(StringUtils.split("f,c,e,d,b,a", ','))));
    assertThat(permutation.getPurchasePlans())
        .contains(new PurchasePlan(Arrays.asList(StringUtils.split("f,e,c,d,b,a", ','))));
    assertThat(permutation.getPurchasePlans())
        .contains(new PurchasePlan(Arrays.asList(StringUtils.split("f,e,d,c,b,a", ','))));
    assertThat(permutation.getPurchasePlans())
        .contains(new PurchasePlan(Arrays.asList(StringUtils.split("f,e,d,b,c,a", ','))));
    assertThat(permutation.getPurchasePlans())
        .contains(new PurchasePlan(Arrays.asList(StringUtils.split("e,f,c,d,b,a", ','))));
    assertThat(permutation.getPurchasePlans())
        .contains(new PurchasePlan(Arrays.asList(StringUtils.split("e,f,d,c,b,a", ','))));
    assertThat(permutation.getPurchasePlans())
        .contains(new PurchasePlan(Arrays.asList(StringUtils.split("e,f,d,b,c,a", ','))));
    assertThat(permutation.getPurchasePlans())
        .contains(new PurchasePlan(Arrays.asList(StringUtils.split("e,d,f,c,b,a", ','))));
    assertThat(permutation.getPurchasePlans())
        .contains(new PurchasePlan(Arrays.asList(StringUtils.split("e,d,f,b,c,a", ','))));
    assertThat(permutation.getPurchasePlans())
        .contains(new PurchasePlan(Arrays.asList(StringUtils.split("e,d,b,f,c,a", ','))));

  }

  @Test(dataProvider = "tree4")
  public void testGetOccupancyDeltaList4(Equipment a) throws Exception {

    int[] occupancyDeltaList = a.getOccupancyDeltaList();

    assertThat(occupancyDeltaList).hasSize(6);
    assertThat(occupancyDeltaList).containsSequence(1, 1, -1, 1, 0, -1);

  }

  @Test(dataProvider = "tree4")
  public void testGetDependsOnAmountRecursively4(Equipment a) throws Exception {

    assertEquals(a.getDependsOnAmountRecursively(), 5);
  }

  @Test(dataProvider = "tree4")
  public void testGetPossibleSequenceAmount4(Equipment a) throws Exception {

    assertEquals(a.getPossibleSequenceAmount().intValue(), 20);
  }

  /**
   * <pre>
   *      a
   *     / \
   *    b   c
   *   /|\   \
   *  d e f  g
   * </pre>
   *
   * @return
   */
  @DataProvider
  public Object[][] tree5() {

    Equipment a = createEquipment("a", new String[] { "b", "c" });
    createEquipment("b", new String[] { "d", "e", "f" });
    createEquipment("d", null);
    createEquipment("e", null);
    createEquipment("f", null);
    createEquipment("c", new String[] { "g" });
    createEquipment("g", null);

    return new Object[][] {
        new Object[] { a }
    };
  }

  @Test(dataProvider = "tree5")
  public void testGetPossibleSequenceAmount5(Equipment a) throws Exception {

    assertEquals(a.getPossibleSequenceAmount().intValue(), 90);
  }


  private Equipment createEquipment(String id, String[] dependsOn) {
    Equipment equipment = equipmentRepository.newModel();
    equipment.setId(id);
    if (ArrayUtils.isNotEmpty(dependsOn)) {
      equipment.setDependsOn(Arrays.asList(dependsOn));
    }

    equipmentRepository.register(equipment);
    return equipment;
  }
}
