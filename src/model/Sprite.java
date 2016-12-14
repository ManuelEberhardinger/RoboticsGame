package model;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Sprite {
    protected final Image image;
    protected double positionX;
    protected double positionY;    
    protected double velocityX;
    protected double velocityY;
    protected double width;
    protected double height;
    
    public Sprite(Image image) {
    	this.image = image;
    }
	
    public Sprite(Image image, double positionX, double positionY) {
    	this(image);
    	this.setWidth(image.getWidth());
    	this.setHeight(image.getHeight());
    	this.positionX = positionX;
    	this.positionY = positionY;
    }
    
    public void setPosition(double x, double y)
    {
        positionX = x;
        positionY = y;
    }

    public void setVelocity(double x, double y)
    {
        velocityX = x;
        velocityY = y;
    }

    public void addVelocity(double x, double y)
    {
        velocityX += x;
        velocityY += y;
    }
    
	public void update(double time) {
		positionX += velocityX * time;
        positionY += velocityY * time;
	}

	public void render(GraphicsContext gc) {
		gc.drawImage( image, positionX, positionY, this.getWidth(), this.getHeight() );		
	}

	public Rectangle2D getBoundary() {
		return new Rectangle2D(positionX,positionY,getWidth(),getHeight());
	}

	public boolean intersects(Sprite s) {
		return s.getBoundary().intersects( this.getBoundary() );
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}
	
	public void setSize(double width, double height) {
		this.width = width;
		this.height = height;
	}

}
