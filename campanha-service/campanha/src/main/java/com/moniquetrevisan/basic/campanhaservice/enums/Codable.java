package com.moniquetrevisan.basic.campanhaservice.enums;

import static java.util.stream.Stream.of;

public interface Codable<T extends Number> {

	T getCode();

	@SuppressWarnings("unchecked")
	static <T extends Number, E extends Enum<? extends Codable<T>>> E ofCode(Class<E> enumm, T code) {
		return of(enumm.getEnumConstants()).filter(type -> ((Codable<T>) type).getCode().equals(code)).findFirst()
				.orElseThrow(IllegalArgumentException::new);
	}

}