# Weekday Plannar
This app use Recycler View and Card View at Core for card management

## Preview

Main Screen App Layout

- <details><summary>Show</summary>

	[![prototype 1](/app_images/preview_v_1.png "prototype 1")](/app_images/preview_v_1.png "prototype 1")

</details>

Second Activity Layout

- <details><summary>Show</summary>

	[![prototype 2](/app_images/preview_v_9.png "prototype 9")](/app_images/preview_v_9.png "prototype 9")

</details>

Input Task Layout

- <details><summary>Show</summary>

	[![prototype 3](/app_images/preview_v_8.png "prototype 8")](/app_images/preview_v_8.png "prototype 3")

</details>


## Log
- Date - 16-04-21
	- start from base , colors and cards are working fine
	- <details><summary>Preview 1</summary>

		[![prototype 1](/app_images/preview_v_1.png "prototype 1")](/app_images/preview_v_1.png "prototype 1")

	</details>

	- added onclick listenr for recycler view
	- Fragment View in Recycler View seems "Hard" to implement 
	- added current weekday rotate support
	- <details><summary>Preview 2</summary>

		[![prototype 2](/app_images/preview_v_2.png "prototype 2")](/app_images/preview_v_2.png "prototype 2")

	</details>
	
	- Shift from Fragment to Activity Done
	- Data comes Back from new Activity and updates the recyclerView Adapter

- Date - 17-04-21
	- Added RecyclerView in Second Activity
	- Data Updates works "Smoothly"
	- Alert Dialog for data update implemented
	- <details><summary>Preview 3</summary>

		[![prototype 3](/app_images/preview_v_3.png "prototype 3")](/app_images/preview_v_3.png "prototype 3")

	</details>
	
	- Added Swipe to Delete with Undo SnackBar
	- <details><summary>Preview 4</summary>

		[![prototype 4](/app_images/preview_v_4.png "prototype 4")](/app_images/preview_v_4.png "prototype 4")

	</details>
	
	- Added red Color and Delete Icon in Swipe To Delete
	- <details><summary>Preview 5</summary>

		[![prototype 5](/app_images/preview_v_5.png "prototype 5")](/app_images/preview_v_5.png "prototype 5")

	</details>
	
	- replaced Toasts to SnackBar 
	- replaced *Round Button to [FAB](https://material.io/develop/android/components/floating-action-button)
	- fixed Snackbar overlap to FAB
	- Good Gradients added and Icons and Theme Colors
	- Added Title Divider using styles
	- <details><summary>Preview 7</summary>

		[![prototype 7](/app_images/preview_v_7.png "prototype 7")](/app_images/preview_v_7.png "prototype 7")

	</details>
	
	- Added red Color and Delete Icon in Swipe To Delete
	- Changed CardView to Material TextView in RecyclerView - 2
	- Alert Dialog to Material IO AlertDialog
	- <details><summary>Preview 9</summary>

		[![prototype 9](/app_images/preview_v_9.png "prototype 9")](/app_images/preview_v_9.png "prototype 9")

	</details>

- Date - 18-04-21
	- Added Splash Screen
	- Long Click to Change Subtext
	- Database Model and View Model added with Live Updates
	- insertOrUpdate seems to work on IO thread
	- Database Updates But only from main Activity
	- Today on Top does not work , replaced with today Highlighted or more Width ?
	- Repository and View Models Extended
	
## TODO
### Important
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
-	Implement Database
-	Pre-Populate Database (not HardCoded)
-	ViewModel Implementation


### Sub-Important
-	Long Press Expands Card Height
-	~~Padding/Margin~~ 
-	~~Highlight the current day~~
-	Good Gradients 
-	~~Round Button~~
-	~~Good CheckBox~~
-	~~App Icon~~
-	Fonts
-	Today on Top