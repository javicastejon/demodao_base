package com.daoexample.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.daoexample.ConnectToDB;
import com.daoexample.iface.IDao;
import com.daoexample.models.Player;


public class PlayerDao implements IDao<Player, Integer>{

    private final Connection connection;
    private PreparedStatement preQuery;
    private boolean isSuccess = false;

    private final Player player;
    private final List<Player> players;

    private static final String INSERT_PLAYER = "INSERT INTO Player (name, position, age) VALUES(?,?,?)";
    private static final String SELECT_ALL_PLAYERS = "SELECT id,name,position,age FROM Player";
    private static final String SELECT_PLAYER_BY_ID = "SELECT id,name,position,age FROM Player WHERE id = ?;";
    private static final String UPDATE_PLAYER = "UPDATE Player SET name=?, position=?, age=? WHERE id = ?;";
    private static final String DELETE_PLAYER = "DELETE FROM Player WHERE id = ?;";

    public PlayerDao(){
        connection = ConnectToDB.getInstance().getConection();
        player = new Player();
        players = new ArrayList<>();
    }


    @Override
    public boolean createRecord(Player model) {
        try {
            preQuery = connection.prepareStatement(INSERT_PLAYER);
            preQuery.setString(1, model.getName());
            preQuery.setString(2, model.getPosition());
            preQuery.setInt(3, model.getAge());

            // Mostrar consulta
            System.out.println(preQuery);

            if(preQuery.executeUpdate() > 0 ){
                isSuccess = true;
            }

        } catch (SQLException e) {
            Logger.getLogger(PlayerDao.class.getName()).log(Level.SEVERE,null,e.getMessage());
        }
        return isSuccess;
    }

    @Override
    public Player readRecord(Integer idModel) {
        try {
            preQuery = connection.prepareStatement(SELECT_PLAYER_BY_ID);
            preQuery.setInt(1, idModel);
            ResultSet data = preQuery.executeQuery();

            if (data.next()) {
                player.setId(data.getInt("id"));
                player.setName(data.getString("name"));
                player.setPosition(data.getString("position"));
                player.setAge(data.getInt("age"));
            }
            else {
                return null;
            }
        } catch (SQLException ex) {
            Logger.getLogger(PlayerDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return player;
    }

    @Override
    public boolean updateRecord(Player model, Integer idModel) {
        try {
            preQuery = connection.prepareStatement(UPDATE_PLAYER);
            preQuery.setString(1, model.getName());
            preQuery.setString(2, model.getPosition());
            preQuery.setInt(3, model.getAge());
            preQuery.setInt(4, idModel);

            if (preQuery.executeUpdate() > 0) {
                isSuccess = true;
            }

        } catch (SQLException ex) {
            Logger.getLogger(PlayerDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return isSuccess;
    }

    @Override
    public boolean deleteRecord(Integer idModel) {
        try {
            preQuery = connection.prepareStatement(DELETE_PLAYER);
            preQuery.setInt(1, idModel);

            if (preQuery.executeUpdate() > 0) {
                isSuccess = true;
            }

        } catch (SQLException ex) {
            Logger.getLogger(PlayerDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return isSuccess;
    }

    @Override
    public List<Player> readRecords() {
        try {
            preQuery = connection.prepareStatement(SELECT_ALL_PLAYERS);
            ResultSet data = preQuery.executeQuery();

            while (data.next()) {
                players.add(new Player(data.getInt("id"), data.getString("name"), data.getString("position"), data.getInt("age")));
            }

        } catch (SQLException ex) {
            Logger.getLogger(PlayerDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return players;
    }

}
