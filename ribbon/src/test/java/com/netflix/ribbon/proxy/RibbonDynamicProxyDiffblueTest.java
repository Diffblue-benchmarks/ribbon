package com.netflix.ribbon.proxy;

import static org.mockito.Mockito.mock;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.netflix.ribbon.http.HttpResourceGroup;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.rules.ExpectedException;

public class RibbonDynamicProxyDiffblueTest {
  @Rule public ExpectedException thrown = ExpectedException.none();

  /**
   * Test {@link RibbonDynamicProxy#newInstance(Class, HttpResourceGroup)} with {@code
   * clientInterface}, {@code httpResourceGroup}.
   *
   * <p>Method under test: {@link RibbonDynamicProxy#newInstance(Class, HttpResourceGroup)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object RibbonDynamicProxy.newInstance(Class, HttpResourceGroup)"})
  public void testNewInstanceWithClientInterfaceHttpResourceGroup() {
    // Arrange
    Class<Object> clientInterface = Object.class;

    // Act and Assert
    thrown.expect(IllegalArgumentException.class);
    RibbonDynamicProxy.newInstance(clientInterface, mock(HttpResourceGroup.class));
  }

  /**
   * Test {@link RibbonDynamicProxy#newInstance(Class)} with {@code clientInterface}.
   *
   * <ul>
   *   <li>Then throw {@link IllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link RibbonDynamicProxy#newInstance(Class)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object RibbonDynamicProxy.newInstance(Class)"})
  public void testNewInstanceWithClientInterface_thenThrowIllegalArgumentException() {
    // Arrange
    Class<Object> clientInterface = Object.class;

    // Act and Assert
    thrown.expect(IllegalArgumentException.class);
    RibbonDynamicProxy.newInstance(clientInterface);
  }
}
