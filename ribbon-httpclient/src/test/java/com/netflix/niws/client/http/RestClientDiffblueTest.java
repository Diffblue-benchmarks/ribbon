package com.netflix.niws.client.http;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.netflix.client.DefaultLoadBalancerRetryHandler;
import com.netflix.servo.monitor.BasicTimer;
import com.sun.jersey.api.client.Client;
import java.net.URL;
import java.nio.file.Paths;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class RestClientDiffblueTest {
  /**
   * Test {@link RestClient#RestClient()}.
   *
   * <p>Method under test: {@link RestClient#RestClient()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void RestClient.<init>()"})
  public void testNewRestClient() {
    // Arrange and Act
    RestClient actualRestClient = new RestClient();

    // Assert
    assertTrue(actualRestClient.getRetryHandler() instanceof DefaultLoadBalancerRetryHandler);
    assertTrue(actualRestClient.getExecuteTracer() instanceof BasicTimer);
    assertEquals("default", actualRestClient.getClientName());
    assertNull(actualRestClient.getLoadBalancer());
    assertNull(actualRestClient.getJerseyClient());
    assertEquals(0, actualRestClient.getMaxAutoRetries());
    assertEquals(1, actualRestClient.getMaxAutoRetriesNextServer());
    assertFalse(actualRestClient.isOkToRetryOnAllOperations());
    assertFalse(actualRestClient.bFollowRedirects);
  }

  /**
   * Test {@link RestClient#getResource(String)}.
   *
   * <p>Method under test: {@link RestClient#getResource(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"URL RestClient.getResource(String)"})
  public void testGetResource() {
    // Arrange and Act
    URL actualResource = RestClient.getResource("https://example.org/example");

    // Assert
    String expectedToStringResult =
        String.join(
            "",
            "file:",
            Paths.get(System.getProperty("user.dir"), "https").toString(),
            ":/example.org/example");
    assertEquals(expectedToStringResult, actualResource.toString());
  }

  /**
   * Test {@link RestClient#getResource(String)}.
   *
   * <ul>
   *   <li>When {@code %s.nfhttpclient.connIdleEvictTimeMilliSeconds}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link RestClient#getResource(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"URL RestClient.getResource(String)"})
  public void testGetResource_whenSNfhttpclientConnIdleEvictTimeMilliSeconds_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(RestClient.getResource("%s.nfhttpclient.connIdleEvictTimeMilliSeconds"));
  }

  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link RestClient#setJerseyClient(Client)}
   *   <li>{@link RestClient#getJerseyClient()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Client RestClient.getJerseyClient()",
    "void RestClient.setJerseyClient(Client)"
  })
  public void testGettersAndSetters() {
    // Arrange
    RestClient restClient = new RestClient();
    Client c = new Client();

    // Act
    restClient.setJerseyClient(c);

    // Assert
    assertSame(c, restClient.getJerseyClient());
  }
}
