package ec.com.atikasoft.proteus.generico;

import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.modelo.base.EntidadBasica;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.persistence.*;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.eclipse.persistence.internal.helper.DatabaseField;
import org.eclipse.persistence.internal.jpa.EJBQueryImpl;
import org.eclipse.persistence.jpa.JpaEntityManager;
import org.eclipse.persistence.queries.DatabaseQuery;
import org.eclipse.persistence.sessions.DatabaseRecord;
import org.eclipse.persistence.sessions.Session;

/**
 *
 * @author Victor Quimbiamba <vicanall@gmail.com>
 * @param <T>
 * @param <ID>
 */
public abstract class GenericDAO<T extends EntidadBasica, ID extends Serializable> {

    private final Class<T> entityClass;

    @PersistenceContext(unitName = "proteusPU")
    private EntityManager em;

    public GenericDAO(final Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public T crear(final T entity) throws DaoException {
        try {
            if (!constraintValidationsDetected(entity)) {
                getEntityManager().persist(entity);
            }
            return entity;
        } catch (Exception e) {
            printConstraintValidationViolation(e);
            throw new DaoException(e);
        }
    }

    public void actualizar(final T entity) throws DaoException {
        try {
            if (!constraintValidationsDetected(entity)) {
                getEntityManager().merge(entity);
            }
        } catch (Exception e) {
            printConstraintValidationViolation(e);
            throw new DaoException(e);
        }
    }

    public void eliminar(final T entity) throws DaoException {
        try {
            if (!constraintValidationsDetected(entity)) {
                getEntityManager().remove(getEntityManager().merge(entity));
            }
        } catch (Exception e) {
            printConstraintValidationViolation(e);
            throw new DaoException(e);
        }
    }

    public T buscarPorId(Object id) throws DaoException {
        return getEntityManager().find(entityClass, id);
    }

    public List<T> buscarTodos() throws DaoException {
        try {
            javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
            cq.select(cq.from(entityClass));
            return getEntityManager().createQuery(cq).getResultList();
        } catch (Exception e) {
            printConstraintValidationViolation(e);
            throw new DaoException(e);
        }
    }

    public List<T> buscarEnRango(final int[] range) throws DaoException {
        try {
            javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
            cq.select(cq.from(entityClass));
            javax.persistence.Query q = getEntityManager().createQuery(cq);
            q.setMaxResults(range[1] - range[0]);
            q.setFirstResult(range[0]);
            return q.getResultList();
        } catch (Exception e) {
            printConstraintValidationViolation(e);
            throw new DaoException(e);
        }
    }

    public int contar() throws DaoException {
        try {
            javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
            javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
            cq.select(getEntityManager().getCriteriaBuilder().count(rt));
            javax.persistence.Query q = getEntityManager().createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } catch (Exception e) {
            printConstraintValidationViolation(e);
            throw new DaoException(e);
        }
    }

    public int ejecutarPorConsultaNombrada(final String namedQuery, final Object... args) throws DaoException {
        final Query q = this.getEntityManager().createNamedQuery(namedQuery);
        procesarParametros(q, args);
        return q.executeUpdate();
    }

    public int ejecutarPorConsultaNombradaConPaginacion(final String namedQuery, final int inicio, final int longitud,
            final Object... args) throws DaoException {
        final Query q = this.getEntityManager().createNamedQuery(namedQuery);
        q.setMaxResults(longitud);
        q.setFirstResult(inicio);
        procesarParametros(q, args);
        int r = q.executeUpdate();
        return r;
    }

    /**
     * Recupera las entidadas que cumplan la condición de la query.
     *
     * @param namedQuery nombre de la query definida en la Entidad
     * @param args representa la lista de parametros que se aplicaran para realizar la consulta
     * @return lista con las entidades que cumplen la query
     *
     * @throws DaoException Cuando exista un error en la ejecución del método.
     */
    public List<T> buscarPorConsultaNombrada(final String namedQuery, final Object... args) throws DaoException {
        try {
            final Query q = this.getEntityManager().createNamedQuery(namedQuery);
            procesarParametros(q, args);
            return q.getResultList();
        } catch (final Exception e) {
            printConstraintValidationViolation(e);
            throw new DaoException(e.getMessage(), e);
        }
    }

    /**
     * Recupera las entidadas que cumplan la condición de la query.
     *
     * @param namedQuery nombre de la query definida en la Entidad
     * @param args representa la lista de parametros que se aplicaran para realizar la consulta
     * @return lista con las entidades que cumplen la query
     *
     * @throws DaoException Cuando exista un error en la ejecución del método.
     */
    public List<T> buscarPorConsultaNombradaPaginacion(final String namedQuery, final int inicio, final int longitud,
            final Object... args) throws DaoException {
        try {
            final Query q = this.getEntityManager().createNamedQuery(namedQuery);
            procesarParametros(q, args);
            q.setFirstResult(inicio);
            q.setMaxResults(longitud);
            return q.getResultList();
        } catch (final Exception e) {
            printConstraintValidationViolation(e);
            throw new DaoException(e.getMessage(), e);
        }
    }

    /**
     * Recupera las entidadas que cumplan la condición de la query.
     *
     * @param namedQuery nombre de la query definida en la Entidad
     * @param args representa la lista de parametros que se aplicaran para realizar la consulta
     * @return lista con las entidades que cumplen la query
     *
     * @throws DaoException Cuando exista un error en la ejecución del método.
     */
    public Object buscarUnicoPorConsultaNombrada(final String namedQuery, final Object... args) throws DaoException {
        try {
            final Query q = this.getEntityManager().createNamedQuery(namedQuery);
            procesarParametros(q, args);
            return q.getSingleResult();
        } catch (final Exception e) {
            printConstraintValidationViolation(e);
            throw new DaoException(e.getMessage(), e);
        }
    }

    /**
     * Este metodo busca segun una consulta nombrada y recibe parametros.
     *
     * @param consulta
     * @param parametros
     * @return lista de objetos
     */
    public List<T> buscarPorConsultaNombrada(final String consulta) throws DaoException {
        try {
            TypedQuery<T> query = getEntityManager().createNamedQuery(consulta, entityClass);
            List<T> resultList = query.getResultList();
            return resultList;
        } catch (Exception e) {
            printConstraintValidationViolation(e);
            throw new DaoException(e);
        }
    }

    /**
     * Este metodo busca segun una consulta nombrada y recibe parametros.
     *
     * @param consulta
     * @param inicio
     * @param longitud
     * @return lista de objetos
     * @throws ec.com.atikasoft.proteus.excepciones.DaoException
     */
    public List<T> buscarPorConsultaNombradaPaginacion(final String consulta, final int inicio, final int longitud)
            throws DaoException {
        try {
            TypedQuery<T> query = getEntityManager().createNamedQuery(consulta, entityClass);
            query.setFirstResult(inicio);
            query.setMaxResults(longitud);
            List<T> resultList = query.getResultList();
            return resultList;
        } catch (Exception e) {
            printConstraintValidationViolation(e);
            throw new DaoException(e);
        }
    }

    /**
     * Se encarga de determinar los parametros que se usaran en la consulta.
     *
     * @param query La consulta a la que se añadiran los parametros.
     * @param parametros El array de parametros.
     */
    private void procesarParametros(final Query query, final Object[] parametros) {
        if (parametros != null && parametros.length > 0) {
            int position = 1;
            for (Object obj : parametros) {
                query.setParameter(position++, obj);
            }
        }
    }

    public EntityManager getEntityManager() {
        return em;
    }

    public void clear() throws DaoException {
        try {
            em.clear();
        } catch (Exception e) {
            printConstraintValidationViolation(e);
            throw new DaoException(e);
        }
    }

    public void flush() throws DaoException {
        try {
            em.flush();
        } catch (Exception e) {
            printConstraintValidationViolation(e);
            throw new DaoException(e);
        }
    }

    public void refresh(Object object) {
        em.refresh(object);
    }

    /**
     * Limpia cache de primer nivel.
     */
    public void lock(final Object entity) {
        getEntityManager().lock(entity, LockModeType.PESSIMISTIC_WRITE);
    }

    /**
     * Imprime los mensaje de error por constraint validation violation.
     *
     * @param cvs
     */
    private void printConstraintValidationViolation(final Exception e) {
        System.out.println(
                "************************printConstraintValidationViolation***************************");
        if (e instanceof ConstraintViolationException) {
            for (ConstraintViolation cv : ((ConstraintViolationException) e).getConstraintViolations()) {
                System.out.println("Error:");
                System.out.println("------Clase         :" + cv.getLeafBean());
                System.out.println("------Atributo      :" + cv.getPropertyPath());
                System.out.println("------Valor Invalido:" + cv.getInvalidValue());
                System.out.println("------Validacion    :" + cv.getMessage());
                System.out.println("---------------------------------------------------------------");
            }
        }
    }

    /**
     * Permite obtener el SQL del named query.
     *
     * @param query
     * @param parametros
     * @return
     */
    public String obtenerSql(final Query query, final Map<String, Object> parametros) {
        Session session = em.unwrap(JpaEntityManager.class).getActiveSession();
        DatabaseQuery databaseQuery = ((EJBQueryImpl) query).getDatabaseQuery();
        DatabaseRecord recordWithValues = null;
        if (parametros != null) {
            recordWithValues = new DatabaseRecord();
            Set<String> keys = parametros.keySet();
            for (String key : keys) {
                recordWithValues.add(new DatabaseField(key), parametros.get(key));
            }
        }
        return databaseQuery.getTranslatedSQLString(session, recordWithValues).toUpperCase();
    }
//    public String obtenerSql(final Query query, final Object[] parametros) {
//        Session session = em.unwrap(JpaEntityManager.class).getActiveSession();
//        DatabaseQuery databaseQuery = ((EJBQueryImpl) query).getDatabaseQuery();
//        DatabaseRecord recordWithValues = null;
//        if (parametros != null) {
//            recordWithValues = new DatabaseRecord();
//            for (Object p : parametros) {
//                recordWithValues.add(new DatabaseField(), p);
//            }
//        }
//        return databaseQuery.getTranslatedSQLString(session, recordWithValues).toUpperCase();
//    }

    private boolean constraintValidationsDetected(T entity) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(entity);
        if (constraintViolations.size() > 0) {
            Iterator<ConstraintViolation<T>> iterator = constraintViolations.iterator();
            while (iterator.hasNext()) {
                ConstraintViolation<T> cv = iterator.next();
                System.err.println(cv.getRootBeanClass().getName() + "." + cv.getPropertyPath() + " " + cv.getMessage());
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * Se encarga de ejecutar un named query que realice un count de registros. Adicionalmente se procesara la consulta
     * con parametro
     *
     * @param consultaNombrada La consulta nombrada
     * @param parametros Parametros usados en el where de la consulta
     * @return El resultado de la ejecucion de la consulta.
     *
     * @throws DaoException Cuando exista un error en la ejecución del método.
     */
    public Long contarPorConsultaNombrada(final String consultaNombrada, final Object... parametros)
            throws DaoException {
        try {
            Query q = getEntityManager().createNamedQuery(consultaNombrada);
            procesarParametros(q, parametros);
            return (Long) q.getSingleResult();
        } catch (Exception e) {
            return 0l;
        }
    }

    /**
     * Genera filtro por parametro de consulta.
     *
     * @param sql Sql.
     * @param parametros Parametros.
     * @param nombre Nombre.
     * @param valor Valor.
     */
    protected void generarFiltro(final StringBuilder sql, final Map<String, Object> parametros, final String nombre,
            final Object valor) {
        if (valor != null) {
            sql.append(" AND o.");
            sql.append(nombre);
            sql.append("=:");
            sql.append(nombre.replace('.', '_'));
            parametros.put(nombre.replace('.', '_'), valor);
        }
    }

    /**
     * Ordena la consulta.
     *
     * @param ordenar
     * @param tipo
     * @param sql
     */
    protected void ordenar(final String ordenar, final String tipo, final StringBuilder sql) {
        if (ordenar != null && !ordenar.trim().isEmpty()) {
            sql.append(" ORDER BY o.");
            sql.append(ordenar);
            if (tipo != null && !tipo.trim().isEmpty()) {
                sql.append(" ");
                sql.append(tipo);
            }
        }
    }
}
