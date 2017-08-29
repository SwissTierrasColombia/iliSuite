package application.ili2db.common;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;

import application.data.AppData;
import application.util.navigation.EnumPaths;
import application.util.navigation.Navigable;
import application.util.plugin.PluginsLoader;
import base.IPluginDb;
import base.Iplugin;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Text;

public class DatabaseSelectionController implements Navigable, Initializable {

	@FXML
	private GridPane buttonsGrid;

	@FXML
	private Text txt_dbDescription;
	
	@FXML
	private AnchorPane buttonsGridContainer;

	@Override
	public boolean validate() {
		return !AppData.getInstance().getPlugin().isEmpty();
	}

	@Override
	public boolean isFinalPage() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		AppData.getInstance().setPlugin("");

		Map<String, Iplugin> lstPlugin = PluginsLoader.getPlugins();

		ToggleGroup group = new ToggleGroup();

		group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			@Override
			public void changed(ObservableValue<? extends Toggle> arg0, Toggle arg1, Toggle arg2) {
				if (arg2 == null) {
					txt_dbDescription.setText("");

					AppData.getInstance().setPlugin("");
				} else {
					String pluginKey = group.getSelectedToggle().getUserData().toString();

					IPluginDb plugin = (IPluginDb) PluginsLoader.getPluginByKey(pluginKey);

					// TODO Verificar que es el lugar correcto para establecer
					// el valor
					AppData.getInstance().setPlugin(pluginKey);
					txt_dbDescription.setText(plugin.getHelpText());
				}

			}
		});
		

		GridPane buttonsGrid = new GridPane();
		buttonsGrid.prefWidthProperty().bind(buttonsGridContainer.widthProperty());
		buttonsGrid.prefHeightProperty().bind(buttonsGridContainer.heightProperty());
		
		

		int elements = 0;
		List<IPluginDb> pluginList = new ArrayList<>();
		for (Entry<String, Iplugin> item : lstPlugin.entrySet()) {
			if (item.getValue() instanceof IPluginDb) {
				elements++;
				IPluginDb itemPlugin = (IPluginDb) item.getValue();
				pluginList.add(itemPlugin);
			}
		}
		int columns = 2;
		int rows= (elements/columns)+ (columns%2 > 0 ? 1 : 0);
		
		
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
		
		
		int rowIndex = 0;
		for(int i = 0; i<pluginList.size();i++){
			rowIndex = i/columns;
			ToggleButton btnItem = new ToggleButton(pluginList.get(i).getNameDB());
			btnItem.setUserData(pluginList.get(i).getName());
			btnItem.setToggleGroup(group);
			btnItem.setPrefWidth(120);
			btnItem.setPrefHeight(50);
			buttonsGrid.addRow(rowIndex, btnItem);
			GridPane.setHalignment(btnItem, HPos.CENTER);
		}
		
		
		
		
		buttonsGridContainer.getChildren().add(buttonsGrid);
	}

	@Override
	public EnumPaths getNextPath() {
		// TODO Auto-generated method stub
		return EnumPaths.ILI2DB_COMMON_DATABASE_OPTIONS;
	}
}
