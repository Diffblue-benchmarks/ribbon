package com.netflix.ribbon.template;

import static org.junit.Assert.assertEquals;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class MatrixVarDiffblueTest {
  /**
   * Test {@link MatrixVar#MatrixVar(String)}.
   * <p>
   * Method under test: {@link MatrixVar#MatrixVar(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void MatrixVar.<init>(String)"})
  public void testNewMatrixVar() {
    // Arrange, Act and Assert
    assertEquals("Val", (new MatrixVar("Val")).toString());
  }
}
