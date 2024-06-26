package com.fincity.nocode.kirun.engine.function.system.math;

import static com.fincity.nocode.kirun.engine.namespaces.Namespaces.MATH;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import com.fincity.nocode.kirun.engine.function.reactive.AbstractReactiveFunction;
import com.fincity.nocode.kirun.engine.json.schema.Schema;
import com.fincity.nocode.kirun.engine.json.schema.type.SchemaType;
import com.fincity.nocode.kirun.engine.json.schema.type.Type;
import com.fincity.nocode.kirun.engine.model.Event;
import com.fincity.nocode.kirun.engine.model.EventResult;
import com.fincity.nocode.kirun.engine.model.FunctionOutput;
import com.fincity.nocode.kirun.engine.model.FunctionSignature;
import com.fincity.nocode.kirun.engine.model.Parameter;
import com.fincity.nocode.kirun.engine.runtime.reactive.ReactiveFunctionExecutionParameters;
import com.fincity.nocode.kirun.engine.util.primitive.PrimitiveUtil;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

public class Add extends AbstractReactiveFunction {

	static final String VALUE = "value";

	private static final FunctionSignature SIGNATURE = new FunctionSignature().setName("Add")
			.setNamespace(MATH)
			.setParameters(Map.of(VALUE, new Parameter().setSchema(Schema.ofNumber(VALUE))
					.setVariableArgument(true)))
			.setEvents(Map.ofEntries(Event.outputEventMapEntry(Map.of(VALUE, Schema.ofNumber(VALUE)))));

	@Override
	public FunctionSignature getSignature() {
		return SIGNATURE;
	}

	@Override
	protected Mono<FunctionOutput> internalExecute(ReactiveFunctionExecutionParameters context) {

		Mono<Number> sum = Mono.just(context.getArguments()
				.get(VALUE))
				.map(JsonArray.class::cast)
				.flatMapMany(Flux::fromIterable)
				.map(JsonPrimitive.class::cast)
				.map(e -> {
					Tuple2<SchemaType, Number> primitiveTypeTuple = PrimitiveUtil.findPrimitiveNumberType(e);
					return primitiveTypeTuple.getT2();
				})
				.reduce((a, b) -> {
					if (a instanceof Double || b instanceof Double)
						return a.doubleValue() + b.doubleValue();
					if (a instanceof Float || b instanceof Float)
						return a.floatValue() + b.floatValue();
					if (a instanceof Long || b instanceof Long)
						return a.longValue() + b.longValue();
					if (a instanceof BigInteger || b instanceof BigInteger)
						return BigInteger.valueOf(a.longValue()).add(BigInteger.valueOf(b.longValue()));
					if (a instanceof BigDecimal || b instanceof BigDecimal)
						return BigDecimal.valueOf(a.doubleValue()).add(BigDecimal.valueOf(b.doubleValue()));
					return (int) a + (int) b;
				})
				.map(Number.class::cast);

		return Flux.merge(sum.map(PrimitiveUtil::toPrimitiveType)
				.map(e -> Map.of(VALUE, (JsonElement) e))
				.map(EventResult::outputOf))
				.collectList()
				.map(FunctionOutput::new);
	}

	@Override
	public Map<String, Event> getProbableEventSignature(Map<String, List<Schema>> probableParameters) {

		Schema schema = probableParameters.get(VALUE)
				.stream()
				.flatMap(e -> e.getType()
						.getAllowedSchemaTypes()
						.stream())
				.reduce((a, b) -> a.ordinal() < b.ordinal() ? b : a)
				.map(e -> new Schema().setType(Type.of(e)))
				.orElse(Schema.ofNumber(VALUE));

		return Map.ofEntries(Event.outputEventMapEntry(Map.of(VALUE, schema)));
	}
}
