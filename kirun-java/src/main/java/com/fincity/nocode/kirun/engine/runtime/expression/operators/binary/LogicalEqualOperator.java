package com.fincity.nocode.kirun.engine.runtime.expression.operators.binary;

import static com.fincity.nocode.kirun.engine.json.schema.type.SchemaType.BOOLEAN;
import static com.fincity.nocode.kirun.engine.json.schema.type.SchemaType.DOUBLE;
import static com.fincity.nocode.kirun.engine.json.schema.type.SchemaType.FLOAT;
import static com.fincity.nocode.kirun.engine.json.schema.type.SchemaType.LONG;
import static com.fincity.nocode.kirun.engine.json.schema.type.SchemaType.STRING;

import com.fincity.nocode.kirun.engine.json.schema.type.SchemaType;
import com.fincity.nocode.kirun.engine.util.primitive.PrimitiveUtil;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

import reactor.util.function.Tuple2;

public class LogicalEqualOperator implements BinaryOperator {

	@Override
	public JsonElement apply(JsonElement t, JsonElement u) {

		if ((t.isJsonObject() && u.isJsonObject()) || (t.isJsonArray() && u.isJsonArray()))
			return new JsonPrimitive(t.equals(u));

		if ((t.isJsonObject() && !u.isJsonObject()) || (!t.isJsonObject() && u.isJsonObject()))
			return new JsonPrimitive(false);

		if ((t.isJsonArray() && !u.isJsonArray()) || (!t.isJsonArray() && u.isJsonArray()))
			return new JsonPrimitive(false);

		Tuple2<SchemaType, Object> tType = PrimitiveUtil.findPrimitiveNullAsBoolean(t);
		Tuple2<SchemaType, Object> uType = PrimitiveUtil.findPrimitiveNullAsBoolean(u);

		if (tType.getT1() == STRING || uType.getT1() == STRING)
			return new JsonPrimitive(tType.getT2()
					.toString()
					.equals(uType.getT2()
							.toString()));

		if (tType.getT1() == BOOLEAN || uType.getT1() == BOOLEAN)
			return new JsonPrimitive(tType.getT2()
					.equals(uType.getT2()));

		Number tNumber = (Number) tType.getT2();
		Number uNumber = (Number) uType.getT2();

		if (tType.getT1() == DOUBLE || uType.getT1() == DOUBLE)
			return new JsonPrimitive(tNumber.doubleValue() == uNumber.doubleValue());

		if (tType.getT1() == FLOAT || uType.getT1() == FLOAT)
			return new JsonPrimitive(tNumber.floatValue() == uNumber.floatValue());

		if (tType.getT1() == LONG || uType.getT1() == LONG)
			return new JsonPrimitive(tNumber.longValue() == uNumber.longValue());

		return new JsonPrimitive(tNumber.intValue() == uNumber.intValue());
	}
}
