package actor;

import java.io.IOException;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import util.Input;
import army.Army;

public class Nazgul extends Actor
{
    /**
     * 
     * Name: Calvin Li Student Number: 040810357
     * 
     *
     * Course: CST8284: Object Oriented Programming Lab Section: 021 Lecture
     * Section: 020 Lab Professor: Eric Torunski
     */
    /**
     * 
     * The <i>Actor</i> class is the super class for all different types of
     * <i>Actor</i> subclasses. The <i>Actor</i> class tracks state information for
     * individual actors in the simulation: <i>name</i>, <i>health</i>,
     * <i>strength</i>, <i>speed</i>, etc (and later, a screen avatar with
     * coordinates). Additional attributes are tracked in the subclasses. The
     * behaviours (moving and battling) are defined in the subclasses. <i>Actor</i>
     * class is currently a concrete class. Later, the <i>Actor</i> class will
     * become an <i>abstract</i> class where no <i>Actor</i> objects will ever be
     * created -- only subclass objects.
     * 
     * @author Calvin Li
     */
    /**
     * Defining instancee variables for Elves
     */
    private boolean ringOfPower = false;
    private boolean hellHawks = false;
    private boolean random;
    private Rectangle avatar;

    /**
     * This is for the boolean instances so that it can be true or false 30% chance
     * 
     * @return
     */
    private boolean rngRandom()
    {
	if (Math.random() > 0.7)
	    this.random = true;
	else
	    this.random = false;
	return this.random;
    }

    /**
     * generating random value for this actor's attributes
     */
    public Nazgul()
    {
	this.ringOfPower = rngRandom();
	this.hellHawks = rngRandom();
    }

    // 2-argument constructor
    /**
     * This is use when actor has to use this subclass for reference
     * 
     * @param ringOfPower
     * @param hellHawks
     */
    public Nazgul(boolean ringOfPower, boolean hellHawks)
    {
	super();
	this.ringOfPower = ringOfPower;
	this.hellHawks = hellHawks;
	if (this.ringOfPower == true)
	{
	    this.setStrength(this.getStrength() + 5.0);
	}
	if (this.hellHawks == true)
	{
	    this.setSpeed(this.getSpeed() + 10.0);
	}
    } // end constructor

    /**
     * This method is to help create an army of this subclass
     * 
     * @param armyAllegiance
     */
    public Nazgul(Army armyAllegiance)
    {
	super(armyAllegiance);
	this.ringOfPower = rngRandom();
	this.hellHawks = rngRandom();
	if (this.ringOfPower == true)
	{
	    this.setStrength(this.getStrength() + 5.0);
	}
	if (this.hellHawks == true)
	{
	    this.setSpeed(this.getSpeed() + 10.0);
	}
    }

    /**
     * get attribute ringOfPower
     * 
     * @return
     */
    public boolean getringOfPower()
    {
	return this.ringOfPower;
    }

    /**
     * set attribute ringOfPower
     * 
     * @param ringOfPower
     */
    public void setringOfPower(boolean ringOfPower)
    {
	this.ringOfPower = ringOfPower;
    }

    /**
     * get attribute HellHawks
     * 
     * @return
     */
    public boolean getHellHawks()
    {
	return hellHawks;
    }

    /**
     * set attribute HellHawks
     * 
     * @param hellHawks
     */
    public void setHellHawks(boolean hellHawks)
    {
	this.hellHawks = hellHawks;
    }

    /**
     * This method is called when a user wants to fill in all the fields for a
     * specific <i>Actor</i> This allows the user to fill in user-specific entries
     * for the individual attributes, within the pre-specified ranges.
     */
    @Override
    public void inputAllFields()
    {
	super.inputAllFields();
	this.ringOfPower = Input.instance.getBoolean("hasringOfPower: ");
	this.hellHawks = Input.instance.getBoolean("hellHawks: ");
    }

    /**
     * Provides a presentable version of the stats of an <i>Actor</i> object.
     */
    public String toString()
    {
	return super.toString() + String.format("RingOfPower: %b   HellHawk: %b", this.ringOfPower, this.hellHawks);
    }

    /**
     * this abstract method makes the individual actors create an avatar for display
     * (eg. Hobbit Red Circles)
     */
    @Override
    public void createAvatar()
    {
	double ratioSize;
	if (this.getStrength() >= 50.0)
	{
	    ratioSize = 100.0;
	} else
	{
	    ratioSize = 65.0;
	}
	double constant = 20.0 * (this.getStrength() / ratioSize);
	this.avatar = new Rectangle(constant, constant, Color.GREEN);
	this.avatar.setStrokeWidth(2.0);
    }

    /**
     * this abstract method makes the individual actors display an avatar
     * 
     * @return it returns a node it could be a ImageView if the avatar is a gif
     */
    @Override
    public Node getAvatar()
    {
	return this.avatar;
    }

    /**
     * this abstract method is created on all actor object classes for actors to
     * have visiblitly of each other base on their attributes
     * 
     * @return
     */
    @Override
    public boolean isVisible()
    {

	return !this.hellHawks;
    }

    /**
     * this is use to write this actor attribute to resume later
     * 
     * @param out
     * @throws IOException
     */
    private void writeObject(java.io.ObjectOutputStream out) throws IOException
    {
	out.writeBoolean(this.ringOfPower);
	out.writeBoolean(this.hellHawks);
    }

    /**
     * this is use to read this actor attribute to resume use in simulatar
     * 
     * @param in
     * @throws IOException
     */
    private void readObject(java.io.ObjectInputStream in) throws IOException
    {
	setringOfPower(in.readBoolean());
	setHellHawks(in.readBoolean());
    }
}
