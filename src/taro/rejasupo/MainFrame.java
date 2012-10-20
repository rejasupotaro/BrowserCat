package taro.rejasupo;

import java.awt.Container;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import taro.rejasupo.model.LogReceiver;
import taro.rejasupo.ui.CommandPanel;
import taro.rejasupo.ui.LogDisplayPanel;
import taro.rejasupo.ui.LogFilterPanel;

@SuppressWarnings("serial")
public class MainFrame extends JFrame {
    public static final String TITLE = "BrowserCat";
    public static final int DEFAULT_WIDTH = 640;
    public static final int DEFAULT_HEIGHT = 480;

    private CommandPanel mCommandPanel;
    private LogFilterPanel mLogFilterPanel;
    private LogDisplayPanel mLogDisplayPanel;
    private Thread mWatchLogThread;

    public MainFrame() {
        initializeFrame();
        initializePanel();
    }

    private void saveAdbPath(String adbPath) {
        try{
            File file = new File("adbPath");
            FileWriter filewriter = new FileWriter(file);
            filewriter.write(adbPath);
            filewriter.close();
        }catch(IOException e){
            System.out.println(e);
        }
    }

    private String getAdbPath() {
        String adbPath = "";

        try {
            FileReader fileReader = new FileReader("adbPath");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                adbPath += line;
            }
            bufferedReader.close();
            fileReader.close();
        } catch (IOException e) {
            System.out.println(e);
        }

        return adbPath;
    }

    private void initializeFrame() {
        setTitle(TITLE);
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("icon.jpeg")));
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);
    }

    private void initializePanel() {
        Container container = getContentPane();

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        mCommandPanel = new CommandPanel(new CommandPanel.Callback() {
            @Override
            public void onExecuteClick(String adbPath) {
                saveAdbPath(adbPath);
                //mLogDisplayPanel.setFilter(mLogFilterPanel.getFilter());
                startWatchLog(adbPath);
            }
        });
        mCommandPanel.setAdbPath(getAdbPath());
        mLogDisplayPanel = new LogDisplayPanel();
        mLogFilterPanel = new LogFilterPanel(new LogFilterPanel.Callback() {
            @Override
            public void onFilterClick(String filter) {
                mLogDisplayPanel.setFilter(filter);
            }

        });

        mainPanel.add(mCommandPanel);
        mainPanel.add(mLogFilterPanel);
        mainPanel.add(mLogDisplayPanel);

        container.add(mainPanel);
    }

    private void startWatchLog(final String adbPath) {
        if (mWatchLogThread == null) {
            mWatchLogThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    new LogReceiver(adbPath, new LogReceiver.Callback() {
                        @Override
                        public void onLogReceive(String log) {
                            mLogDisplayPanel.append(log);
                        }

                    }).start();
                }
            });
            mWatchLogThread.start();
        } else {
            mLogDisplayPanel.clear();
        }
        
    }
}
