package repositories;

import models.LogCome;

import java.sql.SQLException;
import java.util.List;

public class LogComeRepository {

    public void create(LogCome logCome) throws SQLException {
        LogCome.create(logCome.getAmount(), logCome.getUsername(), logCome.getDescription(), logCome.getType(), logCome.getDate());
    }

    public List<LogCome> getAll() throws SQLException {
        return LogCome.getAll();
    }

    public LogCome getById(int id) throws SQLException {
        return LogCome.getById(id);
    }

    public void update(LogCome logCome) throws SQLException {
        LogCome.update(logCome.getId(), logCome.getAmount(), logCome.getUsername(), logCome.getDescription(), logCome.getType(), logCome.getDate());
    }

    public void delete(int id) throws SQLException {
        LogCome.delete(id);
    }
}
