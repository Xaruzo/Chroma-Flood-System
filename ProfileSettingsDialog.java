import com.google.gson.JsonObject;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.mindrot.jbcrypt.BCrypt;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Iterator;

public class ProfileSettingsDialog {

    private final ChromaFloodSystem game;           // ← we get access to currentUser, currentProfileImage, etc.
    private final Stage ownerStage;                 // ← the main window (primaryStage)

    private Stage dialogStage;

    public ProfileSettingsDialog(ChromaFloodSystem game, Stage ownerStage) {
        this.game = game;
        this.ownerStage = ownerStage;
    }

    // Here goes your original showProfileSettingsDialog() method,
    // just rename it to show() and make it public
    public void show() {
        if (game.profileSettingsStage == null) {
            game.profileSettingsStage = new Stage();
            game.profileSettingsStage.initOwner(game.primaryStage);
            game.profileSettingsStage.initModality(Modality.WINDOW_MODAL);
            game.profileSettingsStage.initStyle(StageStyle.TRANSPARENT);
            game.profileSettingsStage.setTitle("Profile Settings");
            game.profileSettingsStage.setResizable(false);

            // Reset dialog flag when closed
            game.profileSettingsStage.setOnHidden(e -> game.isDialogOpen = false);

            // Outer container with transparent background
            StackPane overlay = new StackPane();
            overlay.setStyle("-fx-background-color: transparent;");
            overlay.setPadding(new Insets(40));

            // Main container
            VBox mainContainer = new VBox(20);
            mainContainer.setPadding(new Insets(35, 40, 35, 40));
            mainContainer.setStyle(
                    "-fx-background-color: linear-gradient(to bottom right, #1a1a2e, #0f0f1e);" +
                            "-fx-background-radius: 20;" +
                            "-fx-effect: dropshadow(gaussian, rgba(100, 100, 255, 0.4), 25, 0.5, 0, 0);"
            );

            Label titleLabel = new Label("Profile Settings");
            titleLabel.setStyle("-fx-text-fill: #FFFFFF; -fx-font-family: 'Segoe UI', 'Arial'; -fx-font-size: 24; -fx-font-weight: bold;");

            // ═══════════════════════════════════════════════════════════
            // LEFT PANEL: PROFILE PICTURE & USERNAME
            // ═══════════════════════════════════════════════════════════
            VBox leftPanel = new VBox(20);
            leftPanel.setAlignment(Pos.TOP_CENTER);
            leftPanel.setPadding(new Insets(25, 20, 25, 20));
            leftPanel.setStyle("-fx-background-color: rgba(30, 30, 45, 0.7); -fx-background-radius: 15; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.4), 15, 0, 0, 5);");
            leftPanel.setPrefWidth(300);

            Label pictureHeader = new Label("Profile Picture");
            pictureHeader.setStyle("-fx-text-fill: #00D9FF; -fx-font-family: 'Segoe UI', 'Arial'; -fx-font-size: 15; -fx-font-weight: 600;");

            Circle backgroundCircle = new Circle(60);
            backgroundCircle.setFill(Color.rgb(40, 45, 55, 0.8));

            ImageView profilePreview = new ImageView();
            profilePreview.setFitWidth(120);
            profilePreview.setFitHeight(120);
            Circle clipCircle = new Circle(60, 60, 60);
            profilePreview.setClip(clipCircle);

            profilePreview.setImage(game.currentProfileImage != null
                    ? game.currentProfileImage
                    : new Image("file:resources/images/default_profile.png"));

// Stack: background → image (no outline)
            StackPane profileStack = new StackPane(backgroundCircle, profilePreview);
            profileStack.setEffect(new DropShadow(15, Color.rgb(0, 217, 255, 0.6)));

            final File[] selectedFile = {null};
            final byte[][] uploadedPngBytes = {null};

            Button uploadButton = new Button("Upload New Picture");
            uploadButton.setStyle("-fx-background-color: rgba(100, 100, 255, 0.15); -fx-border-color: #6464FF; -fx-border-width: 2; -fx-border-radius: 10; -fx-background-radius: 10; -fx-text-fill: #A5A5FF; -fx-font-family: 'Segoe UI', 'Arial'; -fx-font-size: 12; -fx-font-weight: 600; -fx-padding: 10 20; -fx-cursor: hand;");
            uploadButton.setPrefWidth(240);

            uploadButton.setOnAction(e -> {
                FileChooser fileChooser = new FileChooser();
                fileChooser.getExtensionFilters().add(
                        new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
                );
                File file = fileChooser.showOpenDialog(game.profileSettingsStage);
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
                        game.showImageCropDialog(file, profilePreview, uploadedPngBytes);
                    } catch (Exception ex) {
                        new Alert(Alert.AlertType.ERROR,
                                "Failed to load image: " + ex.getMessage() +
                                        "\n\nPlease try a PNG or JPEG file instead.")
                                .show();
                    }
                }
            });

