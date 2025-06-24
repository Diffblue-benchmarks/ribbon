package com.netflix.ribbon.guice;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.google.inject.spi.BindingTargetVisitor;
import com.google.inject.spi.ProviderInstanceBinding;
import com.netflix.ribbon.DefaultResourceFactory;
import com.netflix.ribbon.RibbonResourceFactory;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;

public class RibbonResourceProviderDiffblueTest {
  /**
   * Test {@link RibbonResourceProvider#get()}.
   * <ul>
   *   <li>Given {@link DefaultResourceFactory} {@link RibbonResourceFactory#from(Class)} return {@code From}.</li>
   *   <li>Then return {@code From}.</li>
   * </ul>
   * <p>
   * Method under test: {@link RibbonResourceProvider#get()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Object RibbonResourceProvider.get()"})
  public void testGet_givenDefaultResourceFactoryFromReturnFrom_thenReturnFrom() {
    // Arrange
    DefaultResourceFactory factory = mock(DefaultResourceFactory.class);
    when(factory.from(Mockito.<Class<Object>>any())).thenReturn("From");
    Class<Object> contract = Object.class;

    RibbonResourceProvider<Object> ribbonResourceProvider = new RibbonResourceProvider<>(contract);
    ribbonResourceProvider.initialize(factory);

    // Act
    Object actualGetResult = ribbonResourceProvider.get();

    // Assert
    verify(factory).from(isA(Class.class));
    assertEquals("From", actualGetResult);
  }

  /**
   * Test {@link RibbonResourceProvider#acceptExtensionVisitor(BindingTargetVisitor, ProviderInstanceBinding)}.
   * <p>
   * Method under test: {@link RibbonResourceProvider#acceptExtensionVisitor(BindingTargetVisitor, ProviderInstanceBinding)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "Object RibbonResourceProvider.acceptExtensionVisitor(BindingTargetVisitor, ProviderInstanceBinding)"})
  public void testAcceptExtensionVisitor() {
    // Arrange
    Class<Object> contract = Object.class;
    RibbonResourceProvider<Object> ribbonResourceProvider = new RibbonResourceProvider<>(contract);
    BindingTargetVisitor<Object, Object> visitor = mock(BindingTargetVisitor.class);
    when(visitor.visit(Mockito.<ProviderInstanceBinding<?>>any())).thenReturn("Visit");

    // Act
    Object actualAcceptExtensionVisitorResult = ribbonResourceProvider.acceptExtensionVisitor(visitor,
        mock(ProviderInstanceBinding.class));

    // Assert
    verify(visitor).visit(isA(ProviderInstanceBinding.class));
    assertEquals("Visit", actualAcceptExtensionVisitorResult);
  }
}
