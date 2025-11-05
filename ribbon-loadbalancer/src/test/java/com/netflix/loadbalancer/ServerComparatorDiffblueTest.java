package com.netflix.loadbalancer;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class ServerComparatorDiffblueTest {
  /**
   * Method under test: {@link ServerComparator#compare(Server, Server)}
   */
  @Test
  public void testCompare() {
    // Arrange
    ServerComparator serverComparator = new ServerComparator();
    Server s1 = new Server("42");

    // Act and Assert
    assertEquals(0, serverComparator.compare(s1, new Server("42")));
  }
}
