import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.HashSet;
import java.util.Set;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.net.URL;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import javafx.scene.media.AudioClip;
import java.nio.file.StandardCopyOption;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.stage.Modality;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import javafx.stage.FileChooser;
import javafx.concurrent.Task;
import javafx.scene.control.ProgressIndicator;
import javafx.animation.RotateTransition;
import javafx.animation.Animation;
import javafx.util.Duration;
import javafx.scene.layout.StackPane;
import java.util.Collections;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Polygon;
import javafx.animation.TranslateTransition;
import javafx.scene.CacheHint;
import java.util.Queue;
import java.util.LinkedList;
import javafx.animation.ScaleTransition;
import java.net.HttpURLConnection;
import java.util.Optional;

enum ColorType {
    GREEN(Color.GREEN),
    RED(Color.RED),
    BLUE(Color.BLUE),
    YELLOW(Color.YELLOW),
    OBSTACLE(Color.web("#3d3a3d"));  // Custom very dark gray color for obstacle tiles

    private final Color fxColor;

    ColorType(Color fxColor) {
        this.fxColor = fxColor;
    }

    public Color getFxColor() {
        return fxColor;
    }
}

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

public class ChromaFloodSystem extends Application {
    private static final int ROWS = 8;
    private static final int COLS = 10;
    private GridPane grid;
    private IntegerProperty movesRemaining = new SimpleIntegerProperty(0);
    private IntegerProperty currentLevel = new SimpleIntegerProperty(1);
    private ObservableList<ColorType> palette = FXCollections.observableArrayList(
            ColorType.BLUE, ColorType.RED, ColorType.YELLOW, ColorType.GREEN);
    private ColorType selectedColor = null;
    private Tile[][] tiles;
    private boolean isAnimating = false;
    private ColorType targetColor;
    private int lossCount = 0;
    private int extraMoves = 0;
    private Timeline floodFillTimeline = null;
    private boolean isCheckingSuccess = false;
    private Set<Integer> unlockedLevels = new HashSet<>();
    private BooleanProperty isFullScreen = new SimpleBooleanProperty(false); // Track full-screen state
    private BorderPane root; // Single root for all UI
    private Stage primaryStage; // Add as a class field
    private Image level1Image;
    private Image level2Image;
    private Image level3Image;
    private Image level4Image;
    private Image level5Image;
    private Image level6Image;
    private Image level7Image;
    private Image level8Image;
    private Image level9Image;
    private Image level10Image;
    private AudioClip clickSound;
    private MediaPlayer progressionPathAudio;
    private MediaPlayer level1Audio;
    private MediaPlayer level2Audio;
    private MediaPlayer level3Audio;
    private MediaPlayer level4Audio;
    private MediaPlayer level5Audio;
    private MediaPlayer level6Audio;
    private MediaPlayer level7Audio;
    private MediaPlayer level8Audio;
    private MediaPlayer level9Audio;
    private MediaPlayer level10Audio;
    private MediaPlayer currentBackgroundAudio;
    private static final String AUDIO_FOLDER = "audio";
    private static final String CLICK_AUDIO_FILE = "tile_click.mp3";
    private static final String PROGRESSION_PATH_AUDIO_FILE = "progression_path.mp3";
    private static final String LEVEL1_AUDIO_FILE = "level1.mp3";
    private static final String LEVEL2_AUDIO_FILE = "level2.mp3";
    private static final String LEVEL3_AUDIO_FILE = "level3.mp3";
    private static final String LEVEL4_AUDIO_FILE = "level4.mp3";
    private static final String LEVEL5_AUDIO_FILE = "level5.mp3";
    private static final String LEVEL6_AUDIO_FILE = "level6.mp3";
    private static final String LEVEL7_AUDIO_FILE = "level7.mp3";
    private static final String LEVEL8_AUDIO_FILE = "level8.mp3";
    private static final String LEVEL9_AUDIO_FILE = "level9.mp3";
    private static final String LEVEL10_AUDIO_FILE = "level10.mp3";
    private static final String[] AUDIO_FILES = {
            CLICK_AUDIO_FILE,
            PROGRESSION_PATH_AUDIO_FILE,
            LEVEL1_AUDIO_FILE,
            LEVEL2_AUDIO_FILE,
            LEVEL3_AUDIO_FILE,
            LEVEL4_AUDIO_FILE,
            LEVEL5_AUDIO_FILE,
            LEVEL6_AUDIO_FILE,
            LEVEL7_AUDIO_FILE,
            LEVEL8_AUDIO_FILE,
            LEVEL9_AUDIO_FILE,
            LEVEL10_AUDIO_FILE
    };
    private static final String[] DOWNLOAD_URLS = {
            "https://drive.google.com/uc?export=download&id=1fgpOsB1FqqoNe3SzGQUf35hhwpmFbnyL", // Tile click
            "https://drive.google.com/uc?export=download&id=1c1Px-b3izytaYdoW4aD-k_KCWtc67e4E", // Progression path
            "https://drive.google.com/uc?export=download&id=1nwwSS144XRrdkI_77FZVzLb_8JKDQ5yL", // Level 1
            "https://drive.google.com/uc?export=download&id=1pPtjkXefBHcUJmZejaCdFsXtpr8IXH25", // Level 2
            "https://drive.google.com/uc?export=download&id=1JmjIjOCXNPFuCE78VFBG0sNOjJ0DE9c3", // Level 3
            "https://drive.google.com/uc?export=download&id=1Y_CuuC8PY17LzzNpwM5szfjp4--hyX06", // Level 4
            "https://drive.google.com/uc?export=download&id=1SyGjIQRNh9azvEM6sjInItgNadYGmeQL", // Level 5
            "https://drive.google.com/uc?export=download&id=1HE2ZtUJ67B88l6Pb5i2MmQN9pN9A0hk-", // Level 6
            "https://drive.google.com/uc?export=download&id=1t9iWKJSbdW19ynRDmgalCyEJ9HuVlkeG", // Level 7
            "https://drive.google.com/uc?export=download&id=1wPsZduTM3-VIdW7qU9sNF6G8i13nF7vx", // Level 8
            "https://drive.google.com/uc?export=download&id=122W2qCE3aF6BisP0wosDJ6A04deMfCdH", // Level 9
            "https://drive.google.com/uc?export=download&id=1FZO9pjbCqGzhOXcpfrAMwSrTmJIMOetB"  // Level 10
    };
    private static final String AUDIO_FILE = "tile_click.mp3";
    private static final String DOWNLOAD_URL = "https://drive.google.com/uc?export=download&id=1fgpOsB1FqqoNe3SzGQUf35hhwpmFbnyL";
    private boolean isEffectsMuted = false; // For click sounds (SFX)
    private boolean isMusicMuted = false; // For background music
    private double audioVolume = 1.0; // Default volume (1.0 is full volume)
    private String currentUser = null;
    private long levelStartTime;

    private static final char[][][] LEVEL_PATTERNS = {
            {
                    {'G', 'G', 'Y', 'Y', 'Y', 'Y', 'Y', 'G', 'G', 'G'},
                    {'Y', 'G', 'G', 'B', 'B', 'B', 'B', 'B', 'Y', 'Y'},
                    {'Y', 'G', 'G', 'B', 'R', 'R', 'R', 'B', 'G', 'Y'},
                    {'Y', 'Y', 'G', 'B', 'G', 'R', 'G', 'B', 'G', 'Y'},
                    {'Y', 'B', 'B', 'B', 'R', 'B', 'G', 'B', 'G', 'Y'},
                    {'Y', 'Y', 'Y', 'G', 'G', 'R', 'B', 'B', 'Y', 'Y'},
                    {'Y', 'Y', 'Y', 'Y', 'G', 'G', 'G', 'Y', 'Y', 'Y'},
                    {'Y', 'Y', 'Y', 'Y', 'Y', 'G', 'G', 'Y', 'Y', 'Y'}
            },
            {
                    {'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G'},
                    {'B', 'B', 'Y', 'B', 'Y', 'Y', 'Y', 'B', 'Y', 'B'},
                    {'B', 'R', 'Y', 'R', 'Y', 'R', 'Y', 'R', 'Y', 'B'},
                    {'B', 'R', 'Y', 'G', 'G', 'G', 'G', 'G', 'Y', 'B'},
                    {'B', 'R', 'Y', 'R', 'Y', 'R', 'Y', 'R', 'Y', 'B'},
                    {'B', 'R', 'Y', 'R', 'Y', 'R', 'Y', 'R', 'Y', 'B'},
                    {'B', 'B', 'Y', 'Y', 'Y', 'B', 'Y', 'Y', 'Y', 'B'},
                    {'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G'}
            },
            {
                    {'R', 'R', 'R', 'R', 'R', 'R', 'R', 'R', 'B', 'R'},
                    {'R', 'B', 'B', 'B', 'B', 'B', 'Y', 'R', 'B', 'R'},
                    {'B', 'B', 'G', 'B', 'R', 'R', 'B', 'R', 'B', 'R'},
                    {'R', 'R', 'G', 'B', 'Y', 'Y', 'B', 'Y', 'Y', 'Y'},
                    {'R', 'R', 'G', 'G', 'G', 'G', 'B', 'G', 'G', 'G'},
                    {'R', 'R', 'G', 'B', 'B', 'B', 'Y', 'B', 'B', 'R'},
                    {'G', 'G', 'G', 'B', 'R', 'R', 'Y', 'R', 'B', 'R'},
                    {'R', 'R', 'R', 'B', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y'}
            },
            {
                    {'B', 'R', 'R', 'B', 'R', 'R', 'B', 'R', 'R', 'B'},
                    {'G', 'R', 'R', 'G', 'R', 'R', 'G', 'R', 'R', 'G'},
                    {'Y', 'Y', 'Y', 'Y', 'B', 'B', 'Y', 'Y', 'Y', 'Y'},
                    {'G', 'R', 'R', 'G', 'B', 'B', 'G', 'R', 'R', 'G'},
                    {'B', 'R', 'R', 'B', 'B', 'B', 'B', 'R', 'R', 'B'},
                    {'Y', 'Y', 'Y', 'Y', 'B', 'B', 'Y', 'Y', 'Y', 'Y'},
                    {'B', 'R', 'R', 'B', 'R', 'R', 'B', 'R', 'R', 'B'},
                    {'G', 'R', 'R', 'G', 'R', 'R', 'G', 'R', 'R', 'G'}
            },
            {
                    {'R', 'R', 'B', 'Y', 'R', 'R', 'R', 'R', 'R', 'R'},
                    {'B', 'B', 'B', 'Y', 'B', 'B', 'B', 'R', 'R', 'R'},
                    {'R', 'R', 'B', 'Y', 'R', 'R', 'R', 'R', 'R', 'B'},
                    {'B', 'G', 'G', 'Y', 'B', 'B', 'B', 'B', 'R', 'B'},
                    {'Y', 'G', 'G', 'G', 'G', 'G', 'G', 'Y', 'R', 'B'},
                    {'R', 'R', 'B', 'Y', 'R', 'R', 'R', 'R', 'R', 'B'},
                    {'R', 'B', 'B', 'Y', 'B', 'B', 'B', 'B', 'B', 'B'},
                    {'R', 'B', 'B', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y'}
            },
            {
                    {'R', 'B', 'B', 'B', 'G', 'O', 'R', 'B', 'G', 'R'},
                    {'R', 'O', 'R', 'O', 'O', 'R', 'R', 'O', 'B', 'O'},
                    {'O', 'O', 'R', 'Y', 'Y', 'B', 'Y', 'G', 'R', 'R'},
                    {'R', 'B', 'O', 'Y', 'R', 'B', 'B', 'G', 'G', 'R'},
                    {'Y', 'G', 'G', 'G', 'G', 'O', 'B', 'Y', 'Y', 'B'},  // Added 'O' here
                    {'Y', 'R', 'R', 'O', 'R', 'R', 'Y', 'G', 'G', 'O'},
                    {'G', 'R', 'O', 'O', 'Y', 'Y', 'O', 'B', 'B', 'R'},
                    {'Y', 'Y', 'Y', 'O', 'O', 'G', 'O', 'O', 'R', 'R'}
            },
            // Modified Level 7: Added 'O' obstacles
            {
                    {'R', 'R', 'B', 'R', 'R', 'R', 'G', 'G', 'G', 'Y'},
                    {'R', 'O', 'O', 'O', 'O', 'R', 'B', 'B', 'B', 'B'},
                    {'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G', 'O', 'O'},
                    {'G', 'G', 'B', 'B', 'O', 'O', 'R', 'R', 'R', 'O'},
                    {'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G', 'O'},
                    {'G', 'G', 'G', 'R', 'R', 'R', 'B', 'B', 'R', 'R'},  // Added 'O' here
                    {'G', 'B', 'B', 'O', 'B', 'R', 'O', 'O', 'O', 'R'},
                    {'Y', 'R', 'R', 'G', 'R', 'R', 'B', 'R', 'B', 'R'}
            },
            // Modified Level 8: Added 'O' obstacles
            {
                    {'G', 'G', 'Y', 'Y', 'G', 'G', 'Y', 'Y', 'G', 'G'},
                    {'G', 'Y', 'Y', 'B', 'Y', 'Y', 'B', 'Y', 'Y', 'G'},
                    {'Y', 'Y', 'B', 'B', 'R', 'R', 'B', 'B', 'Y', 'Y'},
                    {'Y', 'B', 'B', 'R', 'O', 'O', 'R', 'B', 'B', 'Y'},
                    {'Y', 'Y', 'B', 'R', 'O', 'O', 'R', 'B', 'Y', 'Y'},  // Added 'O' here
                    {'G', 'Y', 'Y', 'B', 'R', 'R', 'B', 'Y', 'Y', 'G'},
                    {'G', 'G', 'Y', 'Y', 'B', 'B', 'Y', 'Y', 'G', 'G'},
                    {'G', 'G', 'G', 'Y', 'Y', 'Y', 'Y', 'G', 'G', 'G'}
            },
            // Modified Level 9: Added 'O' obstacles
            {
                    {'B', 'B', 'B', 'G', 'R', 'Y', 'Y', 'B', 'B', 'B'},
                    {'B', 'B', 'B', 'G', 'R', 'B', 'Y', 'R', 'B', 'B'},
                    {'O', 'O', 'O', 'G', 'Y', 'B', 'Y', 'Y', 'G', 'R'},
                    {'R', 'R', 'Y', 'B', 'B', 'B', 'B', 'B', 'Y', 'Y'},
                    {'B', 'G', 'R', 'G', 'R', 'G', 'B', 'G', 'Y', 'B'},
                    {'B', 'B', 'B', 'G', 'G', 'G', 'Y', 'O', 'O', 'O'},
                    {'B', 'B', 'B', 'G', 'B', 'G', 'R', 'B', 'B', 'B'},  // Added 'O' here
                    {'B', 'B', 'B', 'G', 'B', 'G', 'R', 'B', 'B', 'B'}
            },
            // Modified Level 10: Added 'O' obstacles
            {
                    {'G', 'G', 'Y', 'Y', 'Y', 'Y', 'Y', 'G', 'G', 'G'},
                    {'Y', 'R', 'G', 'Y', 'Y', 'Y', 'G', 'R', 'R', 'Y'},
                    {'Y', 'R', 'R', 'B', 'Y', 'B', 'R', 'Y', 'R', 'Y'},
                    {'Y', 'R', 'Y', 'B', 'R', 'B', 'Y', 'Y', 'R', 'Y'},
                    {'Y', 'R', 'Y', 'G', 'B', 'G', 'Y', 'Y', 'R', 'Y'},
                    {'Y', 'R', 'R', 'B', 'Y', 'B', 'R', 'Y', 'R', 'Y'},
                    {'Y', 'R', 'R', 'Y', 'Y', 'Y', 'R', 'R', 'R', 'Y'},  // Added 'O' here
                    {'G', 'G', 'Y', 'Y', 'Y', 'Y', 'Y', 'G', 'G', 'G'}
            }
    };

    private void saveProgress() {
        if (currentUser == null) return;
        int retries = 3;
        while (retries > 0) {
            try (Connection conn = getConnection()) {
                conn.setAutoCommit(false);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(baos);
                oos.writeObject(unlockedLevels);
                oos.close();
                byte[] levelsBytes = baos.toByteArray();

                PreparedStatement pstmt = conn.prepareStatement(
                        "UPDATE users SET unlockedLevels = ?, isEffectsMuted = ?, isMusicMuted = ?, audioVolume = ? WHERE username = ?");
                pstmt.setBytes(1, levelsBytes);
                pstmt.setBoolean(2, isEffectsMuted);
                pstmt.setBoolean(3, isMusicMuted);
                pstmt.setDouble(4, audioVolume);
                pstmt.setString(5, currentUser);
                pstmt.executeUpdate();
                conn.commit();
                return;
            } catch (IOException | SQLException e) {
                if (e.getMessage().contains("database is locked") && retries > 1) {
                    retries--;
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                    }
                } else {
                    System.err.println("Error saving progress: " + e.getMessage());
                    return;
                }
            }
        }
    }

    private void showCompletionScreen() {
        root.getChildren().clear();
        root.setStyle("-fx-background-color: radial-gradient(center 50% 50%, radius 80%, #1C2526, #0A0E26);");
        primaryStage.setTitle("Chroma Flood - Puzzle Complete");

        double totalTime = 0.0;
        try (Connection conn = getConnection()) {
            PreparedStatement pstmt = conn.prepareStatement(
                    "SELECT SUM(completionTime) as totalTime FROM leaderboard WHERE username = ?");
            pstmt.setString(1, currentUser);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                totalTime = rs.getDouble("totalTime");
            }
        } catch (SQLException e) {
            System.err.println("Error fetching total time: " + e.getMessage());
        }

        Label title = new Label("Puzzle Complete!");
        title.setStyle("-fx-text-fill: #FFFFFF; -fx-font-family: 'Arial Black'; -fx-font-size: 36; -fx-font-weight: bold; -fx-effect: dropshadow(gaussian, #00FFFF, 10, 0.5, 0, 0);");

        Label scoreLabel = new Label(String.format("Final Score: %.2f seconds", totalTime));
        scoreLabel.setStyle("-fx-text-fill: #00FFFF; -fx-font-family: 'Arial Black'; -fx-font-size: 24; -fx-font-weight: bold; -fx-effect: dropshadow(gaussian, #000000, 5, 0.5, 0, 0);");

        Button continueButton = new Button("Continue");
        continueButton.setStyle("-fx-background-color: linear-gradient(to bottom, #44FF44, #00CC00); -fx-text-fill: #FFFFFF; -fx-font-family: 'Arial Black'; -fx-font-size: 18; -fx-font-weight: bold; -fx-background-radius: 20; -fx-padding: 15 30; -fx-effect: dropshadow(gaussian, #000000, 8, 0.5, 0, 0);");
        continueButton.setOnAction(event -> showLevelSelectScreen());
        continueButton.setOnMouseEntered(event -> continueButton.setStyle("-fx-background-color: linear-gradient(to bottom, #66FF66, #22EE22); -fx-text-fill: #FFFFFF; -fx-font-family: 'Arial Black'; -fx-font-size: 18; -fx-font-weight: bold; -fx-background-radius: 20; -fx-padding: 15 30; -fx-effect: dropshadow(gaussian, #000000, 10, 0.7, 0, 0);"));
        continueButton.setOnMouseExited(event -> continueButton.setStyle("-fx-background-color: linear-gradient(to bottom, #44FF44, #00CC00); -fx-text-fill: #FFFFFF; -fx-font-family: 'Arial Black'; -fx-font-size: 18; -fx-font-weight: bold; -fx-background-radius: 20; -fx-padding: 15 30; -fx-effect: dropshadow(gaussian, #000000, 8, 0.5, 0, 0);"));

        VBox container = new VBox(30, title, scoreLabel, continueButton);
        container.setAlignment(Pos.CENTER);
        root.setCenter(container);
    }

