package com.netflix.client.config;

import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.netflix.client.config.IClientConfig.Builder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.rules.ExpectedException;

public class IClientConfigDiffblueTest {
  @Rule public ExpectedException thrown = ExpectedException.none();

  /**
   * Test Builder {@link Builder#newBuilder()}.
   *
   * <p>Method under test: {@link Builder#newBuilder()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Builder Builder.newBuilder()"})
  public void testBuilderNewBuilder() {
    // Arrange, Act and Assert
    IClientConfig iClientConfig = Builder.newBuilder().build();
    assertTrue(iClientConfig instanceof DefaultClientConfigImpl);
  }

  /**
   * Test Builder {@link Builder#newBuilder(Class)} with {@code implClass}.
   *
   * <p>Method under test: {@link Builder#newBuilder(Class)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Builder Builder.newBuilder(Class)"})
  public void testBuilderNewBuilderWithImplClass() {
    // Arrange
    Class<IClientConfig> implClass = IClientConfig.class;

    // Act and Assert
    thrown.expect(IllegalArgumentException.class);
    Builder.newBuilder(implClass);
  }
}
