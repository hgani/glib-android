
# Linking to this library in your project
- **NOTE: The method below doesn't seem to work on WSL anymore. Use this library as a git submodule instead.**
- Clone this repository on the same directory level as your project. If your project is located in `~/workspace`, put this library in `~/workspace` too.
- Make a link to the modules that your project uses (e.g. `glib-core`) in the project's root directory. See below:

## Linux
- `ln -s ../glib-android/glib-core glib-core`
- `ln -s ../glib-android/glib-map glib-map`
- `ln -s ../glib-android/glib-camera glib-camera`

## Windows
- `mklink /J glib-core ..\glib-android\glib-core`
- `mklink /J glib-map ..\glib-android\glib-map`
- `mklink /J glib-camera ..\glib-android\glib-camera`
- You might need to use PowerShell to execute the above commands. See https://superuser.com/questions/1307360/how-do-you-create-a-new-symlink-in-windows-10-using-powershell-not-mklink-exe

# Check jitpack status
- https://jitpack.io/com/github/hgani/glib-android/VERSION_TAG/build.log

# Configure network security
- Add `android:networkSecurityConfig="@xml/network_security_config"` to the `application` tag in AndroidManifest.xml
  - This will automatically use glib's `network_security_config`
- Reference: https://stackoverflow.com/questions/45940861/android-8-cleartext-http-traffic-not-permitted


