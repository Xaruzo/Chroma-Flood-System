import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.scene.shape.StrokeLineCap;
import javafx.animation.*;
import java.net.InetAddress;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;
import java.net.NetworkInterface;
import java.security.MessageDigest;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.util.Arrays;
import javafx.util.Duration;
import javafx.scene.effect.*;
import javafx.scene.paint.Color;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.stage.StageStyle;
import javafx.stage.Modality;
import javafx.scene.text.TextAlignment;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.animation.*;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.*;
import javafx.scene.shape.Circle;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.shape.Rectangle;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.geometry.Pos;
import javafx.stage.FileChooser;
import javafx.scene.Node;
import javafx.scene.shape.Path;
import javafx.scene.shape.FillRule;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.ArcTo;
import javafx.scene.shape.ClosePath;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;          // you probably already have this
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.Glow;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.image.*;
import javafx.embed.swing.SwingFXUtils;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import javax.imageio.*;
import javax.imageio.plugins.jpeg.*;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.HashSet;
import java.util.Set;
import java.util.Collections;
import java.util.stream.Collectors;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import com.google.gson.JsonElement;
import java.io.InputStream;
import java.nio.file.Paths;
import javafx.scene.media.AudioClip;
import java.nio.file.StandardCopyOption;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import javafx.concurrent.Task;
import javafx.animation.RotateTransition;
import javafx.animation.Animation;
import javafx.scene.layout.StackPane;
import java.util.ArrayList;
import javafx.animation.*;
import javafx.scene.paint.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import java.util.List;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Polygon;
import javafx.animation.TranslateTransition;
import javafx.scene.CacheHint;
import java.util.Queue;
import java.util.LinkedList;
import javafx.animation.ScaleTransition;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.Base64;
import java.awt.BasicStroke;
import java.awt.RenderingHints;
import javax.imageio.ImageIO;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javafx.scene.control.CheckBox;
import org.mindrot.jbcrypt.BCrypt;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableCell;
import javafx.beans.property.SimpleStringProperty;
import javafx.animation.*;
import javafx.scene.paint.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.animation.*;
import javafx.scene.paint.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.Cursor;
import javafx.scene.shape.StrokeType;



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

public class ChromaFloodSystem extends Application {
    private volatile boolean justHandledGameEnd = false;
    private Stage connectionLostStage = null;
    private Timeline reconnectCheckTimer = null;
    private Label reconnectStatusLabel = null;
    private Stage tutorialStage;
    private Timeline banCheckTimer;
    private static final long BAN_CHECK_INTERVAL_MS = 120000;
    private boolean tutorialActive = false;
    private boolean skipBackConfirmation = false;
    private int currentTutorialStep = 0;
    private Rectangle tutorialOverlay;
    private boolean tutorialCompleted = false;
    private VBox tutorialCard;
    private volatile boolean isModalDialogOpen = false;
    private int reconnectAttempts = 0;
    public Stage primaryStage;
    public String currentUser = null;
    public boolean isOnLevelSelectScreen = false;
    private volatile boolean isNavigating = false;
    private Timeline maintenanceCheckTimer = null;
    private final Set<String> cachedBannedUsers = Collections.synchronizedSet(new HashSet<>());
    private static final int MAINTENANCE_CHECK_INTERVAL_SECONDS = 15; // Check every 60 seconds (less frequent)
    private AtomicBoolean isCheckingMaintenance = new AtomicBoolean(false);
    private volatile boolean maintenanceDialogShown = false;
    private Stage bannedDialogStage = null;
    private int currentAppealPage = 0;
    private final int APPEALS_PER_PAGE = 15;
    private int totalAppealsCount = 0;
    private Button prevPageBtn;
    private Button nextPageBtn;
    private Label pageInfoLabel;
    private ComboBox<String> statusFilterCombo;
    private String currentStatusFilter = "All"; // Track current filter
    private TextField searchField;
    private String currentSearchQuery = "";
    private Stage resetProgressStage = null;
    private VBox resetProgressDialog = null;
    private boolean isLevelEntranceAnimating = false;
    private boolean isTransitioningToGameplay = false;
    private Stage audioSettingsStage = null;
    private Stage banAppealsStage = null;
    public Stage profileSettingsStage = null;
    private TableView<JsonObject> banAppealsTable = null;
    private ObservableList<JsonObject> allAppealsData = FXCollections.observableArrayList();
    private Stage userManagementStage = null;
    private TableView<JsonObject> userManagementTable = null;
    private ObservableList<JsonObject> allUsersData = FXCollections.observableArrayList();
    private volatile boolean audioResourcesReady = false;
    private volatile boolean isOnline = true;  // ADD THIS
    private Timeline connectionCheckTimer = null;  // ADD THIS
    public ImageView topBarProfilePicView;
    private boolean isTransitioning = false;
    private long lastLeaderboardFetch = 0;
    private static final long CACHE_DURATION_MS = 30000;
    private ParallelTransition gridEntranceAnimation;
    public Label usernameLabel;
    public boolean isDialogOpen = false;
    private Stage leaderboardStage;  // Add this as a field at the top of your class
    private TableView<PlayerData> leaderboardTable;
    private static final String ADMIN_USERNAME = "admin";
    private final Map<String, Image> profilePictureCache = new HashMap<>();
    private Task<Void> leaderboardProfilePreloadTask = null;
    private static final String PROFILE_IMAGE_FOLDER = "resources/images";
    private static final String DEFAULT_PROFILE_PIC_FILE = "default_profile.png";
    private static final String DEFAULT_PROFILE_PIC_URL = "https://drive.google.com/uc?export=download&id=1m1yQ0995BXEa4tBMS_R2p9jY81uc4kAF";
    private static final String LEADERBOARD_API = "https://your-springboot.up.railway.app/api/leaderboard";
    public static final String SUPABASE_URL = "https://pnyzbscskolmgdiuqmwh.supabase.co";
    public static final String SUPABASE_ANON_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InBueXpic2Nza29sbWdkaXVxbXdoIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NjM3MDA0NjksImV4cCI6MjA3OTI3NjQ2OX0.p9Z808Xs4IFgg5vUyv0WEzK22fVSQ6fWd3kNU23bsf0";
    private static final String USERS_TABLE = SUPABASE_URL + "/rest/v1/profiles";
    private static final String APP_VERSION = "1.0.1"; // Semantic versioning
    private static final String VERSION_CHECK_URL = SUPABASE_URL + "/rest/v1/app_config?key=eq.min_required_version";
    private static final String ONLINE_GET = SUPABASE_URL + "/rest/v1/leaderboard?select=*&order=level.desc,completion_time.asc&limit=200";
    private static final String ONLINE_POST = SUPABASE_URL + "/rest/v1/leaderboard?on_conflict=username,level";
    private static final String LEADERBOARD_API_GET = SUPABASE_URL + "/rest/v1/leaderboard?select=*&order=level.desc,completion_time.asc&limit=500";
    private static final String LEADERBOARD_API_POST = SUPABASE_URL + "/rest/v1/leaderboard?on_conflict=username,level";
    private static final String MAINTENANCE_CHECK_URL = SUPABASE_URL + "/rest/v1/app_config?key=eq.maintenance_mode";
    private static final String MAINTENANCE_MESSAGE_URL = SUPABASE_URL + "/rest/v1/app_config?key=eq.maintenance_message";
    private final BooleanProperty profileLoaded = new SimpleBooleanProperty(false);
    public ImageView levelSelectProfileView;
    private ImageView settingsProfileView;
    private static final String LEADERBOARD_POST = LEADERBOARD_API_POST;
    private static final String LEADERBOARD_GET = LEADERBOARD_API_GET;
    private static final String TOKEN_FILE = "resources/token.dat";  // Encrypted file
    private static final String ENCRYPTION_KEY = "ChromaFlood2025!"; // 16 chars = AES-128
    public Image currentProfileImage = null;
    private Timeline profileRealtimeSync;
    public final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final Gson gson = new Gson();
    public final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObservableList<PlayerData> leaderboardData = FXCollections.observableArrayList();
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
    private Image spriteCharacter;
    private long lastChatTime = 0; // Track last chat interaction time
    private static final long CHAT_COOLDOWN_MS = 2000;
    private boolean isSpriteVisible = true;
    private StackPane spriteContainer = null;
    private static final String SPRITE_FOLDER = "resources/sprites";
    private static final String SPRITE_CHARACTER_FILE = "character_sprite.gif";
    private static final String SPRITE_CHARACTER_URL = "https://drive.google.com/uc?export=download&id=1V4Qnp1pqjrYJurSNVQqZpkZq6J68uCka";
    private BooleanProperty isFullScreen = new SimpleBooleanProperty(false); // Track full-screen state
    public BorderPane root; // Single root for all UI
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
            "https://drive.google.com/uc?export=download&id=1WDcNa7wBN9FBNmiGJPRwnLXH06LWHplU", // Tile click
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
    private boolean isEffectsMuted = false; // For click sounds (SFX)
    private boolean isMusicMuted = false; // For background music
    private double audioVolume = 1.0; // Default volume (1.0 is full volume)
    private long levelStartTime;
    private static java.nio.channels.FileLock instanceLock = null;
    private static java.io.RandomAccessFile lockFile = null;


    @FunctionalInterface
    interface DialogCallback {
        void onResult(boolean confirmed, Stage dialogStage);
    }

    @FunctionalInterface
    interface TextInputCallback {
        void onResult(String inputText, Stage dialogStage);
    }

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
        saveProgressToSupabase();   // ← THIS IS THE ONLY LINE YOU NEED NOW
    }

    public void loadOnlineLeaderboard(Consumer<List<PlayerData>> callback) {
        executor.submit(() -> {
            try {
                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(LEADERBOARD_API))
                        .GET()
                        .build();

                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                Type type = new TypeToken<List<Map<String, Object>>>() {
                }.getType();
                List<Map<String, Object>> data = gson.fromJson(response.body(), type);

                List<PlayerData> players = data.stream().map(row -> {
                    String user = (String) row.get("username");
                    int lvl = ((Double) row.get("level")).intValue();
                    double tm = (Double) row.get("completion_time");
                    return new PlayerData(user, lvl, tm, null);
                }).sorted((a, b) -> {
                    if (a.highestLevel != b.highestLevel) return Integer.compare(b.highestLevel, a.highestLevel);
                    return Double.compare(a.totalCompletionTime, b.totalCompletionTime);
                }).collect(Collectors.toList());

                Platform.runLater(() -> callback.accept(players));

            } catch (Exception e) {
                Platform.runLater(() -> new Alert(AlertType.ERROR, "Failed to load online leaderboard").show());
            }
        });
    }

    private void showCompletionScreen() {
        root.getChildren().clear();
        root.setStyle("-fx-background-color: radial-gradient(center 50% 50%, radius 80%, #1C2526, #0A0E26);");
        primaryStage.setTitle("Chroma Flood - Puzzle Complete");

        Label title = new Label("Puzzle Complete!");
        title.setStyle("-fx-text-fill: #FFFFFF; -fx-font-family: 'Arial Black'; -fx-font-size: 36; -fx-font-weight: bold; -fx-effect: dropshadow(gaussian, #00FFFF, 10, 0.5, 0, 0);");

        Label scoreLabel = new Label("Calculating final score...");
        scoreLabel.setStyle("-fx-text-fill: #00FFFF; -fx-font-family: 'Arial Black'; -fx-font-size: 24; -fx-font-weight: bold; -fx-effect: dropshadow(gaussian, #000000, 5, 0.5, 0, 0);");

        Button continueButton = new Button("Continue");
        continueButton.setStyle("-fx-background-color: linear-gradient(to bottom, #44FF44, #00CC00); -fx-text-fill: #FFFFFF; -fx-font-family: 'Arial Black'; -fx-font-size: 18; -fx-font-weight: bold; -fx-background-radius: 20; -fx-padding: 15 30; -fx-effect: dropshadow(gaussian, #000000, 8, 0.5, 0, 0);");
        continueButton.setOnAction(event -> showLevelSelectScreen());
        continueButton.setOnMouseEntered(e -> continueButton.setStyle("-fx-background-color: linear-gradient(to bottom, #66FF66, #22EE22); -fx-text-fill: #FFFFFF; -fx-font-family: 'Arial Black'; -fx-font-size: 18; -fx-font-weight: bold; -fx-background-radius: 20; -fx-padding: 15 30; -fx-effect: dropshadow(gaussian, #000000, 10, 0.7, 0, 0);"));
        continueButton.setOnMouseExited(e -> continueButton.setStyle("-fx-background-color: linear-gradient(to bottom, #44FF44, #00CC00); -fx-text-fill: #FFFFFF; -fx-font-family: 'Arial Black'; -fx-font-size: 18; -fx-font-weight: bold; -fx-background-radius: 20; -fx-padding: 15 30; -fx-effect: dropshadow(gaussian, #000000, 8, 0.5, 0, 0);"));

        VBox container = new VBox(30, title, scoreLabel, continueButton);
        container.setAlignment(Pos.CENTER);
        root.setCenter(container);

        // FETCH TOTAL TIME FROM SUPABASE
        executor.submit(() -> {
            try {
                String url = SUPABASE_URL + "/rest/v1/leaderboard" +
                        "?username=eq." + URLEncoder.encode(currentUser, StandardCharsets.UTF_8) +
                        "&select=completion_time";

                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(url))
                        .header("apikey", SUPABASE_ANON_KEY)
                        .header("Authorization", "Bearer " + SUPABASE_ANON_KEY)
                        .GET()
                        .build();

                HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

                if (response.statusCode() == 200) {
                    JsonArray records = gson.fromJson(response.body(), JsonArray.class);
                    double totalTime = 0.0;
                    for (JsonElement elem : records) {
                        JsonObject obj = elem.getAsJsonObject();
                        if (obj.has("completion_time")) {
                            totalTime += obj.get("completion_time").getAsDouble();
                        }
                    }

                    double finalTime = totalTime;
                    Platform.runLater(() -> scoreLabel.setText(
                            String.format("Final Score: %.2f seconds", finalTime)
                    ));
                } else {
                    Platform.runLater(() -> scoreLabel.setText("Final Score: Offline / Unknown"));
                }
            } catch (Exception e) {
                System.err.println("Failed to fetch total time from Supabase: " + e.getMessage());
                Platform.runLater(() -> scoreLabel.setText("Final Score: Connection Error"));
            }
        });
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

    private void submitOnlineScore(String username, int level, double completionTime) {
        if (username == null || username.isEmpty() || "Guest".equals(username)) return;

        executor.submit(() -> {
            try {
                Map<String, Object> body = new HashMap<>();
                body.put("username", username);
                body.put("level", level);
                body.put("completion_time", completionTime);

                String json = gson.toJson(body);

                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(LEADERBOARD_POST)) // ends with ?on_conflict=username,level
                        .header("apikey", SUPABASE_ANON_KEY)
                        .header("Authorization", "Bearer " + SUPABASE_ANON_KEY)
                        .header("Content-Type", "application/json")
                        .header("Prefer", "resolution=merge-duplicates")  // this + unique index = upsert
                        .POST(HttpRequest.BodyPublishers.ofString(json))
                        .timeout(java.time.Duration.ofSeconds(10))
                        .build();

                HttpResponse<String> resp = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

                if (resp.statusCode() == 201 || resp.statusCode() == 200) {
                    System.out.println("Score submitted: " + username + " → Level " + level + " in " + completionTime + "s");
                    // Immediate refresh
                    Platform.runLater(() -> refreshLeaderboardData());
                } else {
                    System.err.println("Submit failed HTTP " + resp.statusCode() + ": " + resp.body());
                }
            } catch (Exception e) {
                System.err.println("Exception submitting score: " + e.getMessage());
            }
        });
    }

    private void startMaintenanceMonitoring() {
        if (maintenanceCheckTimer != null) {
            maintenanceCheckTimer.stop();
        }

        // Do first check immediately
        if (!isCheckingMaintenance.get() && !maintenanceDialogShown) {
            checkMaintenanceStatus();
        }

        // Then schedule periodic checks every 60 seconds
        maintenanceCheckTimer = new Timeline(new KeyFrame(
                Duration.seconds(MAINTENANCE_CHECK_INTERVAL_SECONDS),
                event -> {
                    if (!isCheckingMaintenance.get() && !maintenanceDialogShown) {
                        checkMaintenanceStatus();
                    }
                }
        ));
        maintenanceCheckTimer.setCycleCount(Timeline.INDEFINITE);
        maintenanceCheckTimer.play();
    }

    private void checkMaintenanceStatus() {
        // Prevent concurrent checks
        if (!isCheckingMaintenance.compareAndSet(false, true)) {
            return;
        }

        // Use cached thread pool executor (non-blocking)
        CompletableFuture.runAsync(() -> {
            try {
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(MAINTENANCE_CHECK_URL))
                        .header("apikey", SUPABASE_ANON_KEY)
                        .header("Authorization", "Bearer " + SUPABASE_ANON_KEY)
                        .timeout(java.time.Duration.ofSeconds(5)) // Shorter timeout
                        .GET()
                        .build();

                HttpResponse<String> response = httpClient.send(request,
                        HttpResponse.BodyHandlers.ofString());

                if (response.statusCode() == 200) {
                    JsonArray array = gson.fromJson(response.body(), JsonArray.class);
                    if (array.size() > 0) {
                        JsonObject config = array.get(0).getAsJsonObject();
                        boolean isMaintenanceMode = config.get("value").getAsString().equalsIgnoreCase("true");

                        if (isMaintenanceMode && !maintenanceDialogShown) {
                            // Check if current user is admin
                            boolean isAdmin = currentUser != null && currentUser.equalsIgnoreCase(ADMIN_USERNAME);

                            if (!isAdmin) {
                                // Kick out non-admin users
                                String message = getMaintenanceMessage();
                                Platform.runLater(() -> {
                                    maintenanceDialogShown = true;
                                    // Stop the maintenance checker
                                    if (maintenanceCheckTimer != null) {
                                        maintenanceCheckTimer.stop();
                                    }
                                    // Show maintenance dialog
                                    showMaintenanceDialog(message);
                                });
                            } else {
                                System.out.println("Maintenance mode active but admin access granted");
                            }
                        }
                    }
                }
            } catch (Exception e) {
                // Silent fail - don't interrupt gameplay
                System.err.println("Maintenance check failed (non-critical): " + e.getMessage());
            } finally {
                isCheckingMaintenance.set(false);
            }
        }, executor);
    }

    private void checkMaintenanceBeforeLogin(Runnable onSuccess) {
        executor.submit(() -> {
            try {
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(MAINTENANCE_CHECK_URL))
                        .header("apikey", SUPABASE_ANON_KEY)
                        .header("Authorization", "Bearer " + SUPABASE_ANON_KEY)
                        .timeout(java.time.Duration.ofSeconds(5))
                        .GET()
                        .build();

                HttpResponse<String> response = httpClient.send(request,
                        HttpResponse.BodyHandlers.ofString());

                if (response.statusCode() == 200) {
                    JsonArray array = gson.fromJson(response.body(), JsonArray.class);
                    if (array.size() > 0) {
                        JsonObject config = array.get(0).getAsJsonObject();
                        boolean isMaintenanceMode = config.get("value").getAsString().equalsIgnoreCase("true");

                        if (isMaintenanceMode) {
                            String message = getMaintenanceMessage();
                            Platform.runLater(() -> {
                                hideLoadingScreen();
                                isNavigating = false; // CRITICAL: Reset navigation flag
                                showMaintenanceDialog(message);
                            });
                            return;
                        }
                    }
                }

                // No maintenance, proceed
                Platform.runLater(onSuccess);

            } catch (Exception e) {
                System.err.println("Maintenance check failed: " + e.getMessage());
                // Fail-open: allow login attempt
                Platform.runLater(onSuccess);
            }
        });
    }

    private void stopMaintenanceMonitoring() {
        if (maintenanceCheckTimer != null) {
            maintenanceCheckTimer.stop();
            maintenanceCheckTimer = null;
        }
    }

    private void checkAppVersion(Runnable onSuccess) {
        executor.submit(() -> {
            try {
                // First check maintenance mode
                HttpRequest maintenanceRequest = HttpRequest.newBuilder()
                        .uri(URI.create(MAINTENANCE_CHECK_URL))
                        .header("apikey", SUPABASE_ANON_KEY)
                        .header("Authorization", "Bearer " + SUPABASE_ANON_KEY)
                        .timeout(java.time.Duration.ofSeconds(10))
                        .GET()
                        .build();

                HttpResponse<String> maintenanceResponse = httpClient.send(maintenanceRequest,
                        HttpResponse.BodyHandlers.ofString());

                if (maintenanceResponse.statusCode() == 200) {
                    JsonArray maintenanceArray = gson.fromJson(maintenanceResponse.body(), JsonArray.class);
                    if (maintenanceArray.size() > 0) {
                        JsonObject config = maintenanceArray.get(0).getAsJsonObject();
                        boolean isMaintenanceMode = config.get("value").getAsString().equalsIgnoreCase("true");

                        if (isMaintenanceMode) {
                            // Check if current user is admin
                            String savedUser = loadLoginToken();
                            boolean isAdmin = savedUser != null && savedUser.equalsIgnoreCase(ADMIN_USERNAME);

                            if (!isAdmin) {
                                // Get maintenance message
                                String message = getMaintenanceMessage();
                                Platform.runLater(() -> showMaintenanceDialog(message));
                                return;
                            } else {
                                System.out.println("Admin access granted during maintenance mode");
                            }
                        }
                    }
                }

                // Then check version
                HttpRequest versionRequest = HttpRequest.newBuilder()
                        .uri(URI.create(VERSION_CHECK_URL))
                        .header("apikey", SUPABASE_ANON_KEY)
                        .header("Authorization", "Bearer " + SUPABASE_ANON_KEY)
                        .timeout(java.time.Duration.ofSeconds(10))
                        .GET()
                        .build();

                HttpResponse<String> versionResponse = httpClient.send(versionRequest,
                        HttpResponse.BodyHandlers.ofString());

                if (versionResponse.statusCode() == 200) {
                    JsonArray array = gson.fromJson(versionResponse.body(), JsonArray.class);
                    if (array.size() > 0) {
                        JsonObject config = array.get(0).getAsJsonObject();
                        String minVersion = config.get("value").getAsString();

                        if (isVersionOutdated(APP_VERSION, minVersion)) {
                            Platform.runLater(() -> showUpdateRequiredDialog());
                            return;
                        }
                    }
                }

                // Everything OK, continue
                Platform.runLater(onSuccess);

            } catch (Exception e) {
                System.err.println("Version/Maintenance check failed: " + e.getMessage());
                // Fail-open approach - allow play if checks fail
                Platform.runLater(onSuccess);
            }
        });
    }

    private String getMaintenanceMessage() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(MAINTENANCE_MESSAGE_URL))
                    .header("apikey", SUPABASE_ANON_KEY)
                    .header("Authorization", "Bearer " + SUPABASE_ANON_KEY)
                    .timeout(java.time.Duration.ofSeconds(10))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request,
                    HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                JsonArray array = gson.fromJson(response.body(), JsonArray.class);
                if (array.size() > 0) {
                    JsonObject config = array.get(0).getAsJsonObject();
                    return config.get("value").getAsString();
                }
            }
        } catch (Exception e) {
            System.err.println("Failed to fetch maintenance message: " + e.getMessage());
        }

        return "Chroma Flood is currently under maintenance.\n\nWe're updating the game to bring you new features and improvements.\n\nPlease check back in a few minutes!";
    }

    private void showMaintenanceDialog(String message) {
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initStyle(StageStyle.UNDECORATED);
        dialog.setTitle("Maintenance Mode");

        VBox content = new VBox(20);
        content.setAlignment(Pos.CENTER);
        content.setPadding(new Insets(40));
        content.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #1a1a2e, #0f0f1e);" +
                        "-fx-effect: dropshadow(gaussian, rgba(255, 165, 0, 0.6), 30, 0.6, 0, 0);"
        );

        Label icon = new Label("🔧");
        icon.setStyle("-fx-font-size: 64;");

        RotateTransition rotate = new RotateTransition(Duration.seconds(2), icon);
        rotate.setByAngle(360);
        rotate.setCycleCount(Animation.INDEFINITE);
        rotate.play();

        Label title = new Label("UNDER MAINTENANCE");
        title.setStyle(
                "-fx-font-size: 28; -fx-font-weight: bold; " +
                        "-fx-text-fill: #ffa500; -fx-font-family: 'Arial Black';"
        );

        VBox messageContainer = new VBox(10);
        messageContainer.setAlignment(Pos.CENTER);
        messageContainer.setMaxWidth(550);
        messageContainer.setPrefHeight(Region.USE_COMPUTED_SIZE);

        Label messageLabel = new Label(message);
        messageLabel.setStyle(
                "-fx-font-size: 16; -fx-text-fill: #ffffff; " +
                        "-fx-text-alignment: center; -fx-line-spacing: 5px;"
        );
        messageLabel.setWrapText(true);
        messageLabel.setMaxWidth(550);
        messageLabel.setAlignment(Pos.CENTER);

        VBox messagePadding = new VBox(messageLabel);
        messagePadding.setPadding(new Insets(10, 20, 10, 20));
        messagePadding.setAlignment(Pos.CENTER);

        ScrollPane messageScroll = new ScrollPane(messagePadding);
        messageScroll.setFitToWidth(true);
        messageScroll.setMaxHeight(200);
        messageScroll.setPrefViewportHeight(Region.USE_COMPUTED_SIZE);
        messageScroll.setStyle(
                "-fx-background: transparent; " +
                        "-fx-background-color: transparent; " +
                        "-fx-border-color: transparent;"
        );
        messageScroll.setPannable(true);
        messageScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        messageScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        messageContainer.getChildren().add(messageScroll);

        ProgressIndicator progress = new ProgressIndicator();
        progress.setStyle("-fx-progress-color: #ffa500;");
        progress.setPrefSize(40, 40);

        Label statusLabel = new Label("We'll be back soon!");
        statusLabel.setStyle(
                "-fx-font-size: 14; -fx-text-fill: #aaaaaa; " +
                        "-fx-font-style: italic;"
        );

        Button retryBtn = new Button("Retry Connection");
        String retryBtnBaseStyle = "-fx-background-color: linear-gradient(to bottom, #ffa500, #ff8c00); " +
                "-fx-text-fill: white; -fx-font-weight: bold; " +
                "-fx-font-size: 15; -fx-padding: 12 35; " +
                "-fx-background-radius: 10; -fx-cursor: hand;";

        String retryBtnHoverStyle = "-fx-background-color: linear-gradient(to bottom, #ffb732, #ffa500); " +
                "-fx-text-fill: white; -fx-font-weight: bold; " +
                "-fx-font-size: 15; -fx-padding: 12 35; " +
                "-fx-background-radius: 10; -fx-cursor: hand; " +
                "-fx-effect: dropshadow(gaussian, rgba(255, 165, 0, 0.8), 15, 0.5, 0, 0);";

        retryBtn.setStyle(retryBtnBaseStyle);
        retryBtn.setOnMouseEntered(e -> retryBtn.setStyle(retryBtnHoverStyle));
        retryBtn.setOnMouseExited(e -> retryBtn.setStyle(retryBtnBaseStyle));
        retryBtn.setOnAction(e -> {
            dialog.close();
            maintenanceDialogShown = false;

            // CRITICAL: Reset navigation flag when retrying
            isNavigating = false;

            showLoadingScreen();
            checkAppVersion(() -> {
                cacheDefaultProfilePic();
                updateImageResources();
                downloadAudioResources();
                downloadImageResources();

                level1Image = new Image(new File(IMAGE_FOLDER + File.separator + IMAGE_FILES[0]).toURI().toString());
                level2Image = new Image(new File(IMAGE_FOLDER + File.separator + IMAGE_FILES[1]).toURI().toString());
                level3Image = new Image(new File(IMAGE_FOLDER + File.separator + IMAGE_FILES[2]).toURI().toString());
                level4Image = new Image(new File(IMAGE_FOLDER + File.separator + IMAGE_FILES[3]).toURI().toString());
                level5Image = new Image(new File(IMAGE_FOLDER + File.separator + IMAGE_FILES[4]).toURI().toString());
                level6Image = new Image(new File(IMAGE_FOLDER + File.separator + IMAGE_FILES[5]).toURI().toString());

                checkSessionAndStart();
            });
        });

        Button exitBtn = new Button("Exit");
        String exitBtnBaseStyle = "-fx-background-color: #666666; -fx-text-fill: white; " +
                "-fx-font-weight: bold; -fx-font-size: 14; " +
                "-fx-padding: 10 28; -fx-background-radius: 10; -fx-cursor: hand;";

        String exitBtnHoverStyle = "-fx-background-color: #777777; -fx-text-fill: white; " +
                "-fx-font-weight: bold; -fx-font-size: 14; " +
                "-fx-padding: 10 28; -fx-background-radius: 10; -fx-cursor: hand;";

        exitBtn.setStyle(exitBtnBaseStyle);
        exitBtn.setOnMouseEntered(e -> exitBtn.setStyle(exitBtnHoverStyle));
        exitBtn.setOnMouseExited(e -> exitBtn.setStyle(exitBtnBaseStyle));
        exitBtn.setOnAction(e -> Platform.exit());

        HBox buttons = new HBox(15, retryBtn, exitBtn);
        buttons.setAlignment(Pos.CENTER);

        content.getChildren().addAll(icon, title, messageContainer, progress, statusLabel, buttons);

        Scene scene = new Scene(content, 650, 550);
        scene.setFill(Color.TRANSPARENT);
        dialog.setScene(scene);

        dialog.setOnCloseRequest(event -> event.consume());

        // CRITICAL: When dialog closes (via Exit or any other means), reset navigation
        dialog.setOnHidden(e -> {
            isNavigating = false;
            System.out.println("[MAINTENANCE] Dialog closed, navigation flag reset");
        });

        dialog.show();
    }

    private boolean isVersionOutdated(String current, String minimum) {
        String[] currentParts = current.split("\\.");
        String[] minParts = minimum.split("\\.");

        // Compare MAJOR version first
        int currentMajor = Integer.parseInt(currentParts[0]);
        int minMajor = Integer.parseInt(minParts[0]);

        // Block if client is from different major version (optional)
        // if (currentMajor != minMajor) return true;

        // Standard comparison (your current implementation)
        for (int i = 0; i < Math.min(currentParts.length, minParts.length); i++) {
            int c = Integer.parseInt(currentParts[i]);
            int m = Integer.parseInt(minParts[i]);
            if (c < m) return true;  // Too old
            if (c > m) return false; // Newer is OK
        }
        return false;
    }

    private void showUpdateRequiredDialog() {
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initStyle(StageStyle.UNDECORATED);
        dialog.setTitle("Update Required");

        VBox content = new VBox(20);
        content.setAlignment(Pos.CENTER);
        content.setPadding(new Insets(40));
        content.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #1a1a2e, #0f0f1e);" +
                        "-fx-effect: dropshadow(gaussian, rgba(255, 0, 0, 0.6), 30, 0.6, 0, 0);"
        );

        Label icon = new Label("⚠");
        icon.setStyle("-fx-font-size: 64; -fx-text-fill: #ff4444;");

        Label title = new Label("UPDATE REQUIRED");
        title.setStyle(
                "-fx-font-size: 28; -fx-font-weight: bold; " +
                        "-fx-text-fill: #ff4444; -fx-font-family: 'Arial Black';"
        );

        // Scrollable message container
        VBox messageContainer = new VBox(10);
        messageContainer.setAlignment(Pos.CENTER);
        messageContainer.setMaxWidth(550);
        messageContainer.setPrefHeight(Region.USE_COMPUTED_SIZE);

        Label message = new Label(
                "Your version of Chroma Flood is outdated.\n\n" +
                        "Please download the latest version to continue playing.\n\n" +
                        "Current Version: " + APP_VERSION
        );
        message.setStyle(
                "-fx-font-size: 16; -fx-text-fill: #ffffff; " +
                        "-fx-text-alignment: center; -fx-line-spacing: 5px;"
        );
        message.setWrapText(true);
        message.setMaxWidth(550);
        message.setAlignment(Pos.CENTER);

        // Add padding around the message
        VBox messagePadding = new VBox(message);
        messagePadding.setPadding(new Insets(10, 20, 10, 20));
        messagePadding.setAlignment(Pos.CENTER);

        ScrollPane messageScroll = new ScrollPane(messagePadding);
        messageScroll.setFitToWidth(true);
        messageScroll.setMaxHeight(180);
        messageScroll.setPrefViewportHeight(Region.USE_COMPUTED_SIZE);
        messageScroll.setStyle(
                "-fx-background: transparent; " +
                        "-fx-background-color: transparent; " +
                        "-fx-border-color: transparent;"
        );
        messageScroll.setPannable(true);
        messageScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        messageScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        messageContainer.getChildren().add(messageScroll);

        Button downloadBtn = new Button("Download Update");
        String downloadBtnBaseStyle = "-fx-background-color: linear-gradient(to bottom, #ff4444, #cc0000); " +
                "-fx-text-fill: white; -fx-font-weight: bold; " +
                "-fx-font-size: 15; -fx-padding: 12 35; " +
                "-fx-background-radius: 10; -fx-cursor: hand;";

        String downloadBtnHoverStyle = "-fx-background-color: linear-gradient(to bottom, #ff6666, #ff4444); " +
                "-fx-text-fill: white; -fx-font-weight: bold; " +
                "-fx-font-size: 15; -fx-padding: 12 35; " +
                "-fx-background-radius: 10; -fx-cursor: hand; " +
                "-fx-effect: dropshadow(gaussian, rgba(255, 68, 68, 0.8), 15, 0.5, 0, 0);";

        downloadBtn.setStyle(downloadBtnBaseStyle);
        downloadBtn.setOnMouseEntered(e -> downloadBtn.setStyle(downloadBtnHoverStyle));
        downloadBtn.setOnMouseExited(e -> downloadBtn.setStyle(downloadBtnBaseStyle));
        downloadBtn.setOnAction(e -> {
            try {
                // Open download page in browser
                java.awt.Desktop.getDesktop().browse(
                        new URI("https://drive.google.com/file/d/1lROOrJCND-o8UN8ChUhqKmziVb0WazeC/view") // Download link for new version
                );
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            Platform.exit();
        });

        Button exitBtn = new Button("Exit");
        String exitBtnBaseStyle = "-fx-background-color: #666666; -fx-text-fill: white; " +
                "-fx-font-weight: bold; -fx-font-size: 14; " +
                "-fx-padding: 10 28; -fx-background-radius: 10; -fx-cursor: hand;";

        String exitBtnHoverStyle = "-fx-background-color: #777777; -fx-text-fill: white; " +
                "-fx-font-weight: bold; -fx-font-size: 14; " +
                "-fx-padding: 10 28; -fx-background-radius: 10; -fx-cursor: hand;";

        exitBtn.setStyle(exitBtnBaseStyle);
        exitBtn.setOnMouseEntered(e -> exitBtn.setStyle(exitBtnHoverStyle));
        exitBtn.setOnMouseExited(e -> exitBtn.setStyle(exitBtnBaseStyle));
        exitBtn.setOnAction(e -> Platform.exit());

        HBox buttons = new HBox(15, downloadBtn, exitBtn);
        buttons.setAlignment(Pos.CENTER);

        content.getChildren().addAll(icon, title, messageContainer, buttons);

        Scene scene = new Scene(content, 650, 500);
        scene.setFill(Color.TRANSPARENT);
        dialog.setScene(scene);

        // Prevent closing
        dialog.setOnCloseRequest(event -> event.consume());

        dialog.show();
    }

    @Override
    public void start(Stage primaryStage) {
        // Check for existing instance FIRST
        if (!acquireInstanceLock()) {
            showAlreadyRunningDialog();
            Platform.exit();
            return;
        }

        this.primaryStage = primaryStage;

        root = new BorderPane();
        Scene scene = new Scene(root, 1000, 800);

        // Key event handlers
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

        // Disable fullscreen
        primaryStage.setFullScreen(false);
        primaryStage.setResizable(true);
        primaryStage.setFullScreenExitHint("");

        primaryStage.fullScreenProperty().addListener((obs, wasFull, isFull) -> {
            if (isFull) {
                Platform.runLater(() -> primaryStage.setFullScreen(false));
            }
        });

        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.F11) {
                event.consume();
            }
        });

        scene.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.isAltDown() && event.getCode() == KeyCode.ENTER) {
                event.consume();
            }
        });

        // Show loading screen
        showLoadingScreen();

        // Start with version check, which will handle everything else
        checkAppVersion(() -> {
            // Only proceed if version is OK
            // Initialize resources ONCE
            cacheDefaultProfilePic();
            updateImageResources();
            downloadAudioResources();
            downloadImageResources();

            // Load level images
            level1Image = new Image(new File(IMAGE_FOLDER + File.separator + IMAGE_FILES[0]).toURI().toString());
            level2Image = new Image(new File(IMAGE_FOLDER + File.separator + IMAGE_FILES[1]).toURI().toString());
            level3Image = new Image(new File(IMAGE_FOLDER + File.separator + IMAGE_FILES[2]).toURI().toString());
            level4Image = new Image(new File(IMAGE_FOLDER + File.separator + IMAGE_FILES[3]).toURI().toString());
            level5Image = new Image(new File(IMAGE_FOLDER + File.separator + IMAGE_FILES[4]).toURI().toString());
            level6Image = new Image(new File(IMAGE_FOLDER + File.separator + IMAGE_FILES[5]).toURI().toString());

            // Check session and start
            checkSessionAndStart();
        });

        startConnectionMonitoring();

        primaryStage.show();
    }

    private void checkSessionAndStart() {
        Task<Void> sessionTask = new Task<>() {
            @Override
            protected Void call() throws Exception {
                Thread.sleep(2000);
                return null;
            }
        };

        sessionTask.setOnSucceeded(event -> Platform.runLater(() -> {
            String savedUser = loadLoginToken();

            if (savedUser != null && !savedUser.isEmpty()) {
                currentUser = savedUser;

                loadFullProfileFromSupabase(savedUser, () -> {
                    profileLoaded.set(true);
                    decidePostLoginScreen();

                    // Start monitoring after login (skip for admin)
                    if (!savedUser.equalsIgnoreCase(ADMIN_USERNAME)) {
                        startMaintenanceMonitoring();
                    }
                });
            } else {
                // No saved session - show login screen
                showLoginScreen();

                // ADD THIS: Start monitoring on login screen
                startMaintenanceMonitoring();
            }
        }));

        sessionTask.setOnFailed(event -> {
            Platform.runLater(() -> {
                System.err.println("Session check failed: " + sessionTask.getException().getMessage());
                showLoginScreen();

                // ADD THIS: Start monitoring on login screen (in case of failure too)
                startMaintenanceMonitoring();
            });
        });

        new Thread(sessionTask).start();

        startConnectionMonitoring();
    }

    @Override
    public void stop() {
        System.out.println("Application shutting down...");

        stopMaintenanceMonitoring();
        if (connectionCheckTimer != null) {
            connectionCheckTimer.stop();
        }
        // ADD THIS: Stop reconnect checker
        if (reconnectCheckTimer != null) {
            reconnectCheckTimer.stop();
        }
        if (executor != null && !executor.isShutdown()) {
            executor.shutdown();
        }
        // 1. Release instance lock
        releaseInstanceLock();

        // 2. Stop connection checker
        if (connectionCheckTimer != null) {
            connectionCheckTimer.stop();
        }

        // 2. Shutdown executor service
        if (executor != null && !executor.isShutdown()) {
            executor.shutdownNow();
            System.out.println("Executor service shut down");
        }

        // 2. Stop all media players
        stopAllAudio();

        // 3. Cancel any running tasks
        if (leaderboardProfilePreloadTask != null && leaderboardProfilePreloadTask.isRunning()) {
            leaderboardProfilePreloadTask.cancel();
        }

        // 4. Stop profile sync timeline
        if (profileRealtimeSync != null) {
            profileRealtimeSync.stop();
        }

        // 5. Close all open stages
        if (audioSettingsStage != null) audioSettingsStage.close();
        if (banAppealsStage != null) banAppealsStage.close();
        if (profileSettingsStage != null) profileSettingsStage.close();
        if (userManagementStage != null) userManagementStage.close();
        if (leaderboardStage != null) leaderboardStage.close();
        // ADD THIS: Close connection dialog
        if (connectionLostStage != null) connectionLostStage.close();

        System.out.println("Application cleanup complete");
    }

    private boolean acquireInstanceLock() {
        try {
            // Create lock file in system temp directory
            File lockFileLocation = new File(System.getProperty("java.io.tmpdir"), "chromaflood.lock");
            lockFile = new java.io.RandomAccessFile(lockFileLocation, "rw");

            // Try to acquire exclusive lock (non-blocking)
            java.nio.channels.FileChannel channel = lockFile.getChannel();
            instanceLock = channel.tryLock();

            if (instanceLock == null) {
                // Lock couldn't be acquired - another instance is running
                lockFile.close();
                return false;
            }

            System.out.println("Instance lock acquired successfully");
            return true;

        } catch (Exception e) {
            System.err.println("Error acquiring instance lock: " + e.getMessage());
            return false;
        }
    }

    private void releaseInstanceLock() {
        try {
            if (instanceLock != null && instanceLock.isValid()) {
                instanceLock.release();
                System.out.println("Instance lock released");
            }
            if (lockFile != null) {
                lockFile.close();
            }
        } catch (Exception e) {
            System.err.println("Error releasing instance lock: " + e.getMessage());
        }
    }

    private void showAlreadyRunningDialog() {
        try {
            // Create a minimal stage for the alert
            Stage alertStage = new Stage();
            alertStage.initStyle(StageStyle.UTILITY);

            VBox content = new VBox(25);
            content.setAlignment(Pos.CENTER);
            content.setPadding(new Insets(40));
            content.setStyle("-fx-background-color: linear-gradient(to bottom, #1a0a0a, #2a1a1a);");

            // Warning icon
            Label iconLabel = new Label("⚠");
            iconLabel.setStyle("-fx-font-size: 64; -fx-text-fill: #ff9900;");

            DropShadow glow = new DropShadow();
            glow.setColor(Color.rgb(255, 153, 0, 0.8));
            glow.setRadius(30);
            iconLabel.setEffect(glow);

            Label titleLabel = new Label("CHROMA FLOOD ALREADY RUNNING");
            titleLabel.setStyle("-fx-font-size: 24; -fx-font-weight: bold; -fx-text-fill: #ff9900;");

            Label messageLabel = new Label(
                    "Another instance of Chroma Flood is already running.\n\n" +
                            "Please close the existing instance before starting a new one.\n\n" +
                            "This prevents data conflicts and ensures proper game performance."
            );
            messageLabel.setStyle("-fx-font-size: 14; -fx-text-fill: #ffffff; -fx-text-alignment: center;");
            messageLabel.setWrapText(true);
            messageLabel.setMaxWidth(450);
            messageLabel.setAlignment(Pos.CENTER);

            Button okButton = new Button("OK");
            okButton.setPrefWidth(150);
            okButton.setPrefHeight(40);
            okButton.setStyle(
                    "-fx-background-color: linear-gradient(to bottom, #ff9900, #cc7700); " +
                            "-fx-text-fill: #ffffff; -fx-font-weight: bold; -fx-font-size: 14; " +
                            "-fx-background-radius: 10; -fx-cursor: hand;"
            );
            okButton.setOnAction(e -> {
                alertStage.close();
                Platform.exit();
            });

            content.getChildren().addAll(iconLabel, titleLabel, messageLabel, okButton);

            Scene scene = new Scene(content, 550, 500);
            alertStage.setScene(scene);
            alertStage.initStyle(StageStyle.UNDECORATED);
            alertStage.setTitle("Already Running");
            alertStage.centerOnScreen();

            // Make sure this dialog stays on top
            alertStage.setAlwaysOnTop(true);

            alertStage.showAndWait();

        } catch (Exception e) {
            // Fallback to console message if dialog fails
            System.err.println("Another instance of Chroma Flood is already running!");
            System.err.println("Please close the existing instance first.");
        }
    }


    private void stopAllAudio() {
        try {
            if (clickSound != null) clickSound.stop();

            MediaPlayer[] players = {
                    progressionPathAudio, level1Audio, level2Audio, level3Audio,
                    level4Audio, level5Audio, level6Audio, level7Audio,
                    level8Audio, level9Audio, level10Audio, currentBackgroundAudio
            };

            for (MediaPlayer player : players) {
                if (player != null) {
                    player.stop();
                    player.dispose();
                }
            }
            System.out.println("All audio resources stopped");
        } catch (Exception e) {
            System.err.println("Error stopping audio: " + e.getMessage());
        }
    }

    private void startConnectionMonitoring() {
        // Check every 3 seconds for fast detection (industry standard for active gameplay)
        connectionCheckTimer = new Timeline(new KeyFrame(Duration.seconds(3), e -> {
            // Run check in background thread to avoid blocking UI
            CompletableFuture.runAsync(this::checkInternetConnection, executor);
        }));
        connectionCheckTimer.setCycleCount(Timeline.INDEFINITE);
        connectionCheckTimer.play();

        // Initial immediate check
        CompletableFuture.runAsync(this::checkInternetConnection, executor);
    }

    public void ensureConnected(Runnable onSuccess) {
        if (!isOnline) {
            Platform.runLater(this::handleConnectionLost);
            return;
        }

        // Quick check, then proceed
        CompletableFuture.runAsync(() -> {
            try {
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(SUPABASE_URL + "/rest/v1/"))
                        .header("apikey", SUPABASE_ANON_KEY)
                        .timeout(java.time.Duration.ofSeconds(2))
                        .method("HEAD", HttpRequest.BodyPublishers.noBody())
                        .build();

                HttpResponse<Void> response = httpClient.send(request, HttpResponse.BodyHandlers.discarding());
                boolean online = (response.statusCode() >= 200 && response.statusCode() < 500);

                if (online) {
                    Platform.runLater(onSuccess);
                } else {
                    isOnline = false;
                    Platform.runLater(this::handleConnectionLost);
                }
            } catch (Exception e) {
                isOnline = false;
                Platform.runLater(this::handleConnectionLost);
            }
        }, executor);
    }

    private void checkInternetConnection() {
        try {
            // Quick lightweight check - just test if Supabase is reachable
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(SUPABASE_URL + "/rest/v1/"))
                    .header("apikey", SUPABASE_ANON_KEY)
                    .timeout(java.time.Duration.ofSeconds(3))  // Short timeout
                    .method("HEAD", HttpRequest.BodyPublishers.noBody())
                    .build();

            HttpResponse<Void> response = httpClient.send(request, HttpResponse.BodyHandlers.discarding());

            boolean nowOnline = (response.statusCode() >= 200 && response.statusCode() < 500);

            // Only react if status changed
            if (isOnline && !nowOnline) {
                // Just went offline
                isOnline = false;
                Platform.runLater(this::handleConnectionLost);
            } else if (!isOnline && nowOnline) {
                // Just came back online
                isOnline = true;
                Platform.runLater(this::handleConnectionRestored);
            }

        } catch (Exception e) {
            // Connection failed
            if (isOnline) {
                isOnline = false;
                Platform.runLater(this::handleConnectionLost);
            }
        }
    }

    private void handleConnectionLost() {
        System.out.println("[CONNECTION] Internet connection lost - showing reconnect dialog");

        // Don't stop the main connection checker - keep it running
        reconnectAttempts = 0;

        // Show reconnection dialog
        showConnectionLostDialog();

        // Start aggressive reconnect checking (every 3 seconds)
        startReconnectChecking();
    }

    private void handleConnectionRestored() {
        System.out.println("[CONNECTION] Internet connection restored!");

        // Stop reconnect checking
        if (reconnectCheckTimer != null) {
            reconnectCheckTimer.stop();
            reconnectCheckTimer = null;
        }

        // Close the dialog if it's showing
        if (connectionLostStage != null && connectionLostStage.isShowing()) {
            connectionLostStage.close();
            connectionLostStage = null;
        }

        reconnectAttempts = 0;

        // Show brief success notification
        showReconnectSuccessNotification();

        // MODIFIED: Only re-check if user hasn't just handled a game end
        if (!justHandledGameEnd) {
            Platform.runLater(() -> {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                checkSuccess();
            });
        }
    }

    private void startReconnectChecking() {
        // Check every 3 seconds for faster reconnection
        reconnectCheckTimer = new Timeline(new KeyFrame(Duration.seconds(3), e -> {
            CompletableFuture.runAsync(this::attemptReconnect, executor);
        }));
        reconnectCheckTimer.setCycleCount(Timeline.INDEFINITE);
        reconnectCheckTimer.play();
    }

    private void attemptReconnect() {
        try {
            reconnectAttempts++;

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(SUPABASE_URL + "/rest/v1/"))
                    .header("apikey", SUPABASE_ANON_KEY)
                    .timeout(java.time.Duration.ofSeconds(3))
                    .method("HEAD", HttpRequest.BodyPublishers.noBody())
                    .build();

            HttpResponse<Void> response = httpClient.send(request, HttpResponse.BodyHandlers.discarding());

            boolean nowOnline = (response.statusCode() >= 200 && response.statusCode() < 500);

            Platform.runLater(() -> {
                if (reconnectStatusLabel != null) {
                    if (nowOnline) {
                        reconnectStatusLabel.setText("Connection restored! Reconnecting...");
                        reconnectStatusLabel.setStyle("-fx-font-size: 14; -fx-text-fill: #00ff00; -fx-text-alignment: center;");
                    } else {
                        reconnectStatusLabel.setText("Reconnecting... (Attempt " + reconnectAttempts + ")");
                        reconnectStatusLabel.setStyle("-fx-font-size: 14; -fx-text-fill: #ffaa00; -fx-text-alignment: center;");
                    }
                }
            });

            if (nowOnline && !isOnline) {
                isOnline = true;
                Platform.runLater(this::handleConnectionRestored);
            }

        } catch (Exception e) {
            Platform.runLater(() -> {
                if (reconnectStatusLabel != null) {
                    reconnectStatusLabel.setText("Reconnecting... (Attempt " + reconnectAttempts + ")");
                    reconnectStatusLabel.setStyle("-fx-font-size: 14; -fx-text-fill: #ffaa00; -fx-text-alignment: center;");
                }
            });
        }
    }

    private void showConnectionLostDialog() {
        if (connectionLostStage != null && connectionLostStage.isShowing()) {
            return; // Already showing
        }

        connectionLostStage = new Stage();
        connectionLostStage.initModality(Modality.APPLICATION_MODAL);
        connectionLostStage.initOwner(primaryStage);
        connectionLostStage.setTitle("Connection Lost");
        connectionLostStage.setResizable(false);

        VBox contentBox = new VBox(20);
        contentBox.setAlignment(Pos.CENTER);
        contentBox.setPadding(new Insets(40, 50, 40, 50));
        contentBox.setStyle("-fx-background-color: linear-gradient(to bottom, #1a0a0a, #2a1a1a);");

        // Warning icon
        Label iconLabel = new Label("⚠");
        iconLabel.setStyle("-fx-font-size: 72; -fx-text-fill: #ff9900;");

        DropShadow glow = new DropShadow();
        glow.setColor(Color.rgb(255, 153, 0, 0.8));
        glow.setRadius(30);
        iconLabel.setEffect(glow);

        // Pulse animation
        Timeline pulse = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(iconLabel.scaleXProperty(), 1.0)),
                new KeyFrame(Duration.millis(500), new KeyValue(iconLabel.scaleXProperty(), 1.1)),
                new KeyFrame(Duration.millis(1000), new KeyValue(iconLabel.scaleXProperty(), 1.0))
        );
        pulse.setCycleCount(Timeline.INDEFINITE);
        pulse.play();

        Label titleLabel = new Label("CONNECTION LOST");
        titleLabel.setStyle("-fx-font-size: 32; -fx-font-weight: bold; -fx-text-fill: #ff9900;");

        Label messageLabel = new Label(
                "Your internet connection has been lost.\n\n" +
                        "Waiting for connection to be restored..."
        );
        messageLabel.setStyle("-fx-font-size: 16; -fx-text-fill: #ffffff; -fx-text-alignment: center;");
        messageLabel.setWrapText(true);
        messageLabel.setMaxWidth(450);
        messageLabel.setAlignment(Pos.CENTER);

        // Reconnect status label
        reconnectStatusLabel = new Label("Reconnecting...");
        reconnectStatusLabel.setStyle("-fx-font-size: 14; -fx-text-fill: #ffaa00; -fx-text-alignment: center;");
        reconnectStatusLabel.setWrapText(true);
        reconnectStatusLabel.setMaxWidth(450);
        reconnectStatusLabel.setAlignment(Pos.CENTER);

        // Loading spinner
        ProgressIndicator spinner = new ProgressIndicator();
        spinner.setStyle("-fx-progress-color: #ff9900;");
        spinner.setPrefSize(50, 50);

        // Close Game button (optional - for users who want to quit)
        Button closeBtn = new Button("Close Game");
        closeBtn.setPrefWidth(200);
        closeBtn.setPrefHeight(45);
        closeBtn.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #444444, #222222); " +
                        "-fx-text-fill: #ffffff; -fx-font-weight: bold; -fx-font-size: 14; " +
                        "-fx-background-radius: 10; -fx-cursor: hand;"
        );
        closeBtn.setOnMouseEntered(e -> closeBtn.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #666666, #444444); " +
                        "-fx-text-fill: #ffffff; -fx-font-weight: bold; -fx-font-size: 14; " +
                        "-fx-background-radius: 10; -fx-cursor: hand;"
        ));
        closeBtn.setOnMouseExited(e -> closeBtn.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #444444, #222222); " +
                        "-fx-text-fill: #ffffff; -fx-font-weight: bold; -fx-font-size: 14; " +
                        "-fx-background-radius: 10; -fx-cursor: hand;"
        ));
        closeBtn.setOnAction(e -> {
            pulse.stop();
            if (reconnectCheckTimer != null) {
                reconnectCheckTimer.stop();
            }
            connectionLostStage.close();
            Platform.exit();
            System.exit(0);
        });

        contentBox.getChildren().addAll(iconLabel, titleLabel, messageLabel, spinner, reconnectStatusLabel, closeBtn);

        Scene scene = new Scene(contentBox, 550, 580);
        connectionLostStage.initStyle(StageStyle.UNDECORATED);
        connectionLostStage.setScene(scene);
        connectionLostStage.centerOnScreen();

        // Prevent closing with ESC or window controls
        connectionLostStage.setOnCloseRequest(event -> event.consume());

        connectionLostStage.show();
    }

    private void showReconnectSuccessNotification() {
        // Create a StackPane container
        StackPane notification = new StackPane();
        notification.setMinWidth(250);
        notification.setPrefWidth(250);
        notification.setMinHeight(55);
        notification.setPrefHeight(55);

        // Create a Rectangle as the solid background
        Rectangle background = new Rectangle(250, 55);
        background.setFill(Color.rgb(45, 45, 45));
        background.setArcWidth(12);  // Rounded corners
        background.setArcHeight(12);

        // Add drop shadow to the rectangle
        DropShadow shadow = new DropShadow();
        shadow.setColor(Color.rgb(0, 0, 0, 0.6));
        shadow.setRadius(15);
        shadow.setOffsetY(5);
        background.setEffect(shadow);

        // Create content HBox
        HBox contentBox = new HBox(12);
        contentBox.setAlignment(Pos.CENTER_LEFT);
        contentBox.setPadding(new Insets(12, 18, 12, 18));
        contentBox.setMaxWidth(250);

        // Green checkmark icon
        Label icon = new Label("✓");
        icon.setStyle("-fx-font-size: 24; -fx-text-fill: #4caf50; -fx-font-weight: bold;");
        icon.setMinWidth(25);

        // Message text
        Label message = new Label("Connection Restored");
        message.setStyle("-fx-font-size: 14; -fx-text-fill: #ffffff; -fx-font-weight: 600;");
        message.setMinWidth(130);
        message.setPrefWidth(130);
        HBox.setHgrow(message, Priority.ALWAYS);

        // Spacer
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // Close button
        Button closeBtn = new Button("×");
        closeBtn.setStyle(
                "-fx-background-color: transparent; " +
                        "-fx-text-fill: #999999; " +
                        "-fx-font-size: 24; " +
                        "-fx-font-weight: bold; " +
                        "-fx-cursor: hand; " +
                        "-fx-padding: 0; " +
                        "-fx-min-width: 25; " +
                        "-fx-pref-width: 25; " +
                        "-fx-min-height: 25;"
        );
        closeBtn.setOnMouseEntered(e -> closeBtn.setStyle(
                "-fx-background-color: rgba(255,255,255,0.15); " +
                        "-fx-background-radius: 12; " +
                        "-fx-text-fill: #ffffff; " +
                        "-fx-font-size: 24; " +
                        "-fx-font-weight: bold; " +
                        "-fx-cursor: hand; " +
                        "-fx-padding: 0; " +
                        "-fx-min-width: 25; " +
                        "-fx-pref-width: 25; " +
                        "-fx-min-height: 25;"
        ));
        closeBtn.setOnMouseExited(e -> closeBtn.setStyle(
                "-fx-background-color: transparent; " +
                        "-fx-text-fill: #999999; " +
                        "-fx-font-size: 24; " +
                        "-fx-font-weight: bold; " +
                        "-fx-cursor: hand; " +
                        "-fx-padding: 0; " +
                        "-fx-min-width: 25; " +
                        "-fx-pref-width: 25; " +
                        "-fx-min-height: 25;"
        ));

        contentBox.getChildren().addAll(icon, message, spacer, closeBtn);

        // Add background rectangle first, then content on top
        notification.getChildren().addAll(background, contentBox);

        // Position at bottom-left with more spacing from the left edge
        notification.setLayoutX(150);  // moves it right
        notification.setLayoutY(primaryStage.getScene().getHeight() - 100);

        // Add to root pane
        if (primaryStage.getScene().getRoot() instanceof Pane) {
            ((Pane) primaryStage.getScene().getRoot()).getChildren().add(notification);
        }

        // Start below screen
        notification.setTranslateY(150);
        notification.setOpacity(0);

        // Slide up animation
        Timeline slideUp = new Timeline(
                new KeyFrame(Duration.ZERO,
                        new KeyValue(notification.translateYProperty(), 150),
                        new KeyValue(notification.opacityProperty(), 0)
                ),
                new KeyFrame(Duration.millis(350),
                        new KeyValue(notification.translateYProperty(), 0, Interpolator.EASE_OUT),
                        new KeyValue(notification.opacityProperty(), 1, Interpolator.EASE_OUT)
                )
        );

        // Auto-dismiss after 4 seconds
        Timeline slideDown = new Timeline(
                new KeyFrame(Duration.ZERO,
                        new KeyValue(notification.translateYProperty(), 0),
                        new KeyValue(notification.opacityProperty(), 1)
                ),
                new KeyFrame(Duration.millis(350),
                        new KeyValue(notification.translateYProperty(), 150, Interpolator.EASE_IN),
                        new KeyValue(notification.opacityProperty(), 0, Interpolator.EASE_IN)
                )
        );
        slideDown.setDelay(Duration.seconds(4));

        slideDown.setOnFinished(e -> {
            if (primaryStage.getScene().getRoot() instanceof Pane) {
                ((Pane) primaryStage.getScene().getRoot()).getChildren().remove(notification);
            }
        });

        // Close button action
        closeBtn.setOnAction(e -> {
            slideDown.stop();
            Timeline quickClose = new Timeline(
                    new KeyFrame(Duration.ZERO,
                            new KeyValue(notification.translateYProperty(), notification.getTranslateY()),
                            new KeyValue(notification.opacityProperty(), notification.getOpacity())
                    ),
                    new KeyFrame(Duration.millis(200),
                            new KeyValue(notification.translateYProperty(), 150, Interpolator.EASE_IN),
                            new KeyValue(notification.opacityProperty(), 0, Interpolator.EASE_IN)
                    )
            );
            quickClose.setOnFinished(event -> {
                if (primaryStage.getScene().getRoot() instanceof Pane) {
                    ((Pane) primaryStage.getScene().getRoot()).getChildren().remove(notification);
                }
            });
            quickClose.play();
        });

        slideUp.play();
        slideDown.play();
    }

    private void loadFullProfileFromSupabase(String username, Runnable onComplete) {
        executor.submit(() -> {
            try {
                String url = SUPABASE_URL + "/rest/v1/profiles?username=eq." + URLEncoder.encode(username, StandardCharsets.UTF_8)
                        + "&select=profile_picture,profile_picture_bytes,unlocked_levels,effects_muted,music_muted,volume,sprite_visible";

                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(url))
                        .header("apikey", SUPABASE_ANON_KEY)
                        .header("Authorization", "Bearer " + SUPABASE_ANON_KEY)
                        .GET()
                        .build();

                HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

                Platform.runLater(() -> {
                    Image loadedImage = null;

                    if (response.statusCode() == 200 && response.body() != null && !response.body().trim().equals("[]")) {
                        JsonArray array = gson.fromJson(response.body(), JsonArray.class);
                        JsonObject data = array.get(0).getAsJsonObject();

                        // === ULTRA-SAFE PROFILE PICTURE LOADING (FINAL FIX) ===
                        String rawBase64 = null;

                        // Prefer the clean new column first
                        if (data.has("profile_picture_bytes") && !data.get("profile_picture_bytes").isJsonNull()) {
                            rawBase64 = data.get("profile_picture_bytes").getAsString();
                        }
                        // Fallback to old broken column
                        else if (data.has("profile_picture") && !data.get("profile_picture").isJsonNull()) {
                            rawBase64 = data.get("profile_picture").getAsString();
                        }

                        // THIS IS THE ONLY PLACE WE DECODE — USING THE FINAL SAFE METHOD
                        loadedImage = loadProfileImageSafely(rawBase64);

                        // === UNLOCKED LEVELS (SAFE PARSING) ===
                        unlockedLevels.clear();
                        if (data.has("unlocked_levels") && !data.get("unlocked_levels").isJsonNull()) {
                            String levelsStr = data.get("unlocked_levels").getAsString();
                            if (levelsStr != null && !levelsStr.isBlank()) {
                                for (String s : levelsStr.split(",")) {
                                    try {
                                        int level = Integer.parseInt(s.trim());
                                        if (level > 0 && level <= 1000) { // sanity cap
                                            unlockedLevels.add(level);
                                        }
                                    } catch (Exception ignored) {
                                    }
                                }
                            }
                        }
                        if (unlockedLevels.isEmpty()) unlockedLevels.add(1);

                        // === AUDIO SETTINGS ===
                        isEffectsMuted = data.has("effects_muted") && data.get("effects_muted").getAsBoolean();
                        isMusicMuted = data.has("music_muted") && data.get("music_muted").getAsBoolean();
                        audioVolume = data.has("volume") ? data.get("volume").getAsDouble() : 1.0;
                        isSpriteVisible = !data.has("sprite_visible") || data.get("sprite_visible").getAsBoolean();
                        updateAudioSettings();

                    } else {
                        // No profile (should never happen, but safe)
                        unlockedLevels.clear();
                        unlockedLevels.add(1);
                        isEffectsMuted = false;
                        isMusicMuted = false;
                        audioVolume = 1.0;
                        updateAudioSettings();
                    }

                    // === FINAL FALLBACK: Default Image (only if still null) ===
                    if (loadedImage == null || loadedImage.isError()) {
                        loadedImage = getDefaultProfileImage();
                    }

                    currentProfileImage = loadedImage;

                    // Update ALL profile picture views
                    if (topBarProfilePicView != null) topBarProfilePicView.setImage(loadedImage);
                    if (levelSelectProfileView != null) levelSelectProfileView.setImage(loadedImage);
                    if (settingsProfileView != null) settingsProfileView.setImage(loadedImage);

                    // Done
                    if (onComplete != null) onComplete.run();

                });

            } catch (Exception e) {
                // Offline / network error
                Platform.runLater(() -> {
                    unlockedLevels.clear();
                    unlockedLevels.add(1);
                    isEffectsMuted = false;
                    isMusicMuted = false;
                    audioVolume = 1.0;
                    updateAudioSettings();

                    Image fallback = getDefaultProfileImage();
                    currentProfileImage = fallback;
                    if (topBarProfilePicView != null) topBarProfilePicView.setImage(fallback);
                    if (levelSelectProfileView != null) levelSelectProfileView.setImage(fallback);
                    if (settingsProfileView != null) settingsProfileView.setImage(fallback);

                    if (onComplete != null) onComplete.run();
                });
            }
        });
    }

    private Image loadProfileImageSafely(String dirty) {
        if (dirty == null || dirty.trim().isEmpty()) {
            return getDefaultProfileImage();
        }

        String s = dirty.trim();

        // Remove data URL prefix
        int comma = s.indexOf(',');
        if (comma != -1) {
            s = s.substring(comma + 1);
        }

        // Fix escaped slashes from JSON: \/ → /
        s = s.replace("\\/", "/");

        // Remove ALL whitespace and garbage
        s = s.replaceAll("\\s+", "");
        s = s.replaceAll("[^A-Za-z0-9+/=]", "");

        if (s.length() < 50) {
            return getDefaultProfileImage();
        }

        try {
            byte[] bytes = Base64.getDecoder().decode(s);
            Image img = new Image(new ByteArrayInputStream(bytes), 100, 100, true, true);
            return img.isError() ? getDefaultProfileImage() : img;
        } catch (Exception e) {
            System.err.println("Profile picture Base64 corrupted (ignored): " + e.getMessage());
            return getDefaultProfileImage();
        }
    }

    private void loginWithSupabase(String username, String password, Button loginButton, boolean keepLoggedIn) {
        executor.submit(() -> {
            try {
                // Fetch user data INCLUDING skip_back_confirmation
                String url = SUPABASE_URL + "/rest/v1/profiles?username=eq." + username
                        + "&select=username,password,profile_picture_bytes,unlocked_levels,effects_muted,music_muted,volume,sprite_visible,banned,ban_reason,is_admin,failed_login_attempts,locked_until,appeal_submitted,appeal_status,tutorial_completed,skip_back_confirmation";

                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(url))
                        .header("apikey", SUPABASE_ANON_KEY)
                        .header("Authorization", "Bearer " + SUPABASE_ANON_KEY)
                        .GET()
                        .build();

                HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

                Platform.runLater(() -> {
                    loginButton.setDisable(false);
                    isNavigating = false;

                    if (response.statusCode() == 200) {
                        String body = response.body();
                        if (body == null || body.trim().isEmpty() || body.equals("[]")) {
                            // Username doesn't exist
                            PauseTransition pause = new PauseTransition(Duration.millis(300));
                            pause.setOnFinished(e -> {
                                showLoginScreen(username);
                                Platform.runLater(() -> {
                                    new Alert(AlertType.ERROR, "Incorrect username or password.").showAndWait();
                                });
                            });
                            pause.play();
                            return;
                        }

                        JsonArray array = gson.fromJson(body, JsonArray.class);
                        JsonObject userData = array.get(0).getAsJsonObject();

                        // CHECK SERVER-SIDE LOCKOUT FIRST
                        long lockedUntil = userData.has("locked_until") ? userData.get("locked_until").getAsLong() : 0;
                        if (System.currentTimeMillis() < lockedUntil) {
                            long remainingSeconds = (lockedUntil - System.currentTimeMillis()) / 1000;
                            long minutes = remainingSeconds / 60;
                            long seconds = remainingSeconds % 60;
                            String timeMsg = minutes > 0
                                    ? minutes + " minute(s) and " + seconds + " second(s)"
                                    : seconds + " second(s)";

                            PauseTransition pause = new PauseTransition(Duration.millis(300));
                            pause.setOnFinished(e -> {
                                showLoginScreen(username);
                                Platform.runLater(() -> {
                                    Alert alert = new Alert(AlertType.WARNING);
                                    alert.setTitle("Account Temporarily Locked");
                                    alert.setHeaderText("Too Many Failed Login Attempts");
                                    alert.setContentText("This account has been temporarily locked due to multiple failed login attempts.\n\n" +
                                            "Please wait " + timeMsg + " before trying again.\n\n" +
                                            "This is a security measure to protect your account.");
                                    alert.showAndWait();
                                });
                            });
                            pause.play();
                            return;
                        }

                        // Check if banned
                        boolean isBanned = userData.has("banned") && userData.get("banned").getAsBoolean();
                        if (isBanned) {
                            String banReason = userData.has("ban_reason") && !userData.get("ban_reason").isJsonNull()
                                    ? userData.get("ban_reason").getAsString()
                                    : "No reason provided";

                            boolean hasAppeal = userData.has("appeal_submitted") && userData.get("appeal_submitted").getAsBoolean();
                            String appealStatus = userData.has("appeal_status") && !userData.get("appeal_status").isJsonNull()
                                    ? userData.get("appeal_status").getAsString()
                                    : "none";

                            showBannedDialog(username, banReason, hasAppeal, appealStatus);
                            return;
                        }

                        // Verify password
                        String storedHash = userData.get("password").getAsString();
                        if (!BCrypt.checkpw(password, storedHash)) {
                            // WRONG PASSWORD - Update server
                            incrementFailedAttemptsOnServer(username, userData);
                            return;
                        }

                        // SUCCESS - Clear attempts on server
                        clearFailedAttemptsOnServer(username);

                        currentUser = username;
                        boolean isAdmin = userData.has("is_admin") && userData.get("is_admin").getAsBoolean();

                        if (!isAdmin) {
                            String rawBase64 = userData.has("profile_picture_bytes") && !userData.get("profile_picture_bytes").isJsonNull()
                                    ? userData.get("profile_picture_bytes").getAsString().trim() : null;
                            currentProfileImage = loadProfileImageSafely(rawBase64);

                            unlockedLevels.clear();
                            if (userData.has("unlocked_levels") && !userData.get("unlocked_levels").isJsonNull()) {
                                String levelsStr = userData.get("unlocked_levels").getAsString();
                                for (String s : levelsStr.split(",")) {
                                    if (!s.trim().isEmpty()) unlockedLevels.add(Integer.parseInt(s.trim()));
                                }
                            }

                            isEffectsMuted = userData.get("effects_muted").getAsBoolean();
                            isMusicMuted = userData.get("music_muted").getAsBoolean();
                            audioVolume = userData.has("volume") ? userData.get("volume").getAsDouble() : 1.0;
                            isSpriteVisible = userData.has("sprite_visible") ? userData.get("sprite_visible").getAsBoolean() : true;
                            tutorialCompleted = userData.has("tutorial_completed") ? userData.get("tutorial_completed").getAsBoolean() : false;
                            skipBackConfirmation = userData.has("skip_back_confirmation") ? userData.get("skip_back_confirmation").getAsBoolean() : false; // NEW
                            updateAudioSettings();
                        } else {
                            currentProfileImage = new Image("file:resources/images/admin_profile.png");
                        }

                        if (keepLoggedIn) saveLoginToken(username);
                        else deleteLoginToken();

                        if (isAdmin) {
                            showAdminPanel();
                        } else {
                            showLevelSelectScreen();
                            startMaintenanceMonitoring();
                        }
                    } else {
                        PauseTransition pause = new PauseTransition(Duration.millis(300));
                        pause.setOnFinished(e -> {
                            showLoginScreen(username);
                            Platform.runLater(() -> {
                                new Alert(AlertType.ERROR, "Server error: " + response.statusCode()).showAndWait();
                            });
                        });
                        pause.play();
                    }
                });

            } catch (java.net.ConnectException | java.nio.channels.UnresolvedAddressException e) {
                System.out.println("[LOGIN] Connection error detected: " + e.getMessage());
                Platform.runLater(() -> {
                    loginButton.setDisable(false);
                    isNavigating = false;
                    showLoginScreen(username);
                });
            } catch (Exception e) {
                Platform.runLater(() -> {
                    loginButton.setDisable(false);
                    isNavigating = false;
                    PauseTransition pause = new PauseTransition(Duration.millis(300));
                    pause.setOnFinished(ev -> {
                        showLoginScreen(username);
                        Platform.runLater(() -> {
                            new Alert(AlertType.ERROR, "Connection failed: " + e.getMessage()).showAndWait();
                        });
                    });
                    pause.play();
                    e.printStackTrace();
                });
            }
        });
    }

    private void startBanStatusMonitoring() {
        if (banCheckTimer != null) {
            banCheckTimer.stop();
        }

        banCheckTimer = new Timeline(new KeyFrame(Duration.millis(BAN_CHECK_INTERVAL_MS), event -> {
            checkBanStatus();
        }));
        banCheckTimer.setCycleCount(Animation.INDEFINITE);
        banCheckTimer.play();

        System.out.println("[SECURITY] Started ban status monitoring for user: " + currentUser);
    }

    // Call this method when user logs out
    private void stopBanStatusMonitoring() {
        if (banCheckTimer != null) {
            banCheckTimer.stop();
            banCheckTimer = null;
            System.out.println("[SECURITY] Stopped ban status monitoring");
        }
    }

    // Check if current user is banned
    private void checkBanStatus() {
        if (currentUser == null || currentUser.isEmpty()) {
            return;
        }

        executor.submit(() -> {
            try {
                String url = SUPABASE_URL + "/rest/v1/profiles?username=eq." + currentUser
                        + "&select=banned,ban_reason,appeal_status";

                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(url))
                        .header("apikey", SUPABASE_ANON_KEY)
                        .header("Authorization", "Bearer " + SUPABASE_ANON_KEY)
                        .GET()
                        .build();

                HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

                if (response.statusCode() == 200) {
                    String body = response.body();
                    if (body != null && !body.trim().isEmpty() && !body.equals("[]")) {
                        JsonArray array = gson.fromJson(body, JsonArray.class);
                        if (array.size() > 0) {
                            JsonObject userData = array.get(0).getAsJsonObject();
                            boolean isBanned = userData.has("banned") && userData.get("banned").getAsBoolean();

                            if (isBanned) {
                                String banReason = userData.has("ban_reason") && !userData.get("ban_reason").isJsonNull()
                                        ? userData.get("ban_reason").getAsString()
                                        : "No reason provided";

                                boolean hasAppeal = userData.has("appeal_submitted") && userData.get("appeal_submitted").getAsBoolean();
                                String appealStatus = userData.has("appeal_status") && !userData.get("appeal_status").isJsonNull()
                                        ? userData.get("appeal_status").getAsString()
                                        : "none";

                                // User was banned while playing - force logout
                                Platform.runLater(() -> {
                                    System.out.println("[SECURITY] User " + currentUser + " was banned during session. Forcing logout.");
                                    stopBanStatusMonitoring();

                                    // Store username before clearing
                                    String bannedUsername = currentUser;

                                    // Clear user session
                                    currentUser = null;
                                    currentProfileImage = null;
                                    deleteLoginToken();
                                    clearUserProgressCache();

                                    // Stop background audio
                                    stopCurrentBackgroundAudio();

                                    // Show ban dialog, then return to login
                                    showBannedDialog(bannedUsername, banReason, hasAppeal, appealStatus);
                                });
                            }
                        }
                    }
                }
            } catch (Exception e) {
                System.err.println("[SECURITY] Failed to check ban status: " + e.getMessage());
                // Don't log out on network errors - only on confirmed bans
            }
        });
    }

    private void incrementFailedAttemptsOnServer(String username, JsonObject currentUserData) {
        executor.submit(() -> {
            try {
                int currentAttempts = currentUserData.has("failed_login_attempts")
                        ? currentUserData.get("failed_login_attempts").getAsInt()
                        : 0;

                int newAttempts = currentAttempts + 1;
                long lockoutTime = 0;

                if (newAttempts >= 3) {
                    // Lock out for 15 minutes (900,000 milliseconds)
                    lockoutTime = System.currentTimeMillis() + (15 * 60 * 1000);
                    System.out.println("[SECURITY] Username '" + username + "' locked out after " + newAttempts + " failed attempts");
                }

                // Update the database
                JsonObject updateData = new JsonObject();
                updateData.addProperty("failed_login_attempts", newAttempts);
                updateData.addProperty("locked_until", lockoutTime);
                updateData.addProperty("last_failed_attempt", System.currentTimeMillis());

                String updateUrl = SUPABASE_URL + "/rest/v1/profiles?username=eq." + username;

                HttpRequest updateRequest = HttpRequest.newBuilder()
                        .uri(URI.create(updateUrl))
                        .header("apikey", SUPABASE_ANON_KEY)
                        .header("Authorization", "Bearer " + SUPABASE_ANON_KEY)
                        .header("Content-Type", "application/json")
                        .header("Prefer", "return=minimal")
                        .method("PATCH", HttpRequest.BodyPublishers.ofString(gson.toJson(updateData)))
                        .build();

                HttpResponse<String> updateResponse = httpClient.send(updateRequest, HttpResponse.BodyHandlers.ofString());

                if (updateResponse.statusCode() == 204 || updateResponse.statusCode() == 200) {
                    System.out.println("[SECURITY] Updated failed attempts for '" + username + "': " + newAttempts);
                }

                // Show error to user
                Platform.runLater(() -> {
                    int remaining = 3 - newAttempts;
                    PauseTransition pause = new PauseTransition(Duration.millis(300));
                    pause.setOnFinished(e -> {
                        showLoginScreen(username);
                        Platform.runLater(() -> {
                            String message;
                            if (remaining > 0) {
                                message = "Incorrect password.\n\nWarning: " + remaining + " attempt(s) remaining before temporary lockout.";
                            } else {
                                message = "Account locked due to too many failed attempts.\nPlease wait 15 minutes before trying again.";
                            }
                            new Alert(AlertType.ERROR, message).showAndWait();
                        });
                    });
                    pause.play();
                });

            } catch (Exception e) {
                System.err.println("[SECURITY] Failed to increment attempts on server: " + e.getMessage());
                e.printStackTrace();
            }
        });
    }

    /**
     * Clear failed attempts on the server after successful login
     */
    private void clearFailedAttemptsOnServer(String username) {
        executor.submit(() -> {
            try {
                JsonObject updateData = new JsonObject();
                updateData.addProperty("failed_login_attempts", 0);
                updateData.addProperty("locked_until", 0);

                String updateUrl = SUPABASE_URL + "/rest/v1/profiles?username=eq." + username;

                HttpRequest updateRequest = HttpRequest.newBuilder()
                        .uri(URI.create(updateUrl))
                        .header("apikey", SUPABASE_ANON_KEY)
                        .header("Authorization", "Bearer " + SUPABASE_ANON_KEY)
                        .header("Content-Type", "application/json")
                        .header("Prefer", "return=minimal")
                        .method("PATCH", HttpRequest.BodyPublishers.ofString(gson.toJson(updateData)))
                        .build();

                HttpResponse<String> updateResponse = httpClient.send(updateRequest, HttpResponse.BodyHandlers.ofString());

                if (updateResponse.statusCode() == 204 || updateResponse.statusCode() == 200) {
                    System.out.println("[SECURITY] Cleared failed attempts for '" + username + "'");
                }

            } catch (Exception e) {
                System.err.println("[SECURITY] Failed to clear attempts on server: " + e.getMessage());
            }
        });
    }

    public void showLoginScreen() {
        showLoginScreen(null);
    }

    public void showLoginScreen(String prefillUsername) {
        isNavigating = false;
        isOnLevelSelectScreen = false;
        root.getChildren().clear();
        root.setStyle("-fx-background-color: #0A0E26;");
        primaryStage.setTitle("Chroma Flood - Login");

        // Create animated background layer that covers entire window
        Canvas backgroundCanvas = new Canvas();
        GraphicsContext gc = backgroundCanvas.getGraphicsContext2D();

        backgroundCanvas.widthProperty().bind(primaryStage.widthProperty());
        backgroundCanvas.heightProperty().bind(primaryStage.heightProperty());

        // Make background canvas clickable to allow unfocusing
        backgroundCanvas.setOnMouseClicked(event -> {
            root.requestFocus();
        });

        // Animated background particles
        AnimationTimer backgroundAnimation = new AnimationTimer() {
            private long lastUpdate = 0;
            private double time = 0;

            @Override
            public void handle(long now) {
                if (lastUpdate == 0) {
                    lastUpdate = now;
                    return;
                }

                double delta = (now - lastUpdate) / 1_000_000_000.0;
                lastUpdate = now;
                time += delta;

                double width = backgroundCanvas.getWidth();
                double height = backgroundCanvas.getHeight();

                gc.setFill(new RadialGradient(0, 0, 0.5, 0.5, 0.8, true,
                        CycleMethod.NO_CYCLE,
                        new Stop(0, Color.rgb(28, 37, 38, 1.0)),
                        new Stop(1, Color.rgb(10, 14, 38, 1.0))));
                gc.fillRect(0, 0, width, height);

                int particleCount = (int) (width * height / 8000);
                for (int i = 0; i < particleCount; i++) {
                    double x = (Math.sin(time * 0.5 + i * 0.5) * (width * 0.45) + width * 0.5);
                    double y = (Math.cos(time * 0.3 + i * 0.3) * (height * 0.45) + height * 0.5);
                    double size = 3 + Math.sin(time + i) * 2;

                    gc.setFill(Color.rgb(0, 255, 255, 0.3 + Math.sin(time + i) * 0.2));
                    gc.fillOval(x, y, size, size);
                }

                gc.setStroke(Color.rgb(68, 255, 255, 0.15));
                gc.setLineWidth(2);
                for (int wave = 0; wave < 3; wave++) {
                    gc.beginPath();
                    for (int x = 0; x < width; x += 5) {
                        double y = height * 0.5 + Math.sin((x + time * 50 + wave * 100) * 0.02) * 30;
                        if (x == 0) gc.moveTo(x, y);
                        else gc.lineTo(x, y);
                    }
                    gc.stroke();
                }
            }
        };
        backgroundAnimation.start();

        VBox container = new VBox(20);
        container.setAlignment(Pos.CENTER);
        container.setPrefWidth(350);
        container.setMaxWidth(350);
        container.setMinHeight(380);
        container.setMaxHeight(380);
        container.setPadding(new Insets(20));
        container.setOpacity(0);
        container.setPickOnBounds(false); // Allow clicks to pass through empty areas

        Rectangle backgroundRect = new Rectangle(350, 380);
        backgroundRect.setFill(Color.rgb(0, 0, 0, 0.6));
        backgroundRect.setArcWidth(20);
        backgroundRect.setArcHeight(20);

        // Allow clicking on background rect to unfocus
        backgroundRect.setOnMouseClicked(event -> {
            root.requestFocus();
            event.consume();
        });

        DropShadow glow = new DropShadow();
        glow.setColor(Color.rgb(0, 255, 255, 0.5));
        glow.setRadius(20);
        glow.setSpread(0.3);
        backgroundRect.setEffect(glow);

        Timeline glowPulse = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(glow.radiusProperty(), 20)),
                new KeyFrame(Duration.seconds(2), new KeyValue(glow.radiusProperty(), 30)),
                new KeyFrame(Duration.seconds(4), new KeyValue(glow.radiusProperty(), 20))
        );
        glowPulse.setCycleCount(Timeline.INDEFINITE);
        glowPulse.play();

        VBox loginBox = new VBox(15);
        loginBox.setAlignment(Pos.CENTER);
        loginBox.setPadding(new Insets(20));
        loginBox.setPickOnBounds(false); // Allow clicks to pass through

        Label title = new Label("Login");
        title.setStyle("-fx-text-fill: #FFFFFF; -fx-font-family: 'Arial Black'; -fx-font-size: 28; -fx-font-weight: bold;");
        title.setOpacity(0);
        title.setTranslateY(-20);

        DropShadow titleGlow = new DropShadow();
        titleGlow.setColor(Color.rgb(0, 255, 255, 0.8));
        titleGlow.setRadius(15);
        title.setEffect(titleGlow);

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        usernameField.setStyle("-fx-background-color: rgba(255, 255, 255, 0.9); -fx-text-fill: #000000; -fx-font-size: 14; -fx-padding: 10; -fx-background-radius: 10; -fx-border-color: transparent; -fx-border-width: 2; -fx-border-radius: 10;");
        usernameField.setPrefWidth(220);
        usernameField.setMaxWidth(220);
        usernameField.setMinHeight(40);
        usernameField.setMaxHeight(40);
        usernameField.setOpacity(0);
        usernameField.setTranslateX(-50);

        // Pre-fill username if provided
        if (prefillUsername != null && !prefillUsername.isEmpty()) {
            usernameField.setText(prefillUsername);
        }

        usernameField.textProperty().addListener((observable, oldValue, newValue) -> {
            // Allow only alphanumeric characters and underscores
            if (!newValue.matches("^[a-zA-Z0-9_]*$")) {
                usernameField.setText(oldValue);
            }
            // Enforce maximum length of 20 characters
            else if (newValue.length() > 20) {
                usernameField.setText(oldValue);
            }
        });

        DropShadow focusGlow = new DropShadow();
        focusGlow.setColor(Color.rgb(0, 255, 255, 0.6));
        focusGlow.setRadius(10);

        usernameField.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            if (isNowFocused) {
                usernameField.setStyle("-fx-background-color: rgba(255, 255, 255, 1.0); -fx-text-fill: #000000; -fx-font-size: 14; -fx-padding: 10; -fx-background-radius: 10; -fx-border-color: #00FFFF; -fx-border-width: 2; -fx-border-radius: 10;");
                usernameField.setEffect(focusGlow);
            } else {
                usernameField.setStyle("-fx-background-color: rgba(255, 255, 255, 0.9); -fx-text-fill: #000000; -fx-font-size: 14; -fx-padding: 10; -fx-background-radius: 10; -fx-border-color: transparent; -fx-border-width: 2; -fx-border-radius: 10;");
                usernameField.setEffect(null);
            }
        });

        StackPane passwordContainer = new StackPane();
        passwordContainer.setPrefWidth(220);
        passwordContainer.setMaxWidth(220);
        passwordContainer.setMinWidth(220);
        passwordContainer.setOpacity(0);
        passwordContainer.setTranslateX(-50);

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setStyle("-fx-background-color: rgba(255, 255, 255, 0.9); -fx-text-fill: #000000; -fx-font-size: 14; -fx-padding: 10 45 10 10; -fx-background-radius: 10; -fx-border-color: transparent; -fx-border-width: 2; -fx-border-radius: 10;");
        passwordField.setPrefWidth(220);
        passwordField.setMaxWidth(220);
        passwordField.setMinWidth(220);
        passwordField.setMinHeight(40);
        passwordField.setMaxHeight(40);

        TextField passwordTextField = new TextField();
        passwordTextField.setPromptText("Password");
        passwordTextField.setStyle("-fx-background-color: rgba(255, 255, 255, 0.9); -fx-text-fill: #000000; -fx-font-size: 14; -fx-padding: 10 45 10 10; -fx-background-radius: 10; -fx-border-color: transparent; -fx-border-width: 2; -fx-border-radius: 10;");
        passwordTextField.setPrefWidth(220);
        passwordTextField.setMaxWidth(220);
        passwordTextField.setMinWidth(220);
        passwordTextField.setMinHeight(40);
        passwordTextField.setMaxHeight(40);
        passwordTextField.setVisible(false);
        passwordTextField.setManaged(false);

        // Bind the text properties so they stay in sync
        passwordTextField.textProperty().bindBidirectional(passwordField.textProperty());

        // Character limit for password fields
        passwordField.textProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue.length() > 32) {
                passwordField.setText(oldValue);
            }
        });
        passwordTextField.textProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue.length() > 32) {
                passwordTextField.setText(oldValue);
            }
        });

        // Create toggle button with eye icon
        Button togglePasswordButton = new Button("👁");
        togglePasswordButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #000000; -fx-font-size: 16; -fx-cursor: hand; -fx-padding: 5;");
        togglePasswordButton.setPrefSize(30, 30);
        togglePasswordButton.setFocusTraversable(false);

        StackPane.setAlignment(togglePasswordButton, Pos.CENTER_RIGHT);
        StackPane.setMargin(togglePasswordButton, new Insets(0, 10, 0, 0));

        // Toggle password visibility
        togglePasswordButton.setOnAction(event -> {
            if (passwordField.isVisible()) {
                passwordField.setVisible(false);
                passwordField.setManaged(false);
                passwordTextField.setVisible(true);
                passwordTextField.setManaged(true);
                togglePasswordButton.setText("🙈");

                // Unfocus the password field
                root.requestFocus();
            } else {
                passwordTextField.setVisible(false);
                passwordTextField.setManaged(false);
                passwordField.setVisible(true);
                passwordField.setManaged(true);
                togglePasswordButton.setText("👁");

                // Unfocus the password field
                root.requestFocus();
            }
        });

        // Apply focus styling to both fields
        passwordField.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            String focusedStyle = "-fx-background-color: rgba(255, 255, 255, 1.0); -fx-text-fill: #000000; -fx-font-size: 14; -fx-padding: 10 45 10 10; -fx-background-radius: 10; -fx-border-color: #00FFFF; -fx-border-width: 2; -fx-border-radius: 10;";
            String normalStyle = "-fx-background-color: rgba(255, 255, 255, 0.9); -fx-text-fill: #000000; -fx-font-size: 14; -fx-padding: 10 45 10 10; -fx-background-radius: 10; -fx-border-color: transparent; -fx-border-width: 2; -fx-border-radius: 10;";

            if (isNowFocused) {
                passwordField.setStyle(focusedStyle);
                passwordTextField.setStyle(focusedStyle);
                passwordField.setEffect(focusGlow);
                passwordTextField.setEffect(focusGlow);
            } else {
                passwordField.setStyle(normalStyle);
                passwordTextField.setStyle(normalStyle);
                passwordField.setEffect(null);
                passwordTextField.setEffect(null);
            }
        });

        passwordTextField.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            String focusedStyle = "-fx-background-color: rgba(255, 255, 255, 1.0); -fx-text-fill: #000000; -fx-font-size: 14; -fx-padding: 10 45 10 10; -fx-background-radius: 10; -fx-border-color: #00FFFF; -fx-border-width: 2; -fx-border-radius: 10;";
            String normalStyle = "-fx-background-color: rgba(255, 255, 255, 0.9); -fx-text-fill: #000000; -fx-font-size: 14; -fx-padding: 10 45 10 10; -fx-background-radius: 10; -fx-border-color: transparent; -fx-border-width: 2; -fx-border-radius: 10;";

            if (isNowFocused) {
                passwordField.setStyle(focusedStyle);
                passwordTextField.setStyle(focusedStyle);
                passwordField.setEffect(focusGlow);
                passwordTextField.setEffect(focusGlow);
            } else {
                passwordField.setStyle(normalStyle);
                passwordTextField.setStyle(normalStyle);
                passwordField.setEffect(null);
                passwordTextField.setEffect(null);
            }
        });

        passwordContainer.getChildren().addAll(passwordField, passwordTextField, togglePasswordButton);

        CheckBox keepLoggedInCheckBox = new CheckBox("Keep me logged in");
        keepLoggedInCheckBox.setStyle("-fx-text-fill: #FFFFFF; -fx-font-family: 'Arial'; -fx-font-size: 12;");
        keepLoggedInCheckBox.setSelected(true);
        keepLoggedInCheckBox.setOpacity(0);
        keepLoggedInCheckBox.setTranslateY(10);

        Button loginButton = new Button("Login");
        loginButton.setStyle(
                "-fx-background-color: linear-gradient(to right, #00D9FF, #0099FF);" +
                        "-fx-text-fill: white;" +
                        "-fx-font-family: 'Arial Black', 'Arial';" +
                        "-fx-font-size: 16;" +
                        "-fx-font-weight: bold;" +
                        "-fx-padding: 12 40;" +
                        "-fx-background-radius: 12;" +
                        "-fx-cursor: hand;" +
                        "-fx-effect: dropshadow(gaussian, rgba(0, 217, 255, 0.6), 15, 0.6, 0, 0);"
        );
        loginButton.setPrefWidth(220);
        loginButton.setMinHeight(45);
        loginButton.setMaxHeight(45);
        loginButton.setOpacity(0);
        loginButton.setTranslateX(50);

        // Hover effect - brighter gradient and stronger glow
        loginButton.setOnMouseEntered(e -> {
            loginButton.setStyle(
                    "-fx-background-color: linear-gradient(to right, #00EEFF, #00AAFF);" +
                            "-fx-text-fill: white;" +
                            "-fx-font-family: 'Arial Black', 'Arial';" +
                            "-fx-font-size: 16;" +
                            "-fx-font-weight: bold;" +
                            "-fx-padding: 12 40;" +
                            "-fx-background-radius: 12;" +
                            "-fx-cursor: hand;" +
                            "-fx-effect: dropshadow(gaussian, rgba(0, 217, 255, 0.9), 20, 0.7, 0, 2);"
            );
            ScaleTransition st = new ScaleTransition(Duration.millis(100), loginButton);
            st.setToX(1.05);
            st.setToY(1.05);
            st.play();
        });

        loginButton.setOnMouseExited(e -> {
            loginButton.setStyle(
                    "-fx-background-color: linear-gradient(to right, #00D9FF, #0099FF);" +
                            "-fx-text-fill: white;" +
                            "-fx-font-family: 'Arial Black', 'Arial';" +
                            "-fx-font-size: 16;" +
                            "-fx-font-weight: bold;" +
                            "-fx-padding: 12 40;" +
                            "-fx-background-radius: 12;" +
                            "-fx-cursor: hand;" +
                            "-fx-effect: dropshadow(gaussian, rgba(0, 217, 255, 0.6), 15, 0.6, 0, 0);"
            );
            ScaleTransition st = new ScaleTransition(Duration.millis(100), loginButton);
            st.setToX(1.0);
            st.setToY(1.0);
            st.play();
        });

        // Press effect
        loginButton.setOnMousePressed(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(50), loginButton);
            st.setToX(0.98);
            st.setToY(0.98);
            st.play();
        });

        loginButton.setOnMouseReleased(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(50), loginButton);
            st.setToX(1.05);
            st.setToY(1.05);
            st.play();
        });

        loginButton.setOnAction(event -> {
            if (isNavigating) return;

            String user = usernameField.getText().trim();
            String pass = passwordField.getText().trim();

            if (user.isEmpty() || pass.isEmpty()) {
                shakeNode(loginBox);
                new Alert(AlertType.ERROR, "Username and password cannot be empty.").showAndWait();
                return;
            }

            if (loginButton.isDisabled()) return;

            isNavigating = true;
            loginButton.setDisable(true);

            backgroundAnimation.stop();
            showLoadingScreen();

            checkMaintenanceBeforeLogin(() -> {
                loginWithSupabase(user, pass, loginButton, keepLoggedInCheckBox.isSelected());
            });
        });

        Button goToRegisterButton = new Button("Go to Register");
        goToRegisterButton.setStyle(
                "-fx-background-color: transparent;" +
                        "-fx-border-color: #00D9FF;" +
                        "-fx-border-width: 2;" +
                        "-fx-border-radius: 12;" +
                        "-fx-background-radius: 12;" +
                        "-fx-text-fill: #00D9FF;" +
                        "-fx-font-family: 'Arial', 'Arial';" +
                        "-fx-font-size: 14;" +
                        "-fx-font-weight: 600;" +
                        "-fx-padding: 12 40;" +
                        "-fx-cursor: hand;"
        );
        goToRegisterButton.setPrefWidth(220);
        goToRegisterButton.setMinHeight(45);
        goToRegisterButton.setMaxHeight(45);
        goToRegisterButton.setOpacity(0);
        goToRegisterButton.setTranslateX(50);

        // Hover effect - subtle glow and fill
        goToRegisterButton.setOnMouseEntered(e -> {
            goToRegisterButton.setStyle(
                    "-fx-background-color: rgba(0, 217, 255, 0.15);" +
                            "-fx-border-color: #00EEFF;" +
                            "-fx-border-width: 2;" +
                            "-fx-border-radius: 12;" +
                            "-fx-background-radius: 12;" +
                            "-fx-text-fill: #00EEFF;" +
                            "-fx-font-family: 'Arial', 'Arial';" +
                            "-fx-font-size: 14;" +
                            "-fx-font-weight: 600;" +
                            "-fx-padding: 12 40;" +
                            "-fx-cursor: hand;" +
                            "-fx-effect: dropshadow(gaussian, rgba(0, 217, 255, 0.4), 10, 0.5, 0, 0);"
            );
            ScaleTransition st = new ScaleTransition(Duration.millis(100), goToRegisterButton);
            st.setToX(1.03);
            st.setToY(1.03);
            st.play();
        });

        goToRegisterButton.setOnMouseExited(e -> {
            goToRegisterButton.setStyle(
                    "-fx-background-color: transparent;" +
                            "-fx-border-color: #00D9FF;" +
                            "-fx-border-width: 2;" +
                            "-fx-border-radius: 12;" +
                            "-fx-background-radius: 12;" +
                            "-fx-text-fill: #00D9FF;" +
                            "-fx-font-family: 'Arial', 'Arial';" +
                            "-fx-font-size: 14;" +
                            "-fx-font-weight: 600;" +
                            "-fx-padding: 12 40;" +
                            "-fx-cursor: hand;"
            );
            ScaleTransition st = new ScaleTransition(Duration.millis(100), goToRegisterButton);
            st.setToX(1.0);
            st.setToY(1.0);
            st.play();
        });

        // Press effect
        goToRegisterButton.setOnMousePressed(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(50), goToRegisterButton);
            st.setToX(0.98);
            st.setToY(0.98);
            st.play();
        });

        goToRegisterButton.setOnMouseReleased(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(50), goToRegisterButton);
            st.setToX(1.03);
            st.setToY(1.03);
            st.play();
        });

        goToRegisterButton.setOnAction(event -> {
            if (isNavigating) return;
            isNavigating = true;

            loginButton.setDisable(true);
            goToRegisterButton.setDisable(true);

            root.requestFocus();

            animateOutroAndTransition(container, loginBox, loginButton, goToRegisterButton,
                    backgroundAnimation, () -> {
                        showRegisterScreen();
                        isNavigating = false;
                    });
        });

        loginBox.getChildren().addAll(
                title, usernameField, passwordContainer,
                keepLoggedInCheckBox, loginButton, goToRegisterButton
        );

        StackPane formStack = new StackPane();
        formStack.getChildren().addAll(backgroundRect, loginBox);
        formStack.setPickOnBounds(true); // Make sure clicks on the form are captured

        container.getChildren().add(formStack);

        StackPane mainStack = new StackPane();
        mainStack.getChildren().addAll(backgroundCanvas, container);

        // Allow clicking on empty areas to unfocus
        mainStack.setOnMouseClicked(event -> {
            if (event.getTarget() == mainStack || event.getTarget() == backgroundCanvas) {
                root.requestFocus();
            }
        });

        root.setCenter(mainStack);

        // Entrance animation
        Timeline entranceAnimation = new Timeline(
                new KeyFrame(Duration.ZERO,
                        new KeyValue(container.opacityProperty(), 0)),
                new KeyFrame(Duration.millis(300),
                        new KeyValue(container.opacityProperty(), 1)),

                new KeyFrame(Duration.millis(200),
                        new KeyValue(title.opacityProperty(), 0),
                        new KeyValue(title.translateYProperty(), -20)),
                new KeyFrame(Duration.millis(600),
                        new KeyValue(title.opacityProperty(), 1, Interpolator.EASE_OUT),
                        new KeyValue(title.translateYProperty(), 0, Interpolator.EASE_OUT)),

                new KeyFrame(Duration.millis(400),
                        new KeyValue(usernameField.opacityProperty(), 0),
                        new KeyValue(usernameField.translateXProperty(), -50)),
                new KeyFrame(Duration.millis(800),
                        new KeyValue(usernameField.opacityProperty(), 1, Interpolator.EASE_OUT),
                        new KeyValue(usernameField.translateXProperty(), 0, Interpolator.EASE_OUT)),

                new KeyFrame(Duration.millis(500),
                        new KeyValue(passwordContainer.opacityProperty(), 0),
                        new KeyValue(passwordContainer.translateXProperty(), -50)),
                new KeyFrame(Duration.millis(900),
                        new KeyValue(passwordContainer.opacityProperty(), 1, Interpolator.EASE_OUT),
                        new KeyValue(passwordContainer.translateXProperty(), 0, Interpolator.EASE_OUT)),

                new KeyFrame(Duration.millis(600),
                        new KeyValue(keepLoggedInCheckBox.opacityProperty(), 0),
                        new KeyValue(keepLoggedInCheckBox.translateYProperty(), 10)),
                new KeyFrame(Duration.millis(1000),
                        new KeyValue(keepLoggedInCheckBox.opacityProperty(), 1, Interpolator.EASE_OUT),
                        new KeyValue(keepLoggedInCheckBox.translateYProperty(), 0, Interpolator.EASE_OUT)),

                new KeyFrame(Duration.millis(700),
                        new KeyValue(loginButton.opacityProperty(), 0),
                        new KeyValue(loginButton.translateXProperty(), 50)),
                new KeyFrame(Duration.millis(1100),
                        new KeyValue(loginButton.opacityProperty(), 1, Interpolator.EASE_OUT),
                        new KeyValue(loginButton.translateXProperty(), 0, Interpolator.EASE_OUT)),

                new KeyFrame(Duration.millis(800),
                        new KeyValue(goToRegisterButton.opacityProperty(), 0),
                        new KeyValue(goToRegisterButton.translateXProperty(), 50)),
                new KeyFrame(Duration.millis(1200),
                        new KeyValue(goToRegisterButton.opacityProperty(), 1, Interpolator.EASE_OUT),
                        new KeyValue(goToRegisterButton.translateXProperty(), 0, Interpolator.EASE_OUT))
        );
        entranceAnimation.play();
        Platform.runLater(() -> {
            root.requestFocus();
        });

        startMaintenanceMonitoring();
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
        isNavigating = false;
        clearUserProgressCache();
        root.getChildren().clear();
        root.setStyle("-fx-background-color: #0A0E26;");
        primaryStage.setTitle("Chroma Flood - Register");

        // Create animated background layer that covers entire window
        Canvas backgroundCanvas = new Canvas();
        GraphicsContext gc = backgroundCanvas.getGraphicsContext2D();

        // Bind canvas size to window size
        backgroundCanvas.widthProperty().bind(primaryStage.widthProperty());
        backgroundCanvas.heightProperty().bind(primaryStage.heightProperty());

        // Animated background particles
        AnimationTimer backgroundAnimation = new AnimationTimer() {
            private long lastUpdate = 0;
            private double time = 0;

            @Override
            public void handle(long now) {
                if (lastUpdate == 0) {
                    lastUpdate = now;
                    return;
                }

                double delta = (now - lastUpdate) / 1_000_000_000.0;
                lastUpdate = now;
                time += delta;

                double width = backgroundCanvas.getWidth();
                double height = backgroundCanvas.getHeight();

                // Clear with gradient
                gc.setFill(new RadialGradient(0, 0, 0.5, 0.5, 0.8, true,
                        CycleMethod.NO_CYCLE,
                        new Stop(0, Color.rgb(28, 37, 38, 1.0)),
                        new Stop(1, Color.rgb(10, 14, 38, 1.0))));
                gc.fillRect(0, 0, width, height);

                // Draw floating particles (scale with window size)
                int particleCount = (int) (width * height / 8000);
                for (int i = 0; i < particleCount; i++) {
                    double x = (Math.sin(time * 0.5 + i * 0.5) * (width * 0.45) + width * 0.5);
                    double y = (Math.cos(time * 0.3 + i * 0.3) * (height * 0.45) + height * 0.5);
                    double size = 3 + Math.sin(time + i) * 2;

                    gc.setFill(Color.rgb(0, 255, 255, 0.3 + Math.sin(time + i) * 0.2));
                    gc.fillOval(x, y, size, size);
                }

                // Draw wave lines (scale with window size)
                gc.setStroke(Color.rgb(68, 255, 255, 0.15));
                gc.setLineWidth(2);
                for (int wave = 0; wave < 3; wave++) {
                    gc.beginPath();
                    for (int x = 0; x < width; x += 5) {
                        double y = height * 0.5 + Math.sin((x + time * 50 + wave * 100) * 0.02) * 30;
                        if (x == 0) gc.moveTo(x, y);
                        else gc.lineTo(x, y);
                    }
                    gc.stroke();
                }
            }
        };
        backgroundAnimation.start();

        // Redraw when canvas size changes
        backgroundCanvas.widthProperty().addListener((obs, oldVal, newVal) -> {
            // Canvas will redraw on next animation frame
        });
        backgroundCanvas.heightProperty().addListener((obs, oldVal, newVal) -> {
            // Canvas will redraw on next animation frame
        });

        // Create main container with fade-in animation
        HBox container = new HBox(40);
        container.setAlignment(Pos.CENTER);
        container.setPrefWidth(800);
        container.setMaxWidth(800);
        container.setPadding(new Insets(30));
        container.setOpacity(0);

        // Background rectangle with glow effect (wider for landscape)
        Rectangle backgroundRect = new Rectangle(800, 450);
        backgroundRect.setFill(Color.rgb(0, 0, 0, 0.6));
        backgroundRect.setArcWidth(20);
        backgroundRect.setArcHeight(20);

        DropShadow glow = new DropShadow();
        glow.setColor(Color.rgb(0, 255, 255, 0.5));
        glow.setRadius(20);
        glow.setSpread(0.3);
        backgroundRect.setEffect(glow);

        // Pulsing glow animation
        Timeline glowPulse = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(glow.radiusProperty(), 20)),
                new KeyFrame(Duration.seconds(2), new KeyValue(glow.radiusProperty(), 30)),
                new KeyFrame(Duration.seconds(4), new KeyValue(glow.radiusProperty(), 20))
        );
        glowPulse.setCycleCount(Timeline.INDEFINITE);
        glowPulse.play();

        // LEFT SIDE - Profile Picture Section
        VBox leftSection = new VBox(20);
        leftSection.setAlignment(Pos.CENTER);
        leftSection.setPadding(new Insets(20));
        leftSection.setPrefWidth(300);

        // Animated title
        Label title = new Label("Register");
        title.setStyle("-fx-text-fill: #FFFFFF; -fx-font-family: 'Arial Black'; -fx-font-size: 32; -fx-font-weight: bold;");
        title.setOpacity(0);
        title.setTranslateY(-20);

        DropShadow titleGlow = new DropShadow();
        titleGlow.setColor(Color.rgb(0, 255, 255, 0.8));
        titleGlow.setRadius(15);
        title.setEffect(titleGlow);

        // Profile picture with animations
        Circle backgroundCircle = new Circle(75);
        backgroundCircle.setFill(Color.rgb(40, 45, 55, 0.8));

        ImageView profilePreview = new ImageView();
        profilePreview.setFitWidth(150);
        profilePreview.setFitHeight(150);
        Circle clipCircle = new Circle(75, 75, 75);
        profilePreview.setClip(clipCircle);

        Circle outlineCircle = new Circle(75, 75, 75);
        outlineCircle.setFill(Color.TRANSPARENT);
        outlineCircle.setStroke(Color.rgb(0, 255, 255, 0.8));
        outlineCircle.setStrokeWidth(3);

// Rotating outline animation
        RotateTransition rotateOutline = new RotateTransition(Duration.seconds(3), outlineCircle);
        rotateOutline.setByAngle(360);
        rotateOutline.setCycleCount(Timeline.INDEFINITE);
        rotateOutline.setInterpolator(Interpolator.LINEAR);
        rotateOutline.play();

        File defaultProfilePic = new File("resources/images/default_profile.png");
        if (defaultProfilePic.exists()) {
            profilePreview.setImage(new Image(defaultProfilePic.toURI().toString()));
        }

// Stack: background → image → outline
        StackPane profileStack = new StackPane();
        profileStack.getChildren().addAll(backgroundCircle, profilePreview, outlineCircle);
        profileStack.setOpacity(0);
        profileStack.setScaleX(0.5);
        profileStack.setScaleY(0.5);

        File[] selectedFile = {null};
        byte[][] croppedBytes = {null};

        Button uploadButton = new Button("Upload Picture");
        uploadButton.setStyle(
                "-fx-background-color: linear-gradient(to right, #FFA500, #FF8C00);" +
                        "-fx-text-fill: white;" +
                        "-fx-font-family: 'Arial Black', 'Arial';" +
                        "-fx-font-size: 14;" +
                        "-fx-font-weight: bold;" +
                        "-fx-padding: 10 30;" +
                        "-fx-background-radius: 12;" +
                        "-fx-cursor: hand;" +
                        "-fx-effect: dropshadow(gaussian, rgba(255, 165, 0, 0.5), 12, 0.6, 0, 0);"
        );
        uploadButton.setPrefWidth(200);
        uploadButton.setMinHeight(42);
        uploadButton.setMaxHeight(42);
        uploadButton.setOpacity(0);
        uploadButton.setTranslateY(20);

        // Hover effect
        uploadButton.setOnMouseEntered(e -> {
            uploadButton.setStyle(
                    "-fx-background-color: linear-gradient(to right, #FFB520, #FFA020);" +
                            "-fx-text-fill: white;" +
                            "-fx-font-family: 'Arial Black', 'Arial';" +
                            "-fx-font-size: 14;" +
                            "-fx-font-weight: bold;" +
                            "-fx-padding: 10 30;" +
                            "-fx-background-radius: 12;" +
                            "-fx-cursor: hand;" +
                            "-fx-effect: dropshadow(gaussian, rgba(255, 165, 0, 0.8), 15, 0.7, 0, 2);"
            );
            ScaleTransition st = new ScaleTransition(Duration.millis(100), uploadButton);
            st.setToX(1.05);
            st.setToY(1.05);
            st.play();
        });

        uploadButton.setOnMouseExited(e -> {
            uploadButton.setStyle(
                    "-fx-background-color: linear-gradient(to right, #FFA500, #FF8C00);" +
                            "-fx-text-fill: white;" +
                            "-fx-font-family: 'Arial Black', 'Arial';" +
                            "-fx-font-size: 14;" +
                            "-fx-font-weight: bold;" +
                            "-fx-padding: 10 30;" +
                            "-fx-background-radius: 12;" +
                            "-fx-cursor: hand;" +
                            "-fx-effect: dropshadow(gaussian, rgba(255, 165, 0, 0.5), 12, 0.6, 0, 0);"
            );
            ScaleTransition st = new ScaleTransition(Duration.millis(100), uploadButton);
            st.setToX(1.0);
            st.setToY(1.0);
            st.play();
        });

        // Press effect
        uploadButton.setOnMousePressed(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(50), uploadButton);
            st.setToX(0.98);
            st.setToY(0.98);
            st.play();
        });

        uploadButton.setOnMouseReleased(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(50), uploadButton);
            st.setToX(1.05);
            st.setToY(1.05);
            st.play();
        });

        uploadButton.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
            );
            File file = fileChooser.showOpenDialog(primaryStage);
            if (file != null) {
                try {
                    // Test if the image can be read
                    BufferedImage testRead = ImageIO.read(file);
                    if (testRead == null) {
                        new Alert(Alert.AlertType.ERROR,
                                "Unable to read this image file. Please try a different image format (PNG or JPEG).")
                                .show();
                        return;
                    }
                    selectedFile[0] = file;
                    showImageCropDialog(file, profilePreview, croppedBytes);
                } catch (Exception ex) {
                    new Alert(Alert.AlertType.ERROR,
                            "Failed to load image: " + ex.getMessage() +
                                    "\n\nPlease try a PNG or JPEG file instead.")
                            .show();
                }
            }
        });

        leftSection.getChildren().addAll(title, profileStack, uploadButton);

        // RIGHT SIDE - Form Fields
        VBox rightSection = new VBox(15);
        rightSection.setAlignment(Pos.CENTER);
        rightSection.setPadding(new Insets(20));
        rightSection.setPrefWidth(400);

        // Focus glow effect for text fields
        DropShadow focusGlow = new DropShadow();
        focusGlow.setColor(Color.rgb(0, 255, 255, 0.6));
        focusGlow.setRadius(10);

        // FIXED: Base style with border already included to prevent layout shift
        String baseFieldStyle = "-fx-background-color: rgba(255, 255, 255, 0.9); -fx-text-fill: #000000; -fx-font-size: 14; -fx-padding: 10; -fx-background-radius: 10; -fx-border-color: transparent; -fx-border-width: 2; -fx-border-radius: 10;";
        String focusedFieldStyle = "-fx-background-color: rgba(255, 255, 255, 1.0); -fx-text-fill: #000000; -fx-font-size: 14; -fx-padding: 10; -fx-background-radius: 10; -fx-border-color: #00FFFF; -fx-border-width: 2; -fx-border-radius: 10;";

        // Username field with slide-in animation
        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        usernameField.setStyle(baseFieldStyle);
        usernameField.setPrefWidth(300);
        usernameField.setMaxWidth(300);
        usernameField.setOpacity(0);
        usernameField.setTranslateX(50);

        usernameField.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            if (isNowFocused) {
                usernameField.setStyle(focusedFieldStyle);
                usernameField.setEffect(focusGlow);
            } else {
                usernameField.setStyle(baseFieldStyle);
                usernameField.setEffect(null);
            }
        });

        usernameField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("^[a-zA-Z0-9_]*$")) {
                usernameField.setText(oldValue);
            } else if (newValue.length() > 20) {
                usernameField.setText(oldValue);
            }
        });

        // Password field with slide-in animation
        StackPane passwordContainer = new StackPane();
        passwordContainer.setPrefWidth(300);
        passwordContainer.setMaxWidth(300);
        passwordContainer.setMinWidth(300);
        passwordContainer.setOpacity(0);
        passwordContainer.setTranslateX(50);

        String basePasswordStyle = "-fx-background-color: rgba(255, 255, 255, 0.9); -fx-text-fill: #000000; -fx-font-size: 14; -fx-padding: 10 45 10 10; -fx-background-radius: 10; -fx-border-color: transparent; -fx-border-width: 2; -fx-border-radius: 10;";
        String focusedPasswordStyle = "-fx-background-color: rgba(255, 255, 255, 1.0); -fx-text-fill: #000000; -fx-font-size: 14; -fx-padding: 10 45 10 10; -fx-background-radius: 10; -fx-border-color: #00FFFF; -fx-border-width: 2; -fx-border-radius: 10;";

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setStyle(basePasswordStyle);
        passwordField.setPrefWidth(300);
        passwordField.setMaxWidth(300);
        passwordField.setMinWidth(300);

        TextField passwordTextField = new TextField();
        passwordTextField.setPromptText("Password");
        passwordTextField.setStyle(basePasswordStyle);
        passwordTextField.setPrefWidth(300);
        passwordTextField.setMaxWidth(300);
        passwordTextField.setMinWidth(300);
        passwordTextField.setVisible(false);
        passwordTextField.setManaged(false);

        passwordTextField.textProperty().bindBidirectional(passwordField.textProperty());

        Button togglePasswordButton = new Button("👁");
        togglePasswordButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #000000; -fx-font-size: 16; -fx-cursor: hand; -fx-padding: 5;");
        togglePasswordButton.setPrefSize(30, 30);
        togglePasswordButton.setFocusTraversable(false);

        StackPane.setAlignment(togglePasswordButton, Pos.CENTER_RIGHT);
        StackPane.setMargin(togglePasswordButton, new Insets(0, 10, 0, 0));

        togglePasswordButton.setOnAction(event -> {
            if (passwordField.isVisible()) {
                passwordField.setVisible(false);
                passwordField.setManaged(false);
                passwordTextField.setVisible(true);
                passwordTextField.setManaged(true);
                togglePasswordButton.setText("🙈");
            } else {
                passwordTextField.setVisible(false);
                passwordTextField.setManaged(false);
                passwordField.setVisible(true);
                passwordField.setManaged(true);
                togglePasswordButton.setText("👁");
            }
            root.requestFocus();
        });

        passwordField.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            if (isNowFocused) {
                passwordField.setStyle(focusedPasswordStyle);
                passwordTextField.setStyle(focusedPasswordStyle);
                passwordField.setEffect(focusGlow);
                passwordTextField.setEffect(focusGlow);
            } else {
                passwordField.setStyle(basePasswordStyle);
                passwordTextField.setStyle(basePasswordStyle);
                passwordField.setEffect(null);
                passwordTextField.setEffect(null);
            }
        });

        passwordTextField.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            if (isNowFocused) {
                passwordField.setStyle(focusedPasswordStyle);
                passwordTextField.setStyle(focusedPasswordStyle);
                passwordField.setEffect(focusGlow);
                passwordTextField.setEffect(focusGlow);
            } else {
                passwordField.setStyle(basePasswordStyle);
                passwordTextField.setStyle(basePasswordStyle);
                passwordField.setEffect(null);
                passwordTextField.setEffect(null);
            }
        });

        passwordField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > 32) {
                passwordField.setText(oldValue);
            }
        });

        passwordTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > 32) {
                passwordTextField.setText(oldValue);
            }
        });

        passwordContainer.getChildren().addAll(passwordField, passwordTextField, togglePasswordButton);

        // Confirm Password field with slide-in animation
        PasswordField confirmPasswordField = new PasswordField();
        confirmPasswordField.setPromptText("Confirm Password");
        confirmPasswordField.setStyle(baseFieldStyle);
        confirmPasswordField.setPrefWidth(300);
        confirmPasswordField.setMaxWidth(300);
        confirmPasswordField.setOpacity(0);
        confirmPasswordField.setTranslateX(50);

        confirmPasswordField.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            if (isNowFocused) {
                confirmPasswordField.setStyle(focusedFieldStyle);
                confirmPasswordField.setEffect(focusGlow);
            } else {
                confirmPasswordField.setStyle(baseFieldStyle);
                confirmPasswordField.setEffect(null);
            }
        });

        confirmPasswordField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > 32) {
                confirmPasswordField.setText(oldValue);
            }
        });

        // Spacer for better spacing
        Region spacer = new Region();
        spacer.setPrefHeight(10);

        // Animated buttons
        Button registerButton = new Button("Register");
        registerButton.setStyle(
                "-fx-background-color: linear-gradient(to right, #00D9FF, #0099FF);" +
                        "-fx-text-fill: white;" +
                        "-fx-font-family: 'Arial Black', 'Arial';" +
                        "-fx-font-size: 16;" +
                        "-fx-font-weight: bold;" +
                        "-fx-padding: 12 40;" +
                        "-fx-background-radius: 12;" +
                        "-fx-cursor: hand;" +
                        "-fx-effect: dropshadow(gaussian, rgba(0, 217, 255, 0.6), 15, 0.6, 0, 0);"
        );
        registerButton.setPrefWidth(300);
        registerButton.setMinHeight(45);
        registerButton.setMaxHeight(45);
        registerButton.setOpacity(0);
        registerButton.setTranslateX(50);

        // Hover effect
        registerButton.setOnMouseEntered(e -> {
            registerButton.setStyle(
                    "-fx-background-color: linear-gradient(to right, #00EEFF, #00AAFF);" +
                            "-fx-text-fill: white;" +
                            "-fx-font-family: 'Arial Black', 'Arial';" +
                            "-fx-font-size: 16;" +
                            "-fx-font-weight: bold;" +
                            "-fx-padding: 12 40;" +
                            "-fx-background-radius: 12;" +
                            "-fx-cursor: hand;" +
                            "-fx-effect: dropshadow(gaussian, rgba(0, 217, 255, 0.9), 20, 0.7, 0, 2);"
            );
            ScaleTransition st = new ScaleTransition(Duration.millis(100), registerButton);
            st.setToX(1.05);
            st.setToY(1.05);
            st.play();
        });

        registerButton.setOnMouseExited(e -> {
            if (!registerButton.isDisabled()) {
                registerButton.setStyle(
                        "-fx-background-color: linear-gradient(to right, #00D9FF, #0099FF);" +
                                "-fx-text-fill: white;" +
                                "-fx-font-family: 'Arial Black', 'Arial';" +
                                "-fx-font-size: 16;" +
                                "-fx-font-weight: bold;" +
                                "-fx-padding: 12 40;" +
                                "-fx-background-radius: 12;" +
                                "-fx-cursor: hand;" +
                                "-fx-effect: dropshadow(gaussian, rgba(0, 217, 255, 0.6), 15, 0.6, 0, 0);"
                );
            }
            ScaleTransition st = new ScaleTransition(Duration.millis(100), registerButton);
            st.setToX(1.0);
            st.setToY(1.0);
            st.play();
        });

        // Press effect
        registerButton.setOnMousePressed(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(50), registerButton);
            st.setToX(0.98);
            st.setToY(0.98);
            st.play();
        });

        registerButton.setOnMouseReleased(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(50), registerButton);
            st.setToX(1.05);
            st.setToY(1.05);
            st.play();
        });

        registerButton.setOnAction(event -> {
            if (isNavigating) return; // Prevent spam clicking

            String user = usernameField.getText().trim();
            String pass = passwordField.getText().trim();
            String confirmPass = confirmPasswordField.getText().trim();

            if (user.isEmpty() || pass.isEmpty() || confirmPass.isEmpty()) {
                shakeNode(rightSection);
                new Alert(AlertType.ERROR, "All fields are required.").show();
                return;
            }
            if (user.length() < 3 || user.length() > 20) {
                shakeNode(usernameField);
                new Alert(AlertType.ERROR, "Username must be between 3 and 20 characters.").show();
                return;
            }
            if (pass.length() < 8 || pass.length() > 32) {
                shakeNode(passwordField);
                new Alert(AlertType.ERROR, "Password must be between 8 and 32 characters long.").show();
                return;
            }
            if (!pass.equals(confirmPass)) {
                shakeNode(confirmPasswordField);
                new Alert(AlertType.ERROR, "Passwords do not match.").show();
                return;
            }

            if (registerButton.isDisabled()) return;

            isNavigating = true;
            registerButton.setDisable(true);
            // Don't worry about disabling goToLoginButton - the navigation flag prevents spam

            byte[] profilePicBytes;
            try {
                if (croppedBytes[0] != null) {
                    // Use the cropped bytes from the crop dialog (already 300x300 PNG)
                    profilePicBytes = croppedBytes[0];
                } else if (selectedFile[0] != null) {
                    // Fallback to original file if somehow crop dialog didn't run
                    profilePicBytes = Files.readAllBytes(selectedFile[0].toPath());
                } else {
                    // Use default profile picture
                    File defaultFile = new File("resources/images/default_profile.png");
                    profilePicBytes = defaultFile.exists()
                            ? Files.readAllBytes(defaultFile.toPath())
                            : getDefaultProfilePicBytes();
                }
            } catch (IOException e) {
                new Alert(AlertType.ERROR, "Failed to read image.").showAndWait();
                registerButton.setDisable(false);
                isNavigating = false;
                return;
            }

            // Store profilePicBytes in a final variable for lambda
            byte[] finalProfilePicBytes = profilePicBytes;

            // CHECK MAINTENANCE BEFORE REGISTRATION
            backgroundAnimation.stop();
            showLoadingScreen();
            checkMaintenanceBeforeLogin(() -> {
                // If maintenance is off, proceed with registration
                signupWithSupabase(user, pass, finalProfilePicBytes, registerButton);
            });
        });

        Button goToLoginButton = new Button("Go to Login");
        goToLoginButton.setStyle(
                "-fx-background-color: transparent;" +
                        "-fx-border-color: #00D9FF;" +
                        "-fx-border-width: 2;" +
                        "-fx-border-radius: 12;" +
                        "-fx-background-radius: 12;" +
                        "-fx-text-fill: #00D9FF;" +
                        "-fx-font-family: 'Arial', 'Arial';" +
                        "-fx-font-size: 14;" +
                        "-fx-font-weight: 600;" +
                        "-fx-padding: 12 40;" +
                        "-fx-cursor: hand;"
        );
        goToLoginButton.setPrefWidth(300);
        goToLoginButton.setMinHeight(45);
        goToLoginButton.setMaxHeight(45);
        goToLoginButton.setOpacity(0);
        goToLoginButton.setTranslateX(50);

        // Hover effect
        goToLoginButton.setOnMouseEntered(e -> {
            goToLoginButton.setStyle(
                    "-fx-background-color: rgba(0, 217, 255, 0.15);" +
                            "-fx-border-color: #00EEFF;" +
                            "-fx-border-width: 2;" +
                            "-fx-border-radius: 12;" +
                            "-fx-background-radius: 12;" +
                            "-fx-text-fill: #00EEFF;" +
                            "-fx-font-family: 'Arial', 'Arial';" +
                            "-fx-font-size: 14;" +
                            "-fx-font-weight: 600;" +
                            "-fx-padding: 12 40;" +
                            "-fx-cursor: hand;" +
                            "-fx-effect: dropshadow(gaussian, rgba(0, 217, 255, 0.4), 10, 0.5, 0, 0);"
            );
            ScaleTransition st = new ScaleTransition(Duration.millis(100), goToLoginButton);
            st.setToX(1.03);
            st.setToY(1.03);
            st.play();
        });

        goToLoginButton.setOnMouseExited(e -> {
            goToLoginButton.setStyle(
                    "-fx-background-color: transparent;" +
                            "-fx-border-color: #00D9FF;" +
                            "-fx-border-width: 2;" +
                            "-fx-border-radius: 12;" +
                            "-fx-background-radius: 12;" +
                            "-fx-text-fill: #00D9FF;" +
                            "-fx-font-family: 'Arial', 'Arial';" +
                            "-fx-font-size: 14;" +
                            "-fx-font-weight: 600;" +
                            "-fx-padding: 12 40;" +
                            "-fx-cursor: hand;"
            );
            ScaleTransition st = new ScaleTransition(Duration.millis(100), goToLoginButton);
            st.setToX(1.0);
            st.setToY(1.0);
            st.play();
        });

        // Press effect
        goToLoginButton.setOnMousePressed(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(50), goToLoginButton);
            st.setToX(0.98);
            st.setToY(0.98);
            st.play();
        });

        goToLoginButton.setOnMouseReleased(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(50), goToLoginButton);
            st.setToX(1.03);
            st.setToY(1.03);
            st.play();
        });

        goToLoginButton.setOnAction(event -> {
            if (isNavigating) return; // Prevent spam clicking
            isNavigating = true;

            registerButton.setDisable(true);
            goToLoginButton.setDisable(true);

            animateOutroAndTransition(container, rightSection, registerButton, goToLoginButton,
                    backgroundAnimation, () -> {
                        showLoginScreen();
                        isNavigating = false; // Reset after navigation completes
                    });
        });

        rightSection.getChildren().addAll(usernameField, passwordContainer, confirmPasswordField, spacer, registerButton, goToLoginButton);

        // Add sections to container
        container.getChildren().addAll(leftSection, rightSection);

        StackPane formStack = new StackPane();
        formStack.getChildren().addAll(backgroundRect, container);

        VBox mainContainer = new VBox();
        mainContainer.setAlignment(Pos.CENTER);
        mainContainer.getChildren().add(formStack);

        // Layer the animated background and form
        StackPane mainStack = new StackPane();
        mainStack.getChildren().addAll(backgroundCanvas, mainContainer);

        // FIXED: Allow ANY click outside text fields to remove focus
        mainStack.setOnMousePressed(event -> {
            // Check if the click target is NOT one of the input fields
            if (!(event.getTarget() instanceof TextField) &&
                    !(event.getTarget() instanceof PasswordField)) {
                root.requestFocus();
            }
        });

        root.setCenter(mainStack);

        // Sequential entrance animations
        Timeline entranceAnimation = new Timeline(
                // Container fade in
                new KeyFrame(Duration.ZERO,
                        new KeyValue(container.opacityProperty(), 0)),
                new KeyFrame(Duration.millis(300),
                        new KeyValue(container.opacityProperty(), 1)),

                // Title animation
                new KeyFrame(Duration.millis(200),
                        new KeyValue(title.opacityProperty(), 0),
                        new KeyValue(title.translateYProperty(), -20)),
                new KeyFrame(Duration.millis(600),
                        new KeyValue(title.opacityProperty(), 1, Interpolator.EASE_OUT),
                        new KeyValue(title.translateYProperty(), 0, Interpolator.EASE_OUT)),

                // Profile picture
                new KeyFrame(Duration.millis(400),
                        new KeyValue(profileStack.opacityProperty(), 0),
                        new KeyValue(profileStack.scaleXProperty(), 0.5),
                        new KeyValue(profileStack.scaleYProperty(), 0.5)),
                new KeyFrame(Duration.millis(800),
                        new KeyValue(profileStack.opacityProperty(), 1, Interpolator.EASE_OUT),
                        new KeyValue(profileStack.scaleXProperty(), 1, Interpolator.EASE_OUT),
                        new KeyValue(profileStack.scaleYProperty(), 1, Interpolator.EASE_OUT)),

                // Upload button
                new KeyFrame(Duration.millis(500),
                        new KeyValue(uploadButton.opacityProperty(), 0),
                        new KeyValue(uploadButton.translateYProperty(), 20)),
                new KeyFrame(Duration.millis(900),
                        new KeyValue(uploadButton.opacityProperty(), 1, Interpolator.EASE_OUT),
                        new KeyValue(uploadButton.translateYProperty(), 0, Interpolator.EASE_OUT)),

                // Username field
                new KeyFrame(Duration.millis(400),
                        new KeyValue(usernameField.opacityProperty(), 0),
                        new KeyValue(usernameField.translateXProperty(), 50)),
                new KeyFrame(Duration.millis(800),
                        new KeyValue(usernameField.opacityProperty(), 1, Interpolator.EASE_OUT),
                        new KeyValue(usernameField.translateXProperty(), 0, Interpolator.EASE_OUT)),

                // Password field
                new KeyFrame(Duration.millis(500),
                        new KeyValue(passwordContainer.opacityProperty(), 0),
                        new KeyValue(passwordContainer.translateXProperty(), 50)),
                new KeyFrame(Duration.millis(900),
                        new KeyValue(passwordContainer.opacityProperty(), 1, Interpolator.EASE_OUT),
                        new KeyValue(passwordContainer.translateXProperty(), 0, Interpolator.EASE_OUT)),

                // Confirm Password field
                new KeyFrame(Duration.millis(600),
                        new KeyValue(confirmPasswordField.opacityProperty(), 0),
                        new KeyValue(confirmPasswordField.translateXProperty(), 50)),
                new KeyFrame(Duration.millis(1000),
                        new KeyValue(confirmPasswordField.opacityProperty(), 1, Interpolator.EASE_OUT),
                        new KeyValue(confirmPasswordField.translateXProperty(), 0, Interpolator.EASE_OUT)),

                // Register button
                new KeyFrame(Duration.millis(700),
                        new KeyValue(registerButton.opacityProperty(), 0),
                        new KeyValue(registerButton.translateXProperty(), 50)),
                new KeyFrame(Duration.millis(1100),
                        new KeyValue(registerButton.opacityProperty(), 1, Interpolator.EASE_OUT),
                        new KeyValue(registerButton.translateXProperty(), 0, Interpolator.EASE_OUT)),

                // Login button
                new KeyFrame(Duration.millis(800),
                        new KeyValue(goToLoginButton.opacityProperty(), 0),
                        new KeyValue(goToLoginButton.translateXProperty(), 50)),
                new KeyFrame(Duration.millis(1200),
                        new KeyValue(goToLoginButton.opacityProperty(), 1, Interpolator.EASE_OUT),
                        new KeyValue(goToLoginButton.translateXProperty(), 0, Interpolator.EASE_OUT))
        );
        entranceAnimation.play();

        startMaintenanceMonitoring();
    }

    private void animateOutroAndTransition(Node container, VBox loginOrRegisterBox,
                                           Button button1, Button button2,
                                           AnimationTimer backgroundAnimation,
                                           Runnable nextScreenAction) {
        // Create outro animation timeline
        Timeline outroAnimation = new Timeline();

        // Animate all elements out in reverse order
        if (loginOrRegisterBox.getChildren().size() > 0) {
            for (int i = 0; i < loginOrRegisterBox.getChildren().size(); i++) {
                Node child = loginOrRegisterBox.getChildren().get(i);
                int delay = (loginOrRegisterBox.getChildren().size() - i - 1) * 80;

                outroAnimation.getKeyFrames().addAll(
                        new KeyFrame(Duration.millis(delay),
                                new KeyValue(child.opacityProperty(), 1)),
                        new KeyFrame(Duration.millis(delay + 300),
                                new KeyValue(child.opacityProperty(), 0, Interpolator.EASE_IN))
                );

                // Add slide out effect - FIXED: Include StackPane
                if (child instanceof TextField || child instanceof PasswordField ||
                        child instanceof Button || child instanceof StackPane) {
                    outroAnimation.getKeyFrames().addAll(
                            new KeyFrame(Duration.millis(delay),
                                    new KeyValue(child.translateXProperty(), 0)),
                            new KeyFrame(Duration.millis(delay + 300),
                                    new KeyValue(child.translateXProperty(), -30, Interpolator.EASE_IN))
                    );
                }
            }
        }

        // Fade out container
        outroAnimation.getKeyFrames().addAll(
                new KeyFrame(Duration.millis(400),
                        new KeyValue(container.opacityProperty(), 1),
                        new KeyValue(container.scaleXProperty(), 1),
                        new KeyValue(container.scaleYProperty(), 1)),
                new KeyFrame(Duration.millis(700),
                        new KeyValue(container.opacityProperty(), 0, Interpolator.EASE_IN),
                        new KeyValue(container.scaleXProperty(), 0.95, Interpolator.EASE_IN),
                        new KeyValue(container.scaleYProperty(), 0.95, Interpolator.EASE_IN))
        );

        outroAnimation.setOnFinished(e -> {
            backgroundAnimation.stop();
            nextScreenAction.run();
        });

        outroAnimation.play();
    }


    // Shake animation for validation errors
    private void shakeNode(Node node) {
        TranslateTransition tt = new TranslateTransition(Duration.millis(50), node);
        tt.setFromX(0);
        tt.setByX(10);
        tt.setCycleCount(6);
        tt.setAutoReverse(true);
        tt.play();
    }

    // Fade out transition
    public void fadeOutAndTransition(Runnable nextScreen) {
        FadeTransition fade = new FadeTransition(Duration.millis(300), root.getCenter());
        fade.setFromValue(1.0);
        fade.setToValue(0.0);
        fade.setOnFinished(e -> nextScreen.run());
        fade.play();
    }

    public void clearUserProgressCache() {
        unlockedLevels.clear();
        // Also clear any other per-user collections/maps if you have them
        // e.g. bestScores.clear(), achievements.clear(), etc.
    }

    // NEW METHOD 1: Main signup — now checks for case-insensitive duplicates
    private void signupWithSupabase(String username, String password, byte[] profilePicBytes, Button registerButton) {
        executor.submit(() -> {
            try {
                // 1. Check if username exists
                String encodedUsername = URLEncoder.encode(username, StandardCharsets.UTF_8);
                String checkUrl = SUPABASE_URL + "/rest/v1/profiles?select=username&username=ilike." + encodedUsername;

                HttpRequest checkRequest = HttpRequest.newBuilder()
                        .uri(URI.create(checkUrl))
                        .header("apikey", SUPABASE_ANON_KEY)
                        .header("Authorization", "Bearer " + SUPABASE_ANON_KEY)
                        .GET()
                        .build();

                HttpResponse<String> checkResponse = httpClient.send(checkRequest, HttpResponse.BodyHandlers.ofString());

                if (checkResponse.statusCode() == 200) {
                    JsonArray result = gson.fromJson(checkResponse.body(), JsonArray.class);
                    if (result != null && result.size() > 0) {
                        // Username taken
                        Platform.runLater(() -> {
                            registerButton.setDisable(false);
                            new Alert(Alert.AlertType.ERROR,
                                    "Username \"" + username + "\" is already taken."
                            ).showAndWait();
                        });
                        return;
                    }
                }

                // ONLY NOW — username is available → show loading screen
                Platform.runLater(this::showLoadingScreen);

                // Then proceed
                proceedWithRegistration(username, password, profilePicBytes, registerButton);

            } catch (Exception e) {
                Platform.runLater(() -> {
                    registerButton.setDisable(false);
                    new Alert(Alert.AlertType.ERROR, "Connection failed. Please try again.").showAndWait();
                });
            }
        });
    }

    // NEW METHOD 2: Does the actual registration (only called if username is free)
    private void proceedWithRegistration(String username, String password, byte[] profilePicBytes, Button registerButton) {
        executor.submit(() -> {
            try {
                String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(12));

                String rawBase64 = (profilePicBytes != null && profilePicBytes.length > 0)
                        ? Base64.getEncoder().encodeToString(profilePicBytes)
                        : Base64.getEncoder().encodeToString(getDefaultProfilePicBytes());

                Map<String, Object> payload = new HashMap<>();
                payload.put("username", username);
                payload.put("password", hashedPassword);
                payload.put("profile_picture", null);
                payload.put("profile_picture_bytes", rawBase64);
                payload.put("unlocked_levels", "1");
                payload.put("effects_muted", false);
                payload.put("music_muted", false);
                payload.put("volume", 1.0);

                String json = gson.toJson(payload);

                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(SUPABASE_URL + "/rest/v1/profiles"))
                        .header("apikey", SUPABASE_ANON_KEY)
                        .header("Authorization", "Bearer " + SUPABASE_ANON_KEY)
                        .header("Content-Type", "application/json")
                        .header("Prefer", "return=representation")
                        .POST(HttpRequest.BodyPublishers.ofString(json))
                        .build();

                HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

                Platform.runLater(() -> {
                    if (response.statusCode() == 201 || response.statusCode() == 200) {
                        currentUser = username;
                        saveLoginToken(username);

                        // KEEP LOADING SCREEN VISIBLE - load profile data first
                        loadFullProfileFromSupabase(username, () -> {
                            // Now that profile is loaded, ensure level 1 is unlocked
                            if (!unlockedLevels.contains(1)) {
                                unlockedLevels.add(1);
                                saveProgress();
                            }

                            // NOW hide loading and show level select with all data ready
                            Platform.runLater(() -> {
                                hideLoadingScreen();
                                showLevelSelectScreen();
                                startMaintenanceMonitoring();
                            });
                        });

                    } else {
                        registerButton.setDisable(false);
                        hideLoadingScreen();
                        isNavigating = false; // Reset on registration failure
                        String error = response.body() != null ? response.body() : "Unknown error";
                        new Alert(Alert.AlertType.ERROR, "Registration failed:\n" + error).showAndWait();
                        showRegisterScreen(); // Return to register screen
                    }
                });

            } catch (java.net.ConnectException | java.nio.channels.UnresolvedAddressException e) {
                System.out.println("[REGISTER] Connection error detected: " + e.getMessage());
                Platform.runLater(() -> {
                    registerButton.setDisable(false);
                    hideLoadingScreen();
                });
            } catch (Exception e) {
                Platform.runLater(() -> {
                    registerButton.setDisable(false);
                    hideLoadingScreen();
                    new Alert(Alert.AlertType.ERROR, "Connection failed: " + e.getMessage()).showAndWait();
                });
            }
        });
    }

    private Image safeLoadProfileImage(String dirtyBase64) {
        if (dirtyBase64 == null || dirtyBase64.trim().isEmpty()) {
            return getDefaultProfileImage();
        }

        String base64 = dirtyBase64.trim();

        // 1. Remove Data URL prefix if present
        if (base64.contains(",")) {
            base64 = base64.substring(base64.lastIndexOf(",") + 1);
        }

        // 2. Fix JSON-escaped slashes:  \/  →  /
        base64 = base64.replace("\\/", "/");

        // 3. Remove ALL whitespace, newlines, and any stray characters
        base64 = base64.replaceAll("\\s+", "");

        // 4. Final sanity check
        if (base64.isEmpty() || base64.length() < 50) {
            return getDefaultProfileImage();
        }

        try {
            byte[] bytes = Base64.getDecoder().decode(base64);
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
            Image img = new Image(bais, 100, 100, true, true);
            if (img.isError()) {
                return getDefaultProfileImage();
            }
            return img;
        } catch (Exception e) {
            System.err.println("Final fallback: corrupted Base64 for profile picture");
            return getDefaultProfileImage();
        }
    }

    private Image getDefaultProfileImage() {
        File f = new File("resources/images/default_profile.png");
        if (f.exists()) {
            return new Image(f.toURI().toString(), 100, 100, true, true);
        }
        return new Image(new ByteArrayInputStream(getDefaultProfilePicBytes()), 100, 100, true, true);
    }

    private String getDeviceFingerprint() {
        try {
            // Combine multiple system properties to create unique device ID
            String os = System.getProperty("os.name", "unknown");
            String version = System.getProperty("os.version", "unknown");
            String arch = System.getProperty("os.arch", "unknown");
            String user = System.getProperty("user.name", "unknown");
            String home = System.getProperty("user.home", "unknown");

            String macAddress = "";
            try {
                // Try to get MAC address, but don't fail if unavailable
                InetAddress ip = InetAddress.getLocalHost();
                NetworkInterface network = NetworkInterface.getByInetAddress(ip);
                if (network != null) {
                    byte[] mac = network.getHardwareAddress();
                    if (mac != null) {
                        macAddress = Arrays.toString(mac);
                    }
                }
            } catch (Exception e) {
                // MAC address not available, continue without it
                System.out.println("MAC address unavailable, using system properties only");
            }

            String combined = os + "|" + version + "|" + arch + "|" + user + "|" + home + "|" + macAddress;

            // Hash it to create consistent fingerprint
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(combined.getBytes("UTF-8"));
            return Base64.getEncoder().encodeToString(hash);
        } catch (Exception e) {
            System.err.println("Failed to generate device fingerprint: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public void saveLoginToken(String username) {
        try {
            String deviceFingerprint = getDeviceFingerprint();
            if (deviceFingerprint == null) {
                System.err.println("Cannot save token without device fingerprint");
                return;
            }

            // Combine username and device fingerprint
            String dataToEncrypt = username + "|" + deviceFingerprint;

            SecretKeySpec key = new SecretKeySpec(ENCRYPTION_KEY.getBytes("UTF-8"), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] encrypted = cipher.doFinal(dataToEncrypt.getBytes("UTF-8"));
            String token = Base64.getEncoder().encodeToString(encrypted);

            Files.createDirectories(Paths.get("resources"));
            Files.writeString(Paths.get(TOKEN_FILE), token);
            System.out.println("Login token saved for " + username + " on this device");
        } catch (Exception e) {
            System.err.println("Failed to save login token: " + e.getMessage());
        }
    }

    private String loadLoginToken() {
        try {
            if (!Files.exists(Paths.get(TOKEN_FILE))) return null;

            String token = Files.readString(Paths.get(TOKEN_FILE));
            SecretKeySpec key = new SecretKeySpec(ENCRYPTION_KEY.getBytes("UTF-8"), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] decrypted = cipher.doFinal(Base64.getDecoder().decode(token));
            String decryptedData = new String(decrypted, "UTF-8");

            // Split username and device fingerprint
            String[] parts = decryptedData.split("\\|");
            if (parts.length != 2) {
                System.err.println("Invalid token format");
                deleteLoginToken();
                return null;
            }

            String username = parts[0];
            String storedFingerprint = parts[1];
            String currentFingerprint = getDeviceFingerprint();

            // Validate device fingerprint
            if (currentFingerprint == null || !currentFingerprint.equals(storedFingerprint)) {
                System.out.println("Device fingerprint mismatch - token invalid on this device");
                deleteLoginToken();
                return null;
            }

            return username;
        } catch (Exception e) {
            System.err.println("Failed to load login token: " + e.getMessage());
            deleteLoginToken();
            return null;
        }
    }

    // Delete token (used on logout)
    public void deleteLoginToken() {
        try {
            Files.deleteIfExists(Paths.get(TOKEN_FILE));
            System.out.println("Login token deleted");
        } catch (Exception e) {
            System.err.println("Failed to delete token: " + e.getMessage());
        }
    }

    private void showLevelSelectScreen() {
        loadUserPreferencesSync();
        isNavigating = false;
        levelSelectProfileView = new ImageView(currentProfileImage);
        isOnLevelSelectScreen = true;
        extraMoves = 0;
        lossCount = 0;
        root.getChildren().clear();
        root.setStyle("-fx-background-color: radial-gradient(center 50% 50%, radius 80%, #1C2526, #0A0E26);");
        primaryStage.setTitle("Chroma Flood - Level Select");

        preloadAllLeaderboardProfilePictures();

        if (spriteCharacter == null) {
            loadSpriteCharacter();
        }

        if (currentUser != null && currentUser.equalsIgnoreCase("admin")) {
            System.out.println("[SECURITY] Blocked level select for admin user");
            showAdminPanel();
            return;
        }

        if (currentUser == null || currentUser.isEmpty()) {
            System.err.println("[SECURITY ALERT] Attempted to access Level Select without being logged in! Redirecting to login.");
            Platform.runLater(this::showLoginScreen);
            return;
        }

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

        // === ANIMATED BACKGROUND PARTICLES (RESPONSIVE) ===
        Pane backgroundPane = new Pane();
        backgroundPane.setStyle("-fx-background-color: transparent;");
// backgroundPane.setMouseTransparent(true);  // REMOVE THIS LINE!

// Bind background pane size to scene size (not stage)
        backgroundPane.prefWidthProperty().bind(root.widthProperty());
        backgroundPane.prefHeightProperty().bind(root.heightProperty());

// Create floating particles - MOVE THIS UP!
        Random random = new Random();
        List<Circle> particles = new ArrayList<>();

        // Function to redistribute all particles
        Runnable redistributeParticles = () -> {
            double currentWidth = root.getWidth() > 0 ? root.getWidth() : primaryStage.getWidth();
            double currentHeight = root.getHeight() > 0 ? root.getHeight() : primaryStage.getHeight();

            int targetParticleCount = (int) ((currentWidth * currentHeight) / 16000);
            targetParticleCount = Math.max(40, Math.min(80, targetParticleCount));

            // If no particles exist yet, create them
            if (particles.isEmpty()) {
                for (int i = 0; i < targetParticleCount; i++) {
                    Circle particle = new Circle(random.nextInt(3) + 1);
                    particle.setFill(Color.rgb(0, 255, 255, 0.3 + random.nextDouble() * 0.3));
                    particle.setEffect(new Glow(0.8));

                    // Ensure particles are distributed across ENTIRE width and height
                    // Add margin to prevent particles from being too close to edges initially
                    double margin = 50;
                    particle.setCenterX(margin + random.nextDouble() * (currentWidth - 2 * margin));
                    particle.setCenterY(margin + random.nextDouble() * (currentHeight - 2 * margin));

                    particles.add(particle);
                    backgroundPane.getChildren().add(particle);

                    // Floating animation - reduce movement to keep particles in bounds
                    TranslateTransition floatAnim = new TranslateTransition(Duration.seconds(3 + random.nextDouble() * 4), particle);
                    floatAnim.setByY(-30 - random.nextInt(60));  // Reduced from -50 to -100
                    floatAnim.setByX(-15 + random.nextInt(30));  // Reduced from -20 to +40
                    floatAnim.setCycleCount(Animation.INDEFINITE);
                    floatAnim.setAutoReverse(true);
                    floatAnim.setInterpolator(Interpolator.EASE_BOTH);
                    floatAnim.play();

                    // Pulse animation
                    FadeTransition pulse = new FadeTransition(Duration.seconds(1.5 + random.nextDouble() * 2), particle);
                    pulse.setFromValue(0.3);
                    pulse.setToValue(0.8);
                    pulse.setCycleCount(Animation.INDEFINITE);
                    pulse.setAutoReverse(true);
                    pulse.play();
                }
            } else {
                // Adjust existing particles - add or remove as needed
                int currentCount = particles.size();

                if (currentCount < targetParticleCount) {
                    // Add new particles
                    for (int i = currentCount; i < targetParticleCount; i++) {
                        Circle particle = new Circle(random.nextInt(3) + 1);
                        particle.setFill(Color.rgb(0, 255, 255, 0.3 + random.nextDouble() * 0.3));
                        particle.setEffect(new Glow(0.8));

                        double margin = 50;
                        particle.setCenterX(margin + random.nextDouble() * (currentWidth - 2 * margin));
                        particle.setCenterY(margin + random.nextDouble() * (currentHeight - 2 * margin));
                        particle.setOpacity(0); // Start invisible
                        particles.add(particle);
                        backgroundPane.getChildren().add(particle);

                        // Fade in new particle
                        FadeTransition fadeIn = new FadeTransition(Duration.millis(500), particle);
                        fadeIn.setToValue(0.3 + random.nextDouble() * 0.3);
                        fadeIn.play();

                        // Floating animation
                        TranslateTransition floatAnim = new TranslateTransition(Duration.seconds(3 + random.nextDouble() * 4), particle);
                        floatAnim.setByY(-50 - random.nextInt(100));
                        floatAnim.setByX(-20 + random.nextInt(40));
                        floatAnim.setCycleCount(Animation.INDEFINITE);
                        floatAnim.setAutoReverse(true);
                        floatAnim.setInterpolator(Interpolator.EASE_BOTH);
                        floatAnim.play();

                        // Pulse animation
                        FadeTransition pulse = new FadeTransition(Duration.seconds(1.5 + random.nextDouble() * 2), particle);
                        pulse.setFromValue(0.3);
                        pulse.setToValue(0.8);
                        pulse.setCycleCount(Animation.INDEFINITE);
                        pulse.setAutoReverse(true);
                        pulse.play();
                    }
                } else if (currentCount > targetParticleCount) {
                    // Remove excess particles with fade out
                    for (int i = currentCount - 1; i >= targetParticleCount; i--) {
                        Circle particle = particles.remove(i);
                        FadeTransition fadeOut = new FadeTransition(Duration.millis(500), particle);
                        fadeOut.setToValue(0);
                        fadeOut.setOnFinished(e -> backgroundPane.getChildren().remove(particle));
                        fadeOut.play();
                    }
                }

                // Reposition existing particles smoothly (keep within bounds)
                for (Circle particle : particles) {
                    double currentX = particle.getCenterX();
                    double currentY = particle.getCenterY();

                    // If particle is now outside bounds, move it back in
                    if (currentX > currentWidth || currentY > currentHeight) {
                        double newX = Math.min(currentX, currentWidth - 10);
                        double newY = Math.min(currentY, currentHeight - 10);

                        TranslateTransition reposition = new TranslateTransition(Duration.millis(800), particle);
                        reposition.setToX(newX - currentX);
                        reposition.setToY(newY - currentY);
                        reposition.setInterpolator(Interpolator.EASE_BOTH);
                        reposition.play();
                    }
                }
            }
        };

        // Initial particle distribution
        Platform.runLater(redistributeParticles);
        new Timeline(new KeyFrame(Duration.millis(100), e -> redistributeParticles.run())).play();

        // Listener to redistribute particles on significant size changes
        final double[] lastWidth = {0};
        final double[] lastHeight = {0};

        root.widthProperty().addListener((obs, oldVal, newVal) -> {
            double newWidth = newVal.doubleValue();
            // Only redistribute if size changed by more than 20%
            if (Math.abs(newWidth - lastWidth[0]) / lastWidth[0] > 0.2 || lastWidth[0] == 0) {
                lastWidth[0] = newWidth;
                redistributeParticles.run();
            }
        });

        root.heightProperty().addListener((obs, oldVal, newVal) -> {
            double newHeight = newVal.doubleValue();
            // Only redistribute if size changed by more than 20%
            if (Math.abs(newHeight - lastHeight[0]) / lastHeight[0] > 0.2 || lastHeight[0] == 0) {
                lastHeight[0] = newHeight;
                redistributeParticles.run();
            }
        });

        Timeline shootingStarTimer = new Timeline(new KeyFrame(Duration.seconds(6 + random.nextInt(10)), event -> {
            // Random shooting star event every 6-16 seconds
            int starCount = 1;
            double chance = random.nextDouble();

            if (chance < 0.15) {
                starCount = 3 + random.nextInt(3); // Meteor shower
            } else if (chance < 0.35) {
                starCount = 2; // Double stars
            }

            for (int i = 0; i < starCount; i++) {
                double starDelay = i * (0.1 + random.nextDouble() * 0.2);

                Timeline spawnStar = new Timeline(new KeyFrame(Duration.seconds(starDelay), e -> {
                    double currentWidth = root.getWidth() > 0 ? root.getWidth() : primaryStage.getWidth();
                    double currentHeight = root.getHeight() > 0 ? root.getHeight() : primaryStage.getHeight();

                    // Start from random position at top
                    double startX = -100 + random.nextDouble() * (currentWidth + 200);
                    double startY = -50;

                    // Create shooting star as a Pane to hold all elements
                    Pane shootingStar = new Pane();
                    shootingStar.setMouseTransparent(true);

                    // Main bright core (small white dot)
                    Circle core = new Circle(0, 0, 2);
                    core.setFill(Color.WHITE);

                    // Glowing head (larger, semi-transparent)
                    Circle glow = new Circle(0, 0, 5);
                    glow.setFill(Color.rgb(255, 255, 255, 0.6));
                    glow.setEffect(new Glow(1.0));

                    // Create elongated tail using multiple overlapping circles
                    List<Circle> tailSegments = new ArrayList<>();
                    int tailLength = 40 + random.nextInt(30); // 40-70 pixels long

                    for (int j = 0; j < tailLength; j++) {
                        double progress = (double) j / tailLength;
                        Circle segment = new Circle(0, 0, 3 * (1 - progress)); // Gets smaller towards end

                        // Color gradient: white -> cyan -> transparent
                        double opacity = (1 - progress) * 0.8;
                        if (progress < 0.3) {
                            segment.setFill(Color.rgb(255, 255, 255, opacity));
                        } else if (progress < 0.6) {
                            segment.setFill(Color.rgb(200, 230, 255, opacity));
                        } else {
                            segment.setFill(Color.rgb(150, 200, 255, opacity * 0.5));
                        }

                        segment.setEffect(new Glow(0.4 * (1 - progress)));
                        tailSegments.add(segment);
                        shootingStar.getChildren().add(segment);
                    }

                    shootingStar.getChildren().addAll(glow, core);
                    shootingStar.setTranslateX(startX);
                    shootingStar.setTranslateY(startY);
                    backgroundPane.getChildren().add(shootingStar);

                    // Calculate realistic trajectory (steep diagonal)
                    double angle = 50 + random.nextDouble() * 25; // 50-75 degrees (mostly downward)
                    double speed = 400 + random.nextDouble() * 300; // pixels per second
                    double duration = (1.0 + random.nextDouble() * 0.8); // 1.0-1.8 seconds
                    double distance = speed * duration;

                    double endX = startX + distance * Math.cos(Math.toRadians(angle));
                    double endY = startY + distance * Math.sin(Math.toRadians(angle));

                    // Rotate the entire star to match trajectory angle
                    shootingStar.setRotate(angle);

                    // Animate the star movement
                    TranslateTransition move = new TranslateTransition(Duration.seconds(duration), shootingStar);
                    move.setToX(endX);
                    move.setToY(endY);
                    move.setInterpolator(Interpolator.LINEAR);

                    // Update tail positions to create streaming effect
                    Timeline tailAnimation = new Timeline();
                    tailAnimation.setCycleCount(Animation.INDEFINITE);
                    tailAnimation.getKeyFrames().add(new KeyFrame(Duration.millis(16), ev -> {
                        // Position tail segments behind the head
                        for (int j = 0; j < tailSegments.size(); j++) {
                            Circle segment = tailSegments.get(j);
                            double offset = -(j + 3); // Stretch backwards
                            segment.setTranslateX(offset);
                        }
                    }));
                    tailAnimation.play();

                    // Fade out as it crosses the screen
                    FadeTransition fade = new FadeTransition(Duration.seconds(duration * 0.4), shootingStar);
                    fade.setToValue(0);
                    fade.setDelay(Duration.seconds(duration * 0.6));

                    // Optional: slight brightness pulse at the start (like a flare)
                    FadeTransition flare = new FadeTransition(Duration.millis(150), core);
                    flare.setFromValue(1.0);
                    flare.setToValue(0.7);
                    flare.setCycleCount(2);
                    flare.setAutoReverse(true);

                    move.play();
                    fade.play();
                    flare.play();

                    // Clean up after animation
                    move.setOnFinished(finish -> {
                        tailAnimation.stop();
                        backgroundPane.getChildren().remove(shootingStar);
                    });
                }));
                spawnStar.play();
            }
        }));
        shootingStarTimer.setCycleCount(Animation.INDEFINITE);
        shootingStarTimer.play();

        Pane levelPane = new Pane();
        double stageWidth = primaryStage.getWidth() > 0 ? primaryStage.getWidth() : 1200;
        double paneWidth = stageWidth * 0.9;
        levelPane.setPrefSize(paneWidth, 400);
        levelPane.setStyle("-fx-background-color: transparent;");

        int totalLevels = LEVEL_PATTERNS.length;
        double pathY = 200;
        double levelSpacing = (paneWidth - 200) / (totalLevels + 1);
        double startX = (paneWidth - (totalLevels - 1) * levelSpacing) / 2;
        double offsetY = 100;

        double level10XPos = startX + (totalLevels - 1) * levelSpacing;

        // === ANIMATED PATH LINE ===
        Line mainPath = new Line(startX, pathY, level10XPos, pathY);
        mainPath.setStroke(Color.rgb(0, 255, 255, 0.6));
        mainPath.setStrokeWidth(4);

        // Animated glow effect
        Glow pathGlow = new Glow(0.4);
        mainPath.setEffect(pathGlow);

        Timeline glowAnimation = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(pathGlow.levelProperty(), 0.3)),
                new KeyFrame(Duration.seconds(1.5), new KeyValue(pathGlow.levelProperty(), 0.8)),
                new KeyFrame(Duration.seconds(3), new KeyValue(pathGlow.levelProperty(), 0.3))
        );
        glowAnimation.setCycleCount(Animation.INDEFINITE);
        glowAnimation.play();

        levelPane.getChildren().add(mainPath);

        // Store level containers and connection lines for dynamic updates
        List<VBox> levelContainers = new ArrayList<>();
        List<Line> connectionLines = new ArrayList<>();

        isLevelEntranceAnimating = true;

        // === ANIMATED LEVEL NODES ===
        for (int level = 1; level <= totalLevels; level++) {
            boolean isUnlocked = unlockedLevels.contains(Integer.valueOf(level));
            double xPos = startX + (level - 1) * levelSpacing;
            double yPos = pathY - (level % 2 == 1 ? offsetY : -offsetY);

            Line connectionLine = null;
            if (isUnlocked) {
                double circleCenterX = xPos;
                double circleCenterY = yPos;
                connectionLine = new Line(circleCenterX, circleCenterY, circleCenterX, pathY);
                connectionLine.setStroke(Color.rgb(0, 255, 255, 0.5));
                connectionLine.setStrokeWidth(2);
                connectionLine.setEffect(new Glow(0.2));

                // Animate connection line drawing
                connectionLine.setEndY(circleCenterY);
                Timeline lineAnim = new Timeline(
                        new KeyFrame(Duration.seconds(0.3 + level * 0.1),
                                new KeyValue(connectionLine.endYProperty(), pathY, Interpolator.EASE_OUT))
                );
                lineAnim.play();

                levelPane.getChildren().add(connectionLine);
                connectionLines.add(connectionLine);
            } else {
                connectionLines.add(null);
            }

            VBox levelContainer = new VBox(15);
            levelContainer.setAlignment(Pos.CENTER);
            levelContainer.setLayoutX(xPos - 50);
            levelContainer.setLayoutY(yPos - 50);

            // Start invisible for animation
            levelContainer.setOpacity(0);
            levelContainer.setScaleX(0.5);
            levelContainer.setScaleY(0.5);

            Text levelLabel = new Text("Level " + level);
            levelLabel.setStyle("-fx-font-family: 'Arial Black'; -fx-font-size: 14; -fx-fill: #FFFFFF; -fx-effect: dropshadow(gaussian, #000000, 3, 0.5, 0, 0);");

            DropShadow defaultEffect = new DropShadow(10, Color.rgb(0, 0, 0, 0.8));
            Glow hoverGlowEffect = new Glow(0.8);
            DropShadow cyanGlow = new DropShadow(15, Color.CYAN);

            if (isUnlocked) {
                Image levelImage;
                switch (level) {
                    case 1:
                        levelImage = level1Image;
                        break;
                    case 2:
                        levelImage = level2Image;
                        break;
                    case 3:
                        levelImage = level3Image;
                        break;
                    case 4:
                        levelImage = level4Image;
                        break;
                    case 5:
                        levelImage = level5Image;
                        break;
                    case 6:
                        levelImage = level6Image;
                        break;
                    case 7:
                        levelImage = level7Image;
                        break;
                    case 8:
                        levelImage = level8Image;
                        break;
                    case 9:
                        levelImage = level9Image;
                        break;
                    case 10:
                        levelImage = level10Image;
                        break;
                    default:
                        levelImage = null;
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

                // Add subtle rotation animation
                RotateTransition rotateIdle = new RotateTransition(Duration.seconds(4), levelImageView);
                rotateIdle.setByAngle(5);
                rotateIdle.setCycleCount(Animation.INDEFINITE);
                rotateIdle.setAutoReverse(true);
                rotateIdle.setInterpolator(Interpolator.EASE_BOTH);
                rotateIdle.play();

                levelContainer.getChildren().add(levelImageView);

                final int selectedLevel = level;

// Shared hover handlers for unlocked levels
                javafx.event.EventHandler<javafx.scene.input.MouseEvent> unlockedHoverEnter = event -> {
                    if (isLevelEntranceAnimating) return;  // ADD THIS LINE
                    levelImageView.setEffect(cyanGlow);
                    ScaleTransition scaleUp = new ScaleTransition(Duration.millis(200), levelContainer);
                    scaleUp.setToX(1.15);
                    scaleUp.setToY(1.15);
                    scaleUp.play();
                };

                javafx.event.EventHandler<javafx.scene.input.MouseEvent> unlockedHoverExit = event -> {
                    if (isLevelEntranceAnimating) return;  // ADD THIS LINE
                    levelImageView.setEffect(defaultEffect);
                    ScaleTransition scaleDown = new ScaleTransition(Duration.millis(200), levelContainer);
                    scaleDown.setToX(1.0);
                    scaleDown.setToY(1.0);
                    scaleDown.play();
                };

                javafx.event.EventHandler<javafx.scene.input.MouseEvent> unlockedClickHandler = event -> {
                    if (isLevelEntranceAnimating) return;  // ADD THIS LINE
                    // Prevent multiple clicks during transition
                    if (isTransitioningToGameplay) {
                        return;
                    }
                    isTransitioningToGameplay = true;

                    // Click animation
                    ScaleTransition clickAnim = new ScaleTransition(Duration.millis(100), levelContainer);
                    clickAnim.setToX(0.9);
                    clickAnim.setToY(0.9);
                    clickAnim.setAutoReverse(true);
                    clickAnim.setCycleCount(2);
                    clickAnim.setOnFinished(e -> {
                        currentLevel.set(selectedLevel);
                        setupGameplayScreen();
                        // Reset flag after screen is set up
                        isTransitioningToGameplay = false;
                    });
                    clickAnim.play();
                };

// Apply to ImageView
                levelImageView.setOnMouseEntered(unlockedHoverEnter);
                levelImageView.setOnMouseExited(unlockedHoverExit);
                levelImageView.setOnMouseClicked(unlockedClickHandler);

// Apply to label too
                levelLabel.setOnMouseEntered(unlockedHoverEnter);
                levelLabel.setOnMouseExited(unlockedHoverExit);
                levelLabel.setOnMouseClicked(unlockedClickHandler);

// Make sure the container doesn't intercept events
                levelContainer.setPickOnBounds(false);

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
                levelCircle.setStroke(Color.rgb(150, 150, 150, 0.3));
                levelCircle.setStrokeWidth(2);
                levelCircle.setEffect(defaultEffect);

                // Add lock icon
                Text lockIcon = new Text("🔒");
                lockIcon.setStyle("-fx-font-size: 30;");
                lockIcon.setMouseTransparent(true); // Lock icon doesn't intercept mouse events

                StackPane lockedNode = new StackPane(levelCircle, lockIcon);
                lockedNode.setPickOnBounds(false); // Only register hits on actual shapes

                levelContainer.getChildren().add(lockedNode);
                if (level % 2 == 1) {
                    levelContainer.getChildren().add(0, levelLabel);
                } else {
                    levelContainer.getChildren().add(levelLabel);
                }
                levelContainer.setOpacity(0.6);
                levelContainer.setPickOnBounds(false); // Container also only picks on actual shapes

                // Shared hover handlers - just scale, no glow effect
                javafx.event.EventHandler<javafx.scene.input.MouseEvent> hoverEnter = event -> {
                    if (isLevelEntranceAnimating) return;  // ADD THIS LINE
                    ScaleTransition scaleUp = new ScaleTransition(Duration.millis(200), levelContainer);
                    scaleUp.setToX(1.1);
                    scaleUp.setToY(1.1);
                    scaleUp.play();
                };

                javafx.event.EventHandler<javafx.scene.input.MouseEvent> hoverExit = event -> {
                    if (isLevelEntranceAnimating) return;  // ADD THIS LINE
                    ScaleTransition scaleDown = new ScaleTransition(Duration.millis(200), levelContainer);
                    scaleDown.setToX(1.0);
                    scaleDown.setToY(1.0);
                    scaleDown.play();
                };

                javafx.event.EventHandler<javafx.scene.input.MouseEvent> shakeOnClick = event -> {
                    if (isLevelEntranceAnimating) return;  // ADD THIS LINE
                    event.consume();

                    // Horizontal shake animation - only on lockedNode (circle + lock)
                    TranslateTransition shake = new TranslateTransition(Duration.millis(50), lockedNode);
                    shake.setFromX(0);
                    shake.setByX(10);
                    shake.setCycleCount(6);  // Shake 6 times
                    shake.setAutoReverse(true);
                    shake.setInterpolator(Interpolator.LINEAR);
                    shake.play();
                };

                // Apply to circle
                levelCircle.setOnMouseEntered(hoverEnter);
                levelCircle.setOnMouseExited(hoverExit);
                levelCircle.setOnMouseClicked(shakeOnClick);

                // Apply to label too
                levelLabel.setOnMouseEntered(hoverEnter);
                levelLabel.setOnMouseExited(hoverExit);
                levelLabel.setOnMouseClicked(shakeOnClick);
            }

            // Entrance animation (staggered)
            FadeTransition fadeIn = new FadeTransition(Duration.millis(400), levelContainer);
            fadeIn.setToValue(isUnlocked ? 1.0 : 0.6);
            fadeIn.setDelay(Duration.millis(level * 80));

            ScaleTransition scaleIn = new ScaleTransition(Duration.millis(400), levelContainer);
            scaleIn.setToX(1.0);
            scaleIn.setToY(1.0);
            scaleIn.setDelay(Duration.millis(level * 80));
            scaleIn.setInterpolator(Interpolator.EASE_OUT);

            ParallelTransition entrance = new ParallelTransition(fadeIn, scaleIn);
            entrance.play();

            levelPane.getChildren().add(levelContainer);
            levelContainers.add(levelContainer);
        }

        double totalEntranceDuration = (totalLevels * 80) + 400;

        // Re-enable interactions after entrance animation completes
        Timeline enableInteractions = new Timeline(
                new KeyFrame(Duration.millis(totalEntranceDuration + 100), e -> {
                    isLevelEntranceAnimating = false;

                    // Check if mouse is already hovering over any level containers
                    for (VBox container : levelContainers) {
                        if (container.isHover()) {
                            // Manually apply hover scale effect
                            ScaleTransition scaleUp = new ScaleTransition(Duration.millis(200), container);
                            scaleUp.setToX(1.15);
                            scaleUp.setToY(1.15);
                            scaleUp.play();

                            // Apply glow effect for unlocked levels
                            if (container.getChildren().size() > 0 &&
                                    container.getChildren().get(0) instanceof ImageView) {
                                ImageView imgView = (ImageView) container.getChildren().get(0);
                                DropShadow cyanGlow = new DropShadow(15, Color.CYAN);
                                imgView.setEffect(cyanGlow);
                            }
                        }
                    }
                })
        );
        enableInteractions.play();

        // Add width listener to update level positions dynamically
        primaryStage.widthProperty().addListener((obs, oldVal, newVal) -> {
            double newPaneWidth = newVal.doubleValue() * 0.9;
            levelPane.setPrefWidth(newPaneWidth);
            double newLevelSpacing = (newPaneWidth - 200) / (totalLevels + 1);
            double newStartX = (newPaneWidth - (totalLevels - 1) * newLevelSpacing) / 2;

            // Update main path
            mainPath.setStartX(newStartX);
            mainPath.setEndX(newStartX + (totalLevels - 1) * newLevelSpacing);

            // Update level positions
            for (int i = 0; i < totalLevels; i++) {
                int level = i + 1;
                double xPos = newStartX + i * newLevelSpacing;
                double yPos = pathY - (level % 2 == 1 ? offsetY : -offsetY);

                VBox container = levelContainers.get(i);
                container.setLayoutX(xPos - 50);
                container.setLayoutY(yPos - 50);

                // Update connection line if exists
                Line connLine = connectionLines.get(i);
                if (connLine != null) {
                    connLine.setStartX(xPos);
                    connLine.setEndX(xPos);
                    connLine.setStartY(yPos);
                }
            }
        });

        VBox centerWrapper = new VBox(levelPane);
        centerWrapper.setAlignment(Pos.CENTER);

        // === ANIMATED TOP BAR ===
        BorderPane topBar = new BorderPane();
        topBar.setPadding(new Insets(15, 20, 15, 20));
        topBar.setOpacity(0);
        topBar.setTranslateY(-50);

        // LEFT: Profile picture + username
        HBox userBox = new HBox(12);
        userBox.setAlignment(Pos.CENTER_LEFT);
        userBox.setStyle("-fx-cursor: hand;");

        // Background circle for profile pic - solid dark gray
        Circle topBarBgCircle = new Circle(20);
        topBarBgCircle.setFill(Color.rgb(40, 45, 55, 0.8));

        topBarProfilePicView = new ImageView();
        topBarProfilePicView.setFitWidth(40);
        topBarProfilePicView.setFitHeight(40);
        topBarProfilePicView.setClip(new Circle(20, 20, 20));

        topBarProfilePicView.setImage(currentProfileImage != null
                ? currentProfileImage
                : new Image("file:resources/images/default_profile.png"));

        StackPane topBarPicContainer = new StackPane(topBarBgCircle, topBarProfilePicView);
        topBarPicContainer.setEffect(new DropShadow(5, Color.rgb(0, 0, 0, 0.8)));

        usernameLabel = new Label(currentUser);
        usernameLabel.setStyle("-fx-text-fill: #FFFFFF; -fx-font-family: 'Arial Black'; -fx-font-size: 14; -fx-font-weight: bold; -fx-effect: dropshadow(gaussian, #000000, 5, 0.5, 0, 0);");

        Label editIcon = new Label("✎");
        editIcon.setStyle("-fx-background-color: rgba(0,0,0,0.7); -fx-text-fill: cyan; -fx-font-size: 12; -fx-font-weight: bold; -fx-padding: 4; -fx-background-radius: 15;");
        editIcon.setVisible(false);
        editIcon.setMouseTransparent(true);

        StackPane profileWrapper = new StackPane(topBarPicContainer, editIcon);  // CHANGE: use topBarPicContainer instead
        profileWrapper.setAlignment(Pos.BOTTOM_RIGHT);

        userBox.getChildren().addAll(profileWrapper, usernameLabel);

        userBox.setOnMouseClicked(e -> showProfileSettingsDialog());
        userBox.setOnMouseEntered(e -> {
            topBarProfilePicView.setOpacity(0.8);
            editIcon.setVisible(true);
            userBox.setStyle("-fx-cursor: hand; -fx-effect: dropshadow(gaussian, cyan, 10, 0.5, 0, 0);");
        });
        userBox.setOnMouseExited(e -> {
            topBarProfilePicView.setOpacity(1.0);
            editIcon.setVisible(false);
            userBox.setStyle("-fx-cursor: hand;");
        });

        topBar.setLeft(userBox);

        // RIGHT: The three buttons
        HBox buttonBox = new HBox(15);
        buttonBox.setAlignment(Pos.CENTER_RIGHT);

        // NEW: How to Play button
        Button howToPlayButton = new Button("❓");
        howToPlayButton.setFocusTraversable(false);
        howToPlayButton.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5); -fx-text-fill: #44AAFF; -fx-font-family: 'Arial Black'; -fx-font-size: 14; -fx-font-weight: bold; -fx-background-radius: 15; -fx-padding: 10 20; -fx-border-color: #44AAFF; -fx-border-width: 2; -fx-border-radius: 15; -fx-effect: dropshadow(gaussian, #44AAFF, 8, 0.6, 0, 0); -fx-min-width: 50; -fx-focus-color: transparent; -fx-faint-focus-color: transparent;");
        howToPlayButton.setOnAction(event -> {
            if (!isDialogOpen) {
                isDialogOpen = true;
                // Show tutorial in "manual mode" - don't affect completion status
                showTutorial(true); // Pass true to indicate manual opening
            }
        });
        howToPlayButton.setOnMouseEntered(event -> howToPlayButton.setStyle("-fx-background-color: rgba(68, 170, 255, 0.15); -fx-text-fill: #66CCFF; -fx-font-family: 'Arial Black'; -fx-font-size: 14; -fx-font-weight: bold; -fx-background-radius: 15; -fx-padding: 10 20; -fx-border-color: #66CCFF; -fx-border-width: 2; -fx-border-radius: 15; -fx-effect: dropshadow(gaussian, #44AAFF, 8, 0.5, 0, 0); -fx-min-width: 50; -fx-focus-color: transparent; -fx-faint-focus-color: transparent;"));
        howToPlayButton.setOnMouseExited(event -> howToPlayButton.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5); -fx-text-fill: #44AAFF; -fx-font-family: 'Arial Black'; -fx-font-size: 14; -fx-font-weight: bold; -fx-background-radius: 15; -fx-padding: 10 20; -fx-border-color: #44AAFF; -fx-border-width: 2; -fx-border-radius: 15; -fx-effect: dropshadow(gaussian, #44AAFF, 8, 0.6, 0, 0); -fx-min-width: 50; -fx-focus-color: transparent; -fx-faint-focus-color: transparent;"));


        Button volumeButton = new Button("⚙");
        volumeButton.setFocusTraversable(false);
        volumeButton.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5); -fx-text-fill: #44FF88; -fx-font-family: 'Arial Black'; -fx-font-size: 14; -fx-font-weight: bold; -fx-background-radius: 15; -fx-padding: 10 20; -fx-border-color: #44FF88; -fx-border-width: 2; -fx-border-radius: 15; -fx-effect: dropshadow(gaussian, #44FF88, 8, 0.6, 0, 0); -fx-min-width: 50; -fx-focus-color: transparent; -fx-faint-focus-color: transparent;");
        volumeButton.setOnAction(event -> {
            if (!isDialogOpen) {
                isDialogOpen = true;
                showSettingsDialog();
            }
        });
        volumeButton.setOnMouseEntered(event -> volumeButton.setStyle("-fx-background-color: rgba(68, 255, 136, 0.15); -fx-text-fill: #66FFAA; -fx-font-family: 'Arial Black'; -fx-font-size: 14; -fx-font-weight: bold; -fx-background-radius: 15; -fx-padding: 10 20; -fx-border-color: #66FFAA; -fx-border-width: 2; -fx-border-radius: 15; -fx-effect: dropshadow(gaussian, #44FF88, 8, 0.5, 0, 0); -fx-min-width: 50; -fx-focus-color: transparent; -fx-faint-focus-color: transparent;"));
        volumeButton.setOnMouseExited(event -> volumeButton.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5); -fx-text-fill: #44FF88; -fx-font-family: 'Arial Black'; -fx-font-size: 14; -fx-font-weight: bold; -fx-background-radius: 15; -fx-padding: 10 20; -fx-border-color: #44FF88; -fx-border-width: 2; -fx-border-radius: 15; -fx-effect: dropshadow(gaussian, #44FF88, 8, 0.6, 0, 0); -fx-min-width: 50; -fx-focus-color: transparent; -fx-faint-focus-color: transparent;"));

        Button leaderboardButton = new Button("🏆 LEADERBOARD 🏆");
        leaderboardButton.setFocusTraversable(false);
        leaderboardButton.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5); -fx-text-fill: #FFAA44; -fx-font-family: 'Arial Black'; -fx-font-size: 14; -fx-font-weight: bold; -fx-background-radius: 15; -fx-padding: 10 20; -fx-border-color: #FFAA44; -fx-border-width: 2; -fx-border-radius: 15; -fx-effect: dropshadow(gaussian, #FFAA44, 8, 0.6, 0, 0); -fx-focus-color: transparent; -fx-faint-focus-color: transparent;");
        leaderboardButton.setOnAction(event -> {
            if (!isDialogOpen) {
                isDialogOpen = true;
                refreshLeaderboardData();
                new Timeline(new KeyFrame(Duration.millis(600), ae -> showLeaderboardDialog())).play();
            }
        });
        leaderboardButton.setOnMouseEntered(event -> leaderboardButton.setStyle("-fx-background-color: rgba(255, 170, 68, 0.15); -fx-text-fill: #FFCC66; -fx-font-family: 'Arial Black'; -fx-font-size: 14; -fx-font-weight: bold; -fx-background-radius: 15; -fx-padding: 10 20; -fx-border-color: #FFCC66; -fx-border-width: 2; -fx-border-radius: 15; -fx-effect: dropshadow(gaussian, #FFAA44, 8, 0.5, 0, 0); -fx-focus-color: transparent; -fx-faint-focus-color: transparent;"));
        leaderboardButton.setOnMouseExited(event -> leaderboardButton.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5); -fx-text-fill: #FFAA44; -fx-font-family: 'Arial Black'; -fx-font-size: 14; -fx-font-weight: bold; -fx-background-radius: 15; -fx-padding: 10 20; -fx-border-color: #FFAA44; -fx-border-width: 2; -fx-border-radius: 15; -fx-effect: dropshadow(gaussian, #FFAA44, 8, 0.6, 0, 0); -fx-focus-color: transparent; -fx-faint-focus-color: transparent;"));

        Button logoutButton = new Button("LOGOUT");
        logoutButton.setFocusTraversable(false);
        logoutButton.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5); -fx-text-fill: #FF4466; -fx-font-family: 'Arial Black'; -fx-font-size: 14; -fx-font-weight: bold; -fx-background-radius: 15; -fx-padding: 10 20; -fx-border-color: #FF4466; -fx-border-width: 2; -fx-border-radius: 15; -fx-effect: dropshadow(gaussian, #FF4466, 8, 0.6, 0, 0); -fx-focus-color: transparent; -fx-faint-focus-color: transparent;");
        logoutButton.setOnAction(event -> {
            if (!isDialogOpen) {
                saveProgress();

                // FIX: Delete token BEFORE clearing currentUser
                deleteLoginToken();  // Move this line up

                currentUser = null;
                currentProfileImage = null;
                clearUserProgressCache();
                // deleteLoginToken();  // Remove from here

                stopCurrentBackgroundAudio();
                showLoginScreen();
            }
        });
        logoutButton.setOnMouseEntered(event -> logoutButton.setStyle("-fx-background-color: rgba(255, 68, 102, 0.15); -fx-text-fill: #FF6688; -fx-font-family: 'Arial Black'; -fx-font-size: 14; -fx-font-weight: bold; -fx-background-radius: 15; -fx-padding: 10 20; -fx-border-color: #FF6688; -fx-border-width: 2; -fx-border-radius: 15; -fx-effect: dropshadow(gaussian, #FF4466, 8, 0.5, 0, 0); -fx-focus-color: transparent; -fx-faint-focus-color: transparent;"));
        logoutButton.setOnMouseExited(event -> logoutButton.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5); -fx-text-fill: #FF4466; -fx-font-family: 'Arial Black'; -fx-font-size: 14; -fx-font-weight: bold; -fx-background-radius: 15; -fx-padding: 10 20; -fx-border-color: #FF4466; -fx-border-width: 2; -fx-border-radius: 15; -fx-effect: dropshadow(gaussian, #FF4466, 8, 0.6, 0, 0); -fx-focus-color: transparent; -fx-faint-focus-color: transparent;"));

// Also update the profile click handler:
        userBox.setOnMouseClicked(e -> {
            if (!isDialogOpen) {
                isDialogOpen = true;
                showProfileSettingsDialog();
            }
        });

        buttonBox.getChildren().addAll(howToPlayButton, volumeButton, leaderboardButton, logoutButton);
        topBar.setRight(buttonBox);

        // Animate top bar entrance
        FadeTransition topBarFade = new FadeTransition(Duration.millis(600), topBar);
        topBarFade.setToValue(1.0);
        topBarFade.setDelay(Duration.millis(400));

        TranslateTransition topBarSlide = new TranslateTransition(Duration.millis(600), topBar);
        topBarSlide.setToY(0);
        topBarSlide.setDelay(Duration.millis(400));
        topBarSlide.setInterpolator(Interpolator.EASE_OUT);

        ParallelTransition topBarEntrance = new ParallelTransition(topBarFade, topBarSlide);
        topBarEntrance.play();

        StackPane rootStack = new StackPane(backgroundPane, new BorderPane(centerWrapper, topBar, null, null, null));

        if (spriteCharacter != null) {
            ImageView spriteView = new ImageView(spriteCharacter);

            // Size the sprite - adjust these values as needed
            spriteView.setFitWidth(150);  // Adjust size as needed
            spriteView.setFitHeight(150); // Adjust size as needed
            spriteView.setPreserveRatio(true);

            // Add subtle idle animation (gentle bounce)
            TranslateTransition idleBounce = new TranslateTransition(Duration.seconds(2), spriteView);
            idleBounce.setByY(-10);
            idleBounce.setCycleCount(Animation.INDEFINITE);
            idleBounce.setAutoReverse(true);
            idleBounce.setInterpolator(Interpolator.EASE_BOTH);
            idleBounce.play();

            // === CHAT BUBBLE ===
            VBox chatBubble = new VBox(5);
            chatBubble.setAlignment(Pos.CENTER);
            chatBubble.setMaxWidth(200);
            chatBubble.setMaxHeight(80); // Limit height
            chatBubble.setPrefHeight(Region.USE_COMPUTED_SIZE); // Auto-size based on content
            chatBubble.setStyle(
                    "-fx-background-color: white; " +
                            "-fx-background-radius: 15; " +
                            "-fx-padding: 12; " +
                            "-fx-border-color: #333333; " +
                            "-fx-border-width: 3; " +
                            "-fx-border-radius: 15; " +
                            "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.3), 8, 0.0, 2, 2);"
            );

            Label chatText = new Label("I'm just here looking for bugs to clean! 🐛✨");
            chatText.setWrapText(true);
            chatText.setStyle(
                    "-fx-text-fill: black; " +
                            "-fx-font-family: 'Arial'; " +
                            "-fx-font-size: 12; " +
                            "-fx-font-weight: bold;"
            );
            chatBubble.getChildren().add(chatText);
            chatBubble.setVisible(false); // Hidden by default
            chatBubble.setOpacity(0);
            chatBubble.setMouseTransparent(true);

            // Random chat messages
            String[] chatMessages = {
                    "I'm just here looking for bugs to clean! 🐛✨",
                    "Everything seems fine here! ✓",
                    "No bugs detected... yet! 👀",
                    "Just doing my routine check! 🔍",
                    "All systems operational! 💚",
                    "Keeping things tidy! ✨"
            };

            // Manually adjust chat bubble position - move it to the right relative to sprite
            VBox.setMargin(chatBubble, new Insets(0, -25, 0, 0)); // Negative right margin moves it right

            // Make sprite clickable
            spriteView.setStyle("-fx-cursor: hand;");
            spriteView.setOnMouseClicked(event -> {
                // Check cooldown to prevent spam
                long currentTime = System.currentTimeMillis();
                if (currentTime - lastChatTime < CHAT_COOLDOWN_MS) {
                    // Still in cooldown, ignore click
                    return;
                }

                if (chatBubble.isVisible()) {
                    // Hide chat bubble with animation
                    lastChatTime = currentTime; // Update cooldown timer

                    FadeTransition fadeOut = new FadeTransition(Duration.millis(300), chatBubble);
                    fadeOut.setToValue(0);
                    fadeOut.setOnFinished(e -> chatBubble.setVisible(false));

                    ScaleTransition scaleOut = new ScaleTransition(Duration.millis(300), chatBubble);
                    scaleOut.setToX(0.8);
                    scaleOut.setToY(0.8);

                    new ParallelTransition(fadeOut, scaleOut).play();
                } else {
                    // Show chat bubble with animation and random message
                    lastChatTime = currentTime; // Update cooldown timer

                    String randomMessage = chatMessages[random.nextInt(chatMessages.length)];
                    chatText.setText(randomMessage);

                    chatBubble.setVisible(true);
                    chatBubble.setScaleX(0.8);
                    chatBubble.setScaleY(0.8);

                    FadeTransition fadeIn = new FadeTransition(Duration.millis(300), chatBubble);
                    fadeIn.setToValue(1);

                    ScaleTransition scaleIn = new ScaleTransition(Duration.millis(300), chatBubble);
                    scaleIn.setToX(1.0);
                    scaleIn.setToY(1.0);
                    scaleIn.setInterpolator(Interpolator.EASE_OUT);

                    ParallelTransition showAnim = new ParallelTransition(fadeIn, scaleIn);
                    showAnim.play();

                    // Auto-hide after 4 seconds
                    Timeline autoHide = new Timeline(new KeyFrame(Duration.seconds(4), e -> {
                        if (chatBubble.isVisible()) {
                            FadeTransition fadeOut = new FadeTransition(Duration.millis(300), chatBubble);
                            fadeOut.setToValue(0);
                            fadeOut.setOnFinished(evt -> chatBubble.setVisible(false));

                            ScaleTransition scaleOut = new ScaleTransition(Duration.millis(300), chatBubble);
                            scaleOut.setToX(0.8);
                            scaleOut.setToY(0.8);

                            new ParallelTransition(fadeOut, scaleOut).play();
                        }
                    }));
                    autoHide.play();
                }
            });

            // Hover effect on sprite
            spriteView.setOnMouseEntered(e -> {
                ScaleTransition scaleUp = new ScaleTransition(Duration.millis(200), spriteView);
                scaleUp.setToX(1.1);
                scaleUp.setToY(1.1);
                scaleUp.play();
            });

            spriteView.setOnMouseExited(e -> {
                ScaleTransition scaleDown = new ScaleTransition(Duration.millis(200), spriteView);
                scaleDown.setToX(1.0);
                scaleDown.setToY(1.0);
                scaleDown.play();
            });

            // Position in bottom right corner
            VBox spriteWithChat = new VBox(10);
            spriteWithChat.setAlignment(Pos.BOTTOM_RIGHT);
            spriteWithChat.getChildren().addAll(chatBubble, spriteView);
            spriteWithChat.setPickOnBounds(false);

// Manually adjust chat bubble position
            VBox.setMargin(chatBubble, new Insets(0, -25, 0, 0));

// Position in bottom right corner
            this.spriteContainer = new StackPane(spriteWithChat);
            spriteContainer.setAlignment(Pos.BOTTOM_RIGHT);
            spriteContainer.setPadding(new Insets(0, 60, 20, 0));
            spriteContainer.setPickOnBounds(false);

// Apply saved visibility state
            spriteContainer.setVisible(isSpriteVisible);
            spriteContainer.setOpacity(isSpriteVisible ? 1.0 : 0.0);

// Add to rootStack as an overlay
            rootStack.getChildren().add(spriteContainer);
            StackPane.setAlignment(spriteContainer, Pos.BOTTOM_RIGHT);
        }

        root.setCenter(rootStack);

        // === MOUSE CURSOR PARTICLE TRAIL ===
        List<Circle> trailParticles = new ArrayList<>();
        final long[] lastTrailTime = {0};

        rootStack.setOnMouseMoved(event -> {
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastTrailTime[0] > 40) {
                lastTrailTime[0] = currentTime;
                // Create burst of particles
                int particleCount = 3 + random.nextInt(3);
                for (int i = 0; i < particleCount; i++) {
                    Circle trail = new Circle(2 + random.nextInt(4));

                    // RGB RAINBOW COLORS - cycles through all colors
                    double hue = (currentTime % 2000) / 2000.0 * 360;  // Full color cycle every 2 seconds
                    Color rainbowColor = Color.hsb(hue + (i * 30), 0.9, 1.0, 0.8);  // Each particle slightly offset
                    trail.setFill(rainbowColor);
                    trail.setEffect(new Glow(0.9));

                    trail.setCenterX(event.getX());
                    trail.setCenterY(event.getY());
                    trail.setMouseTransparent(true);
                    backgroundPane.getChildren().add(trail);
                    trailParticles.add(trail);
                    // Particles explode outward
                    double angle = random.nextDouble() * Math.PI * 2;
                    double distance = 20 + random.nextDouble() * 40;
                    double targetX = Math.cos(angle) * distance;
                    double targetY = Math.sin(angle) * distance;
                    TranslateTransition explode = new TranslateTransition(Duration.millis(800), trail);
                    explode.setByX(targetX);
                    explode.setByY(targetY);
                    explode.setInterpolator(Interpolator.EASE_OUT);
                    FadeTransition fade = new FadeTransition(Duration.millis(800), trail);
                    fade.setToValue(0);
                    fade.setDelay(Duration.millis(200));
                    fade.setOnFinished(e -> {
                        backgroundPane.getChildren().remove(trail);
                        trailParticles.remove(trail);
                    });
                    ScaleTransition scale = new ScaleTransition(Duration.millis(800), trail);
                    scale.setToX(0);
                    scale.setToY(0);
                    ParallelTransition pt = new ParallelTransition(explode, fade, scale);
                    pt.play();
                }
            }
        });

// Ensure profile pic is loaded if just registered/logged in
        if (currentProfileImage == null && currentUser != null) {
            System.err.println("[WARNING] Profile image not loaded during level select!");
        }
    }

    private void loadSpriteCharacter() {
        File spriteFolder = new File(SPRITE_FOLDER);
        if (!spriteFolder.exists()) {
            spriteFolder.mkdirs();
        }

        File spriteFile = new File(SPRITE_FOLDER + File.separator + SPRITE_CHARACTER_FILE);

        // Check if file exists and is valid
        if (!spriteFile.exists() || spriteFile.length() == 0) {
            System.out.println("[SPRITE] Character sprite not found or corrupted. Downloading...");
            try {
                InputStream in = new URL(SPRITE_CHARACTER_URL).openStream();
                Files.copy(in, Paths.get(spriteFile.getPath()), StandardCopyOption.REPLACE_EXISTING);
                in.close();
                System.out.println("[SPRITE] Character sprite downloaded successfully!");
            } catch (IOException e) {
                System.err.println("[SPRITE] Failed to download character sprite: " + e.getMessage());
                spriteCharacter = null;
                return;
            }
        } else {
            System.out.println("[SPRITE] Character sprite already exists, using cached version.");
        }

        // Load the sprite
        if (spriteFile.exists()) {
            try {
                spriteCharacter = new Image(spriteFile.toURI().toString());
                System.out.println("[SPRITE] Character sprite loaded successfully!");
            } catch (Exception e) {
                System.err.println("[SPRITE] Failed to load character sprite: " + e.getMessage());
                spriteCharacter = null;
            }
        }
    }

    private void preloadAllLeaderboardProfilePictures() {
        if (leaderboardProfilePreloadTask != null && leaderboardProfilePreloadTask.isRunning()) {
            return;
        }

        leaderboardProfilePreloadTask = new Task<>() {
            @Override
            protected Void call() throws Exception {
                try {
                    System.out.println("Pre-loading leaderboard data + avatars...");

                    HttpRequest request = HttpRequest.newBuilder()
                            .uri(URI.create(LEADERBOARD_GET))
                            .header("apikey", SUPABASE_ANON_KEY)
                            .header("Authorization", "Bearer " + SUPABASE_ANON_KEY)
                            .timeout(java.time.Duration.ofSeconds(20))
                            .GET()
                            .build();

                    HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

                    if (response.statusCode() != 200) {
                        System.err.println("Leaderboard preload failed: HTTP " + response.statusCode());
                        return null;
                    }

                    Type type = new TypeToken<List<Map<String, Object>>>() {
                    }.getType();
                    List<Map<String, Object>> rows = gson.fromJson(response.body(), type);

                    if (rows == null || rows.isEmpty()) {
                        Platform.runLater(() -> {
                            leaderboardData.clear();
                            System.out.println("Leaderboard is empty (pre-load).");
                        });
                        return null;
                    }

                    // ───── STEP 1: Build aggregated leaderboard data (same logic as refreshLeaderboardData) ─────
                    Map<String, PlayerData> aggregated = new HashMap<>();

                    for (Map<String, Object> row : rows) {
                        String username = (String) row.get("username");
                        if (username == null || username.isBlank()) continue;

                        Number levelNum = (Number) row.get("level");
                        Number timeNum = (Number) row.get("completion_time");
                        if (levelNum == null || timeNum == null) continue;

                        int level = levelNum.intValue();
                        double time = timeNum.doubleValue();

                        aggregated.compute(username, (key, existing) -> {
                            if (existing == null) {
                                return new PlayerData(username, level, time, null);
                            } else {
                                existing.highestLevel = Math.max(existing.highestLevel, level);
                                existing.totalCompletionTime += time;
                                return existing;
                            }
                        });
                    }

                    List<PlayerData> sorted = new ArrayList<>(aggregated.values());
                    sorted.sort((a, b) -> {
                        if (a.highestLevel != b.highestLevel)
                            return Integer.compare(b.highestLevel, a.highestLevel);
                        return Double.compare(a.totalCompletionTime, b.totalCompletionTime);
                    });

                    // ───── STEP 2: Update leaderboardData ONCE on JavaFX thread ─────
                    Platform.runLater(() -> {
                        leaderboardData.clear();
                        leaderboardData.addAll(sorted);
                        System.out.println("Leaderboard pre-loaded: " + sorted.size() + " players ready instantly!");
                    });

                    // ───── STEP 3: Pre-load all profile pictures in parallel (still async) ─────
                    Set<String> usernames = aggregated.keySet();
                    System.out.println("Pre-caching profile pictures for " + usernames.size() + " players...");
                    usernames.parallelStream().forEach(username -> getProfileImageForUser(username));

                } catch (Exception e) {
                    System.err.println("Pre-load error: " + e.getMessage());
                    e.printStackTrace();
                }
                return null;
            }
        };

        new Thread(leaderboardProfilePreloadTask).start();
    }

    private void saveProfileToSupabase() {
        if (currentUser == null) return;

        // Convert unlocked levels to comma-separated string
        String levelsStr = unlockedLevels.stream()
                .sorted()
                .map(String::valueOf)
                .collect(Collectors.joining(","));

        Map<String, Object> body = new HashMap<>();
        // DO NOT include "username" in the body—it's the filter key, including it can confuse the WHERE
        body.put("unlocked_levels", levelsStr);
        body.put("effects_muted", isEffectsMuted);
        body.put("music_muted", isMusicMuted);
        body.put("volume", audioVolume);
        // Optionally: body.put("profile_picture", base64String); // If updating pic

        String json = gson.toJson(body);

        executor.submit(() -> {
            try {
                // KEY FIX: Add ?username=eq.{currentUser} to filter the UPDATE
                String uriStr = SUPABASE_URL + "/rest/v1/profiles?username=eq." +
                        URLEncoder.encode(currentUser, StandardCharsets.UTF_8);

                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(uriStr))  // Filtered URI
                        .header("apikey", SUPABASE_ANON_KEY)
                        .header("Authorization", "Bearer " + SUPABASE_ANON_KEY)
                        .header("Content-Type", "application/json")
                        .header("Prefer", "resolution=merge-duplicates")  // Still useful for conflicts
                        .method("PATCH", HttpRequest.BodyPublishers.ofString(json))  // Use PATCH for updates
                        .build();

                HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

                if (response.statusCode() >= 200 && response.statusCode() < 300) {
                    System.out.println("Profile saved to Supabase: unlocked levels = " + levelsStr);
                } else {
                    System.err.println("Failed to save profile: Status " + response.statusCode() + " - " + response.body());
                    // Optional: Log more details for debugging
                }
            } catch (Exception e) {
                System.err.println("Error saving profile to Supabase: " + e.getMessage());
                e.printStackTrace();  // Add this for full stack trace
            }
        });
    }

    private void decidePostLoginScreen() {
        if (ADMIN_USERNAME.equalsIgnoreCase(currentUser)) {
            showAdminPanel();           // go straight to admin panel
        } else {
            showLevelSelectScreen();    // normal player flow
        }
    }

    public static class PlayerData {
        public String username;
        public int highestLevel;
        public double totalCompletionTime;
        public byte[] profilePicture;

        public PlayerData(String username, int highestLevel, double totalCompletionTime, byte[] profilePicture) {
            this.username = username;
            this.highestLevel = highestLevel;
            this.totalCompletionTime = totalCompletionTime;
            this.profilePicture = profilePicture;
        }
    }

    public void hideLoadingScreen() {
        Platform.runLater(this::showLevelSelectScreen);  // Always restore Level Select
    }

    public void showLoadingScreen() {
        root.getChildren().clear();
        root.setStyle("-fx-background-color: radial-gradient(center 50% 50%, radius 80%, #1C2526, #0A0E26);");
        primaryStage.setTitle("Chroma Flood - Loading");

        VBox loadingBox = new VBox(30);
        loadingBox.setAlignment(Pos.CENTER);
        loadingBox.setPadding(new Insets(20));

        // Game Title with glow effect
        Label titleLabel = new Label("CHROMA FLOOD");
        titleLabel.setStyle(
                "-fx-text-fill: linear-gradient(to right, #4D007F, #FF00FF);" +
                        "-fx-font-family: 'Arial Black';" +
                        "-fx-font-size: 48;" +
                        "-fx-font-weight: bold;" +
                        "-fx-effect: dropshadow(gaussian, #00FFFF, 20, 0.8, 0, 0);"
        );

        // Pulsing animation for title
        FadeTransition titlePulse = new FadeTransition(Duration.seconds(1.5), titleLabel);
        titlePulse.setFromValue(1.0);
        titlePulse.setToValue(0.6);
        titlePulse.setCycleCount(Animation.INDEFINITE);
        titlePulse.setAutoReverse(true);
        titlePulse.play();

        // Custom circular progress indicator
        Circle outerCircle = new Circle(50);
        outerCircle.setFill(Color.TRANSPARENT);
        outerCircle.setStroke(Color.web("#1A1A2E"));
        outerCircle.setStrokeWidth(8);

        Circle innerCircle = new Circle(50);
        innerCircle.setFill(Color.TRANSPARENT);
        innerCircle.setStroke(Color.web("#00FFFF"));
        innerCircle.setStrokeWidth(8);
        innerCircle.getStrokeDashArray().addAll(220.0, 220.0);
        innerCircle.setStrokeDashOffset(0);
        innerCircle.setStrokeLineCap(StrokeLineCap.ROUND);

        // Synchronized rotating animation for the progress circle
        RotateTransition circleRotate = new RotateTransition(Duration.seconds(2), innerCircle);
        circleRotate.setByAngle(360);
        circleRotate.setCycleCount(Animation.INDEFINITE);
        circleRotate.setInterpolator(Interpolator.LINEAR);

        // Synchronized dash offset animation for loading effect
        Timeline dashAnimation = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(innerCircle.strokeDashOffsetProperty(), 220, Interpolator.LINEAR)),
                new KeyFrame(Duration.seconds(2), new KeyValue(innerCircle.strokeDashOffsetProperty(), -220, Interpolator.LINEAR))
        );
        dashAnimation.setCycleCount(Animation.INDEFINITE);

        // Start both animations together
        circleRotate.play();
        dashAnimation.play();

        StackPane progressStack = new StackPane(outerCircle, innerCircle);

        // Loading text with typewriter effect
        Label loadingLabel = new Label("Loading");
        loadingLabel.setStyle(
                "-fx-text-fill: #FFFFFF;" +
                        "-fx-font-family: 'Arial';" +
                        "-fx-font-size: 20;" +
                        "-fx-effect: dropshadow(gaussian, #000000, 5, 0.5, 0, 0);"
        );

        // Animated dots
        Timeline dotsAnimation = new Timeline(
                new KeyFrame(Duration.ZERO, e -> loadingLabel.setText("Loading")),
                new KeyFrame(Duration.millis(500), e -> loadingLabel.setText("Loading.")),
                new KeyFrame(Duration.millis(1000), e -> loadingLabel.setText("Loading..")),
                new KeyFrame(Duration.millis(1500), e -> loadingLabel.setText("Loading..."))
        );
        dotsAnimation.setCycleCount(Animation.INDEFINITE);
        dotsAnimation.play();

        // Tip text for visual interest
        Label tipLabel = new Label("Tip: Match colors to flood the board!");
        tipLabel.setStyle(
                "-fx-text-fill: #88CCEE;" +
                        "-fx-font-family: 'Arial';" +
                        "-fx-font-size: 14;" +
                        "-fx-font-style: italic;" +
                        "-fx-opacity: 0.8;"
        );

        // Fade in animation for tip
        FadeTransition tipFade = new FadeTransition(Duration.seconds(2), tipLabel);
        tipFade.setFromValue(0.0);
        tipFade.setToValue(0.8);
        tipFade.setCycleCount(Animation.INDEFINITE);
        tipFade.setAutoReverse(true);
        tipFade.play();

        // Color particles effect (optional decorative elements)
        HBox colorDots = new HBox(15);
        colorDots.setAlignment(Pos.CENTER);
        String[] colors = {"#FF0000", "#00FF00", "#0000FF", "#FFFF00", "#FF00FF", "#00FFFF"};

        for (String color : colors) {
            Circle dot = new Circle(8);
            dot.setFill(Color.web(color));
            dot.setEffect(new DropShadow(10, Color.web(color)));

            // Bounce animation for each dot
            TranslateTransition bounce = new TranslateTransition(Duration.seconds(1 + Math.random()), dot);
            bounce.setByY(-15);
            bounce.setCycleCount(Animation.INDEFINITE);
            bounce.setAutoReverse(true);
            bounce.play();

            colorDots.getChildren().add(dot);
        }

        loadingBox.getChildren().addAll(titleLabel, progressStack, loadingLabel, tipLabel, colorDots);
        root.setCenter(loadingBox);
    }

    private void showProfileSettingsDialog() {
        new ProfileSettingsDialog(this, primaryStage).show();
    }

    public void showImageCropDialog(File imageFile, ImageView previewTarget, byte[][] uploadBytesTarget) {
        Stage cropStage = new Stage();
        Stage owner = (profileSettingsStage != null) ? profileSettingsStage : primaryStage;
        cropStage.initOwner(owner);
        cropStage.initModality(Modality.WINDOW_MODAL);
        cropStage.initStyle(StageStyle.TRANSPARENT);
        cropStage.setTitle("Crop Profile Picture");
        cropStage.setResizable(false);

        try {
            BufferedImage originalImage = ImageIO.read(imageFile);
            Image fxImage = SwingFXUtils.toFXImage(originalImage, null);

            // Outer container
            StackPane overlay = new StackPane();
            overlay.setStyle("-fx-background-color: transparent;");
            overlay.setPadding(new Insets(30));

            // Main container
            VBox mainContainer = new VBox(20);
            mainContainer.setPadding(new Insets(30));
            mainContainer.setStyle(
                    "-fx-background-color: linear-gradient(to bottom right, #1a1a2e, #0f0f1e);" +
                            "-fx-background-radius: 20;" +
                            "-fx-effect: dropshadow(gaussian, rgba(100, 100, 255, 0.4), 25, 0.5, 0, 0);"
            );

            Label titleLabel = new Label("Crop Your Profile Picture");
            titleLabel.setStyle("-fx-text-fill: #FFFFFF; -fx-font-family: 'Segoe UI', 'Arial'; -fx-font-size: 22; -fx-font-weight: bold;");

            Label instructionLabel = new Label("Drag image to reposition • Scroll to zoom • Fixed circular crop");
            instructionLabel.setStyle("-fx-text-fill: #888899; -fx-font-size: 12; -fx-font-style: italic;");

            // FIXED CANVAS AND CROP SIZE (industry standard 300x300)
            final double CANVAS_SIZE = 500;
            final double CROP_SIZE = 300; // Fixed square size - standard profile picture dimension

            // Crop canvas container
            Pane cropCanvas = new Pane();
            cropCanvas.setStyle("-fx-background-color: rgba(20, 20, 35, 0.8); -fx-background-radius: 15;");
            cropCanvas.setPrefSize(CANVAS_SIZE, CANVAS_SIZE);
            cropCanvas.setMaxSize(CANVAS_SIZE, CANVAS_SIZE);
            cropCanvas.setMinSize(CANVAS_SIZE, CANVAS_SIZE);

            // Image view
            ImageView imageView = new ImageView(fxImage);
            imageView.setPreserveRatio(true);

            // Initial scale to fit and center
            double imgWidth = fxImage.getWidth();
            double imgHeight = fxImage.getHeight();

            // DEFAULT TO MAXIMUM ZOOM OUT - show the full image
            // Scale to fit the entire image in the canvas while maintaining aspect ratio
            double scaleToFitCanvas = Math.min(CANVAS_SIZE / imgWidth, CANVAS_SIZE / imgHeight) * 0.95; // 95% to add padding

            // But ensure it still covers the crop area (minimum scale)
            double minScale = Math.max(CROP_SIZE / imgWidth, CROP_SIZE / imgHeight);

            // Use the larger of the two (prefer showing full image if it still covers crop)
            double initialScale = Math.max(scaleToFitCanvas, minScale);

            // STORE initial dimensions - this is our minimum zoom level
            final double initialWidth = imgWidth * initialScale;
            final double initialHeight = imgHeight * initialScale;

            imageView.setFitWidth(initialWidth);
            imageView.setFitHeight(initialHeight);

            // Center the image
            imageView.setLayoutX((CANVAS_SIZE - imageView.getFitWidth()) / 2);
            imageView.setLayoutY((CANVAS_SIZE - imageView.getFitHeight()) / 2);

            // Clip canvas to rounded corners
            Rectangle canvasClip = new Rectangle(0, 0, CANVAS_SIZE, CANVAS_SIZE);
            canvasClip.setArcWidth(15);
            canvasClip.setArcHeight(15);
            cropCanvas.setClip(canvasClip);

            // FIXED CROP SELECTION (centered, cannot be moved or resized)
            double cropCenterX = CANVAS_SIZE / 2;
            double cropCenterY = CANVAS_SIZE / 2;
            double radius = CROP_SIZE / 2;

// Keep cropX and cropY for compatibility with existing code
            double cropX = cropCenterX - radius;
            double cropY = cropCenterY - radius;

// Dark overlay - full canvas
            Rectangle darkOverlay = new Rectangle(0, 0, CANVAS_SIZE, CANVAS_SIZE);
            darkOverlay.setFill(Color.rgb(0, 0, 0, 0.7));
            darkOverlay.setMouseTransparent(true);

// Create the cutout using Path and FillRule
            Path overlayWithCutout = new Path();
            overlayWithCutout.setFillRule(FillRule.EVEN_ODD);
            overlayWithCutout.getElements().add(new MoveTo(0, 0));
            overlayWithCutout.getElements().add(new LineTo(CANVAS_SIZE, 0));
            overlayWithCutout.getElements().add(new LineTo(CANVAS_SIZE, CANVAS_SIZE));
            overlayWithCutout.getElements().add(new LineTo(0, CANVAS_SIZE));
            overlayWithCutout.getElements().add(new ClosePath());
// Add circle path (this creates the hole)
            overlayWithCutout.getElements().add(new MoveTo(cropCenterX + radius, cropCenterY));
            overlayWithCutout.getElements().add(new ArcTo(radius, radius, 0, cropCenterX - radius, cropCenterY, false, true));
            overlayWithCutout.getElements().add(new ArcTo(radius, radius, 0, cropCenterX + radius, cropCenterY, false, true));
            overlayWithCutout.getElements().add(new ClosePath());
            overlayWithCutout.setFill(Color.rgb(0, 0, 0, 0.7));
            overlayWithCutout.setMouseTransparent(true);

// Cyan circular border
            Circle cropSelection = new Circle(cropCenterX, cropCenterY, radius);
            cropSelection.setFill(Color.TRANSPARENT);
            cropSelection.setStroke(Color.rgb(0, 217, 255));
            cropSelection.setStrokeWidth(3);
            cropSelection.setStrokeType(StrokeType.INSIDE);
            cropSelection.setMouseTransparent(true);

            // IMAGE DRAGGING (constrained to keep crop area filled)
            final double[] imageDragStart = new double[4]; // mouseX, mouseY, imageX, imageY
            final boolean[] isDragging = {false};

            imageView.setOnMousePressed(e -> {
                imageDragStart[0] = e.getSceneX();
                imageDragStart[1] = e.getSceneY();
                imageDragStart[2] = imageView.getLayoutX();
                imageDragStart[3] = imageView.getLayoutY();
                isDragging[0] = true;
                imageView.setCursor(Cursor.CLOSED_HAND);
                e.consume();
            });

            imageView.setOnMouseDragged(e -> {
                if (!isDragging[0]) return;

                double deltaX = e.getSceneX() - imageDragStart[0];
                double deltaY = e.getSceneY() - imageDragStart[1];

                double newX = imageDragStart[2] + deltaX;
                double newY = imageDragStart[3] + deltaY;

                // Constrain: image must always cover the crop area completely
                double minX = cropX + CROP_SIZE - imageView.getFitWidth();
                double maxX = cropX;
                double minY = cropY + CROP_SIZE - imageView.getFitHeight();
                double maxY = cropY;

                newX = Math.max(minX, Math.min(newX, maxX));
                newY = Math.max(minY, Math.min(newY, maxY));

                imageView.setLayoutX(newX);
                imageView.setLayoutY(newY);
                e.consume();
            });

            imageView.setOnMouseReleased(e -> {
                isDragging[0] = false;
                imageView.setCursor(Cursor.OPEN_HAND);
                e.consume();
            });

            imageView.setOnMouseEntered(e -> imageView.setCursor(Cursor.OPEN_HAND));
            imageView.setCursor(Cursor.OPEN_HAND);

            // ZOOM WITH MOUSE WHEEL (constrained)
            cropCanvas.setOnScroll(e -> {
                double zoomFactor = e.getDeltaY() > 0 ? 1.1 : 0.9;

                double oldWidth = imageView.getFitWidth();
                double oldHeight = imageView.getFitHeight();
                double newWidth = oldWidth * zoomFactor;

                // Calculate new height maintaining aspect ratio
                double aspectRatio = imgHeight / imgWidth;
                double newHeight = newWidth * aspectRatio;

                // CRITICAL: Minimum zoom is the initial scale (when image first loaded)
                // This ensures you can always zoom back out to the starting view
                if (newWidth < initialWidth || newHeight < initialHeight) {
                    return;
                }

                // MAXIMUM ZOOM: Industry standard for profile pictures is 2x
                // Instagram/Twitter/LinkedIn all limit zoom to prevent pixelation
                // 2x is enough to focus on face details without quality loss
                double maxWidth = initialWidth * 2.0;  // Changed from 4x original to 2x initial
                double maxHeight = initialHeight * 2.0;

                if (newWidth > maxWidth || newHeight > maxHeight) {
                    return;
                }

                // Calculate relative position in image
                double relX = (cropCenterX - imageView.getLayoutX()) / oldWidth;
                double relY = (cropCenterY - imageView.getLayoutY()) / oldHeight;

                // Update size
                imageView.setFitWidth(newWidth);
                imageView.setFitHeight(newHeight);

                // Reposition to maintain relative position
                double newX = cropCenterX - (relX * newWidth);
                double newY = cropCenterY - (relY * newHeight);

                // Constrain to keep crop area filled
                double minX = cropX + CROP_SIZE - newWidth;
                double maxX = cropX;
                double minY = cropY + CROP_SIZE - newHeight;
                double maxY = cropY;

                newX = Math.max(minX, Math.min(newX, maxX));
                newY = Math.max(minY, Math.min(newY, maxY));

                imageView.setLayoutX(newX);
                imageView.setLayoutY(newY);

                e.consume();
            });

            cropCanvas.getChildren().addAll(
                    imageView,
                    overlayWithCutout
            );


            // Size info label
            Label sizeLabel = new Label("Output: 300×300px");
            sizeLabel.setStyle("-fx-text-fill: #00D9FF; -fx-font-size: 12; -fx-font-weight: 600;");
            sizeLabel.setAlignment(Pos.CENTER);

            // Buttons
            HBox buttonBox = new HBox(15);
            buttonBox.setAlignment(Pos.CENTER);

            Button cropButton = new Button("Crop & Apply");
            cropButton.setStyle("-fx-background-color: linear-gradient(to right, #00D9FF, #0099FF); -fx-text-fill: white; -fx-font-size: 14; -fx-font-weight: 600; -fx-padding: 12 35; -fx-background-radius: 10; -fx-effect: dropshadow(gaussian, rgba(0, 153, 255, 0.5), 12, 0, 0, 3); -fx-cursor: hand;");
            cropButton.setPrefWidth(200);

            cropButton.setOnMouseEntered(e -> cropButton.setStyle("-fx-background-color: linear-gradient(to right, #00EEFF, #00AAFF); -fx-text-fill: white; -fx-font-size: 14; -fx-font-weight: 600; -fx-padding: 12 35; -fx-background-radius: 10; -fx-effect: dropshadow(gaussian, rgba(0, 153, 255, 0.7), 15, 0, 0, 4); -fx-cursor: hand;"));
            cropButton.setOnMouseExited(e -> cropButton.setStyle("-fx-background-color: linear-gradient(to right, #00D9FF, #0099FF); -fx-text-fill: white; -fx-font-size: 14; -fx-font-weight: 600; -fx-padding: 12 35; -fx-background-radius: 10; -fx-effect: dropshadow(gaussian, rgba(0, 153, 255, 0.5), 12, 0, 0, 3); -fx-cursor: hand;"));

            cropButton.setOnAction(e -> {
                try {
                    // Calculate scale between displayed image and original
                    double scaleX = originalImage.getWidth() / imageView.getFitWidth();
                    double scaleY = originalImage.getHeight() / imageView.getFitHeight();

                    // Calculate crop area relative to the image position
                    double relCropX = cropX - imageView.getLayoutX();
                    double relCropY = cropY - imageView.getLayoutY();

                    // Convert to original image coordinates
                    int origX = (int) Math.max(0, relCropX * scaleX);
                    int origY = (int) Math.max(0, relCropY * scaleY);
                    int origW = (int) (CROP_SIZE * scaleX);
                    int origH = (int) (CROP_SIZE * scaleY);

                    // Ensure we don't exceed image bounds
                    origW = Math.min(origW, originalImage.getWidth() - origX);
                    origH = Math.min(origH, originalImage.getHeight() - origY);

                    if (origW <= 0 || origH <= 0) {
                        new Alert(Alert.AlertType.ERROR, "Invalid crop area. Please zoom in or reposition the image.").show();
                        return;
                    }

                    // Crop the image
                    BufferedImage croppedImage = originalImage.getSubimage(origX, origY, origW, origH);

                    // Resize to exactly 300x300 (standard profile picture size)
                    BufferedImage resizedImage = new BufferedImage(300, 300, BufferedImage.TYPE_INT_ARGB);
                    Graphics2D g = resizedImage.createGraphics();
                    g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                    g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                    g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g.drawImage(croppedImage, 0, 0, 300, 300, null);
                    g.dispose();

                    // Convert to PNG bytes
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    ImageIO.write(resizedImage, "png", out);
                    uploadBytesTarget[0] = out.toByteArray();

                    // Update preview (this will show the cropped image)
                    Image previewImage = new Image(new ByteArrayInputStream(uploadBytesTarget[0]), 120, 120, false, true);
                    previewTarget.setImage(previewImage);

                    cropStage.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    new Alert(Alert.AlertType.ERROR, "Failed to crop image: " + ex.getMessage()).show();
                }
            });

            Button cancelButton = new Button("Cancel");
            cancelButton.setStyle("-fx-background-color: rgba(60, 60, 80, 0.5); -fx-border-color: rgba(100, 100, 120, 0.6); -fx-border-width: 1.5; -fx-border-radius: 10; -fx-background-radius: 10; -fx-text-fill: #AAAACC; -fx-font-size: 14; -fx-font-weight: 600; -fx-padding: 12 35; -fx-cursor: hand;");
            cancelButton.setPrefWidth(200);
            cancelButton.setOnAction(e -> cropStage.close());

            cancelButton.setOnMouseEntered(e -> cancelButton.setStyle("-fx-background-color: rgba(70, 70, 90, 0.7); -fx-border-color: rgba(120, 120, 140, 0.8); -fx-border-width: 1.5; -fx-border-radius: 10; -fx-background-radius: 10; -fx-text-fill: #CCCCEE; -fx-font-size: 14; -fx-font-weight: 600; -fx-padding: 12 35; -fx-cursor: hand;"));
            cancelButton.setOnMouseExited(e -> cancelButton.setStyle("-fx-background-color: rgba(60, 60, 80, 0.5); -fx-border-color: rgba(100, 100, 120, 0.6); -fx-border-width: 1.5; -fx-border-radius: 10; -fx-background-radius: 10; -fx-text-fill: #AAAACC; -fx-font-size: 14; -fx-font-weight: 600; -fx-padding: 12 35; -fx-cursor: hand;"));

            buttonBox.getChildren().addAll(cropButton, cancelButton);

            mainContainer.getChildren().addAll(titleLabel, instructionLabel, cropCanvas, sizeLabel, buttonBox);
            overlay.getChildren().add(mainContainer);

            Scene scene = new Scene(overlay, 620, 800);
            scene.setFill(Color.TRANSPARENT);
            cropStage.setScene(scene);

            cropStage.setOnShown(ev -> {
                Stage parentStage = (profileSettingsStage != null && profileSettingsStage.isShowing())
                        ? profileSettingsStage
                        : primaryStage;
                double x = parentStage.getX() + (parentStage.getWidth() - cropStage.getWidth()) / 2;
                double y = parentStage.getY() + (parentStage.getHeight() - cropStage.getHeight()) / 2;
                cropStage.setX(Math.max(0, x));
                cropStage.setY(Math.max(0, y));
            });

            cropStage.show();

        } catch (Exception ex) {
            ex.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load image: " + ex.getMessage()).show();
        }
    }

    public void invalidateProfilePictureCache(String username) {
        if (username == null || username.isEmpty()) return;

        profilePictureCache.remove(username);
        System.out.println("[CACHE] Profile picture cache invalidated for user: " + username);

        // If leaderboard is currently showing, refresh it to display updated picture
        if (leaderboardStage != null && leaderboardStage.isShowing() && leaderboardTable != null) {
            Platform.runLater(() -> {
                // Force reload of this user's profile picture
                loadProfilePictureAsync(username, image -> {
                    Platform.runLater(() -> {
                        profilePictureCache.put(username, image);
                        leaderboardTable.refresh();
                        System.out.println("[LEADERBOARD] Profile picture refreshed for: " + username);
                    });
                });
            });
        }
    }

    public void showToastNotification(String message) {
        Label toast = new Label(message);
        toast.setStyle("-fx-background-color: rgba(0, 255, 100, 0.9); -fx-text-fill: white; " +
                "-fx-font-size: 14; -fx-padding: 15 25; -fx-background-radius: 10; " +
                "-fx-effect: dropshadow(gaussian, black, 10, 0.5, 0, 0);");

        StackPane toastPane = new StackPane(toast);
        toastPane.setAlignment(Pos.BOTTOM_CENTER);
        toastPane.setPadding(new Insets(0, 0, 50, 0));
        toastPane.setMouseTransparent(true);

        root.getChildren().add(toastPane);

        FadeTransition fadeIn = new FadeTransition(Duration.millis(300), toast);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);

        FadeTransition fadeOut = new FadeTransition(Duration.millis(300), toast);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);
        fadeOut.setDelay(Duration.seconds(2));
        fadeOut.setOnFinished(e -> root.getChildren().remove(toastPane));

        fadeIn.setOnFinished(e -> fadeOut.play());
        fadeIn.play();
    }

    private void updateProfileDisplayInLevelSelect() {
        // Look for the top bar (it's an HBox)
        if (!(root.getTop() instanceof HBox topBox)) return;

        // Inside the top bar, the first big child is usually the user box (HBox with avatar + name)
        for (javafx.scene.Node node : topBox.getChildren()) {
            if (node instanceof HBox userBox && userBox.getChildren().size() >= 2) {
                javafx.scene.Node firstChild = userBox.getChildren().get(0);
                javafx.scene.Node secondChild = userBox.getChildren().get(1);

                // Update profile picture
                if (firstChild instanceof ImageView profilePicView) {
                    if (currentProfileImage != null) {
                        profilePicView.setImage(currentProfileImage);
                    } else {
                        // Fallback to generated default
                        byte[] defaultBytes = getDefaultProfilePicBytes();
                        if (defaultBytes != null) {
                            profilePicView.setImage(new Image(new ByteArrayInputStream(defaultBytes)));
                        }
                    }
                }

                // Update username label
                if (secondChild instanceof Label usernameLabel) {
                    usernameLabel.setText(currentUser);
                }
                // If the layout is reversed (rare), also check the other way
                else if (secondChild instanceof ImageView profilePicView && firstChild instanceof Label usernameLabel) {
                    if (currentProfileImage != null) {
                        profilePicView.setImage(currentProfileImage);
                    }
                    usernameLabel.setText(currentUser);
                }

                return; // Done — no need to check further
            }
        }
    }

    private void downloadAudioResources() {
        File audioFolder = new File(AUDIO_FOLDER);
        if (!audioFolder.exists()) {
            audioFolder.mkdirs();
        }

        // We will count how many files we still need to download
        final int[] pending = {0};

        for (int i = 0; i < AUDIO_FILES.length; i++) {
            File dest = new File(AUDIO_FOLDER + File.separator + AUDIO_FILES[i]);
            if (dest.exists()) {
                continue; // already cached
            }

            pending[0]++;
            final int index = i;

            // Download in background
            executor.submit(() -> {
                try {
                    System.out.println("Downloading audio: " + AUDIO_FILES[index]);
                    URL url = new URL(DOWNLOAD_URLS[index]);
                    try (InputStream in = url.openStream()) {
                        Files.copy(in, dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    }
                    System.out.println("Saved: " + dest.getName());
                } catch (Exception e) {
                    System.err.println("Failed to download " + AUDIO_FILES[index] + ": " + e.getMessage());
                } finally {
                    // When the last file finishes → initialise audio objects
                    synchronized (pending) {
                        pending[0]--;
                        if (pending[0] == 0) {
                            Platform.runLater(this::initializeAudioObjects);
                        }
                    }
                }
            });
        }

        // If nothing had to be downloaded → initialise immediately
        if (pending[0] == 0) {
            Platform.runLater(this::initializeAudioObjects);
        }
    }

//─────────────────────────────────────────────────────────────────────────────
//  NEW METHOD – creates AudioClip & MediaPlayers only when files exist
// ─────────────────────────────────────────────────────────────────────────────
    private void initializeAudioObjects() {
        if (audioResourcesReady) return;

        try {
            clickSound = new AudioClip(new File(AUDIO_FOLDER + File.separator + CLICK_AUDIO_FILE).toURI().toString());
            clickSound.setVolume(audioVolume);
            clickSound.setCycleCount(1);

            // Background music players (you can keep them as fields)
            progressionPathAudio = createMediaPlayer(PROGRESSION_PATH_AUDIO_FILE);
            level1Audio = createMediaPlayer(LEVEL1_AUDIO_FILE);
            level2Audio = createMediaPlayer(LEVEL2_AUDIO_FILE);
            level3Audio = createMediaPlayer(LEVEL3_AUDIO_FILE);
            level4Audio = createMediaPlayer(LEVEL4_AUDIO_FILE);
            level5Audio = createMediaPlayer(LEVEL5_AUDIO_FILE);
            level6Audio = createMediaPlayer(LEVEL6_AUDIO_FILE);
            level7Audio = createMediaPlayer(LEVEL7_AUDIO_FILE);
            level8Audio = createMediaPlayer(LEVEL8_AUDIO_FILE);
            level9Audio = createMediaPlayer(LEVEL9_AUDIO_FILE);
            level10Audio = createMediaPlayer(LEVEL10_AUDIO_FILE);

            // apply current mute/volume settings
            updateAudioSettings();

            audioResourcesReady = true;
            System.out.println("All audio resources loaded successfully.");
        } catch (Exception e) {
            System.err.println("Could not create audio objects even after download: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Helper to avoid repeating the same code 11 times
    private MediaPlayer createMediaPlayer(String fileName) {
        String path = new File(AUDIO_FOLDER + File.separator + fileName).toURI().toString();
        Media media = new Media(path);
        MediaPlayer mp = new MediaPlayer(media);
        mp.setVolume(isMusicMuted ? 0.0 : audioVolume);
        mp.setCycleCount(MediaPlayer.INDEFINITE); // loop background music
        return mp;
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

    private void showAdminPanel() {
        new AdminPanel(this).show();
    }

    public void showResetProgressDialog() {
        // Create new Stage window if it doesn't exist
        if (resetProgressStage == null) {
            resetProgressStage = new Stage();
            resetProgressStage.initOwner(primaryStage);
            resetProgressStage.initModality(Modality.WINDOW_MODAL);
            resetProgressStage.initStyle(StageStyle.TRANSPARENT);
            resetProgressStage.setTitle("Reset All Progress");
            resetProgressStage.setResizable(false);

            // Outer container with transparent background
            StackPane overlay = new StackPane();
            overlay.setStyle("-fx-background-color: transparent;");
            overlay.setPadding(new Insets(40));

            // Main dialog container
// Main dialog container
            resetProgressDialog = new VBox(20);
            resetProgressDialog.setAlignment(Pos.CENTER);
            resetProgressDialog.setPadding(new Insets(30));
            resetProgressDialog.setMaxWidth(700);
            resetProgressDialog.setStyle(
                    "-fx-background-color: linear-gradient(to bottom, #1a1a2e, #16213e, #0f3460);" +
                            "-fx-background-radius: 20;" +
                            "-fx-effect: dropshadow(gaussian, rgba(255, 68, 68, 0.6), 30, 0.6, 0, 0);"
            );

            // Warning icon and title
            // Warning icon and title
            Label warningIcon = new Label("⚠");
            warningIcon.setStyle(
                    "-fx-text-fill: #FF4444;" +
                            "-fx-font-size: 48;" +
                            "-fx-effect: dropshadow(gaussian, rgba(255, 68, 68, 0.3), 8, 0.4, 0, 0);"
            );

            Label titleLabel = new Label("NUCLEAR OPTION");
            titleLabel.setStyle(
                    "-fx-text-fill: #FF4444;" +
                            "-fx-font-family: 'Arial Black';" +
                            "-fx-font-size: 28;" +
                            "-fx-font-weight: bold;" +
                            "-fx-effect: dropshadow(gaussian, rgba(255, 68, 68, 0.6), 10, 0.7, 0, 0);"
            );

            Label subtitleLabel = new Label("Reset All Progress - WARNING");
            subtitleLabel.setStyle(
                    "-fx-text-fill: #FFAA00;" +
                            "-fx-font-family: 'Arial';" +
                            "-fx-font-size: 16;" +
                            "-fx-font-weight: bold;"
            );

            VBox titleBox = new VBox(8, warningIcon, titleLabel, subtitleLabel);
            titleBox.setAlignment(Pos.CENTER);

            // Warning message
            Label messageLabel = new Label(
                    "PERMANENTLY erase ALL player progress AND leaderboard scores?\n\n" +
                            "This will affect:\n" +
                            "• ALL leaderboard records will be DELETED\n" +
                            "• ALL players will be reset to Level 1\n" +
                            "• User accounts will remain active\n\n" +
                            "⚠ THIS ACTION CANNOT BE UNDONE ⚠"
            );
            messageLabel.setStyle(
                    "-fx-text-fill: #FFFFFF;" +
                            "-fx-font-family: 'Arial';" +
                            "-fx-font-size: 13;" +
                            "-fx-text-alignment: center;" +
                            "-fx-line-spacing: 2px;"
            );
            messageLabel.setWrapText(true);
            messageLabel.setMaxWidth(600);
            messageLabel.setAlignment(Pos.CENTER);

            // Action buttons
            Button continueBtn = new Button("CONTINUE");
            continueBtn.setPrefWidth(200);
            continueBtn.setStyle(
                    "-fx-background-color: linear-gradient(to bottom, #ff4444, #cc2222);" +
                            "-fx-text-fill: white;" +
                            "-fx-font-weight: bold;" +
                            "-fx-font-size: 14;" +
                            "-fx-padding: 12 24;" +
                            "-fx-background-radius: 10;" +
                            "-fx-cursor: hand;" +
                            "-fx-effect: dropshadow(gaussian, rgba(255, 68, 68, 0.5), 10, 0.5, 0, 0);"
            );
            continueBtn.setOnMouseEntered(ev -> continueBtn.setStyle(
                    "-fx-background-color: linear-gradient(to bottom, #ff6666, #ee4444);" +
                            "-fx-text-fill: white;" +
                            "-fx-font-weight: bold;" +
                            "-fx-font-size: 14;" +
                            "-fx-padding: 12 24;" +
                            "-fx-background-radius: 10;" +
                            "-fx-cursor: hand;" +
                            "-fx-effect: dropshadow(gaussian, rgba(255, 68, 68, 0.7), 12, 0.6, 0, 0);"
            ));
            continueBtn.setOnMouseExited(ev -> continueBtn.setStyle(
                    "-fx-background-color: linear-gradient(to bottom, #ff4444, #cc2222);" +
                            "-fx-text-fill: white;" +
                            "-fx-font-weight: bold;" +
                            "-fx-font-size: 14;" +
                            "-fx-padding: 12 24;" +
                            "-fx-background-radius: 10;" +
                            "-fx-cursor: hand;" +
                            "-fx-effect: dropshadow(gaussian, rgba(255, 68, 68, 0.5), 10, 0.5, 0, 0);"
            ));
            continueBtn.setOnAction(ev -> {
                resetProgressStage.close();
                showTextConfirmationDialog();
            });

            Button cancelBtn = new Button("Cancel");
            cancelBtn.setPrefWidth(200);
            cancelBtn.setStyle(
                    "-fx-background-color: rgba(136, 136, 255, 0.2);" +
                            "-fx-text-fill: #8888ff;" +
                            "-fx-font-weight: bold;" +
                            "-fx-font-size: 14;" +
                            "-fx-padding: 12 24;" +
                            "-fx-background-radius: 10;" +
                            "-fx-border-color: #8888ff;" +
                            "-fx-border-radius: 10;" +
                            "-fx-border-width: 2;" +
                            "-fx-cursor: hand;"
            );
            cancelBtn.setOnMouseEntered(ev -> cancelBtn.setStyle(
                    "-fx-background-color: rgba(136, 136, 255, 0.3);" +
                            "-fx-text-fill: #aaaaff;" +
                            "-fx-font-weight: bold;" +
                            "-fx-font-size: 14;" +
                            "-fx-padding: 12 24;" +
                            "-fx-background-radius: 10;" +
                            "-fx-border-color: #aaaaff;" +
                            "-fx-border-radius: 10;" +
                            "-fx-border-width: 2;" +
                            "-fx-cursor: hand;"
            ));
            cancelBtn.setOnMouseExited(ev -> cancelBtn.setStyle(
                    "-fx-background-color: rgba(136, 136, 255, 0.2);" +
                            "-fx-text-fill: #8888ff;" +
                            "-fx-font-weight: bold;" +
                            "-fx-font-size: 14;" +
                            "-fx-padding: 12 24;" +
                            "-fx-background-radius: 10;" +
                            "-fx-border-color: #8888ff;" +
                            "-fx-border-radius: 10;" +
                            "-fx-border-width: 2;" +
                            "-fx-cursor: hand;"
            ));
            cancelBtn.setOnAction(ev -> resetProgressStage.close());

            HBox buttonBox = new HBox(20, continueBtn, cancelBtn);
            buttonBox.setAlignment(Pos.CENTER);

            // Assemble the dialog
            resetProgressDialog.getChildren().addAll(titleBox, messageLabel, buttonBox);
            overlay.getChildren().add(resetProgressDialog);
            StackPane.setAlignment(resetProgressDialog, Pos.CENTER);

            // Create scene with transparent background
            Scene scene = new Scene(overlay, 750, 550);
            scene.setFill(Color.TRANSPARENT);

            resetProgressStage.setScene(scene);

            // Perfect centering
            resetProgressStage.setOnShown(ev -> {
                double x = primaryStage.getX() + (primaryStage.getWidth() - resetProgressStage.getWidth()) / 2;
                double y = primaryStage.getY() + (primaryStage.getHeight() - resetProgressStage.getHeight()) / 2;
                resetProgressStage.setX(x);
                resetProgressStage.setY(y);
            });
        }

        // Show the stage window
        resetProgressStage.show();
        resetProgressStage.requestFocus();
    }

    private void showTextConfirmationDialog() {
        Stage textStage = new Stage();
        textStage.initOwner(primaryStage);
        textStage.initModality(Modality.WINDOW_MODAL);
        textStage.initStyle(StageStyle.TRANSPARENT);
        textStage.setTitle("Final Confirmation Required");
        textStage.setResizable(false);

        StackPane overlay = new StackPane();
        overlay.setStyle("-fx-background-color: transparent;");
        overlay.setPadding(new Insets(40));

        VBox dialog = new VBox(25);
        dialog.setAlignment(Pos.CENTER);
        dialog.setPadding(new Insets(40));
        dialog.setMaxWidth(650);
        dialog.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #1a1a2e, #16213e, #0f3460);" +
                        "-fx-background-radius: 20;" +
                        "-fx-effect: dropshadow(gaussian, rgba(255, 170, 0, 0.6), 30, 0.6, 0, 0);"
        );

        Label titleLabel = new Label("TYPE TO CONFIRM");
        titleLabel.setStyle(
                "-fx-text-fill: #FFAA00;" +
                        "-fx-font-family: 'Arial Black';" +
                        "-fx-font-size: 28;" +
                        "-fx-font-weight: bold;" +
                        "-fx-effect: dropshadow(gaussian, rgba(255, 170, 0, 0.6), 10, 0.7, 0, 0);"
        );

        Label instructionLabel = new Label(
                "To proceed with this DANGEROUS operation,\ntype exactly:\n\nRESET EVERYTHING"
        );
        instructionLabel.setStyle(
                "-fx-text-fill: #FFFFFF;" +
                        "-fx-font-family: 'Arial';" +
                        "-fx-font-size: 15;" +
                        "-fx-text-alignment: center;" +
                        "-fx-line-spacing: 5px;"
        );
        instructionLabel.setWrapText(true);
        instructionLabel.setMaxWidth(550);
        instructionLabel.setAlignment(Pos.CENTER);

        TextField confirmTextField = new TextField();
        confirmTextField.setPromptText("Type here...");
        confirmTextField.setMaxWidth(400);
        confirmTextField.setStyle(
                "-fx-background-color: rgba(255, 255, 255, 0.1);" +
                        "-fx-text-fill: white;" +
                        "-fx-prompt-text-fill: rgba(255, 255, 255, 0.5);" +
                        "-fx-font-size: 14;" +
                        "-fx-padding: 12;" +
                        "-fx-background-radius: 8;" +
                        "-fx-border-color: #FFAA00;" +
                        "-fx-border-radius: 8;" +
                        "-fx-border-width: 2;"
        );

        // Limit character input to 20 characters
        confirmTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > 20) {
                confirmTextField.setText(oldValue);
            }
        });

        Button confirmBtn = new Button("CONFIRM");
        confirmBtn.setPrefWidth(200);
        confirmBtn.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #ffaa00, #cc8800);" +
                        "-fx-text-fill: black;" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 14;" +
                        "-fx-padding: 12 24;" +
                        "-fx-background-radius: 10;" +
                        "-fx-cursor: hand;" +
                        "-fx-effect: dropshadow(gaussian, rgba(255, 170, 0, 0.5), 10, 0.5, 0, 0);"
        );
        confirmBtn.setOnMouseEntered(ev -> confirmBtn.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #ffbb22, #ee9900);" +
                        "-fx-text-fill: black;" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 14;" +
                        "-fx-padding: 12 24;" +
                        "-fx-background-radius: 10;" +
                        "-fx-cursor: hand;" +
                        "-fx-effect: dropshadow(gaussian, rgba(255, 170, 0, 0.7), 12, 0.6, 0, 0);"
        ));
        confirmBtn.setOnMouseExited(ev -> confirmBtn.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #ffaa00, #cc8800);" +
                        "-fx-text-fill: black;" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 14;" +
                        "-fx-padding: 12 24;" +
                        "-fx-background-radius: 10;" +
                        "-fx-cursor: hand;" +
                        "-fx-effect: dropshadow(gaussian, rgba(255, 170, 0, 0.5), 10, 0.5, 0, 0);"
        ));
        confirmBtn.setOnAction(ev -> {
            String inputText = confirmTextField.getText().trim();
            if (!"RESET EVERYTHING".equalsIgnoreCase(inputText)) {
                textStage.close();
                showErrorDialog("Wrong phrase entered.\n\nYou typed: \"" + inputText + "\"\n\nOperation cancelled for safety.");
            } else {
                textStage.close();
                showFinalWarningDialog();
            }
        });

        Button cancelBtn = new Button("Cancel");
        cancelBtn.setPrefWidth(200);
        cancelBtn.setStyle(
                "-fx-background-color: rgba(136, 136, 255, 0.2);" +
                        "-fx-text-fill: #8888ff;" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 14;" +
                        "-fx-padding: 12 24;" +
                        "-fx-background-radius: 10;" +
                        "-fx-border-color: #8888ff;" +
                        "-fx-border-radius: 10;" +
                        "-fx-border-width: 2;" +
                        "-fx-cursor: hand;"
        );
        cancelBtn.setOnMouseEntered(ev -> cancelBtn.setStyle(
                "-fx-background-color: rgba(136, 136, 255, 0.3);" +
                        "-fx-text-fill: #aaaaff;" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 14;" +
                        "-fx-padding: 12 24;" +
                        "-fx-background-radius: 10;" +
                        "-fx-border-color: #aaaaff;" +
                        "-fx-border-radius: 10;" +
                        "-fx-border-width: 2;" +
                        "-fx-cursor: hand;"
        ));
        cancelBtn.setOnMouseExited(ev -> cancelBtn.setStyle(
                "-fx-background-color: rgba(136, 136, 255, 0.2);" +
                        "-fx-text-fill: #8888ff;" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 14;" +
                        "-fx-padding: 12 24;" +
                        "-fx-background-radius: 10;" +
                        "-fx-border-color: #8888ff;" +
                        "-fx-border-radius: 10;" +
                        "-fx-border-width: 2;" +
                        "-fx-cursor: hand;"
        ));
        cancelBtn.setOnAction(ev -> textStage.close());

        HBox buttonBox = new HBox(20, confirmBtn, cancelBtn);
        buttonBox.setAlignment(Pos.CENTER);

        dialog.getChildren().addAll(titleLabel, instructionLabel, confirmTextField, buttonBox);
        overlay.getChildren().add(dialog);
        StackPane.setAlignment(dialog, Pos.CENTER);

        Scene scene = new Scene(overlay, 750, 500);
        scene.setFill(Color.TRANSPARENT);
        textStage.setScene(scene);

        textStage.setOnShown(ev -> {
            double x = primaryStage.getX() + (primaryStage.getWidth() - textStage.getWidth()) / 2;
            double y = primaryStage.getY() + (primaryStage.getHeight() - textStage.getHeight()) / 2;
            textStage.setX(x);
            textStage.setY(y);
            Platform.runLater(() -> confirmTextField.requestFocus());
        });

        textStage.show();
    }

    private void showFinalWarningDialog() {
        Stage finalStage = new Stage();
        finalStage.initOwner(primaryStage);
        finalStage.initModality(Modality.WINDOW_MODAL);
        finalStage.initStyle(StageStyle.TRANSPARENT);
        finalStage.setTitle("FINAL WARNING");
        finalStage.setResizable(false);

        StackPane overlay = new StackPane();
        overlay.setStyle("-fx-background-color: transparent;");
        overlay.setPadding(new Insets(40));

        VBox dialog = new VBox(25);
        dialog.setAlignment(Pos.CENTER);
        dialog.setPadding(new Insets(40));
        dialog.setMaxWidth(700);
        dialog.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #2e1a1a, #3e1616, #460f0f);" +
                        "-fx-background-radius: 20;" +
                        "-fx-effect: dropshadow(gaussian, rgba(255, 0, 0, 0.8), 40, 0.7, 0, 0);"
        );

        // Close button
        Button closeButton = new Button("✕");
        closeButton.setStyle(
                "-fx-background-color: rgba(255, 68, 68, 0.2);" +
                        "-fx-text-fill: #FF4444;" +
                        "-fx-font-family: 'Arial';" +
                        "-fx-font-size: 18;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 20;" +
                        "-fx-padding: 8 12;" +
                        "-fx-border-color: #FF4444;" +
                        "-fx-border-radius: 20;" +
                        "-fx-border-width: 2;" +
                        "-fx-cursor: hand;"
        );
        closeButton.setOnMouseEntered(ev -> closeButton.setStyle(
                "-fx-background-color: #FF4444;" +
                        "-fx-text-fill: #FFFFFF;" +
                        "-fx-font-family: 'Arial';" +
                        "-fx-font-size: 18;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 20;" +
                        "-fx-padding: 8 12;" +
                        "-fx-border-color: #FF4444;" +
                        "-fx-border-radius: 20;" +
                        "-fx-border-width: 2;" +
                        "-fx-cursor: hand;"
        ));
        closeButton.setOnMouseExited(ev -> closeButton.setStyle(
                "-fx-background-color: rgba(255, 68, 68, 0.2);" +
                        "-fx-text-fill: #FF4444;" +
                        "-fx-font-family: 'Arial';" +
                        "-fx-font-size: 18;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 20;" +
                        "-fx-padding: 8 12;" +
                        "-fx-border-color: #FF4444;" +
                        "-fx-border-radius: 20;" +
                        "-fx-border-width: 2;" +
                        "-fx-cursor: hand;"
        ));
        closeButton.setOnAction(ev -> finalStage.close());

        HBox topBar = new HBox(closeButton);
        topBar.setAlignment(Pos.TOP_RIGHT);

        Label skullIcon = new Label("💀");
        skullIcon.setStyle(
                "-fx-font-size: 60;" +
                        "-fx-effect: dropshadow(gaussian, rgba(255, 0, 0, 0.8), 20, 0.8, 0, 0);"
        );

        Label titleLabel = new Label("FINAL WARNING");
        titleLabel.setStyle(
                "-fx-text-fill: #FF0000;" +
                        "-fx-font-family: 'Arial Black';" +
                        "-fx-font-size: 32;" +
                        "-fx-font-weight: bold;" +
                        "-fx-effect: dropshadow(gaussian, rgba(255, 0, 0, 0.8), 12, 0.8, 0, 0);"
        );

        Label subtitleLabel = new Label("LAST CHANCE TO CANCEL");
        subtitleLabel.setStyle(
                "-fx-text-fill: #FFAA00;" +
                        "-fx-font-family: 'Arial';" +
                        "-fx-font-size: 16;" +
                        "-fx-font-weight: bold;"
        );

        VBox titleBox = new VBox(10, skullIcon, titleLabel, subtitleLabel);
        titleBox.setAlignment(Pos.CENTER);

        Label messageLabel = new Label(
                "This is your FINAL WARNING.\n\n" +
                        "Clicking YES will:\n" +
                        "• Delete ALL leaderboard data\n" +
                        "• Reset ALL players to Level 1\n" +
                        "• Clear the entire game history\n\n" +
                        "⚠ NO RECOVERY POSSIBLE ⚠\n\n" +
                        "Are you 100% absolutely certain?"
        );
        messageLabel.setStyle(
                "-fx-text-fill: #FFFFFF;" +
                        "-fx-font-family: 'Arial';" +
                        "-fx-font-size: 15;" +
                        "-fx-text-alignment: center;" +
                        "-fx-line-spacing: 5px;"
        );
        messageLabel.setWrapText(true);
        messageLabel.setMaxWidth(600);
        messageLabel.setAlignment(Pos.CENTER);

        Button yesBtn = new Button("YES, DELETE");
        yesBtn.setPrefWidth(220);
        yesBtn.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #cc0000, #990000);" +
                        "-fx-text-fill: white;" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 15;" +
                        "-fx-padding: 14 28;" +
                        "-fx-background-radius: 10;" +
                        "-fx-cursor: hand;" +
                        "-fx-effect: dropshadow(gaussian, rgba(255, 0, 0, 0.6), 12, 0.6, 0, 0);"
        );
        yesBtn.setOnMouseEntered(ev -> yesBtn.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #ff0000, #cc0000);" +
                        "-fx-text-fill: white;" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 15;" +
                        "-fx-padding: 14 28;" +
                        "-fx-background-radius: 10;" +
                        "-fx-cursor: hand;" +
                        "-fx-effect: dropshadow(gaussian, rgba(255, 0, 0, 0.8), 15, 0.7, 0, 0);"
        ));
        yesBtn.setOnMouseExited(ev -> yesBtn.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #cc0000, #990000);" +
                        "-fx-text-fill: white;" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 15;" +
                        "-fx-padding: 14 28;" +
                        "-fx-background-radius: 10;" +
                        "-fx-cursor: hand;" +
                        "-fx-effect: dropshadow(gaussian, rgba(255, 0, 0, 0.6), 12, 0.6, 0, 0);"
        ));
        yesBtn.setOnAction(ev -> {
            finalStage.close();
            executeReset();
        });

        Button noBtn = new Button("NO, CANCEL");
        noBtn.setPrefWidth(220);
        noBtn.setStyle(
                "-fx-background-color: rgba(68, 255, 68, 0.2);" +
                        "-fx-text-fill: #44ff44;" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 15;" +
                        "-fx-padding: 14 28;" +
                        "-fx-background-radius: 10;" +
                        "-fx-border-color: #44ff44;" +
                        "-fx-border-radius: 10;" +
                        "-fx-border-width: 2;" +
                        "-fx-cursor: hand;"
        );
        noBtn.setOnMouseEntered(ev -> noBtn.setStyle(
                "-fx-background-color: rgba(68, 255, 68, 0.3);" +
                        "-fx-text-fill: #66ff66;" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 15;" +
                        "-fx-padding: 14 28;" +
                        "-fx-background-radius: 10;" +
                        "-fx-border-color: #66ff66;" +
                        "-fx-border-radius: 10;" +
                        "-fx-border-width: 2;" +
                        "-fx-cursor: hand;"
        ));
        noBtn.setOnMouseExited(ev -> noBtn.setStyle(
                "-fx-background-color: rgba(68, 255, 68, 0.2);" +
                        "-fx-text-fill: #44ff44;" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 15;" +
                        "-fx-padding: 14 28;" +
                        "-fx-background-radius: 10;" +
                        "-fx-border-color: #44ff44;" +
                        "-fx-border-radius: 10;" +
                        "-fx-border-width: 2;" +
                        "-fx-cursor: hand;"
        ));
        noBtn.setOnAction(ev -> finalStage.close());

        HBox buttonBox = new HBox(20, yesBtn, noBtn);
        buttonBox.setAlignment(Pos.CENTER);

        dialog.getChildren().addAll(topBar, titleBox, messageLabel, buttonBox);
        overlay.getChildren().add(dialog);
        StackPane.setAlignment(dialog, Pos.CENTER);

        Scene scene = new Scene(overlay, 800, 750);
        scene.setFill(Color.TRANSPARENT);
        finalStage.setScene(scene);

        finalStage.setOnShown(ev -> {
            double x = primaryStage.getX() + (primaryStage.getWidth() - finalStage.getWidth()) / 2;
            double y = primaryStage.getY() + (primaryStage.getHeight() - finalStage.getHeight()) / 2;
            finalStage.setX(x);
            finalStage.setY(y);
        });

        finalStage.show();
    }

    private void showErrorDialog(String message) {
        Stage errorStage = new Stage();
        errorStage.initOwner(primaryStage);
        errorStage.initModality(Modality.WINDOW_MODAL);
        errorStage.initStyle(StageStyle.TRANSPARENT);
        errorStage.setTitle("Confirmation Failed");
        errorStage.setResizable(false);

        StackPane overlay = new StackPane();
        overlay.setStyle("-fx-background-color: transparent;");
        overlay.setPadding(new Insets(40));

        VBox dialog = new VBox(20);
        dialog.setAlignment(Pos.CENTER);
        dialog.setPadding(new Insets(35));
        dialog.setMaxWidth(550);
        dialog.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #1a1a2e, #16213e, #0f3460);" +
                        "-fx-background-radius: 20;" +
                        "-fx-effect: dropshadow(gaussian, rgba(255, 68, 68, 0.5), 25, 0.5, 0, 0);"
        );

        Label iconLabel = new Label("✗");
        iconLabel.setStyle(
                "-fx-text-fill: #FF4444;" +
                        "-fx-font-size: 50;" +
                        "-fx-effect: dropshadow(gaussian, rgba(255, 68, 68, 0.7), 12, 0.7, 0, 0);"
        );

        Label titleLabel = new Label("Confirmation Failed");
        titleLabel.setStyle(
                "-fx-text-fill: #FF4444;" +
                        "-fx-font-family: 'Arial Black';" +
                        "-fx-font-size: 24;" +
                        "-fx-font-weight: bold;"
        );

        Label messageLabel = new Label(message);
        messageLabel.setStyle(
                "-fx-text-fill: #FFFFFF;" +
                        "-fx-font-family: 'Arial';" +
                        "-fx-font-size: 14;" +
                        "-fx-text-alignment: center;" +
                        "-fx-line-spacing: 4px;"
        );
        messageLabel.setWrapText(true);
        messageLabel.setMaxWidth(480);
        messageLabel.setAlignment(Pos.CENTER);

        Button okBtn = new Button("OK");
        okBtn.setPrefWidth(180);
        okBtn.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #8888ff, #6666cc);" +
                        "-fx-text-fill: white;" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 14;" +
                        "-fx-padding: 12 24;" +
                        "-fx-background-radius: 10;" +
                        "-fx-cursor: hand;"
        );
        okBtn.setOnMouseEntered(ev -> okBtn.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #aaaaff, #8888ee);" +
                        "-fx-text-fill: white;" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 14;" +
                        "-fx-padding: 12 24;" +
                        "-fx-background-radius: 10;" +
                        "-fx-cursor: hand;"
        ));
        okBtn.setOnMouseExited(ev -> okBtn.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #8888ff, #6666cc);" +
                        "-fx-text-fill: white;" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 14;" +
                        "-fx-padding: 12 24;" +
                        "-fx-background-radius: 10;" +
                        "-fx-cursor: hand;"
        ));
        okBtn.setOnAction(ev -> errorStage.close());

        dialog.getChildren().addAll(iconLabel, titleLabel, messageLabel, okBtn);
        overlay.getChildren().add(dialog);
        StackPane.setAlignment(dialog, Pos.CENTER);

        Scene scene = new Scene(overlay, 650, 500);
        scene.setFill(Color.TRANSPARENT);
        errorStage.setScene(scene);

        errorStage.setOnShown(ev -> {
            double x = primaryStage.getX() + (primaryStage.getWidth() - errorStage.getWidth()) / 2;
            double y = primaryStage.getY() + (primaryStage.getHeight() - errorStage.getHeight()) / 2;
            errorStage.setX(x);
            errorStage.setY(y);
        });

        errorStage.show();
    }

    private void showBannedDialog(String username, String banReason, boolean hasAppeal, String appealStatus) {
        bannedDialogStage = new Stage();
        Stage dialogStage = bannedDialogStage;
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.initOwner(primaryStage);
        dialogStage.setTitle("Account Banned");
        dialogStage.setResizable(false);

        // Animated background
        Canvas bgCanvas = new Canvas(600, 550);
        GraphicsContext gc = bgCanvas.getGraphicsContext2D();

        AnimationTimer bgAnim = new AnimationTimer() {
            private long lastUpdate = 0;
            private double time = 0;

            @Override
            public void handle(long now) {
                if (lastUpdate == 0) {
                    lastUpdate = now;
                    return;
                }
                double delta = (now - lastUpdate) / 1_000_000_000.0;
                lastUpdate = now;
                time += delta * 0.3;

                double w = bgCanvas.getWidth();
                double h = bgCanvas.getHeight();

                gc.setFill(new LinearGradient(0, 0, 0, h, false, CycleMethod.NO_CYCLE,
                        new Stop(0, Color.rgb(20, 10, 10, 1.0)),
                        new Stop(1, Color.rgb(30, 15, 15, 1.0))));
                gc.fillRect(0, 0, w, h);

                gc.setStroke(Color.rgb(255, 51, 51, 0.15));
                gc.setLineWidth(1);
                for (int i = 0; i < 10; i++) {
                    double y = (h / 10) * i;
                    double offset = Math.sin(time + i * 0.5) * 5;
                    gc.strokeLine(0, y + offset, w, y + offset);
                }
            }
        };
        bgAnim.start();

        VBox contentBox = new VBox(20);
        contentBox.setAlignment(Pos.CENTER);
        contentBox.setPadding(new Insets(30));
        contentBox.setMaxWidth(550);

        // Ban icon
        Label iconLabel = new Label("🚫");
        iconLabel.setStyle("-fx-font-size: 64; -fx-text-fill: #ff3366;");
        DropShadow iconGlow = new DropShadow();
        iconGlow.setColor(Color.rgb(255, 51, 102, 0.8));
        iconGlow.setRadius(20);
        iconLabel.setEffect(iconGlow);

        Label headerLabel = new Label("ACCOUNT BANNED");
        headerLabel.setStyle("-fx-font-size: 28; -fx-font-weight: bold; -fx-text-fill: #ff3366; -fx-font-family: 'Arial Black';");

        Label reasonLabel = new Label("Reason: " + banReason);
        reasonLabel.setStyle("-fx-font-size: 16; -fx-text-fill: #ffffff; -fx-padding: 15; -fx-background-color: rgba(255, 51, 51, 0.2); -fx-background-radius: 8;");
        reasonLabel.setWrapText(true);
        reasonLabel.setMaxWidth(500);
        reasonLabel.setAlignment(Pos.CENTER);

        Separator separator = new Separator(Orientation.HORIZONTAL);
        separator.setMaxWidth(500);
        separator.setStyle("-fx-background-color: #ff3366; -fx-border-color: #ff3366;");

        HBox buttonBox = new HBox(15);
        buttonBox.setAlignment(Pos.CENTER);

        Button okBtn = new Button("OK");
        okBtn.setPrefWidth(150);
        okBtn.setPrefHeight(45);
        okBtn.setStyle("-fx-background-color: linear-gradient(to bottom, #666666, #444444); " +
                "-fx-text-fill: #ffffff; -fx-font-family: 'Arial Black'; -fx-font-size: 14; " +
                "-fx-font-weight: bold; -fx-background-radius: 12; -fx-cursor: hand;");
        okBtn.setOnAction(e -> {
            bgAnim.stop();
            dialogStage.close();
            bannedDialogStage = null;
            // After closing dialog, redirect to login
            Platform.runLater(() -> showLoginScreen());
        });
        // Appeal button - show based on appeal status
        if (!hasAppeal) {
            // No appeal submitted yet - show submit button
            Button appealBtn = new Button("Submit Appeal");
            appealBtn.setPrefWidth(180);
            appealBtn.setPrefHeight(45);
            appealBtn.setStyle("-fx-background-color: linear-gradient(to bottom, #ff9900, #cc7700); " +
                    "-fx-text-fill: #ffffff; -fx-font-family: 'Arial Black'; -fx-font-size: 14; " +
                    "-fx-font-weight: bold; -fx-background-radius: 12; -fx-cursor: hand;");

            appealBtn.setOnMouseEntered(e -> {
                appealBtn.setStyle("-fx-background-color: linear-gradient(to bottom, #ffaa22, #ee8800); " +
                        "-fx-text-fill: #ffffff; -fx-font-family: 'Arial Black'; -fx-font-size: 14; " +
                        "-fx-font-weight: bold; -fx-background-radius: 12; -fx-cursor: hand;");
            });
            appealBtn.setOnMouseExited(e -> {
                appealBtn.setStyle("-fx-background-color: linear-gradient(to bottom, #ff9900, #cc7700); " +
                        "-fx-text-fill: #ffffff; -fx-font-family: 'Arial Black'; -fx-font-size: 14; " +
                        "-fx-font-weight: bold; -fx-background-radius: 12; -fx-cursor: hand;");
            });

            appealBtn.setOnAction(e -> {
                bgAnim.stop();
                dialogStage.close();
                bannedDialogStage = null;
                showAppealSubmissionDialog(username);
            });
            buttonBox.getChildren().add(appealBtn);
        } else {
            // Appeal has been submitted - check status
            if (appealStatus.equals("pending")) {
                Label pendingLabel = new Label("Appeal Status: PENDING REVIEW");
                pendingLabel.setStyle("-fx-font-size: 14; -fx-text-fill: #ffaa00; -fx-font-weight: bold; " +
                        "-fx-padding: 10; -fx-background-color: rgba(255, 170, 0, 0.2); -fx-background-radius: 8;");
                contentBox.getChildren().add(pendingLabel);
            } else if (appealStatus.equals("rejected")) {
                Label rejectedLabel = new Label("Appeal Status: REJECTED");
                rejectedLabel.setStyle("-fx-font-size: 14; -fx-text-fill: #ff4444; -fx-font-weight: bold; " +
                        "-fx-padding: 10; -fx-background-color: rgba(255, 68, 68, 0.2); -fx-background-radius: 8;");
                contentBox.getChildren().add(rejectedLabel);

                // Show resubmit button for rejected appeals
                Button resubmitBtn = new Button("Submit New Appeal");
                resubmitBtn.setPrefWidth(180);
                resubmitBtn.setPrefHeight(45);
                resubmitBtn.setStyle("-fx-background-color: linear-gradient(to bottom, #ff9900, #cc7700); " +
                        "-fx-text-fill: #ffffff; -fx-font-family: 'Arial Black'; -fx-font-size: 14; " +
                        "-fx-font-weight: bold; -fx-background-radius: 12; -fx-cursor: hand;");

                resubmitBtn.setOnMouseEntered(ev -> {
                    resubmitBtn.setStyle("-fx-background-color: linear-gradient(to bottom, #ffaa22, #ee8800); " +
                            "-fx-text-fill: #ffffff; -fx-font-family: 'Arial Black'; -fx-font-size: 14; " +
                            "-fx-font-weight: bold; -fx-background-radius: 12; -fx-cursor: hand;");
                });
                resubmitBtn.setOnMouseExited(ev -> {
                    resubmitBtn.setStyle("-fx-background-color: linear-gradient(to bottom, #ff9900, #cc7700); " +
                            "-fx-text-fill: #ffffff; -fx-font-family: 'Arial Black'; -fx-font-size: 14; " +
                            "-fx-font-weight: bold; -fx-background-radius: 12; -fx-cursor: hand;");
                });

                resubmitBtn.setOnAction(e -> {
                    bgAnim.stop();
                    dialogStage.close();
                    bannedDialogStage = null;
                    showAppealSubmissionDialog(username);
                });
                buttonBox.getChildren().add(resubmitBtn);
            } else if (appealStatus.equals("approved")) {
                Label approvedLabel = new Label("Appeal Status: APPROVED - Contact admin for unbanning");
                approvedLabel.setStyle("-fx-font-size: 14; -fx-text-fill: #44ff44; -fx-font-weight: bold; " +
                        "-fx-padding: 10; -fx-background-color: rgba(68, 255, 68, 0.2); -fx-background-radius: 8;");
                contentBox.getChildren().add(approvedLabel);
            }
        }

        buttonBox.getChildren().add(okBtn);

        contentBox.getChildren().addAll(iconLabel, headerLabel, reasonLabel, separator, buttonBox);

        Rectangle bgRect = new Rectangle(600, 550);
        bgRect.setFill(Color.rgb(0, 0, 0, 0.85));
        bgRect.setArcWidth(0);
        bgRect.setArcHeight(0);

        StackPane contentStack = new StackPane();
        contentStack.getChildren().addAll(bgRect, contentBox);

        StackPane root = new StackPane();
        root.getChildren().addAll(bgCanvas, contentStack);

        Scene scene = new Scene(root, 600, 550);
        scene.setFill(Color.TRANSPARENT);
        dialogStage.initStyle(StageStyle.TRANSPARENT);
        dialogStage.setScene(scene);
        dialogStage.show();
    }

    // 3. Add method for submitting an appeal:
    private void showAppealSubmissionDialog(String username) {
        // CHECK CURRENT APPEAL STATUS FIRST
        executor.submit(() -> {
            try {
                String url = SUPABASE_URL + "/rest/v1/profiles?username=eq." + username
                        + "&select=appeal_submitted,appeal_status";

                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(url))
                        .header("apikey", SUPABASE_ANON_KEY)
                        .header("Authorization", "Bearer " + SUPABASE_ANON_KEY)
                        .GET()
                        .build();

                HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

                if (response.statusCode() == 200) {
                    JsonArray array = gson.fromJson(response.body(), JsonArray.class);
                    if (array.size() > 0) {
                        JsonObject userData = array.get(0).getAsJsonObject();
                        boolean hasAppeal = userData.has("appeal_submitted") && userData.get("appeal_submitted").getAsBoolean();
                        String appealStatus = userData.has("appeal_status") && !userData.get("appeal_status").isJsonNull()
                                ? userData.get("appeal_status").getAsString()
                                : "none";

                        Platform.runLater(() -> {
                            // If appeal is pending, don't allow submission
                            if (hasAppeal && appealStatus.equals("pending")) {
                                showAppealStatusModal(
                                        "⏳ Appeal Already Pending",
                                        "You already have a PENDING appeal.\n\n" +
                                                "Please wait for the admin to review your current appeal before submitting a new one.",
                                        "#FFAA00"
                                );
                                return;
                            }

                            // If appeal is approved, don't allow submission
                            if (hasAppeal && appealStatus.equals("approved")) {
                                showAppealStatusModal(
                                        "✅ Appeal Approved",
                                        "Your appeal has been APPROVED.\n\n" +
                                                "Please contact an admin to complete the unbanning process.",
                                        "#44ff44"
                                );
                                return;
                            }

                            // Otherwise (no appeal, or rejected), show the submission dialog
                            showAppealSubmissionDialogUI(username);
                        });
                        return;
                    }
                }

                // If check fails, still show the dialog (fail-open)
                Platform.runLater(() -> showAppealSubmissionDialogUI(username));

            } catch (Exception ex) {
                // If error, still show the dialog
                Platform.runLater(() -> showAppealSubmissionDialogUI(username));
            }
        });
    }

    private void showAppealStatusModal(String title, String message, String accentColor) {
        // Create a NEW stage for the modal (instead of trying to overlay on existing stage)
        Stage modalStage = new Stage();
        modalStage.initModality(Modality.APPLICATION_MODAL);

        // Set owner based on what's currently open
        if (bannedDialogStage != null && bannedDialogStage.isShowing()) {
            modalStage.initOwner(bannedDialogStage);
        } else if (banAppealsStage != null && banAppealsStage.isShowing()) {
            modalStage.initOwner(banAppealsStage);
        } else if (userManagementStage != null && userManagementStage.isShowing()) {
            modalStage.initOwner(userManagementStage);
        } else {
            modalStage.initOwner(primaryStage);
        }

        modalStage.initStyle(StageStyle.TRANSPARENT);
        modalStage.setTitle(title);
        modalStage.setResizable(false);

        // Create overlay background - REMOVED DIMMING (transparent)
        StackPane overlay = new StackPane();
        overlay.setStyle("-fx-background-color: transparent;");
        overlay.setPadding(new Insets(40));

        // Dialog container - increased width to prevent text cutoff
        VBox dialogBox = new VBox(25);
        dialogBox.setPadding(new Insets(40));
        dialogBox.setAlignment(Pos.CENTER);
        dialogBox.setMaxWidth(700);  // Increased from 600
        dialogBox.setMinHeight(350);
        dialogBox.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #1a1a2e, #16213e, #0f3460);" +
                        "-fx-background-radius: 20;" +
                        "-fx-effect: dropshadow(gaussian, " + accentColor + ", 40, 0.6, 0, 0);"
        );

        // Icon based on status - REDUCED NEON EFFECT
        String icon = accentColor.equals("#FFAA00") ? "⏳" : "✅";
        Label iconLabel = new Label(icon);
        iconLabel.setStyle(
                "-fx-font-size: 64; " +
                        "-fx-text-fill: " + accentColor + ";" +
                        "-fx-effect: dropshadow(gaussian, " + accentColor + ", 8, 0.4, 0, 0);"  // Reduced from 20, 0.8
        );

        // Title - REDUCED NEON EFFECT
        Label titleLabel = new Label(title);
        titleLabel.setStyle(
                "-fx-font-size: 26; " +
                        "-fx-font-weight: bold; " +
                        "-fx-text-fill: " + accentColor + ";" +
                        "-fx-font-family: 'Arial Black';" +
                        "-fx-effect: dropshadow(gaussian, " + accentColor + ", 4, 0.3, 0, 0);"  // Reduced from 8, 0.6
        );
        titleLabel.setWrapText(true);
        titleLabel.setAlignment(Pos.CENTER);
        titleLabel.setMaxWidth(640);  // Increased from 540

        // Separator line
        Region separator = new Region();
        separator.setPrefHeight(2);
        separator.setMaxWidth(600);  // Increased from 500
        separator.setStyle("-fx-background-color: " + accentColor + ";");

        // Message
        Label messageLabel = new Label(message);
        messageLabel.setStyle(
                "-fx-font-size: 15; " +
                        "-fx-text-fill: #ffffff; " +
                        "-fx-line-spacing: 8;" +
                        "-fx-padding: 20 0 20 0;"
        );
        messageLabel.setWrapText(true);
        messageLabel.setAlignment(Pos.CENTER);
        messageLabel.setMaxWidth(620);  // Increased from 520

        // OK Button - INCREASED WIDTH
        Button okBtn = new Button("UNDERSTOOD");
        okBtn.setPrefWidth(280);  // Increased from 200
        okBtn.setPrefHeight(50);
        okBtn.setStyle(
                "-fx-background-color: linear-gradient(to bottom, " + accentColor + ", " + adjustBrightness(accentColor, -0.3) + "); " +
                        "-fx-text-fill: white; " +
                        "-fx-font-weight: bold; " +
                        "-fx-font-family: 'Arial Black';" +
                        "-fx-padding: 12 40; " +
                        "-fx-background-radius: 12; " +
                        "-fx-cursor: hand; " +
                        "-fx-font-size: 16;" +
                        "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.5), 10, 0.3, 0, 3);"
        );
        okBtn.setOnMouseEntered(e -> okBtn.setStyle(
                "-fx-background-color: linear-gradient(to bottom, " + adjustBrightness(accentColor, 0.3) + ", " + accentColor + "); " +
                        "-fx-text-fill: white; " +
                        "-fx-font-weight: bold; " +
                        "-fx-font-family: 'Arial Black';" +
                        "-fx-padding: 12 40; " +
                        "-fx-background-radius: 12; " +
                        "-fx-cursor: hand; " +
                        "-fx-font-size: 16;" +
                        "-fx-effect: dropshadow(gaussian, " + accentColor + ", 15, 0.5, 0, 3);"
        ));
        okBtn.setOnMouseExited(e -> okBtn.setStyle(
                "-fx-background-color: linear-gradient(to bottom, " + accentColor + ", " + adjustBrightness(accentColor, -0.3) + "); " +
                        "-fx-text-fill: white; " +
                        "-fx-font-weight: bold; " +
                        "-fx-font-family: 'Arial Black';" +
                        "-fx-padding: 12 40; " +
                        "-fx-background-radius: 12; " +
                        "-fx-cursor: hand; " +
                        "-fx-font-size: 16;" +
                        "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.5), 10, 0.3, 0, 3);"
        ));

        okBtn.setOnAction(e -> modalStage.close());

        // Allow clicking outside to close
        overlay.setOnMouseClicked(e -> {
            if (e.getTarget() == overlay) {
                modalStage.close();
            }
        });

        dialogBox.getChildren().addAll(iconLabel, titleLabel, separator, messageLabel, okBtn);
        overlay.getChildren().add(dialogBox);
        StackPane.setAlignment(dialogBox, Pos.CENTER);

        // Create scene - INCREASED SIZE to prevent cutoff
        Scene scene = new Scene(overlay, 800, 600);  // Increased from 700, 550
        scene.setFill(Color.TRANSPARENT);
        modalStage.setScene(scene);

        // Center on parent
        modalStage.setOnShown(ev -> {
            Stage owner = (Stage) modalStage.getOwner();
            if (owner != null) {
                double x = owner.getX() + (owner.getWidth() - modalStage.getWidth()) / 2;
                double y = owner.getY() + (owner.getHeight() - modalStage.getHeight()) / 2;
                modalStage.setX(x);
                modalStage.setY(y);
            }
        });

        modalStage.showAndWait();
    }

    // Helper method to adjust color brightness (keep your existing one)
    private String adjustBrightness(String hexColor, double factor) {
        String hex = hexColor.replace("#", "");
        int r = Integer.parseInt(hex.substring(0, 2), 16);
        int g = Integer.parseInt(hex.substring(2, 4), 16);
        int b = Integer.parseInt(hex.substring(4, 6), 16);

        r = Math.max(0, Math.min(255, (int)(r * (1 + factor))));
        g = Math.max(0, Math.min(255, (int)(g * (1 + factor))));
        b = Math.max(0, Math.min(255, (int)(b * (1 + factor))));

        return String.format("#%02x%02x%02x", r, g, b);
    }

    // RENAME your current dialog UI to this:
    private void showAppealSubmissionDialogUI(String username) {
        // Create a new Stage for the modal dialog
        Stage modalStage = new Stage();
        modalStage.initOwner(primaryStage);
        modalStage.initModality(Modality.APPLICATION_MODAL);
        modalStage.initStyle(StageStyle.TRANSPARENT);
        modalStage.setTitle("Submit Ban Appeal");
        modalStage.setResizable(false);

        // Create modal overlay
        StackPane modalOverlay = new StackPane();
        modalOverlay.setStyle("-fx-background-color: transparent;");

        // Dialog container
        VBox dialogBox = new VBox(20);
        dialogBox.setPadding(new Insets(30));
        dialogBox.setAlignment(Pos.CENTER);
        dialogBox.setMaxWidth(600);
        dialogBox.setMaxHeight(550);
        dialogBox.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #1a1a2e, #16213e);" +
                        "-fx-background-radius: 20;" +
                        "-fx-effect: dropshadow(gaussian, rgba(255, 153, 0, 0.5), 30, 0.5, 0, 0);"
        );

        // Close button
        Button closeButton = new Button("✕");
        closeButton.setStyle(
                "-fx-background-color: rgba(255, 68, 68, 0.2);" +
                        "-fx-text-fill: #FF4444;" +
                        "-fx-font-size: 18;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 20;" +
                        "-fx-padding: 8 12;" +
                        "-fx-border-color: #FF4444;" +
                        "-fx-border-radius: 20;" +
                        "-fx-border-width: 2;" +
                        "-fx-cursor: hand;"
        );

        HBox topBar = new HBox(closeButton);
        topBar.setAlignment(Pos.TOP_RIGHT);

        Label titleLabel = new Label("📝 Submit Ban Appeal");
        titleLabel.setStyle(
                "-fx-font-size: 24;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: #ff9900;" +
                        "-fx-effect: dropshadow(gaussian, rgba(255, 153, 0, 0.4), 5, 0.6, 0, 0);"
        );

        Label instructionLabel = new Label("Explain why you believe the ban should be lifted:");
        instructionLabel.setStyle("-fx-font-size: 14; -fx-text-fill: #ffffff;");
        instructionLabel.setWrapText(true);
        instructionLabel.setMaxWidth(500);

        TextArea appealArea = new TextArea();
        appealArea.setPromptText("Describe your situation, admit any mistakes, and explain why you deserve a second chance...");
        appealArea.setPrefRowCount(8);
        appealArea.setPrefWidth(500);
        appealArea.setWrapText(true);
        appealArea.setStyle(
                "-fx-control-inner-background: #ffffff;" +
                        "-fx-background-color: #ffffff;" +
                        "-fx-text-fill: #000000;" +
                        "-fx-prompt-text-fill: #888888;" +
                        "-fx-font-size: 14px;" +
                        "-fx-padding: 10;" +
                        "-fx-background-radius: 8;" +
                        "-fx-border-color: #ff9900;" +
                        "-fx-border-width: 2;" +
                        "-fx-border-radius: 8;"
        );

        Label charCountLabel = new Label("0 / 1000 characters");
        charCountLabel.setStyle("-fx-font-size: 12; -fx-text-fill: #00ffff;");

        // Character counter with color changes
        appealArea.textProperty().addListener((obs, oldVal, newVal) -> {
            int length = newVal.length();
            charCountLabel.setText(length + " / 1000 characters");

            if (length > 1000) {
                appealArea.setText(oldVal);
            } else if (length >= 950) {
                charCountLabel.setStyle("-fx-font-size: 12; -fx-text-fill: #ff4444; -fx-font-weight: bold;");
            } else if (length >= 800) {
                charCountLabel.setStyle("-fx-font-size: 12; -fx-text-fill: #ffaa00;");
            } else {
                charCountLabel.setStyle("-fx-font-size: 12; -fx-text-fill: #00ffff;");
            }
        });

        HBox buttonBox = new HBox(15);
        buttonBox.setAlignment(Pos.CENTER);

        Button submitBtn = new Button("📤 Submit Appeal");
        submitBtn.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #ff9900, #cc7700);" +
                        "-fx-text-fill: white;" +
                        "-fx-font-weight: bold;" +
                        "-fx-padding: 12 35;" +
                        "-fx-background-radius: 10;" +
                        "-fx-cursor: hand;" +
                        "-fx-font-size: 14;"
        );

        // Hover effects for submit button
        submitBtn.setOnMouseEntered(e -> submitBtn.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #ffaa11, #dd8800);" +
                        "-fx-text-fill: white;" +
                        "-fx-font-weight: bold;" +
                        "-fx-padding: 12 35;" +
                        "-fx-background-radius: 10;" +
                        "-fx-cursor: hand;" +
                        "-fx-font-size: 14;"
        ));
        submitBtn.setOnMouseExited(e -> submitBtn.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #ff9900, #cc7700);" +
                        "-fx-text-fill: white;" +
                        "-fx-font-weight: bold;" +
                        "-fx-padding: 12 35;" +
                        "-fx-background-radius: 10;" +
                        "-fx-cursor: hand;" +
                        "-fx-font-size: 14;"
        ));

        Button cancelBtn = new Button("Cancel");
        cancelBtn.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #666666, #444444);" +
                        "-fx-text-fill: white;" +
                        "-fx-font-weight: bold;" +
                        "-fx-padding: 12 35;" +
                        "-fx-background-radius: 10;" +
                        "-fx-cursor: hand;" +
                        "-fx-font-size: 14;"
        );

        // Hover effects for cancel button
        cancelBtn.setOnMouseEntered(e -> cancelBtn.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #888888, #666666);" +
                        "-fx-text-fill: white;" +
                        "-fx-font-weight: bold;" +
                        "-fx-padding: 12 35;" +
                        "-fx-background-radius: 10;" +
                        "-fx-cursor: hand;" +
                        "-fx-font-size: 14;"
        ));
        cancelBtn.setOnMouseExited(e -> cancelBtn.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #666666, #444444);" +
                        "-fx-text-fill: white;" +
                        "-fx-font-weight: bold;" +
                        "-fx-padding: 12 35;" +
                        "-fx-background-radius: 10;" +
                        "-fx-cursor: hand;" +
                        "-fx-font-size: 14;"
        ));

        // Close modal handler
        Runnable closeModal = () -> modalStage.close();

        closeButton.setOnAction(e -> closeModal.run());

        // Hover effects for close button
        closeButton.setOnMouseEntered(e -> closeButton.setStyle(
                "-fx-background-color: #FF4444;" +
                        "-fx-text-fill: #FFFFFF;" +
                        "-fx-font-size: 18;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 20;" +
                        "-fx-padding: 8 12;" +
                        "-fx-border-color: #FF4444;" +
                        "-fx-border-radius: 20;" +
                        "-fx-border-width: 2;" +
                        "-fx-cursor: hand;"
        ));
        closeButton.setOnMouseExited(e -> closeButton.setStyle(
                "-fx-background-color: rgba(255, 68, 68, 0.2);" +
                        "-fx-text-fill: #FF4444;" +
                        "-fx-font-size: 18;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 20;" +
                        "-fx-padding: 8 12;" +
                        "-fx-border-color: #FF4444;" +
                        "-fx-border-radius: 20;" +
                        "-fx-border-width: 2;" +
                        "-fx-cursor: hand;"
        ));

        submitBtn.setOnAction(e -> {
            String appealText = appealArea.getText().trim();

            if (appealText.isEmpty()) {
                showCustomErrorDialog("Empty Appeal", "Please write your appeal before submitting.");
                return;
            }

            if (appealText.length() < 50) {
                showCustomErrorDialog("Appeal Too Short", "Appeal must be at least 50 characters long.\n\nCurrent length: " + appealText.length() + " characters");
                return;
            }

            closeModal.run();
            submitAppeal(username, appealText);
        });

        cancelBtn.setOnAction(e -> closeModal.run());

        // Allow clicking outside to close
        modalOverlay.setOnMouseClicked(e -> {
            if (e.getTarget() == modalOverlay) {
                closeModal.run();
            }
        });

        buttonBox.getChildren().addAll(submitBtn, cancelBtn);
        dialogBox.getChildren().addAll(topBar, titleLabel, instructionLabel, appealArea, charCountLabel, buttonBox);

        modalOverlay.getChildren().add(dialogBox);
        StackPane.setAlignment(dialogBox, Pos.CENTER);

        // Create scene with transparent background
        Scene scene = new Scene(modalOverlay, 700, 600);
        scene.setFill(Color.TRANSPARENT);
        modalStage.setScene(scene);

        // Center on primary stage
        modalStage.setOnShown(e -> {
            double x = primaryStage.getX() + (primaryStage.getWidth() - modalStage.getWidth()) / 2;
            double y = primaryStage.getY() + (primaryStage.getHeight() - modalStage.getHeight()) / 2;
            modalStage.setX(x);
            modalStage.setY(y);
        });

        // Show the modal stage
        modalStage.show();

        // Focus on text area
        Platform.runLater(() -> appealArea.requestFocus());
    }

    // 4. Add method to submit appeal to database:
    private void submitAppeal(String username, String appealText) {
        executor.submit(() -> {
            try {
                JsonObject payload = new JsonObject();
                payload.addProperty("appeal_text", appealText);
                payload.addProperty("appeal_submitted", true);
                payload.addProperty("appeal_status", "pending");
                payload.addProperty("appeal_date", java.time.LocalDateTime.now().toString());

                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(SUPABASE_URL + "/rest/v1/profiles?username=eq." + username))
                        .header("apikey", SUPABASE_ANON_KEY)
                        .header("Authorization", "Bearer " + SUPABASE_ANON_KEY)
                        .header("Content-Type", "application/json")
                        .method("PATCH", HttpRequest.BodyPublishers.ofString(payload.toString()))
                        .build();

                HttpResponse<String> resp = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

                Platform.runLater(() -> {
                    if (resp.statusCode() == 204 || resp.statusCode() == 200) {
                        showCustomSuccessDialog("Appeal Submitted",
                                "Your ban appeal has been submitted successfully.\n\n" +
                                        "An administrator will review your appeal and make a decision.\n" +
                                        "You will be notified of the outcome when you try to login again.");
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Failed to submit appeal. Please try again.");
                        alert.showAndWait();
                    }
                });
            } catch (Exception ex) {
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Error: " + ex.getMessage());
                    alert.showAndWait();
                });
            }
        });
    }

    private final Map<String, JsonObject> appealsCache = new LinkedHashMap<String, JsonObject>(100, 0.75f, true) {
        @Override
        protected boolean removeEldestEntry(Map.Entry<String, JsonObject> eldest) {
            return size() > 100; // Keep max 100 appeals in cache
        }
    };

    public void showBanAppealsDialog() {
        // Create new Stage window if it doesn't exist
        if (banAppealsStage == null) {
            banAppealsStage = new Stage();
            banAppealsStage.initOwner(primaryStage);
            banAppealsStage.initModality(Modality.WINDOW_MODAL);
            banAppealsStage.initStyle(StageStyle.TRANSPARENT);
            banAppealsStage.setTitle("Ban Appeals Management");
            banAppealsStage.setResizable(false);

            // Reset dialog flag when closed
            banAppealsStage.setOnHidden(e -> {
                isDialogOpen = false;
                currentAppealPage = 0; // Reset page when closing
                currentStatusFilter = "All"; // Reset filter when closing
                currentSearchQuery = ""; // Reset search when closing
                // Clear memory when dialog closes
                clearAppealsCache();
            });

            // Outer container with transparent background
            StackPane overlay = new StackPane();
            overlay.setStyle("-fx-background-color: transparent;");
            overlay.setPadding(new Insets(40));

            // Main dialog container
            VBox banAppealsDialog = new VBox(20);
            banAppealsDialog.setAlignment(Pos.CENTER);
            banAppealsDialog.setPadding(new Insets(30));
            banAppealsDialog.setMaxWidth(900);
            banAppealsDialog.setMaxHeight(700);
            banAppealsDialog.setStyle(
                    "-fx-background-color: linear-gradient(to bottom, #1a1a2e, #16213e, #0f3460);" +
                            "-fx-background-radius: 20;" +
                            "-fx-effect: dropshadow(gaussian, rgba(255, 170, 0, 0.4), 25, 0.5, 0, 0);"
            );

            // Close button with hover effects
            Button closeButton = new Button("✕");
            closeButton.setStyle(
                    "-fx-background-color: rgba(255, 68, 68, 0.2);" +
                            "-fx-text-fill: #FF4444;" +
                            "-fx-font-family: 'Arial';" +
                            "-fx-font-size: 18;" +
                            "-fx-font-weight: bold;" +
                            "-fx-background-radius: 20;" +
                            "-fx-padding: 8 12;" +
                            "-fx-border-color: #FF4444;" +
                            "-fx-border-radius: 20;" +
                            "-fx-border-width: 2;" +
                            "-fx-cursor: hand;"
            );
            closeButton.setOnMouseEntered(e -> closeButton.setStyle(
                    "-fx-background-color: #FF4444;" +
                            "-fx-text-fill: #FFFFFF;" +
                            "-fx-font-family: 'Arial';" +
                            "-fx-font-size: 18;" +
                            "-fx-font-weight: bold;" +
                            "-fx-background-radius: 20;" +
                            "-fx-padding: 8 12;" +
                            "-fx-border-color: #FF4444;" +
                            "-fx-border-radius: 20;" +
                            "-fx-border-width: 2;" +
                            "-fx-cursor: hand;"
            ));
            closeButton.setOnMouseExited(e -> closeButton.setStyle(
                    "-fx-background-color: rgba(255, 68, 68, 0.2);" +
                            "-fx-text-fill: #FF4444;" +
                            "-fx-font-family: 'Arial';" +
                            "-fx-font-size: 18;" +
                            "-fx-font-weight: bold;" +
                            "-fx-background-radius: 20;" +
                            "-fx-padding: 8 12;" +
                            "-fx-border-color: #FF4444;" +
                            "-fx-border-radius: 20;" +
                            "-fx-border-width: 2;" +
                            "-fx-cursor: hand;"
            ));
            closeButton.setOnAction(e -> banAppealsStage.close());

            HBox topBar = new HBox(closeButton);
            topBar.setAlignment(Pos.TOP_RIGHT);

            // Title with glow effect
            Label titleLabel = new Label("📋 BAN APPEALS MANAGEMENT 📋");
            titleLabel.setStyle(
                    "-fx-text-fill: #FFFFFF;" +
                            "-fx-font-family: 'Arial Black';" +
                            "-fx-font-size: 28;" +
                            "-fx-font-weight: bold;" +
                            "-fx-effect: dropshadow(gaussian, rgba(255, 170, 0, 0.4), 8, 0.6, 0, 0);"
            );

            Label subtitleLabel = new Label("Review & Process User Appeal Requests");
            subtitleLabel.setStyle(
                    "-fx-text-fill: #FFAA00;" +
                            "-fx-font-family: 'Arial';" +
                            "-fx-font-size: 14;" +
                            "-fx-font-style: italic;"
            );

            VBox titleBox = new VBox(5, titleLabel, subtitleLabel);
            titleBox.setAlignment(Pos.CENTER);

            // ========== FILTER CONTROLS ==========
            HBox filterBox = new HBox(15);
            filterBox.setAlignment(Pos.CENTER);
            filterBox.setPadding(new Insets(10, 0, 10, 0));

            Label filterLabel = new Label("Filter by Status:");
            filterLabel.setStyle(
                    "-fx-text-fill: #FFFFFF;" +
                            "-fx-font-size: 14;" +
                            "-fx-font-weight: bold;"
            );

            statusFilterCombo = new ComboBox<>();
            statusFilterCombo.getItems().addAll("Active", "All", "Pending", "Approved", "Rejected", "Resolved");
            statusFilterCombo.setValue("Active");
            statusFilterCombo.setPrefWidth(150);
            currentStatusFilter = "Active";
            statusFilterCombo.setStyle(
                    "-fx-background-color: rgba(0, 0, 0, 0.5);" +
                            "-fx-text-fill: white;" +
                            "-fx-font-size: 13;" +
                            "-fx-font-weight: bold;" +
                            "-fx-background-radius: 8;" +
                            "-fx-border-color: #FFAA00;" +
                            "-fx-border-radius: 8;" +
                            "-fx-border-width: 2;"
            );

            // Search field
            Label searchLabel = new Label("Search Username:");
            searchLabel.setStyle(
                    "-fx-text-fill: #FFFFFF;" +
                            "-fx-font-size: 14;" +
                            "-fx-font-weight: bold;"
            );

            searchField = new TextField();
            searchField.setPromptText("Type username...");
            searchField.setPrefWidth(200);
// Limit character input to 50
            searchField.textProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null && newValue.length() > 50) {
                    searchField.setText(oldValue);
                }
            });
            searchField.setStyle(
                    "-fx-background-color: rgba(0, 0, 0, 0.5);" +
                            "-fx-text-fill: white;" +
                            "-fx-prompt-text-fill: #888888;" +
                            "-fx-font-size: 13;" +
                            "-fx-background-radius: 8;" +
                            "-fx-border-color: #FFAA00;" +
                            "-fx-border-radius: 8;" +
                            "-fx-border-width: 2;" +
                            "-fx-padding: 5 10;"
            );

            // Clear search button - FIXED SIZE
            Button clearSearchBtn = new Button("✕");
            clearSearchBtn.setPrefWidth(30);
            clearSearchBtn.setMinWidth(30);
            clearSearchBtn.setMaxWidth(30);
            clearSearchBtn.setPrefHeight(30);
            clearSearchBtn.setMinHeight(30);
            clearSearchBtn.setMaxHeight(30);
            clearSearchBtn.setStyle(
                    "-fx-background-color: rgba(255, 68, 68, 0.3);" +
                            "-fx-text-fill: #ff4444;" +
                            "-fx-font-size: 14;" +
                            "-fx-font-weight: bold;" +
                            "-fx-background-radius: 8;" +
                            "-fx-padding: 0;" +
                            "-fx-cursor: hand;"
            );
            clearSearchBtn.setVisible(false);
            // REMOVED: clearSearchBtn.setManaged(false); - This was causing the layout issue
            clearSearchBtn.setOnAction(e -> {
                searchField.clear();
                currentSearchQuery = "";
                currentAppealPage = 0;
                clearSearchBtn.setVisible(false);
                loadAppealsPage();
            });

            // Search with debounce (wait 500ms after typing stops)
            final long[] lastTypedTime = {0};
            searchField.textProperty().addListener((obs, oldVal, newVal) -> {
                lastTypedTime[0] = System.currentTimeMillis();

                if (newVal.trim().isEmpty()) {
                    clearSearchBtn.setVisible(false);
                    if (!currentSearchQuery.isEmpty()) {
                        currentSearchQuery = "";
                        currentAppealPage = 0;
                        loadAppealsPage();
                    }
                } else {
                    clearSearchBtn.setVisible(true);

                    // Debounce: wait 500ms before searching
                    new Thread(() -> {
                        try {
                            Thread.sleep(500);
                            if (System.currentTimeMillis() - lastTypedTime[0] >= 500) {
                                String query = newVal.trim();
                                if (query.length() >= 2 && !query.equals(currentSearchQuery)) {
                                    currentSearchQuery = query;
                                    currentAppealPage = 0;
                                    Platform.runLater(() -> loadAppealsPage());
                                }
                            }
                        } catch (InterruptedException ex) {
                            // Ignore
                        }
                    }).start();
                }
            });

            // Refresh button
            Button refreshBtn = new Button("🔄 Refresh");
            refreshBtn.setStyle(
                    "-fx-background-color: linear-gradient(to bottom, #4444ff, #2222cc);" +
                            "-fx-text-fill: white;" +
                            "-fx-font-weight: bold;" +
                            "-fx-padding: 8 20;" +
                            "-fx-background-radius: 10;" +
                            "-fx-cursor: hand;" +
                            "-fx-font-size: 13;"
            );
            refreshBtn.setOnMouseEntered(e -> refreshBtn.setStyle(
                    "-fx-background-color: linear-gradient(to bottom, #6666ff, #4444ee);" +
                            "-fx-text-fill: white;" +
                            "-fx-font-weight: bold;" +
                            "-fx-padding: 8 20;" +
                            "-fx-background-radius: 10;" +
                            "-fx-cursor: hand;" +
                            "-fx-font-size: 13;"
            ));
            refreshBtn.setOnMouseExited(e -> refreshBtn.setStyle(
                    "-fx-background-color: linear-gradient(to bottom, #4444ff, #2222cc);" +
                            "-fx-text-fill: white;" +
                            "-fx-font-weight: bold;" +
                            "-fx-padding: 8 20;" +
                            "-fx-background-radius: 10;" +
                            "-fx-cursor: hand;" +
                            "-fx-font-size: 13;"
            ));
            refreshBtn.setOnAction(e -> {
                currentAppealPage = 0; // Reset to first page
                loadAppealsPage();
            });

            // Status count label
            Label statusCountLabel = new Label("");
            statusCountLabel.setStyle(
                    "-fx-text-fill: #00ffcc;" +
                            "-fx-font-size: 13;" +
                            "-fx-font-weight: bold;"
            );

            // Filter change listener
            statusFilterCombo.setOnAction(e -> {
                currentStatusFilter = statusFilterCombo.getValue();
                currentAppealPage = 0; // Reset to first page when filter changes
                loadAppealsPage();
            });

            // Create two rows for filters
            HBox firstRow = new HBox(15);
            firstRow.setAlignment(Pos.CENTER);
            firstRow.getChildren().addAll(filterLabel, statusFilterCombo, searchLabel, searchField, clearSearchBtn);

            HBox secondRow = new HBox(15);
            secondRow.setAlignment(Pos.CENTER);
            secondRow.getChildren().addAll(refreshBtn);

            VBox filterContainer = new VBox(10);
            filterContainer.setAlignment(Pos.CENTER);
            filterContainer.getChildren().addAll(firstRow, secondRow);

            // Create the ban appeals table
            banAppealsTable = new TableView<>();
            banAppealsTable.setSelectionModel(null);
            banAppealsTable.setTableMenuButtonVisible(false);
            Label placeholderLabel = new Label("Loading appeals...");
            placeholderLabel.setStyle("-fx-text-fill: white;");
            banAppealsTable.setPlaceholder(placeholderLabel);
            banAppealsTable.setStyle("-fx-background-color: rgba(58, 58, 58, 0.4); -fx-control-inner-background: transparent;");
            banAppealsTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            banAppealsTable.setPrefHeight(400);
            banAppealsTable.setMaxHeight(400);
            banAppealsTable.setFixedCellSize(50);

            // USERNAME COLUMN
            TableColumn<JsonObject, String> colUser = new TableColumn<>("USERNAME");
            colUser.setMinWidth(150);
            colUser.setMaxWidth(200);
            colUser.setReorderable(false);
            colUser.setSortable(false);
            colUser.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().get("username").getAsString()));
            colUser.setCellFactory(col -> new TableCell<JsonObject, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                        setStyle("");
                    } else {
                        setText(item);
                        // CHANGED: Match user management style exactly
                        setStyle("-fx-text-fill: #00ffcc; -fx-font-weight: bold; -fx-font-size: 14px; -fx-alignment: CENTER-LEFT; -fx-padding: 20 10 10 10;");
                    }
                }
            });

            // STATUS COLUMN
            TableColumn<JsonObject, String> colStatus = new TableColumn<>("STATUS");
            colStatus.setMinWidth(100);
            colStatus.setMaxWidth(130);
            colStatus.setReorderable(false);
            colStatus.setSortable(false);
            colStatus.setCellValueFactory(d -> new SimpleStringProperty(
                    d.getValue().get("appeal_status").getAsString().toUpperCase()));
            colStatus.setCellFactory(col -> new TableCell<JsonObject, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                        setStyle("");
                    } else {
                        String color = item.equals("PENDING") ? "#ffaa00" :
                                item.equals("APPROVED") ? "#44ff44" :
                                        item.equals("RESOLVED") ? "#00ccff" :
                                                "#ff4444";
                        setText(item);
                        // CHANGED: Match user management style exactly
                        setStyle("-fx-text-fill: " + color + "; -fx-font-weight: bold; -fx-font-size: 13px; -fx-alignment: CENTER; -fx-padding: 20 10 10 10;");
                    }
                }
            });

            // DATE COLUMN
            TableColumn<JsonObject, String> colDate = new TableColumn<>("DATE SUBMITTED");
            colDate.setMinWidth(140);
            colDate.setMaxWidth(180);
            colDate.setReorderable(false);
            colDate.setSortable(false);
            colDate.setCellValueFactory(d -> {
                String date = d.getValue().get("appeal_date").getAsString();
                return new SimpleStringProperty(date.substring(0, Math.min(16, date.length())));
            });
            colDate.setCellFactory(col -> new TableCell<JsonObject, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                        setStyle("");
                    } else {
                        setText(item);
                        // CHANGED: Match user management style exactly
                        setStyle("-fx-text-fill: #ffffff; -fx-font-size: 14px; -fx-alignment: CENTER; -fx-padding: 20 10 10 10;");
                    }
                }
            });

            // ACTIONS COLUMN
            TableColumn<JsonObject, Void> colActions = new TableColumn<>("ACTIONS");
            colActions.setMinWidth(320);
            colActions.setMaxWidth(360);
            colActions.setSortable(false);
            colActions.setResizable(false);
            colActions.setReorderable(false);
            colActions.setCellFactory(param -> new TableCell<>() {
                private final Button viewBtn = new Button("View");
                private final Button approveBtn = new Button("Approve");
                private final Button rejectBtn = new Button("Reject");

                {
                    viewBtn.setPrefWidth(80);
                    viewBtn.setMinWidth(80);
                    viewBtn.setMaxWidth(80);
                    viewBtn.setStyle("-fx-background-color: linear-gradient(to bottom, #4444ff, #2222cc); -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 6 12; -fx-background-radius: 8; -fx-cursor: hand; -fx-font-size: 11;");

                    approveBtn.setPrefWidth(80);
                    approveBtn.setMinWidth(80);
                    approveBtn.setMaxWidth(80);
                    approveBtn.setStyle("-fx-background-color: linear-gradient(to bottom, #44ff44, #22cc22); -fx-text-fill: black; -fx-font-weight: bold; -fx-padding: 6 12; -fx-background-radius: 8; -fx-cursor: hand; -fx-font-size: 11;");

                    rejectBtn.setPrefWidth(80);
                    rejectBtn.setMinWidth(80);
                    rejectBtn.setMaxWidth(80);
                    rejectBtn.setStyle("-fx-background-color: linear-gradient(to bottom, #ff4444, #cc2222); -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 6 12; -fx-background-radius: 8; -fx-cursor: hand; -fx-font-size: 11;");

                    // Hover effects
                    viewBtn.setOnMouseEntered(e -> viewBtn.setStyle("-fx-background-color: linear-gradient(to bottom, #6666ff, #4444ee); -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 6 12; -fx-background-radius: 8; -fx-cursor: hand; -fx-font-size: 11;"));
                    viewBtn.setOnMouseExited(e -> viewBtn.setStyle("-fx-background-color: linear-gradient(to bottom, #4444ff, #2222cc); -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 6 12; -fx-background-radius: 8; -fx-cursor: hand; -fx-font-size: 11;"));

                    approveBtn.setOnMouseEntered(e -> approveBtn.setStyle("-fx-background-color: linear-gradient(to bottom, #66ff66, #44ee44); -fx-text-fill: black; -fx-font-weight: bold; -fx-padding: 6 12; -fx-background-radius: 8; -fx-cursor: hand; -fx-font-size: 11;"));
                    approveBtn.setOnMouseExited(e -> approveBtn.setStyle("-fx-background-color: linear-gradient(to bottom, #44ff44, #22cc22); -fx-text-fill: black; -fx-font-weight: bold; -fx-padding: 6 12; -fx-background-radius: 8; -fx-cursor: hand; -fx-font-size: 11;"));

                    rejectBtn.setOnMouseEntered(e -> rejectBtn.setStyle("-fx-background-color: linear-gradient(to bottom, #ff6666, #ee4444); -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 6 12; -fx-background-radius: 8; -fx-cursor: hand; -fx-font-size: 11;"));
                    rejectBtn.setOnMouseExited(e -> rejectBtn.setStyle("-fx-background-color: linear-gradient(to bottom, #ff4444, #cc2222); -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 6 12; -fx-background-radius: 8; -fx-cursor: hand; -fx-font-size: 11;"));
                }

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || getIndex() < 0) {
                        setGraphic(null);
                        setStyle("");
                        return;
                    }

                    JsonObject appeal = getTableView().getItems().get(getIndex());
                    String username = appeal.get("username").getAsString();
                    String appealText = appeal.get("appeal_text").getAsString();

                    // FIXED: Add modal check to all button actions
                    viewBtn.setOnAction(e -> {
                        if (isModalDialogOpen) return;
                        isModalDialogOpen = true;
                        showAppealDetailDialog(username, appealText);
                    });

                    approveBtn.setOnAction(e -> {
                        if (isModalDialogOpen) return;
                        isModalDialogOpen = true;
                        updateAppealStatus(username, "approved", banAppealsTable);
                    });

                    rejectBtn.setOnAction(e -> {
                        if (isModalDialogOpen) return;
                        isModalDialogOpen = true;
                        updateAppealStatus(username, "rejected", banAppealsTable);
                    });

                    HBox box = new HBox(6, viewBtn, approveBtn, rejectBtn);
                    box.setAlignment(Pos.CENTER);
                    setGraphic(box);
                    setStyle("-fx-alignment: CENTER; -fx-padding: 20 10 10 10;");
                }
            });

            banAppealsTable.getColumns().addAll(colUser, colStatus, colDate, colActions);

            // ========== PAGINATION CONTROLS ==========
            HBox paginationBox = new HBox(15);
            paginationBox.setAlignment(Pos.CENTER);
            paginationBox.setPadding(new Insets(10, 0, 0, 0));

            prevPageBtn = new Button("◀ Previous");
            prevPageBtn.setStyle(
                    "-fx-background-color: linear-gradient(to bottom, #666666, #444444);" +
                            "-fx-text-fill: white;" +
                            "-fx-font-weight: bold;" +
                            "-fx-padding: 8 20;" +
                            "-fx-background-radius: 10;" +
                            "-fx-cursor: hand;" +
                            "-fx-font-size: 13;"
            );
            prevPageBtn.setDisable(true);
            prevPageBtn.setOnAction(e -> {
                if (currentAppealPage > 0) {
                    currentAppealPage--;
                    loadAppealsPage();
                }
            });

            pageInfoLabel = new Label("Page 1 of 1");
            pageInfoLabel.setStyle(
                    "-fx-text-fill: #FFFFFF;" +
                            "-fx-font-size: 14;" +
                            "-fx-font-weight: bold;" +
                            "-fx-min-width: 120;" +
                            "-fx-alignment: center;"
            );

            nextPageBtn = new Button("Next ▶");
            nextPageBtn.setStyle(
                    "-fx-background-color: linear-gradient(to bottom, #FFAA00, #FF8800);" +
                            "-fx-text-fill: white;" +
                            "-fx-font-weight: bold;" +
                            "-fx-padding: 8 20;" +
                            "-fx-background-radius: 10;" +
                            "-fx-cursor: hand;" +
                            "-fx-font-size: 13;"
            );
            nextPageBtn.setDisable(true);
            nextPageBtn.setOnAction(e -> {
                int totalPages = (int) Math.ceil((double) totalAppealsCount / APPEALS_PER_PAGE);
                if (currentAppealPage < totalPages - 1) {
                    currentAppealPage++;
                    loadAppealsPage();
                }
            });

            // Hover effects for pagination buttons
            prevPageBtn.setOnMouseEntered(e -> {
                if (!prevPageBtn.isDisabled()) {
                    prevPageBtn.setStyle("-fx-background-color: linear-gradient(to bottom, #888888, #666666); -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 8 20; -fx-background-radius: 10; -fx-cursor: hand; -fx-font-size: 13;");
                }
            });
            prevPageBtn.setOnMouseExited(e -> {
                if (!prevPageBtn.isDisabled()) {
                    prevPageBtn.setStyle("-fx-background-color: linear-gradient(to bottom, #666666, #444444); -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 8 20; -fx-background-radius: 10; -fx-cursor: hand; -fx-font-size: 13;");
                }
            });

            nextPageBtn.setOnMouseEntered(e -> {
                if (!nextPageBtn.isDisabled()) {
                    nextPageBtn.setStyle("-fx-background-color: linear-gradient(to bottom, #FFCC22, #FFAA00); -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 8 20; -fx-background-radius: 10; -fx-cursor: hand; -fx-font-size: 13;");
                }
            });
            nextPageBtn.setOnMouseExited(e -> {
                if (!nextPageBtn.isDisabled()) {
                    nextPageBtn.setStyle("-fx-background-color: linear-gradient(to bottom, #FFAA00, #FF8800); -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 8 20; -fx-background-radius: 10; -fx-cursor: hand; -fx-font-size: 13;");
                }
            });

            paginationBox.getChildren().addAll(prevPageBtn, pageInfoLabel, nextPageBtn);

            // Appeals count info
            Label appealsCountLabel = new Label("Loading...");
            appealsCountLabel.setStyle(
                    "-fx-text-fill: #00ffcc;" +
                            "-fx-font-size: 13;" +
                            "-fx-font-weight: bold;" +
                            "-fx-padding: 5 0 0 0;"
            );
            appealsCountLabel.setId("appealsCountLabel"); // Add ID for easy reference

            // Store reference for updates
            final Label countLabelRef = appealsCountLabel;

            // Assemble the dialog
            banAppealsDialog.getChildren().addAll(topBar, titleBox, filterContainer, banAppealsTable, paginationBox, appealsCountLabel);
            overlay.getChildren().add(banAppealsDialog);
            StackPane.setAlignment(banAppealsDialog, Pos.CENTER);

            // Create scene with transparent background
            Scene scene = new Scene(overlay, 950, 750);
            scene.setFill(Color.TRANSPARENT);

            // Add CSS stylesheet for table styling
            scene.getStylesheets().add("data:text/css," +
                    ".table-view { -fx-background-color: transparent; -fx-background-radius: 0; }" +
                    ".table-view .column-header-background { -fx-background-color: linear-gradient(to right, rgba(255, 170, 0, 0.15), rgba(255, 140, 0, 0.15)); }" +
                    ".table-view .column-header { -fx-background-color: transparent; -fx-text-fill: #FFAA00; -fx-font-weight: bold; -fx-font-size: 13px; -fx-effect: dropshadow(gaussian, rgba(255, 170, 0, 0.5), 5, 0.5, 0, 0); -fx-alignment: CENTER; }" +
                    ".table-view .table-cell { -fx-border-color: transparent; -fx-background-color: transparent; }" +
                    ".table-view .table-row-cell { -fx-background-color: transparent; -fx-border-color: rgba(255, 255, 255, 0.1); -fx-border-width: 0 0 1 0; -fx-cell-size: 50; }" +
                    ".table-view .table-row-cell:odd { -fx-background-color: rgba(0, 0, 0, 0.1); }" +
                    ".table-view .table-row-cell:selected { -fx-background-color: rgba(60, 60, 60, 0.3); }" +
                    ".table-view:focused .table-row-cell:selected { -fx-background-color: rgba(60, 60, 60, 0.5); }"
            );

            banAppealsStage.setScene(scene);

            // Perfect centering
            banAppealsStage.setOnShown(e -> {
                double x = primaryStage.getX() + (primaryStage.getWidth() - banAppealsStage.getWidth()) / 2;
                double y = primaryStage.getY() + (primaryStage.getHeight() - banAppealsStage.getHeight()) / 2;
                banAppealsStage.setX(x);
                banAppealsStage.setY(y);
            });

        } else {
            Label loadingLabel = new Label("Loading appeals...");
            loadingLabel.setStyle("-fx-text-fill: white;");
            banAppealsTable.setPlaceholder(loadingLabel);
            banAppealsTable.setItems(FXCollections.observableArrayList());
            currentAppealPage = 0;
            currentStatusFilter = "Active";
            currentSearchQuery = "";
            if (statusFilterCombo != null) {
                statusFilterCombo.getItems().clear();
                statusFilterCombo.getItems().addAll("Active", "All", "Pending", "Approved", "Rejected", "Resolved");
                statusFilterCombo.setValue("Active");
            }
            if (searchField != null) {
                searchField.clear();
            }
        }

        // Show the stage window
        banAppealsStage.show();
        banAppealsStage.requestFocus();

        // Load first page of appeals
        loadAppealsPage();
    }

    // NEW METHOD: Load appeals with pagination
    private void loadAppealsPage() {
        executor.submit(() -> {
            try {
                // Build filter parameter based on selected status
                String statusFilter = "";
                if (currentStatusFilter.equals("Active")) {
                    // Show only pending, approved, and rejected (hide resolved)
                    statusFilter = "&appeal_status=in.(pending,approved,rejected)";
                } else if (!currentStatusFilter.equals("All")) {
                    statusFilter = "&appeal_status=eq." + currentStatusFilter.toLowerCase();
                }

                // Build search parameter
                String searchFilter = "";
                if (!currentSearchQuery.isEmpty()) {
                    // Use ilike for case-insensitive partial match
                    searchFilter = "&username=ilike.*" + currentSearchQuery + "*";
                }

                // First, get total count with filters
                String countUrl = SUPABASE_URL + "/rest/v1/profiles?appeal_submitted=eq.true" +
                        statusFilter + searchFilter + "&select=count";

                HttpRequest countRequest = HttpRequest.newBuilder()
                        .uri(URI.create(countUrl))
                        .header("apikey", SUPABASE_ANON_KEY)
                        .header("Authorization", "Bearer " + SUPABASE_ANON_KEY)
                        .header("Prefer", "count=exact")
                        .GET()
                        .build();

                HttpResponse<String> countResponse = httpClient.send(countRequest, HttpResponse.BodyHandlers.ofString());

                // Parse count from Content-Range header
                String contentRange = countResponse.headers().firstValue("Content-Range").orElse("0-0/0");
                totalAppealsCount = Integer.parseInt(contentRange.split("/")[1]);

                // Calculate offset for pagination
                int offset = currentAppealPage * APPEALS_PER_PAGE;

                // Load paginated data with filters
                String url = SUPABASE_URL + "/rest/v1/profiles" +
                        "?appeal_submitted=eq.true" +
                        statusFilter +
                        searchFilter +
                        "&select=username,appeal_text,appeal_status,appeal_date" +
                        "&order=appeal_date.desc" +
                        "&limit=" + APPEALS_PER_PAGE +
                        "&offset=" + offset;

                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(url))
                        .header("apikey", SUPABASE_ANON_KEY)
                        .header("Authorization", "Bearer " + SUPABASE_ANON_KEY)
                        .GET()
                        .build();

                HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

                if (response.statusCode() == 200) {
                    JsonArray array = gson.fromJson(response.body(), JsonArray.class);
                    List<JsonObject> appealsList = new ArrayList<>();

                    array.forEach(el -> {
                        JsonObject appeal = el.getAsJsonObject();
                        String username = appeal.get("username").getAsString();

                        // Cache the appeal for faster access
                        appealsCache.put(username, appeal);
                        appealsList.add(appeal);
                    });

                    // Sort: Pending first, then by date (newest first)
                    appealsList.sort((a, b) -> {
                        String statusA = a.get("appeal_status").getAsString();
                        String statusB = b.get("appeal_status").getAsString();

                        // Pending appeals always come first
                        if (statusA.equals("pending") && !statusB.equals("pending")) return -1;
                        if (!statusA.equals("pending") && statusB.equals("pending")) return 1;

                        // Within same status, sort by date (newest first)
                        String dateA = a.get("appeal_date").getAsString();
                        String dateB = b.get("appeal_date").getAsString();
                        return dateB.compareTo(dateA);
                    });

                    // Calculate display range
                    int startItem = (currentAppealPage * APPEALS_PER_PAGE) + 1;
                    int endItem = Math.min(startItem + appealsList.size() - 1, totalAppealsCount);

                    // Build count message
                    String filterInfo = "";
                    if (!currentStatusFilter.equals("All") && !currentSearchQuery.isEmpty()) {
                        filterInfo = " (" + currentStatusFilter + " - \"" + currentSearchQuery + "\")";
                    } else if (!currentStatusFilter.equals("All")) {
                        filterInfo = " (" + currentStatusFilter + ")";
                    } else if (!currentSearchQuery.isEmpty()) {
                        filterInfo = " (Search: \"" + currentSearchQuery + "\")";
                    }

                    String countMessage = totalAppealsCount == 0 ? "No appeals found" + filterInfo :
                            "Showing " + startItem + "-" + endItem + " of " + totalAppealsCount + " appeals" + filterInfo;

                    Platform.runLater(() -> {
                        banAppealsTable.setItems(FXCollections.observableArrayList(appealsList));

                        // Update placeholder text based on filters
                        String placeholderText = "No appeals found";
                        if (!currentSearchQuery.isEmpty()) {
                            placeholderText = "No appeals found for \"" + currentSearchQuery + "\"";
                        } else if (!currentStatusFilter.equals("All")) {
                            placeholderText = "No " + currentStatusFilter.toLowerCase() + " appeals found";
                        }

                        // UPDATED: Set white text color for placeholder
                        Label placeholderLabel = new Label(placeholderText);
                        placeholderLabel.setStyle("-fx-text-fill: white;");
                        banAppealsTable.setPlaceholder(placeholderLabel);
                        banAppealsTable.scrollTo(0);
                        banAppealsTable.refresh();

                        // Update count label using ID lookup
                        VBox parent = (VBox) banAppealsTable.getParent();
                        if (parent != null) {
                            parent.getChildren().stream()
                                    .filter(node -> "appealsCountLabel".equals(node.getId()))
                                    .findFirst()
                                    .ifPresent(node -> ((Label) node).setText(countMessage));
                        }

                        // Update pagination controls
                        updatePaginationControls();
                    });
                } else {
                    // UPDATED: Set white text color for error message
                    Platform.runLater(() -> {
                        Label failedLabel = new Label("Failed to load appeals");
                        failedLabel.setStyle("-fx-text-fill: white;");
                        banAppealsTable.setPlaceholder(failedLabel);
                    });
                }
            } catch (Exception e) {
                // UPDATED: Set white text color for connection error
                Platform.runLater(() -> {
                    Label errorLabel = new Label("Connection error");
                    errorLabel.setStyle("-fx-text-fill: white;");
                    banAppealsTable.setPlaceholder(errorLabel);
                });
            }
        });
    }

    // NEW METHOD: Update pagination button states
    private void updatePaginationControls() {
        int totalPages = (int) Math.ceil((double) totalAppealsCount / APPEALS_PER_PAGE);
        if (totalPages == 0) totalPages = 1;

        // Update page label
        pageInfoLabel.setText("Page " + (currentAppealPage + 1) + " of " + totalPages);

        // Update button states
        prevPageBtn.setDisable(currentAppealPage == 0);
        nextPageBtn.setDisable(currentAppealPage >= totalPages - 1);

        // Update disabled button styles
        if (prevPageBtn.isDisabled()) {
            prevPageBtn.setStyle("-fx-background-color: #333333; -fx-text-fill: #666666; -fx-font-weight: bold; -fx-padding: 8 20; -fx-background-radius: 10; -fx-font-size: 13; -fx-opacity: 0.5;");
        } else {
            prevPageBtn.setStyle("-fx-background-color: linear-gradient(to bottom, #666666, #444444); -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 8 20; -fx-background-radius: 10; -fx-cursor: hand; -fx-font-size: 13;");
        }

        if (nextPageBtn.isDisabled()) {
            nextPageBtn.setStyle("-fx-background-color: #333333; -fx-text-fill: #666666; -fx-font-weight: bold; -fx-padding: 8 20; -fx-background-radius: 10; -fx-font-size: 13; -fx-opacity: 0.5;");
        } else {
            nextPageBtn.setStyle("-fx-background-color: linear-gradient(to bottom, #FFAA00, #FF8800); -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 8 20; -fx-background-radius: 10; -fx-cursor: hand; -fx-font-size: 13;");
        }
    }

    // NEW METHOD: Clear appeals cache to free memory
    private void clearAppealsCache() {
        appealsCache.clear();
        System.gc(); // Suggest garbage collection (optional)
    }

    // 8. Add method to show appeal details:
    private void showAppealDetailDialog(String username, String appealText) {
        Stage detailStage = new Stage();
        detailStage.initOwner(primaryStage);
        detailStage.initModality(Modality.APPLICATION_MODAL);
        detailStage.initStyle(StageStyle.TRANSPARENT);
        detailStage.setTitle("Appeal Details");
        detailStage.setResizable(false);

        // Outer container with transparent background
        StackPane overlay = new StackPane();
        overlay.setStyle("-fx-background-color: transparent;");
        overlay.setPadding(new Insets(40));

        // Main dialog container
        VBox detailDialog = new VBox(20);
        detailDialog.setAlignment(Pos.TOP_CENTER);
        detailDialog.setPadding(new Insets(30));
        detailDialog.setMaxWidth(600);
        detailDialog.setMaxHeight(500);
        detailDialog.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #1a1a2e, #16213e, #0f3460);" +
                        "-fx-background-radius: 20;" +
                        "-fx-effect: dropshadow(gaussian, rgba(255, 170, 0, 0.6), 30, 0.5, 0, 0);"
        );

        // Close button
        Button closeButton = new Button("✕");
        closeButton.setStyle(
                "-fx-background-color: rgba(255, 68, 68, 0.2);" +
                        "-fx-text-fill: #FF4444;" +
                        "-fx-font-family: 'Arial';" +
                        "-fx-font-size: 16;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 20;" +
                        "-fx-padding: 6 10;" +
                        "-fx-border-color: #FF4444;" +
                        "-fx-border-radius: 20;" +
                        "-fx-border-width: 2;" +
                        "-fx-cursor: hand;"
        );
        closeButton.setOnMouseEntered(e -> closeButton.setStyle(
                "-fx-background-color: #FF4444;" +
                        "-fx-text-fill: #FFFFFF;" +
                        "-fx-font-family: 'Arial';" +
                        "-fx-font-size: 16;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 20;" +
                        "-fx-padding: 6 10;" +
                        "-fx-border-color: #FF4444;" +
                        "-fx-border-radius: 20;" +
                        "-fx-border-width: 2;" +
                        "-fx-cursor: hand;"
        ));
        closeButton.setOnMouseExited(e -> closeButton.setStyle(
                "-fx-background-color: rgba(255, 68, 68, 0.2);" +
                        "-fx-text-fill: #FF4444;" +
                        "-fx-font-family: 'Arial';" +
                        "-fx-font-size: 16;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 20;" +
                        "-fx-padding: 6 10;" +
                        "-fx-border-color: #FF4444;" +
                        "-fx-border-radius: 20;" +
                        "-fx-border-width: 2;" +
                        "-fx-cursor: hand;"
        ));

        detailStage.setOnHidden(e -> isModalDialogOpen = false);

        closeButton.setOnAction(e -> detailStage.close());

        HBox topBar = new HBox(closeButton);
        topBar.setAlignment(Pos.TOP_RIGHT);

        // Title
        Label titleLabel = new Label("📄 Appeal Details");
        titleLabel.setStyle(
                "-fx-text-fill: #FFFFFF;" +
                        "-fx-font-family: 'Arial Black';" +
                        "-fx-font-size: 24;" +
                        "-fx-font-weight: bold;" +
                        "-fx-effect: dropshadow(gaussian, rgba(255, 170, 0, 0.4), 8, 0.6, 0, 0);"
        );

        // Username label
        Label usernameLabel = new Label("From: " + username);
        usernameLabel.setStyle(
                "-fx-text-fill: #00ffcc;" +
                        "-fx-font-family: 'Arial';" +
                        "-fx-font-size: 16;" +
                        "-fx-font-weight: bold;"
        );

        // Appeal text area (read-only)
        TextArea appealTextArea = new TextArea(appealText);
        appealTextArea.setEditable(false);
        appealTextArea.setWrapText(true);
        appealTextArea.setPrefHeight(250);
        appealTextArea.setStyle(
                "-fx-control-inner-background: #16213e;" +
                        "-fx-text-fill: #ffffff;" +
                        "-fx-font-family: 'Arial';" +
                        "-fx-font-size: 14;" +
                        "-fx-background-color: #16213e;" +
                        "-fx-background-radius: 0;" +
                        "-fx-border-color: #ffaa00;" +
                        "-fx-border-width: 2;" +
                        "-fx-border-radius: 0;" +
                        "-fx-padding: 15;"
        );

        // OK button
        Button okButton = new Button("Close");
        okButton.setPrefWidth(120);
        okButton.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #ffaa00, #ff8800);" +
                        "-fx-text-fill: #000000;" +
                        "-fx-font-family: 'Arial';" +
                        "-fx-font-size: 14;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 10;" +
                        "-fx-padding: 10 20;" +
                        "-fx-cursor: hand;"
        );
        okButton.setOnMouseEntered(e -> okButton.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #ffcc00, #ffaa00);" +
                        "-fx-text-fill: #000000;" +
                        "-fx-font-family: 'Arial';" +
                        "-fx-font-size: 14;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 10;" +
                        "-fx-padding: 10 20;" +
                        "-fx-cursor: hand;"
        ));
        okButton.setOnMouseExited(e -> okButton.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #ffaa00, #ff8800);" +
                        "-fx-text-fill: #000000;" +
                        "-fx-font-family: 'Arial';" +
                        "-fx-font-size: 14;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 10;" +
                        "-fx-padding: 10 20;" +
                        "-fx-cursor: hand;"
        ));
        okButton.setOnAction(e -> detailStage.close());

        HBox buttonBox = new HBox(okButton);
        buttonBox.setAlignment(Pos.CENTER);

        // Assemble the dialog
        detailDialog.getChildren().addAll(topBar, titleLabel, usernameLabel, appealTextArea, buttonBox);
        overlay.getChildren().add(detailDialog);
        StackPane.setAlignment(detailDialog, Pos.CENTER);

        // Create scene with transparent background
        Scene scene = new Scene(overlay, 700, 600);
        scene.setFill(Color.TRANSPARENT);

        detailStage.setScene(scene);

        // Center on screen
        detailStage.setOnShown(e -> {
            double x = primaryStage.getX() + (primaryStage.getWidth() - detailStage.getWidth()) / 2;
            double y = primaryStage.getY() + (primaryStage.getHeight() - detailStage.getHeight()) / 2;
            detailStage.setX(x);
            detailStage.setY(y);
        });

        detailStage.showAndWait();
    }

    // 9. Add method to update appeal status:
    private void updateAppealStatus(String username, String status, TableView<JsonObject> table) {
        // Validate username exists before showing confirmation
        validateUsernameBeforeAction(username, () -> {
            Platform.runLater(() -> {
                String actionText = status.equals("approved") ? "approve" : "reject";
                boolean confirmed = showCustomConfirmDialog(
                        "Confirm Appeal Decision",
                        actionText.toUpperCase() + " APPEAL",
                        actionText.substring(0, 1).toUpperCase() + actionText.substring(1) +
                                " the ban appeal from \"" + username + "\"?\n\n" +
                                (status.equals("approved") ?
                                        "This will mark the appeal as approved.\nYou will still need to manually unban the user." :
                                        "This will reject the appeal.\nThe user can submit a new appeal if desired."),
                        "YES, " + actionText.toUpperCase(),
                        "Cancel"
                );

                if (!confirmed) {
                    // ADDED: Reset flag if cancelled
                    isModalDialogOpen = false;
                    return;
                }

                executor.submit(() -> {
                    try {
                        JsonObject payload = new JsonObject();
                        payload.addProperty("appeal_status", status);

                        HttpRequest request = HttpRequest.newBuilder()
                                .uri(URI.create(SUPABASE_URL + "/rest/v1/profiles?username=eq." + username))
                                .header("apikey", SUPABASE_ANON_KEY)
                                .header("Authorization", "Bearer " + SUPABASE_ANON_KEY)
                                .header("Content-Type", "application/json")
                                .method("PATCH", HttpRequest.BodyPublishers.ofString(payload.toString()))
                                .build();

                        HttpResponse<String> resp = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

                        Platform.runLater(() -> {
                            if (resp.statusCode() == 204 || resp.statusCode() == 200) {
                                showCustomSuccessDialog(
                                        "Appeal " + actionText.substring(0, 1).toUpperCase() + actionText.substring(1) + "d",
                                        "Successfully " + actionText + "d appeal from \"" + username + "\"."
                                );
                                loadAppealsPage();

                                isModalDialogOpen = false;

                                // Update table
                                table.getItems().forEach(appeal -> {
                                    if (appeal.get("username").getAsString().equals(username)) {
                                        appeal.addProperty("appeal_status", status);
                                    }
                                });
                                table.refresh();
                            } else {
                                Alert alert = new Alert(Alert.AlertType.ERROR, "Failed to update appeal status.");
                                alert.showAndWait();
                            }
                        });
                    } catch (Exception ex) {
                        Platform.runLater(() -> {
                            Alert alert = new Alert(Alert.AlertType.ERROR, "Error: " + ex.getMessage());
                            alert.showAndWait();
                            isModalDialogOpen = false;
                        });
                    }
                });
            });
        }, table);
    }

    private void refreshUserManagementTable() {
        if (userManagementStage == null || !userManagementStage.isShowing()) {
            return;
        }

        Platform.runLater(() -> {
            Label loadingLabel = new Label("Refreshing...");
            loadingLabel.setStyle("-fx-text-fill: white;");
            userManagementTable.setPlaceholder(loadingLabel);
        });

        executor.submit(() -> {
            try {
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(SUPABASE_URL + "/rest/v1/profiles?select=*"))
                        .header("apikey", SUPABASE_ANON_KEY)
                        .header("Authorization", "Bearer " + SUPABASE_ANON_KEY)
                        .GET()
                        .build();

                HttpResponse<String> response = httpClient.send(request,
                        HttpResponse.BodyHandlers.ofString());

                if (response.statusCode() == 200) {
                    JsonArray array = gson.fromJson(response.body(), JsonArray.class);
                    List<JsonObject> userList = new ArrayList<>();

                    array.forEach(el -> {
                        JsonObject user = el.getAsJsonObject();
                        String username = user.get("username").getAsString();
                        if (!"admin".equalsIgnoreCase(username)) {
                            userList.add(user);
                        }
                    });

                    userList.sort((u1, u2) -> {
                        String name1 = u1.get("username").getAsString().toLowerCase();
                        String name2 = u2.get("username").getAsString().toLowerCase();
                        return name1.compareTo(name2);
                    });

                    allUsersData.clear();
                    allUsersData.addAll(userList);

                    Platform.runLater(() -> {
                        // Re-apply current filters
                        applyUserManagementFilters();

                        Label noUsersLabel = new Label("No users found");
                        noUsersLabel.setStyle("-fx-text-fill: white;");
                        userManagementTable.setPlaceholder(noUsersLabel);
                        userManagementTable.scrollTo(0);
                        userManagementTable.refresh();
                    });
                }
            } catch (Exception e) {
                Platform.runLater(() -> {
                    Label errorLabel = new Label("Refresh failed");
                    errorLabel.setStyle("-fx-text-fill: white;");
                    userManagementTable.setPlaceholder(errorLabel);
                });
            }
        });
    }

    // Helper to re-apply filters after refresh
    private void applyUserManagementFilters() {
        // Get current filter values from UI
        ComboBox<String> filterComboBox = null;
        TextField searchField = null;

        Scene scene = userManagementStage.getScene();
        if (scene != null && scene.getRoot() instanceof StackPane) {
            StackPane root = (StackPane) scene.getRoot();
            if (!root.getChildren().isEmpty() && root.getChildren().get(0) instanceof VBox) {
                VBox dialog = (VBox) root.getChildren().get(0);
                for (javafx.scene.Node node : dialog.getChildren()) {
                    if (node instanceof HBox) {
                        HBox hbox = (HBox) node;
                        for (javafx.scene.Node child : hbox.getChildren()) {
                            if (child instanceof ComboBox) {
                                filterComboBox = (ComboBox<String>) child;
                            } else if (child instanceof TextField) {
                                searchField = (TextField) child;
                            }
                        }
                    }
                }
            }
        }

        if (filterComboBox != null && searchField != null) {
            String searchText = searchField.getText() == null ? "" :
                    searchField.getText().toLowerCase().trim();
            String filterStatus = filterComboBox.getValue();

            ObservableList<JsonObject> filtered = FXCollections.observableArrayList();

            for (JsonObject user : allUsersData) {
                String username = user.get("username").getAsString().toLowerCase();
                boolean matchesSearch = searchText.isEmpty() || username.contains(searchText);

                boolean isBanned = user.get("banned").getAsBoolean();
                boolean matchesStatus = filterStatus.equals("All Users") ||
                        (filterStatus.equals("Active Only") && !isBanned) ||
                        (filterStatus.equals("Banned Only") && isBanned);

                if (matchesSearch && matchesStatus) {
                    filtered.add(user);
                }
            }

            userManagementTable.setItems(filtered);
        } else {
            userManagementTable.setItems(FXCollections.observableArrayList(allUsersData));
        }
    }


    // Extract reset logic into separate method for cleaner code
    private void executeReset() {
        executor.submit(() -> {
            try {
                HttpRequest deleteLb = HttpRequest.newBuilder()
                        .uri(URI.create(SUPABASE_URL + "/rest/v1/leaderboard?username=not.is.null"))
                        .header("apikey", SUPABASE_ANON_KEY)
                        .header("Authorization", "Bearer " + SUPABASE_ANON_KEY)
                        .header("Prefer", "return=minimal")
                        .DELETE()
                        .build();
                HttpResponse<String> lbResp = httpClient.send(deleteLb, HttpResponse.BodyHandlers.ofString());

                HttpRequest resetProfiles = HttpRequest.newBuilder()
                        .uri(URI.create(SUPABASE_URL + "/rest/v1/profiles?username=not.is.null"))
                        .header("apikey", SUPABASE_ANON_KEY)
                        .header("Authorization", "Bearer " + SUPABASE_ANON_KEY)
                        .header("Content-Type", "application/json")
                        .header("Prefer", "return=minimal")
                        .method("PATCH", HttpRequest.BodyPublishers.ofString("{\"unlocked_levels\": \"1\"}"))
                        .build();
                HttpResponse<String> profResp = httpClient.send(resetProfiles, HttpResponse.BodyHandlers.ofString());

                Platform.runLater(() -> {
                    if (lbResp.statusCode() == 204 && profResp.statusCode() == 204) {
                        showCustomSuccessDialog(
                                "Game World Reset Complete",
                                "RESET SUCCESSFUL\n\n" +
                                        "✓ Leaderboard: CLEARED\n" +
                                        "✓ All players: Reset to Level 1\n\n" +
                                        "A new era has begun.\n" +
                                        "The game world is now pristine."
                        );

                        invalidateLeaderboardCache();
                        if (leaderboardStage != null && leaderboardStage.isShowing()) {
                            refreshLeaderboardImmediately();
                        }
                    } else {
                        showCustomErrorDialog(
                                "Reset Operation Failed",
                                "Reset operation encountered errors:\n\n" +
                                        "Leaderboard status: " + lbResp.statusCode() + "\n" +
                                        "Profiles status: " + profResp.statusCode() + "\n\n" +
                                        "Please check the database and try again."
                        );
                    }
                });

            } catch (Exception ex) {
                Platform.runLater(() ->
                        showCustomErrorDialog(
                                "Network Error",
                                "Failed to connect to database:\n\n" + ex.getMessage()
                        )
                );
            }
        });
    }

    // Helper method for error dialogs
    private void showCustomErrorDialog(String title, String message) {
        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.initOwner(primaryStage);
        dialogStage.setTitle(title);
        dialogStage.setResizable(false);

        VBox contentBox = new VBox(20);
        contentBox.setAlignment(Pos.CENTER);
        contentBox.setPadding(new Insets(40));
        contentBox.setStyle("-fx-background-color: linear-gradient(to bottom, #1a0a0a, #2a1a1a); -fx-background-radius: 0;");

        Label iconLabel = new Label("✗");
        iconLabel.setStyle("-fx-font-size: 64; -fx-text-fill: #ff4444;");

        DropShadow glow = new DropShadow();
        glow.setColor(Color.rgb(255, 68, 68, 0.8));
        glow.setRadius(30);
        iconLabel.setEffect(glow);

        Label messageLabel = new Label(message);
        messageLabel.setStyle("-fx-font-size: 16; -fx-text-fill: #ffffff; -fx-text-alignment: center;");
        messageLabel.setWrapText(true);
        messageLabel.setMaxWidth(400);
        messageLabel.setAlignment(Pos.CENTER);

        Button okBtn = new Button("OK");
        okBtn.setPrefWidth(150);
        okBtn.setPrefHeight(40);
        okBtn.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #ff4444, #cc0000); " +
                        "-fx-text-fill: #ffffff; -fx-font-weight: bold; -fx-font-size: 14; " +
                        "-fx-background-radius: 0; -fx-cursor: hand;"
        );
        okBtn.setOnAction(e -> dialogStage.close());

        contentBox.getChildren().addAll(iconLabel, messageLabel, okBtn);

        Scene scene = new Scene(contentBox, 450, 320);
        dialogStage.initStyle(StageStyle.UNDECORATED);
        dialogStage.setScene(scene);
        dialogStage.centerOnScreen();
        dialogStage.showAndWait();
    }

    // Enhanced User Management Dialog
    public void showUserManagementDialog() {
        // Create new Stage window if it doesn't exist
        if (userManagementStage == null) {
            userManagementStage = new Stage();
            userManagementStage.initOwner(primaryStage);
            userManagementStage.initModality(Modality.WINDOW_MODAL);
            userManagementStage.initStyle(StageStyle.TRANSPARENT);
            userManagementStage.setTitle("User Management");
            userManagementStage.setResizable(false);

            // Reset dialog flag when closed
            userManagementStage.setOnHidden(e -> isDialogOpen = false);

            // Outer container with transparent background
            StackPane overlay = new StackPane();
            overlay.setStyle("-fx-background-color: transparent;");
            overlay.setPadding(new Insets(40));

            // Main dialog container
            VBox userManagementDialog = new VBox(20);
            userManagementDialog.setAlignment(Pos.CENTER);
            userManagementDialog.setPadding(new Insets(30));
            userManagementDialog.setMaxWidth(820);
            userManagementDialog.setMaxHeight(650);
            userManagementDialog.setStyle(
                    "-fx-background-color: linear-gradient(to bottom, #1a1a2e, #16213e, #0f3460);" +
                            "-fx-background-radius: 20;" +
                            "-fx-effect: dropshadow(gaussian, rgba(255, 51, 102, 0.4), 25, 0.5, 0, 0);"
            );

            // Close button with hover effects
            Button closeButton = new Button("✕");
            closeButton.setStyle(
                    "-fx-background-color: rgba(255, 68, 68, 0.2);" +
                            "-fx-text-fill: #FF4444;" +
                            "-fx-font-family: 'Arial';" +
                            "-fx-font-size: 18;" +
                            "-fx-font-weight: bold;" +
                            "-fx-background-radius: 20;" +
                            "-fx-padding: 8 12;" +
                            "-fx-border-color: #FF4444;" +
                            "-fx-border-radius: 20;" +
                            "-fx-border-width: 2;" +
                            "-fx-cursor: hand;"
            );
            closeButton.setOnMouseEntered(e -> closeButton.setStyle(
                    "-fx-background-color: #FF4444;" +
                            "-fx-text-fill: #FFFFFF;" +
                            "-fx-font-family: 'Arial';" +
                            "-fx-font-size: 18;" +
                            "-fx-font-weight: bold;" +
                            "-fx-background-radius: 20;" +
                            "-fx-padding: 8 12;" +
                            "-fx-border-color: #FF4444;" +
                            "-fx-border-radius: 20;" +
                            "-fx-border-width: 2;" +
                            "-fx-cursor: hand;"
            ));
            closeButton.setOnMouseExited(e -> closeButton.setStyle(
                    "-fx-background-color: rgba(255, 68, 68, 0.2);" +
                            "-fx-text-fill: #FF4444;" +
                            "-fx-font-family: 'Arial';" +
                            "-fx-font-size: 18;" +
                            "-fx-font-weight: bold;" +
                            "-fx-background-radius: 20;" +
                            "-fx-padding: 8 12;" +
                            "-fx-border-color: #FF4444;" +
                            "-fx-border-radius: 20;" +
                            "-fx-border-width: 2;" +
                            "-fx-cursor: hand;"
            ));
            closeButton.setOnAction(e -> userManagementStage.close());

            HBox topBar = new HBox(closeButton);
            topBar.setAlignment(Pos.TOP_RIGHT);

            // Title with glow effect
            Label titleLabel = new Label("👥 USER MANAGEMENT 👥");
            titleLabel.setStyle(
                    "-fx-text-fill: #FFFFFF;" +
                            "-fx-font-family: 'Arial Black';" +
                            "-fx-font-size: 28;" +
                            "-fx-font-weight: bold;" +
                            "-fx-effect: dropshadow(gaussian, rgba(255, 51, 102, 0.4), 8, 0.6, 0, 0);"
            );

            Label subtitleLabel = new Label("Manage User Accounts & Permissions");
            subtitleLabel.setStyle(
                    "-fx-text-fill: #FF3366;" +
                            "-fx-font-family: 'Arial';" +
                            "-fx-font-size: 14;" +
                            "-fx-font-style: italic;"
            );

            VBox titleBox = new VBox(5, titleLabel, subtitleLabel);
            titleBox.setAlignment(Pos.CENTER);

            // ========== FILTER CONTROLS ==========
            HBox filterBox = new HBox(15);
            filterBox.setAlignment(Pos.CENTER);
            filterBox.setPadding(new Insets(10, 0, 10, 0));

            Label filterLabel = new Label("Filter by Status:");
            filterLabel.setStyle(
                    "-fx-text-fill: #FFFFFF;" +
                            "-fx-font-size: 14;" +
                            "-fx-font-weight: bold;"
            );

            // Filter dropdown
            ComboBox<String> filterComboBox = new ComboBox<>();
            filterComboBox.getItems().addAll("All Users", "Active Only", "Banned Only");
            filterComboBox.setValue("All Users");
            filterComboBox.setPrefWidth(150);
            filterComboBox.setStyle(
                    "-fx-background-color: rgba(0, 0, 0, 0.5);" +
                            "-fx-text-fill: white;" +
                            "-fx-font-size: 13;" +
                            "-fx-font-weight: bold;" +
                            "-fx-background-radius: 8;" +
                            "-fx-border-color: #ff3366;" +
                            "-fx-border-radius: 8;" +
                            "-fx-border-width: 2;"
            );

            // Search field label
            Label searchLabel = new Label("Search Username:");
            searchLabel.setStyle(
                    "-fx-text-fill: #FFFFFF;" +
                            "-fx-font-size: 14;" +
                            "-fx-font-weight: bold;"
            );

            // Search field
            TextField searchField = new TextField();
            searchField.setPromptText("Type username...");
            searchField.setPrefWidth(200);
            searchField.setStyle(
                    "-fx-background-color: rgba(0, 0, 0, 0.5);" +
                            "-fx-text-fill: white;" +
                            "-fx-prompt-text-fill: #888888;" +
                            "-fx-font-size: 13;" +
                            "-fx-background-radius: 8;" +
                            "-fx-border-color: #ff3366;" +
                            "-fx-border-radius: 8;" +
                            "-fx-border-width: 2;" +
                            "-fx-padding: 5 10;"
            );

            // Clear search button - FIXED SIZE
            Button clearSearchBtn = new Button("✕");
            clearSearchBtn.setPrefWidth(30);
            clearSearchBtn.setMinWidth(30);
            clearSearchBtn.setMaxWidth(30);
            clearSearchBtn.setPrefHeight(30);
            clearSearchBtn.setMinHeight(30);
            clearSearchBtn.setMaxHeight(30);
            clearSearchBtn.setStyle(
                    "-fx-background-color: rgba(255, 68, 68, 0.3);" +
                            "-fx-text-fill: #ff4444;" +
                            "-fx-font-size: 14;" +
                            "-fx-font-weight: bold;" +
                            "-fx-background-radius: 8;" +
                            "-fx-padding: 0;" +
                            "-fx-cursor: hand;"
            );
            clearSearchBtn.setVisible(false);
            clearSearchBtn.setOnAction(e -> {
                searchField.clear();
                clearSearchBtn.setVisible(false);
            });

            // Show/hide clear button based on search field content
            searchField.textProperty().addListener((obs, oldVal, newVal) -> {
                clearSearchBtn.setVisible(newVal != null && !newVal.trim().isEmpty());
            });

            filterBox.getChildren().addAll(filterLabel, filterComboBox, searchLabel, searchField, clearSearchBtn);

            // Create the user management table
            userManagementTable = new TableView<>();
            userManagementTable.setSelectionModel(null);
            userManagementTable.setTableMenuButtonVisible(false);
            Label placeholderLabel = new Label("Loading users...");
            placeholderLabel.setStyle("-fx-text-fill: white;");
            userManagementTable.setPlaceholder(placeholderLabel);
            userManagementTable.setStyle("-fx-background-color: rgba(58, 58, 58, 0.4); -fx-control-inner-background: transparent;");
            userManagementTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            userManagementTable.setPrefHeight(400);
            userManagementTable.setMaxHeight(400);
            userManagementTable.setFixedCellSize(50);

            // USERNAME COLUMN
            TableColumn<JsonObject, String> colUser = new TableColumn<>("USERNAME");
            colUser.setMinWidth(180);
            colUser.setMaxWidth(250);
            colUser.setReorderable(false);
            colUser.setSortable(false);
            colUser.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().get("username").getAsString()));
            colUser.setCellFactory(col -> new TableCell<JsonObject, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                        setStyle("");
                    } else {
                        setText(item);
                        setStyle("-fx-text-fill: #00ffcc; -fx-font-weight: bold; -fx-font-size: 14px; -fx-alignment: CENTER-LEFT; -fx-padding: 20 10 10 10;");
                    }
                }
            });

// LEVEL COLUMN
            TableColumn<JsonObject, String> colLevel = new TableColumn<>("UNLOCKED");
            colLevel.setMinWidth(100);
            colLevel.setMaxWidth(120);
            colLevel.setReorderable(false);
            colLevel.setSortable(false);
            colLevel.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().get("unlocked_levels").getAsString()));
            colLevel.setCellFactory(col -> new TableCell<JsonObject, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                        setStyle("");
                    } else {
                        String[] levels = item.split(",");
                        int unlockedCount = 0;

                        // Count only if the string is not just an empty string after a potential split
                        if (levels.length > 0 && !levels[0].trim().isEmpty()) {
                            unlockedCount = levels.length;
                        }

                        // Display the count followed by "levels"
                        setText(unlockedCount + " levels");
                        setStyle("-fx-text-fill: #ffffff; -fx-font-size: 14px; -fx-alignment: CENTER; -fx-padding: 20 10 10 10;");
                    }
                }
            });

// STATUS COLUMN
            TableColumn<JsonObject, Boolean> colBanned = new TableColumn<>("STATUS");
            colBanned.setMinWidth(100);
            colBanned.setMaxWidth(120);
            colBanned.setReorderable(false);
            colBanned.setSortable(false);
            colBanned.setCellValueFactory(d -> new SimpleBooleanProperty(d.getValue().get("banned").getAsBoolean()));
            colBanned.setCellFactory(col -> new TableCell<JsonObject, Boolean>() {
                @Override
                protected void updateItem(Boolean item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                        setStyle("");
                    } else {
                        setText(item ? "BANNED" : "ACTIVE");
                        setStyle(item ?
                                "-fx-text-fill: #ff4444; -fx-font-weight: bold; -fx-font-size: 13px; -fx-alignment: CENTER; -fx-padding: 20 10 10 10;" :
                                "-fx-text-fill: #44ff44; -fx-font-weight: bold; -fx-font-size: 13px; -fx-alignment: CENTER; -fx-padding: 20 10 10 10;");
                    }
                }
            });

// ACTIONS COLUMN
            TableColumn<JsonObject, Void> colActions = new TableColumn<>("ACTIONS");
            colActions.setMinWidth(360);
            colActions.setMaxWidth(400);
            colActions.setSortable(false);
            colActions.setResizable(false);
            colActions.setReorderable(false);
            colActions.setCellFactory(param -> new TableCell<>() {
                private final Button banBtn = new Button();
                private final Button resetPicBtn = new Button("Reset Pic");
                private final Button delLbBtn = new Button("Clear LB");
                private final Button delBtn = new Button("Delete");

                {
                    // Ban button
                    banBtn.setPrefWidth(70);
                    banBtn.setMinWidth(70);
                    banBtn.setMaxWidth(70);
                    banBtn.setStyle("-fx-background-color: linear-gradient(to bottom, #ff4444, #cc0000); -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 6 12; -fx-background-radius: 8; -fx-cursor: hand; -fx-font-size: 11;");

                    // Reset Picture button
                    resetPicBtn.setPrefWidth(75);
                    resetPicBtn.setMinWidth(75);
                    resetPicBtn.setMaxWidth(75);
                    resetPicBtn.setStyle("-fx-background-color: linear-gradient(to bottom, #6666ff, #4444cc); -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 6 10; -fx-background-radius: 8; -fx-cursor: hand; -fx-font-size: 11;");

                    // Clear Leaderboard button
                    delLbBtn.setPrefWidth(75);
                    delLbBtn.setMinWidth(75);
                    delLbBtn.setMaxWidth(75);
                    delLbBtn.setStyle("-fx-background-color: linear-gradient(to bottom, #ff9900, #cc7700); -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 6 10; -fx-background-radius: 8; -fx-cursor: hand; -fx-font-size: 11;");

                    // Delete button
                    delBtn.setPrefWidth(70);
                    delBtn.setMinWidth(70);
                    delBtn.setMaxWidth(70);
                    delBtn.setStyle("-fx-background-color: linear-gradient(to bottom, #880000, #550000); -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 6 12; -fx-background-radius: 8; -fx-cursor: hand; -fx-font-size: 11;");

                    // Hover effects remain the same...
                    banBtn.setOnMouseEntered(e -> banBtn.setStyle("-fx-background-color: linear-gradient(to bottom, #ff6666, #ee2222); -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 6 12; -fx-background-radius: 8; -fx-cursor: hand; -fx-font-size: 11;"));
                    banBtn.setOnMouseExited(e -> banBtn.setStyle("-fx-background-color: linear-gradient(to bottom, #ff4444, #cc0000); -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 6 12; -fx-background-radius: 8; -fx-cursor: hand; -fx-font-size: 11;"));

                    resetPicBtn.setOnMouseEntered(e -> resetPicBtn.setStyle("-fx-background-color: linear-gradient(to bottom, #7777ff, #5555dd); -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 6 10; -fx-background-radius: 8; -fx-cursor: hand; -fx-font-size: 11;"));
                    resetPicBtn.setOnMouseExited(e -> resetPicBtn.setStyle("-fx-background-color: linear-gradient(to bottom, #6666ff, #4444cc); -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 6 10; -fx-background-radius: 8; -fx-cursor: hand; -fx-font-size: 11;"));

                    delLbBtn.setOnMouseEntered(e -> delLbBtn.setStyle("-fx-background-color: linear-gradient(to bottom, #ffaa22, #ee8800); -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 6 10; -fx-background-radius: 8; -fx-cursor: hand; -fx-font-size: 11;"));
                    delLbBtn.setOnMouseExited(e -> delLbBtn.setStyle("-fx-background-color: linear-gradient(to bottom, #ff9900, #cc7700); -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 6 10; -fx-background-radius: 8; -fx-cursor: hand; -fx-font-size: 11;"));

                    delBtn.setOnMouseEntered(e -> delBtn.setStyle("-fx-background-color: linear-gradient(to bottom, #aa0000, #770000); -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 6 12; -fx-background-radius: 8; -fx-cursor: hand; -fx-font-size: 11;"));
                    delBtn.setOnMouseExited(e -> delBtn.setStyle("-fx-background-color: linear-gradient(to bottom, #880000, #550000); -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 6 12; -fx-background-radius: 8; -fx-cursor: hand; -fx-font-size: 11;"));
                }

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || getIndex() < 0) {
                        setGraphic(null);
                        setStyle("");
                        return;
                    }

                    JsonObject user = getTableView().getItems().get(getIndex());
                    String username = user.get("username").getAsString();

                    if ("admin".equalsIgnoreCase(username)) {
                        setGraphic(null);
                        setStyle("");
                        return;
                    }

                    boolean banned = user.get("banned").getAsBoolean();
                    banBtn.setText(banned ? "Unban" : "Ban");

                    // FIXED: Add modal check to all button actions
                    banBtn.setOnAction(e -> {
                        if (isModalDialogOpen) return;
                        isModalDialogOpen = true;
                        toggleBanUser(username, !banned, userManagementTable);
                    });

                    resetPicBtn.setOnAction(e -> {
                        if (isModalDialogOpen) return;
                        isModalDialogOpen = true;
                        resetUserProfilePicture(username, userManagementTable);
                    });

                    delLbBtn.setOnAction(e -> {
                        if (isModalDialogOpen) return;
                        isModalDialogOpen = true;
                        deleteUserLeaderboard(username, userManagementTable);
                    });

                    delBtn.setOnAction(e -> {
                        if (isModalDialogOpen) return;
                        isModalDialogOpen = true;
                        deleteUser(username, userManagementTable);
                    });

                    HBox box = new HBox(6, banBtn, resetPicBtn, delLbBtn, delBtn);
                    box.setAlignment(Pos.CENTER);
                    setGraphic(box);
                    setStyle("-fx-alignment: CENTER; -fx-padding: 20 10 10 10;");
                }
            });

            userManagementTable.getColumns().addAll(colUser, colLevel, colBanned, colActions);

            // Combined search and filter functionality
            Runnable applyFilters = () -> {
                ObservableList<JsonObject> filtered = FXCollections.observableArrayList();
                String searchText = searchField.getText() == null ? "" : searchField.getText().toLowerCase().trim();
                String filterStatus = filterComboBox.getValue();

                for (JsonObject user : allUsersData) {
                    // Apply search filter
                    String username = user.get("username").getAsString().toLowerCase();
                    boolean matchesSearch = searchText.isEmpty() || username.contains(searchText);

                    // Apply status filter
                    boolean isBanned = user.get("banned").getAsBoolean();
                    boolean matchesStatus = filterStatus.equals("All Users") ||
                            (filterStatus.equals("Active Only") && !isBanned) ||
                            (filterStatus.equals("Banned Only") && isBanned);

                    if (matchesSearch && matchesStatus) {
                        filtered.add(user);
                    }
                }

                userManagementTable.setItems(filtered);
            };

            // Add listeners
            searchField.textProperty().addListener((observable, oldValue, newValue) -> applyFilters.run());
            filterComboBox.valueProperty().addListener((observable, oldValue, newValue) -> applyFilters.run());

            // UPDATE: Reset filters when close button is clicked
            closeButton.setOnAction(e -> {
                // Reset dropdown to default
                filterComboBox.setValue("All Users");
                // Clear search field
                searchField.clear();
                // Close the stage
                userManagementStage.close();
            });

            // Assemble the dialog
            userManagementDialog.getChildren().addAll(topBar, titleBox, filterBox, userManagementTable);
            overlay.getChildren().add(userManagementDialog);
            StackPane.setAlignment(userManagementDialog, Pos.CENTER);

            // Create scene with transparent background
            Scene scene = new Scene(overlay, 900, 730);
            scene.setFill(Color.TRANSPARENT);

            // Add CSS stylesheet for table styling
            scene.getStylesheets().add("data:text/css," +
                    ".table-view { -fx-background-color: transparent; -fx-background-radius: 0; -fx-padding: 0; }" +
                    ".table-view .column-header-background { -fx-background-color: linear-gradient(to right, rgba(255, 51, 102, 0.15), rgba(138, 43, 226, 0.15)); -fx-padding: 0; }" +
                    ".table-view .column-header-background .nested-column-header { -fx-padding: 0; }" +
                    ".table-view .column-header-background .filler { -fx-background-color: white; }" +
                    ".table-view .column-header { -fx-background-color: transparent; -fx-text-fill: #FF3366; -fx-font-weight: bold; -fx-font-size: 13px; -fx-effect: dropshadow(gaussian, rgba(255, 51, 102, 0.5), 5, 0.5, 0, 0); }" +
                    ".table-view .table-cell { -fx-border-color: transparent; -fx-background-color: transparent; -fx-padding: 15 10 10 10; }" +
                    ".table-view .table-row-cell { -fx-background-color: transparent; -fx-border-color: rgba(255, 255, 255, 0.1); -fx-border-width: 0 0 1 0; }" +
                    ".table-view .table-row-cell:odd { -fx-background-color: rgba(0, 0, 0, 0.1); }" +
                    ".table-view .table-row-cell:selected { -fx-background-color: rgba(60, 60, 60, 0.3); }" +
                    ".table-view:focused .table-row-cell:selected { -fx-background-color: rgba(60, 60, 60, 0.5); }" +
                    ".combo-box-popup .list-view { -fx-background-color: rgba(0, 0, 0, 0.9); -fx-background-radius: 8; -fx-border-color: #ff3366; -fx-border-width: 2; -fx-border-radius: 8; -fx-padding: 5; }" +
                    ".combo-box-popup .list-view .list-cell { -fx-background-color: transparent; -fx-text-fill: white; -fx-padding: 8 10; }" +
                    ".combo-box-popup .list-view .list-cell:hover { -fx-background-color: #ff3366; -fx-text-fill: white; }" +
                    ".combo-box-popup .list-view .list-cell:selected { -fx-background-color: rgba(255, 51, 102, 0.5); -fx-text-fill: white; }"
            );

            userManagementStage.setScene(scene);

            // Perfect centering - EXACTLY like leaderboard
            userManagementStage.setOnShown(e -> {
                double x = primaryStage.getX() + (primaryStage.getWidth() - userManagementStage.getWidth()) / 2;
                double y = primaryStage.getY() + (primaryStage.getHeight() - userManagementStage.getHeight()) / 2;
                userManagementStage.setX(x);
                userManagementStage.setY(y);
            });

        } else {
            // Reset the table state before re-showing
            Label loadingLabel = new Label("Loading users...");
            loadingLabel.setStyle("-fx-text-fill: white;");
            userManagementTable.setPlaceholder(loadingLabel);
            userManagementTable.setItems(FXCollections.observableArrayList());
        }

        // Show the stage window
        userManagementStage.show();
        userManagementStage.requestFocus();

        // Load user data
        executor.submit(() -> {
            try {
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(SUPABASE_URL + "/rest/v1/profiles?select=*"))
                        .header("apikey", SUPABASE_ANON_KEY)
                        .header("Authorization", "Bearer " + SUPABASE_ANON_KEY)
                        .GET()
                        .build();

                HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

                if (response.statusCode() == 200) {
                    JsonArray array = gson.fromJson(response.body(), JsonArray.class);
                    List<JsonObject> userList = new ArrayList<>();

                    array.forEach(el -> {
                        JsonObject user = el.getAsJsonObject();
                        String username = user.get("username").getAsString();
                        if (!"admin".equalsIgnoreCase(username)) {
                            userList.add(user);
                        }
                    });

                    // Sort alphabetically by username
                    userList.sort((u1, u2) -> {
                        String name1 = u1.get("username").getAsString().toLowerCase();
                        String name2 = u2.get("username").getAsString().toLowerCase();
                        return name1.compareTo(name2);
                    });

                    allUsersData.clear();
                    allUsersData.addAll(userList);

                    Platform.runLater(() -> {
                        // KEY FIX: Get current filter values from UI controls
                        ComboBox<String> filterComboBox = null;
                        TextField searchField = null;

                        // Find the filter controls in the scene
                        Scene scene = userManagementStage.getScene();
                        if (scene != null && scene.getRoot() instanceof StackPane) {
                            StackPane root = (StackPane) scene.getRoot();
                            if (!root.getChildren().isEmpty() && root.getChildren().get(0) instanceof VBox) {
                                VBox dialog = (VBox) root.getChildren().get(0);
                                for (javafx.scene.Node node : dialog.getChildren()) {
                                    if (node instanceof HBox) {
                                        HBox hbox = (HBox) node;
                                        for (javafx.scene.Node child : hbox.getChildren()) {
                                            if (child instanceof ComboBox) {
                                                filterComboBox = (ComboBox<String>) child;
                                            } else if (child instanceof TextField) {
                                                searchField = (TextField) child;
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        // Apply filters if they were set during loading
                        if (filterComboBox != null && searchField != null) {
                            String searchText = searchField.getText() == null ? "" : searchField.getText().toLowerCase().trim();
                            String filterStatus = filterComboBox.getValue();

                            ObservableList<JsonObject> filtered = FXCollections.observableArrayList();

                            for (JsonObject user : allUsersData) {
                                String username = user.get("username").getAsString().toLowerCase();
                                boolean matchesSearch = searchText.isEmpty() || username.contains(searchText);

                                boolean isBanned = user.get("banned").getAsBoolean();
                                boolean matchesStatus = filterStatus.equals("All Users") ||
                                        (filterStatus.equals("Active Only") && !isBanned) ||
                                        (filterStatus.equals("Banned Only") && isBanned);

                                if (matchesSearch && matchesStatus) {
                                    filtered.add(user);
                                }
                            }

                            userManagementTable.setItems(filtered);
                        } else {
                            // Fallback: show all data if controls not found
                            userManagementTable.setItems(FXCollections.observableArrayList(allUsersData));
                        }

                        Label noUsersLabel = new Label("No users found");
                        noUsersLabel.setStyle("-fx-text-fill: white;");
                        userManagementTable.setPlaceholder(noUsersLabel);
                        userManagementTable.scrollTo(0);
                        userManagementTable.refresh();
                    });
                } else {
                    Label failedLabel = new Label("Failed to load users");
                    failedLabel.setStyle("-fx-text-fill: white;");
                    Platform.runLater(() -> userManagementTable.setPlaceholder(failedLabel));
                }
            } catch (Exception e) {
                Label errorLabel = new Label("Connection error");
                errorLabel.setStyle("-fx-text-fill: white;");
                Platform.runLater(() -> userManagementTable.setPlaceholder(errorLabel));
            }
        });
    }

    private void validateUsernameBeforeAction(String username, Runnable action, TableView<JsonObject> table) {
        executor.submit(() -> {
            try {
                // Check if username still exists in database
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(SUPABASE_URL + "/rest/v1/profiles?username=eq." +
                                URLEncoder.encode(username, StandardCharsets.UTF_8)))
                        .header("apikey", SUPABASE_ANON_KEY)
                        .header("Authorization", "Bearer " + SUPABASE_ANON_KEY)
                        .GET()
                        .build();

                HttpResponse<String> response = httpClient.send(request,
                        HttpResponse.BodyHandlers.ofString());

                if (response.statusCode() == 200) {
                    JsonArray array = gson.fromJson(response.body(), JsonArray.class);

                    if (array.size() == 0) {
                        // Username no longer exists - refresh table
                        Platform.runLater(() -> {
                            showCustomErrorDialog(
                                    "User Not Found",
                                    "The user \"" + username + "\" no longer exists.\n\n" +
                                            "They may have changed their username or been deleted.\n\n" +
                                            "Refreshing the list..."
                            );
                            isModalDialogOpen = false;

                            // Refresh the appropriate table
                            if (table == userManagementTable) {
                                refreshUserManagementTable();
                            } else if (table == banAppealsTable) {
                                loadAppealsPage();
                            }
                        });
                        return;
                    }

                    // Username exists - proceed with action
                    action.run();
                }
            } catch (Exception e) {
                Platform.runLater(() -> {
                    showCustomErrorDialog("Connection Error",
                            "Failed to verify user existence: " + e.getMessage());
                    isModalDialogOpen = false;
                });
            }
        });
    }

    private void resetUserProfilePicture(String username, TableView<JsonObject> table) {
        // Validate username exists before showing modal
        validateUsernameBeforeAction(username, () -> {
            Platform.runLater(() -> {
                // Create modal overlay with rounded corners to match dialog
                StackPane modalOverlay = new StackPane();
                modalOverlay.setStyle(
                        "-fx-background-color: rgba(0, 0, 0, 0.7);" +
                                "-fx-background-radius: 20;"  // Match user management dialog radius
                );
                modalOverlay.setPrefSize(userManagementStage.getWidth(), userManagementStage.getHeight());

                // Dialog container
                VBox dialogBox = new VBox(25);
                dialogBox.setPadding(new Insets(35));
                dialogBox.setAlignment(Pos.CENTER);
                dialogBox.setMaxWidth(500);
                dialogBox.setMaxHeight(400);
                dialogBox.setStyle(
                        "-fx-background-color: linear-gradient(to bottom, #1a1a2e, #16213e);" +
                                "-fx-background-radius: 20;" +
                                "-fx-effect: dropshadow(gaussian, rgba(100, 150, 255, 0.5), 30, 0.5, 0, 0);"
                );

                Label titleLabel = new Label("Reset Profile Picture");
                titleLabel.setStyle(
                        "-fx-font-size: 24; " +
                                "-fx-font-weight: bold; " +
                                "-fx-text-fill: #00d9ff;" +
                                "-fx-effect: dropshadow(gaussian, rgba(0, 217, 255, 0.4), 5, 0.6, 0, 0);"
                );

                Label usernameLabel = new Label(username);
                usernameLabel.setStyle(
                        "-fx-font-size: 18; " +
                                "-fx-font-weight: 600; " +
                                "-fx-text-fill: #ffffff;"
                );

                // Preview of default profile picture
                ImageView defaultPreview = new ImageView();
                defaultPreview.setFitWidth(100);
                defaultPreview.setFitHeight(100);
                Circle clipCircle = new Circle(50, 50, 50);
                defaultPreview.setClip(clipCircle);

                File defaultFile = new File("resources/images/default_profile.png");
                if (defaultFile.exists()) {
                    defaultPreview.setImage(new Image(defaultFile.toURI().toString()));
                }

                Circle outlineCircle = new Circle(50, 50, 50);
                outlineCircle.setFill(Color.TRANSPARENT);
                outlineCircle.setStroke(Color.rgb(0, 217, 255, 0.8));
                outlineCircle.setStrokeWidth(3);

                StackPane previewStack = new StackPane(defaultPreview, outlineCircle);

                Label messageLabel = new Label("This will reset their profile picture to the default avatar.");
                messageLabel.setStyle(
                        "-fx-font-size: 14; " +
                                "-fx-text-fill: #cccccc;" +
                                "-fx-text-alignment: center;"
                );
                messageLabel.setWrapText(true);
                messageLabel.setMaxWidth(420);
                messageLabel.setAlignment(Pos.CENTER);

                // Button container
                HBox buttonBox = new HBox(15);
                buttonBox.setAlignment(Pos.CENTER);

                Button confirmBtn = new Button("✓ Reset Picture");
                confirmBtn.setPrefWidth(180);
                confirmBtn.setStyle(
                        "-fx-background-color: linear-gradient(to bottom, #00d9ff, #0099ff); " +
                                "-fx-text-fill: white; " +
                                "-fx-font-weight: bold; " +
                                "-fx-padding: 12 25; " +
                                "-fx-background-radius: 10; " +
                                "-fx-cursor: hand; " +
                                "-fx-font-size: 14;" +
                                "-fx-effect: dropshadow(gaussian, rgba(0, 217, 255, 0.4), 10, 0.5, 0, 2);"
                );
                confirmBtn.setOnMouseEntered(e -> confirmBtn.setStyle(
                        "-fx-background-color: linear-gradient(to bottom, #00eeff, #00aaff); " +
                                "-fx-text-fill: white; " +
                                "-fx-font-weight: bold; " +
                                "-fx-padding: 12 25; " +
                                "-fx-background-radius: 10; " +
                                "-fx-cursor: hand; " +
                                "-fx-font-size: 14;" +
                                "-fx-effect: dropshadow(gaussian, rgba(0, 217, 255, 0.6), 15, 0.6, 0, 3);"
                ));
                confirmBtn.setOnMouseExited(e -> confirmBtn.setStyle(
                        "-fx-background-color: linear-gradient(to bottom, #00d9ff, #0099ff); " +
                                "-fx-text-fill: white; " +
                                "-fx-font-weight: bold; " +
                                "-fx-padding: 12 25; " +
                                "-fx-background-radius: 10; " +
                                "-fx-cursor: hand; " +
                                "-fx-font-size: 14;" +
                                "-fx-effect: dropshadow(gaussian, rgba(0, 217, 255, 0.4), 10, 0.5, 0, 2);"
                ));

                Button cancelBtn = new Button("Cancel");
                cancelBtn.setPrefWidth(180);
                cancelBtn.setStyle(
                        "-fx-background-color: linear-gradient(to bottom, #666666, #444444); " +
                                "-fx-text-fill: white; " +
                                "-fx-font-weight: bold; " +
                                "-fx-padding: 12 25; " +
                                "-fx-background-radius: 10; " +
                                "-fx-cursor: hand; " +
                                "-fx-font-size: 14;"
                );
                cancelBtn.setOnMouseEntered(e -> cancelBtn.setStyle(
                        "-fx-background-color: linear-gradient(to bottom, #888888, #666666); " +
                                "-fx-text-fill: white; " +
                                "-fx-font-weight: bold; " +
                                "-fx-padding: 12 25; " +
                                "-fx-background-radius: 10; " +
                                "-fx-cursor: hand; " +
                                "-fx-font-size: 14;"
                ));
                cancelBtn.setOnMouseExited(e -> cancelBtn.setStyle(
                        "-fx-background-color: linear-gradient(to bottom, #666666, #444444); " +
                                "-fx-text-fill: white; " +
                                "-fx-font-weight: bold; " +
                                "-fx-padding: 12 25; " +
                                "-fx-background-radius: 10; " +
                                "-fx-cursor: hand; " +
                                "-fx-font-size: 14;"
                ));

                // Get the root pane of userManagementStage
                Scene userMgmtScene = userManagementStage.getScene();
                javafx.scene.Parent rootParent = userMgmtScene.getRoot();

                if (!(rootParent instanceof StackPane)) {
                    System.err.println("Root is not a StackPane, cannot show modal dialog");
                    isModalDialogOpen = false; // ✅ FIXED: Reset flag on early return
                    return;
                }

                StackPane rootPane = (StackPane) rootParent;

                Runnable closeModal = () -> {
                    rootPane.getChildren().remove(modalOverlay);
                    isModalDialogOpen = false; // Reset flag
                };

                cancelBtn.setOnAction(e -> closeModal.run());

                confirmBtn.setOnAction(e -> {
                    closeModal.run();

                    // Execute the reset
                    executor.submit(() -> {
                        try {
                            // Get default profile picture bytes
                            byte[] defaultPicBytes;
                            File defaultFileLoad = new File("resources/images/default_profile.png");
                            if (defaultFileLoad.exists()) {
                                defaultPicBytes = Files.readAllBytes(defaultFileLoad.toPath());
                            } else {
                                defaultPicBytes = getDefaultProfilePicBytes();
                            }

                            if (defaultPicBytes == null) {
                                Platform.runLater(() -> {
                                    showCustomErrorDialog("Reset Failed", "Failed to load default profile picture.");
                                    isModalDialogOpen = false; // ✅ FIXED: Reset flag after error dialog
                                });
                                return;
                            }

                            // Keep a reference to the bytes for updating current user's UI
                            final byte[] finalDefaultBytes = defaultPicBytes;

                            // Encode to Base64
                            String base64 = Base64.getEncoder().encodeToString(defaultPicBytes);

                            // Create payload
                            JsonObject payload = new JsonObject();
                            payload.addProperty("profile_picture_bytes", base64);
                            payload.addProperty("profile_picture", (String) null);

                            // Update profile in database
                            String selectFilter = URLEncoder.encode(username, StandardCharsets.UTF_8);
                            String profileUrl = SUPABASE_URL + "/rest/v1/profiles?username=eq." + selectFilter;

                            HttpRequest request = HttpRequest.newBuilder()
                                    .uri(URI.create(profileUrl))
                                    .header("apikey", SUPABASE_ANON_KEY)
                                    .header("Authorization", "Bearer " + SUPABASE_ANON_KEY)
                                    .header("Content-Type", "application/json")
                                    .header("Prefer", "return=representation")
                                    .method("PATCH", HttpRequest.BodyPublishers.ofString(payload.toString()))
                                    .build();

                            HttpResponse<String> resp = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

                            if (resp.statusCode() == 200 || resp.statusCode() == 204) {
                                Platform.runLater(() -> {
                                    // Invalidate cache for this user
                                    invalidateProfilePictureCache(username);

                                    // Refresh leaderboard to show updated picture
                                    refreshLeaderboardData();

                                    // CRITICAL: If this is the current user, update their UI immediately
                                    if (username.equals(currentUser)) {
                                        // Create Image from the bytes we just uploaded
                                        Image defaultImg = new Image(new ByteArrayInputStream(finalDefaultBytes));
                                        currentProfileImage = defaultImg;

                                        // Update top bar profile picture
                                        if (topBarProfilePicView != null) {
                                            topBarProfilePicView.setImage(defaultImg);
                                        }

                                        // Update level select profile picture
                                        if (levelSelectProfileView != null) {
                                            levelSelectProfileView.setImage(defaultImg);
                                        }

                                        // If profile settings dialog is currently open, update it too
                                        if (profileSettingsStage != null && profileSettingsStage.isShowing()) {
                                            try {
                                                VBox mainContainer = (VBox) ((StackPane) profileSettingsStage.getScene().getRoot()).getChildren().get(0);
                                                HBox contentLayout = (HBox) mainContainer.getChildren().get(1);
                                                VBox leftPanel = (VBox) contentLayout.getChildren().get(0);
                                                StackPane profileStack = (StackPane) leftPanel.getChildren().get(1);
                                                ImageView profilePreview = (ImageView) profileStack.getChildren().get(1);
                                                profilePreview.setImage(defaultImg);
                                            } catch (Exception ex) {
                                                // If we can't update the dialog, it's not critical
                                                ex.printStackTrace();
                                            }
                                        }
                                    }

                                    showCustomSuccessDialog(
                                            "Profile Picture Reset",
                                            "Profile picture for \"" + username + "\" has been reset to default."
                                    );
                                    isModalDialogOpen = false; // ✅ FIXED: Reset flag after success dialog
                                });
                            } else {
                                Platform.runLater(() -> {
                                    showCustomErrorDialog(
                                            "Reset Failed",
                                            "Failed to reset profile picture:\n" + resp.body()
                                    );
                                    isModalDialogOpen = false; // ✅ FIXED: Reset flag after error dialog
                                });
                            }

                        } catch (Exception ex) {
                            ex.printStackTrace();
                            Platform.runLater(() -> {
                                showCustomErrorDialog(
                                        "Reset Failed",
                                        "Error resetting profile picture:\n" + ex.getMessage()
                                );
                                isModalDialogOpen = false; // ✅ FIXED: Reset flag after error dialog
                            });
                        }
                    });
                });

                // Allow clicking outside to close
                modalOverlay.setOnMouseClicked(e -> {
                    if (e.getTarget() == modalOverlay) {
                        closeModal.run();
                    }
                });

                buttonBox.getChildren().addAll(confirmBtn, cancelBtn);

                dialogBox.getChildren().addAll(
                        titleLabel,
                        usernameLabel,
                        previewStack,
                        messageLabel,
                        buttonBox
                );

                modalOverlay.getChildren().add(dialogBox);
                StackPane.setAlignment(dialogBox, Pos.CENTER);

                // Add modal to the scene
                rootPane.getChildren().add(modalOverlay);
            });
        }, table);
    }

    private void toggleBanUser(String username, boolean ban, TableView<JsonObject> table) {
        if (username.equalsIgnoreCase("admin")) {
            showCustomErrorDialog("Cannot Ban Admin", "You cannot ban the admin account!");
            isModalDialogOpen = false;
            return;
        }

        // Validate username exists before proceeding
        validateUsernameBeforeAction(username, () -> {
            Platform.runLater(() -> {
                if (ban) {
                    showBanReasonDialog(username, table);
                } else {
                    // Unban confirmation
                    boolean confirmed = showCustomConfirmDialog(
                            "Confirm User Unban",
                            "Unban User Account",
                            "Unban user \"" + username + "\"?\n\n" +
                                    "This user will regain access to the game.\n" +
                                    "All their progress and data will be restored.\n\n" +
                                    "They will be able to login immediately.",
                            "YES, UNBAN",
                            "Cancel"
                    );

                    if (confirmed) {
                        executeBanAction(username, false, null, table);
                    } else {
                        isModalDialogOpen = false; // Reset if cancelled
                    }
                }
            });
        }, table);
    }

    private void showBanReasonDialog(String username, TableView<JsonObject> table) {
        // Create modal overlay
        StackPane modalOverlay = new StackPane();
        modalOverlay.setStyle("-fx-background-color: rgba(0, 0, 0, 0.7);");
        modalOverlay.setPrefSize(userManagementStage.getWidth(), userManagementStage.getHeight());

        // Dialog container
        VBox dialogBox = new VBox(20);
        dialogBox.setPadding(new Insets(30));
        dialogBox.setAlignment(Pos.CENTER);
        dialogBox.setMaxWidth(550);
        dialogBox.setMaxHeight(380);
        dialogBox.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #1a1a2e, #16213e);" +
                        "-fx-background-radius: 20;" +
                        "-fx-effect: dropshadow(gaussian, rgba(255, 51, 102, 0.5), 30, 0.5, 0, 0);"
        );

        Label titleLabel = new Label("⚠ Ban User: " + username);
        titleLabel.setStyle(
                "-fx-font-size: 22; " +
                        "-fx-font-weight: bold; " +
                        "-fx-text-fill: #ff3366;" +
                        "-fx-effect: dropshadow(gaussian, rgba(255, 51, 102, 0.4), 5, 0.6, 0, 0);"
        );

        Label instructionLabel = new Label("Enter reason for ban (max 100 characters):");
        instructionLabel.setStyle("-fx-font-size: 14; -fx-text-fill: #ffffff;");
        instructionLabel.setWrapText(true);

        // Character counter label
        Label charCountLabel = new Label("0/100");
        charCountLabel.setStyle("-fx-font-size: 12; -fx-text-fill: #00ffff;");

        TextArea reasonArea = new TextArea();
        reasonArea.setPromptText("e.g., Cheating, Inappropriate behavior...");
        reasonArea.setPrefRowCount(4);
        reasonArea.setPrefWidth(450);
        reasonArea.setWrapText(true);
        reasonArea.setStyle(
                "-fx-control-inner-background: #ffffff; " +
                        "-fx-background-color: #ffffff; " +
                        "-fx-text-fill: #000000; " +
                        "-fx-prompt-text-fill: #888888; " +
                        "-fx-font-size: 14px; " +
                        "-fx-padding: 10; " +
                        "-fx-background-radius: 8; " +
                        "-fx-border-color: #ff3366; " +
                        "-fx-border-width: 2; " +
                        "-fx-border-radius: 8;"
        );

        // Add character limit listener
        reasonArea.textProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal.length() > 100) {
                reasonArea.setText(oldVal);
            } else {
                charCountLabel.setText(newVal.length() + "/100");
                // Change color when approaching limit
                if (newVal.length() >= 90) {
                    charCountLabel.setStyle("-fx-font-size: 12; -fx-text-fill: #ff4444; -fx-font-weight: bold;");
                } else if (newVal.length() >= 70) {
                    charCountLabel.setStyle("-fx-font-size: 12; -fx-text-fill: #ffaa00;");
                } else {
                    charCountLabel.setStyle("-fx-font-size: 12; -fx-text-fill: #00ffff;");
                }
            }
        });

        HBox buttonBox = new HBox(15);
        buttonBox.setAlignment(Pos.CENTER);

        Button confirmBtn = new Button("🚫 Ban User");
        confirmBtn.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #ff4444, #cc0000); " +
                        "-fx-text-fill: white; " +
                        "-fx-font-weight: bold; " +
                        "-fx-padding: 12 35; " +
                        "-fx-background-radius: 10; " +
                        "-fx-cursor: hand; " +
                        "-fx-font-size: 14;"
        );
        confirmBtn.setOnMouseEntered(e -> confirmBtn.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #ff6666, #ee2222); " +
                        "-fx-text-fill: white; " +
                        "-fx-font-weight: bold; " +
                        "-fx-padding: 12 35; " +
                        "-fx-background-radius: 10; " +
                        "-fx-cursor: hand; " +
                        "-fx-font-size: 14;"
        ));
        confirmBtn.setOnMouseExited(e -> confirmBtn.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #ff4444, #cc0000); " +
                        "-fx-text-fill: white; " +
                        "-fx-font-weight: bold; " +
                        "-fx-padding: 12 35; " +
                        "-fx-background-radius: 10; " +
                        "-fx-cursor: hand; " +
                        "-fx-font-size: 14;"
        ));

        Button cancelBtn = new Button("Cancel");
        cancelBtn.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #666666, #444444); " +
                        "-fx-text-fill: white; " +
                        "-fx-font-weight: bold; " +
                        "-fx-padding: 12 35; " +
                        "-fx-background-radius: 10; " +
                        "-fx-cursor: hand; " +
                        "-fx-font-size: 14;"
        );
        cancelBtn.setOnMouseEntered(e -> cancelBtn.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #888888, #666666); " +
                        "-fx-text-fill: white; " +
                        "-fx-font-weight: bold; " +
                        "-fx-padding: 12 35; " +
                        "-fx-background-radius: 10; " +
                        "-fx-cursor: hand; " +
                        "-fx-font-size: 14;"
        ));
        cancelBtn.setOnMouseExited(e -> cancelBtn.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #666666, #444444); " +
                        "-fx-text-fill: white; " +
                        "-fx-font-weight: bold; " +
                        "-fx-padding: 12 35; " +
                        "-fx-background-radius: 10; " +
                        "-fx-cursor: hand; " +
                        "-fx-font-size: 14;"
        ));

        // Get the root pane of userManagementStage
        Scene userMgmtScene = userManagementStage.getScene();
        javafx.scene.Parent rootParent = userMgmtScene.getRoot();

        // The root should be a StackPane based on showUserManagementDialog structure
        if (!(rootParent instanceof StackPane)) {
            System.err.println("Root is not a StackPane, cannot show modal dialog");
            return;
        }

        StackPane rootPane = (StackPane) rootParent;

        // Close modal handler
        Runnable closeModal = () -> {
            rootPane.getChildren().remove(modalOverlay);
            isModalDialogOpen = false; // Reset flag
        };

        cancelBtn.setOnAction(e -> closeModal.run());

        confirmBtn.setOnAction(e -> {
            String reason = reasonArea.getText().trim();
            if (reason.isEmpty()) {
                reason = "No reason provided";
            }

            closeModal.run();

            // Show confirmation dialog
            boolean confirmed = showCustomConfirmDialog(
                    "Confirm User Ban",
                    "Ban User Account",
                    "Ban user \"" + username + "\"?\n\n" +
                            "Reason: " + reason + "\n\n" +
                            "This user will be unable to login to the game.\n" +
                            "Their progress and data will remain intact.\n\n" +
                            "You can unban them later if needed.",
                    "YES, BAN",
                    "Cancel"
            );

            if (confirmed) {
                executeBanAction(username, true, reason, table);
            } else {
                isModalDialogOpen = false; // Reset if user cancels confirmation
            }
        });

        // Allow clicking outside to close
        modalOverlay.setOnMouseClicked(e -> {
            if (e.getTarget() == modalOverlay) {
                closeModal.run();
            }
        });

        buttonBox.getChildren().addAll(confirmBtn, cancelBtn);
        dialogBox.getChildren().addAll(titleLabel, instructionLabel, reasonArea, charCountLabel, buttonBox);

        modalOverlay.getChildren().add(dialogBox);
        StackPane.setAlignment(dialogBox, Pos.CENTER);

        // Add modal to the scene
        rootPane.getChildren().add(modalOverlay);

        // Focus on text area
        Platform.runLater(() -> reasonArea.requestFocus());
    }

    private void executeBanAction(String username, boolean ban, String banReason, TableView<JsonObject> table) {
        executor.submit(() -> {
            try {
                JsonObject payload = new JsonObject();
                payload.addProperty("banned", ban);

                if (ban && banReason != null) {
                    payload.addProperty("ban_reason", banReason);
                } else if (!ban) {
                    payload.add("ban_reason", null); // Clear ban reason when unbanning
                }

                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(SUPABASE_URL + "/rest/v1/profiles?username=eq." + username))
                        .header("apikey", SUPABASE_ANON_KEY)
                        .header("Authorization", "Bearer " + SUPABASE_ANON_KEY)
                        .header("Content-Type", "application/json")
                        .method("PATCH", HttpRequest.BodyPublishers.ofString(payload.toString()))
                        .build();

                HttpResponse<String> resp = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

                // In your executeBanAction() method, find this section:

                Platform.runLater(() -> {
                    if (resp.statusCode() == 204 || resp.statusCode() == 200) {
                        showCustomSuccessDialog(
                                ban ? "User Banned Successfully" : "User Unbanned Successfully",
                                ban ? "User \"" + username + "\" has been banned.\n\nReason: " + banReason + "\n\nThey will no longer be able to access the game."
                                        : "User \"" + username + "\" has been unbanned.\n\nThey can now login and play again."
                        );

                        // Refresh the row in the table
                        table.getItems().forEach(user -> {
                            if (user.get("username").getAsString().equals(username)) {
                                user.addProperty("banned", ban);
                                if (ban && banReason != null) {
                                    user.addProperty("ban_reason", banReason);
                                } else if (!ban) {
                                    user.add("ban_reason", null);
                                }
                            }
                        });
                        table.refresh();

                        // Invalidate leaderboard cache so banned players are hidden immediately
                        invalidateLeaderboardCache();

                        // If leaderboard dialog is currently open, refresh it in real-time
                        if (leaderboardStage != null && leaderboardStage.isShowing()) {
                            refreshLeaderboardData(() -> {
                                lastLeaderboardFetch = System.currentTimeMillis();
                                Platform.runLater(() -> {
                                    leaderboardData.stream()
                                            .limit(20)
                                            .map(p -> p.username)
                                            .forEach(name -> getProfileImageForUser(name));

                                    leaderboardTable.setItems(FXCollections.observableArrayList(leaderboardData));
                                    leaderboardTable.setPlaceholder(new Label("No players yet..."));
                                    leaderboardTable.scrollTo(0);
                                    leaderboardTable.refresh();

                                    System.out.println("[LEADERBOARD] Refreshed after " +
                                            (ban ? "banning" : "unbanning") + " user: " + username);
                                });
                            });
                        }

                        // ============ HANDLE APPEALS ON UNBAN (BEST PRACTICE) ============
                        if (!ban) {
                            // When unbanning, close any pending/approved appeals as "resolved"
                            // This follows industry standard: appeal is resolved via admin action
                            closeAppealsOnUnban(username, () -> {
                                // Callback: refresh appeals dialog AFTER database update completes
                                if (banAppealsStage != null && banAppealsStage.isShowing()) {
                                    Platform.runLater(() -> loadAppealsPage());
                                }
                                // ADD THIS LINE - Reset modal flag after appeals are processed
                                isModalDialogOpen = false;
                            });

                            // If ban appeals dialog is open, refresh it to show updated status
                            if (banAppealsStage != null && banAppealsStage.isShowing()) {
                                Platform.runLater(() -> loadAppealsPage());
                            }
                        } else {
                            // ADD THIS ELSE BLOCK - Reset modal flag immediately for bans (no async appeals processing)
                            isModalDialogOpen = false;
                        }
                    } else {
                        new Alert(Alert.AlertType.ERROR,
                                "Failed (" + resp.statusCode() + "):\n" + resp.body()).show();
                        isModalDialogOpen = false; // This one was already there
                    }
                });
            } catch (Exception ex) {
                Platform.runLater(() -> new Alert(Alert.AlertType.ERROR,
                        "Error: " + ex.getMessage()).show());
                isModalDialogOpen = false;
            }
        });
    }

    private void closeAppealsOnUnban(String username, Runnable onComplete) {
        executor.submit(() -> {
            try {
                // Check if user has ANY appeals (pending, approved, OR rejected)
                // FIXED: Query profiles table, not ban_appeals
                HttpRequest checkRequest = HttpRequest.newBuilder()
                        .uri(URI.create(SUPABASE_URL + "/rest/v1/profiles?username=eq." + username +
                                "&appeal_status=in.(pending,approved,rejected)&select=*"))
                        .header("apikey", SUPABASE_ANON_KEY)
                        .header("Authorization", "Bearer " + SUPABASE_ANON_KEY)
                        .GET()
                        .build();

                HttpResponse<String> checkResp = httpClient.send(checkRequest, HttpResponse.BodyHandlers.ofString());

                if (checkResp.statusCode() == 200) {
                    JsonArray appeals = new com.google.gson.Gson().fromJson(checkResp.body(), JsonArray.class);

                    if (appeals.size() > 0) {
                        // Mark as "resolved" - the proper status for admin-unbanned users
                        String newStatus = "resolved";

                        JsonObject updatePayload = new JsonObject();
                        updatePayload.addProperty("appeal_status", newStatus);

                        // FIXED: Update profiles table, not ban_appeals
                        HttpRequest updateRequest = HttpRequest.newBuilder()
                                .uri(URI.create(SUPABASE_URL + "/rest/v1/profiles?username=eq." + username +
                                        "&appeal_status=in.(pending,approved,rejected)"))
                                .header("apikey", SUPABASE_ANON_KEY)
                                .header("Authorization", "Bearer " + SUPABASE_ANON_KEY)
                                .header("Content-Type", "application/json")
                                .method("PATCH", HttpRequest.BodyPublishers.ofString(updatePayload.toString()))
                                .build();

                        HttpResponse<String> updateResp = httpClient.send(updateRequest,
                                HttpResponse.BodyHandlers.ofString());

                        if (updateResp.statusCode() == 204 || updateResp.statusCode() == 200) {
                            System.out.println("[APPEALS] Marked " + appeals.size() +
                                    " appeal(s) as RESOLVED for unbanned user: " + username);

                            // Wait a moment for database to propagate, then run callback
                            Thread.sleep(500);
                            if (onComplete != null) {
                                onComplete.run();
                            }
                        } else {
                            System.err.println("[APPEALS] Failed to update appeals: " + updateResp.body());
                            if (onComplete != null) {
                                onComplete.run();
                            }
                        }
                    } else {
                        System.out.println("[APPEALS] No active appeals found for: " + username);
                        if (onComplete != null) {
                            onComplete.run();
                        }
                    }
                } else {
                    if (onComplete != null) {
                        onComplete.run();
                    }
                }
            } catch (Exception ex) {
                System.err.println("[APPEALS] Error closing appeals for " + username + ": " + ex.getMessage());
                // Still run callback even on error
                if (onComplete != null) {
                    onComplete.run();
                }
            }
        });
    }

    private void deleteUser(String username, TableView<JsonObject> table) {
        if (username.equalsIgnoreCase("admin")) {
            new Alert(AlertType.ERROR, "Cannot delete the admin account!").show();
            isModalDialogOpen = false;
            return;
        }

        // Validate username exists before showing confirmation
        validateUsernameBeforeAction(username, () -> {
            Platform.runLater(() -> {
                boolean confirmed = showCustomConfirmDialog(
                        "Confirm Account Deletion",
                        "DELETE USER ACCOUNT",
                        "PERMANENTLY delete user \"" + username + "\"?\n\n" +
                                "This will erase:\n" +
                                "• User profile and credentials\n" +
                                "• All progress and unlocked levels\n" +
                                "• All leaderboard records\n\n" +
                                "⚠ THIS ACTION CANNOT BE UNDONE ⚠\n\n" +
                                "The username will become available for registration again.",
                        "YES, DELETE FOREVER",
                        "Cancel"
                );

                if (!confirmed) {
                    isModalDialogOpen = false; // Reset if cancelled
                    return;
                }

                executor.submit(() -> {
                    try {
                        // DELETE LEADERBOARD
                        HttpRequest delLb = HttpRequest.newBuilder()
                                .uri(URI.create(SUPABASE_URL + "/rest/v1/leaderboard?username=eq." + username))
                                .header("apikey", SUPABASE_ANON_KEY)
                                .header("Authorization", "Bearer " + SUPABASE_ANON_KEY)
                                .header("Prefer", "return=minimal")
                                .DELETE()
                                .build();
                        HttpResponse<String> lbResp = httpClient.send(delLb, HttpResponse.BodyHandlers.ofString());
                        System.out.println("[DELETE] Leaderboard: " + lbResp.statusCode());

                        // DELETE PROFILE
                        HttpRequest delProf = HttpRequest.newBuilder()
                                .uri(URI.create(SUPABASE_URL + "/rest/v1/profiles?username=eq." + username))
                                .header("apikey", SUPABASE_ANON_KEY)
                                .header("Authorization", "Bearer " + SUPABASE_ANON_KEY)
                                .header("Prefer", "return=minimal")
                                .DELETE()
                                .build();
                        HttpResponse<String> profResp = httpClient.send(delProf, HttpResponse.BodyHandlers.ofString());
                        System.out.println("[DELETE] Profile: " + profResp.statusCode());

                        Platform.runLater(() -> {
                            if (profResp.statusCode() == 204) {
                                showCustomSuccessDialog(
                                        "Account Deleted Successfully",
                                        "User \"" + username + "\" has been permanently deleted.\n\n" +
                                                "All associated data has been erased from the system."
                                );

                                isModalDialogOpen = false;

                                table.getItems().removeIf(user ->
                                        user.get("username").getAsString().equals(username));
                                refreshUserList(table);
                            } else {
                                new Alert(AlertType.ERROR, "Delete failed: " + profResp.statusCode()).show();
                                isModalDialogOpen = false;
                            }
                        });

                    } catch (Exception e) {
                        e.printStackTrace();
                        Platform.runLater(() -> new Alert(AlertType.ERROR, "Error: " + e.getMessage()).show());
                    }
                });
            });
        }, table);
    }

    private boolean showCustomConfirmDialog(String title, String header, String message, String confirmText, String cancelText) {
        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.initOwner(primaryStage);
        dialogStage.setTitle(title);
        dialogStage.setResizable(false);

        // Create animated background - INCREASED HEIGHT
        Canvas bgCanvas = new Canvas(600, 480); // Changed from 380 to 480
        GraphicsContext gc = bgCanvas.getGraphicsContext2D();

        AnimationTimer bgAnim = new AnimationTimer() {
            private long lastUpdate = 0;
            private double time = 0;

            @Override
            public void handle(long now) {
                if (lastUpdate == 0) {
                    lastUpdate = now;
                    return;
                }

                double delta = (now - lastUpdate) / 1_000_000_000.0;
                lastUpdate = now;
                time += delta * 0.3;

                double w = bgCanvas.getWidth();
                double h = bgCanvas.getHeight();

                // Dark gradient background
                gc.setFill(new LinearGradient(0, 0, 0, h, false,
                        CycleMethod.NO_CYCLE,
                        new Stop(0, Color.rgb(10, 10, 20, 1.0)),
                        new Stop(1, Color.rgb(25, 15, 30, 1.0))));
                gc.fillRect(0, 0, w, h);

                // Animated grid lines
                gc.setStroke(Color.rgb(255, 51, 102, 0.15));
                gc.setLineWidth(1);
                for (int i = 0; i < 10; i++) {
                    double y = (h / 10) * i;
                    double offset = Math.sin(time + i * 0.5) * 5;
                    gc.strokeLine(0, y + offset, w, y + offset);
                }

                gc.setStroke(Color.rgb(102, 51, 255, 0.1));
                for (int i = 0; i < 15; i++) {
                    double x = (w / 15) * i;
                    double offset = Math.cos(time + i * 0.3) * 5;
                    gc.strokeLine(x + offset, 0, x + offset, h);
                }

                // Pulsing corner glows
                double pulse = Math.abs(Math.sin(time));
                gc.setFill(new RadialGradient(0, 0, 0, 0, 100, false,
                        CycleMethod.NO_CYCLE,
                        new Stop(0, Color.rgb(255, 51, 102, 0.2 * pulse)),
                        new Stop(1, Color.TRANSPARENT)));
                gc.fillOval(-50, -50, 150, 150);
                gc.fillOval(w - 100, h - 100, 150, 150);
            }
        };
        bgAnim.start();

        // Content container
        VBox contentBox = new VBox(20);
        contentBox.setAlignment(Pos.CENTER);
        contentBox.setPadding(new Insets(30));
        contentBox.setMaxWidth(550);

        // Header with icon and glow
        HBox headerBox = new HBox(15);
        headerBox.setAlignment(Pos.CENTER);

        // Warning icon
        Label iconLabel = new Label("⚠");
        iconLabel.setStyle("-fx-font-size: 48; -fx-text-fill: #ff3366;");
        DropShadow iconGlow = new DropShadow();
        iconGlow.setColor(Color.rgb(255, 51, 102, 0.8));
        iconGlow.setRadius(20);
        iconLabel.setEffect(iconGlow);

        // Pulsing animation for icon
        Timeline iconPulse = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(iconGlow.radiusProperty(), 20)),
                new KeyFrame(Duration.seconds(1), new KeyValue(iconGlow.radiusProperty(), 30)),
                new KeyFrame(Duration.seconds(2), new KeyValue(iconGlow.radiusProperty(), 20))
        );
        iconPulse.setCycleCount(Timeline.INDEFINITE);
        iconPulse.play();

        // Header text
        Label headerLabel = new Label(header);
        headerLabel.setStyle("-fx-font-size: 26; -fx-font-weight: bold; -fx-text-fill: #ff3366; -fx-font-family: 'Arial Black';"); // Slightly smaller font
        headerLabel.setWrapText(true);
        headerLabel.setMaxWidth(450);
        headerLabel.setAlignment(Pos.CENTER);

        headerBox.getChildren().addAll(iconLabel, headerLabel);

        // Message text
        Label messageLabel = new Label(message);
        messageLabel.setStyle("-fx-font-size: 14; -fx-text-fill: #ffffff; -fx-line-spacing: 4; -fx-font-family: 'Arial'; -fx-text-alignment: left;"); // Slightly smaller
        messageLabel.setWrapText(true);
        messageLabel.setMaxWidth(520);
        messageLabel.setAlignment(Pos.TOP_LEFT);
        messageLabel.setTextAlignment(TextAlignment.LEFT);

        // Separator
        Separator separator = new Separator(Orientation.HORIZONTAL);
        separator.setMaxWidth(500);
        separator.setStyle("-fx-background-color: #ff3366; -fx-border-color: #ff3366;");

        // Buttons container
        HBox buttonBox = new HBox(15);
        buttonBox.setAlignment(Pos.CENTER);

        // Result holder
        final boolean[] result = {false};

        // Cancel button
        Button cancelBtn = new Button(cancelText);
        cancelBtn.setPrefWidth(180);
        cancelBtn.setPrefHeight(45);
        cancelBtn.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #666666, #444444); " +
                        "-fx-text-fill: #ffffff; -fx-font-family: 'Arial Black'; " +
                        "-fx-font-size: 14; -fx-font-weight: bold; -fx-background-radius: 12; " + // Slightly smaller font
                        "-fx-cursor: hand;"
        );

        DropShadow cancelShadow = new DropShadow();
        cancelShadow.setColor(Color.rgb(0, 0, 0, 0.5));
        cancelShadow.setRadius(8);
        cancelBtn.setEffect(cancelShadow);

        cancelBtn.setOnMouseEntered(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(100), cancelBtn);
            st.setToX(1.05);
            st.setToY(1.05);
            st.play();
        });

        cancelBtn.setOnMouseExited(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(100), cancelBtn);
            st.setToX(1.0);
            st.setToY(1.0);
            st.play();
        });

        cancelBtn.setOnAction(e -> {
            bgAnim.stop();
            iconPulse.stop();
            dialogStage.close();
            isModalDialogOpen = false;
        });

        // Confirm button
        Button confirmBtn = new Button(confirmText);
        confirmBtn.setPrefWidth(200); // Increased width to fit "YES, DELETE FOREVER"
        confirmBtn.setPrefHeight(45);
        confirmBtn.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #ff4444, #cc0000); " +
                        "-fx-text-fill: #ffffff; -fx-font-family: 'Arial Black'; " +
                        "-fx-font-size: 13; -fx-font-weight: bold; -fx-background-radius: 12; " + // Slightly smaller font
                        "-fx-cursor: hand;"
        );

        DropShadow confirmShadow = new DropShadow();
        confirmShadow.setColor(Color.rgb(255, 0, 0, 0.6));
        confirmShadow.setRadius(10);
        confirmBtn.setEffect(confirmShadow);

        confirmBtn.setOnMouseEntered(e -> {
            confirmBtn.setStyle(
                    "-fx-background-color: linear-gradient(to bottom, #ff6666, #ee2222); " +
                            "-fx-text-fill: #ffffff; -fx-font-family: 'Arial Black'; " +
                            "-fx-font-size: 13; -fx-font-weight: bold; -fx-background-radius: 12; " +
                            "-fx-cursor: hand;"
            );
            ScaleTransition st = new ScaleTransition(Duration.millis(100), confirmBtn);
            st.setToX(1.05);
            st.setToY(1.05);
            st.play();
            confirmShadow.setRadius(15);
        });

        confirmBtn.setOnMouseExited(e -> {
            confirmBtn.setStyle(
                    "-fx-background-color: linear-gradient(to bottom, #ff4444, #cc0000); " +
                            "-fx-text-fill: #ffffff; -fx-font-family: 'Arial Black'; " +
                            "-fx-font-size: 13; -fx-font-weight: bold; -fx-background-radius: 12; " +
                            "-fx-cursor: hand;"
            );
            ScaleTransition st = new ScaleTransition(Duration.millis(100), confirmBtn);
            st.setToX(1.0);
            st.setToY(1.0);
            st.play();
            confirmShadow.setRadius(10);
        });

        confirmBtn.setOnAction(e -> {
            result[0] = true;
            bgAnim.stop();
            iconPulse.stop();
            dialogStage.close();
        });

        buttonBox.getChildren().addAll(cancelBtn, confirmBtn);

        contentBox.getChildren().addAll(headerBox, messageLabel, separator, buttonBox);

        // Background panel with glow - INCREASED HEIGHT
        Rectangle bgRect = new Rectangle(600, 480); // Changed from 380 to 480
        bgRect.setFill(Color.rgb(0, 0, 0, 0.85));
        bgRect.setArcWidth(0);
        bgRect.setArcHeight(0);

        DropShadow panelGlow = new DropShadow();
        panelGlow.setColor(Color.rgb(255, 51, 102, 0.5));
        panelGlow.setRadius(20);
        bgRect.setEffect(panelGlow);

        StackPane contentStack = new StackPane();
        contentStack.getChildren().addAll(bgRect, contentBox);

        StackPane root = new StackPane();
        root.getChildren().addAll(bgCanvas, contentStack);
        root.setStyle("-fx-background-color: transparent;");

        // Entrance animation
        contentStack.setOpacity(0);
        contentStack.setScaleX(0.8);
        contentStack.setScaleY(0.8);

        Timeline entrance = new Timeline(
                new KeyFrame(Duration.ZERO,
                        new KeyValue(contentStack.opacityProperty(), 0),
                        new KeyValue(contentStack.scaleXProperty(), 0.8),
                        new KeyValue(contentStack.scaleYProperty(), 0.8)),
                new KeyFrame(Duration.millis(300),
                        new KeyValue(contentStack.opacityProperty(), 1, Interpolator.EASE_OUT),
                        new KeyValue(contentStack.scaleXProperty(), 1.0, Interpolator.EASE_OUT),
                        new KeyValue(contentStack.scaleYProperty(), 1.0, Interpolator.EASE_OUT))
        );
        entrance.play();

        Scene scene = new Scene(root, 600, 500); // Changed from 400 to 500
        scene.setFill(Color.TRANSPARENT);
        dialogStage.initStyle(StageStyle.TRANSPARENT);
        dialogStage.setScene(scene);

        // ADD THIS CENTERING CODE:
        dialogStage.setOnShown(e -> {
            double x = primaryStage.getX() + (primaryStage.getWidth() - dialogStage.getWidth()) / 2;
            double y = primaryStage.getY() + (primaryStage.getHeight() - dialogStage.getHeight()) / 2;
            dialogStage.setX(x);
            dialogStage.setY(y);
        });

        dialogStage.showAndWait();

        if (!result[0]) {
            isModalDialogOpen = false;
        }

        return result[0];
    }

    // Example usage - replace your existing deleteUserLeaderboard confirmation:
    private void deleteUserLeaderboard(String username, TableView<JsonObject> table) {
        if (username.equalsIgnoreCase("admin")) {
            new Alert(AlertType.ERROR, "Cannot reset admin progress!").show();
            isModalDialogOpen = false; // Reset flag
            return;
        }

        validateUsernameBeforeAction(username, () -> {
            Platform.runLater(() -> {
                boolean confirmed = showCustomConfirmDialog(
                        "Confirm Progress Reset",
                        "Reset Player Progress",
                        "Reset ALL progress for \"" + username + "\"?\n\n" +
                                "This will delete all leaderboard records and reset unlocked levels to Level 1.\n" +
                                "The user account will remain active.\n\n" +
                                "This action cannot be undone.",
                        "YES, RESET",
                        "Cancel"
                );

                if (!confirmed) {
                    isModalDialogOpen = false; // Reset if cancelled
                    return;
                }

                executor.submit(() -> {
                    try {
                        // Delete leaderboard records
                        HttpRequest delLb = HttpRequest.newBuilder()
                                .uri(URI.create(SUPABASE_URL + "/rest/v1/leaderboard?username=eq." + username))
                                .header("apikey", SUPABASE_ANON_KEY)
                                .header("Authorization", "Bearer " + SUPABASE_ANON_KEY)
                                .header("Prefer", "return=minimal")
                                .DELETE()
                                .build();

                        HttpResponse<String> lbResp = httpClient.send(delLb, HttpResponse.BodyHandlers.ofString());

                        // Reset unlocked levels to 1
                        String resetJson = "{\"unlocked_levels\": \"1\"}";
                        HttpRequest resetLevels = HttpRequest.newBuilder()
                                .uri(URI.create(SUPABASE_URL + "/rest/v1/profiles?username=eq." + username))
                                .header("apikey", SUPABASE_ANON_KEY)
                                .header("Authorization", "Bearer " + SUPABASE_ANON_KEY)
                                .header("Content-Type", "application/json")
                                .header("Prefer", "return=minimal")
                                .method("PATCH", HttpRequest.BodyPublishers.ofString(resetJson))
                                .build();

                        HttpResponse<String> levelResp = httpClient.send(resetLevels, HttpResponse.BodyHandlers.ofString());

                        Platform.runLater(() -> {
                            if (lbResp.statusCode() == 204 && levelResp.statusCode() == 204) {
                                showCustomSuccessDialog("Progress Reset Complete",
                                        "Successfully reset progress for\n\"" + username + "\"!");
                                isModalDialogOpen = false;

                                // Update table locally
                                table.getItems().forEach(user -> {
                                    if (user.get("username").getAsString().equals(username)) {
                                        user.addProperty("unlocked_levels", "1");
                                    }
                                });
                                table.refresh();

                                // Invalidate caches
                                invalidateLeaderboardCache();

                                // Refresh leaderboard if open
                                if (leaderboardStage != null && leaderboardStage.isShowing()) {
                                    refreshLeaderboardImmediately();
                                }
                            } else {
                                new Alert(AlertType.ERROR, "Reset failed!").show();
                                isModalDialogOpen = false;
                            }
                        });

                    } catch (Exception e) {
                        Platform.runLater(() -> new Alert(AlertType.ERROR, "Error: " + e.getMessage()).show());
                    }
                });
            });
        }, table);
    }

    // Bonus: Success dialog variant
    private void showCustomSuccessDialog(String title, String message) {
        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.initOwner(primaryStage);
        dialogStage.setTitle(title);
        dialogStage.setResizable(false);

        VBox contentBox = new VBox(20);
        contentBox.setAlignment(Pos.CENTER);
        contentBox.setPadding(new Insets(40));
        contentBox.setStyle("-fx-background-color: linear-gradient(to bottom, #0a1a0a, #1a2a1a); -fx-background-radius: 0;");

        Label iconLabel = new Label("✓");
        iconLabel.setStyle("-fx-font-size: 64; -fx-text-fill: #44ff44;");

        DropShadow glow = new DropShadow();
        glow.setColor(Color.rgb(68, 255, 68, 0.8));
        glow.setRadius(30);
        iconLabel.setEffect(glow);

        Label messageLabel = new Label(message);
        messageLabel.setStyle("-fx-font-size: 16; -fx-text-fill: #ffffff; -fx-text-alignment: center;");
        messageLabel.setWrapText(true);
        messageLabel.setMaxWidth(600);
        messageLabel.setAlignment(Pos.CENTER);
        messageLabel.setMinHeight(150);
        messageLabel.setPrefHeight(Region.USE_COMPUTED_SIZE);

        Button okBtn = new Button("OK");
        okBtn.setPrefWidth(150);
        okBtn.setPrefHeight(40);
        okBtn.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #44ff44, #00cc00); " +
                        "-fx-text-fill: #ffffff; -fx-font-weight: bold; -fx-font-size: 14; " +
                        "-fx-background-radius: 0; -fx-cursor: hand;"
        );
        okBtn.setOnAction(e -> dialogStage.close());

        contentBox.getChildren().addAll(iconLabel, messageLabel, okBtn);

        Scene scene = new Scene(contentBox, 650, 450);
        dialogStage.initStyle(StageStyle.UNDECORATED);
        dialogStage.setScene(scene);

        // ADD THIS: Center the dialog on primary stage (same as confirmation dialog)
        dialogStage.setOnShown(e -> {
            double x = primaryStage.getX() + (primaryStage.getWidth() - dialogStage.getWidth()) / 2;
            double y = primaryStage.getY() + (primaryStage.getHeight() - dialogStage.getHeight()) / 2;
            dialogStage.setX(x);
            dialogStage.setY(y);
        });

        dialogStage.showAndWait();
    }

    private void invalidateLeaderboardCache() {
        lastLeaderboardFetch = 0;
        leaderboardData.clear();
        System.out.println("[CACHE] Leaderboard cache invalidated - next access will fetch fresh data");
    }

    private void refreshLeaderboardImmediately() {
        if (leaderboardTable == null) return;

        Platform.runLater(() -> {
            leaderboardTable.setPlaceholder(new Label("Refreshing..."));
            leaderboardTable.setItems(FXCollections.observableArrayList());
        });

        refreshLeaderboardData(() -> {
            lastLeaderboardFetch = System.currentTimeMillis();
            Platform.runLater(() -> {
                leaderboardData.stream()
                        .limit(20)
                        .map(p -> p.username)
                        .forEach(username -> getProfileImageForUser(username));

                leaderboardTable.setItems(FXCollections.observableArrayList(leaderboardData));
                leaderboardTable.setPlaceholder(new Label("No players yet..."));
                leaderboardTable.scrollTo(0);
                leaderboardTable.refresh();

                System.out.println("[LEADERBOARD] Immediate refresh complete - showing " + leaderboardData.size() + " players");
            });
        });
    }

    private void refreshUserList(TableView<JsonObject> table) {
        executor.submit(() -> {
            try {
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(SUPABASE_URL + "/rest/v1/profiles?select=*"))
                        .header("apikey", SUPABASE_ANON_KEY)
                        .header("Authorization", "Bearer " + SUPABASE_ANON_KEY)
                        .GET()
                        .build();

                HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

                if (response.statusCode() == 200) {
                    JsonArray array = gson.fromJson(response.body(), JsonArray.class);
                    List<JsonObject> userList = new ArrayList<>();

                    array.forEach(el -> {
                        JsonObject user = el.getAsJsonObject();
                        String username = user.get("username").getAsString();
                        if (!"admin".equalsIgnoreCase(username)) {
                            userList.add(user);
                        }
                    });

                    // Sort alphabetically by username
                    userList.sort((u1, u2) -> {
                        String name1 = u1.get("username").getAsString().toLowerCase();
                        String name2 = u2.get("username").getAsString().toLowerCase();
                        return name1.compareTo(name2);
                    });

                    Platform.runLater(() -> {
                        table.setItems(FXCollections.observableArrayList(userList));
                        table.refresh();
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void stopCurrentBackgroundAudio() {
        if (currentBackgroundAudio != null && currentBackgroundAudio.getStatus() == MediaPlayer.Status.PLAYING) {
            currentBackgroundAudio.stop();
        }
    }

    // ADD THIS METHOD — saves everything to Supabase (progress, audio settings, etc.)
    private void saveProgressToSupabase() {
        if (currentUser == null) return;

        executor.submit(() -> {
            try {
                String levelsStr = String.join(",",
                        unlockedLevels.stream()
                                .map(String::valueOf)
                                .sorted()
                                .toList());

                Map<String, Object> payload = Map.of(
                        "unlocked_levels", levelsStr,
                        "effects_muted", isEffectsMuted,
                        "music_muted", isMusicMuted,
                        "volume", audioVolume,
                        "sprite_visible", isSpriteVisible
                );
                String json = gson.toJson(payload);

                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(SUPABASE_URL + "/rest/v1/profiles?username=eq." + currentUser))
                        .header("apikey", SUPABASE_ANON_KEY)
                        .header("Authorization", "Bearer " + SUPABASE_ANON_KEY)
                        .header("Content-Type", "application/json")
                        .method("PATCH", HttpRequest.BodyPublishers.ofString(json))
                        .build();

                httpClient.send(request, HttpResponse.BodyHandlers.discarding());

            } catch (Exception e) {
                System.err.println("Failed to save progress to Supabase: " + e.getMessage());
            }
        });
    }

    public void showLeaderboardDialog() {
        // Create new Stage window if it doesn't exist
        if (leaderboardStage == null) {
            leaderboardStage = new Stage();
            leaderboardStage.initOwner(primaryStage);
            leaderboardStage.initModality(Modality.WINDOW_MODAL);
            leaderboardStage.initStyle(StageStyle.TRANSPARENT);
            leaderboardStage.setTitle("Leaderboard");
            leaderboardStage.setResizable(false);

            // Reset dialog flag when closed
            leaderboardStage.setOnHidden(e -> isDialogOpen = false);

            // Outer container with transparent background (no dimming)
            StackPane overlay = new StackPane();
            overlay.setStyle("-fx-background-color: transparent;");
            overlay.setPadding(new Insets(40));

            // Main dialog container
            VBox leaderboardDialog = new VBox(20);
            leaderboardDialog.setAlignment(Pos.CENTER);
            leaderboardDialog.setPadding(new Insets(30));
            leaderboardDialog.setMaxWidth(680);
            leaderboardDialog.setMaxHeight(550);
            leaderboardDialog.setStyle(
                    "-fx-background-color: linear-gradient(to bottom, #1a1a2e, #16213e, #0f3460);" +
                            "-fx-background-radius: 20;" +
                            "-fx-effect: dropshadow(gaussian, rgba(0, 217, 255, 0.4), 25, 0.5, 0, 0);"
            );

            // Close button with hover effects
            Button closeButton = new Button("✕");
            closeButton.setStyle(
                    "-fx-background-color: rgba(255, 68, 68, 0.2);" +
                            "-fx-text-fill: #FF4444;" +
                            "-fx-font-family: 'Arial';" +
                            "-fx-font-size: 18;" +
                            "-fx-font-weight: bold;" +
                            "-fx-background-radius: 20;" +
                            "-fx-padding: 8 12;" +
                            "-fx-border-color: #FF4444;" +
                            "-fx-border-radius: 20;" +
                            "-fx-border-width: 2;" +
                            "-fx-cursor: hand;"
            );
            closeButton.setOnMouseEntered(e -> closeButton.setStyle(
                    "-fx-background-color: #FF4444;" +
                            "-fx-text-fill: #FFFFFF;" +
                            "-fx-font-family: 'Arial';" +
                            "-fx-font-size: 18;" +
                            "-fx-font-weight: bold;" +
                            "-fx-background-radius: 20;" +
                            "-fx-padding: 8 12;" +
                            "-fx-border-color: #FF4444;" +
                            "-fx-border-radius: 20;" +
                            "-fx-border-width: 2;" +
                            "-fx-cursor: hand;"
            ));
            closeButton.setOnMouseExited(e -> closeButton.setStyle(
                    "-fx-background-color: rgba(255, 68, 68, 0.2);" +
                            "-fx-text-fill: #FF4444;" +
                            "-fx-font-family: 'Arial';" +
                            "-fx-font-size: 18;" +
                            "-fx-font-weight: bold;" +
                            "-fx-background-radius: 20;" +
                            "-fx-padding: 8 12;" +
                            "-fx-border-color: #FF4444;" +
                            "-fx-border-radius: 20;" +
                            "-fx-border-width: 2;" +
                            "-fx-cursor: hand;"
            ));
            closeButton.setOnAction(e -> leaderboardStage.close());

            HBox topBar = new HBox(closeButton);
            topBar.setAlignment(Pos.TOP_RIGHT);

            // Title with glow effect
            Label titleLabel = new Label("🏆 LEADERBOARD 🏆");
            titleLabel.setStyle(
                    "-fx-text-fill: #FFFFFF;" +
                            "-fx-font-family: 'Arial Black';" +
                            "-fx-font-size: 28;" +
                            "-fx-font-weight: bold;" +
                            "-fx-effect: dropshadow(gaussian, rgba(0, 217, 255, 0.4), 8, 0.6, 0, 0);"
            );

            Label subtitleLabel = new Label("Top Players of All Time");
            subtitleLabel.setStyle(
                    "-fx-text-fill: #00D9FF;" +
                            "-fx-font-family: 'Arial';" +
                            "-fx-font-size: 14;" +
                            "-fx-font-style: italic;"
            );

            VBox titleBox = new VBox(5, titleLabel, subtitleLabel);
            titleBox.setAlignment(Pos.CENTER);

            // Create the leaderboard table
            leaderboardTable = new TableView<>();
            leaderboardTable.setTableMenuButtonVisible(false);
            leaderboardTable.setSelectionModel(null);
            Label loadingLabel = new Label("Loading players...");
            loadingLabel.setStyle("-fx-text-fill: white; -fx-font-size: 14px;");
            leaderboardTable.setPlaceholder(loadingLabel);
            leaderboardTable.setStyle("-fx-background-color: rgba(58, 58, 58, 0.4); -fx-control-inner-background: transparent;");
            leaderboardTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);
            leaderboardTable.setPrefHeight(350);
            leaderboardTable.setMaxHeight(350);
            leaderboardTable.setFixedCellSize(50);

            // RANK COLUMN
            TableColumn<PlayerData, Integer> colRank = new TableColumn<>("RANK");
            colRank.setMinWidth(80);
            colRank.setMaxWidth(80);
            colRank.setReorderable(false);
            colRank.setSortable(false);
            colRank.setCellValueFactory(param -> {
                int index = leaderboardTable.getItems().indexOf(param.getValue());
                return new SimpleIntegerProperty(index + 1).asObject();
            });
            colRank.setCellFactory(col -> new TableCell<PlayerData, Integer>() {
                @Override
                protected void updateItem(Integer rank, boolean empty) {
                    super.updateItem(rank, empty);
                    if (empty || rank == null) {
                        setText(null);
                        setStyle("");
                    } else {
                        setText(String.valueOf(rank));
                        // Added #FFFFFF for white text color
                        setStyle("-fx-text-fill: #FFFFFF; -fx-font-weight: bold; -fx-font-size: 14px; -fx-alignment: CENTER; -fx-padding: 20 10 10 10;");
                    }
                }
            });


            // PLAYER COLUMN with enhanced profile pictures
            TableColumn<PlayerData, PlayerData> colPlayer = new TableColumn<>("PLAYER");
            colPlayer.setMinWidth(200);
            colPlayer.setMaxWidth(250);
            colPlayer.setReorderable(false);
            colPlayer.setSortable(false);
            colPlayer.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue()));
            colPlayer.setCellFactory(col -> new TableCell<PlayerData, PlayerData>() {
                private final Circle backgroundCircle = new Circle(18);
                private final ImageView profilePic = new ImageView();
                private final Label nameLabel = new Label();
                private final StackPane picContainer = new StackPane();
                private final HBox container = new HBox(12, picContainer, nameLabel);

                {
                    // Background circle - solid dark with slight gradient feel
                    backgroundCircle.setFill(Color.rgb(30, 35, 45));
                    backgroundCircle.setStroke(Color.rgb(60, 70, 85));
                    backgroundCircle.setStrokeWidth(1.5);

                    // Profile picture - larger and higher quality
                    profilePic.setFitWidth(36);
                    profilePic.setFitHeight(36);
                    profilePic.setPreserveRatio(true);
                    profilePic.setSmooth(true);

                    // Circular clip for profile pic
                    Circle clip = new Circle(18, 18, 18);
                    profilePic.setClip(clip);

                    // Add subtle border to profile pic
                    Circle picBorder = new Circle(18);
                    picBorder.setFill(Color.TRANSPARENT);
                    picBorder.setStroke(Color.rgb(255, 255, 255, 0.3));
                    picBorder.setStrokeWidth(1);

                    // Stack all elements
                    picContainer.getChildren().addAll(backgroundCircle, profilePic, picBorder);
                    picContainer.setAlignment(Pos.CENTER);

                    container.setAlignment(Pos.CENTER_LEFT);
                    nameLabel.setStyle("-fx-text-fill: #FFFFFF; -fx-font-size: 14px; -fx-font-weight: bold;");

                    container.setOnMouseEntered(e -> {
                        ScaleTransition st = new ScaleTransition(Duration.millis(150), picContainer);
                        st.setToX(1.15);
                        st.setToY(1.15);
                        st.play();
                    });

                    container.setOnMouseExited(e -> {
                        ScaleTransition st = new ScaleTransition(Duration.millis(150), picContainer);
                        st.setToX(1.0);
                        st.setToY(1.0);
                        st.play();
                    });
                }

                @Override
                protected void updateItem(PlayerData player, boolean empty) {
                    super.updateItem(player, empty);
                    if (empty || player == null) {
                        setGraphic(null);
                    } else {
                        // Load high-quality profile image
                        Image img = getProfileImageForUser(player.username);
                        profilePic.setImage(img != null ? img : new Image("file:resources/images/default_profile.png"));

                        nameLabel.setText(player.username);

                        int rank = leaderboardTable.getItems().indexOf(player) + 1;

                        // Rank-based styling
                        String nameColor;
                        Color glowColor;
                        Color bgColor;

                        switch (rank) {
                            case 1: // Gold
                                nameColor = "#FFD700";
                                glowColor = Color.rgb(255, 215, 0, 0.8);
                                bgColor = Color.rgb(60, 50, 20);
                                backgroundCircle.setStroke(Color.rgb(255, 215, 0, 0.6));
                                break;
                            case 2: // Silver
                                nameColor = "#C0C0C0";
                                glowColor = Color.rgb(192, 192, 192, 0.8);
                                bgColor = Color.rgb(50, 50, 55);
                                backgroundCircle.setStroke(Color.rgb(192, 192, 192, 0.6));
                                break;
                            case 3: // Bronze
                                nameColor = "#CD7F32";
                                glowColor = Color.rgb(205, 127, 50, 0.8);
                                bgColor = Color.rgb(50, 40, 30);
                                backgroundCircle.setStroke(Color.rgb(205, 127, 50, 0.6));
                                break;
                            default:
                                nameColor = "#FFFFFF";
                                glowColor = Color.rgb(0, 217, 255, 0.6);
                                bgColor = Color.rgb(30, 35, 45);
                                backgroundCircle.setStroke(Color.rgb(60, 70, 85));
                                break;
                        }


                        nameLabel.setStyle("-fx-text-fill: " + nameColor + "; -fx-font-size: 14px; -fx-font-weight: bold;");

                        setGraphic(container);
                        setStyle("-fx-alignment: CENTER_LEFT; -fx-padding: 20 10 10 10;");
                    }
                }
            });

            // LEVEL COLUMN
            TableColumn<PlayerData, Integer> colLevel = new TableColumn<>("LEVEL");
            colLevel.setMinWidth(120);
            colLevel.setMaxWidth(150);
            colLevel.setReorderable(false);
            colLevel.setSortable(false);
            colLevel.setCellValueFactory(param -> new SimpleIntegerProperty(param.getValue().highestLevel).asObject());
            colLevel.setCellFactory(col -> new TableCell<PlayerData, Integer>() {
                @Override
                protected void updateItem(Integer level, boolean empty) {
                    super.updateItem(level, empty);
                    if (empty || level == null) {
                        setText(null);
                        setStyle("");
                    } else {
                        setText("Level " + level);
                        setStyle("-fx-text-fill: #FFFFFF; -fx-font-size: 14px; -fx-alignment: CENTER; -fx-padding: 20 10 10 10;");
                    }
                }
            });

            // TIME COLUMN
            TableColumn<PlayerData, Double> colTime = new TableColumn<>("TIME (S)");
            colTime.setMinWidth(130);
            colTime.setMaxWidth(150);
            colTime.setReorderable(false);
            colTime.setSortable(false);
            colTime.setCellValueFactory(param -> new SimpleDoubleProperty(param.getValue().totalCompletionTime).asObject());
            colTime.setCellFactory(col -> new TableCell<PlayerData, Double>() {
                @Override
                protected void updateItem(Double time, boolean empty) {
                    super.updateItem(time, empty);
                    if (empty || time == null) {
                        setText(null);
                        setStyle("");
                    } else {
                        setText(String.format("%.3f s", time));
                        setStyle("-fx-text-fill: #88FF88; -fx-font-size: 14px; -fx-alignment: CENTER; -fx-padding: 20 10 10 10;");
                    }
                }
            });

            leaderboardTable.getColumns().addAll(colRank, colPlayer, colLevel, colTime);

            // Assemble the dialog
            leaderboardDialog.getChildren().addAll(topBar, titleBox, leaderboardTable);
            overlay.getChildren().add(leaderboardDialog);
            StackPane.setAlignment(leaderboardDialog, Pos.CENTER);

            // Create scene with transparent background
            Scene scene = new Scene(overlay, 760, 630);
            scene.setFill(Color.TRANSPARENT);

            // Add CSS stylesheet for table styling
            scene.getStylesheets().add("data:text/css," +
                    ".table-view { -fx-background-color: transparent; -fx-background-radius: 0; -fx-padding: 0; }" +
                    ".table-view .column-header-background { -fx-background-color: linear-gradient(to right, rgba(0, 217, 255, 0.15), rgba(138, 43, 226, 0.15)); -fx-padding: 0; }" +
                    ".table-view .column-header-background .nested-column-header { -fx-padding: 0; }" +
                    ".table-view .column-header-background .filler { -fx-background-color: white; }" +
                    ".table-view .column-header { -fx-background-color: transparent; -fx-text-fill: #00FFFF; -fx-font-weight: bold; -fx-font-size: 13px; -fx-effect: dropshadow(gaussian, rgba(0, 255, 255, 0.5), 5, 0.5, 0, 0); }" +
                    ".table-view .table-cell { -fx-border-color: transparent; -fx-background-color: transparent; -fx-padding: 15 10 10 10; }" +
                    ".table-view .table-row-cell { -fx-background-color: transparent; -fx-border-color: rgba(255, 255, 255, 0.1); -fx-border-width: 0 0 1 0; }" +
                    ".table-view .table-row-cell:odd { -fx-background-color: rgba(0, 0, 0, 0.1); }" +
                    ".table-view .table-row-cell:selected { -fx-background-color: rgba(60, 60, 60, 0.3); }" +
                    ".table-view:focused .table-row-cell:selected { -fx-background-color: rgba(60, 60, 60, 0.5); }"
            );

            leaderboardStage.setScene(scene);

            // Perfect centering - EXACTLY like your failure modal
            leaderboardStage.setOnShown(e -> {
                double x = primaryStage.getX() + (primaryStage.getWidth() - leaderboardStage.getWidth()) / 2;
                double y = primaryStage.getY() + (primaryStage.getHeight() - leaderboardStage.getHeight()) / 2;
                leaderboardStage.setX(x);
                leaderboardStage.setY(y);
            });

        } else {
            // Reset the table state before re-showing
            Label loadingLabel = new Label("Loading players...");
            loadingLabel.setStyle("-fx-text-fill: white; -fx-font-size: 14px;");
            leaderboardTable.setPlaceholder(loadingLabel);
            leaderboardTable.setItems(FXCollections.observableArrayList());
        }

        // Show the stage window
        leaderboardStage.show();
        leaderboardStage.requestFocus();

        // Load leaderboard data
        long currentTime = System.currentTimeMillis();
        boolean needsRefresh = (currentTime - lastLeaderboardFetch) > CACHE_DURATION_MS;

        if (needsRefresh || leaderboardData.isEmpty()) {
            refreshLeaderboardData(() -> {
                lastLeaderboardFetch = System.currentTimeMillis();
                Platform.runLater(() -> {
                    leaderboardData.stream()
                            .limit(20)
                            .map(p -> p.username)
                            .forEach(username -> getProfileImageForUser(username));

                    leaderboardTable.setItems(FXCollections.observableArrayList(leaderboardData));
                    Label noPlayersLabel = new Label("No players yet...");
                    noPlayersLabel.setStyle("-fx-text-fill: white; -fx-font-size: 14px;");
                    leaderboardTable.setPlaceholder(noPlayersLabel);
                    leaderboardTable.scrollTo(0);
                    leaderboardTable.refresh();
                });
            });
        } else {
            Platform.runLater(() -> {
                // Filter using cached banned users (no network calls)
                List<PlayerData> filteredData = leaderboardData.stream()
                        .filter(p -> !cachedBannedUsers.contains(p.username))
                        .collect(Collectors.toList());

                filteredData.stream()
                        .limit(20)
                        .map(p -> p.username)
                        .forEach(username -> getProfileImageForUser(username));

                leaderboardTable.setItems(FXCollections.observableArrayList(filteredData));
                Label noPlayersLabel = new Label("No players yet...");
                noPlayersLabel.setStyle("-fx-text-fill: white; -fx-font-size: 14px;");
                leaderboardTable.setPlaceholder(noPlayersLabel);
                leaderboardTable.scrollTo(0);
                leaderboardTable.refresh();
            });
        }
    }

    private Image getProfileImageForUser(String username) {
        // 1. Current user → use already-loaded high-quality image
        if (currentUser != null && currentUser.equals(username) && currentProfileImage != null) {
            return currentProfileImage;
        }

        // 2. Check cache first
        if (profilePictureCache.containsKey(username)) {
            return profilePictureCache.get(username);
        }

        // 3. Not in cache → load from Supabase asynchronously
        loadProfilePictureAsync(username, image -> {
            Platform.runLater(() -> {
                profilePictureCache.put(username, image);
                // Refresh leaderboard TableView to show newly loaded pics
                if (leaderboardStage != null && leaderboardStage.isShowing() && leaderboardTable != null) {
                    leaderboardTable.refresh();
                }
            });
        });

        // Return default while loading (will update later)
        return new Image("file:resources/images/default_profile.png");
    }

    // Keep loadProfilePictureAsync and startRealtimeProfileSync as they are - no changes needed
    private void loadProfilePictureAsync(String username, Consumer<Image> callback) {
        executor.submit(() -> {
            Image result = getDefaultProfileImage();  // Start with default

            try {
                String url = SUPABASE_URL + "/rest/v1/profiles?username=eq." + URLEncoder.encode(username, StandardCharsets.UTF_8)
                        + "&select=profile_picture,profile_picture_bytes,unlocked_levels,effects_muted,music_muted,volume,sprite_visible";

                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(url))
                        .header("apikey", SUPABASE_ANON_KEY)
                        .header("Authorization", "Bearer " + SUPABASE_ANON_KEY)
                        .timeout(java.time.Duration.ofSeconds(10))
                        .GET()
                        .build();

                HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

                if (response.statusCode() == 200 && response.body() != null && !response.body().trim().equals("[]")) {
                    JsonArray array = gson.fromJson(response.body(), JsonArray.class);
                    JsonObject data = array.get(0).getAsJsonObject();

                    String rawBase64 = null;
                    if (data.has("profile_picture_bytes") && !data.get("profile_picture_bytes").isJsonNull()) {
                        rawBase64 = data.get("profile_picture_bytes").getAsString();
                    } else if (data.has("profile_picture") && !data.get("profile_picture").isJsonNull()) {
                        rawBase64 = data.get("profile_picture").getAsString();
                    }

                    // THIS IS THE ONLY SAFE WAY — USE THE FINAL DECODER
                    result = loadProfileImageSafely(rawBase64);
                }
            } catch (Exception e) {
                System.err.println("Failed to load avatar for " + username + " (using default): " + e.getMessage());
                // Silently fail → default image
            }

            Image finalImage = result;
            Platform.runLater(() -> callback.accept(finalImage));
        });
    }

    // Keep your existing refreshLeaderboardDisplay method (the one that fills leaderboardData and rebuilds the grid)
    public void refreshLeaderboardData() {
        refreshLeaderboardData(null);
    }

    private void refreshLeaderboardData(Runnable onComplete) {
        executor.submit(() -> {
            try {
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(LEADERBOARD_GET))
                        .header("apikey", SUPABASE_ANON_KEY)
                        .header("Authorization", "Bearer " + SUPABASE_ANON_KEY)
                        .timeout(java.time.Duration.ofSeconds(15))
                        .GET()
                        .build();

                HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

                if (response.statusCode() != 200) {
                    System.err.println("Leaderboard fetch failed: HTTP " + response.statusCode() + " → " + response.body());
                    Platform.runLater(() -> {
                        if (onComplete != null) onComplete.run();
                    });
                    return;
                }

                Type type = new TypeToken<List<Map<String, Object>>>() {
                }.getType();
                List<Map<String, Object>> rows = gson.fromJson(response.body(), type);

                if (rows == null || rows.isEmpty()) {
                    Platform.runLater(() -> {
                        leaderboardData.clear();
                        System.out.println("Leaderboard is empty.");
                        if (onComplete != null) onComplete.run();
                    });
                    return;
                }

                Map<String, PlayerData> aggregated = new HashMap<>();

                for (Map<String, Object> row : rows) {
                    String username = (String) row.get("username");
                    if (username == null || username.isBlank()) continue;

                    Number levelNum = (Number) row.get("level");
                    Number timeNum = (Number) row.get("completion_time");
                    if (levelNum == null || timeNum == null) continue;

                    int level = levelNum.intValue();
                    double time = timeNum.doubleValue();

                    aggregated.compute(username, (key, existing) -> {
                        if (existing == null) {
                            return new PlayerData(username, level, time, null);
                        } else {
                            existing.highestLevel = Math.max(existing.highestLevel, level);
                            existing.totalCompletionTime += time;
                            return existing;
                        }
                    });
                }

                // **NEW: Filter out banned players**
                Set<String> bannedUsers = fetchAllBannedUsers();

// Filter out banned players using the cached set
                List<PlayerData> filtered = aggregated.values().stream()
                        .filter(player -> !bannedUsers.contains(player.username))
                        .collect(Collectors.toList());

                cachedBannedUsers.clear();
                cachedBannedUsers.addAll(bannedUsers);

// Log filtered players
                bannedUsers.forEach(username ->
                        System.out.println("Filtered banned player from leaderboard: " + username)
                );

                // Sort the filtered list
                filtered.sort((a, b) -> {
                    if (a.highestLevel != b.highestLevel) {
                        return Integer.compare(b.highestLevel, a.highestLevel);
                    }
                    return Double.compare(a.totalCompletionTime, b.totalCompletionTime);
                });

// Limit to top 100 players
                List<PlayerData> top100 = filtered.stream()
                        .limit(100)
                        .collect(Collectors.toList());

                Platform.runLater(() -> {
                    leaderboardData.clear();
                    leaderboardData.addAll(top100);
                    System.out.println("Leaderboard refreshed — " + top100.size() + " players shown (top 100, banned players hidden)");
                    if (!top100.isEmpty()) {
                        System.out.println("Top player: " + top100.get(0).username +
                                " → Level " + top100.get(0).highestLevel +
                                " in " + String.format("%.3f", top100.get(0).totalCompletionTime) + "s");
                    }

                    // Trigger callback after data is ready
                    if (onComplete != null) onComplete.run();
                });


            } catch (Exception e) {
                System.err.println("Error refreshing leaderboard: " + e.toString());
                e.printStackTrace();
                Platform.runLater(() -> {
                    if (onComplete != null) onComplete.run();
                });
            }
        });
    }

    private Set<String> fetchAllBannedUsers() {
        try {
            String url = USERS_TABLE + "?banned=eq.true&select=username";
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("apikey", SUPABASE_ANON_KEY)
                    .header("Authorization", "Bearer " + SUPABASE_ANON_KEY)
                    .timeout(java.time.Duration.ofSeconds(10))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                Type type = new TypeToken<List<Map<String, Object>>>() {}.getType();
                List<Map<String, Object>> result = gson.fromJson(response.body(), type);

                return result.stream()
                        .map(row -> (String) row.get("username"))
                        .filter(username -> username != null && !username.isBlank())
                        .collect(Collectors.toSet());
            }
        } catch (Exception e) {
            System.err.println("Error fetching banned users: " + e.getMessage());
        }

        return new HashSet<>(); // Return empty set on failure
    }

    // Tutorial step data
    private static class TutorialStep {
        String title;
        String description;
        String highlightElement; // "target", "palette", "grid", "moves", null for no highlight

        TutorialStep(String title, String description, String highlightElement) {
            this.title = title;
            this.description = description;
            this.highlightElement = highlightElement;
        }
    }

    private final TutorialStep[] tutorialSteps = {
            new TutorialStep(
                    "Welcome to Chroma Flood!",
                    "Your goal is to flood the entire grid with the TARGET COLOR.\n\nLet's learn how to play!",
                    null
            ),
            new TutorialStep(
                    "Target Color",
                    "Each level has a TARGET COLOR shown at the top.\n\nYou must flood all tiles to match this color to win the level!",
                    null
            ),
            new TutorialStep(
                    "Color Palette",
                    "Select a color from the palette by clicking on it.\n\nPress the number keys (1-4) for quick selection!",
                    "palette"
            ),
            new TutorialStep(
                    "Flood Fill Demo",
                    "Watch how clicking changes ALL CONNECTED tiles of the same color!",
                    "grid_demo"
            ),
            new TutorialStep(
                    "Strategy Tips",
                    "Pro tips from experienced players:\n\n" +
                            "• START FROM A CORNER and work across the board\n" +
                            "• CONNECT SCATTERED REGIONS before changing them\n" +
                            "• Choose colors that SPREAD DEEPEST into unflooded territory\n" +
                            "• Plan ahead - don't take the first move that comes to mind!",
                    "grid"
            ),
            new TutorialStep(
                    "Moves Counter",
                    "You have LIMITED MOVES to complete each level.\n\nUse them wisely to reach the target color!",
                    "moves"
            ),
            new TutorialStep(
                    "Ready to Play!",
                    "You're all set!\n\n✓ Pick a color from palette\n✓ Click tiles to flood fill\n✓ Expand your region each move\n✓ Match the target color\n✓ Beat all 10 levels!\n\nGood luck!",
                    null
            )
    };

    // Main method to show tutorial
    private void showTutorial() {
        showTutorial(false); // Default to automatic mode
    }

    // Overloaded method that accepts a boolean flag
    private void showTutorial(boolean isManualOpen) {
        tutorialActive = true;
        currentTutorialStep = 0;
        boolean wasManuallyOpened = isManualOpen;

        tutorialStage = new Stage();
        tutorialStage.initOwner(primaryStage);
        tutorialStage.initModality(Modality.WINDOW_MODAL);
        tutorialStage.initStyle(StageStyle.TRANSPARENT);
        tutorialStage.setUserData(wasManuallyOpened);

        // Create root container
        StackPane tutorialRoot = new StackPane();
        tutorialRoot.setStyle("-fx-background-color: transparent;");

        // Semi-transparent dark overlay
        tutorialOverlay = new Rectangle();
        tutorialOverlay.setFill(Color.rgb(0, 0, 0, 0.85));
        tutorialOverlay.widthProperty().bind(tutorialStage.widthProperty());
        tutorialOverlay.heightProperty().bind(tutorialStage.heightProperty());

        // Tutorial card container - landscape oriented
        tutorialCard = new VBox(15);
        tutorialCard.setAlignment(Pos.CENTER);
        tutorialCard.setPadding(new Insets(40, 60, 40, 60));
        tutorialCard.setMaxWidth(850);
        tutorialCard.setMinHeight(650);
        tutorialCard.setMaxHeight(650);
        tutorialCard.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #0a0e1a 0%, #1a1f35 100%);" +
                        "-fx-background-radius: 20;" +
                        "-fx-border-color: #00FFFF;" +
                        "-fx-border-width: 3;" +
                        "-fx-border-radius: 20;" +
                        "-fx-effect: dropshadow(gaussian, rgba(0,255,255,0.6), 25, 0.5, 0, 0);"
        );

        tutorialRoot.getChildren().addAll(tutorialOverlay, tutorialCard);

        Scene tutorialScene = new Scene(tutorialRoot);
        tutorialScene.setFill(Color.TRANSPARENT);
        tutorialStage.setScene(tutorialScene);

        // Size and position
        tutorialStage.setWidth(primaryStage.getWidth());
        tutorialStage.setHeight(primaryStage.getHeight());
        tutorialStage.setX(primaryStage.getX());
        tutorialStage.setY(primaryStage.getY());

        updateTutorialStep();
        tutorialStage.show();
    }

    // Update tutorial content based on current step
    private void updateTutorialStep() {
        // Stop any running animations from previous step
        if (tutorialCard.getUserData() instanceof Timeline) {
            Timeline previousAnimation = (Timeline) tutorialCard.getUserData();
            previousAnimation.stop();
            tutorialCard.setUserData(null);
        }

        tutorialCard.getChildren().clear();

        TutorialStep step = tutorialSteps[currentTutorialStep];

        // Step indicator
        Label stepIndicator = new Label("Step " + (currentTutorialStep + 1) + " of " + tutorialSteps.length);
        stepIndicator.setStyle(
                "-fx-text-fill: #00FFFF;" +
                        "-fx-font-size: 16;" +
                        "-fx-font-weight: bold;"
        );

        // Title - smaller for landscape
        Label title = new Label(step.title);
        title.setStyle(
                "-fx-text-fill: #FFFFFF;" +
                        "-fx-font-family: 'Arial Black';" +
                        "-fx-font-size: 26;" +
                        "-fx-font-weight: bold;" +
                        "-fx-effect: dropshadow(gaussian, #00FFFF, 8, 0.5, 0, 0);"
        );
        title.setWrapText(true);
        title.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        title.setAlignment(Pos.CENTER);
        title.setMaxWidth(700);

        // Description - smaller for landscape
        Label description = new Label(step.description);
        description.setStyle(
                "-fx-text-fill: #CCCCCC;" +
                        "-fx-font-size: 15;" +
                        "-fx-line-spacing: 4;"
        );
        description.setWrapText(true);
        description.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        description.setAlignment(Pos.CENTER);
        description.setMaxWidth(700);

        // Visual indicator for what's being highlighted - smaller for landscape
        VBox visualBox = new VBox(10);
        visualBox.setAlignment(Pos.CENTER);
        visualBox.setPadding(new Insets(15, 0, 15, 0));

        if (step.highlightElement != null) {
            switch (step.highlightElement) {
                case "target":
                    Circle targetDemo = new Circle(25, targetColor.getFxColor());
                    DropShadow targetGlow = new DropShadow();
                    targetGlow.setColor(targetColor.getFxColor());
                    targetGlow.setRadius(25);
                    targetGlow.setSpread(0.8);
                    targetDemo.setEffect(targetGlow);

                    // Pulse animation
                    ScaleTransition pulse = new ScaleTransition(Duration.millis(1000), targetDemo);
                    pulse.setFromX(1.0);
                    pulse.setFromY(1.0);
                    pulse.setToX(1.3);
                    pulse.setToY(1.3);
                    pulse.setAutoReverse(true);
                    pulse.setCycleCount(Animation.INDEFINITE);
                    pulse.play();

                    visualBox.getChildren().add(targetDemo);
                    break;

                case "palette":
                    HBox paletteDemo = new HBox(12);
                    paletteDemo.setAlignment(Pos.CENTER);

                    for (ColorType color : palette) {
                        Circle orb = new Circle(20, color.getFxColor());
                        orb.setEffect(new Glow(0.8));

                        ScaleTransition orbPulse = new ScaleTransition(Duration.millis(800), orb);
                        orbPulse.setFromX(1.0);
                        orbPulse.setFromY(1.0);
                        orbPulse.setToX(1.2);
                        orbPulse.setToY(1.2);
                        orbPulse.setAutoReverse(true);
                        orbPulse.setCycleCount(Animation.INDEFINITE);
                        orbPulse.setDelay(Duration.millis(palette.indexOf(color) * 150));
                        orbPulse.play();

                        paletteDemo.getChildren().add(orb);
                    }
                    visualBox.getChildren().add(paletteDemo);
                    break;

                case "grid_demo":
                    // Animated flood fill demonstration - Complete Level 1 solution with BFS spreading
                    VBox demoContainer = new VBox(15);
                    demoContainer.setAlignment(Pos.CENTER);

                    // Demo grid - 10x8 to match actual game
                    GridPane demoGrid = new GridPane();
                    demoGrid.setAlignment(Pos.CENTER);
                    demoGrid.setHgap(2);
                    demoGrid.setVgap(2);

                    // Level 1 patterns - showing all 5 states (initial + 4 moves)
                    char[][][] levelStates = {
                            // Initial state
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
                            // After 1st move (B -> R at center)
                            {
                                    {'G', 'G', 'Y', 'Y', 'Y', 'Y', 'Y', 'G', 'G', 'G'},
                                    {'Y', 'G', 'G', 'B', 'B', 'B', 'B', 'B', 'Y', 'Y'},
                                    {'Y', 'G', 'G', 'B', 'R', 'R', 'R', 'B', 'G', 'Y'},
                                    {'Y', 'Y', 'G', 'B', 'G', 'R', 'G', 'B', 'G', 'Y'},
                                    {'Y', 'B', 'B', 'B', 'R', 'R', 'G', 'B', 'G', 'Y'},
                                    {'Y', 'Y', 'Y', 'G', 'G', 'R', 'B', 'B', 'Y', 'Y'},
                                    {'Y', 'Y', 'Y', 'Y', 'G', 'G', 'G', 'Y', 'Y', 'Y'},
                                    {'Y', 'Y', 'Y', 'Y', 'Y', 'G', 'G', 'Y', 'Y', 'Y'}
                            },
                            // After 2nd move (R -> G at center)
                            {
                                    {'G', 'G', 'Y', 'Y', 'Y', 'Y', 'Y', 'G', 'G', 'G'},
                                    {'Y', 'G', 'G', 'B', 'B', 'B', 'B', 'B', 'Y', 'Y'},
                                    {'Y', 'G', 'G', 'B', 'G', 'G', 'G', 'B', 'G', 'Y'},
                                    {'Y', 'Y', 'G', 'B', 'G', 'G', 'G', 'B', 'G', 'Y'},
                                    {'Y', 'B', 'B', 'B', 'G', 'G', 'G', 'B', 'G', 'Y'},
                                    {'Y', 'Y', 'Y', 'G', 'G', 'G', 'B', 'B', 'Y', 'Y'},
                                    {'Y', 'Y', 'Y', 'Y', 'G', 'G', 'G', 'Y', 'Y', 'Y'},
                                    {'Y', 'Y', 'Y', 'Y', 'Y', 'G', 'G', 'Y', 'Y', 'Y'}
                            },
                            // After 3rd move (B -> G at edges)
                            {
                                    {'G', 'G', 'Y', 'Y', 'Y', 'Y', 'Y', 'G', 'G', 'G'},
                                    {'Y', 'G', 'G', 'G', 'G', 'G', 'G', 'G', 'Y', 'Y'},
                                    {'Y', 'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G', 'Y'},
                                    {'Y', 'Y', 'G', 'G', 'G', 'G', 'G', 'G', 'G', 'Y'},
                                    {'Y', 'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G', 'Y'},
                                    {'Y', 'Y', 'Y', 'G', 'G', 'G', 'G', 'G', 'Y', 'Y'},
                                    {'Y', 'Y', 'Y', 'Y', 'G', 'G', 'G', 'Y', 'Y', 'Y'},
                                    {'Y', 'Y', 'Y', 'Y', 'Y', 'G', 'G', 'Y', 'Y', 'Y'}
                            },
                            // After 4th move (G -> Y, complete!)
                            {
                                    {'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y'},
                                    {'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y'},
                                    {'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y'},
                                    {'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y'},
                                    {'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y'},
                                    {'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y'},
                                    {'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y'},
                                    {'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y'}
                            }
                    };

                    // Click positions for each move (row, col)
                    int[][] clickPositions = {
                            {4, 5},  // Move 1: Click at center R tile
                            {2, 4},  // Move 2: Click at center (now R, will become G)
                            {1, 3},  // Move 3: Click at B tile to flood to G
                            {1, 1}   // Move 4: Click at G tile to flood to Y
                    };

                    Rectangle[][] demoTiles = new Rectangle[8][10];

                    // Create tiles for initial state
                    for (int i = 0; i < 8; i++) {
                        for (int j = 0; j < 10; j++) {
                            Rectangle tile = new Rectangle(20, 20);
                            tile.setFill(charToColor(levelStates[0][i][j]).getFxColor());
                            tile.setArcWidth(4);
                            tile.setArcHeight(4);
                            tile.setStroke(Color.rgb(255, 255, 255, 0.2));
                            tile.setStrokeWidth(1);
                            demoTiles[i][j] = tile;
                            demoGrid.add(tile, j, i);
                        }
                    }

                    // Status labels
                    Label movesLabel = new Label("Moves: 4");
                    movesLabel.setStyle(
                            "-fx-text-fill: #FFFFFF;" +
                                    "-fx-font-family: 'Arial Black';" +
                                    "-fx-font-size: 16;" +
                                    "-fx-font-weight: bold;" +
                                    "-fx-effect: dropshadow(gaussian, #FF4466, 8, 0.6, 0, 0);"
                    );

                    Label actionLabel = new Label("Watch the solution!");
                    actionLabel.setStyle(
                            "-fx-text-fill: #00FFFF;" +
                                    "-fx-font-size: 12;" +
                                    "-fx-font-weight: bold;"
                    );

                    // Target color indicator
                    HBox targetColorBox = new HBox(8);
                    targetColorBox.setAlignment(Pos.CENTER);
                    Label targetLabel = new Label("Target:");
                    targetLabel.setStyle("-fx-text-fill: #CCCCCC; -fx-font-size: 11;");
                    Circle targetColorCircle = new Circle(10, ColorType.YELLOW.getFxColor());
                    targetColorCircle.setEffect(new Glow(0.8));
                    targetColorBox.getChildren().addAll(targetLabel, targetColorCircle);

                    // Selected color indicator
                    HBox selectedColorBox = new HBox(8);
                    selectedColorBox.setAlignment(Pos.CENTER);
                    Label selectedLabel = new Label("Selected:");
                    selectedLabel.setStyle("-fx-text-fill: #CCCCCC; -fx-font-size: 11;");
                    Circle selectedColorCircle = new Circle(10, ColorType.RED.getFxColor());
                    selectedColorCircle.setEffect(new Glow(0.8));
                    selectedColorBox.getChildren().addAll(selectedLabel, selectedColorCircle);

                    HBox statusRow = new HBox(20, targetColorBox, selectedColorBox);
                    statusRow.setAlignment(Pos.CENTER);

                    demoContainer.getChildren().addAll(movesLabel, statusRow, demoGrid, actionLabel);
                    visualBox.getChildren().add(demoContainer);

                    // Helper method to perform BFS and get tiles in distance order from source
                    class BFSHelper {
                        List<int[]> getTilesInBFSOrder(int startRow, int startCol, char[][] prevState, char[][] nextState) {
                            List<int[]> result = new ArrayList<>();
                            Set<String> visited = new HashSet<>();
                            Queue<int[]> queue = new LinkedList<>();

                            char originalColor = prevState[startRow][startCol];

                            // Add starting tile
                            queue.add(new int[]{startRow, startCol});
                            visited.add(startRow + "," + startCol);

                            int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

                            // BFS traversal
                            while (!queue.isEmpty()) {
                                int[] pos = queue.poll();
                                int row = pos[0];
                                int col = pos[1];

                                // Only add if this tile actually changed
                                if (prevState[row][col] != nextState[row][col]) {
                                    result.add(new int[]{row, col});
                                }

                                // Check adjacent tiles
                                for (int[] dir : directions) {
                                    int nr = row + dir[0];
                                    int nc = col + dir[1];
                                    String key = nr + "," + nc;

                                    if (nr >= 0 && nr < 8 && nc >= 0 && nc < 10 &&
                                            !visited.contains(key) &&
                                            prevState[nr][nc] == originalColor) {
                                        queue.add(new int[]{nr, nc});
                                        visited.add(key);
                                    }
                                }
                            }

                            return result;
                        }
                    }

                    BFSHelper bfsHelper = new BFSHelper();

                    // Animate the complete solution
                    Timeline demoAnimation = new Timeline();

                    // Move descriptions
                    String[] moveDescriptions = {
                            "Move 1: Select RED, flood center!",
                            "Move 2: Select GREEN, expand region!",
                            "Move 3: Select GREEN, flood more!",
                            "Move 4: Select YELLOW, complete! 🎉"
                    };

                    Color[] moveColors = {
                            ColorType.RED.getFxColor(),
                            ColorType.GREEN.getFxColor(),
                            ColorType.GREEN.getFxColor(),
                            ColorType.YELLOW.getFxColor()
                    };

                    double currentTime = 0.5; // Start after 0.5 seconds

                    for (int move = 0; move < 4; move++) {
                        final int moveIndex = move;
                        final int movesLeft = 4 - move;
                        final int clickRow = clickPositions[move][0];
                        final int clickCol = clickPositions[move][1];

                        // Highlight click position AND immediately deduct move
                        demoAnimation.getKeyFrames().add(new KeyFrame(Duration.seconds(currentTime), e -> {
                            actionLabel.setText(moveDescriptions[moveIndex]);
                            selectedColorCircle.setFill(moveColors[moveIndex]);

                            // IMMEDIATELY update moves counter (before spreading animation)
                            movesLabel.setText("Moves: " + (movesLeft - 1));

                            // Pulse the clicked tile
                            Rectangle clickedTile = demoTiles[clickRow][clickCol];
                            ScaleTransition clickPulse = new ScaleTransition(Duration.millis(300), clickedTile);
                            clickPulse.setToX(1.3);
                            clickPulse.setToY(1.3);
                            clickPulse.setAutoReverse(true);
                            clickPulse.setCycleCount(2);
                            clickPulse.play();

                            // Add glow
                            clickedTile.setEffect(new Glow(1.0));
                        }));

                        currentTime += 0.5; // Wait before animating tiles

                        // Get tiles in BFS order from click position
                        char[][] prevState = levelStates[moveIndex];
                        char[][] nextState = levelStates[moveIndex + 1];

                        List<int[]> changedTiles = bfsHelper.getTilesInBFSOrder(clickRow, clickCol, prevState, nextState);

                        // Animate each changed tile in BFS order (spreading outward)
                        for (int idx = 0; idx < changedTiles.size(); idx++) {
                            final int i = changedTiles.get(idx)[0];
                            final int j = changedTiles.get(idx)[1];
                            final char newColorChar = nextState[i][j];

                            demoAnimation.getKeyFrames().add(new KeyFrame(
                                    Duration.seconds(currentTime + idx * 0.05), // 50ms between tiles
                                    e -> {
                                        Rectangle tile = demoTiles[i][j];

                                        // Change color
                                        tile.setFill(charToColor(newColorChar).getFxColor());

                                        // Scale animation
                                        ScaleTransition scale = new ScaleTransition(Duration.millis(100), tile);
                                        scale.setFromX(1.0);
                                        scale.setFromY(1.0);
                                        scale.setToX(1.4);
                                        scale.setToY(1.4);
                                        scale.setAutoReverse(true);
                                        scale.setCycleCount(2);
                                        scale.play();

                                        // Glow effect
                                        tile.setEffect(new Glow(0.8));

                                        // Remove glow and highlight after animation
                                        Timeline removeEffects = new Timeline(new KeyFrame(
                                                Duration.millis(200),
                                                evt -> {
                                                    tile.setEffect(null);
                                                }
                                        ));
                                        removeEffects.play();
                                    }
                            ));
                        }

                        currentTime += changedTiles.size() * 0.05 + 0.3; // Wait for all tiles + buffer

                        // Check for completion message (only after move 4)
                        if (move == 3) {
                            demoAnimation.getKeyFrames().add(new KeyFrame(Duration.seconds(currentTime), e -> {
                                actionLabel.setText("LEVEL COMPLETE! All tiles match target! 🎉");
                                actionLabel.setStyle(
                                        "-fx-text-fill: #00FF00;" +
                                                "-fx-font-size: 12;" +
                                                "-fx-font-weight: bold;"
                                );
                            }));
                        }

                        currentTime += 1.0; // Pause between moves
                    }

                    // Reset after completion
                    demoAnimation.getKeyFrames().add(new KeyFrame(
                            Duration.seconds(currentTime + 2),
                            e -> {
                                // Reset to initial state
                                movesLabel.setText("Moves: 4");
                                actionLabel.setText("Watch the solution!");
                                actionLabel.setStyle(
                                        "-fx-text-fill: #00FFFF;" +
                                                "-fx-font-size: 12;" +
                                                "-fx-font-weight: bold;"
                                );
                                selectedColorCircle.setFill(ColorType.RED.getFxColor());

                                // Reset all tiles
                                for (int i = 0; i < 8; i++) {
                                    for (int j = 0; j < 10; j++) {
                                        demoTiles[i][j].setFill(charToColor(levelStates[0][i][j]).getFxColor());
                                        demoTiles[i][j].setStroke(Color.rgb(255, 255, 255, 0.2));
                                        demoTiles[i][j].setStrokeWidth(1);
                                        demoTiles[i][j].setEffect(null);
                                    }
                                }
                            }
                    ));

                    demoAnimation.setCycleCount(Animation.INDEFINITE);
                    demoAnimation.play();

                    // Store animation reference to stop it when leaving this step
                    tutorialCard.setUserData(demoAnimation);
                    break;

                case "grid":
                    // Mini grid demonstration - smaller
                    GridPane miniGrid = new GridPane();
                    miniGrid.setAlignment(Pos.CENTER);
                    miniGrid.setHgap(3);
                    miniGrid.setVgap(3);

                    for (int i = 0; i < 3; i++) {
                        for (int j = 0; j < 3; j++) {
                            Rectangle miniTile = new Rectangle(35, 35);
                            miniTile.setArcWidth(8);
                            miniTile.setArcHeight(8);

                            if (i == 1 && j == 1) {
                                miniTile.setFill(ColorType.BLUE.getFxColor());
                                miniTile.setStroke(Color.WHITE);
                                miniTile.setStrokeWidth(3);
                                miniTile.setStrokeType(StrokeType.INSIDE);  // Draw stroke inside the bounds
                                miniTile.setEffect(new Glow(1.0));

                                // Pulse the center tile
                                ScaleTransition tilePulse = new ScaleTransition(Duration.millis(600), miniTile);
                                tilePulse.setFromX(1.0);
                                tilePulse.setFromY(1.0);
                                tilePulse.setToX(1.15);
                                tilePulse.setToY(1.15);
                                tilePulse.setAutoReverse(true);
                                tilePulse.setCycleCount(Animation.INDEFINITE);
                                tilePulse.play();
                            } else {
                                miniTile.setFill(ColorType.GREEN.getFxColor());
                            }
                            miniGrid.add(miniTile, j, i);
                        }
                    }
                    visualBox.getChildren().add(miniGrid);
                    break;

                case "moves":
                    Label movesDemo = new Label("Moves: 4");
                    movesDemo.setStyle(
                            "-fx-text-fill: #FFFFFF;" +
                                    "-fx-font-family: 'Arial Black';" +
                                    "-fx-font-size: 30;" +
                                    "-fx-font-weight: bold;" +
                                    "-fx-effect: dropshadow(gaussian, #FF4466, 10, 0.6, 0, 0);"
                    );

                    // Pulse animation
                    ScaleTransition movesPulse = new ScaleTransition(Duration.millis(800), movesDemo);
                    movesPulse.setFromX(1.0);
                    movesPulse.setFromY(1.0);
                    movesPulse.setToX(1.2);
                    movesPulse.setToY(1.2);
                    movesPulse.setAutoReverse(true);
                    movesPulse.setCycleCount(Animation.INDEFINITE);
                    movesPulse.play();

                    visualBox.getChildren().add(movesDemo);
                    break;
            }
        }

        // Navigation buttons
        HBox buttonBox = new HBox(20);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(20, 0, 0, 0));

        // Previous button (if not first step)
        if (currentTutorialStep > 0) {
            Button prevButton = createTutorialButton("← PREVIOUS", Color.rgb(100, 150, 255));
            prevButton.setOnAction(e -> {
                currentTutorialStep--;
                updateTutorialStep();
            });
            buttonBox.getChildren().add(prevButton);
        }

        // Next/Finish button
        boolean isLastStep = currentTutorialStep == tutorialSteps.length - 1;
        Button nextButton = createTutorialButton(
                isLastStep ? "START PLAYING!" : "NEXT →",
                Color.rgb(0, 255, 100)
        );
        nextButton.setOnAction(e -> {
            if (isLastStep) {
                closeTutorial();
            } else {
                currentTutorialStep++;
                updateTutorialStep();
            }
        });
        buttonBox.getChildren().add(nextButton);

        // Skip button
        Button skipButton = createTutorialButton("SKIP TUTORIAL", Color.rgb(255, 100, 100));
        skipButton.setOnAction(e -> closeTutorial());

        // Add all elements to card
        tutorialCard.getChildren().addAll(
                stepIndicator,
                title,
                description,
                visualBox,
                buttonBox,
                new Region(), // Spacer
                skipButton
        );

        // Entrance animation
        tutorialCard.setScaleX(0.9);
        tutorialCard.setScaleY(0.9);
        tutorialCard.setOpacity(0);

        FadeTransition fade = new FadeTransition(Duration.millis(300), tutorialCard);
        fade.setFromValue(0);
        fade.setToValue(1);

        ScaleTransition scale = new ScaleTransition(Duration.millis(300), tutorialCard);
        scale.setFromX(0.9);
        scale.setFromY(0.9);
        scale.setToX(1.0);
        scale.setToY(1.0);

        new ParallelTransition(fade, scale).play();
    }

    // Helper method to create tutorial buttons
    private Button createTutorialButton(String text, Color color) {
        Button button = new Button(text);
        button.setFocusTraversable(false);
        button.setMinWidth(180);
        button.setMinHeight(45);
        button.setMaxHeight(45);
        button.setCursor(javafx.scene.Cursor.HAND);

        String colorString = toRgbString(color);
        String normalStyle =
                "-fx-background-color: " + toRgbString(Color.rgb(
                        (int)(color.getRed() * 255 * 0.3),
                        (int)(color.getGreen() * 255 * 0.3),
                        (int)(color.getBlue() * 255 * 0.3)
                )) + ";" +
                        "-fx-border-color: " + colorString + ";" +
                        "-fx-border-width: 2;" +
                        "-fx-border-radius: 25;" +
                        "-fx-background-radius: 25;" +
                        "-fx-text-fill: " + colorString + ";" +
                        "-fx-font-family: 'Arial Black';" +
                        "-fx-font-size: 16;" +
                        "-fx-font-weight: bold;" +
                        "-fx-padding: 12 30;" +
                        "-fx-effect: dropshadow(gaussian, " + colorString + ", 10, 0.4, 0, 0);";

        String hoverStyle =
                "-fx-background-color: " + toRgbString(Color.rgb(
                        (int)(color.getRed() * 255 * 0.5),
                        (int)(color.getGreen() * 255 * 0.5),
                        (int)(color.getBlue() * 255 * 0.5)
                )) + ";" +
                        "-fx-border-color: " + colorString + ";" +
                        "-fx-border-width: 2;" +
                        "-fx-border-radius: 25;" +
                        "-fx-background-radius: 25;" +
                        "-fx-text-fill: #FFFFFF;" +
                        "-fx-font-family: 'Arial Black';" +
                        "-fx-font-size: 16;" +
                        "-fx-font-weight: bold;" +
                        "-fx-padding: 12 30;" +
                        "-fx-effect: dropshadow(gaussian, " + colorString + ", 15, 0.6, 0, 0);";

        button.setStyle(normalStyle);
        button.setOnMouseEntered(e -> button.setStyle(hoverStyle));
        button.setOnMouseExited(e -> button.setStyle(normalStyle));

        return button;
    }
    // Close tutorial
    private void closeTutorial() {
        if (tutorialStage != null) {
            // Only mark as completed if:
            // 1. User is logged in
            // 2. Tutorial wasn't already completed
            // 3. Tutorial was NOT manually opened (i.e., it was auto-triggered on level 1)
            boolean wasManuallyOpened = tutorialStage.getUserData() instanceof Boolean
                    ? (Boolean) tutorialStage.getUserData()
                    : false;

            if (currentUser != null && !tutorialCompleted && !wasManuallyOpened) {
                tutorialCompleted = true;
                saveTutorialStatusToSupabase();
                System.out.println("[DEBUG] Tutorial completed and saved!");
            }

            tutorialActive = false;
            tutorialStage.close();
            tutorialStage = null;
            isDialogOpen = false; // Reset dialog flag
        }
    }

    private void saveTutorialStatusToSupabase() {
        if (currentUser == null) return;

        executor.submit(() -> {
            try {
                String levelsStr = String.join(",",
                        unlockedLevels.stream()
                                .map(String::valueOf)
                                .sorted()
                                .toList());

                Map<String, Object> payload = Map.of(
                        "unlocked_levels", levelsStr,
                        "effects_muted", isEffectsMuted,
                        "music_muted", isMusicMuted,
                        "volume", audioVolume,
                        "sprite_visible", isSpriteVisible,
                        "tutorial_completed", tutorialCompleted  // Save tutorial status
                );
                String json = gson.toJson(payload);

                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(SUPABASE_URL + "/rest/v1/profiles?username=eq." + currentUser))
                        .header("apikey", SUPABASE_ANON_KEY)
                        .header("Authorization", "Bearer " + SUPABASE_ANON_KEY)
                        .header("Content-Type", "application/json")
                        .method("PATCH", HttpRequest.BodyPublishers.ofString(json))
                        .build();

                httpClient.send(request, HttpResponse.BodyHandlers.discarding());

            } catch (Exception e) {
                System.err.println("Failed to save tutorial status to Supabase: " + e.getMessage());
            }
        });
    }

    private void setupGameplayScreen() {
        loadUserPreferencesSync();
        System.out.println("[DEBUG] Setting up gameplay screen. skipBackConfirmation = " + skipBackConfirmation);
        root.getChildren().clear();
        root.setStyle("-fx-background-color: radial-gradient(center 50% 50%, radius 80%, #1C2526, #0A0E26);");
        primaryStage.setTitle("Chroma Flood - Level " + currentLevel.get());

        levelStartTime = System.currentTimeMillis();
        ChromaFloodSystem gameInstance = this;
        currentLevel.set(currentLevel.get());

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
        double tileSize = 60.0;
        double hGap = 6.0;
        double vGap = 6.0;
        double gridWidth = COLS * tileSize + (COLS - 1) * hGap;
        double gridHeight = ROWS * tileSize + (ROWS - 1) * vGap;

        // Create a Rectangle for the border/background
        double borderPadding = 12.0;
        Rectangle borderRect = new Rectangle(
                gridWidth + 2 * borderPadding,
                gridHeight + 2 * borderPadding
        );
        borderRect.setFill(Color.web("#1A1A1A"));
        borderRect.setStroke(targetColor.getFxColor());
        borderRect.setStrokeWidth(5.0);
        borderRect.setArcWidth(20.0);
        borderRect.setArcHeight(20.0);

        DropShadow borderShadow = new DropShadow();
        borderShadow.setRadius(12.0);
        borderShadow.setOffsetX(6.0);
        borderShadow.setOffsetY(6.0);
        borderShadow.setColor(Color.rgb(0, 0, 0, 0.9));
        borderRect.setEffect(borderShadow);

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
            arrow.getPoints().addAll(10.0, 0.0, 0.0, 5.0, 10.0, 10.0);
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
        resetButton.setFocusTraversable(false);
        resetButton.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5); -fx-text-fill: #FF4466; -fx-font-family: 'Arial Black'; -fx-font-size: 14; -fx-font-weight: bold; -fx-background-radius: 15; -fx-padding: 10 20; -fx-border-color: #FF4466; -fx-border-width: 2; -fx-border-radius: 15; -fx-effect: dropshadow(gaussian, #FF4466, 8, 0.6, 0, 0); -fx-focus-color: transparent; -fx-faint-focus-color: transparent;");
        resetButton.setOnAction(this::handleReset);
        resetButton.setOnMouseEntered(event -> resetButton.setStyle("-fx-background-color: rgba(255, 68, 102, 0.15); -fx-text-fill: #FF6688; -fx-font-family: 'Arial Black'; -fx-font-size: 14; -fx-font-weight: bold; -fx-background-radius: 15; -fx-padding: 10 20; -fx-border-color: #FF6688; -fx-border-width: 2; -fx-border-radius: 15; -fx-effect: dropshadow(gaussian, #FF4466, 8, 0.5, 0, 0); -fx-focus-color: transparent; -fx-faint-focus-color: transparent;"));
        resetButton.setOnMouseExited(event -> resetButton.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5); -fx-text-fill: #FF4466; -fx-font-family: 'Arial Black'; -fx-font-size: 14; -fx-font-weight: bold; -fx-background-radius: 15; -fx-padding: 10 20; -fx-border-color: #FF4466; -fx-border-width: 2; -fx-border-radius: 15; -fx-effect: dropshadow(gaussian, #FF4466, 8, 0.6, 0, 0); -fx-focus-color: transparent; -fx-faint-focus-color: transparent;"));

        Button backButton = new Button("BACK");
        backButton.setFocusTraversable(false);
        backButton.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5); -fx-text-fill: #44DDFF; -fx-font-family: 'Arial Black'; -fx-font-size: 14; -fx-font-weight: bold; -fx-background-radius: 15; -fx-padding: 10 20; -fx-border-color: #44DDFF; -fx-border-width: 2; -fx-border-radius: 15; -fx-effect: dropshadow(gaussian, #44DDFF, 8, 0.6, 0, 0); -fx-focus-color: transparent; -fx-faint-focus-color: transparent;");
        backButton.setOnAction(event -> {
            if (backButton.isDisabled()) return;

            // Check if user wants to skip confirmation
            if (skipBackConfirmation) {
                backButton.setDisable(true);
                if (floodFillTimeline != null) {
                    floodFillTimeline.stop();
                    floodFillTimeline = null;
                }
                if (clickSound != null) {
                    clickSound.stop();
                }
                isAnimating = false;
                animateGridExplosion(() -> showLevelSelectScreen());
            } else {
                showBackConfirmationModal(backButton);
            }
        });

        backButton.setOnMouseEntered(event -> backButton.setStyle("-fx-background-color: rgba(68, 221, 255, 0.15); -fx-text-fill: #66EEFF; -fx-font-family: 'Arial Black'; -fx-font-size: 14; -fx-font-weight: bold; -fx-background-radius: 15; -fx-padding: 10 20; -fx-border-color: #66EEFF; -fx-border-width: 2; -fx-border-radius: 15; -fx-effect: dropshadow(gaussian, #44DDFF, 8, 0.5, 0, 0); -fx-focus-color: transparent; -fx-faint-focus-color: transparent;"));
        backButton.setOnMouseExited(event -> backButton.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5); -fx-text-fill: #44DDFF; -fx-font-family: 'Arial Black'; -fx-font-size: 14; -fx-font-weight: bold; -fx-background-radius: 15; -fx-padding: 10 20; -fx-border-color: #44DDFF; -fx-border-width: 2; -fx-border-radius: 15; -fx-effect: dropshadow(gaussian, #44DDFF, 8, 0.6, 0, 0); -fx-focus-color: transparent; -fx-faint-focus-color: transparent;"));

        Button volumeButton = new Button("⚙");
        volumeButton.setFocusTraversable(false);
        volumeButton.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5); -fx-text-fill: #44FF88; -fx-font-family: 'Arial Black'; -fx-font-size: 14; -fx-font-weight: bold; -fx-background-radius: 15; -fx-padding: 10 20; -fx-border-color: #44FF88; -fx-border-width: 2; -fx-border-radius: 15; -fx-effect: dropshadow(gaussian, #44FF88, 8, 0.6, 0, 0); -fx-min-width: 50; -fx-focus-color: transparent; -fx-faint-focus-color: transparent;");
        volumeButton.setOnAction(event -> showSettingsDialog());
        volumeButton.setOnMouseEntered(event -> volumeButton.setStyle("-fx-background-color: rgba(68, 255, 136, 0.15); -fx-text-fill: #66FFAA; -fx-font-family: 'Arial Black'; -fx-font-size: 14; -fx-font-weight: bold; -fx-background-radius: 15; -fx-padding: 10 20; -fx-border-color: #66FFAA; -fx-border-width: 2; -fx-border-radius: 15; -fx-effect: dropshadow(gaussian, #44FF88, 8, 0.5, 0, 0); -fx-min-width: 50; -fx-focus-color: transparent; -fx-faint-focus-color: transparent;"));
        volumeButton.setOnMouseExited(event -> volumeButton.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5); -fx-text-fill: #44FF88; -fx-font-family: 'Arial Black'; -fx-font-size: 14; -fx-font-weight: bold; -fx-background-radius: 15; -fx-padding: 10 20; -fx-border-color: #44FF88; -fx-border-width: 2; -fx-border-radius: 15; -fx-effect: dropshadow(gaussian, #44FF88, 8, 0.6, 0, 0); -fx-min-width: 50; -fx-focus-color: transparent; -fx-faint-focus-color: transparent;"));
        HBox buttonBox = new HBox(20, resetButton, backButton, volumeButton);
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
        if (currentUser != null && !tutorialCompleted && currentLevel.get() == 1) {
            Platform.runLater(() -> showTutorial());
        }

        animateGridEntrance();
    }

    private void loadUserPreferencesSync() {
        if (currentUser == null) return;

        try {
            // Add tutorial_completed to the select query
            String url = SUPABASE_URL + "/rest/v1/profiles?username=eq." + currentUser
                    + "&select=skip_back_confirmation,tutorial_completed";

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("apikey", SUPABASE_ANON_KEY)
                    .header("Authorization", "Bearer " + SUPABASE_ANON_KEY)
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                JsonArray array = gson.fromJson(response.body(), JsonArray.class);
                if (array.size() > 0) {
                    JsonObject userData = array.get(0).getAsJsonObject();

                    // Load skip_back_confirmation
                    skipBackConfirmation = userData.has("skip_back_confirmation")
                            ? userData.get("skip_back_confirmation").getAsBoolean()
                            : false;

                    // Load tutorial_completed
                    if (userData.has("tutorial_completed") && !userData.get("tutorial_completed").isJsonNull()) {
                        tutorialCompleted = userData.get("tutorial_completed").getAsBoolean();
                        System.out.println("[LOAD SYNC] Loaded tutorial_completed: " + tutorialCompleted);
                    } else {
                        tutorialCompleted = false; // Default if not found
                    }

                    System.out.println("[LOAD SYNC] Loaded skip_back_confirmation: " + skipBackConfirmation);
                }
            }
        } catch (Exception e) {
            System.err.println("[ERROR] Failed to load user preferences: " + e.getMessage());
            skipBackConfirmation = false; // Safe default on error
            tutorialCompleted = false; // Safe default on error
        }
    }

    private void showBackConfirmationModal(Button backButton) {
        Stage modal = new Stage();
        modal.initOwner(primaryStage);
        modal.initModality(Modality.WINDOW_MODAL);
        modal.initStyle(StageStyle.TRANSPARENT);

        StackPane borderContainer = new StackPane();
        borderContainer.setStyle("-fx-background-color: transparent;");

        Rectangle leftBorder = new Rectangle(3, 0);
        leftBorder.setFill(Color.CYAN);
        StackPane.setAlignment(leftBorder, Pos.CENTER_LEFT);

        Rectangle rightBorder = new Rectangle(3, 0);
        rightBorder.setFill(Color.CYAN);
        StackPane.setAlignment(rightBorder, Pos.CENTER_RIGHT);

        Rectangle topBorder = new Rectangle(0, 3);
        topBorder.setFill(Color.CYAN);
        StackPane.setAlignment(topBorder, Pos.TOP_CENTER);

        Rectangle bottomBorder = new Rectangle(0, 3);
        bottomBorder.setFill(Color.CYAN);
        StackPane.setAlignment(bottomBorder, Pos.BOTTOM_CENTER);

        VBox card = new VBox(25);
        card.setAlignment(Pos.CENTER);
        card.setPadding(new Insets(50, 80, 50, 80));
        card.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #0a0e1a 0%, #1a1f35 100%);" +
                        "-fx-effect: dropshadow(gaussian, rgba(0,255,255,0.25), 12, 0.4, 0, 0)," +
                        "dropshadow(gaussian, rgba(68,221,255,0.2), 15, 0.3, 0, 0);"
        );

        Timeline rgbAnimation = new Timeline(
                new KeyFrame(Duration.ZERO,
                        new KeyValue(leftBorder.fillProperty(), Color.rgb(0, 255, 255)),
                        new KeyValue(rightBorder.fillProperty(), Color.rgb(0, 255, 255)),
                        new KeyValue(topBorder.fillProperty(), Color.rgb(0, 255, 255)),
                        new KeyValue(bottomBorder.fillProperty(), Color.rgb(0, 255, 255))
                ),
                new KeyFrame(Duration.seconds(1),
                        new KeyValue(leftBorder.fillProperty(), Color.rgb(68, 221, 255)),
                        new KeyValue(rightBorder.fillProperty(), Color.rgb(68, 221, 255)),
                        new KeyValue(topBorder.fillProperty(), Color.rgb(68, 221, 255)),
                        new KeyValue(bottomBorder.fillProperty(), Color.rgb(68, 221, 255))
                ),
                new KeyFrame(Duration.seconds(2),
                        new KeyValue(leftBorder.fillProperty(), Color.rgb(0, 170, 255)),
                        new KeyValue(rightBorder.fillProperty(), Color.rgb(0, 170, 255)),
                        new KeyValue(topBorder.fillProperty(), Color.rgb(0, 170, 255)),
                        new KeyValue(bottomBorder.fillProperty(), Color.rgb(0, 170, 255))
                ),
                new KeyFrame(Duration.seconds(3),
                        new KeyValue(leftBorder.fillProperty(), Color.rgb(0, 255, 255)),
                        new KeyValue(rightBorder.fillProperty(), Color.rgb(0, 255, 255)),
                        new KeyValue(topBorder.fillProperty(), Color.rgb(0, 255, 255)),
                        new KeyValue(bottomBorder.fillProperty(), Color.rgb(0, 255, 255))
                )
        );
        rgbAnimation.setCycleCount(Timeline.INDEFINITE);
        rgbAnimation.play();

        leftBorder.setEffect(new DropShadow(10.0, Color.CYAN));
        rightBorder.setEffect(new DropShadow(10.0, Color.CYAN));
        topBorder.setEffect(new DropShadow(10.0, Color.CYAN));
        bottomBorder.setEffect(new DropShadow(10.0, Color.CYAN));

        borderContainer.getChildren().addAll(card, leftBorder, rightBorder, topBorder, bottomBorder);

        Label title = new Label("⚠ LEAVE LEVEL? ⚠");
        title.setStyle(
                "-fx-text-fill: #44DDFF;" +
                        "-fx-font-family: 'Arial Black', 'Impact';" +
                        "-fx-font-size: 32;" +
                        "-fx-font-weight: bold;" +
                        "-fx-effect: dropshadow(gaussian, #44DDFF, 5, 0.3, 0, 0);"
        );

        Label message = new Label("Your progress on this level will be lost.\nAre you sure you want to go back?");
        message.setStyle(
                "-fx-text-fill: #FFFFFF;" +
                        "-fx-font-size: 18;" +
                        "-fx-line-spacing: 5;" +
                        "-fx-text-alignment: center;"
        );
        message.setTextAlignment(TextAlignment.CENTER);
        message.setWrapText(true);

        CheckBox dontAskCheckBox = new CheckBox("Don't ask me again");
        dontAskCheckBox.setStyle(
                "-fx-text-fill: #AAAAAA;" +
                        "-fx-font-size: 14;" +
                        "-fx-font-weight: bold;"
        );
        dontAskCheckBox.setSelected(false);

        dontAskCheckBox.selectedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                dontAskCheckBox.setStyle(
                        "-fx-text-fill: #44DDFF;" +
                                "-fx-font-size: 14;" +
                                "-fx-font-weight: bold;"
                );
            } else {
                dontAskCheckBox.setStyle(
                        "-fx-text-fill: #AAAAAA;" +
                                "-fx-font-size: 14;" +
                                "-fx-font-weight: bold;"
                );
            }
        });

        HBox buttonBox = new HBox(25);
        buttonBox.setAlignment(Pos.CENTER);

        Button yesButton = createNeonButton("✓ YES, GO BACK", "#44DDFF", "#2288CC");
        Button noButton = createNeonButton("✗ STAY HERE", "#FF5555", "#AA0000");

        yesButton.setOnAction(e -> {
            // Save preference FIRST if checkbox is selected
            if (dontAskCheckBox.isSelected()) {
                skipBackConfirmation = true;
                // Save synchronously in background, but update local variable immediately
                saveBackConfirmationPreference(true);
            }

            modal.close();
            backButton.setDisable(true);

            if (floodFillTimeline != null) {
                floodFillTimeline.stop();
                floodFillTimeline = null;
            }
            if (clickSound != null) {
                clickSound.stop();
            }
            isAnimating = false;
            animateGridExplosion(() -> showLevelSelectScreen());
        });

        noButton.setOnAction(e -> {
            // Save preference FIRST if checkbox is selected
            if (dontAskCheckBox.isSelected()) {
                skipBackConfirmation = true;
                // Save synchronously in background, but update local variable immediately
                saveBackConfirmationPreference(true);
            }
            modal.close();
        });

        noButton.setOnAction(e -> {
            // Save preference if checkbox is checked
            if (dontAskCheckBox.isSelected()) {
                skipBackConfirmation = true;
                saveBackConfirmationPreference(true);
            }
            modal.close();
        });

        buttonBox.getChildren().addAll(yesButton, noButton);

        card.getChildren().addAll(title, message, dontAskCheckBox, buttonBox);

        Scene scene = new Scene(borderContainer);
        scene.setFill(Color.TRANSPARENT);
        modal.setScene(scene);

        modal.setOnShown(e -> {
            leftBorder.setHeight(card.getHeight());
            rightBorder.setHeight(card.getHeight());
            topBorder.setWidth(card.getWidth());
            bottomBorder.setWidth(card.getWidth());

            double x = primaryStage.getX() + (primaryStage.getWidth() - modal.getWidth()) / 2;
            double y = primaryStage.getY() + (primaryStage.getHeight() - modal.getHeight()) / 2;
            modal.setX(x);
            modal.setY(y);
        });

        modal.show();
    }

    // NEW METHOD: Save back confirmation preference to Supabase
    private void saveBackConfirmationPreference(boolean skip) {
        // UPDATE LOCAL VARIABLE IMMEDIATELY - This is the key fix!
        this.skipBackConfirmation = skip;
        System.out.println("[LOCAL] skipBackConfirmation set to: " + skip);

        // Then save to database asynchronously
        executor.submit(() -> {
            try {
                String url = SUPABASE_URL + "/rest/v1/profiles?username=eq." + currentUser;

                JsonObject updates = new JsonObject();
                updates.addProperty("skip_back_confirmation", skip);

                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(url))
                        .header("apikey", SUPABASE_ANON_KEY)
                        .header("Authorization", "Bearer " + SUPABASE_ANON_KEY)
                        .header("Content-Type", "application/json")
                        .header("Prefer", "return=minimal")
                        .method("PATCH", HttpRequest.BodyPublishers.ofString(gson.toJson(updates)))
                        .build();

                HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

                if (response.statusCode() == 200 || response.statusCode() == 204) {
                    System.out.println("[SAVE] Back confirmation preference saved to DB: " + skip);
                } else {
                    System.err.println("[ERROR] Failed to save preference. Status: " + response.statusCode());
                }
            } catch (Exception e) {
                System.err.println("[ERROR] Failed to save back confirmation preference: " + e.getMessage());
                e.printStackTrace();
            }
        });
    }

    private void animateGridEntrance() {
        ParallelTransition parallelTransition = new ParallelTransition();

        // Animate each tile with staggered entrance
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                Tile tile = tiles[row][col];

                // Start from invisible and scaled down
                tile.setOpacity(0);
                tile.setScaleX(0.1);
                tile.setScaleY(0.1);
                tile.setRotate(-180);

                // Calculate delay based on distance from center
                int centerRow = ROWS / 2;
                int centerCol = COLS / 2;
                double distance = Math.sqrt(Math.pow(row - centerRow, 2) + Math.pow(col - centerCol, 2));
                double delay = distance * 50; // 50ms per unit of distance

                // Fade in
                FadeTransition fade = new FadeTransition(Duration.millis(400), tile);
                fade.setDelay(Duration.millis(delay));
                fade.setFromValue(0);
                fade.setToValue(1);

                // Scale up with bounce
                ScaleTransition scale = new ScaleTransition(Duration.millis(500), tile);
                scale.setDelay(Duration.millis(delay));
                scale.setFromX(0.1);
                scale.setFromY(0.1);
                scale.setToX(1.0);
                scale.setToY(1.0);
                scale.setInterpolator(Interpolator.EASE_OUT);

                // Rotate
                RotateTransition rotate = new RotateTransition(Duration.millis(500), tile);
                rotate.setDelay(Duration.millis(delay));
                rotate.setFromAngle(-180);
                rotate.setToAngle(0);
                rotate.setInterpolator(Interpolator.EASE_OUT);

                // Add glow effect during entrance
                Glow entranceGlow = new Glow(0.8);
                Timeline glowTimeline = new Timeline(
                        new KeyFrame(Duration.millis(delay), e -> {
                            if (tile.getEffect() == null) {  // Only set if no effect exists
                                tile.setEffect(entranceGlow);
                            }
                        }),
                        new KeyFrame(Duration.millis(delay + 500), e -> {
                            if (tile.getEffect() instanceof Glow) {  // Only clear glow, not other effects
                                tile.setEffect(null);
                            }
                        })
                );

                parallelTransition.getChildren().addAll(fade, scale, rotate, glowTimeline);
            }
        }

        // Play entrance sound when animation starts (if you have one)
        parallelTransition.setOnFinished(e -> {
            System.out.println("Grid entrance animation complete");
            // Grid is now ready for interaction
        });

        gridEntranceAnimation = parallelTransition;
        parallelTransition.play();
    }

    private void animateGridExplosion(Runnable onComplete) {
        isTransitioning = true;
        ParallelTransition explosion = new ParallelTransition();

        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                Tile tile = tiles[row][col];

                // Calculate random direction for explosion
                double angle = Math.random() * 2 * Math.PI;
                double distance = 200 + Math.random() * 300;
                double targetX = Math.cos(angle) * distance;
                double targetY = Math.sin(angle) * distance;

                // Translate away
                TranslateTransition translate = new TranslateTransition(Duration.millis(800), tile);
                translate.setByX(targetX);
                translate.setByY(targetY);
                translate.setInterpolator(Interpolator.EASE_IN);

                // Fade out
                FadeTransition fade = new FadeTransition(Duration.millis(800), tile);
                fade.setToValue(0);

                // Spin wildly
                RotateTransition rotate = new RotateTransition(Duration.millis(800), tile);
                rotate.setByAngle(360 + Math.random() * 720);

                // Scale down
                ScaleTransition scale = new ScaleTransition(Duration.millis(800), tile);
                scale.setToX(0.1);
                scale.setToY(0.1);

                explosion.getChildren().addAll(translate, fade, rotate, scale);
            }
        }

        explosion.setOnFinished(e -> {
            isTransitioning = false;
            onComplete.run();
        });

        explosion.play();
    }

    private void showSettingsDialog() {
        settingsProfileView = new ImageView(currentProfileImage);

        audioSettingsStage = new Stage();
        audioSettingsStage.initOwner(primaryStage);
        audioSettingsStage.initModality(Modality.WINDOW_MODAL);
        audioSettingsStage.initStyle(StageStyle.TRANSPARENT);
        audioSettingsStage.setTitle("Settings");
        audioSettingsStage.setResizable(false);

        audioSettingsStage.setOnHidden(e -> isDialogOpen = false);

        StackPane overlay = new StackPane();
        overlay.setStyle("-fx-background-color: transparent;");
        overlay.setPadding(new Insets(40));

        VBox mainContainer = new VBox(20);
        mainContainer.setPadding(new Insets(30, 35, 30, 35));
        mainContainer.setStyle(
                "-fx-background-color: linear-gradient(to bottom right, #1a1a2e, #0f0f1e);" +
                        "-fx-background-radius: 20;" +
                        "-fx-effect: dropshadow(gaussian, rgba(100, 100, 255, 0.4), 25, 0.5, 0, 0);"
        );
        mainContainer.setAlignment(Pos.CENTER);

        Label titleLabel = new Label("Settings");
        titleLabel.setStyle("-fx-text-fill: #FFFFFF; -fx-font-family: 'Segoe UI', 'Arial'; -fx-font-size: 24; -fx-font-weight: bold;");

        HBox contentLayout = new HBox(30);
        contentLayout.setAlignment(Pos.CENTER);
        contentLayout.setPadding(new Insets(20));

        // === LEFT SIDE: AUDIO SETTINGS ===
        VBox audioPanel = new VBox(18);
        audioPanel.setAlignment(Pos.TOP_CENTER);
        audioPanel.setPadding(new Insets(25, 30, 25, 30));
        audioPanel.setStyle("-fx-background-color: rgba(30, 30, 45, 0.7); -fx-background-radius: 15; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.4), 15, 0, 0, 5);");
        audioPanel.setPrefWidth(320);

        Label audioSectionHeader = new Label("Audio");
        audioSectionHeader.setStyle("-fx-text-fill: #FFD700; -fx-font-family: 'Segoe UI', 'Arial'; -fx-font-size: 18; -fx-font-weight: 700;");

        Label effectsHeader = new Label("Sound Effects");
        effectsHeader.setStyle("-fx-text-fill: #00D9FF; -fx-font-family: 'Segoe UI', 'Arial'; -fx-font-size: 14; -fx-font-weight: 600;");

        ToggleButton effectsMuteButton = new ToggleButton(isEffectsMuted ? "Muted" : "Unmuted");
        effectsMuteButton.setSelected(isEffectsMuted);
        effectsMuteButton.setPrefWidth(160);
        styleModernToggle(effectsMuteButton);
        applyToggleState(effectsMuteButton);
        effectsMuteButton.setOnAction(event -> {
            isEffectsMuted = effectsMuteButton.isSelected();
            effectsMuteButton.setText(isEffectsMuted ? "Muted" : "Unmuted");
            updateAudioSettings();
        });

        VBox effectsBox = new VBox(10);
        effectsBox.setAlignment(Pos.CENTER);
        effectsBox.getChildren().addAll(effectsHeader, effectsMuteButton);

        Region separator1 = new Region();
        separator1.setPrefHeight(1);
        separator1.setMaxWidth(Double.MAX_VALUE);
        separator1.setStyle("-fx-background-color: rgba(100, 100, 255, 0.2);");

        Label musicHeader = new Label("Background Music");
        musicHeader.setStyle("-fx-text-fill: #00D9FF; -fx-font-family: 'Segoe UI', 'Arial'; -fx-font-size: 14; -fx-font-weight: 600;");

        ToggleButton musicMuteButton = new ToggleButton(isMusicMuted ? "Muted" : "Unmuted");
        musicMuteButton.setSelected(isMusicMuted);
        musicMuteButton.setPrefWidth(160);
        styleModernToggle(musicMuteButton);
        applyToggleState(musicMuteButton);
        musicMuteButton.setOnAction(event -> {
            isMusicMuted = musicMuteButton.isSelected();
            musicMuteButton.setText(isMusicMuted ? "Muted" : "Unmuted");
            updateAudioSettings();
        });

        VBox musicBox = new VBox(10);
        musicBox.setAlignment(Pos.CENTER);
        musicBox.getChildren().addAll(musicHeader, musicMuteButton);

        Region separator2 = new Region();
        separator2.setPrefHeight(1);
        separator2.setMaxWidth(Double.MAX_VALUE);
        separator2.setStyle("-fx-background-color: rgba(100, 100, 255, 0.2);");

        Label volumeHeader = new Label("Master Volume");
        volumeHeader.setStyle("-fx-text-fill: #00D9FF; -fx-font-family: 'Segoe UI', 'Arial'; -fx-font-size: 14; -fx-font-weight: 600;");

        Label volumeValue = new Label(String.format("%d%%", (int) (audioVolume * 100)));
        volumeValue.setStyle("-fx-text-fill: #FFFFFF; -fx-font-family: 'Segoe UI', 'Arial'; -fx-font-size: 13; -fx-font-weight: 600;");

        Slider volumeSlider = new Slider(0.0, 1.0, audioVolume);
        volumeSlider.setShowTickMarks(false);
        volumeSlider.setShowTickLabels(false);
        volumeSlider.setPrefWidth(240);
        volumeSlider.setStyle(
                "-fx-control-inner-background: rgba(40, 40, 60, 0.8);" +
                        "-fx-accent: linear-gradient(to right, #00D9FF, #0099FF);" +
                        "-fx-background-color: transparent;"
        );

        volumeSlider.valueProperty().addListener((obs, oldValue, newValue) -> {
            audioVolume = newValue.doubleValue();
            volumeValue.setText(String.format("%d%%", (int) (audioVolume * 100)));
            updateAudioSettings();
        });

        HBox volumeHeaderBox = new HBox(10);
        volumeHeaderBox.setAlignment(Pos.CENTER);
        volumeHeaderBox.getChildren().addAll(volumeHeader, volumeValue);

        VBox volumeBox = new VBox(10);
        volumeBox.setAlignment(Pos.CENTER);
        volumeBox.getChildren().addAll(volumeHeaderBox, volumeSlider);

        audioPanel.getChildren().addAll(
                audioSectionHeader,
                effectsBox,
                separator1,
                musicBox,
                separator2,
                volumeBox
        );

        // === RIGHT SIDE: DISPLAY & PREFERENCES SETTINGS ===
        VBox displayPanel = new VBox(18);
        displayPanel.setAlignment(Pos.TOP_CENTER);
        displayPanel.setPadding(new Insets(25, 30, 25, 30));
        displayPanel.setStyle("-fx-background-color: rgba(30, 30, 45, 0.7); -fx-background-radius: 15; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.4), 15, 0, 0, 5);");
        displayPanel.setPrefWidth(320);

        Label displaySectionHeader = new Label("Display & Preferences");
        displaySectionHeader.setStyle("-fx-text-fill: #FFD700; -fx-font-family: 'Segoe UI', 'Arial'; -fx-font-size: 18; -fx-font-weight: 700;");

        // Sprite Visibility Section
        Label spriteHeader = new Label("Character");
        spriteHeader.setStyle("-fx-text-fill: #00D9FF; -fx-font-family: 'Segoe UI', 'Arial'; -fx-font-size: 14; -fx-font-weight: 600;");

        ToggleButton spriteVisibilityButton = new ToggleButton(isSpriteVisible ? "Visible" : "Hidden");
        spriteVisibilityButton.setSelected(!isSpriteVisible);
        spriteVisibilityButton.setPrefWidth(160);
        styleModernToggle(spriteVisibilityButton);
        applyToggleState(spriteVisibilityButton);
        spriteVisibilityButton.setOnAction(event -> {
            isSpriteVisible = !spriteVisibilityButton.isSelected();
            spriteVisibilityButton.setText(isSpriteVisible ? "Visible" : "Hidden");
            updateSpriteVisibility();
            saveProgress();
        });

        VBox spriteBox = new VBox(10);
        spriteBox.setAlignment(Pos.CENTER);
        spriteBox.getChildren().addAll(spriteHeader, spriteVisibilityButton);

        // Separator
        Region separator3 = new Region();
        separator3.setPrefHeight(1);
        separator3.setMaxWidth(Double.MAX_VALUE);
        separator3.setStyle("-fx-background-color: rgba(100, 100, 255, 0.2);");

        // Back Confirmation Section
        Label backConfirmHeader = new Label("Back Button Confirmation");
        backConfirmHeader.setStyle("-fx-text-fill: #00D9FF; -fx-font-family: 'Segoe UI', 'Arial'; -fx-font-size: 14; -fx-font-weight: 600;");

        ToggleButton backConfirmButton = new ToggleButton(skipBackConfirmation ? "Disabled" : "Enabled");
        backConfirmButton.setSelected(skipBackConfirmation);
        backConfirmButton.setPrefWidth(160);
        styleModernToggle(backConfirmButton);
        applyToggleState(backConfirmButton);
        backConfirmButton.setOnAction(event -> {
            skipBackConfirmation = backConfirmButton.isSelected();
            backConfirmButton.setText(skipBackConfirmation ? "Disabled" : "Enabled");
            saveBackConfirmationPreference(skipBackConfirmation);
        });

        VBox backConfirmBox = new VBox(10);
        backConfirmBox.setAlignment(Pos.CENTER);
        backConfirmBox.getChildren().addAll(backConfirmHeader, backConfirmButton);

        displayPanel.getChildren().addAll(
                displaySectionHeader,
                spriteBox,
                separator3,
                backConfirmBox
        );

        contentLayout.getChildren().addAll(audioPanel, displayPanel);

        Button closeButton = new Button("Close");
        closeButton.setStyle("-fx-background-color: rgba(60, 60, 80, 0.5); -fx-border-color: rgba(100, 100, 120, 0.6); -fx-border-width: 1.5; -fx-border-radius: 10; -fx-background-radius: 10; -fx-text-fill: #AAAACC; -fx-font-family: 'Segoe UI', 'Arial'; -fx-font-size: 14; -fx-font-weight: 600; -fx-padding: 13 35; -fx-cursor: hand;");
        closeButton.setPrefWidth(200);
        closeButton.setOnAction(event -> audioSettingsStage.close());

        closeButton.setOnMouseEntered(event -> closeButton.setStyle("-fx-background-color: rgba(70, 70, 90, 0.7); -fx-border-color: rgba(120, 120, 140, 0.8); -fx-border-width: 1.5; -fx-border-radius: 10; -fx-background-radius: 10; -fx-text-fill: #CCCCEE; -fx-font-family: 'Segoe UI', 'Arial'; -fx-font-size: 14; -fx-font-weight: 600; -fx-padding: 13 35; -fx-cursor: hand;"));
        closeButton.setOnMouseExited(event -> closeButton.setStyle("-fx-background-color: rgba(60, 60, 80, 0.5); -fx-border-color: rgba(100, 100, 120, 0.6); -fx-border-width: 1.5; -fx-border-radius: 10; -fx-background-radius: 10; -fx-text-fill: #AAAACC; -fx-font-family: 'Segoe UI', 'Arial'; -fx-font-size: 14; -fx-font-weight: 600; -fx-padding: 13 35; -fx-cursor: hand;"));

        mainContainer.getChildren().addAll(titleLabel, contentLayout, closeButton);

        overlay.getChildren().add(mainContainer);
        StackPane.setAlignment(mainContainer, Pos.CENTER);

        Scene dialogScene = new Scene(overlay, 860, 660);
        dialogScene.setFill(Color.TRANSPARENT);

        audioSettingsStage.setScene(dialogScene);

        audioSettingsStage.setOnShown(e -> {
            double x = primaryStage.getX() + (primaryStage.getWidth() - audioSettingsStage.getWidth()) / 2;
            double y = primaryStage.getY() + (primaryStage.getHeight() - audioSettingsStage.getHeight()) / 2;
            audioSettingsStage.setX(x);
            audioSettingsStage.setY(y);
        });

        audioSettingsStage.show();
        audioSettingsStage.requestFocus();
    }

    // Add this new method to handle sprite visibility
    private void updateSpriteVisibility() {
        if (spriteContainer != null) {
            if (isSpriteVisible) {
                spriteContainer.setVisible(true);
                spriteContainer.setOpacity(0);
                FadeTransition fadeIn = new FadeTransition(Duration.millis(300), spriteContainer);
                fadeIn.setToValue(1.0);
                fadeIn.play();
            } else {
                FadeTransition fadeOut = new FadeTransition(Duration.millis(300), spriteContainer);
                fadeOut.setToValue(0);
                fadeOut.setOnFinished(e -> spriteContainer.setVisible(false));
                fadeOut.play();
            }
        }
    }

    private void closeAudioSettingsIfOpen() {
        if (audioSettingsStage != null && audioSettingsStage.isShowing()) {
            audioSettingsStage.close();
        }
    }

    // Helper method to apply the correct toggle state styling
    private void applyToggleState(ToggleButton button) {
        if (button.isSelected()) {
            button.setStyle(
                    "-fx-background-color: linear-gradient(to right, #FF4444, #CC0000);" +
                            "-fx-border-color: #FF6666;" +
                            "-fx-border-width: 2;" +
                            "-fx-border-radius: 10;" +
                            "-fx-background-radius: 10;" +
                            "-fx-text-fill: #FFFFFF;" +
                            "-fx-font-family: 'Segoe UI', 'Arial';" +
                            "-fx-font-size: 13;" +
                            "-fx-font-weight: 600;" +
                            "-fx-padding: 12 25;" +
                            "-fx-cursor: hand;"
            );
        } else {
            button.setStyle(
                    "-fx-background-color: rgba(100, 100, 255, 0.15);" +
                            "-fx-border-color: #6464FF;" +
                            "-fx-border-width: 2;" +
                            "-fx-border-radius: 10;" +
                            "-fx-background-radius: 10;" +
                            "-fx-text-fill: #A5A5FF;" +
                            "-fx-font-family: 'Segoe UI', 'Arial';" +
                            "-fx-font-size: 13;" +
                            "-fx-font-weight: 600;" +
                            "-fx-padding: 12 25;" +
                            "-fx-cursor: hand;"
            );
        }
    }

    // Helper method to style toggle buttons
    private void styleModernToggle(ToggleButton button) {
        button.setStyle(
                "-fx-background-color: rgba(100, 100, 255, 0.15);" +
                        "-fx-border-color: #6464FF;" +
                        "-fx-border-width: 2;" +
                        "-fx-border-radius: 10;" +
                        "-fx-background-radius: 10;" +
                        "-fx-text-fill: #A5A5FF;" +
                        "-fx-font-family: 'Segoe UI', 'Arial';" +
                        "-fx-font-size: 13;" +
                        "-fx-font-weight: 600;" +
                        "-fx-padding: 12 25;" +
                        "-fx-cursor: hand;"
        );

        button.selectedProperty().addListener((obs, wasSelected, isSelected) -> {
            if (isSelected) {
                button.setStyle(
                        "-fx-background-color: linear-gradient(to right, #FF4444, #CC0000);" +
                                "-fx-border-color: #FF6666;" +
                                "-fx-border-width: 2;" +
                                "-fx-border-radius: 10;" +
                                "-fx-background-radius: 10;" +
                                "-fx-text-fill: #FFFFFF;" +
                                "-fx-font-family: 'Segoe UI', 'Arial';" +
                                "-fx-font-size: 13;" +
                                "-fx-font-weight: 600;" +
                                "-fx-padding: 12 25;" +
                                "-fx-cursor: hand;"
                );
            } else {
                button.setStyle(
                        "-fx-background-color: rgba(100, 100, 255, 0.15);" +
                                "-fx-border-color: #6464FF;" +
                                "-fx-border-width: 2;" +
                                "-fx-border-radius: 10;" +
                                "-fx-background-radius: 10;" +
                                "-fx-text-fill: #A5A5FF;" +
                                "-fx-font-family: 'Segoe UI', 'Arial';" +
                                "-fx-font-size: 13;" +
                                "-fx-font-weight: 600;" +
                                "-fx-padding: 12 25;" +
                                "-fx-cursor: hand;"
                );
            }
        });

        button.setOnMouseEntered(e -> {
            if (button.isSelected()) {
                button.setStyle(
                        "-fx-background-color: linear-gradient(to right, #FF6666, #DD2222);" +
                                "-fx-border-color: #FF8888;" +
                                "-fx-border-width: 2;" +
                                "-fx-border-radius: 10;" +
                                "-fx-background-radius: 10;" +
                                "-fx-text-fill: #FFFFFF;" +
                                "-fx-font-family: 'Segoe UI', 'Arial';" +
                                "-fx-font-size: 13;" +
                                "-fx-font-weight: 600;" +
                                "-fx-padding: 12 25;" +
                                "-fx-cursor: hand;"
                );
            } else {
                button.setStyle(
                        "-fx-background-color: rgba(120, 120, 255, 0.25);" +
                                "-fx-border-color: #7D7DFF;" +
                                "-fx-border-width: 2;" +
                                "-fx-border-radius: 10;" +
                                "-fx-background-radius: 10;" +
                                "-fx-text-fill: #C5C5FF;" +
                                "-fx-font-family: 'Segoe UI', 'Arial';" +
                                "-fx-font-size: 13;" +
                                "-fx-font-weight: 600;" +
                                "-fx-padding: 12 25;" +
                                "-fx-cursor: hand;"
                );
            }
        });

        button.setOnMouseExited(e -> {
            if (button.isSelected()) {
                button.setStyle(
                        "-fx-background-color: linear-gradient(to right, #FF4444, #CC0000);" +
                                "-fx-border-color: #FF6666;" +
                                "-fx-border-width: 2;" +
                                "-fx-border-radius: 10;" +
                                "-fx-background-radius: 10;" +
                                "-fx-text-fill: #FFFFFF;" +
                                "-fx-font-family: 'Segoe UI', 'Arial';" +
                                "-fx-font-size: 13;" +
                                "-fx-font-weight: 600;" +
                                "-fx-padding: 12 25;" +
                                "-fx-cursor: hand;"
                );
            } else {
                button.setStyle(
                        "-fx-background-color: rgba(100, 100, 255, 0.15);" +
                                "-fx-border-color: #6464FF;" +
                                "-fx-border-width: 2;" +
                                "-fx-border-radius: 10;" +
                                "-fx-background-radius: 10;" +
                                "-fx-text-fill: #A5A5FF;" +
                                "-fx-font-family: 'Segoe UI', 'Arial';" +
                                "-fx-font-size: 13;" +
                                "-fx-font-weight: 600;" +
                                "-fx-padding: 12 25;" +
                                "-fx-cursor: hand;"
                );
            }
        });
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

    private byte[] getDefaultProfilePicBytes() {
        try {
            // Create a simple 50x50 PNG: Cyan circle (placeholder avatar)
            BufferedImage img = new BufferedImage(50, 50, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = img.createGraphics();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setColor(new java.awt.Color(0, 255, 255, 200)); // Semi-transparent cyan
            g2d.fillOval(5, 5, 40, 40); // Circle
            g2d.setColor(java.awt.Color.WHITE);
            g2d.setStroke(new BasicStroke(2));
            g2d.drawOval(5, 5, 40, 40); // White outline
            g2d.dispose();

            // Write to ByteArrayOutputStream as PNG
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(img, "png", baos);
            return baos.toByteArray();
        } catch (IOException e) {
            System.err.println("Failed to generate default profile pic: " + e.getMessage());
            return null; // Fallback to null if generation fails (rare)
        }
    }

    private void updateImageResources() {
        // Define folders (create if needed
        String imageFolderPath = "resources/images";
        File cacheFolder = new File(imageFolderPath);
        if (!cacheFolder.exists()) {
            cacheFolder.mkdirs();
        }

        // === 1. Download/Cache the OFFICIAL default profile picture (highest priority) ===
        File defaultProfileFile = new File(imageFolderPath + File.separator + "default_profile.png");
        if (!defaultProfileFile.exists()) {
            executor.submit(() -> {
                try {
                    System.out.println("Downloading official default profile picture...");
                    InputStream in = new URL("https://drive.google.com/uc?export=download&id=1m1yQ0995BXEa4tBMS_R2p9jY81uc4kAF").openStream();
                    Files.copy(in, Paths.get(defaultProfileFile.getPath()), StandardCopyOption.REPLACE_EXISTING);
                    in.close();
                    System.out.println("Official default profile picture successfully cached.");

                    // If the user is already logged in and we are showing a screen with the avatar,
                    // immediately refresh it to the newly downloaded (nicer) version
                    Platform.runLater(() -> {
                        if (currentProfileImage == null ||
                                currentProfileImage.isError() ||
                                currentProfileImage.getUrl() == null ||
                                currentProfileImage.getUrl().contains("ByteArrayInputStream")) {  // was using generated fallback

                            currentProfileImage = new Image(defaultProfileFile.toURI().toString(), 100, 100, true, true);
                            refreshProfileImageInUI();  // You already have this method from previous fix
                        }
                    });
                } catch (Exception e) {
                    System.err.println("Failed to download official default profile pic: " + e.getMessage());
                    // Not fatal - we still have the generated cyan circle as last resort
                }
            });
        }

        // === 2. Existing level background images (unchanged, just kept for context) ===
        Image[] levelImages = new Image[IMAGE_FILES.length];
        for (int i = 0; i < IMAGE_FILES.length; i++) {
            File imageFile = new File(imageFolderPath + File.separator + File.separator + IMAGE_FILES[i]);

            if (imageFile.exists()) {
                Image cachedImage = new Image(imageFile.toURI().toString());
                if (!cachedImage.isError()) {
                    levelImages[i] = cachedImage;
                    System.out.println("Loaded cached image: " + IMAGE_FILES[i]);
                } else {
                    System.err.println("Error loading cached image: " + IMAGE_FILES[i]);
                    levelImages[i] = null;
                }
            } else if (IMAGE_DOWNLOAD_URLS[i] != null) {
                try {
                    System.out.println("Downloading image: " + IMAGE_FILES[i]);
                    InputStream in = new URL(IMAGE_DOWNLOAD_URLS[i]).openStream();
                    Files.copy(in, Paths.get(imageFile.getPath()), StandardCopyOption.REPLACE_EXISTING);
                    in.close();

                    Image downloadedImage = new Image(imageFile.toURI().toString());
                    levelImages[i] = downloadedImage.isError() ? null : downloadedImage;
                } catch (IOException e) {
                    System.err.println("Failed to download image: " + IMAGE_FILES[i] + " - " + e.getMessage());
                    levelImages[i] = null;
                }
            } else {
                levelImages[i] = null;
            }
        }

        // Assign level images (unchanged)
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

        saveProgress();
    }

    private void cacheDefaultProfilePic() {
        File folder = new File("resources/images");
        if (!folder.exists()) {
            folder.mkdirs();
        }

        File defaultFile = new File("resources/images/default_profile.png");

        if (!defaultFile.exists()) {
            executor.submit(() -> {
                try {
                    System.out.println("Downloading official default profile picture...");
                    InputStream in = new URL("https://drive.google.com/uc?export=download&id=1m1yQ0995BXEa4tBMS_R2p9jY81uc4kAF").openStream();
                    Files.copy(in, Paths.get(defaultFile.getPath()), StandardCopyOption.REPLACE_EXISTING);
                    in.close();
                    System.out.println("Official default profile picture downloaded.");

                    Platform.runLater(() -> {
                        currentProfileImage = new Image(defaultFile.toURI().toString(), 100, 100, true, true);
                        refreshProfileImageInUI();  // Now this will work and instantly update the avatar if UI is already visible
                    });
                } catch (Exception e) {
                    System.err.println("Failed to download default profile pic: " + e.getMessage());
                }
            });
        }
    }

    private void refreshProfileImageInUI() {
        // Update level select avatar if it exists
        if (levelSelectProfileView != null) {
            levelSelectProfileView.setImage(currentProfileImage);
        }

        // Update settings dialog avatar if the dialog is currently open
        if (settingsProfileView != null) {
            settingsProfileView.setImage(currentProfileImage);
        }
    }

    private void setTargetColor(int level) {
        switch (level) {
            case 1:
                targetColor = ColorType.YELLOW;
                break;
            case 2:
                targetColor = ColorType.GREEN;
                break;
            case 3:
                targetColor = ColorType.RED;
                break;
            case 4:
                targetColor = ColorType.BLUE;
                break;
            case 5:
                targetColor = ColorType.RED;
                break;
            case 6:
                targetColor = ColorType.RED;
                break;
            case 7:
                targetColor = ColorType.GREEN;
                break;
            case 8:
                targetColor = ColorType.GREEN;
                break;
            case 9:
                targetColor = ColorType.BLUE;
                break;
            case 10:
                targetColor = ColorType.YELLOW;
                break;
            default:
                targetColor = ColorType.YELLOW;
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
                Tile tile = new Tile(60, 60, initialColor, gameInstance);
                tiles[row][col] = tile;
                grid.add(tile, col, row);
            }
        }
        grid.setHgap(6);
        grid.setVgap(6);
        // Inside initializeGrid method, update the switch:
        switch (level) {
            case 1:
                movesRemaining.set(4 + extraMoves);
                break;
            case 2:
                movesRemaining.set(4 + extraMoves);
                break;
            case 3:
                movesRemaining.set(4 + extraMoves);
                break;
            case 4:
                movesRemaining.set(4 + extraMoves);
                break;
            case 5:
                movesRemaining.set(3 + extraMoves);
                break;
            case 6:
                movesRemaining.set(7 + extraMoves);
                break;
            case 7:
                movesRemaining.set(5 + extraMoves);
                break;
            case 8:
                movesRemaining.set(4 + extraMoves);
                break;
            case 9:
                movesRemaining.set(4 + extraMoves);
                break;
            case 10:
                movesRemaining.set(5 + extraMoves);
                break;
            default:
                movesRemaining.set(4 + extraMoves);
        }
        setTargetColor(level);
    }

    private ColorType charToColor(char c) {
        switch (c) {
            case 'R':
                return ColorType.RED;
            case 'B':
                return ColorType.BLUE;
            case 'Y':
                return ColorType.YELLOW;
            case 'G':
                return ColorType.GREEN;
            case 'O':
                return ColorType.OBSTACLE;  // New case for obstacles
            default:
                return ColorType.GREEN;
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

    private void showNeonSuccessPage(int current, double completionTime) {
        // Clear the entire root and create a full-page success screen
        root.getChildren().clear();
        root.setStyle("-fx-background-color: radial-gradient(center 50% 50%, radius 80%, #0A0E26, #000000);");

        // Main container
        VBox container = new VBox(40);
        container.setAlignment(Pos.CENTER);
        container.setPadding(new Insets(60));

        // Victory title with neon effect - matching button style
        Label titleLabel = new Label("LEVEL COMPLETE");
        titleLabel.setStyle(
                "-fx-font-family: 'Arial Black';" +
                        "-fx-font-size: 64;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: " + toRgbString(targetColor.getFxColor()) + ";"
        );

        // Add glow effect like the buttons
        DropShadow neonGlow = new DropShadow();
        neonGlow.setColor(targetColor.getFxColor());
        titleLabel.setEffect(neonGlow);

        // Pulsing animation for title
        ScaleTransition pulse = new ScaleTransition(Duration.millis(1000), titleLabel);
        pulse.setFromX(1.0);
        pulse.setFromY(1.0);
        pulse.setToX(1.15);
        pulse.setToY(1.15);
        pulse.setAutoReverse(true);
        pulse.setCycleCount(Animation.INDEFINITE);
        pulse.play();

        // Decorative circles animation
        HBox topCircles = new HBox(20);
        topCircles.setAlignment(Pos.CENTER);
        for (int i = 0; i < 7; i++) {
            Circle circle = new Circle(10);
            circle.setFill(Color.TRANSPARENT);
            circle.setStroke(targetColor.getFxColor());
            circle.setStrokeWidth(3);

            DropShadow circleGlow = new DropShadow();
            circleGlow.setColor(targetColor.getFxColor());
            circleGlow.setRadius(20);
            circleGlow.setSpread(0.7);
            circle.setEffect(circleGlow);

            // Staggered scale animation
            ScaleTransition scaleAnim = new ScaleTransition(Duration.millis(800), circle);
            scaleAnim.setFromX(0.5);
            scaleAnim.setFromY(0.5);
            scaleAnim.setToX(1.5);
            scaleAnim.setToY(1.5);
            scaleAnim.setAutoReverse(true);
            scaleAnim.setCycleCount(Animation.INDEFINITE);
            scaleAnim.setDelay(Duration.millis(i * 100));
            scaleAnim.play();

            topCircles.getChildren().add(circle);
        }

        // Stats panel with neon border
        VBox statsPanel = new VBox(20);
        statsPanel.setAlignment(Pos.CENTER);
        statsPanel.setPadding(new Insets(40));
        statsPanel.setMaxWidth(600);
        statsPanel.setStyle(
                "-fx-background-color: rgba(10, 14, 38, 0.7);" +
                        "-fx-background-radius: 20;" +
                        "-fx-border-color: " + toRgbString(targetColor.getFxColor()) + ";" +
                        "-fx-border-width: 3;" +
                        "-fx-border-radius: 20;"
        );

        DropShadow panelGlow = new DropShadow();
        panelGlow.setColor(targetColor.getFxColor());
        panelGlow.setRadius(30);
        panelGlow.setSpread(0.5);
        statsPanel.setEffect(panelGlow);

        // Level info
        Label levelInfoLabel = new Label("LEVEL " + current);
        levelInfoLabel.setStyle(
                "-fx-font-family: 'Arial Black';" +
                        "-fx-font-size: 36;" +
                        "-fx-text-fill: #FFFFFF;" +
                        "-fx-font-weight: bold;"
        );
        DropShadow levelGlow = new DropShadow();
        levelGlow.setColor(Color.rgb(0, 255, 255));
        levelGlow.setRadius(15);
        levelInfoLabel.setEffect(levelGlow);

        // Time display
        Label timeLabel = new Label("TIME: " + String.format("%.2f", completionTime) + "s");
        timeLabel.setStyle(
                "-fx-font-family: 'Arial';" +
                        "-fx-font-size: 28;" +
                        "-fx-text-fill: #00FFFF;" +
                        "-fx-font-weight: bold;"
        );
        DropShadow timeGlow = new DropShadow();
        timeGlow.setColor(Color.rgb(0, 255, 255));
        timeGlow.setRadius(10);
        timeLabel.setEffect(timeGlow);

        // Target color display
        HBox targetDisplay = new HBox(15);
        targetDisplay.setAlignment(Pos.CENTER);
        Label targetTextLabel = new Label("TARGET COLOR:");
        targetTextLabel.setStyle(
                "-fx-font-family: 'Arial';" +
                        "-fx-font-size: 24;" +
                        "-fx-text-fill: #FFFFFF;"
        );
        Circle targetCircle = new Circle(20, targetColor.getFxColor());
        DropShadow targetGlow = new DropShadow();
        targetGlow.setColor(targetColor.getFxColor());
        targetGlow.setRadius(25);
        targetGlow.setSpread(0.8);
        targetCircle.setEffect(targetGlow);

        // Rotate animation for target circle
        RotateTransition rotate = new RotateTransition(Duration.millis(3000), targetCircle);
        rotate.setByAngle(360);
        rotate.setCycleCount(Animation.INDEFINITE);
        rotate.play();

        targetDisplay.getChildren().addAll(targetTextLabel, targetCircle);

        // Content message
        String contentText;
        if (current == LEVEL_PATTERNS.length) {
            contentText = "🎉 CONGRATULATIONS! 🎉\n\nYou have conquered all " + LEVEL_PATTERNS.length + " levels!\n\nYou are a true Chroma Flood master!";
        } else {
            contentText = "All tiles flooded!\nMoving to the next challenge...";
        }

        Label contentLabel = new Label(contentText);
        contentLabel.setStyle(
                "-fx-font-family: 'Arial';" +
                        "-fx-font-size: 20;" +
                        "-fx-text-fill: #FFFFFF;" +
                        "-fx-text-alignment: center;"
        );
        contentLabel.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        contentLabel.setWrapText(true);
        contentLabel.setWrapText(true);
        contentLabel.setMaxWidth(550);           // ADD THIS LINE HERE
        contentLabel.setAlignment(Pos.CENTER);

        statsPanel.getChildren().addAll(levelInfoLabel, timeLabel, targetDisplay, contentLabel);

        // Buttons container with spacing
        HBox buttonBox = new HBox(30);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(20));

        // Create neon buttons
        Button nextButton = createNeonButton(
                current == LEVEL_PATTERNS.length ? "MAIN MENU" : "NEXT LEVEL",
                Color.rgb(0, 255, 100),
                200
        );
        nextButton.setFocusTraversable(false);  // ADD THIS LINE HERE

        Button replayButton = createNeonButton(
                "REPLAY",
                Color.rgb(255, 200, 0),
                200
        );
        replayButton.setFocusTraversable(false);  // ADD THIS LINE HERE

        Button selectButton = createNeonButton(
                "LEVEL SELECT",
                Color.rgb(100, 150, 255),
                200
        );
        selectButton.setFocusTraversable(false);  // ADD THIS LINE HERE

// Button actions
        int nextLevel = current + 1;

        nextButton.setOnAction(e -> {
            if (current < LEVEL_PATTERNS.length) {
                currentLevel.set(nextLevel);
                setupGameplayScreen();
            } else {
                showCompletionScreen();
            }
        });

        replayButton.setOnAction(e -> {
            levelStartTime = System.currentTimeMillis();
            setupGameplayScreen();
        });

        selectButton.setOnAction(e -> {
            showLevelSelectScreen();
        });

        buttonBox.getChildren().addAll(nextButton, replayButton, selectButton);

        // Bottom decorative circles
        HBox bottomCircles = new HBox(20);
        bottomCircles.setAlignment(Pos.CENTER);
        for (int i = 0; i < 5; i++) {
            Circle circle = new Circle(8);
            circle.setFill(targetColor.getFxColor());
            circle.setOpacity(0.6);

            DropShadow circleGlow = new DropShadow();
            circleGlow.setColor(targetColor.getFxColor());
            circleGlow.setRadius(15);
            circleGlow.setSpread(0.6);
            circle.setEffect(circleGlow);

            // Fade animation
            FadeTransition fade = new FadeTransition(Duration.millis(1000), circle);
            fade.setFromValue(0.3);
            fade.setToValue(1.0);
            fade.setAutoReverse(true);
            fade.setCycleCount(Animation.INDEFINITE);
            fade.setDelay(Duration.millis(i * 200));
            fade.play();

            bottomCircles.getChildren().add(circle);
        }

        // Assemble container
        container.getChildren().addAll(titleLabel, topCircles, statsPanel, buttonBox, bottomCircles);

        // Add to root as CENTER
        root.setCenter(container);
        BorderPane.setAlignment(container, Pos.CENTER);

        // Entrance animation for entire page
        container.setOpacity(0);
        container.setScaleX(0.8);
        container.setScaleY(0.8);

        FadeTransition fadeIn = new FadeTransition(Duration.millis(600), container);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);

        ScaleTransition scaleIn = new ScaleTransition(Duration.millis(600), container);
        scaleIn.setFromX(0.8);
        scaleIn.setFromY(0.8);
        scaleIn.setToX(1.0);
        scaleIn.setToY(1.0);

        ParallelTransition entrance = new ParallelTransition(fadeIn, scaleIn);
        entrance.play();
    }

    private Button createNeonButton(String text, Color neonColor, double width) {
        Button button = new Button(text);
        button.setPrefWidth(width);
        button.setPrefHeight(55);

        String colorString = toRgbString(neonColor);
        button.setStyle(
                "-fx-background-color: transparent;" +
                        "-fx-border-color: " + colorString + ";" +
                        "-fx-border-width: 3;" +
                        "-fx-border-radius: 30;" +
                        "-fx-background-radius: 30;" +
                        "-fx-text-fill: " + colorString + ";" +
                        "-fx-font-family: 'Arial Black';" +
                        "-fx-font-size: 18;" +
                        "-fx-font-weight: bold;" +
                        "-fx-cursor: hand;"
        );

        DropShadow buttonGlow = new DropShadow();
        buttonGlow.setColor(neonColor);
        buttonGlow.setRadius(25);
        buttonGlow.setSpread(0.5);
        button.setEffect(buttonGlow);

        // Hover effects
        button.setOnMouseEntered(e -> {
            button.setStyle(
                    "-fx-background-color: " + toRgbString(Color.rgb(
                            (int) (neonColor.getRed() * 255 * 0.3),
                            (int) (neonColor.getGreen() * 255 * 0.3),
                            (int) (neonColor.getBlue() * 255 * 0.3)
                    )) + ";" +
                            "-fx-border-color: " + colorString + ";" +
                            "-fx-border-width: 4;" +
                            "-fx-border-radius: 30;" +
                            "-fx-background-radius: 30;" +
                            "-fx-text-fill: #FFFFFF;" +
                            "-fx-font-family: 'Arial Black';" +
                            "-fx-font-size: 18;" +
                            "-fx-font-weight: bold;" +
                            "-fx-cursor: hand;"
            );

            DropShadow hoverGlow = new DropShadow();
            hoverGlow.setColor(neonColor);
            hoverGlow.setRadius(35);
            hoverGlow.setSpread(0.7);
            button.setEffect(hoverGlow);

            ScaleTransition scale = new ScaleTransition(Duration.millis(100), button);
            scale.setToX(1.08);
            scale.setToY(1.08);
            scale.play();
        });

        button.setOnMouseExited(e -> {
            button.setStyle(
                    "-fx-background-color: transparent;" +
                            "-fx-border-color: " + colorString + ";" +
                            "-fx-border-width: 3;" +
                            "-fx-border-radius: 30;" +
                            "-fx-background-radius: 30;" +
                            "-fx-text-fill: " + colorString + ";" +
                            "-fx-font-family: 'Arial Black';" +
                            "-fx-font-size: 18;" +
                            "-fx-font-weight: bold;" +
                            "-fx-cursor: hand;"
            );

            DropShadow normalGlow = new DropShadow();
            normalGlow.setColor(neonColor);
            normalGlow.setRadius(25);
            normalGlow.setSpread(0.5);
            button.setEffect(normalGlow);

            ScaleTransition scale = new ScaleTransition(Duration.millis(100), button);
            scale.setToX(1.0);
            scale.setToY(1.0);
            scale.play();
        });

        return button;
    }

    private String toRgbString(Color color) {
        return String.format("rgb(%d, %d, %d)",
                (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255)
        );
    }

    // Modified checkSuccess method - replace the success Platform.runLater block with:
    private void checkSuccess() {
        if (isCheckingSuccess || isTransitioning || justHandledGameEnd) {
            return;
        }

        // NEW: Don't check success if connection dialog is showing
        if (connectionLostStage != null && connectionLostStage.isShowing()) {
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
                    // NEW: Double-check before showing success
                    if (connectionLostStage != null && connectionLostStage.isShowing()) {
                        System.out.println("Connection dialog is open, delaying success screen");
                        return;
                    }

                    System.out.println("Showing success page for level " + currentLevel.get());
                    double completionTime = (System.currentTimeMillis() - levelStartTime) / 1000.0;
                    int current = currentLevel.get();
                    int nextLevel = current + 1;

                    // Submit score to Supabase
                    submitOnlineScore(currentUser, current, completionTime);

                    // Unlock next level
                    if (current < LEVEL_PATTERNS.length && !unlockedLevels.contains(nextLevel)) {
                        unlockedLevels.add(nextLevel);
                        saveProfileToSupabase();
                        System.out.println("Unlocked level " + nextLevel + " and saved to Supabase");
                    }

                    // Reset state
                    lossCount = 0;
                    extraMoves = 0;
                    selectedColor = ColorType.BLUE;

                    // Close ALL open dialogs before showing success page
                    closeAudioSettingsIfOpen();
                    if (profileSettingsStage != null && profileSettingsStage.isShowing()) {
                        profileSettingsStage.close();
                    }
                    // Close connection modal if it's open
                    if (connectionLostStage != null && connectionLostStage.isShowing()) {
                        connectionLostStage.close();
                        connectionLostStage = null;
                    }
                    // Stop reconnect timer if running
                    if (reconnectCheckTimer != null) {
                        reconnectCheckTimer.stop();
                        reconnectCheckTimer = null;
                    }

                    // Animate grid color drain before showing success page
                    animateGridColorDrain(() -> {
                        showNeonSuccessPage(current, completionTime);
                    });
                });
            } else if (movesRemaining.get() <= 0) {
                Platform.runLater(() -> {
                    // NEW: Double-check before showing failure
                    if (connectionLostStage != null && connectionLostStage.isShowing()) {
                        System.out.println("Connection dialog is open, delaying failure modal");
                        return;
                    }

                    System.out.println("No moves remaining → showing neon modal");

                    // Close connection modal before showing failure modal
                    if (connectionLostStage != null && connectionLostStage.isShowing()) {
                        connectionLostStage.close();
                        connectionLostStage = null;
                    }
                    // Stop reconnect timer if running
                    if (reconnectCheckTimer != null) {
                        reconnectCheckTimer.stop();
                        reconnectCheckTimer = null;
                    }

                    showFailureModal();
                });
            }
        } finally {
            isCheckingSuccess = false;
        }
    }

    private void animateGridColorDrain(Runnable onComplete) {
        isTransitioning = true;
        ParallelTransition drain = new ParallelTransition();

        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                Tile tile = tiles[row][col];

                // DISABLE MOUSE INTERACTION DURING ANIMATION
                tile.setMouseTransparent(true);

                // Slight stagger based on position
                double delay = (row + col) * 15;

                // Color glow transition with target color
                Timeline colorTransition = new Timeline(
                        new KeyFrame(Duration.millis(delay), e -> {
                            Glow glow = new Glow(0.8);
                            tile.setEffect(glow);
                        }),
                        new KeyFrame(Duration.millis(delay + 150), e -> {
                            Glow glow = new Glow(1.0);
                            tile.setEffect(glow);
                        }),
                        new KeyFrame(Duration.millis(delay + 300), e -> {
                            tile.setEffect(null);
                        })
                );

                // Shrink to tiny dot
                ScaleTransition shrink = new ScaleTransition(Duration.millis(300), tile);
                shrink.setDelay(Duration.millis(delay));
                shrink.setToX(0.05);
                shrink.setToY(0.05);
                shrink.setInterpolator(Interpolator.EASE_IN);

                // Fade out
                FadeTransition fade = new FadeTransition(Duration.millis(300), tile);
                fade.setDelay(Duration.millis(delay));
                fade.setToValue(0);

                drain.getChildren().addAll(colorTransition, shrink, fade);
            }
        }

        drain.setOnFinished(e -> {
            isTransitioning = false;
            onComplete.run();
        });

        drain.play();
    }

    private void showFailureModal() {
        // Close any open dialogs first
        if (audioSettingsStage != null && audioSettingsStage.isShowing()) {
            audioSettingsStage.close();
        }
        if (profileSettingsStage != null && profileSettingsStage.isShowing()) {
            profileSettingsStage.close();
        }
        if (leaderboardStage != null && leaderboardStage.isShowing()) {
            leaderboardStage.close();
        }

        justHandledGameEnd = true;

        Stage modal = new Stage();
        modal.initOwner(primaryStage);
        modal.initModality(Modality.WINDOW_MODAL);
        modal.initStyle(StageStyle.TRANSPARENT);

        // Outer container for RGB border animation
        StackPane borderContainer = new StackPane();
        borderContainer.setStyle("-fx-background-color: transparent;");

        // Animated RGB border using rectangles
        Rectangle leftBorder = new Rectangle(3, 0);
        leftBorder.setFill(Color.RED);
        StackPane.setAlignment(leftBorder, Pos.CENTER_LEFT);

        Rectangle rightBorder = new Rectangle(3, 0);
        rightBorder.setFill(Color.BLUE);
        StackPane.setAlignment(rightBorder, Pos.CENTER_RIGHT);

        Rectangle topBorder = new Rectangle(0, 3);
        topBorder.setFill(Color.GREEN);
        StackPane.setAlignment(topBorder, Pos.TOP_CENTER);

        Rectangle bottomBorder = new Rectangle(0, 3);
        bottomBorder.setFill(Color.MAGENTA);
        StackPane.setAlignment(bottomBorder, Pos.BOTTOM_CENTER);

        // Main container with animated background
        VBox card = new VBox(25);
        card.setAlignment(Pos.CENTER);
        card.setPadding(new Insets(60, 90, 60, 90));
        card.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #0a0e1a 0%, #1a1f35 100%);" +
                        "-fx-effect: dropshadow(gaussian, rgba(255,0,255,0.25), 12, 0.4, 0, 0)," +
                        "dropshadow(gaussian, rgba(0,255,255,0.2), 15, 0.3, 0, 0);"
        );

        // RGB color animation timeline
        Timeline rgbAnimation = new Timeline(
                new KeyFrame(Duration.ZERO,
                        new KeyValue(leftBorder.fillProperty(), Color.rgb(255, 0, 0)),
                        new KeyValue(rightBorder.fillProperty(), Color.rgb(255, 0, 0)),
                        new KeyValue(topBorder.fillProperty(), Color.rgb(255, 0, 0)),
                        new KeyValue(bottomBorder.fillProperty(), Color.rgb(255, 0, 0))
                ),
                new KeyFrame(Duration.seconds(1),
                        new KeyValue(leftBorder.fillProperty(), Color.rgb(255, 255, 0)),
                        new KeyValue(rightBorder.fillProperty(), Color.rgb(255, 255, 0)),
                        new KeyValue(topBorder.fillProperty(), Color.rgb(255, 255, 0)),
                        new KeyValue(bottomBorder.fillProperty(), Color.rgb(255, 255, 0))
                ),
                new KeyFrame(Duration.seconds(2),
                        new KeyValue(leftBorder.fillProperty(), Color.rgb(0, 255, 0)),
                        new KeyValue(rightBorder.fillProperty(), Color.rgb(0, 255, 0)),
                        new KeyValue(topBorder.fillProperty(), Color.rgb(0, 255, 0)),
                        new KeyValue(bottomBorder.fillProperty(), Color.rgb(0, 255, 0))
                ),
                new KeyFrame(Duration.seconds(3),
                        new KeyValue(leftBorder.fillProperty(), Color.rgb(0, 255, 255)),
                        new KeyValue(rightBorder.fillProperty(), Color.rgb(0, 255, 255)),
                        new KeyValue(topBorder.fillProperty(), Color.rgb(0, 255, 255)),
                        new KeyValue(bottomBorder.fillProperty(), Color.rgb(0, 255, 255))
                ),
                new KeyFrame(Duration.seconds(4),
                        new KeyValue(leftBorder.fillProperty(), Color.rgb(0, 0, 255)),
                        new KeyValue(rightBorder.fillProperty(), Color.rgb(0, 0, 255)),
                        new KeyValue(topBorder.fillProperty(), Color.rgb(0, 0, 255)),
                        new KeyValue(bottomBorder.fillProperty(), Color.rgb(0, 0, 255))
                ),
                new KeyFrame(Duration.seconds(5),
                        new KeyValue(leftBorder.fillProperty(), Color.rgb(255, 0, 255)),
                        new KeyValue(rightBorder.fillProperty(), Color.rgb(255, 0, 255)),
                        new KeyValue(topBorder.fillProperty(), Color.rgb(255, 0, 255)),
                        new KeyValue(bottomBorder.fillProperty(), Color.rgb(255, 0, 255))
                ),
                new KeyFrame(Duration.seconds(6),
                        new KeyValue(leftBorder.fillProperty(), Color.rgb(255, 0, 0)),
                        new KeyValue(rightBorder.fillProperty(), Color.rgb(255, 0, 0)),
                        new KeyValue(topBorder.fillProperty(), Color.rgb(255, 0, 0)),
                        new KeyValue(bottomBorder.fillProperty(), Color.rgb(255, 0, 0))
                )
        );
        rgbAnimation.setCycleCount(Timeline.INDEFINITE);
        rgbAnimation.play();

        // Add glow effects to borders
        leftBorder.setEffect(new javafx.scene.effect.DropShadow(10.0, Color.rgb(255, 0, 0)));
        rightBorder.setEffect(new javafx.scene.effect.DropShadow(10.0, Color.rgb(255, 0, 0)));
        topBorder.setEffect(new javafx.scene.effect.DropShadow(10.0, Color.rgb(255, 0, 0)));
        bottomBorder.setEffect(new javafx.scene.effect.DropShadow(10.0, Color.rgb(255, 0, 0)));

        borderContainer.getChildren().addAll(card, leftBorder, rightBorder, topBorder, bottomBorder);

        // Glowing title
        Label title = new Label("⚡ NO MOVES LEFT ⚡");
        title.setStyle(
                "-fx-text-fill: #DD00DD;" +
                        "-fx-font-family: 'Arial Black', 'Impact';" +
                        "-fx-font-size: 36;" +
                        "-fx-font-weight: bold;" +
                        "-fx-effect: dropshadow(gaussian, #DD00DD, 5, 0.3, 0, 0);"
        );

        // Level info
        Label info = new Label("LEVEL " + currentLevel.get() + " • MISSION FAILED");
        info.setStyle(
                "-fx-text-fill: #00CCCC;" +
                        "-fx-font-size: 20;" +
                        "-fx-font-weight: bold;" +
                        "-fx-effect: dropshadow(gaussian, #00CCCC, 4, 0.3, 0, 0);"
        );

        VBox content = new VBox(30);
        content.setAlignment(Pos.CENTER);

        // Count this loss
        lossCount++;

        // Offer +1 move after every 3 losses
        if (lossCount >= 3 && extraMoves < 3) {
            VBox offerBox = new VBox(20);
            offerBox.setAlignment(Pos.CENTER);
            offerBox.setPadding(new Insets(30));
            offerBox.setStyle(
                    "-fx-background-color: rgba(255, 0, 255, 0.08);" +
                            "-fx-border-color: #DD00DD;" +
                            "-fx-border-width: 2;" +
                            "-fx-effect: dropshadow(gaussian, #DD00DD, 5, 0.3, 0, 0);"
            );

            Label offerTitle = new Label("🎁 SPECIAL OFFER 🎁");
            offerTitle.setStyle(
                    "-fx-text-fill: #DDDD00;" +
                            "-fx-font-size: 24;" +
                            "-fx-font-weight: bold;" +
                            "-fx-effect: dropshadow(gaussian, #DDDD00, 4, 0.3, 0, 0);"
            );

            Label offerDesc = new Label("Get +1 Extra Move\nto complete this level!");
            offerDesc.setStyle(
                    "-fx-text-fill: #FFFFFF;" +
                            "-fx-font-size: 18;" +
                            "-fx-line-spacing: 5;"
            );
            offerDesc.setTextAlignment(TextAlignment.CENTER);

            HBox buttons = new HBox(25);
            buttons.setAlignment(Pos.CENTER);

            Button yes = createNeonButton("✓ YES PLEASE", "#00FF88", "#00AA55");
            Button no = createNeonButton("✗ NO THANKS", "#FF5555", "#AA0000");

            yes.setOnAction(e -> {
                extraMoves++;
                lossCount = 0;
                justHandledGameEnd = false;
                modal.close();
                initializeGrid(this, currentLevel.get());
                updatePaletteVisuals();
            });

            no.setOnAction(e -> {
                lossCount = 0;
                justHandledGameEnd = false;
                modal.close();
                initializeGrid(this, currentLevel.get());
                updatePaletteVisuals();
            });

            buttons.getChildren().addAll(yes, no);
            offerBox.getChildren().addAll(offerTitle, offerDesc, buttons);
            content.getChildren().add(offerBox);

        } else {
            Button restart = createNeonButton("↻ RESTART LEVEL", "#4488FF", "#2255CC");
            restart.setOnAction(e -> {
                justHandledGameEnd = false;
                modal.close();
                initializeGrid(this, currentLevel.get());
                updatePaletteVisuals();
            });
            content.getChildren().add(restart);
        }

        card.getChildren().addAll(title, info, content);

        Scene scene = new Scene(borderContainer);
        scene.setFill(Color.TRANSPARENT);
        modal.setScene(scene);

        // Perfect centering
        modal.setOnShown(e -> {
            leftBorder.setHeight(card.getHeight());
            rightBorder.setHeight(card.getHeight());
            topBorder.setWidth(card.getWidth());
            bottomBorder.setWidth(card.getWidth());

            double x = primaryStage.getX() + (primaryStage.getWidth() - modal.getWidth()) / 2;
            double y = primaryStage.getY() + (primaryStage.getHeight() - modal.getHeight()) / 2;
            modal.setX(x);
            modal.setY(y);
        });

        modal.show();
    }

    private Button createNeonButton(String text, String mainColor, String darkColor) {
        Button b = new Button(text);
        b.setMinWidth(180);
        b.setCursor(javafx.scene.Cursor.HAND);

        String normalStyle =
                "-fx-background-color: linear-gradient(to bottom, " + mainColor + ", " + darkColor + ");" +
                        "-fx-text-fill: white;" +
                        "-fx-font-family: 'Arial Black', 'Impact';" +
                        "-fx-font-size: 18;" +
                        "-fx-font-weight: bold;" +
                        "-fx-padding: 16 40;" +
                        "-fx-effect: dropshadow(gaussian, " + mainColor + ", 8, 0.5, 0, 0);";

        String hoverStyle =
                "-fx-background-color: linear-gradient(to bottom, derive(" + mainColor + ", 30%), " + mainColor + ");" +
                        "-fx-text-fill: white;" +
                        "-fx-font-family: 'Arial Black', 'Impact';" +
                        "-fx-font-size: 18;" +
                        "-fx-font-weight: bold;" +
                        "-fx-padding: 16 40;" +
                        "-fx-effect: dropshadow(gaussian, " + mainColor + ", 12, 0.7, 0, 0)," +
                        "dropshadow(gaussian, white, 5, 0.3, 0, 0);";

        b.setStyle(normalStyle);
        b.setOnMouseEntered(e -> b.setStyle(hoverStyle));
        b.setOnMouseExited(e -> b.setStyle(normalStyle));

        return b;
    }

    public static void main(String[] args) {
        launch(args);
    }
}