package com.netflix.ribbon.template;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class PathVarDiffblueTest {
  /**
   * Method under test: {@link PathVar#PathVar(String)}
   */
  @Test
  public void testNewPathVar() {
    // Arrange, Act and Assert
    assertEquals("Val", (new PathVar("Val")).toString());
  }
}
