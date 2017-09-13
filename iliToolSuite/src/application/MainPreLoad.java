package application;

import com.sun.javafx.application.LauncherImpl;

import application.exception.IliSuiteSecurityManager;

public class MainPreLoad {
	public static void main(String[] args) {
		LauncherImpl.launchApplication(Main.class, SplashScreenLoader.class, args);
	   }
}
