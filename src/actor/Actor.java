package actor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javafx.animation.Animation;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Duration;
import javafx.util.converter.DoubleStringConverter;
import util.Input;
import util.SingletonRandom;
import army.Army;

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

@SuppressWarnings("serial")
public abstract class Actor implements Serializable
{
    /**
     * Static variable used to keep track of the number of <i>Actor</i> objects
     * built.
     */
    private static int actorSerialNumber = 1;

    // THESE ARE BOUNDARIES FOR THE ATTRIBUTES
    /** Minimum Boundary for <i>health</i> attribute, currently:{@value} */
    public final static double MIN_HEALTH = 0.0; // static variable: one and// only instance shared but// all objects
    /** Maximum Boundary for <i>health</i> attribute, currently:{@value} */
    public final static double MAX_HEALTH = 100.0;
    /** Minimum Boundary for <i>strength</i> attribute, currently:{@value} */
    public final static double MIN_STRENGTH = 20.0;
    /** Maximum Boundary for <i>strength</i> attribute, currently:{@value} */
    public final static double MAX_STRENGTH = 100.0;
    /** Minimum Boundary for <i>speed</i> attribute, currently:{@value} */
    public final static double MIN_SPEED = 20.0;
    /** Maximum Boundary for <i>speed</i> attribute, currently:{@value} */
    public final static double MAX_SPEED = 100.0;

    // THESE ARE VARIOUS INSTANCE FIELDS USED FOR EACH ACTOR
    /**
     * * Stores the <i>name</i> value assigned to the specific <i>Actor</i> object
     */
    private SimpleStringProperty name = new SimpleStringProperty();
    /**
     * * Stores the <i>strength</i> value assigned to the specific <i>Actor</i> *
     * object
     */
    private SimpleDoubleProperty strength = new SimpleDoubleProperty();
    /**
     * * Stores the <i>speed</i> value assigned to the specific <i>Actor</i> *
     * object
     */
    private SimpleDoubleProperty speed = new SimpleDoubleProperty();
    /**
     * * Stores the <i>health</i> value assigned to the specific <i>Actor</i> *
     * object
     */
    private SimpleDoubleProperty health = new SimpleDoubleProperty();

    /**
     * generates a variable for army class to call upon army methods
     */
    private Army armyAllegiance;
    /**
     * it is use to make actors move
     */
    private TranslateTransition tt;
    /**
     * it is use to make actors rotate
     */
    private RotateTransition rt;
    /**
     * use to display attributes of the individual actors
     */
    private Tooltip tooltip;

    // CONSTRUCTOR
    /**
     * <i>Actor</i> constructor is used to create <i>Actor</i> objects
     * automatically. It gives them randomly generated values for <i>name</i>,
     * <i>health</i>, <i>strength</i> and <i>speed</i>.
     */
    public Actor()
    {
	setName(this.getClass().getSimpleName() + actorSerialNumber);
	setStrength((double) Math
		.round((SingletonRandom.instance.getNormalDistribution(MIN_STRENGTH, MAX_STRENGTH, 3.0)) * 10.0)
		/ 10.0);
	setHealth((double) Math
		.round((SingletonRandom.instance.getNormalDistribution(MIN_HEALTH, MAX_HEALTH, 3.0)) * 10.0) / 10.0);// Math.round((MAX_HEALTH)*10)/10.0);
	setSpeed((double) Math.round((SingletonRandom.instance.getNormalDistribution(MIN_SPEED, MAX_SPEED, 3.0)) * 10.0)
		/ 10.0);
	actorSerialNumber++;
    }

    /**
     * <i>Actor</i> constructor is used to create <i>Actor</i> objects
     * automatically. It gives them randomly generated values for <i>name</i>,
     * <i>health</i>, <i>strength</i> and <i>speed</i>. It takes a parameter
     * <i>armyAllegiance</i>.
     * 
     * @param armyAllegiance This is used to return the army it belongs to.
     */
    public Actor(Army armyAllegiance)
    {
	this();
	this.armyAllegiance = armyAllegiance;
    }

    // SETTER FOR NAME
    /**
     * Allows user inputed values for <i>name</i>.
     * 
     * @param name Used to assign a name to an <i>Actor</i> object.
     */
    public void setName(String name)
    {
	this.name.set(name);
    }

