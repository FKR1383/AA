package controller;

import model.User;
import view.Paths;

import java.io.File;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserController {
    private static String temporaryUsername;
    private static String temporaryPassword;
    private static String temporaryAvatarAddress;

    public static String getTemporaryUsername() {
        return temporaryUsername;
    }

    public static void setTemporaryUsername(String tu) {
        temporaryUsername = tu;
    }

    public static String getTemporaryPassword() {
        return temporaryPassword;
    }

    public static void setTemporaryPassword(String tp) {
        temporaryPassword = tp;
    }

    public static boolean isUsernameExist(String username) {
        for (User user : App.getAllUsers()) {
            if (user.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    public static User getUserByUsername(String username) {
        for (User user : App.getAllUsers()) {
            if (user.getUsername().equals(username))
                return user;
        }
        return null;
    }

    public static boolean login(String username , String password){
        if (!isUsernameExist(username)) {
            return false;
        }
        User user = getUserByUsername(username);
        if (!user.isPasswordCorrect(password)) {
            return false;
        }
        App.setCurrentUser(user);
        DBController.saveCurrentUser();
        return true;
    }

    public static void createUser() {
        User user = new User(getTemporaryUsername() , getTemporaryPassword() , getTemporaryAvatarAddress());
        App.setCurrentUser(user);
        DBController.saveUsers();
        DBController.saveCurrentUser();
    }

    public static String getTemporaryAvatarAddress() {
        return temporaryAvatarAddress;
    }

    public static void setTemporaryAvatarAddress(String temporaryAvatarAddress) {
        UserController.temporaryAvatarAddress = temporaryAvatarAddress;
    }


    public static void chooseRandomAvatar() {
        Random random = new Random();
        int randomAvatarNumber = ((Math.abs(random.nextInt())) % 8) + 1;
        if (UserController.getTemporaryUsername() != null) {
            Matcher numberMatcher = Pattern.compile("(?<number>[\\d])\\.png").matcher(UserController.getTemporaryUsername());
            if (numberMatcher.find()) {
                int number = Integer.parseInt(numberMatcher.group("number"));
                while (number == randomAvatarNumber) {
                    randomAvatarNumber = ((Math.abs(random.nextInt())) % 8) + 1;
                }
            }
        }
        String pathOfAvatar = UserController.class.getResource("/images/Avatars/").toString()
                + randomAvatarNumber + ".png";
        UserController.setTemporaryAvatarAddress(pathOfAvatar);
    }

    public static void choosingAvatarFromTemplates(int number) {
        String pathOfAvatar = UserController.class.getResource("/images/Avatars/").toString()
                + number + ".png";
        UserController.setTemporaryAvatarAddress(pathOfAvatar);
    }

    public static void logout() {
        App.setCurrentUser(null);
        FileController.writeTextToFile("" , Paths.CURRENT_USER_JSON_FILE);
    }

    public static void deleteUser() {
        App.getAllUsers().remove(App.getCurrentUser());
        App.setCurrentUser(null);
        DBController.saveCurrentUser();
        DBController.saveUsers();
    }

    public static void changeUsername(String newUsername) {
        User user = App.getCurrentUser();
        user.setUsername(newUsername);
        DBController.saveUsers();
        DBController.saveCurrentUser();
    }

    public static void changePassword(String newPassword) {
        User user = App.getCurrentUser();
        user.setPassword(newPassword);
        DBController.saveUsers();
        DBController.saveCurrentUser();
    }

    public static void changeAvatar() {
        User user = App.getCurrentUser();
        user.setAvatarFilePath(UserController.getTemporaryAvatarAddress());
        DBController.saveUsers();
        DBController.saveCurrentUser();
    }
}
