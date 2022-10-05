package com.fincity.nocode.kirun.engine.function.system.string;

import static com.fincity.nocode.kirun.engine.namespaces.Namespaces.STRING;

import java.util.List;
import java.util.Map;

import com.fincity.nocode.kirun.engine.function.AbstractFunction;
import com.fincity.nocode.kirun.engine.json.schema.Schema;
import com.fincity.nocode.kirun.engine.model.Event;
import com.fincity.nocode.kirun.engine.model.EventResult;
import com.fincity.nocode.kirun.engine.model.FunctionOutput;
import com.fincity.nocode.kirun.engine.model.FunctionSignature;
import com.fincity.nocode.kirun.engine.model.Parameter;
import com.fincity.nocode.kirun.engine.runtime.FunctionExecutionParameters;
import com.google.gson.JsonPrimitive;

public class Reverse extends AbstractFunction {

	private static final String VALUE = "value";

	private static final FunctionSignature SIGNATURE = new FunctionSignature().setName("Reverse").setNamespace(STRING)
			.setParameters(Map.of(VALUE,
					new Parameter().setParameterName(VALUE).setSchema(Schema.ofString(VALUE))
							.setVariableArgument(false)))
			.setEvents(Map.ofEntries(Event.outputEventMapEntry(Map.of(VALUE, Schema.ofString(VALUE)))));

	@Override
	public FunctionSignature getSignature() {
		return SIGNATURE;
	}

	@Override
	protected FunctionOutput internalExecute(FunctionExecutionParameters context) {

		String acutalString = context.getArguments().get(VALUE).getAsString();
		Integer stringLength = acutalString.length() - 1;
		StringBuilder reversedString = new StringBuilder(stringLength);

		while (stringLength >= 0) {
			reversedString.append(acutalString.charAt(stringLength--));
		}

		return new FunctionOutput(
				List.of(EventResult.outputOf(Map.of(VALUE, new JsonPrimitive(reversedString.toString())))));
	}

}
