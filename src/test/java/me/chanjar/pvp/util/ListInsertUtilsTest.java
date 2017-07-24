package me.chanjar.pvp.util;

import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.testng.Assert.assertEquals;

/**
 * 两个list插入的工具，获得
 */
public class ListInsertUtilsTest {

  @Test
  public void testCalculateInsertResults() throws Exception {

    List<List<String>> listInsertionResults = ListInsertUtils
        .mergeInsert(Arrays.asList("a1", "a2"), Arrays.asList("b1", "b2"), -1);

    assertEquals(listInsertionResults.size(), 6);
    assertThat(listInsertionResults).contains(Arrays.asList("b1", "b2", "a1", "a2"));
    assertThat(listInsertionResults).contains(Arrays.asList("b1", "a1", "b2", "a2"));
    assertThat(listInsertionResults).contains(Arrays.asList("b1", "a1", "a2", "b2"));
    assertThat(listInsertionResults).contains(Arrays.asList("a1", "b1", "b2", "a2"));
    assertThat(listInsertionResults).contains(Arrays.asList("a1", "b1", "a2", "b2"));
    assertThat(listInsertionResults).contains(Arrays.asList("a1", "a2", "b1", "b2"));

  }

  @Test
  public void testCalculateInsertResults2() throws Exception {

    List<List<String>> listInsertionResults = ListInsertUtils
        .mergeInsert(Arrays.asList("a1", "a2"), Arrays.asList("a1", "a2"), -1);

    assertEquals(listInsertionResults.size(), 2);
    assertThat(listInsertionResults).contains(Arrays.asList("a1", "a2", "a1", "a2"));
    assertThat(listInsertionResults).contains(Arrays.asList("a1", "a1", "a2", "a2"));

  }

}
