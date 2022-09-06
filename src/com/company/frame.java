package com.company;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class frame implements KeyListener {
    boolean loose = false;
    static Graphics graphics;
    static int width = 450, height = 500;
    Ball ball;
    Board board;

    frame() {
        int bricksAlive = 12;

        homework.serieTwo.Packman.DrawingPanel frame = new homework.serieTwo.Packman.DrawingPanel(width, height);
        frame.setBackground(Color.BLACK);
        frame.addKeyListener(this);
        graphics = frame.getGraphics();
        board = new Board(graphics);
        ball = new Ball(330, 200, graphics, width, height);

        Brick[] brick = new Brick[12];

        for(int i = 0; i < 4; i++){
            brick[i] = new Brick(93 + 69 * i, 10, graphics);
        }

        for(int i = 4; i < 8; i++){
            brick[i] = new Brick(93 + 69 * (i - 4), 50, graphics);
        }

        for(int i = 8; i < 12; i++){
            brick[i] = new Brick(93 + 69 * (i - 8), 90, graphics);
        }

        while(bricksAlive > 0 && !loose){
            ball.moveBall();

            if(board.getX() + board.getWidth() >= ball.getX() && ball.getX() + ball.getR() >= board.getX() && ball.getY() + ball.getR() == board.getY())
                ball.hitDown();

            if(board.getY() == ball.getY() + ball.getR() && board.getX() == ball.getX() + ball.getR())
                ball.hitCorner();

            if(board.getX() + board.getWidth() == ball.getX() && board.getY() == ball.getY())
                ball.hitCorner();

            for(int i = 0; i < 12; i++) {
                if (ball.getY() == brick[i].getHeight() + brick[i].getY() && ball.getX() <= brick[i].getX() + brick[i].getWidth() && ball.getX() + ball.getR() >= brick[i].getX()) {
                    if (brick[i].isAlive) {
                        brick[i].die();
                        ball.hitUp();
                        bricksAlive--;
                        break;
                    }
                }

                if (ball.getY() + ball.getR() == brick[i].getY() && ball.getX() <= brick[i].getX() + brick[i].getWidth() && ball.getX() + ball.getR() >= brick[i].getX()) {
                    if (brick[i].isAlive) {
                        brick[i].die();
                        ball.hitDown();
                        bricksAlive--;
                        break;
                    }
                }

                if (ball.getX() + ball.getR() == brick[i].getX() && ball.getY() <= brick[i].getY() + brick[i].getHeight() && ball.getY() + ball.getR() >= brick[i].getY()) {
                    if (brick[i].isAlive) {
                        brick[i].die();
                        ball.hitRight();
                        bricksAlive--;
                        break;
                    }
                }

                if (ball.getX() == brick[i].getX() + brick[i].getWidth() && ball.getY() <= brick[i].getY() + brick[i].getHeight() && ball.getY() + ball.getR() >= brick[i].getY()) {
                    if (brick[i].isAlive) {
                        brick[i].die();
                        ball.hitLeft();
                        bricksAlive--;
                        break;
                    }
                }

                if (brick[i].getX() == ball.getX() + ball.getR() && brick[i].getY() == ball.getR() + ball.getY()) {
                    if (brick[i].isAlive) {
                        brick[i].die();
                        ball.hitCorner();
                        bricksAlive--;
                        break;
                    }
                }

                if (brick[i].getX() == ball.getX() + ball.getR() && brick[i].getY() + brick[i].getHeight()  == ball.getY()) {
                    if (brick[i].isAlive) {
                        brick[i].die();
                        ball.hitCorner();
                        bricksAlive--;
                        break;
                    }
                }

                if (brick[i].getX() + brick[i].getWidth()  == ball.getX() && brick[i].getY()  == ball.getY() + ball.getR()) {
                    if (brick[i].isAlive) {
                        brick[i].die();
                        ball.hitCorner();
                        bricksAlive--;
                        break;
                    }
                }

                if (brick[i].getX() + brick[i].getWidth() == ball.getX() && brick[i].getY() + brick[i].getHeight() == ball.getY()) {
                    if (brick[i].isAlive) {
                        brick[i].die();
                        ball.hitCorner();
                        bricksAlive--;
                        break;
                    }
                }
            }

            if(ball.getY() + ball.getR() == 500){
                loose = true;
                break;
            }

            frame.sleep(8);
        }

        if(loose) {
            graphics.setColor(Color.black);
            graphics.fillRect(0, 0, 500, 500);
        } else {
            graphics.setColor(Color.green);
            graphics.fillRect(0, 0, 500, 500);
        }
    }

    public void keyTyped(KeyEvent keyEvent) {

    }
    public void keyPressed(KeyEvent keyEvent) {
        if(keyEvent.getKeyCode() == KeyEvent.VK_RIGHT)
            board.moveRight();

        if(keyEvent.getKeyCode() == KeyEvent.VK_LEFT)
            board.moveLeft();
    }
    public void keyReleased(KeyEvent keyEvent) {

    }
}

class Brick{
    int width = 60, height = 30;
    int x, y;
    Graphics g;
    boolean isAlive = true;

    int getX(){
        return x;
    }

    int getY(){
        return y;
    }

    int getWidth(){
        return width;
    }

    int getHeight(){
        return height;
    }

    Brick(int x, int y, Graphics g){
        this.x = x;
        this.y = y;
        this.g = g;
        drawBreak();
    }

    void drawBreak(){
        g.setColor(Color.green);
        g.fillRect(x, y, width, height);
    }

    void die(){
        g.setColor(Color.black);
        g.fillRect(x, y, width, height);
        isAlive = false;
    }
}

class Board{
    private Graphics g;
    private int x = 230, y = 460;
    private int lastX = 0, lastY = 0;
    private int width = 60, height = 10;
    private int moveLeft = -8, moveRight = 8;

    int getX(){
        return x;
    }

    int getY(){
        return y;
    }

    int getWidth(){
        return width;
    }

    Board(Graphics g){
        this.g = g;
        drawBoard();
    }

    void drawBoard(){
        g.setColor(Color.black);
        g.fillRect(lastX, lastY, width, height);
        g.setColor(Color.red);
        g.fillRect(x, y, width, height);
        lastX = x;
        lastY = y;
    }

    void moveLeft(){
        if(x > 0) {
            x += moveLeft;
            drawBoard();
        }
    }

    void moveRight(){
        if(x + width < 450) {
            x += moveRight;
            drawBoard();
        }
    }

}

class Ball{
    private int moveX = 1, moveY = 1;
    private int lastX = 0, lastY = 0;
    private int x, y;
    private int height, width;
    private int R = 10;
    private Graphics g;

    int getX(){
        return x;
    }

    int getY(){
        return y;
    }

    int getR(){
        return R;
    }

    Ball(int x, int y, Graphics g, int width, int height){
        this.g = g;
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
    }

    private void drawBall(int x, int y){
        g.setColor(Color.BLACK);
        g.fillRect(lastX, lastY, R, R);
        g.setColor(Color.CYAN);
        g.fillOval(x, y, R, R);
        lastX = x;
        lastY = y;
    }

    void moveBall(){
        drawBall(x, y);
        x += moveX;
        y += moveY;
        if(x + R == width || x == 0)
            moveX *= -1;

        if(y + R == height || y == 0)
            moveY *= -1;
    }

    void hitDown(){
        moveY *= -1;
    }

    void hitUp(){
        moveY *= -1;
    }

    void hitRight(){
        moveX *= -1;
    }

    void hitLeft(){
        moveX *= -1;
    }

    void hitCorner(){
        moveX *= -1;
        moveY *= -1;
    }
}
