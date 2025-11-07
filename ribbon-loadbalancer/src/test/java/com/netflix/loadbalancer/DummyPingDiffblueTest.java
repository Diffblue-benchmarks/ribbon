package com.netflix.loadbalancer;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class DummyPingDiffblueTest {
  /**
   * Test new {@link DummyPing} (default constructor).
   * <p>
   * Method under test: default or parameterless constructor of {@link DummyPing}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DummyPing.<init>()"})
  public void testNewDummyPing() {
    // Arrange, Act and Assert
    assertNull((new DummyPing()).getLoadBalancer());
  }

  /**
   * Test {@link DummyPing#isAlive(Server)}.
   * <p>
   * Method under test: {@link DummyPing#isAlive(Server)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean DummyPing.isAlive(Server)"})
  public void testIsAlive() {
    // Arrange
    DummyPing dummyPing = new DummyPing();

    // Act and Assert
    assertTrue(dummyPing.isAlive(new Server("42")));
  }
}
