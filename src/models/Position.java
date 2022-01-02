package models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

import connector.Connector;

public class Position {
    private int positionID;
    private String positionName;
    private Connector con = Connector.connect();
    private String table = "position";
    
    private Position map(ResultSet rs) {
        try {
            int positionID = rs.getInt("positionID");
            String name = rs.getString("name");
            return new Position(positionID, name);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public List<Position> getAllPosition() {
        String query = String.format("SELECT * FROM " + this.table);
        ResultSet rs = con.executeQuery(query);
        
        try {
            Vector<Position> positions = new Vector<>();
            while(rs.next()) {
                Position position = map(rs);
                positions.add(position);
            }
            return positions;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return null;
    }

    public Position getPosition (int positionID) {
        String query = "SELECT * FROM " + this.table 
                + " WHERE positionID = " + positionID 
                + " LIMIT 1";
        ResultSet rs = con.executeQuery(query);
        
        try {
            rs.next();
            return map(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public Position(int positionID, String positionName) {
        super();
        this.positionID = positionID;
        this.positionName = positionName;
    }
    
    public Position() {}
    
    public int getPositionID() {
        return positionID;
    }
    public void setPositionID(int positionID) {
        this.positionID = positionID;
    }
    public String getPositionName() {
        return positionName;
    }
    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }
    
}