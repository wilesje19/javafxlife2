package javafxlife;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * A JavaFX 8 program to run Conway's Game of Life.
 *
 * @author John Phillips
 */
public class JavaFXLife extends Application {

    Label lbStatus;
    LifePane lifePane;
    Stage pStage;

    @Override
    public void start(Stage primaryStage) {
        pStage = primaryStage;
        lbStatus = new Label("Status: starting JavaFXLife...");
        lifePane = new LifePane(lbStatus);

        BorderPane borderPane = new BorderPane();
        borderPane.setTop(myMenuBar());
        borderPane.setCenter(lifePane);
        borderPane.setBottom(lbStatus);

        Scene scene = new Scene(borderPane, 800, 500);
        primaryStage.setTitle("JavaFXLife");
        primaryStage.setScene(scene);

//        primaryStage.setMaximized(true);
//        primaryStage.setFullScreen(true);
        primaryStage.hide();
        primaryStage.show();
    }

    /**
     * Displays a menu for this application.
     *
     * FYI: menu accelerator key codes are listed at:
     * https://docs.oracle.com/javase/8/javafx/api/javafx/scene/input/KeyCode.html
     */
    public MenuBar myMenuBar() {
        MenuBar myBar = new MenuBar();
        final Menu fileMenu = new Menu("File");
        final Menu speedMenu = new Menu("Speed");
        final Menu optionsMenu = new Menu("Options");
        final Menu helpMenu = new Menu("Help");

        myBar.getMenus().addAll(fileMenu, speedMenu, optionsMenu, helpMenu);

        /**
         * *********************************************************************
         * File Menu Section
         */
        MenuItem newCanvas = new MenuItem("New");
        newCanvas.setOnAction((ActionEvent e) -> {
            lifePane.clearCells();
            lifePane.drawCells();
            lifePane.pause();
        });
        fileMenu.getItems().add(newCanvas);

        MenuItem open = new MenuItem("Open");
        open.setOnAction((ActionEvent e) -> {
            lifePane.pause();
            lifePane.clearCells();
            FileChooser fileChooser = new FileChooser();
            File file = fileChooser.showOpenDialog(pStage);
            if (file != null) {
                readFile(file);
            }
            lifePane.drawCells();
        });
        fileMenu.getItems().add(open);

        MenuItem save = new MenuItem("Save");
        save.setOnAction((ActionEvent e) -> {
            lifePane.pause();
            FileChooser fileChooser = new FileChooser();
            File file = fileChooser.showSaveDialog(pStage);
            if (file != null) {
                writeFile(file);
            }
        });
        fileMenu.getItems().add(save);
        MenuItem exit = new MenuItem("Exit");

        exit.setOnAction(e -> System.exit(0));
        fileMenu.getItems().add(exit);

        /**
         * *********************************************************************
         * Speed Menu Section
         */
        MenuItem pause = new MenuItem("Pause");
        pause.setAccelerator(KeyCombination.valueOf("Left"));
        pause.setOnAction(e -> lifePane.pause());
        speedMenu.getItems().add(pause);

        MenuItem play = new MenuItem("Play");
        play.setAccelerator(KeyCombination.valueOf("Right"));
        play.setOnAction(e -> lifePane.play());
        speedMenu.getItems().add(play);

        MenuItem faster = new MenuItem("Faster");
        faster.setAccelerator(KeyCombination.valueOf("Up"));
        faster.setOnAction(e -> lifePane.increaseSpeed());
        speedMenu.getItems().add(faster);

        MenuItem fasterx10 = new MenuItem("Faster x 10");
        fasterx10.setAccelerator(KeyCombination.keyCombination("Ctrl+Up"));
        fasterx10.setOnAction(e -> lifePane.increaseSpeedx10());
        speedMenu.getItems().add(fasterx10);

        MenuItem fasterx100 = new MenuItem("Faster x 100");
        fasterx100.setAccelerator(KeyCombination.keyCombination("Ctrl+Shift+Up"));
        fasterx100.setOnAction(e -> lifePane.increaseSpeedx100());
        speedMenu.getItems().add(fasterx100);

        MenuItem slower = new MenuItem("Slower");
        slower.setAccelerator(KeyCombination.keyCombination("Down"));
        slower.setOnAction(e -> lifePane.decreaseSpeed());
        speedMenu.getItems().add(slower);

        MenuItem slowerx10 = new MenuItem("Slower x 10");
        slowerx10.setAccelerator(KeyCombination.keyCombination("Ctrl+Down"));
        slowerx10.setOnAction(e -> lifePane.decreaseSpeedx10());
        speedMenu.getItems().add(slowerx10);

        MenuItem slowerx100 = new MenuItem("Slower x 100");
        slowerx100.setAccelerator(KeyCombination.keyCombination("Ctrl+Shift+Down"));
        slowerx100.setOnAction(e -> lifePane.decreaseSpeedx100());
        speedMenu.getItems().add(slowerx100);

        /**
         * *********************************************************************
         * Options Menu Section
         */
        MenuItem randomize = new MenuItem("Randomize Cells");
        randomize.setAccelerator(KeyCombination.keyCombination("Ctrl+R"));
        randomize.setOnAction(e -> lifePane.randomizeCells());
        optionsMenu.getItems().add(randomize);

        CheckMenuItem color = new CheckMenuItem("Color Cells");
        color.setAccelerator(KeyCombination.keyCombination("Ctrl+C"));
        color.setOnAction(e -> lifePane.setShowColors(color.isSelected()));
        optionsMenu.getItems().add(color);
        
        CheckMenuItem blue = new CheckMenuItem("Color Blue");
        blue.setOnAction(e -> lifePane.thisBlue());
        optionsMenu.getItems().add(blue);

        /**
         * *********************************************************************
         * Help Menu Section
         */
        
        MenuItem jp = new MenuItem("JP");
        jp.setOnAction(e -> {
            lifePane.pause();
            lifePane.clearCells();
            readFile(new File("jp.txt"));
            lifePane.drawCells();
        });
        helpMenu.getItems().add(jp);

        MenuItem jw = new MenuItem("JW");
        jw.setOnAction(e -> {
            lifePane.pause();
            lifePane.clearCells();
            readFile(new File("jw.txt"));
            lifePane.drawCells();
        });
        helpMenu.getItems().add(jw);
          
        
        MenuItem acorn = new MenuItem("Acorn");
        acorn.setOnAction(e -> {
            lifePane.pause();
            lifePane.clearCells();
            readFile(new File("acorn.txt"));
            lifePane.drawCells();
        });
        helpMenu.getItems().add(acorn);

        MenuItem ship = new MenuItem("Spaceship");
        ship.setOnAction(e -> {
            lifePane.pause();
            lifePane.clearCells();
            readFile(new File("spaceship.txt"));
            lifePane.drawCells();
        });
        helpMenu.getItems().add(ship);

        MenuItem gosper = new MenuItem("Gosper Glider Gun");
        gosper.setOnAction(e -> {
            lifePane.pause();
            lifePane.clearCells();
            readFile(new File("gosperglidergun.txt"));
            lifePane.drawCells();
        });
        helpMenu.getItems().add(gosper);

        MenuItem about = new MenuItem("About");
        about.setOnAction((ActionEvent e) -> {
            String message = "Rules for Conway's Game of Life:\n"
                    + "    1) a dead cell with exactly 3 neighbors becomes alive\n"
                    + "    2) a living cell with 2 or 3 neighbors continues living\n";
            Alert alert = new Alert(Alert.AlertType.INFORMATION, message);
            alert.setTitle("About");
            alert.setHeaderText("JavaFXLife v1.0 by John Phillips");
            alert.showAndWait();
        });
        helpMenu.getItems().add(about);

        return myBar;
    }

    /**
     * *************************************************************************
     * File helper methods
     */
    private void readFile(File myFile) {
        int y = 0;
        try (Scanner sc = new Scanner(myFile)) {
            while (sc.hasNextLine()) {
                String s[] = sc.nextLine().split("");
                System.out.println("s=" + Arrays.toString(s));
                for (int i = 0; i < s.length; i++) {

                    lifePane.cells[i][y] = Integer.parseInt(s[i]);
                }
                y++;

            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(JavaFXLife.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    // only write 100x100 pattern to file
    private void writeFile(File myFile) {
        try (FileWriter writer = new FileWriter(myFile)) {
            StringBuilder sb = new StringBuilder("");
            for (int y = 0; y < 100; y++) {
                for (int x = 0; x < 100; x++) {
                    sb.append(lifePane.cells[x][y]);
                }
                sb.append("\n");
            }
            writer.write(sb.toString());
            System.out.println(sb.toString());
        } catch (IOException ex) {
            Logger.getLogger(JavaFXLife.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
