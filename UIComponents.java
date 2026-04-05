package EventRegistration;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import java.util.List;

public class UIComponents {

    public static Label darkLabel(String text) {
        Label l = new Label(text);
        l.setStyle("-fx-font-size:11px;-fx-font-weight:bold;-fx-text-fill:#667788;");
        return l;
    }

    public static Label darkHeader(String text) {
        Label l = new Label(text);
        l.setStyle("-fx-font-size:24px;-fx-font-weight:bold;-fx-text-fill:#f0ece0;");
        return l;
    }

    public static Label featureLine(String text) {
        Label l = new Label(text);
        l.setStyle("-fx-font-size:13px;-fx-text-fill:#445566;");
        return l;
    }

    public static Label infoRow(String icon, String label, String value) {
        Label l = new Label(icon + "  " + label + ":  " + value);
        l.setStyle("-fx-font-size:13px;-fx-text-fill:#aabbcc;");
        l.setWrapText(true);
        return l;
    }

    public static Label placeholder(String text) {
        Label l = new Label(text);
        l.setStyle("-fx-text-fill:#445566;-fx-font-size:13px;");
        return l;
    }

    // ── Inputs ────────────────────────────────────────────────────────────────

    private static final String INPUT_STYLE =
        "-fx-background-radius:6;-fx-border-radius:6;-fx-border-color:#2a2a3e;" +
        "-fx-border-width:1;-fx-padding:9;-fx-font-size:13px;" +
        "-fx-background-color:#0d0d18;-fx-text-fill:#c8c0b0;-fx-control-inner-background:#0d0d18;";

    public static TextField darkTextField(String prompt) {
        TextField tf = new TextField();
        tf.setPromptText(prompt);
        tf.setStyle(INPUT_STYLE);
        return tf;
    }

    public static PasswordField darkPasswordField(String prompt) {
        PasswordField pf = new PasswordField();
        pf.setPromptText(prompt);
        pf.setStyle(INPUT_STYLE);
        return pf;
    }

    // ── Buttons ───────────────────────────────────────────────────────────────

    public static Button goldButton(String text) {
        final String n = "-fx-background-color:#e8c97e;-fx-text-fill:#0a0a0f;-fx-font-size:13px;-fx-font-weight:bold;-fx-background-radius:7;-fx-padding:10 20;";
        final String h = "-fx-background-color:#d4b060;-fx-text-fill:#0a0a0f;-fx-font-size:13px;-fx-font-weight:bold;-fx-background-radius:7;-fx-padding:10 20;";
        Button b = new Button(text); b.setStyle(n);
        b.setOnMouseEntered(e -> b.setStyle(h));
        b.setOnMouseExited(e  -> b.setStyle(n));
        return b;
    }

    public static Button ghostButton(String text) {
        final String n = "-fx-background-color:transparent;-fx-text-fill:#8899aa;-fx-font-size:13px;-fx-border-color:#2a2a3e;-fx-border-width:1;-fx-background-radius:7;-fx-border-radius:7;-fx-padding:9 18;";
        final String h = "-fx-background-color:#1e1e30;-fx-text-fill:#e8c97e;-fx-font-size:13px;-fx-border-color:#e8c97e;-fx-border-width:1;-fx-background-radius:7;-fx-border-radius:7;-fx-padding:9 18;";
        Button b = new Button(text); b.setStyle(n);
        b.setOnMouseEntered(e -> b.setStyle(h));
        b.setOnMouseExited(e  -> b.setStyle(n));
        return b;
    }

    public static Button dangerButton(String text) {
        final String n = "-fx-background-color:#3a1515;-fx-text-fill:#e05c5c;-fx-font-size:13px;-fx-font-weight:bold;-fx-background-radius:7;-fx-padding:10 20;-fx-border-color:#5a2020;-fx-border-width:1;-fx-border-radius:7;";
        final String h = "-fx-background-color:#e05c5c;-fx-text-fill:white;-fx-font-size:13px;-fx-font-weight:bold;-fx-background-radius:7;-fx-padding:10 20;";
        Button b = new Button(text); b.setStyle(n);
        b.setOnMouseEntered(e -> b.setStyle(h));
        b.setOnMouseExited(e  -> b.setStyle(n));
        return b;
    }

    // ── Layout ────────────────────────────────────────────────────────────────

    public static Separator divider() {
        Separator s = new Separator();
        s.setStyle("-fx-background-color:#2a2a3e;");
        s.setPadding(new Insets(4, 0, 8, 0));
        return s;
    }

