# pathdiff-kotlin in Kotlin

[![GitHub link](https://img.shields.io/badge/GitHub-KotlinMania%2Fpathdiff--kotlin-blue.svg)](https://github.com/KotlinMania/pathdiff-kotlin)
[![Maven Central](https://img.shields.io/maven-central/v/io.github.kotlinmania/pathdiff-kotlin)](https://central.sonatype.com/artifact/io.github.kotlinmania/pathdiff-kotlin)
[![Build status](https://img.shields.io/github/actions/workflow/status/KotlinMania/pathdiff-kotlin/ci.yml?branch=main)](https://github.com/KotlinMania/pathdiff-kotlin/actions)

This is a Kotlin Multiplatform line-by-line transliteration port of [`Manishearth/pathdiff`](https://github.com/Manishearth/pathdiff).

**Original Project:** This port is based on [`Manishearth/pathdiff`](https://github.com/Manishearth/pathdiff). All design credit and project intent belong to the upstream authors; this repository is a faithful port to Kotlin Multiplatform with no behavioural changes intended.

### Porting status

This is a **completed port** of `Manishearth/pathdiff` to Kotlin Multiplatform (`diffPaths` and `diffUtf8Paths` in `io.github.kotlinmania.pathdiff`), matching upstream `src/lib.rs` across all 17 retained KMP targets with zero external runtime dependencies.

---

## About this Kotlin port

### Installation

```kotlin
dependencies {
    implementation("io.github.kotlinmania:pathdiff-kotlin:0.1.1")
}
```

### Building

```bash
./gradlew build
./gradlew test
```

### Targets

- JVM & Android (API 24+)
- Android Native (`androidNativeArm64`, `androidNativeX64`)
- Apple (`macosArm64`, `iosArm64`, `iosSimulatorArm64`, `tvosArm64`, `tvosSimulatorArm64`, `watchosDeviceArm64`, `watchosSimulatorArm64`) + Swift Export (`Pathdiff`)
- Linux (`linuxX64`, `linuxArm64`)
- Windows (`mingwX64`)
- JS & Wasm (`js`, `wasmJs`, `wasmWasi`)

### Porting guidelines

See [AGENTS.md](AGENTS.md) for translator discipline and Rust → Kotlin idiom mapping.

### License

This Kotlin port is distributed under the same dual-license model as the upstream [`Manishearth/pathdiff`](https://github.com/Manishearth/pathdiff): Apache-2.0 OR MIT. See [LICENSE-APACHE](LICENSE-APACHE), [LICENSE-MIT](LICENSE-MIT), and [LICENSE](LICENSE) for the full text.

Original work copyrighted by the pathdiff authors.  
Kotlin port: Copyright (c) 2026 Sydney Renee and The Solace Project.

### Acknowledgments

Thanks to the [`Manishearth/pathdiff`](https://github.com/Manishearth/pathdiff) maintainers and contributors for the original Rust implementation. This port reproduces their work in Kotlin Multiplatform; bug reports about upstream design or behavior should go to the upstream repository.
