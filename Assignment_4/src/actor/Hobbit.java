package actor;

import java.io.IOException;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import util.SingletonRandom;
import util.Input;
import army.Army;
public class Hobbit extends Actor{

	/**
	 * 
	 * 		Name: Calvin Li
	 *      Student Number: 040810357
	 *  
	 *
	 * 		Course: CST8284: Object Oriented Programming
	 *      Lab Section: 021
	 *      Lecture Section: 020
	 *      Lab Professor: Eric Torunski 
	 */
	/**
	 * 
	 * The <i>Actor</i> class is the super class for all different types of <i>Actor</i> subclasses. The <i>Actor</i> class tracks state information for individual actors in the simulation: <i>name</i>, <i>health</i>, <i>strength</i>, <i>speed</i>, etc (and later, a screen avatar with coordinates). Additional attributes are tracked in the subclasses. The behaviours
	 * (moving and battling) are defined in the subclasses. <i>Actor</i> class is currently a concrete class. Later, the <i>Actor</i> class will become an <i>abstract</i> class where no <i>Actor</i> objects will ever be created -- only subclass objects.
	 * 
	 * @author Calvin Li
	 */
	/** This is the limit stealth an actor should initialized with*/
	private final static double MAX_STEALTH = 50.0;
	private final static double MIN_STEALTH = 0.0;
	private final double MAXSCENEWIDTH = 1000.0;
	private final double MAXSCENEHEIGHT = 571.0;
	private final double MINSCENEWIDTH = 0.0;
	private final double MINSCENEHEIGHT = 0.0;
	/**
	 * Declaration of stealth variable for Hobbit class
	 */
	private double stealth;
	private Node avatar;
	/**
	 * generating random value for stealth between the limits
	 */
	public Hobbit() {
		this.stealth = SingletonRandom.instance.getNormalDistribution(MIN_STEALTH, MAX_STEALTH,2.0);
	}
	// 2-argument constructor
	/**
	 * This is use when actor has to use the subclass hobbit for reference
	 * @param stealth
	 */
	public Hobbit(double stealth) {
		super();	
		this.stealth = stealth;
	} // end constructor
	public Hobbit(Army armyAllegiance) {
		super(armyAllegiance);
		this.stealth = SingletonRandom.instance.getNormalDistribution(MIN_STEALTH, MAX_STEALTH,2.0);
	}
	/**
	 * This is use to set the Stealth value within the limits
	 * @param stealth
	 */
	public void setStealth(double stealth) 
	{
		if (stealth < MIN_STEALTH)
			stealth = MIN_STEALTH;
		else if (stealth > MAX_STEALTH)
			stealth = MAX_STEALTH;
		this.stealth = stealth;
	}
	/**
	 * This method is used for returning variable stealth to an object actor (eg. Hobbit)
	 * @return
	 */
	public double getStealth() {
		return this.stealth;
	}
	/**
	 * This method is called when a user wants to fill in all the fields for a
	 * specific <i>Actor</i> This allows the user to fill in user-specific
	 * entries for the individual attributes, within the pre-specified ranges.
	 */
	@Override
	public void inputAllFields() {
		super.inputAllFields();
		stealth = Input.instance.getDouble("Stealth: ", 0.0, 50.0);
	}
	/**
	 * Provides a presentable version of the stats of an <i>Actor</i> object.
	 */
	public String toString(){
		return super.toString() + String.format("Stealth: %.1f   ",this.stealth);
	}
	/**
	 * this abstract method makes the individual actors create an avatar for display (eg. Hobbit Red Circles)
	 */
	@Override
	public void createAvatar() {
		double ratioSize;
		if(this.getStrength() >= 85.0)
		{
			ratioSize = 145.0;
		}
		else if(this.getStrength() >= 50.0)
		{
			ratioSize = 130.0;
		}
		else
		{
			ratioSize = 80.0;
		}
		double constant = 20.0 * (this.getStrength() / ratioSize);
		this.avatar = new Circle(constant, constant, constant, Color.RED);
		((Shape) this.avatar).setStrokeWidth(2.0);
	}
	/**
	 * this abstract method makes the individual actors display an avatar
	 * @return it returns a node it could be a ImageView if the avatar is a gif
	 */
	@Override
	public Node getAvatar() {
		return this.avatar;
	}
	/**
	 * this abstract method is created on all actor object classes for actors to have visiblitly of each other base on their attributes
	 * @return
	 */
	@Override
	public boolean isVisible(){
			
		return getStealth() < ((MIN_STEALTH + MAX_STRENGTH)/2.0);
	}
	/**
	 * this is use to write this actor attribute to resume later
	 * @param out
	 * @throws IOException
	 */
	private void writeObject(java.io.ObjectOutputStream out) throws IOException 
	{
		out.writeDouble(this.stealth);
	}
	/**
	 * this is use to read this actor attribute to resume use in simulatar
	 * @param in
	 * @throws IOException
	 */
	private void readObject(java.io.ObjectInputStream in) throws IOException
	{
		setStealth(in.readDouble());
	}
	/**
	 * use for actor army to find their closest opponent on the battlefield simulator
	 * @param opponent use to find the actors closest opponent
	 * @return this returns a point (x,y) on the simulator
	 */
	@Override
	protected Point2D findNewLocation(Actor opponent) {
		double ox = opponent.getAvatar().getTranslateX();
		double oy = opponent.getAvatar().getTranslateY();
		double ax = this.getAvatar().getTranslateX();
		double ay = this.getAvatar().getTranslateY();
		double dx = ox-ax;
		double dy = oy-ay;
		double distanceToOpponent = Math.sqrt(dx*dx+dy*dy);
		if (Math.abs(Math.round(dx)) < 5.0 && Math.abs(Math.round(dy)) < 4.0)
		{
			return new Point2D(ax,ay);
		}
		if (distanceToOpponent < 150.0)
		{
			if(ax-dx/2.0 > this.MAXSCENEWIDTH)
			{
				return new Point2D(this.MAXSCENEWIDTH,ay-dy);
			}
			else if (ax-dx/2.0 < this.MINSCENEWIDTH)
			{
				return new Point2D(this.MINSCENEWIDTH,ay-dy);
			}
			else if(ay-dy/2.0 > this.MAXSCENEHEIGHT)
			{
				return new Point2D(ax-dx,this.MAXSCENEHEIGHT);
			}
			else if(ay-dy/2.0 < this.MINSCENEHEIGHT)
			{
				return new Point2D(ax-dx,this.MINSCENEHEIGHT);
			}
			return new Point2D(ax-dx/2.0,ay-dy/2.0);
		}
			return new Point2D(ax,ay);
	}
}
