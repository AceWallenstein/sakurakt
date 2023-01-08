package com.blankspace.sakura.drag;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.blankspace.sakura.R;
import com.blankspace.sakura.book.blibook.BliBookActivity;

public class TimeFragment extends Fragment implements View.OnClickListener {

    private TextView textView;
    private Button plus;
    private Button minus;
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_time, container, false);
        textView = view.findViewById(R.id.text_view);
        plus = view.findViewById(R.id.plus);
        minus = view.findViewById(R.id.minus);
        recyclerView = view.findViewById(R.id.recycler_view);
        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        plus.setOnClickListener(this);
        minus.setOnClickListener(this);
    }

    private void start() {
        mCalHandler.post(mTicker);


    }

    private void pause() {
        mCalHandler.removeCallbacks(mTicker);

    }

    /**
     * 精确修正时间
     */
    private Handler mCalHandler = new Handler(Looper.getMainLooper());
    private final Runnable mTicker = new Runnable() {
        public void run() {
            long now = SystemClock.uptimeMillis();
            long next = now + (1000 - now % 1000);
            mCalHandler.postAtTime(mTicker,next);
            textView.setText(next + "");
        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.plus:
//                start();

                startActivity(new Intent(getActivity(), BliBookActivity.class));
                break;
            case R.id.minus:
                pause();
                break;
        }
    }
}