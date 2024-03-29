package com.fincity.nocode.kirun.engine.model;

import java.io.Serializable;
import java.util.Map;
import java.util.Map.Entry;

import com.fincity.nocode.kirun.engine.json.schema.Schema;
import com.fincity.nocode.kirun.engine.json.schema.type.SchemaType;
import com.fincity.nocode.kirun.engine.json.schema.type.Type;
import com.fincity.nocode.kirun.engine.namespaces.Namespaces;
import com.google.gson.JsonPrimitive;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
public class Parameter implements Serializable {

	private static final long serialVersionUID = 8040181175269846469L;

	private static final String SCHEMA_NAME = "Parameter";

	private static final String VALUE = "value";

	public static final Schema SCHEMA = new Schema().setNamespace(Namespaces.SYSTEM)
	        .setName(SCHEMA_NAME)
	        .setProperties(Map.of("schema", Schema.SCHEMA, "parameterName", Schema.ofString("parameterName"),
	                "variableArgument", Schema.of("variableArgument", SchemaType.BOOLEAN)
	                        .setDefaultValue(new JsonPrimitive(Boolean.FALSE))));

	public static final Schema EXPRESSION = new Schema().setNamespace(Namespaces.SYSTEM)
	        .setName("ParameterExpression")
	        .setType(Type.of(SchemaType.OBJECT))
	        .setProperties(Map.of("isExpression", Schema.ofBoolean("isExpression")
	                .setDefaultValue(new JsonPrimitive(true)), VALUE, Schema.ofAny(VALUE)));

	private Schema schema; // NOSONAR
	private String parameterName;
	private boolean variableArgument = false;
	private ParameterType type = ParameterType.EXPRESSION;

	public Parameter(Parameter param) {

		this.schema = param.schema == null ? null : new Schema(param.schema);
		this.parameterName = param.parameterName;
		this.variableArgument = param.variableArgument;
		this.type = param.type;
	}

	public static Entry<String, Parameter> ofEntry(String name, Schema schema) {
		return Map.entry(name, new Parameter().setParameterName(name)
		        .setSchema(schema));
	}

	public static Entry<String, Parameter> ofEntry(String name, Schema schema, ParameterType type) {
		return Map.entry(name, new Parameter().setParameterName(name)
		        .setSchema(schema)
		        .setType(type));
	}

	public static Entry<String, Parameter> ofEntry(String name, Schema schema, boolean variableArgument) {
		return Map.entry(name, new Parameter().setParameterName(name)
		        .setSchema(schema)
		        .setVariableArgument(variableArgument));
	}

	public static Parameter of(String name, Schema schema) {
		return new Parameter().setParameterName(name)
		        .setSchema(schema);
	}

	public static Parameter of(String name, Schema schema, ParameterType type) {
		return new Parameter().setParameterName(name)
		        .setSchema(schema)
		        .setType(type);
	}

	public static Parameter of(String name, Schema schema, boolean variableArgument) {
		return new Parameter().setParameterName(name)
		        .setSchema(schema)
		        .setVariableArgument(variableArgument);
	}
}
