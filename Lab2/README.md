# Lab 2
Today’s lab will introduce the Android Activity Lifecycle by way of demonstrating how to create and interact with multiple Activities. We will also demonstrate lifecycle methods and how they relate to volatile device configurations such as screen rotation.

#### Pre-Lab
Make sure you are familiar with the following sections on Activities in the Developer API Guide before getting started on the lab:
* http://developer.android.com/guide/components/activities.html
* http://developer.android.com/guide/components/activities.html#Lifecycle

#### Objectives
* interact with ```Activity``` Layout Resources programmatically
* create and add Listeners to Button Resources
* the Android Lifecycle methods
* Intents, and how to start new activities programmatically

When an Android application provides more than one functionalitity, for instance adding contact information rather than just selecting an existing contact from a list, the applications are composed of multiple activities each serving their specific duty. This is a design decision by the Android engineering team and it dictates how applications are expected to generally behave in the Android OS.   

Each ```Activity``` serves one major function. This modularity ensures a more maintainable codebase as well as the ability for applications to interact with individual activities accessible from other applications on the device. Given the dynamic nature of device configurations, activities must be able to respond quickly to a user’s ability to change screen orientation; keyboard layout; language display; and supply correct input to fields within the application that is currently undergoing these changes.

#### Example
An email application may have one ```Activity``` to navigate a list of user emails, another ```Activity``` for reading a particular email, and another ```Activity``` for responding to an email. Each ```Activity``` will have sub-functionality that serves to achieve some goal central to that particular ```Activity```.

For instance, in the read email ```Activity``` a user may be able to delete, archive, or choose to respond to an email.  These sub-functionalities may in turn  initiate other activities that deliver functionality fit for accomplishing the next goal of the application based on the user's choice. If the user decided to respond to an email, the application would start the ```Activity``` the developer has decided should serve that function to the user. The activity would begin, its layout and state would be loaded, the user would craft their email, hit send, and they'd be returned to, say, their email list, or to read the next email from the list, depending how the developer decided to implement return from sending email behavior.

#### Managing the Activity Lifecycle

Knowing that applications are made up of several ```Activity``` instances, it's important to know how ```Activity``` states are handled for an application running on a device. Each application is assigned a ```Task``` backstack. This stack consists of a hierarchy of ```Activity``` accesses within an application’s lifecycle. The most recent task item on this backstack will be the ```Activity``` that sits in the foreground and is active for the user to interact with (resumed, or running). Each successive ```Activity``` in the stack “underneath” this current ```Activity```/```Task``` and can be accessed by hitting either the device back button or a software key back button. As eluded to above, applications can also contain activities on their ```Task``` backstack that do not strictly belong to their codebase; you can call activities in from other applications to meet some functionality your application needs but is commonly provided by another application. Cameras and email applications are both common examples.

Other applications on the device that have public activities within their codebase allow you to use their functionality from within your application; for instance, offering users the ability to send an email from within your application, but sending out an intent to be captured by their preferred email application, or offer them the choice of email applications available on the device simply by announcing the intent of sending an email. The device handles the rest!

From the pre-lab reading regarding managing the ```Activity``` lifecycle you learned that, generally, an ```Activity``` is either resumed (running), paused, or stopped. Which state an ```Activity``` is in determines how a user can or cannot interact with the ```Activity``` and whether or not the ```Activity``` is at risk of being destroyed by the OS if device resources require it. We must therefore secure ```Activity``` changes (and later, data) to ensure user-state is not lost if the application state is destroyed in memory to recoup resources.

