package com.netflix.loadbalancer.reactive;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import com.netflix.loadbalancer.Server;
import org.junit.Test;

public class ExecutionInfoDiffblueTest {
  /**
   * Method under test: {@link ExecutionInfo#create(Server, int, int)}
   */
  @Test
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
   * Methods under test:
   * <ul>
   *   <li>{@link ExecutionInfo#toString()}
   *   <li>{@link ExecutionInfo#getNumberOfPastAttemptsOnServer()}
   *   <li>{@link ExecutionInfo#getNumberOfPastServersAttempted()}
   *   <li>{@link ExecutionInfo#getServer()}
   * </ul>
   */
  @Test
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
