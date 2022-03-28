package com.raksit.example.feature;

public interface FeatureToggleService {

  boolean isEnabled(FeatureName featureName);
}
