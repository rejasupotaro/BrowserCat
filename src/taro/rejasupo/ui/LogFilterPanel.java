package taro.rejasupo.ui;

import java.awt.Dimension;
import java.awt.TextField;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class LogFilterPanel extends JPanel {
    private static final String BUTTON_TEXT = "フィルタ";
    
    private TextField mFilterTextField;
    private static LogFilterPanel.Callback mCallback;
    
    public LogFilterPanel(final LogFilterPanel.Callback callback) {
        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));

        mFilterTextField = new TextField(LogDisplayPanel.DEFAULT_FILTER);
        JButton filterButton = new JButton(BUTTON_TEXT);
        filterButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent event) {
                callback.onFilterClick(mFilterTextField.getText());
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

        Dimension commandTextFieldDimension = mFilterTextField.getMaximumSize();
        Dimension filterButtonDimension = filterButton.getMaximumSize();
        commandTextFieldDimension.width  = Short.MAX_VALUE;
        commandTextFieldDimension.height = filterButtonDimension.height;
        mFilterTextField.setMaximumSize(commandTextFieldDimension);
        add(mFilterTextField);
        add(filterButton);
    }
    
    public String getFilter() {
        return mFilterTextField.getText();
    }
    
    public interface Callback {
        public void onFilterClick(String filter);
    }
}
