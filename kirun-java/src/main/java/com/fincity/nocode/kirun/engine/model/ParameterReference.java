package com.fincity.nocode.kirun.engine.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import com.fincity.nocode.kirun.engine.json.schema.Schema;
import com.fincity.nocode.kirun.engine.json.schema.type.SchemaType;
import com.fincity.nocode.kirun.engine.json.schema.type.Type;
import com.fincity.nocode.kirun.engine.namespaces.Namespaces;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ParameterReference implements Serializable {

	private static final long serialVersionUID = 9001249267359224945L;

	private static final String SCHEMA_NAME = "ParameterReference";

	public static final Schema SCHEMA = new Schema().setNamespace(Namespaces.SYSTEM)
	        .setName(SCHEMA_NAME)
	        .setType(Type.of(SchemaType.OBJECT))
	        .setProperties(Map.of("key", Schema.ofString("key"),

	                "type", Schema.ofString("references")
	                        .setEnums(List.of(new JsonPrimitive("EXPRESSION"), new JsonPrimitive("VALUE"))),

	                "value", Schema.ofAny("value"),

	                "expression", Schema.ofString("expression")));

	private String key;
	private ParameterReferenceType type;
	private JsonElement value; // NOSONAR - JSON element is not serialised for some reason.
	private String expression;
	private int order = 0;

	public ParameterReference(ParameterReference param) {

		this.key = param.key;
		this.type = param.type;
		this.value = param.value == null ? null : param.value.deepCopy();
		this.expression = param.expression;
		this.order = param.order;
	}

	public ParameterReference() {
		this.key = UUID.randomUUID()
		        .toString();
	}

	public static Entry<String, ParameterReference> of(String expression) {

		var param = new ParameterReference().setType(ParameterReferenceType.EXPRESSION)
		        .setExpression(expression);

		return Map.entry(param.getKey(), param);
	}

	public static Entry<String, ParameterReference> of(JsonElement value) {

		var param = new ParameterReference().setType(ParameterReferenceType.VALUE)
		        .setValue(value);

		return Map.entry(param.getKey(), param);
	}
}
