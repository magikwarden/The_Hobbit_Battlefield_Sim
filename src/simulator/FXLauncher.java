package simulator;

import java.io.FileInputStream;
import java.io.IOException;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class FXLauncher extends Application
{
    private Simulator simulator; // Must be a heap-oriented instance field so that MenuItem objects can make
				 // repeated calls to it.

    /**
     * Entry point to program execution for a JavaFX application
     * 
     * @param primaryStage Existing window that has been pre-built by the render
     *                     engine.
     */
    @Override
    public void start(Stage primaryStage) throws Exception
    {
	simulator = new Simulator(primaryStage); // Simulator HAS the two Army objects. It provides a communication path
						 // between the JavaFX GUI and the Battlefield logic.
	primaryStage.setTitle("The Hobbit Battlefield Simulator");
	primaryStage.setScene(createScene()); // The Scene contains an organized collection of ALL the JavaFX Node
					      // objects that are to be displayed on the screen.
	primaryStage.show(); // Once the objects have been assembled, the window can be opened (like raising
			     // the "curtain on a theatrical stage".
    } // end start()
      // program execution will drive through the start() method in microseconds . . .
      // then the program patiently waits for user-events to occur . . . responding
      // when necessary

    /**
     * Assembles the JavaFX Node objects that are to be displayed on the screen.
     * 
     * @return Scene is an object that contains an organized collection of ALL
     *         JavaFX Node objects that are to be displayed on the screen.
     */
    private Scene createScene()
    {
	ImageView imageViewBackground = createBackground(); // attempts to load a disk-based file into an Image object
							    // which is then wrapped inside an ImageView object (and an
							    // ImageView object can be added to a Scene)
	double aspectRatio = imageViewBackground.getImage().getHeight() / 
		imageViewBackground.getImage().getWidth(); // auto-adjust the window aspect-ratio based on the image.
	final double SCENEWIDTH = 1000.0;
	final Group simulatorContainer = new Group(imageViewBackground, simulator); // Order matters here. The
										    // imageViewBackground is first,
										    // thus on the bottom visually. The
										    // simulator sits on top of that.

	Group sceneGraphRoot = new Group(simulatorContainer, createMenuBar());// Order matters here. The
									      // simulatorContainer is first, thus on
									      // the bottom visually. The newly
									      // contructed MenuBar sits on top of that.
	Scene mainScene = new Scene(sceneGraphRoot, SCENEWIDTH, SCENEWIDTH * aspectRatio); // Scene needs the Parent
											   // Node (and a parent Node
											   // will have child Node
											   // objects). Scene also needs
											   // to know its initial size.
	imageViewBackground.fitWidthProperty().bind(mainScene.widthProperty()); // resize the Background automatically,
										// based on the Scene resizing

	return mainScene;
    } // end createScene()

    /**
     * The method is private since it is used only internally to class FXLauncher.
     * The method exists to make the code more readable. It attempts to load a
     * disk-based file and Create a JavaFX Node (called an ImageView) that can
     * display Image objects.
     * 
     * @return ImageView object that has been created from an Image object which was
     *         loaded from a disk-based file
     */
    private ImageView createBackground()
    {
	final String filename = "MiddleEarth-3.jpg"; // must reside in the project directory to support this relative
						     // pathname
	try (FileInputStream fileInputStream = new FileInputStream(filename))
	{ // try-catch block implemented to manage potential file loading issues.
	    Image imageBackground = new Image(fileInputStream);
	    ImageView imageViewBackground = new ImageView(imageBackground);
	    imageViewBackground.setPreserveRatio(true); // maintain aspect ratio
	    imageViewBackground.setManaged(false); // suppresses the automatic centering . . . facilitates management of
						   // layout for POI markers
	    return imageViewBackground; // SUCCESS, we now have the ImageView object. Send a reference-to this object
					// back to createScene()
	} catch (IOException exception)
	{
	    exception.printStackTrace();
	    System.err.println(exception.getMessage());
	    System.exit(0);
	}
	return null; // failed to load Image, thus return nothing . . .
    } // end createBackground()

    /**
     * The method is private since it is used only internally to class FXLauncher.
     * The method exists to make the code more readable.
     * 
     * @return
     */
    private MenuBar createMenuBar()
    {
	// Create the "File" Menu
	MenuItem save = new MenuItem("S_ave");
	save.setOnAction(event -> simulator.save());
	MenuItem restore = new MenuItem("Res_tore");
	restore.setOnAction(event -> simulator.restore());
	Menu menuFile = new Menu("_File");
	menuFile.getItems().addAll(save, restore);

	// Create the "Run" Menu
	MenuItem populateMenuItem = new MenuItem("_Populate");
	populateMenuItem.setOnAction(event -> simulator.populate()); // create CALLBACK, that is, the code to execute
								     // when triggered by user event (in this case,
								     // simulator.populate())
	MenuItem runMenuItem = new MenuItem("_Run");
	runMenuItem.setOnAction(event -> simulator.run()); // create CALLBACK, that is, the code to execute when
							   // triggered by user event (in this case, simulator.run())
	MenuItem suspendMenuItem = new MenuItem("_Suspend");
	suspendMenuItem.setOnAction(event -> simulator.suspend()); // create CALLBACK, that is, the code to execute when
								   // triggered by user event (in this case,
								   // simulator.suspend())
	MenuItem clearMenuItem = new MenuItem("_Clear");
	clearMenuItem.setOnAction(event -> simulator.clear());
	
	Menu menuRun = new Menu("_Run");
	menuRun.getItems().addAll(populateMenuItem, runMenuItem, suspendMenuItem, clearMenuItem); // assemble MenuItems in the "Run"
										   // Menu

	// Create the "Properties" Menu
	MenuItem openArmyListsMenuItem = new MenuItem("Show Army _Lists");
	openArmyListsMenuItem.setOnAction(event -> simulator.openListViewWindow()); // create CALLBACK, that is, the
										    // code to execute when triggered by
										    // user event (in this case,
										    // simulator.openListViewWindow())
	MenuItem openArmyTablesMenuItem = new MenuItem("Show Army _Tables");
	openArmyTablesMenuItem.setOnAction(event -> simulator.openTableViewWindow()); // create CALLBACK, that is, the
										      // code to execute when triggered
										      // by user event (in this case,
										      // simulator.openListViewWindow())
	Menu menuProperties = new Menu("_Properties");
	menuProperties.getItems().addAll(openArmyListsMenuItem, openArmyTablesMenuItem); // assemble MenuItems in the
											 // "Run" Menu

	// Assemble Menu objects in new MenuBar and return
	return new MenuBar(menuFile, menuRun, menuProperties);
    } // end createMenuBar()

    public static void main(String[] args)
    {
	launch(args);
    }
}