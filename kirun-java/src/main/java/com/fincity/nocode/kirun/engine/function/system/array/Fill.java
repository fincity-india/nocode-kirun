package com.fincity.nocode.kirun.engine.function.system.array;

import java.util.List;
import java.util.Map;

import com.fincity.nocode.kirun.engine.model.EventResult;
import com.fincity.nocode.kirun.engine.model.FunctionOutput;
import com.fincity.nocode.kirun.engine.runtime.FunctionExecutionParameters;
import com.google.gson.JsonNull;

public class Fill extends AbstractArrayFunction {

	public Fill() {
		super("Fill", List.of(PARAMETER_ARRAY_SOURCE, PARAMETER_INT_SOURCE_FROM, PARAMETER_INT_LENGTH, PARAMETER_ANY),
		        EVENT_RESULT_EMPTY);
	}

	@Override
	protected FunctionOutput internalExecute(FunctionExecutionParameters context) {

		var source = context.getArguments()
		        .get(PARAMETER_ARRAY_SOURCE.getParameterName())
		        .getAsJsonArray();
		var srcfrom = context.getArguments()
		        .get(PARAMETER_INT_SOURCE_FROM.getParameterName())
		        .getAsInt();
		var length = context.getArguments()
		        .get(PARAMETER_INT_LENGTH.getParameterName())
		        .getAsInt();
		var element = context.getArguments()
		        .get(PARAMETER_ANY.getParameterName());
		
		if (length == -1)
			length = source.size() - srcfrom;

		int add = (srcfrom + length) - source.size();

		if (add > 0) {
			for (int i = 0; i < add; i++)
				source.add(JsonNull.INSTANCE);
		}

		for (int i = srcfrom; i < (srcfrom + length); i++) {
			source.set(i, element.deepCopy());
		}

		return new FunctionOutput(List.of(EventResult.outputOf(Map.of())));
	}

}