    public static VBox statCard(String title, String value, String color) {
        VBox card = new VBox(4);
        card.setPadding(new Insets(18, 28, 18, 28));
        card.setStyle("-fx-background-color:#1a1a2e;-fx-background-radius:10;");
        card.setMinWidth(140);
        DropShadow sh = new DropShadow();
        sh.setColor(Color.rgb(0, 0, 0, 0.4)); sh.setRadius(12);
        card.setEffect(sh);
        Label val = new Label(value);
        val.setStyle("-fx-font-size:30px;-fx-font-weight:bold;-fx-text-fill:" + color + ";");
        Label lbl = new Label(title);
        lbl.setStyle("-fx-font-size:11px;-fx-text-fill:#445566;");
        card.getChildren().addAll(val, lbl);
        return card;
    }

    public static ScrollPane scrollWrap(VBox content) {
        ScrollPane sp = new ScrollPane(content);
        sp.setFitToWidth(true);
        sp.setStyle("-fx-background-color:#0f0f1a;-fx-background:#0f0f1a;");
        return sp;
    }

    public static SplitPane splitPane(javafx.scene.Node a, javafx.scene.Node b) {
        SplitPane sp = new SplitPane(a, b);
        sp.setDividerPositions(0.60);
        sp.setStyle("-fx-background-color:transparent;");
        return sp;
    }

    // ── Sidebar ───────────────────────────────────────────────────────────────