    private List<Tile> getFloodFillTiles(int startRow, int startCol, ColorType originalColor, ColorType newColor) {
        List<Tile> tilesToChange = new ArrayList<>();
        Set<String> visited = new HashSet<>();
        Queue<int[]> queue = new LinkedList<>();

        // Mark the clicked tile as visited to avoid processing it
        visited.add(startRow + "," + startCol);
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        // Check adjacent tiles
        for (int[] dir : directions) {
            int nr = startRow + dir[0];
            int nc = startCol + dir[1];
            if (nr >= 0 && nr < ROWS && nc >= 0 && nc < COLS &&
                    tiles[nr][nc].getColor() == originalColor &&
                    tiles[nr][nc].getColor() != ColorType.OBSTACLE) {
                queue.add(new int[]{nr, nc});
                visited.add(nr + "," + nc);
                tilesToChange.add(tiles[nr][nc]);
            }
        }

        // BFS to find all connected tiles
        while (!queue.isEmpty()) {
            int[] pos = queue.poll();
            int row = pos[0];
            int col = pos[1];
            for (int[] dir : directions) {
                int nr = row + dir[0];
                int nc = col + dir[1];
                String key = nr + "," + nc;
                if (nr >= 0 && nr < ROWS && nc >= 0 && nc < COLS &&
                        !visited.contains(key) &&
                        tiles[nr][nc].getColor() == originalColor &&
                        tiles[nr][nc].getColor() != ColorType.OBSTACLE) {
                    queue.add(new int[]{nr, nc});
                    visited.add(key);
                    tilesToChange.add(tiles[nr][nc]);
                }
            }
        }
        return tilesToChange;
    }

    private void animateFloodFill(List<Tile> tilesToChange, ColorType newColor) {
        try {
            // Fallback for single tile to ensure color change
            if (tilesToChange.size() == 1) {
                Tile tile = tilesToChange.get(0);
                tile.setColor(newColor); // Immediate color change
                ScaleTransition scale = new ScaleTransition(Duration.millis(100), tile);
                scale.setFromX(1.0);
                scale.setFromY(1.0);
                scale.setToX(1.2);
                scale.setToY(1.2);
                scale.setAutoReverse(true);
                scale.setCycleCount(2);
                scale.setOnFinished(event -> {
                    // Force update tile color to ensure state is consistent
                    tile.setColor(newColor);
                    isAnimating = false; // Unlock clicks
                    checkSuccess(); // Check success directly
                });
                if (!isAnimating) return; // Skip audio if reset has occurred
                clickSound.play();
                scale.play();
                return;
            }

            // Sequential animation for multiple tiles
            floodFillTimeline = new Timeline(); // Store in class field
            int delay = 50; // Milliseconds between each tile's animation
            for (int i = 0; i < tilesToChange.size(); i++) {
                Tile tile = tilesToChange.get(i);
                KeyFrame keyFrame = new KeyFrame(
                        Duration.millis(i * delay),
                        event -> {
                            tile.setColor(newColor); // Change color in sync with animation
                            if (!isEffectsMuted) {  // Add this check
                                clickSound.play();
                            }
                            // Add a brief scale animation for visual appeal
                            ScaleTransition scale = new ScaleTransition(Duration.millis(100), tile);
                            scale.setFromX(1.0);
                            scale.setFromY(1.0);
                            scale.setToX(1.2);
                            scale.setToY(1.2);
                            scale.setAutoReverse(true);
                            scale.setCycleCount(2);
                            scale.play();
                        }
                );
                floodFillTimeline.getKeyFrames().add(keyFrame);
            }
            // Unlock clicks and check success when animation completes
            floodFillTimeline.setOnFinished(event -> {
                // Force update all tiles to ensure state is consistent
                for (Tile tile : tilesToChange) {
                    tile.setColor(newColor);
                }
                isAnimating = false;
                checkSuccess(); // Check success directly
            });
            // Play the animation if there are tiles to change
            if (!tilesToChange.isEmpty()) {
                floodFillTimeline.play();
            } else {
                isAnimating = false; // Unlock if no animation occurs
            }
        } catch (Exception e) {
            System.err.println("Error in animateFloodFill: " + e.getMessage());
            e.printStackTrace();
            // Fallback: Ensure tiles are updated and check success
            for (Tile tile : tilesToChange) {
                tile.setColor(newColor);
            }
            isAnimating = false;
            checkSuccess();
        }
    }

