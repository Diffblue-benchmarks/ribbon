package com.netflix.ribbon.proxy.processor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import java.util.List;
import org.junit.Test;

public class AnnotationProcessorsProviderDiffblueTest {
  /**
   * Method under test: default or parameterless constructor of
   * {@link AnnotationProcessorsProvider.DefaultAnnotationProcessorsProvider}
   */
  @Test
  public void testDefaultAnnotationProcessorsProviderNewDefaultAnnotationProcessorsProvider() {
    // Arrange, Act and Assert
    assertTrue((new AnnotationProcessorsProvider.DefaultAnnotationProcessorsProvider()).getProcessors().isEmpty());
  }

  /**
   * Method under test:
   * {@link AnnotationProcessorsProvider#register(AnnotationProcessor)}
   */
  @Test
  public void testRegister() {
    // Arrange
    AnnotationProcessorsProvider.DefaultAnnotationProcessorsProvider defaultAnnotationProcessorsProvider = new AnnotationProcessorsProvider.DefaultAnnotationProcessorsProvider();
    CacheProviderAnnotationProcessor processor = new CacheProviderAnnotationProcessor();

    // Act
    defaultAnnotationProcessorsProvider.register(processor);

    // Assert
    List<AnnotationProcessor> processors = defaultAnnotationProcessorsProvider.getProcessors();
    assertEquals(1, processors.size());
    assertSame(processor, processors.get(0));
  }

  /**
   * Method under test: {@link AnnotationProcessorsProvider#getProcessors()}
   */
  @Test
  public void testGetProcessors() {
    // Arrange, Act and Assert
    assertTrue((new AnnotationProcessorsProvider.DefaultAnnotationProcessorsProvider()).getProcessors().isEmpty());
  }
}
