package com.parking.model;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class SelectParamsActivity extends Activity {
//	private static final String TAG = "SelectParamsActivity";
	
	private TextView mFloorsText;
	private TextView mCarsText;
	
	private SeekBar mFloorsBar;
	private SeekBar mCarsBar;
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.select_params);
        
        mFloorsText = (TextView)findViewById(R.id.floorsCount);
        mCarsText = (TextView)findViewById(R.id.carsOnFloorCount);
        
        mFloorsBar = (SeekBar)findViewById(R.id.floorsSeek);
        mCarsBar = (SeekBar)findViewById(R.id.carsSeek);
        
        mFloorsText.setText(getString(R.string.floorsCnt)+String.valueOf(mFloorsBar.getProgress()+1));
        mCarsText.setText(getString(R.string.carsCnt)+String.valueOf(3*(mCarsBar.getProgress()+1)));
        
        mFloorsBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
		        mFloorsText.setText(getString(R.string.floorsCnt)+String.valueOf(progress+1));				
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {

			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
			}});
        mCarsBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
		        mCarsText.setText(getString(R.string.carsCnt)+String.valueOf(3*(progress+1)));				
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {

			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
			}});
    }    
    
    public void onModeling(View view) {
    	Intent intent = new Intent(this, TouchRotateActivity.class);
    	intent.putExtra("floors", mFloorsBar.getProgress()+1);
    	intent.putExtra("cars", 3*(mCarsBar.getProgress()+1));
		startActivity(intent);	
		return;
    }

}