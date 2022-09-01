package com.fincity.nocode.kirun.engine.runtime;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fincity.nocode.kirun.engine.Repository;
import com.fincity.nocode.kirun.engine.function.Function;
import com.fincity.nocode.kirun.engine.json.schema.Schema;
import com.fincity.nocode.kirun.engine.runtime.expression.tokenextractor.TokenValueExtractor;
import com.fincity.nocode.kirun.engine.runtime.tokenextractors.ArgumentsTokenValueExtractor;
import com.fincity.nocode.kirun.engine.runtime.tokenextractors.ContextTokenValueExtractor;
import com.fincity.nocode.kirun.engine.runtime.tokenextractors.OutputMapTokenValueExtractor;
import com.google.gson.JsonElement;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@RequiredArgsConstructor
public class FunctionExecutionParameters {

	private Map<String, ContextElement> context;
	private Map<String, JsonElement> arguments;
	private Map<String, List<Map<String, JsonElement>>> events;
	private StatementExecution statementExecution;
	private Map<String, Map<String, Map<String, JsonElement>>> steps;
	private int count;
	private final Repository<Function> functionRepository;
	private final Repository<Schema> schemaRepository;

	private HashMap<String, TokenValueExtractor> valueExtractors = new HashMap<>();

	public FunctionExecutionParameters setContext(Map<String, ContextElement> context) {

		this.context = context;
		var x = new ContextTokenValueExtractor(context);
		valueExtractors.put(x.getPrefix(), x);

		return this;
	}

	public FunctionExecutionParameters setSteps(Map<String, Map<String, Map<String, JsonElement>>> steps) {

		this.steps = steps;
		var x = new OutputMapTokenValueExtractor(steps);
		valueExtractors.put(x.getPrefix(), x);

		return this;
	}

	public FunctionExecutionParameters setArguments(Map<String, JsonElement> arguments) {

		this.arguments = arguments;
		var x = new ArgumentsTokenValueExtractor(arguments);
		valueExtractors.put(x.getPrefix(), x);

		return this;
	}

	public Map<String, TokenValueExtractor> getValuesMap() {
		return this.valueExtractors;
	}
}
