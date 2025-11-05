package com.netflix.http4.ssl;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import com.netflix.client.ssl.AbstractSslContextFactory;
import com.netflix.client.ssl.ClientSslSocketFactoryException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import org.apache.http.conn.ssl.AllowAllHostnameVerifier;
import org.apache.http.conn.ssl.BrowserCompatHostnameVerifier;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.junit.Test;

public class KeyStoreAwareSocketFactoryDiffblueTest {
  /**
   * Method under test:
   * {@link KeyStoreAwareSocketFactory#KeyStoreAwareSocketFactory(AbstractSslContextFactory)}
   */
  @Test
  public void testNewKeyStoreAwareSocketFactory() throws ClientSslSocketFactoryException, NoSuchAlgorithmException {
    // Arrange and Act
    KeyStoreAwareSocketFactory actualKeyStoreAwareSocketFactory = new KeyStoreAwareSocketFactory(
        (AbstractSslContextFactory) null);

    // Assert
    X509HostnameVerifier hostnameVerifier = actualKeyStoreAwareSocketFactory.getHostnameVerifier();
    assertTrue(hostnameVerifier instanceof BrowserCompatHostnameVerifier);
    assertNull(actualKeyStoreAwareSocketFactory.getKeyStore());
    assertNull(actualKeyStoreAwareSocketFactory.getTrustStore());
    assertSame(actualKeyStoreAwareSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER, hostnameVerifier);
  }

  /**
   * Method under test:
   * {@link KeyStoreAwareSocketFactory#KeyStoreAwareSocketFactory(AbstractSslContextFactory, X509HostnameVerifier)}
   */
  @Test
  public void testNewKeyStoreAwareSocketFactory2() throws ClientSslSocketFactoryException, NoSuchAlgorithmException {
    // Arrange
    AllowAllHostnameVerifier hostnameVerifier = new AllowAllHostnameVerifier();

    // Act
    KeyStoreAwareSocketFactory actualKeyStoreAwareSocketFactory = new KeyStoreAwareSocketFactory(null,
        hostnameVerifier);

    // Assert
    X509HostnameVerifier hostnameVerifier2 = actualKeyStoreAwareSocketFactory.getHostnameVerifier();
    assertTrue(hostnameVerifier2 instanceof AllowAllHostnameVerifier);
    assertNull(actualKeyStoreAwareSocketFactory.getKeyStore());
    assertNull(actualKeyStoreAwareSocketFactory.getTrustStore());
    assertSame(hostnameVerifier, hostnameVerifier2);
  }

  /**
   * Method under test:
   * {@link KeyStoreAwareSocketFactory#KeyStoreAwareSocketFactory(X509HostnameVerifier)}
   */
  @Test
  public void testNewKeyStoreAwareSocketFactory3() throws KeyStoreException, NoSuchAlgorithmException {
    // Arrange
    AllowAllHostnameVerifier hostnameVerifier = new AllowAllHostnameVerifier();

    // Act
    KeyStoreAwareSocketFactory actualKeyStoreAwareSocketFactory = new KeyStoreAwareSocketFactory(hostnameVerifier);

    // Assert
    X509HostnameVerifier hostnameVerifier2 = actualKeyStoreAwareSocketFactory.getHostnameVerifier();
    assertTrue(hostnameVerifier2 instanceof AllowAllHostnameVerifier);
    assertNull(actualKeyStoreAwareSocketFactory.getKeyStore());
    assertNull(actualKeyStoreAwareSocketFactory.getTrustStore());
    assertSame(hostnameVerifier, hostnameVerifier2);
  }
}
