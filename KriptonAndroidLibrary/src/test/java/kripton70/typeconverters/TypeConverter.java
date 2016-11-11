package kripton70.typeconverters;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;

/**
 * Implement this interface in order to create a way to custom parse and serialize @JsonFields
 **/
public interface TypeConverter<T> {

	/**
	 * Called to parse the current object in the jsonParser to an object of type T
	 *
	 * @param jsonParser
	 *            The JsonParser that is pre-configured for this field.
	 * 
	 */
	T parse(JsonParser jsonParser, boolean onlyText) throws IOException;

	/**
	 * Called to serialize an object of type T to JSON using the JsonGenerator and field name.
	 *
	 * @param value
	 *            The object to serialize
	 * @param fieldName
	 *            The JSON field name of the object when it is serialized
	 * @param writeFieldNameForObject
	 *            If true, you're responsible for calling jsonGenerator.writeFieldName(fieldName) before writing the field
	 * @param jsonGenerator
	 *            The JsonGenerator object to which the object should be written
	 */
	void serialize(T value, String fieldName, boolean writeFieldNameForObject, JsonGenerator jsonGenerator) throws IOException;

}