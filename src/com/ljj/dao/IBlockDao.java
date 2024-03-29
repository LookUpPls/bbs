package com.ljj.dao;

import com.ljj.entity.Block;

import java.sql.SQLException;
import java.util.ArrayList;

public interface IBlockDao {
    public ArrayList<Block> getAllBlocks();

    public boolean create(Block block) throws SQLException;

    public String getNameById(int id);
    public int getIdByName(String name);
    public boolean delete(int id);
}
