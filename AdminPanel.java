import javafx.animation.*;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.*;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class AdminPanel {
    private final ChromaFloodSystem game;

    public AdminPanel(ChromaFloodSystem game) {
        this.game = game;
    }

    public void show() {
        game.isOnLevelSelectScreen = false;
        game.root.getChildren().clear();
        game.root.setStyle("-fx-background-color: #0A0E26;");
        game.primaryStage.setTitle("Chroma Flood - ADMIN PANEL");

        // Create animated background layer
        Canvas backgroundCanvas = new Canvas();
        GraphicsContext gc = backgroundCanvas.getGraphicsContext2D();

        // Bind canvas size to window size
        backgroundCanvas.widthProperty().bind(game.primaryStage.widthProperty());
        backgroundCanvas.heightProperty().bind(game.primaryStage.heightProperty());

        // Animated background with circuit board theme for admin
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
                time += delta * 0.5;

                double width = backgroundCanvas.getWidth();
                double height = backgroundCanvas.getHeight();

                // Dark gradient background
                gc.setFill(new LinearGradient(0, 0, 0, height, false,
                        CycleMethod.NO_CYCLE,
                        new Stop(0, Color.rgb(15, 15, 30, 1.0)),
                        new Stop(1, Color.rgb(30, 15, 30, 1.0))));
                gc.fillRect(0, 0, width, height);

                // Draw circuit board lines - horizontal
                gc.setStroke(Color.rgb(255, 51, 102, 0.2));
                gc.setLineWidth(2);
                for (int i = 0; i < 15; i++) {
                    double y = (height / 15) * i;
                    double offset = Math.sin(time + i * 0.5) * 10;
                    gc.strokeLine(0, y + offset, width, y + offset);
                }

                // Draw circuit board lines - vertical
                gc.setStroke(Color.rgb(102, 51, 255, 0.15));
                gc.setLineWidth(2);
                for (int i = 0; i < 20; i++) {
                    double x = (width / 20) * i;
                    double offset = Math.cos(time + i * 0.3) * 10;
                    gc.strokeLine(x + offset, 0, x + offset, height);
                }

                // Draw pulsing nodes at intersections
                for (int row = 0; row < 15; row++) {
                    for (int col = 0; col < 20; col++) {
                        if ((row + col) % 3 == 0) {
                            double x = (width / 20) * col + Math.cos(time + col * 0.3) * 10;
                            double y = (height / 15) * row + Math.sin(time + row * 0.5) * 10;
                            double pulse = Math.abs(Math.sin(time + row + col));
                            double size = 4 + pulse * 3;

                            gc.setFill(Color.rgb(255, 51, 102, 0.4 + pulse * 0.3));
                            gc.fillOval(x - size / 2, y - size / 2, size, size);
                        }
                    }
                }

                // Draw scanning lines
                double scanLine1 = (time * 100) % (height + 200) - 100;
                double scanLine2 = (time * 150 + height / 2) % (height + 200) - 100;

                gc.setStroke(Color.rgb(255, 51, 102, 0.4));
                gc.setLineWidth(3);
                gc.strokeLine(0, scanLine1, width, scanLine1);

                // Glowing gradient for scan line
                gc.setStroke(new LinearGradient(0, scanLine1 - 10, 0, scanLine1 + 10, false,
                        CycleMethod.NO_CYCLE,
                        new Stop(0, Color.TRANSPARENT),
                        new Stop(0.5, Color.rgb(255, 51, 102, 0.6)),
                        new Stop(1, Color.TRANSPARENT)));
                gc.setLineWidth(20);
                gc.strokeLine(0, scanLine1, width, scanLine1);

                gc.setStroke(Color.rgb(102, 51, 255, 0.3));
                gc.setLineWidth(2);
                gc.strokeLine(0, scanLine2, width, scanLine2);

                // Glowing corner accents
                gc.setFill(new RadialGradient(0, 0, 0, 0, 150, false,
                        CycleMethod.NO_CYCLE,
                        new Stop(0, Color.rgb(255, 51, 102, 0.3)),
                        new Stop(1, Color.TRANSPARENT)));
                gc.fillOval(-50, -50, 200, 200);
                gc.fillOval(width - 150, -50, 200, 200);
                gc.fillOval(-50, height - 150, 200, 200);
                gc.fillOval(width - 150, height - 150, 200, 200);
            }
        };
        backgroundAnimation.start();

        // Main container with fade-in animation
        VBox container = new VBox(25);
        container.setAlignment(Pos.CENTER);
        container.setPadding(new Insets(40));
        container.setOpacity(0);

        // Background panel with glow
        Rectangle backgroundRect = new Rectangle(600, 600);
        backgroundRect.setFill(Color.rgb(0, 0, 0, 0.7));
        backgroundRect.setArcWidth(25);
        backgroundRect.setArcHeight(25);

        DropShadow glow = new DropShadow();
        glow.setColor(Color.rgb(255, 51, 102, 0.6));
        glow.setRadius(25);
        glow.setSpread(0.4);
        backgroundRect.setEffect(glow);

        // Pulsing glow animation
        Timeline glowPulse = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(glow.radiusProperty(), 25)),
                new KeyFrame(Duration.seconds(2), new KeyValue(glow.radiusProperty(), 35)),
                new KeyFrame(Duration.seconds(4), new KeyValue(glow.radiusProperty(), 25))
        );
        glowPulse.setCycleCount(Timeline.INDEFINITE);
        glowPulse.play();

        VBox contentBox = new VBox(18);
        contentBox.setAlignment(Pos.CENTER);
        contentBox.setPadding(new Insets(30));

        // Animated title
        Label title = new Label("ADMIN PANEL");
        title.setStyle("-fx-font-size: 40; -fx-font-weight: bold; -fx-text-fill: #ff3366; -fx-font-family: 'Arial Black';");
        title.setOpacity(0);
        title.setScaleX(0.5);
        title.setScaleY(0.5);

        DropShadow titleGlow = new DropShadow();
        titleGlow.setColor(Color.rgb(255, 0, 102, 0.9));
        titleGlow.setRadius(20);
        title.setEffect(titleGlow);

        // User label with animation
        Label userLabel = new Label("Logged in as: " + game.currentUser);
        userLabel.setStyle("-fx-font-size: 18; -fx-text-fill: #00ffcc; -fx-font-family: 'Arial';");
        userLabel.setOpacity(0);
        userLabel.setTranslateY(-10);

        // Create animated admin buttons
        Button manageUsersBtn = new Button("Manage Users");
        manageUsersBtn.setFocusTraversable(false);
        manageUsersBtn.setPrefWidth(400);
        manageUsersBtn.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5); -fx-text-fill: #ff4444; -fx-font-family: 'Arial Black'; -fx-font-size: 16; -fx-font-weight: bold; -fx-background-radius: 15; -fx-padding: 12 30; -fx-border-color: #ff4444; -fx-border-width: 2; -fx-border-radius: 15; -fx-effect: dropshadow(gaussian, #ff4444, 10, 0.6, 0, 0); -fx-focus-color: transparent; -fx-faint-focus-color: transparent;");
        manageUsersBtn.setOnMouseEntered(event -> manageUsersBtn.setStyle("-fx-background-color: rgba(255, 68, 68, 0.1); -fx-text-fill: #ff6666; -fx-font-family: 'Arial Black'; -fx-font-size: 16; -fx-font-weight: bold; -fx-background-radius: 15; -fx-padding: 12 30; -fx-border-color: #ff6666; -fx-border-width: 2; -fx-border-radius: 15; -fx-effect: dropshadow(gaussian, #ff4444, 10, 0.5, 0, 0); -fx-focus-color: transparent; -fx-faint-focus-color: transparent;"));
        manageUsersBtn.setOnMouseExited(event -> manageUsersBtn.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5); -fx-text-fill: #ff4444; -fx-font-family: 'Arial Black'; -fx-font-size: 16; -fx-font-weight: bold; -fx-background-radius: 15; -fx-padding: 12 30; -fx-border-color: #ff4444; -fx-border-width: 2; -fx-border-radius: 15; -fx-effect: dropshadow(gaussian, #ff4444, 10, 0.6, 0, 0); -fx-focus-color: transparent; -fx-faint-focus-color: transparent;"));
        manageUsersBtn.setOpacity(0);
        manageUsersBtn.setTranslateX(-50);

