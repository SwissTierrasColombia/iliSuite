package ai.iliSuite.view.util.navigation;

import java.util.Stack;

import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

public class NavigationUtil {
	
	private static Stack<VisualResource> stepStack;
	private static VisualResource mainScreen;
	private static VisualResource currentScreen;
	
	
	public static void setNextScreen(VisualResource component){
		if(stepStack==null)
			stepStack = new Stack<VisualResource>();
 		Parent main = NavigationUtil.mainScreen.getComponent();
		BorderPane borderPane = (BorderPane) main.getChildrenUnmodifiable().get(0);
		borderPane.setCenter(component.getComponent());

		stepStack.push(component);
		NavigationUtil.currentScreen = component;
					
	}
	
	public static void setPreviousScreen(){
		stepStack.pop();
		VisualResource previous = stepStack.lastElement();
		
		Parent main = NavigationUtil.mainScreen.getComponent();
		BorderPane borderPane = (BorderPane) main.getChildrenUnmodifiable().get(0);
		borderPane.setCenter(previous.getComponent());
		
		NavigationUtil.currentScreen = previous;
	}
	
	public static void setFirstScreen(){
		VisualResource first = stepStack.firstElement();
		stepStack.clear();
		stepStack.push(first);
		
		Parent main = NavigationUtil.mainScreen.getComponent();
		BorderPane borderPane = (BorderPane) main.getChildrenUnmodifiable().get(0);
		borderPane.setCenter(first.getComponent());
		
		NavigationUtil.currentScreen = first;
		
	}

	public static VisualResource getMainScreen() {
		return mainScreen;
	}

	public static void setMainScreen(VisualResource mainScreen) {
		NavigationUtil.mainScreen = mainScreen;
	}
	
	public static VisualResource getCurrentScreen() {
		return currentScreen;
	}


	public static Stack<VisualResource> getStepStack() {
		return stepStack;
	}

	public static void setStepStack(Stack<VisualResource> stepStack) {
		NavigationUtil.stepStack = stepStack;
	}
	
	public static void clearStepStack(){
		if(stepStack!=null)
			stepStack.removeAllElements();
	}

}
