package com.netflix.http4.ssl;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import org.apache.http.conn.ssl.AllowAllHostnameVerifier;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class KeyStoreAwareSocketFactoryDiffblueTest {
  /**
   * Test {@link KeyStoreAwareSocketFactory#KeyStoreAwareSocketFactory(X509HostnameVerifier)}.
   *
   * <p>Method under test: {@link
   * KeyStoreAwareSocketFactory#KeyStoreAwareSocketFactory(X509HostnameVerifier)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void KeyStoreAwareSocketFactory.<init>(X509HostnameVerifier)"})
  public void testNewKeyStoreAwareSocketFactory()
      throws KeyStoreException, NoSuchAlgorithmException {
    // Arrange and Act
    KeyStoreAwareSocketFactory actualKeyStoreAwareSocketFactory =
        new KeyStoreAwareSocketFactory(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

    // Assert
    X509HostnameVerifier hostnameVerifier = actualKeyStoreAwareSocketFactory.getHostnameVerifier();
    assertTrue(hostnameVerifier instanceof AllowAllHostnameVerifier);
    assertNull(actualKeyStoreAwareSocketFactory.getKeyStore());
    assertNull(actualKeyStoreAwareSocketFactory.getTrustStore());
    assertSame(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER, hostnameVerifier);
  }
}