    // GETTER FOR NAME
    /**
     * Returns the user inputed values for <i>name</i>.
     * 
     * @return the user inputed values for <i>name</i>.
     */
    public String getName()
    {
	return this.name.get();
    }

    // SETTER FOR HEALTH
    /**
     * Allows user inputed values for <i>health</i> within max and min boundaries
     * that have been preset.
     * 
     * @param health Used to assign health points to an <i>Actor</i> object.
     */
    public void setHealth(double health)
    {
	if (health < MIN_HEALTH)
	    health = MIN_HEALTH;
	else if (health > MAX_HEALTH)
	    health = MAX_HEALTH;
	this.health.set(health);
    }

    // GETTER FOR HEALTH
    /***
     * Returns the user inputed vales for <i>health</i>.
     * 
     * @return the user inputed vales for <i>health</i>.
     */
    public double getHealth()
    {
	return this.health.get();
    }

    // SETTER FOR STRENGTH
    /**
     * Allows user inputed values for <i>strength</i> within max and min boundaries
     * that have been preset.
     * 
     * @param strength Used to assign strength points to an <i>Actor</i> object.
     */
    public void setStrength(double strength)
    {
	if (strength < MIN_STRENGTH)
	    strength = MIN_STRENGTH;
	else if (strength > MAX_STRENGTH)
	    strength = MAX_STRENGTH;
	this.strength.set(strength);
    }

    // GETTER FOR STRENGTH
    /**
     * Returns the user inputed values for <i>strength</i>.
     * 
     * @return the user inputed values for <i>strength</i>.
     */
    public double getStrength()
    {
	return this.strength.get();
    }

    // SETTER FOR SPEED
    /**
     * Allows user inputed values for <i>speed</i> within max and min boundaries
     * that have been preset.
     * 
     * @param speed Used to assign speed points to and <i>Actor</i> object.
     */
    public void setSpeed(double speed)
    {
	if (speed < MIN_SPEED)
	    speed = MIN_SPEED;
	else if (speed > MAX_SPEED)
	    speed = MAX_SPEED;
	this.speed.set(speed);
    }

    // GETTER FOR SPEED
    /**
     * Returns the user inputed values for <i>speed</i>.
     * 
     * @return the user inputed values for <i>speed</i>.
     */
    public double getSpeed()
    {
	return this.speed.get();
    }

    // GETTER FOR ARMY ALLEGIANCE
    /**
     * This method returns the <i>armyAllegiance</i>
     * 
     * @return armyAllegiance
     */
    public Army getArmyAllegiance()
    {
	return this.armyAllegiance;
    }

    // METHOD FOR USER INPUTED VALUES
    /**
     * This method is called when a user wants to fill in all the fields for a
     * specific <i>Actor</i> This allows the user to fill in user-specific entries
     * for the individual attributes (<i>name</i>, <i>health</i>, <i>strength</i>
     * and <i>speed</i>), within the pre-specified ranges.
     */
    public void inputAllFields()
    {
	setName(Input.instance.getString("Enter name:"));
	setHealth(Input.instance.getDouble("Health:", MIN_HEALTH, MAX_HEALTH));
	setStrength(Input.instance.getDouble("Strength:", MIN_STRENGTH, MAX_STRENGTH));
	setSpeed(Input.instance.getDouble("Speed:", MIN_SPEED, MAX_SPEED));
    }

    // METHOD TO BEGIN A BATTLE BETWEEN TWO ACTOR OBJECTS
    /**
     * This method starts a new round of combat between two different <i>Actor</i>
     * objects.
     * 
     * @param defender is a reference to a different <i>Actor</i> object that will
     *                 be engaged in the battle.
     * @return The health of the <i>Actor</i> object that started the battle.
     */
    protected double combatRound(Actor defender)
    {
	double damageRatio = 0.2;
	// 100 * (0.2 * 1) = 20
	double damageDef = defender.strength.get() * (damageRatio * Math.random());
	double damageOpp = this.strength.get() * (damageRatio * Math.random());
	this.health.set((Math.round(this.health.get() - damageDef)) * 10.0 / 10.0);
	defender.health.set(Math.round((defender.health.get() - damageOpp)) * 10.0 / 10.0);

	return this.health.get();
    }

