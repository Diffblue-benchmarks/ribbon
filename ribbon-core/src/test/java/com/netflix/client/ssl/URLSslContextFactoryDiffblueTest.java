package com.netflix.client.ssl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;
import java.security.Provider;
import javax.net.ssl.SSLContext;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.rules.ExpectedException;

public class URLSslContextFactoryDiffblueTest {
  @Rule public ExpectedException thrown = ExpectedException.none();

  /**
   * Test {@link URLSslContextFactory#URLSslContextFactory(URL, String, URL, String)}.
   *
   * <ul>
   *   <li>Then return TrustStorePasswordLength is minus one.
   * </ul>
   *
   * <p>Method under test: {@link URLSslContextFactory#URLSslContextFactory(URL, String, URL,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void URLSslContextFactory.<init>(URL, String, URL, String)"})
  public void testNewURLSslContextFactory_thenReturnTrustStorePasswordLengthIsMinusOne()
      throws ClientSslSocketFactoryException {
    // Arrange and Act
    URLSslContextFactory actualUrlSslContextFactory =
        new URLSslContextFactory(null, null, null, "not empty");

    // Assert
    assertNull(actualUrlSslContextFactory.getKeyStore());
    assertNull(actualUrlSslContextFactory.getTrustStore());
    assertEquals(-1, actualUrlSslContextFactory.getTrustStorePasswordLength());
    SSLContext sSLContext = actualUrlSslContextFactory.getSSLContext();
    Provider provider = sSLContext.getProvider();
    assertEquals(39, provider.size());
    assertEquals(9, actualUrlSslContextFactory.getKeyStorePasswordLength());
    assertTrue(provider.containsKey("Alg.Alias.KeyManagerFactory.PKIX"));
    assertTrue(provider.containsKey("Alg.Alias.Signature.OID.1.2.840.113549.1.1.2"));
    assertTrue(provider.containsKey("Provider.id name"));
    assertTrue(provider.containsKey("Signature.MD5andSHA1withRSA"));
    assertEquals(AbstractSslContextFactory.SOCKET_ALGORITHM, sSLContext.getProtocol());
  }

  /**
   * Test {@link URLSslContextFactory#URLSslContextFactory(URL, String, URL, String)}.
   *
   * <ul>
   *   <li>When {@code https://example.org/example}.
   * </ul>
   *
   * <p>Method under test: {@link URLSslContextFactory#URLSslContextFactory(URL, String, URL,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void URLSslContextFactory.<init>(URL, String, URL, String)"})
  public void testNewURLSslContextFactory_whenHttpsExampleOrgExample()
      throws ClientSslSocketFactoryException, MalformedURLException {
    // Arrange
    URL trustStoreUrl = Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri().toURL();
    URL keyStoreUrl = Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri().toURL();

    // Act and Assert
    thrown.expect(ClientSslSocketFactoryException.class);
    new URLSslContextFactory(
        trustStoreUrl, "https://example.org/example", keyStoreUrl, "https://example.org/example");
  }

  /**
   * Test {@link URLSslContextFactory#URLSslContextFactory(URL, String, URL, String)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then return KeyStorePasswordLength is minus one.
   * </ul>
   *
   * <p>Method under test: {@link URLSslContextFactory#URLSslContextFactory(URL, String, URL,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void URLSslContextFactory.<init>(URL, String, URL, String)"})
  public void testNewURLSslContextFactory_whenNull_thenReturnKeyStorePasswordLengthIsMinusOne()
      throws ClientSslSocketFactoryException {
    // Arrange and Act
    URLSslContextFactory actualUrlSslContextFactory =
        new URLSslContextFactory(null, "not empty", null, null);

    // Assert
    assertNull(actualUrlSslContextFactory.getKeyStore());
    assertNull(actualUrlSslContextFactory.getTrustStore());
    assertEquals(-1, actualUrlSslContextFactory.getKeyStorePasswordLength());
    SSLContext sSLContext = actualUrlSslContextFactory.getSSLContext();
    Provider provider = sSLContext.getProvider();
    assertEquals(39, provider.size());
    assertEquals(9, actualUrlSslContextFactory.getTrustStorePasswordLength());
    assertTrue(provider.containsKey("Alg.Alias.KeyManagerFactory.PKIX"));
    assertTrue(provider.containsKey("Alg.Alias.Signature.OID.1.2.840.113549.1.1.2"));
    assertTrue(provider.containsKey("Provider.id name"));
    assertTrue(provider.containsKey("Signature.MD5andSHA1withRSA"));
    assertEquals(AbstractSslContextFactory.SOCKET_ALGORITHM, sSLContext.getProtocol());
  }

  /**
   * Test {@link URLSslContextFactory#URLSslContextFactory(URL, String, URL, String)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then return KeyStorePasswordLength is nine.
   * </ul>
   *
   * <p>Method under test: {@link URLSslContextFactory#URLSslContextFactory(URL, String, URL,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void URLSslContextFactory.<init>(URL, String, URL, String)"})
  public void testNewURLSslContextFactory_whenNull_thenReturnKeyStorePasswordLengthIsNine()
      throws ClientSslSocketFactoryException {
    // Arrange and Act
    URLSslContextFactory actualUrlSslContextFactory =
        new URLSslContextFactory(null, "not empty", null, "not empty");

    // Assert
    assertNull(actualUrlSslContextFactory.getKeyStore());
    assertNull(actualUrlSslContextFactory.getTrustStore());
    SSLContext sSLContext = actualUrlSslContextFactory.getSSLContext();
    Provider provider = sSLContext.getProvider();
    assertEquals(39, provider.size());
    assertEquals(9, actualUrlSslContextFactory.getKeyStorePasswordLength());
    assertEquals(9, actualUrlSslContextFactory.getTrustStorePasswordLength());
    assertTrue(provider.containsKey("Alg.Alias.KeyManagerFactory.PKIX"));
    assertTrue(provider.containsKey("Alg.Alias.Signature.OID.1.2.840.113549.1.1.2"));
    assertTrue(provider.containsKey("Provider.id name"));
    assertTrue(provider.containsKey("Signature.MD5andSHA1withRSA"));
    assertEquals(AbstractSslContextFactory.SOCKET_ALGORITHM, sSLContext.getProtocol());
  }

  /**
   * Test {@link URLSslContextFactory#URLSslContextFactory(URL, String, URL, String)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then throw {@link ClientSslSocketFactoryException}.
   * </ul>
   *
   * <p>Method under test: {@link URLSslContextFactory#URLSslContextFactory(URL, String, URL,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void URLSslContextFactory.<init>(URL, String, URL, String)"})
  public void testNewURLSslContextFactory_whenNull_thenThrowClientSslSocketFactoryException()
      throws ClientSslSocketFactoryException, MalformedURLException {
    // Arrange
    URL keyStoreUrl = Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri().toURL();

    // Act and Assert
    thrown.expect(ClientSslSocketFactoryException.class);
    new URLSslContextFactory(null, "not empty", keyStoreUrl, "not empty");
  }

  /**
   * Test {@link URLSslContextFactory#URLSslContextFactory(URL, String, URL, String)}.
   *
   * <ul>
   *   <li>When Property is {@code java.io.tmpdir} is empty string toUri toURL.
   * </ul>
   *
   * <p>Method under test: {@link URLSslContextFactory#URLSslContextFactory(URL, String, URL,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void URLSslContextFactory.<init>(URL, String, URL, String)"})
  public void testNewURLSslContextFactory_whenPropertyIsJavaIoTmpdirIsEmptyStringToUriToURL()
      throws ClientSslSocketFactoryException, MalformedURLException {
    // Arrange
    URL trustStoreUrl = Paths.get(System.getProperty("java.io.tmpdir"), "").toUri().toURL();
    URL keyStoreUrl = Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri().toURL();

    // Act and Assert
    thrown.expect(ClientSslSocketFactoryException.class);
    new URLSslContextFactory(
        trustStoreUrl, "https://example.org/example", keyStoreUrl, "https://example.org/example");
  }
}
