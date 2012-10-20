package taro.rejasupo;

import javax.swing.SwingUtilities;

public class BrowserCat {
    private MainFrame mMainFrame;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new BrowserCat().initialize();
            }
        });
    }

    public void initialize() {
        mMainFrame = new MainFrame();
        mMainFrame.setVisible(true);
    }
}
