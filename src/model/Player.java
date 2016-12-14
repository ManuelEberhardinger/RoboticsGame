package model;

import javafx.geometry.Point2D;

public class Player {
	private Point2D coordinates;
	private double speed;
	private double angle;
	
	public Player(Point2D coordinates, Double speed, double angle) {
		this.setCoordinates(coordinates);
		this.setVelocity(speed);
		this.setAngle(angle);
	}

	public Point2D getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(Point2D coordinates) {
		this.coordinates = coordinates;
	}

	public double getSpeed() {
		return speed;
	}

	public void setVelocity(double speed) {
		this.speed = speed;
	}

	public double getAngle() {
		return angle;
	}

	public void setAngle(double angle) {
		this.angle = angle;
	}
	
	
	
}
