package pl.pg.eti.kask.laboratorium1.user.repository;

import pl.pg.eti.kask.laboratorium1.datastore.DataStore;
import pl.pg.eti.kask.laboratorium1.repository.Repository;
import pl.pg.eti.kask.laboratorium1.user.entity.User;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.*;

@Dependent
public class UserRepository implements Repository<User, Long> {

    private DataStore dataStore;

    @Inject
    public UserRepository(DataStore dataStore) {
        this.dataStore = dataStore;
    }

    @Override
    public Optional<User> find(Long id) {
        return dataStore.findUser(id);
    }

    @Override
    public List<User> findAll() {
        return dataStore.findAllUsers();
    }

    @Override
    public void create(User entity) {
        dataStore.createUser(entity);
    }

    @Override
    public void update(User entity) {
        dataStore.updateUser(entity);
    }
}
