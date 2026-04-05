package EventRegistration;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import static EventRegistration.UIComponents.*;

public class MainApp extends Application {

    private final EventManager manager = EventManager.getInstance();
    private Stage primaryStage;
    private User currentUser;
    private VBox centerPanel = new VBox(10);
    private BorderPane adminRoot = new BorderPane();
    private BorderPane userRoot  = new BorderPane();

    @Override
    public void start(Stage stage) {
        primaryStage = stage;
        primaryStage.setMinWidth(960);
        primaryStage.setMinHeight(600);
        showLoginScene();
    }

    // ==================== LOGIN ====================
    private void showLoginScene() {
        VBox brand = new VBox(18);
        brand.setAlignment(Pos.CENTER);
        brand.setPrefWidth(400);
        brand.setPadding(new Insets(40));
        brand.setStyle("-fx-background-color:#0a0a0f;");
        Label appTitle = new Label("EventHub");
        appTitle.setStyle("-fx-font-size:46px;-fx-font-weight:bold;-fx-text-fill:#e8c97e;");
        Label tagline = new Label("Manage. Register. Attend.");
        tagline.setStyle("-fx-font-size:14px;-fx-text-fill:#8899aa;");
        Separator sep = new Separator();
        sep.setStyle("-fx-background-color:#2a2a3e;"); sep.setMaxWidth(200);
        brand.getChildren().addAll(appTitle, tagline, sep,
            featureLine("✦  Elegant event management"),
            featureLine("✦  Instant seat registration"),
            featureLine("✦  Full admin control panel"));

        TextField     username = darkTextField("Username");
        PasswordField password = darkPasswordField("Password");
        Label errorLabel = new Label("");
        errorLabel.setStyle("-fx-text-fill:#e05c5c;-fx-font-size:12px;");
        Button loginBtn = goldButton("Sign In"), registerBtn = ghostButton("Create Account");
        loginBtn.setMaxWidth(Double.MAX_VALUE); registerBtn.setMaxWidth(Double.MAX_VALUE);

        loginBtn.setOnAction(e -> {
            if (username.getText().isBlank() || password.getText().isBlank()) {
                errorLabel.setText("Please fill in all fields."); return;
            }
            currentUser = manager.login(username.getText().trim(), password.getText());
            if (currentUser != null) showDashboard();
            else errorLabel.setText("Invalid username or password.");
        });
        registerBtn.setOnAction(e -> showRegisterScene());

        Label heading = new Label("Sign In");
        heading.setStyle("-fx-font-size:30px;-fx-font-weight:bold;-fx-text-fill:#f0ece0;");
        Label sub = new Label("Welcome back to EventHub");
        sub.setStyle("-fx-font-size:13px;-fx-text-fill:#556677;");
        Label noAccount = new Label("Don't have an account?");
        noAccount.setStyle("-fx-font-size:13px;-fx-text-fill:#556677;");

        VBox formBox = new VBox(14);
        formBox.setMaxWidth(320); formBox.setAlignment(Pos.CENTER_LEFT);
        formBox.getChildren().addAll(heading, sub, new Label(""),
            darkLabel("Username"), username, darkLabel("Password"), password,
            errorLabel, loginBtn, new Separator(), noAccount, registerBtn);

        VBox formPanel = new VBox(0);
        formPanel.setAlignment(Pos.CENTER);
        formPanel.setStyle("-fx-background-color:#13131e;");
        formPanel.getChildren().add(formBox);
        HBox.setHgrow(formPanel, Priority.ALWAYS);

        setScene(new HBox(brand, formPanel), 820, 520, "Sign In");
    }

