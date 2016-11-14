package kripton70.typeconverters;

import java.io.IOException;

import kripton70.core.BinderSerializer;
import kripton70.core.BinderParser;

import com.fasterxml.jackson.core.JsonToken;

public class ShortConverter implements TypeConverter<Short> {

	@Override
	public Short parse(BinderParser parser) throws IOException {
		if (parser.getCurrentToken() == JsonToken.VALUE_NULL) {
			return null;
		} else if (parser.onlyText) {
			return Short.valueOf(parser.getText());
		} else {
			return parser.getShortValue();			
		}
	}

	@Override
	public void serialize(BinderSerializer generator, boolean writeFieldNameForObject, String fieldName, Short value) throws IOException {
		generator.writeFieldName(fieldName);
		generator.writeNumber(value);
		
	}

}
