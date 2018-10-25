package com.miyue.doushow.lib_base.lib;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.util.DisplayMetrics;
import android.view.WindowManager;


import java.util.ArrayList;
import java.util.List;

public class VLApplication extends Application
{
	protected static VLApplication sInstance;
	protected String mAppBundleName;
	protected String mAppName;
	protected int mAppVersionCode;
	protected String mAppVersionName;
	protected VLModelManager mModelManager;
	protected int mScreenWidth;
	protected int mScreenHeight;
	protected float mScreenDensity;

	protected List<VLActivity> mActivities;
	protected VLActivity mCurrentActivity;

	public static final VLApplication instance()
	{
		if(sInstance==null) throw new RuntimeException();
		return sInstance;
	}
	
	@Override
	public void onCreate() 
	{
		super.onCreate();
		sInstance = this;
		
		mAppBundleName = getPackageName();
		mAppName = mAppBundleName.lastIndexOf('.') <0 ? mAppBundleName : mAppBundleName.substring(mAppBundleName.lastIndexOf('.')+1);
		PackageInfo packageInfo = VLUtils.getSelfPackageInfo(this, 0);
		mAppVersionCode = packageInfo.versionCode;
		mAppVersionName = packageInfo.versionName;

		WindowManager windowManager = (WindowManager)getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics dm = new DisplayMetrics();
		windowManager.getDefaultDisplay().getMetrics(dm);
		mScreenWidth = dm.widthPixels;
		mScreenHeight = dm.heightPixels;
		mScreenDensity = dm.density;

		mActivities = new ArrayList<VLActivity>();
		mModelManager = new VLModelManager();
		onConfigModels();
		mModelManager.createAndInitModels();
	}
	

	protected void onConfigModels()
	{

	}
	
	public final String appBundleName()
	{
		if(mAppBundleName==null) throw new RuntimeException();
		return mAppBundleName;
	}
	
	public final String appName()
	{
		if(mAppName==null) throw new RuntimeException();
		return mAppName;
	}
	
	public final int appVersionCode()
	{
		if(mAppVersionCode==0) throw new RuntimeException();
		return mAppVersionCode;
	}
	
	public final String appVersionName()
	{
		if(mAppVersionName==null) throw new RuntimeException();
		return mAppVersionName;
	}
	
	public VLModelManager getModelManager()
	{
		return mModelManager;
	}
	
	public <T> T getModel(Class<T> modelClass)
	{
		return mModelManager.getModel(modelClass);
	}
	


	@SuppressLint("NewApi")
	@Override
	public void onTrimMemory(int level) 
	{
		super.onTrimMemory(level);
	}
	
	@Override
	public void onTerminate() 
	{
		super.onTerminate();
	}
	
	public int screenWidth()
	{
		return mScreenWidth;
	}
	
	public int screenHeight()
	{
		return mScreenHeight;
	}
	
	public float screenDensity()
	{
		return mScreenDensity;
	}
	
	public boolean isBackground() {
		ActivityManager activityManager = (ActivityManager) this
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningAppProcessInfo> appProcesses = activityManager
				.getRunningAppProcesses();
		for (RunningAppProcessInfo appProcess : appProcesses) {
			if (appProcess.processName.equals(this.getPackageName())) {
				if (appProcess.importance >= RunningAppProcessInfo.IMPORTANCE_SERVICE) {
					return true;
				} else {
					return false;
				}
			}
		}
		return false;
	}

	public void finishAllActivities(Activity exclude)
	{
		for(int i=mActivities.size()-1;i>=0;i--)
		{
			VLActivity activity = mActivities.get(i);
			if (activity == exclude)
			{
				continue;
			}
			activity.finish();
		}
		mActivities.clear();
	}

	public boolean isTopActivity(Activity act)
	{
		return (mActivities.indexOf(act) >= (mActivities.size()-1));
	}

	public int getActivityCount(){
		return mActivities.size();
	}
}
