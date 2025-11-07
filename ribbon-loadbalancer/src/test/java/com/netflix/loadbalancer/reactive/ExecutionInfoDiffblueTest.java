package com.netflix.loadbalancer.reactive;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.netflix.loadbalancer.Server;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class ExecutionInfoDiffblueTest {
  /**
   * Test {@link ExecutionInfo#create(Server, int, int)}.
   * <p>
   * Method under test: {@link ExecutionInfo#create(Server, int, int)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ExecutionInfo ExecutionInfo.create(Server, int, int)"})
  public void testCreate() {
    // Arrange
    Server server = new Server("42");

    // Act
    ExecutionInfo actualCreateResult = ExecutionInfo.create(server, 10, 10);

    // Assert
    assertEquals(10, actualCreateResult.getNumberOfPastAttemptsOnServer());
    assertEquals(10, actualCreateResult.getNumberOfPastServersAttempted());
    assertSame(server, actualCreateResult.getServer());
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ExecutionInfo#toString()}
   *   <li>{@link ExecutionInfo#getNumberOfPastAttemptsOnServer()}
   *   <li>{@link ExecutionInfo#getNumberOfPastServersAttempted()}
   *   <li>{@link ExecutionInfo#getServer()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"int ExecutionInfo.getNumberOfPastAttemptsOnServer()",
      "int ExecutionInfo.getNumberOfPastServersAttempted()", "Server ExecutionInfo.getServer()",
      "String ExecutionInfo.toString()"})
  public void testGettersAndSetters() {
    // Arrange
    Server server = new Server("42");
    ExecutionInfo createResult = ExecutionInfo.create(server, 10, 10);

    // Act
    String actualToStringResult = createResult.toString();
    int actualNumberOfPastAttemptsOnServer = createResult.getNumberOfPastAttemptsOnServer();
    int actualNumberOfPastServersAttempted = createResult.getNumberOfPastServersAttempted();

    // Assert
    assertEquals("ExecutionInfo{server=42:80, numberOfPastAttemptsOnServer=10, numberOfPastServersAttempted=10}",
        actualToStringResult);
    assertEquals(10, actualNumberOfPastAttemptsOnServer);
    assertEquals(10, actualNumberOfPastServersAttempted);
    assertSame(server, createResult.getServer());
  }
}
