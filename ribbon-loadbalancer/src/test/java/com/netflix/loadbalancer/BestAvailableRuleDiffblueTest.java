package com.netflix.loadbalancer;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.atLeast;
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
public class BestAvailableRuleDiffblueTest {
  @InjectMocks private BestAvailableRule bestAvailableRule;

  @Mock private ILoadBalancer iLoadBalancer;

  /**
   * Test {@link BestAvailableRule#choose(Object)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@code null}.
   *   <li>Then calls {@link ILoadBalancer#getAllServers()}.
   * </ul>
   *
   * <p>Method under test: {@link BestAvailableRule#choose(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Server BestAvailableRule.choose(Object)"})
  public void testChoose_givenArrayListAddNull_thenCallsGetAllServers() {
    // Arrange
    ArrayList<Server> serverList = new ArrayList<>();
    serverList.add(null);

    ArrayList<Server> serverList2 = new ArrayList<>();
    serverList2.add(new Server("42"));
    when(iLoadBalancer.getAllServers()).thenReturn(serverList);
    when(iLoadBalancer.getReachableServers()).thenReturn(serverList2);
    bestAvailableRule.setLoadBalancer(iLoadBalancer);

    // Act
    Server actualChooseResult = bestAvailableRule.choose("Key");

    // Assert
    verify(iLoadBalancer, atLeast(1)).getAllServers();
    verify(iLoadBalancer, atLeast(1)).getReachableServers();
    assertNull(actualChooseResult);
  }

  /**
   * Test {@link BestAvailableRule#choose(Object)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link Server#Server(String)} with id is {@code
   *       42}.
   *   <li>Then calls {@link ILoadBalancer#getAllServers()}.
   * </ul>
   *
   * <p>Method under test: {@link BestAvailableRule#choose(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Server BestAvailableRule.choose(Object)"})
  public void testChoose_givenArrayListAddServerWithIdIs42_thenCallsGetAllServers() {
    // Arrange
    ArrayList<Server> serverList = new ArrayList<>();
    serverList.add(new Server("42"));
    when(iLoadBalancer.getAllServers()).thenReturn(serverList);
    when(iLoadBalancer.getReachableServers()).thenReturn(new ArrayList<>());
    bestAvailableRule.setLoadBalancer(iLoadBalancer);

    // Act
    Server actualChooseResult = bestAvailableRule.choose("Key");

    // Assert
    verify(iLoadBalancer).getAllServers();
    verify(iLoadBalancer).getReachableServers();
    assertNull(actualChooseResult);
  }

  /**
   * Test {@link BestAvailableRule#choose(Object)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link Server#Server(String)} with id is {@code
   *       42}.
   *   <li>Then calls {@link ILoadBalancer#getAllServers()}.
   * </ul>
   *
   * <p>Method under test: {@link BestAvailableRule#choose(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Server BestAvailableRule.choose(Object)"})
  public void testChoose_givenArrayListAddServerWithIdIs42_thenCallsGetAllServers2() {
    // Arrange
    ArrayList<Server> serverList = new ArrayList<>();
    serverList.add(new Server("42"));

    ArrayList<Server> serverList2 = new ArrayList<>();
    serverList2.add(new Server("42"));
    when(iLoadBalancer.getAllServers()).thenReturn(serverList);
    when(iLoadBalancer.getReachableServers()).thenReturn(serverList2);
    bestAvailableRule.setLoadBalancer(iLoadBalancer);

    // Act
    Server actualChooseResult = bestAvailableRule.choose("Key");

    // Assert
    verify(iLoadBalancer, atLeast(1)).getAllServers();
    verify(iLoadBalancer, atLeast(1)).getReachableServers();
    assertNull(actualChooseResult);
  }

  /**
   * Test {@link BestAvailableRule#choose(Object)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link Server#Server(String)} with id is {@code
   *       42}.
   *   <li>Then calls {@link ILoadBalancer#getAllServers()}.
   * </ul>
   *
   * <p>Method under test: {@link BestAvailableRule#choose(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Server BestAvailableRule.choose(Object)"})
  public void testChoose_givenArrayListAddServerWithIdIs42_thenCallsGetAllServers3() {
    // Arrange
    ArrayList<Server> serverList = new ArrayList<>();
    serverList.add(new Server("42"));
    when(iLoadBalancer.getAllServers()).thenReturn(new ArrayList<>());
    when(iLoadBalancer.getReachableServers()).thenReturn(serverList);
    bestAvailableRule.setLoadBalancer(iLoadBalancer);

    // Act
    Server actualChooseResult = bestAvailableRule.choose("Key");

    // Assert
    verify(iLoadBalancer).getAllServers();
    verify(iLoadBalancer).getReachableServers();
    assertNull(actualChooseResult);
  }

  /**
   * Test {@link BestAvailableRule#choose(Object)}.
   *
   * <ul>
   *   <li>Given {@link BestAvailableRule} (default constructor) LoadBalancer is {@link
   *       BaseLoadBalancer#BaseLoadBalancer()}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link BestAvailableRule#choose(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Server BestAvailableRule.choose(Object)"})
  public void testChoose_givenBestAvailableRuleLoadBalancerIsBaseLoadBalancer_thenReturnNull() {
    // Arrange
    BestAvailableRule bestAvailableRule = new BestAvailableRule();
    bestAvailableRule.setLoadBalancer(new BaseLoadBalancer());

    // Act and Assert
    assertNull(bestAvailableRule.choose("Key"));
  }

  /**
   * Test {@link BestAvailableRule#choose(Object)}.
   *
   * <ul>
   *   <li>Given {@link BestAvailableRule} (default constructor).
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link BestAvailableRule#choose(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Server BestAvailableRule.choose(Object)"})
  public void testChoose_givenBestAvailableRule_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(new BestAvailableRule().choose("Key"));
  }

  /**
   * Test {@link BestAvailableRule#choose(Object)}.
   *
   * <ul>
   *   <li>Given {@link Server#Server(String)} with id is {@code 42} Alive is {@code true}.
   *   <li>Then return {@link Server#Server(String)} with id is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link BestAvailableRule#choose(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Server BestAvailableRule.choose(Object)"})
  public void testChoose_givenServerWithIdIs42AliveIsTrue_thenReturnServerWithIdIs42() {
    // Arrange
    Server server = new Server("42");
    server.setAlive(true);

    ArrayList<Server> serverList = new ArrayList<>();
    serverList.add(server);

    ArrayList<Server> serverList2 = new ArrayList<>();
    serverList2.add(new Server("42"));
    when(iLoadBalancer.getAllServers()).thenReturn(serverList);
    when(iLoadBalancer.getReachableServers()).thenReturn(serverList2);
    bestAvailableRule.setLoadBalancer(iLoadBalancer);

    // Act
    Server actualChooseResult = bestAvailableRule.choose("Key");

    // Assert
    verify(iLoadBalancer).getAllServers();
    verify(iLoadBalancer).getReachableServers();
    assertSame(server, actualChooseResult);
  }

  /**
   * Test {@link BestAvailableRule#setLoadBalancer(ILoadBalancer)}.
   *
   * <ul>
   *   <li>Then {@link BestAvailableRule} (default constructor) LoadBalancer is {@link
   *       BaseLoadBalancer#BaseLoadBalancer()}.
   * </ul>
   *
   * <p>Method under test: {@link BestAvailableRule#setLoadBalancer(ILoadBalancer)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BestAvailableRule.setLoadBalancer(ILoadBalancer)"})
  public void testSetLoadBalancer_thenBestAvailableRuleLoadBalancerIsBaseLoadBalancer() {
    // Arrange
    BestAvailableRule bestAvailableRule = new BestAvailableRule();
    BaseLoadBalancer lb = new BaseLoadBalancer();

    // Act
    bestAvailableRule.setLoadBalancer(lb);

    // Assert
    assertSame(lb, bestAvailableRule.getLoadBalancer());
    assertSame(lb, bestAvailableRule.roundRobinRule.getLoadBalancer());
  }

  /**
   * Test {@link BestAvailableRule#setLoadBalancer(ILoadBalancer)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then {@link BestAvailableRule} (default constructor) LoadBalancer is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link BestAvailableRule#setLoadBalancer(ILoadBalancer)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BestAvailableRule.setLoadBalancer(ILoadBalancer)"})
  public void testSetLoadBalancer_whenNull_thenBestAvailableRuleLoadBalancerIsNull() {
    // Arrange
    BestAvailableRule bestAvailableRule = new BestAvailableRule();

    // Act
    bestAvailableRule.setLoadBalancer(null);

    // Assert that nothing has changed
    assertNull(bestAvailableRule.getLoadBalancer());
    assertNull(bestAvailableRule.roundRobinRule.getLoadBalancer());
  }

  /**
   * Test new {@link BestAvailableRule} (default constructor).
   *
   * <p>Method under test: default or parameterless constructor of {@link BestAvailableRule}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BestAvailableRule.<init>()"})
  public void testNewBestAvailableRule() {
    // Arrange and Act
    BestAvailableRule actualBestAvailableRule = new BestAvailableRule();

    // Assert
    assertNull(actualBestAvailableRule.getLoadBalancer());
    assertNull(actualBestAvailableRule.roundRobinRule.getLoadBalancer());
  }
}
