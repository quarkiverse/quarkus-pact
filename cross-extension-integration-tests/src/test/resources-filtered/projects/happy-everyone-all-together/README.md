This project combines consumer and provider tests. Some of the names overlap here because of copy and pasting, but the
realistic scenario is that the consumer and provider of the *same* service would not be in the same project, because
that would be silly. :)

You can run this sample project independently to debug issues. This project is a filtered resource, so you will need to
run from the filtered output directory, not the source directory (
usually `target/test-classes/projects/happy-everyone-alltogether-processed`).

Use

```bash
mvn verify
```

or, for continuous testing and dev mode,

```bash
mvn quarkus:dev
```

If you do want to run in the source directory, you will need to edit `pom.xml` to add temporary versions for these
properties:

- `quarkus.version`
- `project.version`