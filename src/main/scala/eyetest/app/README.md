# Introduction
This is an application for the eyesight checkup. It is written with [SubScript](https://github.com/scala-subscript/subscript).

The application is designed to register the dynamics in your vision acuty. Therefore, it is supposed to be used during an extended period of time, regularly and possibly in same conditions during every examination.

The application aims to measure the smallest font you can read from a distance of 50cm.

## Testing procedure
Here follows the conceptual description of the test procedure used in the application:

1. Sit 50cm from your computer screen
2. Your eyes will be tested in order: right eye, then left eye. You need to cover the opposite eye with something while you're taking the test.
3. A sequence of 6 uppercase letters will appear on the screen, the font of which is 20. When you take the test next time, the font will be set to the one you scored during your previous test.
4. Your goal is to type the letters into the text box properly. Once you've done, press **Enter**.
5. Another sequence of 6 lettes will appear. If you've guessed the previous one correctly, its font will be one unit smaller then the previous one's. Otherwise, the font will increase by one unit.
6. This will continue while there's no dynamics in your guesses. That is, while you keep getting it right, or keep getting it wrong all the time.
7. After there is a dynamics, it means that you've reached the limit of your vision, and it needs to be estimated precisely. So the test will continue untill you score 5 correct answers.
8. These 5 correct answers' average font is the score of the eye currently been tested.

# Usage

## Download and launch the application
1. Clone the repository with `git clone https://github.com/scala-subscript/eye-test.git`
2. Cd into it with `cd eye-test`
3. Launch the program with `sbt run`

## Register withing the application
1. After the program has loaded (it creates (if not exists) and connects to the local database, located in `./eyetest-db folder`), you'll see a *Login Screen* where you can select the user. Initially, there are no users, so you'll need to register one. Press **New user** button.
2. Enter the name of your user and press **Register**, or just press **Enter** on your keyboard.
3. You'll be back to the *Login* screen, now you'll be able to select the user you've registered and continue the work with him.

## Test your eyesight
1. From the *Login* screen, select your user.
2. Press **Test** button.
3. Go through the test procedure as described in the [Testing procedure](https://github.com/scala-subscript/eye-test#testing-procedure) section above.
4. Your score will automatically be recorded into the local database.
5. At the end of the test, a form with your results will appear. Close it after reading.

## View your progress
1. From the *Login* screen, select your user.
2. Press **Export to CSV** button.
3. Select where to save your scores, save them to the file with a `*.csv` extension, for example `my_scores.csv`.
4. Open the file with a table processor, like Microsoft Exel. If asked what the values are separated by, specify only comma "`,`" symbol.
5. The table contains your scores for each eye and the dates where the tests were taken. A good idea is to use your table processor's built-in functionalities to build a plot of the dynamics of your eyesight, so that you know whether you need to change your working habits.