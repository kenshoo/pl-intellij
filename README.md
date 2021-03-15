# IntelliJ Plugin for Persistence Layer

This project adds a wizard to ease creations of new PL entities.

* Right click on the package where you want to create the PL entity. On the menu, choose `New` -> `PL Entity`. 

![image](https://user-images.githubusercontent.com/10692534/96195312-1a21ed80-0f55-11eb-84c0-9961b930cf15.png)

* In the dialog, fill the entity name (for Java classes) and SQL table name. Then, fill the sql fields. 

![image](https://user-images.githubusercontent.com/10692534/96195479-8a307380-0f55-11eb-8180-0a8e6d7e1bcd.png)

* This will generate all the required classes (Jooq table, flowConfig, etc...) so you can immediately start working with PL commands.

# Manual installation

In case the latest plugin is not yet available in the marketplace within Intellij, you can manually download it [here](https://plugins.jetbrains.com/plugin/download?rel=true&updateId=99885). 
Then, you can install it from the disk like this: 

![image](https://user-images.githubusercontent.com/10692534/96195881-a7b20d00-0f56-11eb-9e24-5e0ebd9a36d1.png)


# Limitations (or "coming soon", ha ha....)
* No relations supported yet.
* Cannot reuse the JOOQ tables already existing in the current project.
* Cannot edit existing entities (only creation flow supported).

# Project setup (for plugin developers)

* Add this to `~/.gradle/init.gradle` file:
```
maven { 
   url 'https://jetbrains.bintray.com/intellij-plugin-service' 
}
```    

* Run `./gradlew build` in a command line. This will cause Gradle to cache the dependencies. It is a workaround to an IntelliJ bug when it sometimes fails to access the dependencies.

* Using IntelliJ, open the `build.gradle` file as a project.

* Run using Gradle task `runIde`

# How to deploy
(Sorry, no automatic build for this)
1. Increase version number in `build.gradle`
2. Write some release notes in element `<change-notes>` in file `plugin.xml`
3. Run gradle task `buildPlugin`
4. Find the ZIP on `./build/distributions/pl-intellij-plugin-0.{version}.zip`
5. Login to https://plugins.jetbrains.com/plugin/15212-kenshoo-persistence-layer and upload the ZIP
