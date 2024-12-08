package org.example.repositorios;

import org.example.BDUtils;
import org.example.Dominio.Colaboraciones.DonacionDeVianda;
import org.example.Dominio.Persona.PersonaHumana;
import org.example.Dominio.Rol.Colaborador;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;

public class RepositorioColaboradores {

    private static RepositorioColaboradores instance;

    public RepositorioColaboradores() {}

    public static RepositorioColaboradores getInstance(){
        if (instance == null) {
            instance = new RepositorioColaboradores();
        }
        return instance;
    }

    public Colaborador obtenerColaborador(String nombreColaborador) {
        EntityManager em = BDUtils.getEntityManager();
        try {
            TypedQuery<Colaborador> query = em.createQuery(
                    "SELECT c FROM Colaborador c " +
                            "JOIN c.persona p " +
                            "JOIN p.usuario u " +
                            "WHERE u.usuario = :nombreColaborador", Colaborador.class);
            query.setParameter("nombreColaborador", nombreColaborador);

            // Usa getSingleResult() para obtener el único resultado esperado
            return query.getSingleResult();
        } catch (NoResultException e) {
            // Si no se encuentra ningún colaborador, retorna null
            return null;
        }
    }

    public Colaborador obtenerColaboradorxID(Long id) {
        EntityManager em = BDUtils.getEntityManager();
        try {
            TypedQuery<Colaborador> query = em.createQuery(
                    "SELECT c FROM Colaborador c " +
                            "WHERE c.id = :idColaborador", Colaborador.class);
            query.setParameter("idColaborador", id);

            // Usa getSingleResult() para obtener el único resultado esperado
            return query.getSingleResult();
        } catch (NoResultException e) {
            // Si no se encuentra ningún colaborador, retorna null
            return null;
        }
    }



    public void addColaborador(Colaborador colaborador){
        EntityManager em = BDUtils.getEntityManager();
        BDUtils.comenzarTransaccion(em);
        em.persist(colaborador);
        BDUtils.commit(em);
    }

    public List<Colaborador> getColaboradoresHumanos() {
        EntityManager em = BDUtils.getEntityManager();
        TypedQuery<Colaborador> query = em.createQuery("SELECT c FROM Colaborador c ", Colaborador.class);
        List<Colaborador> colaboradores = query.getResultList();
        return colaboradores.stream()
            .filter(colaborador -> colaborador.getPersona() instanceof PersonaHumana)
            .toList(); // Devuelve la lista filtrada
    }

    public Colaborador getColaboradorPersona(PersonaHumana persona) {
        EntityManager em = BDUtils.getEntityManager();

        TypedQuery<Colaborador> query = em.createQuery(
            "SELECT c FROM Colaborador c JOIN c.persona p WHERE TYPE(p) = PersonaHumana AND p.nombre = :nombre AND p.apellido = :apellido",
            Colaborador.class
        );
        return query
            .setParameter("nombre", persona.getNombre())
            .setParameter("apellido", persona.getApellido())
            .getSingleResult();
    }
}
