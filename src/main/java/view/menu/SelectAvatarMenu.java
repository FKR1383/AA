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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import view.Paths;

import java.io.File;
import java.net.URL;

public class SelectAvatarMenu extends Application {
    private static boolean isChangingAvatarMenuActive = false;
    // isChangingAvatarMenu is for selecting avatar in profile menu after creating account
    @FXML
    private Button registerButton;
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
    private Button randomAvatarButton;
    @FXML
    private static ImageView selectedAvatar;

    public static boolean isIsChangingAvatarMenuActive() {
        return isChangingAvatarMenuActive;
    }

    public static void setIsChangingAvatarMenuActive(boolean isChangingAvatarMenuActive) {
        SelectAvatarMenu.isChangingAvatarMenuActive = isChangingAvatarMenuActive;
    }

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
        if (isChangingAvatarMenuActive) {
            try {
                SelectAvatarMenu.setIsChangingAvatarMenuActive(false);
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
            emptyAvatarAlert.setTitle("Register failed!");
            emptyAvatarAlert.setHeaderText("Register was not successful!");
            emptyAvatarAlert.setContentText("You didn't choose any avatar!");
            emptyAvatarAlert.showAndWait();
            return;
        }
        if (isChangingAvatarMenuActive) {
            UserController.changeAvatar();
            try {
                SelectAvatarMenu.setIsChangingAvatarMenuActive(true);
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
        if (isChangingAvatarMenuActive) {
            registerButton.setText("Change");
            // changing text of Register button to "Change" for changing Avatar menu
        }
        randomAvatarButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                UserController.chooseRandomAvatar();
                selectedAvatar.setImage(new Image(UserController.getTemporaryAvatarAddress()));
            }
        });
        chooseAvatarFromFileButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                String addressBeforeChangeAddress = UserController.getTemporaryAvatarAddress();
                new FileChoosingMenu().start(LoginMenu.stageOfProgram);
                if (!addressBeforeChangeAddress.equals(UserController.getTemporaryAvatarAddress())) {
                    File fileOfAvatar = new File(UserController.getTemporaryAvatarAddress());
                    try {
                        selectedAvatar.setImage(new Image(fileOfAvatar.toURI().toURL().toExternalForm()));
                    } catch (Exception e) {
                        System.out.println("an error occurred");
                    }
                }
            }
        });
        chooseAvatarFromTemplatesEventHandling(avatar1 , 1);
        chooseAvatarFromTemplatesEventHandling(avatar2 , 2);
        chooseAvatarFromTemplatesEventHandling(avatar3 , 3);
        chooseAvatarFromTemplatesEventHandling(avatar4 , 4);
        chooseAvatarFromTemplatesEventHandling(avatar5 , 5);
        chooseAvatarFromTemplatesEventHandling(avatar6 , 6);
        chooseAvatarFromTemplatesEventHandling(avatar7 , 7);
        chooseAvatarFromTemplatesEventHandling(avatar8 , 8);
    }
    private static void chooseAvatarFromTemplatesEventHandling(ImageView avatar , int number) {
        avatar.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                selectedAvatar.setImage(new Image(UserController.class.getResource
                        ("/images/Avatars/").toString()
                        + number + ".png"));
                UserController.choosingAvatarFromTemplates(number);
            }
        });
    }
}
