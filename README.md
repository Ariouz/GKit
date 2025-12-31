# GKit

**GraalVM native builds, simplified.**

GKit is a lightweight CLI tool that simplifies building Java projects
and generating GraalVM `native-image` binaries using a single,
portable configuration file.

Instead of relying on verbose Maven or Gradle plugins,
GKit orchestrates your existing build tools and GraalVM
with an explicit and predictable workflow.


## Why GKit?

Building native images with GraalVM is powerful but often painful:
complex plugins, duplicated configuration, CI inconsistencies.

GKit focuses on:
- **Developer Experience** – clear commands, no magic
- **Portable configuration** – one `gkit.yml`, reusable everywhere
- **CI-friendly workflows** – explicit commands, dry-run support

GKit does **not** replace Maven or Gradle.
It orchestrates them.


## Features

- Simple CLI (`gkit build`, `gkit native`)
- Single configuration file (`gkit.yml`)
- Profile support (`--profile`)
- Dry-run mode (`--dry-run`)
- Configuration validation (`gkit check-config`)
- Build requirements checks (`gkit doctor`)
- Variables substitution (`${namespace.key}`)


## Installation

Download the native binary for your OS from the
**GitHub Releases** page and add it to your `PATH`.

Check your installation:
```bash
gkit version
```
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

## Commands
| Command                                          |                   Description                    |
|:-------------------------------------------------|:------------------------------------------------:|
| gkit help                                        |                    	Show help                    |
| gkit version                                     |                  	Show version                   |
| gkit doctor                                      | 	Check environment (Java, GraalVM, native-image) |
| gkit check-config [-p PROFILE]                   |             	Validate configuration              |
| gkit build   [-p PROFILE] [--dry-run] [--native] |           	Build the project artifact            |
| gkit native	[-p PROFILE] [--dry-run]             |             Build the native binary              |

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

### Variable substitution

GKit supports variable substitution inside configuration values using the
`${namespace.key}` syntax.

Variables are expanded after profiles and defaults are applied, and before
any command is executed. Only **string and list fields** support substitution.

Currently supported namespaces:
- `env` – environment variables (e.g. `${env.JAVA_HOME}`)
- `gkit` – variables defined in the variables section of the config (e.g. `${gkit.BUILD_DIR}`)

If a variable cannot be resolved, GKit fails with an explicit error.


### The `variables` field

The optional `variables` field allows you to define reusable, project-local
values directly in the configuration.

These variables can reference each other and are exposed through the
`gkit` namespace.

```yaml
project:
  variables:
      BUILD_DIR: target
      OUTPUT_NAME: my-app
      OUTPUT_PATH: ${gkit.BUILD_DIR}/${gkit.OUTPUT_NAME}
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