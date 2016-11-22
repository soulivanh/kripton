/**
 * 
 */
package com.abubusoft.kripton.processor.bind.transform;

import static com.abubusoft.kripton.processor.core.reflect.PropertyUtility.getter;
import static com.abubusoft.kripton.processor.core.reflect.PropertyUtility.setter;

import com.abubusoft.kripton.binder.xml.XmlType;
import com.abubusoft.kripton.escape.StringEscapeUtils;
import com.abubusoft.kripton.processor.bind.model.BindProperty;
import com.fasterxml.jackson.core.JsonToken;
import com.squareup.javapoet.MethodSpec.Builder;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;

/**
 * @author xcesco
 *
 */
public class WrappedCompileTimeTransform extends AbstractBindTransform {
	
	protected Class<?> utilClazz;

	public WrappedCompileTimeTransform(Class<?> utilClazz)
	{
		this.utilClazz=utilClazz;
	}
	
	@Override
	public void generateSerializeOnXml(MethodSpec.Builder methodBuilder, String serializerName, TypeName beanClass, String beanName, BindProperty property) {
		XmlType xmlType = property.xmlInfo.xmlType;
		methodBuilder.beginControlFlow("if ($L.$L!=null) ", beanName, getter(beanClass, property));		
		switch (xmlType) {
		case ATTRIBUTE:
			methodBuilder.addStatement("$L.writeAttribute($S, $T.write($L.$L))", serializerName, property.xmlInfo.tagName, utilClazz, beanName, getter(beanClass, property));
			break;
		case TAG:
			methodBuilder.addStatement("$L.writeStartElement($S)", serializerName, property.xmlInfo.tagName);
			methodBuilder.addStatement("$L.writeCharacters($T.escapeXml10($T.write($L.$L)))", serializerName, StringEscapeUtils.class, utilClazz, beanName, getter(beanClass, property));
			methodBuilder.addStatement("$L.writeEndElement()", serializerName);
			break;
		case VALUE:
			methodBuilder.addStatement("$L.writeCharacters($T.escapeXml10($T.write($L.$L)))", serializerName, StringEscapeUtils.class, utilClazz, beanName, getter(beanClass, property));
			break;
		case VALUE_CDATA:
			methodBuilder.addStatement("$L.writeCData($T.escapeXml10($T.write($L.$L)))", serializerName, StringEscapeUtils.class, utilClazz, beanName, getter(beanClass, property));
			break;
		}

		methodBuilder.endControlFlow();

	}
	
	@Override
	public void generateSerializeOnJackson(MethodSpec.Builder methodBuilder, String serializerName, TypeName beanClass, String beanName, BindProperty property) {
		methodBuilder.beginControlFlow("if ($L.$L!=null) ", beanName, getter(beanClass, property));
		methodBuilder.addStatement("$L.writeStringField($S, $T.write($L.$L))", serializerName, property.jacksonName, utilClazz, beanName, getter(beanClass, property));
		methodBuilder.endControlFlow();
	}

	@Override
	public void generateParseOnXml(MethodSpec.Builder methodBuilder, String parserName, TypeName beanClass, String beanName, BindProperty property) {
		XmlType xmlType = property.xmlInfo.xmlType;
		
		switch (xmlType) {
		case ATTRIBUTE:
			methodBuilder.addStatement("$L."+setter(beanClass, property,"$T.read(attributeValue)"), beanName, utilClazz);
			break;
		case TAG:
			methodBuilder.addStatement("$L."+setter(beanClass, property,"$T.read($T.unescapeXml($L.getElementText()))"), beanName, utilClazz, StringEscapeUtils.class, parserName);
			break;
		case VALUE:
		case VALUE_CDATA:
			methodBuilder.addStatement("$L."+setter(beanClass, property,"$T.read($T.unescapeXml($L.getText()))"), beanName, utilClazz, StringEscapeUtils.class, parserName);			
			break;
		default:
			break;
		}

	}

	@Override
	public void generateSerializeOnJacksonAsString(MethodSpec.Builder methodBuilder, String serializerName, TypeName beanClass, String beanName, BindProperty property) {
		methodBuilder.beginControlFlow("if ($L.$L!=null) ", beanName, getter(beanClass, property));
		methodBuilder.addStatement("$L.writeStringField($S, $T.write($L.$L))", serializerName, property.jacksonName, utilClazz, beanName, getter(beanClass, property));
		methodBuilder.endControlFlow();
	}

	@Override
	public void generateParseOnJackson(Builder methodBuilder, String parserName, TypeName beanClass, String beanName, BindProperty property) {
		methodBuilder.beginControlFlow("if ($L.currentToken()!=$T.VALUE_NULL)", parserName, JsonToken.class);
		methodBuilder.addStatement("$L."+setter(beanClass, property," $T.read($L.getText())"), beanName, utilClazz, parserName);
		methodBuilder.endControlFlow();
		
	}
	
	@Override
	public void generateParseOnJacksonAsString(MethodSpec.Builder methodBuilder, String parserName, TypeName beanClass, String beanName, BindProperty property) {
		methodBuilder.beginControlFlow("if ($L.currentToken()!=$T.VALUE_NULL)", parserName, JsonToken.class);
		methodBuilder.addStatement("$L."+setter(beanClass, property,"$T.read($L.getText())"), beanName, utilClazz, parserName);
		methodBuilder.endControlFlow();
	}
	
}