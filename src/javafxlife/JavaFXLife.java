package javafxlife;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 *
 * @author John
 */
public class JavaFXLife extends Application {

    int[][] cells = new int[100][100];
    BorderPane borderPane = new BorderPane();
    Pane canvas = new Pane();

    @Override
    public void start(Stage primaryStage) {
        initCells();
        borderPane.setCenter(canvas);

        Button btn = new Button();
        btn.setText("Next Generation");
        btn.setOnAction((ActionEvent event) -> {
            System.out.println("Hello World!");
            nextGen();
            drawCells();
        });
        borderPane.setBottom(btn);

        Scene scene = new Scene(borderPane, 1000, 1000);
        drawCells();
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void initCells() {
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                cells[i][j] = Math.random() > .6 ? 0 : 1;
            }
        }
    }

    public void drawCells() {
        canvas.getChildren().clear();
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                Rectangle rect = new Rectangle(i * 10, j * 10, 10, 10);
                rect.setFill(cells[i][j] == 0 ? Color.WHITE : Color.GREEN);
                canvas.getChildren().add(rect);
            }
        }
    }

    public void nextGen() {
        int[][] cellsng = new int[100][100];
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                int neighbors = 0;
                if (i > 1 && i < 99 && j > 1 && j < 99) {
                    if (cells[i - 1][j - 1] == 1) {
                        neighbors++;
                    }
                    if (cells[i - 1][j] == 1) {
                        neighbors++;
                    }
                    if (cells[i - 1][j + 1] == 1) {
                        neighbors++;
                    }
                    if (cells[i][j - 1] == 1) {
                        neighbors++;
                    }
                    if (cells[i][j + 1] == 1) {
                        neighbors++;
                    }
                    if (cells[i + 1][j - 1] == 1) {
                        neighbors++;
                    }
                    if (cells[i + 1][j] == 1) {
                        neighbors++;
                    }
                    if (cells[i + 1][j + 1] == 1) {
                        neighbors++;
                    }
                    if (cells[i][j] == 1) {
                        if (neighbors < 2 || neighbors > 3) {
                            cellsng[i][j] = 0;
                        } else {
                            cellsng[i][j] = 1;
                        }
                    } else if (neighbors == 3) {
                        cellsng[i][j] = 1;
                    }
                } else {
                    cellsng[i][j] = 0;
                }
            }
        }
        cells = cellsng;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
