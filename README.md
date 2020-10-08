# Viaplay AndroidTV Challenge

This coding challenge will assess your understanding and knowledge in the leanback framework and also in general Android.
This repository is a leanback sample app which consumes an API of Viaplay and shows a list of sections and movies. The challenge consists of 3 small parts:

### Warmup part:
If you click on a product item, You will be navigated to DetailsFragment which is empty. Now if you press the back button, The header menu items will be tripled, As the first task, we want you to fix this.
Hint: Please don't go with null checks or similar methods, They don't look really good!

### Part1:
Currently, by moving the focus from product items in **MainFragment** a background image is loaded using Glide (**updateBackground
()** method). Let's suppose we've been given a gradient background drawable (**home_page_background_gradient.png**) and we want to overlay on images loaded by Glide. (We don't want to overlay a view over the whole fragment view or any child views). This needs to be done by Glide. You need to show that your implementation is memory friendly and show how much memory it's using and prove that it's releasing acquired resources and allocations on fast scrolling (We don't want to spend all the allocated heap for our app with bitmaps, Right?).

### Part2:
You've been given a task to refactor the current UI and more specifically the state of the left side menu. If you run the app you will see it currently looks like:

![CurrentCollapsed](https://github.com/mr-nent/ATV_Sample/raw/master/screenshots/current_app_collapsed.jpeg)
![CurrentExpanded](https://github.com/mr-nent/ATV_Sample/blob/master/screenshots/current_app_expanded.jpeg)

in collapsed and expanded mode. We want to achieve something like this:


![NextCollapsed](https://github.com/mr-nent/ATV_Sample/blob/master/screenshots/next_state_collapsed.png)
![NextCollapsed](https://github.com/mr-nent/ATV_Sample/blob/master/screenshots/next_state_expanded.png)

You should do this by customizing leanback components, no 3rd party usage is allowed in this task. You can pick the icons as drawables from material icons set. As an example you can check how the youtube's AndroidTv app is looking now. We want to achieve something similar 


Just remember that your code must be written in Kotlin!


### Delivery
Start the challenge by forking this repository, Once finished, deliver the result of the assignment as a project available on your GitHub **in three different branches**(One branch for each part) and share the link to the repo with us.

