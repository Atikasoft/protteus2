package ec.com.atikasoft.proteus.pagination.base;

import org.apache.log4j.Logger;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;

import java.util.*;

public abstract class PaginacionBase<E> extends LazyDataModel<E> {

    /**
     * 
     */
    private static final long serialVersionUID = -6074599821435569629L;

    /**
     * 
     */
    private static final Logger LOG = Logger.getLogger(PaginacionBase.class);

    /**
     * 
     */
    private int rowCount;

    @Override
    public void setRowIndex(int rowIndex) {
        /*
         * The following is in ancestor (LazyDataModel):
         * this.rowIndex = rowIndex == -1 ? rowIndex : (rowIndex % pageSize);
         */
        if (rowIndex == -1 || getPageSize() == 0) {
            super.setRowIndex(-1);
        } else {
            super.setRowIndex(rowIndex % getPageSize());
        }
    }

    @Override
    public int getRowCount() {
        return rowCount;
    }

    @Override
    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
    }

    @Override
    public List<E> load(int first, int pageSize, List<SortMeta> multiSortMeta, Map<String, String> filters) {
        throw new UnsupportedOperationException();
    }

    /**
     * Obtiene los resultados en base a los datos de paginaci&oacute;n
     *
     *
     * @param firstRow
     * @param numberOfRows
     * @param filters
     * @return
     */
    protected abstract List<E> loadData(int firstRow, int numberOfRows, String orderField, 
            SortOrder orderDirection, Map<String, String> filters);

    /**
     * Cuenta el total de registros
     *
     * @return
     * @param filters
     */
    protected abstract int countRecords(Map<String, String> filters);

    @Override
    public List<E> load(int firstRow, int numberOfRows, String orderField, SortOrder orderDirection, 
            Map<String, String> filters) {
        List<E> objects = loadData(firstRow, numberOfRows, orderField, orderDirection, filters);
        this.setRowCount(countRecords(filters));
        return objects;
    }

}