    private void loadProgress() {
        if (currentUser == null) return;
        try (Connection conn = getConnection()) {
            PreparedStatement pstmt = conn.prepareStatement(
                    "SELECT unlockedLevels, isEffectsMuted, isMusicMuted, audioVolume FROM users WHERE username = ?");
            pstmt.setString(1, currentUser);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                byte[] levelsBytes = rs.getBytes("unlockedLevels");
                if (levelsBytes != null && levelsBytes.length > 0) {
                    ByteArrayInputStream bais = new ByteArrayInputStream(levelsBytes);
                    ObjectInputStream ois = new ObjectInputStream(bais);
                    unlockedLevels = (HashSet<Integer>) ois.readObject();
                    ois.close();
                } else {
                    unlockedLevels = new HashSet<>();
                    unlockedLevels.add(Integer.valueOf(1));
                }
                isEffectsMuted = rs.getBoolean("isEffectsMuted");
                isMusicMuted = rs.getBoolean("isMusicMuted");
                audioVolume = rs.getDouble("audioVolume");
            } else {
                unlockedLevels = new HashSet<>();
                unlockedLevels.add(Integer.valueOf(1));
                isEffectsMuted = false;
                isMusicMuted = false;
                audioVolume = 1.0;
                saveProgress();
            }
            updateAudioSettings();
        } catch (SQLException | IOException | ClassNotFoundException e) {
            System.err.println("Error loading progress: " + e.getMessage());
            unlockedLevels = new HashSet<>();
            unlockedLevels.add(Integer.valueOf(1));
            isEffectsMuted = false;
            isMusicMuted = false;
            audioVolume = 1.0;
        }
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        updateImageResources();
        root = new BorderPane();
        Scene scene = new Scene(root, 900, 700);
        scene.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            KeyCode code = event.getCode();
            if (code.isDigitKey()) {
                try {
                    int digit = Integer.parseInt(event.getText());
                    if (digit >= 1 && digit <= 4) {
                        int index = digit - 1;
                        selectedColor = palette.get(index);
                        updatePaletteVisuals();
                        if (root.getRight() instanceof VBox paletteBox) {
                            paletteBox.getChildren().forEach(node -> {
                                if (node instanceof VBox vbox) {
                                    HBox orbWithArrow = (HBox) vbox.getChildren().get(0);
                                    Circle orb = (Circle) orbWithArrow.getChildren().get(0);
                                    Polygon arrow = (Polygon) orbWithArrow.getChildren().get(1);
                                    if (orb.getFill().equals(selectedColor.getFxColor())) {
                                        orb.setEffect(new Glow(1.0));
                                        orb.setStroke(Color.WHITE);
                                        arrow.setVisible(true);
                                    } else {
                                        orb.setEffect(new Glow(0.3));
                                        orb.setStroke(Color.TRANSPARENT);
                                        arrow.setVisible(false);
                                    }
                                }
                            });
                        }
                    }
                } catch (NumberFormatException e) {
                    // ignore
                }
            }
        });
        primaryStage.setScene(scene);
        primaryStage.setTitle("Chroma Flood");

        createDatabase(); // Initialize SQLite DB

        // Create resources/images folder and download default profile picture
        File imagesFolder = new File("resources/images");
        if (!imagesFolder.exists()) {
            imagesFolder.mkdirs();
        }
        File defaultProfilePic = new File("resources/images/default_profile.png");
        if (!defaultProfilePic.exists()) {
            try {
                // Convert Google Drive sharing link to direct download link
                String fileId = "1m1yQ0995BXEa4tBMS_R2p9jY81uc4kAF";
                String downloadUrl = "https://drive.google.com/uc?export=download&id=" + fileId;
                InputStream in = new URL(downloadUrl).openStream();
                Files.copy(in, defaultProfilePic.toPath(), StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Default profile picture downloaded to: " + defaultProfilePic.getPath());
            } catch (IOException e) {
                System.err.println("Failed to download default profile picture: " + e.getMessage());
            }
        }

        // Initialize audio resources
        downloadAudioResources();
        updateAudioSettings(); // Apply default audio settings initially

        // Add this call in the start() method, after downloadAudioResources() and before the session check
        downloadImageResources();level1Image = new Image(new File(IMAGE_FOLDER + File.separator + IMAGE_FILES[0]).toURI().toString());
        level2Image = new Image(new File(IMAGE_FOLDER + File.separator + IMAGE_FILES[1]).toURI().toString());
        level3Image = new Image(new File(IMAGE_FOLDER + File.separator + IMAGE_FILES[2]).toURI().toString());
        level4Image = new Image(new File(IMAGE_FOLDER + File.separator + IMAGE_FILES[3]).toURI().toString());
        level5Image = new Image(new File(IMAGE_FOLDER + File.separator + IMAGE_FILES[4]).toURI().toString());
        level6Image = new Image(new File(IMAGE_FOLDER + File.separator + IMAGE_FILES[5]).toURI().toString());

        // Show loading screen while checking session
        showLoadingScreen();

        // Check session in a background task
        Task<Void> sessionTask = new Task<>() {
            @Override
            protected Void call() throws Exception {
                // Simulate minimum loading time
                Thread.sleep(2000);
                return null;
            }
        };

        sessionTask.setOnSucceeded(event -> {
            Platform.runLater(() -> {
                try (Connection conn = getConnection()) {
                    ResultSet rs = conn.createStatement().executeQuery("SELECT username FROM session WHERE id = 1");
                    if (rs.next()) {
                        currentUser = rs.getString("username");
                        loadProgress();
                        showLevelSelectScreen();
                    } else {
                        showLoginScreen();
                    }
                } catch (SQLException e) {
                    System.err.println("Error checking session: " + e.getMessage());
                    showLoginScreen();
                }
            });
        });

        sessionTask.setOnFailed(event -> {
            Platform.runLater(() -> {
                System.err.println("Session check failed: " + sessionTask.getException().getMessage());
                showLoginScreen();
            });
        });

        new Thread(sessionTask).start();

        // Sync isFullScreen with stage's fullScreenProperty
        primaryStage.fullScreenProperty().addListener((obs, wasFullScreen, isNowFullScreen) -> {
            isFullScreen.set(isNowFullScreen);
        });

        // Add key binding for full-screen toggle (F11)
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.F11) {
                Platform.runLater(() -> {
                    primaryStage.setFullScreen(!primaryStage.isFullScreen());
                    primaryStage.setFullScreenExitHint("");
                    primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
                });
            }
        });

        // Ensure full-screen state is reapplied after UI updates
        isFullScreen.addListener((obs, wasFullScreen, isNowFullScreen) -> {
            if (isNowFullScreen) {
                Platform.runLater(() -> {
                    primaryStage.setFullScreen(true);
                    primaryStage.setFullScreenExitHint("");
                    primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
                });
            }
        });

        primaryStage.show();
    }

    private boolean hasInternetConnection() {
        try {
            URL url = new URL("https://www.google.com");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(3000); // 3-second timeout
            connection.setRequestMethod("HEAD");
            connection.connect();
            int responseCode = connection.getResponseCode();
            return responseCode >= 200 && responseCode < 400; // Success range
        } catch (IOException e) {
            System.err.println("No internet connection: " + e.getMessage());
            return false;
        }
    }

    private boolean areResourcesMissing() {
        // Check audio files
        for (String audioFile : AUDIO_FILES) {
            File file = new File(AUDIO_FOLDER + File.separator + audioFile);
            if (!file.exists()) {
                return true;
            }
        }
        // Check image files
        for (String imageFile : IMAGE_FILES) {
            File file = new File(IMAGE_FOLDER + File.separator + imageFile);
            if (!file.exists()) {
                return true;
            }
        }
        return false;
    }

    private void showOfflineWarningDialog() {
        int maxRetries = 3; // Limit retry attempts
        int retryCount = 0;
        boolean resourcesMissing = areResourcesMissing();

        while (resourcesMissing && retryCount < maxRetries) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.initOwner(primaryStage);
            alert.setTitle("Resources Missing");
            alert.setHeaderText("No Internet Connection");
            // Update message based on retry count
            String message = retryCount == 0
                    ? "Some audio and image resources are not yet downloaded and require an internet connection. "
                    + "The game may run in offline mode with missing sounds or images. What would you like to do?"
                    : "Retry failed: Still no internet connection. Please check your connection. "
                    + (maxRetries - retryCount) + " retries remaining.";
            alert.setContentText(message);

            ButtonType continueButton = new ButtonType("Continue Offline");
            ButtonType retryButton = new ButtonType("Retry Download");
            ButtonType exitButton = new ButtonType("Exit");

            alert.getButtonTypes().setAll(continueButton, retryButton, exitButton);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent()) {
                if (result.get() == retryButton) {
                    retryCount++;
                    if (hasInternetConnection()) {
                        downloadAudioResources();
                        downloadImageResources();
                        resourcesMissing = areResourcesMissing(); // Re-check after download
                        if (!resourcesMissing) {
                            // Downloads succeeded, exit loop
                            return;
                        }
                        // Continue loop if resources still missing
                    } else {
                        // No connection, continue loop to show dialog again
                    }
                } else if (result.get() == exitButton) {
                    Platform.exit();
                    return;
                } else if (result.get() == continueButton) {
                    // Show confirmation for degraded mode
                    Alert confirmation = new Alert(AlertType.INFORMATION);
                    confirmation.initModality(Modality.APPLICATION_MODAL);
                    confirmation.initOwner(primaryStage);
                    confirmation.setTitle("Offline Mode");
                    confirmation.setHeaderText(null);
                    confirmation.setContentText("Continuing in offline mode with limited resources. Some audio or images may be missing.");
                    confirmation.showAndWait();
                    return; // Proceed with degraded mode
                }
            } else {
                // Dialog closed without selection (unlikely due to modality), treat as continue
                return;
            }
        }

        // If max retries reached and resources still missing
        if (resourcesMissing) {
            Alert finalAlert = new Alert(AlertType.WARNING);
            finalAlert.initModality(Modality.APPLICATION_MODAL);
            finalAlert.initOwner(primaryStage);
            finalAlert.setTitle("Resources Missing");
            finalAlert.setHeaderText("Maximum Retries Reached");
            finalAlert.setContentText("Unable to download resources after " + maxRetries + " attempts. "
                    + "You can continue in offline mode with limited resources or exit the game.");

            ButtonType continueButton = new ButtonType("Continue Offline");
            ButtonType exitButton = new ButtonType("Exit");

            finalAlert.getButtonTypes().setAll(continueButton, exitButton);

            Optional<ButtonType> result = finalAlert.showAndWait();
            if (result.isPresent() && result.get() == exitButton) {
                Platform.exit();
            } else {
                // Show confirmation for degraded mode
                Alert confirmation = new Alert(AlertType.INFORMATION);
                confirmation.initModality(Modality.APPLICATION_MODAL);
                confirmation.initOwner(primaryStage);
                confirmation.setTitle("Offline Mode");
                confirmation.setHeaderText(null);
                confirmation.setContentText("Continuing in offline mode with limited resources. Some audio or images may be missing.");
                confirmation.showAndWait();
            }
        }
    }

    private void createDatabase() {
        try (Connection conn = getConnection()) {
            Statement stmt = conn.createStatement();
            // Create users table
            stmt.execute("CREATE TABLE IF NOT EXISTS users (" +
                    "username TEXT PRIMARY KEY, " +
                    "password TEXT NOT NULL, " +
                    "unlockedLevels BLOB, " +
                    "isEffectsMuted BOOLEAN DEFAULT FALSE, " +  // New: for SFX
                    "isMusicMuted BOOLEAN DEFAULT FALSE, " +    // New: for music
                    "audioVolume DOUBLE DEFAULT 1.0, " +
                    "profilePicture BLOB" +
                    ")");
            // Create session table
            stmt.execute("CREATE TABLE IF NOT EXISTS session (" +
                    "id INTEGER PRIMARY KEY CHECK (id = 1), " +
                    "username TEXT, " +
                    "FOREIGN KEY (username) REFERENCES users(username) ON UPDATE CASCADE" +
                    ")");
// Create leaderboard table
            stmt.execute("CREATE TABLE IF NOT EXISTS leaderboard (" +
                    "username TEXT, " +
                    "level INTEGER, " +
                    "completionTime DOUBLE, " +
                    "PRIMARY KEY (username, level), " +
                    "FOREIGN KEY (username) REFERENCES users(username) ON UPDATE CASCADE" +
                    ")");
        } catch (SQLException e) {
            System.err.println("Error creating database: " + e.getMessage());
        }
    }

    private void showLoginScreen() {
        root.getChildren().clear();
        root.setStyle("-fx-background-color: radial-gradient(center 50% 50%, radius 80%, #1C2526, #0A0E26);");
        primaryStage.setTitle("Chroma Flood - Login");

        // Create a fixed-width container for the form
        VBox container = new VBox(20);
        container.setAlignment(Pos.CENTER);
        container.setPrefWidth(300); // Fixed width for the form
        container.setMaxWidth(300); // Prevent stretching
        container.setPadding(new Insets(20));

        // Create a background rectangle
        Rectangle backgroundRect = new Rectangle(300, 300); // Adjust height based on content
        backgroundRect.setFill(Color.rgb(0, 0, 0, 0.5)); // Semi-transparent black
        backgroundRect.setArcWidth(20); // Rounded corners
        backgroundRect.setArcHeight(20);
        backgroundRect.setEffect(new DropShadow(10, Color.rgb(0, 0, 0, 0.8)));

        // Create the form content
        VBox loginBox = new VBox(15); // Reduced spacing for tighter layout
        loginBox.setAlignment(Pos.CENTER);
        loginBox.setPadding(new Insets(15));

        Label title = new Label("Login");
        title.setStyle("-fx-text-fill: #FFFFFF; -fx-font-family: 'Arial Black'; -fx-font-size: 24; -fx-font-weight: bold; -fx-effect: dropshadow(gaussian, #000000, 5, 0.5, 0, 0);");

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        usernameField.setStyle("-fx-background-color: #FFFFFF; -fx-text-fill: #000000; -fx-font-size: 14; -fx-padding: 8; -fx-background-radius: 10;");
        usernameField.setPrefWidth(150);
        usernameField.setMaxWidth(150); // Prevent stretching

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setStyle("-fx-background-color: #FFFFFF; -fx-text-fill: #000000; -fx-font-size: 14; -fx-padding: 8; -fx-background-radius: 10;");
        passwordField.setPrefWidth(150);
        passwordField.setMaxWidth(150); // Prevent stretching

        Button loginButton = new Button("Login");
        loginButton.setStyle("-fx-background-color: linear-gradient(to bottom, #4444FF, #0000CC); -fx-text-fill: #FFFFFF; -fx-font-family: 'Arial Black'; -fx-font-size: 14; -fx-font-weight: bold; -fx-background-radius: 15; -fx-padding: 8 15; -fx-effect: dropshadow(gaussian, #000000, 5, 0.5, 0, 0);");
        loginButton.setPrefWidth(150); // Match input field width

        loginButton.setOnAction(event -> {
            String user = usernameField.getText().trim();
            String pass = passwordField.getText().trim();
            if (user.isEmpty() || pass.isEmpty()) {
                new Alert(AlertType.ERROR, "Username and password cannot be empty.").showAndWait();
                return;
            }

            // Disable login button to prevent multiple clicks
            loginButton.setDisable(true);

            // Show loading screen
            showLoadingScreen();

            // Perform authentication and progress loading in a background thread
            Task<Boolean> loginTask = new Task<>() {
                @Override
                protected Boolean call() throws Exception {
                    // Ensure minimum loading time of 2 seconds for spinner visibility
                    long startTime = System.currentTimeMillis();
                    boolean result = authenticate(user, pass);
                    long elapsedTime = System.currentTimeMillis() - startTime;
                    long remainingTime = 2000 - elapsedTime; // 2 seconds total
                    if (remainingTime > 0) {
                        Thread.sleep(remainingTime);
                    }
                    return Boolean.valueOf(result);
                }
            };

            loginTask.setOnSucceeded(taskEvent -> {
                Platform.runLater(() -> {
                    boolean isAuthenticated = loginTask.getValue();
                    if (isAuthenticated) {
                        currentUser = user;
                        // Save the logged-in user to the session table
                        try (Connection conn = getConnection()) {
                            // Clear any existing session
                            conn.createStatement().execute("DELETE FROM session");
                            // Insert new session
                            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO session (id, username) VALUES (1, ?)");
                            pstmt.setString(1, user);
                            pstmt.executeUpdate();
                        } catch (SQLException e) {
                            System.err.println("Error saving session: " + e.getMessage());
                        }
                        loadProgress();
                        // Use Timeline to delay transition to level select screen
                        Timeline transitionDelay = new Timeline(new KeyFrame(
                                Duration.millis(500),
                                ae -> showLevelSelectScreen()
                        ));
                        transitionDelay.play();
                    } else {
                        new Alert(AlertType.ERROR, "Invalid username or password.").showAndWait();
                        showLoginScreen();
                    }
                });
            });

            loginTask.setOnFailed(taskEvent -> {
                Platform.runLater(() -> {
                    new Alert(AlertType.ERROR, "An error occurred during login: " + loginTask.getException().getMessage()).showAndWait();
                    showLoginScreen();
                });
            });

            // Start the task in a new thread
            new Thread(loginTask).start();
        });

        loginButton.setOnMouseEntered(event -> loginButton.setStyle("-fx-background-color: linear-gradient(to bottom, #6666FF, #2222EE); -fx-text-fill: #FFFFFF; -fx-font-family: 'Arial Black'; -fx-font-size: 14; -fx-font-weight: bold; -fx-background-radius: 15; -fx-padding: 8 15; -fx-effect: dropshadow(gaussian, #000000, 8, 0.7, 0, 0);"));
        loginButton.setOnMouseExited(event -> loginButton.setStyle("-fx-background-color: linear-gradient(to bottom, #4444FF, #0000CC); -fx-text-fill: #FFFFFF; -fx-font-family: 'Arial Black'; -fx-font-size: 14; -fx-font-weight: bold; -fx-background-radius: 15; -fx-padding: 8 15; -fx-effect: dropshadow(gaussian, #000000, 5, 0.5, 0, 0);"));

        Button goToRegisterButton = new Button("Go to Register");
        goToRegisterButton.setStyle("-fx-background-color: linear-gradient(to bottom, #44FF44, #00CC00); -fx-text-fill: #FFFFFF; -fx-font-family: 'Arial Black'; -fx-font-size: 14; -fx-font-weight: bold; -fx-background-radius: 15; -fx-padding: 8 15; -fx-effect: dropshadow(gaussian, #000000, 5, 0.5, 0, 0);");
        goToRegisterButton.setPrefWidth(150); // Match input field width
        goToRegisterButton.setOnAction(event -> showRegisterScreen());
        goToRegisterButton.setOnMouseEntered(event -> goToRegisterButton.setStyle("-fx-background-color: linear-gradient(to bottom, #66FF66, #22EE22); -fx-text-fill: #FFFFFF; -fx-font-family: 'Arial Black'; -fx-font-size: 14; -fx-font-weight: bold; -fx-background-radius: 15; -fx-padding: 8 15; -fx-effect: dropshadow(gaussian, #000000, 8, 0.7, 0, 0);"));
        goToRegisterButton.setOnMouseExited(event -> goToRegisterButton.setStyle("-fx-background-color: linear-gradient(to bottom, #44FF44, #00CC00); -fx-text-fill: #FFFFFF; -fx-font-family: 'Arial Black'; -fx-font-size: 14; -fx-font-weight: bold; -fx-background-radius: 15; -fx-padding: 8 15; -fx-effect: dropshadow(gaussian, #000000, 5, 0.5, 0, 0);"));

        loginBox.getChildren().addAll(title, usernameField, passwordField, loginButton, goToRegisterButton);

        // Stack the background rectangle and form
        StackPane formStack = new StackPane();
        formStack.getChildren().addAll(backgroundRect, loginBox);
        container.getChildren().add(formStack);
        root.setCenter(container);
    }

    private static final String IMAGE_FOLDER = "resources/images";
    private static final String[] IMAGE_FILES = {
            "level1.png",
            "level2.png",
            "level3.png",
            "level4.png",
            "level5.png",
            "level6.png",
            "level7.png",
            "level8.png",
            "level9.png",
            "level10.png"
    };
    private static final String[] IMAGE_DOWNLOAD_URLS = {
            "https://drive.google.com/uc?export=download&id=1qlObt9ZmRK0E757PW-KDL6ko7bzn3CmS", // Level 1
            "https://drive.google.com/uc?export=download&id=1OaWXO0vQDCh0XsVStJLGcVY89HEtrs0O", // Level 2
            "https://drive.google.com/uc?export=download&id=1Q-WXzTG6VhRCjV-dS-KBn0uNZaiha1YQ", // Level 3
            "https://drive.google.com/uc?export=download&id=1sR8fJ1xqC25cNiSVMBwdP9PfGhFxpAAW", // Level 4
            "https://drive.google.com/uc?export=download&id=1U_IV7rB1vn9jxK8PmPKI8XPEGH-MMpat", // Level 5
            "https://drive.google.com/uc?export=download&id=15BOn3kHa0yILWCDSGTQP70j34VGdSKer", // Level 6
            "https://drive.google.com/uc?export=download&id=12qlw6-Z30V2mnRYaH07MangnU0TvPE6L", // Level 7
            "https://drive.google.com/uc?export=download&id=1Z-PA6mZCDyemWGc_0paUZvZqqlS2HZCx", // Level 8
            "https://drive.google.com/uc?export=download&id=1atqYAZ5Fp2h06-XlFRebdRayf3cWkeu0", // Level 9
            "https://drive.google.com/uc?export=download&id=1cklw9U04gqHaKb3FVAY7SBA9song33mV"  // Level 10
    };

    private void downloadImageResources() {
        File folder = new File(IMAGE_FOLDER);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        for (int i = 0; i < IMAGE_FILES.length; i++) {
            File imageFile = new File(IMAGE_FOLDER + File.separator + IMAGE_FILES[i]);
            if (!imageFile.exists() && IMAGE_DOWNLOAD_URLS[i] != null) {
                try {
                    InputStream in = new URL(IMAGE_DOWNLOAD_URLS[i]).openStream();
                    Files.copy(in, Paths.get(imageFile.getPath()), StandardCopyOption.REPLACE_EXISTING);
                    System.out.println("Downloaded image: " + imageFile.getPath());
                } catch (IOException e) {
                    System.err.println("Failed to download image file: " + IMAGE_FILES[i] + " - " + e.getMessage());
                    // No alert here - handled centrally
                }
            }
        }
    }

    private void showRegisterScreen() {
        root.getChildren().clear();
        root.setStyle("-fx-background-color: radial-gradient(center 50% 50%, radius 80%, #1C2526, #0A0E26);");
        primaryStage.setTitle("Chroma Flood - Register");

        // Create a fixed-width container for the form (increased width from 300 to 400)
        VBox container = new VBox(20);
        container.setAlignment(Pos.CENTER);
        container.setPrefWidth(400); // Increased from 300 to 400
        container.setMaxWidth(400); // Prevent stretching
        container.setPadding(new Insets(20));

        // Create a background rectangle (increased width from 300 to 400)
        Rectangle backgroundRect = new Rectangle(400, 450); // Width increased from 300 to 400
        backgroundRect.setFill(Color.rgb(0, 0, 0, 0.5)); // Semi-transparent black
        backgroundRect.setArcWidth(20); // Rounded corners
        backgroundRect.setArcHeight(20);
        backgroundRect.setEffect(new DropShadow(10, Color.rgb(0, 0, 0, 0.8)));

        // Create the form content
        VBox registerBox = new VBox(15); // Reduced spacing for tighter layout
        registerBox.setAlignment(Pos.CENTER);
        registerBox.setPadding(new Insets(15));

        Label title = new Label("Register");
        title.setStyle("-fx-text-fill: #FFFFFF; -fx-font-family: 'Arial Black'; -fx-font-size: 24; -fx-font-weight: bold; -fx-effect: dropshadow(gaussian, #000000, 5, 0.5, 0, 0);");

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        usernameField.setStyle("-fx-background-color: #FFFFFF; -fx-text-fill: #000000; -fx-font-size: 14; -fx-padding: 8; -fx-background-radius: 10;");
        usernameField.setPrefWidth(200); // Increased from 150 to 200 for wider form
        usernameField.setMaxWidth(200); // Prevent stretching

        usernameField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("^[a-zA-Z0-9_]*$")) {
                usernameField.setText(oldValue);
            } else if (newValue.length() > 20) {
                usernameField.setText(oldValue);
            }
        });

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setStyle("-fx-background-color: #FFFFFF; -fx-text-fill: #000000; -fx-font-size: 14; -fx-padding: 8; -fx-background-radius: 10;");
        passwordField.setPrefWidth(200); // Increased from 150 to 200 for wider form
        passwordField.setMaxWidth(200); // Prevent stretching

        // Enforce max password length of 32 characters in real-time
        passwordField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > 32) {
                passwordField.setText(oldValue);
            }
        });

        // Profile picture preview with circle outline
        ImageView profilePreview = new ImageView();
        profilePreview.setFitWidth(100);
        profilePreview.setFitHeight(100);
        Circle clipCircle = new Circle(50, 50, 50);
        profilePreview.setClip(clipCircle);
        profilePreview.setEffect(new DropShadow(10, Color.rgb(0, 0, 0, 0.8)));

        // Circle outline
        Circle outlineCircle = new Circle(50, 50, 50);
        outlineCircle.setFill(Color.TRANSPARENT);
        outlineCircle.setStroke(Color.rgb(0, 255, 255, 0.8)); // Cyan stroke
        outlineCircle.setStrokeWidth(2);

        // Load default profile picture
        File defaultProfilePic = new File("resources/images/default_profile.png");
        if (defaultProfilePic.exists()) {
            profilePreview.setImage(new Image(defaultProfilePic.toURI().toString()));
        } else {
            System.err.println("Default profile picture not found at: " + defaultProfilePic.getPath());
        }

        StackPane profileStack = new StackPane();
        profileStack.getChildren().addAll(profilePreview, outlineCircle);

        File[] selectedFile = {null};
        Button uploadButton = new Button("Upload Profile Picture");
        uploadButton.setStyle("-fx-background-color: linear-gradient(to bottom, #FFAA00, #CC8800); -fx-text-fill: #FFFFFF; -fx-font-family: 'Arial Black'; -fx-font-size: 14; -fx-font-weight: bold; -fx-background-radius: 15; -fx-padding: 8 15; -fx-effect: dropshadow(gaussian, #000000, 5, 0.5, 0, 0);");
        uploadButton.setPrefWidth(200); // Increased from 150 to 200 for wider form
        uploadButton.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
            File file = fileChooser.showOpenDialog(primaryStage);
            if (file != null) {
                selectedFile[0] = file;
                try {
                    Image image = new Image(file.toURI().toString());
                    profilePreview.setImage(image);
                } catch (Exception e) {
                    new Alert(AlertType.ERROR, "Failed to load image.").show();
                }
            }
        });
        uploadButton.setOnMouseEntered(event -> uploadButton.setStyle("-fx-background-color: linear-gradient(to bottom, #FFCC33, #EEAA22); -fx-text-fill: #FFFFFF; -fx-font-family: 'Arial Black'; -fx-font-size: 14; -fx-font-weight: bold; -fx-background-radius: 15; -fx-padding: 8 15; -fx-effect: dropshadow(gaussian, #000000, 8, 0.7, 0, 0);"));
        uploadButton.setOnMouseExited(event -> uploadButton.setStyle("-fx-background-color: linear-gradient(to bottom, #FFAA00, #CC8800); -fx-text-fill: #FFFFFF; -fx-font-family: 'Arial Black'; -fx-font-size: 14; -fx-font-weight: bold; -fx-background-radius: 15; -fx-padding: 8 15; -fx-effect: dropshadow(gaussian, #000000, 5, 0.5, 0, 0);"));

        Button registerButton = new Button("Register");
        registerButton.setStyle("-fx-background-color: linear-gradient(to bottom, #44FF44, #00CC00); -fx-text-fill: #FFFFFF; -fx-font-family: 'Arial Black'; -fx-font-size: 14; -fx-font-weight: bold; -fx-background-radius: 15; -fx-padding: 8 15; -fx-effect: dropshadow(gaussian, #000000, 5, 0.5, 0, 0);");
        registerButton.setPrefWidth(200); // Increased from 150 to 200 for wider form
        registerButton.setOnAction(event -> {
            String user = usernameField.getText().trim();
            String pass = passwordField.getText().trim();
            if (user.isEmpty() || pass.isEmpty()) {
                new Alert(AlertType.ERROR, "Username and password cannot be empty.").show();
                return;
            }
            // Validate password length (min 8, max 32)
            if (pass.length() < 8 || pass.length() > 32) {
                new Alert(AlertType.ERROR, "Password must be between 8 and 32 characters long.").show();
                return;
            }
            byte[] profilePicData = null;
            if (selectedFile[0] != null) {
                try {
                    profilePicData = Files.readAllBytes(selectedFile[0].toPath());
                } catch (IOException e) {
                    new Alert(AlertType.ERROR, "Failed to read profile picture.").show();
                    return;
                }
            } else {
                // Use default profile picture if none uploaded
                File defaultPic = new File("resources/images/default_profile.png");
                if (defaultPic.exists()) {
                    try {
                        profilePicData = Files.readAllBytes(defaultPic.toPath());
                    } catch (IOException e) {
                        System.err.println("Failed to read default profile picture: " + e.getMessage());
                    }
                }
            }
            if (register(user, pass, profilePicData)) {
                currentUser = user;
                // Save the new user to the session table
                try (Connection conn = getConnection()) {
                    conn.createStatement().execute("DELETE FROM session");
                    PreparedStatement pstmt = conn.prepareStatement("INSERT INTO session (id, username) VALUES (1, ?)");
                    pstmt.setString(1, user);
                    pstmt.executeUpdate();
                } catch (SQLException e) {
                    System.err.println("Error saving session: " + e.getMessage());
                }
                unlockedLevels = new HashSet<>();
                unlockedLevels.add(Integer.valueOf(1));
                isEffectsMuted = false; // Replace isMuted with isEffectsMuted
                isMusicMuted = false;   // Replace isMuted with isMusicMuted
                audioVolume = 1.0;
                saveProgress();
                showLevelSelectScreen();
            } else {
                new Alert(AlertType.ERROR, "Username already exists or registration failed.").show();
            }
        });
        registerButton.setOnMouseEntered(event -> registerButton.setStyle("-fx-background-color: linear-gradient(to bottom, #66FF66, #22EE22); -fx-text-fill: #FFFFFF; -fx-font-family: 'Arial Black'; -fx-font-size: 14; -fx-font-weight: bold; -fx-background-radius: 15; -fx-padding: 8 15; -fx-effect: dropshadow(gaussian, #000000, 8, 0.7, 0, 0);"));
        registerButton.setOnMouseExited(event -> registerButton.setStyle("-fx-background-color: linear-gradient(to bottom, #44FF44, #00CC00); -fx-text-fill: #FFFFFF; -fx-font-family: 'Arial Black'; -fx-font-size: 14; -fx-font-weight: bold; -fx-background-radius: 15; -fx-padding: 8 15; -fx-effect: dropshadow(gaussian, #000000, 5, 0.5, 0, 0);"));

        Button goToLoginButton = new Button("Go to Login");
        goToLoginButton.setStyle("-fx-background-color: linear-gradient(to bottom, #4444FF, #0000CC); -fx-text-fill: #FFFFFF; -fx-font-family: 'Arial Black'; -fx-font-size: 14; -fx-font-weight: bold; -fx-background-radius: 15; -fx-padding: 8 15; -fx-effect: dropshadow(gaussian, #000000, 5, 0.5, 0, 0);");
        goToLoginButton.setPrefWidth(200); // Increased from 150 to 200 for wider form
        goToLoginButton.setOnAction(event -> showLoginScreen());
        goToLoginButton.setOnMouseEntered(event -> goToLoginButton.setStyle("-fx-background-color: linear-gradient(to bottom, #6666FF, #2222EE); -fx-text-fill: #FFFFFF; -fx-font-family: 'Arial Black'; -fx-font-size: 14; -fx-font-weight: bold; -fx-background-radius: 15; -fx-padding: 8 15; -fx-effect: dropshadow(gaussian, #000000, 8, 0.7, 0, 0);"));
        goToLoginButton.setOnMouseExited(event -> goToLoginButton.setStyle("-fx-background-color: linear-gradient(to bottom, #4444FF, #0000CC); -fx-text-fill: #FFFFFF; -fx-font-family: 'Arial Black'; -fx-font-size: 14; -fx-font-weight: bold; -fx-background-radius: 15; -fx-padding: 8 15; -fx-effect: dropshadow(gaussian, #000000, 5, 0.5, 0, 0);"));

        registerBox.getChildren().addAll(title, usernameField, passwordField, profileStack, uploadButton, registerButton, goToLoginButton);

        // Stack the background rectangle and form
        StackPane formStack = new StackPane();
        formStack.getChildren().addAll(backgroundRect, registerBox);
        container.getChildren().add(formStack);
        root.setCenter(container);
    }

    private void showLevelSelectScreen() {
        extraMoves = 0;
        lossCount = 0;
        root.getChildren().clear();
        root.setStyle("-fx-background-color: radial-gradient(center 50% 50%, radius 80%, #1C2526, #0A0E26);");
        primaryStage.setTitle("Chroma Flood - Level Select");

        if (progressionPathAudio == null) {
            File audioFile = new File(AUDIO_FOLDER + File.separator + PROGRESSION_PATH_AUDIO_FILE);
            if (!audioFile.exists()) {
                try {
                    InputStream in = new URL(DOWNLOAD_URLS[1]).openStream();
                    Files.copy(in, Paths.get(audioFile.getPath()), StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException e) {
                    System.err.println("Failed to download progression path audio: " + e.getMessage());
                }
            }
            if (audioFile.exists()) {
                progressionPathAudio = new MediaPlayer(new Media(audioFile.toURI().toString()));
                updateAudioSettings();
            }
        }

        if (progressionPathAudio == null || progressionPathAudio.getStatus() != MediaPlayer.Status.PLAYING) {
            Platform.runLater(this::playProgressionPathAudio);
        }

        Pane levelPane = new Pane();
        // Set pane size to 90% of stage width for responsiveness
        double stageWidth = primaryStage.getWidth() > 0 ? primaryStage.getWidth() : 1200; // Fallback to 1200 if stage width is not set
        double paneWidth = stageWidth * 0.9; // Use 90% of stage width
        levelPane.setPrefSize(paneWidth, 400);
        levelPane.setStyle("-fx-background-color: transparent;");

        // Bind pane width to stage width for dynamic resizing
        primaryStage.widthProperty().addListener((obs, oldVal, newVal) -> {
            double newPaneWidth = newVal.doubleValue() * 0.9;
            levelPane.setPrefWidth(newPaneWidth);
            // Update level positions dynamically
            double newLevelSpacing = (newPaneWidth - 200) / (LEVEL_PATTERNS.length + 1); // Recalculate spacing
            double newStartX = (newPaneWidth - (LEVEL_PATTERNS.length - 1) * newLevelSpacing) / 2; // Center the path
            updateLevelPositions(levelPane, newStartX, newLevelSpacing);
        });

        int totalLevels = LEVEL_PATTERNS.length;
        double pathY = 200;
        double levelSpacing = (paneWidth - 200) / (totalLevels + 1); // Adjusted to span pane width minus margins
        double startX = (paneWidth - (totalLevels - 1) * levelSpacing) / 2; // Center the path
        double offsetY = 100;

        double level10XPos = startX + (totalLevels - 1) * levelSpacing; // Calculate x position for level 10
        Line mainPath = new Line(startX, pathY, level10XPos, pathY); // Updated startX
        mainPath.setStroke(Color.rgb(0, 255, 255, 0.5));
        mainPath.setStrokeWidth(3);
        mainPath.setEffect(new Glow(0.3));
        levelPane.getChildren().add(mainPath);

        for (int level = 1; level <= totalLevels; level++) {
            boolean isUnlocked = unlockedLevels.contains(Integer.valueOf(level));
            double xPos = startX + (level - 1) * levelSpacing; // Updated xPos with new startX
            double yPos = pathY - (level % 2 == 1 ? offsetY : -offsetY);

            if (isUnlocked) {
                double circleCenterX = xPos;
                double circleCenterY = yPos;
                Line connectionLine = new Line(circleCenterX, circleCenterY, circleCenterX, pathY);
                connectionLine.setStroke(Color.rgb(0, 255, 255, 0.5));
                connectionLine.setStrokeWidth(2);
                connectionLine.setEffect(new Glow(0.2));
                levelPane.getChildren().add(connectionLine);
            }

            VBox levelContainer = new VBox(15);
            levelContainer.setAlignment(Pos.CENTER);
            levelContainer.setLayoutX(xPos - 50);
            levelContainer.setLayoutY(yPos - 50);

            Text levelLabel = new Text("Level " + level);
            levelLabel.setStyle("-fx-font-family: 'Arial Black'; -fx-font-size: 14; -fx-fill: #FFFFFF; -fx-effect: dropshadow(gaussian, #000000, 3, 0.5, 0, 0);");

            DropShadow defaultEffect = new DropShadow(10, Color.rgb(0, 0, 0, 0.8));
            Glow hoverGlowEffect = new Glow(0.8);

            if (isUnlocked) {
                Image levelImage;
                switch (level) {
                    case 1: levelImage = level1Image; break;
                    case 2: levelImage = level2Image; break;
                    case 3: levelImage = level3Image; break;
                    case 4: levelImage = level4Image; break;
                    case 5: levelImage = level5Image; break;
                    case 6: levelImage = level6Image; break;
                    case 7: levelImage = level7Image; break;
                    case 8: levelImage = level8Image; break;
                    case 9: levelImage = level9Image; break;
                    case 10: levelImage = level10Image; break;
                    default: levelImage = null;
                }
                if (levelImage == null) {
                    levelImage = new Image("file:resources/images/default_profile.png");
                }
                final ImageView levelImageView = new ImageView(levelImage);
                levelImageView.setFitWidth(100);
                levelImageView.setFitHeight(100);
                Circle clipCircle = new Circle(50, 50, 50);
                levelImageView.setClip(clipCircle);
                levelImageView.setEffect(defaultEffect);
                levelContainer.getChildren().add(levelImageView);

                final int selectedLevel = level;
                levelContainer.setOnMouseClicked(event -> {
                    currentLevel.set(selectedLevel);
                    setupGameplayScreen();
                });
                levelContainer.setOnMouseEntered(event -> levelImageView.setEffect(hoverGlowEffect));
                levelContainer.setOnMouseExited(event -> levelImageView.setEffect(defaultEffect));

                if (level == 2 || level == 4) {
                    levelContainer.getChildren().add(levelLabel);
                } else if (level % 2 == 1) {
                    levelContainer.getChildren().add(0, levelLabel);
                } else {
                    levelContainer.getChildren().add(levelLabel);
                }
            } else {
                Circle levelCircle = new Circle(50);
                levelCircle.setFill(Color.rgb(100, 100, 100, 0.5));
                levelCircle.setEffect(defaultEffect);
                levelContainer.getChildren().add(levelCircle);
                if (level % 2 == 1) {
                    levelContainer.getChildren().add(0, levelLabel);
                } else {
                    levelContainer.getChildren().add(levelLabel);
                }
                levelContainer.setOpacity(0.7);
                levelContainer.setOnMouseEntered(event -> levelCircle.setEffect(hoverGlowEffect));
                levelContainer.setOnMouseExited(event -> levelCircle.setEffect(defaultEffect));
            }

            levelPane.getChildren().add(levelContainer);
        }

        VBox centerWrapper = new VBox(levelPane);
        centerWrapper.setAlignment(Pos.CENTER);

        HBox topBox = new HBox(20);
        topBox.setAlignment(Pos.TOP_RIGHT);
        topBox.setPadding(new Insets(10));

        HBox userBox = new HBox(10);
        userBox.setAlignment(Pos.CENTER_LEFT);
        userBox.setStyle("-fx-cursor: hand;");
        userBox.setOnMouseClicked(event -> showProfileSettingsDialog());

        ImageView profilePicView = new ImageView();
        profilePicView.setFitWidth(40);
        profilePicView.setFitHeight(40);
        Circle clipCircle = new Circle(20, 20, 20);
        profilePicView.setClip(clipCircle);
        profilePicView.setEffect(new DropShadow(5, Color.rgb(0, 0, 0, 0.8)));
        try (Connection conn = getConnection()) {
            PreparedStatement pstmt = conn.prepareStatement("SELECT profilePicture FROM users WHERE username = ?");
            pstmt.setString(1, currentUser);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                byte[] picData = rs.getBytes("profilePicture");
                if (picData != null && picData.length > 0) {
                    profilePicView.setImage(new Image(new ByteArrayInputStream(picData)));
                } else {
                    File defaultProfilePic = new File("resources/images/default_profile.png");
                    if (defaultProfilePic.exists()) {
                        profilePicView.setImage(new Image(defaultProfilePic.toURI().toString()));
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error loading profile picture: " + e.getMessage());
            File defaultProfilePic = new File("resources/images/default_profile.png");
            if (defaultProfilePic.exists()) {
                profilePicView.setImage(new Image(defaultProfilePic.toURI().toString()));
            }
        }

        Label usernameLabel = new Label(currentUser);
        usernameLabel.setStyle("-fx-text-fill: #FFFFFF; -fx-font-family: 'Arial Black'; -fx-font-size: 14; -fx-font-weight: bold; -fx-effect: dropshadow(gaussian, #000000, 5, 0.5, 0, 0);");

        userBox.getChildren().addAll(profilePicView, usernameLabel);

        Button settingsButton = new Button("SETTINGS");
        settingsButton.setStyle("-fx-background-color: linear-gradient(to bottom, #44FF44, #00CC00); -fx-text-fill: #FFFFFF; -fx-font-family: 'Arial Black'; -fx-font-size: 14; -fx-font-weight: bold; -fx-background-radius: 15; -fx-padding: 10 20; -fx-effect: dropshadow(gaussian, #000000, 5, 0.5, 0, 0);");
        settingsButton.setOnAction(event -> showSettingsDialog());
        settingsButton.setOnMouseEntered(event -> settingsButton.setStyle("-fx-background-color: linear-gradient(to bottom, #66FF66, #22EE22); -fx-text-fill: #FFFFFF; -fx-font-family: 'Arial Black'; -fx-font-size: 14; -fx-font-weight: bold; -fx-background-radius: 15; -fx-padding: 10 20; -fx-effect: dropshadow(gaussian, #000000, 8, 0.7, 0, 0);"));
        settingsButton.setOnMouseExited(event -> settingsButton.setStyle("-fx-background-color: linear-gradient(to bottom, #44FF44, #00CC00); -fx-text-fill: #FFFFFF; -fx-font-family: 'Arial Black'; -fx-font-size: 14; -fx-font-weight: bold; -fx-background-radius: 15; -fx-padding: 10 20; -fx-effect: dropshadow(gaussian, #000000, 5, 0.5, 0, 0);"));

        Button leaderboardButton = new Button("LEADERBOARD");
        leaderboardButton.setStyle("-fx-background-color: linear-gradient(to bottom, #FFAA00, #CC8800); -fx-text-fill: #FFFFFF; -fx-font-family: 'Arial Black'; -fx-font-size: 14; -fx-font-weight: bold; -fx-background-radius: 15; -fx-padding: 10 20; -fx-effect: dropshadow(gaussian, #000000, 5, 0.5, 0, 0);");
        leaderboardButton.setOnAction(event -> showLeaderboardDialog());
        leaderboardButton.setOnMouseEntered(event -> leaderboardButton.setStyle("-fx-background-color: linear-gradient(to bottom, #FFCC33, #EEAA22); -fx-text-fill: #FFFFFF; -fx-font-family: 'Arial Black'; -fx-font-size: 14; -fx-font-weight: bold; -fx-background-radius: 15; -fx-padding: 10 20; -fx-effect: dropshadow(gaussian, #000000, 8, 0.7, 0, 0);"));
        leaderboardButton.setOnMouseExited(event -> leaderboardButton.setStyle("-fx-background-color: linear-gradient(to bottom, #FFAA00, #CC8800); -fx-text-fill: #FFFFFF; -fx-font-family: 'Arial Black'; -fx-font-size: 14; -fx-font-weight: bold; -fx-background-radius: 15; -fx-padding: 10 20; -fx-effect: dropshadow(gaussian, #000000, 5, 0.5, 0, 0);"));

        Button logoutButton = new Button("LOGOUT");
        logoutButton.setStyle("-fx-background-color: linear-gradient(to bottom, #FF4444, #CC0000); -fx-text-fill: #FFFFFF; -fx-font-family: 'Arial Black'; -fx-font-size: 14; -fx-font-weight: bold; -fx-background-radius: 15; -fx-padding: 10 20; -fx-effect: dropshadow(gaussian, #000000, 5, 0.5, 0, 0);");
        logoutButton.setOnAction(event -> {
            saveProgress();
            currentUser = null;
            try (Connection conn = getConnection()) {
                conn.createStatement().execute("DELETE FROM session");
            } catch (SQLException e) {
                System.err.println("Error clearing session: " + e.getMessage());
            }
            stopCurrentBackgroundAudio();
            showLoginScreen();
        });
        logoutButton.setOnMouseEntered(event -> logoutButton.setStyle("-fx-background-color: linear-gradient(to bottom, #FF6666, #EE2222); -fx-text-fill: #FFFFFF; -fx-font-family: 'Arial Black'; -fx-font-size: 14; -fx-font-weight: bold; -fx-background-radius: 15; -fx-padding: 10 20; -fx-effect: dropshadow(gaussian, #000000, 8, 0.7, 0, 0);"));
        logoutButton.setOnMouseExited(event -> logoutButton.setStyle("-fx-background-color: linear-gradient(to bottom, #FF4444, #CC0000); -fx-text-fill: #FFFFFF; -fx-font-family: 'Arial Black'; -fx-font-size: 14; -fx-font-weight: bold; -fx-background-radius: 15; -fx-padding: 10 20; -fx-effect: dropshadow(gaussian, #000000, 5, 0.5, 0, 0);"));

        topBox.getChildren().addAll(userBox, settingsButton, leaderboardButton, logoutButton);
        root.setTop(topBox);
        root.setCenter(centerWrapper);
    }

    private void updateLevelPositions(Pane levelPane, double startX, double levelSpacing) {
        levelPane.getChildren().clear(); // Clear existing children
        int totalLevels = LEVEL_PATTERNS.length;
        double pathY = 200;
        double offsetY = 100;

        double level10XPos = startX + (totalLevels - 1) * levelSpacing;
        Line mainPath = new Line(startX, pathY, level10XPos, pathY);
        mainPath.setStroke(Color.rgb(0, 255, 255, 0.5));
        mainPath.setStrokeWidth(3);
        mainPath.setEffect(new Glow(0.3));
        levelPane.getChildren().add(mainPath);

        for (int level = 1; level <= totalLevels; level++) {
            boolean isUnlocked = unlockedLevels.contains(Integer.valueOf(level));
            double xPos = startX + (level - 1) * levelSpacing;
            double yPos = pathY - (level % 2 == 1 ? offsetY : -offsetY);

            if (isUnlocked) {
                double circleCenterX = xPos;
                double circleCenterY = yPos;
                Line connectionLine = new Line(circleCenterX, circleCenterY, circleCenterX, pathY);
                connectionLine.setStroke(Color.rgb(0, 255, 255, 0.5));
                connectionLine.setStrokeWidth(2);
                connectionLine.setEffect(new Glow(0.2));
                levelPane.getChildren().add(connectionLine);
            }

            VBox levelContainer = new VBox(15);
            levelContainer.setAlignment(Pos.CENTER);
            levelContainer.setLayoutX(xPos - 50);
            levelContainer.setLayoutY(yPos - 50);

            Text levelLabel = new Text("Level " + level);
            levelLabel.setStyle("-fx-font-family: 'Arial Black'; -fx-font-size: 14; -fx-fill: #FFFFFF; -fx-effect: dropshadow(gaussian, #000000, 3, 0.5, 0, 0);");

            DropShadow defaultEffect = new DropShadow(10, Color.rgb(0, 0, 0, 0.8));
            Glow hoverGlowEffect = new Glow(0.8);

            if (isUnlocked) {
                Image levelImage;
                switch (level) {
                    case 1: levelImage = level1Image; break;
                    case 2: levelImage = level2Image; break;
                    case 3: levelImage = level3Image; break;
                    case 4: levelImage = level4Image; break;
                    case 5: levelImage = level5Image; break;
                    case 6: levelImage = level6Image; break;
                    case 7: levelImage = level7Image; break;
                    case 8: levelImage = level8Image; break;
                    case 9: levelImage = level9Image; break;
                    case 10: levelImage = level10Image; break;
                    default: levelImage = null;
                }
                if (levelImage == null) {
                    levelImage = new Image("file:resources/images/default_profile.png");
                }
                final ImageView levelImageView = new ImageView(levelImage);
                levelImageView.setFitWidth(100);
                levelImageView.setFitHeight(100);
                Circle clipCircle = new Circle(50, 50, 50);
                levelImageView.setClip(clipCircle);
                levelImageView.setEffect(defaultEffect);
                levelContainer.getChildren().add(levelImageView);

                final int selectedLevel = level;
                levelContainer.setOnMouseClicked(event -> {
                    currentLevel.set(selectedLevel);
                    setupGameplayScreen();
                });
                levelContainer.setOnMouseEntered(event -> levelImageView.setEffect(hoverGlowEffect));
                levelContainer.setOnMouseExited(event -> levelImageView.setEffect(defaultEffect));

                if (level == 2 || level == 4) {
                    levelContainer.getChildren().add(levelLabel);
                } else if (level % 2 == 1) {
                    levelContainer.getChildren().add(0, levelLabel);
                } else {
                    levelContainer.getChildren().add(levelLabel);
                }
            } else {
                Circle levelCircle = new Circle(50);
                levelCircle.setFill(Color.rgb(100, 100, 100, 0.5));
                levelCircle.setEffect(defaultEffect);
                levelContainer.getChildren().add(levelCircle);
                if (level % 2 == 1) {
                    levelContainer.getChildren().add(0, levelLabel);
                } else {
                    levelContainer.getChildren().add(levelLabel);
                }
                levelContainer.setOpacity(0.7);
                levelContainer.setOnMouseEntered(event -> levelCircle.setEffect(hoverGlowEffect));
                levelContainer.setOnMouseExited(event -> levelCircle.setEffect(defaultEffect));
            }

            levelPane.getChildren().add(levelContainer);
        }
    }

    private void showLoadingScreen() {
        root.getChildren().clear();
        root.setStyle("-fx-background-color: radial-gradient(center 50% 50%, radius 80%, #1C2526, #0A0E26);");
        primaryStage.setTitle("Chroma Flood - Loading");

        VBox loadingBox = new VBox(20);
        loadingBox.setAlignment(Pos.CENTER);
        loadingBox.setPadding(new Insets(20));

        Label loadingLabel = new Label("Loading...");
        loadingLabel.setStyle("-fx-text-fill: #FFFFFF; -fx-font-family: 'Arial Black'; -fx-font-size: 24; -fx-font-weight: bold; -fx-effect: dropshadow(gaussian, #000000, 5, 0.5, 0, 0);");

        ProgressIndicator progressIndicator = new ProgressIndicator();
        progressIndicator.setStyle("-fx-progress-color: #00FFFF; -fx-accent: #00FFFF;");
        progressIndicator.setPrefSize(60, 60); // Adjust size for visibility

        // Add rotation animation to ensure the spinner moves
        RotateTransition rotateTransition = new RotateTransition(Duration.seconds(2), progressIndicator);
        rotateTransition.setByAngle(360); // Full 360-degree rotation
        rotateTransition.setCycleCount(Animation.INDEFINITE); // Repeat indefinitely
        rotateTransition.setAutoReverse(false); // Continuous rotation in one direction
        rotateTransition.play(); // Start the animation

        loadingBox.getChildren().addAll(loadingLabel, progressIndicator);
        root.setCenter(loadingBox);
    }

    private void showProfileSettingsDialog() {
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(primaryStage);
        dialog.setTitle("Profile Settings");

        dialog.setFullScreen(false);
        dialog.setMaximized(false);
        dialog.setResizable(false);

        VBox dialogVBox = new VBox(20);
        dialogVBox.setAlignment(Pos.CENTER);
        dialogVBox.setPadding(new Insets(20));
        dialogVBox.setStyle("-fx-background-color: linear-gradient(to bottom, #333333, #111111);");

        Label titleLabel = new Label("Profile Settings");
        titleLabel.setStyle("-fx-text-fill: #FFFFFF; -fx-font-family: 'Arial Black'; -fx-font-size: 18; -fx-font-weight: bold; -fx-effect: dropshadow(gaussian, #000000, 5, 0.5, 0, 0);");

        TextField usernameField = new TextField(currentUser);
        usernameField.setPromptText("Username");
        usernameField.setStyle("-fx-background-color: #FFFFFF; -fx-text-fill: #000000; -fx-font-size: 14; -fx-padding: 8; -fx-background-radius: 10;");
        usernameField.setPrefWidth(150);
        usernameField.setMaxWidth(150);

        ImageView profilePreview = new ImageView();
        profilePreview.setFitWidth(100);
        profilePreview.setFitHeight(100);
        Circle clipCircle = new Circle(50, 50, 50);
        profilePreview.setClip(clipCircle);
        profilePreview.setEffect(new DropShadow(10, Color.rgb(0, 0, 0, 0.8)));

        usernameField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("^[a-zA-Z0-9_]*$")) {
                usernameField.setText(oldValue);
            } else if (newValue.length() > 20) {
                usernameField.setText(oldValue);
            }
        });

        // Load profile picture
        byte[] tempProfilePicData = null;
        Connection conn = null;
        try {
            conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement("SELECT profilePicture FROM users WHERE username = ?");
            pstmt.setString(1, currentUser);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                tempProfilePicData = rs.getBytes("profilePicture");
                if (tempProfilePicData != null && tempProfilePicData.length > 0) {
                    profilePreview.setImage(new Image(new ByteArrayInputStream(tempProfilePicData)));
                }
            }
            if (tempProfilePicData == null || tempProfilePicData.length == 0) {
                File defaultProfilePic = new File("resources/images/default_profile.png");
                if (defaultProfilePic.exists()) {
                    profilePreview.setImage(new Image(defaultProfilePic.toURI().toString()));
                    tempProfilePicData = Files.readAllBytes(defaultProfilePic.toPath());
                }
            }
        } catch (SQLException | IOException e) {
            System.err.println("Error loading profile picture: " + e.getMessage());
            File defaultProfilePic = new File("resources/images/default_profile.png");
            if (defaultProfilePic.exists()) {
                try {
                    profilePreview.setImage(new Image(defaultProfilePic.toURI().toString()));
                    tempProfilePicData = Files.readAllBytes(defaultProfilePic.toPath());
                } catch (IOException ex) {
                    System.err.println("Failed to read default profile picture: " + ex.getMessage());
                    tempProfilePicData = null;
                }
            } else {
                tempProfilePicData = null;
            }
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    System.err.println("Error closing connection: " + e.getMessage());
                }
            }
        }
        final byte[] initialProfilePicData = tempProfilePicData;

        final File[] selectedFile = {null};
        Button uploadButton = new Button("Upload Profile Picture");
        uploadButton.setStyle("-fx-background-color: linear-gradient(to bottom, #FFAA00, #CC8800); -fx-text-fill: #FFFFFF; -fx-font-family: 'Arial Black'; -fx-font-size: 14; -fx-font-weight: bold; -fx-background-radius: 15; -fx-padding: 8 15; -fx-effect: dropshadow(gaussian, #000000, 5, 0.5, 0, 0);");
        uploadButton.setPrefWidth(200);
        uploadButton.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
            File file = fileChooser.showOpenDialog(primaryStage);
            if (file != null) {
                selectedFile[0] = file;
                try {
                    Image image = new Image(file.toURI().toString());
                    profilePreview.setImage(image);
                } catch (Exception e) {
                    new Alert(AlertType.ERROR, "Failed to load image.").show();
                }
            }
        });
        uploadButton.setOnMouseEntered(event -> uploadButton.setStyle("-fx-background-color: linear-gradient(to bottom, #FFCC33, #EEAA22); -fx-text-fill: #FFFFFF; -fx-font-family: 'Arial Black'; -fx-font-size: 14; -fx-font-weight: bold; -fx-background-radius: 15; -fx-padding: 8 15; -fx-effect: dropshadow(gaussian, #000000, 8, 0.7, 0, 0);"));
        uploadButton.setOnMouseExited(event -> uploadButton.setStyle("-fx-background-color: linear-gradient(to bottom, #FFAA00, #CC8800); -fx-text-fill: #FFFFFF; -fx-font-family: 'Arial Black'; -fx-font-size: 14; -fx-font-weight: bold; -fx-background-radius: 15; -fx-padding: 8 15; -fx-effect: dropshadow(gaussian, #000000, 5, 0.5, 0, 0);"));

        Button saveButton = new Button("Save");
        saveButton.setStyle("-fx-background-color: linear-gradient(to bottom, #44FF44, #00CC00); -fx-text-fill: #FFFFFF; -fx-font-family: 'Arial Black'; -fx-font-size: 14; -fx-font-weight: bold; -fx-background-radius: 15; -fx-padding: 8 15; -fx-effect: dropshadow(gaussian, #000000, 5, 0.5, 0, 0);");
        saveButton.setPrefWidth(150);
        saveButton.setOnAction(event -> {
            final String newUsername = usernameField.getText().trim();
            if (newUsername.isEmpty()) {
                new Alert(AlertType.ERROR, "Username cannot be empty.").show();
                return;
            }
            final byte[] finalProfilePicData;
            if (selectedFile[0] != null) {
                try {
                    finalProfilePicData = Files.readAllBytes(selectedFile[0].toPath());
                } catch (IOException e) {
                    new Alert(AlertType.ERROR, "Failed to read profile picture.").show();
                    return;
                }
            } else {
                finalProfilePicData = initialProfilePicData;
            }
            final String oldUsername = currentUser;

            Task<Void> updateTask = new Task<>() {
                @Override
                protected Void call() throws Exception {
                    int retries = 3;
                    Connection taskConn = null;
                    try {
                        taskConn = getConnection();
                        taskConn.setAutoCommit(false);
                        taskConn.createStatement().execute("PRAGMA foreign_keys = ON;");
                        if (!newUsername.equals(oldUsername)) {
                            PreparedStatement checkStmt = taskConn.prepareStatement("SELECT username FROM users WHERE username = ?");
                            checkStmt.setString(1, newUsername);
                            ResultSet rs = checkStmt.executeQuery();
                            if (rs.next()) {
                                taskConn.rollback();
                                Platform.runLater(() -> new Alert(AlertType.ERROR, "Username already exists.").show());
                                return null;
                            }
                        }
                        long startTime = System.nanoTime();
                        System.out.println("Updating users table: " + oldUsername + " to " + newUsername);
                        PreparedStatement userStmt = taskConn.prepareStatement(
                                "UPDATE users SET username = ?, profilePicture = ? WHERE username = ?");
                        userStmt.setString(1, newUsername);
                        if (finalProfilePicData != null) {
                            userStmt.setBytes(2, finalProfilePicData);
                        } else {
                            userStmt.setNull(2, java.sql.Types.BLOB);
                        }
                        userStmt.setString(3, oldUsername);
                        int updated = userStmt.executeUpdate();
                        System.out.println("Users rows updated: " + updated);
                        System.out.println("Update took: " + (System.nanoTime() - startTime) / 1_000_000.0 + " ms");
                        if (updated == 0) {
                            taskConn.rollback();
                            Platform.runLater(() -> new Alert(AlertType.ERROR, "User not found for update.").show());
                            return null;
                        }
                        taskConn.commit();
                        System.out.println("Transaction committed successfully");
                        Platform.runLater(() -> {
                            currentUser = newUsername;
                            saveProgress();
                            dialog.close();
                            showLevelSelectScreen();
                        });
                        return null;
                    } catch (SQLException e) {
                        if (e.getMessage().contains("database is locked") && retries > 1) {
                            retries--;
                            System.out.println("Database locked, retrying (" + retries + " attempts left)...");
                            Thread.sleep(50);
                        } else {
                            System.err.println("Error updating profile: " + e.getMessage());
                            try {
                                taskConn.rollback();
                            } catch (SQLException ex) {
                                System.err.println("Error during rollback: " + ex.getMessage());
                            }
                            Platform.runLater(() -> new Alert(AlertType.ERROR, "Update failed: " + e.getMessage()).show());
                        }
                        return null;
                    } finally {
                        if (taskConn != null) {
                            try {
                                taskConn.close();
                            } catch (SQLException e) {
                                System.err.println("Error closing connection: " + e.getMessage());
                            }
                        }
                    }
                }
            };
            new Thread(updateTask).start();
        });
        saveButton.setOnMouseEntered(event -> saveButton.setStyle("-fx-background-color: linear-gradient(to bottom, #66FF66, #22EE22); -fx-text-fill: #FFFFFF; -fx-font-family: 'Arial Black'; -fx-font-size: 14; -fx-font-weight: bold; -fx-background-radius: 15; -fx-padding: 8 15; -fx-effect: dropshadow(gaussian, #000000, 8, 0.7, 0, 0);"));
        saveButton.setOnMouseExited(event -> saveButton.setStyle("-fx-background-color: linear-gradient(to bottom, #44FF44, #00CC00); -fx-text-fill: #FFFFFF; -fx-font-family: 'Arial Black'; -fx-font-size: 14; -fx-font-weight: bold; -fx-background-radius: 15; -fx-padding: 8 15; -fx-effect: dropshadow(gaussian, #000000, 5, 0.5, 0, 0);"));

        Button closeButton = new Button("Close");
        closeButton.setStyle("-fx-background-color: linear-gradient(to bottom, #FF4444, #CC0000); -fx-text-fill: #FFFFFF; -fx-font-family: 'Arial Black'; -fx-font-size: 14; -fx-font-weight: bold; -fx-background-radius: 15; -fx-padding: 8 15; -fx-effect: dropshadow(gaussian, #000000, 5, 0.5, 0, 0);");
        closeButton.setPrefWidth(150);
        closeButton.setOnAction(event -> dialog.close());
        closeButton.setOnMouseEntered(event -> closeButton.setStyle("-fx-background-color: linear-gradient(to bottom, #FF6666, #EE2222); -fx-text-fill: #FFFFFF; -fx-font-family: 'Arial Black'; -fx-font-size: 14; -fx-font-weight: bold; -fx-background-radius: 15; -fx-padding: 8 15; -fx-effect: dropshadow(gaussian, #000000, 8, 0.7, 0, 0);"));
        closeButton.setOnMouseExited(event -> closeButton.setStyle("-fx-background-color: linear-gradient(to bottom, #FF4444, #CC0000); -fx-text-fill: #FFFFFF; -fx-font-family: 'Arial Black'; -fx-font-size: 14; -fx-font-weight: bold; -fx-background-radius: 15; -fx-padding: 8 15; -fx-effect: dropshadow(gaussian, #000000, 5, 0.5, 0, 0);"));

        dialogVBox.getChildren().addAll(titleLabel, usernameField, profilePreview, uploadButton, saveButton, closeButton);
        Scene dialogScene = new Scene(dialogVBox, 400, 400);
        dialog.setScene(dialogScene);
        dialog.show();
    }

    private Connection getConnection() throws SQLException {
        int retries = 3;
        while (retries > 0) {
            try {
                System.out.println("Checking for SQLite JDBC driver...");
                Class.forName("org.sqlite.JDBC");
                System.out.println("SQLite JDBC driver loaded successfully: org.sqlite.JDBC");
                String dbUrl = "jdbc:sqlite:" + System.getProperty("user.home") + "/game.db";
                System.out.println("Connecting to database: " + dbUrl);
                Connection conn = DriverManager.getConnection(dbUrl);
                // Enable foreign key enforcement
                Statement stmt = conn.createStatement();
                stmt.execute("PRAGMA foreign_keys = ON;");
                stmt.close();
                System.out.println("Database connection established successfully: " + dbUrl);
                return conn;
            } catch (ClassNotFoundException e) {
                String libPath = "C:\\Users\\Xaruzo\\IdeaProjects\\untitled3\\lib\\sqlite-jdbc-3.46.1.jar";
                System.err.println("SQLite JDBC driver not found: " + e.getMessage());
                System.err.println("Current classpath: " + System.getProperty("java.class.path"));
                System.err.println("Expected SQLite JAR at: " + libPath);
                System.err.println("To fix: In IntelliJ IDEA: 1) Go to File > Project Structure > Libraries, click '+', select 'Java', choose " + libPath + ", and click OK. 2) Go to File > Project Structure > Modules > untitled3 > Dependencies, click '+', select 'Library', choose the SQLite JAR, and click OK. 3) Verify in Run > Edit Configurations > ChromaFloodSystem that the JAR is in the Classpath.");
                throw new SQLException("Failed to load SQLite JDBC driver. Add sqlite-jdbc-3.46.1.jar to the project classpath in IntelliJ IDEA.", e);
            } catch (SQLException e) {
                if (e.getMessage().contains("database is locked") && retries > 1) {
                    retries--;
                    try {
                        Thread.sleep(100); // Wait before retrying
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                    }
                } else {
                    System.err.println("Database connection error: " + e.getMessage());
                    throw new SQLException("Failed to connect to database: " + e.getMessage(), e);
                }
            }
        }
        throw new SQLException("Failed to connect to database after retries: Database is locked");
    }

    private boolean authenticate(String username, String password) {
        int retries = 3;
        while (retries > 0) {
            try (Connection conn = getConnection()) {
                PreparedStatement pstmt = conn.prepareStatement("SELECT password FROM users WHERE username = ?");
                pstmt.setString(1, username);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    String storedPassword = rs.getString("password");
                    System.out.println("Authentication attempt: username=" + username + ", provided password=" + password + ", stored password=" + storedPassword + ", isValid=" + storedPassword.equals(password));
                    return storedPassword.equals(password);
                } else {
                    System.out.println("Authentication failed: No user found for username=" + username);
                    return false;
                }
            } catch (SQLException e) {
                if (e.getMessage().contains("database is locked") && retries > 1) {
                    retries--;
                    try {
                        Thread.sleep(100); // Wait before retrying
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                    }
                } else {
                    System.err.println("Error during authentication: " + e.getMessage());
                    return false;
                }
            }
        }
        return false;
    }

    private boolean register(String username, String password, byte[] profilePicture) {
        System.out.println("Initiating registration for username: " + username + ", profile picture size: " + (profilePicture != null ? profilePicture.length + " bytes" : "none"));
        try (Connection conn = getConnection()) {
            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO users (username, password, profilePicture) VALUES (?, ?, ?)");
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            if (profilePicture != null) {
                pstmt.setBytes(3, profilePicture);
            } else {
                pstmt.setNull(3, java.sql.Types.BLOB);
            }
            pstmt.executeUpdate();
            System.out.println("Registration completed successfully for username: " + username);
            return true;
        } catch (SQLException e) {
            if (e.getMessage().contains("SQLITE_CONSTRAINT") && e.getMessage().contains("PRIMARY KEY")) {
                System.err.println("Registration failed: Username already exists: " + username);
                new Alert(AlertType.ERROR, "Username already exists.").show();
            } else {
                System.err.println("Registration error: " + e.getMessage());
                new Alert(AlertType.ERROR, "Failed to connect to database. Please ensure sqlite-jdbc-3.46.1.jar is added to the project classpath.").show();
            }
            return false;
        }
    }

    private void downloadAudioResources() {
        File folder = new File(AUDIO_FOLDER);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        boolean allDownloaded = true;
        for (int i = 0; i < AUDIO_FILES.length; i++) {
            File audioFile = new File(AUDIO_FOLDER + File.separator + AUDIO_FILES[i]);
            if (!audioFile.exists()) {
                try {
                    InputStream in = new URL(DOWNLOAD_URLS[i]).openStream();
                    Files.copy(in, Paths.get(audioFile.getPath()), StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException e) {
                    System.err.println("Failed to download audio file: " + AUDIO_FILES[i] + " - " + e.getMessage());
                    allDownloaded = false;
                    // No alert here - handled centrally
                }
            }
        }
        clickSound = new AudioClip(new File(AUDIO_FOLDER + File.separator + CLICK_AUDIO_FILE).toURI().toString());
        progressionPathAudio = new MediaPlayer(new Media(new File(AUDIO_FOLDER + File.separator + PROGRESSION_PATH_AUDIO_FILE).toURI().toString()));
        level1Audio = new MediaPlayer(new Media(new File(AUDIO_FOLDER + File.separator + LEVEL1_AUDIO_FILE).toURI().toString()));
        level2Audio = new MediaPlayer(new Media(new File(AUDIO_FOLDER + File.separator + LEVEL2_AUDIO_FILE).toURI().toString()));
        level3Audio = new MediaPlayer(new Media(new File(AUDIO_FOLDER + File.separator + LEVEL3_AUDIO_FILE).toURI().toString()));
        level4Audio = new MediaPlayer(new Media(new File(AUDIO_FOLDER + File.separator + LEVEL4_AUDIO_FILE).toURI().toString()));
        level5Audio = new MediaPlayer(new Media(new File(AUDIO_FOLDER + File.separator + LEVEL5_AUDIO_FILE).toURI().toString()));
        level6Audio = new MediaPlayer(new Media(new File(AUDIO_FOLDER + File.separator + LEVEL6_AUDIO_FILE).toURI().toString()));
        level7Audio = new MediaPlayer(new Media(new File(AUDIO_FOLDER + File.separator + LEVEL7_AUDIO_FILE).toURI().toString()));
        level8Audio = new MediaPlayer(new Media(new File(AUDIO_FOLDER + File.separator + LEVEL8_AUDIO_FILE).toURI().toString()));
        level9Audio = new MediaPlayer(new Media(new File(AUDIO_FOLDER + File.separator + LEVEL9_AUDIO_FILE).toURI().toString()));
        level10Audio = new MediaPlayer(new Media(new File(AUDIO_FOLDER + File.separator + LEVEL10_AUDIO_FILE).toURI().toString()));
    }

    private void playProgressionPathAudio() {
        stopCurrentBackgroundAudio();
        currentBackgroundAudio = progressionPathAudio;
        if (currentBackgroundAudio != null) {
            currentBackgroundAudio.setVolume(isMusicMuted ? 0.0 : audioVolume); // Replace isMuted with isMusicMuted
            currentBackgroundAudio.setCycleCount(MediaPlayer.INDEFINITE);
            currentBackgroundAudio.play();
        }
    }

    private void playLevelAudio(int level) {
        stopCurrentBackgroundAudio();
        switch (level) {
            case 1:
                currentBackgroundAudio = level1Audio;
                break;
            case 2:
                currentBackgroundAudio = level2Audio;
                break;
            case 3:
                currentBackgroundAudio = level3Audio;
                break;
            case 4:
                currentBackgroundAudio = level4Audio;
                break;
            case 5:
                currentBackgroundAudio = level5Audio;
                break;
            case 6:
                currentBackgroundAudio = level6Audio;
                break;
            case 7:
                currentBackgroundAudio = level7Audio;
                break;
            case 8:
                currentBackgroundAudio = level8Audio;
                break;
            case 9:
                currentBackgroundAudio = level9Audio;
                break;
            case 10:
                currentBackgroundAudio = level10Audio;
                break;
            default:
                currentBackgroundAudio = null;
                return;
        }
        if (currentBackgroundAudio != null) {
            currentBackgroundAudio.setVolume(isMusicMuted ? 0.0 : audioVolume); // Replace isMuted with isMusicMuted
            currentBackgroundAudio.setCycleCount(MediaPlayer.INDEFINITE);
            currentBackgroundAudio.play();
        }
    }

    private void stopCurrentBackgroundAudio() {
        if (currentBackgroundAudio != null && currentBackgroundAudio.getStatus() == MediaPlayer.Status.PLAYING) {
            currentBackgroundAudio.stop();
        }
    }

    private void showLeaderboardDialog() {
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(primaryStage);
        dialog.setTitle("Leaderboard");
        dialog.setFullScreen(false);
        dialog.setResizable(false);

        BorderPane dialogLayout = new BorderPane();
        dialogLayout.setPadding(new Insets(25));
        dialogLayout.setStyle("-fx-background-color: linear-gradient(to bottom, #2C2C2C, #1A1A1A);");

        // Close button in top-right corner
        Button closeButton = new Button("Close");
        closeButton.setStyle("-fx-background-color: #FF4444; -fx-text-fill: #FFFFFF; -fx-font-family: 'Arial'; -fx-font-size: 14; -fx-font-weight: bold; -fx-background-radius: 10; -fx-padding: 5 15;");
        closeButton.setOnAction(event -> dialog.close());
        closeButton.setOnMouseEntered(event -> closeButton.setStyle("-fx-background-color: #FF6666; -fx-text-fill: #FFFFFF; -fx-font-family: 'Arial'; -fx-font-size: 14; -fx-font-weight: bold; -fx-background-radius: 10; -fx-padding: 5 15;"));
        closeButton.setOnMouseExited(event -> closeButton.setStyle("-fx-background-color: #FF4444; -fx-text-fill: #FFFFFF; -fx-font-family: 'Arial'; -fx-font-size: 14; -fx-font-weight: bold; -fx-background-radius: 10; -fx-padding: 5 15;"));
        HBox topBar = new HBox(closeButton);
        topBar.setAlignment(Pos.TOP_RIGHT);
        dialogLayout.setTop(topBar);

        // Title
        Label titleLabel = new Label("Leaderboard");
        titleLabel.setStyle("-fx-text-fill: #FFFFFF; -fx-font-family: 'Arial Black'; -fx-font-size: 24; -fx-font-weight: bold; -fx-effect: dropshadow(gaussian, #000000, 5, 0.5, 0, 0);");
        VBox titleBox = new VBox(titleLabel);
        titleBox.setAlignment(Pos.CENTER);
        titleBox.setPadding(new Insets(0, 0, 10, 0));

        // Leaderboard container with clipping and no border
        Pane leaderboardContainer = new Pane();
        leaderboardContainer.setStyle("-fx-background-color: #3A3A3A; -fx-background-radius: 15; -fx-effect: dropshadow(gaussian, #000000, 10, 0.5, 0, 0);");
        leaderboardContainer.setPadding(new Insets(10));
        Rectangle clip = new Rectangle();
        clip.setArcWidth(30);
        clip.setArcHeight(30);
        leaderboardContainer.setClip(clip);
        leaderboardContainer.layoutBoundsProperty().addListener((obs, oldVal, newVal) -> {
            clip.setWidth(newVal.getWidth());
            clip.setHeight(newVal.getHeight());
            clip.setX(0);
            clip.setY(0);
        });

        GridPane leaderboardGrid = new GridPane();
        leaderboardGrid.setHgap(15);
        leaderboardGrid.setVgap(10);
        leaderboardGrid.setPadding(new Insets(10));

        // Set column widths to fill container and prevent truncation
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setMinWidth(60); // Rank
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setMinWidth(200); // Player, increased to fill space
        ColumnConstraints col3 = new ColumnConstraints();
        col3.setMinWidth(150); // Highest Level, increased to fill space
        ColumnConstraints col4 = new ColumnConstraints();
        col4.setMinWidth(120); // Total Time (s)
        leaderboardGrid.getColumnConstraints().addAll(col1, col2, col3, col4);

        // Add headers
        Label rankHeader = new Label("Rank");
        rankHeader.setStyle("-fx-text-fill: #00FFFF; -fx-font-family: 'Arial Black'; -fx-font-size: 14; -fx-font-weight: bold;");
        Label usernameHeader = new Label("Player");
        usernameHeader.setStyle("-fx-text-fill: #00FFFF; -fx-font-family: 'Arial Black'; -fx-font-size: 14; -fx-font-weight: bold;");
        Label levelsHeader = new Label("Highest Level");
        levelsHeader.setStyle("-fx-text-fill: #00FFFF; -fx-font-family: 'Arial Black'; -fx-font-size: 14; -fx-font-weight: bold;");
        Label totalTimeHeader = new Label("Total Time (s)");
        totalTimeHeader.setStyle("-fx-text-fill: #00FFFF; -fx-font-family: 'Arial Black'; -fx-font-size: 14; -fx-font-weight: bold;");
        leaderboardGrid.add(rankHeader, 0, 0);
        leaderboardGrid.add(usernameHeader, 1, 0);
        leaderboardGrid.add(levelsHeader, 2, 0);
        leaderboardGrid.add(totalTimeHeader, 3, 0);

        // Class to hold player data
        class PlayerData {
            String username;
            int highestLevel;
            double totalCompletionTime;
            byte[] profilePicture;

            PlayerData(String username, int highestLevel, double totalCompletionTime, byte[] profilePicture) {
                this.username = username;
                this.highestLevel = highestLevel;
                this.totalCompletionTime = totalCompletionTime;
                this.profilePicture = profilePicture;
            }
        }

        List<PlayerData> players = new ArrayList<>();

        try (Connection conn = getConnection()) {
            PreparedStatement pstmt = conn.prepareStatement(
                    "SELECT u.username, u.profilePicture, u.unlockedLevels FROM users u");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String username = rs.getString("username");
                byte[] levelsBytes = rs.getBytes("unlockedLevels");
                Set<Integer> userLevels = new HashSet<>();
                if (levelsBytes != null && levelsBytes.length > 0) {
                    ByteArrayInputStream bais = new ByteArrayInputStream(levelsBytes);
                    ObjectInputStream ois = new ObjectInputStream(bais);
                    userLevels = (HashSet<Integer>) ois.readObject();
                    ois.close();
                }
                int highestLevel = userLevels.isEmpty() ? 0 : Collections.max(userLevels);

                PreparedStatement timeStmt = conn.prepareStatement(
                        "SELECT SUM(completionTime) as totalTime FROM leaderboard WHERE username = ?");
                timeStmt.setString(1, username);
                ResultSet timeRs = timeStmt.executeQuery();
                double totalCompletionTime = timeRs.next() ? timeRs.getDouble("totalTime") : Double.MAX_VALUE;

                byte[] picData = rs.getBytes("profilePicture");
                players.add(new PlayerData(username, highestLevel, totalCompletionTime, picData));
            }

            Collections.sort(players, (p1, p2) -> {
                if (p1.highestLevel != p2.highestLevel) {
                    return Integer.compare(p2.highestLevel, p1.highestLevel);
                }
                return Double.compare(p1.totalCompletionTime, p2.totalCompletionTime);
            });

            int row = 1;
            int currentRank = 1;
            int previousLevel = -1;
            double previousTime = -1;
            for (int i = 0; i < players.size(); i++) {
                PlayerData player = players.get(i);

                if (i > 0 && player.highestLevel == previousLevel && player.totalCompletionTime == previousTime) {
                    // Keep same rank for ties
                } else {
                    currentRank = i + 1;
                }
                previousLevel = player.highestLevel;
                previousTime = player.totalCompletionTime;

                ImageView profilePicView = new ImageView();
                profilePicView.setFitWidth(30);
                profilePicView.setFitHeight(30);
                Circle clipCircle = new Circle(15, 15, 15);
                profilePicView.setClip(clipCircle);
                if (player.profilePicture != null && player.profilePicture.length > 0) {
                    profilePicView.setImage(new Image(new ByteArrayInputStream(player.profilePicture)));
                } else {
                    File defaultProfilePic = new File("resources/images/default_profile.png");
                    if (defaultProfilePic.exists()) {
                        profilePicView.setImage(new Image(defaultProfilePic.toURI().toString()));
                    }
                }

                Label rankLabel = new Label(String.valueOf(currentRank));
                rankLabel.setStyle("-fx-text-fill: #FFFFFF; -fx-font-family: 'Arial'; -fx-font-size: 12;");

                HBox userBox = new HBox(10, profilePicView, new Label(player.username));
                userBox.setAlignment(Pos.CENTER_LEFT);
                userBox.setStyle("-fx-text-fill: #FFFFFF; -fx-font-family: 'Arial'; -fx-font-size: 12;");

                Label levelLabel = new Label(String.valueOf(player.highestLevel));
                levelLabel.setStyle("-fx-text-fill: #FFFFFF; -fx-font-family: 'Arial'; -fx-font-size: 12;");

                Label totalTimeLabel = new Label(player.totalCompletionTime == Double.MAX_VALUE ? "N/A" : String.format("%.2f s", Double.valueOf(player.totalCompletionTime)));
                totalTimeLabel.setStyle("-fx-text-fill: #FFFFFF; -fx-font-family: 'Arial'; -fx-font-size: 12;");

                String rowStyle = (row % 2 == 0) ? "-fx-background-color: #2E2E2E;" : "-fx-background-color: #262626;";
                Pane rowPane = new Pane();
                rowPane.setStyle(rowStyle);
                rowPane.setOnMouseEntered(event -> rowPane.setStyle(rowStyle + "-fx-background-color: #3C3C3C;"));
                rowPane.setOnMouseExited(event -> rowPane.setStyle(rowStyle));
                leaderboardGrid.add(rowPane, 0, row, 4, 1);
                GridPane.setFillWidth(rowPane, Boolean.TRUE);

                leaderboardGrid.add(rankLabel, 0, row);
                leaderboardGrid.add(userBox, 1, row);
                leaderboardGrid.add(levelLabel, 2, row);
                leaderboardGrid.add(totalTimeLabel, 3, row);
                row++;
            }
        } catch (SQLException | IOException | ClassNotFoundException e) {
            System.err.println("Error loading leaderboard: " + e.getMessage());
            Alert alert = new Alert(AlertType.ERROR, "Failed to load leaderboard data.");
            alert.showAndWait();
        }

        // Wrap grid in ScrollPane
        ScrollPane scrollPane = new ScrollPane(leaderboardGrid);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(false);
        scrollPane.setStyle("-fx-background: #3A3A3A; -fx-background-color: transparent;");
        scrollPane.setMaxHeight(300);
        scrollPane.setMaxWidth(600);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setPannable(false);
        leaderboardContainer.getChildren().add(scrollPane);

        VBox contentBox = new VBox(10, titleBox, leaderboardContainer);
        contentBox.setAlignment(Pos.CENTER);
        contentBox.setMaxWidth(620);
        dialogLayout.setCenter(contentBox);

        Scene dialogScene = new Scene(dialogLayout, 650, 450);
        dialogScene.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
            if (event.getTarget() == dialogScene.getRoot()) {
                event.consume();
            }
        });

        dialog.setScene(dialogScene);
        Platform.runLater(() -> {
            dialog.show();
            dialog.requestFocus();
        });
    }


    private void setupGameplayScreen() {
        root.getChildren().clear();
        root.setStyle("-fx-background-color: radial-gradient(center 50% 50%, radius 80%, #1C2526, #0A0E26);");
        primaryStage.setTitle("Chroma Flood - Level " + currentLevel.get());

        levelStartTime = System.currentTimeMillis(); // Record start time

        ChromaFloodSystem gameInstance = this;
        currentLevel.set(currentLevel.get()); // Ensure current level is set

        grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        playLevelAudio(currentLevel.get());
        tiles = new Tile[ROWS][COLS];
        setTargetColor(currentLevel.get());
        initializeGrid(gameInstance, currentLevel.get());

        // Create a StackPane to wrap the GridPane and add a border
        StackPane gridContainer = new StackPane();
        gridContainer.setAlignment(Pos.CENTER);

        // Calculate dimensions of the GridPane (based on tile size and gaps)
        double tileSize = 40.0; // Tile width/height from Tile constructor
        double hGap = 4.0; // GridPane hgap
        double vGap = 4.0; // GridPane vgap
        double gridWidth = COLS * tileSize + (COLS - 1) * hGap;
        double gridHeight = ROWS * tileSize + (ROWS - 1) * vGap;

        // Create a Rectangle for the border/background
        double borderPadding = 10.0; // Space between grid and border edge
        Rectangle borderRect = new Rectangle(
                gridWidth + 2 * borderPadding,
                gridHeight + 2 * borderPadding
        );
        borderRect.setFill(Color.web("#1A1A1A")); // Dark background to blend with game
        borderRect.setStroke(targetColor.getFxColor()); // NEW: Use targetColor for stroke
        borderRect.setStrokeWidth(3.0); // Visible border thickness
        borderRect.setArcWidth(15.0); // Rounded corners
        borderRect.setArcHeight(15.0);
        // Add a subtle shadow for depth
        DropShadow borderShadow = new DropShadow();
        borderShadow.setRadius(8.0);
        borderShadow.setOffsetX(4.0);
        borderShadow.setOffsetY(4.0);
        borderShadow.setColor(Color.rgb(0, 0, 0, 0.8));
        borderRect.setEffect(borderShadow);

        // Add Rectangle and GridPane to StackPane (Rectangle behind)
        gridContainer.getChildren().addAll(borderRect, grid);

        Label movesLabel = new Label("Moves: ");
        Label movesCount = new Label();
        movesCount.textProperty().bind(movesRemaining.asString());
        movesLabel.setStyle("-fx-text-fill: #00FFFF; -fx-font-family: 'Arial Black'; -fx-font-size: 16; -fx-font-weight: bold; -fx-effect: dropshadow(gaussian, #000000, 5, 0.5, 0, 0);");
        movesCount.setStyle("-fx-text-fill: #FFFFFF; -fx-font-family: 'Arial Black'; -fx-font-size: 16; -fx-font-weight: bold; -fx-effect: dropshadow(gaussian, #000000, 5, 0.5, 0, 0);");

        Label levelLabel = new Label("Level: ");
        Label levelCount = new Label();
        levelCount.textProperty().bind(currentLevel.asString());
        levelLabel.setStyle("-fx-text-fill: #00FFFF; -fx-font-family: 'Arial Black'; -fx-font-size: 16; -fx-font-weight: bold; -fx-effect: dropshadow(gaussian, #000000, 5, 0.5, 0, 0);");
        levelCount.setStyle("-fx-text-fill: #FFFFFF; -fx-font-family: 'Arial Black'; -fx-font-size: 16; -fx-font-weight: bold; -fx-effect: dropshadow(gaussian, #000000, 5, 0.5, 0, 0);");

        Label targetLabel = new Label("Target: ");
        targetLabel.setStyle("-fx-text-fill: #00FFFF; -fx-font-family: 'Arial Black'; -fx-font-size: 16; -fx-font-weight: bold; -fx-effect: dropshadow(gaussian, #000000, 5, 0.5, 0, 0);");
        Circle targetCircle = new Circle(12, targetColor.getFxColor());
        targetCircle.setEffect(new Glow(0.8));

        HBox statusBox = new HBox(15, movesLabel, movesCount, levelLabel, levelCount, targetLabel, targetCircle);
        statusBox.setAlignment(Pos.CENTER_LEFT);
        statusBox.setPadding(new Insets(10));
        statusBox.setStyle("-fx-background-color: linear-gradient(to right, rgba(0, 0, 50, 0.8), rgba(0, 0, 100, 0.8)); -fx-background-radius: 10;");

        VBox paletteBox = new VBox(15);
        paletteBox.setAlignment(Pos.CENTER);
        paletteBox.setPadding(new Insets(15));
        paletteBox.setStyle("-fx-background-color: rgba(0, 0, 50, 0.8); -fx-background-radius: 10;");
        for (int i = 0; i < palette.size(); i++) {
            ColorType color = palette.get(i);
            Circle orb = new Circle(25, color.getFxColor());
            orb.setStroke(Color.TRANSPARENT);
            orb.setStrokeWidth(2);
            Polygon arrow = new Polygon();
            arrow.getPoints().addAll(
                    10.0, 0.0,  // Top-right
                    0.0, 5.0,   // Left tip
                    10.0, 10.0  // Bottom-right
            );
            arrow.setFill(Color.WHITE);
            arrow.setVisible(color == ColorType.BLUE);
            arrow.setEffect(null);
            arrow.setCache(true);
            arrow.setCacheHint(CacheHint.SPEED);
            TranslateTransition arrowAnim = new TranslateTransition(Duration.millis(250), arrow);
            arrowAnim.setByX(5);
            arrowAnim.setAutoReverse(true);
            arrowAnim.setCycleCount(Animation.INDEFINITE);
            arrow.setUserData(arrowAnim);
            if (color == ColorType.BLUE) {
                orb.setEffect(new Glow(0.8));
                orb.setStroke(Color.WHITE);
                arrowAnim.play();
            } else {
                orb.setEffect(new Glow(0.3));
            }
            int index = i + 1;
            Label number = new Label(String.valueOf(index));
            number.setStyle("-fx-text-fill: #FFFFFF; -fx-font-family: 'Arial Black'; -fx-font-size: 14; -fx-effect: dropshadow(gaussian, #000000, 3, 0.5, 0, 0);");
            number.setAlignment(Pos.CENTER);
            HBox orbWithNumberAndArrow = new HBox(10);
            orbWithNumberAndArrow.getChildren().addAll(number, orb, arrow);
            orbWithNumberAndArrow.setAlignment(Pos.CENTER);
            number.setPrefWidth(20);
            orbWithNumberAndArrow.setMinWidth(80);
            orb.setOnMouseClicked(event -> {
                selectedColor = color;
                updatePaletteVisuals();
                paletteBox.getChildren().forEach(node -> {
                    HBox innerBox = (HBox) node;
                    Circle otherOrb = (Circle) innerBox.getChildren().get(1);
                    Polygon otherArrow = (Polygon) innerBox.getChildren().get(2);
                    TranslateTransition otherAnim = (TranslateTransition) otherArrow.getUserData();
                    otherOrb.setStroke(Color.TRANSPARENT);
                    otherOrb.setEffect(new Glow(0.3));
                    otherArrow.setVisible(false);
                    otherArrow.setEffect(null);
                    otherAnim.stop();
                    otherArrow.setTranslateX(0);
                });
                orb.setStroke(Color.WHITE);
                orb.setEffect(new Glow(0.8));
                arrow.setVisible(true);
                arrow.setEffect(null);
                arrowAnim.play();
            });
            orb.setOnMouseEntered(event -> {
                orb.setEffect(new Glow(1.2));
                arrow.setEffect(null);
                if (selectedColor != color) {
                    orb.setStroke(Color.rgb(255, 255, 255, 0.5));
                }
            });
            orb.setOnMouseExited(event -> {
                if (selectedColor == color) {
                    orb.setStroke(Color.WHITE);
                    orb.setEffect(new Glow(0.8));
                    arrow.setVisible(true);
                } else {
                    orb.setStroke(Color.TRANSPARENT);
                    orb.setEffect(new Glow(0.3));
                    arrow.setVisible(false);
                }
                arrow.setEffect(null);
            });
            paletteBox.getChildren().add(orbWithNumberAndArrow);
        }

        selectedColor = ColorType.BLUE;
        updatePaletteVisuals();

        Button resetButton = new Button("RESET");
        resetButton.setStyle("-fx-background-color: linear-gradient(to bottom, #FF4444, #CC0000); -fx-text-fill: #FFFFFF; -fx-font-family: 'Arial Black'; -fx-font-size: 14; -fx-font-weight: bold; -fx-background-radius: 15; -fx-padding: 10 20; -fx-effect: dropshadow(gaussian, #000000, 5, 0.5, 0, 0);");
        resetButton.setOnAction(this::handleReset);
        resetButton.setOnMouseEntered(event -> resetButton.setStyle("-fx-background-color: linear-gradient(to bottom, #FF6666, #EE2222); -fx-text-fill: #FFFFFF; -fx-font-family: 'Arial Black'; -fx-font-size: 14; -fx-font-weight: bold; -fx-background-radius: 15; -fx-padding: 10 20; -fx-effect: dropshadow(gaussian, #000000, 8, 0.7, 0, 0);"));
        resetButton.setOnMouseExited(event -> resetButton.setStyle("-fx-background-color: linear-gradient(to bottom, #FF4444, #CC0000); -fx-text-fill: #FFFFFF; -fx-font-family: 'Arial Black'; -fx-font-size: 14; -fx-font-weight: bold; -fx-background-radius: 15; -fx-padding: 10 20; -fx-effect: dropshadow(gaussian, #000000, 5, 0.5, 0, 0);"));

        Button backButton = new Button("BACK");
        backButton.setStyle("-fx-background-color: linear-gradient(to bottom, #4444FF, #0000CC); -fx-text-fill: #FFFFFF; -fx-font-family: 'Arial Black'; -fx-font-size: 14; -fx-font-weight: bold; -fx-background-radius: 15; -fx-padding: 10 20; -fx-effect: dropshadow(gaussian, #000000, 5, 0.5, 0, 0);");
        backButton.setOnAction(event -> {
            if (floodFillTimeline != null) {
                floodFillTimeline.stop();
                floodFillTimeline = null;
            }
            if (clickSound != null) {
                clickSound.stop();
            }
            isAnimating = false;
            showLevelSelectScreen();
        });
        backButton.setOnMouseEntered(event -> backButton.setStyle("-fx-background-color: linear-gradient(to bottom, #6666FF, #2222EE); -fx-text-fill: #FFFFFF; -fx-font-family: 'Arial Black'; -fx-font-size: 14; -fx-font-weight: bold; -fx-background-radius: 15; -fx-padding: 10 20; -fx-effect: dropshadow(gaussian, #000000, 8, 0.7, 0, 0);"));
        backButton.setOnMouseExited(event -> backButton.setStyle("-fx-background-color: linear-gradient(to bottom, #4444FF, #0000CC); -fx-text-fill: #FFFFFF; -fx-font-family: 'Arial Black'; -fx-font-size: 14; -fx-font-weight: bold; -fx-background-radius: 15; -fx-padding: 10 20; -fx-effect: dropshadow(gaussian, #000000, 5, 0.5, 0, 0);"));

        Button settingsButton = new Button("SETTINGS");
        settingsButton.setStyle("-fx-background-color: linear-gradient(to bottom, #44FF44, #00CC00); -fx-text-fill: #FFFFFF; -fx-font-family: 'Arial Black'; -fx-font-size: 14; -fx-font-weight: bold; -fx-background-radius: 15; -fx-padding: 10 20; -fx-effect: dropshadow(gaussian, #000000, 5, 0.5, 0, 0);");
        settingsButton.setOnAction(event -> showSettingsDialog());
        settingsButton.setOnMouseEntered(event -> settingsButton.setStyle("-fx-background-color: linear-gradient(to bottom, #66FF66, #22EE22); -fx-text-fill: #FFFFFF; -fx-font-family: 'Arial Black'; -fx-font-size: 14; -fx-font-weight: bold; -fx-background-radius: 15; -fx-padding: 10 20; -fx-effect: dropshadow(gaussian, #000000, 8, 0.7, 0, 0);"));
        settingsButton.setOnMouseExited(event -> settingsButton.setStyle("-fx-background-color: linear-gradient(to bottom, #44FF44, #00CC00); -fx-text-fill: #FFFFFF; -fx-font-family: 'Arial Black'; -fx-font-size: 14; -fx-font-weight: bold; -fx-background-radius: 15; -fx-padding: 10 20; -fx-effect: dropshadow(gaussian, #000000, 5, 0.5, 0, 0);"));

        HBox buttonBox = new HBox(20, resetButton, backButton, settingsButton);
        buttonBox.setAlignment(Pos.CENTER_RIGHT);
        buttonBox.setPadding(new Insets(10));

        root.setTop(statusBox);
        root.setCenter(gridContainer);
        root.setRight(paletteBox);
        root.setBottom(buttonBox);
        BorderPane.setAlignment(statusBox, Pos.CENTER_LEFT);
        BorderPane.setAlignment(gridContainer, Pos.CENTER);
        BorderPane.setAlignment(paletteBox, Pos.CENTER_RIGHT);
        BorderPane.setAlignment(buttonBox, Pos.BOTTOM_RIGHT);
        BorderPane.setMargin(statusBox, new Insets(15, 15, 15, 15));
        BorderPane.setMargin(gridContainer, new Insets(15));
        BorderPane.setMargin(paletteBox, new Insets(15));
        BorderPane.setMargin(buttonBox, new Insets(30, 30, 50, 0));

        checkSuccess();
    }

    private void applySwitchStyle(ToggleButton button, String prefix) {
        // Set initial style based on selected state
        if (button.isSelected()) {
            button.setStyle(
                    "-fx-background-color: linear-gradient(to bottom, #AAAAAA, #666666);" +
                            "-fx-text-fill: #FFFFFF;" +
                            "-fx-font-family: 'Arial Black';" +
                            "-fx-font-size: 14;" +
                            "-fx-font-weight: bold;" +
                            "-fx-background-radius: 20;" +
                            "-fx-padding: 5 10;" +
                            "-fx-pref-width: 160;" +  // Increased width for longer text
                            "-fx-alignment: center;" +
                            "-fx-border-color: #FFFFFF;" +
                            "-fx-border-width: 2;" +
                            "-fx-border-radius: 20;" +
                            "-fx-effect: dropshadow(gaussian, #000000, 5, 0.5, 0, 0);"
            );
            button.setText(prefix + " Muted");
        } else {
            button.setStyle(
                    "-fx-background-color: linear-gradient(to bottom, #4444FF, #0000CC);" +
                            "-fx-text-fill: #FFFFFF;" +
                            "-fx-font-family: 'Arial Black';" +
                            "-fx-font-size: 14;" +
                            "-fx-font-weight: bold;" +
                            "-fx-background-radius: 20;" +
                            "-fx-padding: 5 10;" +
                            "-fx-pref-width: 160;" +  // Increased width for longer text
                            "-fx-alignment: center;" +
                            "-fx-border-color: #FFFFFF;" +
                            "-fx-border-width: 2;" +
                            "-fx-border-radius: 20;" +
                            "-fx-effect: dropshadow(gaussian, #000000, 5, 0.5, 0, 0);"
            );
            button.setText(prefix + " Unmuted");
        }

        // Update style based on selected state changes
        button.selectedProperty().addListener((obs, wasSelected, isSelected) -> {
            if (isSelected) {
                button.setStyle(
                        "-fx-background-color: linear-gradient(to bottom, #AAAAAA, #666666);" +
                                "-fx-text-fill: #FFFFFF;" +
                                "-fx-font-family: 'Arial Black';" +
                                "-fx-font-size: 14;" +
                                "-fx-font-weight: bold;" +
                                "-fx-background-radius: 20;" +
                                "-fx-padding: 5 10;" +
                                "-fx-pref-width: 160;" +
                                "-fx-alignment: center;" +
                                "-fx-border-color: #FFFFFF;" +
                                "-fx-border-width: 2;" +
                                "-fx-border-radius: 20;" +
                                "-fx-effect: dropshadow(gaussian, #000000, 5, 0.5, 0, 0);"
                );
                button.setText(prefix + " Muted");
            } else {
                button.setStyle(
                        "-fx-background-color: linear-gradient(to bottom, #4444FF, #0000CC);" +
                                "-fx-text-fill: #FFFFFF;" +
                                "-fx-font-family: 'Arial Black';" +
                                "-fx-font-size: 14;" +
                                "-fx-font-weight: bold;" +
                                "-fx-background-radius: 20;" +
                                "-fx-padding: 5 10;" +
                                "-fx-pref-width: 160;" +
                                "-fx-alignment: center;" +
                                "-fx-border-color: #FFFFFF;" +
                                "-fx-border-width: 2;" +
                                "-fx-border-radius: 20;" +
                                "-fx-effect: dropshadow(gaussian, #000000, 5, 0.5, 0, 0);"
                );
                button.setText(prefix + " Unmuted");
            }
        });
    }

    private void showSettingsDialog() {
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(primaryStage);
        dialog.setTitle("Audio Settings");

        // Explicitly disable full-screen and maximized modes
        dialog.setFullScreen(false);
        dialog.setMaximized(false);
        dialog.setResizable(false); // Optional: prevents resizing

        VBox dialogVBox = new VBox(20);
        dialogVBox.setAlignment(Pos.CENTER);
        dialogVBox.setPadding(new Insets(20));
        dialogVBox.setStyle("-fx-background-color: linear-gradient(to bottom, #333333, #111111);");

        Label titleLabel = new Label("Audio Settings");
        titleLabel.setStyle("-fx-text-fill: #FFFFFF; -fx-font-family: 'Arial Black'; -fx-font-size: 18; -fx-font-weight: bold; -fx-effect: dropshadow(gaussian, #000000, 5, 0.5, 0, 0);");

        // Effects Mute Toggle
        ToggleButton effectsMuteButton = new ToggleButton(isEffectsMuted ? "Effects Muted" : "Effects Unmuted");
        effectsMuteButton.setSelected(isEffectsMuted);
        applySwitchStyle(effectsMuteButton, "Effects"); // Updated method call, see Step 7
        effectsMuteButton.setOnAction(event -> {
            isEffectsMuted = effectsMuteButton.isSelected();
            updateAudioSettings();
        });

        // Music Mute Toggle
        ToggleButton musicMuteButton = new ToggleButton(isMusicMuted ? "Music Muted" : "Music Unmuted");
        musicMuteButton.setSelected(isMusicMuted);
        applySwitchStyle(musicMuteButton, "Music"); // Updated method call, see Step 7
        musicMuteButton.setOnAction(event -> {
            isMusicMuted = musicMuteButton.isSelected();
            updateAudioSettings();
        });

        Label volumeLabel = new Label("Volume: ");
        volumeLabel.setStyle("-fx-text-fill: #00FFFF; -fx-font-family: 'Arial Black'; -fx-font-size: 14; -fx-font-weight: bold; -fx-effect: dropshadow(gaussian, #000000, 3, 0.5, 0, 0);");

        Slider volumeSlider = new Slider(0.0, 1.0, audioVolume);
        volumeSlider.setShowTickMarks(true);
        volumeSlider.setShowTickLabels(true);
        volumeSlider.setMajorTickUnit(0.25);
        volumeSlider.setMinorTickCount(5);
        volumeSlider.setBlockIncrement(0.1);
        volumeSlider.setSnapToTicks(false);
        volumeSlider.valueProperty().addListener((obs, oldValue, newValue) -> {
            audioVolume = newValue.doubleValue();
            updateAudioSettings();
        });

        HBox volumeBox = new HBox(10, volumeLabel, volumeSlider);
        volumeBox.setAlignment(Pos.CENTER);

        Button closeButton = new Button("Close");
        closeButton.setStyle("-fx-background-color: linear-gradient(to bottom, #FF4444, #CC0000); -fx-text-fill: #FFFFFF; -fx-font-family: 'Arial Black'; -fx-font-size: 14; -fx-font-weight: bold; -fx-background-radius: 10; -fx-padding: 10 20;");
        closeButton.setOnAction(event -> dialog.close());
        closeButton.setOnMouseEntered(event -> closeButton.setStyle("-fx-background-color: linear-gradient(to bottom, #FF6666, #EE2222); -fx-text-fill: #FFFFFF; -fx-font-family: 'Arial Black'; -fx-font-size: 14; -fx-font-weight: bold; -fx-background-radius: 10; -fx-padding: 10 20;"));
        closeButton.setOnMouseExited(event -> closeButton.setStyle("-fx-background-color: linear-gradient(to bottom, #FF4444, #CC0000); -fx-text-fill: #FFFFFF; -fx-font-family: 'Arial Black'; -fx-font-size: 14; -fx-font-weight: bold; -fx-background-radius: 10; -fx-padding: 10 20;"));

        dialogVBox.getChildren().addAll(titleLabel, effectsMuteButton, musicMuteButton, volumeBox, closeButton);

        Scene dialogScene = new Scene(dialogVBox, 300, 300); // Increased height for two buttons
        dialog.setScene(dialogScene);
        dialog.show();
    }

    // Add new method to update audio settings
    private void updateAudioSettings() {
        clickSound.setVolume(isEffectsMuted ? 0.0 : audioVolume);
        MediaPlayer[] backgroundClips = {progressionPathAudio, level1Audio, level2Audio, level3Audio, level4Audio, level5Audio, level6Audio, level7Audio, level8Audio, level9Audio, level10Audio};
        for (MediaPlayer clip : backgroundClips) {
            if (clip != null) {
                clip.setVolume(isMusicMuted ? 0.0 : audioVolume);
            }
        }
        saveProgress(); // Save settings whenever they are updated
    }

    private void updateImageResources() {
        File cacheFolder = new File(IMAGE_FOLDER);
        if (!cacheFolder.exists()) {
            cacheFolder.mkdirs(); // Create cache folder if it doesn't exist
        }

        Image[] levelImages = new Image[IMAGE_FILES.length];
        for (int i = 0; i < IMAGE_FILES.length; i++) {
            File imageFile = new File(IMAGE_FOLDER + File.separator + IMAGE_FILES[i]);

            // Check if the image file already exists in the cache
            if (imageFile.exists()) {
                // Load image from cache
                Image cachedImage = new Image(imageFile.toURI().toString());
                if (!cachedImage.isError()) {
                    levelImages[i] = cachedImage;
                    System.out.println("Loaded cached image: " + IMAGE_FILES[i]);
                } else {
                    System.err.println("Error loading cached image: " + IMAGE_FILES[i]);
                    levelImages[i] = null;
                }
            } else if (IMAGE_DOWNLOAD_URLS[i] != null) {
                // File doesn't exist and URL is available, attempt to download
                try {
                    System.out.println("Downloading image: " + IMAGE_FILES[i] + " from URL: " + IMAGE_DOWNLOAD_URLS[i]);
                    InputStream in = new URL(IMAGE_DOWNLOAD_URLS[i]).openStream();
                    Files.copy(in, Paths.get(imageFile.getPath()), StandardCopyOption.REPLACE_EXISTING);
                    in.close();
                    System.out.println("Successfully downloaded and cached image: " + imageFile.getPath());

                    // Load the newly downloaded image
                    Image downloadedImage = new Image(imageFile.toURI().toString());
                    if (!downloadedImage.isError()) {
                        levelImages[i] = downloadedImage;
                    } else {
                        System.err.println("Error loading downloaded image: " + IMAGE_FILES[i]);
                        levelImages[i] = null;
                    }
                } catch (IOException e) {
                    System.err.println("Failed to download image: " + IMAGE_FILES[i] + " - " + e.getMessage());
                    levelImages[i] = null;
                    // Only show alert if online (avoid spamming offline users)
                    try {
                        // Simple connectivity check
                        new URL("https://www.google.com").openConnection().connect();
                        new Alert(Alert.AlertType.ERROR, "Failed to download image: " + IMAGE_FILES[i] + ". Check your internet connection.").showAndWait();
                    } catch (IOException ignored) {
                        System.out.println("Offline mode: Skipping download for " + IMAGE_FILES[i]);
                    }
                }
            } else {
                // No file and no URL (e.g., levels 9 and 10)
                levelImages[i] = null;
                System.out.println("No image available for: " + IMAGE_FILES[i]);
            }
        }

        // Assign images to instance fields
        level1Image = levelImages[0];
        level2Image = levelImages[1];
        level3Image = levelImages[2];
        level4Image = levelImages[3];
        level5Image = levelImages[4];
        level6Image = levelImages[5];
        level7Image = levelImages[6];
        level8Image = levelImages[7];
        level9Image = levelImages[8];
        level10Image = levelImages[9];

        saveProgress(); // Save progress after updating images
    }

    private void setTargetColor(int level) {
        switch (level) {
            case 1: targetColor = ColorType.YELLOW; break;
            case 2: targetColor = ColorType.GREEN; break;
            case 3: targetColor = ColorType.RED; break;
            case 4: targetColor = ColorType.BLUE; break;
            case 5: targetColor = ColorType.RED; break;
            case 6: targetColor = ColorType.RED; break;
            case 7: targetColor = ColorType.GREEN; break;
            case 8: targetColor = ColorType.GREEN; break;
            case 9: targetColor = ColorType.BLUE; break;
            case 10: targetColor = ColorType.YELLOW; break;
            default: targetColor = ColorType.YELLOW;
        }
    }

    private void initializeGrid(ChromaFloodSystem gameInstance, int level) {
        grid.getChildren().clear();
        tiles = new Tile[ROWS][COLS];
        int patternIndex = Math.max(0, Math.min(level - 1, LEVEL_PATTERNS.length - 1));
        char[][] pattern = LEVEL_PATTERNS[patternIndex];
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                ColorType initialColor = charToColor(pattern[row][col]);
                Tile tile = new Tile(40, 40, initialColor, gameInstance);
                tiles[row][col] = tile;
                grid.add(tile, col, row);
            }
        }
        grid.setHgap(4);
        grid.setVgap(4);
        // Inside initializeGrid method, update the switch:
        switch (level) {
            case 1: movesRemaining.set(4 + extraMoves); break;
            case 2: movesRemaining.set(4 + extraMoves); break;
            case 3: movesRemaining.set(4 + extraMoves); break;
            case 4: movesRemaining.set(4 + extraMoves); break;
            case 5: movesRemaining.set(3 + extraMoves); break;
            case 6: movesRemaining.set(7 + extraMoves); break;
            case 7: movesRemaining.set(5 + extraMoves); break;
            case 8: movesRemaining.set(4 + extraMoves); break;
            case 9: movesRemaining.set(4 + extraMoves); break;
            case 10: movesRemaining.set(5 + extraMoves); break;
            default: movesRemaining.set(4 + extraMoves);
        }
        setTargetColor(level);
    }

    private ColorType charToColor(char c) {
        switch (c) {
            case 'R': return ColorType.RED;
            case 'B': return ColorType.BLUE;
            case 'Y': return ColorType.YELLOW;
            case 'G': return ColorType.GREEN;
            case 'O': return ColorType.OBSTACLE;  // New case for obstacles
            default: return ColorType.GREEN;
        }
    }

    public void handleTileClick(Tile tile) {
        if (isAnimating) {
            return;
        }
        if (movesRemaining.get() > 0 && selectedColor != null) {
            ColorType originalColor = tile.getColor();
            if (originalColor != selectedColor && originalColor != ColorType.OBSTACLE) {
                isAnimating = true; // Lock to prevent new clicks
                // Create list for tiles to change
                List<Tile> tilesToChange = new ArrayList<>();
                // Always include the clicked tile
                tilesToChange.add(tile);
                // Add any connected tiles with the same color
                tilesToChange.addAll(getFloodFillTiles(GridPane.getRowIndex(tile), GridPane.getColumnIndex(tile), originalColor, selectedColor));
                // Update move counter immediately
                movesRemaining.set(movesRemaining.get() - 1);
                // Animate the color change for all tiles, check success after animation
                animateFloodFill(tilesToChange, selectedColor);
            }
        }
    }

    private void updatePaletteVisuals() {
        if (root.getRight() instanceof VBox paletteBox) {
            paletteBox.getChildren().forEach(node -> {
                if (node instanceof HBox hbox) {
                    Circle orb = (Circle) hbox.getChildren().get(1);
                    Polygon arrow = (Polygon) hbox.getChildren().get(2);
                    TranslateTransition anim = (TranslateTransition) arrow.getUserData();
                    if (orb.getFill().equals(selectedColor != null ? selectedColor.getFxColor() : ColorType.BLUE.getFxColor())) {
                        orb.setEffect(new Glow(0.8));
                        orb.setStroke(Color.WHITE);
                        orb.setStrokeWidth(2);
                        arrow.setVisible(true);
                        arrow.setEffect(null);
                        anim.play();
                    } else {
                        orb.setEffect(new Glow(0.3));
                        orb.setStroke(Color.TRANSPARENT);
                        arrow.setVisible(false);
                        arrow.setEffect(null);
                        anim.stop();
                        arrow.setTranslateX(0);
                    }
                }
            });
            paletteBox.requestLayout();
        }
    }

    private void handleReset(ActionEvent event) {
        // Stop any ongoing click sound
        if (clickSound != null) {
            clickSound.stop();
            System.out.println("Stopped click sound in handleReset");
        }
        // Stop any ongoing flood fill animation
        if (floodFillTimeline != null) {
            floodFillTimeline.stop();
            floodFillTimeline = null;
            System.out.println("Stopped flood fill timeline in handleReset");
        }
        // Reset animation flag to prevent new animations
        isAnimating = false;
        // Reinitialize grid and update visuals
        initializeGrid(this, currentLevel.get());
        updatePaletteVisuals();
    }

    private void checkSuccess() {
        if (isCheckingSuccess) {
            return;
        }
        isCheckingSuccess = true;
        try {
            boolean allTarget = true;
            for (int row = 0; row < ROWS; row++) {
                for (int col = 0; col < COLS; col++) {
                    if (tiles[row][col].getColor() != targetColor && tiles[row][col].getColor() != ColorType.OBSTACLE) {
                        allTarget = false;
                        break;
                    }
                }
                if (!allTarget) break;
            }
            if (allTarget) {
                Platform.runLater(() -> {
                    System.out.println("Showing success alert for level " + currentLevel.get());
                    double completionTime = (System.currentTimeMillis() - levelStartTime) / 1000.0;
                    int current = currentLevel.get();

                    try (Connection conn = getConnection()) {
                        // Check existing time
                        PreparedStatement checkStmt = conn.prepareStatement(
                                "SELECT completionTime FROM leaderboard WHERE username = ? AND level = ?");
                        checkStmt.setString(1, currentUser);
                        checkStmt.setInt(2, current);
                        ResultSet rs = checkStmt.executeQuery();

                        boolean shouldUpdate = true;
                        if (rs.next()) {
                            double existingTime = rs.getDouble("completionTime");
                            if (completionTime >= existingTime) {
                                shouldUpdate = false;  // New time is not better; skip update
                            }
                        }

                        if (shouldUpdate) {
                            PreparedStatement pstmt = conn.prepareStatement(
                                    "INSERT OR REPLACE INTO leaderboard (username, level, completionTime) VALUES (?, ?, ?)");
                            pstmt.setString(1, currentUser);
                            pstmt.setInt(2, current);
                            pstmt.setDouble(3, completionTime);
                            pstmt.executeUpdate();
                        }
                    } catch (SQLException e) {
                        System.err.println("Error saving completion time: " + e.getMessage());
                    }

                    if (current < LEVEL_PATTERNS.length) {
                        unlockedLevels.add(Integer.valueOf(current + 1));
                        saveProgress();
                    }
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Success!");
                    alert.setHeaderText("All blocks turned to " + targetColor.name() + "!");
                    alert.setContentText(String.format("Completed Level %d in %.2f seconds.", Integer.valueOf(current), Double.valueOf(completionTime)));
                    alert.showAndWait();
                    lossCount = 0; // Reset lossCount on success
                    extraMoves = 0;
                    selectedColor = ColorType.BLUE;
                    int nextLevel = current + 1;
                    if (nextLevel <= LEVEL_PATTERNS.length && unlockedLevels.contains(Integer.valueOf(nextLevel))) {
                        currentLevel.set(nextLevel);
                        setupGameplayScreen();
                    } else {
                        if (current == LEVEL_PATTERNS.length) {
                            showCompletionScreen();
                        } else {
                            showLevelSelectScreen();
                        }
                    }
                    updatePaletteVisuals();
                });
            } else if (movesRemaining.get() <= 0) {
                Platform.runLater(() -> {
                    System.out.println("Checking lose condition for level " + currentLevel.get() + ", lossCount: " + lossCount + ", extraMoves: " + extraMoves);
                    lossCount++;
                    if (lossCount % 3 == 0 && extraMoves < 3) { // Show difficulty adjustment every 3rd loss
                        System.out.println("Showing difficulty adjustment alert for level " + currentLevel.get());
                        Alert alert = new Alert(AlertType.CONFIRMATION);
                        alert.setTitle("Difficulty Adjustment");
                        alert.setHeaderText("You've lost multiple times on Level " + currentLevel.get());
                        alert.setContentText("Would you like to lower the difficulty by adding 1 extra move?");
                        ButtonType yesButton = new ButtonType("Yes");
                        ButtonType noButton = new ButtonType("No");
                        alert.getButtonTypes().setAll(yesButton, noButton);
                        alert.showAndWait().ifPresent(response -> {
                            if (response == yesButton) {
                                extraMoves++;
                                lossCount = 0; // Reset lossCount after accepting extra move
                                System.out.println("Extra move added, extraMoves: " + extraMoves);
                            } else {
                                lossCount = 0; // Reset lossCount after declining to keep cycle
                                System.out.println("Extra move declined, resetting lossCount");
                            }
                            initializeGrid(this, currentLevel.get());
                            updatePaletteVisuals();
                        });
                    } else {
                        System.out.println("Showing game over alert for level " + currentLevel.get());
                        Alert alert = new Alert(AlertType.WARNING);
                        alert.setTitle("Game Over");
                        alert.setHeaderText("No moves remaining!");
                        alert.setContentText("Resetting level.");
                        alert.showAndWait();
                        initializeGrid(this, currentLevel.get());
                        updatePaletteVisuals();
                    }
                });
            }
        } finally {
            isCheckingSuccess = false;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}