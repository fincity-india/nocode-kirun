package com.fincity.nocode.kirun.engine.function.system.loop;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Map;

import org.junit.jupiter.api.Test;

import com.fincity.nocode.kirun.engine.exception.KIRuntimeException;
import com.fincity.nocode.kirun.engine.repository.KIRunFunctionRepository;
import com.fincity.nocode.kirun.engine.repository.KIRunSchemaRepository;
import com.fincity.nocode.kirun.engine.runtime.FunctionExecutionParameters;
import com.google.gson.JsonPrimitive;

class RangeLoopTest {

	@Test
	void test() {

		var loop = new RangeLoop();

//		StepVerifier
//		        .create(loop.execute(new FunctionExecutionParameters(new KIRunFunctionRepository(), new KIRunSchemaRepository()).setArguments(
//		                Map.of(RangeLoop.FROM, new JsonPrimitive(2), RangeLoop.TO, new JsonPrimitive(5)))))
//		        .expectNext(EventResult.of(Event.ITERATION, Map.of(RangeLoop.INDEX, new JsonPrimitive(2))))
//		        .expectNext(EventResult.of(Event.ITERATION, Map.of(RangeLoop.INDEX, new JsonPrimitive(3))))
//		        .expectNext(EventResult.of(Event.ITERATION, Map.of(RangeLoop.INDEX, new JsonPrimitive(4))))
//		        .expectNext(EventResult.outputOf(Map.of(RangeLoop.VALUE, new JsonPrimitive(5))))
//		        .expectComplete()
//		        .verify();

//		StepVerifier
//		        .create(loop.execute(
//		                new FunctionExecutionParameters(new KIRunFunctionRepository(), new KIRunSchemaRepository()).setArguments(Map.of(RangeLoop.FROM, new JsonPrimitive(201.56),
//		                        RangeLoop.TO, new JsonPrimitive(201.88), RangeLoop.STEP, new JsonPrimitive(0.08)))))
//		        .expectNext(EventResult.of(Event.ITERATION, Map.of(RangeLoop.INDEX, new JsonPrimitive(201.56))))
//		        .expectNext(EventResult.of(Event.ITERATION, Map.of(RangeLoop.INDEX, new JsonPrimitive(201.56 + 0.08))))
//		        .expectNext(EventResult.of(Event.ITERATION,
//		                Map.of(RangeLoop.INDEX, new JsonPrimitive(201.56 + 0.08 + 0.08))))
//		        .expectNext(EventResult.of(Event.ITERATION,
//		                Map.of(RangeLoop.INDEX, new JsonPrimitive(201.56 + 0.08 + 0.08 + 0.08))))
//		        .expectNext(EventResult.outputOf(Map.of(RangeLoop.VALUE, new JsonPrimitive(201.88))))
//		        .expectComplete()
//		        .verify();

		var params = new FunctionExecutionParameters(new KIRunFunctionRepository(), new KIRunSchemaRepository())
		        .setArguments(Map.of(RangeLoop.FROM, new JsonPrimitive("2"), RangeLoop.TO, new JsonPrimitive(5)));
		assertThrows(KIRuntimeException.class, () -> loop.execute(params));

	}
}
