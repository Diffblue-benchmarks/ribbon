package com.netflix.http4.ssl;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.netflix.client.config.IClientConfig;
import com.netflix.client.config.IClientConfigKey;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import org.apache.http.conn.ssl.AllowAllHostnameVerifier;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;

public class AcceptAllSocketFactoryDiffblueTest {
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  /**
   * Method under test:
   * {@link AcceptAllSocketFactory#initWithNiwsConfig(IClientConfig)}
   */
  @Test
  public void testInitWithNiwsConfig()
      throws KeyManagementException, KeyStoreException, NoSuchAlgorithmException, UnrecoverableKeyException {
    // Arrange
    AcceptAllSocketFactory acceptAllSocketFactory = new AcceptAllSocketFactory();
    IClientConfig clientConfig = mock(IClientConfig.class);
    when(clientConfig.getOrDefault(Mockito.<IClientConfigKey<String>>any())).thenReturn("Or Default");

    // Act and Assert
    thrown.expect(IllegalArgumentException.class);
    acceptAllSocketFactory.initWithNiwsConfig(clientConfig);
    verify(clientConfig).getOrDefault(isA(IClientConfigKey.class));
  }

  /**
   * Method under test:
   * {@link AcceptAllSocketFactory#initWithNiwsConfig(IClientConfig)}
   */
  @Test
  public void testInitWithNiwsConfig2()
      throws KeyManagementException, KeyStoreException, NoSuchAlgorithmException, UnrecoverableKeyException {
    // Arrange
    AcceptAllSocketFactory acceptAllSocketFactory = new AcceptAllSocketFactory();
    IClientConfig clientConfig = mock(IClientConfig.class);
    when(clientConfig.getOrDefault(Mockito.<IClientConfigKey<String>>any()))
        .thenThrow(new IllegalArgumentException("foo"));

    // Act and Assert
    thrown.expect(IllegalArgumentException.class);
    acceptAllSocketFactory.initWithNiwsConfig(clientConfig);
    verify(clientConfig).getOrDefault(isA(IClientConfigKey.class));
  }

  /**
   * Method under test: default or parameterless constructor of
   * {@link AcceptAllSocketFactory}
   */
  @Test
  public void testNewAcceptAllSocketFactory()
      throws KeyManagementException, KeyStoreException, NoSuchAlgorithmException, UnrecoverableKeyException {
    // Arrange and Act
    AcceptAllSocketFactory actualAcceptAllSocketFactory = new AcceptAllSocketFactory();

    // Assert
    X509HostnameVerifier hostnameVerifier = actualAcceptAllSocketFactory.getHostnameVerifier();
    assertTrue(hostnameVerifier instanceof AllowAllHostnameVerifier);
    assertSame(actualAcceptAllSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER, hostnameVerifier);
  }
}