// View Leaderboard button
        Button viewLeaderboardBtn = new Button("View Leaderboard");
        viewLeaderboardBtn.setFocusTraversable(false);
        viewLeaderboardBtn.setPrefWidth(400);
        viewLeaderboardBtn.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5); -fx-text-fill: #44ff44; -fx-font-family: 'Arial Black'; -fx-font-size: 16; -fx-font-weight: bold; -fx-background-radius: 15; -fx-padding: 12 30; -fx-border-color: #44ff44; -fx-border-width: 2; -fx-border-radius: 15; -fx-effect: dropshadow(gaussian, #44ff44, 10, 0.6, 0, 0); -fx-focus-color: transparent; -fx-faint-focus-color: transparent;");
        viewLeaderboardBtn.setOnMouseEntered(event -> viewLeaderboardBtn.setStyle("-fx-background-color: rgba(68, 255, 68, 0.1); -fx-text-fill: #66ff66; -fx-font-family: 'Arial Black'; -fx-font-size: 16; -fx-font-weight: bold; -fx-background-radius: 15; -fx-padding: 12 30; -fx-border-color: #66ff66; -fx-border-width: 2; -fx-border-radius: 15; -fx-effect: dropshadow(gaussian, #44ff44, 10, 0.5, 0, 0); -fx-focus-color: transparent; -fx-faint-focus-color: transparent;"));
        viewLeaderboardBtn.setOnMouseExited(event -> viewLeaderboardBtn.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5); -fx-text-fill: #44ff44; -fx-font-family: 'Arial Black'; -fx-font-size: 16; -fx-font-weight: bold; -fx-background-radius: 15; -fx-padding: 12 30; -fx-border-color: #44ff44; -fx-border-width: 2; -fx-border-radius: 15; -fx-effect: dropshadow(gaussian, #44ff44, 10, 0.6, 0, 0); -fx-focus-color: transparent; -fx-faint-focus-color: transparent;"));
        viewLeaderboardBtn.setOpacity(0);
        viewLeaderboardBtn.setTranslateX(-50);

