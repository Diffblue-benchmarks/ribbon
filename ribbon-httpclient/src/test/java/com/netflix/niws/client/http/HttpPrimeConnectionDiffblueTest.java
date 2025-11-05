package com.netflix.niws.client.http;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.netflix.client.config.IClientConfig;
import org.junit.Test;

public class HttpPrimeConnectionDiffblueTest {
  /**
   * Method under test:
   * {@link HttpPrimeConnection#initWithNiwsConfig(IClientConfig)}
   */
  @Test
  public void testInitWithNiwsConfig() {
    // Arrange
    HttpPrimeConnection httpPrimeConnection = new HttpPrimeConnection();
    IClientConfig niwsClientConfig = mock(IClientConfig.class);
    when(niwsClientConfig.getClientName()).thenReturn("Dr Jane Doe");

    // Act
    httpPrimeConnection.initWithNiwsConfig(niwsClientConfig);

    // Assert
    verify(niwsClientConfig).getClientName();
  }

  /**
   * Method under test:
   * {@link HttpPrimeConnection#initWithNiwsConfig(IClientConfig)}
   */
  @Test
  public void testInitWithNiwsConfig2() {
    // Arrange
    HttpPrimeConnection httpPrimeConnection = new HttpPrimeConnection();
    IClientConfig niwsClientConfig = mock(IClientConfig.class);
    when(niwsClientConfig.getClientName()).thenReturn("-PrimeConnsClient");

    // Act
    httpPrimeConnection.initWithNiwsConfig(niwsClientConfig);

    // Assert
    verify(niwsClientConfig).getClientName();
  }
}
