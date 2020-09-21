package ai.ilisuite.view;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import ai.ilisuite.base.log.IliMessageType;
import ai.ilisuite.controller.ExecutionController;
import ai.ilisuite.view.util.navigation.EnumPaths;
import ai.ilisuite.view.util.navigation.ResourceUtil;
import ai.ilisuite.view.wizard.StepArgs;
import ai.ilisuite.view.wizard.StepViewController;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class FinishActionView  extends StepViewController  implements Initializable {

	private ResourceBundle applicationBundle;

	@FXML
	private VBox verticalWrapper;

	@FXML
	private ProgressIndicator progressIndicator;
		
	@FXML
	private TextArea registerReport;
	
	@FXML
	private TreeView<String> processTree;
	
	private Parent viewRootNode;

	private ExecutionController controller;
	
	private TreeItem<String> errors;
	private TreeItem<String> warnings;
	private TreeItem<String> others;
	
	private TreeItem<String> projectRoot;
	private TreeItem<String> issues;
	private TreeItem<String> command;
	
	private TreeItem<String> registerReportNode;
	
	private String commandText;
	private String summary;
	
	private String strProcess;
	private String strOthers;
	private String strWarnings;
	private String strErrors;
	private String strIssues;
	private String strCommand;
	private String strResult;
	private String strSuccessful;
	private String strFailed;
	
	@FXML
	private AnchorPane infoPanel;
	
	public FinishActionView (ExecutionController controller) throws IOException {
		this.controller = controller;
		// TODO Posible carga de componentes antes de ser necesario
		viewRootNode = ResourceUtil.loadResource(EnumPaths.FINISH_ACTION, EnumPaths.RESOURCE_BUNDLE, this);
		commandText = "";
		summary = "";
	}
		
	public void reset() {
		projectRoot.setValue(strProcess);
		
		ObservableList<TreeItem<String>> childrenIssues = issues.getChildren();
		
		childrenIssues.remove(errors);
		childrenIssues.remove(warnings);
		childrenIssues.remove(others);
		
		errors = new TreeItem<>(strErrors);
		warnings = new TreeItem<>(strWarnings);
		others = new TreeItem<>(strOthers);
		
		childrenIssues.add(errors);
		childrenIssues.add(warnings);
		childrenIssues.add(others);
		
		commandText = "";
		summary = "";
		registerReport.setText(summary);
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		applicationBundle = arg1;
		initText();
		
		issues = new TreeItem<>(strIssues);
		issues.setExpanded(true);
		command = new TreeItem<>(strCommand);
		registerReportNode = new TreeItem<>(strResult);
		errors = new TreeItem<>(strErrors);
		warnings = new TreeItem<>(strWarnings);
		others = new TreeItem<>(strOthers);
		
		ObservableList<TreeItem<String>> children = issues.getChildren();
		
		children.add(warnings);
		children.add(errors);
		children.add(others);
		
		projectRoot = new TreeItem<>(strProcess);
		projectRoot.setExpanded(true);
		
		projectRoot.getChildren().add(command);
		projectRoot.getChildren().add(issues);
		projectRoot.getChildren().add(registerReportNode);
		
		processTree.getSelectionModel()
        .selectedItemProperty()
        .addListener((observable, oldValue, newValue) -> {
        	boolean isInfoPanelVisible = false;
        	
        	String registerReportText = "";
        	if(newValue == command) {
        		registerReportText = commandText;
        		isInfoPanelVisible = true;
        	} else if (newValue == registerReportNode) {
        		registerReportText = summary;
        		isInfoPanelVisible = true;
        	} else {
        		TreeItem<String> parent = newValue.getParent();
        		if(parent != null) {
        			TreeItem<String> grantParent = parent.getParent();
        			
        			if(grantParent != null && grantParent == issues) {
        				registerReportText = newValue.getValue();
        				isInfoPanelVisible = true;
        			}
        		}
        	}
        	infoPanel.setVisible(isInfoPanelVisible);
        	registerReport.setText(registerReportText);
        });
		processTree.setRoot(projectRoot);
	}
	
	private void initText() {
		strProcess = applicationBundle.getString("finish.process");
		strOthers = applicationBundle.getString("finish.others");
		strWarnings = applicationBundle.getString("finish.warnings");
		strErrors = applicationBundle.getString("finish.errors");
		strIssues = applicationBundle.getString("finish.issues");
		strCommand = applicationBundle.getString("finish.command");
		strResult = applicationBundle.getString("finish.result");
		strSuccessful = applicationBundle.getString("finish.successful");
		strFailed = applicationBundle.getString("finish.failed");
	}
	
	@Override
	public void loadedPage(StepArgs args) {
		super.loadedPage(args);
		commandText = controller.getTextParams();
	}

	@Override
	public Parent getGraphicComponent() {
		return viewRootNode;
	}
	
	@Override
	public void goBack(StepArgs args) {
		controller.cancelExecution();
	}

	public void enableProgressIndicator() {
		progressIndicator.setVisible(true);
	}
	
	public void disableProgressIndicator() {
		progressIndicator.setVisible(false);
	}
	
	public void addIssue(String issue, IliMessageType messageType) {
		Platform.runLater(() -> {
			
			TreeItem<String> node = null;
			String nodeTitle = null;
			
			switch(messageType) {
			case Error:
				node = errors;
				nodeTitle = strErrors;
				break;
			case Warning:
				node = warnings;
				nodeTitle = strWarnings;
				break;
			case Other:
				node = others;
				nodeTitle = strOthers;
				break;
			default:
			}

			if(node != null) {
				node.getChildren().add(new TreeItem<String>(issue));
				node.setValue(nodeTitle + String.format(" %d", node.getChildren().size()));
			}
		});
	}
	
	public void setProccessResult(boolean successful) {
		if(successful) {
			projectRoot.setValue(strProcess + " (" + strSuccessful + ")");
		} else {
			projectRoot.setValue(strProcess + " (" + strFailed + ")");
		}
	}
	
	public void setSummary(String summary) {
		this.summary = summary;
		
		processTree.getSelectionModel().select(registerReportNode);
	}
}
