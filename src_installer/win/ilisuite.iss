#define MyAppName "iliSuite"
#define MyAppVersion "1.3.3-20200303_SNR"
#define MyAppPublisher "Agencia de implementación"
#define MyAppURL "https://www.proadmintierra.info/"

#define MyAppJarName "iliSuite.jar"
#define SourcePath "..\..\build\dist"
#define OutputDirectory "..\..\build\bindist"

[Setup]
AppId={{F9E32CF6-C279-4A90-90C9-76F0AA72CA54}
AppName={#MyAppName}
AppVersion={#MyAppVersion}
AppPublisher={#MyAppPublisher}
AppPublisherURL={#MyAppURL}
AppSupportURL={#MyAppURL}
AppUpdatesURL={#MyAppURL}
DefaultDirName=c:\{#MyAppName}
DefaultGroupName={#MyAppName}
OutputDir={#OutputDirectory}
OutputBaseFilename={#MyAppName}_setup
Compression=none
; lzma
SolidCompression=yes
ArchitecturesInstallIn64BitMode=x64

; additional pages
DisableWelcomePage=No
DisableProgramGroupPage=no
DisableReadyPage=no

SetupIconFile=.\images\ilisuite.ico
WizardSmallImageFile=.\images\suiteup.bmp
WizardImageFile=.\images\suiteleft.bmp

[Languages]
Name: "english"; MessagesFile: "compiler:Default.isl"
Name: "spanish"; MessagesFile: "compiler:Languages\Spanish.isl"
Name: "german"; MessagesFile: "compiler:Languages\German.isl"

[Tasks]
Name: "desktopicon"; Description: "{cm:CreateDesktopIcon}"; GroupDescription: "{cm:AdditionalIcons}";

[Files]
Source: "{#SourcePath}\ilisuite.jar"; DestDir: "{app}"; Flags: ignoreversion nocompression
Source: ".\images\ilisuite.ico"; DestDir: "{app}"; Flags: ignoreversion nocompression
Source: "{#SourcePath}\iliSuite_help_es.pdf"; DestDir: "{app}"; Flags: ignoreversion
Source: "{#SourcePath}\help\*"; DestDir: "{app}\help"; Flags: ignoreversion createallsubdirs recursesubdirs
Source: "{#SourcePath}\ilisuite_lib\*"; DestDir: "{app}\ilisuite_lib"; Flags: ignoreversion createallsubdirs recursesubdirs nocompression
Source: "{#SourcePath}\programs\*"; DestDir: "{app}\programs"; Flags: ignoreversion createallsubdirs recursesubdirs nocompression
Source: "{#SourcePath}\.defaultConfig.properties"; DestDir: "{app}"

[Icons]
Name: "{commondesktop}\{#MyAppName}"; Filename: "{code:getPathJava}"; WorkingDir: "{app}"; IconFilename: "{app}\ilisuite.ico"; Parameters: "-jar ""{app}\{#MyAppJarName}"""; Tasks: desktopicon
Name: "{group}\{#MyAppName}"; Filename: "{code:getPathJava}"; WorkingDir: "{app}"; IconFilename: "{app}\ilisuite.ico"; Parameters: "-jar ""{app}\{#MyAppJarName}"""
Name: "{group}\Ayuda iliSuite"; Filename: "{app}\iliSuite_help_es.pdf"; Languages: spanish

[Run]
Filename: "{code:getPathJava}"; WorkingDir: "{app}"; Parameters: "-jar ""{app}\{#MyAppJarName}"""; Description: "{cm:LaunchProgram,{#StringChange(MyAppName, '&', '&&')}}"; Flags: shellexec postinstall skipifsilent

#define REG_PATH_JAVA32 'SOFTWARE\WOW6432node\JavaSoft'
#define REG_PATH_JAVA 'SOFTWARE\JavaSoft'

#define JRE_FOLDER 'Java Runtime Environment'
#define JDK_FOLDER 'Java Development Kit'

#define JAVA_MAJOR 1
#define JAVA_MINOR 8
#define JAVA_MICRO_A 0
#define JAVA_MICRO_B 0

[Messages]
BeveledLabel=iliSuite v{#MyAppVersion}

[CustomMessages]
english.jre_error=You must install Java Runtime Environment JRE™ version 1.8 or later.
english.jrepage_title=Select JRE
english.jrepage_description=Select JRE version that will be used for {#MyAppName}
english.jrepage_content=Select an option, then click Next to continue.
english.jrepage_nooption=Please, select an option

spanish.jrepage_title=Seleccione JRE
spanish.jrepage_description=Seleccione la versión del JRE que utilizará {#MyAppName}
spanish.jrepage_content=Seleccione una opción y haga clic en Siguiente para continuar.
spanish.jre_error=Debe instalar Java Runtime Environment JRE™ versión 1.8 o superior.
spanish.jrepage_nooption=Por favor, seleccione una opción

[Dirs]
Name: "{app}\help\help"
Name: "{app}\help\html\html"
Name: "{app}\help\html\css\css"
Name: "{app}\help\html\fonts\fonts"
Name: "{app}\help\html\img\img"
Name: "{app}\help\html\js\js"
Name: "{app}\help\html\js\vendor\vendor"
Name: "{app}\log"
[Code]
var
lstJavaInstalled: TStringList;
JreOptionsPage: TInputOptionWizardPage;
jreVersionSelected: Integer;
index32: Integer;

function Split(strInput, strToken: String): TStringList;
var
	strInputCopy: String;
	position: Integer;
	return: TStringList;
begin
	strInputCopy := strInput;
	return :=  TStringList.Create;

	repeat
	begin
		position := pos(strToken, strInputCopy);

		// the token was found
		if position > 0 then
		begin
			return.Append(Copy(strInputCopy, 0, position-1));
			strInputCopy := Copy(strInputCopy, position+1, Length(strInputCopy) - (position));
		end else
		begin
		return.Append(strInputCopy);
		end;
	end
	until position <= 0;

	Result := return;
end;

function JavaValid(versionJava: String): Boolean;
var
	itemVersion: TStringList;
	itemMajor: Integer;
	itemMinor: Integer;
	itemMicroA: Integer;
	itemMicroB: Integer;
	return: Boolean;
begin
	return := False;

	itemVersion := Split(versionJava, '.');

	itemMajor := StrToInt(itemVersion[0]);
	itemMinor := StrToInt(itemVersion[1]);

	itemVersion := Split(itemVersion[2], '_');
	itemMicroA := StrToInt(itemVersion[0]);
	itemMicroB := StrToInt(itemVersion[1]);

	if itemMajor > {#JAVA_MAJOR} then
	begin
		Result := True;
	end
	else if itemMajor = {#JAVA_MAJOR} then
	begin
		if itemMinor > {#JAVA_MINOR} then
		begin
			return := True;
		end
		else if itemMinor = {#JAVA_MINOR} then
		begin
			if itemMicroA > {#JAVA_MICRO_A} then
			begin
				return := True;
			end
			else if itemMicroA = {#JAVA_MICRO_A} then
			begin
				if itemMicroB >= {#JAVA_MICRO_B} then
				begin
					return := True;
				end;
			end;
		end;
	end;

	Result := return;
end;

Procedure getLstJre(lstJre:TStringList; findFolder32: Boolean);
var
	Names: TArrayOfString;
	I: Integer;
	regPath: String;
begin
	if findFolder32 then
	begin
		regPath := '{#REG_PATH_JAVA32}'
	end
	else begin
		regPath :='{#REG_PATH_JAVA}';
	end;

	if RegKeyExists(HKEY_LOCAL_MACHINE, regPath) then
	begin		
		if RegGetSubkeyNames(HKEY_LOCAL_MACHINE, regPath + '\{#JRE_FOLDER}', Names) then
		begin
			for I := 0 to GetArrayLength(Names)-1 do
			begin		 
				if (Pos('_', Names[I]) <> 0) then
				begin
					if JavaValid(Names[I]) then
						lstJre.Add(Names[I]);
				end;
			end;
		end;
	end;
end;

function InitializeSetup(): Boolean;
var
	return: Boolean;
begin
	return := True
	jreVersionSelected := -1;
	index32 := 200;

	lstJavaInstalled := TStringList.Create;

	getLstJre(lstJavaInstalled, False);

	index32 := lstJavaInstalled.Count;

	if Is64BitInstallMode then
	begin
		getLstJre(lstJavaInstalled, True);
	end;

	if lstJavaInstalled.Count = 0 then
	begin
		MsgBox(CustomMessage('jre_error'), mbCriticalError, MB_OK);
		return := False;
	end;

	Result:=return;
end;

procedure InitializeWizard;
var
	k: Integer;
	txt32: String;
begin
	txt32 := '';
	// installer always creates the jre selection page but it does not show it when there is only one jre installed
	JreOptionsPage := CreateInputOptionPage(wpWelcome, CustomMessage('jrepage_title'),
	CustomMessage('jrepage_description'), CustomMessage('jrepage_content'), True, False);

	if lstJavaInstalled.Count > 1 then
	begin  
		for k:=0 to lstJavaInstalled.Count-1 do
		begin
			if k=index32 then
				txt32 := ' (x86)';

			JreOptionsPage.Add(lstJavaInstalled[k]+txt32);
		end;
	end
	else if lstJavaInstalled.Count = 1 then
	begin
		// choose the unique option
		jreVersionSelected := 0;
	end
end;

function getPathJava(Param: String): String;
var
pathRegisterJava: String;
pathJava: String;
begin
	if jreVersionSelected < index32 then
	begin
		pathRegisterJava := '{#REG_PATH_JAVA}\{#JRE_FOLDER}\'+lstJavaInstalled[jreVersionSelected];
	end else
	begin
		pathRegisterJava := '{#REG_PATH_JAVA32}\{#JRE_FOLDER}\'+lstJavaInstalled[jreVersionSelected];
	end;

	if RegQueryStringValue(HKEY_LOCAL_MACHINE, pathRegisterJava, 'JavaHome', pathJava) then
	begin
		pathJava := pathJava+'\bin\javaw.exe';
	end;

	Result:=pathJava;
end;

function ShouldSkipPage(PageID: Integer): Boolean;
var
return: Boolean;
begin
	return := False;
	if (JreOptionsPage.ID = PageID) and (lstJavaInstalled.Count = 1) then
		return := True;
	Result:=return;
end;

function NextButtonClick(CurPageID: Integer): Boolean;
var
return: Boolean;
k: Integer;
begin
	return := True;
	if JreOptionsPage.ID = CurPageID then
	begin
		for k:=0 to lstJavaInstalled.Count-1 do
		begin
			if JreOptionsPage.Values[k] then
			jreVersionSelected := k;
		end;

		if jreVersionSelected = -1 then begin
			MsgBox(CustomMessage('jrepage_nooption'),mbInformation, MB_OK);
			return := False;
		end;
	end;

	Result := return;
end;
