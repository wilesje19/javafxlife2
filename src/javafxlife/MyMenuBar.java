//package javafxlife;
//
//import java.io.File;
//import javafx.event.ActionEvent;
//import javafx.event.EventHandler;
//import javafx.scene.control.Alert;
//import javafx.scene.control.Alert.AlertType;
//import javafx.scene.control.Menu;
//import javafx.scene.control.MenuBar;
//import javafx.scene.control.MenuItem;
//import javafx.scene.input.KeyCombination;
//import javafx.stage.FileChooser;
//
///**
// *
// * @author John
// */
//public class MyMenuBar extends MenuBar {
//
//    final Menu fileMenu = new Menu("File");
//    final Menu speedMenu = new Menu("Speed");
//    final Menu optionsMenu = new Menu("Options");
//    final Menu helpMenu = new Menu("Help");
//
//    public MyMenuBar(LifePane lifePane) {
//        this.getMenus().addAll(fileMenu, speedMenu, optionsMenu, helpMenu);
//
//        MenuItem newCanvas = new MenuItem("New");
//        newCanvas.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent e) {
//                lifePane.clearCells();
//                lifePane.drawCells();
//                lifePane.pause();
//            }
//        });
//        fileMenu.getItems().add(newCanvas);
//        
//        MenuItem save = new MenuItem("Save");
//        save.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent e) {
//                lifePane.pause();
//                
//                FileChooser fileChooser = new FileChooser();
//                 
//                //Set extension filter
////                FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
////                fileChooser.getExtensionFilters().add(extFilter);
//                 
//                //Show save file dialog
//                File file = fileChooser.showOpenDialog();
//                if(file != null){
//                    textArea.setText(readFile(file));
//                }
//            }
//                
//                
//            }
//        });
//        fileMenu.getItems().add(save);
//        
//        
//
//        MenuItem exit = new MenuItem("Exit");
//        exit.setOnAction(t -> System.exit(0));
//        fileMenu.getItems().add(exit);
//
//        MenuItem reset = new MenuItem("Speed = 1");
//        reset.setAccelerator(KeyCombination.valueOf("="));
//        reset.setOnAction(e -> lifePane.resetSpeed());
//        speedMenu.getItems().add(reset);
//
//        MenuItem pause = new MenuItem("Pause");
//        pause.setAccelerator(KeyCombination.keyCombination("Ctrl+Left"));
//        pause.setOnAction(e -> lifePane.pause());
//        speedMenu.getItems().add(pause);
//
//        MenuItem play = new MenuItem("Play");
//        play.setAccelerator(KeyCombination.keyCombination("Ctrl+Right"));
//        play.setOnAction(e -> lifePane.play());
//        speedMenu.getItems().add(play);
//
//        // https://docs.oracle.com/javase/8/javafx/api/javafx/scene/input/KeyCode.html
//        MenuItem faster = new MenuItem("Faster");
//        faster.setAccelerator(KeyCombination.valueOf("Up"));
//        faster.setOnAction(e -> lifePane.increaseSpeed());
//        speedMenu.getItems().add(faster);
//
//        MenuItem fasterx10 = new MenuItem("Faster x 10");
//        fasterx10.setAccelerator(KeyCombination.keyCombination("Ctrl+Up"));
//        fasterx10.setOnAction((ActionEvent e) -> {
//            for (int i = 0; i < 10; i++) {
//                lifePane.increaseSpeed();
//            }
//        });
//        speedMenu.getItems().add(fasterx10);
//
//        MenuItem fasterx100 = new MenuItem("Faster x 100");
//        fasterx100.setAccelerator(KeyCombination.keyCombination("Ctrl+Shift+Up"));
//        //fasterx100.setAccelerator(KeyCombination.valueOf("Page Up"));
//        fasterx100.setOnAction((ActionEvent e) -> {
//            for (int i = 0; i < 100; i++) {
//                lifePane.increaseSpeed();
//            }
//        });
//        speedMenu.getItems().add(fasterx100);
//
//        MenuItem slower = new MenuItem("Slower");
//        slower.setAccelerator(KeyCombination.keyCombination("Down"));
//        slower.setOnAction(e -> lifePane.decreaseSpeed());
//        speedMenu.getItems().add(slower);
//
//        MenuItem slowerx10 = new MenuItem("Slower x 10");
//        slowerx10.setAccelerator(KeyCombination.keyCombination("Ctrl+Down"));
//        slowerx10.setOnAction((ActionEvent e) -> {
//            for (int i = 0; i < 10; i++) {
//                lifePane.decreaseSpeed();
//            }
//        });
//        speedMenu.getItems().add(slowerx10);
//
//        MenuItem slowerx100 = new MenuItem("Slower x 100");
//        slowerx100.setAccelerator(KeyCombination.keyCombination("Ctrl+Shift+Down"));
//        slowerx100.setOnAction((ActionEvent e) -> {
//            for (int i = 0; i < 100; i++) {
//                lifePane.decreaseSpeed();
//            }
//        });
//        speedMenu.getItems().add(slowerx100);
//
//        MenuItem randomize = new MenuItem("Randomize Cells");
//        randomize.setAccelerator(KeyCombination.keyCombination("Ctrl+R"));
//        randomize.setOnAction(e -> lifePane.randomizeCells());
//        optionsMenu.getItems().add(randomize);
//
//        MenuItem about = new MenuItem("About");
//        about.setOnAction((ActionEvent e) -> {
//            Alert alert = new Alert(AlertType.INFORMATION, "John Phillips' Game of Life version 1.0");
//            alert.setTitle("About");
//            alert.setHeaderText("JavaFXLife");
//            alert.showAndWait();
//        });
//        helpMenu.getItems().add(about);
//    }
//
//}