// View Ban Appeals button
        Button viewAppealsBtn = new Button("View Ban Appeals");
        viewAppealsBtn.setFocusTraversable(false);
        viewAppealsBtn.setPrefWidth(400);
        viewAppealsBtn.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5); -fx-text-fill: #aa66ff; -fx-font-family: 'Arial Black'; -fx-font-size: 16; -fx-font-weight: bold; -fx-background-radius: 15; -fx-padding: 12 30; -fx-border-color: #aa66ff; -fx-border-width: 2; -fx-border-radius: 15; -fx-effect: dropshadow(gaussian, #aa66ff, 10, 0.6, 0, 0); -fx-focus-color: transparent; -fx-faint-focus-color: transparent;");
        viewAppealsBtn.setOnMouseEntered(event -> viewAppealsBtn.setStyle("-fx-background-color: rgba(170, 102, 255, 0.1); -fx-text-fill: #cc88ff; -fx-font-family: 'Arial Black'; -fx-font-size: 16; -fx-font-weight: bold; -fx-background-radius: 15; -fx-padding: 12 30; -fx-border-color: #cc88ff; -fx-border-width: 2; -fx-border-radius: 15; -fx-effect: dropshadow(gaussian, #aa66ff, 10, 0.5, 0, 0); -fx-focus-color: transparent; -fx-faint-focus-color: transparent;"));
        viewAppealsBtn.setOnMouseExited(event -> viewAppealsBtn.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5); -fx-text-fill: #aa66ff; -fx-font-family: 'Arial Black'; -fx-font-size: 16; -fx-font-weight: bold; -fx-background-radius: 15; -fx-padding: 12 30; -fx-border-color: #aa66ff; -fx-border-width: 2; -fx-border-radius: 15; -fx-effect: dropshadow(gaussian, #aa66ff, 10, 0.6, 0, 0); -fx-focus-color: transparent; -fx-faint-focus-color: transparent;"));
        viewAppealsBtn.setOpacity(0);
        viewAppealsBtn.setTranslateX(-50);
        viewAppealsBtn.setOnAction(e -> game.showBanAppealsDialog());

