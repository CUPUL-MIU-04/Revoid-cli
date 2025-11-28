## **2_building.md** - Modificado:

```markdown
# 🔨️ Building

Build ReVoid CLI from source.

## 📝 Requirements

- Java Development Kit 11 (Azul Zulu JRE or OpenJDK)

## 🏗️ Building

To build ReVoid CLI, follow these steps:

1. Clone the repository:

   ```bash
   git clone git@github.com:cupul-miu-04/revoid-cli.git
   cd revoid-cli
```

1. Build the project:
   ```bash
    ./gradlew build
   ```

[!NOTE]
If the build fails due to authentication, you may need to authenticate to GitHub Packages.
Create a PAT with the scope read:packages here and add your token to ~/.gradle/gradle.properties.

Example gradle.properties file:

```properties
gpr.user = user
gpr.key = key
```

After the build succeeds, the built JAR file will be located at build/libs/revoid-cli-<version>-all.jar.

```