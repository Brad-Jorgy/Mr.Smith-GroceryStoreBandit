package cs3450;

import javax.swing.SwingUtilities;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Arrays;

//import com.mongodb.*;
//import com.mongodb.client.MongoCollection;
//import com.mongodb.client.MongoDatabase;
//import com.mongodb.DBCollection;
//import javax.xml.parsers.DocumentBuilder;
//import javax.xml.parsers.DocumentBuilderFactory;
//import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import javax.swing.JFrame;
//import javax.swing.text.Document;

//import MainScreen;

public class Main {
	public static JFrame frame = new JFrame("Store Management System");

	public static void startProgram()
	{
		MongoClient client = new MongoClient(new MongoClientURI("mongodb://brad:bikerman@ds115870.mlab.com:15870/grocery"));
		try {
			MongoDatabase db = client.getDatabase("grocery");
			MongoCollection <Document> collection = db.getCollection("items");
			collection.insertOne(new Document("authors", new Document().append("author_id", "1").append("name", "Chetan Bhagat"))
					.append("book_id", "1").append("title", "One Indian Girl").append("isbn", "8129142147")
					.append("price", "$14.99"));
			collection.find();
		}catch(Exception e){
			e.printStackTrace();
		}

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setSize(new Dimension(1000,550));
		MainScreen.addComponentsToPane(frame.getContentPane());
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	public static void showMainScreen()
	{
		frame.getContentPane().removeAll();
		MainScreen.addComponentsToPane(frame.getContentPane());
		updateFrame();
	}

	public static void showInventoryScreen()
	{
		frame.getContentPane().removeAll();
		InventoryScreen.addComponentsToPane(frame.getContentPane());
		updateFrame();
	}

	public static void showCheckoutScreen()
	{
		frame.getContentPane().removeAll();
		CheckoutScreen.addComponentsToPane(frame.getContentPane());
		updateFrame();
	}

	public static void showPaymentScreen()
	{
		frame.getContentPane().removeAll();
		PaymentScreen.addComponentsToPane(frame.getContentPane());
		updateFrame();
	}


	public static void main(String[] args) {
		//System.out.print("start");
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				new Main().startProgram();
		}});


	}

	public static void updateFrame(){
		frame.getContentPane().repaint();
		frame.getContentPane().revalidate();
	}
}
