package com.fincity.nocode.kirun.engine.model;

import java.util.Map;

import com.fincity.nocode.kirun.engine.json.schema.Schema;
import com.fincity.nocode.kirun.engine.json.schema.object.AdditionalPropertiesType;
import com.fincity.nocode.kirun.engine.namespaces.Namespaces;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class FunctionDefinition extends FunctionSignature {

	private static final long serialVersionUID = 4891316472479078149L;

	private static final String SCHEMA_NAME = "FunctionDefinition";

	public static final Schema SCHEMA = new Schema().setNamespace(Namespaces.SYSTEM)
	        .setName(SCHEMA_NAME)
	        .setProperties(Map.of("name", Schema.ofString("name"), "namespace", Schema.ofString("namespace"), "parameters",
	                Schema.ofArray("parameters", Parameter.SCHEMA), "events", Schema.ofObject("events")
	                        .setAdditionalProperties(new AdditionalPropertiesType().setSchemaValue(Event.SCHEMA)),
	                "parts", Schema.ofObject("parts")
	                        .setAdditionalProperties(
	                                new AdditionalPropertiesType().setSchemaValue(FunctionSignature.SCHEMA)),
	                "steps", Schema.ofObject("steps")
	                        .setAdditionalProperties(new AdditionalPropertiesType().setSchemaValue(Statement.SCHEMA))));

	private int version = 1;
	private Map<String, Statement> steps;
	private Map<String, StatementGroup> stepGroups;
}
