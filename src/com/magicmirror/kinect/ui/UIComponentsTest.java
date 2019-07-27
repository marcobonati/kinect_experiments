package com.magicmirror.kinect.ui;

import com.magicmirror.kinect.ui.components.UILabel;
import processing.core.PApplet;
import processing.core.PVector;

public class UIComponentsTest extends AbstractMainApplet {

    private UILabel _label;

    // The argument passed to main must match the class name
    public static void main(String[] args) {
        runApplet(UIComponentsTest.class);
    }


    @Override
    public void setup() {
        super.setup();

        _label = new UILabel(this)
                    .setColor(color(255,255,255))
                    .setBackground(color(92,172,29, 255))
                    .setPosition(new PVector(50,50))
                    .setText("Hello World!");
    }

    @Override
    public void draw() {
        super.draw();

        _label.setPosition(new PVector(mouseX +20 ,mouseY +20))
                .draw();
    }

}