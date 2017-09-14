package mochi.tool.mongodb.cache;

import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Set;

import org.bson.BsonDocument;
import org.bson.Document;
import org.bson.codecs.Encoder;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.json.JsonWriterSettings;
import org.bson.types.ObjectId;

public class AdvancedFilter extends Document {
	
	private static final long serialVersionUID = 6297731997167536582L;
	
	public AdvancedFilter() {
		super();
	}

    /**
     * Create a Document instance initialized with the given key/value pair.
     *
     * @param key   key
     * @param value value
     */
    public AdvancedFilter(final String key, final Object value) {
        super(key, value);
    }

    /**
     * Creates a Document instance initialized with the given map.
     *
     * @param map initial map
     */
    public AdvancedFilter(final Map<String, Object> map) {
    	super(map);
    }

	/* (non-Javadoc)
	 * @see org.bson.Document#toBsonDocument(java.lang.Class, org.bson.codecs.configuration.CodecRegistry)
	 */
	@Override
	public <C> BsonDocument toBsonDocument(Class<C> documentClass,
			CodecRegistry codecRegistry) {
		// TODO Auto-generated method stub
		return super.toBsonDocument(documentClass, codecRegistry);
	}

	/* (non-Javadoc)
	 * @see org.bson.Document#append(java.lang.String, java.lang.Object)
	 */
	@Override
	public Document append(String key, Object value) {
		// TODO Auto-generated method stub
		return super.append(key, value);
	}

	/* (non-Javadoc)
	 * @see org.bson.Document#get(java.lang.Object, java.lang.Class)
	 */
	@Override
	public <T> T get(Object key, Class<T> clazz) {
		// TODO Auto-generated method stub
		return super.get(key, clazz);
	}

	/* (non-Javadoc)
	 * @see org.bson.Document#getInteger(java.lang.Object)
	 */
	@Override
	public Integer getInteger(Object key) {
		// TODO Auto-generated method stub
		return super.getInteger(key);
	}

	/* (non-Javadoc)
	 * @see org.bson.Document#getInteger(java.lang.Object, int)
	 */
	@Override
	public int getInteger(Object key, int defaultValue) {
		// TODO Auto-generated method stub
		return super.getInteger(key, defaultValue);
	}

	/* (non-Javadoc)
	 * @see org.bson.Document#getLong(java.lang.Object)
	 */
	@Override
	public Long getLong(Object key) {
		// TODO Auto-generated method stub
		return super.getLong(key);
	}

	/* (non-Javadoc)
	 * @see org.bson.Document#getDouble(java.lang.Object)
	 */
	@Override
	public Double getDouble(Object key) {
		// TODO Auto-generated method stub
		return super.getDouble(key);
	}

	/* (non-Javadoc)
	 * @see org.bson.Document#getString(java.lang.Object)
	 */
	@Override
	public String getString(Object key) {
		// TODO Auto-generated method stub
		return super.getString(key);
	}

	/* (non-Javadoc)
	 * @see org.bson.Document#getBoolean(java.lang.Object)
	 */
	@Override
	public Boolean getBoolean(Object key) {
		// TODO Auto-generated method stub
		return super.getBoolean(key);
	}

	/* (non-Javadoc)
	 * @see org.bson.Document#getBoolean(java.lang.Object, boolean)
	 */
	@Override
	public boolean getBoolean(Object key, boolean defaultValue) {
		// TODO Auto-generated method stub
		return super.getBoolean(key, defaultValue);
	}

	/* (non-Javadoc)
	 * @see org.bson.Document#getObjectId(java.lang.Object)
	 */
	@Override
	public ObjectId getObjectId(Object key) {
		// TODO Auto-generated method stub
		return super.getObjectId(key);
	}

	/* (non-Javadoc)
	 * @see org.bson.Document#getDate(java.lang.Object)
	 */
	@Override
	public Date getDate(Object key) {
		// TODO Auto-generated method stub
		return super.getDate(key);
	}

