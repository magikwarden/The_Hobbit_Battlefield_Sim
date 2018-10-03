package actor;

import java.io.IOException;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import util.Input;
import army.Army;

public class Orc extends Actor {

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
	private boolean axe = false;
	private boolean random;
	private boolean fury;
	private Node avatar;
	/**
	 * This is for the boolean instances so that it can be true or false 30% chance
	 * @return
	 */
	private boolean rngRandom()
	{
		if (Math.random() > 0.5)
			this.random = true;
		else
			this.random = false;
		return this.random;
	}
	/**
	 * constructor to initialize values for this actor's instance variables
	 */
	public Orc() {
		this.axe = rngRandom();
		this.fury = rngRandom();
	}
	// 2-argument constructor
	/**
	 * This is use when actor has to use the subclass Orc for reference
	 * @param AXE
	 * @param fury
	 */
	public Orc(boolean axe, boolean fury) {
		super();	
		this.fury = fury;
		this.axe = axe;
		if (this.axe == true)
		{
			this.setStrength(this.getStrength() + 5.0);
		}
		if (this.fury == true)
		{
			this.setSpeed(this.getSpeed() + 10.0);
		}
	} // end constructor
	/**
	 * This method is to help create an army of Orc
	 * @param armyAllegiance
	 */
	public Orc(Army armyAllegiance) {
		super(armyAllegiance);
		this.fury = rngRandom();
		this.axe = rngRandom();
		if (this.axe == true)
		{
			this.setStrength(this.getStrength() + 5.0);
		}
		if (this.fury == true)
		{
			this.setSpeed(this.getSpeed() + 10.0);
		}
	}
	/**
	 * get axe boolean
	 * @return
	 */
	public boolean getAxe() {
		return this.axe;
	}
	/**
	 * set axe boolean
	 * @param axe
	 */
	public void setAxe(boolean axe) {
		this.axe = axe;
	}
	/**
	 * get fury boolean
	 * @return
	 */
	public boolean getFury() {
		return this.fury;
	}
	/**
	 * set fury boolean
	 * @param fury
	 */
	public void setFury(boolean fury) {
		this.fury = fury;
	}
	/**
	 * This method is called when a user wants to fill in all the fields for a
	 * specific <i>Actor</i> This allows the user to fill in user-specific
	 * entries for the individual attributes, within the pre-specified ranges.
	 */
	@Override
	public void inputAllFields() {
		super.inputAllFields();
		this.axe = Input.instance.getBoolean("hasAXE: ");
		this.fury = Input.instance.getBoolean("Fury: ");
	} // end toString()
	/**
	 * Provides a presentable version of the stats of an <i>Actor</i> object.
	 */
	public String toString(){
		return super.toString() + String.format("hasAXE: %b   Fury: %b  ",this.axe,this.fury);
	}
	/**
	 * this abstract method makes the individual actors create an avatar for display (eg. Hobbit Red Circles)
	 */
	@Override
	public void createAvatar() {
		double ratioSize;
		if(this.getStrength() >= 50.0)
		{
			ratioSize = 100.0;
		}
		else
		{
			ratioSize = 65.0;
		}
		double constant = 20.0 * (this.getStrength() / ratioSize);
		this.avatar = new Rectangle(constant, constant,Color.BLUE);
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
	public boolean isVisible() {
		
		return true;
	}
	/**
	 * this is use to write this actor attribute to resume later
	 * @param out
	 * @throws IOException
	 */
	private void writeObject(java.io.ObjectOutputStream out) throws IOException 
	{
		out.writeBoolean(this.axe);
		out.writeBoolean(this.fury);
	}
	/**
	 * this is use to read this actor attribute to resume use in simulatar
	 * @param in
	 * @throws IOException
	 */
	private void readObject(java.io.ObjectInputStream in) throws IOException
	{
		setAxe(in.readBoolean());
		setFury(in.readBoolean());
	}
}
