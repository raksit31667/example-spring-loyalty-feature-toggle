package com.raksit.example.feature;

import java.util.Map;

public class DefaultFeatureToggleService implements FeatureToggleService {

  private final Map<String, Boolean> featureFlagProperties;

  public DefaultFeatureToggleService(Map<String, Boolean> featureFlagProperties) {
    this.featureFlagProperties = featureFlagProperties;
  }

  @Override
  public boolean isEnabled(FeatureName featureName) {
    return featureFlagProperties.getOrDefault(featureName.name(), false);
  }
}
