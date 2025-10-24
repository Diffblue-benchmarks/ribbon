package com.netflix.loadbalancer.reactive;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.netflix.loadbalancer.Server;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class ExecutionInfoDiffblueTest {
  /**
   * Test {@link ExecutionInfo#create(Server, int, int)}.
   *
   * <ul>
   *   <li>When {@link Server#Server(String)} with id is {@code 42}.
   *   <li>Then return NumberOfPastAttemptsOnServer is ten.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionInfo#create(Server, int, int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ExecutionInfo ExecutionInfo.create(Server, int, int)"})
  public void testCreate_whenServerWithIdIs42_thenReturnNumberOfPastAttemptsOnServerIsTen() {
    // Arrange
    Server server = new Server("42");

    // Act
    ExecutionInfo actualCreateResult = ExecutionInfo.create(server, 10, 10);

    // Assert
    assertEquals(10, actualCreateResult.getNumberOfPastAttemptsOnServer());
    assertEquals(10, actualCreateResult.getNumberOfPastServersAttempted());
    assertSame(server, actualCreateResult.getServer());
  }
}
