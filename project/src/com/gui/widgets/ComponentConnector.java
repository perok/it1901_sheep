package com.gui.widgets;

import java.lang.reflect.Method;

public class ComponentConnector<T>
{
	private T t_object;
	private Method f_read, f_write;
	private Object componentOrigData;
	
	public ComponentConnector(T t_object, Method f_retrieveVal, Method writeFunc)
	{
		this.t_object = t_object;
		this.f_write = writeFunc;
		this.f_read = f_retrieveVal;
		
		this.componentOrigData = retrieveObjectData(this.t_object);	
	}
	
	private Object retrieveObjectData(Object o)
	{
		try
		{
			return o.getClass().getMethod(this.f_read.getName(), this.f_read.getParameterTypes()).invoke(o);
		}
		
		catch(Throwable t)
		{
			return null;
		}
	}
	
	public boolean changeMade()
	{
		Object componentCurrentData = retrieveObjectData(this.t_object);
		
		//return (componentCurrentData == null || componentCurrentData.equals(this.t_object) == false);
		return (componentCurrentData == null || componentCurrentData.equals(componentOrigData) == false);
	}
	
	public void writeChange()
	{
		
	}
}
