package com.netflix.ribbon.proxy;

import static org.junit.Assert.assertNull;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class UtilsDiffblueTest {
  /**
   * Test {@link Utils#methodByName(Class, String)}.
   * <ul>
   *   <li>When {@code Object}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Utils#methodByName(Class, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"java.lang.reflect.Method Utils.methodByName(Class, String)"})
  public void testMethodByName_whenJavaLangObject_thenReturnNull() {
    // Arrange
    Class<Object> aClass = Object.class;

    // Act and Assert
    assertNull(Utils.methodByName(aClass, "Name"));
  }
}
