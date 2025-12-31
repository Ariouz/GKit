# GKit

**GraalVM native builds, simplified.**

GKit is a lightweight CLI tool that simplifies building Java projects
and generating GraalVM `native-image` binaries using a single,
portable configuration file.

Instead of relying on verbose Maven or Gradle plugins,
GKit orchestrates your existing build tools and GraalVM
with an explicit and predictable workflow.

---

## Why GKit?

Building native images with GraalVM is powerful but often painful:
complex plugins, duplicated configuration, CI inconsistencies.

GKit focuses on:
- **Developer Experience** – clear commands, no magic
- **Portable configuration** – one `gkit.yml`, reusable everywhere
- **CI-friendly workflows** – explicit commands, dry-run support

GKit does **not** replace Maven or Gradle.
It orchestrates them.

---

## Features

- Simple CLI (`gkit build`, `gkit native`)
- Single configuration file (`gkit.yml`)
- Profile support (`--profile`)
- Dry-run mode (`--dry-run`)
- Configuration validation (`gkit check-config`)
- Build requirements checks (`gkit doctor`)

---

## Installation

Download the native binary for your OS from the
**GitHub Releases** page and add it to your `PATH`.

Check your installation:
```bash
gkit version
```
---
# Quick start

### Create a gkit.yml
```yaml
project:
    name: my-app
    mainClass: com.example.Main
    
build:
    artifact:
        command: mvn clean package
        path: target/my-app.jar
    
nativeImage:
      output: target/my-app

profiles:
  dev:
      build:
        artifact:
          command: mvn clean package -Pdev
          path: target/my-app-dev.jar
    
      nativeImage:
        output: target/my-app-dev
```

### Build
Build the JAR
```bash
gkit build [-p PROFILE]
```

Build the native binary
```bash
gkit native [-p PROFILE]
```

Build both at once
```bash
gkit build --native [-p PROFILE]
```

---
## Commands
| Command                                          |                   Description                    |
|:-------------------------------------------------|:------------------------------------------------:|
| gkit help                                        |                    	Show help                    |
| gkit version                                     |                  	Show version                   |
| gkit doctor                                      | 	Check environment (Java, GraalVM, native-image) |
| gkit check-config [-p PROFILE]                   |             	Validate configuration              |
| gkit build   [-p PROFILE] [--dry-run] [--native] |           	Build the project artifact            |
| gkit native	[-p PROFILE] [--dry-run]             |             Build the native binary              |

---
# Configuration (gkit.yml)

GKit is configured using a single gkit.yml file.

### Structure
```yaml
project:
build:
nativeImage:
profiles:
```

### Profiles
Profiles override global configuration values.
```bash
gkit build --profile dev
```

```yaml
profiles:
  dev:
    build:
      artifact:
        command: mvn clean package -Pdev
```

---

## Contributing

GKit is still a **young project** and is actively evolving.

Contributions, feedback, and suggestions are very welcome:
- bug reports
- feature requests
- documentation improvements
- code contributions

If you find GKit useful or have ideas to improve it, feel free to open
an issue or a pull request.

Even small contributions are appreciated.