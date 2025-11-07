package com.netflix.ribbon.evache;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class CacheMissExceptionDiffblueTest {
  /**
   * Test new {@link CacheMissException} (default constructor).
   * <p>
   * Method under test: default or parameterless constructor of {@link CacheMissException}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void CacheMissException.<init>()"})
  public void testNewCacheMissException() {
    // Arrange and Act
    CacheMissException actualCacheMissException = new CacheMissException();

    // Assert
    assertNull(actualCacheMissException.getMessage());
    assertNull(actualCacheMissException.getCause());
    assertEquals(0, actualCacheMissException.getSuppressed().length);
  }
}
