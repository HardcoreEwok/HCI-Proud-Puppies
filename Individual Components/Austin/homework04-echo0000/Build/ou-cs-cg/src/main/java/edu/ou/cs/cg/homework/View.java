//******************************************************************************
// Copyright (C) 2016 University of Oklahoma Board of Trustees.
//******************************************************************************
// Last modified: Tue Mar  1 18:52:22 2016 by Chris Weaver
//******************************************************************************
// Major Modification History:
//
// 20160209 [weaver]:	Original file.
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
import java.util.*;
import javax.swing.*;
import javax.media.opengl.*;
import javax.media.opengl.awt.*;
import javax.media.opengl.glu.*;
import com.jogamp.opengl.util.*;
import java.util.Random; 

import javax.media.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.gl2.GLUT;

//******************************************************************************

/**
 * The <CODE>homework04</CODE> class.<P>
 * 
 * This code implements the 1st, 2nd, and 3rd parts of the homework. It also implements the first 3 bonuses, with slight variation
 * The Bouncing is calculated by the reflection equation v-2(v dot n)n
 * The collision detection is calculated by the parametric equation of a line and using p-hit and t-hit
 * Because of the above methods the animation can be done with any convex polygon.
 *
 *The left and right arrow keys change the velocity of the point by just multiplying the vector by either .9 or 1.1
 *There are 4 different container shapes. The square, hexagon, 32-gon circle, and the weird polygon. Collision and bouncing works with all shapes
 *
 *I did not do part 4 or 5
 *
 *Each bounce changes the color of the shapes randomly
 *Only on shape 4 will the impulse factor occur. With each bounce the movement vector will increase by a random number between .1 and 2
 *only shape 2 will have the disappear animation. After each bounce the alpha is change by a fraction and will eventually disappear at 20
 *bounces
 *
 * @author  THE AUSTIN ECHOLS MUHAHAHAHAHAHAH
 * @version %I%, %G%
 */
