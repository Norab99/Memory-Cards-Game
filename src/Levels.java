import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class Levels extends Application {
	   public static void main(String[] args) {
	        launch(args);
	    }
	  
	private DoubleProperty time = new SimpleDoubleProperty();
    private BooleanProperty running = new SimpleBooleanProperty();
    private Button startStop = new Button();
	private int counter = 0;
    private Image images[]; 
    private Image imagesCopy[]; 
    private static final Image BACK = new Image("card/back.png");
    private Button buttons[];
    private int numImages = 2;
    private boolean firstClick = true; 
    private Button clickedButton;
    private int clickedButtonIndex; 
    private Label label = new Label(); 
    private boolean StopGame=false;
    private boolean continue_repeat=false; 
    private long StartButton;
    private long StopButton;
    private long ResumeTime;
    private double score =0;
    private String highscore = "";
    private Stage pop = new Stage(); // each page is a stage.
	private HomePage HP = new HomePage();
	private String Path = "/Users/norabasalamah/Desktop/University/7th Term/Java 2/Project/TEST/PT7/bin";
	private File f;
	private Stage PrimaryStage;
	private Media music;
	private MediaPlayer sound;
	
    public void loadImages() {
        
        images = new Image[numImages]; // the length of images.
        for (int i = 0; i < images.length; i++) {
        	
            images[i] = new Image(("image/" + (i + 1) + ".png"));
          
        } // assign images to array.
    }
    

    /**
     * method to setup the buttons
     */
    public void setupButtons() {
    	
    	label.textProperty().bind(time.asString("Time: %.3f Seconds"));
    	label.setTextFill(Color.WHITE);
    	label.setPadding(new Insets(20));
    	label.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
        score = score+time.doubleValue(); // score cumulative from a level to another.

    	    	AnimationTimer timer = new AnimationTimer() {

    	            private long startTime ;
//add comment.
    	            @Override
    	            public void start() {
    	            	if (continue_repeat==false) {
    	               startTime = System.currentTimeMillis();
    	                running.set(true);
    	                super.start();} // override method.
    	            	else {
    	            		ResumeTime=(StartButton-StopButton);
    	            		startTime=ResumeTime+startTime;
    	            		running.set(true);
    	            		super.start();
    	            	}
    	            		
    	            }

    	            @Override
    	            public void stop() {
    	                running.set(false);
    	                super.stop(); // override method.
    	            }

    	            @Override
    	            public void handle(long timestamp) {
    	                long now = System.currentTimeMillis();
    	                time.set((now - startTime) / 1000.0);
    	            }
    	        };
    	        
    	  	   startStop.textProperty().bind(
    	  	 Bindings.when(running)
    	  	    .then("Stop")
    	  	          .otherwise("Start"));
// naming the button start/stop.
    	  	    startStop.setOnAction(e -> {
    	  	    	f = new File(Path+"/card/Play.mp3");   
    	        	music = new Media(f.toURI().toString());
    	        	sound = new MediaPlayer(music);
    	        	sound.play();
    	  	    if (running.get()) {
    	  	    	StopGame=true;
    	  	       timer.stop();
    	  	       continue_repeat = true; // when the stop button is pressed it continues and doesn't restart.
    	  	     StopButton = System.currentTimeMillis(); // the time when the button was pressed to stop the game.
    	  	        } else {
    	  	        	StopGame=false; 
    	  	        	StartButton = System.currentTimeMillis(); // the time when the button was pressed to resume the game.
    	  	          timer.start();
  	               

    	  	        continue_repeat=false; // when the start button is pressed it repeats the time from zero.
    	  	      }
    	  	    // start time = current time. 
    	  	    // running action is stop , start , stop.
    	  	     });
    	  	

        buttons = new Button[numImages * 2]; // we need double the no of buttons.
        /**
         * another array to store the shuffled images. This array will have all
         * images from original array twice.
         */
        imagesCopy = new Image[numImages * 2];
        //copying array (images) twice.
        System.arraycopy(images, 0, imagesCopy, 0, images.length);// to take the images from image to image copy.
        System.arraycopy(images, 0, imagesCopy, images.length, images.length);
        
        //system.arraycopy(the obj i want to copy, start index, obj that will take the copy, copied to start index, the length to stop copying);
        //image copy must have all the 16 copies of the images.
        /**
         * using Collections, shuffling the array
         */
        List<Image> list = Arrays.asList(imagesCopy);
        Collections.shuffle(list);
        imagesCopy = (Image[]) list.toArray();
        // every time the game runs the images indexes will change.
        // return the shuffled images into image copy.
        /**
         * setting up the buttons, making icons not visible when first ran.
         */
        if (highscore.equals("")) {
            highscore = this.getHighScore();
            } // gets the first score and set it as the high score until there is less than it.

        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new Button();
            buttons[i].setMinHeight(100);
            buttons[i].setMaxHeight(100);
            buttons[i].setMinWidth(100);
            buttons[i].setMaxWidth(100);
            buttons[i].setGraphic(new ImageView(BACK));
           // buttons[i].setGraphic(new ImageView(imagesCopy[i]));
          //  buttons[i].getGraphic().setVisible(false); // when starting the program the picture won't be visible.
            final int btnIndex = i;
            buttons[i].setOnAction(e ->{handleClick(btnIndex);
            f = new File(Path+"/card/Open.mp3");   
        	music = new Media(f.toURI().toString());
        	sound = new MediaPlayer(music);
        	sound.play();
            if (counter==numImages+1) {
            	//+1 because the counter stated with 1 not 0.
   	         timer.stop(); // the game finished
   	        if (numImages<16) {
   	        f = new File(Path+"/card/Next.mp3");   
        	music = new Media(f.toURI().toString());
        	sound = new MediaPlayer(music);
        	sound.play();
   	        NextLevel();
   	        }
   	        if (numImages==16) {
   	        CheckerScore ();}
   	        }
            else if (counter==0) {
   	          timer.start();
   	          counter = 1; // avoid restarting the counter when pressing each button.
   	      }
            // the condition adresses the time starts the time when 0 and end when all 8 images are shown.
            });

        }
    } 
    
    /**
     * method to add buttons to the grid pane
     */
    public void addButtons(GridPane p){
        p.getChildren().clear(); // clear the pane.
        int i = 0, col = 0, row = 0;
        //adding all buttons, 4 in a row
        while (i < buttons.length) {
            p.add(buttons[i], col, row);
            col++;
            if (numImages==2) {
                if (col == 2) {
                    col = 0;
                    row++;
                }
                }
            if (numImages==4||numImages==8) {
            if (col == 4) {
                col = 0;
                row++;
            }
            }
 
            else if (numImages==12) {
                if (col == 6) {
                    col = 0;
                    row++;
                }
                }
            else  {
                if (col == 8) {
                    col = 0;
                    row++;
                }
                }
            i++; // until 15.
            
            
        }
        
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
    	PrimaryStage=primaryStage;
        //loading images calling it's method.
        loadImages();
        //setting up buttons by calling it's method.
        setupButtons();
        //using grid pane to arrange elements
        GridPane pane = new GridPane();
        pane.setPadding(new Insets(20));
        addButtons(pane);
        
    	Button BacktoMenu= new Button ("Back To Menu");


        //adding startStop button layout
        startStop.setStyle("-fx-background-color: Gold");
        startStop.setFont(Font.font("Verdana", FontWeight.BOLD, 14));
        startStop.setTextFill(Color.INDIGO);
        startStop.setPrefHeight(40);
        startStop.setPrefWidth(100);
        BacktoMenu.setStyle("-fx-background-color: LIGHTCYAN");
        BacktoMenu.setFont(Font.font("Verdana", FontWeight.BOLD, 13));
        BacktoMenu.setTextFill(Color.INDIGO);
        BacktoMenu.setPrefHeight(40);
        BacktoMenu.setPrefWidth(130);
        BacktoMenu.setOnAction(e-> {
        	f = new File(Path+"/card/Play.mp3");   
        	music = new Media(f.toURI().toString());
        	sound = new MediaPlayer(music);
        	sound.play();
        	primaryStage.close();
        	try {
				HP.start(primaryStage);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        });
       
        
        //using an Hbox, adding the button
        HBox hBox = new HBox(startStop,BacktoMenu);
        hBox.setSpacing(20);
        hBox.setAlignment(Pos.CENTER);
        
        Rectangle r1=new Rectangle(6,6,985,635);
	     r1.setFill(Color.LIGHTCYAN);
	     r1.setStroke(Color.BLACK);
	     Rectangle r2=new Rectangle(14,14,969,619);
	     r2.setFill(Color.INDIGO);
	     r2.setStroke(Color.BLACK);
	     BorderPane bpane = new BorderPane();
   	     bpane.getChildren().add(r1);
   	     bpane.getChildren().add(r2);
   
   	    pane.setAlignment(Pos.CENTER);
        BorderPane bp = new BorderPane();
        bpane.setBottom(hBox);
        bpane.setMargin(hBox, new Insets(20));
        bpane.setCenter(pane);
        bpane.setTop(label);
        bpane.setAlignment(pane, Pos.CENTER);
        bpane.setAlignment(label, Pos.CENTER);
        Scene scene = new Scene(bpane,1000,650);
        primaryStage.setScene(scene);
        primaryStage.setTitle("MEMORY GAME");
        primaryStage.show();
        
    }

    /**
     * method to handle button click
     *
     * @param btnIndex index of clicked button
     */
    
    public void handleClick(int btnIndex) {
    	if (StopGame==false) {
        Button b = buttons[btnIndex]; // the index of the buttons that are clicked.
        
        if (firstClick==true) {
            //first click
            firstClick = false;
            //any click after entering the condition will not be the first click so the 1st clock will be false.
            clickedButton = b; // b is the button that is 1st clicked.
            clickedButtonIndex = btnIndex; // 
            b.setGraphic(new ImageView(imagesCopy[btnIndex]));
        } else {
            //second click, checking if first and second buttons are same
            if (imagesCopy[btnIndex].equals(imagesCopy[clickedButtonIndex])
                    && btnIndex != clickedButtonIndex) {
            	// the image in this button is equal to the image in the 1st click but the same button wasn't clicked twice.
                //same, making buttons disabled to prevent further clicks
                b.getGraphic().setVisible(true);
                b.setDisable(true); 
                b.setGraphic(new ImageView(imagesCopy[btnIndex]));
                clickedButton.setDisable(true);
                // disabling the option to click the same buttons that were true again.
                counter++; // if 2 images are correct add the counter to reach till 8 correct images and stop.
                
            } else {
                //not the same picture.
            	 //not the same picture.
                clickedButton.setGraphic(new ImageView(BACK));
				//clickedButton.getGraphic().setVisible(false);
                clickedButton.setDisable(false);
            }
            firstClick = true;
            // return the 1st click true to re-run the code.
        }
    }
    }
 public String getHighScore() {
	 // name:time
	 FileReader reader = null; // to read the file that contain all times.
	 BufferedReader getReadValue = null; // to take the value from reader.
	 try {
		reader = new FileReader("highscore.dat");
		 getReadValue = new BufferedReader(reader); // a buffer that gets the value from the reader.
		 return getReadValue.readLine();
	} catch (Exception e) {
		return "NO_WINNERS:1000";
		// TODO Auto-generated catch block
	}
	 finally {
		    try {
		    if (getReadValue != null)
		    	getReadValue.close();
		}
		    catch (IOException e) {
		e.printStackTrace();
		}
	 }
	 }
 
		    public void CheckerScore() { 
		    	Stage popupwindow = new Stage();
		    	Stage start = new Stage();
		    	Text text1 = new Text();
		    	text1.setTextAlignment(TextAlignment.CENTER);
		        Insets insets = new Insets(10);
		        TextField tf = new TextField("Enter Your name");
		        tf.setMaxWidth(140);
		        Button enter = new Button("Done");
		        enter.setStyle("-fx-background-color: LIGHTCYAN");
				enter.setBackground(null);
		        enter.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
		        enter.setTextFill(Color.INDIGO);
		        enter.setPrefHeight(30);
		        enter.setPrefWidth(55);
		        
		       
		        VBox.setMargin(tf, insets);
		        VBox.setMargin(enter, insets);
		score = score + time.doubleValue();

		    if (score < Double.parseDouble((highscore.split(":")[1]))) {
		    	f = new File(Path+"/card/finish_h.mp3");   
	        	music = new Media(f.toURI().toString());
	        	sound = new MediaPlayer(music);
	        	sound.play();
		    	text1.setText("YOU FINISHED THE GAME\n"+"CONGRATS \nYOU BROKE THE HIGH SCORE!!");
		    	text1.setFont(Font.font("Verdana", FontWeight.BOLD,FontPosture.ITALIC, 14));
				text1.setFill(Color.GOLD);
		        VBox V = new VBox(text1,tf,enter);
		        V.setAlignment(Pos.CENTER);
		    enter.setOnAction(event -> {
		    	f = new File(Path+"/card/Play.mp3");   
	        	music = new Media(f.toURI().toString());
	        	sound = new MediaPlayer(music);
	        	sound.play();
		    String name = tf.getText();
		    highscore = name +":" + score;
		    File scorefile = new File("highscore.dat");
		        if (!scorefile.exists()) {
		    try {
		    scorefile.createNewFile();
		    } catch (IOException e) {
		    e.printStackTrace();
		    }
		        }
		        FileWriter writefile = null;
		        BufferedWriter writer = null;
		        try {
		        writefile = new FileWriter(scorefile); 
		        writer = new BufferedWriter(writefile);
		        writer.write(this.highscore);
		        }
		        catch (Exception e) {
		        // error
		        }
		        finally {
		    try {
		    if (writer != null)
		    writer.close();
		    } catch (Exception e) {
		    }
		        }
		        
		    popupwindow.close();
		    try {
				HP.start(start);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        	PrimaryStage.close();

		    });

		    Rectangle r1=new Rectangle(6,6,312,240);
		     r1.setFill(Color.LIGHTCYAN);
		     r1.setStroke(Color.BLACK);
		     Rectangle r2=new Rectangle(14,14,294,224);
		     r2.setFill(Color.INDIGO);
		     r2.setStroke(Color.BLACK);
		     BorderPane bpane = new BorderPane();
	    	bpane.getChildren().add(r1);
	    	bpane.getChildren().add(r2);
	    	bpane.setCenter(V);
           
		    Scene scene1 = new Scene(bpane,320,250);
		    popupwindow.setScene(scene1);
		    popupwindow.show();
		    }
		    
		    else {
		    	f = new File(Path+"/card/finish_n.mp3");   
	        	music = new Media(f.toURI().toString());
	        	sound = new MediaPlayer(music);
	        	sound.play();
		    	text1.setText("YOU FINISHED THE GAME\n"+"PLAY AGAIN LATER \nTO BREAK THE HIGH SCORE WHICH WAS:\n" + highscore);
		    	text1.setFont(Font.font("Verdana", FontWeight.BOLD,FontPosture.ITALIC, 14));
				text1.setFill(Color.GOLD);
				
		    	

	        enter.setOnAction(event -> {
	        	popupwindow.close();
	        	try {
					HP.start(start);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        	PrimaryStage.close();
	        });
	        
	        VBox V = new VBox(text1,enter);
	        V.setAlignment(Pos.CENTER);
	        
	       Rectangle r1=new Rectangle(6,6,435,290);
		     r1.setFill(Color.LIGHTCYAN);
		     r1.setStroke(Color.BLACK);
		     Rectangle r2=new Rectangle(14,14,420,275);
		     r2.setFill(Color.INDIGO);
		     r2.setStroke(Color.BLACK);
		     BorderPane bpane = new BorderPane();
	    	bpane.getChildren().add(r1);
	    	bpane.getChildren().add(r2);
            bpane.setCenter(V);
		    Scene scene1 = new Scene(bpane,445,300);
		    popupwindow.setScene(scene1);
		    popupwindow.show();
		    }
		    
		    }
		    public void NextLevel() {
		    	Stage NextStage = new Stage ();
		    	//Stage p = new Stage();
		    	Button BacktoMenu1= new Button ("Back To Menu");

		    	int i=0;
		    	if (numImages==2)
		    	i=1;
		    	else if (numImages==4)
		    		i=2;
		    	else if (numImages==8)
		    		i=3;
		    	else if (numImages==12)
		    		i=4;
		    	else
		    		i=5;
		    	Label text = new Label("LEVEL "+i+" COMPLETED");
		    	text.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
		    	text.setTextFill(Color.GOLD);
		    	
		    	
		    	Button Next_Level = new Button("Next Level");
		    	Next_Level.setStyle("-fx-background-color: GOLD");
		    	Next_Level.setBackground(null);
		    	Next_Level.setFont(Font.font("Verdana", FontWeight.BOLD, 13));
		    	Next_Level.setTextFill(Color.INDIGO);
		    	Next_Level.setPrefHeight(40);
		    	Next_Level.setPrefWidth(120);
		    	
		    	
		    	if (numImages<16) {
		    		
		    		NextStage.show();
		    		
		    	
		    	Next_Level.setOnAction(e->{
		    		f = new File(Path+"/card/Play.mp3");   
		        	music = new Media(f.toURI().toString());
		        	sound = new MediaPlayer(music);
		        	sound.play();
		    		try {
		    			counter=0;
		    			if (numImages==2)
		    				numImages = numImages*2;
		    		    	else 
		    		    		numImages = numImages+4;
			    		PrimaryStage.close();
			    		
						start(pop);
					
						
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
		    		NextStage.close();
		    	});

		    	BacktoMenu1.setPrefHeight(40);
		    	BacktoMenu1.setPrefWidth(130);
		        BacktoMenu1.setOnAction(e-> {
		        	f = new File(Path+"/card/Play.mp3");   
		        	music = new Media(f.toURI().toString());
		        	sound = new MediaPlayer(music);
		        	sound.play();
		        	PrimaryStage.close();
		        	NextStage.close();
		        	try {
						HP.start(pop);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
		        });
		    	}
		    	BacktoMenu1.setStyle("-fx-background-color: LIGHTCYAN");
		        BacktoMenu1.setFont(Font.font("Verdana", FontWeight.BOLD, 13));
		        BacktoMenu1.setTextFill(Color.INDIGO);
		        BacktoMenu1.setPrefHeight(40);
		        BacktoMenu1.setPrefWidth(130);
		    	 Rectangle r1=new Rectangle(6,6,292,234);
			     r1.setFill(Color.LIGHTCYAN);
			     r1.setStroke(Color.BLACK);
			     Rectangle r2=new Rectangle(14,14,276,218);
			     r2.setFill(Color.INDIGO);
			     r2.setStroke(Color.BLACK);
			     BorderPane bpane = new BorderPane();
		    	bpane.getChildren().add(r1);
		    	bpane.getChildren().add(r2);
		    	bpane.setCenter(text);
		    	HBox hbox2 = new HBox (Next_Level,BacktoMenu1);
		    	hbox2.setSpacing(20);
		    	hbox2.setAlignment(Pos.CENTER);
		    	bpane.setBottom(hbox2);
		    	bpane.setPadding(new Insets(20));
		      Scene sence2 = new Scene(bpane,300,250);
		      NextStage.setX(500);
		      NextStage.setY(220);
		      NextStage.setScene(sence2);
		    }
		    }
		    