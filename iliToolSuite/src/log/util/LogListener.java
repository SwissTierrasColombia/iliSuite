package log.util;

import javafx.scene.text.Text;

public class LogListener extends ch.interlis.iox_j.logging.StdLogger {

	private Text out;
	public LogListener(Text out,String logfileName) {
		super(logfileName);
		this.out = out;
	}
	
	@Override
	public void outputMsgLine(int arg0, int arg1, String msg) {
		if(msg.endsWith("\n")){
			out.setText(out.getText() + msg);
		}else{
			out.setText(out.getText() + msg +"\n");
		}
	}
}
