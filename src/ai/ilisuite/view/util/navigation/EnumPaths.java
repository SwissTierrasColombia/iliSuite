package ai.ilisuite.view.util.navigation;

public enum EnumPaths {
	
	RESOURCE_BUNDLE("ai.ilisuite.resources.languages.application"),
	VERSION_BUNDLE("ai.ilisuite.application.Version"),
	
	GENERAL_LAYOUT("/ai/ilisuite/view/fxml/generalLayout.fxml"),
	MAIN_OPTIONS("/ai/ilisuite/view/fxml/mainOptions.fxml"),

	ILI2DB_COMMON_DATABASE_SELECTION("/ai/ilisuite/view/fxml/databaseSelection.fxml"),
	FINISH_ACTION("/ai/ilisuite/view/fxml/finishAction.fxml"),	
	OPEN_UML_EDITOR("/ai/ilisuite/view/fxml/openUmlEditor.fxml"),
	MODEL_CONVERT_OPTIONS("/ai/ilisuite/view/fxml/modelConvertOptions.fxml"),
	IMP_DATA_IMPORT_OPTIONS("/ai/ilisuite/view/fxml/importDataOptions.fxml"),
	VAL_DATA_VALIDATE_OPTIONS("/ai/ilisuite/view/fxml/validateOptions.fxml"),
	EXP_DATA_EXPORT_DATA_OPTIONS("/ai/ilisuite/view/fxml/exportDataOptions.fxml"),
	
	WIZARD_LAYOUT("/ai/ilisuite/view/wizard/wizardLayout.fxml"),
	MULTIPLE_SELECTION_DIALOG("/ai/ilisuite/view/dialog/multipleSelectionDialog.fxml"), //FIX it's not used
	
	APP_ICON("/ai/ilisuite/resources/images/app.png"),
	UMLEDITOR_ICON("/ai/ilisuite/resources/images/edit.png"),
	GENERATEPHYSICALMODEL_ICON("/ai/ilisuite/resources/images/db.png"),
	IMPORT_ICON("/ai/ilisuite/resources/images/import.png"),
	VALIDATE_ICON("/ai/ilisuite/resources/images/check.png"),
	EXPORT_ICON("/ai/ilisuite/resources/images/export.png"),
	
	CSS_MAIN_PATH("/ai/ilisuite/resources/css/styles.css"),
	APP_ICON_128("/ai/ilisuite/resources/images/icon128.png"),
	APP_ICON_64("/ai/ilisuite/resources/images/icon64.png"),
	APP_ICON_48("/ai/ilisuite/resources/images/icon48.png"),
	APP_ICON_32("/ai/ilisuite/resources/images/icon32.png"),
	APP_ICON_16("/ai/ilisuite/resources/images/icon16.png");
	
	private final String path;
	
	EnumPaths(String absolutePath){
		this.path = absolutePath;
	}

	public String getPath() {
		return path;
	}
	
	
}
