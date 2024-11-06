This is a toy project to reproduce the Lombok code leak when using Takari Lifecycle + ECJ + `@SneakyThrows`.
It has just one super silly Java class with a `main()` method calling a private method `dangerousMethod()` annotated with `@SneakyThrows`.

When compiling with the Takari Lifecycle plugin, the generated code leaks Lombok code, thus forcing the `lombok` artifact to be available at runtime:
```
    private void dangerousMethod() {
        try {
            throw new IOException("This is a test");
        } catch (Throwable var2) {
            throw Lombok.sneakyThrow(var2);
        }
    }
```

However, when using the regular Maven Compiler plugin, the generated code is the expected one, i.e.:
```
    private void dangerousMethod() {
        try {
            throw new IOException("This is a test");
        } catch (Throwable var2) {
            throw var2;
        }
    }
```

The Takari Lifecycle plugin configuration is included in a `incremental-compilation-takari` Maven profile. The profile can be deactivated by creating a file `takari.disabled` at the project's root folder (an empty file would suffice). This will lead to the regular Maven Compiler Plugin taking over the compilation process.

NOTE: I followed [these instructions](https://projectlombok.org/setup/ecj) in order to setup the ECJ-Lombok integration properly.