    // ==================== REGISTER ====================
    private void showRegisterScene() {
        VBox brand = new VBox(18);
        brand.setAlignment(Pos.CENTER); brand.setPrefWidth(360);
        brand.setPadding(new Insets(40)); brand.setStyle("-fx-background-color:#0a0a0f;");
        Label appTitle = new Label("EventHub");
        appTitle.setStyle("-fx-font-size:40px;-fx-font-weight:bold;-fx-text-fill:#e8c97e;");
        Label tagline = new Label("Join us today");
        tagline.setStyle("-fx-font-size:14px;-fx-text-fill:#8899aa;");
        Label info = new Label("Create your free account\nand start exploring events.");
        info.setStyle("-fx-font-size:13px;-fx-text-fill:#445566;"); info.setWrapText(true);
        brand.getChildren().addAll(appTitle, tagline, new Separator(), info);

        TextField     nameF = darkTextField("Full Name"),  emailF = darkTextField("Email Address"),
                      userF = darkTextField("Username");
        PasswordField passF = darkPasswordField("Password"), cfrmF = darkPasswordField("Confirm Password");
        Label errorLabel = new Label("");
        errorLabel.setStyle("-fx-text-fill:#e05c5c;-fx-font-size:12px;");
        Button createBtn = goldButton("Create Account"), backBtn = ghostButton("← Back to Login");
        createBtn.setMaxWidth(Double.MAX_VALUE); backBtn.setMaxWidth(Double.MAX_VALUE);

        createBtn.setOnAction(e -> {
            String n = nameF.getText().trim(), em = emailF.getText().trim(),
                   un = userF.getText().trim(), pw = passF.getText(), cf = cfrmF.getText();
            if (n.isEmpty() || em.isEmpty() || un.isEmpty() || pw.isEmpty()) {
                errorLabel.setText("All fields are required."); return;
            }
            if (!em.contains("@")) { errorLabel.setText("Enter a valid email."); return; }
            if (!pw.equals(cf))    { errorLabel.setText("Passwords do not match."); return; }
            if (manager.registerUser(new User(n, em, un, pw, "USER")))
                { showAlert(Alert.AlertType.INFORMATION, "Account created! Please log in."); showLoginScene(); }
            else errorLabel.setText("Username already exists.");
        });
        backBtn.setOnAction(e -> showLoginScene());

        Label heading = new Label("Create Account");
        heading.setStyle("-fx-font-size:28px;-fx-font-weight:bold;-fx-text-fill:#f0ece0;");
        Label sub = new Label("Fill in your details below");
        sub.setStyle("-fx-font-size:13px;-fx-text-fill:#556677;");

        VBox formBox = new VBox(11);
        formBox.setMaxWidth(310); formBox.setAlignment(Pos.CENTER_LEFT);
        formBox.getChildren().addAll(heading, sub, new Label(""),
            darkLabel("Full Name"), nameF, darkLabel("Email"), emailF,
            darkLabel("Username"), userF, darkLabel("Password"), passF,
            darkLabel("Confirm Password"), cfrmF, errorLabel, createBtn, backBtn);

        VBox formPanel = new VBox(0);
        formPanel.setAlignment(Pos.CENTER); formPanel.setStyle("-fx-background-color:#13131e;");
        formPanel.getChildren().add(formBox);
        HBox.setHgrow(formPanel, Priority.ALWAYS);

        setScene(new HBox(brand, formPanel), 820, 600, "Register");
    }

    // ==================== DASHBOARD ROUTING ====================
    private void showDashboard() {
        if (currentUser.isAdmin()) showAdminDashboard(); else showUserDashboard();
    }

    // ==================== ADMIN ====================
    private void showAdminDashboard() {
        centerPanel = new VBox(10); adminRoot = new BorderPane();
        adminRoot.setStyle("-fx-background-color:#0f0f1a;");
        refreshAdminSidebar("Dashboard"); showAdminDashboardMain();
        setRootScene(adminRoot, 1000, 640, "Admin");
    }

    private void refreshAdminSidebar(String active) {
        String[]   menu    = {"Dashboard", "Manage Events", "View Participants", "Logout"};
        Runnable[] actions = { this::showAdminDashboardMain, this::showAdminManageEvents,
                               this::showAdminViewParticipants, this::showLoginScene };
        adminRoot.setLeft(createSidebar(menu, actions, active, "⬡  Administrator"));
        adminRoot.setCenter(centerPanel);
    }

    private void showAdminDashboardMain() {
        prepareCenter();
        Label sub = new Label("Welcome, " + currentUser.getName() + ". Here's your event overview.");
        sub.setStyle("-fx-text-fill:#556677;-fx-font-size:13px;");
        int te = manager.getEvents().size(),
            ts = manager.getEvents().stream().mapToInt(Event::getTotalSeats).sum(),
            tr = manager.getEvents().stream().mapToInt(e -> e.getParticipants().size()).sum();
        HBox stats = new HBox(16,
            statCard("Events",        String.valueOf(te), "#e8c97e"),
            statCard("Total Seats",   String.valueOf(ts), "#7eb8e8"),
            statCard("Registrations", String.valueOf(tr), "#7ee8a2"));
        stats.setPadding(new Insets(18, 0, 18, 0));
        TableView<Event> table = createEventTable(manager.getEvents());
        VBox.setVgrow(table, Priority.ALWAYS);
        centerPanel.getChildren().addAll(darkHeader("Dashboard"), sub, stats, divider(), table);
        refreshAdminSidebar("Dashboard");
    }

