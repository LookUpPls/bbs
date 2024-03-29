package com.ljj.dao.impl;

import com.ljj.dao.IReplyDao;
import com.ljj.entity.Reply;
import com.ljj.factory.Factory;

import java.sql.*;
import java.util.ArrayList;

public class ReplyDao implements IReplyDao {

    @Override
    public ArrayList<Reply> getAll(int postId) {
        Connection con = Factory.getCon();
        PreparedStatement state = null;
        ResultSet res = null;
        ArrayList<Reply> posts = new ArrayList<>();
        try {
            state = con.prepareStatement("select rId,rContext,uName,rTime from reply join user using (uid) where pId=? order by rTime");
            state.setInt(1, postId);
            res = state.executeQuery();
            while (res.next()) {
                posts.add(new Reply(res.getInt(1), res.getString(2), res.getString(3), new Date(res.getTimestamp(4).getTime())));
            }
        } catch (SQLException e) {
            System.out.println(e.getSQLState() + e.getSQLState());
            e.printStackTrace();
            System.exit(-1);
        } finally {
            Factory.closeAll(res, state, con);
        }
        return posts;
    }

    @Override
    public boolean add(Reply reply) throws SQLException {
        Connection con = Factory.getCon();
        PreparedStatement state = null;
        try {
            state = con.prepareStatement("insert into reply values(null,?,?,?,?)");
            state.setString(1, reply.getContext());
            state.setTimestamp(2, new Timestamp(reply.getTime().getTime()));
            state.setInt(3, reply.getUserId());
            state.setInt(4, reply.getPostId());

            if (state.executeUpdate() > 0) {
                return true;
            }
            return false;
        } finally {
            Factory.closeAll(null, state, con);
        }
    }

    @Override
    public String getNameById(int id) {
        return null;
    }

    @Override
    public int getIdByTitle(String title) {
        Connection con = Factory.getCon();
        PreparedStatement state = null;
        ResultSet res = null;
        try {
            state = con.prepareStatement("select rId from reply where rTitle=?");
            state.setString(1, title);
            res = state.executeQuery();
            if (res.next()) {
                return res.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println(e.getSQLState() + e.getSQLState());
            e.printStackTrace();
            System.exit(-1);
        } finally {
            Factory.closeAll(res, state, con);
        }
        return -1;
    }

    @Override
    public boolean delete(int replyId, int postId, int userId, boolean isAdmin) {
        Connection con = Factory.getCon();
        PreparedStatement state = null;
        try {
//            if (isAdmin) {
                state = con.prepareStatement("delete from reply where pId=? and rId=?");
                state.setInt(1, postId);
                state.setInt(2, replyId);
//            } else {
//                state = con.prepareStatement("delete from reply where pId=? and rId=? and uId=?");
//                state.setInt(1, postId);
//                state.setInt(2, replyId);
//                state.setInt(3, userId);
//            }

            int res = state.executeUpdate();
            return res > 0;
        } catch (SQLException e) {
            System.out.println(e.getSQLState() + e.getSQLState());
            e.printStackTrace();
            System.exit(-1);
        } finally {
            Factory.closeAll(null, state, con);
        }
        return false;
    }

    @Override
    public boolean getPostById(Reply post) {
        return false;
    }

    @Override
    public int getUserId(int id) {
        Connection con = Factory.getCon();
        PreparedStatement state = null;
        ResultSet res = null;
        try {
            state = con.prepareStatement("select uId from reply where rId=?");
            state.setInt(1, id);
            res = state.executeQuery();
            if (res.next()) {
                return res.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println(e.getSQLState() + e.getSQLState());
            e.printStackTrace();
            System.exit(-1);
        } finally {
            Factory.closeAll(res, state, con);
        }
        return -1;
    }

}
