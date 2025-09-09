package com.example.ispw2.engineering.DAO.mysql;

import com.example.ispw2.engineering.DAO.SettoreDAO;
import com.example.ispw2.engineering.query.SearchQuery;
import com.example.ispw2.altro.Connector;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SettoreMySQLDAO implements SettoreDAO {

    public String getNumMaxSettoriDAO(){
        try(ResultSet rs = SearchQuery.getMaxCodSettore(Connector.getConnection())){
            if(rs.next()){
                return rs.getString("maxCodice");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
