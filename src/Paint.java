import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Paint extends Application {
    private static final int change = 1;
    String slectedShape = "";
    private static final int CANVAS_SIZE = 450;
    private static final int PIXEL_SIZE = 6 / change;
    private static final int NUM_PIXELS = (CANVAS_SIZE + 200) / PIXEL_SIZE;
    private Color currentColor = Color.BLACK; 
    private int brushRadius = 1 * change; 

    @Override
    public void start(Stage primaryStage) {
        Canvas canvas = new Canvas(CANVAS_SIZE, CANVAS_SIZE + 200);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, CANVAS_SIZE, CANVAS_SIZE + 200);

        canvas.setOnMousePressed(event -> handleMousePress(event, gc));
        canvas.setOnMouseDragged(event -> handleMouseDrag(event, gc));

        ComboBox<String> colorComboBox = new ComboBox<>(FXCollections.observableArrayList("BLACK", "BLUE", "GREEN", "MAGENTA", "ORANGE", "RED", "VIOLET", "WHITE", "YELLOW"));
        ComboBox<String> sizeComboBox = new ComboBox<>(FXCollections.observableArrayList("Small", "Medium", "Large"));
        //ComboBox<String> shapeComboBox = new ComboBox<>(FXCollections.observableArrayList("Draw","Square", "Circle", "Triangle", "Star"));

        sizeComboBox.setOnAction(event -> {
            String selectedSize = sizeComboBox.getValue();
            if ("Small".equals(selectedSize)) {
                brushRadius = 1 * change;
            } else if ("Medium".equals(selectedSize)) {
                brushRadius = 2 * change;
            } else if ("Large".equals(selectedSize)) {
                brushRadius = 4 * change;
            }
        });
        colorComboBox.setOnAction(event -> {
            currentColor = Color.valueOf(colorComboBox.getValue());
        });


        colorComboBox.getSelectionModel().selectFirst();
        sizeComboBox.getSelectionModel().selectFirst();
        //shapeComboBox.getSelectionModel().selectFirst();
        Button clearButton = new Button("Clear");
        clearButton.setOnAction(event -> {
            gc.setFill(Color.WHITE);
            gc.fillRect(0, 0, CANVAS_SIZE, CANVAS_SIZE + 200);
        });

        BorderPane root = new BorderPane(canvas);
        HBox buttonBox = new HBox(5); 

        buttonBox.setAlignment(Pos.CENTER);

        buttonBox.getChildren().addAll(colorComboBox, sizeComboBox,clearButton);

        root.setBottom(buttonBox);

        Scene scene = new Scene(root, 500, 900);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Paint");
        primaryStage.show();
    }

    private void handleMousePress(MouseEvent event, GraphicsContext gc) {
        int row = (int) (event.getY() / PIXEL_SIZE);
        int column = (int) (event.getX() / PIXEL_SIZE);
        drawPixels(gc, row, column);
    }
    

    private void handleMouseDrag(MouseEvent event, GraphicsContext gc) {
        int row = (int) (event.getY() / PIXEL_SIZE);
        int column = (int) (event.getX() / PIXEL_SIZE);
        drawPixels(gc, row, column);
    }

    private void drawPixels(GraphicsContext gc, int row, int column) {
        for (int i = row - brushRadius; i <= row + brushRadius; i ++) {
            for (int j = column - brushRadius; j <= column + brushRadius; j++) {
                if (i >= 0 && i < NUM_PIXELS && j >= 0 && j < NUM_PIXELS) {
                    int x = j * PIXEL_SIZE;
                    int y = i * PIXEL_SIZE;
                    gc.setFill(currentColor);
                    gc.fillRect(x, y, PIXEL_SIZE, PIXEL_SIZE);
                }
            }
        }
    }
    public static void main(String[] args) {
        launch(args);
    }
}            