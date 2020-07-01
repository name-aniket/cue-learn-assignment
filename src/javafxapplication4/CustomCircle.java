/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication4;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;

/**
 *
 * @author warhawk
 */
public class CustomCircle {

    private double x = 0;
    private double y = 0;
    // mouse position
    private double mousex = 0;
    private double mousey = 0;

    private boolean dragging = false;
    private boolean moveToFront = true;

    private Circle circle;

    public CustomCircle(Circle circle) {
        this.circle = circle;

    }

    public Circle makeDraggable() {
        circle.onMouseClickedProperty().set((EventHandler<MouseEvent>) (MouseEvent event) -> {
            x = circle.getLayoutX();
            y = circle.getLayoutY();

            mousex = event.getSceneX();
        });

        circle.onMouseDraggedProperty().set((EventHandler<MouseEvent>) (MouseEvent event) -> {
            double offsetX = event.getSceneX() - mousex;
            double offsetY = event.getSceneY() - mousey;

            x += offsetX;
            y += offsetY;

            double scaledX = x;
            double scaledY = y;

            circle.setLayoutX(scaledX);
            circle.setLayoutY(scaledY);

            // again set current Mouse x AND y position
            mousex = event.getSceneX();
            mousey = event.getSceneY();

            event.consume();
        });

        circle.onMouseClickedProperty().set((EventHandler<MouseEvent>) (MouseEvent event) -> {
            dragging = false;
        });

        return this.circle;
    }

    protected boolean isDragging() {
        return dragging;
    }

    /**
     * @param moveToFront the moveToFront to set
     */
    public void setMoveToFront(boolean moveToFront) {
        this.moveToFront = moveToFront;
    }

    /**
     * @return the moveToFront
     */
    public boolean isMoveToFront() {
        return moveToFront;
    }

}
