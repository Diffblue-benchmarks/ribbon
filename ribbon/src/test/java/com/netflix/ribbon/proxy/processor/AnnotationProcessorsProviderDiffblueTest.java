package com.netflix.ribbon.proxy.processor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.netflix.ribbon.proxy.processor.AnnotationProcessorsProvider.DefaultAnnotationProcessorsProvider;
import java.util.List;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class AnnotationProcessorsProviderDiffblueTest {
  /**
   * Test DefaultAnnotationProcessorsProvider {@link DefaultAnnotationProcessorsProvider#DefaultAnnotationProcessorsProvider()}.
   * <p>
   * Method under test: default or parameterless constructor of {@link DefaultAnnotationProcessorsProvider}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultAnnotationProcessorsProvider.<init>()"})
  public void testDefaultAnnotationProcessorsProviderNewDefaultAnnotationProcessorsProvider() {
    // Arrange, Act and Assert
    assertTrue((new DefaultAnnotationProcessorsProvider()).getProcessors().isEmpty());
  }

  /**
   * Test {@link AnnotationProcessorsProvider#register(AnnotationProcessor)}.
   * <p>
   * Method under test: {@link AnnotationProcessorsProvider#register(AnnotationProcessor)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void AnnotationProcessorsProvider.register(AnnotationProcessor)"})
  public void testRegister() {
    // Arrange
    DefaultAnnotationProcessorsProvider defaultAnnotationProcessorsProvider = new DefaultAnnotationProcessorsProvider();
    CacheProviderAnnotationProcessor processor = new CacheProviderAnnotationProcessor();

    // Act
    defaultAnnotationProcessorsProvider.register(processor);

    // Assert
    List<AnnotationProcessor> processors = defaultAnnotationProcessorsProvider.getProcessors();
    assertEquals(1, processors.size());
    assertSame(processor, processors.get(0));
  }

  /**
   * Test {@link AnnotationProcessorsProvider#getProcessors()}.
   * <p>
   * Method under test: {@link AnnotationProcessorsProvider#getProcessors()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List AnnotationProcessorsProvider.getProcessors()"})
  public void testGetProcessors() {
    // Arrange, Act and Assert
    assertTrue((new DefaultAnnotationProcessorsProvider()).getProcessors().isEmpty());
  }
}
