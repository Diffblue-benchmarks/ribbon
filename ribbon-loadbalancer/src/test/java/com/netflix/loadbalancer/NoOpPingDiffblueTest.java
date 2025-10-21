package com.netflix.loadbalancer;

import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class NoOpPingDiffblueTest {
  /**
   * Test {@link NoOpPing#isAlive(Server)}.
   *
   * <ul>
   *   <li>When {@link Server#Server(String)} with id is {@code 42}.
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link NoOpPing#isAlive(Server)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean NoOpPing.isAlive(Server)"})
  public void testIsAlive_whenServerWithIdIs42_thenReturnTrue() {
    // Arrange
    NoOpPing noOpPing = new NoOpPing();

    // Act
    boolean actualIsAliveResult = noOpPing.isAlive(new Server("42"));

    // Assert
    assertTrue(actualIsAliveResult);
  }
}
