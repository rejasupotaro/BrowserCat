package taro.rejasupo.ui;

import java.awt.TextArea;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class LogDisplayPanel extends JPanel {
    public static String DEFAULT_FILTER = "I/browser";
    private static int MAX_LOG_COUNT_SIZE = 50;
    
    private LinkedList<String> mLogList;
    private TextArea mLogTextArea;

    private String mFilter = DEFAULT_FILTER;

    public LogDisplayPanel() {
        mLogList = new LinkedList<String>();

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        mLogTextArea = new TextArea();
        mLogTextArea.setEditable(false);
        add(mLogTextArea);
    }

    public void append(String log) {
        mLogList.offer(log);
        if (mLogList.size() >= MAX_LOG_COUNT_SIZE) {
            mLogList.pollFirst();
        }

        String filteredLog = applyFilter(log, mFilter);
        if (filteredLog != null) {
            mLogTextArea.append(filteredLog);
        }
    }

    private static final String applyFilter(String log, String filter) {
        Pattern pattern = Pattern.compile(filter);
        Matcher matcher = null;

        matcher = pattern.matcher(log);
        if (matcher.find()) {
            return log;
        }

        return null;
    }

    private static final List<String> applyFilter(List<String> logList, String filter) {
        List<String> filteredLogList = new ArrayList<String>();

        Pattern pattern = Pattern.compile(filter);
        Matcher matcher = null;

        for (String line: logList) {
            matcher = pattern.matcher(line);
            if (matcher.find()) {
                filteredLogList.add(line);
            }
        }

        return filteredLogList;
    }

    public void setFilter(String filter) {
        mFilter = filter;
        
        List<String> filteredLogList = applyFilter(mLogList, mFilter);

        mLogTextArea.setText("");
        for (String line: filteredLogList) {
            mLogTextArea.append(line);
        }
    }

    public void clear() {
        mLogTextArea.setText("");
        //mLogList.clear();
    }
}
