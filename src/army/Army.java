package army;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
import simulator.Simulator;
import actor.Actor;
import actor.ActorFactory;

/**
 * <i>Army</i> class is used to create armies with arrays of <i>Actor</i>
 * subclasses using the ArrayList.
 * 
 * @author Chang Xing Li
 *
 */
public class Army
{
    private final double ARMYGROUPRATIO = 35.0;
    private static final String FONT_NAME = "Copperplate Gothic Bold";
    private static final Font NOTIFICATION_FONT_SMALL = new Font(FONT_NAME, 25.0);
    private static final Font NOTIFICATION_FONT_LARGE = new Font(FONT_NAME, 45.0);
    // INSTANCE FIELDS
    /**
     * <i>name</i> is the instance field used to give a name to the army that is
     * created.
     */
    private String name;
    /**
     * <i>collectionActors</i> is a reference-to an ArrayList Object. It is used to
     * store various <i>Actor</i> subclass objects.
     */
    private ObservableList<Actor> collectionActors = FXCollections.observableList(new ArrayList<>());
    private Simulator simulator;
    private Color color;
    private DropShadow dropShadow;
    private Army opposingArmy;

    // CONSTRUCTOR
    /**
     * <i>Army</i> constructor. It is used to automatically create an <i>Army</i>.
     * It takes a parameter <i>name</i> to identify the <i>Army</i> created.
     * 
     * @param name      Accepts a string input from the user to give the <i>Army</i>
     *                  a name.
     * @param simulator
     */
    public Army(String name, Simulator simulator, Color color)
    {
	super();
	this.name = name;
	this.simulator = simulator;
	this.color = color;
	this.dropShadow = new DropShadow(20.0, color);
    }

    /**
     * this gives the collection of actors
     * 
     * @return
     */
    public ObservableList<Actor> getCollectionActors()
    {
	return FXCollections.unmodifiableObservableList(collectionActors);
    }

    // POPULATE METHOD
    /**
     * The <i>populate</i> method is used to fill up the <i>Army</i> object that is
     * created. It takes two parameters <i>type</i> and <i>numToAdd</i>.
     * 
     * @param type     This is used to declare what type of <i>Actor</i> subclass
     *                 object is being used.
     * @param numToAdd This is used to declare how many instances are to be created
     *                 to fill up the ArrayList.
     */
    @SuppressWarnings("static-access")
    public void populate(ActorFactory.Type type, int numToAdd)
    {
	for (int i = 0; i < numToAdd; ++i)
	{
	    Actor actor = type.create(this);
	    actor.createAvatar();
	    Node avatar = actor.getAvatar();
	    double standDeviX;
	    double standDeviY;
	    if (type.equals(type.HOBBIT) || type == type.WIZARD)
	    {
		standDeviX = 25.0 / 11.0;
		standDeviY = 571.0 / 160.0;
		double newX = this.simulator.getScene().getWidth() / standDeviX + (this.ARMYGROUPRATIO * Math.random());
		double newY = this.simulator.getScene().getHeight() / standDeviY
			+ (this.ARMYGROUPRATIO * Math.random());
		avatar.setTranslateX(newX);
		avatar.setTranslateY(newY);
		avatar.setEffect(this.dropShadow);
		this.simulator.getChildren().add(actor.getAvatar());
		this.collectionActors.add(actor); // send "this" so that Actor object can capture its allegiance
		Tooltip.install(avatar, new Tooltip(actor.toString()));
	    } else
	    {
		standDeviX = 10.0 / 7.0;
		standDeviY = 571.0 / 340.0;
		double newX = this.simulator.getScene().getWidth() / standDeviX + (this.ARMYGROUPRATIO * Math.random());// 700
		double newY = this.simulator.getScene().getHeight() / standDeviY
			+ (this.ARMYGROUPRATIO * Math.random());// 340
		avatar.setTranslateX(newX);
		avatar.setTranslateY(newY);
		avatar.setEffect(this.dropShadow);
		this.simulator.getChildren().add(actor.getAvatar());
		this.collectionActors.add(actor); // send "this" so that Actor object can capture its allegiance
		Tooltip.install(avatar, new Tooltip(actor.toString()));
	    }
	} // end for
    } // end populate()

