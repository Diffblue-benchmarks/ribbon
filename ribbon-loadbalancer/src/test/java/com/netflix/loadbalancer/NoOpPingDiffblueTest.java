package com.netflix.loadbalancer;

import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class NoOpPingDiffblueTest {
  /**
   * Test {@link NoOpPing#isAlive(Server)}.
   * <p>
   * Method under test: {@link NoOpPing#isAlive(Server)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean NoOpPing.isAlive(Server)"})
  public void testIsAlive() {
    // Arrange
    NoOpPing noOpPing = new NoOpPing();

    // Act and Assert
    assertTrue(noOpPing.isAlive(new Server("42")));
  }
}
