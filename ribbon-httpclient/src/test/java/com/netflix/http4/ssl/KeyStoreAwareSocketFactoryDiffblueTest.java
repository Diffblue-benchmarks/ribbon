package com.netflix.http4.ssl;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import org.apache.http.conn.ssl.AllowAllHostnameVerifier;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class KeyStoreAwareSocketFactoryDiffblueTest {
  /**
   * Test {@link KeyStoreAwareSocketFactory#KeyStoreAwareSocketFactory(X509HostnameVerifier)}.
   * <p>
   * Method under test: {@link KeyStoreAwareSocketFactory#KeyStoreAwareSocketFactory(X509HostnameVerifier)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void KeyStoreAwareSocketFactory.<init>(X509HostnameVerifier)"})
  public void testNewKeyStoreAwareSocketFactory() throws KeyStoreException, NoSuchAlgorithmException {
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
