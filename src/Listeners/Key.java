package Listeners;

import Central.Main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static java.awt.event.KeyEvent.*;

public class Key implements KeyListener {
    public final int LEFT = 0, RIGHT = 1, UP = 2, DOWN = 3, CTRL = 4, SPACE = 5;
    boolean[] pressed = new boolean[6];
    boolean[] released = new boolean[6];
    int keyCode = 0;
    char key = 0;

    @Override
    public void keyTyped(KeyEvent e) {
        key = e.getKeyChar();
        Main.INTERFACE.keyType();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        keyCode = e.getKeyCode();
        switch (keyCode) {
            case VK_LEFT -> {
                pressed[LEFT] = true;
                released[LEFT] = false;
            }
            case VK_RIGHT -> {
                pressed[RIGHT] = true;
                released[RIGHT] = false;
            }
            case VK_UP -> {
                pressed[UP] = true;
                released[UP] = false;
            }
            case VK_DOWN -> {
                pressed[DOWN] = true;
                released[DOWN] = false;
            }
            case VK_CONTROL -> {
                pressed[CTRL] = true;
                released[CTRL] = false;
            }
            case VK_SPACE -> {
                pressed[SPACE] = true;
                released[SPACE] = false;
            }
        }
        Main.INTERFACE.keyPress();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        switch (code) {
            case VK_LEFT -> {
                pressed[LEFT] = false;
                released[LEFT] = true;
            }
            case VK_RIGHT -> {
                pressed[RIGHT] = false;
                released[RIGHT] = true;
            }
            case VK_UP -> {
                pressed[UP] = false;
                released[UP] = true;
            }
            case VK_DOWN -> {
                pressed[DOWN] = false;
                released[DOWN] = true;
            }
            case VK_CONTROL -> {
                pressed[CTRL] = false;
                released[CTRL] = true;
            }
            case VK_SPACE -> {
                pressed[SPACE] = false;
                released[SPACE] = true;
            }
        }
        Main.INTERFACE.keyRelease();
    }

    public char key() {
        return key;
    }

    public int keyCode() {
        return keyCode;
    }

    public boolean pressed(int key) {
        return pressed[key];
    }

    public boolean released(int key) {
        return released[key];
    }
}
