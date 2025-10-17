package com.netflix.client;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.net.URI;
import java.nio.file.Paths;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class ClientRequestDiffblueTest {
  /**
   * Test {@link ClientRequest#ClientRequest(ClientRequest)}.
   *
   * <ul>
   *   <li>When {@link ClientRequest#ClientRequest()}.
   *   <li>Then return OverrideConfig is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ClientRequest#ClientRequest(ClientRequest)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ClientRequest.<init>(ClientRequest)"})
  public void testNewClientRequest_whenClientRequest_thenReturnOverrideConfigIsNull() {
    // Arrange and Act
    ClientRequest actualClientRequest = new ClientRequest(new ClientRequest());

    // Assert
    assertNull(actualClientRequest.getOverrideConfig());
    assertNull(actualClientRequest.isRetriable);
    assertNull(actualClientRequest.getLoadBalancerKey());
    assertNull(actualClientRequest.getUri());
    assertFalse(actualClientRequest.isRetriable());
  }

  /**
   * Test {@link ClientRequest#isRetriable()}.
   *
   * <ul>
   *   <li>Given {@link ClientRequest#ClientRequest()} Retriable is {@code true}.
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link ClientRequest#isRetriable()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean ClientRequest.isRetriable()"})
  public void testIsRetriable_givenClientRequestRetriableIsTrue_thenReturnTrue() {
    // Arrange
    ClientRequest clientRequest = new ClientRequest();
    clientRequest.setRetriable(true);

    // Act and Assert
    assertTrue(clientRequest.isRetriable());
  }

  /**
   * Test {@link ClientRequest#isRetriable()}.
   *
   * <ul>
   *   <li>Given {@link ClientRequest#ClientRequest()}.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link ClientRequest#isRetriable()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean ClientRequest.isRetriable()"})
  public void testIsRetriable_givenClientRequest_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse(new ClientRequest().isRetriable());
  }

  /**
   * Test {@link ClientRequest#setRetriable(boolean)}.
   *
   * <p>Method under test: {@link ClientRequest#setRetriable(boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ClientRequest ClientRequest.setRetriable(boolean)"})
  public void testSetRetriable() {
    // Arrange
    ClientRequest clientRequest = new ClientRequest();

    // Act
    ClientRequest actualSetRetriableResult = clientRequest.setRetriable(true);

    // Assert
    assertTrue(clientRequest.isRetriable());
    assertTrue(clientRequest.isRetriable);
    assertSame(clientRequest, actualSetRetriableResult);
  }

  /**
   * Test {@link ClientRequest#replaceUri(URI)}.
   *
   * <p>Method under test: {@link ClientRequest#replaceUri(URI)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ClientRequest ClientRequest.replaceUri(URI)"})
  public void testReplaceUri() {
    // Arrange
    URI newURI = Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri();

    // Act
    ClientRequest actualReplaceUriResult = new ClientRequest().replaceUri(newURI);

    // Assert
    assertNull(actualReplaceUriResult.getOverrideConfig());
    assertNull(actualReplaceUriResult.isRetriable);
    assertNull(actualReplaceUriResult.getLoadBalancerKey());
    assertFalse(actualReplaceUriResult.isRetriable());
    assertSame(newURI, actualReplaceUriResult.getUri());
  }
}