            uploadButton.setOnMouseEntered(e -> uploadButton.setStyle("-fx-background-color: rgba(120, 120, 255, 0.25); -fx-border-color: #7D7DFF; -fx-border-width: 2; -fx-border-radius: 10; -fx-background-radius: 10; -fx-text-fill: #C5C5FF; -fx-font-family: 'Segoe UI', 'Arial'; -fx-font-size: 12; -fx-font-weight: 600; -fx-padding: 10 20; -fx-cursor: hand;"));
            uploadButton.setOnMouseExited(e -> uploadButton.setStyle("-fx-background-color: rgba(100, 100, 255, 0.15); -fx-border-color: #6464FF; -fx-border-width: 2; -fx-border-radius: 10; -fx-background-radius: 10; -fx-text-fill: #A5A5FF; -fx-font-family: 'Segoe UI', 'Arial'; -fx-font-size: 12; -fx-font-weight: 600; -fx-padding: 10 20; -fx-cursor: hand;"));

            // Username section in left panel
            Label usernameHeader = new Label("Username");
            usernameHeader.setStyle("-fx-text-fill: #00D9FF; -fx-font-family: 'Segoe UI', 'Arial'; -fx-font-size: 15; -fx-font-weight: 600;");

            TextField usernameField = new TextField(game.currentUser);
            usernameField.setPromptText("Enter username");
            usernameField.setStyle("-fx-background-color: rgba(40, 40, 60, 0.8); -fx-text-fill: #FFFFFF; -fx-prompt-text-fill: #888888; -fx-font-size: 14; -fx-padding: 12; -fx-background-radius: 10; -fx-border-color: rgba(100, 100, 255, 0.3); -fx-border-width: 1; -fx-border-radius: 10;");

            usernameField.textProperty().addListener((obs, oldValue, newValue) -> {
                if (!newValue.matches("[a-zA-Z0-9_]*")) {
                    usernameField.setText(oldValue);
                } else if (newValue.length() > 20) {
                    usernameField.setText(oldValue);
                }
            });

            usernameField.setOnMouseEntered(e -> usernameField.setStyle("-fx-background-color: rgba(45, 45, 70, 0.9); -fx-text-fill: #FFFFFF; -fx-prompt-text-fill: #888888; -fx-font-size: 14; -fx-padding: 12; -fx-background-radius: 10; -fx-border-color: rgba(120, 120, 255, 0.5); -fx-border-width: 1; -fx-border-radius: 10;"));
            usernameField.setOnMouseExited(e -> usernameField.setStyle("-fx-background-color: rgba(40, 40, 60, 0.8); -fx-text-fill: #FFFFFF; -fx-prompt-text-fill: #888888; -fx-font-size: 14; -fx-padding: 12; -fx-background-radius: 10; -fx-border-color: rgba(100, 100, 255, 0.3); -fx-border-width: 1; -fx-border-radius: 10;"));

            Region spacer = new Region();
            VBox.setVgrow(spacer, Priority.ALWAYS);

            leftPanel.getChildren().addAll(pictureHeader, profileStack, uploadButton, spacer, usernameHeader, usernameField);

            // ═══════════════════════════════════════════════════════════
            // RIGHT PANEL: CHANGE PASSWORD
            // ═══════════════════════════════════════════════════════════
            VBox rightPanel = new VBox(18);
            rightPanel.setAlignment(Pos.TOP_LEFT);
            rightPanel.setPadding(new Insets(25, 20, 25, 20));
            rightPanel.setStyle("-fx-background-color: rgba(30, 30, 45, 0.7); -fx-background-radius: 15; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.4), 15, 0, 0, 5);");
            rightPanel.setPrefWidth(380);

            Label passwordHeader = new Label("Change Password");
            passwordHeader.setStyle("-fx-text-fill: #00D9FF; -fx-font-family: 'Segoe UI', 'Arial'; -fx-font-size: 15; -fx-font-weight: 600;");

