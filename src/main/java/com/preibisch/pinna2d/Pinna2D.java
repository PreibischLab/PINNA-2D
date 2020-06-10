package com.preibisch.pinna2d;

import com.preibisch.pinna2d.app.MyApp;
import ij.plugin.PlugIn;
import javafx.stage.Stage;
import org.scijava.command.Command;
import org.scijava.plugin.Plugin;

@Plugin(type = Command.class, menuPath = "Plugins>PINNA Annotation>Pinna2D")
public class Pinna2D implements PlugIn, Command {
    @Override
    public void run() {
        run("Pinna2D");
    }

    @Override
    public void run(String arg) {
        com.sun.javafx.application.PlatformImpl.startup(() -> {
            MyApp app = new MyApp();
            app.start(new Stage());
        });
    }


    public static void main(String[] args) {
        new Pinna2D().run();
    }
}
