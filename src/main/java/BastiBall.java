import processing.core.PApplet;

public class BastiBall extends PApplet {
    private final int ballDiremeter = 20;
    private final int platformWidth = 200;

    private int highscore = 0;
    private int platformColor = color(random(255), random(255), random(255));
    private int ballColor = color(random(255), random(255), random(255));
    private int x, y, score, lifes;
    private double velocity;
    private boolean pause = false;
    private boolean reverseX, reverseY;

    public static void main(String[] args) {
        PApplet.main("BastiBall", args);
    }

    public void settings() {
        fullScreen();
    }

    public void setup() {
        noCursor();

        declare();
    }

    public void draw() {
        key = ("" + key).toLowerCase().charAt(0);
        if (key == 'q') {
            pause = true;
        }

        if (pause) {
            pause(key);

            key = 'o';

            delay(300);
        } else {
            play();
        }
    }

    private void pause(char key) {background(0);

        textAlign(CENTER);
        textSize(40);
        text("PAUSE", displayWidth / 2, displayHeight / 2 - 50);

        textSize(20);text("Change ball color [B]", displayWidth / 2, displayHeight / 2);
        fill(ballColor);
        ellipse(displayWidth / 2 + 150, displayHeight / 2 - 7, ballDiremeter, ballDiremeter);

        fill(255);
        text("Change platform color [P]", displayWidth / 2, displayHeight / 2 + 30);
        fill(platformColor);
        rect(displayWidth / 2 + 140, displayHeight / 2 + 12, platformWidth, 20);

        fill(255);
        text("Resume Game [R]", displayWidth / 2, displayHeight / 2 + 60);

        textAlign(LEFT);
        text("Lifes: ", 30, 40);

        for (int i = 0; i < lifes; i++) {
            noStroke();
            ellipse(100 + 25 * i, 33, 20, 20);
        }

        stroke(0);
        textAlign(RIGHT);
        text("Score: " + score, displayWidth - 30, 40);
        text("Highscore: " + highscore, displayWidth - 30, 60);

        switch (key) {
            case 'b':
                ballColor = color(random(255), random(255), random(255));
                break;
            case 'p':
                platformColor = color(random(255), random(255), random(255));
                break;
            case 'r':
                pause = false;
        }
    }

    private void play() {
        move();

        if (y >= displayHeight + ballDiremeter) {
            lifes--;

            reverseY = false;
            reverseX = false;
            x = (int) random(displayWidth);
            y = 11;
            delay(1000);
        }

        if (lifes > 0) {
            fill(platformColor);
            rect(mouseX, displayHeight - 30, platformWidth, 20);

            if (mouseX > displayWidth - platformWidth) {
                fill(platformColor);
                rect(displayWidth - platformWidth, displayHeight - 30, platformWidth, 20);
            }

            textSize(20);
            fill(255);
            textAlign(LEFT);
            text("Lifes: ", 30, 40);

            for (int i = 0; i < lifes; i++) {
                noStroke();
                ellipse(100 + 25 * i, 33, 20, 20);
            }

            stroke(0);
            textAlign(CENTER);
            text("Pause [Q]", displayWidth / 2, 40);
            text("Exit [ESC]", displayWidth / 2, 60);

            textAlign(RIGHT);
            text("Score: " + score, displayWidth - 30, 40);
            text("Highscore: " + highscore, displayWidth - 30, 60);

            velocity += 0.005;
        } else {
            gameOver();
        }
    }

    private void move() {
        boolean hitPlatform;

        if (x >= displayWidth - ballDiremeter / 2) {
            reverseX = true;
        }
        if (x <= ballDiremeter / 2) {
            reverseX = false;
        }

        hitPlatform = (x >= mouseX
                && x <= mouseX + platformWidth
                && y >= displayHeight - 40)
                || (mouseX > displayWidth - platformWidth
                && x >= displayWidth - platformWidth
                && x <= displayWidth
                && y >= displayHeight - 40);

        if (hitPlatform) {
            reverseY = true;

            score++;

            if (score > highscore) {
                highscore = score;
            }
        }

        if (y <= ballDiremeter / 2) {
            reverseY = false;
        }

        if (reverseX) {
            x -= velocity;
        } else {
            x += velocity;
        }

        if (reverseY) {
            y -= velocity;
        } else {
            y += velocity;
        }

        background(40);

        fill(ballColor);
        ellipse(x, y, ballDiremeter, ballDiremeter);
    }

    private void gameOver() {
        background(0);

        textSize(40);
        textAlign(CENTER);
        fill(255);
        text("GAME OVER", displayWidth / 2, displayHeight / 2);

        textSize(20);
        text("Score: " + score, displayWidth / 2, displayHeight / 2 + 30);
        text("Highscore: " + highscore, displayWidth / 2, displayHeight / 2 + 60);
        text("Click to play again", displayWidth / 2, displayHeight / 2 + 90);

        if (mousePressed) {
            declare();
        }
    }

    private void declare() {
        velocity = 2;
        x = (int) random(displayWidth);
        y = 11;
        score = 0;
        lifes = 3;
        reverseX = false;
        reverseY = false;
    }
}