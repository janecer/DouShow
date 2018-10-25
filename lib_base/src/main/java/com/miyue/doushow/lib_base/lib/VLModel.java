package com.miyue.doushow.lib_base.lib;

public class VLModel
{
	private VLModelManager mModelManager;
	
	public VLModel()
	{
		//mModelManager = VLApplication.instance().getModelManager();
	}
	
	protected void onCreate()
	{
	}
	
	protected void onAfterCreate()
	{
	}
	
	protected <T> T getModel(Class<T> modelClass)
	{
		return mModelManager.getModel(modelClass);
	}
	

}
