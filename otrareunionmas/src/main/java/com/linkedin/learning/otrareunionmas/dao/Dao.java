package com.linkedin.learning.otrareunionmas.dao;

import java.util.List;
import java.util.Optional;


//la <T> es para tratar distintos tipos de datos
public interface Dao<T> {

	Optional<T> get(int id);

	List<T> getAll();

	void save(T t);

	void update(T t);

	void delete(T t);
}
