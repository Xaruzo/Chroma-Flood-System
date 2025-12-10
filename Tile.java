import javafx.scene.effect.DropShadow;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

class Tile extends StackPane {
    private ColorType color;
    private ChromaFloodSystem gameInstance;
    private Rectangle rect;

    public Tile(double width, double height, ColorType initialColor, ChromaFloodSystem game) {
        super();
        this.color = initialColor;
        this.gameInstance = game;

        rect = new Rectangle(width, height, initialColor.getFxColor());

        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(6.0);
        dropShadow.setOffsetX(4.0);
        dropShadow.setOffsetY(4.0);
        dropShadow.setColor(Color.rgb(0, 0, 0, 0.8));

        rect.setArcWidth(10);
        rect.setArcHeight(10);

        getChildren().add(rect);

        // FIX: Apply the initial dropShadow to the Tile (StackPane) instead of the rect.
        // This ensures consistent effect application and prevents layering/darkening.
        setEffect(dropShadow);

        if (initialColor == ColorType.OBSTACLE) {
            // Obstacles cannot be clicked or changed
            setOnMouseClicked(null);  // Disable click
            setOnMouseEntered(null);  // Disable hover
            setOnMouseExited(null);
            rect.setFill(Color.web("#3d3a3d"));  // Ensure custom very dark gray color
            // Add smaller 'X' mark using two lines (60% of tile size)
            double offset = 0.2 * width;  // 20% margin on each side (40% total reduction)
            Line line1 = new Line(offset, offset, width - offset, height - offset);
            line1.setStroke(Color.web("#28272a"));  // Custom dark grayish-purple 'X'
            line1.setStrokeWidth(3);
            Line line2 = new Line(offset, height - offset, width - offset, offset);
            line2.setStroke(Color.web("#28272a"));  // Custom dark grayish-purple 'X'
            line2.setStrokeWidth(3);
            getChildren().addAll(line1, line2);
            // Stronger shadow for distinction
            setEffect(new DropShadow(10.0, Color.BLACK));
        } else {
            setOnMouseClicked(event -> {
                if (gameInstance != null) {
                    gameInstance.handleTileClick(this);
                }
            });

            setOnMouseEntered(event -> {
                DropShadow hoverEffect = new DropShadow();
                hoverEffect.setRadius(10.0);
                hoverEffect.setColor(Color.rgb(255, 255, 255, 0.9));
                setEffect(hoverEffect);
            });
            setOnMouseExited(event -> setEffect(dropShadow));
        }
    }

    public void setColor(ColorType newColor) {
        if (this.color == ColorType.OBSTACLE) {
            return;  // Prevent changing obstacle color
        }
        this.color = newColor;
        rect.setFill(newColor.getFxColor());
    }

    public ColorType getColor() {
        return color;
    }
}