    // METHOD FOR TEXTUAL DISPLAY
    /**
     * Provides a presentable version of the stats of an <i>Actor</i> object.
     */
    // This code has been obtained from author Professor Rex Woollard from his
    // in-class lectures
    @Override
    public String toString()
    {
	return String.format("Name:%s   Health:%.1f   Strength:%.1f   Speed:%.1f ", this.getName(), this.getHealth(),
		this.getStrength(), this.getSpeed());
    }

    /**
     * this abstract method makes the individual actors create an avatar for display
     * (eg. Hobbit Red Circles)
     */
    public abstract void createAvatar();

    /**
     * this abstract method makes the individual actors display an avatar
     * 
     * @return it returns a node it could be a ImageView if the avatar is a gif
     */
    public abstract Node getAvatar();

    /**
     * startMoving method is method use to make avatars move and rotate use for most
     * of the displays of how actors interact in simulator
     */
    public void startMoving()
    {
	Army opposingArmy = this.armyAllegiance.getOpposingArmy();
	Actor opponent = opposingArmy.findNearestActor(this);
	if (opponent == null)
	{
	    return;
	} else
	{
	    if (this.getHealth() <= 0.0)
	    {
		this.getAvatar().setVisible(false);
		this.armyAllegiance.removeNowDeadActor(this);
		return;
	    } else
	    {
		double distanceToOpponent = this.distanceTo(opponent);
		if (tt == null && rt == null)
		{
		    tt = new TranslateTransition(Duration.seconds(distanceToOpponent / this.getSpeed()),
			    this.getAvatar());
		    rt = new RotateTransition(Duration.seconds(0.5), this.getAvatar());
		}
		if (distanceToOpponent < 4.0)
		{
		    this.combatRound(opponent);
		    this.rt.setByAngle(this.combatRound(opponent));
		    this.rt.setOnFinished(event -> startMoving());
		    this.rt.play();
		} else
		{
		    Point2D newLocation = findNewLocation(opponent);
		    this.tt.setDelay(Duration.seconds(1.0 / this.getSpeed()));
		    this.tt.setToY(newLocation.getY());
		    this.tt.setToX(newLocation.getX());
		    this.tt.setOnFinished(event -> this.startMoving()); // NOT RECURSION!!!!
		    this.tt.play();
		}
	    }

	}
    }

    /**
     * use for actor army to find their closest opponent on the battlefield
     * simulator
     * 
     * @param opponent use to find the actors closest opponent
     * @return this returns a point (x,y) on the simulator
     */
    protected Point2D findNewLocation(Actor opponent)
    {
	double xOpponent = opponent.getAvatar().getTranslateX();
	double yOpponent = opponent.getAvatar().getTranslateY();
	double xToMove = getAvatar().getTranslateX();
	double yToMove = getAvatar().getTranslateY();
	double deltaX = xOpponent - xToMove;
	double deltaY = yOpponent - yToMove;
	if (Math.abs(Math.round(deltaX)) < 4.0 && Math.abs(Math.round(deltaY)) < 4.0)
	{
	    return new Point2D(xToMove, yToMove);
	}
	if (this.toWeak() == null)
	{
	    return new Point2D(xToMove + deltaX / 2.0, yToMove + deltaY / 2.0);
	} else
	{
	    return this.toWeak();
	}
    };

    /**
     * this pauses all actor from moving on the simulator
     */
    public void suspendMoving()
    {
	if (tt == null && rt == null)
	{
	    Army opposingArmy = this.armyAllegiance.getOpposingArmy();
	    Actor opponent = opposingArmy.findNearestActor(this);
	    double distanceToOpponent = this.distanceTo(opponent);
	    tt = new TranslateTransition(Duration.seconds(distanceToOpponent / this.getSpeed()), this.getAvatar());
	    rt = new RotateTransition(Duration.seconds(0.5), this.getAvatar());
	}
	if (this.tt.getStatus() == Animation.Status.RUNNING && this.tt != null)
	{
	    this.tt.stop();
	}
	if (this.rt.getStatus() == Animation.Status.RUNNING && this.rt != null)
	{
	    this.rt.stop();
	}
    }

