package com.fincity.nocode.kirun.engine.json.schema.validator;

import static com.fincity.nocode.kirun.engine.json.schema.validator.reactive.ReactiveSchemaValidator.path;

import java.util.List;
import java.util.regex.Pattern;

import com.fincity.nocode.kirun.engine.json.schema.Schema;
import com.fincity.nocode.kirun.engine.json.schema.string.StringFormat;
import com.fincity.nocode.kirun.engine.json.schema.validator.exception.SchemaValidationException;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

public class StringValidator {

	private static final Pattern TIME = Pattern
	        .compile("^([01]?[0-9]|2[0-3]):[0-5][0-9](:[0-5][0-9])?([+-][01][0-9]:[0-5][0-9])?$");

	private static final Pattern DATE = Pattern.compile("^[0-9]{4,4}-([0][0-9]|[1][0-2])-(0[1-9]|[1-2][1-9]|3[01])$");

	private static final Pattern DATETIME = Pattern.compile("^[0-9]{4,4}-([0][0-9]|[1][0-2])-(0[1-9]|[1-2][1-9]|3[01])T" // NOSONAR
	        + "([01]?[0-9]|2[0-3]):[0-5][0-9](:[0-5][0-9])?([+-][01][0-9]:[0-5][0-9])?$");

	private static final Pattern EMAIL = Pattern.compile("^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$"); // NOSONAR
	
	
	public static JsonElement validate(List<Schema> parents, Schema schema, JsonElement element) {

		if (element == null || element.isJsonNull())
			throw new SchemaValidationException(path(parents), "Expected a string but found null");

		if (!element.isJsonPrimitive())
			throw new SchemaValidationException(path(parents), element.toString() + " is not String");

		JsonPrimitive jp = (JsonPrimitive) element;
		if (!jp.isString())
			throw new SchemaValidationException(path(parents), element.toString() + " is not String");

		if (schema.getFormat() == StringFormat.TIME) {
			patternMatcher(parents, schema, element, jp, TIME, "time pattern");
		} else if (schema.getFormat() == StringFormat.DATE) {
			patternMatcher(parents, schema, element, jp, DATE, "date pattern");
		} else if (schema.getFormat() == StringFormat.DATETIME) {
			patternMatcher(parents, schema, element, jp, DATETIME, "date time pattern");
		} else if (schema.getFormat() == StringFormat.EMAIL) {
		    patternMatcher(parents, schema, element, jp, EMAIL, "email pattern");
		} else if (schema.getPattern() != null) {
			patternMatcher(parents, schema, element, jp, Pattern.compile(schema.getPattern()),
			        "pattern " + schema.getPattern());
		}

		int length = jp.getAsString()
		        .length();
		if (schema.getMinLength() != null && length < schema.getMinLength()) {
			throw new SchemaValidationException(path(parents),
			        "Expected a minimum of " + schema.getMinLength() + " characters");
		} else if (schema.getMaxLength() != null && length > schema.getMaxLength()) {
			throw new SchemaValidationException(path(parents),
			        "Expected a maximum of " + schema.getMaxLength() + " characters");
		}
		
		return element;
	}

	private static void patternMatcher(List<Schema> parents, Schema schema, JsonElement element, JsonPrimitive jp,
	        Pattern pattern, String message) {

		boolean matched = pattern.matcher(jp.getAsString()).matches();
		if (!matched) {
			throw new SchemaValidationException(path(parents),
			        element.toString() + " is not matched with the " + message);
		}
	}

	private StringValidator() {
	}
}
