# example-spring-loyalty-feature-toggle

[![Build status](https://badge.buildkite.com/d408e163c4716826de1c12bd1cb8babd028721dd1973fa5130.svg)](https://buildkite.com/raksit31667/example-spring-loyalty-feature-toggle)

## Prerequisites

In Buildkite managed secrets S3 bucket, create a new folder
called `example-spring-loyalty-feature-toggle`. Then, create a new file called `env` with the
following credentials:

   ```
   export GITHUB_USERNAME=<your-github-username>
   export GITHUB_TOKEN=<your-github-token>
   ```

## Development guidelines

Install Homebrew formulae [direnv](https://direnv.net/) that can load and unload environment
variables depending on the current directory.

Then, clone `.envrc.template` with:

```shell
$ cp .envrc.template .envrc
```

Fill environment variables in `.envrc`, then load environment variables by running:

```shell
$ direnv allow
```

Finally, start local server on `local` active profile by running:

```shell
$ docker-compose -f docker-compose-local.yml up -d && ./gradlew clean api-service:bootRun
```

```shell
$ docker-compose -f docker-compose-local.yml up -d && ./gradlew clean batch-job:bootRun
```

For other active profiles, specify profile on System property as:

```shell
$ ./gradlew clean api-service:bootRun -Dspring.profile.active=<your-profile-name-comma-separated>
```

### Compile and Build

```
$ ./gradlew clean build
```

### Style Guide

Since it is much easier to understand a large codebase when all the code in it is in a consistent
style, we use [IntelliJ Java Google Style](https://google.github.io/styleguide/) where you can
import to IntelliJ by navigating to **Preference > Editor > Code Style > Java**, then click
on [Kebab Menu](https://thenounproject.com/term/kebab-menu/198755/) and **Import Scheme**. Finally,
browse to the XML file underneath `config/codestyle`.

### Environment variables

We use [direnv](https://direnv.net/) that can load and unload environment variables depending on the
current directory, so that you don't have to export variables repetitively.

After finish installation, copy `.envrc` based from `.envrc.template` and fill all required
variables, then run this following command in the root directory to apply variables:

```
direnv allow
```

### Git hooks

We use [ghooks.gradle](https://github.com/gtramontina/ghooks.gradle) for Git hooks Gradle plugin.

To download Git hooks plugin, run:

```shell
./gradlew clean build
```

### API documentation

See available API endpoints in [Swagger UI](http://localhost:8080/swagger-ui/index.html#/).

### CICD

[Buildkite](https://buildkite.com/) is a platform for running fast, secure, and scalable continuous
integration pipelines on our own infrastructure, it even works with a local Docker engine. Although
buildkite agent doesn't have dedicated agent for Java / Gradle, yet it allows builds to use Docker (
for more details, read
this [documentation](https://buildkite.com/docs/agent/v3/docker#allowing-builds-to-use-docker).)

Please be careful that **you have to start `buildkite-agent` in local machine in order to use
Buildkite.**

Install `buildkite-agent` based on your own machine. Follow the
instructions [here](https://buildkite.com/docs/agent/v3/installation). The snippet below is the
example for macOS using Homebrew:

```
brew install buildkite/buildkite/buildkite-agent
```

Then, you have to set Buildkite agent token for this pipeline, you can either run this command below
or use `direnv` by creating `.envrc` based from `.envrc.template`:

```
export BUILDKITE_AGENT_TOKEN=<your-buildkite-agent-token-here>
```

Finally, run `buildkite-agent` via `docker-compose` with the following command:

```
docker compose -f docker-compose-buildkite-agent.yml up
```
