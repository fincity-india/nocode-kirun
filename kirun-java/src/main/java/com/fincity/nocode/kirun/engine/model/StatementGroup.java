package com.fincity.nocode.kirun.engine.model;

import java.util.List;
import java.util.Map;

import com.fincity.nocode.kirun.engine.json.schema.Schema;
import com.fincity.nocode.kirun.engine.json.schema.type.SchemaType;
import com.fincity.nocode.kirun.engine.json.schema.type.Type;
import com.fincity.nocode.kirun.engine.namespaces.Namespaces;
import com.google.gson.JsonPrimitive;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class StatementGroup extends AbstractStatement {

	private static final long serialVersionUID = -550951603829175548L;

	private static final String SCHEMA_NAME = "StatementGroup";

	public static final Schema SCHEMA = new Schema().setNamespace(Namespaces.SYSTEM)
	        .setName(SCHEMA_NAME)
	        .setTitle(SCHEMA_NAME)
	        .setType(Type.of(SchemaType.OBJECT))
	        .setProperties(Map.of("statementGroupName", Schema.STRING, "comment", Schema.STRING, "description",
	                Schema.STRING, "position", Position.SCHEMA, "state", new Schema().setName("state")
	                        .setType(Type.of(SchemaType.STRING))
	                        .setEnums(List.of(new JsonPrimitive("EXPAND"), new JsonPrimitive("CLOSE")))));

	private String statementGroupName;
}
