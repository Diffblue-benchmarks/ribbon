package com.netflix.ribbon;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.netflix.client.config.ClientConfigFactory;
import com.netflix.ribbon.RibbonTransportFactory.DefaultRibbonTransportFactory;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class RibbonResourceFactoryDiffblueTest {
  /**
   * Test {@link RibbonResourceFactory#getTransportFactory()}.
   *
   * <p>Method under test: {@link RibbonResourceFactory#getTransportFactory()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"RibbonTransportFactory RibbonResourceFactory.getTransportFactory()"})
  public void testGetTransportFactory() {
    // Arrange
    ClientConfigFactory clientConfigFactory = mock(ClientConfigFactory.class);
    DefaultRibbonTransportFactory transportFactory =
        new DefaultRibbonTransportFactory(mock(ClientConfigFactory.class));

    DefaultResourceFactory defaultResourceFactory =
        new DefaultResourceFactory(clientConfigFactory, transportFactory);

    // Act
    RibbonTransportFactory actualTransportFactory = defaultResourceFactory.getTransportFactory();

    // Assert
    assertTrue(actualTransportFactory instanceof DefaultRibbonTransportFactory);
    assertSame(defaultResourceFactory.transportFactory, actualTransportFactory);
  }
}
