package javafxlife;

import java.util.Random;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.util.Duration;

/**
 * The LifePane contains the Game of Life logic and drawing screen. The
 * simulation is represented by a two dimensional array that is a minimum size
 * of 100x100 and a maximum size of the user's screen size. A JavaFX Timeline
 * controls the simulation speed. The results from each new generation is drawn
 * on a canvas which is attached to a Pane. The simulation runs much faster when
 * drawing on a canvas versus placing rectangle objects on a Pane.
 *
 * ToDo: I have not yet figured out a good way to place the canvas / pane into a
 * ScrollPane. Let me know if you figure it out...
 *
 * @author John Phillips
 */
public class LifePane extends Pane {

    private final int CELLWIDTH = 10;
    private final int CELLHEIGHT = 10;
    private final int CELLFILLWIDTH = 9;
    private final int CELLFILLHEIGHT = 9;

    int[][] cells;
    private int xCellCount = 100;
    private int yCellCount = 100;
    private boolean showColors = false;
    private int iteration = 0;
    private final Label lbStatus;
    private final Canvas canvas;
    private final Timeline animation;
    private final Random gen = new Random();

    public LifePane(Label lbStatus) {
        this.lbStatus = lbStatus;

        // set the x,y cell count proportional to the screen size or 100 whichever is greater
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        int screenWidth = (int) primaryScreenBounds.getWidth();
        System.out.println("width=" + screenWidth);
        int screenHeight = (int) primaryScreenBounds.getHeight();
        System.out.println("height=" + screenHeight);
        xCellCount = screenWidth / CELLWIDTH;
        yCellCount = screenHeight / CELLHEIGHT;
        if (xCellCount < 100) {
            xCellCount = 100;
        }
        if (yCellCount < 100) {
            yCellCount = 100;
        }

        // create the main array for the game of life simulation
        this.cells = new int[xCellCount][yCellCount];
        randomizeCells();

        // a canvas is used to display a visual representation of the simulation
        canvas = new Canvas();

        // each rectangular cell can be clicked to turn it on or off
        canvas.setOnMouseClicked((MouseEvent event) -> {
            int x = (int) event.getX() / CELLWIDTH;
            int y = (int) event.getY() / CELLHEIGHT;
            System.out.println("x,y=" + x + "," + y);
            cells[x][y] = cells[x][y] == 0 ? 1 : 0;
            drawCells();
        });

        // must bind canvas to pane or nothing will display
        canvas.widthProperty().bind(this.widthProperty());
        canvas.heightProperty().bind(this.heightProperty());

        this.getChildren().add(canvas);

        animation = new Timeline(
                new KeyFrame(Duration.millis(1000), e -> {
                    nextGeneration();
                    drawCells();
                })
        );
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.play();
    }

    public void play() {
        animation.play();
    }

    public void pause() {
        animation.pause();
    }

    public void increaseSpeed() {
        animation.setRate(animation.getRate() + 0.5);
    }

    public void increaseSpeedx10() {
        animation.setRate(animation.getRate() + 10.0);
    }

    public void increaseSpeedx100() {
        animation.setRate(animation.getRate() + 100.0);
    }

    public void decreaseSpeed() {
        if (animation.getRate() >= 1.0) {
            animation.setRate(animation.getRate() - 0.5);
        }
    }

    public void decreaseSpeedx10() {
        if (animation.getRate() >= 10.5) {
            animation.setRate(animation.getRate() - 10.0);
        }
    }

    public void decreaseSpeedx100() {
        if (animation.getRate() >= 100.5) {
            animation.setRate(animation.getRate() - 100.0);
        }
    }

    public void resetSpeed() {
        animation.setRate(1.0);
    }

    public DoubleProperty rateProperty() {
        return animation.rateProperty();
    }

    public final void randomizeCells() {
        iteration = 0;
        for (int i = 0; i < xCellCount; i++) {
            for (int j = 0; j < yCellCount; j++) {
                cells[i][j] = Math.random() > .5 ? 0 : 1;
            }
        }
    }

    public void clearCells() {
        iteration = 0;
        for (int i = 0; i < xCellCount; i++) {
            for (int j = 0; j < yCellCount; j++) {
                cells[i][j] = 0;
            }
        }
    }

    public void drawCells() {
        GraphicsContext g = canvas.getGraphicsContext2D();
        g.clearRect(0, 0, xCellCount * CELLWIDTH, yCellCount * CELLHEIGHT);
        g.setFill(Color.gray(0, 0.2));
        for (int x = 0; x < xCellCount; x++) {
            for (int y = 0; y < yCellCount; y++) {
                if (cells[x][y] == 0) {
                    g.setFill(Color.WHITE);
                } else {
                    if (showColors) {
                        int myBlue = gen.nextInt(255);
                        int myGreen = gen.nextInt(255);
                        int myRed = gen.nextInt(255);
                        Color myColor = Color.rgb(myRed, myGreen, myBlue);
                        g.setFill(myColor);
                    } else {
                        g.setFill(Color.BLACK);
                    }
                }
                g.fillRect(x * CELLWIDTH, y * CELLHEIGHT, CELLFILLWIDTH, CELLFILLHEIGHT);
            }
        }
        iteration++;
        lbStatus.setText("Iterations=" + iteration + ", rate="
                + animation.getRate() + ", xcount=" + xCellCount
                + ", ycount=" + yCellCount);
    }

    // This method implements the rules of the game of life by checking each neighbor cell to see if:
    // 1. cell is dead and has exactly 3 live neighbors then it becomes alive
    // 2. cell is living and has 2 or 3 living neighbors then it keeps living otherwise it dies
    public void nextGeneration() {
        int[][] cellsng = new int[xCellCount][yCellCount];
        for (int x = 0; x < xCellCount; x++) {
            for (int y = 0; y < yCellCount; y++) {
                int neighbors = 0;
                if (x > 0 && x < xCellCount - 1 && y > 0 && y < yCellCount - 1) {
                    if (cells[x - 1][y - 1] == 1) {
                        neighbors++;
                    }
                    if (cells[x - 1][y] == 1) {
                        neighbors++;
                    }
                    if (cells[x - 1][y + 1] == 1) {
                        neighbors++;
                    }
                    if (cells[x][y - 1] == 1) {
                        neighbors++;
                    }
                    if (cells[x][y + 1] == 1) {
                        neighbors++;
                    }
                    if (cells[x + 1][y - 1] == 1) {
                        neighbors++;
                    }
                    if (cells[x + 1][y] == 1) {
                        neighbors++;
                    }
                    if (cells[x + 1][y + 1] == 1) {
                        neighbors++;
                    }
                    if (cells[x][y] == 1) {
                        if (neighbors < 2 || neighbors > 3) {
                            cellsng[x][y] = 0;
                        } else {
                            cellsng[x][y] = 1;
                        }
                    } else if (neighbors == 3) {
                        cellsng[x][y] = 1;
                    }
                } else {
                    cellsng[x][y] = 0;
                }
            }
        }
        cells = cellsng;
    }

    public void setShowColors(boolean showColors) {
        this.showColors = showColors;
    }

    public boolean getShowColors() {
        return this.showColors;
    }
}
