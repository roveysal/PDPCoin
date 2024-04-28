package org.example.repository;

import org.example.jdbc.CustomDataSource;
import org.example.modul.User;
import org.example.states.BotState;
import org.example.states.Role;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    private final String findAllUsers = "SELECT * FROM users";
    private final String findById = "SELECT * FROM users WHERE chat_id = ?";
    private final String findBotStateById = "SELECT bot_state FROM users WHERE chat_id = ?";
    private final String findBotRole = "SELECT role FROM users WHERE chat_id = ?";
    private final String updateById = "UPDATE users SET fullname=?, role=?, bot_state=?, grade = ? WHERE id=?";
    private final String insertUser =
            "INSERT INTO users (chat_id, bot_state) VALUES(?,?)";
    private final String updateBotState = "UPDATE users SET bot_state=? WHERE chat_id=?";

    public void updateBotState(long chatId, BotState botState) {
        try {
            Connection conn = CustomDataSource.getInstance().getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(updateBotState);

            preparedStatement.setString(1, botState.name());
            preparedStatement.setLong(2, chatId);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public List<User> findAllUser(long chatId) {
        List<User> users = new ArrayList<>();
        try (
                Connection connection = CustomDataSource.getInstance().getConnection();
                Statement statement = connection.createStatement();
        ) {
            ResultSet resultSet = statement.executeQuery(findAllUsers);
            while (resultSet.next()) {
                User user = new User(chatId, null, null, null, BotState.START);
                user.setChatId(resultSet.getLong("chat_id"));
                user.setFullName(resultSet.getString("full_name"));
                user.setRole(Role.valueOf(resultSet.getString("role")));
                user.setBotState(BotState.valueOf(resultSet.getString("bot_state")));
                user.setGrade(String.valueOf(resultSet.getInt("grade")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    public User getUserById(long chatId) {
        User user = new User();
        try {
            Connection connection = CustomDataSource.getInstance().getConnection();
            PreparedStatement ps = connection.prepareStatement(findById);
            ps.setLong(1, chatId);
            ResultSet resultSet = ps.executeQuery();
            if (!resultSet.next()) {
                return null ;
            }
            user.setChatId(resultSet.getLong("chat_id"));
            user.setBotState(BotState.valueOf(resultSet.getString("bot_state")));
            return user;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public BotState getUserBotState(long chatId) {
        try (
                Connection connection = CustomDataSource.getInstance().getConnection();
                PreparedStatement ps = connection.prepareStatement(findBotStateById, Statement.RETURN_GENERATED_KEYS)
        ) {
            ps.setObject(1, chatId);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                return BotState.valueOf(resultSet.getString("bot_state"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public Role getUserRole(long chatId) {
        try (
                Connection connection = CustomDataSource.getInstance().getConnection();
                PreparedStatement ps = connection.prepareStatement(findBotRole)
        ) {
            ps.setLong(1, chatId);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                return Role.valueOf(resultSet.getString("role"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public void createUser(long chatId, BotState state) {
        try {
            Connection conn = CustomDataSource.getInstance().getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(insertUser);

            preparedStatement.setLong(1, chatId);
            preparedStatement.setString(2, state.name());

            preparedStatement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean getUpdateById(Long id, User user) {
        try (
                Connection connection = CustomDataSource.getInstance().getConnection();
                PreparedStatement ps = connection.prepareStatement(updateById)
        ) {
            ps.setObject(1, user.getFullName());
            ps.setObject(2, user.getRole());
            ps.setObject(3, user.getBotState());
            ps.setObject(4, user.getGrade());
            int rowsUpdated = ps.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}