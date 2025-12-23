# GKit
GraalVM native builds, simplified.

## POC Project

GKit is a CLI tool for GraalVM-based projects.

Instead of configuring verbose Maven or Gradle plugins,
GKit uses a single `gkit.yml` file to describe how your native image
should be built.

GKit will work with Maven, Gradle, or standalone JARs.

## Main goals:
- Improved DevUx
- Portable build configuration
- CI friendly
