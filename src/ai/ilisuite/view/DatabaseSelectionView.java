package ai.ilisuite.view;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import ai.ilisuite.controller.DbSelectorController;
import ai.ilisuite.impl.DbDescription;
import ai.ilisuite.view.util.navigation.EnumPaths;
import ai.ilisuite.view.util.navigation.ResourceUtil;
import ai.ilisuite.view.wizard.StepArgs;
import ai.ilisuite.view.wizard.StepViewController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.scene.Parent;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Text;

public class DatabaseSelectionView  extends StepViewController implements Initializable {
	@FXML
	private Text txtDbDescription;
	
	@FXML
	private AnchorPane buttonsGridContainer;

	private Parent viewRootNode;
	
	private ToggleGroup databaseButtonGroup;
	
	private DbSelectorController controller;
	private Map<String, DbDescription> textDatabases;
	private String selectedDatabase;
	
	public DatabaseSelectionView(DbSelectorController controller, Map<String, DbDescription> textDatabases) throws IOException {
		this.controller = controller;
		this.textDatabases = textDatabases;

		viewRootNode = ResourceUtil.loadResource(EnumPaths.ILI2DB_COMMON_DATABASE_SELECTION, EnumPaths.RESOURCE_BUNDLE, this);
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initDatabaseButtons();
		addInitListeners();
	}
	
	private void initDatabaseButtons() {
		databaseButtonGroup = new ToggleGroup();
		
		GridPane buttonsGrid = new GridPane();
		buttonsGrid.prefWidthProperty().bind(buttonsGridContainer.widthProperty());
		buttonsGrid.prefHeightProperty().bind(buttonsGridContainer.heightProperty());
		
		int elements = textDatabases.size();
		int columns = 2;
		int rows= (elements/columns)+ (elements%2 > 0 ? 1 : 0);
		float percentHeight = 100f/rows;
		float percentWidth = 100f/columns;		
		ColumnConstraints cc = new ColumnConstraints();
		cc.setPercentWidth(percentWidth);
		
		List<ColumnConstraints> ccList = new ArrayList<>();
		for(int i = 0; i<columns; i++){
			ccList.add(cc);
		}
		buttonsGrid.getColumnConstraints().addAll(ccList);
		
		RowConstraints rc = new RowConstraints();
		rc.setPercentHeight(percentHeight);
		
		List<RowConstraints> rcList = new ArrayList<>();
		for(int i = 0; i<rows; i++){
			rcList.add(rc);
		}
		buttonsGrid.getRowConstraints().addAll(rcList);
		
		int rowIndex = 0, i = 0;
		
		for (DbDescription item:textDatabases.values()) {
			rowIndex = i/columns;
			ToggleButton btnItem = new ToggleButton(item.getDbName());
			btnItem.setUserData(item.getAppName());
			btnItem.setToggleGroup(databaseButtonGroup);
			btnItem.setPrefWidth(120);
			btnItem.setPrefHeight(50);
			buttonsGrid.addRow(rowIndex, btnItem);
			GridPane.setHalignment(btnItem, HPos.CENTER);
			i++;
		}
		
		buttonsGridContainer.getChildren().add(buttonsGrid);
	}
	
	private void addInitListeners() {
		databaseButtonGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			@Override
			public void changed(ObservableValue<? extends Toggle> arg0, Toggle arg1, Toggle arg2) {
				selectedDatabase = null;
				String dbDescription = "";
				
				if (arg2 != null) {
					selectedDatabase = arg2.getUserData().toString();
					dbDescription = textDatabases.get(selectedDatabase).getHelpText();
				}
				txtDbDescription.setText(dbDescription);
			}
		});
	}

	@Override
	public void goForward(StepArgs args) {
		super.goForward(args);
		boolean cancel = selectedDatabase == null;
		args.setCancel(cancel);
		
		if(!cancel) {
			controller.databaseSelected(selectedDatabase);
		}
	}
	
	@Override
	public Parent getGraphicComponent() {
		return viewRootNode;
	}
}
