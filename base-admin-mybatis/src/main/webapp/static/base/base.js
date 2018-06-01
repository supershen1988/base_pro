function deleteEntity(url) {
	if (url == null || url == '' || typeof (url) == 'undefined') {
		return false;
	}

	layer.confirm('确定删除?', function() {
		location.href = url;
	}, function() {
		layer.closeAll();
	});
}