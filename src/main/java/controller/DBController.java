package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import model.User;
import view.Paths;

import java.io.File;
import java.util.ArrayList;

public class DBController {
    public static void loadUsers() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        App.setAllUsers(gson.fromJson(FileController.getTextOfFile(Paths.USERS_JSON_FILE) ,
                new TypeToken<ArrayList<User>>(){}));
        if (App.getAllUsers() == null) {
            App.setAllUsers(new ArrayList<>());
        }
    }
    public static void saveUsers() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        FileController.writeTextToFile(gson.toJson(App.getAllUsers()) , Paths.USERS_JSON_FILE);
    }
    public static void loadCurrentUser() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        App.setCurrentUser(gson.fromJson(FileController.getTextOfFile(Paths.CURRENT_USER_JSON_FILE) ,
                User.class));
    }
    public static void saveCurrentUser() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        FileController.writeTextToFile(gson.toJson(App.getCurrentUser()) , Paths.CURRENT_USER_JSON_FILE);
    }
}