    private void showAdminManageEvents() {
        prepareCenter();
        TableView<Event> table = createEventTable(manager.getEvents());
        VBox.setVgrow(table, Priority.ALWAYS);

        Button createBtn  = goldButton("+ Create Event"),  editBtn    = ghostButton("✎  Edit"),
               deleteBtn  = dangerButton("✕  Delete"),     refreshBtn = ghostButton("↻  Refresh");

        createBtn.setOnAction(e -> showEventFormScene(null));
        editBtn.setOnAction(e -> {
            Event sel = table.getSelectionModel().getSelectedItem();
            if (sel != null) showEventFormScene(sel);
            else showAlert(Alert.AlertType.WARNING, "Select an event to edit.");
        });
        deleteBtn.setOnAction(e -> {
            Event sel = table.getSelectionModel().getSelectedItem();
            if (sel == null) { showAlert(Alert.AlertType.WARNING, "Select an event to delete."); return; }
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
                "Delete \"" + sel.getName() + "\"?", ButtonType.YES, ButtonType.NO);
            confirm.setTitle("Confirm Delete");
            confirm.showAndWait().ifPresent(r -> {
                if (r == ButtonType.YES) {
                    manager.deleteEvent(sel.getId());
                    table.setItems(FXCollections.observableArrayList(manager.getEvents()));
                    showAlert(Alert.AlertType.INFORMATION, "Event deleted.");
                }
            });
        });
        refreshBtn.setOnAction(e -> table.setItems(FXCollections.observableArrayList(manager.getEvents())));

