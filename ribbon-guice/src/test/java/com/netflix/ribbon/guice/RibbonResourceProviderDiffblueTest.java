package com.netflix.ribbon.guice;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.google.inject.spi.BindingTargetVisitor;
import com.google.inject.spi.ProviderInstanceBinding;
import com.netflix.ribbon.DefaultResourceFactory;
import org.junit.Test;
import org.mockito.Mockito;

public class RibbonResourceProviderDiffblueTest {
  /**
   * Method under test: {@link RibbonResourceProvider#get()}
   */
  @Test
  public void testGet() {
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
   * Method under test:
   * {@link RibbonResourceProvider#acceptExtensionVisitor(BindingTargetVisitor, ProviderInstanceBinding)}
   */
  @Test
  public void testAcceptExtensionVisitor() {
    // Arrange
    Class<Object> contract = Object.class;
    RibbonResourceProvider<Object> ribbonResourceProvider = new RibbonResourceProvider<>(contract);
    BindingTargetVisitor<Object, Object> visitor = mock(BindingTargetVisitor.class);
    when(visitor.visit(Mockito.<ProviderInstanceBinding<Object>>any())).thenReturn("Visit");

    // Act
    Object actualAcceptExtensionVisitorResult = ribbonResourceProvider.acceptExtensionVisitor(visitor,
        mock(ProviderInstanceBinding.class));

    // Assert
    verify(visitor).visit(isA(ProviderInstanceBinding.class));
    assertEquals("Visit", actualAcceptExtensionVisitorResult);
  }
}
