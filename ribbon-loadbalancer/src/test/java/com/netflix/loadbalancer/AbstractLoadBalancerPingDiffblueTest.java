package com.netflix.loadbalancer;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class AbstractLoadBalancerPingDiffblueTest {
  /**
   * Test {@link AbstractLoadBalancerPing#isAlive(Server)}.
   *
   * <p>Method under test: {@link AbstractLoadBalancerPing#isAlive(Server)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean AbstractLoadBalancerPing.isAlive(Server)"})
  public void testIsAlive() {
    // Arrange
    DummyPing dummyPing = new DummyPing();

    // Act
    boolean actualIsAliveResult = dummyPing.isAlive(new Server("42"));

    // Assert
    assertTrue(actualIsAliveResult);
  }

  /**
   * Test {@link AbstractLoadBalancerPing#setLoadBalancer(AbstractLoadBalancer)}.
   *
   * <p>Method under test: {@link AbstractLoadBalancerPing#setLoadBalancer(AbstractLoadBalancer)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void AbstractLoadBalancerPing.setLoadBalancer(AbstractLoadBalancer)"})
  public void testSetLoadBalancer() {
    // Arrange
    DummyPing dummyPing = new DummyPing();
    BaseLoadBalancer lb = new BaseLoadBalancer();

    // Act
    dummyPing.setLoadBalancer(lb);

    // Assert
    assertSame(lb, dummyPing.getLoadBalancer());
  }

  /**
   * Test {@link AbstractLoadBalancerPing#getLoadBalancer()}.
   *
   * <p>Method under test: {@link AbstractLoadBalancerPing#getLoadBalancer()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"AbstractLoadBalancer AbstractLoadBalancerPing.getLoadBalancer()"})
  public void testGetLoadBalancer() {
    // Arrange, Act and Assert
    assertNull(new DummyPing().getLoadBalancer());
  }
}
