
import java.io.File;
import javafx.animation.ScaleTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
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
import javafx.util.Duration;

public class HomePage extends Application {
 
	

	public static void main(String[] args) {
	
		// TODO Auto-generated method stub
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		
		File f = new File("/Users/norabasalamah/Desktop/University/7th Term/Java 2/Project/TEST/PT7/bin/card/Play.mp3");   
		Media music = new Media(f.toURI().toString());
		MediaPlayer sound = new MediaPlayer(music);
		
		// TODO Auto-generated method stub
		Levels one = new Levels();
		HelpStage helpstage = new HelpStage();

		DropShadow dropShadow = new DropShadow();
        dropShadow.setOffsetX(8.0f);
        dropShadow.setOffsetY(10.0f);
		
		Text MemoryGame = new Text ("MEMORY GAME");
		MemoryGame.setFont(Font.font("Verdana", FontWeight.BOLD, 46));
		MemoryGame.setFill(Color.GOLD);
		MemoryGame.setEffect(dropShadow);
		Button Help = new Button ("HELP");
		Help.setStyle("-fx-background-color: LIGHTCYAN");
		Help.setBackground(null);
        Help.setFont(Font.font("Verdana", FontWeight.BOLD, 16));
        Help.setTextFill(Color.INDIGO);
        Help.setPrefHeight(40);
        Help.setPrefWidth(100);
		Help.setOnAction(e ->{ 
			sound.play();
			primaryStage.close();
        	try {
        		helpstage.start(primaryStage);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        });

		Button Play = new Button ("PLAY");
		Play.setStyle("-fx-background-color: Gold");
		Play.setBackground(null);
        Play.setFont(Font.font("Verdana", FontWeight.BOLD, 16));
        Play.setTextFill(Color.INDIGO);
        Play.setPrefHeight(40);
        Play.setPrefWidth(100);
		Play.setOnAction(e ->{
			sound.play();
			primaryStage.close();
			try {
				one.start(primaryStage);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		} );
		sound.pause();
		
		 Rectangle r1=new Rectangle(7,4,485,490);
	     r1.setFill(Color.LIGHTCYAN);
	     r1.setStroke(Color.BLACK);
	     Rectangle r2=new Rectangle(15,12,469,474);
	     r2.setFill(Color.INDIGO);
	     r2.setStroke(Color.BLACK);
	     
	    ImageView background = new ImageView("card/card.jpg");
	    background.setFitWidth(270);
	    background.setFitHeight(150);
	    ScaleTransition logo=new ScaleTransition(Duration.seconds(2),background);
	    logo.setFromX(3);
	    logo.setByX(100);
	    logo.setFromY(1);
	    logo.setByY(80);
	    logo.setToX(1.5);
	    logo.setToY(1.5);
	    logo.play();
	    
	   Text highestscore=new Text("Highest Score: ");
	   highestscore.setStyle("-fx-background-color: Gold");
	   highestscore.setFont(Font.font("Verdana", FontWeight.BOLD, 16));
	   highestscore.setFill(Color.LIGHTCYAN);
	   highestscore.setX(195);
	   highestscore.setY(190);
	  
	        
		Label highscore= new Label ();
		highscore.setText(one.getHighScore());
		highscore.setTextFill(Color.WHITE);
		highscore.setFont(Font.font("Verdana", FontWeight.BOLD, 14));
		BorderPane pane=new BorderPane ();
		BorderPane bp=new BorderPane();
		bp.getChildren().add(MemoryGame);
		MemoryGame.setTranslateX(50);
		MemoryGame.setTranslateY(100);
		bp.getChildren().add(r2);
		r2.toBack();
		bp.getChildren().add(r1);
		r1.toBack();
		bp.getChildren().add(background);
		background.setTranslateX(110);
		background.setTranslateY(260);
		bp.getChildren().add(highestscore);
		pane.setTop(bp);
		bp.toBack();
		pane.setCenter(highscore);
		HBox hbox = new HBox(Play,Help);
		hbox.setSpacing(10);
		hbox.setAlignment(Pos.CENTER);
		pane.setBottom(hbox);
		pane.setMargin(hbox, new Insets(20));
	
		pane.setOnKeyPressed(event -> {
			switch (event.getCode()) {
			case ESCAPE: primaryStage.close();
			break;
			}
		});
		
		
		Scene scene = new Scene (pane,500,500);
		primaryStage.setTitle("Home Page");
		primaryStage.setScene(scene);
		primaryStage.show();
		
		pane.requestFocus();
	}

}

class HelpStage{
	public void start (Stage primaryStage) {
	
		File f = new File("/Users/norabasalamah/Desktop/University/7th Term/Java 2/Project/TEST/PT7/bin/card/Play.mp3");   
		Media music = new Media(f.toURI().toString());
		MediaPlayer sound = new MediaPlayer(music);
		
		HomePage HP = new HomePage();
		BorderPane pane = new BorderPane();
		Stage HelpStage = new Stage();
		Text text = new Text();
		Text text2 = new Text();
		Text text3 = new Text("How To Play the Game:\n");


    	text.setText("In memory cards game the player will have five levels to complete the game with each level the difficulty increases.\n\n"
    			+ "The game starts when the player press the play button and the timer begins counting when the cards starts flibing."
    			+ " When two identical cards appears they will be not flibed back again and the player will start flibbing other cards."
    			+ " But if the two cards are not identical then they will be flibbed back again.\n\n"
    			+ "The game will continue untill the player matches all identical cards, then the timer will stop and the next level will begin. "
    			 + "When the player finish all levels, the total score will appear."
    			 + "\n\n "
                );
    	text.setFill(Color.LIGHTCYAN);
    	text.setFont((Font.font("Verdana",FontWeight.BOLD,12)));
    	text.setWrappingWidth(452);

    	text2.setText("For more information please contact the game developers via email: danaldahiry@gmail.com");
    	text2.setFill(Color.LIGHTCYAN);
    	text2.setFont((Font.font("Verdana",FontWeight.BOLD,FontPosture.ITALIC,12)));
    	text2.setWrappingWidth(452);
    	text2.setTextAlignment(TextAlignment.CENTER);

    	text3.setTextAlignment(TextAlignment.CENTER);
    	text3.setFill(Color.GOLD);
    	text3.setFont((Font.font("Verdana",FontWeight.BOLD,FontPosture.ITALIC,14)));
    	text3.setWrappingWidth(452);
    		
    	VBox vbox= new VBox();
    	vbox.getChildren().addAll(text3,text,text2);
    	pane.setTop(vbox);
    	
    	ImageView cardpic = new ImageView ("card/card.jpg");
    	cardpic.setFitHeight(130);
    	cardpic.setFitWidth(330);

    	Button BacktoMenu= new Button ("Back To Menu");
    	BacktoMenu.setStyle("-fx-background-color: Gold");
    	BacktoMenu.setBackground(null);
    	BacktoMenu.setFont(Font.font("Verdana", FontWeight.BOLD,14));
    	BacktoMenu.setTextFill(Color.INDIGO);
    	BacktoMenu.setPrefHeight(40);
    	BacktoMenu.setPrefWidth(130);
    	BacktoMenu.setOnAction(e-> {
    		sound.play();
        	HelpStage.close();
        	try {
				HP.start(HelpStage);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        });
 
    	Rectangle r1 =new Rectangle(10,3,480,494);
    	r1.setFill(Color.LIGHTCYAN);
    	r1.setStroke(Color.BLACK);
    	Rectangle r2 =new Rectangle(18,10, 464,480);
    	r2.setFill(Color.INDIGO);
    	r2.setStroke(Color.BLACK);
    	BorderPane pane2 = new BorderPane();
    	pane2.getChildren().addAll(r1,r2,pane);
    	pane.setBottom(BacktoMenu);
		pane.setCenter(cardpic);
    	pane.setAlignment(BacktoMenu,Pos.CENTER);
    	BorderPane.setMargin(BacktoMenu, new Insets(10));    	
    	pane.setPadding(new Insets(27));
    	pane.setStyle("-fx-background-color: White");
    	Scene scene = new Scene (pane2,500,500);
		HelpStage.setTitle("Help");
		HelpStage.setScene(scene);
		HelpStage.show();
	}
		
}
