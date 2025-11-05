package com.netflix.ribbon.template;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class MatrixVarDiffblueTest {
  /**
   * Method under test: {@link MatrixVar#MatrixVar(String)}
   */
  @Test
  public void testNewMatrixVar() {
    // Arrange, Act and Assert
    assertEquals("Val", (new MatrixVar("Val")).toString());
  }
}
