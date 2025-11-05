package com.netflix.ribbon.proxy;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import com.netflix.client.config.ClientConfigFactory;
import com.netflix.ribbon.RibbonResourceFactory;
import com.netflix.ribbon.RibbonTransportFactory;
import com.netflix.ribbon.http.HttpResourceGroup;
import com.netflix.ribbon.proxy.processor.AnnotationProcessorsProvider;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class RibbonDynamicProxyDiffblueTest {
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  /**
   * Method under test:
   * {@link RibbonDynamicProxy#registerAnnotationProcessors(AnnotationProcessorsProvider)}
   */
  @Test
  public void testRegisterAnnotationProcessors() {
    // Arrange
    AnnotationProcessorsProvider processors = AnnotationProcessorsProvider.DEFAULT;

    // Act
    RibbonDynamicProxy.registerAnnotationProcessors(processors);

    // Assert
    assertTrue(processors instanceof AnnotationProcessorsProvider.DefaultAnnotationProcessorsProvider);
  }

  /**
   * Method under test: {@link RibbonDynamicProxy#newInstance(Class)}
   */
  @Test
  public void testNewInstance() {
    // Arrange
    Class<Object> clientInterface = Object.class;

    // Act and Assert
    thrown.expect(IllegalArgumentException.class);
    RibbonDynamicProxy.newInstance(clientInterface);
  }

  /**
   * Method under test:
   * {@link RibbonDynamicProxy#newInstance(Class, RibbonResourceFactory, ClientConfigFactory, RibbonTransportFactory)}
   */
  @Test
  public void testNewInstance2() {
    // Arrange
    Class<Object> clientInterface = Object.class;
    ClientConfigFactory configFactory = mock(ClientConfigFactory.class);

    // Act and Assert
    thrown.expect(IllegalArgumentException.class);
    RibbonDynamicProxy.newInstance(clientInterface, RibbonResourceFactory.DEFAULT, configFactory,
        new RibbonTransportFactory.DefaultRibbonTransportFactory(mock(ClientConfigFactory.class)));
  }

  /**
   * Method under test:
   * {@link RibbonDynamicProxy#newInstance(Class, RibbonResourceFactory, ClientConfigFactory, RibbonTransportFactory, AnnotationProcessorsProvider)}
   */
  @Test
  public void testNewInstance3() {
    // Arrange
    Class<Object> clientInterface = Object.class;
    ClientConfigFactory configFactory = mock(ClientConfigFactory.class);

    // Act and Assert
    thrown.expect(IllegalArgumentException.class);
    RibbonDynamicProxy.newInstance(clientInterface, RibbonResourceFactory.DEFAULT, configFactory,
        new RibbonTransportFactory.DefaultRibbonTransportFactory(mock(ClientConfigFactory.class)),
        AnnotationProcessorsProvider.DEFAULT);
  }

  /**
   * Method under test:
   * {@link RibbonDynamicProxy#newInstance(Class, HttpResourceGroup)}
   */
  @Test
  public void testNewInstance4() {
    // Arrange
    Class<Object> clientInterface = Object.class;

    // Act and Assert
    thrown.expect(IllegalArgumentException.class);
    RibbonDynamicProxy.newInstance(clientInterface, mock(HttpResourceGroup.class));
  }
}