            // Base styles for password fields
            String basePasswordStyle = "-fx-background-color: rgba(40, 40, 60, 0.8); -fx-text-fill: #FFFFFF; -fx-prompt-text-fill: #888888; -fx-font-size: 14; -fx-padding: 12 45 12 12; -fx-background-radius: 10; -fx-border-color: rgba(100, 100, 255, 0.3); -fx-border-width: 1; -fx-border-radius: 10;";
            String hoverPasswordStyle = "-fx-background-color: rgba(45, 45, 70, 0.9); -fx-text-fill: #FFFFFF; -fx-prompt-text-fill: #888888; -fx-font-size: 14; -fx-padding: 12 45 12 12; -fx-background-radius: 10; -fx-border-color: rgba(120, 120, 255, 0.5); -fx-border-width: 1; -fx-border-radius: 10;";

            // Current Password with toggle
            StackPane oldPasswordContainer = new StackPane();

            PasswordField oldPasswordField = new PasswordField();
            oldPasswordField.setPromptText("Current Password");
            oldPasswordField.setStyle(basePasswordStyle);

            TextField oldPasswordTextField = new TextField();
            oldPasswordTextField.setPromptText("Current Password");
            oldPasswordTextField.setStyle(basePasswordStyle);
            oldPasswordTextField.setVisible(false);
            oldPasswordTextField.setManaged(false);

            oldPasswordTextField.textProperty().bindBidirectional(oldPasswordField.textProperty());

            // Character limit for old password
            oldPasswordField.textProperty().addListener((obs, oldValue, newValue) -> {
                if (newValue.length() > 32) {
                    oldPasswordField.setText(oldValue);
                }
            });
            oldPasswordTextField.textProperty().addListener((obs, oldValue, newValue) -> {
                if (newValue.length() > 32) {
                    oldPasswordTextField.setText(oldValue);
                }
            });

            Button toggleOldPasswordButton = new Button("👁");
            toggleOldPasswordButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #00D9FF; -fx-font-size: 16; -fx-cursor: hand; -fx-padding: 5;");
            toggleOldPasswordButton.setPrefSize(30, 30);
            toggleOldPasswordButton.setFocusTraversable(false);

            StackPane.setAlignment(toggleOldPasswordButton, Pos.CENTER_RIGHT);
            StackPane.setMargin(toggleOldPasswordButton, new Insets(0, 10, 0, 0));

            toggleOldPasswordButton.setOnAction(ev -> {
                if (oldPasswordField.isVisible()) {
                    oldPasswordField.setVisible(false);
                    oldPasswordField.setManaged(false);
                    oldPasswordTextField.setVisible(true);
                    oldPasswordTextField.setManaged(true);
                    toggleOldPasswordButton.setText("🙈");
                } else {
                    oldPasswordTextField.setVisible(false);
                    oldPasswordTextField.setManaged(false);
                    oldPasswordField.setVisible(true);
                    oldPasswordField.setManaged(true);
                    toggleOldPasswordButton.setText("👁");
                }
                mainContainer.requestFocus();
            });

            // Hover effects for old password
            oldPasswordField.setOnMouseEntered(e -> {
                oldPasswordField.setStyle(hoverPasswordStyle);
                oldPasswordTextField.setStyle(hoverPasswordStyle);
            });
            oldPasswordField.setOnMouseExited(e -> {
                oldPasswordField.setStyle(basePasswordStyle);
                oldPasswordTextField.setStyle(basePasswordStyle);
            });
            oldPasswordTextField.setOnMouseEntered(e -> {
                oldPasswordField.setStyle(hoverPasswordStyle);
                oldPasswordTextField.setStyle(hoverPasswordStyle);
            });
            oldPasswordTextField.setOnMouseExited(e -> {
                oldPasswordField.setStyle(basePasswordStyle);
                oldPasswordTextField.setStyle(basePasswordStyle);
            });

            oldPasswordContainer.getChildren().addAll(oldPasswordField, oldPasswordTextField, toggleOldPasswordButton);

            // New Password with toggle
            StackPane newPasswordContainer = new StackPane();

            PasswordField newPasswordField = new PasswordField();
            newPasswordField.setPromptText("New Password");
            newPasswordField.setStyle(basePasswordStyle);