        HBox actions = new HBox(10, createBtn, editBtn, deleteBtn, refreshBtn);
        actions.setAlignment(Pos.CENTER_RIGHT); actions.setPadding(new Insets(12, 0, 0, 0));
        centerPanel.getChildren().addAll(darkHeader("Manage Events"), divider(), table, actions);
        refreshAdminSidebar("Manage Events");
    }

    private void showAdminViewParticipants() {
        prepareCenter();
        TableView<Registration> table = new TableView<>();
        table.setStyle("-fx-background-color:#1a1a2e;-fx-background-radius:8;");

        TableColumn<Registration, String> evC = new TableColumn<>("Event"),
            unC = new TableColumn<>("Username"), nmC = new TableColumn<>("Full Name"), emC = new TableColumn<>("Email");
        evC.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getEvent().getName()));
        unC.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getUser().getUsername()));
        nmC.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getUser().getName()));
        emC.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getUser().getEmail()));
        table.getColumns().addAll(evC, unC, nmC, emC);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        ObservableList<Registration> regs = FXCollections.observableArrayList();
        for (Event e : manager.getEvents())
            for (User u : e.getParticipants()) regs.add(new Registration(u, e));
        table.setItems(regs);
        table.setPlaceholder(placeholder("No registrations yet."));
        VBox.setVgrow(table, Priority.ALWAYS);

        Label count = new Label("Total Registrations: " + regs.size());
        count.setStyle("-fx-font-size:13px;-fx-text-fill:#7eb8e8;");
        centerPanel.getChildren().addAll(darkHeader("View Participants"), count, divider(), table);
        refreshAdminSidebar("View Participants");
    }

    // ==================== CREATE / EDIT EVENT (merged) ====================
    private void showEventFormScene(Event event) {
        prepareCenter();
        boolean isNew = (event == null);
        VBox card = buildEventFormCard(event);
        TextField nameF  = (TextField) card.lookup("#nameField");
        TextArea  descF  = (TextArea)  card.lookup("#descField");
        TextField dtF    = (TextField) card.lookup("#dtField");
        TextField venueF = (TextField) card.lookup("#venueField");
        TextField seatsF = (TextField) card.lookup("#seatsField");
        Label     errLbl = (Label)     card.lookup("#errorLabel");

        Button save = goldButton(isNew ? "Save Event" : "Save Changes"), back = ghostButton("← Cancel");
        HBox btns = new HBox(10, save, back); btns.setAlignment(Pos.CENTER_RIGHT);

        save.setOnAction(e -> {
            if (nameF.getText().isBlank() || dtF.getText().isBlank()
                    || venueF.getText().isBlank() || seatsF.getText().isBlank()) {
                errLbl.setText("All fields except description are required."); return;
            }
            try {
                int s = Integer.parseInt(seatsF.getText().trim());
                if (isNew) {
                    if (s <= 0) throw new NumberFormatException();
                    manager.createEvent(nameF.getText().trim(), descF.getText().trim(),
                        dtF.getText().trim(), venueF.getText().trim(), s);
                    showAlert(Alert.AlertType.INFORMATION, "Event created successfully!");
                } else {
                    if (s < event.getParticipants().size()) {
                        errLbl.setText("Seats cannot be less than current registrations (" + event.getParticipants().size() + ")."); return;
                    }
                    manager.updateEvent(event.getId(), nameF.getText().trim(), descF.getText().trim(),
                        dtF.getText().trim(), venueF.getText().trim(), s);
                    showAlert(Alert.AlertType.INFORMATION, "Event updated successfully!");
                }
                showAdminManageEvents();
            } catch (NumberFormatException ex) { errLbl.setText("Seats must be a positive number."); }
        });
        back.setOnAction(e -> showAdminManageEvents());
        card.getChildren().add(btns);

        ScrollPane scroll = scrollWrap(card);
        VBox.setVgrow(scroll, Priority.ALWAYS);
        centerPanel.getChildren().addAll(darkHeader(isNew ? "Create New Event" : "Edit Event"), divider(), scroll);
        refreshAdminSidebar("Manage Events");
    }

    // ==================== USER ====================
    private void showUserDashboard() {
        centerPanel = new VBox(10); userRoot = new BorderPane();
        userRoot.setStyle("-fx-background-color:#0f0f1a;");
        refreshUserSidebar("All Events"); showUserAllEvents();
        setRootScene(userRoot, 1000, 640, currentUser.getName());
    }

    private void refreshUserSidebar(String active) {
        String[]   menu    = {"All Events", "My Registrations", "Logout"};
        Runnable[] actions = { this::showUserAllEvents, this::showUserRegisteredEvents, this::showLoginScene };
        userRoot.setLeft(createSidebar(menu, actions, active, "⬡  " + currentUser.getUsername()));
        userRoot.setCenter(centerPanel);
    }

    private void showUserAllEvents() {
        prepareCenter();
        Label sub = new Label("Browse and register for available events.");
        sub.setStyle("-fx-text-fill:#556677;-fx-font-size:13px;");
        TableView<Event> table = createEventTable(manager.getEvents());
        VBox detailBox = eventDetailBox();
        table.getSelectionModel().selectedItemProperty().addListener(
            (obs, o, sel) -> { if (sel != null) populateDetailBox(sel, detailBox, true, table); });
        SplitPane split = splitPane(table, detailBox);
        VBox.setVgrow(split, Priority.ALWAYS);
        centerPanel.getChildren().addAll(darkHeader("All Events"), sub, divider(), split);
        refreshUserSidebar("All Events");
    }

    private void showUserRegisteredEvents() {
        prepareCenter();
        Label sub = new Label("Events you have registered for. Select one to unregister.");
        sub.setStyle("-fx-text-fill:#556677;-fx-font-size:13px;");

        ObservableList<Event> registered = FXCollections.observableArrayList();
        for (Event e : manager.getEvents())
            if (e.getParticipants().stream().anyMatch(u -> u.getUsername().equals(currentUser.getUsername())))
                registered.add(e);

        TableView<Event> table = createEventTable(registered);
        table.setPlaceholder(placeholder("You have no registrations yet."));
        Label count = new Label("Registered for " + registered.size() + " event(s).");
        count.setStyle("-fx-font-size:13px;-fx-text-fill:#e8c97e;");
        VBox detailBox = eventDetailBox();
        table.getSelectionModel().selectedItemProperty().addListener(
            (obs, o, sel) -> { if (sel != null) populateDetailBox(sel, detailBox, false, table); });
        SplitPane split = splitPane(table, detailBox);
        VBox.setVgrow(split, Priority.ALWAYS);
        centerPanel.getChildren().addAll(darkHeader("My Registrations"), sub, count, divider(), split);
        refreshUserSidebar("My Registrations");
    }

    // ==================== EVENT DETAIL ====================
    private void populateDetailBox(Event event, VBox box, boolean isAllEvents, TableView<Event> table) {
        box.getChildren().clear();
        Label name = new Label(event.getName());
        name.setStyle("-fx-font-size:18px;-fx-font-weight:bold;-fx-text-fill:#e8c97e;"); name.setWrapText(true);
        Label desc = new Label(event.getDescription().isBlank() ? "No description provided." : event.getDescription());
        desc.setWrapText(true); desc.setStyle("-fx-text-fill:#8899aa;-fx-font-size:13px;");
        int taken = event.getParticipants().size(), total = event.getTotalSeats(), left = total - taken;
        ProgressBar pb = new ProgressBar(total == 0 ? 0 : (double) taken / total);
        pb.setMaxWidth(Double.MAX_VALUE);
        pb.setStyle(left == 0 ? "-fx-accent:#e05c5c;" : "-fx-accent:#e8c97e;");
        box.getChildren().addAll(name, desc, new Separator(),
            infoRow("📅", "Date/Time", event.getDateTime()),
            infoRow("📍", "Venue",     event.getVenue()),
            infoRow("💺", "Seats",     taken + " / " + total + "  (" + left + " remaining)"), pb);

        boolean isRegistered = event.getParticipants().stream()
            .anyMatch(u -> u.getUsername().equals(currentUser.getUsername()));

        if (isAllEvents) {
            if (isRegistered) {
                Label badge = new Label("✓  Already Registered");
                badge.setStyle("-fx-text-fill:#7ee8a2;-fx-font-size:13px;-fx-font-weight:bold;");
                box.getChildren().add(badge);
            } else if (!event.hasAvailableSeats()) {
                Label full = new Label("✕  Event is Full");
                full.setStyle("-fx-text-fill:#e05c5c;-fx-font-size:13px;-fx-font-weight:bold;");
                box.getChildren().add(full);
            } else {
                Button regBtn = goldButton("Register for this Event");
                regBtn.setMaxWidth(Double.MAX_VALUE);
                regBtn.setOnAction(e -> {
                    if (manager.register(currentUser, event.getId())) {
                        showAlert(Alert.AlertType.INFORMATION, "Registered for \"" + event.getName() + "\"!");
                        table.setItems(FXCollections.observableArrayList(manager.getEvents()));
                        table.getSelectionModel().clearSelection();
                        box.getChildren().clear();
                        Label done = new Label("✓  Registration successful!");
                        done.setStyle("-fx-text-fill:#7ee8a2;-fx-font-size:13px;");
                        box.getChildren().add(done);
                    } else showAlert(Alert.AlertType.WARNING, "Registration failed. Event may be full.");
                });
                box.getChildren().add(regBtn);
            }
        } else if (isRegistered) {
            Button unregBtn = dangerButton("Unregister from this Event");
            unregBtn.setMaxWidth(Double.MAX_VALUE);
            unregBtn.setOnAction(e -> {
                Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
                    "Unregister from \"" + event.getName() + "\"?", ButtonType.YES, ButtonType.NO);
                confirm.setTitle("Confirm Unregister");
                confirm.showAndWait().ifPresent(r -> {
                    if (r == ButtonType.YES) {
                        if (manager.unregister(currentUser, event.getId()))
                            { showAlert(Alert.AlertType.INFORMATION, "Unregistered successfully."); showUserRegisteredEvents(); }
                        else showAlert(Alert.AlertType.ERROR, "Unregistration failed.");
                    }
                });
            });
            box.getChildren().add(unregBtn);
        }
    }

    // ==================== SMALL HELPERS ====================
    private void prepareCenter() {
        centerPanel.getChildren().clear();
        centerPanel.setPadding(new Insets(32));
        centerPanel.setStyle("-fx-background-color:#0f0f1a;");
    }

    private void setScene(Parent root, double w, double h, String title) {
        Scene scene = new Scene(root, w, h);
        try { scene.getStylesheets().add(getClass().getResource("/EventRegistration/style.css").toExternalForm()); }
        catch (Exception ignored) {}
        primaryStage.setScene(scene);
        primaryStage.setTitle("EventHub — " + title);
        primaryStage.show();
    }

    private void setRootScene(BorderPane root, double w, double h, String title) {
        Scene scene = new Scene(root, w, h);
        try { scene.getStylesheets().add(getClass().getResource("/EventRegistration/style.css").toExternalForm()); }
        catch (Exception ignored) {}
        primaryStage.setScene(scene);
        primaryStage.setTitle("EventHub — " + title);
    }

    public static void main(String[] args) { launch(args); }
}