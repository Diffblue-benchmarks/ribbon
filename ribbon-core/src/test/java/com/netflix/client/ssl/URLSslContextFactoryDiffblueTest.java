package com.netflix.client.ssl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
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
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  /**
   * Test {@link URLSslContextFactory#URLSslContextFactory(URL, String, URL, String)}.
   * <ul>
   *   <li>Then return KeyStorePasswordLength is twenty-seven.</li>
   * </ul>
   * <p>
   * Method under test: {@link URLSslContextFactory#URLSslContextFactory(URL, String, URL, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void URLSslContextFactory.<init>(URL, String, URL, String)"})
  public void testNewURLSslContextFactory_thenReturnKeyStorePasswordLengthIsTwentySeven()
      throws ClientSslSocketFactoryException {
    // Arrange and Act
    URLSslContextFactory actualUrlSslContextFactory = new URLSslContextFactory(null, null, null,
        "https://example.org/example");

    // Assert
    assertNull(actualUrlSslContextFactory.getKeyStore());
    assertNull(actualUrlSslContextFactory.getTrustStore());
    assertEquals(-1, actualUrlSslContextFactory.getTrustStorePasswordLength());
    assertEquals(27, actualUrlSslContextFactory.getKeyStorePasswordLength());
    SSLContext sSLContext = actualUrlSslContextFactory.getSSLContext();
    Provider provider = sSLContext.getProvider();
    assertEquals(39, provider.size());
    assertTrue(provider.containsKey("Alg.Alias.KeyManagerFactory.PKIX"));
    assertTrue(provider.containsKey("Alg.Alias.Signature.OID.1.2.840.113549.1.1.2"));
    assertTrue(provider.containsKey("Provider.id name"));
    assertTrue(provider.containsKey("Signature.MD5andSHA1withRSA"));
    assertEquals(AbstractSslContextFactory.SOCKET_ALGORITHM, sSLContext.getProtocol());
  }

  /**
   * Test {@link URLSslContextFactory#URLSslContextFactory(URL, String, URL, String)}.
   * <ul>
   *   <li>Then return TrustStorePasswordLength is twenty-seven.</li>
   * </ul>
   * <p>
   * Method under test: {@link URLSslContextFactory#URLSslContextFactory(URL, String, URL, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void URLSslContextFactory.<init>(URL, String, URL, String)"})
  public void testNewURLSslContextFactory_thenReturnTrustStorePasswordLengthIsTwentySeven()
      throws ClientSslSocketFactoryException {
    // Arrange and Act
    URLSslContextFactory actualUrlSslContextFactory = new URLSslContextFactory(null, "https://example.org/example",
        null, null);

    // Assert
    assertNull(actualUrlSslContextFactory.getKeyStore());
    assertNull(actualUrlSslContextFactory.getTrustStore());
    assertEquals(-1, actualUrlSslContextFactory.getKeyStorePasswordLength());
    assertEquals(27, actualUrlSslContextFactory.getTrustStorePasswordLength());
    SSLContext sSLContext = actualUrlSslContextFactory.getSSLContext();
    Provider provider = sSLContext.getProvider();
    assertEquals(39, provider.size());
    assertTrue(provider.containsKey("Alg.Alias.KeyManagerFactory.PKIX"));
    assertTrue(provider.containsKey("Alg.Alias.Signature.OID.1.2.840.113549.1.1.2"));
    assertTrue(provider.containsKey("Provider.id name"));
    assertTrue(provider.containsKey("Signature.MD5andSHA1withRSA"));
    assertEquals(AbstractSslContextFactory.SOCKET_ALGORITHM, sSLContext.getProtocol());
  }

  /**
   * Test {@link URLSslContextFactory#URLSslContextFactory(URL, String, URL, String)}.
   * <ul>
   *   <li>Then throw {@link ClientSslSocketFactoryException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link URLSslContextFactory#URLSslContextFactory(URL, String, URL, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void URLSslContextFactory.<init>(URL, String, URL, String)"})
  public void testNewURLSslContextFactory_thenThrowClientSslSocketFactoryException()
      throws ClientSslSocketFactoryException, MalformedURLException {
    // Arrange, Act and Assert
    thrown.expect(ClientSslSocketFactoryException.class);

    new URLSslContextFactory(Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri().toURL(),
        "https://example.org/example", Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri().toURL(),
        "https://example.org/example");

  }

  /**
   * Test {@link URLSslContextFactory#URLSslContextFactory(URL, String, URL, String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return KeyStorePasswordLength is minus one.</li>
   * </ul>
   * <p>
   * Method under test: {@link URLSslContextFactory#URLSslContextFactory(URL, String, URL, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void URLSslContextFactory.<init>(URL, String, URL, String)"})
  public void testNewURLSslContextFactory_whenNull_thenReturnKeyStorePasswordLengthIsMinusOne()
      throws ClientSslSocketFactoryException {
    // Arrange and Act
    URLSslContextFactory actualUrlSslContextFactory = new URLSslContextFactory(null, null, null, null);

    // Assert
    assertNull(actualUrlSslContextFactory.getKeyStore());
    assertNull(actualUrlSslContextFactory.getTrustStore());
    assertEquals(-1, actualUrlSslContextFactory.getKeyStorePasswordLength());
    assertEquals(-1, actualUrlSslContextFactory.getTrustStorePasswordLength());
    SSLContext sSLContext = actualUrlSslContextFactory.getSSLContext();
    Provider provider = sSLContext.getProvider();
    assertEquals(39, provider.size());
    assertTrue(provider.containsKey("Alg.Alias.KeyManagerFactory.PKIX"));
    assertTrue(provider.containsKey("Alg.Alias.Signature.OID.1.2.840.113549.1.1.2"));
    assertTrue(provider.containsKey("Provider.id name"));
    assertTrue(provider.containsKey("Signature.MD5andSHA1withRSA"));
    assertEquals(AbstractSslContextFactory.SOCKET_ALGORITHM, sSLContext.getProtocol());
  }

  /**
   * Test {@link URLSslContextFactory#URLSslContextFactory(URL, String, URL, String)}.
   * <ul>
   *   <li>When Property is {@code java.io.tmpdir} is empty string toUri toURL.</li>
   * </ul>
   * <p>
   * Method under test: {@link URLSslContextFactory#URLSslContextFactory(URL, String, URL, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void URLSslContextFactory.<init>(URL, String, URL, String)"})
  public void testNewURLSslContextFactory_whenPropertyIsJavaIoTmpdirIsEmptyStringToUriToURL()
      throws ClientSslSocketFactoryException, MalformedURLException {
    // Arrange, Act and Assert
    thrown.expect(ClientSslSocketFactoryException.class);

    new URLSslContextFactory(Paths.get(System.getProperty("java.io.tmpdir"), "").toUri().toURL(),
        "https://example.org/example", Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri().toURL(),
        "https://example.org/example");

  }
}