// Reset All Progress button
        Button resetProgressBtn = new Button("Reset All Progress");
        resetProgressBtn.setFocusTraversable(false);
        resetProgressBtn.setPrefWidth(400);
        resetProgressBtn.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5); -fx-text-fill: #ffaa00; -fx-font-family: 'Arial Black'; -fx-font-size: 16; -fx-font-weight: bold; -fx-background-radius: 15; -fx-padding: 12 30; -fx-border-color: #ffaa00; -fx-border-width: 2; -fx-border-radius: 15; -fx-effect: dropshadow(gaussian, #ffaa00, 10, 0.6, 0, 0); -fx-focus-color: transparent; -fx-faint-focus-color: transparent;");
        resetProgressBtn.setOnMouseEntered(event -> resetProgressBtn.setStyle("-fx-background-color: rgba(255, 170, 0, 0.1); -fx-text-fill: #ffbb22; -fx-font-family: 'Arial Black'; -fx-font-size: 16; -fx-font-weight: bold; -fx-background-radius: 15; -fx-padding: 12 30; -fx-border-color: #ffbb22; -fx-border-width: 2; -fx-border-radius: 15; -fx-effect: dropshadow(gaussian, #ffaa00, 10, 0.5, 0, 0); -fx-focus-color: transparent; -fx-faint-focus-color: transparent;"));
        resetProgressBtn.setOnMouseExited(event -> resetProgressBtn.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5); -fx-text-fill: #ffaa00; -fx-font-family: 'Arial Black'; -fx-font-size: 16; -fx-font-weight: bold; -fx-background-radius: 15; -fx-padding: 12 30; -fx-border-color: #ffaa00; -fx-border-width: 2; -fx-border-radius: 15; -fx-effect: dropshadow(gaussian, #ffaa00, 10, 0.6, 0, 0); -fx-focus-color: transparent; -fx-faint-focus-color: transparent;"));
        resetProgressBtn.setOpacity(0);
        resetProgressBtn.setTranslateX(-50);

