package ai.ilisuite.util.params;

public enum EnumParams {
	
	/******************
	 * ili2db Params
	 ******************/
	
	//General Options
	LOG("--log", true),
	PROXY("--proxy", true),
	PROXY_PORT("--proxyPort", true),
	TRACE("--trace", false),
	HELP("--help", false),
	VERSION("--version", false),
	
	MODEL_DIR("--modeldir",true),
	//General validation Options
	DISABLE_VALIDATION("--disableValidation",false),
	VALID_CONFIG("--validConfig",true),
	DISABLE_AREA_VALIDATION("--disableAreaValidation", false),
	FORCE_TYPE_VALIDATION("--forceTypeValidation", false),
	SKIP_GEOMETRY_ERRORS("--skipGeometryErrors", false),
	SKIP_REFERENCE_ERRORS("--skipReferenceErrors", false),
	//Main Functions
	SCHEMA_IMPORT("--schemaimport", false),
	DATA_IMPORT("--import", false),
	DATA_EXPORT("--export", false),
	//export functions
	MODELS("--models", true),
	TOPICS("--topics", true),
	BASKETS("--baskets", true),
	DATASET("--dataset", true),
	//import functions
	DELETE_DATA("--deleteData", false),
	REPLACE("--replace", false),
	DELETE("--delete", false),
	UPDATE("--update", false),
	//table/column names
	DISABLE_NAME_OPTIMIZATION("--disableNameOptimization", false),
	NAME_BY_TOPIC("--nameByTopic", false),
	MAX_NAME_LENGTH("--maxNameLength", true),
	//inheritance mapping
	NO_SMART_MAPPING("--noSmartMapping", false),
	SMART_1_INHERITANCE("--smart1Inheritance", false),
	SMART_2_INHERITANCE("--smart2Inheritance", false),
	//
	CREATE_ENUM_TABS("--createEnumTabs", false),
	CREATE_ENUM_TABS_WITH_ID("--createEnumTabsWithId", false),
	CREATE_SINGLE_ENUM_TAB("--createSingleEnumTab", false),
	CREATE_ENUM_COL_AS_ITF_CODE("--createEnumColAsItfCode", false), //only interlis 1
	CREATE_ENUM_TXT_COL("--createEnumTxtCol", false),
	BEAUTIFY_ENUM_DISP_NAME("--beautifyEnumDispName", false),
	//
	COALESCE_CATALOGUE_REF("--coalesceCatalogueRef", false),
	COALESCE_MULTISURFACE("--coalesceMultiSurface", false),
	EXPAND_MULTILINGUAL("--expandMultilingual", false),
	COALESCE_MULTILINE("--coalesceMultiLine",false),
	@Deprecated
	STRUCT_WITH_GENERIC_REF("--structWithGenericRef", false),
	//
	CREATE_GEOM_IDX("--createGeomIdx", false),
	CREATE_FK_IDX("--createFkIdx", false),
	//Constrains creation
	SQL_ENABLE_NULL("--sqlEnableNull", false),
	CREATE_FK("--createFk", false),
	CREATE_UNIQUE("--createUnique", false),
	CREATE_NUM_CHECKS("--createNumChecks", false),
	//Mapping of geometry
	DEFAULT_SRS_AUTH("--defaultSrsAuth", true),
	DEFAULT_SRS_CODE("--defaultSrsCode", true),
	STROKE_ARCS("--strokeArcs", false),
	ONE_GEOM_PER_TABLE("--oneGeomPerTable", false),
	//Additional meta information
	CREATE_STD_COLS("--createStdCols", false),
	T_ID_NAME("--t_id_Name", true),
	CREATE_TYPE_DISCRIMINATOR("--createTypeDiscriminator", false),
	IMPORT_TID("--importTid", false),
	CREATE_BASKET_COL("--createBasketCol", false),
	CREATE_DATASET_COL("--createDatasetCol", false),
	CREATE_METAINFO("--createMetaInfo", false),
	//Misellaneous
	VER4_TRANSLATION("--ver4-translation", false),
	ID_SEQ_MIN("--idSeqMin", true), //number
	ID_SEQ_MAX("--idSeqMax", true), //number
	CREATE_SCRIPT("--createscript", true),
	DROP_SCRIPT("--dropscript", false),
	
	/******************
	 * iliValidator Params
	 ******************/
	IV_CONFIG_FILE("--config", true),
	IV_FORCETYPEVALIDATION("--forceTypeValidation", false),
	IV_DISABLEAREAVALIDATION("--disableAreaValidation", false),
	IV_XTFLOG("--xtflog", true),
	IV_PLUGINS("--plugins", true),

	// TODO Options Unimplemented
	COALESCE_ARRAY("--coalesceArray", false),
	COALESCE_MULTI_POINT("--coalesceMultiPoint", false),
	EXPORT_MODELS("--exportModels", true),
	// GUI("--gui", false), 
	IMPORT_BID("--importBid", false),
	ILIGML20("--ILIGML20", false),
	ILI_META_ATTRS("--iliMetaAttrs", true),
	KEEP_AREA_REF("--keepAreaRef", false),
	POST_SCRIPT("--postScript", true),
	PRE_SCRIPT("--preScript", true),
	SKIP_POLYGON_BUILDING("--skipPolygonBuilding", false),
	TRANSLATION("--translation", true),
	VER4NO_SCHEMA_IMPORT("--ver4-noSchemaImport", false),
	
	DISABLE_ROUNDING("--disableRounding", false),
	FILE_NAME("ili_xtf_file", true);
	
	private final String name;
	private final boolean needParam;
	
	EnumParams(String name, boolean needParam){
		this.name = name;
		this.needParam = needParam;
	}

	public String getName() {
		return name;
	}

	public boolean isParamNeeded() {
		return needParam;
	}
		
	
}
