package com.fincity.nocode.kirun.engine.function.system.array;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Map;

import org.junit.jupiter.api.Test;

import com.fincity.nocode.kirun.engine.runtime.FunctionExecutionParameters;
import com.google.gson.JsonArray;
import com.google.gson.JsonPrimitive;

class FillTest {

	@Test
	void testInternalExecute() {

		Fill fill = new Fill();

		FunctionExecutionParameters fep = new FunctionExecutionParameters();
		JsonArray array = new JsonArray();
		array.add(0);
		array.add(1);

		fep.setArguments(Map.of("source", array, "element", new JsonPrimitive(3)))
		        .setContext(Map.of())
		        .setOutput(Map.of());

		fill.execute(fep);

		JsonArray finArray = new JsonArray();
		finArray.add(new JsonPrimitive(3));
		finArray.add(new JsonPrimitive(3));

		assertEquals(finArray, array);

		fep.setArguments(Map.of("source", array, "element", new JsonPrimitive(5), "srcFrom", new JsonPrimitive(2),
		        "length", new JsonPrimitive(5)))
		        .setContext(Map.of())
		        .setOutput(Map.of());

		finArray = new JsonArray();
		finArray.add(new JsonPrimitive(3));
		finArray.add(new JsonPrimitive(3));
		finArray.add(new JsonPrimitive(5));
		finArray.add(new JsonPrimitive(5));
		finArray.add(new JsonPrimitive(5));
		finArray.add(new JsonPrimitive(5));
		finArray.add(new JsonPrimitive(5));
		
		fill.execute(fep);

		assertEquals(finArray, array);
		
		fep.setArguments(Map.of("source", array, "element", new JsonPrimitive(25), "srcFrom", new JsonPrimitive(5)))
		        .setContext(Map.of())
		        .setOutput(Map.of());

		finArray = new JsonArray();
		finArray.add(new JsonPrimitive(3));
		finArray.add(new JsonPrimitive(3));
		finArray.add(new JsonPrimitive(5));
		finArray.add(new JsonPrimitive(5));
		finArray.add(new JsonPrimitive(5));
		finArray.add(new JsonPrimitive(25));
		finArray.add(new JsonPrimitive(25));
		
		fill.execute(fep);

		assertEquals(finArray, array);
		
		fep.setArguments(Map.of("source", array, "element", new JsonPrimitive(20), "srcFrom", new JsonPrimitive(-1)))
        .setContext(Map.of())
        .setOutput(Map.of());
		
		
		assertThrows(IndexOutOfBoundsException.class, () -> fill.execute(fep));
	}

}
