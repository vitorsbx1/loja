function tamanhoMax(element, limit) {
	if (element.value.length >= limit) {
		element.value = element.value.substring(0, limit - 1);
	}
}