	/* (non-Javadoc)
	 * @see org.bson.Document#toJson()
	 */
	@Override
	public String toJson() {
		// TODO Auto-generated method stub
		return super.toJson();
	}

	/* (non-Javadoc)
	 * @see org.bson.Document#toJson(org.bson.json.JsonWriterSettings)
	 */
	@Override
	public String toJson(JsonWriterSettings writerSettings) {
		// TODO Auto-generated method stub
		return super.toJson(writerSettings);
	}

	/* (non-Javadoc)
	 * @see org.bson.Document#toJson(org.bson.codecs.Encoder)
	 */
	@Override
	public String toJson(Encoder<Document> encoder) {
		// TODO Auto-generated method stub
		return super.toJson(encoder);
	}

	/* (non-Javadoc)
	 * @see org.bson.Document#toJson(org.bson.json.JsonWriterSettings, org.bson.codecs.Encoder)
	 */
	@Override
	public String toJson(JsonWriterSettings writerSettings,
			Encoder<Document> encoder) {
		// TODO Auto-generated method stub
		return super.toJson(writerSettings, encoder);
	}

	/* (non-Javadoc)
	 * @see org.bson.Document#size()
	 */
	@Override
	public int size() {
		// TODO Auto-generated method stub
		return super.size();
	}

	/* (non-Javadoc)
	 * @see org.bson.Document#isEmpty()
	 */
	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return super.isEmpty();
	}

	/* (non-Javadoc)
	 * @see org.bson.Document#containsValue(java.lang.Object)
	 */
	@Override
	public boolean containsValue(Object value) {
		// TODO Auto-generated method stub
		return super.containsValue(value);
	}

	/* (non-Javadoc)
	 * @see org.bson.Document#containsKey(java.lang.Object)
	 */
	@Override
	public boolean containsKey(Object key) {
		// TODO Auto-generated method stub
		return super.containsKey(key);
	}

	/* (non-Javadoc)
	 * @see org.bson.Document#get(java.lang.Object)
	 */
	@Override
	public Object get(Object key) {
		// TODO Auto-generated method stub
		return super.get(key);
	}

	/* (non-Javadoc)
	 * @see org.bson.Document#put(java.lang.String, java.lang.Object)
	 */
	@Override
	public Object put(String key, Object value) {
		// TODO Auto-generated method stub
		return super.put(key, value);
	}

	/* (non-Javadoc)
	 * @see org.bson.Document#remove(java.lang.Object)
	 */
	@Override
	public Object remove(Object key) {
		// TODO Auto-generated method stub
		return super.remove(key);
	}

	/* (non-Javadoc)
	 * @see org.bson.Document#putAll(java.util.Map)
	 */
	@Override
	public void putAll(Map<? extends String, ?> map) {
		// TODO Auto-generated method stub
		super.putAll(map);
	}

	/* (non-Javadoc)
	 * @see org.bson.Document#clear()
	 */
	@Override
	public void clear() {
		// TODO Auto-generated method stub
		super.clear();
	}

	/* (non-Javadoc)
	 * @see org.bson.Document#keySet()
	 */
	@Override
	public Set<String> keySet() {
		// TODO Auto-generated method stub
		return super.keySet();
	}

	/* (non-Javadoc)
	 * @see org.bson.Document#values()
	 */
	@Override
	public Collection<Object> values() {
		// TODO Auto-generated method stub
		return super.values();
	}

	/* (non-Javadoc)
	 * @see org.bson.Document#entrySet()
	 */
	@Override
	public Set<java.util.Map.Entry<String, Object>> entrySet() {
		// TODO Auto-generated method stub
		return super.entrySet();
	}

	/* (non-Javadoc)
	 * @see org.bson.Document#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o) {
		// TODO Auto-generated method stub
		return super.equals(o);
	}

	/* (non-Javadoc)
	 * @see org.bson.Document#hashCode()
	 */
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}

	/* (non-Javadoc)
	 * @see org.bson.Document#toString()
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}

    
    
}
