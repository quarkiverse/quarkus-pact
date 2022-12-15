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

## Contributors ✨

Thanks goes to these wonderful people ([emoji key](https://allcontributors.org/docs/en/emoji-key)):

<!-- ALL-CONTRIBUTORS-LIST:START - Do not remove or modify this section -->
<!-- prettier-ignore-start -->
<!-- markdownlint-disable -->
<table>
  <tr>
    <td align="center"><a href="http://gastaldi.wordpress.com"><img src="https://avatars.githubusercontent.com/u/54133?v=4?s=100" width="100px;" alt=""/><br /><sub><b>George Gastaldi</b></sub></a><br /><a href="https://github.com/quarkiverse/quarkus-prettytime/commits?author=gastaldi" title="Code">💻</a> <a href="#maintenance-gastaldi" title="Maintenance">🚧</a></td>
    <td align="center"><a href="https://github.com/mkouba"><img src="https://avatars.githubusercontent.com/u/913004?v=4?s=100" width="100px;" alt=""/><br /><sub><b>Martin Kouba</b></sub></a><br /><a href="https://github.com/quarkiverse/quarkus-prettytime/commits?author=mkouba" title="Code">💻</a></td>
  </tr>
</table>

<!-- markdownlint-restore -->
<!-- prettier-ignore-end -->

<!-- ALL-CONTRIBUTORS-LIST:END -->

This project follows the [all-contributors](https://github.com/all-contributors/all-contributors) specification. Contributions of any kind welcome!
