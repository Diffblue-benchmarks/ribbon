package com.netflix.ribbon.proxy;

import static org.junit.Assert.assertNull;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class UtilsDiffblueTest {
  /**
   * Test {@link Utils#methodByName(Class, String)}.
   *
   * <ul>
   *   <li>When {@code Object}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link Utils#methodByName(Class, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"java.lang.reflect.Method Utils.methodByName(Class, String)"})
  public void testMethodByName_whenJavaLangObject_thenReturnNull() {
    // Arrange
    Class<Object> aClass = Object.class;

    // Act and Assert
    assertNull(Utils.methodByName(aClass, "Name"));
  }
}
