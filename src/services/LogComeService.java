package services;

import models.LogCome;
import repositories.LogComeRepository;

import java.sql.SQLException;
import java.util.List;

public class LogComeService {

    private final LogComeRepository logComeRepository;

    public LogComeService() {
        this.logComeRepository = new LogComeRepository();
    }

    public LogCome createLogCome(LogCome logCome) throws SQLException {
        logComeRepository.create(logCome);
        // Assuming the repository will set the ID in the LogCome object after creation
        return logCome;
    }

    public List<LogCome> getAllLogComes() throws SQLException {
        return logComeRepository.getAll();
    }

    public LogCome getLogComeById(int id) throws SQLException {
        return logComeRepository.getById(id);
    }

    public LogCome updateLogCome(LogCome logCome) throws SQLException {
        logComeRepository.update(logCome);
        return logCome;
    }

    public void deleteLogCome(int id) throws SQLException {
        logComeRepository.delete(id);
    }
}
