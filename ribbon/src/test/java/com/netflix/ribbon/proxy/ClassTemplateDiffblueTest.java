package com.netflix.ribbon.proxy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import com.netflix.ribbon.http.HttpResourceGroup;
import org.junit.Test;

public class ClassTemplateDiffblueTest {
  /**
   * Method under test: {@link ClassTemplate#from(Class)}
   */
  @Test
  public void testFrom() {
    // Arrange
    Class<Object> clientInterface = Object.class;

    // Act
    ClassTemplate<Object> actualFromResult = ClassTemplate.from(clientInterface);

    // Assert
    assertNull(actualFromResult.getResourceGroupClass());
    assertNull(actualFromResult.getResourceGroupName());
    Class<Object> expectedClientInterface = Object.class;
    Class<Object> clientInterface2 = actualFromResult.getClientInterface();
    assertEquals(expectedClientInterface, clientInterface2);
    assertSame(clientInterface, clientInterface2);
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link ClassTemplate#getClientInterface()}
   *   <li>{@link ClassTemplate#getResourceGroupClass()}
   *   <li>{@link ClassTemplate#getResourceGroupName()}
   * </ul>
   */
  @Test
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
   * Method under test: {@link ClassTemplate#ClassTemplate(Class)}
   */
  @Test
  public void testNewClassTemplate() {
    // Arrange
    Class<Object> clientInterface = Object.class;

    // Act
    ClassTemplate<Object> actualClassTemplate = new ClassTemplate<>(clientInterface);

    // Assert
    assertNull(actualClassTemplate.getResourceGroupClass());
    assertNull(actualClassTemplate.getResourceGroupName());
    Class<Object> expectedClientInterface = Object.class;
    Class<Object> clientInterface2 = actualClassTemplate.getClientInterface();
    assertEquals(expectedClientInterface, clientInterface2);
    assertSame(clientInterface, clientInterface2);
  }
}
