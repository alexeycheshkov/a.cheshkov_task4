package utils;

import aquality.selenium.core.logging.Logger;
import main.Config;
import org.sikuli.script.ImagePath;
import org.sikuli.script.Match;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;

public class SikuliUtils {
    private static Screen screen;
    private static final double PERCENT = 0.6;

    public static boolean isExistPhoto(String photoName){
        Logger.getInstance().debug("Finding photo on the screen");
        screen = new Screen();
        ImagePath.add(Config.get("upload_photo_folder"));
        Match exists = screen.exists(new Pattern(photoName).similar(PERCENT));
        if (exists!=null){
            FileUtils.saveBufferedImageToFile(exists.getImage().get(),"png");
            return true;
        }
        Logger.getInstance().debug("Photo wasn't found on the screen");
        return false;
    }
}
