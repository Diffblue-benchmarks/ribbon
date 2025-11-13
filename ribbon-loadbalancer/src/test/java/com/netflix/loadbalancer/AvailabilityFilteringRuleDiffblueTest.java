package com.netflix.loadbalancer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
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
public class AvailabilityFilteringRuleDiffblueTest {
  @InjectMocks private AvailabilityFilteringRule availabilityFilteringRule;

  @Mock private ILoadBalancer iLoadBalancer;

  /**
   * Test new {@link AvailabilityFilteringRule} (default constructor).
   *
   * <p>Method under test: default or parameterless constructor of {@link AvailabilityFilteringRule}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void AvailabilityFilteringRule.<init>()"})
  public void testNewAvailabilityFilteringRule() {
    // Arrange and Act
    AvailabilityFilteringRule actualAvailabilityFilteringRule = new AvailabilityFilteringRule();

    // Assert
    AbstractServerPredicate predicate = actualAvailabilityFilteringRule.getPredicate();
    assertTrue(predicate instanceof CompositePredicate);
    assertNull(actualAvailabilityFilteringRule.getLoadBalancer());
    assertNull(actualAvailabilityFilteringRule.roundRobinRule.getLoadBalancer());
    assertNull(((CompositePredicate) predicate).rule);
    assertNull(predicate.getLBStats());
  }

  /**
   * Test {@link AvailabilityFilteringRule#getAvailableServersCount()}.
   *
   * <p>Method under test: {@link AvailabilityFilteringRule#getAvailableServersCount()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int AvailabilityFilteringRule.getAvailableServersCount()"})
  public void testGetAvailableServersCount() {
    // Arrange
    AvailabilityFilteringRule availabilityFilteringRule = new AvailabilityFilteringRule();
    availabilityFilteringRule.setLoadBalancer(new NoOpLoadBalancer());

    // Act and Assert
    assertEquals(0, availabilityFilteringRule.getAvailableServersCount());
  }

  /**
   * Test {@link AvailabilityFilteringRule#getAvailableServersCount()}.
   *
   * <ul>
   *   <li>Given {@link BaseLoadBalancer#BaseLoadBalancer()} LoadBalancerStats is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link AvailabilityFilteringRule#getAvailableServersCount()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int AvailabilityFilteringRule.getAvailableServersCount()"})
  public void testGetAvailableServersCount_givenBaseLoadBalancerLoadBalancerStatsIsNull() {
    // Arrange
    BaseLoadBalancer lb = new BaseLoadBalancer();
    lb.setLoadBalancerStats(null);
    lb.addServer(new Server("42"));

    AvailabilityFilteringRule availabilityFilteringRule = new AvailabilityFilteringRule();
    availabilityFilteringRule.setLoadBalancer(lb);

    // Act and Assert
    assertEquals(1, availabilityFilteringRule.getAvailableServersCount());
  }

  /**
   * Test {@link AvailabilityFilteringRule#getAvailableServersCount()}.
   *
   * <ul>
   *   <li>Then calls {@link ILoadBalancer#getAllServers()}.
   * </ul>
   *
   * <p>Method under test: {@link AvailabilityFilteringRule#getAvailableServersCount()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int AvailabilityFilteringRule.getAvailableServersCount()"})
  public void testGetAvailableServersCount_thenCallsGetAllServers() {
    // Arrange
    ArrayList<Server> serverList = new ArrayList<>();
    serverList.add(new Server("42"));
    when(iLoadBalancer.getAllServers()).thenReturn(serverList);

    // Act
    int actualAvailableServersCount = availabilityFilteringRule.getAvailableServersCount();

    // Assert
    verify(iLoadBalancer).getAllServers();
    assertEquals(1, actualAvailableServersCount);
  }

  /**
   * Test {@link AvailabilityFilteringRule#getAvailableServersCount()}.
   *
   * <ul>
   *   <li>Then return zero.
   * </ul>
   *
   * <p>Method under test: {@link AvailabilityFilteringRule#getAvailableServersCount()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int AvailabilityFilteringRule.getAvailableServersCount()"})
  public void testGetAvailableServersCount_thenReturnZero() {
    // Arrange
    AvailabilityFilteringRule availabilityFilteringRule = new AvailabilityFilteringRule();
    availabilityFilteringRule.setLoadBalancer(new BaseLoadBalancer());

    // Act and Assert
    assertEquals(0, availabilityFilteringRule.getAvailableServersCount());
  }

  /**
   * Test {@link AvailabilityFilteringRule#choose(Object)}.
   *
   * <p>Method under test: {@link AvailabilityFilteringRule#choose(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Server AvailabilityFilteringRule.choose(Object)"})
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
   * Test {@link AvailabilityFilteringRule#choose(Object)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link Server#Server(String)} with id is {@code
   *       42}.
   *   <li>When {@code Key}.
   *   <li>Then return {@link Server#Server(String)} with id is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link AvailabilityFilteringRule#choose(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Server AvailabilityFilteringRule.choose(Object)"})
  public void testChoose_givenArrayListAddServerWithIdIs42_whenKey_thenReturnServerWithIdIs42() {
    // Arrange
    ArrayList<Server> serverList = new ArrayList<>();
    Server server = new Server("42");
    serverList.add(server);
    when(iLoadBalancer.getAllServers()).thenReturn(serverList);

    // Act
    Server actualChooseResult = availabilityFilteringRule.choose("Key");

    // Assert
    verify(iLoadBalancer).getAllServers();
    assertSame(server, actualChooseResult);
  }

  /**
   * Test {@link AvailabilityFilteringRule#choose(Object)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link Server#Server(String)} with id is {@code
   *       42}.
   *   <li>When {@code null}.
   *   <li>Then return {@link Server#Server(String)} with id is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link AvailabilityFilteringRule#choose(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Server AvailabilityFilteringRule.choose(Object)"})
  public void testChoose_givenArrayListAddServerWithIdIs42_whenNull_thenReturnServerWithIdIs42() {
    // Arrange
    ArrayList<Server> serverList = new ArrayList<>();
    Server server = new Server("42");
    serverList.add(server);
    when(iLoadBalancer.getAllServers()).thenReturn(serverList);

    // Act
    Server actualChooseResult = availabilityFilteringRule.choose(null);

    // Assert
    verify(iLoadBalancer).getAllServers();
    assertSame(server, actualChooseResult);
  }

  /**
   * Test {@link AvailabilityFilteringRule#choose(Object)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link Server#Server(String)} with id is {@code
   *       42}.
   *   <li>When {@code null}.
   *   <li>Then return {@link Server#Server(String)} with id is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link AvailabilityFilteringRule#choose(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Server AvailabilityFilteringRule.choose(Object)"})
  public void testChoose_givenArrayListAddServerWithIdIs42_whenNull_thenReturnServerWithIdIs422() {
    // Arrange
    ArrayList<Server> serverList = new ArrayList<>();
    Server server = new Server("42");
    serverList.add(server);
    serverList.add(new Server("42"));
    when(iLoadBalancer.getAllServers()).thenReturn(serverList);

    // Act
    Server actualChooseResult = availabilityFilteringRule.choose(null);

    // Assert
    verify(iLoadBalancer).getAllServers();
    assertSame(server, actualChooseResult);
  }

  /**
   * Test {@link AvailabilityFilteringRule#choose(Object)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link Server#Server(String)} with id is {@code
   *       42}.
   *   <li>When {@code null}.
   *   <li>Then return {@link Server#Server(String)} with id is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link AvailabilityFilteringRule#choose(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Server AvailabilityFilteringRule.choose(Object)"})
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
    Server actualChooseResult = availabilityFilteringRule.choose(null);

    // Assert
    verify(iLoadBalancer).getAllServers();
    assertSame(server, actualChooseResult);
  }

  /**
   * Test {@link AvailabilityFilteringRule#choose(Object)}.
   *
   * <ul>
   *   <li>Given {@link AvailabilityFilteringRule} (default constructor) LoadBalancer is {@link
   *       BaseLoadBalancer#BaseLoadBalancer()}.
   * </ul>
   *
   * <p>Method under test: {@link AvailabilityFilteringRule#choose(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Server AvailabilityFilteringRule.choose(Object)"})
  public void testChoose_givenAvailabilityFilteringRuleLoadBalancerIsBaseLoadBalancer() {
    // Arrange
    AvailabilityFilteringRule availabilityFilteringRule = new AvailabilityFilteringRule();
    availabilityFilteringRule.setLoadBalancer(new BaseLoadBalancer());

    // Act and Assert
    assertNull(availabilityFilteringRule.choose("Key"));
  }

  /**
   * Test {@link AvailabilityFilteringRule#getPredicate()}.
   *
   * <p>Method under test: {@link AvailabilityFilteringRule#getPredicate()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"AbstractServerPredicate AvailabilityFilteringRule.getPredicate()"})
  public void testGetPredicate() {
    // Arrange and Act
    AbstractServerPredicate actualPredicate = new AvailabilityFilteringRule().getPredicate();

    // Assert
    assertTrue(actualPredicate instanceof CompositePredicate);
    assertNull(((CompositePredicate) actualPredicate).rule);
  }
}