// Logout button
        Button logoutBtn = new Button("Logout");
        logoutBtn.setFocusTraversable(false);
        logoutBtn.setPrefWidth(400);
        logoutBtn.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5); -fx-text-fill: #8888ff; -fx-font-family: 'Arial Black'; -fx-font-size: 16; -fx-font-weight: bold; -fx-background-radius: 15; -fx-padding: 12 30; -fx-border-color: #8888ff; -fx-border-width: 2; -fx-border-radius: 15; -fx-effect: dropshadow(gaussian, #8888ff, 10, 0.6, 0, 0); -fx-focus-color: transparent; -fx-faint-focus-color: transparent;");
        logoutBtn.setOnMouseEntered(event -> logoutBtn.setStyle("-fx-background-color: rgba(136, 136, 255, 0.1); -fx-text-fill: #aaaaff; -fx-font-family: 'Arial Black'; -fx-font-size: 16; -fx-font-weight: bold; -fx-background-radius: 15; -fx-padding: 12 30; -fx-border-color: #aaaaff; -fx-border-width: 2; -fx-border-radius: 15; -fx-effect: dropshadow(gaussian, #8888ff, 10, 0.5, 0, 0); -fx-focus-color: transparent; -fx-faint-focus-color: transparent;"));
        logoutBtn.setOnMouseExited(event -> logoutBtn.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5); -fx-text-fill: #8888ff; -fx-font-family: 'Arial Black'; -fx-font-size: 16; -fx-font-weight: bold; -fx-background-radius: 15; -fx-padding: 12 30; -fx-border-color: #8888ff; -fx-border-width: 2; -fx-border-radius: 15; -fx-effect: dropshadow(gaussian, #8888ff, 10, 0.6, 0, 0); -fx-focus-color: transparent; -fx-faint-focus-color: transparent;"));
        logoutBtn.setOpacity(0);
        logoutBtn.setTranslateX(-50);

        // Separator with animation
        Separator separator = new Separator(Orientation.HORIZONTAL);
        separator.setMaxWidth(500);
        separator.setStyle("-fx-background-color: #ff3366; -fx-border-color: #ff3366;");
        separator.setOpacity(0);

        // Button actions
        logoutBtn.setOnAction(e -> {
            backgroundAnimation.stop();

            // FIX: Delete token BEFORE clearing currentUser
            game.deleteLoginToken();  // ← ADD THIS LINE

            game.currentUser = null;
            game.currentProfileImage = null;  // Also clear profile image
            game.clearUserProgressCache();    // Also clear cache

            game.fadeOutAndTransition(() -> game.showLoginScreen());
        });


        manageUsersBtn.setOnAction(e -> game.showUserManagementDialog());
        viewLeaderboardBtn.setOnAction(e -> game.showLeaderboardDialog());

        resetProgressBtn.setOnAction(e -> game.showResetProgressDialog());

        contentBox.getChildren().addAll(
                title, userLabel,
                manageUsersBtn,
                viewLeaderboardBtn,
                viewAppealsBtn,
                resetProgressBtn,
                separator,
                logoutBtn
        );

        StackPane formStack = new StackPane();
        formStack.getChildren().addAll(backgroundRect, contentBox);
        container.getChildren().add(formStack);

        // Layer the animated background and form
        StackPane mainStack = new StackPane();
        mainStack.getChildren().addAll(backgroundCanvas, container);
        game.root.setCenter(mainStack);

        // Sequential entrance animations
        Timeline entranceAnimation = new Timeline(
                // Container fade in
                new KeyFrame(Duration.ZERO,
                        new KeyValue(container.opacityProperty(), 0)),
                new KeyFrame(Duration.millis(300),
                        new KeyValue(container.opacityProperty(), 1)),

                // Title animation with scale
                new KeyFrame(Duration.ZERO,
                        new KeyValue(title.opacityProperty(), 0),
                        new KeyValue(title.scaleXProperty(), 0.5),
                        new KeyValue(title.scaleYProperty(), 0.5)),
                new KeyFrame(Duration.millis(500),
                        new KeyValue(title.opacityProperty(), 1, Interpolator.EASE_OUT),
                        new KeyValue(title.scaleXProperty(), 1.1, Interpolator.EASE_OUT),
                        new KeyValue(title.scaleYProperty(), 1.1, Interpolator.EASE_OUT)),
                new KeyFrame(Duration.millis(700),
                        new KeyValue(title.scaleXProperty(), 1.0, Interpolator.EASE_OUT),
                        new KeyValue(title.scaleYProperty(), 1.0, Interpolator.EASE_OUT)),

                // User label
                new KeyFrame(Duration.millis(400),
                        new KeyValue(userLabel.opacityProperty(), 0),
                        new KeyValue(userLabel.translateYProperty(), -10)),
                new KeyFrame(Duration.millis(800),
                        new KeyValue(userLabel.opacityProperty(), 1, Interpolator.EASE_OUT),
                        new KeyValue(userLabel.translateYProperty(), 0, Interpolator.EASE_OUT)),

                // Manage Users button
                new KeyFrame(Duration.millis(600),
                        new KeyValue(manageUsersBtn.opacityProperty(), 0),
                        new KeyValue(manageUsersBtn.translateXProperty(), -50)),
                new KeyFrame(Duration.millis(1000),
                        new KeyValue(manageUsersBtn.opacityProperty(), 1, Interpolator.EASE_OUT),
                        new KeyValue(manageUsersBtn.translateXProperty(), 0, Interpolator.EASE_OUT)),

                // View Leaderboard button
                new KeyFrame(Duration.millis(700),
                        new KeyValue(viewLeaderboardBtn.opacityProperty(), 0),
                        new KeyValue(viewLeaderboardBtn.translateXProperty(), -50)),
                new KeyFrame(Duration.millis(1100),
                        new KeyValue(viewLeaderboardBtn.opacityProperty(), 1, Interpolator.EASE_OUT),
                        new KeyValue(viewLeaderboardBtn.translateXProperty(), 0, Interpolator.EASE_OUT)),

                new KeyFrame(Duration.millis(750),
                        new KeyValue(viewAppealsBtn.opacityProperty(), 0),
                        new KeyValue(viewAppealsBtn.translateXProperty(), -50)),
                new KeyFrame(Duration.millis(1150),
                        new KeyValue(viewAppealsBtn.opacityProperty(), 1, Interpolator.EASE_OUT),
                        new KeyValue(viewAppealsBtn.translateXProperty(), 0, Interpolator.EASE_OUT)),

                // Reset Progress button
                new KeyFrame(Duration.millis(800),
                        new KeyValue(resetProgressBtn.opacityProperty(), 0),
                        new KeyValue(resetProgressBtn.translateXProperty(), -50)),
                new KeyFrame(Duration.millis(1200),
                        new KeyValue(resetProgressBtn.opacityProperty(), 1, Interpolator.EASE_OUT),
                        new KeyValue(resetProgressBtn.translateXProperty(), 0, Interpolator.EASE_OUT)),

                // Separator
                new KeyFrame(Duration.millis(900),
                        new KeyValue(separator.opacityProperty(), 0)),
                new KeyFrame(Duration.millis(1300),
                        new KeyValue(separator.opacityProperty(), 1, Interpolator.EASE_OUT)),

                // Logout button
                new KeyFrame(Duration.millis(1000),
                        new KeyValue(logoutBtn.opacityProperty(), 0),
                        new KeyValue(logoutBtn.translateXProperty(), -50)),
                new KeyFrame(Duration.millis(1400),
                        new KeyValue(logoutBtn.opacityProperty(), 1, Interpolator.EASE_OUT),
                        new KeyValue(logoutBtn.translateXProperty(), 0, Interpolator.EASE_OUT))
        );
        entranceAnimation.play();
    }
}