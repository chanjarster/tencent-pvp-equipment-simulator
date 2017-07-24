package me.chanjar.pvp.util;

import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.testng.Assert.assertEquals;

public class PermutationHelperTest {

  /**
   * [1,2,3] -> [1,2,3], [1,3,2], [2,1,3], [2,3,1], [3,1,2], and [3,2,1].
   *
   * @throws Exception
   */
  @Test
  public void testPermute1() throws Exception {

    List<List<Integer>> permute = PermutationHelper.permuteUnique(Arrays.asList(1, 2, 3));

    permute.contains(Arrays.asList(1, 2, 3));
    permute.contains(Arrays.asList(1, 3, 2));
    permute.contains(Arrays.asList(2, 1, 3));
    permute.contains(Arrays.asList(2, 3, 1));
    permute.contains(Arrays.asList(3, 1, 2));
    permute.contains(Arrays.asList(3, 2, 1));

    assertEquals(permute.size(), 6);
  }

  /**
   * [1,1,2] -> [1,1,2], [1,2,1], [2,1,1].
   *
   * @throws Exception
   */
  @Test
  public void testPermute2() throws Exception {

    List<List<Integer>> permute = PermutationHelper.permuteUnique(Arrays.asList(1, 1, 2));

    permute.contains(Arrays.asList(1, 1, 2));
    permute.contains(Arrays.asList(1, 2, 1));
    permute.contains(Arrays.asList(2, 1, 1));

    assertEquals(permute.size(), 3);

  }

  @Test
  public void testPermute3() throws Exception {

    List<List<Integer>> permute = PermutationHelper.permuteUnique(Arrays.asList(1));

    permute.contains(Arrays.asList(1));

    assertEquals(permute.size(), 1);
  }

  @Test
  public void testPermute4() throws Exception {

    List<List<Integer>> permute = PermutationHelper.permuteUnique(Collections.emptyList());

    permute.contains(Collections.emptyList());
    assertEquals(permute.size(), 1);

  }
}
