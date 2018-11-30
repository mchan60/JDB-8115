# JDB-8115

RELEASE NOTES FOR HAPPY HEALTHY TUMMY APP
v1.0

New Features
Added diary functionality that allows users to post pictures of their meals
Added Daily, Weekly, and Monthly displays for diary entries
Implemented the application's notification manager
Added ability for users to view, edit, or delete their diary entries
Added menu to allow users to navigate app
Added notifications to alert user to post entries
Added autofill capabilities when adding entries
Added Recipe List and Feedback pages 
 
Fixes
UI for creating notifications is now visible and able to use 
Clicking on a diary entry now shows you the details for that entry instead of a different entry
Camera button on home page now linked to adding entry 

Improvements
Diary Entries are now ordered from most recent post instead of oldest post
UI is updated to a unique design instead of standard Android Studio design
User now lands on Home Page instead of Camera Page
Monthly View now moved to tabs instead of navigation menu

Known Bugs
Creating a blank entry will return the user back to the camera
No display for permissions for camera, notifications, or storage so if user does not do this manually app will crash
No return from camera screen to unless adding an entry 
Switching to front camera in the emulator crashes app
Clicking the Android OS back-arrow when on the main page leads to a blank page and requires the app to be restarted






INSTALL GUIDE FOR HAPPY HEALTHY TUMMY APP
v1.0

Pre-requisites
Must have Android Studio to build, run emulator, and install application
To run application on phone, must have a device with API 28 or higher 
To run application in the emulator, must install an emulator with API 28 or higher

Download
Download/Clone the “dev” branch from GitHub found here: https://github.com/mchan60/JDB-8115/tree/dev

Installation
In Android Studio, build and run the application using the button with the green Play icon
In order to open project, click “Open Project” under File in Android Studio. Select the Happy Healthy Tummy App project folder
Select an emulated device (with API higher than 28) or connect a compatible Android device to the computer
Before launching the application, go into system settings and allow app Permissions for Storage, Camera, and Notification. NOT DOING THIS STEP WILL CAUSE THE APPLICATION TO CRASH

Troubleshooting
With some emulators, the application seems to either crash or not have full functionality:
Make sure emulator has an API higher than 28
Try out different emulators, sometimes specific ones don’t function well with the code
Best to use with connected android device 
If the app crashes on start:
Make sure app permissions are given for storage, camera, and notifications
