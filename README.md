# quarkus-pact
<!-- ALL-CONTRIBUTORS-BADGE:START - Do not remove or modify this section -->
[![All Contributors](https://img.shields.io/badge/all_contributors-1-orange.svg?style=flat-square)](#contributors-)
<!-- ALL-CONTRIBUTORS-BADGE:END -->

<!-- ALL-CONTRIBUTORS-BADGE:START - Do not remove or modify this section -->
[![All Contributors](https://img.shields.io/badge/all_contributors-2-blue.svg?style=plastic)](#contributors-)
<!-- ALL-CONTRIBUTORS-BADGE:END -->
_Pact Support for Quarkus_

[![Version](https://img.shields.io/maven-central/v/io.quarkiverse.pact/quarkus-pact-provider?logo=apache-maven&style=for-the-badge&color=blue&style=plastic)](https://search.maven.org/artifact/io.quarkiverse.pact/quarkus-pact-provider)
[![Version](https://img.shields.io/maven-central/v/io.quarkiverse.pact/quarkus-pact-consumer?logo=apache-maven&style=for-the-badge&color=blue&style=plastic)](https://search.maven.org/artifact/io.quarkiverse.pact/quarkus-pact-consumer)

Pact is a polyglot contract testing framework. You can read more about it here: https://pact.io/
This extension ensures Pact works will with Quarkus applications, including with continuous testing.

To get started, add the dependency:

### Provider tests

```
    <dependency>
      <groupId>io.quarkiverse.pact</groupId>
      <artifactId>quarkus-pact-provider</artifactId>
      <version>LATEST</version>
      <!-- <scope>test</scope>--> <!-- See https://github.com/quarkiverse/quarkus-pact/issues/28; for dev mode tests, the scope cannot be test -->
    </dependency>
```

### Consumer tests

```
    <dependency>
      <groupId>io.quarkiverse.pact</groupId>
      <artifactId>quarkus-pact-consumer</artifactId>
      <version>LATEST</version>
      <!-- <scope>test</scope>--> <!-- See https://github.com/quarkiverse/quarkus-pact/issues/28; for dev mode tests, the scope cannot be test -->
    </dependency>
```

## Limitations

- Pact tests do not run in dev mode if the dependency scope is `test` (which would be the most natural scope)

## Features

### Continuous testing/dev mode

This extension allows Pact contract tests to run with `mvn quarkus:dev` and `mvn quarkus:test`.

## Contributors ???

Thanks goes to these wonderful people ([emoji key](https://allcontributors.org/docs/en/emoji-key)):
<!-- ALL-CONTRIBUTORS-LIST:START - Do not remove or modify this section -->
<!-- prettier-ignore-start -->
<!-- markdownlint-disable -->
<table>
  <tbody>
    <tr>
      <td align="center" valign="top" width="14.28%"><a href="https://hollycummins.com"><img src="https://avatars.githubusercontent.com/u/11509290?v=4?s=100" width="100px;" alt="Holly Cummins"/><br /><sub><b>Holly Cummins</b></sub></a><br /><a href="https://github.com/quarkiverse/quarkus-pact/commits?author=holly-cummins" title="Code">????</a> <a href="#maintenance-holly-cummins" title="Maintenance">????</a></td>
    </tr>
  </tbody>
</table>

<!-- markdownlint-restore -->
<!-- prettier-ignore-end -->

<!-- ALL-CONTRIBUTORS-LIST:END -->

This project follows the [all-contributors](https://github.com/all-contributors/all-contributors) specification.
Contributions of any kind welcome!