            TextField newPasswordTextField = new TextField();
            newPasswordTextField.setPromptText("New Password");
            newPasswordTextField.setStyle(basePasswordStyle);
            newPasswordTextField.setVisible(false);
            newPasswordTextField.setManaged(false);

            newPasswordTextField.textProperty().bindBidirectional(newPasswordField.textProperty());

            // Character limit for new password
            newPasswordField.textProperty().addListener((obs, oldValue, newValue) -> {
                if (newValue.length() > 32) {
                    newPasswordField.setText(oldValue);
                }
            });
            newPasswordTextField.textProperty().addListener((obs, oldValue, newValue) -> {
                if (newValue.length() > 32) {
                    newPasswordTextField.setText(oldValue);
                }
            });

            Button toggleNewPasswordButton = new Button("👁");
            toggleNewPasswordButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #00D9FF; -fx-font-size: 16; -fx-cursor: hand; -fx-padding: 5;");
            toggleNewPasswordButton.setPrefSize(30, 30);
            toggleNewPasswordButton.setFocusTraversable(false);

            StackPane.setAlignment(toggleNewPasswordButton, Pos.CENTER_RIGHT);
            StackPane.setMargin(toggleNewPasswordButton, new Insets(0, 10, 0, 0));

            toggleNewPasswordButton.setOnAction(ev -> {
                if (newPasswordField.isVisible()) {
                    newPasswordField.setVisible(false);
                    newPasswordField.setManaged(false);
                    newPasswordTextField.setVisible(true);
                    newPasswordTextField.setManaged(true);
                    toggleNewPasswordButton.setText("🙈");
                } else {
                    newPasswordTextField.setVisible(false);
                    newPasswordTextField.setManaged(false);
                    newPasswordField.setVisible(true);
                    newPasswordField.setManaged(true);
                    toggleNewPasswordButton.setText("👁");
                }
                mainContainer.requestFocus();
            });

            // Hover effects for new password
            newPasswordField.setOnMouseEntered(e -> {
                newPasswordField.setStyle(hoverPasswordStyle);
                newPasswordTextField.setStyle(hoverPasswordStyle);
            });
            newPasswordField.setOnMouseExited(e -> {
                newPasswordField.setStyle(basePasswordStyle);
                newPasswordTextField.setStyle(basePasswordStyle);
            });
            newPasswordTextField.setOnMouseEntered(e -> {
                newPasswordField.setStyle(hoverPasswordStyle);
                newPasswordTextField.setStyle(hoverPasswordStyle);
            });
            newPasswordTextField.setOnMouseExited(e -> {
                newPasswordField.setStyle(basePasswordStyle);
                newPasswordTextField.setStyle(basePasswordStyle);
            });

            newPasswordContainer.getChildren().addAll(newPasswordField, newPasswordTextField, toggleNewPasswordButton);

            // Confirm Password (no toggle)
            // Confirm Password (no toggle)
            PasswordField confirmPasswordField = new PasswordField();
            confirmPasswordField.setPromptText("Confirm New Password");
            confirmPasswordField.setStyle("-fx-background-color: rgba(40, 40, 60, 0.8); -fx-text-fill: #FFFFFF; -fx-prompt-text-fill: #888888; -fx-font-size: 14; -fx-padding: 12; -fx-background-radius: 10; -fx-border-color: rgba(100, 100, 255, 0.3); -fx-border-width: 1; -fx-border-radius: 10;");

            // Character limit for confirm password
            confirmPasswordField.textProperty().addListener((obs, oldValue, newValue) -> {
                if (newValue.length() > 32) {
                    confirmPasswordField.setText(oldValue);
                }
            });

            confirmPasswordField.setOnMouseEntered(e -> confirmPasswordField.setStyle("-fx-background-color: rgba(45, 45, 70, 0.9); -fx-text-fill: #FFFFFF; -fx-prompt-text-fill: #888888; -fx-font-size: 14; -fx-padding: 12; -fx-background-radius: 10; -fx-border-color: rgba(120, 120, 255, 0.5); -fx-border-width: 1; -fx-border-radius: 10;"));
            confirmPasswordField.setOnMouseExited(e -> confirmPasswordField.setStyle("-fx-background-color: rgba(40, 40, 60, 0.8); -fx-text-fill: #FFFFFF; -fx-prompt-text-fill: #888888; -fx-font-size: 14; -fx-padding: 12; -fx-background-radius: 10; -fx-border-color: rgba(100, 100, 255, 0.3); -fx-border-width: 1; -fx-border-radius: 10;"));

