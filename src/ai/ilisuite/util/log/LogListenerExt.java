package ai.ilisuite.util.log;
import org.fxmisc.richtext.StyleClassedTextArea;
import ch.ehi.basics.logging.AbstractFilteringListener;
import javafx.application.Platform;

public class LogListenerExt extends AbstractFilteringListener {

	private StyleClassedTextArea out;

	public LogListenerExt(StyleClassedTextArea out, String logfileName) {
		this.out = out;
	}

	@Override
	public void outputMsgLine(int arg0, int arg1, String msg) {

		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				String endOfLine = "";
				if (!msg.endsWith("\n")) {
					endOfLine = "\n";
				}

				int length = out.getLength();
				out.appendText(msg + endOfLine);

				if (msg.startsWith("Info", 0)) {
					out.setStyleClass(length, length + 6, "info");
				} else if (msg.startsWith("Error", 0)) {
					out.setStyleClass(length, length + 7, "error");
				}
			}
		});

	}
}
