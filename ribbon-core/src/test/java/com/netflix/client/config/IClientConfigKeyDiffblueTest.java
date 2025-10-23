package com.netflix.client.config;

import static org.junit.Assert.assertNull;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.netflix.client.config.DefaultClientConfigImplTest.NewConfigKey;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class IClientConfigKeyDiffblueTest {
  /**
   * Test {@link IClientConfigKey#defaultValue()}.
   *
   * <p>Method under test: {@link IClientConfigKey#defaultValue()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object IClientConfigKey.defaultValue()"})
  public void testDefaultValue() {
    // Arrange
    NewConfigKey<Object> newConfigKey =
        new DefaultClientConfigImplTest().new NewConfigKey("Config Key");

    // Act and Assert
    assertNull(newConfigKey.defaultValue());
  }
}
