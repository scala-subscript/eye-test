# Introduction

This is an application for eyesight checkupe. 
It has been written in [SubScript](https://github.com/scala-subscript/subscript).

The application is designed to register the dynamics in your vision acuty.
Therefore it is supposed to be used during an extended period of time, 
regularly and possibly in same conditions during every examination.

The application aims to measure the smallest font you can read from a certain fixed distance.

## Test procedure

The application works with the following test procedure:

1. Sit at ~1 meter from your computer screen.
2. First your right eye is tested, then your left eye. You need to cover the other eye with something while you're taking the test.
3. A sequence of 6 uppercase letters will appear on the screen. Your goal is to type the letters into the text box properly. Once you are done, press **Enter**.
4. Another sequence of 6 lettes will appear. If you've entered the previous one correctly, its font will be 10 units smaller then the previous one's. Otherwise, the font will increase by 10 units. This is the calibration phase.
5. This will continue while there's no dynamics in your guesses. That is, while you keep getting it right, or keep getting it wrong all the time.
6. After there is a dynamics, it means that you've reached the limit of your vision, and it needs to be estimated precisely. So the test will continue untill you score 5 correct answers in a row. In this phase, a correct guess reduces your font size by 1 unit and an incorrect one - increases it by 10 units.
7. These 5 correct answers' average font is the score of the eye currently been tested.

# Usage

## Download and launch the application
1. [Download](https://github.com/scala-subscript/eye-test/releases/download/v1.0.2/eyetest-1.0.2.zip) the latest release
2. Extract the archive to some folder
3. Run `eye-test.sh` (Mac OS X and Linux) or `eye-test.bat` (Windows)

## Register withing the application
1. After the program has loaded (it creates (if not exists) and connects to the local database, located in `./eyetest-db folder`), you'll see a *Login Screen* where you can select the user. Initially, there are no users, so you'll need to register one. Press **New user** button.
2. Enter the name of your user and press **Register**, or just press **Enter** on your keyboard.
3. You'll be back to the *Login* screen, now you'll be able to select the user you've registered and continue the work with him.

## Test your eyesight
1. From the *Login* screen, select your user name.
2. Press **Test** button.
3. Go through the test procedure as described in the [Testing procedure](https://github.com/scala-subscript/eye-test#testing-procedure) section above.
4. Your score will automatically be recorded into the local database.
5. At the end of the test, a form with your results will appear. Close it after reading.

## View your progress
1. From the *Login* screen, select your user.
2. Press **Export to CSV** button.
3. Select where to save your scores, save them to the file with a `*.csv` extension, for example `my_scores.csv`.
4. Open the file with a table processor, like Microsoft Excel. If asked what the values are separated by, specify only the comma "`,`" symbol.
5. The table contains your scores for each eye and the dates where the tests were taken. A good idea is to use your table processor's built-in functionalities to build a plot of the dynamics of your eyesight, so that you know whether you need to change your working habits.

# Issues
An issue tracker with known bugs is located [here](https://github.com/scala-subscript/eye-test/issues).
