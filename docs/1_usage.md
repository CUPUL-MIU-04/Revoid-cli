# 1_usage.md - Modificado:

```markdown
# 🛠️ Using ReVoid CLI

Learn how to use ReVoid CLI.
The following examples will show you how to perform basic operations.
You can list patches, patch an app, uninstall, and install an app.

## 🚀 Show all commands

```bash
java -jar revoid-cli.jar -h
```

📃 List patches

```bash
java -jar revoid-cli.jar list-patches --with-packages --with-versions --with-options patches.rvp
```

💉 Patch an app

To patch an app using the default list of patches, use the patch command:

```bash
java -jar revoid-cli.jar patch -p patches.rvp input.apk
```

You can also use multiple RVP files:

```bash
java -jar revoid-cli.jar patch -p patches.rvp -p another-patches.rvp input.apk
```

To change the default set of enabled or disabled patches, use the option -e or -d to enable or disable specific patches.
You can use thelist-patches command to see which patches are enabled by default.

To only enable specific patches, you can use the option --exclusive combined with -e.
Remember that the options-e and -d match the patch's name exactly. Here is an example:

```bash
java -jar revoid-cli.jar patch -p patches.rvp --exclusive -e "Patch name" -e "Another patch name" input.apk
```

You can also use the options --ei or --di to enable or disable patches by their index.
This is useful,if two patches happen to have the same name, or if typing the names is too cumbersome.
To know the indices of patches,use the command list-patches:

```bash
java -jar revoid-cli.jar list-patches patches.rvp
```

Then you can use the indices to enable or disable patches:

```bash
java -jar revoid-cli.jar patch -p patches.rvp --ei 123 --di 456 input.apk
```

You can combine the option -e, -d, --ei, --di and --exclusive. Here is an example:

```bash
java -jar revoid-cli.jar patch -p patches.rvp --exclusive -e "Patch name" --ei 123 input.apk
```

[!TIP]
You can use the option -i to automatically install the patched app after patching.
Make sure ADB is working:

```bash
 adb shell exit
```

[!TIP]
You can use the option --mount to mount the patched app on top of the un-patched app.
Make sure you have root permissions and the same app you are patching and mounting over is installed on your device:

```bash
adb shell su -c exit
adb install input.apk
```

Patches can have options you can set using the option -O alongside the option to include the patch by name or index.
To know the options of a patch,use the option --with-options when listing patches:

```bash
java -jar revoid-cli.jar list-patches --with-options patches.rvp
```

Each patch can have multiple options. You can set them using the option -O.
For example,to set the options for the patch with the name Patch name
with the keykey1 and key2 to value1 and value2 respectively, use the following command:

```bash
java -jar revoid-cli.jar patch -p patches.rvp -e "Patch name" -Okey1=value1 -Okey2=value2 input.apk
```

If you want to set the option value to null, you can omit the value:

```bash
java -jar revoid-cli.jar patch -p patches.rvp -i "Patch name" -Okey1 input.apk
```

[!WARNING]
Option values are usually typed. If you set a value with the wrong type, the patch can fail.
The value types can be seen when listing patches with the option --with-options.

Example option values:

· String: string
· Boolean: true, false
· Integer: 123
· Double: 1.0
· Float: 1.0f
· Long: 1234567890, 1L
· List: [item1,item2,item3]
· List of type Any: [item1,123,true,1.0]
· Empty list of type Any: []
· Typed empty list: int[]
· Typed and nested empty list: [int[]]
· List with null value and two empty strings: [null,\'\',\"\"]

Quotes and commas escaped in strings (\", \', \,) are parsed as part of the string.
List items are recursively parsed, so you can escape values in lists:

· Escaped integer as a string: [\'123\']
· Escaped boolean as a string: [\'true\']
· Escaped list as a string: [\'[item1,item2]\']
· Escaped null value as a string: [\'null\']
· List with an integer, an integer as a string and a string with a comma, and an escaped list: [`123,\'123\',str\,ing`,`\'[]\'`]

Example command with an escaped integer as a string:

```bash
java -jar revoid-cli.jar -p patches.rvp -e "Patch name" -OstringKey=\'1\' input.apk
```

📦 Install an app manually

```bash
java -jar revoid-cli.jar utility install -a input.apk
```

[!TIP]
You can use the option --mount to mount the patched app on top of the un-patched app.
Make sure you have root permissions and the same app you are patching and mounting over is installed on your device:

```bash
adb shell su -c exit
adb install input.apk
```

🗑️ Uninstall an app manually

Here <package-name> is the package name of the app you want to uninstall:

```bash
java -jar revoid-cli.jar utility uninstall --package-name <package-name>
```

If the app is mounted, you need to unmount it by using the option --unmount:

```bash
java -jar revoid-cli.jar utility uninstall --package-name <package-name> --unmount
```

[!TIP]
By default, the app is installed or uninstalled to the first connected device.
You can append one or more devices by their serial to install or uninstall an app on your selected choice of devices:

```bash
java -jar revoid-cli.jar utility uninstall --package-name <package-name> [<device-serial> ...]
```

```