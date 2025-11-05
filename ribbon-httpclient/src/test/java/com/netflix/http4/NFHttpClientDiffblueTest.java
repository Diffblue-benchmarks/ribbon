package com.netflix.http4;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import com.netflix.client.config.FallbackProperty;
import com.netflix.client.config.Property;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.junit.Test;

public class NFHttpClientDiffblueTest {
  /**
   * Method under test: {@link NFHttpClient#getMaxTotalConnnections()}
   */
  @Test
  public void testGetMaxTotalConnnections() {
    // Arrange, Act and Assert
    assertEquals(20, NFHttpClientFactory.getDefaultClient().getMaxTotalConnnections());
  }

  /**
   * Method under test: {@link NFHttpClient#getMaxTotalConnnections()}
   */
  @Test
  public void testGetMaxTotalConnnections2() {
    // Arrange
    NFHttpClient defaultClient = NFHttpClientFactory.getDefaultClient();
    defaultClient.addResponseInterceptor(mock(HttpResponseInterceptor.class));

    // Act and Assert
    assertEquals(20, defaultClient.getMaxTotalConnnections());
  }

  /**
   * Method under test: {@link NFHttpClient#getMaxConnectionsPerHost()}
   */
  @Test
  public void testGetMaxConnectionsPerHost() {
    // Arrange, Act and Assert
    assertEquals(2, NFHttpClientFactory.getDefaultClient().getMaxConnectionsPerHost());
    assertEquals(2,
        NFHttpClientFactory.getNFHttpClient("https://example.org/example", 8080).getMaxConnectionsPerHost());
  }

  /**
   * Method under test: {@link NFHttpClient#getMaxConnectionsPerHost()}
   */
  @Test
  public void testGetMaxConnectionsPerHost2() {
    // Arrange
    NFHttpClient defaultClient = NFHttpClientFactory.getDefaultClient();
    defaultClient.addResponseInterceptor(mock(HttpResponseInterceptor.class));

    // Act and Assert
    assertEquals(2, defaultClient.getMaxConnectionsPerHost());
  }

  /**
   * Method under test: {@link NFHttpClient#getNumRetries()}
   */
  @Test
  public void testGetNumRetries() {
    // Arrange, Act and Assert
    assertEquals(3, NFHttpClientFactory.getDefaultClient().getNumRetries());
  }

  /**
   * Method under test: {@link NFHttpClient#getNumRetries()}
   */
  @Test
  public void testGetNumRetries2() {
    // Arrange
    NFHttpClient defaultClient = NFHttpClientFactory.getDefaultClient();
    defaultClient.addResponseInterceptor(mock(HttpResponseInterceptor.class));

    // Act and Assert
    assertEquals(3, defaultClient.getNumRetries());
  }

  /**
   * Method under test: {@link NFHttpClient#getSleepTimeFactorMs()}
   */
  @Test
  public void testGetSleepTimeFactorMs() {
    // Arrange, Act and Assert
    assertEquals(10, NFHttpClientFactory.getDefaultClient().getSleepTimeFactorMs());
  }

  /**
   * Method under test: {@link NFHttpClient#getSleepTimeFactorMs()}
   */
  @Test
  public void testGetSleepTimeFactorMs2() {
    // Arrange
    NFHttpClient defaultClient = NFHttpClientFactory.getDefaultClient();
    defaultClient.addResponseInterceptor(mock(HttpResponseInterceptor.class));

    // Act and Assert
    assertEquals(10, defaultClient.getSleepTimeFactorMs());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link NFHttpClient#setConnIdleEvictTimeMilliSeconds(Property)}
   *   <li>{@link NFHttpClient#getConnIdleEvictTimeMilliSeconds()}
   *   <li>{@link NFHttpClient#getConnPoolCleaner()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange
    NFHttpClient nfHttpClient = new NFHttpClient();
    FallbackProperty<Integer> connIdleEvictTimeMilliSeconds = new FallbackProperty<>(null, null);

    // Act
    nfHttpClient.setConnIdleEvictTimeMilliSeconds(connIdleEvictTimeMilliSeconds);
    Property<Integer> actualConnIdleEvictTimeMilliSeconds = nfHttpClient.getConnIdleEvictTimeMilliSeconds();
    ConnectionPoolCleaner actualConnPoolCleaner = nfHttpClient.getConnPoolCleaner();

    // Assert that nothing has changed
    assertTrue(actualConnIdleEvictTimeMilliSeconds instanceof FallbackProperty);
    assertTrue(actualConnPoolCleaner.scheduler instanceof ScheduledThreadPoolExecutor);
    assertTrue(actualConnPoolCleaner.connMgr instanceof ThreadSafeClientConnManager);
    assertEquals(10L, actualConnPoolCleaner.getConnectionCleanerTimerDelay());
    assertEquals(30000L, actualConnPoolCleaner.getConnectionCleanerRepeatInterval());
    assertFalse(actualConnPoolCleaner.isEnableConnectionPoolCleanerTask());
    assertSame(connIdleEvictTimeMilliSeconds, actualConnIdleEvictTimeMilliSeconds);
  }
}
