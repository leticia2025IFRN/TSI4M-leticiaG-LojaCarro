package br.org.edu.ifrn.LojaCarro.services;

import br.org.edu.ifrn.LojaCarro.model.Log;
import br.org.edu.ifrn.LojaCarro.repository.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogService {
    @Autowired
    private LogRepository repository;

    public void saveLog(Log log) {
        repository.save(log);
    }
}
