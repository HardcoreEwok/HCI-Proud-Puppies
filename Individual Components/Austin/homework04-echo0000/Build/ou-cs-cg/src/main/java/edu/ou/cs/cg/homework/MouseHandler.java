//******************************************************************************
// Copyright (C) 2016 University of Oklahoma Board of Trustees.
//******************************************************************************
// Last modified: Mon Feb 29 23:46:15 2016 by Chris Weaver
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
import java.util.ArrayList;

//******************************************************************************

/**
 * The <CODE>MouseHandler</CODE> class.<P>
 *
 * @author  Chris Weaver
 * @version %I%, %G%
 */
public final class MouseHandler extends MouseAdapter
{
	//**********************************************************************
	// Private Members
	//**********************************************************************

	// State (internal) variables
	private final View	view;
	private boolean flag = false;
	private ArrayList<Point> k = new ArrayList<Point>();
	
		

	//**********************************************************************
	// Constructors and Finalizer
	//**********************************************************************

	public MouseHandler(View view)
	{
		this.view = view;

		Component	component = view.getComponent();

		component.addMouseListener(this);
		component.addMouseMotionListener(this);
		component.addMouseWheelListener(this);
	}

	// //**********************************************************************
	// // Override Methods (MouseListener)
	// //**********************************************************************

	 public void		mouseClicked(MouseEvent e)
	 {
		// boolean[] stars = view.getStarArray();
		
		
		// //Point2D.Double	v = calcCoordinatesInView(e.getX(), e.getY());
		
		// Point2D.Double v = new Point2D.Double(e.getX(), e.getY());
		
		// for(int i = 0; i < 5; ++i)
		// {
			// if(stars[i] == true && i == 0)
			// {
				// view.setP1(v);
				// flag = true;
				// break;
			// }
			// else if(stars[i] == true && i == 1)
			// {
				// view.setP2(v);
				// flag = true;
				// break;
			// }
			// else if(stars[i] == true && i == 2)
			// {
				// view.setP3(v);
				// flag = true;
				// break;
			// }
			// else if(stars[i] == true && i == 3)
			// {
				// view.setP4(v);
				// flag = true;
				// break;
			// }
			// else if(stars[i] == true && i == 4)
			// {
				// view.setP5(v);
				// flag = true;
				// break;
			// }
			// else
			// {
				// //do nothing
				// flag = false;
			// }
		// }
		
	 }

	// public void		mouseEntered(MouseEvent e)
	// {
		// Point2D.Double	v = calcCoordinatesInView(e.getX(), e.getY());

		// view.setCursor(v);
	// }

	// public void		mouseExited(MouseEvent e)
	// {
		// view.setCursor(null);
	// }

	// public void		mousePressed(MouseEvent e)
	// {
	// }

	// public void		mouseReleased(MouseEvent e)
	// {

		
	// }

	// //**********************************************************************
	// // Override Methods (MouseMotionListener)
	// //**********************************************************************

	public void		mouseDragged(MouseEvent e)
	{
		// Point2D.Double v = new Point2D.Double(e.getX(), e.getY());
		
		// if(!flag)
		// {
			// k.add(new Point(e.getX(), 720 - e.getY()));
			// view.setKiteCenter(v);
			// view.setKiteLine(k);
			
		// }
	}

	// public void		mouseMoved(MouseEvent e)
	// {
			// Point2D.Double	v = calcCoordinatesInView(e.getX(), e.getY());
			// System.out.println("MOVE X: "+ e.getX() + " : " + "MOVE Y: " + e.getY());

		// // view.setCursor(v);
	// }

	// //**********************************************************************
	// // Override Methods (MouseWheelListener)
	// //**********************************************************************

	// public void		mouseWheelMoved(MouseWheelEvent e)
	// {
	// }

	// //**********************************************************************
	// // Private Methods
	// //**********************************************************************

	// private Point2D.Double	calcCoordinatesInView(int sx, int sy)
	// {
		// int				w = view.getWidth();
		// int				h = view.getHeight();
		// Point2D.Double	p = view.getOrigin();
		// double			vx = p.x + (sx * 2.0) / w - 1.0;
		// double			vy = p.y - (sy * 2.0) / h + 1.0;
		
		// //System.out.println(vx + " : " + vy);

		// return new Point2D.Double(vx, vy);
	// }
}

//******************************************************************************
