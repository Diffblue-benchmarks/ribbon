package com.netflix.loadbalancer;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PredicateBasedRuleDiffblueTest {
  @Mock private ILoadBalancer iLoadBalancer;

  @InjectMocks private ZoneAvoidanceRule zoneAvoidanceRule;

  /**
   * Test {@link PredicateBasedRule#choose(Object)}.
   *
   * <p>Method under test: {@link PredicateBasedRule#choose(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Server PredicateBasedRule.choose(Object)"})
  public void testChoose() {
    // Arrange
    IPing ping = mock(IPing.class);

    BaseLoadBalancer lb = new BaseLoadBalancer(ping, new AvailabilityFilteringRule());
    lb.setEnablePrimingConnections(true);
    lb.setPing(mock(IPing.class));
    lb.addServer(null);

    AvailabilityFilteringRule availabilityFilteringRule = new AvailabilityFilteringRule();
    availabilityFilteringRule.setLoadBalancer(lb);

    // Act and Assert
    assertNull(availabilityFilteringRule.choose(null));
  }

  /**
   * Test {@link PredicateBasedRule#choose(Object)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link Server#Server(String)} with id is {@code
   *       42}.
   *   <li>When {@code Key}.
   *   <li>Then return {@link Server#Server(String)} with id is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link PredicateBasedRule#choose(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Server PredicateBasedRule.choose(Object)"})
  public void testChoose_givenArrayListAddServerWithIdIs42_whenKey_thenReturnServerWithIdIs42() {
    // Arrange
    ArrayList<Server> serverList = new ArrayList<>();
    Server server = new Server("42");
    serverList.add(server);
    when(iLoadBalancer.getAllServers()).thenReturn(serverList);

    // Act
    Server actualChooseResult = zoneAvoidanceRule.choose("Key");

    // Assert
    verify(iLoadBalancer).getAllServers();
    assertSame(server, actualChooseResult);
  }

  /**
   * Test {@link PredicateBasedRule#choose(Object)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link Server#Server(String)} with id is {@code
   *       42}.
   *   <li>When {@code null}.
   *   <li>Then return {@link Server#Server(String)} with id is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link PredicateBasedRule#choose(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Server PredicateBasedRule.choose(Object)"})
  public void testChoose_givenArrayListAddServerWithIdIs42_whenNull_thenReturnServerWithIdIs42() {
    // Arrange
    ArrayList<Server> serverList = new ArrayList<>();
    Server server = new Server("42");
    serverList.add(server);
    when(iLoadBalancer.getAllServers()).thenReturn(serverList);

    // Act
    Server actualChooseResult = zoneAvoidanceRule.choose(null);

    // Assert
    verify(iLoadBalancer).getAllServers();
    assertSame(server, actualChooseResult);
  }

  /**
   * Test {@link PredicateBasedRule#choose(Object)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link Server#Server(String)} with id is {@code
   *       42}.
   *   <li>When {@code null}.
   *   <li>Then return {@link Server#Server(String)} with id is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link PredicateBasedRule#choose(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Server PredicateBasedRule.choose(Object)"})
  public void testChoose_givenArrayListAddServerWithIdIs42_whenNull_thenReturnServerWithIdIs422() {
    // Arrange
    ArrayList<Server> serverList = new ArrayList<>();
    Server server = new Server("42");
    serverList.add(server);
    serverList.add(new Server("42"));
    when(iLoadBalancer.getAllServers()).thenReturn(serverList);

    // Act
    Server actualChooseResult = zoneAvoidanceRule.choose(null);

    // Assert
    verify(iLoadBalancer).getAllServers();
    assertSame(server, actualChooseResult);
  }

  /**
   * Test {@link PredicateBasedRule#choose(Object)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link Server#Server(String)} with id is {@code
   *       42}.
   *   <li>When {@code null}.
   *   <li>Then return {@link Server#Server(String)} with id is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link PredicateBasedRule#choose(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Server PredicateBasedRule.choose(Object)"})
  public void testChoose_givenArrayListAddServerWithIdIs42_whenNull_thenReturnServerWithIdIs423() {
    // Arrange
    ArrayList<Server> serverList = new ArrayList<>();
    Server server = new Server("42");
    serverList.add(server);
    serverList.add(new Server("42"));
    serverList.add(new Server("42"));
    serverList.add(new Server("42"));
    when(iLoadBalancer.getAllServers()).thenReturn(serverList);

    // Act
    Server actualChooseResult = zoneAvoidanceRule.choose(null);

    // Assert
    verify(iLoadBalancer).getAllServers();
    assertSame(server, actualChooseResult);
  }

  /**
   * Test {@link PredicateBasedRule#choose(Object)}.
   *
   * <ul>
   *   <li>Given {@link AvailabilityFilteringRule} (default constructor) LoadBalancer is {@link
   *       BaseLoadBalancer#BaseLoadBalancer()}.
   * </ul>
   *
   * <p>Method under test: {@link PredicateBasedRule#choose(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Server PredicateBasedRule.choose(Object)"})
  public void testChoose_givenAvailabilityFilteringRuleLoadBalancerIsBaseLoadBalancer() {
    // Arrange
    AvailabilityFilteringRule availabilityFilteringRule = new AvailabilityFilteringRule();
    availabilityFilteringRule.setLoadBalancer(new BaseLoadBalancer());

    // Act and Assert
    assertNull(availabilityFilteringRule.choose("Key"));
  }

  /**
   * Test {@link PredicateBasedRule#choose(Object)}.
   *
   * <ul>
   *   <li>Given {@link ZoneAvoidanceRule} (default constructor) LoadBalancer is {@link
   *       BaseLoadBalancer#BaseLoadBalancer()}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link PredicateBasedRule#choose(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Server PredicateBasedRule.choose(Object)"})
  public void testChoose_givenZoneAvoidanceRuleLoadBalancerIsBaseLoadBalancer_thenReturnNull() {
    // Arrange
    ZoneAvoidanceRule zoneAvoidanceRule = new ZoneAvoidanceRule();
    zoneAvoidanceRule.setLoadBalancer(new BaseLoadBalancer());

    // Act and Assert
    assertNull(zoneAvoidanceRule.choose("Key"));
  }
}
