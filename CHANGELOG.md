# Change Log

## Version 0.6 - May 25, 2016
#### Added Functions and Features
- Users now have their own profile page
- Challenges can be shared to social media
- Search challenges
- Users can now remove an upvote
- Users can now upvote completed challenges
- Name of logged in user is shown
- Feedback when providing a video of challenge performed
- High Score
- Sort challenges

#### Changed
- Users now receive a clear warning before providing a video, confirming it's the last step
- Users can now receive points from both performing and creating challenges
- Refactor Angular, now using route provider

#### Fixed
- Smoother login via Google
- Disapproved videos is now properly moved to available challenges
- Claimed challenges can no longer be interacted with when user has logged out

#### Removed
- Comments
- Popups

## Version 0.5 - May, 12 2016
#### Added Functions and Features
- Add a challenge
- View challenges
- Claim a challenge
- Confirm a challenge
- Upvote a challenge
- Upvote a video
- Add a YouTube video to a claimed challenge
- Login with Google
- Collect points from performing challenges
- Comments

#### Changed
- Limit amount of characters in the Create Challenge form
- Users now get feedback when trying to interact with own challenge
- Users can now provide a reason why they did not approve a challenge

#### Fixed
- User can now only upvote a challenge once
- Validation in the the Create Challenge form
- User can no longer confirm a video before a video is provided
- Backend validation for claiming a video
- Challenge creator can now see who tried to perform the created challenge