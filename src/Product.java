import java.io.IOException;

import javax.swing.JOptionPane;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class Product {
	
	String url;
	int i;
	//Constructor
	public Product(String url , int i){
	
		this.url = url;
		this.i = i;
	
	}
	//Gets the price of the product
	public String findPrice(){
		String price = "";
		try{
						
			Document doc  = Jsoup.connect(url).timeout(3000).get();
			price = doc.select("input[name=price]").first().attr("value");
		}catch(IOException| NullPointerException e){
			JOptionPane.showMessageDialog(null, "Couldn't connect to the " + i + ". product's website", "Error", JOptionPane.INFORMATION_MESSAGE);
			e.printStackTrace();
			
		}
		return price;	
	}
	//Gets the name of the product
	public String findName(){
		String name = "";
		
		try{
			Document doc = Jsoup.connect(url).timeout(3000).get();
			name = doc.select("input[name=productName]").first().attr("value");
		}catch(IOException| NullPointerException e){
			e.printStackTrace();
		}
		return name;
	}
	
}
