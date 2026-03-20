package EventRegistration;

import javafx.application.Application;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MainApp extends Application {

    private EventManager manager = EventManager.getInstance();
    private Stage primaryStage;
    private User currentUser;

    @Override
    public void start(Stage stage) {
        primaryStage = stage;
        showLoginScene();
    }

    // ---------------- LOGIN ----------------
    private void showLoginScene() {
        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(30));

        Label title = new Label("Event Management System");
        title.getStyleClass().add("title");

        TextField username = new TextField();
        username.setPromptText("Username");

        PasswordField password = new PasswordField();
        password.setPromptText("Password");

        Button loginBtn = new Button("Login");
        Button registerBtn = new Button("Create Account");

        loginBtn.setMaxWidth(Double.MAX_VALUE);
        registerBtn.setMaxWidth(Double.MAX_VALUE);

        loginBtn.setOnAction(e -> {
            currentUser = manager.login(username.getText(), password.getText());
            if (currentUser != null) showDashboard();
            else showAlert(Alert.AlertType.ERROR, "Invalid login");
        });

        registerBtn.setOnAction(e -> showRegisterScene());

        VBox form = new VBox(10, username, password, loginBtn, registerBtn);
        form.setMaxWidth(250);

        root.getChildren().addAll(title, form);
        primaryStage.setScene(new Scene(root, 400, 350));
        primaryStage.setTitle("Login");
        primaryStage.show();
    }

    // ---------------- REGISTER ----------------
    private void showRegisterScene() {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(30));
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setAlignment(Pos.CENTER);

        Label title = new Label("Create Account");
        title.getStyleClass().add("title");

        TextField name = new TextField(); name.setPromptText("Full Name");
        TextField email = new TextField(); email.setPromptText("Email");
        TextField username = new TextField(); username.setPromptText("Username");
        PasswordField password = new PasswordField(); password.setPromptText("Password");

        grid.add(new Label("Name:"), 0, 0); grid.add(name, 1, 0);
        grid.add(new Label("Email:"), 0, 1); grid.add(email, 1, 1);
        grid.add(new Label("Username:"), 0, 2); grid.add(username, 1, 2);
        grid.add(new Label("Password:"), 0, 3); grid.add(password, 1, 3);

        Button registerBtn = new Button("Register");
        Button backBtn = new Button("Back");

        HBox buttons = new HBox(10, registerBtn, backBtn);
        buttons.setAlignment(Pos.CENTER);

        registerBtn.setOnAction(e -> {
            boolean ok = manager.registerUser(
                    new User(name.getText(), email.getText(), username.getText(), password.getText(), "USER")
            );
            if (ok) {
                showAlert(Alert.AlertType.INFORMATION, "Account created!");
                showLoginScene();
            } else {
                showAlert(Alert.AlertType.ERROR, "Username exists");
            }
        });

        backBtn.setOnAction(e -> showLoginScene());

        VBox root = new VBox(20, title, grid, buttons);
        root.setAlignment(Pos.CENTER);
        primaryStage.setScene(new Scene(root, 450, 400));
    }

    // ---------------- DASHBOARD ----------------
    private void showDashboard() {
        if (currentUser.isAdmin()) showAdminDashboard();
        else showUserDashboard();
    }

    // ---------------- SIDEBAR CREATION ----------------
    private VBox createSidebar(String[] items, Runnable[] actions) {
        VBox sidebar = new VBox(10);
        sidebar.setPadding(new Insets(15));
        sidebar.setStyle("-fx-background-color: #2c3e50;");
        sidebar.setPrefWidth(180);

        for (int i = 0; i < items.length; i++) {
            Button btn = new Button(items[i]);
            btn.setMaxWidth(Double.MAX_VALUE);
            btn.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-font-size: 14;");
            final int idx = i;
            btn.setOnAction(e -> actions[idx].run());
            btn.setOnMouseEntered(e -> btn.setStyle("-fx-background-color: #34495e; -fx-text-fill: white;"));
            btn.setOnMouseExited(e -> btn.setStyle("-fx-background-color: transparent; -fx-text-fill: white;"));
            sidebar.getChildren().add(btn);
        }
        return sidebar;
    }

    // ---------------- ADMIN DASHBOARD ----------------
    private void showAdminDashboard() {
        BorderPane root = new BorderPane();

        // Sidebar
        String[] menu = {"Dashboard", "Manage Events", "View Participants", "Logout"};
        Runnable[] actions = {
                this::showAdminDashboardMain,
                this::showAdminManageEvents,
                this::showAdminViewParticipants,
                () -> showLoginScene()
        };
        VBox sidebar = createSidebar(menu, actions);
        root.setLeft(sidebar);

        // Initial main panel
        showAdminDashboardMain();
        root.setCenter(centerPanel);

        primaryStage.setScene(new Scene(root, 900, 550));
    }

    private VBox centerPanel = new VBox(10);

    // ---------------- ADMIN MAIN PANEL ----------------
    private void showAdminDashboardMain() {
        centerPanel.getChildren().clear();
        Label header = new Label("Admin Dashboard");
        header.getStyleClass().add("title");

        TableView<Event> table = createEventTableWithDetails();

        centerPanel.getChildren().addAll(header, table);
        centerPanel.setPadding(new Insets(20));
    }

    private void showAdminManageEvents() {
        centerPanel.getChildren().clear();
        Label header = new Label("Manage Events");
        header.getStyleClass().add("title");

        TableView<Event> table = createEventTableWithDetails();
        Button createBtn = new Button("Create Event");
        Button deleteBtn = new Button("Delete Event");

        createBtn.setOnAction(e -> showCreateEventScene());
        deleteBtn.setOnAction(e -> {
            Event selected = table.getSelectionModel().getSelectedItem();
            if (selected != null) {
                manager.deleteEvent(selected.getId());
                table.setItems(FXCollections.observableArrayList(manager.getEvents()));
            }
        });

        HBox actions = new HBox(10, createBtn, deleteBtn);
        actions.setAlignment(Pos.CENTER);

        centerPanel.getChildren().addAll(header, table, actions);
        centerPanel.setPadding(new Insets(20));
    }

    private void showAdminViewParticipants() {
        centerPanel.getChildren().clear();
        Label header = new Label("View Participants");
        header.getStyleClass().add("title");

        TableView<Registration> table = new TableView<>();
        TableColumn<Registration, String> eventCol = new TableColumn<>("Event");
        TableColumn<Registration, String> userCol = new TableColumn<>("User");

        eventCol.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getEvent().getName()));
        userCol.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getUser().getUsername()));

        table.getColumns().addAll(eventCol, userCol);

        ObservableList<Registration> registrations = FXCollections.observableArrayList();
        for (Event e : manager.getEvents()) {
            for (User u : e.getParticipants()) {
                registrations.add(new Registration(u, e));
            }
        }
        table.setItems(registrations);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        centerPanel.getChildren().addAll(header, table);
        centerPanel.setPadding(new Insets(20));
    }

    // ---------------- USER DASHBOARD ----------------
    private void showUserDashboard() {
        BorderPane root = new BorderPane();

        String[] menu = {"All Events", "Registered Events", "Logout"};
        Runnable[] actions = {
                this::showUserAllEvents,
                this::showUserRegisteredEvents,
                () -> showLoginScene()
        };
        VBox sidebar = createSidebar(menu, actions);
        root.setLeft(sidebar);

        // Initial main panel
        showUserAllEvents();
        root.setCenter(centerPanel);

        primaryStage.setScene(new Scene(root, 900, 550));
    }

    private void showUserAllEvents() {
        centerPanel.getChildren().clear();
        Label header = new Label("All Events");
        header.getStyleClass().add("title");

        SplitPane split = new SplitPane();
        TableView<Event> table = createEventTableWithDetails();
        VBox detailBox = createEventDetailBox();

        table.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) showEventDetails(newSel, detailBox, true);
        });

        split.getItems().addAll(table, detailBox);
        split.setDividerPositions(0.6);

        centerPanel.getChildren().addAll(header, split);
        centerPanel.setPadding(new Insets(20));
    }

    private void showUserRegisteredEvents() {
        centerPanel.getChildren().clear();
        Label header = new Label("Registered Events");
        header.getStyleClass().add("title");

        SplitPane split = new SplitPane();
        TableView<Event> table = createEventTableWithDetails();

        ObservableList<Event> registered = FXCollections.observableArrayList();
        for (Event e : manager.getEvents()) {
            if (e.getParticipants().contains(currentUser)) registered.add(e);
        }
        table.setItems(registered);

        VBox detailBox = createEventDetailBox();

        table.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) showEventDetails(newSel, detailBox, false);
        });

        split.getItems().addAll(table, detailBox);
        split.setDividerPositions(0.6);

        centerPanel.getChildren().addAll(header, split);
        centerPanel.setPadding(new Insets(20));
    }

    // ---------------- CREATE EVENT SCENE ----------------
    private void showCreateEventScene() {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setAlignment(Pos.CENTER);

        TextField name = new TextField(); name.setPromptText("Event Name");
        TextArea desc = new TextArea(); desc.setPromptText("Description");
        TextField dt = new TextField(); dt.setPromptText("Date & Time");
        TextField venue = new TextField(); venue.setPromptText("Venue");
        TextField seats = new TextField(); seats.setPromptText("Seats");

        grid.add(new Label("Name:"), 0, 0); grid.add(name, 1, 0);
        grid.add(new Label("Description:"), 0, 1); grid.add(desc, 1, 1);
        grid.add(new Label("Date/Time:"), 0, 2); grid.add(dt, 1, 2);
        grid.add(new Label("Venue:"), 0, 3); grid.add(venue, 1, 3);
        grid.add(new Label("Seats:"), 0, 4); grid.add(seats, 1, 4);

        Button save = new Button("Save");
        Button back = new Button("Back");

        HBox btns = new HBox(10, save, back);
        btns.setAlignment(Pos.CENTER);

        save.setOnAction(e -> {
            try {
                int s = Integer.parseInt(seats.getText());
                manager.createEvent(name.getText(), desc.getText(), dt.getText(), venue.getText(), s);
                showAlert(Alert.AlertType.INFORMATION, "Event Created");
                showAdminDashboard();
            } catch (Exception ex) {
                showAlert(Alert.AlertType.ERROR, "Invalid Input");
            }
        });

        back.setOnAction(e -> showAdminDashboard());

        VBox root = new VBox(20, new Label("Create Event"), grid, btns);
        root.setAlignment(Pos.CENTER);
        Scene scene = new Scene(root, 500, 500);
        primaryStage.setScene(scene);
    }

    // ---------------- TABLE + DETAILS ----------------
    private TableView<Event> createEventTableWithDetails() {
        TableView<Event> table = new TableView<>();

        TableColumn<Event, String> nameCol = new TableColumn<>("Name");
        TableColumn<Event, String> descCol = new TableColumn<>("Description");
        TableColumn<Event, String> dtCol = new TableColumn<>("Date/Time");
        TableColumn<Event, String> venueCol = new TableColumn<>("Venue");
        TableColumn<Event, String> seatsCol = new TableColumn<>("Seats");

        nameCol.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getName()));
        descCol.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getDescription()));
        dtCol.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getDateTime()));
        venueCol.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getVenue()));
        seatsCol.setCellValueFactory(d -> new SimpleStringProperty(
                d.getValue().getParticipants().size() + "/" + d.getValue().getTotalSeats()
        ));

        table.getColumns().addAll(nameCol, descCol, dtCol, venueCol, seatsCol);
        table.setItems(FXCollections.observableArrayList(manager.getEvents()));
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        return table;
    }

    private VBox createEventDetailBox() {
        VBox box = new VBox(10);
        box.setPadding(new Insets(15));
        box.setStyle("-fx-background-color: #ecf0f1; -fx-border-color: #bdc3c7; -fx-border-width: 1;");
        box.setPrefWidth(300);
        return box;
    }

    private void showEventDetails(Event event, VBox box, boolean allowRegister) {
        box.getChildren().clear();

        Label name = new Label("Name: " + event.getName());
        Label desc = new Label("Description: " + event.getDescription());
        Label dt = new Label("Date/Time: " + event.getDateTime());
        Label venue = new Label("Venue: " + event.getVenue());
        Label seats = new Label("Seats: " + event.getParticipants().size() + "/" + event.getTotalSeats());

        Button registerBtn = new Button("Register");
        registerBtn.setDisable(!allowRegister || event.getParticipants().contains(currentUser));
        registerBtn.setOnAction(e -> {
            if (manager.register(event.getId())) {
                showAlert(Alert.AlertType.INFORMATION, "Registered!");
                showUserAllEvents(); // refresh
            } else {
                showAlert(Alert.AlertType.WARNING, "Cannot register");
            }
        });

        box.getChildren().addAll(name, desc, dt, venue, seats);
        if (allowRegister) box.getChildren().add(registerBtn);
    }

    private void showAlert(Alert.AlertType type, String msg) {
        new Alert(type, msg).showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}