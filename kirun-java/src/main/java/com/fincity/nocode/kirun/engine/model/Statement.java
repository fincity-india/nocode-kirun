package com.fincity.nocode.kirun.engine.model;

import java.io.Serializable;
import java.util.Map;

import com.fincity.nocode.kirun.engine.json.schema.Schema;
import com.fincity.nocode.kirun.engine.json.schema.object.AdditionalPropertiesType;
import com.fincity.nocode.kirun.engine.json.schema.type.SchemaType;
import com.fincity.nocode.kirun.engine.json.schema.type.Type;
import com.fincity.nocode.kirun.engine.namespaces.Namespaces;

import lombok.Data;

@Data
public class Statement implements Serializable {

	private static final long serialVersionUID = 8126173238268421930L;

	public static final String SCHEMA_NAME = "Statement";

	public static final Schema SCHEMA = new Schema().setNamespace(Namespaces.SYSTEM)
	        .setName(SCHEMA_NAME)
	        .setTitle(SCHEMA_NAME)
	        .setType(Type.of(SchemaType.OBJECT))
	        .setProperties(Map.of("statementName", Schema.STRING, "comment", Schema.STRING, "description",
	                Schema.STRING, "namespace", Schema.STRING, "name", Schema.STRING, "parameterMap",
	                new Schema().setName("parameterMap")
	                        .setAdditionalProperties(new AdditionalPropertiesType().setSchemaValue(Schema.STRING))));

	private String statementName;
	private String comment;
	private String description;
	private String namespace;
	private String name;
	private Map<String, String> parameterMap;
}
