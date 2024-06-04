<div style="text-align: center;">
<h1>UpdateChecker Library</h1>

[![](./src/main/resources/readme/spigotmc-thread-button.png)](https://www.spigotmc.org/threads/mcversion-minecraft-version-data-api.646198/) [![](./src/main/resources/readme/java-docs-button.png)](https://docs.monster2408.com/MCVersion/) [![](./src/main/resources/readme/website-button.png)](https://monster2408.com)

[![](https://jitpack.io/v/xyz.mlserver/UpdateChecker.svg)](https://jitpack.io/#xyz.mlserver/UpdateChecker)
[![Twitter](https://img.shields.io/twitter/follow/monster_2408?style=social)](https://twitter.com/monster_2408)
<!--[![Discord](https://discord.com/api/guilds/1134139990885027890/widget.png)](https://discord.gg/pkXnefCFsX)-->

</div>

## How to use

```java
import xyz.mlserver.logapi.LogAPI;
import xyz.mlserver.updatechecker.SpigotUpdateChecker;

public class Example {
    public static void example() {
        SpigotUpdateChecker updateChecker = new SpigotUpdateChecker(plugin, 12345);
    }
}
```

## Using with Maven

```xml
<repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
</repository>

<dependency>
    <groupId>xyz.mlserver</groupId>
    <artifactId>UpdateChecker</artifactId>
    <version>VERSION</version>
</dependency>
```

## Using with Gradle

```gradle
repositories {
    maven { url 'https://jitpack.io' }
}

dependencies {
    implementation 'xyz.mlserver:UpdateChecker:VERSION'
}
```

### Developer
#### JavaDoc Command
```shell
mvn javadoc:javadoc
```