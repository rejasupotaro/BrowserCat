package taro.rejasupo.ui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class CommandPanel extends JPanel {
    private static String DUMMY_PATH = "/fullpath/to/adb";
    private static String EXECUTE_BUTTON_TEXT = "読み込み";
    
    private TextField mAdbPathTextField;

    public CommandPanel(final CommandPanel.Callback callback) {
        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));

        mAdbPathTextField = new TextField(DUMMY_PATH);
        JButton executeButton = new JButton(EXECUTE_BUTTON_TEXT);
        executeButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent event) {
                callback.onExecuteClick(mAdbPathTextField.getText());
            }
            @Override
            public void mouseEntered(MouseEvent event) {}
            @Override
            public void mouseExited(MouseEvent event) {}
            @Override
            public void mousePressed(MouseEvent event) {}
            @Override
            public void mouseReleased(MouseEvent event) {}
        });

        Dimension adbPathTextFieldDimension = mAdbPathTextField.getMaximumSize();
        Dimension executeButtonDimension = executeButton.getMaximumSize();
        adbPathTextFieldDimension.width  = Short.MAX_VALUE;
        adbPathTextFieldDimension.height = executeButtonDimension.height;
        mAdbPathTextField.setMaximumSize(adbPathTextFieldDimension);
        add(mAdbPathTextField);
        add(executeButton);
    }
    
    public void setAdbPath(String adbPath) {
        if (adbPath != null && adbPath != "") {
            mAdbPathTextField.setText(adbPath);
        }
    }

    public interface Callback {
        public void onExecuteClick(String adbPath);
    }
}
