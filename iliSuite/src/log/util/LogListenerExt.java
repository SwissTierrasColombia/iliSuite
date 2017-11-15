package log.util;


import ch.ehi.basics.logging.AbstractFilteringListener;
import javafx.scene.text.Text;

public class LogListenerExt extends AbstractFilteringListener {

	private Text out;
	public LogListenerExt(Text out,String logfileName) {
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