    /**
     * this is use for actors that have less then 15.0 when actors are to weak and
     * they will not move
     * 
     * @return this will make then stay at the point therefore no more movements for
     *         that actor
     */
    protected Point2D toWeak()
    {
	if (this.getHealth() < 15.0)
	{
	    return new Point2D(this.getAvatar().getTranslateX(), this.getAvatar().getTranslateY());
	} else
	{
	    return null;
	}
    }

    /**
     * this is use to calculate the distance between the actor and the opposing
     * actor
     * 
     * @param opponent use for calculating distance
     * @return double value of the distance
     */
    public double distanceTo(Actor opponent)
    {
	double xToMove = getAvatar().getTranslateX();
	double yToMove = getAvatar().getTranslateY();
	double xOpponent = opponent.getAvatar().getTranslateX();
	double yOpponent = opponent.getAvatar().getTranslateY();
	double deltaX = xToMove - xOpponent;
	double deltaY = yToMove - yOpponent;
	double calculatedDistance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
	return calculatedDistance;
    }

    // * createTable is static to allow Army to define a table without having
    // any Actor objects present. */
    /**
     * createTable is static to allow Army to define a table without having any
     * Actor objects present.
     */
    public static TableView<Actor> createTable()
    {
	TableView<Actor> table = new TableView<Actor>();
	final double PREF_WIDTH_DOUBLE = 75.0;
	table.setPrefWidth(PREF_WIDTH_DOUBLE * 7.5);
	table.setEditable(true);

	TableColumn<Actor, String> nameCol = new TableColumn<>("Name");
	nameCol.setCellValueFactory(new PropertyValueFactory<Actor, String>("name"));
	nameCol.setPrefWidth(PREF_WIDTH_DOUBLE);
	TableColumn<Actor, Double> healthCol = new TableColumn<>("Health");
	healthCol.setCellValueFactory(new PropertyValueFactory<Actor, Double>("health"));
	healthCol.setPrefWidth(PREF_WIDTH_DOUBLE);
	TableColumn<Actor, Double> strengthCol = new TableColumn<>("Strength");
	strengthCol.setCellValueFactory(new PropertyValueFactory<Actor, Double>("strength"));
	strengthCol.setPrefWidth(PREF_WIDTH_DOUBLE);
	TableColumn<Actor, Double> speedCol = new TableColumn<>("Speed");
	speedCol.setCellValueFactory(new PropertyValueFactory<Actor, Double>("speed"));
	speedCol.setPrefWidth(PREF_WIDTH_DOUBLE);
	TableColumn<Actor, Number> locationXCol = new TableColumn<>("X");
	locationXCol.setCellValueFactory(cell -> cell.getValue().getAvatar().translateXProperty());
	locationXCol.setPrefWidth(PREF_WIDTH_DOUBLE);
	TableColumn<Actor, Number> locationYCol = new TableColumn<>("Y");
	locationYCol.setCellValueFactory(cell -> cell.getValue().getAvatar().translateYProperty());
	locationYCol.setPrefWidth(PREF_WIDTH_DOUBLE);
	ObservableList<TableColumn<Actor, ?>> c = table.getColumns();
	c.add(nameCol);
	c.add(healthCol);
	c.add(strengthCol);
	c.add(speedCol);
	c.add(locationXCol);
	c.add(locationYCol);
	// Compare line ABOVE with line BELOW: The BELOW line looks cleaner and does
	// actually work . . . but the compiler spits out a warning. The ABOVE line
	// accomplishes the same thing, less elegantly, but without warnings.
	// table.getColumns().addAll(nameCol, healthCol, strengthCol, speedCol,
	// locationXCol, locationYCol);

	// The following code makes each cell in the selected columns editable (Name,
	// Health, Strength, Speed)
	// We CANNOT implement edit capabilities on the X/Y columns since they are
	// READ-ONLY.
	nameCol.setCellFactory(TextFieldTableCell.<Actor>forTableColumn());
	nameCol.setOnEditCommit(event ->
	{
	    Actor a = (event.getTableView().getItems().get(event.getTablePosition().getRow()));
	    a.setName(event.getNewValue());
	    a.resetAvatarAttributes();
	});

	healthCol.setCellFactory(TextFieldTableCell.<Actor, Double>forTableColumn(new DoubleStringConverter()));
	healthCol.setOnEditCommit(event ->
	{
	    Actor a = (event.getTableView().getItems().get(event.getTablePosition().getRow()));
	    a.setHealth(event.getNewValue());
	    a.resetAvatarAttributes();
	});

	strengthCol.setCellFactory(TextFieldTableCell.<Actor, Double>forTableColumn(new DoubleStringConverter()));
	strengthCol.setOnEditCommit(event ->
	{
	    Actor a = (event.getTableView().getItems().get(event.getTablePosition().getRow()));
	    a.setStrength(event.getNewValue());
	    a.resetAvatarAttributes();
	});

	speedCol.setCellFactory(TextFieldTableCell.<Actor, Double>forTableColumn(new DoubleStringConverter()));
	speedCol.setOnEditCommit(event ->
	{
	    Actor a = (event.getTableView().getItems().get(event.getTablePosition().getRow()));
	    a.setSpeed(event.getNewValue());
	    a.resetAvatarAttributes();
	});

	return table;
    } // end createTable()

