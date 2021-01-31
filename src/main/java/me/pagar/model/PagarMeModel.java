package me.pagar.model;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.HttpMethod;

import org.atteo.evo.inflector.English;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import com.google.common.base.CaseFormat;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import me.pagar.model.filter.QueriableFields;
import me.pagar.util.DateTimeIsodateAdapter;
import me.pagar.util.JSONUtils;
import me.pagar.util.LocalDateAdapter;


public abstract class PagarMeModel<PK extends Serializable> {

    /**
     * Número identificador da transação
     */
    @Expose(serialize = true)
    @SerializedName("id")
    private PK id;

    /**
     * Data de criação da transação no formato ISODate
     */
    @Expose(serialize = false)
    @SerializedName("date_created")
    private DateTime createdAt;

    private transient String className;

    private transient Collection<String> dirtyProperties;

    protected void validateId() {

        if (getId() == null) {
            throw new IllegalArgumentException("The Object ID must be set in order to use this method.");
        }

    }

    public PagarMeModel() {
        className = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, English.plural(getClass().getSimpleName()));
        dirtyProperties = new ArrayList<String>();
    }

    /**
     * {@link #id}
     */
    public PK getId() {
        return id;
    }

    /**
     * {@link #createdAt}
     */
    public DateTime getCreatedAt() {
        return createdAt;
    }

    public String getClassName() {
        return className;
    }
    
    protected void setCreatedAt(DateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setId(final PK id) {
        this.id = id;
    }

    public void setClassName(final String className) {
        this.className = className;
    }

    protected JsonObject refreshModel() throws PagarMeException {
        return get(this.id);
    }

    protected JsonObject get(final PK id) throws PagarMeException {
        validateId();

        if (null == id) {
            throw new IllegalArgumentException("You must provide an ID to get this object data");
        }

        final PagarMeRequest request = new PagarMeRequest(HttpMethod.GET, String.format("/%s/%s", className, id));

        return request.execute();
    }
    
    protected JsonObject getThrough(PagarMeModel modelFilter) throws PagarMeException {
        validateId();

        if (null == id) {
            throw new IllegalArgumentException("You must provide an ID to get this object data");
        }
        String path = "";
        path += "/" + this.getClassName() + "/" + this.getId();
        path += "/" + modelFilter.getClassName() + "/" + modelFilter.getId();

        final PagarMeRequest request = new PagarMeRequest(HttpMethod.GET, path);

        return request.execute();
    }

    protected JsonArray paginate(final Integer totalPerPage) throws PagarMeException {
        return paginate(totalPerPage, 1);
    }

    protected JsonArray paginate(final Integer totalPerPage, Integer page) throws PagarMeException {
        return paginate(totalPerPage, page, null);
    }

    protected JsonArray paginate(final Integer totalPerPage, Integer page, QueriableFields modelFilter) throws PagarMeException {
        final Map<String, Object> parameters = new HashMap<String, Object>();

        if (null != totalPerPage && totalPerPage > 0) {
            parameters.put("count", totalPerPage);
        }

        if (null != page && page > 0) {
            parameters.put("page", page);
        }
        
        String path = "/" + getClassName();
        final PagarMeRequest request = new PagarMeRequest(HttpMethod.GET, path);
        request.getParameters().putAll(parameters);

        if(modelFilter != null){
            Map<String, Object> filter = modelFilter.toMap();
            request.getParameters().putAll(filter);
        }

        return request.execute();
    }

    protected <T extends PagarMeModel> JsonArray  paginateThrough(final Integer totalPerPage, Integer page, QueriableFields modelFilter) throws PagarMeException {
        final Map<String, Object> parameters = new HashMap<String, Object>();

        if (null != totalPerPage && 0 != totalPerPage) {
            parameters.put("count", totalPerPage);
        }

        if (null == page || 0 >= page) {
            page = 1;
        }
        parameters.put("page", page);

        String path = "/" + this.getClassName() + "/" + this.getId() + "/" + modelFilter.pagarmeRelatedModel();
        Map<String, Object> filter = new  HashMap<String, Object>();
        if(modelFilter != null){
            filter = modelFilter.toMap();
        }

        final PagarMeRequest request = new PagarMeRequest(HttpMethod.GET, path);
        request.getParameters().putAll(parameters);
        request.getParameters().putAll(filter);

        return request.execute();
    }

    protected <T extends PagarMeModel<PK>> T save(final Class<T> clazz) throws PagarMeException {

        if (!validate()) {
            return null;
        }

        final PagarMeRequest request = null == id ?
                new PagarMeRequest(HttpMethod.POST, String.format("/%s", className)) :
                new PagarMeRequest(HttpMethod.PUT, String.format("/%s/%s/", className, id));
        request.setParameters(JSONUtils.objectToMap(this));

        final JsonElement element = request.execute();
        flush();

        return JSONUtils.getAsObject((JsonObject) element, clazz);
    }

    protected void addUnsavedProperty(final String name) {
        for (String s : dirtyProperties) {
            if (s.startsWith(name.concat("."))) {
                dirtyProperties.remove(s);
            }
        }
        dirtyProperties.add(name);
    }

    protected void flush() {
        dirtyProperties.clear();
    }
    
    protected <T extends PagarMeModel<PK>> void copy(T other){
        this.id = other.getId();
        this.createdAt = other.getCreatedAt();
    }

    protected boolean validate() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final PagarMeModel<?> that = (PagarMeModel<?>) o;

        return id.equals(that.id);

    }

    public String toJson() {
        return new GsonBuilder()
                .registerTypeAdapter(DateTime.class, new DateTimeIsodateAdapter())
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create()
                .toJson(this);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        try {
            return this.toJson();
        } catch (UnsupportedOperationException e) {
            return getClass().getSimpleName().concat(String.format("=(%s)", this.id));
        }
    }

}
