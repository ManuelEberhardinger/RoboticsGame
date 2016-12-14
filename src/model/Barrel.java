package model;

import application.Constants;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Barrel extends Sprite {

	public Barrel(Image image, double positionX, double positionY) {
		super(image);
    	this.setWidth(this.image.getWidth() / 4);
    	this.setHeight(this.image.getHeight() / 4);
    	this.positionX = positionX;
    	this.positionY = positionY;
	}
	
	@Override
	public void render(GraphicsContext gc) {
		gc.drawImage( image, positionX, positionY, this.getWidth(), this.getHeight() );		
	}
	
	public void render(GraphicsContext gc, Point2D point) {
		if(positionX > 1400 && positionX < 2600 && positionY > 2300 && positionY < 2600)
			System.out.println();
		double translatedY = positionY - point.getY();
		boolean yIsVisible = translatedY > Constants.HEIGTH / 2 && translatedY <= Constants.HEIGTH;
		double translatedX = positionX - point.getX();
		boolean xIsVisible = translatedX > -(Constants.VISIBLE_WIDTH/2) && translatedX <= Constants.VISIBLE_WIDTH / 2;
		if(xIsVisible && yIsVisible) {
			double s = (translatedY) / (Constants.HEIGTH/2);
			gc.drawImage(this.image, translatedX, translatedY, this.getWidth() * s, this.getHeight() * s);
		}
	}
}
