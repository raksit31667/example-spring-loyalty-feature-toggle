package com.raksit.example.feature;

import static com.raksit.example.feature.FeatureName.EXAMPLE_SPRING_LOYALTY_EXAMPLE_FEATURE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.util.HashMap;
import org.junit.jupiter.api.Test;

class DefaultFeatureToggleServiceTest {

  @Test
  void shouldReturnTrue_whenIsEnabled_givenPropertiesReturnsTrue() {
    // Given
    DefaultFeatureToggleService defaultFeatureToggleService = new DefaultFeatureToggleService(
        new HashMap<>() {
          {
            put("EXAMPLE_SPRING_LOYALTY_EXAMPLE_FEATURE", true);
          }
        });

    // When
    // Then
    assertThat(defaultFeatureToggleService.isEnabled(EXAMPLE_SPRING_LOYALTY_EXAMPLE_FEATURE),
        equalTo(true));
  }

  @Test
  void shouldReturnFalse_whenIsEnabled_givenPropertiesReturnsFalse() {
    // Given
    DefaultFeatureToggleService defaultFeatureToggleService = new DefaultFeatureToggleService(
        new HashMap<>() {
          {
            put("EXAMPLE_SPRING_LOYALTY_EXAMPLE_FEATURE", false);
          }
        });

    // When
    // Then
    assertThat(defaultFeatureToggleService.isEnabled(EXAMPLE_SPRING_LOYALTY_EXAMPLE_FEATURE),
        equalTo(false));
  }

  @Test
  void shouldReturnFalse_whenIsEnabled_givenPropertiesNotFound() {
    // Given
    DefaultFeatureToggleService defaultFeatureToggleService = new DefaultFeatureToggleService(
        new HashMap<>() {
          {
            put("EXAMPLE_SPRING_LOYALTY_BLAH_BLAH", true);
          }
        });

    // When
    // Then
    assertThat(defaultFeatureToggleService.isEnabled(EXAMPLE_SPRING_LOYALTY_EXAMPLE_FEATURE),
        equalTo(false));
  }
}