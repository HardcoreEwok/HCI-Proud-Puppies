//******************************************************************************
// Copyright (C) 2016 University of Oklahoma Board of Trustees.
//******************************************************************************
// Last modified: Mon Feb 29 23:36:04 2016 by Chris Weaver
//******************************************************************************
// Major Modification History:
//
// 20160225 [weaver]:	Original file.
//
//******************************************************************************
// Notes:
//
//******************************************************************************

package edu.ou.cs.cg.homework;

//import java.lang.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

//******************************************************************************

/**
 * The <CODE>KeyHandler</CODE> class.<P>
 *
 * @author Austin Echols
 * @version %I%, %G%
 */
public final class KeyHandler extends KeyAdapter
{
	//**********************************************************************
	// Private Members
	//**********************************************************************

	// State (internal) variables
	private final View	view;

	//**********************************************************************
	// Constructors and Finalizer
	//**********************************************************************

	public KeyHandler(View view)
	{
		this.view = view;

		Component	component = view.getComponent();

		component.addKeyListener(this);
	}

	//**********************************************************************
	// Override Methods (KeyListener)
	//**********************************************************************

	public void		keyPressed(KeyEvent e)
	{
		 switch (e.getKeyCode())
		 {
			
			case KeyEvent.VK_1:
				view.setShape1(true);
				view.setShape2(false);
				view.setShape3(false);
				view.setShape4(false);
				view.isChanged();
				break;
			case KeyEvent.VK_2:
				view.setShape1(false);
				view.setShape2(true);
				view.setShape3(false);
				view.setShape4(false);
				view.isChanged();
				break;
			case KeyEvent.VK_3:
				view.setShape1(false);
				view.setShape2(false);
				view.setShape3(true);
				view.setShape4(false);
				view.isChanged();
				break;
			case KeyEvent.VK_4:
				view.setShape1(false);
				view.setShape2(false);
				view.setShape3(false);
				view.setShape4(true);
				view.isChanged();
				break;
			case KeyEvent.VK_LEFT:
				view.decSpeed();
				break;
			case KeyEvent.VK_RIGHT:
				view.upSpeed();
				break;
		}
	}
}

//******************************************************************************
