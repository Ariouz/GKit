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

### Native build args

You can define arguments to be passed to GraalVM **native-image** in the `nativeImage.buildArgs` section of your config.

Supported arguments are defined in the code: [NativeBuildArg.java](src/main/java/fr/ariouz/gkit/build/image/arg/NativeBuildArg.java).

If you need to pass an argument **not supported by default**, use `rawArgs` as a list of strings.

#### Example:

```yaml
nativeImage:
  buildArgs:
    - fallbackImage: false                  # Boolean flag
    - initializeAtBuildTime: [foo, bar]     # List of classes to initialize at build time
    - rawArgs: ["-Ob"]                      # Any custom native-image arguments
```

#### Notes:

- **Boolean flags** (`fallbackImage: false`) will be converted to `--no-fallback` automatically.
- **List arguments** (`initializeAtBuildTime: [foo, bar]`) will generate repeated flags:

```bash
  --initialize-at-build-time=foo --initialize-at-build-time=bar
```

- **Raw args** are passed **as-is** to native-image.

- If a [profile](#Profiles) overrides a flag:
    - New values for lists are **concatenated** with the base values.
    - Boolean flags or single-value keys are **replaced**.

#### Example:
**Base `nativeImage.buildArgs`:**

```yaml
nativeImage:
  buildArgs:
    - fallbackImage: false
    - initializeAtBuildTime: [foo, bar]
    - verbose: false
```
**Dev profile `nativeImage.buildArgs`:**

```yaml
nativeImage:
  buildArgs:
    - initializeAtBuildTime: [foobar]
    - verbose: true
    - static: true
```

**Generated CLI**
```bash
--no-fallback \
--initialize-at-build-time=foo \
--initialize-at-build-time=bar \
--initialize-at-build-time=foobar \
--verbose \
--static
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