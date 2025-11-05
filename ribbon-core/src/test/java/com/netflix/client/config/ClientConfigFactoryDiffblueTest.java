package com.netflix.client.config;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.junit.Test;

public class ClientConfigFactoryDiffblueTest {
  /**
   * Method under test: {@link ClientConfigFactory#getPriority()}
   */
  @Test
  public void testGetPriority() {
    // Arrange, Act and Assert
    assertEquals(0, (new ArchaiusClientConfigFactory()).getPriority());
  }

  /**
   * Method under test: {@link ClientConfigFactory#getPriority()}
   */
  @Test
  public void testGetPriority2() {
    // Arrange
    ClientConfigFactory clientConfigFactory = mock(ClientConfigFactory.class);
    when(clientConfigFactory.getPriority()).thenReturn(1);

    // Act
    clientConfigFactory.getPriority();

    // Assert
    verify(clientConfigFactory).getPriority();
  }
}