    // METHOD TO RETURN AN INDIVIDUAL ACTOR
    /**
     * The <i>displayIndividualActor</i> method is used to return a specific
     * <i>Actor</i> object from the <i>Army</i> it belongs in. It takes one
     * parameter <i>indexOfActorToEdit</i>.
     * 
     * @param indexOfActorToEdit This is used to look up the <i>Actor</i> in the
     *                           ArrayList using the index.
     * @return Actor
     */
    public Actor displayIndividualActor(int indexOfActorToEdit)
    {
	return this.collectionActors.get(indexOfActorToEdit);
    }

    // METHOD TO DISPLAY CONTENTS OF AN ARMY
    /**
     * This is used to display all the Actors and their stats that belong to the
     * <i>Army</i> object.
     * 
     * @author Rex Woollard (code displayed during lecture)
     */
    public void display()
    {
	System.out.println(this.name);
	for (Actor current : this.collectionActors)
	{
	    System.out.println(current);
	}
    }// more code in class Army

    // METHOD TO DISPLAY SIZE OF AN ARMY
    /**
     * This method is used to return the length of the <i>Army</i> ArrayList in an
     * int value.
     * 
     * @return int
     */
    public int size()
    {
	return this.collectionActors.size();
    }

    // METHOD USED TO EDIT THE CONTENTS OF AN ARMY
    /**
     * This method is used to make changes to an existing <i>Actor</i> subclass
     * object in the Army. It takes one parameter <i>indexOfActorToEdit</i>.
     * 
     * @param indexOfActorToEdit This is used to look up the <i>Actor</i> in the
     *                           ArrayList using the index.
     */
    public void edit(int indexOfActorToEdit)
    {
	this.collectionActors.get(indexOfActorToEdit).inputAllFields();
    }

    /**
     * help with start moving actors
     */
    public void startMoving()
    {
	for (Actor actor : this.collectionActors)
	    actor.startMoving();

    }

    public void clearScreen()
    {
	final ObservableList<Node> listJavaFXNodesOnBattlefield = simulator.getChildren(); // creating as a convenience
	
	for (Actor actor : this.collectionActors)
	{
	    actor.getAvatar().setVisible(false);
	    listJavaFXNodesOnBattlefield.remove(actor.getAvatar());
	}
	while(this.collectionActors.size() > 0)
	{
	    this.collectionActors.remove(0);
	}
    }

    /**
     * help with stop moving actors
     */
    public void suspendMoving()
    {
	for (Actor actor : this.collectionActors)
	    actor.suspendMoving();

    }

    /**
     * gets name of armyAllegiance
     * 
     * @return
     */
    public String getName()
    {
	return this.name;
    }

    /**
     * creates a table to display actors on the screen
     * 
     * @return
     */
    public Node getTableViewOfActors()
    {
	TableView<Actor> tableView = Actor.createTable();
	tableView.setItems(this.collectionActors);
	return tableView;
    }

    /**
     * set an opposing army for different armyAllegiance
     * 
     * @param opposingArmy
     */
    public void setOpposingArmy(Army opposingArmy)
    {
	this.opposingArmy = opposingArmy;
    }

    /**
     * set an opposing army for different armyAllegiance
     * 
     * @param opposingArmy
     */
    public Army getOpposingArmy()
    {
	return this.opposingArmy;
    }

    /**
     * help with saving data from actors
     * 
     * @param out
     * @throws IOException
     */
    public void serialize(ObjectOutputStream out) throws IOException
    {
	out.writeObject(this.name);
	out.writeDouble(this.color.getRed());
	out.writeDouble(this.color.getGreen());
	out.writeDouble(this.color.getBlue());
	out.writeDouble(this.color.getOpacity());
	out.writeInt(this.collectionActors.size());
	for (Actor a : this.collectionActors)
	{
	    out.writeObject(a);
	}
    } // end serialize() to support serialization

