package com.raksit.example.feature;

import io.split.client.SplitClient;
import io.split.client.SplitClientConfig;
import io.split.client.SplitFactory;
import io.split.client.SplitFactoryBuilder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.concurrent.TimeoutException;

@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "feature")
public class FeatureToggleAutoConfiguration {

  private final Environment environment;

  @Value("${split.io.api.key:-}")
  private String splitApiKey;

  private Map<String, Boolean> flags;

  public FeatureToggleAutoConfiguration(Environment environment) {
    this.environment = environment;
  }

  @Bean
  public FeatureToggleService defaultFeatureToggleService() {
    return new DefaultFeatureToggleService(flags);
  }

  @Bean
  @Primary
  @Profile({"dev", "functional-test", "prod"})
  public FeatureToggleService splitFeatureToggleService()
      throws IOException, URISyntaxException, InterruptedException, TimeoutException {
    return new SplitFeatureToggleService(splitClient());
  }

  @Bean
  @Profile({"dev", "functional-test", "prod"})
  SplitClient splitClient()
      throws IOException, URISyntaxException, InterruptedException, TimeoutException {
    final SplitClientConfig clientConfig = SplitClientConfig.builder()
        .setBlockUntilReadyTimeout(10000)
        .enableDebug()
        .build();

    final SplitFactory factory = SplitFactoryBuilder.build(splitApiKey, clientConfig);
    final SplitClient client = factory.client();

    if (environment.acceptsProfiles(Profiles.of("!default"))) {
      client.blockUntilReady();
    }

    return client;
  }
}
