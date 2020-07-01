package javafxapplication4;


import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

/**
 *
 * @author Aniket Pandey
 */
public class FXMLDocumentController implements Initializable {

    ArrayList<Circle> l_balance;

    ArrayList<Circle> r_balance;

    @FXML
    Circle circle1;

    @FXML
    Circle circle2;

    @FXML
    Circle circle3;

    @FXML
    Circle circle4;

    @FXML
    ImageView left_balance;

    @FXML
    ImageView right_balance;

    @FXML
    ImageView rod;
    
    @FXML
    Label label;  

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        circle1 = new CustomCircle(circle1).makeDraggable();
        circle2 = new CustomCircle(circle2).makeDraggable();
        circle3 = new CustomCircle(circle3).makeDraggable();
        circle4 = new CustomCircle(circle4).makeDraggable();
        l_balance = new ArrayList<>();
        r_balance = new ArrayList<>();

    }

    int isLeftorRight(Circle c) {
        // For left balance
        double left_lower_limit_x = left_balance.getLayoutX();
        double left_upper_limit_x = left_balance.getLayoutX() + left_balance.getFitWidth();
        double left_lower_limit_y = left_balance.getLayoutY();
        double left_upper_limit_y = left_balance.getLayoutY() + left_balance.getFitHeight();

        // For right balance
        double right_lower_limit_x = right_balance.getLayoutX();
        double right_upper_limit_x = right_balance.getLayoutX() + right_balance.getFitWidth();
        double right_lower_limit_y = right_balance.getLayoutY();
        double right_upper_limit_y = right_balance.getLayoutY() + right_balance.getFitHeight();

        double x_circle = c.getLayoutX();
        double y_circle = c.getLayoutY();

//        System.out.println("Circle x: " +  x_circle);
//        System.out.println("Circle y: " +  y_circle);
//        
//       
//        
//        System.out.println("LB lower x: " + left_lower_limit_x);
//        System.out.println("LB upper x: " + left_upper_limit_x);
//        System.out.println("LB lower y: " + left_lower_limit_y);
//        System.out.println("LB upper y: " + left_upper_limit_y);
//        
//        System.out.println("RB lower x: " + right_lower_limit_x);
//        System.out.println("RB upper x: " + right_upper_limit_x);
//        System.out.println("RB lower y: " + right_lower_limit_y);
//        System.out.println("RB upper y: " + right_upper_limit_y);
//        
//       
//        System.out.println("************************************");
        if ((x_circle > left_lower_limit_x && x_circle < left_upper_limit_x) && (y_circle > left_lower_limit_y && y_circle < left_upper_limit_y)) {
            return -1;
        } else if ((x_circle > right_lower_limit_x && x_circle < right_upper_limit_x) && ((y_circle > right_lower_limit_y && y_circle < right_upper_limit_y))) {
            return 1;
        } else {
            return 0;
        }
    }

    @FXML
    void countCircles() {

        l_balance.clear();

        r_balance.clear();

        Circle[] circles = new Circle[]{circle1, circle2, circle3, circle4};

        for (Circle circle : circles) {
            int status = isLeftorRight(circle);
            if (status == -1) {
                l_balance.add(circle);
            } else if (status == 1) {
                r_balance.add(circle);
            }

        }

        if (l_balance.size() == r_balance.size()) {
            if (l_balance.size() == 1 || l_balance.isEmpty()) {
                //System.out.println("The bowls have equal objects, but there are still some more to add.");
                label.setText("The bowls have equal objects, but there are still some more to add.");
            } else {
                //System.out.println("Yay! You divided them all equally. There are 2 objects in each bowl.");
                label.setText("Yay! You divided them all equally. There are 2 objects in each bowl.");
            }
        } else {
            label.setText("Oops! They are not equally divided!");
            doAnimation((l_balance.size() < r_balance.size()));
        }

    }

    public void doAnimation(boolean flag) {

        // If true, it means right size is heavier;
        // False means left is heavier.
        double angle = (flag) ? 8.0 : -8.0;
        double trans = (flag) ? -14.0 : 14.0;

        /**
         *
         */
        RotateTransition rt = new RotateTransition(Duration.millis(1500), rod);
        rt.setByAngle(angle);
        rt.setCycleCount(1);
        rt.setAutoReverse(false);

        /**
         *
         */
        TranslateTransition tt = new TranslateTransition(Duration.millis(1500), left_balance);
        tt.setByY(trans);
        tt.setCycleCount(1);

        /**
         *
         */
        TranslateTransition tt1 = new TranslateTransition(Duration.millis(1500), right_balance);
        tt1.setByY(-trans);
        tt1.setCycleCount(1);

        /**
         *
         */
        TranslateTransition[] left_circles_tt = new TranslateTransition[l_balance.size()];
        for (int i = 0; i < l_balance.size(); i++) {
            left_circles_tt[i] = new TranslateTransition(Duration.millis(1500), l_balance.get(i));
            left_circles_tt[i].setByY(trans);
            left_circles_tt[i].setCycleCount(1);
        }

        /**
         *
         */
        TranslateTransition[] right_circles_tt = new TranslateTransition[r_balance.size()];
        for (int i = 0; i < r_balance.size(); i++) {
            right_circles_tt[i] = new TranslateTransition(Duration.millis(1500), r_balance.get(i));
            right_circles_tt[i].setByY(-trans);
            right_circles_tt[i].setCycleCount(1);
        }

        /**
         *
         */
        rt.play();
        tt.play();
        tt1.play();

        for (TranslateTransition temp : left_circles_tt) {
            temp.play();
        }

        for (TranslateTransition temp : right_circles_tt) {
            temp.play();
        }
    }
}
