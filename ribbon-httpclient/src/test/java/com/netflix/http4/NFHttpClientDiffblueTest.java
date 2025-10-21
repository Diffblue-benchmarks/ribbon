package com.netflix.http4;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.netflix.client.config.FallbackProperty;
import com.netflix.client.config.Property;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class NFHttpClientDiffblueTest {
  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link NFHttpClient#setConnIdleEvictTimeMilliSeconds(Property)}
   *   <li>{@link NFHttpClient#getConnIdleEvictTimeMilliSeconds()}
   *   <li>{@link NFHttpClient#getConnPoolCleaner()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Property NFHttpClient.getConnIdleEvictTimeMilliSeconds()",
    "ConnectionPoolCleaner NFHttpClient.getConnPoolCleaner()",
    "void NFHttpClient.setConnIdleEvictTimeMilliSeconds(Property)"
  })
  public void testGettersAndSetters() {
    // Arrange
    NFHttpClient nfHttpClient = new NFHttpClient();
    FallbackProperty<Integer> connIdleEvictTimeMilliSeconds = new FallbackProperty<>(null, null);

    // Act
    nfHttpClient.setConnIdleEvictTimeMilliSeconds(connIdleEvictTimeMilliSeconds);
    Property<Integer> actualConnIdleEvictTimeMilliSeconds =
        nfHttpClient.getConnIdleEvictTimeMilliSeconds();
    ConnectionPoolCleaner actualConnPoolCleaner = nfHttpClient.getConnPoolCleaner();

    // Assert
    assertTrue(actualConnIdleEvictTimeMilliSeconds instanceof FallbackProperty);
    assertTrue(actualConnPoolCleaner.scheduler instanceof ScheduledThreadPoolExecutor);
    assertTrue(actualConnPoolCleaner.connMgr instanceof ThreadSafeClientConnManager);
    assertEquals(10L, actualConnPoolCleaner.getConnectionCleanerTimerDelay());
    assertEquals(30000L, actualConnPoolCleaner.getConnectionCleanerRepeatInterval());
    assertFalse(actualConnPoolCleaner.isEnableConnectionPoolCleanerTask());
    assertSame(connIdleEvictTimeMilliSeconds, actualConnIdleEvictTimeMilliSeconds);
  }

  /**
   * Test {@link NFHttpClient#getMaxTotalConnnections()}.
   *
   * <p>Method under test: {@link NFHttpClient#getMaxTotalConnnections()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int NFHttpClient.getMaxTotalConnnections()"})
  public void testGetMaxTotalConnnections() {
    // Arrange, Act and Assert
    assertEquals(20, NFHttpClientFactory.getDefaultClient().getMaxTotalConnnections());
  }

  /**
   * Test {@link NFHttpClient#getNumRetries()}.
   *
   * <p>Method under test: {@link NFHttpClient#getNumRetries()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int NFHttpClient.getNumRetries()"})
  public void testGetNumRetries() {
    // Arrange, Act and Assert
    assertEquals(3, NFHttpClientFactory.getDefaultClient().getNumRetries());
  }

  /**
   * Test {@link NFHttpClient#getSleepTimeFactorMs()}.
   *
   * <p>Method under test: {@link NFHttpClient#getSleepTimeFactorMs()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int NFHttpClient.getSleepTimeFactorMs()"})
  public void testGetSleepTimeFactorMs() {
    // Arrange, Act and Assert
    assertEquals(10, NFHttpClientFactory.getDefaultClient().getSleepTimeFactorMs());
  }
}
