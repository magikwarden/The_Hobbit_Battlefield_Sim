package simulator;

import java.io.*;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import actor.Actor;
import actor.ActorFactory;
import army.Army;

public class Simulator extends Group
{
    private Stage primaryStage;
    private Army forcesOfLight;
    private Army forcesOfDarkness;
    private Stage stageListControllerWindow;
    private Stage stageTableControllerWindow;

    /**
     * this sets up the forces when simulator is ran ssetting their initial values
     * 
     * @param primaryStage
     */
    public Simulator(Stage primaryStage)
    {
	this.primaryStage = primaryStage;
	this.forcesOfLight = new Army("Forces of Light", this, Color.AQUA);
	this.forcesOfDarkness = new Army("Forces of Darkness", this, Color.RED);
	this.forcesOfDarkness.setOpposingArmy(forcesOfLight);
	this.forcesOfLight.setOpposingArmy(forcesOfDarkness);
	this.buildListViewWindow();
	this.buildTableViewWindow();
    }

    /**
     * when called it popluates the screen with actors
     */
    public void populate()
    {
	this.forcesOfLight.populate(ActorFactory.Type.HOBBIT, 2);
	this.forcesOfLight.populate(ActorFactory.Type.WIZARD, 8);
	this.forcesOfDarkness.populate(ActorFactory.Type.NAZGUL, 4);
	this.forcesOfDarkness.populate(ActorFactory.Type.ORC, 4);
    }

    /**
     * when called actors start moving
     */
    public void run()
    {
	this.forcesOfLight.startMoving();
	this.forcesOfDarkness.startMoving();
    }
    
    public void clear()
    {
	this.forcesOfLight.clearScreen();
	this.forcesOfDarkness.clearScreen();
    }

    /**
     * when called actors stop moving
     */
    public void suspend()
    {
	this.forcesOfLight.suspendMoving();
	this.forcesOfDarkness.suspendMoving();

    }

    /**
     * when called displays list view window of actors
     */
    public void openListViewWindow()
    {
	this.stageListControllerWindow.show();
    }

    /**
     * when called displays table view window of actors
     */
    public void openTableViewWindow()
    {
	this.stageTableControllerWindow.show();

    }

    /**
     * use for saving actors on the screen for later
     */
    public void save()
    {
	// Using a try block in case there is a file I/O error. Open a file that
	// is configured for binary output.
	try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("battlefield.ser")))
	{
	    this.forcesOfLight.serialize(out);// "normal" method call that I created. Army class NOT serializable.
	    this.forcesOfDarkness.serialize(out);// Actor class and ALL its subclasses are serializable.
	} catch (Exception e)
	{
	    e.printStackTrace();
	}
    } // end save()

    /**
     * use to read or open saved acotrs on the screen to display
     */
    public void restore()
    {
	// Using a try block in case there is a file I/O error. Open a file that
	// is configured for binary input.
	try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("battlefield.ser")))
	{
	    this.forcesOfLight.deserialize(in);// "normal" method call that I created. Army class NOT serializable.
	    this.forcesOfDarkness.deserialize(in);// Actor class and ALL its subclasses are serializable.
	} catch (Exception e)
	{
	    e.printStackTrace();
	}
    } // end restore()

    /**
     * get force of light actors
     * 
     * @return
     */
    public Army getForceOfLight()
    {
	return this.forcesOfLight;
    }

    /**
     * get force of darkness actors
     * 
     * @return
     */
    public Army getForceOfDarkness()
    {
	return this.forcesOfDarkness;
    }

    /**
     * helps display list view window of actors
     */
    public final void buildListViewWindow()
    { // final because of its use in
      // the constructor
	final double PREF_WIDTH = 700.0;
	ListView<Actor> listView = new ListView<Actor>(this.forcesOfLight.getCollectionActors());
	listView.setPrefWidth(PREF_WIDTH);
	VBox vBoxLightArmy = new VBox(5.0, new Text(this.forcesOfLight.getName()), listView);
	listView = new ListView<Actor>(this.forcesOfDarkness.getCollectionActors());
	listView.setPrefWidth(PREF_WIDTH);
	VBox vBoxDarkArmy = new VBox(5.0, new Text(this.forcesOfDarkness.getName()), listView);
	HBox hBoxSceneGraphRoot = new HBox(5.0, vBoxLightArmy, vBoxDarkArmy);

	if (this.stageListControllerWindow != null)
	{
	    this.stageListControllerWindow.close();
	    this.stageListControllerWindow.setScene(null);
	}
	this.stageListControllerWindow = new Stage(StageStyle.UTILITY);
	this.stageListControllerWindow.initOwner(this.primaryStage);
	this.stageListControllerWindow.setScene(new Scene(hBoxSceneGraphRoot));
    } // end buildListViewWindow()

    /**
     * helps display table view windows for actors
     */
    public final void buildTableViewWindow()
    { // final because of its use in
      // the constructor
	final double PREF_WIDTH = 470.0;
	final double PREF_HEIGHT = 450.0;
	VBox vBoxLightArmy = new VBox(10.0, new Text(this.forcesOfLight.getName()),
		this.forcesOfLight.getTableViewOfActors());
	vBoxLightArmy.setPrefWidth(PREF_WIDTH);
	VBox vBoxDarkArmy = new VBox(10.0, new Text(this.forcesOfDarkness.getName()),
		this.forcesOfDarkness.getTableViewOfActors());
	vBoxDarkArmy.setPrefWidth(PREF_WIDTH);
	HBox hBoxSceneGraphRoot = new HBox(10.0, vBoxLightArmy, vBoxDarkArmy);
	hBoxSceneGraphRoot.setPrefWidth((PREF_WIDTH + 20) * 2);
	hBoxSceneGraphRoot.setPrefHeight(PREF_HEIGHT);
	if (this.stageTableControllerWindow != null)
	{
	    this.stageTableControllerWindow.close();
	    this.stageTableControllerWindow.setScene(null);
	}
	this.stageTableControllerWindow = new Stage(StageStyle.UTILITY);
	this.stageTableControllerWindow.initOwner(this.primaryStage);
	this.stageTableControllerWindow.setScene(new Scene(hBoxSceneGraphRoot));
    } // end buildTableViewWindow()
}