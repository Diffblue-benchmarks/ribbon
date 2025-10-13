package com.netflix.loadbalancer;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.netflix.util.Pair;
import java.net.URI;
import java.nio.file.Paths;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;

public class LoadBalancerContextDiffblueTest {
  /**
   * Test {@link LoadBalancerContext#deriveSchemeAndPortFromPartialUri(URI)}.
   *
   * <p>Method under test: {@link LoadBalancerContext#deriveSchemeAndPortFromPartialUri(URI)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Pair LoadBalancerContext.deriveSchemeAndPortFromPartialUri(URI)"})
  public void testDeriveSchemeAndPortFromPartialUri() {
    // Arrange and Act
    Pair<String, Integer> actualDeriveSchemeAndPortFromPartialUriResult =
        new LoadBalancerContext(new BaseLoadBalancer())
            .deriveSchemeAndPortFromPartialUri(
                Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri());

    // Assert
    assertEquals("file", actualDeriveSchemeAndPortFromPartialUriResult.first());
    assertEquals(80, actualDeriveSchemeAndPortFromPartialUriResult.second().intValue());
  }

  /**
   * Test {@link LoadBalancerContext#deriveSchemeAndPortFromPartialUri(URI)}.
   *
   * <p>Method under test: {@link LoadBalancerContext#deriveSchemeAndPortFromPartialUri(URI)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Pair LoadBalancerContext.deriveSchemeAndPortFromPartialUri(URI)"})
  public void testDeriveSchemeAndPortFromPartialUri2() {
    // Arrange and Act
    Pair<String, Integer> actualDeriveSchemeAndPortFromPartialUriResult =
        new LoadBalancerContext(new BaseLoadBalancer())
            .deriveSchemeAndPortFromPartialUri(
                Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri());

    // Assert
    assertEquals("file", actualDeriveSchemeAndPortFromPartialUriResult.first());
    assertEquals(80, actualDeriveSchemeAndPortFromPartialUriResult.second().intValue());
  }

  /**
   * Test {@link LoadBalancerContext#deriveSchemeAndPortFromPartialUri(URI)}.
   *
   * <ul>
   *   <li>Given {@link Server#Server(String)} with id is {@code 42} Alive is {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link LoadBalancerContext#deriveSchemeAndPortFromPartialUri(URI)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Pair LoadBalancerContext.deriveSchemeAndPortFromPartialUri(URI)"})
  public void testDeriveSchemeAndPortFromPartialUri_givenServerWithIdIs42AliveIsTrue() {
    // Arrange
    Server newServer = new Server("42");
    newServer.setAlive(true);

    IPing ping = mock(IPing.class);
    when(ping.isAlive(Mockito.<Server>any())).thenThrow(new RuntimeException());

    BaseLoadBalancer lb = new BaseLoadBalancer();
    lb.setPing(ping);
    lb.addServer(newServer);

    // Act
    Pair<String, Integer> actualDeriveSchemeAndPortFromPartialUriResult =
        new LoadBalancerContext(lb)
            .deriveSchemeAndPortFromPartialUri(
                Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri());

    // Assert
    verify(ping).isAlive(isA(Server.class));
    assertEquals("file", actualDeriveSchemeAndPortFromPartialUriResult.first());
    assertEquals(80, actualDeriveSchemeAndPortFromPartialUriResult.second().intValue());
  }
}