    /**
     * help retriving values for actors and their instance variables
     * 
     * @param in
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void deserialize(ObjectInputStream in) throws IOException, ClassNotFoundException
    {
	this.collectionActors.clear();
	this.name = (String) in.readObject();
	this.color = new Color(in.readDouble(), in.readDouble(), in.readDouble(), in.readDouble());
	this.dropShadow = new DropShadow(20.0, this.color);
	int size = in.readInt();
	for (int i = 0; i < size; ++i)
	{
	    Actor actor = (Actor) in.readObject();
	    actor.setArmyAllegiance(this);
	    actor.getAvatar().setEffect(this.dropShadow);
	    this.simulator.getChildren().add(actor.getAvatar());
	    this.collectionActors.add(actor);
	}
    } // end deserialize() to support serialization

    /**
     * use for finding nearest opposing actor
     * 
     * @param actorToMove
     * @return
     */
    public Actor findNearestActor(Actor actorToMove)
    {
	Actor nearest = null;
	double distanceToClosest = Double.MAX_VALUE;
	for (Actor opponent : this.collectionActors)
	{
	    if (opponent.isVisible())
	    {
		double currentDistance = actorToMove.distanceTo(opponent);
		if (currentDistance < distanceToClosest)
		{
		    distanceToClosest = currentDistance;
		    nearest = opponent;
		} // end if closer
	    } // end if (isVisible)
	} // end for (collectionActors)
	return nearest;
    } // end of findNearestActor()

    /**
     * this is use when actors drop below 1 health so they can get removed and
     * displays their death on screen
     * 
     * @param nowDeadActor
     */
    public void removeNowDeadActor(Actor nowDeadActor)
    {
	final ObservableList<Node> listJavaFXNodesOnBattlefield = simulator.getChildren(); // creating as a convenience
											   // variable, since the
											   // removeNowDeadActor()
											   // method needs to manage
											   // many Node objects in the
											   // simulator collection of
											   // Node objects
	// START: Create Notification message about the nowDeadActor: Create, then add
	// two Transition Animations, packing in a ParallelTransition
	{ // setup Stack Frame to allow re-use of variable identifiers "tt", "ft" and "pt"
	    Text message = new Text(120.0, 120.0, "Dead: " + nowDeadActor.getName());
	    message.setFont(NOTIFICATION_FONT_SMALL);
	    message.setStroke(color);
	    final Duration duration = Duration.seconds(3.0);
	    FadeTransition ft = new FadeTransition(duration);
	    ft.setToValue(0.0); // no need to associate with the Text (message) here, that will be done in the
				// ParallelTransition
	    TranslateTransition tt = new TranslateTransition(duration);
	    tt.setByY(200.0); // no need to associate with the Text (message) here, that will be done in the
			      // ParallelTransition
	    ParallelTransition pt = new ParallelTransition(message, ft, tt);
	    pt.setOnFinished(event -> listJavaFXNodesOnBattlefield.remove(message));
	    pt.play(); // couple both Transitions in the ParallelTransition and associate with Text
	    listJavaFXNodesOnBattlefield.add(message); // it will play() and after playing the code in the
						       // setOnFinished() method will called to remove the temporay
						       // message from the scenegraph.
	}
	// END: Create Notification message about the nowDeadActor: Create, then add two
	// Transition Animations, packing in a ParallelTransition

	collectionActors.remove(nowDeadActor); // removes nowDeadActor from the collection of active Actor objects that
					       // are part of this army.
	listJavaFXNodesOnBattlefield.remove(nowDeadActor.getAvatar()); // removes the avatar from the screnegraph (the
								       // Node object). The actor will disappear from
								       // the screen.

	// START: Create Final Announcement of Winning Army
	if (collectionActors.size() == 0)
	{ // Army has been wiped out, since no Actor objects remain in the collection.
	  // Therefore . . . the opposing Army wins.
	    Text winner = new Text(205.0, 300.0, "Winner: " + opposingArmy.getName());
	    winner.setFont(NOTIFICATION_FONT_LARGE);
	    winner.setStroke(opposingArmy.color);
	    winner.setEffect(opposingArmy.dropShadow);
	    final Duration duration = Duration.seconds(1.0);
	    FadeTransition ft = new FadeTransition(duration, winner);
	    ft.setToValue(0.2);
	    ft.setCycleCount(10);
	    ft.setAutoReverse(true);
	    ft.setOnFinished(event -> listJavaFXNodesOnBattlefield.remove(winner));
	    ft.play();
	    listJavaFXNodesOnBattlefield.add(winner); // it will play() and after playing the code in the
						      // setOnFinished() method will called to remove the temporary
						      // winner from the scenegraph.
	}
	// END: Create Final Announcement of Winning Army
    } // end removeNowDeadActor()
}// end of class