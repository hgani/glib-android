
# Linking in Windows

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


