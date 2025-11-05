package com.netflix.ribbon.http;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import com.netflix.client.config.ClientConfigFactory;
import com.netflix.ribbon.ClientOptions;
import com.netflix.ribbon.RibbonTransportFactory;
import org.junit.Test;

public class HttpResourceGroupDiffblueTest {
  /**
   * Method under test:
   * {@link HttpResourceGroup.Builder#withClientOptions(ClientOptions)}
   */
  @Test
  public void testBuilderWithClientOptions() {
    // Arrange
    ClientConfigFactory configFactory = mock(ClientConfigFactory.class);
    HttpResourceGroup.Builder newBuilderResult = HttpResourceGroup.Builder.newBuilder("https://example.org/example",
        configFactory, new RibbonTransportFactory.DefaultRibbonTransportFactory(mock(ClientConfigFactory.class)));

    // Act and Assert
    assertSame(newBuilderResult, newBuilderResult.withClientOptions(ClientOptions.create()));
  }

  /**
   * Method under test:
   * {@link HttpResourceGroup.Builder#withHeader(String, String)}
   */
  @Test
  public void testBuilderWithHeader() {
    // Arrange
    ClientConfigFactory configFactory = mock(ClientConfigFactory.class);
    HttpResourceGroup.Builder newBuilderResult = HttpResourceGroup.Builder.newBuilder("https://example.org/example",
        configFactory, new RibbonTransportFactory.DefaultRibbonTransportFactory(mock(ClientConfigFactory.class)));

    // Act and Assert
    assertSame(newBuilderResult, newBuilderResult.withHeader("Name", "https://example.org/example"));
  }

  /**
   * Method under test:
   * {@link HttpResourceGroup.Builder#withHeader(String, String)}
   */
  @Test
  public void testBuilderWithHeader2() {
    // Arrange
    ClientConfigFactory configFactory = mock(ClientConfigFactory.class);
    HttpResourceGroup.Builder newBuilderResult = HttpResourceGroup.Builder.newBuilder("https://example.org/example",
        configFactory, new RibbonTransportFactory.DefaultRibbonTransportFactory(mock(ClientConfigFactory.class)));

    // Act and Assert
    assertSame(newBuilderResult, newBuilderResult.withHeader("42", "https://example.org/example"));
  }

  /**
   * Method under test:
   * {@link HttpResourceGroup.Builder#withHeader(String, String)}
   */
  @Test
  public void testBuilderWithHeader3() {
    // Arrange
    ClientConfigFactory configFactory = mock(ClientConfigFactory.class);
    HttpResourceGroup.Builder newBuilderResult = HttpResourceGroup.Builder.newBuilder("https://example.org/example",
        configFactory, new RibbonTransportFactory.DefaultRibbonTransportFactory(mock(ClientConfigFactory.class)));

    // Act and Assert
    assertSame(newBuilderResult, newBuilderResult.withHeader("", "https://example.org/example"));
  }
}
