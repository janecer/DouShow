package com.miyue.doushow.lib_base.lib;

import java.util.Iterator;

public class VLModelManager 
{
	private VLListMap<String, VLModel> mModels;
	
	public VLModelManager()
	{
		mModels = new VLListMap<String, VLModel>();
	}
	
	public void registerModel(Class<?> modelClass)
	{
		String className = modelClass.getName();
		VLModel model = VLUtils.classNew(className);
		mModels.addTail(className, model);
	}
	
	public void createAndInitModels()
	{
		for(Iterator<VLModel> it = mModels.values(); it.hasNext();)
		{
			VLModel model = it.next();
			model.onCreate();
		}
		for(Iterator<VLModel> it = mModels.values(); it.hasNext();)
		{
			VLModel model = it.next();
			model.onAfterCreate();
		}
	}

	@SuppressWarnings("unchecked")
	public <T> T getModel(Class<T> modelClass)
	{
		String className = modelClass.getName();
		VLModel model = mModels.get(className);
		return (T)model;
	}
	
}
