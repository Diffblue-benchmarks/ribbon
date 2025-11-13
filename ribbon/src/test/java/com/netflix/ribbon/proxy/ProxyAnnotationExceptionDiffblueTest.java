package com.netflix.ribbon.proxy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class ProxyAnnotationExceptionDiffblueTest {
  /**
   * Test {@link ProxyAnnotationException#ProxyAnnotationException(String)}.
   *
   * <p>Method under test: {@link ProxyAnnotationException#ProxyAnnotationException(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProxyAnnotationException.<init>(String)"})
  public void testNewProxyAnnotationException() {
    // Arrange and Act
    ProxyAnnotationException actualProxyAnnotationException =
        new ProxyAnnotationException("An error occurred");

    // Assert
    assertEquals("An error occurred", actualProxyAnnotationException.getMessage());
    assertNull(actualProxyAnnotationException.getCause());
    assertEquals(0, actualProxyAnnotationException.getSuppressed().length);
  }
}
