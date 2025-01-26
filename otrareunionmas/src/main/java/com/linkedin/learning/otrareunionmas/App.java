package com.linkedin.learning.otrareunionmas;

import java.util.Date;
import java.util.List;

import com.linkedin.learning.otrareunionmas.dao.ReunionDao;
import com.linkedin.learning.otrareunionmas.dominio.Reunion;
/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {
		ReunionDao dao = new ReunionDao();
		List<Reunion> reuniones = dao.getAll();
		System.out.println("*** " + reuniones);

		Reunion r = new Reunion(new Date(), "Reunión de Test");
		System.out.println(r);

		dao.save(r);
		System.out.println(r);

	}
}
