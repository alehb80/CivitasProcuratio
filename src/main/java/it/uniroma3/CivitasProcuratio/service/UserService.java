package it.uniroma3.CivitasProcuratio.service;

import it.uniroma3.CivitasProcuratio.dao.UserDAO;
import it.uniroma3.CivitasProcuratio.model.Cas;
import it.uniroma3.CivitasProcuratio.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserDAO userDAO;

    @Transactional
    public void save(final User user) {
        this.userDAO.save(user);
    }

    @Transactional
    public Iterable<User> findAll() {
        return this.userDAO.findAll();
    }

    @Transactional
    public User findOne(Long id) {
        return this.userDAO.findOne(id);
    }

    @Transactional
    public void deleteById(Long id) {
        this.userDAO.delete(id);
    }

    @Transactional
    public User findByUsername(String username) {
        return this.userDAO.findByUsername(username);
    }

    @Transactional
    public User findByCas(Cas cas) {
        return this.userDAO.findByCas(cas);
    }

    public boolean alreadyExists(User user) {
        List<User> users = (List<User>) this.userDAO.findAll();
        for (User u : users) {
            if (u.getUsername().equals(user.getUsername()) || u.getEmail().equals(user.getEmail()))
                return true;
        }
        return false;
    }

}
