package view;

public enum Paths {LOGIN_MENU_FXML_FILE("/FXML/loginMenu.fxml"),
    MAIN_MENU_FXML_FILE("/FXML/mainMenu.fxml"),
    REGISTER_MENU_FXML_FILE("/FXML/registerMenu.fxml"),
    CHANGE_AVATAR_MENU_FXML_FILE("/FXML/changeAvatarMenu.fxml"),
    SETTINGS_MENU_FXML_FILE("/FXML/settingsMenu.fxml"),
    PROFILE_MENU_FXML_FILE("/FXML/profileMenu.fxml"),
    SCOREBOARD_MENU_FXML_FILE("/FXML/scoreboardMenu.fxml"),
    GAME_MENU_FXML_FILE("/FXML/gameMenu.fxml"),
    USERS_JSON_FILE("src/main/resources/JSON/users.json"),
    CURRENT_USER_JSON_FILE("src/main/resources/JSON/currentuser.json"),
    CUSTOM_AVATARS_PATH("src/main/resources/images/customAvatars/"),
    AVATARS_PATH("src/main/resources/images/Avatars/"),
    SELECT_AVATAR_MENU_FXML_FILE("/FXML/selectAvatarMenu.fxml"),
    WALLPAPER_IMAGE_PATH("images/wallpaper.jpg");

    private Paths(String path) {
        this.path = path;
    }

    private String path;

    public String getPath() {
        return path;
    }
}
