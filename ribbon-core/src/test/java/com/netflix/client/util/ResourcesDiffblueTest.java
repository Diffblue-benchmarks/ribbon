package com.netflix.client.util;

import static org.junit.Assert.assertEquals;
import java.net.URL;
import java.nio.file.Paths;
import org.junit.Test;

public class ResourcesDiffblueTest {
  /**
   * Method under test: {@link Resources#getResource(String)}
   */
  @Test
  public void testGetResource() {
    // Arrange and Act
    URL actualResource = Resources.getResource("Resource Name");

    // Assert
    String expectedToStringResult = String.join("", "file:",
        Paths.get(System.getProperty("user.dir"), "Resource").toString(), "%20Name");
    assertEquals(expectedToStringResult, actualResource.toString());
  }
}
