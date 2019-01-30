package actions.importData;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.fxmisc.flowless.VirtualizedScrollPane;
import org.fxmisc.richtext.StyleClassedTextArea;

import application.data.AppData;
import base.IPluginDb;
import ch.ehi.basics.logging.EhiLogger;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import util.exception.ExitException;
import util.log.LogListenerExt;
import util.params.ParamsContainer;
import util.plugin.PluginsLoader;
import view.util.console.NoOpUndoManager;
import view.util.navigation.EnumPaths;
import view.util.navigation.Navigable;

public class FinishDataImportController implements Navigable, Initializable {

	private StyleClassedTextArea txtConsole;

	private LogListenerExt log;

	@FXML
	private VBox verticalWrapper;

	private List<String> command;

	IPluginDb plugin;

	ExecutorService executor = Executors.newFixedThreadPool(1);
	boolean stop = false;
	
	Runnable runnableTask;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		initTxtConsole();

		log = new LogListenerExt(txtConsole, "");
		
		EhiLogger.getInstance().addListener(log);

		ParamsContainer paramsContainer = AppData.getInstance().getParamsContainer();
		ParamsContainer.addCommonsParameters();

		command = paramsContainer.getCommand(null);
		txtConsole.replaceText(String.join(" ", command) + "\n\n");
	}

	private void initTxtConsole() {
		txtConsole = new StyleClassedTextArea();

		VirtualizedScrollPane<StyleClassedTextArea> vsPane = new VirtualizedScrollPane<>(txtConsole);
		verticalWrapper.getChildren().add(vsPane);
		VBox.setVgrow(vsPane, Priority.ALWAYS);

		txtConsole.setWrapText(true);
		txtConsole.setMinHeight(400);
		txtConsole.getStyleClass().add("text_console");
		txtConsole.setEditable(false);
		txtConsole.setUndoManager(new NoOpUndoManager());

		runnableTask = () -> {
			int maxParagraphs = 500;

			while (!stop) {
				try {
					Thread.sleep(20);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				Platform.runLater(() -> {
					int numParsToRemove = txtConsole.getParagraphs().size() - maxParagraphs;
					if (numParsToRemove > 0) {
						txtConsole.deleteText(0, 0, numParsToRemove, 0);
					}

					txtConsole.showParagraphAtBottom(txtConsole.getParagraphs().size());

				});
			}
		};
	}

	@Override
	public void finalize() {
		EhiLogger.getInstance().removeListener(log);
		stop = true;
	}

	@Override
	public boolean validate() {
		String pluginKey = AppData.getInstance().getPlugin();
		plugin = (IPluginDb) PluginsLoader.getPluginByKey(pluginKey);

		String[] args = command.toArray(new String[0]);
		stop = false;
		executor.execute(runnableTask);
		try {
			plugin.runMain(args);
			stop = true;
			return true;
		} catch (ExitException e) {
			System.out.println(e.status);
			stop = true; 
			return false;
		}
	}

	@Override
	public EnumPaths getNextPath() {
		return null;
	}

	@Override
	public boolean isFinalPage() {
		return true;
	}
}
