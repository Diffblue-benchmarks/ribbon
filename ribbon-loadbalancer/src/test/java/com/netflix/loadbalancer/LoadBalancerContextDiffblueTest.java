package com.netflix.loadbalancer;

import static org.junit.Assert.assertEquals;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.netflix.util.Pair;
import java.net.URI;
import java.nio.file.Paths;
import org.junit.Test;
import org.junit.experimental.categories.Category;

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
}
