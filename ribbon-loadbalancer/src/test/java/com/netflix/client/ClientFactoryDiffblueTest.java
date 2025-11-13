package com.netflix.client;

import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.rules.ExpectedException;

public class ClientFactoryDiffblueTest {
  @Rule public ExpectedException thrown = ExpectedException.none();

  /**
   * Test {@link ClientFactory#getNamedClient(String)} with {@code name}.
   *
   * <ul>
   *   <li>When {@code Name}.
   *   <li>Then throw {@link RuntimeException}.
   * </ul>
   *
   * <p>Method under test: {@link ClientFactory#getNamedClient(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"com.netflix.client.IClient ClientFactory.getNamedClient(String)"})
  public void testGetNamedClientWithName_whenName_thenThrowRuntimeException() {
    // Arrange, Act and Assert
    thrown.expect(RuntimeException.class);
    ClientFactory.getNamedClient("Name");
  }
}
