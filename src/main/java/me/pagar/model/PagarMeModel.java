package me.pagar.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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
import com.google.gson.internal.LinkedTreeMap;

import me.pagar.model.filter.QueriableFields;
import me.pagar.util.DateTimeIsodateAdapter;
import me.pagar.util.JSONUtils;
import me.pagar.util.LocalDateAdapter;

public abstract class PagarMeModel<PK extends Serializable> {

	/**
	 * Número identificador da transação
	 */
	@Expose(serialize = false)
	@SerializedName("id")
	protected PK id;

	/**
	 * Data de criação da transação no formato ISODate
	 */
	@Expose(serialize = false)
	@SerializedName("date_created")
	private DateTime createdAt;

	private transient String className;

	private transient Collection<String> dirtyProperties;

	public PagarMeModel() {
		className		= CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, English.plural(getClass().getSimpleName()));
		dirtyProperties	= new ArrayList<String>();
	}

	protected void addUnsavedProperty(final String name) {
		for (String s : dirtyProperties) {
			if (s.startsWith(name.concat("."))) {
				dirtyProperties.remove(s);
			}
		}
		dirtyProperties.add(name);
	}

	protected <T extends PagarMeModel<PK>> void copy(T other) {
		this.id			= other.getId();
		this.createdAt	= other.getCreatedAt();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		final PagarMeModel<?> that = (PagarMeModel<?>) o;

		return id.equals(that.id);

	}

	protected void flush() {
		dirtyProperties.clear();
	}

	protected JsonObject get(final PK id) throws PagarMeException {
		validateId();

		if (null == id) {
			throw new IllegalArgumentException("You must provide an ID to get this object data");
		}

		final PagarMeRequest request = new PagarMeRequest(HttpMethod.GET, String.format("/%s/%s", className, id));

		return request.execute();
	}

	public String getClassName() {
		return className;
	}

	/**
	 * {@link #createdAt}
	 */
	public DateTime getCreatedAt() {
		return createdAt;
	}

	/**
	 * {@link #id}
	 */
	public PK getId() {
		return id;
	}

	protected JsonObject getThrough(PagarMeModel modelFilter) throws PagarMeException {
		validateId();

		if (null == id) {
			throw new IllegalArgumentException("You must provide an ID to get this object data");
		}
		String path = "";
		path	+= "/" + this.getClassName() + "/" + this.getId();
		path	+= "/" + modelFilter.getClassName() + "/" + modelFilter.getId();

		final PagarMeRequest request = new PagarMeRequest(HttpMethod.GET, path);

		return request.execute();
	}

	@Override
	public int hashCode() {
		return id.hashCode();
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

		String					path	= "/" + getClassName();
		final PagarMeRequest	request	= new PagarMeRequest(HttpMethod.GET, path);
		request.getParameters().putAll(parameters);

		if (modelFilter != null) {
			Map<String, Object> filter = modelFilter.toMap();
			request.getParameters().putAll(filter);
		}

		return request.execute();
	}

	protected <T extends PagarMeModel> JsonArray paginateThrough(final Integer totalPerPage, Integer page, QueriableFields modelFilter)
			throws PagarMeException {
		final Map<String, Object> parameters = new HashMap<String, Object>();

		if (null != totalPerPage && 0 != totalPerPage) {
			parameters.put("count", totalPerPage);
		}

		if (null == page || 0 >= page) {
			page = 1;
		}
		parameters.put("page", page);

		String				path	= "/" + this.getClassName() + "/" + this.getId() + "/" + modelFilter.pagarmeRelatedModel();
		Map<String, Object>	filter	= new HashMap<String, Object>();
		if (modelFilter != null) {
			filter = modelFilter.toMap();
		}

		final PagarMeRequest request = new PagarMeRequest(HttpMethod.GET, path);
		request.getParameters().putAll(parameters);
		request.getParameters().putAll(filter);

		return request.execute();
	}

	protected JsonObject refreshModel() throws PagarMeException {
		return get(this.id);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected <T extends PagarMeModel<PK>> T save(final Class<T> clazz) throws PagarMeException {

		if (!validate()) {
			return null;
		}

		final PagarMeRequest request = null == id ? new PagarMeRequest(HttpMethod.POST, String.format("/%s", className))
				: new PagarMeRequest(HttpMethod.PUT, String.format("/%s/%s/", className, id));

		Map<String, Object> jsonMap = JSONUtils.objectToMap(this);

		jsonMap.forEach((strCara, obj) -> {
			if (strCara.equals("customer")) {
				LinkedTreeMap	mp	= (LinkedTreeMap) obj;
				Set				s	= mp.keySet();
				if (mp.containsKey("customer_id")) {
					Double valor = (Double) mp.get("customer_id");
					if (valor != 0) {
						mp.put("id", mp.get("customer_id"));
					}
					mp.remove("customer_id");
				}
			}
		});

		request.setParameters(jsonMap);
		// request.setParameters(JSONUtils.objectToMap(this));

		final JsonElement element = request.execute();
		flush();

		return JSONUtils.getAsObject((JsonObject) element, clazz);
	}

	public void setClassName(final String className) {
		this.className = className;
	}

	protected void setCreatedAt(DateTime createdAt) {
		this.createdAt = createdAt;
	}

	public void setId(final PK id) {
		this.id = id;
	}

	public String toJson() {
		return new GsonBuilder().registerTypeAdapter(DateTime.class, new DateTimeIsodateAdapter())
				.registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create().toJson(this);
	}

	@Override
	public String toString() {
		try {
			return this.toJson();
		} catch (UnsupportedOperationException e) {
			return getClass().getSimpleName().concat(String.format("=(%s)", this.id));
		}
	}

	protected boolean validate() {
		return true;
	}

	protected void validateId() {

		if (getId() == null) {
			throw new IllegalArgumentException("The Object ID must be set in order to use this method.");
		}

	}

}