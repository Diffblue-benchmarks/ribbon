package com.netflix.loadbalancer;

import static org.junit.Assert.assertEquals;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class ServerComparatorDiffblueTest {
  /**
   * Test {@link ServerComparator#compare(Server, Server)} with {@code Server}, {@code Server}.
   * <ul>
   *   <li>When {@link Server#Server(String)} with id is {@code 42}.</li>
   *   <li>Then return zero.</li>
   * </ul>
   * <p>
   * Method under test: {@link ServerComparator#compare(Server, Server)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"int ServerComparator.compare(Server, Server)"})
  public void testCompareWithServerServer_whenServerWithIdIs42_thenReturnZero() {
    // Arrange
    ServerComparator serverComparator = new ServerComparator();
    Server s1 = new Server("42");

    // Act and Assert
    assertEquals(0, serverComparator.compare(s1, new Server("42")));
  }
}
