package bank.model.repository;

import bank.exceptions.ItemAlreadyExists;
import bank.exceptions.NotFound;

import java.util.List;

public interface  Repository<T> {

    List<T> findAll() throws NotFound;

    T findById(Long id) throws NotFound;

    T add(T t) throws ItemAlreadyExists;

    T update(T t, Long id) throws NotFound;

    boolean deleteById(Long id);

}
