package view.menu;

import controller.UserController;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.User;
import view.Paths;

import java.io.File;
import java.net.URL;

public class SelectAvatarMenu extends Application {
    private static boolean isChangingMenuActive = false;
    @FXML
    private Button registerBottom;
    @FXML
    private ImageView avatar1;
    @FXML
    private ImageView avatar2;
    @FXML
    private ImageView avatar3;
    @FXML
    private ImageView avatar4;
    @FXML
    private ImageView avatar5;
    @FXML
    private ImageView avatar6;
    @FXML
    private ImageView avatar7;
    @FXML
    private ImageView avatar8;
    @FXML
    private Button chooseAvatarFromFileButton;
    @FXML
    private Button randomButton;
    @FXML
    private ImageView selectedAvatar;

    @Override
    public void start(Stage stage) throws Exception {
        URL selectAvatarMenuFXMLUrl = SelectAvatarMenu.class.getResource
                (Paths.SELECT_AVATAR_MENU_FXML_FILE.getPath());
        BorderPane borderPane = FXMLLoader.load(selectAvatarMenuFXMLUrl);
        Scene selectAvatarMenuScene = new Scene(borderPane);
        stage.setScene(selectAvatarMenuScene);
        stage.show();
    }

    public void back(MouseEvent mouseEvent) {
        if (isChangingMenuActive) {
            try {
                SelectAvatarMenu.setChangingMenuActive(false);
                new ProfileMenu().start(LoginMenu.stageOfProgram);
            } catch (Exception e) {
                System.out.println("an error occurred");
            }
        } else {
            try {
                UserController.setTemporaryAvatarAddress(null);
                new RegisterMenu().start(LoginMenu.stageOfProgram);
            } catch (Exception e) {
                System.out.println("an error occurred");
            }
        }
    }

    public void Register(MouseEvent mouseEvent) {
        if (UserController.getTemporaryAvatarAddress() == null) {
            Alert emptyAvatarAlert = new Alert(Alert.AlertType.ERROR);
            emptyAvatarAlert.setTitle("register failed!");
            emptyAvatarAlert.setHeaderText("register was not successful!");
            emptyAvatarAlert.setContentText("you didn't choose any avatar!");
            emptyAvatarAlert.showAndWait();
            return;
        }
        if (isChangingMenuActive) {
            UserController.changeAvatar();
            try {
                SelectAvatarMenu.setChangingMenuActive(true);
                new ProfileMenu().start(LoginMenu.stageOfProgram);
            } catch (Exception e) {
                System.out.println("an error occurred");
            }
        } else {
            UserController.createUser();
            try {
                new MainMenu().start(LoginMenu.stageOfProgram);
            } catch (Exception e) {
                System.out.println("an error occurred");
            }
        }
    }

    @FXML
    public void initialize() {
        if (isChangingMenuActive) {
            registerBottom.setText("Change");
        }
        randomButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                UserController.chooseRandomAvatar();
                selectedAvatar.setImage(new Image(UserController.getTemporaryAvatarAddress()));
            }
        });
        chooseAvatarFromFileButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                String beforeChangeAddress = UserController.getTemporaryAvatarAddress();
                new FileChoosingMenu().start(LoginMenu.stageOfProgram);
                if (!beforeChangeAddress.equals(UserController.getTemporaryAvatarAddress())) {
                    File fileOfAvatar = new File(UserController.getTemporaryAvatarAddress());
                    try {
                        selectedAvatar.setImage(new Image(fileOfAvatar.toURI().toURL().toExternalForm()));
                    } catch (Exception e) {
                        System.out.println("an error occurred");
                    }
                }
            }
        });
        avatar1.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                selectedAvatar.setImage(new Image(UserController.class.getResource
                        ("/images/Avatars/").toString()
                        + 1 + ".png"));
                UserController.choosingAvatarFromTemplates(1);
            }
        });
        avatar2.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                selectedAvatar.setImage(new Image(UserController.class.getResource
                        ("/images/Avatars/").toString()
                        + 2 + ".png"));
                UserController.choosingAvatarFromTemplates(2);
            }
        });
        avatar3.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                selectedAvatar.setImage(new Image(UserController.class.getResource
                        ("/images/Avatars/").toString()
                        + 3 + ".png"));
                UserController.choosingAvatarFromTemplates(3);
            }
        });
        avatar4.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                selectedAvatar.setImage(new Image(UserController.class.getResource
                        ("/images/Avatars/").toString()
                        + 4 + ".png"));
                UserController.choosingAvatarFromTemplates(4);
            }
        });
        avatar5.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                selectedAvatar.setImage(new Image(UserController.class.getResource
                        ("/images/Avatars/").toString()
                        + 5 + ".png"));
                UserController.choosingAvatarFromTemplates(5);
            }
        });
        avatar6.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                selectedAvatar.setImage(new Image(UserController.class.getResource
                        ("/images/Avatars/").toString()
                        + 6 + ".png"));
                UserController.choosingAvatarFromTemplates(6);
            }
        });
        avatar7.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                selectedAvatar.setImage(new Image(UserController.class.getResource
                        ("/images/Avatars/").toString()
                        + 7 + ".png"));
                UserController.choosingAvatarFromTemplates(7);
            }
        });
        avatar8.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                selectedAvatar.setImage(new Image(UserController.class.getResource
                        ("/images/Avatars/").toString()
                        + 8 + ".png"));
                UserController.choosingAvatarFromTemplates(8);
            }
        });

    }

    public static boolean isChangingMenuActive() {
        return isChangingMenuActive;
    }

    public static void setChangingMenuActive(boolean changingMenuActive) {
        isChangingMenuActive = changingMenuActive;
    }
}
