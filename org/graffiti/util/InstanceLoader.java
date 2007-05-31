//==============================================================================
//
//   InstanceLoader.java
//
//   Copyright (c) 2001-2004 Gravisto Team, University of Passau
//
//==============================================================================
// $Id: InstanceLoader.java,v 1.1 2007/05/31 12:56:03 klukas Exp $

package org.graffiti.util;

import java.lang.reflect.InvocationTargetException;

/**
 * Represents an instance loader, which can be used to instanciate a class with
 * the given name.
 *
 * @version $Revision: 1.1 $
 */
public class InstanceLoader
{
    //~ Methods ================================================================

    /**
     * Returns a new instance of the specified class.
     *
     * @param theClass the class to instanciate.
     *
     * @return DOCUMENT ME!
     *
     * @throws InstanceCreationException DOCUMENT ME!
     */
    public static Object createInstance(Class theClass)
        throws InstanceCreationException
    {
        try
        {
            return theClass.newInstance();
        }
        catch(InstantiationException ie)
        {
            throw new InstanceCreationException(ie);
        }
        catch(IllegalAccessException iae)
        {
            throw new InstanceCreationException(iae);
        }
    }

    /**
     * Returns a new instance of the specified class.
     *
     * @param name the name of the class to instanciate.
     *
     * @return DOCUMENT ME!
     *
     * @throws InstanceCreationException DOCUMENT ME!
     */
    public static Object createInstance(String name)
        throws InstanceCreationException
    {
        try
        {
            Class c = InstanceLoader.class.getClassLoader().loadClass(name);

            return c.newInstance();
        }
        catch(NullPointerException npe)
        {
            throw new InstanceCreationException(npe);
        }
        catch(ClassNotFoundException cnfe)
        {
            throw new InstanceCreationException(cnfe);
        }
        catch(InstantiationException ie)
        {
            throw new InstanceCreationException(ie);
        }
        catch(IllegalAccessException iae)
        {
            throw new InstanceCreationException(iae);
        }
        catch(Exception e) {
        	System.err.println("Unhandled (ignored) exception: "+e.getLocalizedMessage());
        	e.printStackTrace();
        	throw new InstanceCreationException(e);
        }
    }

    /**
     * Returns a new instance of the specified class. Uses a constructor taking
     * one argument.
     *
     * @param name the name of the class to instanciate.
     * @param param param
     *
     * @return DOCUMENT ME!
     *
     * @throws InstanceCreationException DOCUMENT ME!
     */
    public static Object createInstance(String name, Object param)
        throws InstanceCreationException
    {
        try
        {
            return Class.forName(name)
                        .getConstructor(new Class[] { param.getClass() })
                        .newInstance(new Object[] { param });
        }
        catch(InvocationTargetException ite)
        {
            throw new InstanceCreationException(ite);
        }
        catch(NoSuchMethodException nsme)
        {
            throw new InstanceCreationException(nsme);
        }
        catch(NullPointerException npe)
        {
            throw new InstanceCreationException(npe);
        }
        catch(ClassNotFoundException cnfe)
        {
            throw new InstanceCreationException(cnfe);
        }
        catch(InstantiationException ie)
        {
            throw new InstanceCreationException(ie);
        }
        catch(IllegalAccessException iae)
        {
            throw new InstanceCreationException(iae);
        }
    }

    /**
     * Returns a new instance of the specified class. Uses a constructor taking
     * one argument.
     *
     * @param theClass the name of the class to instanciate.
     * @param paramClassname DOCUMENT ME!
     * @param param param
     *
     * @return DOCUMENT ME!
     *
     * @throws InstanceCreationException DOCUMENT ME!
     */
    public static Object createInstance(Class theClass, String paramClassname,
        Object param)
        throws InstanceCreationException
    {
        try
        {
            return theClass.getConstructor(new Class[]
                {
                    Class.forName(paramClassname)
                }).newInstance(new Object[] { param });
        }
        catch(InvocationTargetException ite)
        {
            throw new InstanceCreationException(ite);
        }
        catch(NoSuchMethodException nsme)
        {
            throw new InstanceCreationException(nsme);
        }
        catch(NullPointerException npe)
        {
            throw new InstanceCreationException(npe);
        }
        catch(ClassNotFoundException cnfe)
        {
            throw new InstanceCreationException(cnfe);
        }
        catch(InstantiationException ie)
        {
            throw new InstanceCreationException(ie);
        }
        catch(IllegalAccessException iae)
        {
            throw new InstanceCreationException(iae);
        }
    }

    /**
     * Returns a new instance of the specified class. Uses a constructor taking
     * one argument.
     *
     * @param theClass the name of the class to instanciate.
     * @param param param
     *
     * @return DOCUMENT ME!
     *
     * @throws InstanceCreationException DOCUMENT ME!
     */
    public static Object createInstance(Class theClass, Object param)
        throws InstanceCreationException
    {
        try
        {
            return theClass.getConstructor(new Class[] { param.getClass() })
                           .newInstance(new Object[] { param });
        }
        catch(InvocationTargetException ite)
        {
            throw new InstanceCreationException(ite);
        }
        catch(NoSuchMethodException nsme)
        {
            throw new InstanceCreationException(nsme);
        }
        catch(NullPointerException npe)
        {
            throw new InstanceCreationException(npe);
        }
        catch(InstantiationException ie)
        {
            throw new InstanceCreationException(ie);
        }
        catch(IllegalAccessException iae)
        {
            throw new InstanceCreationException(iae);
        }
    }
}

//------------------------------------------------------------------------------
//   end of file
//------------------------------------------------------------------------------
