package com.gui.widgets;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ComponentConnector
{
	private Object t_object;
	private Method f_read, f_write;
	private Object componentOrigData;
	private Object componentCurrentData;
	
	public <T> ComponentConnector(T t_object, Method f_retrieveVal, Method writeFunc)
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
			System.out.println("[did not successfully recieve original data]");
			return null;
		}
	}
	
	private boolean changeMade()
	{
		this.componentCurrentData = retrieveObjectData(this.t_object);
		
		return (componentCurrentData == null || componentCurrentData.equals(componentOrigData) == false);
	}
	
	public void writeChanges()
	{
		if(changeMade() == true)
		{
			try
			{
				Object[] a = new Object[1];
							
				a[0] = this.componentCurrentData;				
				this.f_write.invoke(null, a);
			}
			
			catch(InvocationTargetException| IllegalAccessException | IllegalArgumentException e)
			{
				System.out.println("unable to write user-data [" + e.toString() + "]");				
			}
		}
	}
}
