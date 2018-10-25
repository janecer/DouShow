package com.miyue.doushow.lib_base.lib;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.miyue.doushow.lib_base.lib.app.SwipeBackActivity;


public class VLActivity extends SwipeBackActivity
{
	public enum ActivityState
	{
		ActivityInited,
		ActivityCreated,
		ActivityStarted,
		ActivityResumed,
		ActivityPaused,
		ActivityStopped,
		ActivityRestarted,
		ActivityDestroyed,
	}
	
	private String mClassName;
	private ActivityState mActivityState;
	private VLModelManager mModelManager;

	public ActivityState getActivityState()
	{
		return mActivityState;
	}
	
	public VLActivity()
	{	
		mActivityState = ActivityState.ActivityInited;
		mModelManager = VLApplication.instance().getModelManager();
        mClassName =getClass().getName();
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		VLApplication app = VLApplication.instance();
		app.mActivities.add(this);
		if(app.mCurrentActivity==null) app.mCurrentActivity = this;
		mActivityState = ActivityState.ActivityCreated;
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
	}
	
	protected boolean isFinished() 
	{
		return mActivityState == ActivityState.ActivityDestroyed;
	}

	protected boolean isRunning() 
	{
		if ( mActivityState == ActivityState.ActivityStarted) return true;
		if ( mActivityState == ActivityState.ActivityResumed) return true;
		if ( mActivityState == ActivityState.ActivityPaused) return true;
		return false;
	}
	
	public <T> T getModel(Class<T> modelClass)
	{
		return mModelManager.getModel(modelClass);
	}
	
	public void showKeyboard()
	{
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
	}
	
	public void hideKeyboard()
	{
		InputMethodManager inputManager = (InputMethodManager)this.getSystemService(Context.INPUT_METHOD_SERVICE);
		inputManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
	}
	

	@Override
	protected void onStart()
	{
		super.onStart();
		mActivityState = ActivityState.ActivityStarted;
	}
	
	@Override
	protected void onResume() 
	{
		super.onResume();

		mActivityState = ActivityState.ActivityResumed;
		VLApplication app = VLApplication.instance();
		app.mCurrentActivity = this;
	}
	
	@Override
	protected void onPause() 
	{
		super.onPause();
		mActivityState = ActivityState.ActivityPaused;
	}
	
	@Override
	protected void onStop()
	{
		super.onStop();
		mActivityState = ActivityState.ActivityStopped;
	}
	
	@Override
	protected void onDestroy()
	{
		super.onDestroy();

		VLApplication app = VLApplication.instance();
		app.mActivities.remove(this);
		if(app.mCurrentActivity==this)
		{
			if(app.mActivities.size()>0)
				app.mCurrentActivity = app.mActivities.get(app.mActivities.size()-1);
			else
				app.mCurrentActivity = null;
		}
		mActivityState = ActivityState.ActivityDestroyed;
	}
	
	@Override
	protected void onRestart() 
	{
		super.onRestart();
		mActivityState = ActivityState.ActivityRestarted;
	}
	

	protected void restart()
	{
		Intent intent = getIntent();
		overridePendingTransition(0, 0);
		intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		finish();
		overridePendingTransition(0, 0);
		startActivity(intent);
	}
	


	protected void startActivity(Class<?> cls)
	{
		Intent intent = new Intent(this, cls);
		startActivity(intent);
	}

}
