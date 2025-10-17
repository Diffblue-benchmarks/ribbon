package com.netflix.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.netflix.client.PrimeConnections.PrimeConnectionEndStats;
import com.netflix.servo.monitor.BasicCounter;
import com.netflix.servo.monitor.BasicTimer;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class PrimeConnectionsDiffblueTest {
  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link PrimeConnections#PrimeConnections(String, int, long, String, float)}
   *   <li>{@link PrimeConnections#getEndStats()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void PrimeConnections.<init>(String, int, long, String, float)",
    "PrimeConnectionEndStats PrimeConnections.getEndStats()"
  })
  public void testGettersAndSetters() {
    // Arrange and Act
    PrimeConnections actualPrimeConnections =
        new PrimeConnections("Name", 3, 1L, "Prime Connections URI", 10.0f);

    // Assert
    assertNull(actualPrimeConnections.getEndStats());
  }

  /**
   * Test {@link PrimeConnections#PrimeConnections(String, int, long, String)}.
   *
   * <p>Method under test: {@link PrimeConnections#PrimeConnections(String, int, long, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void PrimeConnections.<init>(String, int, long, String)"})
  public void testNewPrimeConnections() {
    // Arrange and Act
    PrimeConnections actualPrimeConnections =
        new PrimeConnections("Name", 3, 1L, "Prime Connections URI");

    // Assert
    assertTrue(actualPrimeConnections.successCounter instanceof BasicCounter);
    assertTrue(actualPrimeConnections.totalCounter instanceof BasicCounter);
    assertTrue(actualPrimeConnections.initialPrimeTimer instanceof BasicTimer);
    assertEquals("Prime Connections URI", actualPrimeConnections.primeConnectionsURIPath);
    assertNull(actualPrimeConnections.getEndStats());
    assertEquals(0L, actualPrimeConnections.totalTimeTaken);
    assertEquals(1L, actualPrimeConnections.maxTotalTimeToPrimeConnections);
    assertEquals(3, actualPrimeConnections.maxRetries);
  }

  /**
   * Test PrimeConnectionEndStats getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link PrimeConnectionEndStats#PrimeConnectionEndStats(int, int, int, long)}
   *   <li>{@link PrimeConnectionEndStats#toString()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void PrimeConnectionEndStats.<init>(int, int, int, long)",
    "String PrimeConnectionEndStats.toString()"
  })
  public void testPrimeConnectionEndStatsGettersAndSetters() {
    // Arrange and Act
    PrimeConnectionEndStats actualPrimeConnectionEndStats =
        new PrimeConnectionEndStats(1, 1, 1, 1L);

    // Assert
    assertEquals(
        "PrimeConnectionEndStats [total=1, success=1, failure=1, totalTime=1]",
        actualPrimeConnectionEndStats.toString());
  }
}
