package ai.iliSuite.view.util.navigation;

public enum EnumPaths {
	
	RESOURCE_BUNDLE("ai.iliSuite.resources.languages.application"),
	
	GENERAL_LAYOUT("/ai/iliSuite/view/fxml/generalLayout.fxml"),
	MAIN_OPTIONS("/ai/iliSuite/view/fxml/mainOptions.fxml"),

	ILI2DB_COMMON_DATABASE_SELECTION("/ai/iliSuite/view/fxml/databaseSelection.fxml"),
	FINISH_ACTION("/ai/iliSuite/view/fxml/finishAction.fxml"),	
	OPEN_UML_EDITOR("/ai/iliSuite/view/fxml/openUmlEditor.fxml"),
	MODEL_CONVERT_OPTIONS("/ai/iliSuite/view/fxml/modelConvertOptions.fxml"),
	IMP_DATA_IMPORT_OPTIONS("/ai/iliSuite/view/fxml/importDataOptions.fxml"),
	VAL_DATA_VALIDATE_OPTIONS("/ai/iliSuite/view/fxml/validateOptions.fxml"),
	EXP_DATA_EXPORT_DATA_OPTIONS("/ai/iliSuite/view/fxml/exportDataOptions.fxml"),
	
	WIZARD_LAYOUT("/ai/iliSuite/view/wizard/wizardLayout.fxml"),
	MULTIPLE_SELECTION_DIALOG("/ai/iliSuite/view/dialog/multipleSelectionDialog.fxml"), //FIX it's not used
	
	APP_ICON("/ai/iliSuite/resources/images/app.png"),
	UMLEDITOR_ICON("/ai/iliSuite/resources/images/edit.png"), //FIX it's not used
	GENERATEPHYSICALMODEL_ICON("/ai/iliSuite/resources/images/db.png"), //FIX it's not used
	IMPORT_ICON("/ai/iliSuite/resources/images/import.png"), //FIX it's not used
	VALIDATE_ICON("/ai/iliSuite/resources/images/check.png"), //FIX it's not used
	EXPORT_ICON("/ai/iliSuite/resources/images/export.png"); //FIX it's not used
	
	private final String path;
	
	EnumPaths(String absolutePath){
		this.path = absolutePath;
	}

	public String getPath() {
		return path;
	}
	
	
}
