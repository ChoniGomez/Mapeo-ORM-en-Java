package com.linkedin.learning.otrareunionmas;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.NoResultException;

import com.linkedin.learning.otrareunionmas.dao.ActaDao;
import com.linkedin.learning.otrareunionmas.dao.ReunionDao;
import com.linkedin.learning.otrareunionmas.dao.SalaDao;
import com.linkedin.learning.otrareunionmas.dominio.Acta;
import com.linkedin.learning.otrareunionmas.dominio.Persona;
import com.linkedin.learning.otrareunionmas.dominio.Reunion;
import com.linkedin.learning.otrareunionmas.dominio.Sala;
/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {
		// DAOs
		ReunionDao reunionDao = new ReunionDao();
		ActaDao actaDao = new ActaDao();
		SalaDao salaDao = new SalaDao();

		// Creación de objetos
		Sala s099 = new Sala("S099", "Trastero", 1);
		Sala s101 = new Sala("S101", "Reunión primera planta", 10);
		Sala s109 = new Sala("S109", "Entrevistas primera planta", 3);
		Sala s203 = new Sala("S203", "Sala grande", 25);

		salaDao.save(s099);
		salaDao.save(s101);
		salaDao.save(s109);
		salaDao.save(s203);

		Persona marta = new Persona("E001", "Marta", "García López");
		Persona pedro = new Persona("E002", "Pedro", "Gómez Fernández");
		Persona santi = new Persona("E003", "Santi", "Pérez Pérez");
		Persona luisa = new Persona("E004", "Luisa", "Gutiérrez González");

		Reunion r0 = new Reunion(LocalDateTime.now(), "Reunión de Test");
		Reunion r1 = new Reunion(LocalDateTime.now().plus(2, ChronoUnit.HOURS), "Otra Reunión de Test");
		Reunion r2 = new Reunion(LocalDateTime.now().plus(2, ChronoUnit.DAYS), "Reunión de pasado mañana");
		Reunion r3 = new Reunion(LocalDateTime.now().plus(1, ChronoUnit.DAYS), "Reunión de mañana");
		Reunion r4 = new Reunion(LocalDateTime.now().minus(1, ChronoUnit.DAYS), "Reunión de ayer");

		r0.addParticipante(marta);
		r0.setSala(s099);
		reunionDao.save(r0);
		Acta a0 = new Acta("Marta se reúne sola, solo para descansar un rato", r0);
		actaDao.save(a0);
		reunionDao.update(r0);

		r1.addParticipante(marta);
		r1.addParticipante(pedro);
		r1.addParticipante(santi);
		r1.addParticipante(luisa);
		r1.setSala(s101);
		reunionDao.save(r1);

		r2.addParticipante(pedro);
		r2.addParticipante(santi);
		r2.setSala(s109);
		reunionDao.save(r2);

		r3.addParticipante(marta);
		r3.addParticipante(luisa);
		r3.setSala(s109);
		reunionDao.save(r3);

		r4.addParticipante(marta);
		r4.addParticipante(pedro);
		r4.addParticipante(santi);
		r4.addParticipante(luisa);
		r4.setSala(s203);
		reunionDao.save(r4);

		Acta a4 = new Acta("Participantes: M. García, P. Gómez, S. Pérez y L. Gutiérrez. Duración: 1h. "
				+ "Se reunieron en la sala 203 para preparar el lanzamiento de la aplicación "
				+ "\"Otra Reunión Más\".", r4);
		actaDao.save(a4);
		reunionDao.update(r4);

		// Recuperación de datoss
		List<Reunion> reuniones = reunionDao.getAll();
		System.out.println("*** " + reuniones);

		try {
			System.out.println("Tu próxima reunión es: " + reunionDao.proximaReunion());
		} catch (NoResultException nre) {
			System.out.println("No tienes ninguna reunión a la vista");
		}
		List<Reunion> reunionesManyana = reunionDao.reunionesManiana();
		System.out.println("Para mañana: " + reunionesManyana);

	}
}