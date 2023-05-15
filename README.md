# quarkus-pact
[![All Contributors](https://img.shields.io/github/all-contributors/quarkiverse/quarkus-pact?style=for-the-badge&color=blue&style=plastic)](#contributors)

_Pact Support for Quarkus_

provider: [![Version](https://img.shields.io/maven-central/v/io.quarkiverse.pact/quarkus-pact-provider?logo=apache-maven&style=for-the-badge&color=blue&style=plastic)](https://search.maven.org/artifact/io.quarkiverse.pact/quarkus-pact-provider) &emsp; consumer: [![Version](https://img.shields.io/maven-central/v/io.quarkiverse.pact/quarkus-pact-consumer?logo=apache-maven&style=for-the-badge&color=blue&style=plastic)](https://search.maven.org/artifact/io.quarkiverse.pact/quarkus-pact-consumer)

Pact is a polyglot contract testing framework. You can read more about it here: https://pact.io/
This extension ensures Pact works will with Quarkus applications, including with continuous testing.


To get started, add the dependencies. Choose a 1.x version for Quarkus 3, and a 0.x version for Quarkus 2. 

### Provider tests

```
    <dependency>
      <groupId>io.quarkiverse.pact</groupId>
      <artifactId>quarkus-pact-provider</artifactId>
      <version>LATEST</version>
      <scope>test</scope>
    </dependency>
```

### Consumer tests

```
    <dependency>
      <groupId>io.quarkiverse.pact</groupId>
      <artifactId>quarkus-pact-consumer</artifactId>
      <version>LATEST</version>
      <scope>test</scope>
    </dependency>
```

## Features

### Continuous testing/dev mode

This extension allows Pact contract tests to run with `mvn quarkus:dev` and `mvn quarkus:test`.

## Samples and resources

- [Introduction to Pact](https://docs.pact.io/)
- How to use Pact's [Java library](https://docs.pact.io/implementation_guides/jvm)
- [Quarkus Superheroes sample](https://github.com/quarkusio/quarkus-super-heroes)
- [Eric Deandrea](https://developers.redhat.com/author/eric-deandrea) and [Holly Cummins](https://hollycummins.com)
  recently spoke about contract testing with Pact and used the Quarkus Superheroes for their
  demos. [Watch the replay](https://www.youtube.com/watch?v=vYwkDPrzqV8)
  and [view the slides](https://hollycummins.com/modern-microservices-testing-pitfalls-devoxx/) if you'd like to learn
  more about contract testing.

## Contributors ✨

Thanks goes to these wonderful people ([emoji key](https://allcontributors.org/docs/en/emoji-key)):
<!-- ALL-CONTRIBUTORS-LIST:START - Do not remove or modify this section -->
<!-- prettier-ignore-start -->
<!-- markdownlint-disable -->
<table>
  <tbody>
    <tr>
      <td align="center" valign="top" width="14.28%"><a href="https://hollycummins.com"><img src="https://avatars.githubusercontent.com/u/11509290?v=4?s=100" width="100px;" alt="Holly Cummins"/><br /><sub><b>Holly Cummins</b></sub></a><br /><a href="https://github.com/quarkiverse/quarkus-pact/commits?author=holly-cummins" title="Code">💻</a> <a href="#maintenance-holly-cummins" title="Maintenance">🚧</a></td>
      <td align="center" valign="top" width="14.28%"><a href="https://github.com/michalvavrik"><img src="https://avatars.githubusercontent.com/u/43821672?v=4?s=100" width="100px;" alt="Michal Vavřík"/><br /><sub><b>Michal Vavřík</b></sub></a><br /><a href="https://github.com/quarkiverse/quarkus-pact/commits?author=michalvavrik" title="Documentation">📖</a></td>
    </tr>
  </tbody>
</table>

<!-- markdownlint-restore -->
<!-- prettier-ignore-end -->

<!-- ALL-CONTRIBUTORS-LIST:END -->

This project follows the [all-contributors](https://github.com/all-contributors/all-contributors) specification.
Contributions of any kind welcome!
