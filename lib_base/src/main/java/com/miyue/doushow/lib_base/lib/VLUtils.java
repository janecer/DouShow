package com.miyue.doushow.lib_base.lib;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.os.Looper;

@SuppressLint("UseSparseArrays")
public final class VLUtils
{
	public static final String V(String str)
	{
		if(str==null) return "";
		return str;
	}
	
	public static <T> T cast(Object obj, Class<T> type)
	{
		if(type.isInstance(obj)==false)
		{
			return null;
		}
		return type.cast(obj);
	}

	public static <T> T classNew(String className)
	{
		try
		{
			Class<?> cls = Class.forName(className);
			@SuppressWarnings("unchecked")
			T t = (T)cls.newInstance();
			return t;
		}
		catch(Exception e)
		{
			return null;
		}
	}
	
	public static <T> T classNew(Class<T> cls)
	{
		try
		{
			T t = cls.newInstance();
			return t;
		}
		catch(Exception e)
		{
			return null;
		}
	}
	

	public static final boolean threadInMain()
	{
		if(Thread.currentThread().getId()== Looper.getMainLooper().getThread().getId()) return true;
		return false;
	}

	public static final PackageInfo getSelfPackageInfo(Context context, int flags)
	{
		PackageInfo packageInfo = null;
		try
		{
			String packageName = context.getPackageName();
			packageInfo = context.getPackageManager().getPackageInfo(packageName, flags);
		}
		catch(Exception e){}
		return packageInfo;
	}
}
