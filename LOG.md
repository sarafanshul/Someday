
## Log
- Date - 16-04-21
	- start from base , colors and cards are working fine
	- added onclick listenr for recycler view
	- Fragment View in Recycler View seems "Hard" to implement 
	- added current weekday rotate support
	- Shift from Fragment to Activity Done
	- Data comes Back from new Activity and updates the recyclerView Adapter

- Date - 17-04-21
	- Added RecyclerView in Second Activity
	- Data Updates works "Smoothly"
	- Alert Dialog for data update implemented
	- Added Swipe to Delete with Undo SnackBar
	- Added red Color and Delete Icon in Swipe To Delete
	- replaced Toasts to SnackBar 
	- replaced *Round Button to FAB
	- fixed Snackbar overlap to FAB
	- Good Gradients added and Icons and Theme Colors
	- Added Title Divider using styles
	- Added red Color and Delete Icon in Swipe To Delete
	- Changed CardView to Material TextView in RecyclerView - 2
	- Alert Dialog to Material IO AlertDialog

- Date - 18-04-21
	- Added Splash Screen
	- Long Click to Change Subtext
	- Database Model and View Model added with Live Updates
	- insertOrUpdate seems to work on IO thread
	- Database Updates But only from main Activity
	- Today on Top does not work , replaced with today Highlighted or more Width ?
	- Repository and View Models Extended

- Date - 19-04-21
	- Basic MVVM Model Implemented
	- Delete Works
	- So do Update
	- ViewModel for activity 2 "done"

- Date - 20-04-21
	- Right swipe for "Next Week" Added
	- Recycler View Ordering Adjusting by Customizing DB Queries 
	- Implementation for change in subtext from inside added
	- Swipible Menu added with settings and other options
	- Shared Prefrences to Store Settings Data
	- Notification Class Implemented
	- Support toggle for removing notifications

- Date - 22-04-21
	- Expandable FAB added in ActivityInfo
	- Support for change of subtext in Second Activity
	- WorkManager API implemented for schudling notifications tasks in background
	- Long press to expand card added
	- Fonts changed to product sans (NOT OPEN SOURCE)(CHANGE BEFORE PUBLISHING)

- Date - 23-04-21
	- Singleton Class for NotificationUtil for easy access in Work-Manager
	- Work Manager Works 
	- Work-Manager starts from Settings.OnPrefChanged.. insted of main (intended)
	- Async Notifications in Work-Manager Works 

- Date 24-04-21
	- Notifiaction style and implementation changet to inboxView
	- Reset Database functionality added

- Date 25-05-21
	- Changed fonts to Open-Source Roboto
	- Preview Images
	- Project Completed !

## TODO

- Open
	-	Ordering based on Priority
	-	Notification gap time

- Back-End
	-	~~Implemnt CardView in Recycler View~~
	-	~~Implement layout Structure~~
	-	~~Implemnt ItemOnclick in Recycler View~~
	-	~~Shift from Fragment to Activity~~
	-	~~RecyclerView in Second Activity for displaying Tasks~~
	-	~~AlertDialog for "Round Button"~~
	-	~~Delete Items from List and Update~~
	-	~~Add Swipe To Delete and Undo~~
	-	~~fixed Snackbar overlap to FAB~~
	-	~~Added Title Divider~~
	-	~~Implement Database(Stage 1)~~
	-	~~Pre-Populate Database (not HardCoded)~~
	-	~~ViewModel Implementation~~
	-	~~Functionality to Nuke Database (Start Fresh)~~
	-	~~Notification Bugs~~
	-	~~Bugs in Workmanager~~
	-	~~Implement Work-Manager in Settings.OnPrefChanged.. insted of main~~
	-	~~Add sequential task in Work-manager for Data-Collection from DB~~

- Aesthetics
	-	~~Long Press Expands Card Height~~
	-	~~Padding/Margin~~ 
	-	~~Highlight the current day~~
	-	~~Good Gradients~~ 
	-	~~Round Button~~
	-	~~Good CheckBox~~
	-	~~App Icon~~
	-	~~Fonts~~
	-	~~Today on Top~~
	-	~~Credits~~
