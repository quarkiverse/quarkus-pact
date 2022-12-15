# quarkus-pact

<!-- ALL-CONTRIBUTORS-BADGE:START - Do not remove or modify this section -->
[![All Contributors](https://img.shields.io/badge/all_contributors-2-orange.svg?style=flat-square)](#contributors-)
<!-- ALL-CONTRIBUTORS-BADGE:END -->
_Pact Support for Quarkus_

[![Version](https://img.shields.io/maven-central/v/io.quarkiverse.pact/quarkus-pact-provider?logo=apache-maven&style=for-the-badge)](https://search.maven.org/artifact/io.quarkiverse.pact/quarkus-pact-provider)

Pact is a polyglot contract testing framework. You can read more about it here: https://pact.io/

To get started, add the dependency:

```
    <dependency>
      <groupId>io.quarkiverse.pact</groupId>
      <artifactId>quarkus-pact-provider</artifactId>
      <version>LATEST</version>
      <!-- <scope>test</scope>--> <!-- See https://github.com/quarkiverse/quarkus-pact/issues/28; for dev mode tests, the scope cannot be test -->
    </dependency>
```

## Limitations 

- At the moment, only pact providers are supported. 

- Provider contract tests do not run in dev mode if the dependency scope is `test` (which would be the most natural scope)

## Features

### Continuous testing/dev mode

This extension allows Pact contract tests to run with `mvn quarkus:dev` and `mvn quarkus:test`.

This project follows the [all-contributors](https://github.com/all-contributors/all-contributors) specification. Contributions of any kind welcome!
