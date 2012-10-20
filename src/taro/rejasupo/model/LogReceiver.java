package taro.rejasupo.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class LogReceiver {
    private String mCommand;
    private LogReceiver.Callback mCallback;

    public LogReceiver(String adbPath, LogReceiver.Callback callback) {
        mCallback = callback;
        mCommand = adbPath + " " + "logcat";
    }

    public void start() {
        if (mCommand == null) {
            return;
        }

        try {
            ProcessBuilder builder = new ProcessBuilder(mCommand.split(" "));
            Process process;
            process = builder.start();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (mCallback != null) {
                    mCallback.onLogReceive(line + "\n");
                }
            }
            process.destroy();
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public interface Callback {
        public void onLogReceive(String log);
    }

}
