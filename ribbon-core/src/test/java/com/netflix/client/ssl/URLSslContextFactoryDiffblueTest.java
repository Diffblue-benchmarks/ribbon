package com.netflix.client.ssl;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;
import java.security.Provider;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLParameters;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class URLSslContextFactoryDiffblueTest {
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  /**
   * Method under test:
   * {@link URLSslContextFactory#URLSslContextFactory(URL, String, URL, String)}
   */
  @Test
  public void testNewURLSslContextFactory() throws ClientSslSocketFactoryException, MalformedURLException {
    // Arrange, Act and Assert
    thrown.expect(ClientSslSocketFactoryException.class);

    new URLSslContextFactory(Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri().toURL(),
        "https://example.org/example", Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri().toURL(),
        "https://example.org/example");

  }

  /**
   * Method under test:
   * {@link URLSslContextFactory#URLSslContextFactory(URL, String, URL, String)}
   */
  @Test
  public void testNewURLSslContextFactory2() throws ClientSslSocketFactoryException {
    // Arrange and Act
    URLSslContextFactory actualUrlSslContextFactory = new URLSslContextFactory(null, null, null, null);

    // Assert
    SSLContext sSLContext = actualUrlSslContextFactory.getSSLContext();
    Provider provider = sSLContext.getProvider();
    assertEquals(39, provider.size());
    assertEquals("MD2withRSA", provider.get("Alg.Alias.Signature.OID.1.2.840.113549.1.1.2"));
    assertEquals("NewSunX509", provider.get("Alg.Alias.KeyManagerFactory.PKIX"));
    assertEquals("SunJSSE", provider.get("Provider.id name"));
    assertEquals("sun.security.ssl.RSASignature", provider.get("Signature.MD5andSHA1withRSA"));
    SSLParameters defaultSSLParameters = sSLContext.getDefaultSSLParameters();
    assertNull(defaultSSLParameters.getEndpointIdentificationAlgorithm());
    SSLParameters supportedSSLParameters = sSLContext.getSupportedSSLParameters();
    assertNull(supportedSSLParameters.getEndpointIdentificationAlgorithm());
    assertNull(supportedSSLParameters.getAlgorithmConstraints());
    assertNull(actualUrlSslContextFactory.getKeyStore());
    assertNull(actualUrlSslContextFactory.getTrustStore());
    assertNull(defaultSSLParameters.getSNIMatchers());
    assertNull(supportedSSLParameters.getSNIMatchers());
    assertNull(defaultSSLParameters.getServerNames());
    assertNull(supportedSSLParameters.getServerNames());
    assertEquals(-1, actualUrlSslContextFactory.getKeyStorePasswordLength());
    assertEquals(-1, actualUrlSslContextFactory.getTrustStorePasswordLength());
    assertEquals(0, defaultSSLParameters.getApplicationProtocols().length);
    assertEquals(0, supportedSSLParameters.getApplicationProtocols().length);
    assertFalse(defaultSSLParameters.getNeedClientAuth());
    assertFalse(supportedSSLParameters.getNeedClientAuth());
    assertFalse(defaultSSLParameters.getUseCipherSuitesOrder());
    assertFalse(supportedSSLParameters.getUseCipherSuitesOrder());
    assertFalse(defaultSSLParameters.getWantClientAuth());
    assertFalse(supportedSSLParameters.getWantClientAuth());
    assertEquals(AbstractSslContextFactory.SOCKET_ALGORITHM, sSLContext.getProtocol());
    assertArrayEquals(new String[]{"TLSv1.3", "TLSv1.2", "TLSv1.1", "TLSv1"}, defaultSSLParameters.getProtocols());
    assertArrayEquals(new String[]{"TLSv1.3", "TLSv1.2", "TLSv1.1", "TLSv1", "SSLv3", "SSLv2Hello"},
        supportedSSLParameters.getProtocols());
    assertArrayEquals(new String[]{"TLS_AES_256_GCM_SHA384", "TLS_AES_128_GCM_SHA256",
        "TLS_ECDHE_ECDSA_WITH_AES_256_GCM_SHA384", "TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256",
        "TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384", "TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256",
        "TLS_DHE_RSA_WITH_AES_256_GCM_SHA384", "TLS_DHE_DSS_WITH_AES_256_GCM_SHA384",
        "TLS_DHE_RSA_WITH_AES_128_GCM_SHA256", "TLS_DHE_DSS_WITH_AES_128_GCM_SHA256",
        "TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA384", "TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA384",
        "TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA256", "TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA256",
        "TLS_DHE_RSA_WITH_AES_256_CBC_SHA256", "TLS_DHE_DSS_WITH_AES_256_CBC_SHA256",
        "TLS_DHE_RSA_WITH_AES_128_CBC_SHA256", "TLS_DHE_DSS_WITH_AES_128_CBC_SHA256",
        "TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA", "TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA",
        "TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA", "TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA",
        "TLS_DHE_RSA_WITH_AES_256_CBC_SHA", "TLS_DHE_DSS_WITH_AES_256_CBC_SHA", "TLS_DHE_RSA_WITH_AES_128_CBC_SHA",
        "TLS_DHE_DSS_WITH_AES_128_CBC_SHA", "TLS_RSA_WITH_AES_256_GCM_SHA384", "TLS_RSA_WITH_AES_128_GCM_SHA256",
        "TLS_RSA_WITH_AES_256_CBC_SHA256", "TLS_RSA_WITH_AES_128_CBC_SHA256", "TLS_RSA_WITH_AES_256_CBC_SHA",
        "TLS_RSA_WITH_AES_128_CBC_SHA", "TLS_ECDHE_ECDSA_WITH_3DES_EDE_CBC_SHA", "TLS_ECDHE_RSA_WITH_3DES_EDE_CBC_SHA",
        "SSL_DHE_RSA_WITH_3DES_EDE_CBC_SHA", "SSL_DHE_DSS_WITH_3DES_EDE_CBC_SHA", "SSL_RSA_WITH_3DES_EDE_CBC_SHA",
        "TLS_EMPTY_RENEGOTIATION_INFO_SCSV"}, defaultSSLParameters.getCipherSuites());
    assertArrayEquals(
        new String[]{"TLS_AES_256_GCM_SHA384", "TLS_AES_128_GCM_SHA256", "TLS_ECDHE_ECDSA_WITH_AES_256_GCM_SHA384",
            "TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256", "TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384",
            "TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256", "TLS_DHE_RSA_WITH_AES_256_GCM_SHA384",
            "TLS_DHE_DSS_WITH_AES_256_GCM_SHA384", "TLS_DHE_RSA_WITH_AES_128_GCM_SHA256",
            "TLS_DHE_DSS_WITH_AES_128_GCM_SHA256", "TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA384",
            "TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA384", "TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA256",
            "TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA256", "TLS_DHE_RSA_WITH_AES_256_CBC_SHA256",
            "TLS_DHE_DSS_WITH_AES_256_CBC_SHA256", "TLS_DHE_RSA_WITH_AES_128_CBC_SHA256",
            "TLS_DHE_DSS_WITH_AES_128_CBC_SHA256", "TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA",
            "TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA", "TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA",
            "TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA", "TLS_DHE_RSA_WITH_AES_256_CBC_SHA",
            "TLS_DHE_DSS_WITH_AES_256_CBC_SHA", "TLS_DHE_RSA_WITH_AES_128_CBC_SHA", "TLS_DHE_DSS_WITH_AES_128_CBC_SHA",
            "TLS_RSA_WITH_AES_256_GCM_SHA384", "TLS_RSA_WITH_AES_128_GCM_SHA256", "TLS_RSA_WITH_AES_256_CBC_SHA256",
            "TLS_RSA_WITH_AES_128_CBC_SHA256", "TLS_RSA_WITH_AES_256_CBC_SHA", "TLS_RSA_WITH_AES_128_CBC_SHA",
            "TLS_ECDHE_ECDSA_WITH_3DES_EDE_CBC_SHA", "TLS_ECDHE_RSA_WITH_3DES_EDE_CBC_SHA",
            "SSL_DHE_RSA_WITH_3DES_EDE_CBC_SHA", "SSL_DHE_DSS_WITH_3DES_EDE_CBC_SHA", "SSL_RSA_WITH_3DES_EDE_CBC_SHA",
            "TLS_EMPTY_RENEGOTIATION_INFO_SCSV", "TLS_KRB5_WITH_3DES_EDE_CBC_SHA", "TLS_KRB5_WITH_3DES_EDE_CBC_MD5"},
        supportedSSLParameters.getCipherSuites());
  }

  /**
   * Method under test:
   * {@link URLSslContextFactory#URLSslContextFactory(URL, String, URL, String)}
   */
  @Test
  public void testNewURLSslContextFactory3() throws ClientSslSocketFactoryException {
    // Arrange and Act
    URLSslContextFactory actualUrlSslContextFactory = new URLSslContextFactory(null, null, null,
        "https://example.org/example");

    // Assert
    SSLContext sSLContext = actualUrlSslContextFactory.getSSLContext();
    Provider provider = sSLContext.getProvider();
    assertEquals(39, provider.size());
    assertEquals("MD2withRSA", provider.get("Alg.Alias.Signature.OID.1.2.840.113549.1.1.2"));
    assertEquals("NewSunX509", provider.get("Alg.Alias.KeyManagerFactory.PKIX"));
    assertEquals("SunJSSE", provider.get("Provider.id name"));
    assertEquals("sun.security.ssl.RSASignature", provider.get("Signature.MD5andSHA1withRSA"));
    SSLParameters defaultSSLParameters = sSLContext.getDefaultSSLParameters();
    assertNull(defaultSSLParameters.getEndpointIdentificationAlgorithm());
    SSLParameters supportedSSLParameters = sSLContext.getSupportedSSLParameters();
    assertNull(supportedSSLParameters.getEndpointIdentificationAlgorithm());
    assertNull(supportedSSLParameters.getAlgorithmConstraints());
    assertNull(actualUrlSslContextFactory.getKeyStore());
    assertNull(actualUrlSslContextFactory.getTrustStore());
    assertNull(defaultSSLParameters.getSNIMatchers());
    assertNull(supportedSSLParameters.getSNIMatchers());
    assertNull(defaultSSLParameters.getServerNames());
    assertNull(supportedSSLParameters.getServerNames());
    assertEquals(-1, actualUrlSslContextFactory.getTrustStorePasswordLength());
    assertEquals(0, defaultSSLParameters.getApplicationProtocols().length);
    assertEquals(0, supportedSSLParameters.getApplicationProtocols().length);
    assertEquals(27, actualUrlSslContextFactory.getKeyStorePasswordLength());
    assertFalse(defaultSSLParameters.getNeedClientAuth());
    assertFalse(supportedSSLParameters.getNeedClientAuth());
    assertFalse(defaultSSLParameters.getUseCipherSuitesOrder());
    assertFalse(supportedSSLParameters.getUseCipherSuitesOrder());
    assertFalse(defaultSSLParameters.getWantClientAuth());
    assertFalse(supportedSSLParameters.getWantClientAuth());
    assertEquals(AbstractSslContextFactory.SOCKET_ALGORITHM, sSLContext.getProtocol());
    assertArrayEquals(new String[]{"TLSv1.3", "TLSv1.2", "TLSv1.1", "TLSv1"}, defaultSSLParameters.getProtocols());
    assertArrayEquals(new String[]{"TLSv1.3", "TLSv1.2", "TLSv1.1", "TLSv1", "SSLv3", "SSLv2Hello"},
        supportedSSLParameters.getProtocols());
    assertArrayEquals(new String[]{"TLS_AES_256_GCM_SHA384", "TLS_AES_128_GCM_SHA256",
        "TLS_ECDHE_ECDSA_WITH_AES_256_GCM_SHA384", "TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256",
        "TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384", "TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256",
        "TLS_DHE_RSA_WITH_AES_256_GCM_SHA384", "TLS_DHE_DSS_WITH_AES_256_GCM_SHA384",
        "TLS_DHE_RSA_WITH_AES_128_GCM_SHA256", "TLS_DHE_DSS_WITH_AES_128_GCM_SHA256",
        "TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA384", "TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA384",
        "TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA256", "TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA256",
        "TLS_DHE_RSA_WITH_AES_256_CBC_SHA256", "TLS_DHE_DSS_WITH_AES_256_CBC_SHA256",
        "TLS_DHE_RSA_WITH_AES_128_CBC_SHA256", "TLS_DHE_DSS_WITH_AES_128_CBC_SHA256",
        "TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA", "TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA",
        "TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA", "TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA",
        "TLS_DHE_RSA_WITH_AES_256_CBC_SHA", "TLS_DHE_DSS_WITH_AES_256_CBC_SHA", "TLS_DHE_RSA_WITH_AES_128_CBC_SHA",
        "TLS_DHE_DSS_WITH_AES_128_CBC_SHA", "TLS_RSA_WITH_AES_256_GCM_SHA384", "TLS_RSA_WITH_AES_128_GCM_SHA256",
        "TLS_RSA_WITH_AES_256_CBC_SHA256", "TLS_RSA_WITH_AES_128_CBC_SHA256", "TLS_RSA_WITH_AES_256_CBC_SHA",
        "TLS_RSA_WITH_AES_128_CBC_SHA", "TLS_ECDHE_ECDSA_WITH_3DES_EDE_CBC_SHA", "TLS_ECDHE_RSA_WITH_3DES_EDE_CBC_SHA",
        "SSL_DHE_RSA_WITH_3DES_EDE_CBC_SHA", "SSL_DHE_DSS_WITH_3DES_EDE_CBC_SHA", "SSL_RSA_WITH_3DES_EDE_CBC_SHA",
        "TLS_EMPTY_RENEGOTIATION_INFO_SCSV"}, defaultSSLParameters.getCipherSuites());
    assertArrayEquals(
        new String[]{"TLS_AES_256_GCM_SHA384", "TLS_AES_128_GCM_SHA256", "TLS_ECDHE_ECDSA_WITH_AES_256_GCM_SHA384",
            "TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256", "TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384",
            "TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256", "TLS_DHE_RSA_WITH_AES_256_GCM_SHA384",
            "TLS_DHE_DSS_WITH_AES_256_GCM_SHA384", "TLS_DHE_RSA_WITH_AES_128_GCM_SHA256",
            "TLS_DHE_DSS_WITH_AES_128_GCM_SHA256", "TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA384",
            "TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA384", "TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA256",
            "TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA256", "TLS_DHE_RSA_WITH_AES_256_CBC_SHA256",
            "TLS_DHE_DSS_WITH_AES_256_CBC_SHA256", "TLS_DHE_RSA_WITH_AES_128_CBC_SHA256",
            "TLS_DHE_DSS_WITH_AES_128_CBC_SHA256", "TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA",
            "TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA", "TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA",
            "TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA", "TLS_DHE_RSA_WITH_AES_256_CBC_SHA",
            "TLS_DHE_DSS_WITH_AES_256_CBC_SHA", "TLS_DHE_RSA_WITH_AES_128_CBC_SHA", "TLS_DHE_DSS_WITH_AES_128_CBC_SHA",
            "TLS_RSA_WITH_AES_256_GCM_SHA384", "TLS_RSA_WITH_AES_128_GCM_SHA256", "TLS_RSA_WITH_AES_256_CBC_SHA256",
            "TLS_RSA_WITH_AES_128_CBC_SHA256", "TLS_RSA_WITH_AES_256_CBC_SHA", "TLS_RSA_WITH_AES_128_CBC_SHA",
            "TLS_ECDHE_ECDSA_WITH_3DES_EDE_CBC_SHA", "TLS_ECDHE_RSA_WITH_3DES_EDE_CBC_SHA",
            "SSL_DHE_RSA_WITH_3DES_EDE_CBC_SHA", "SSL_DHE_DSS_WITH_3DES_EDE_CBC_SHA", "SSL_RSA_WITH_3DES_EDE_CBC_SHA",
            "TLS_EMPTY_RENEGOTIATION_INFO_SCSV", "TLS_KRB5_WITH_3DES_EDE_CBC_SHA", "TLS_KRB5_WITH_3DES_EDE_CBC_MD5"},
        supportedSSLParameters.getCipherSuites());
  }

  /**
   * Method under test:
   * {@link URLSslContextFactory#URLSslContextFactory(URL, String, URL, String)}
   */
  @Test
  public void testNewURLSslContextFactory4() throws ClientSslSocketFactoryException {
    // Arrange and Act
    URLSslContextFactory actualUrlSslContextFactory = new URLSslContextFactory(null, "https://example.org/example",
        null, null);

    // Assert
    SSLContext sSLContext = actualUrlSslContextFactory.getSSLContext();
    Provider provider = sSLContext.getProvider();
    assertEquals(39, provider.size());
    assertEquals("MD2withRSA", provider.get("Alg.Alias.Signature.OID.1.2.840.113549.1.1.2"));
    assertEquals("NewSunX509", provider.get("Alg.Alias.KeyManagerFactory.PKIX"));
    assertEquals("SunJSSE", provider.get("Provider.id name"));
    assertEquals("sun.security.ssl.RSASignature", provider.get("Signature.MD5andSHA1withRSA"));
    SSLParameters defaultSSLParameters = sSLContext.getDefaultSSLParameters();
    assertNull(defaultSSLParameters.getEndpointIdentificationAlgorithm());
    SSLParameters supportedSSLParameters = sSLContext.getSupportedSSLParameters();
    assertNull(supportedSSLParameters.getEndpointIdentificationAlgorithm());
    assertNull(supportedSSLParameters.getAlgorithmConstraints());
    assertNull(actualUrlSslContextFactory.getKeyStore());
    assertNull(actualUrlSslContextFactory.getTrustStore());
    assertNull(defaultSSLParameters.getSNIMatchers());
    assertNull(supportedSSLParameters.getSNIMatchers());
    assertNull(defaultSSLParameters.getServerNames());
    assertNull(supportedSSLParameters.getServerNames());
    assertEquals(-1, actualUrlSslContextFactory.getKeyStorePasswordLength());
    assertEquals(0, defaultSSLParameters.getApplicationProtocols().length);
    assertEquals(0, supportedSSLParameters.getApplicationProtocols().length);
    assertEquals(27, actualUrlSslContextFactory.getTrustStorePasswordLength());
    assertFalse(defaultSSLParameters.getNeedClientAuth());
    assertFalse(supportedSSLParameters.getNeedClientAuth());
    assertFalse(defaultSSLParameters.getUseCipherSuitesOrder());
    assertFalse(supportedSSLParameters.getUseCipherSuitesOrder());
    assertFalse(defaultSSLParameters.getWantClientAuth());
    assertFalse(supportedSSLParameters.getWantClientAuth());
    assertEquals(AbstractSslContextFactory.SOCKET_ALGORITHM, sSLContext.getProtocol());
    assertArrayEquals(new String[]{"TLSv1.3", "TLSv1.2", "TLSv1.1", "TLSv1"}, defaultSSLParameters.getProtocols());
    assertArrayEquals(new String[]{"TLSv1.3", "TLSv1.2", "TLSv1.1", "TLSv1", "SSLv3", "SSLv2Hello"},
        supportedSSLParameters.getProtocols());
    assertArrayEquals(new String[]{"TLS_AES_256_GCM_SHA384", "TLS_AES_128_GCM_SHA256",
        "TLS_ECDHE_ECDSA_WITH_AES_256_GCM_SHA384", "TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256",
        "TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384", "TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256",
        "TLS_DHE_RSA_WITH_AES_256_GCM_SHA384", "TLS_DHE_DSS_WITH_AES_256_GCM_SHA384",
        "TLS_DHE_RSA_WITH_AES_128_GCM_SHA256", "TLS_DHE_DSS_WITH_AES_128_GCM_SHA256",
        "TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA384", "TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA384",
        "TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA256", "TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA256",
        "TLS_DHE_RSA_WITH_AES_256_CBC_SHA256", "TLS_DHE_DSS_WITH_AES_256_CBC_SHA256",
        "TLS_DHE_RSA_WITH_AES_128_CBC_SHA256", "TLS_DHE_DSS_WITH_AES_128_CBC_SHA256",
        "TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA", "TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA",
        "TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA", "TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA",
        "TLS_DHE_RSA_WITH_AES_256_CBC_SHA", "TLS_DHE_DSS_WITH_AES_256_CBC_SHA", "TLS_DHE_RSA_WITH_AES_128_CBC_SHA",
        "TLS_DHE_DSS_WITH_AES_128_CBC_SHA", "TLS_RSA_WITH_AES_256_GCM_SHA384", "TLS_RSA_WITH_AES_128_GCM_SHA256",
        "TLS_RSA_WITH_AES_256_CBC_SHA256", "TLS_RSA_WITH_AES_128_CBC_SHA256", "TLS_RSA_WITH_AES_256_CBC_SHA",
        "TLS_RSA_WITH_AES_128_CBC_SHA", "TLS_ECDHE_ECDSA_WITH_3DES_EDE_CBC_SHA", "TLS_ECDHE_RSA_WITH_3DES_EDE_CBC_SHA",
        "SSL_DHE_RSA_WITH_3DES_EDE_CBC_SHA", "SSL_DHE_DSS_WITH_3DES_EDE_CBC_SHA", "SSL_RSA_WITH_3DES_EDE_CBC_SHA",
        "TLS_EMPTY_RENEGOTIATION_INFO_SCSV"}, defaultSSLParameters.getCipherSuites());
    assertArrayEquals(
        new String[]{"TLS_AES_256_GCM_SHA384", "TLS_AES_128_GCM_SHA256", "TLS_ECDHE_ECDSA_WITH_AES_256_GCM_SHA384",
            "TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256", "TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384",
            "TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256", "TLS_DHE_RSA_WITH_AES_256_GCM_SHA384",
            "TLS_DHE_DSS_WITH_AES_256_GCM_SHA384", "TLS_DHE_RSA_WITH_AES_128_GCM_SHA256",
            "TLS_DHE_DSS_WITH_AES_128_GCM_SHA256", "TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA384",
            "TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA384", "TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA256",
            "TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA256", "TLS_DHE_RSA_WITH_AES_256_CBC_SHA256",
            "TLS_DHE_DSS_WITH_AES_256_CBC_SHA256", "TLS_DHE_RSA_WITH_AES_128_CBC_SHA256",
            "TLS_DHE_DSS_WITH_AES_128_CBC_SHA256", "TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA",
            "TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA", "TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA",
            "TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA", "TLS_DHE_RSA_WITH_AES_256_CBC_SHA",
            "TLS_DHE_DSS_WITH_AES_256_CBC_SHA", "TLS_DHE_RSA_WITH_AES_128_CBC_SHA", "TLS_DHE_DSS_WITH_AES_128_CBC_SHA",
            "TLS_RSA_WITH_AES_256_GCM_SHA384", "TLS_RSA_WITH_AES_128_GCM_SHA256", "TLS_RSA_WITH_AES_256_CBC_SHA256",
            "TLS_RSA_WITH_AES_128_CBC_SHA256", "TLS_RSA_WITH_AES_256_CBC_SHA", "TLS_RSA_WITH_AES_128_CBC_SHA",
            "TLS_ECDHE_ECDSA_WITH_3DES_EDE_CBC_SHA", "TLS_ECDHE_RSA_WITH_3DES_EDE_CBC_SHA",
            "SSL_DHE_RSA_WITH_3DES_EDE_CBC_SHA", "SSL_DHE_DSS_WITH_3DES_EDE_CBC_SHA", "SSL_RSA_WITH_3DES_EDE_CBC_SHA",
            "TLS_EMPTY_RENEGOTIATION_INFO_SCSV", "TLS_KRB5_WITH_3DES_EDE_CBC_SHA", "TLS_KRB5_WITH_3DES_EDE_CBC_MD5"},
        supportedSSLParameters.getCipherSuites());
  }

  /**
   * Method under test:
   * {@link URLSslContextFactory#URLSslContextFactory(URL, String, URL, String)}
   */
  @Test
  public void testNewURLSslContextFactory5() throws ClientSslSocketFactoryException, MalformedURLException {
    // Arrange, Act and Assert
    thrown.expect(ClientSslSocketFactoryException.class);

    new URLSslContextFactory(Paths.get(System.getProperty("java.io.tmpdir"), "").toUri().toURL(),
        "https://example.org/example", Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri().toURL(),
        "https://example.org/example");

  }
}
