package me.chanjar.pvp.equipment.model;

import me.chanjar.pvp.equipment.repo.EquipmentRepository;
import me.chanjar.pvp.equipment.repo.EquipmentRepositoryImpl;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.testng.annotations.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class EquipmentModelTest {
  private EquipmentRepository equipmentRepository = new EquipmentRepositoryImpl();

  /**
   * single node: a
   *
   * @throws Exception
   */
  @Test
  public void testGetPermutation1() throws Exception {

    Equipment a = createEquipment("a", null);

    Permutation permutation = a.calculatePermutation(1);

    assertThat(permutation.getSequenceList()).hasSize(1);
    assertThat(permutation.getSequenceList()).contains(new Sequence(Arrays.asList("a")));

  }

  @Test
  public void testGetOccupancyDeltaList1() throws Exception {

    Equipment a = createEquipment("a", null);

    int[] occupancyDeltaList = a.getOccupancyDeltaList();

    assertThat(occupancyDeltaList).hasSize(1);
    assertThat(occupancyDeltaList).containsSequence(1);

  }


  /**
   * nodes:
   * <pre>
   *      a
   *     / \
   *    b   c
   * </pre>
   * @throws Exception
   */
  @Test
  public void testGetPermutation2() throws Exception {

    Equipment a = createEquipment("a", new String[] { "b", "c" });
    createEquipment("b", null);
    createEquipment("c", null);

    Permutation permutation = a.calculatePermutation(2);

    assertThat(permutation.getSequenceList()).hasSize(2);
    assertThat(permutation.getSequenceList()).contains(new Sequence(Arrays.asList("b", "c", "a")));
    assertThat(permutation.getSequenceList()).contains(new Sequence(Arrays.asList("c", "b", "a")));
  }

  @Test
  public void testGetOccupancyDeltaList2() throws Exception {

    Equipment a = createEquipment("a", new String[] { "b", "c" });
    createEquipment("b", null);
    createEquipment("c", null);

    int[] occupancyDeltaList = a.getOccupancyDeltaList();

    assertThat(occupancyDeltaList).hasSize(3);
    assertThat(occupancyDeltaList).containsSequence(1, 1, -1);

  }


  /**
   * nodes:
   * <pre>
   *      x
   *     / \
   *    a2  b2
   *   /     \
   *  a1      b1
   * </pre>
   * @throws Exception
   */
  @Test
  public void testGetPermutation3() throws Exception {

    Equipment x = createEquipment("x", new String[] { "a2", "b2" });
    createEquipment("a2", new String[] { "a1" });
    createEquipment("a1", null);
    createEquipment("b2", new String[] { "b1" });
    createEquipment("b1", null);

    Permutation permutation = x.calculatePermutation(3);

    assertThat(permutation.getSequenceList()).hasSize(6);
    assertThat(permutation.getSequenceList()).contains(new Sequence(Arrays.asList("b1", "b2", "a1", "a2", "x")));
    assertThat(permutation.getSequenceList()).contains(new Sequence(Arrays.asList("b1", "a1", "b2", "a2", "x")));
    assertThat(permutation.getSequenceList()).contains(new Sequence(Arrays.asList("b1", "a1", "a2", "b2", "x")));
    assertThat(permutation.getSequenceList()).contains(new Sequence(Arrays.asList("a1", "b1", "b2", "a2", "x")));
    assertThat(permutation.getSequenceList()).contains(new Sequence(Arrays.asList("a1", "b1", "a2", "b2", "x")));
    assertThat(permutation.getSequenceList()).contains(new Sequence(Arrays.asList("a1", "a2", "b1", "b2", "x")));

  }

  @Test
  public void testGetOccupancyDeltaList3() throws Exception {

    Equipment x = createEquipment("x", new String[] { "a2", "b2" });
    createEquipment("a2", new String[] { "a1" });
    createEquipment("a1", null);
    createEquipment("b2", new String[] { "b1" });
    createEquipment("b1", null);

    int[] occupancyDeltaList = x.getOccupancyDeltaList();

    assertThat(occupancyDeltaList).hasSize(5);
    assertThat(occupancyDeltaList).containsSequence(1, 0, 1, 0, -1);

  }

  /**
   * nodes:
   * <pre>
   *      a
   *     / \
   *    b   c
   *   / \   \
   *  d   e   f
   * </pre>
   * @throws Exception
   */
  @Test
  public void testGetPermutation4() throws Exception {

    Equipment x = createEquipment("a", new String[] { "b", "c" });
    createEquipment("b", new String[] { "d", "e" });
    createEquipment("d", null);
    createEquipment("e", null);
    createEquipment("c", new String[] { "f" });
    createEquipment("f", null);

    Permutation permutation = x.calculatePermutation(4);

    assertThat(permutation.getSequenceList()).hasSize(20);

    assertThat(permutation.getSequenceList()).contains(new Sequence(Arrays.asList(StringUtils.split("f,c,d,e,b,a", ','))));
    assertThat(permutation.getSequenceList()).contains(new Sequence(Arrays.asList(StringUtils.split("f,d,c,e,b,a", ','))));
    assertThat(permutation.getSequenceList()).contains(new Sequence(Arrays.asList(StringUtils.split("f,d,e,c,b,a", ','))));
    assertThat(permutation.getSequenceList()).contains(new Sequence(Arrays.asList(StringUtils.split("f,d,e,b,c,a", ','))));
    assertThat(permutation.getSequenceList()).contains(new Sequence(Arrays.asList(StringUtils.split("d,f,c,e,b,a", ','))));
    assertThat(permutation.getSequenceList()).contains(new Sequence(Arrays.asList(StringUtils.split("d,f,e,c,b,a", ','))));
    assertThat(permutation.getSequenceList()).contains(new Sequence(Arrays.asList(StringUtils.split("d,f,e,b,c,a", ','))));
    assertThat(permutation.getSequenceList()).contains(new Sequence(Arrays.asList(StringUtils.split("d,e,f,c,b,a", ','))));
    assertThat(permutation.getSequenceList()).contains(new Sequence(Arrays.asList(StringUtils.split("d,e,f,b,c,a", ','))));
    assertThat(permutation.getSequenceList()).contains(new Sequence(Arrays.asList(StringUtils.split("d,e,b,f,c,a", ','))));
    assertThat(permutation.getSequenceList()).contains(new Sequence(Arrays.asList(StringUtils.split("f,c,e,d,b,a", ','))));
    assertThat(permutation.getSequenceList()).contains(new Sequence(Arrays.asList(StringUtils.split("f,e,c,d,b,a", ','))));
    assertThat(permutation.getSequenceList()).contains(new Sequence(Arrays.asList(StringUtils.split("f,e,d,c,b,a", ','))));
    assertThat(permutation.getSequenceList()).contains(new Sequence(Arrays.asList(StringUtils.split("f,e,d,b,c,a", ','))));
    assertThat(permutation.getSequenceList()).contains(new Sequence(Arrays.asList(StringUtils.split("e,f,c,d,b,a", ','))));
    assertThat(permutation.getSequenceList()).contains(new Sequence(Arrays.asList(StringUtils.split("e,f,d,c,b,a", ','))));
    assertThat(permutation.getSequenceList()).contains(new Sequence(Arrays.asList(StringUtils.split("e,f,d,b,c,a", ','))));
    assertThat(permutation.getSequenceList()).contains(new Sequence(Arrays.asList(StringUtils.split("e,d,f,c,b,a", ','))));
    assertThat(permutation.getSequenceList()).contains(new Sequence(Arrays.asList(StringUtils.split("e,d,f,b,c,a", ','))));
    assertThat(permutation.getSequenceList()).contains(new Sequence(Arrays.asList(StringUtils.split("e,d,b,f,c,a", ','))));

  }

  @Test
  public void testGetOccupancyDeltaList4() throws Exception {

    Equipment x = createEquipment("a", new String[] { "b", "c" });
    createEquipment("b", new String[] { "d", "e" });
    createEquipment("d", null);
    createEquipment("e", null);
    createEquipment("c", new String[] { "f" });
    createEquipment("f", null);

    int[] occupancyDeltaList = x.getOccupancyDeltaList();

    assertThat(occupancyDeltaList).hasSize(6);
    assertThat(occupancyDeltaList).containsSequence(1, 1, -1, 1, 0, -1);

  }

  private Equipment createEquipment(String id, String[] dependsOn) {
    Equipment equipment = equipmentRepository.newModel();
    equipment.setId(id);
    if (ArrayUtils.isNotEmpty(dependsOn)) {
      equipment.setDependsOn(Arrays.asList(dependsOn));
    }

    equipmentRepository.registerEquipment(equipment);
    return equipment;
  }

}
