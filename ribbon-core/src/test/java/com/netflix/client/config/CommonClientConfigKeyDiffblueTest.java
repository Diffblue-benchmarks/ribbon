package com.netflix.client.config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.Set;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class CommonClientConfigKeyDiffblueTest {
  /**
   * Test {@link CommonClientConfigKey#values()}.
   * <p>
   * Method under test: {@link CommonClientConfigKey#values()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"IClientConfigKey[] CommonClientConfigKey.values()"})
  public void testValues() {
    // Arrange and Act
    IClientConfigKey[] actualValuesResult = CommonClientConfigKey.values();

    // Assert
    assertEquals("", (actualValuesResult[23]).defaultValue());
    assertEquals("SECONDS",
        (actualValuesResult[AbstractDefaultClientConfigImpl.DEFAULT_MAX_CONNECTIONS_PER_HOST]).defaultValue());
    assertNull((actualValuesResult[10]).defaultValue());
    assertNull((actualValuesResult[15]).defaultValue());
    assertNull((actualValuesResult[17]).defaultValue());
    assertNull((actualValuesResult[18]).defaultValue());
    assertNull((actualValuesResult[19]).defaultValue());
    assertNull((actualValuesResult[2]).defaultValue());
    assertNull((actualValuesResult[20]).defaultValue());
    assertNull((actualValuesResult[22]).defaultValue());
    assertNull((actualValuesResult[5]).defaultValue());
    assertNull((actualValuesResult[54]).defaultValue());
    assertNull((actualValuesResult[56]).defaultValue());
    assertNull((actualValuesResult[57]).defaultValue());
    assertNull((actualValuesResult[58]).defaultValue());
    assertNull((actualValuesResult[63]).defaultValue());
    assertNull((actualValuesResult[72]).defaultValue());
    assertNull((actualValuesResult[73]).defaultValue());
    assertNull((actualValuesResult[Short.SIZE]).defaultValue());
    assertEquals(0, ((Integer) (actualValuesResult[1]).defaultValue()).intValue());
    assertEquals(1, ((Integer) (actualValuesResult[52]).defaultValue()).intValue());
    assertEquals(1, ((Integer) (actualValuesResult[68]).defaultValue()).intValue());
    assertEquals(75, actualValuesResult.length);
    assertFalse((Boolean) (actualValuesResult[11]).defaultValue());
    assertFalse((Boolean) (actualValuesResult[13]).defaultValue());
    assertFalse((Boolean) (actualValuesResult[51]).defaultValue());
    assertFalse((Boolean) (actualValuesResult[59]).defaultValue());
    assertFalse((Boolean) (actualValuesResult[62]).defaultValue());
    assertFalse((Boolean) (actualValuesResult[74]).defaultValue());
    assertTrue((Boolean) (actualValuesResult[24]).defaultValue());
    assertTrue((Boolean) (actualValuesResult[55]).defaultValue());
    assertTrue((Boolean) (actualValuesResult[6]).defaultValue());
    assertTrue((Boolean) (actualValuesResult[Double.SIZE]).defaultValue());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_CONNECTIONIDLE_TIME_IN_MSECS,
        ((Integer) (actualValuesResult[69]).defaultValue()).intValue());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_CONNECTIONIDLE_TIME_IN_MSECS,
        ((Integer) (actualValuesResult[71]).defaultValue()).intValue());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_CONNECTION_MANAGER_TIMEOUT,
        ((Integer) (actualValuesResult[0]).defaultValue()).intValue());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_CONNECTION_MANAGER_TIMEOUT,
        ((Integer) (actualValuesResult[4]).defaultValue()).intValue());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_MAX_CONNECTIONS_PER_HOST,
        ((Integer) (actualValuesResult[67]).defaultValue()).intValue());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_MAX_TOTAL_CONNECTIONS,
        ((Integer) (actualValuesResult[3]).defaultValue()).intValue());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_MAX_TOTAL_CONNECTIONS,
        ((Integer) (actualValuesResult[7]).defaultValue()).intValue());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_MAX_TOTAL_CONNECTIONS,
        ((Integer) (actualValuesResult[8]).defaultValue()).intValue());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_MIN_PRIME_CONNECTIONS_RATIO,
        ((Float) (actualValuesResult[12]).defaultValue()).floatValue(), 0.0f);
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_NFLOADBALANCER_CLASSNAME,
        (actualValuesResult[66]).defaultValue());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_NFLOADBALANCER_PING_CLASSNAME,
        (actualValuesResult[14]).defaultValue());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_NFLOADBALANCER_RULE_CLASSNAME,
        (actualValuesResult[53]).defaultValue());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_PORT,
        ((Integer) (actualValuesResult[61]).defaultValue()).intValue());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_PORT,
        ((Integer) (actualValuesResult[AbstractDefaultClientConfigImpl.DEFAULT_MAX_RETRIES_PER_SERVER_PRIME_CONNECTION])
            .defaultValue()).intValue());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_PRIME_CONNECTIONS_CLASS,
        (actualValuesResult[70]).defaultValue());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_PRIME_CONNECTIONS_URI,
        (actualValuesResult[21]).defaultValue());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_READ_TIMEOUT,
        ((Integer) (actualValuesResult[65]).defaultValue()).intValue());
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_SERVER_LIST_UPDATER_CLASS,
        (actualValuesResult[60]).defaultValue());
  }

  /**
   * Test {@link CommonClientConfigKey#keys()}.
   * <p>
   * Method under test: {@link CommonClientConfigKey#keys()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Set CommonClientConfigKey.keys()"})
  public void testKeys() {
    // Arrange and Act
    Set<IClientConfigKey> actualKeysResult = CommonClientConfigKey.keys();

    // Assert
    assertEquals(75, actualKeysResult.size());
  }

  /**
   * Test {@link CommonClientConfigKey#valueOf(String)}.
   * <ul>
   *   <li>Then return defaultValue intValue is {@link AbstractDefaultClientConfigImpl#DEFAULT_CONNECTION_MANAGER_TIMEOUT}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CommonClientConfigKey#valueOf(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"IClientConfigKey CommonClientConfigKey.valueOf(String)"})
  public void testValueOf_thenReturnDefaultValueIntValueIsDefault_connection_manager_timeout() {
    // Arrange, Act and Assert
    assertEquals(AbstractDefaultClientConfigImpl.DEFAULT_CONNECTION_MANAGER_TIMEOUT,
        ((Integer) CommonClientConfigKey.valueOf("ConnectTimeout").defaultValue()).intValue());
  }

  /**
   * Test {@link CommonClientConfigKey#valueOf(String)}.
   * <ul>
   *   <li>When {@code Name}.</li>
   *   <li>Then return defaultValue is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CommonClientConfigKey#valueOf(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"IClientConfigKey CommonClientConfigKey.valueOf(String)"})
  public void testValueOf_whenName_thenReturnDefaultValueIsNull() {
    // Arrange, Act and Assert
    assertNull(CommonClientConfigKey.valueOf("Name").defaultValue());
  }
}