            Label passwordHint = new Label("Leave all fields blank to keep your current password");
            passwordHint.setStyle("-fx-text-fill: #888899; -fx-font-size: 11; -fx-font-style: italic; -fx-padding: 5 0 0 0;");

            rightPanel.getChildren().addAll(passwordHeader, oldPasswordContainer, newPasswordContainer, confirmPasswordField, passwordHint);

            // ═══════════════════════════════════════════════════════════
            // CONTENT LAYOUT (LANDSCAPE)
            // ═══════════════════════════════════════════════════════════
            HBox contentLayout = new HBox(25);
            contentLayout.setAlignment(Pos.CENTER);
            contentLayout.getChildren().addAll(leftPanel, rightPanel);

            // ═══════════════════════════════════════════════════════════
            // ACTION BUTTONS
            // ═══════════════════════════════════════════════════════════
            HBox buttonBox = new HBox(15);
            buttonBox.setAlignment(Pos.CENTER);

            Button saveButton = new Button("Save Changes");
            saveButton.setStyle("-fx-background-color: linear-gradient(to right, #00D9FF, #0099FF); -fx-text-fill: white; -fx-font-family: 'Segoe UI', 'Arial'; -fx-font-size: 14; -fx-font-weight: 600; -fx-padding: 13 35; -fx-background-radius: 10; -fx-effect: dropshadow(gaussian, rgba(0, 153, 255, 0.5), 12, 0, 0, 3); -fx-cursor: hand;");
            saveButton.setPrefWidth(200);

            saveButton.setOnMouseEntered(e -> saveButton.setStyle("-fx-background-color: linear-gradient(to right, #00EEFF, #00AAFF); -fx-text-fill: white; -fx-font-family: 'Segoe UI', 'Arial'; -fx-font-size: 14; -fx-font-weight: 600; -fx-padding: 13 35; -fx-background-radius: 10; -fx-effect: dropshadow(gaussian, rgba(0, 153, 255, 0.7), 15, 0, 0, 4); -fx-cursor: hand;"));
            saveButton.setOnMouseExited(e -> saveButton.setStyle("-fx-background-color: linear-gradient(to right, #00D9FF, #0099FF); -fx-text-fill: white; -fx-font-family: 'Segoe UI', 'Arial'; -fx-font-size: 14; -fx-font-weight: 600; -fx-padding: 13 35; -fx-background-radius: 10; -fx-effect: dropshadow(gaussian, rgba(0, 153, 255, 0.5), 12, 0, 0, 3); -fx-cursor: hand;"));

