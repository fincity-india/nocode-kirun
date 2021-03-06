package com.fincity.nocode.kirun.engine.json.schema;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.fincity.nocode.kirun.engine.json.schema.type.SchemaType;
import com.fincity.nocode.kirun.engine.json.schema.validator.ArrayValidator;
import com.fincity.nocode.kirun.engine.json.schema.validator.BooleanValidator;
import com.fincity.nocode.kirun.engine.json.schema.validator.NullValidator;
import com.fincity.nocode.kirun.engine.json.schema.validator.NumberValidator;
import com.fincity.nocode.kirun.engine.json.schema.validator.StringValidator;
import com.fincity.nocode.kirun.engine.json.schema.validator.TypeValidator;
import com.fincity.nocode.kirun.engine.json.schema.validator.exception.SchemaValidationException;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

class TypeValidatorTest {

	@Test
	void testTypeValidator() {

		// For Number
		JsonObject element = new JsonObject();
		element.addProperty("value", 123);

		SchemaType type = SchemaType.INTEGER;
		Schema schema = new Schema();

		assertEquals(TypeValidator.validate(null, type, schema, null, element.get("value")),
		        NumberValidator.validate(type, null, schema, element.get("value")));

		// For String
		type = SchemaType.STRING;
		JsonObject elementString = new JsonObject();
		elementString.addProperty("value", "string");

		assertEquals(TypeValidator.validate(null, type, schema, null, elementString.get("value")),
		        StringValidator.validate(null, schema, elementString.get("value")));

		// For Boolean
		type = SchemaType.BOOLEAN;
		JsonObject elementBoolean = new JsonObject();
		elementBoolean.addProperty("value", Boolean.TRUE);

		assertEquals(TypeValidator.validate(null, type, schema, null, elementBoolean.get("value")),
		        BooleanValidator.validate(null, schema, elementBoolean.get("value")));

		// For Array
		type = SchemaType.ARRAY;
		JsonArray array = new JsonArray();
		array.add("abc");

		assertEquals(TypeValidator.validate(null, type, schema, null, array),
		        ArrayValidator.validate(null, schema, null, array));

		// For Null
		type = SchemaType.NULL;

		assertEquals(TypeValidator.validate(null, type, schema, null, null),
		        NullValidator.validate(null, schema, null));

		// For Exception;
		type = null;

		SchemaValidationException schemaValidationException = assertThrows(SchemaValidationException.class,
		        () -> TypeValidator.validate(null, null, schema, null, element));

		assertEquals(type + " is not a valid type.", schemaValidationException.getMessage());

	}

}