    /**
     * use to update the tooltip on actors
     */
    public void resetAvatarAttributes()
    {
	this.tooltip = new Tooltip();
	this.tooltip.setText(this.toString());// Note: This updates the text in the Tooltip that was installed earlier.
					      // We re-use the originally installed Tooltip.
    }

    /**
     * this abstract method is created on all actor object classes for actors to
     * have visiblitly of each other base on their attributes
     * 
     * @return
     */
    public abstract boolean isVisible();

    /**
     * this returns an int value for actor serial number
     * 
     * @return int value
     */
    public int getactorSerial()
    {
	return actorSerialNumber;
    }

    /**
     * setting army allegiance for an individual actor
     * 
     * @param army
     */
    public void setArmyAllegiance(Army army)
    {
	this.armyAllegiance = army;
    }

    // Explicit implementation of writeObject, but called implicitly as a result
    // of recursive calls to writeObject() based on Serializable interface
    /**
     * save the actor coordinates and attributes for later on
     * 
     * @param out
     * @throws IOException
     */
    private void writeObject(ObjectOutputStream out) throws IOException
    {
	out.writeObject(getName()); // SimpleDoubleProperty name is NOT serializable, so I do it manually
	out.writeDouble(getStrength()); // SimpleDoubleProperty strength is NOT serializable, so I do it manually
	out.writeDouble(getHealth()); // SimpleDoubleProperty health is NOT serializable, so I do it manually
	out.writeDouble(getSpeed()); // SimpleDoubleProperty speed is NOT serializable, so I do it manually
	out.writeDouble(getAvatar().getTranslateX()); // Node battlefieldAvatar is NOT serializable. It's TOO BIG
						      // anyway, so I extract the elements that I need (here, translateX
						      // property) to retain manually.
	out.writeDouble(getAvatar().getTranslateY()); // Node battlefieldAvatar is NOT serializable. It's TOO BIG
						      // anyway, so I extract the elements that I need (here, translateY
						      // property) to retain manually.
    } // end writeObject() to support serialization

    // Explicit implementation of readObject, but called implicitly as a result
    // of recursive calls to readObject() based on Serializable interface
    /**
     * resume/open the saved actors and display coordinates for actors and their
     * value of attributes
     * 
     * @param in
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException
    {
	this.name = new SimpleStringProperty((String) in.readObject());
	this.strength = new SimpleDoubleProperty(in.readDouble());
	this.health = new SimpleDoubleProperty(in.readDouble());
	this.speed = new SimpleDoubleProperty(in.readDouble());
	this.createAvatar();
	this.getAvatar().setTranslateX(in.readDouble());
	this.getAvatar().setTranslateY(in.readDouble());
	this.tooltip = new Tooltip(toString());
	Tooltip.install(getAvatar(), tooltip);
	this.resetAvatarAttributes();
    } // end readObject() to support serialization
}