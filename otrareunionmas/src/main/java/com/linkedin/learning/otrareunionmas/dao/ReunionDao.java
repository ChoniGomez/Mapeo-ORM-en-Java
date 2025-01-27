package com.linkedin.learning.otrareunionmas.dao;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;

import com.linkedin.learning.otrareunionmas.dominio.Persona;
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
	
	public List<Reunion> reunionesParticipante(String numEmple) {
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Reunion> criteriaQuery = cb.createQuery(Reunion.class);

		Root<Persona> fromPersona = criteriaQuery.from(Persona.class);
		criteriaQuery.where(cb.equal(fromPersona.get("numeroEmpleado"), numEmple));

		Join<Persona, Reunion> joinReunion = fromPersona.join("reuniones", JoinType.INNER);

		CriteriaQuery<Reunion> cq = criteriaQuery.multiselect(joinReunion);
		TypedQuery<Reunion> query = getEntityManager().createQuery(cq);
		return query.getResultList();
	}
}

