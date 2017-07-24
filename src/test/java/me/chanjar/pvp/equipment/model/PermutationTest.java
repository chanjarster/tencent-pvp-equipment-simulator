package me.chanjar.pvp.equipment.model;

import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

public class PermutationTest {

  @Test
  public void testMerge() throws Exception {

    Permutation p1 = new Permutation(Collections.singletonList(new Sequence(Arrays.asList("a1"))));
    Permutation p2 = new Permutation(Collections.singletonList(new Sequence(Arrays.asList("b1"))));

    Permutation p3 = p1.merge(p2);
    assertThat(p3.getSequenceList()).hasSize(2);
    assertThat(p3.getSequenceList()).contains(new Sequence(Arrays.asList("a1", "b1")));
    assertThat(p3.getSequenceList()).contains(new Sequence(Arrays.asList("b1", "a1")));

  }

  @Test
  public void testMerge2() throws Exception {

    Permutation p1 = new Permutation(Collections.singletonList(new Sequence(Arrays.asList("a1", "a2"))));
    Permutation p2 = new Permutation(Collections.singletonList(new Sequence(Arrays.asList("b1", "b2"))));

    Permutation p3 = p1.merge(p2);
    assertThat(p3.getSequenceList()).hasSize(6);
    assertThat(p3.getSequenceList()).contains(new Sequence(Arrays.asList("b1", "b2", "a1", "a2")));
    assertThat(p3.getSequenceList()).contains(new Sequence(Arrays.asList("b1", "a1", "b2", "a2")));
    assertThat(p3.getSequenceList()).contains(new Sequence(Arrays.asList("b1", "a1", "a2", "b2")));
    assertThat(p3.getSequenceList()).contains(new Sequence(Arrays.asList("a1", "b1", "b2", "a2")));
    assertThat(p3.getSequenceList()).contains(new Sequence(Arrays.asList("a1", "b1", "a2", "b2")));
    assertThat(p3.getSequenceList()).contains(new Sequence(Arrays.asList("a1", "a2", "b1", "b2")));
  }

}