    public static VBox createSidebar(String[] items, Runnable[] actions, String active, String roleLabel) {
        VBox sidebar = new VBox(4);
        sidebar.setStyle("-fx-background-color:#0d0d18;");
        sidebar.setPrefWidth(210);

        VBox logoBox = new VBox(5);
        logoBox.setPadding(new Insets(24, 18, 22, 18));
        logoBox.setStyle("-fx-background-color:#080810;");
        Label logo = new Label("EventHub");
        logo.setStyle("-fx-font-size:22px;-fx-font-weight:bold;-fx-text-fill:#e8c97e;");
        Label role = new Label(roleLabel);
        role.setStyle("-fx-font-size:11px;-fx-text-fill:#556677;");
        logoBox.getChildren().addAll(logo, role);

        Label nav = new Label("NAVIGATION");
        nav.setStyle("-fx-font-size:9px;-fx-text-fill:#334455;-fx-padding:14 18 4 18;-fx-font-weight:bold;");
        sidebar.getChildren().addAll(logoBox, nav);

        for (int i = 0; i < items.length; i++) {
            boolean isLogout = items[i].equals("Logout");
            boolean isAct    = items[i].equals(active);
            String clr  = isLogout ? "#e05c5c" : "#8899aa";
            String hClr = isLogout ? "#ff7777" : "#c8b870";
            String normal  = "-fx-background-color:transparent;-fx-text-fill:" + clr + ";-fx-font-size:13px;-fx-padding:11 18;-fx-background-radius:0;-fx-alignment:CENTER-LEFT;";
            String hover   = "-fx-background-color:#1a1a28;-fx-text-fill:" + hClr + ";-fx-font-size:13px;-fx-padding:11 18;-fx-background-radius:0;-fx-alignment:CENTER-LEFT;";
            String activeS = "-fx-background-color:#1e1e30;-fx-text-fill:#e8c97e;-fx-font-size:13px;-fx-padding:11 18;-fx-background-radius:0;-fx-alignment:CENTER-LEFT;-fx-border-color:transparent transparent transparent #e8c97e;-fx-border-width:0 0 0 3;";
            Button btn = new Button(items[i]);
            btn.setMaxWidth(Double.MAX_VALUE);
            btn.setStyle(isAct ? activeS : normal);
            final int idx = i; final String label = items[i];
            btn.setOnAction(e -> actions[idx].run());
            btn.setOnMouseEntered(e -> { if (!label.equals(active)) btn.setStyle(hover); });
            btn.setOnMouseExited(e  -> { if (!label.equals(active)) btn.setStyle(normal); });
            sidebar.getChildren().add(btn);
        }
        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);
        sidebar.getChildren().add(spacer);
        return sidebar;
    }

    // ── Event Table ───────────────────────────────────────────────────────────

    public static TableView<Event> createEventTable(List<Event> events) {
        TableView<Event> table = new TableView<>();
        table.setStyle("-fx-background-color:#1a1a2e;-fx-background-radius:8;");

        TableColumn<Event, String> nameCol   = new TableColumn<>("Event Name");
        TableColumn<Event, String> dtCol     = new TableColumn<>("Date / Time");
        TableColumn<Event, String> venueCol  = new TableColumn<>("Venue");
        TableColumn<Event, String> seatsCol  = new TableColumn<>("Seats");
        TableColumn<Event, String> statusCol = new TableColumn<>("Status");

        nameCol.setCellValueFactory(d  -> new SimpleStringProperty(d.getValue().getName()));
        dtCol.setCellValueFactory(d    -> new SimpleStringProperty(d.getValue().getDateTime()));
        venueCol.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getVenue()));
        seatsCol.setCellValueFactory(d -> new SimpleStringProperty(
            d.getValue().getParticipants().size() + " / " + d.getValue().getTotalSeats()));
        statusCol.setCellValueFactory(d -> new SimpleStringProperty(
            d.getValue().hasAvailableSeats() ? "Open" : "Full"));

        statusCol.setCellFactory(col -> new TableCell<Event, String>() {
            @Override protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) { setText(null); setStyle(""); return; }
                setText(item);
                setStyle(item.equals("Open")
                    ? "-fx-text-fill:#7ee8a2;-fx-font-weight:bold;"
                    : "-fx-text-fill:#e05c5c;-fx-font-weight:bold;");
            }
        });

        table.getColumns().addAll(nameCol, dtCol, venueCol, seatsCol, statusCol);
        table.setItems(FXCollections.observableArrayList(events));
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setPlaceholder(placeholder("No events found."));
        return table;
    }

    // ── Event Form Card ───────────────────────────────────────────────────────

    public static VBox buildEventFormCard(Event prefill) {
        VBox card = new VBox(12);
        card.setMaxWidth(520);
        card.setPadding(new Insets(28));
        card.setStyle("-fx-background-color:#1a1a2e;-fx-background-radius:12;");
        DropShadow sh = new DropShadow();
        sh.setColor(Color.rgb(0, 0, 0, 0.5)); sh.setRadius(20);
        card.setEffect(sh);

        TextField nameF = darkTextField("Event Name");                         nameF.setId("nameField");
        TextField dtF   = darkTextField("Date & Time (e.g. 2025-12-10 18:00)"); dtF.setId("dtField");
        TextField venF  = darkTextField("Venue");                               venF.setId("venueField");
        TextField seaF  = darkTextField("Total Seats");                         seaF.setId("seatsField");
        TextArea  desF  = new TextArea();
        desF.setId("descField"); desF.setPromptText("Description (optional)"); desF.setPrefRowCount(3);
        desF.setStyle("-fx-background-radius:6;-fx-border-radius:6;-fx-border-color:#2a2a3e;-fx-border-width:1;" +
                      "-fx-font-size:13px;-fx-background-color:#0d0d18;-fx-text-fill:#c8c0b0;-fx-control-inner-background:#0d0d18;");
        Label errLbl = new Label(""); errLbl.setId("errorLabel");
        errLbl.setStyle("-fx-text-fill:#e05c5c;-fx-font-size:12px;");

        if (prefill != null) {
            nameF.setText(prefill.getName());       desF.setText(prefill.getDescription());
            dtF.setText(prefill.getDateTime());     venF.setText(prefill.getVenue());
            seaF.setText(String.valueOf(prefill.getTotalSeats()));
        }

        card.getChildren().addAll(
            darkLabel("Event Name"),  nameF,
            darkLabel("Description"), desF,
            darkLabel("Date & Time"), dtF,
            darkLabel("Venue"),       venF,
            darkLabel("Total Seats"), seaF,
            errLbl);
        return card;
    }

    // ── Event Detail Box ──────────────────────────────────────────────────────

    public static VBox eventDetailBox() {
        VBox box = new VBox(12);
        box.setPadding(new Insets(22));
        box.setStyle("-fx-background-color:#1a1a2e;");
        Label ph = new Label("Select an event to view details.");
        ph.setStyle("-fx-text-fill:#445566;-fx-font-size:13px;");
        box.getChildren().add(ph);
        return box;
    }

    // ── Alert ─────────────────────────────────────────────────────────────────

    public static void showAlert(Alert.AlertType type, String msg) {
        Alert a = new Alert(type, msg);
        a.setHeaderText(null);
        a.showAndWait();
    }
}