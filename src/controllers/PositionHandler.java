package controllers;

import java.util.List;

import models.Position;

public class PositionHandler {
	
	private static PositionHandler controller = null;
	private String errorMessage;
	private Position position;

	public PositionHandler() {
		position = new Position();
		errorMessage = "";
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}
	
	public static PositionHandler getInstance() {
		if(controller == null) {
			controller = new PositionHandler();
		}
		return controller;
	}
	
	public List<Position> getAllPositions() {
		return position.getAllPosition();
	}
	
	public Position getPosition(String positionID) {
		
		if(positionID.isEmpty()) {
			errorMessage = "Position ID cannot be empty";
			return null;
		}
		
		int posIDInt = -1;
		try {
			posIDInt = Integer.parseInt(positionID);
		} catch (Exception e) {
			errorMessage = "Position ID cannot be empty";
			return null;
		}
		
		return position.getPosition(posIDInt);
	}

}
