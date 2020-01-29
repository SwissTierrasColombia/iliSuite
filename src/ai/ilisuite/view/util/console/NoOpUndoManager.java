package ai.ilisuite.view.util.console;
import org.reactfx.value.Val;
import javafx.beans.value.ObservableBooleanValue;
import org.fxmisc.undo.UndoManager;

public class NoOpUndoManager implements UndoManager<String>{
	private final Val<Boolean> alwaysFalse = Val.constant(false);

    @Override public boolean undo() { return false; }
    @Override public boolean redo() { return false; }
    @Override public Val<Boolean> undoAvailableProperty() { return alwaysFalse; }
    @Override public boolean isUndoAvailable() { return false; }
    @Override public Val<Boolean> redoAvailableProperty() { return alwaysFalse; }
    @Override public boolean isRedoAvailable() { return false; }
    @Override public boolean isPerformingAction() { return false; }
    @Override public boolean isAtMarkedPosition() { return false; }
    
    @Override public Val<String> nextUndoProperty() { return null; }
    @Override public Val<String> nextRedoProperty() { return null; }
    @Override public ObservableBooleanValue performingActionProperty() { return null; }
    @Override public UndoPosition getCurrentPosition() { return null; }
    @Override public ObservableBooleanValue atMarkedPositionProperty() { return null; }
    
    @Override public void preventMerge() { }
    @Override public void forgetHistory() { }
    @Override public void close() { }
}
