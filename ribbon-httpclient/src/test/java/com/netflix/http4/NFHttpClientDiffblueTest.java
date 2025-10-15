package com.netflix.http4;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.netflix.client.config.FallbackProperty;
import com.netflix.client.config.Property;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.conn.DefaultClientConnection;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.protocol.HttpContext;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.rules.ExpectedException;

public class NFHttpClientDiffblueTest {
  @Rule public ExpectedException thrown = ExpectedException.none();

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
   * Test {@link NFHttpClient#getConnectionsInPool()}.
   *
   * <p>Method under test: {@link NFHttpClient#getConnectionsInPool()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int NFHttpClient.getConnectionsInPool()"})
  public void testGetConnectionsInPool() {
    // Arrange, Act and Assert
    assertEquals(0, NFHttpClientFactory.getDefaultClient().getConnectionsInPool());
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
   * Test {@link NFHttpClient#getMaxConnectionsPerHost()}.
   *
   * <ul>
   *   <li>Given DefaultClient.
   * </ul>
   *
   * <p>Method under test: {@link NFHttpClient#getMaxConnectionsPerHost()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int NFHttpClient.getMaxConnectionsPerHost()"})
  public void testGetMaxConnectionsPerHost_givenDefaultClient() {
    // Arrange, Act and Assert
    assertEquals(2, NFHttpClientFactory.getDefaultClient().getMaxConnectionsPerHost());
  }

  /**
   * Test {@link NFHttpClient#getMaxConnectionsPerHost()}.
   *
   * <ul>
   *   <li>Given NFHttpClient {@code https://example.org/example} is {@code 8080}.
   * </ul>
   *
   * <p>Method under test: {@link NFHttpClient#getMaxConnectionsPerHost()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int NFHttpClient.getMaxConnectionsPerHost()"})
  public void testGetMaxConnectionsPerHost_givenNFHttpClientHttpsExampleOrgExampleIs8080() {
    // Arrange, Act and Assert
    assertEquals(
        2,
        NFHttpClientFactory.getNFHttpClient("https://example.org/example", 8080)
            .getMaxConnectionsPerHost());
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

  /**
   * Test {@link NFHttpClient#execute(HttpUriRequest, ResponseHandler, HttpContext)} with {@code
   * request}, {@code responseHandler}, {@code context}.
   *
   * <ul>
   *   <li>Then throw {@link ClientProtocolException}.
   * </ul>
   *
   * <p>Method under test: {@link NFHttpClient#execute(HttpUriRequest, ResponseHandler,
   * HttpContext)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object NFHttpClient.execute(HttpUriRequest, ResponseHandler, HttpContext)"})
  public void testExecuteWithRequestResponseHandlerContext_thenThrowClientProtocolException()
      throws IOException {
    // Arrange
    NFHttpClient defaultClient = NFHttpClientFactory.getDefaultClient();

    HttpDelete request = new HttpDelete("https://example.org/example");
    request.setURI(Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri());
    ResponseHandler<Object> responseHandler = mock(ResponseHandler.class);

    // Act and Assert
    thrown.expect(ClientProtocolException.class);
    defaultClient.execute(request, responseHandler, new DefaultClientConnection());
  }

  /**
   * Test {@link NFHttpClient#execute(HttpUriRequest, ResponseHandler)} with {@code request}, {@code
   * responseHandler}.
   *
   * <ul>
   *   <li>Then throw {@link ClientProtocolException}.
   * </ul>
   *
   * <p>Method under test: {@link NFHttpClient#execute(HttpUriRequest, ResponseHandler)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object NFHttpClient.execute(HttpUriRequest, ResponseHandler)"})
  public void testExecuteWithRequestResponseHandler_thenThrowClientProtocolException()
      throws IOException {
    // Arrange
    NFHttpClient defaultClient = NFHttpClientFactory.getDefaultClient();

    HttpDelete request = new HttpDelete("https://example.org/example");
    request.setURI(Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri());

    // Act and Assert
    thrown.expect(ClientProtocolException.class);
    defaultClient.execute(request, mock(ResponseHandler.class));
  }
}
