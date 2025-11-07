package com.netflix.client.util;

import static org.junit.Assert.assertEquals;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.net.URL;
import java.nio.file.Paths;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class ResourcesDiffblueTest {
  /**
   * Test {@link Resources#getResource(String)}.
   * <p>
   * Method under test: {@link Resources#getResource(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"URL Resources.getResource(String)"})
  public void testGetResource() {
    // Arrange and Act
    URL actualResource = Resources.getResource("Resource Name");

    // Assert
    String expectedToStringResult = String.join("", "file:",
        Paths.get(System.getProperty("user.dir"), "Resource").toString(), "%20Name");
    assertEquals(expectedToStringResult, actualResource.toString());
  }
}
