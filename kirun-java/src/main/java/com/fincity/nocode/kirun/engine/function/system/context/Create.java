package com.fincity.nocode.kirun.engine.function.system.context;

import static com.fincity.nocode.kirun.engine.namespaces.Namespaces.SYSTEM_CTX;

import java.util.List;
import java.util.Map;

import com.fincity.nocode.kirun.engine.exception.KIRuntimeException;
import com.fincity.nocode.kirun.engine.function.reactive.AbstractReactiveFunction;
import com.fincity.nocode.kirun.engine.json.schema.Schema;
import com.fincity.nocode.kirun.engine.json.schema.array.ArraySchemaType;
import com.fincity.nocode.kirun.engine.json.schema.array.ArraySchemaType.ArraySchemaTypeAdapter;
import com.fincity.nocode.kirun.engine.json.schema.object.AdditionalType;
import com.fincity.nocode.kirun.engine.json.schema.object.AdditionalType.AdditionalTypeAdapter;
import com.fincity.nocode.kirun.engine.json.schema.string.StringFormat;
import com.fincity.nocode.kirun.engine.json.schema.type.SchemaType;
import com.fincity.nocode.kirun.engine.json.schema.type.Type;
import com.fincity.nocode.kirun.engine.model.Event;
import com.fincity.nocode.kirun.engine.model.EventResult;
import com.fincity.nocode.kirun.engine.model.FunctionOutput;
import com.fincity.nocode.kirun.engine.model.FunctionSignature;
import com.fincity.nocode.kirun.engine.model.Parameter;
import com.fincity.nocode.kirun.engine.model.ParameterType;
import com.fincity.nocode.kirun.engine.runtime.ContextElement;
import com.fincity.nocode.kirun.engine.runtime.reactive.ReactiveFunctionExecutionParameters;
import com.fincity.nocode.kirun.engine.util.string.StringFormatter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;

import reactor.core.publisher.Mono;

public class Create extends AbstractReactiveFunction {

	static final String NAME = "name";

	static final String SCHEMA = "schema";

	private final Gson gson;

	public Create() {

		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(Type.class, new Type.SchemaTypeAdapter());
		AdditionalTypeAdapter ata = new AdditionalTypeAdapter();
		builder.registerTypeAdapter(AdditionalType.class, ata);
		ArraySchemaTypeAdapter asta = new ArraySchemaTypeAdapter();
		builder.registerTypeAdapter(ArraySchemaType.class, asta);
		gson = builder.create();
		ata.setGson(gson);
		asta.setGson(gson);
	}

	private static final FunctionSignature SIGNATURE = new FunctionSignature().setName("Create")
			.setNamespace(SYSTEM_CTX)
			.setParameters(Map.ofEntries(Parameter.ofEntry(NAME, new Schema().setName(NAME)
					.setType(Type.of(SchemaType.STRING))
					.setMinLength(1)
					.setFormat(StringFormat.REGEX)
					.setPattern("^[a-zA-Z_$][a-zA-Z_$0-9]*$"), ParameterType.CONSTANT),
					Parameter.ofEntry(SCHEMA, Schema.SCHEMA, ParameterType.CONSTANT)))
			.setEvents(Map.ofEntries(Event.outputEventMapEntry(Map.of())));

	@Override
	public FunctionSignature getSignature() {
		return SIGNATURE;
	}

	@Override
	protected Mono<FunctionOutput> internalExecute(ReactiveFunctionExecutionParameters context) {

		String name = context.getArguments()
				.get(NAME)
				.getAsString();

		if (context.getContext()
				.containsKey(name))
			throw new KIRuntimeException(StringFormatter.format("Context already has an element for '$' ", name));

		JsonElement schema = context.getArguments()
				.get(SCHEMA);
		Schema s = gson.fromJson(schema, Schema.class);

		context.getContext()
				.put(name,
						new ContextElement(s, s.getDefaultValue() == null ? JsonNull.INSTANCE : s.getDefaultValue()));

		return Mono.just(new FunctionOutput(List.of(EventResult.outputOf(Map.of()))));
	}

}
