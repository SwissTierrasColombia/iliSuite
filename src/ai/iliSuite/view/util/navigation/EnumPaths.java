package ai.iliSuite.view.util.navigation;

public enum EnumPaths {
	
	RESOURCE_BUNDLE("ai.iliSuite.resources.languages.application"),
	GENERAL_LAYOUT("/ai/iliSuite/view/general/generalLayout.fxml"),
	MAIN_OPTIONS("/ai/iliSuite/view/general/mainOptions.fxml"),
	
	OPEN_UML_EDITOR("/ai/iliSuite/actions/openUmlEditor/openUmlEditor.fxml"),
	
	ILI2DB_COMMON_DATABASE_SELECTION("/ai/iliSuite/view/common/ili2db/databaseSelection.fxml"),
	ILI2DB_COMMON_DATABASE_OPTIONS("/ai/iliSuite/view/common/ili2db/databaseOptions.fxml"),
	
	MODEL_CONVERT_OPTIONS("/ai/iliSuite/actions/generatePhysicalModel/modelConvertOptions.fxml"),
	FINISH_MODEL_GENERATION("/ai/iliSuite/actions/generatePhysicalModel/finishModelGeneration.fxml"),
	
	EXP_DATA_EXPORT_DATA_OPTIONS("/ai/iliSuite/actions/exportData/exportDataOptions.fxml"),
	EXP_DATA_FINISH_DATA_EXPORT("/ai/iliSuite/actions/exportData/finishDataExport.fxml"),
	
	IMP_DATA_IMPORT_OPTIONS("/ai/iliSuite/actions/importData/importDataOptions.fxml"),
	IMP_DATA_FINISH_DATA_IMPORT("/ai/iliSuite/actions/importData/finishDataImport.fxml"),
	
	VAL_DATA_VALIDATE_OPTIONS("/ai/iliSuite/actions/validateData/validateOptions.fxml"),
	VAL_DATA_FINISH_VALIDATION("/ai/iliSuite/actions/validateData/finishDataValidation.fxml"),
		
	MULTIPLE_SELECTION_DIALOG("/ai/iliSuite/view/dialog/multipleSelectionDialog.fxml"),
	
	APP_ICON("/ai/iliSuite/resources/images/app.png"),
	UMLEDITOR_ICON("/ai/iliSuite/resources/images/edit.png"),
	GENERATEPHYSICALMODEL_ICON("/ai/iliSuite/resources/images/db.png"),
	IMPORT_ICON("/ai/iliSuite/resources/images/import.png"),
	VALIDATE_ICON("/ai/iliSuite/resources/images/check.png"),
	EXPORT_ICON("/ai/iliSuite/resources/images/export.png");
	
	private final String path;
	
	EnumPaths(String absolutePath){
		this.path = absolutePath;
	}

	public String getPath() {
		return path;
	}
	
	
}
