package com.netflix.ribbon.http;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.netflix.client.config.ClientConfigFactory;
import com.netflix.ribbon.ClientOptions;
import com.netflix.ribbon.RibbonTransportFactory;
import com.netflix.ribbon.RibbonTransportFactory.DefaultRibbonTransportFactory;
import com.netflix.ribbon.http.HttpResourceGroup.Builder;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class HttpResourceGroupDiffblueTest {
  /**
   * Test Builder {@link Builder#withClientOptions(ClientOptions)}.
   * <p>
   * Method under test: {@link Builder#withClientOptions(ClientOptions)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Builder Builder.withClientOptions(ClientOptions)"})
  public void testBuilderWithClientOptions() {
    // Arrange
    ClientConfigFactory configFactory = mock(ClientConfigFactory.class);
    Builder newBuilderResult = Builder.newBuilder("https://example.org/example", configFactory,
        new DefaultRibbonTransportFactory(mock(ClientConfigFactory.class)));

    // Act and Assert
    assertSame(newBuilderResult, newBuilderResult.withClientOptions(ClientOptions.create()));
  }

  /**
   * Test Builder {@link Builder#withHeader(String, String)}.
   * <ul>
   *   <li>When {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Builder#withHeader(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Builder Builder.withHeader(String, String)"})
  public void testBuilderWithHeader_when42() {
    // Arrange
    ClientConfigFactory configFactory = mock(ClientConfigFactory.class);
    Builder newBuilderResult = Builder.newBuilder("https://example.org/example", configFactory,
        new DefaultRibbonTransportFactory(mock(ClientConfigFactory.class)));

    // Act and Assert
    assertSame(newBuilderResult, newBuilderResult.withHeader("42", "https://example.org/example"));
  }

  /**
   * Test Builder {@link Builder#withHeader(String, String)}.
   * <ul>
   *   <li>When empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link Builder#withHeader(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Builder Builder.withHeader(String, String)"})
  public void testBuilderWithHeader_whenEmptyString() {
    // Arrange
    ClientConfigFactory configFactory = mock(ClientConfigFactory.class);
    Builder newBuilderResult = Builder.newBuilder("https://example.org/example", configFactory,
        new DefaultRibbonTransportFactory(mock(ClientConfigFactory.class)));

    // Act and Assert
    assertSame(newBuilderResult, newBuilderResult.withHeader("", "https://example.org/example"));
  }

  /**
   * Test Builder {@link Builder#withHeader(String, String)}.
   * <ul>
   *   <li>When {@code Name}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Builder#withHeader(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Builder Builder.withHeader(String, String)"})
  public void testBuilderWithHeader_whenName() {
    // Arrange
    ClientConfigFactory configFactory = mock(ClientConfigFactory.class);
    Builder newBuilderResult = Builder.newBuilder("https://example.org/example", configFactory,
        new DefaultRibbonTransportFactory(mock(ClientConfigFactory.class)));

    // Act and Assert
    assertSame(newBuilderResult, newBuilderResult.withHeader("Name", "https://example.org/example"));
  }
}
