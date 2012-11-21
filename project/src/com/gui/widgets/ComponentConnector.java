package com.gui.widgets;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/** Connect an input-component to an output-function, 
 * so that whenever the input-data is changed, changes get written.
 * 
 * @author Gruppe 10
 *
 */
public class ComponentConnector
{
	private Object t_object,
				   f_object = null;
	private Method f_read, f_write;
	private Object componentOrigData,
				   componentCurrentData;
	
	
	
	/** Constructor.. Initialize
	 * 
	 * @param t_object object that holds the input-component
	 * @param f_retrieveVal	method used to retrieve input
	 * @param writeFunc	method used to write data from input
	 */
	public <T> ComponentConnector(T t_object, Method f_retrieveVal, Method writeFunc)
	{
		this.t_object = t_object;
		this.f_write = writeFunc;
		this.f_read = f_retrieveVal;
		
		this.componentOrigData = retrieveObjectData(this.t_object);	
	}
	
	public <T, F> ComponentConnector(T t_object, Method f_retrieveVal, F f_object, Method writeFunc)
	{
		this.t_object = t_object;
		this.f_object = f_object;
		this.f_write = writeFunc;
		this.f_read = f_retrieveVal;
		
		this.componentOrigData = retrieveObjectData(this.t_object);	
	}
	
	public void updateOrigdata()
	{
		this.componentOrigData = retrieveObjectData(this.t_object);
	}
	
	/** Get input-data from given input-components and methods
	 * 
	 * @param o the object to obtain data from
	 * @return data from the input-component
	 */
	private Object retrieveObjectData(Object o)
	{
		try
		{
//			Object a[] = new Object[1]; a[0] = null;
			Object oOutput =  this.f_read.invoke(t_object, null);
						
			return oOutput;
		}
		
		 /* In case of an error, write out a message to cmdline and return null.
		  * After that, usage of THIS is still O.K. Userdata may not be written, but it isn't considered a catastrophe. 
		  */
		catch(Throwable t)
		{
			System.out.println("[did not successfully recieve original data]");
			return null;
		}
	}
	
	/** Check whether or not any changes has been made to the input-component
	 * 
	 * @return true if a change is made, false if not
	 */
	private boolean changeMade()
	{
		this.componentCurrentData = retrieveObjectData(this.t_object);
		
		return (componentCurrentData == null || componentCurrentData.equals(componentOrigData) == false);
	}
	
	/** Write changes made to the input-component
	 */
	public void writeChanges()
	{
		if(changeMade() == true)
		{
			try
			{
				/* Given a change is made, write data */
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

/* EOF */