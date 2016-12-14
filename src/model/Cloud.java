package model;

import application.Constants;
import javafx.scene.image.Image;

public class Cloud extends Sprite {
	
    public Cloud(Image image, double positionX, double positionY) {
    	super(image);
    	this.setWidth(this.image.getWidth() / 2);
    	this.setHeight(this.image.getHeight() / 2);
    	this.positionX = positionX;
    	this.positionY = positionY;
    }
    
	@Override
	public void update(double time) {
		positionX += velocityX * time;
        positionY += velocityY * time;
        if(positionX > Constants.VISIBLE_WIDTH)
        	positionX = 0;
	}
}