public final class View
implements GLEventListener
{
	//**********************************************************************
	// Public Class Members
	//**********************************************************************

	public static final int		DEFAULT_FRAMES_PER_SECOND = 60;

	public static final GLU		GLU = new GLU();
	public static final GLUT	GLUT = new GLUT();
	public static final Random	RANDOM = new Random();

	// State (internal) variables

	//**********************************************************************
	// Private Members
	//**********************************************************************

	// State (internal) variables
	private final GLJPanel			canvas;
	private int						w;				// Canvas width
	private int						h;				// Canvas height
	private int						k = 0;		// Just an animation counter

	private int rectWidth = (int)(1280 * .9);
	private int rectHeight = (int)(720 * .9);
	private Random r;

	private Point2D.Double vec;
	private Point2D.Double moveVec;

	double initVelX = 0; 
	double initVelY = 0;

	private Point2D.Double[] pointArray = new Point2D.Double[10]; 
	private ArrayList<Point2D.Double> weirdPoly; 

	private ArrayList<Point2D.Double> rectangle;

	private ArrayList<Point2D.Double> hexagon;

	private ArrayList<Point2D.Double> circle;

	private Point2D.Double endPointVec;

	private boolean shape1 = true;
	private boolean shape2 = false;
	private boolean shape3 = false;
	private boolean shape4 = false;

	int red = 89;
	int green = 253;
	int blue = 60;
	boolean change = false;
	int hitCount = 0;

	private double posX = 640;
	private double posY = 360;

	private Point2D.Double currentPoint = new Point2D.Double(posX, posY);


	private Point2D.Double			origin;	// Current origin coordinates

	private final KeyHandler		keyHandler;
	private final MouseHandler		mouseHandler;

	private final FPSAnimator		animator;

	//private TextRenderer			renderer;

	//**********************************************************************
	// Constructors and Finalizer
	//**********************************************************************


	Point2D.Double p1 = new Point2D.Double(0,0);
	Point2D.Double p2 = new Point2D.Double(0,0);
	Point2D.Double p3 = new Point2D.Double(0,0);
	Point2D.Double p4 = new Point2D.Double(0,0);


	public View(GLJPanel canvas)
	{
		this.canvas = canvas;

		origin = new Point2D.Double(0.0, 0.0);
		r = new Random();

		//creates a random point within the square
		initVelX = r.nextInt(1216-64) + 64;
		initVelY = r.nextInt(684-36) + 36;

		//calculate the first random vector 
		endPointVec = new Point2D.Double(initVelX, initVelY);
		vec = calcVec(currentPoint, endPointVec);

		moveVec = calcUnitVec(vec);

		//creates the weird poly
		pointArray[0] = new Point2D.Double(-500, 100);
		pointArray[1] = new Point2D.Double(-400, 200);
		pointArray[2] = new Point2D.Double(-200, 300);
		pointArray[3] = new Point2D.Double(-100, 325);
		pointArray[4] = new Point2D.Double(100, 275);
		pointArray[5] = new Point2D.Double(400, -150);
		pointArray[6] = new Point2D.Double(400, -250);
		pointArray[7] = new Point2D.Double(300, -325);
		pointArray[8] = new Point2D.Double(-200, -275);
		pointArray[9] = new Point2D.Double(-480, -50);


		canvas.setFocusTraversalKeysEnabled(false);
		canvas.setPreferredSize(new Dimension(1280, 720));

		// Initialize rendering
		canvas.addGLEventListener(this);
		animator = new FPSAnimator(canvas, DEFAULT_FRAMES_PER_SECOND);
		animator.start();

		// Initialize interaction
		keyHandler = new KeyHandler(this);
		mouseHandler = new MouseHandler(this);
	}

	//**********************************************************************
	// Getters and Setters
	//**********************************************************************

	public int	getWidth()
	{
		return w;
	}

	public int	getHeight()
	{
		return h;
	}

	//these determine what shape is picked
	public void setShape1(boolean b)
	{
		this.shape1 = b;
	}

	public void setShape2(boolean b)
	{
		this.shape2 = b;
	}

	public void setShape3(boolean b)
	{
		this.shape3 = b;
	}

	public void setShape4(boolean b)
	{
		this.shape4 = b;
	}

	/*
	 * increases the velocity by 1.1
	 */
	public void upSpeed()
	{
		moveVec.setLocation(moveVec.getX() * 1.1, moveVec.getY() * 1.1);

		//this.speed *= 1.1;
	}

	/*
	 * decreses the velocity by .9
	 */
	public void decSpeed()
	{
		moveVec.setLocation(moveVec.getX() * .9, moveVec.getY() * .9);
		//this.speed *= 0.9;
	}


	//**********************************************************************
	// Public Methods
	//**********************************************************************

	public Component	getComponent()
	{
		return (Component)canvas;
	}

	//**********************************************************************
	// Override Methods (GLEventListener)
	//**********************************************************************

	public void		init(GLAutoDrawable drawable)
	{
		w = drawable.getWidth();
		h = drawable.getHeight();
	}

	public void		dispose(GLAutoDrawable drawable)
	{
	}

	public void		display(GLAutoDrawable drawable)
	{
		update();
		render(drawable);
	}

	public void		reshape(GLAutoDrawable drawable, int x, int y, int w, int h)
	{
		this.w = w;
		this.h = h;
	}

	//**********************************************************************
	// Private Methods (Rendering)
	//**********************************************************************

	private void	update()
	{
		k++;									// Counters are useful, right?
	}

	//sets the color of shapes
	private void changeShapeColor(int r, int g, int b)
	{
		this.red = r;
		this.green = g;
		this.blue = b;
	}

	//determines if we have changed shapes
	public void isChanged()
	{
		change = true;
	}
	
	
	/*
	 * creates a circle, which is a small 20-gon circle and sets the color and alpha value based on what the hit counter is
	 * Only allows the Alpha change in shape 2
	 * 
	 */
	private void createBall(GL2 gl)
	{
		if(!change)
		{
			if(shape2)
			{
				setColor(gl, 0, 0, 0, 255 - (13 * hitCount));
			}
			else
			{
				setColor(gl, 0, 0, 0, 255);
			}
			
			drawGon(gl, 20, currentPoint.getX(), currentPoint.getY(), 10);
		}
		else
		{
			setColor(gl, 0, 0, 0, 255);
			currentPoint.setLocation(640, 360);
			drawGon(gl, 20, currentPoint.getX(), currentPoint.getY(), 10);
			change = false;
			hitCount = 0;
		}
	}


	/*
	 * renders which shapes to draw and what to do with them
	 */
	private void	render(GLAutoDrawable drawable)
	{
		GL2		gl = drawable.getGL().getGL2();

		gl.glClear(GL.GL_COLOR_BUFFER_BIT);	// Clear the buffer
		gl.glEnable(GL.GL_BLEND);
		gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
		setScreenProjection(gl);

		
		//changes what to render and what animation is going to happen based off what shape is selected
		if(shape1)
		{
			setColor(gl, red, green, blue);
			//gives us the points for the shape
			rectangle = fillRect(gl, (int)(getWidth() / 2) - (int)(rectWidth / 2), 
					(int)(getHeight() / 2) - (int)(rectHeight / 2), rectWidth , rectHeight);
			//need to add the first point so we can loop around for collision detection
			rectangle.add(rectangle.get(0));
			createBall(gl);
			movePoint(rectangle);

		}
		else if(shape2)
		{	
			setColor(gl, red, green, blue);
			//gives us the points for the shape
			hexagon = drawGon(gl, 6, getWidth() / 2, getHeight() / 2, (int)(rectHeight / 2));
			createBall(gl);
			movePoint(hexagon);

		}
		else if(shape3)
		{
			setColor(gl, red, green, blue);
			//gives us the points for the shape
			circle = drawGon(gl, 32, getWidth() / 2, getHeight() / 2, (int)(rectHeight / 2));
			createBall(gl);
			movePoint(circle);
		}
		else if(shape4)
		{	
			setColor(gl, red, green, blue);
			//gives us the points for the shape
			weirdPoly = fillPoly(gl,(int)(getWidth() / 2) , (int)(getHeight() / 2), pointArray);
			//need to add the first point so we can loop around for collision detection
			weirdPoly.add(weirdPoly.get(0));
			createBall(gl);
			movePoint(weirdPoly);
		}
		else
		{
			//Do Nothing??
		}

	}

	//**********************************************************************
	// Private Methods (Scene)
	//**********************************************************************

	/*
	 * calculates the vector given 2 points
	 */
	private Point2D.Double calcVec(Point2D.Double a, Point2D.Double b)
	{
		return new Point2D.Double((b.getX() - a.getX()), (b.getY() - a.getY()));
	}

	/*
	 * calculates the unit vector given a vector
	 */
	private Point2D.Double calcUnitVec(Point2D.Double vec)
	{
		double mag = Math.sqrt((vec.getX() * vec.getX()) + (vec.getY() * vec.getY()));

		return new Point2D.Double((vec.getX() / mag), (vec.getY() / mag));

	}

	/*
	 * calculates the normal vector given the points of the vectors
	 */
	private Point2D.Double calcNormVec(double x1, double x2, double y1, double y2)
	{

		return new Point2D.Double(-(y2 - y1), (x2 - x1));
	}

	/*
	 * calculates the dot product of 2 vectors
	 */
	private double calcDotProduct(Point2D.Double a, Point2D.Double b)
	{	
		return (a.getX() * b.getX()) + (a.getY() * b.getY());
	}

	/*
	 * Calculates the reflection vector
	 */
	private Point2D.Double calcReflection(Point2D.Double normalVec, Point2D.Double velocity)
	{

		double dotProd = calcDotProduct(normalVec, velocity);
		Point2D.Double reflectVec = new Point2D.Double(velocity.getX() - 2 * dotProd * normalVec.getX(), 
				velocity.getY() - 2 * dotProd * normalVec.getY());
		return reflectVec;
	}

	/*
	 * Point q is some point on the wall, which will always be the beginning point of the wall vector
	 * Point s will be the end point of the wall vector, need this to calculate the norm vector of the wall
	 * Point r will be the current point
	 * Vector vel will be the velocity vector starting from point r
	 * 
	 * @return boolean that tells if the current point is past the wall
	 */
	private boolean pastLine(Point2D.Double q, Point2D.Double s, Point2D.Double r, Point2D.Double vel)
	{	
		Point2D.Double normVec = calcNormVec(q.getX(), s.getX(), q.getY(), s.getY());

		double top = calcDotProduct(normVec, new Point2D.Double(q.getX() - r.getX(), q.getY() - r.getY()));
		double bottom = calcDotProduct(normVec, vel);

		//if bottom is 0 they will never touch and it will avoid dividing by 0
		if(bottom == 0 || bottom == -0)
		{
			return false;
		}

		double tHit = top / bottom;

		if(0 <= tHit && tHit <= 1)
		{
			return true;
		}

		return false;
	}

	double randNum = 0;
	
	/*
	 * Takes in any container and creates the reflection vector based on if a collision happened
	 * It also changes the containers color randomly
	 * Only on shape 4 does it change the velocity of the point based on what sides are hit. 
	 * 
	 * @return
	 */
	private void movePoint(ArrayList<Point2D.Double> polygon)
	{	
		for(int i = 0; i < polygon.size(); ++i)
		{
			int a = i + 1;
			
			//makes sure we dont try and compare an index that is not there
			if(a > polygon.size() - 1)
			{
				break;
			}
			if(pastLine(polygon.get(i), polygon.get(a), currentPoint, moveVec))
			{	
				//counts the number of hits
				++hitCount;
				//create the norm vector of the wall
				Point2D.Double normalVec = calcNormVec(polygon.get(i).getX(), polygon.get(a).getX(), 
						polygon.get(i).getY(), polygon.get(a).getY());
				//gets the unit vec
				normalVec = calcUnitVec(normalVec);
				//get the reflection vec
				Point2D.Double reflectionVec = calcReflection(normalVec, moveVec);
				//set the old moving vec to the new refelction vec
				moveVec = reflectionVec;
				//only allow shape 4 to randomly change the speed after collision
				if(shape4)
				{
					randNum = r.nextInt(200 + 1 - 1) + 1;
					randNum = randNum / 100;
					//set the movement vecs components
					moveVec.setLocation(moveVec.getX() * randNum, moveVec.getY() * randNum);
				}
				//change the color randomly on a bounce
				changeShapeColor(r.nextInt(255-0) + 0,r.nextInt(255-0), r.nextInt(255-0));
				break;
			}
		}
		//set the current location of the point;
		currentPoint.setLocation(currentPoint.getX() + moveVec.getX(), currentPoint.getY() + moveVec.getY());
	}

	private void	setScreenProjection(GL2 gl)
	{
		GLU		glu = new GLU();

		gl.glMatrixMode(GL2.GL_PROJECTION);			// Prepare for matrix xform
		gl.glLoadIdentity();						// Set to identity matrix
		glu.gluOrtho2D(0.0f, 1280.0f, 0.0f, 720.0f);// 2D translate and scale
	}

	private ArrayList<Point2D.Double>	drawGon(GL2 gl, int sides, double cx, double cy, int r)
	{
		ArrayList<Point2D.Double> points = new ArrayList<Point2D.Double>();
		double ang =  2.0 * Math.PI / sides;
		double	theta = 0.20 * ang;
		gl.glBegin(GL2.GL_POLYGON);

		for (int i=0; i<sides + 1; i++)			// n sides
		{
			gl.glVertex2d(cx + r * Math.cos(theta), cy + r * Math.sin(theta));
			points.add(new Point2D.Double(cx + r * Math.cos(theta), cy + r * Math.sin(theta)));			
			theta += ang;
		}

		gl.glEnd();

		return points;
	}

	//**********************************************************************
	// Private Methods (Utility Functions)
	//**********************************************************************

	private void	setColor(GL2 gl, int r, int g, int b, int a)
	{
		gl.glColor4f(r / 255.0f, g / 255.0f, b / 255.0f, a / 255.0f);
	}

	private void	setColor(GL2 gl, int r, int g, int b)
	{
		setColor(gl, r, g, b, 255);
	}

	private ArrayList<Point2D.Double>	fillRect(GL2 gl, int x, int y, int w, int h)
	{
		ArrayList<Point2D.Double> points = new ArrayList<Point2D.Double>();


		gl.glBegin(GL2.GL_POLYGON);

		gl.glVertex2i(x+0, y+0);
		points.add(new Point2D.Double(x+0, y+0));

		gl.glVertex2i(x+0, y+h);
		points.add(new Point2D.Double(x+0, y+h));

		gl.glVertex2i(x+w, y+h);
		points.add(new Point2D.Double(x+w, y+h));

		gl.glVertex2i(x+w, y+0);
		points.add(new Point2D.Double(x+w, y+0));

		gl.glEnd();

		return points;

	}
	
	private ArrayList<Point2D.Double>	fillPoly(GL2 gl, int startx, int starty, Point2D.Double[] offsets)
	{
		ArrayList<Point2D.Double> points = new ArrayList<Point2D.Double>();

		gl.glBegin(GL2.GL_POLYGON);

		for (int i=0; i<offsets.length; i++)
		{
			gl.glVertex2d(startx + offsets[i].x, starty + offsets[i].y);
			points.add(new Point2D.Double(startx + offsets[i].x, starty + offsets[i].y));
		}

		gl.glEnd();

		return points;
	}

}

//******************************************************************************
