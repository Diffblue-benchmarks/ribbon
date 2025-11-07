package com.netflix.loadbalancer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.netflix.loadbalancer.CompositePredicate.Builder;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class CompositePredicateDiffblueTest {
  /**
   * Test {@link CompositePredicate#apply(PredicateKey)} with {@code PredicateKey}.
   * <p>
   * Method under test: {@link CompositePredicate#apply(PredicateKey)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean CompositePredicate.apply(PredicateKey)"})
  public void testApplyWithPredicateKey() {
    // Arrange
    CompositePredicate buildResult = CompositePredicate
        .withPredicates(new AvailabilityPredicate(new AvailabilityFilteringRule()))
        .build();

    // Act and Assert
    assertTrue(buildResult.apply(new PredicateKey(new Server("42"))));
  }

  /**
   * Test {@link CompositePredicate#apply(PredicateKey)} with {@code PredicateKey}.
   * <ul>
   *   <li>Given withPredicate {@link ZoneAffinityPredicate#ZoneAffinityPredicate(String)} with {@code Zone} build.</li>
   * </ul>
   * <p>
   * Method under test: {@link CompositePredicate#apply(PredicateKey)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean CompositePredicate.apply(PredicateKey)"})
  public void testApplyWithPredicateKey_givenWithPredicateZoneAffinityPredicateWithZoneBuild() {
    // Arrange
    CompositePredicate buildResult = CompositePredicate.withPredicate(new ZoneAffinityPredicate("Zone")).build();

    // Act and Assert
    assertFalse(buildResult.apply(new PredicateKey(new Server("42"))));
  }

  /**
   * Test {@link CompositePredicate#apply(PredicateKey)} with {@code PredicateKey}.
   * <ul>
   *   <li>Given {@link ZoneAffinityPredicate#ZoneAffinityPredicate(String)} with {@code Zone}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CompositePredicate#apply(PredicateKey)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean CompositePredicate.apply(PredicateKey)"})
  public void testApplyWithPredicateKey_givenZoneAffinityPredicateWithZone_thenReturnFalse() {
    // Arrange
    CompositePredicate buildResult = CompositePredicate.withPredicates(new ZoneAffinityPredicate("Zone")).build();

    // Act and Assert
    assertFalse(buildResult.apply(new PredicateKey(new Server("42"))));
  }

  /**
   * Test {@link CompositePredicate#apply(PredicateKey)} with {@code PredicateKey}.
   * <ul>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CompositePredicate#apply(PredicateKey)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean CompositePredicate.apply(PredicateKey)"})
  public void testApplyWithPredicateKey_thenReturnTrue() {
    // Arrange
    CompositePredicate buildResult = CompositePredicate
        .withPredicate(new AvailabilityPredicate(new AvailabilityFilteringRule()))
        .build();

    // Act and Assert
    assertTrue(buildResult.apply(new PredicateKey(new Server("42"))));
  }

  /**
   * Test Builder {@link Builder#addFallbackPredicate(AbstractServerPredicate)}.
   * <p>
   * Method under test: {@link Builder#addFallbackPredicate(AbstractServerPredicate)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Builder Builder.addFallbackPredicate(AbstractServerPredicate)"})
  public void testBuilderAddFallbackPredicate() {
    // Arrange
    Builder withPredicateResult = CompositePredicate.withPredicate(new CompositePredicate());

    // Act and Assert
    assertSame(withPredicateResult, withPredicateResult.addFallbackPredicate(new CompositePredicate()));
  }

  /**
   * Test Builder {@link Builder#build()}.
   * <p>
   * Method under test: {@link Builder#build()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"CompositePredicate Builder.build()"})
  public void testBuilderBuild() {
    // Arrange and Act
    CompositePredicate actualBuildResult = (new Builder(new CompositePredicate())).build();

    // Assert
    assertNull(actualBuildResult.rule);
    assertNull(actualBuildResult.getLBStats());
  }

  /**
   * Test Builder {@link Builder#Builder(AbstractServerPredicate)}.
   * <p>
   * Method under test: {@link Builder#Builder(AbstractServerPredicate)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void Builder.<init>(AbstractServerPredicate)"})
  public void testBuilderNewBuilder() {
    // Arrange, Act and Assert
    CompositePredicate buildResult = (new Builder(new CompositePredicate())).build();
    assertNull(buildResult.rule);
    assertNull(buildResult.getLBStats());
  }

  /**
   * Test Builder {@link Builder#Builder(AbstractServerPredicate[])}.
   * <ul>
   *   <li>When {@link CompositePredicate} (default constructor).</li>
   *   <li>Then return build {@link AbstractServerPredicate#rule} is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Builder#Builder(AbstractServerPredicate[])}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void Builder.<init>(AbstractServerPredicate[])"})
  public void testBuilderNewBuilder_whenCompositePredicate_thenReturnBuildRuleIsNull() {
    // Arrange, Act and Assert
    CompositePredicate buildResult = (new Builder(new CompositePredicate())).build();
    assertNull(buildResult.rule);
    assertNull(buildResult.getLBStats());
  }

  /**
   * Test Builder {@link Builder#setFallbackThresholdAsMinimalFilteredNumberOfServers(int)}.
   * <p>
   * Method under test: {@link Builder#setFallbackThresholdAsMinimalFilteredNumberOfServers(int)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Builder Builder.setFallbackThresholdAsMinimalFilteredNumberOfServers(int)"})
  public void testBuilderSetFallbackThresholdAsMinimalFilteredNumberOfServers() {
    // Arrange
    Builder withPredicateResult = CompositePredicate.withPredicate(new CompositePredicate());

    // Act and Assert
    assertSame(withPredicateResult, withPredicateResult.setFallbackThresholdAsMinimalFilteredNumberOfServers(10));
  }

  /**
   * Test Builder {@link Builder#setFallbackThresholdAsMinimalFilteredPercentage(float)}.
   * <p>
   * Method under test: {@link Builder#setFallbackThresholdAsMinimalFilteredPercentage(float)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Builder Builder.setFallbackThresholdAsMinimalFilteredPercentage(float)"})
  public void testBuilderSetFallbackThresholdAsMinimalFilteredPercentage() {
    // Arrange
    Builder withPredicateResult = CompositePredicate.withPredicate(new CompositePredicate());

    // Act and Assert
    assertSame(withPredicateResult, withPredicateResult.setFallbackThresholdAsMinimalFilteredPercentage(10.0f));
  }

  /**
   * Test {@link CompositePredicate#getEligibleServers(List, Object)} with {@code servers}, {@code loadBalancerKey}.
   * <p>
   * Method under test: {@link CompositePredicate#getEligibleServers(List, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List CompositePredicate.getEligibleServers(List, Object)"})
  public void testGetEligibleServersWithServersLoadBalancerKey() {
    // Arrange
    CompositePredicate buildResult = CompositePredicate
        .withPredicate(new AvailabilityPredicate(new AvailabilityFilteringRule()))
        .build();

    ArrayList<Server> servers = new ArrayList<>();
    servers.add(new Server("42"));

    // Act and Assert
    assertEquals(servers, buildResult.getEligibleServers(servers, "Load Balancer Key"));
  }

  /**
   * Test {@link CompositePredicate#getEligibleServers(List, Object)} with {@code servers}, {@code loadBalancerKey}.
   * <p>
   * Method under test: {@link CompositePredicate#getEligibleServers(List, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List CompositePredicate.getEligibleServers(List, Object)"})
  public void testGetEligibleServersWithServersLoadBalancerKey2() {
    // Arrange
    CompositePredicate buildResult = CompositePredicate
        .withPredicate(new AvailabilityPredicate(new AvailabilityFilteringRule()))
        .build();

    ArrayList<Server> servers = new ArrayList<>();
    servers.add(new Server("42"));

    // Act and Assert
    assertEquals(servers, buildResult.getEligibleServers(servers, null));
  }

  /**
   * Test {@link CompositePredicate#getEligibleServers(List, Object)} with {@code servers}, {@code loadBalancerKey}.
   * <p>
   * Method under test: {@link CompositePredicate#getEligibleServers(List, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List CompositePredicate.getEligibleServers(List, Object)"})
  public void testGetEligibleServersWithServersLoadBalancerKey3() {
    // Arrange
    CompositePredicate buildResult = CompositePredicate.withPredicates(new ZoneAffinityPredicate("Zone")).build();

    ArrayList<Server> servers = new ArrayList<>();
    servers.add(new Server("42"));

    // Act and Assert
    assertTrue(buildResult.getEligibleServers(servers, "Load Balancer Key").isEmpty());
  }

  /**
   * Test {@link CompositePredicate#getEligibleServers(List, Object)} with {@code servers}, {@code loadBalancerKey}.
   * <p>
   * Method under test: {@link CompositePredicate#getEligibleServers(List, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List CompositePredicate.getEligibleServers(List, Object)"})
  public void testGetEligibleServersWithServersLoadBalancerKey4() {
    // Arrange
    CompositePredicate buildResult = CompositePredicate
        .withPredicates(new AvailabilityPredicate(new AvailabilityFilteringRule()))
        .build();

    ArrayList<Server> servers = new ArrayList<>();
    servers.add(new Server("42"));

    // Act and Assert
    assertEquals(servers, buildResult.getEligibleServers(servers, "Load Balancer Key"));
  }

  /**
   * Test {@link CompositePredicate#getEligibleServers(List, Object)} with {@code servers}, {@code loadBalancerKey}.
   * <p>
   * Method under test: {@link CompositePredicate#getEligibleServers(List, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List CompositePredicate.getEligibleServers(List, Object)"})
  public void testGetEligibleServersWithServersLoadBalancerKey5() {
    // Arrange
    Builder withPredicateResult = CompositePredicate.withPredicate(new ZoneAffinityPredicate("Zone"));
    withPredicateResult.addFallbackPredicate(new AvailabilityPredicate(new AvailabilityFilteringRule()));
    CompositePredicate buildResult = withPredicateResult.build();

    ArrayList<Server> servers = new ArrayList<>();
    servers.add(new Server("42"));

    // Act and Assert
    assertEquals(servers, buildResult.getEligibleServers(servers, "Load Balancer Key"));
  }

  /**
   * Test {@link CompositePredicate#getEligibleServers(List, Object)} with {@code servers}, {@code loadBalancerKey}.
   * <p>
   * Method under test: {@link CompositePredicate#getEligibleServers(List, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List CompositePredicate.getEligibleServers(List, Object)"})
  public void testGetEligibleServersWithServersLoadBalancerKey6() {
    // Arrange
    Builder withPredicateResult = CompositePredicate.withPredicate(new ZoneAffinityPredicate("Zone"));
    withPredicateResult.addFallbackPredicate(new ZoneAffinityPredicate("unknown"));
    CompositePredicate buildResult = withPredicateResult.build();

    ArrayList<Server> servers = new ArrayList<>();
    servers.add(new Server("42"));

    // Act and Assert
    assertEquals(servers, buildResult.getEligibleServers(servers, "Load Balancer Key"));
  }

  /**
   * Test {@link CompositePredicate#getEligibleServers(List, Object)} with {@code servers}, {@code loadBalancerKey}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link CompositePredicate#getEligibleServers(List, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List CompositePredicate.getEligibleServers(List, Object)"})
  public void testGetEligibleServersWithServersLoadBalancerKey_thenReturnEmpty() {
    // Arrange
    CompositePredicate buildResult = CompositePredicate.withPredicate(new ZoneAffinityPredicate("Zone")).build();

    ArrayList<Server> servers = new ArrayList<>();
    servers.add(new Server("42"));

    // Act and Assert
    assertTrue(buildResult.getEligibleServers(servers, "Load Balancer Key").isEmpty());
  }

  /**
   * Test {@link CompositePredicate#getEligibleServers(List, Object)} with {@code servers}, {@code loadBalancerKey}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link CompositePredicate#getEligibleServers(List, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List CompositePredicate.getEligibleServers(List, Object)"})
  public void testGetEligibleServersWithServersLoadBalancerKey_thenReturnEmpty2() {
    // Arrange
    CompositePredicate buildResult = CompositePredicate.withPredicate(new ZoneAffinityPredicate("Zone")).build();

    ArrayList<Server> servers = new ArrayList<>();
    servers.add(new Server("42"));

    // Act and Assert
    assertTrue(buildResult.getEligibleServers(servers, null).isEmpty());
  }

  /**
   * Test {@link CompositePredicate#getEligibleServers(List, Object)} with {@code servers}, {@code loadBalancerKey}.
   * <ul>
   *   <li>Then return size is two.</li>
   * </ul>
   * <p>
   * Method under test: {@link CompositePredicate#getEligibleServers(List, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List CompositePredicate.getEligibleServers(List, Object)"})
  public void testGetEligibleServersWithServersLoadBalancerKey_thenReturnSizeIsTwo() {
    // Arrange
    CompositePredicate buildResult = CompositePredicate
        .withPredicate(new AvailabilityPredicate(new AvailabilityFilteringRule()))
        .build();

    ArrayList<Server> servers = new ArrayList<>();
    servers.add(new Server("42"));
    Server server = new Server("42");
    servers.add(server);

    // Act
    List<Server> actualEligibleServers = buildResult.getEligibleServers(servers, null);

    // Assert
    assertEquals(2, actualEligibleServers.size());
    assertSame(server, actualEligibleServers.get(1));
  }

  /**
   * Test {@link CompositePredicate#getEligibleServers(List, Object)} with {@code servers}, {@code loadBalancerKey}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link CompositePredicate#getEligibleServers(List, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List CompositePredicate.getEligibleServers(List, Object)"})
  public void testGetEligibleServersWithServersLoadBalancerKey_whenArrayList_thenReturnEmpty() {
    // Arrange
    CompositePredicate compositePredicate = new CompositePredicate();

    // Act and Assert
    assertTrue(compositePredicate.getEligibleServers(new ArrayList<>(), "Load Balancer Key").isEmpty());
  }

  /**
   * Test new {@link CompositePredicate} (default constructor).
   * <p>
   * Method under test: default or parameterless constructor of {@link CompositePredicate}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void CompositePredicate.<init>()"})
  public void testNewCompositePredicate() {
    // Arrange and Act
    CompositePredicate actualCompositePredicate = new CompositePredicate();

    // Assert
    assertNull(actualCompositePredicate.rule);
    assertNull(actualCompositePredicate.getLBStats());
  }
}
