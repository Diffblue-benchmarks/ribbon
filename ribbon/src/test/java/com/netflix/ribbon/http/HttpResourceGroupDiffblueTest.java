package com.netflix.ribbon.http;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
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
   *
   * <p>Method under test: {@link Builder#withClientOptions(ClientOptions)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Builder Builder.withClientOptions(ClientOptions)"})
  public void testBuilderWithClientOptions() {
    // Arrange
    ClientConfigFactory configFactory = mock(ClientConfigFactory.class);
    Builder newBuilderResult =
        Builder.newBuilder(
            "https://example.org/example",
            configFactory,
            new DefaultRibbonTransportFactory(mock(ClientConfigFactory.class)));

    // Act
    Builder actualWithClientOptionsResult =
        newBuilderResult.withClientOptions(ClientOptions.create());

    // Assert
    assertSame(newBuilderResult, actualWithClientOptionsResult);
  }

  /**
   * Test Builder {@link Builder#withHeader(String, String)}.
   *
   * <ul>
   *   <li>When {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link Builder#withHeader(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Builder Builder.withHeader(String, String)"})
  public void testBuilderWithHeader_when42() {
    // Arrange
    ClientConfigFactory configFactory = mock(ClientConfigFactory.class);
    Builder newBuilderResult =
        Builder.newBuilder(
            "https://example.org/example",
            configFactory,
            new DefaultRibbonTransportFactory(mock(ClientConfigFactory.class)));

    // Act
    Builder actualWithHeaderResult =
        newBuilderResult.withHeader("42", "https://example.org/example");

    // Assert
    assertSame(newBuilderResult, actualWithHeaderResult);
  }

  /**
   * Test Builder {@link Builder#withHeader(String, String)}.
   *
   * <ul>
   *   <li>When empty string.
   * </ul>
   *
   * <p>Method under test: {@link Builder#withHeader(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Builder Builder.withHeader(String, String)"})
  public void testBuilderWithHeader_whenEmptyString() {
    // Arrange
    ClientConfigFactory configFactory = mock(ClientConfigFactory.class);
    Builder newBuilderResult =
        Builder.newBuilder(
            "https://example.org/example",
            configFactory,
            new DefaultRibbonTransportFactory(mock(ClientConfigFactory.class)));

    // Act
    Builder actualWithHeaderResult = newBuilderResult.withHeader("", "https://example.org/example");

    // Assert
    assertSame(newBuilderResult, actualWithHeaderResult);
  }

  /**
   * Test Builder {@link Builder#withHeader(String, String)}.
   *
   * <ul>
   *   <li>When {@code Name}.
   * </ul>
   *
   * <p>Method under test: {@link Builder#withHeader(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Builder Builder.withHeader(String, String)"})
  public void testBuilderWithHeader_whenName() {
    // Arrange
    ClientConfigFactory configFactory = mock(ClientConfigFactory.class);
    Builder newBuilderResult =
        Builder.newBuilder(
            "https://example.org/example",
            configFactory,
            new DefaultRibbonTransportFactory(mock(ClientConfigFactory.class)));

    // Act
    Builder actualWithHeaderResult =
        newBuilderResult.withHeader("Name", "https://example.org/example");

    // Assert
    assertSame(newBuilderResult, actualWithHeaderResult);
  }
}
