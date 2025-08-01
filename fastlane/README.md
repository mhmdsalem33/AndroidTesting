fastlane documentation
----

# Installation

Make sure you have the latest version of the Xcode command line tools installed:

```sh
xcode-select --install
```

For _fastlane_ installation instructions, see [Installing _fastlane_](https://docs.fastlane.tools/#installing-fastlane)

# Available Actions

## Android

### android test

```sh
[bundle exec] fastlane android test
```

Run Fastlane Success

### android build_debug_android

```sh
[bundle exec] fastlane android build_debug_android
```

Prepare Android Debug Build

### android build_release_build

```sh
[bundle exec] fastlane android build_release_build
```

Prepare Android Release Build

### android run_tests

```sh
[bundle exec] fastlane android run_tests
```

Run unit tests for android

### android run_release_tests

```sh
[bundle exec] fastlane android run_release_tests
```

Run release unit tests for android

### android run_all_tasks

```sh
[bundle exec] fastlane android run_all_tasks
```

Run All CI TASKS

### android beta_android

```sh
[bundle exec] fastlane android beta_android
```

Prepare Android Build & Distribute to firebase app distribution

----

This README.md is auto-generated and will be re-generated every time [_fastlane_](https://fastlane.tools) is run.

More information about _fastlane_ can be found on [fastlane.tools](https://fastlane.tools).

The documentation of _fastlane_ can be found on [docs.fastlane.tools](https://docs.fastlane.tools).
