package com.erikmafo.btviewer;

import com.erikmafo.btviewer.services.ServicesModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

public class FXMLLoaderUtil {
    public static void loadFxml(String fxmlFile, Object controller) {
        var loader = new FXMLLoader(controller.getClass().getResource(fxmlFile));
        loader.setRoot(controller);
        loader.setController(controller);
        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException("Could not load fxml file: " + fxmlFile, e);
        }
    }
}
