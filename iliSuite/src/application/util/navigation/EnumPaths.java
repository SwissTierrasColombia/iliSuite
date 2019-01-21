package application.util.navigation;

public enum EnumPaths {
	
	RESOURCE_BUNDLE("resources.languages.application"),
	GENERAL_LAYOUT("/application/view/generalLayout.fxml"),
	MAIN_OPTIONS("/application/view/mainOptions.fxml"),
	
	OPEN_UML_EDITOR("/actions/openUmlEditor/openUmlEditor.fxml"),
	
	ILI2DB_COMMON_DATABASE_SELECTION("/application/ili2db/common/databaseSelection.fxml"),
	ILI2DB_COMMON_DATABASE_OPTIONS("/application/ili2db/common/databaseOptions.fxml"),
	
	MODEL_CONVERT_OPTIONS("/actions/generatePhysicalModel/modelConvertOptions.fxml"),
	FINISH_MODEL_GENERATION("/actions/generatePhysicalModel/finishModelGeneration.fxml"),
	
	EXP_DATA_EXPORT_DATA_OPTIONS("/actions/exportData/exportDataOptions.fxml"),
	EXP_DATA_FINISH_DATA_EXPORT("/actions/exportData/finishDataExport.fxml"),
	
	IMP_DATA_IMPORT_OPTIONS("/actions/importData/importDataOptions.fxml"),
	IMP_DATA_FINISH_DATA_IMPORT("/actions/importData/finishDataImport.fxml"),
	
	VAL_DATA_VALIDATE_OPTIONS("/actions/validateData/validateOptions.fxml"),
	VAL_DATA_FINISH_VALIDATION("/actions/validateData/finishDataValidation.fxml"),
		
	MULTIPLE_SELECTION_DIALOG("/application/dialog/multipleSelectionDialog.fxml"),
	
	APP_ICON("/resources/images/app.png"),
	UMLEDITOR_ICON("/resources/images/edit.png"),
	GENERATEPHYSICALMODEL_ICON("/resources/images/db.png"),
	IMPORT_ICON("/resources/images/import.png"),
	VALIDATE_ICON("/resources/images/check.png"),
	EXPORT_ICON("/resources/images/export.png");
	
	private final String path;
	
	EnumPaths(String absolutePath){
		this.path = absolutePath;
	}

	public String getPath() {
		return path;
	}
	
	
}