            saveButton.setOnAction(e -> {
                final String newUsername = usernameField.getText().trim();
                final String oldPassword = oldPasswordField.getText();
                final String newPassword = newPasswordField.getText();
                final String confirmPassword = confirmPasswordField.getText();

                // Validation
                if (newUsername.isEmpty() || newUsername.length() < 3 || newUsername.length() > 20) {
                    new Alert(Alert.AlertType.ERROR, "Username must be 3–20 characters.").show();
                    return;
                }

                // Password validation (if attempting to change)
                boolean changingPassword = !oldPassword.isEmpty() || !newPassword.isEmpty() || !confirmPassword.isEmpty();

                if (changingPassword) {
                    if (oldPassword.isEmpty()) {
                        new Alert(Alert.AlertType.ERROR, "Please enter your current password.").show();
                        return;
                    }
                    if (newPassword.isEmpty()) {
                        new Alert(Alert.AlertType.ERROR, "Please enter a new password.").show();
                        return;
                    }
                    if (newPassword.length() < 8 || newPassword.length() > 32) {
                        new Alert(Alert.AlertType.ERROR, "New password must be between 8 and 32 characters.").show();
                        return;
                    }
                    if (!newPassword.equals(confirmPassword)) {
                        new Alert(Alert.AlertType.ERROR, "Passwords do not match.").show();
                        return;
                    }
                }

                saveButton.setDisable(true);
                game.showLoadingScreen();

                Task<Void> task = new Task<>() {
                    @Override
                    protected Void call() throws Exception {
                        // ─────── VERIFY OLD PASSWORD IF CHANGING ───────
                        if (changingPassword) {
                            String verifyUrl = game.SUPABASE_URL + "/rest/v1/rpc/verify_password";
                            JsonObject verifyPayload = new JsonObject();
                            verifyPayload.addProperty("p_username", game.currentUser);
                            verifyPayload.addProperty("p_password", oldPassword);

                            HttpRequest verifyRequest = HttpRequest.newBuilder()
                                    .uri(URI.create(verifyUrl))
                                    .header("apikey", game.SUPABASE_ANON_KEY)
                                    .header("Authorization", "Bearer " + game.SUPABASE_ANON_KEY)
                                    .header("Content-Type", "application/json")
                                    .POST(HttpRequest.BodyPublishers.ofString(verifyPayload.toString()))
                                    .build();

                            HttpResponse<String> verifyResp = game.httpClient.send(verifyRequest, HttpResponse.BodyHandlers.ofString());
                            if (!verifyResp.body().equalsIgnoreCase("true")) {
                                throw new Exception("Current password is incorrect.");
                            }
                        }

                        // ─────── CHECK IF USERNAME ALREADY EXISTS ───────
                        // Only check if username is changing
                        if (!newUsername.equals(game.currentUser)) {
                            String checkUrl = game.SUPABASE_URL + "/rest/v1/profiles?username=eq." +
                                    URLEncoder.encode(newUsername, StandardCharsets.UTF_8);

                            HttpRequest checkRequest = HttpRequest.newBuilder()
                                    .uri(URI.create(checkUrl))
                                    .header("apikey", game.SUPABASE_ANON_KEY)
                                    .header("Authorization", "Bearer " + game.SUPABASE_ANON_KEY)
                                    .GET()
                                    .build();

                            HttpResponse<String> checkResp = game.httpClient.send(checkRequest,
                                    HttpResponse.BodyHandlers.ofString());

                            // Parse response - if array is not empty, username exists
                            if (!checkResp.body().equals("[]")) {
                                throw new Exception("USERNAME_TAKEN");
                            }
                        }

                        JsonObject payload = new JsonObject();
                        payload.addProperty("username", newUsername);

                        // ─────── ADD NEW PASSWORD TO PAYLOAD IF CHANGING ───────
                        if (changingPassword) {
                            // Hash the new password using BCrypt
                            String hashedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt(10));
                            payload.addProperty("password", hashedPassword);
                        }

                        // ─────── PROFILE PICTURE PROCESSING ───────
                        if (uploadedPngBytes[0] != null) {
                            try {
                                // Use the already-cropped bytes directly (300x300 from crop dialog)
                                byte[] bytes = uploadedPngBytes[0];

                                // For a 300x300 PNG, we can afford better quality
                                // Supabase free tier: 500MB storage, so 200-300KB per image is fine
                                if (bytes.length > 300_000) {  // Increased from 180KB to 300KB
                                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                                    BufferedImage buf = ImageIO.read(new ByteArrayInputStream(bytes));

                                    // Try PNG optimization first (better quality than JPEG)
                                    ImageIO.write(buf, "png", out);
                                    byte[] pngBytes = out.toByteArray();

                                    // Only convert to JPEG if PNG is still too large
                                    if (pngBytes.length > 300_000) {
                                        out.reset();
                                        BufferedImage jpeg = new BufferedImage(buf.getWidth(), buf.getHeight(), BufferedImage.TYPE_INT_RGB);
                                        Graphics2D g = jpeg.createGraphics();
                                        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                                        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                                        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                                        g.setBackground(java.awt.Color.WHITE);
                                        g.clearRect(0, 0, jpeg.getWidth(), jpeg.getHeight());
                                        g.drawImage(buf, 0, 0, null);
                                        g.dispose();

                                        Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("jpeg");
                                        if (writers.hasNext()) {
                                            ImageWriter writer = writers.next();
                                            ImageWriteParam p = writer.getDefaultWriteParam();
                                            p.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
                                            p.setCompressionQuality(0.95f);  // Increased from 0.87 to 0.95 for better quality
                                            try (ImageOutputStream ios = ImageIO.createImageOutputStream(out)) {
                                                writer.setOutput(ios);
                                                writer.write(null, new IIOImage(jpeg, null, null), p);
                                            }
                                            writer.dispose();
                                        }
                                        bytes = out.toByteArray();
                                    } else {
                                        bytes = pngBytes;  // Use PNG if it's small enough
                                    }
                                }

                                String base64 = Base64.getEncoder().encodeToString(bytes);
                                payload.addProperty("profile_picture_bytes", base64);
                                payload.addProperty("profile_picture", (String) null);

                                final Image finalImg = new Image(new ByteArrayInputStream(bytes));
                                Platform.runLater(() -> {
                                    game.currentProfileImage = finalImg;
                                    if (game.topBarProfilePicView != null) game.topBarProfilePicView.setImage(finalImg);
                                    if (game.levelSelectProfileView != null) game.levelSelectProfileView.setImage(finalImg);
                                });

                            } catch (Exception ex) {
                                ex.printStackTrace();
                                Platform.runLater(() -> new Alert(Alert.AlertType.ERROR,
                                        "Failed to process image: " + ex.getMessage()).show());
                            }
                            uploadedPngBytes[0] = null;
                        }

                        // ─────── UPDATE PROFILE ───────
                        String selectFilter = URLEncoder.encode(game.currentUser, StandardCharsets.UTF_8);
                        String profileUrl = game.SUPABASE_URL + "/rest/v1/profiles?username=eq." + selectFilter;

                        HttpRequest request = HttpRequest.newBuilder()
                                .uri(URI.create(profileUrl))
                                .header("apikey", game.SUPABASE_ANON_KEY)
                                .header("Authorization", "Bearer " + game.SUPABASE_ANON_KEY)
                                .header("Content-Type", "application/json")
                                .header("Prefer", "return=representation")
                                .method("PATCH", HttpRequest.BodyPublishers.ofString(payload.toString()))
                                .build();

                        HttpResponse<String> resp = game.httpClient.send(request, HttpResponse.BodyHandlers.ofString());
                        if (resp.statusCode() != 200 && resp.statusCode() != 204) {
                            throw new Exception("Profile update failed: " + resp.body());
                        }

                        Platform.runLater(() -> {
                            game.currentUser = newUsername;
                            game.saveLoginToken(newUsername);

                            if (game.usernameLabel != null) {
                                game.usernameLabel.setText(newUsername);
                            }

                            // CRITICAL: Invalidate profile picture cache for this user
                            // This ensures leaderboard shows the updated picture immediately
                            game.invalidateProfilePictureCache(newUsername);

                            // If username changed, also invalidate the old username's cache
                            if (!newUsername.equals(game.currentUser)) {
                                game.invalidateProfilePictureCache(game.currentUser);
                            }

                            game.refreshLeaderboardData();
                            game.profileSettingsStage.close();

                            String message = changingPassword ? "Profile and password updated!" : "Profile updated!";
                            game.showToastNotification(message);
                        });

                        return null;
                    }

                    @Override
                    protected void failed() {
                        Platform.runLater(() -> {
                            String errorMessage;
                            String exMsg = getException().getMessage();

                            if ("USERNAME_TAKEN".equals(exMsg)) {
                                errorMessage = "This username is already taken. Please choose a different username.";
                            } else if (exMsg != null && exMsg.contains("Current password is incorrect")) {
                                errorMessage = "Current password is incorrect.";
                            } else {
                                errorMessage = "Failed to save profile. Please try again.";
                            }

                            new Alert(Alert.AlertType.ERROR, errorMessage).show();
                            saveButton.setDisable(false);
                            game.hideLoadingScreen();
                        });
                    }

                    @Override
                    protected void succeeded() {
                        Platform.runLater(() -> {
                            game.hideLoadingScreen();
                            saveButton.setDisable(false);
                        });
                    }
                };

                game.executor.submit(task);
            });

            Button closeButton = new Button("Cancel");
            closeButton.setStyle("-fx-background-color: rgba(60, 60, 80, 0.5); -fx-border-color: rgba(100, 100, 120, 0.6); -fx-border-width: 1.5; -fx-border-radius: 10; -fx-background-radius: 10; -fx-text-fill: #AAAACC; -fx-font-family: 'Segoe UI', 'Arial'; -fx-font-size: 14; -fx-font-weight: 600; -fx-padding: 13 35; -fx-cursor: hand;");
            closeButton.setPrefWidth(200);
            closeButton.setOnAction(e -> game.profileSettingsStage.close());

            closeButton.setOnMouseEntered(e -> closeButton.setStyle("-fx-background-color: rgba(70, 70, 90, 0.7); -fx-border-color: rgba(120, 120, 140, 0.8); -fx-border-width: 1.5; -fx-border-radius: 10; -fx-background-radius: 10; -fx-text-fill: #CCCCEE; -fx-font-family: 'Segoe UI', 'Arial'; -fx-font-size: 14; -fx-font-weight: 600; -fx-padding: 13 35; -fx-cursor: hand;"));
            closeButton.setOnMouseExited(e -> closeButton.setStyle("-fx-background-color: rgba(60, 60, 80, 0.5); -fx-border-color: rgba(100, 100, 120, 0.6); -fx-border-width: 1.5; -fx-border-radius: 10; -fx-background-radius: 10; -fx-text-fill: #AAAACC; -fx-font-family: 'Segoe UI', 'Arial'; -fx-font-size: 14; -fx-font-weight: 600; -fx-padding: 13 35; -fx-cursor: hand;"));

            buttonBox.getChildren().addAll(saveButton, closeButton);

            // Only add children if mainContainer is empty (first time creation)
            if (mainContainer.getChildren().isEmpty()) {
                mainContainer.getChildren().addAll(
                        titleLabel,
                        contentLayout,
                        buttonBox
                );
            }

            overlay.getChildren().add(mainContainer);
            StackPane.setAlignment(mainContainer, Pos.CENTER);

            // Create scene with transparent background
            Scene scene = new Scene(overlay, 860, 660);
            scene.setFill(Color.TRANSPARENT);

            game.profileSettingsStage.setScene(scene);

            // Perfect centering
            game.profileSettingsStage.setOnShown(e -> {
                double x = game.primaryStage.getX() + (game.primaryStage.getWidth() - game.profileSettingsStage.getWidth()) / 2;
                double y = game.primaryStage.getY() + (game.primaryStage.getHeight() - game.profileSettingsStage.getHeight()) / 2;
                game.profileSettingsStage.setX(x);
                game.profileSettingsStage.setY(y);
            });
        } else {
            // Reset field values when reopening
            VBox mainContainer = (VBox) ((StackPane) game.profileSettingsStage.getScene().getRoot()).getChildren().get(0);
            // Find and reset the username field and password fields
            HBox contentLayout = (HBox) mainContainer.getChildren().get(1);
            VBox leftPanel = (VBox) contentLayout.getChildren().get(0);
            StackPane profileStack = (StackPane) leftPanel.getChildren().get(1);
            ImageView profilePreview = (ImageView) profileStack.getChildren().get(1);

            // Simply update the preview with the current image (no delay needed)
            if (game.currentProfileImage != null) {
                profilePreview.setImage(game.currentProfileImage);
            } else {
                profilePreview.setImage(new Image("file:resources/images/default_profile.png"));
            }

            TextField usernameField = (TextField) leftPanel.getChildren().get(leftPanel.getChildren().size() - 1);
            usernameField.setText(game.currentUser);

            VBox rightPanel = (VBox) contentLayout.getChildren().get(1);

            // Reset old password container
            StackPane oldPassContainer = (StackPane) rightPanel.getChildren().get(1);
            PasswordField oldPassField = (PasswordField) oldPassContainer.getChildren().get(0);
            TextField oldPassTextField = (TextField) oldPassContainer.getChildren().get(1);
            Button oldPassToggle = (Button) oldPassContainer.getChildren().get(2);
            oldPassField.clear();
            oldPassTextField.clear();
            // Reset to hidden state
            oldPassField.setVisible(true);
            oldPassField.setManaged(true);
            oldPassTextField.setVisible(false);
            oldPassTextField.setManaged(false);
            oldPassToggle.setText("👁");

            // Reset new password container
            StackPane newPassContainer = (StackPane) rightPanel.getChildren().get(2);
            PasswordField newPassField = (PasswordField) newPassContainer.getChildren().get(0);
            TextField newPassTextField = (TextField) newPassContainer.getChildren().get(1);
            Button newPassToggle = (Button) newPassContainer.getChildren().get(2);
            newPassField.clear();
            newPassTextField.clear();
            // Reset to hidden state
            newPassField.setVisible(true);
            newPassField.setManaged(true);
            newPassTextField.setVisible(false);
            newPassTextField.setManaged(false);
            newPassToggle.setText("👁");

            // Clear confirm password
            ((PasswordField) rightPanel.getChildren().get(3)).clear();
        }

        // Show the stage window
        game.profileSettingsStage.show();
        game.profileSettingsStage.requestFocus();
    }
}