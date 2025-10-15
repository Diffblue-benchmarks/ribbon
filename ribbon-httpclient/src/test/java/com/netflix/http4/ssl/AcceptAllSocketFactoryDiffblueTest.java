package com.netflix.http4.ssl;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.netflix.client.config.IClientConfig;
import com.netflix.client.config.IClientConfig.Builder;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import org.apache.http.conn.ssl.AllowAllHostnameVerifier;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.rules.ExpectedException;

public class AcceptAllSocketFactoryDiffblueTest {
  @Rule public ExpectedException thrown = ExpectedException.none();

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

  /**
   * Test {@link AcceptAllSocketFactory#initWithNiwsConfig(IClientConfig)} with {@code
   * clientConfig}.
   *
   * <ul>
   *   <li>Given {@code 42}.
   *   <li>Then throw {@link IllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link AcceptAllSocketFactory#initWithNiwsConfig(IClientConfig)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void AcceptAllSocketFactory.initWithNiwsConfig(IClientConfig)"})
  public void testInitWithNiwsConfigWithClientConfig_given42_thenThrowIllegalArgumentException()
      throws KeyManagementException, KeyStoreException, NoSuchAlgorithmException,
          UnrecoverableKeyException {
    // Arrange
    AcceptAllSocketFactory acceptAllSocketFactory = new AcceptAllSocketFactory();

    Builder newBuilderResult = Builder.newBuilder();
    newBuilderResult.withTrustStore("42");

    // Act and Assert
    thrown.expect(IllegalArgumentException.class);
    acceptAllSocketFactory.initWithNiwsConfig(
        newBuilderResult.ignoreUserTokenInConnectionPoolForSecureClient(true).build());
  }
}
