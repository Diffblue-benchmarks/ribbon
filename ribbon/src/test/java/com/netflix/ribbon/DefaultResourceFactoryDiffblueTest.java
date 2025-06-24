package com.netflix.ribbon;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.netflix.client.config.ClientConfigFactory;
import com.netflix.ribbon.RibbonTransportFactory.DefaultRibbonTransportFactory;
import com.netflix.ribbon.proxy.processor.AnnotationProcessorsProvider;
import com.netflix.ribbon.proxy.processor.AnnotationProcessorsProvider.DefaultAnnotationProcessorsProvider;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DefaultResourceFactoryDiffblueTest {
  @Mock
  private RibbonTransportFactory ribbonTransportFactory;

  /**
   * Test {@link DefaultResourceFactory#DefaultResourceFactory(ClientConfigFactory, RibbonTransportFactory, AnnotationProcessorsProvider)}.
   * <p>
   * Method under test: {@link DefaultResourceFactory#DefaultResourceFactory(ClientConfigFactory, RibbonTransportFactory, AnnotationProcessorsProvider)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "void DefaultResourceFactory.<init>(ClientConfigFactory, RibbonTransportFactory, AnnotationProcessorsProvider)"})
  public void testNewDefaultResourceFactory() {
    // Arrange
    ClientConfigFactory clientConfigFactory = mock(ClientConfigFactory.class);
    DefaultRibbonTransportFactory transportFactory = new DefaultRibbonTransportFactory(mock(ClientConfigFactory.class));

    // Act
    DefaultResourceFactory actualDefaultResourceFactory = new DefaultResourceFactory(clientConfigFactory,
        transportFactory, AnnotationProcessorsProvider.DEFAULT);

    // Assert
    assertSame(transportFactory, actualDefaultResourceFactory.getTransportFactory());
    assertSame(clientConfigFactory, actualDefaultResourceFactory.getClientConfigFactory());
  }

  /**
   * Test {@link DefaultResourceFactory#DefaultResourceFactory(ClientConfigFactory, RibbonTransportFactory)}.
   * <p>
   * Method under test: {@link DefaultResourceFactory#DefaultResourceFactory(ClientConfigFactory, RibbonTransportFactory)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultResourceFactory.<init>(ClientConfigFactory, RibbonTransportFactory)"})
  public void testNewDefaultResourceFactory2() {
    // Arrange
    ClientConfigFactory clientConfigFactory = mock(ClientConfigFactory.class);

    // Act
    DefaultResourceFactory actualDefaultResourceFactory = new DefaultResourceFactory(clientConfigFactory,
        ribbonTransportFactory);

    // Assert
    AnnotationProcessorsProvider annotationProcessorsProvider = actualDefaultResourceFactory.annotationProcessors;
    assertTrue(annotationProcessorsProvider instanceof DefaultAnnotationProcessorsProvider);
    assertTrue(annotationProcessorsProvider.getProcessors().isEmpty());
    assertSame(ribbonTransportFactory, actualDefaultResourceFactory.getTransportFactory());
    assertSame(clientConfigFactory, actualDefaultResourceFactory.getClientConfigFactory());
  }
}
