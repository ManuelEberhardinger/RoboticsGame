package application;
	
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.stage.Stage;
import model.Barrel;
import model.Cloud;
import model.LongValue;
import model.Player;
import model.Sprite;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
	        StackPane sky = createSky();
	        StackPane ground = createGround();
	        VBox vbox = new VBox(sky, ground);
	        Group root = new Group();
	        Canvas canvas = new Canvas(Constants.VISIBLE_WIDTH, Constants.HEIGTH);
	        root.getChildren().add(vbox);
	        root.getChildren().add(canvas);
	        vbox.toBack();
	        
	        Scene scene = new Scene(root, Constants.VISIBLE_WIDTH, Constants.HEIGTH);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			GraphicsContext gc = canvas.getGraphicsContext2D();
			
			// set key event handlers
			
	        List<String> input = new ArrayList<String>();

	        initializeEventhandlers(scene, input);
	        
	        String path = Paths.get(".").toAbsolutePath().normalize().toString();
	        Sprite crosshair = createCrosshair(path);
	        List<Cloud> clouds = createAllClouds(path);
	        List<Barrel> barrels = createAllBarrels(path);
	        Point2D coordinate = new Point2D(Constants.GAME_LENGTH / 2, Constants.GAME_WIDTH / 2);
			Player player = new Player(coordinate, 10.0, 0.0);
	        // Animation Handler
	        final LongValue lastNanoTime = new LongValue(System.nanoTime());
	        new AnimationTimer() {
				
				@Override
				public void handle(long currentNanoTime) {
					double elapsedTime = (currentNanoTime - lastNanoTime.value) / 1000000000.0;
	                lastNanoTime.value = currentNanoTime;
	                
	                gc.clearRect(0, 0, Constants.VISIBLE_WIDTH, Constants.HEIGTH);
	                
	                for (Cloud sprite : clouds ) {
	                	sprite.update(elapsedTime);
	                    sprite.render(gc);
	                }
	                
	                for(Barrel barrel : barrels) {
	                	barrel.render(gc, player.getCoordinates());
	                }
	            
	                crosshair.render(gc);
	                
				}
			}.start();
	        
			primaryStage.setTitle("Robotics");
			primaryStage.setScene(scene);
			primaryStage.show();
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	private StackPane createGround() {
		StackPane ground = new StackPane();
		ground.setBackground(
				new Background(
						new BackgroundFill(
								Color.GREEN, 
								CornerRadii.EMPTY, 
								Insets.EMPTY
								)));
		ground.setMinSize(Constants.VISIBLE_WIDTH, Constants.HEIGTH / 2);
		return ground;
	}

	private StackPane createSky() {
		StackPane sky = new StackPane();
		sky.setBackground(
				new Background(
						new BackgroundFill(
								Color.DEEPSKYBLUE, 
								CornerRadii.EMPTY, 
								Insets.EMPTY
								)));
		sky.setMinSize(Constants.VISIBLE_WIDTH, Constants.HEIGTH / 2);
		return sky;
	}

	private void initializeEventhandlers(Scene scene, List<String> input) {
		scene.setOnKeyPressed(
		    new EventHandler<KeyEvent>()
		    {
		        public void handle(KeyEvent e)
		        {
		            String code = e.getCode().toString();
		            if ( !input.contains(code) )
		                input.add( code );
		        }
		    });

		scene.setOnKeyReleased(
		    new EventHandler<KeyEvent>()
		    {
		        public void handle(KeyEvent e)
		        {
		            String code = e.getCode().toString();
		            input.remove( code );
		        }
		    });
	}

	private List<Cloud> createAllClouds(String path) throws FileNotFoundException {
		String cloudUrl = path + "/img/SingleCloud_0.png";
		FileInputStream cloudFile = new FileInputStream(cloudUrl);
		Image cloudImg = new Image(cloudFile);
		double cloudHeight = cloudImg.getHeight() / 2;
		List<Cloud> clouds = new ArrayList<>();
		for(int i = 0; i < 7; i++) {
			double px = Constants.VISIBLE_WIDTH * Math.random();
		    double py = (Constants.HEIGTH / 2) * Math.random() - cloudHeight;
		    if(py < cloudHeight) py = 5;
			Cloud cloud = new Cloud(cloudImg, px, py);
			cloud.setVelocity(5, 0);
			clouds.add(cloud);
		}
		return clouds;
	}

	private List<Barrel> createAllBarrels(String path) throws FileNotFoundException {
		String barrelUrl = path + "/img/barrel.png";
		FileInputStream barrelFile = new FileInputStream(barrelUrl);
		Image barrelImg = new Image(barrelFile);
		List<Barrel> barrels = new ArrayList<>();
		for(int i = 0; i < 100; i++) {
			double px = Constants.GAME_WIDTH * Math.random();
		    double py = Constants.GAME_LENGTH * Math.random() + 300;
		    Barrel barrel = new Barrel(barrelImg, px, py);
		    if(barrels.parallelStream().allMatch(b -> !b.intersects(barrel))) {
		    	barrels.add(barrel);
		    } else {
		    	i--;
		    };
		    
		}
		return barrels;
	}
	
	private Sprite createCrosshair(String path) throws FileNotFoundException {
		String crosshairUrl = path + "/img/crosshair.png";
		FileInputStream crosshairFile = new FileInputStream(crosshairUrl);
		Image crosshairImg = new Image(crosshairFile);
		Sprite crosshair = new Sprite(crosshairImg);
		crosshair.setSize(Constants.CROSSHAIR_SIZE, Constants.CROSSHAIR_SIZE);
		int x = (Constants.VISIBLE_WIDTH - Constants.CROSSHAIR_SIZE) / 2;
		int y = (Constants.HEIGTH - Constants.CROSSHAIR_SIZE) / 2 - 5;
		crosshair.setPosition(x, y);
		return crosshair;
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
