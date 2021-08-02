package Concentration;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import hsa2.GraphicsConsole;

public class ConcentrationGame {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new ConcentrationGame();
	}

	static final int WINW 		= 1024;
	static final int WINH 		= 768;
	static final int MARGINLEFT	= 60;
	static final int MARGINTOP	= 90;
	
	Random rand = new Random();
	
	GraphicsConsole gc = new GraphicsConsole (WINW,WINH, "Galaga Ship");
	GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
	BufferedImage imgSprites=null;
	Point p = new Point();
	int iBoxSizeW=(int)((WINW-(2*MARGINLEFT))/5);
	int iBoxSizeH=(int)((WINH-(2*MARGINTOP))/5);
	int[][] arrBoard = new int[6][6];
	
	public ConcentrationGame(){
		setup();
		drawBoard();
		loadRebus();
		
		for(int j=0;j<25;j++) {
			pickSpot(p);
			showSquare(p.x,p.y);
			gc.sleep(500);
		}
	}
	
	void drawBoard() {
		int iPosX, iPosY=0;
		
		
		gc.setBackgroundColor(Color.BLUE);
		gc.clear();
		
		gc.setColor(Color.RED);
		gc.drawString("CONCENTRATION", 200-2, 64-2);
		gc.setColor(Color.CYAN);
		gc.drawString("CONCENTRATION", 200+2, 64+2);
		gc.setColor(Color.YELLOW);
		gc.drawString("CONCENTRATION", 200, 64);
		
		iPosX=MARGINLEFT;
		iPosY=MARGINTOP;
		gc.setColor(Color.RED.darker().darker());
		gc.fillRect(iPosX-4, iPosY-4, iBoxSizeW*5+8, iBoxSizeH*5+8);
		gc.setColor(Color.RED);
		gc.fillRect(iPosX, iPosY, iBoxSizeW*5, iBoxSizeH*5);
		
		gc.setColor(Color.YELLOW.darker().darker().darker().darker().darker());
		for(int iCol=0;iCol<5;iCol++) {			
			gc.fillRect(MARGINLEFT+iCol*iBoxSizeW-4, MARGINTOP, 8, iBoxSizeH*5);
			gc.sleep(150);
		}
		for(int iRow=0;iRow<=5;iRow++) {	
			gc.fillRect(MARGINLEFT, MARGINTOP+iRow*iBoxSizeH-4, iBoxSizeW*5, 8);
			gc.sleep(150);
		}
		int iBoxID=0;
		
		for(int iRow=0;iRow<5;iRow++) {
			for(int iCol=0;iCol<5;iCol++) {
				arrBoard[iCol][iRow]=0; 
				gc.sleep(50);
				iBoxID++;
				iPosX=MARGINLEFT+20+(iBoxSizeW)*iCol;
				iPosY=MARGINTOP+20+(iBoxSizeH)*iRow;
				gc.setColor(Color.YELLOW);
				gc.fillRect(iPosX, iPosY, iBoxSizeW-40, iBoxSizeH-40);
				gc.setColor(Color.RED);
				gc.fillOval(iPosX-15, iPosY-15, 30, 30);
				gc.fillOval(iPosX+iBoxSizeW-55, iPosY-15, 30, 30);
				gc.fillOval(iPosX+iBoxSizeW-55, iPosY+iBoxSizeH-55, 30, 30);
				gc.fillOval(iPosX-15, iPosY+iBoxSizeH-55, 30, 30);
				if(iBoxID<10) {
					gc.drawString(iBoxID+"", iPosX+50, iPosY+64);
				} else {
					gc.drawString(iBoxID+"", iPosX+30, iPosY+64);
				}
			}
		}
		
	}
	
	void setup() {
		gc.setAntiAlias(true);
		p.x=0;
		p.y=0;
		// Setup Font
		try {
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("coolvetica rg.ttf"))); //file goes in root of project
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// Load Font
		Font font = new Font("ARCADECLASSIC", Font.BOLD, 70); // Requirement -7-
		gc.setFont(font);
	}
	
	void loadRebus() {
		try { // must be wrapped in a 'try' statement
			imgSprites = ImageIO.read(getClass().getClassLoader().getResource("Rebus.png")); // .jpg must be in 'bin' folder
		}catch(IOException ex) {
			System.out.println("Image cannot be loaded");
		}
	}
	
	void showSquare(int iRow,int iCol) {
		BufferedImage spriteImage;
		
		int iX=(iCol-1)*iBoxSizeW+MARGINLEFT+4;
		int iY=(iRow-1)*iBoxSizeH+MARGINTOP+4;
		
		spriteImage = imgSprites.getSubimage(1+(iCol-1)*iBoxSizeW-1,(iRow-1)*iBoxSizeH+1,iBoxSizeW-7,iBoxSizeH-8);
		
		Image spriteBigImage = null;
		
		spriteBigImage = spriteImage.getScaledInstance(iBoxSizeW-7,iBoxSizeH-8, spriteImage.TYPE_BYTE_INDEXED);
		
		gc.drawImage(spriteBigImage,iX,iY);
	}
	
	void pickSpot(Point p) {
		p.x=0;
		p.y=0;
		while(true) {
			p.x=(int)(Math.random()*5)+1;
			p.y=(int)(Math.random()*5)+1;
			if(arrBoard[p.x][p.y]==0) {
				arrBoard[p.x][p.y]=1;
				break;
			};
		}
		System.out.println(p.x+","+p.y);
	}

}
