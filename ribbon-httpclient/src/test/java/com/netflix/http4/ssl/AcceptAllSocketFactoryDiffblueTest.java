package com.netflix.http4.ssl;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import org.apache.http.conn.ssl.AllowAllHostnameVerifier;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class AcceptAllSocketFactoryDiffblueTest {
  /**
   * Test {@link AcceptAllSocketFactory#AcceptAllSocketFactory()}.
   *
   * <p>Method under test: default or parameterless constructor of {@link AcceptAllSocketFactory}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void AcceptAllSocketFactory.<init>()"})
  public void testNewAcceptAllSocketFactory()
      throws KeyManagementException, KeyStoreException, NoSuchAlgorithmException,
          UnrecoverableKeyException {
    // Arrange, Act and Assert
    X509HostnameVerifier hostnameVerifier = new AcceptAllSocketFactory().getHostnameVerifier();
    assertTrue(hostnameVerifier instanceof AllowAllHostnameVerifier);
    assertSame(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER, hostnameVerifier);
  }
}
