package com.netflix.ribbon;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import com.netflix.client.config.ClientConfigFactory;
import com.netflix.ribbon.proxy.processor.AnnotationProcessorsProvider;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DefaultResourceFactoryDiffblueTest {
  @Mock
  private AnnotationProcessorsProvider annotationProcessorsProvider;

  @Mock
  private ClientConfigFactory clientConfigFactory;

  @InjectMocks
  private DefaultResourceFactory defaultResourceFactory;

  @Mock
  private RibbonTransportFactory ribbonTransportFactory;

  /**
   * Method under test:
   * {@link DefaultResourceFactory#DefaultResourceFactory(ClientConfigFactory, RibbonTransportFactory, AnnotationProcessorsProvider)}
   */
  @Test
  public void testNewDefaultResourceFactory() {
    // Arrange
    ClientConfigFactory clientConfigFactory = mock(ClientConfigFactory.class);
    RibbonTransportFactory.DefaultRibbonTransportFactory transportFactory = new RibbonTransportFactory.DefaultRibbonTransportFactory(
        mock(ClientConfigFactory.class));

    // Act
    DefaultResourceFactory actualDefaultResourceFactory = new DefaultResourceFactory(clientConfigFactory,
        transportFactory, AnnotationProcessorsProvider.DEFAULT);

    // Assert
    assertSame(transportFactory, actualDefaultResourceFactory.getTransportFactory());
    assertSame(clientConfigFactory, actualDefaultResourceFactory.getClientConfigFactory());
  }

  /**
   * Method under test:
   * {@link DefaultResourceFactory#DefaultResourceFactory(ClientConfigFactory, RibbonTransportFactory)}
   */
  @Test
  public void testNewDefaultResourceFactory2() {
    // Arrange
    ClientConfigFactory clientConfigFactory2 = mock(ClientConfigFactory.class);

    // Act
    DefaultResourceFactory actualDefaultResourceFactory = new DefaultResourceFactory(clientConfigFactory2,
        ribbonTransportFactory);

    // Assert
    AnnotationProcessorsProvider annotationProcessorsProvider2 = actualDefaultResourceFactory.annotationProcessors;
    assertTrue(
        annotationProcessorsProvider2 instanceof AnnotationProcessorsProvider.DefaultAnnotationProcessorsProvider);
    assertTrue(annotationProcessorsProvider2.getProcessors().isEmpty());
    assertSame(ribbonTransportFactory, actualDefaultResourceFactory.getTransportFactory());
    assertSame(clientConfigFactory2, actualDefaultResourceFactory.getClientConfigFactory());
  }
}
