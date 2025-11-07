package com.netflix.ribbon.proxy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.netflix.ribbon.http.HttpResourceGroup;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class ClassTemplateDiffblueTest {
  /**
   * Test {@link ClassTemplate#ClassTemplate(Class)}.
   * <ul>
   *   <li>When {@code Object}.</li>
   *   <li>Then return ResourceGroupClass is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ClassTemplate#ClassTemplate(Class)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ClassTemplate.<init>(Class)"})
  public void testNewClassTemplate_whenJavaLangObject_thenReturnResourceGroupClassIsNull() {
    // Arrange
    Class<Object> clientInterface = Object.class;

    // Act
    ClassTemplate<Object> actualClassTemplate = new ClassTemplate<>(clientInterface);

    // Assert
    assertNull(actualClassTemplate.getResourceGroupClass());
    assertNull(actualClassTemplate.getResourceGroupName());
    Class<Object> expectedClientInterface = Object.class;
    assertEquals(expectedClientInterface, actualClassTemplate.getClientInterface());
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ClassTemplate#getClientInterface()}
   *   <li>{@link ClassTemplate#getResourceGroupClass()}
   *   <li>{@link ClassTemplate#getResourceGroupName()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Class ClassTemplate.getClientInterface()", "Class ClassTemplate.getResourceGroupClass()",
      "java.lang.String ClassTemplate.getResourceGroupName()"})
  public void testGettersAndSetters() {
    // Arrange
    Class<Object> clientInterface = Object.class;
    ClassTemplate<Object> fromResult = ClassTemplate.from(clientInterface);

    // Act
    Class<Object> actualClientInterface = fromResult.getClientInterface();
    Class<? extends HttpResourceGroup> actualResourceGroupClass = fromResult.getResourceGroupClass();

    // Assert
    assertNull(actualResourceGroupClass);
    assertNull(fromResult.getResourceGroupName());
    Class<Object> expectedClientInterface = Object.class;
    assertEquals(expectedClientInterface, actualClientInterface);
    assertSame(clientInterface, actualClientInterface);
  }

  /**
   * Test {@link ClassTemplate#from(Class)}.
   * <ul>
   *   <li>When {@code Object}.</li>
   *   <li>Then return ResourceGroupClass is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ClassTemplate#from(Class)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ClassTemplate ClassTemplate.from(Class)"})
  public void testFrom_whenJavaLangObject_thenReturnResourceGroupClassIsNull() {
    // Arrange
    Class<Object> clientInterface = Object.class;

    // Act
    ClassTemplate<Object> actualFromResult = ClassTemplate.from(clientInterface);

    // Assert
    assertNull(actualFromResult.getResourceGroupClass());
    assertNull(actualFromResult.getResourceGroupName());
    Class<Object> expectedClientInterface = Object.class;
    assertEquals(expectedClientInterface, actualFromResult.getClientInterface());
  }
}
