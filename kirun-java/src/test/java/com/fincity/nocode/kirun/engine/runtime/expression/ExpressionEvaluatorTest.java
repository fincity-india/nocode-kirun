package com.fincity.nocode.kirun.engine.runtime.expression;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;

import org.junit.jupiter.api.Test;

import com.fincity.nocode.kirun.engine.runtime.FunctionExecutionParameters;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

class ExpressionEvaluatorTest {

	@Test
	void test() {

		JsonObject phone = new JsonObject();
		phone.addProperty("phone1", "1234");
		phone.addProperty("phone2", "5678");
		phone.addProperty("phone3", "5678");

		JsonObject address = new JsonObject();
		address.addProperty("line1", "Flat 202, PVR Estates");
		address.addProperty("line2", "Nagvara");
		address.addProperty("city", "Benguluru");
		address.addProperty("pin", "560048");
		address.add("phone", phone);

		JsonArray arr = new JsonArray();
		arr.add(10);
		arr.add(20);
		arr.add(30);

		JsonObject obj = new JsonObject();
		obj.add("studentName", new JsonPrimitive("Kumar"));
		obj.add("math", new JsonPrimitive(20));
		obj.add("isStudent", new JsonPrimitive(true));
		obj.add("address", address);
		obj.add("array", arr);
		obj.add("num", new JsonPrimitive(1));

		Map<String, Map<String, Map<String, JsonElement>>> output = Map.of("step1",
		        Map.of("output", Map.of("name", new JsonPrimitive("Kiran"), "obj", obj)));

		FunctionExecutionParameters parameters = new FunctionExecutionParameters().setArguments(Map.of())
		        .setContext(Map.of())
		        .setOutput(output);

		assertEquals(new JsonPrimitive(10), new ExpressionEvaluator("3 + 7").evaluate(parameters));
		assertEquals(new JsonPrimitive("asdf333"), new ExpressionEvaluator("\"asdf\"+333").evaluate(parameters));
		assertEquals(new JsonPrimitive(422), new ExpressionEvaluator("10*11+12*13*14/7").evaluate(parameters));
		assertEquals(new JsonPrimitive(true), new ExpressionEvaluator("34 >> 2 = 8 ").evaluate(parameters));

		assertEquals(new JsonPrimitive(true), new ExpressionEvaluator("34 >> 2 = 8 ").evaluate(parameters));

		assertEquals(null, new ExpressionEvaluator("Steps.step1.output.name1").evaluate(parameters));

		assertEquals(new JsonPrimitive(true),
		        new ExpressionEvaluator("\"Kiran\" = Steps.step1.output.name ").evaluate(parameters));

		assertEquals(new JsonPrimitive(true),
		        new ExpressionEvaluator("null = Steps.step1.output.name1 ").evaluate(parameters));

		assertEquals(new JsonPrimitive(true),
		        new ExpressionEvaluator("Steps.step1.output.obj.phone.phone2 = Steps.step1.output.obj.phone.phone2 ")
		                .evaluate(parameters));

		assertEquals(new JsonPrimitive(true),
		        new ExpressionEvaluator(
		                "Steps.step1.output.obj.address.phone.phone2 != Steps.step1.output.address.obj.phone.phone1 ")
		                .evaluate(parameters));

		assertEquals(new JsonPrimitive(32),
		        new ExpressionEvaluator("Steps.step1.output.obj.array[Steps.step1.output.obj.num +1]+2")
		                .evaluate(parameters));

		assertEquals(new JsonPrimitive(60), new ExpressionEvaluator(
		        "Steps.step1.output.obj.array[Steps.step1.output.obj.num +1]+Steps.step1.output.obj.array[Steps.step1.output.obj.num +1]")
		        .evaluate(parameters));

		assertEquals(new JsonPrimitive(60), new ExpressionEvaluator(
		        "Steps.step1.output.obj.array[Steps.step1.output.obj.num +1]+Steps.step1.output.obj.array[Steps.step1.output.obj.num +1]")
		        .evaluate(parameters));

		assertEquals(new JsonPrimitive(32),
		        new ExpressionEvaluator("Steps.step1.output.obj.array[-Steps.step1.output.obj.num + 3]+2")
		                .evaluate(parameters));

		assertEquals(new JsonPrimitive(17.3533f), new ExpressionEvaluator("2.43*4.22+7.0987").evaluate(parameters));
	}

}