#### Activity States and Their Meaning
![](http://i.imgur.com/ok4PARv.png)

**Fig. 1** [The Android Activity Lifecycle](http://developer.android.com/guide/components/activities.html#ImplementingLifecycleCallbacks)

All states an ```Activity``` can be in are a result of system calls made to the Android operating system from within an ```Activity```'s Java implementation. These method calls are made on behalf of the application based on multiple factors such as user actions, low system resources, and remembering ```Activity``` ordering in the task stack for each application.

Developers who understand this ```Activity``` lifecycle thoroughly can ensure a smooth user experience through saving and restoring user state at the right points within the ```Activity``` lifecycle. Being mindful about how much time is required to handle state changes will affect the user experience. Long save and load times can translate into frustrated users and reduce favor for your application.

#### Defining Your Activity Response to State Change

To control how your application’s activities respond to state changes you must ```@Override``` the system call methods within each ```Activity```. By doing so you can include custom behavior for how your ```Activity``` behaves during that particular state change within the ```Activity``` lifecycle.

There are 7 states within the lifecycle, and each can be ```@Override```. In this lab we will ```@Override``` all but ```onDestroy()```.

```java
	• onCreate(Bundle savedInstanceState)
	• onStart()
	• onResume()
	• onPause()
	• onRestart()
	• onStop()
	• onDestroy()
```

#### Procedure
###### Initialize Project State for Lab 2

* Open Android Studio
* Import project: SKELETON - Lab2ActivityLifecycle

With the project imported, navigate to the layout file associated with Activity One. You will be presented with a layout view containing some text and a button. Your first goal for this lab is to wire these TextViews containing lifecycle method call counts. They should contain values relevant to the number of times their respective system call functions have been activated for Activity One.  Each TextView has been assigned its own unique ID within the Android project.

**1.** Click on each TextView containing a lifecycle method name and count in ActivityOne and write down its unique id value, obtainable from the Properties panel.
	Also click on the Button and write down its unique ID.

You will use these IDs to programmatically capture references to these layout resources in your ActivityOne.java. This will allow you to update their contents and, in the case of the button, perform an operation such as starting a second ```Activity```.

Open ActivityOne.java. Inside the source code are ```HINTs``` and ```TODO``` items that require your attention. 	

**2.** Complete TODO 2.

Android Studio will alert you that it does not recognize ```TextView``` objects and prompt you to Alt+Enter to import this code. Go ahead and do so. Your imports list has grown!

With programmatic placeholders for Android resources now ready to be used, we can start capturing references from the layout we looked at when when we first opened this project.  Resource capture should occur when the application is first created, which takes place during the ```onCreate()``` system method call. Each system method call must always make a call to its super to ensure that the proper Android operating system functions will be combined with whatever additional functionalities you describe.

Take note of ```ActivityOne.java```’s ```onCreate()``` method; it makes a call to its super, passes super any bundled savedInstanceState it received and sets the layout for the activity using ```setContentView(R.layout.activity_one)```. The use of ```R. is Android’s``` way of providing a method an Android Resource ID number. These unique numbers are assigned and handled by the Android SDK via Android Studio and get passed along as a reference by using the name of the resource file containing the item they are interested in. Here, the .XML layout file activity_one is passed as a resource ID, and its contents get used to describe the layout we saw in the editor.

Hint 4 in the Skeleton project source code shows how these references to resources are obtained programmatically. You can also see that Android Button Objects have listeners attached to them very similarly to that of classic Java GUI Buttons.

**3.** Complete TODO 3.

See the documentation for intents for help: [android.content.Intent](http://developer.android.com/reference/android/app/Activity.html#startActivity)

Great! But we don’t have a second activity java class yet, as we can see from the red ActivityTwo in our Intent statement. To fix this, right click mobiledev.unb.ca.lab2activitylifecycle under the Java project structure folder. Select New → Java Class and name it ActivityTwo. Just leave this class empty for now. We'll finish it later.

**4.** Complete TODO 4 in ```ActivityOne```. Use the Button’s ```findViewByID``` implementation example to similarly capture resource references to each of the four ```TextView``` objects you programmatically created. These will be used to update their respective counter values later.

**5.** Find and complete TODO 5 following the initial example. Note that ```CREATE_VALUE``` is a ```final``` ```String``` declared at the top of ```ActivityOne```. ```START_VALUE```, ```RESUME_VALUE```, and ```RESTART_VALUE``` have already been declared as well. These help identify values we pass around in a ```Bundle```. Note also the Skeleton code already contains ```count``` variables for each system call. These are the values we pass along with our ```Bundle```.

This is how a ```Bundle``` is created to save activity state when a new activity is called and added to the application’s ```Task``` stack. Having such a ```Bundle``` allows you to use the lifecycle methods to reload basic stateful elements. ```onSaveInstanceState()``` is called as an ```Activity``` is stopped, meaning it is no longer the activity of immediate user interest in the application.

With the ```Bundle``` programmatically created, we can now use the test of whether a ```Bundle``` exists or not to update our counter values. Whenever activities are stopped in favor of running another activity, their running information is lost. If that information is not backed up in a ```Bundle``` or via a local or online database (more on this in the future), then the ```Activity``` will be recreated fresh as if it were running for the first time.

We can now unbundle any existing data that may have been saved in the ```onSaveInstanceState()``` method by checking for an existing ```Bundle``` in our ```onCreate()``` implementation.

**6.** Complete TODO 6 following the initial example. This is how a ```Bundle``` is unpacked to recreate an activity’s existing state before it was stopped.

**7.** Complete TODO 7 following the initial example.

**8.** Complete TODO 8, increment each count variable in its respective lifecycle method call ```onCreate()```, ```onStart()```, ```onResume()```, ```onRestart()```. A call to the ```updateCounts()``` method modified above (in TODO 7) has already been included in each activity lifecycle method we are overriding.  (Make sure you increment the counts before the ```updateCounts()``` method is called or else the new value will not be reflected in the text update!)

At this point the entire application is almost entirely wired for two activities; however, we need to implement ```ActivityTwo```. Copy and paste the code from  ```ActivityOne.java``` into ```ActivityTwo.java```. Be sure to change your class name, set the appropriate layout resource file in ```setContentView()``` and make sure to remove the ```Button``` listener code in ```onCreate()```; the ```activity_two.xml``` layout file does not include any button to reference.

Now all that's missing is defining the existence of ```ActivityTwo.java``` (which itself is the programmatic instance of ```ActivityTwo```) in the ```AndroidManifest.xml```

Open ```AndroidManifest.xml``` and locate the ```<application>``` and ```<activity>``` tags. Notice how your application is defined as having ```ActivityOne``` as the application’s MAIN ```<intent-filter>``` with a Category ```LAUNCH```. This allows the defined activity to be the first to run when your application is started.

**9.** After the declaration of this first activity is ended ```</activity>```, create a new ```<activity>``` tag and use ```name=”.ActivityTwo”```.

Your application is finally ready to run! On an emulator or device, run the application.

Take note of the initial values for ```ActivityOne```. Click the ```Button``` to access ```ActivityTwo``` and you’ll see the same values. Use the back ```Button``` to navigate to ```ActivityOne``` again. Notice the values have changed! Rotate your device to the left, back to vertical, and then to the right. Notice the values change with each successive rotation. Once again access ```ActivityTwo```. The values are still the initial values!

Why is this? ```ActivityTwo``` is coded for behavior much like ```ActivityOne```; go ahead and rotate the device several times while in ```ActivityTwo``` and you will note the values change. So why do the values always reinitialize once we leave ```ActivityTwo``` and return to ```ActivityOne```? This demonstrates the application ```Task``` back stack mentioned earlier.

When the back button is being used to navigate away from ```ActivityTwo```, we the user are telling the Android operating system lifecycle that we are no longer interested in interacting with ```ActivityTwo``` (whether we are or not doesn't matter, that's how the design pattern interprets this behavior). As such, Android destroys this activity. Each time we successively re-enter ```ActivityTwo``` from ```ActivityOne```’s ```Button```, we are creating an entire new instance of ```ActivityTwo```. No previous existence of ```ActivityTwo``` remains on the backstack once we use the back button to navigate away. ```ActivityOne``` remained on this back stack and that is why it retains its values.

Even if you use the Device's Home Button to navigate away from ```ActivityOne```, it still exists as the top-most activity in your application’s ```Task``` back stack. As such, navigating to home and reopening your app will reveal the same values as your most recent interaction. This will persist until you use the back button to manually kill ```ActivityOne``` (or swipe the application away from your list of running application backstacks in the Android task manager window).

In future labs we will investigate maintaining the state of an ```Activity``` even if it is destroyed by using more permanent device storage.

**10. Deliverable** Kill your application and start it fresh, holding it in the upright portrait mode. Navigate to ```ActivityTwo``` and back twice, rotate the device to your right into landscape, left back to portrait, and again left into landscape. Press the home button, reopen your application from the icon deck. Record the values displayed under your ```ActivityOne``` system call totals.

* onCreate() calls: __________________________
* onStart() calls: ___________________________
* onResume() calls: __________________________
* onRestart() calls: _________________________
