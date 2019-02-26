# Network state receiver

## What is this?
Small powerful manager to tracking networking changes and fire changes who listens changes. Written in Kotlin and RxJava.

    
## Requirements

Android 5.0 or later (Minimum SDK level 21)

Android Studio 3.0 (to compile and use)

Eclipse is not supported



## Getting Started
Download Android Studio

Launch Android Studio

Start your new project

Open your project's main Gradle file, in root directory (/build.gradle)

Make sure you are using jcenter() in the repository block (mavenCentral() should work too)

Open your app module Gradle file, for example /app/build.gradle

Click Tools/Android/Sync Project with Gradle Files or click on the Sync icon in the top toolbar

Click Run/Run 'app' to see if it's resolved correctly

This will run the app on your device. You may need to download a newer version of Gradle, which will be available in the Android Studio UI if compile fails


## Using Networking Receiver
Using is very simple. Create new instace of NetworkingReceiver in your module/activity and attach to changes.

## Example to observe changes:


 **@param NetworkType** when you attach on changes, you will get activ network type, that may be the type of ENetworkType(**Wifi, Mobile, None**)



val **mNetworkStateReceiver** = **NetworkStateReceiver(this)**

***mNetworkStateReceiver.publishSubjectNetworking.subscribe({ networkType -> **somethingDo()**})***

 ![test image size](https://github.com/MilanBojic/network-state-receiver/blob/master/image1.png)



**Support**

If you've found an error while using the library, please file an issue. All patches are encouraged, and may be submitted by forking this project and submitting a pull request through GitHub.

