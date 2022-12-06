package com.blankspace.sakura.calendar;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.blankspace.sakura.R;

public class TestFragment extends Fragment {


    private int i = 1;
    private HandlerThread mThread;
    //    private Handler mHandler = new Handler(Looper.getMainLooper()) {
//        @Override
//        public void handleMessage(@NonNull Message msg) {
//            super.handleMessage(msg);
//            if (msg.what == 2000) {
//                test.setText(i++ + "");
//            }
//
//        }
//    };
    private TextView test;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_test, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        test = view.findViewById(R.id.text);
//        if(compare("1.8.4","1.8.3.9")){
//            test.setText("升级");
//        }
        mThread = new HandlerThread("test");
        mThread.start();
        Handler handler = new Handler(mThread.getLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                if (msg.what == 2000) {
                    if (getActivity() != null) {
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                test.setText(i++ + "");
                            }
                        });
                        this.sendEmptyMessageDelayed(2000, 1000);
                    }
                }
            }
        };
        test.setOnClickListener(v ->

        {
            handler.sendEmptyMessage(2000);
        });

    }

    private boolean compare(String v1, String v2) {
        return v1.compareTo(v2) > 0;
    }

    public static boolean getCompare(String networkVersion, String localVersion) {
        String[] ResultNetworkVersion = networkVersion.split("\\.");
        String[] ResultLocalVersion = localVersion.split("\\.");
        if (Integer.parseInt(ResultNetworkVersion[0]) > Integer.parseInt(ResultLocalVersion[0])) {
            return true;
        } else if (Integer.parseInt(ResultNetworkVersion[0]) == Integer.parseInt(ResultLocalVersion[0])) {
            if (Integer.parseInt(ResultNetworkVersion[1]) > Integer.parseInt(ResultLocalVersion[1])) {
                return true;
            } else if (Integer.parseInt(ResultNetworkVersion[1]) == Integer.parseInt(ResultLocalVersion[1])) {
                if (Integer.parseInt(ResultNetworkVersion[2]) > Integer.parseInt(ResultLocalVersion[2])) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mThread.quit();
        mThread = null;
    }
}