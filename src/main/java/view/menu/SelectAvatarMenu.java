package view.menu;

import controller.App;
import controller.GameViewController;
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
    private ImageView selectedAvatar;
    @FXML
    private BorderPane avatarPane;

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
        if (isChangingAvatarMenuActive) {
            ImageView imageView = new ImageView(new Image(App.getCurrentUser().getAvatarFilePath()));
            imageView.setTranslateY(675);
            imageView.setTranslateX(175);
            imageView.setFitHeight(100);
            imageView.setFitWidth(100);
            borderPane.getChildren().add(imageView);
        }
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
            GameViewController.alertShowing(Alert.AlertType.ERROR , "Register failed!" ,
                    "Register was not successful!" , "You didn't choose any avatar!");
            return;
        }
        if (isChangingAvatarMenuActive) {
            UserController.changeAvatar();
            GameViewController.alertShowing(Alert.AlertType.INFORMATION ,
                    "Change information was successful!",
                    "Avatar changed successfully!" , "Avatar was changed!");
            try {
                SelectAvatarMenu.setIsChangingAvatarMenuActive(true);
                new ProfileMenu().start(LoginMenu.stageOfProgram);
            } catch (Exception e) {
                System.out.println("an error occurred");
            }
        } else {
            UserController.createUser();
            GameViewController.alertShowing(Alert.AlertType.INFORMATION , "User creation was successful!",
                    "User was created successfully!" , " User was created!");
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
                if (addressBeforeChangeAddress == null ||
                        !addressBeforeChangeAddress.equals(UserController.getTemporaryAvatarAddress())) {
                    try {
                        selectedAvatar.setImage(new Image(UserController.getTemporaryAvatarAddress()));
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
    private void chooseAvatarFromTemplatesEventHandling(ImageView avatar , int number) {
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
