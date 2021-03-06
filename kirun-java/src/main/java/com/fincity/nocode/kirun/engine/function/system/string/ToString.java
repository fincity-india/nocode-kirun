package com.fincity.nocode.kirun.engine.function.system.string;

import java.util.List;
import java.util.Map;

import com.fincity.nocode.kirun.engine.exception.KIRuntimeException;
import com.fincity.nocode.kirun.engine.function.AbstractFunction;
import com.fincity.nocode.kirun.engine.json.schema.Schema;
import com.fincity.nocode.kirun.engine.model.Event;
import com.fincity.nocode.kirun.engine.model.EventResult;
import com.fincity.nocode.kirun.engine.model.FunctionOutput;
import com.fincity.nocode.kirun.engine.model.FunctionSignature;
import com.fincity.nocode.kirun.engine.model.Parameter;
import com.fincity.nocode.kirun.engine.namespaces.Namespaces;
import com.fincity.nocode.kirun.engine.runtime.FunctionExecutionParameters;
import com.fincity.nocode.kirun.engine.util.string.StringFormatter;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

public class ToString extends AbstractFunction {

	protected static final String PARAMETER_INPUT_ANYTYPE_NAME = "anytype";

	protected static final String EVENT_RESULT_NAME = "result";

	protected static final Parameter PARAMETER_INPUT_ANYTYPE = new Parameter()
			.setParameterName(PARAMETER_INPUT_ANYTYPE_NAME).setSchema(Schema.ofAny(PARAMETER_INPUT_ANYTYPE_NAME));

	protected static final Event EVENT_STRING = new Event().setName(Event.OUTPUT)
			.setParameters(Map.of(EVENT_RESULT_NAME, Schema.ofString(EVENT_RESULT_NAME)));

	private final FunctionSignature signature = new FunctionSignature().setName("ToString")
			.setNamespace(Namespaces.STRING)
			.setParameters(Map.of(PARAMETER_INPUT_ANYTYPE.getParameterName(), PARAMETER_INPUT_ANYTYPE))
			.setEvents(Map.of(EVENT_STRING.getName(), EVENT_STRING));

	@Override
	public FunctionSignature getSignature() {
		return signature;
	}

	@Override
	protected FunctionOutput internalExecute(FunctionExecutionParameters context) {

		JsonElement input = context.getArguments().get(PARAMETER_INPUT_ANYTYPE_NAME);

		return new FunctionOutput(List.of(EventResult
				.outputOf(Map.of(EVENT_RESULT_NAME, new JsonPrimitive(input.getAsJsonPrimitive().getAsString())))));

	}

}
