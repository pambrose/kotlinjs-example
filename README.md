# Kotlin/JS Example

Misc Notes:

* JS module identifiers cannot have a "-" character. Set the name of the module that the JS references will use
  in `settings.gradle.kts`.

* The default ktor static configuration will not serve up the `output.js.map` file:

```kotlin
      static("/static") {
        resources()
      }
```

To fix the problem, change it to this:

```kotlin
      static("/static") {
        files("build/distributions")
      }
```
