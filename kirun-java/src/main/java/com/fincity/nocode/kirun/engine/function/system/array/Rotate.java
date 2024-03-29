package com.fincity.nocode.kirun.engine.function.system.array;

import java.util.List;
import java.util.Map;

import com.fincity.nocode.kirun.engine.model.EventResult;
import com.fincity.nocode.kirun.engine.model.FunctionOutput;
import com.fincity.nocode.kirun.engine.runtime.reactive.ReactiveFunctionExecutionParameters;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

import reactor.core.publisher.Mono;

public class Rotate extends AbstractArrayFunction {

	public Rotate() {
		super("Rotate", List.of(PARAMETER_ARRAY_SOURCE, PARAMETER_ROTATE_LENGTH), EVENT_RESULT_ARRAY);
	}

	@Override
	protected Mono<FunctionOutput> internalExecute(ReactiveFunctionExecutionParameters context) {

		JsonArray source = context.getArguments()
				.get(PARAMETER_ARRAY_SOURCE.getParameterName())
				.getAsJsonArray();

		JsonPrimitive distance = context.getArguments()
				.get(PARAMETER_ROTATE_LENGTH.getParameterName())
				.getAsJsonPrimitive();

		if (source.size() == 0)
			return Mono.just(new FunctionOutput(List.of(EventResult.outputOf(Map.of(EVENT_RESULT_NAME, source)))));

		int rotLen = distance.getAsInt();
		int size = source.size();
		rotLen = rotLen % size;

		source = duplicateArray(source);
		rotate(source, 0, rotLen - 1);
		rotate(source, rotLen, size - 1);
		rotate(source, 0, size - 1);

		return Mono.just(new FunctionOutput(List.of(EventResult.outputOf(Map.of(EVENT_RESULT_NAME, source)))));
	}

	private void rotate(JsonArray elements, int start, int end) {
		while (start < end) {
			JsonElement temp = elements.get(start);
			elements.set(start++, elements.get(end));
			elements.set(end--, temp);
		}
	}

}
