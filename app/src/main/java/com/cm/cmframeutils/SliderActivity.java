package com.cm.cmframeutils;

import android.os.Bundle;
import android.view.View;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import customview.ArrowloadingView;
import sliderView.BaseSliderView;
import sliderView.DescriptionAnimation;
import sliderView.SliderLayout;
import sliderView.TextSliderView;
import utlis.ToastUtils;

public class SliderActivity extends BaseActivity implements BaseSliderView.OnSliderClickListener {
    private SliderLayout slider_layout;
    private ArrowloadingView mArrowLoadingView;
    int count = 0;
    int progress = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slider);
        initView();
    }

    public void initView() {
        slider_layout= (SliderLayout) findViewById(R.id.slider_layout);
        mArrowLoadingView= (ArrowloadingView) findViewById(R.id.arrow_loading_button);
        mArrowLoadingView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((count % 2) == 0) {
                    mArrowLoadingView.startAnimating();
                    Timer timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    progress = progress + 1;
                                    mArrowLoadingView.setProgress(progress);
                                }
                            });
                        }
                    }, 800, 20);
                } else {
                    mArrowLoadingView.reset();
                }
                count++;
            }
        });

        HashMap<String,Integer> file_maps = new HashMap<String, Integer>();
        file_maps.put("Hannibal",R.drawable.slider_hannibal);
        file_maps.put("Big Bang Theory",R.drawable.slider_bigbang);
        file_maps.put("House of Cards", R.drawable.slider_house);
        file_maps.put("Game of Thrones", R.drawable.slider_game_of_thrones);

//        for (int i = 0; i <4 ; i++) {
//            final TextSliderView textSliderView = new TextSliderView(this);
//            textSliderView.description(i + "");
//            textSliderView.image("http://tvfiles.alphacoders.com/100/hdclearart-10.png");
//            textSliderView.setScaleType(BaseSliderView.ScaleType.Fit);
//            slider_layout.addSlider(textSliderView);
//        }

        for(String name : file_maps.keySet()){
            TextSliderView textSliderView = new TextSliderView(this);
            // initialize a SliderLayout
            textSliderView
                    .description(name)
                    .image(file_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);

            //add your extra information
            textSliderView.getBundle().putString("extra",name);

            slider_layout.addSlider(textSliderView);
        }
        slider_layout.setPresetTransformer(SliderLayout.Transformer.Stack);
        slider_layout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        slider_layout.setCustomAnimation(new DescriptionAnimation());
        slider_layout.setDuration(4000);
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
        ToastUtils.show(this,slider.getBundle().getString("extra"));
    }
}
