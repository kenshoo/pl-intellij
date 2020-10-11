# IntelliJ Plugin for Persistence Layer

This project adds a wizard to ease creations of new PL entities.

# How to setup dev machine

* Add this to `~/.gradle/init.gradle` file:
```
maven { 
   url 'https://jetbrains.bintray.com/intellij-plugin-service' 
}
```    

* Run `./gradlew build` in a command line. This will cause Gradle to cache the dependencies. It is a workaround to an IntelliJ bug when it sometimes fails to access the dependencies.

* Using IntelliJ, open the `build.gradle` file as a project.

* Run using Gradle task `runIde`
