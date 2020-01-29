package ai.ilisuite.application;

import com.sun.javafx.application.LauncherImpl;

public class MainPreLoad {
	public static void main(String[] args) {
		LauncherImpl.launchApplication(Main.class, SplashScreenLoader.class, args);
	   }
}
