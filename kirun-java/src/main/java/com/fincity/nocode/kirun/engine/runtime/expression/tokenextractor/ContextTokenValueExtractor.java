package com.fincity.nocode.kirun.engine.runtime.expression.tokenextractor;

import java.util.Map;

import com.fincity.nocode.kirun.engine.runtime.ContextElement;
import com.google.gson.JsonElement;

public class ContextTokenValueExtractor extends TokenValueExtractor {

	public static final String PREFIX = "Context.";

	private Map<String, ContextElement> context;

	public ContextTokenValueExtractor(Map<String, ContextElement> context) {

		this.context = context;
	}

	@Override
	protected JsonElement getValueInternal(String token) {
		String[] parts = token.split("\\.");

		String key = parts[1];
		int bIndex = key.indexOf('[');
		int fromIndex = 2;
		if (bIndex != -1) {
			key = parts[1].substring(0, bIndex);
			parts[1] = parts[1].substring(bIndex);
			fromIndex = 1;
		}

		return retrieveElementFrom(token, parts, fromIndex, context.getOrDefault(key, ContextElement.NULL)
		        .getElement());
	}

	@Override
	public String getPrefix() {
		return PREFIX;
	}
}
