package com.linkedin.learning.otrareunionmas.dao;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import javax.persistence.Query;

import com.linkedin.learning.otrareunionmas.dominio.Reunion;

public class ReunionDao extends AbstractDao<Reunion> {

	public ReunionDao() {
		setClazz(Reunion.class);
	}
	
	public Reunion proximaReunion() {
		String qlString = "FROM " + Reunion.class.getName() + " WHERE fecha > now() order by fecha ";
		Query query = getEntityManager().createQuery(qlString).setMaxResults(1);
		return (Reunion) query.getSingleResult();
	}
	
	public List<Reunion> reunionesManiana() {
	    String qlString = "FROM " + Reunion.class.getName() + " WHERE fecha BETWEEN ?1 AND ?2";
	    Query query = getEntityManager().createQuery(qlString);

	    // Obtener la fecha de mañana (el día siguiente a hoy)
	    LocalDate maniana = LocalDate.now().plus(1, ChronoUnit.DAYS);
	    
	    // Configurar el parámetro de inicio de día (00:00:00)
	    query.setParameter(1, maniana.atStartOfDay());
	    
	    // Configurar el parámetro del final del día (23:59:59)
	    query.setParameter(2, maniana.plus(1, ChronoUnit.DAYS).atStartOfDay());
	    
	    return query.getResultList();
	}
}

