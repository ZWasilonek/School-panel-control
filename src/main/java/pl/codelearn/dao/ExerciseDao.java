package pl.codelearn.dao;

import pl.codelearn.model.Exercise;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

public class ExerciseDao {

    private static final String CREATE_EXERCISE =
        "INSERT INTO exercises(title, description) VALUES (?,?)";
    private static final String READ_EXERCISE =
        "SELECT * FROM exercises WHERE id = ?";
    private static final String UPDATE_EXERCISE =
        "UPDATE exercises SET title = ?, description = ? WHERE id = ?";
    private static final String DELETE_EXERCISE =
        "DELETE FROM exercises WHERE id = ?";
    private static final String FIND_ALL_EXERCISES =
        "SELECT * FROM exercises";

    public static Exercise create(Exercise exercise) {
        try (Connection conn = DBUtil.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(CREATE_EXERCISE, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, exercise.getTitle());
            statement.setString(2, exercise.getDescription());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                exercise.setId(resultSet.getInt(1));
            }
            return exercise;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Exercise read(int exerciseId) {
        try (Connection conn = DBUtil.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(READ_EXERCISE);
            statement.setInt(1, exerciseId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Exercise exercise = new Exercise();
                exercise.setId(resultSet.getInt("id"));
                exercise.setTitle(resultSet.getString("title"));
                exercise.setDescription(resultSet.getString("description"));
                return exercise;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void update(Exercise exercise) {
        try (Connection conn = DBUtil.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(UPDATE_EXERCISE);
            statement.setString(1, exercise.getTitle());
            statement.setString(2, exercise.getDescription());
            statement.setInt(3, exercise.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void delete(int exerciseId) {
        try (Connection conn = DBUtil.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(DELETE_EXERCISE);
            statement.setInt(1, exerciseId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static Exercise[] addToArray(Exercise e, Exercise[] exercises) {
        Exercise[] tmpExercises = Arrays.copyOf(exercises, exercises.length + 1);
        tmpExercises[exercises.length] = e;
        return tmpExercises;
    }

    public static Exercise[] findAll() {
        try (Connection conn = DBUtil.getConnection()) {
            Exercise[] exercises = new Exercise[0];
            PreparedStatement statement = conn.prepareStatement(FIND_ALL_EXERCISES);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Exercise exercise = new Exercise();
                exercise.setId(resultSet.getInt("id"));
                exercise.setTitle(resultSet.getString("title"));
                exercise.setDescription(resultSet.getString("description"));
                exercises = addToArray(exercise, exercises);
            }
            return exercises;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
