package com.netflix.client.ssl;

import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;
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
